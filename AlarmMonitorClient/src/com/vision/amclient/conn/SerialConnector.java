package com.vision.amclient.conn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TooManyListenersException;
import java.util.Vector;
import java.util.logging.Logger;

import javax.comm.*;

import com.vision.amclient.model.AlarmEventMessage;
import com.vision.amclient.model.AlarmMessage;
import com.vision.amclient.monitor.AlarmMonitor;
import com.vision.amclient.util.PropertyUtil;
import com.vision.amclient.util.StaticMessages;
import com.vision.amclient.util.WSCommUtil;

/*import gnu.io.CommPortIdentifier;
 import gnu.io.PortInUseException;
 import gnu.io.SerialPort;
 import gnu.io.SerialPortEvent;
 import gnu.io.SerialPortEventListener;
 import gnu.io.UnsupportedCommOperationException;*/

public class SerialConnector implements Runnable, SerialPortEventListener {

	static CommPortIdentifier portId;
	static Enumeration portList;
	SerialPort serialPort;
	InputStream inputStr;
	OutputStream outputStr;
	Thread readThread;
	Logger log = Logger.getLogger("SerialConnector");
	private Vector<String> serialPortList = new Vector<String>();

	boolean controllerConfig = true;
	boolean stop=false;
	public SerialConnector() {
		
		if(!AlarmMonitor.isInputViaTestUI){
		log.info("Serial Port Initialization Started");
		portList = CommPortIdentifier.getPortIdentifiers();
		serialPortList.clear();
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				serialPortList.add(portId.getName());
			}
		}
		portList = CommPortIdentifier.getPortIdentifiers();
		log.info("PortList Empty? :" + portList.hasMoreElements());
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			log.info("Port Details----->" + portId + ":"
					+ (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
					+ ":" + portId.getName());

			String serialportId = PropertyUtil.getInstance()
					.getConfigProperty("serialport.comportid");
			int baudrate = Integer.parseInt(PropertyUtil.getInstance()
					.getConfigProperty("serialport.baudrate"));
			int databits = Integer.parseInt(PropertyUtil.getInstance()
					.getConfigProperty("serialport.databits"));
			int stopbit = Integer.parseInt(PropertyUtil.getInstance()
					.getConfigProperty("serialport.stopbits"));
			int parity = Integer.parseInt(PropertyUtil.getInstance()
					.getConfigProperty("serialport.parity"));

			
			boolean portFound = false;
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL
					&& portId.getName().equals(serialportId)) {
				portFound = true;
				log.info("Port ID :" + portId.getName() + " available");
				try {
					serialPort = (SerialPort) portId.open("FacilityConnector",
							1000);

					inputStr = serialPort.getInputStream();
					outputStr = serialPort.getOutputStream();

					// String
					// baudrate=PropertyUtil.getInstance().getConfigProperty("serialport.baudrate");
					serialPort.setSerialPortParams(baudrate, databits, stopbit,
							parity);
					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
					serialPort.addEventListener(this);

					serialPort.notifyOnDataAvailable(true);
					outputStr.write("Connected".getBytes());

					log.info("Serial Port initialized with baudrate :"
							+ baudrate);
				} catch (PortInUseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log
							.info("ERROR while initializing serial connection!!!!!!!!! "
									+ e.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log
							.info("ERROR while initializing serial connection!!!!!!!!! "
									+ e.toString());
				} catch (TooManyListenersException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log
							.info("ERROR while initializing serial connection!!!!!!!!! "
									+ e.toString());
				} catch (UnsupportedCommOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log
							.info("ERROR while initializing serial connection!!!!!!!!! "
									+ e.toString());
				}
				break;
			}
		}
		}
		//StatusMonitor.getInstance().updateSerialPortList(serialPortList);
		//readThread = new Thread(this);
		//readThread.start();
	}

	public void run() {
		try {
			while (!stop){
			List<AlarmMessage> messages=new ArrayList<AlarmMessage>();
			messages.addAll(AlarmMonitor.getInstance().getAlarmMessages());
			AlarmMonitor.getInstance().getAlarmMessages().clear();
			//System.out.println("AlarmMonitor.getInstance().getAlarmMessages()----"+AlarmMonitor.getInstance().getAlarmMessages().size());
			for(AlarmMessage message: messages){
				if(!processAlarmMessage(message)){
					AlarmMonitor.getInstance().getAlarmMessages().add(0, message);
				}
			}
			Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void serialEvent(SerialPortEvent event) {

		log.info("Serial Port Data arrived");
		// TODO Auto-generated method stub
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.CD:
		case SerialPortEvent.DSR:
		case SerialPortEvent.OE:
		case SerialPortEvent.CTS:
		case SerialPortEvent.FE:
		case SerialPortEvent.RI:
		case SerialPortEvent.PE:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			processSerialMessage();
			break;
		}

		log.info("Data read finished");
	}

	private void processSerialMessage() {

		
		AlarmMonitor.getInstance().setSerialDataLastReceivedTime(
				System.currentTimeMillis());
	
		try {
			String str = "";
			while (inputStr.available() > 0) {
				byte[] dataArr = new byte[1024];
				int len = inputStr.read(dataArr, 0, dataArr.length);
				if (len > 0) {
					str += new String(dataArr, 0, len);
				}
				int datareaddelay = Integer.parseInt(PropertyUtil.getInstance()
						.getConfigProperty(
								"serialport.datareaddelay"));
				Thread.sleep(datareaddelay);

			}

			
			List<AlarmEventMessage> alarmMessageList=getAllAlarmMessages(str);
			AlarmMonitor.getInstance().getAlarmMessages().addAll(alarmMessageList);
			AlarmMonitor.getInstance().getAlarmMessagesFromSerial().add(str);
			log.info("Data received from Serial Port : " + str);
			String[] arr = str.split("\n");
			Vector<AlarmMessage> secMessages = new Vector<AlarmMessage>();
			boolean stop = false;
			/*for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < 16; j++) {
					String port = "A" + j;
					if (arr[i].contains(port)) {
						int index = arr[i].indexOf("A" + j);
						String message = arr[i].substring(
								index + ("A" + j).length()).trim();
						Date date = new Date(System.currentTimeMillis());
						AlarmMessage secMessage = new AlarmMessage(message,"fire".contains(message.toLowerCase())?StaticMessages.ALARM_TYPE_FIRE:StaticMessages.ALARM_TYPE_NORMAL,date);
						secMessages.add(secMessage);

						int statFromSerialPort = 0;
						try {
							statFromSerialPort = Integer.parseInt(message
									.trim());
						} catch (Exception e) {
							e.printStackTrace();
							log
									.info("ERROR while processing serial data!!!!!!!!! "
											+ e.toString());
						}
						
						AlarmMonitor.getInstance().addAlarmMessage(message,"fire".contains(message.toLowerCase())?StaticMessages.ALARM_TYPE_FIRE:StaticMessages.ALARM_TYPE_NORMAL);
						

					}
				}
			}*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("ERROR while processing serial data!!!!!!!!! "
					+ e.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("ERROR while processing serial data!!!!!!!!! "
					+ e.toString());
		}

	}
	private List<AlarmEventMessage> getAllAlarmMessages(String str){
		
		boolean eofReached=false;
		String processedStr=str;
		List<AlarmEventMessage> resultList=new ArrayList<AlarmEventMessage>();
		while(!eofReached){
			
			
			int startIndexMessage=processedStr.indexOf("@128");
			int startIndexNormalAlarm=processedStr.indexOf("U0-");
			int startIndexFireAlarm=processedStr.indexOf("U1-");
			String messageString=null;
			boolean isFireAlarm=false;
			int endIndex=0;
			if(startIndexMessage>0 && (startIndexFireAlarm>0 || startIndexNormalAlarm>0) && (startIndexFireAlarm<startIndexNormalAlarm)){
				messageString=processedStr.substring(startIndexMessage,startIndexFireAlarm+3);
				isFireAlarm=true;
				endIndex=startIndexFireAlarm+3;
			}else if(startIndexMessage>0 && (startIndexFireAlarm>0 || startIndexNormalAlarm>0) && (startIndexFireAlarm>startIndexNormalAlarm)){
				messageString=processedStr.substring(startIndexMessage,startIndexNormalAlarm+3);
				endIndex=startIndexNormalAlarm+3;
			}
			
			if(messageString!=null){
				processedStr=processedStr.substring(endIndex+1,processedStr.length());
				
				String[] arr1=processedStr.split(" ");
				if(arr1.length>0){
					String[] arr2=arr1[0].split("-");
					if(arr2.length>2){
						int floorNo=Integer.parseInt(arr2[1]);
						AlarmEventMessage evMessage=new AlarmEventMessage(isFireAlarm?"FIRE ALARM":"NORMAL ALARM", isFireAlarm?StaticMessages.ALARM_TYPE_FIRE:StaticMessages.ALARM_TYPE_NORMAL, new Date(), floorNo);
					resultList.add(evMessage);
					
					
					}
				}
				
				
			}else{
				eofReached=true;
			}
			
		}
		return resultList;
	}
	public void closeSerialConnection() {
		log.info("Going to close Serial Connection");
		try {
			if (inputStr != null) {
				inputStr.close();

			}
			if (outputStr != null) {
				outputStr.close();
			}
			if (serialPort != null) {
				serialPort.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("ERROR while closing serial connection!!!!!!!!! "
					+ e.toString());
		}
		log.info("Serial Connection Closed");
	}

	

	public boolean processAlarmMessage(AlarmMessage message) {
		
		return WSCommUtil.saveAlarmEvent((message.getType()==StaticMessages.ALARM_TYPE_FIRE?"FIRE":"NORMAL"), message.getMessage(),"N", message.getMessageDate().getTime(),(((AlarmEventMessage)message).getFloorNo()));
		
	}

	public static void main(String[] args) {
		//new SerialConnector(true);
	}
}
