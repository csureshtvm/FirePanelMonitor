package com.vision.notificationservices.communicator;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TooManyListenersException;
import java.util.Vector;
import java.util.logging.Logger;


import javax.comm.*;

import com.vision.notificationservices.monitor.AlarmNotificationMonitor;
import com.vision.notificationservices.util.PropertyUtil;
public class SMSCommunicator implements SerialPortEventListener {

	static boolean regnFinished=false;
	static CommPortIdentifier  portId;
	static Enumeration portList;
	SerialPort serialPort;
	InputStream inputStr;
	OutputStream outputStr;
	
	//BufferedWriter bw; 
	Thread readThread;
	Logger log = Logger.getLogger("SMSCommunicator");
	private Vector<String> serialPortList = new Vector<String>();
	boolean stop=false;
	public Vector<String> messageList=new Vector<String>();
	public boolean serialConnected=false;
	
	private long lastRegnTimeInterval=0;
	public SMSCommunicator() {
		//System.out.println("**********************111111111111111111111*************");
		//initializeSerialCommunication();
	}
int i=0;
	
	public void initializeSerialCommunication(){

		log.info("Serial Port Initialization Started ---"+(++i));
		portList = CommPortIdentifier.getPortIdentifiers();
		serialPortList.clear();
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				serialPortList.add(portId.getName());
			}
		}
		System.out.println("PortList=====>"+serialPortList);
		serialConnected=false;
		boolean connected=false;
		portList = CommPortIdentifier.getPortIdentifiers();
		log.info("GSMConnector PortList Empty? :" + portList.hasMoreElements());
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			log.info("Port Details----->" + portId + ":"
					+ (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
					+ ":" + portId.getName());

			String serialportId = PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.gsmport.comportid");
			int baudrate = Integer.parseInt( PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.gsmport.baudrate"));
			int databits = Integer.parseInt( PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.gsmport.databits"));
			int stopbit = Integer.parseInt( PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.gsmport.stopbits"));
			int parity = Integer.parseInt( PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.gsmport.parity"));
			

			
			
			boolean portFound = false;
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL
					&& portId.getName().equals(serialportId)) {
				portFound = true;
				log.info("Port ID :" + portId.getName() + " available");
				try {
					serialPort = (SerialPort) portId.open("GSMConnector",
							1000);

					inputStr = serialPort.getInputStream();
					outputStr = serialPort.getOutputStream();
				//	bw= new BufferedWriter(new OutputStreamWriter(outputStr, "UTF-8"));
					
					// String
					// baudrate=PropertyUtil.getInstance().getConfigProperty("serialport.baudrate");
					serialPort.setSerialPortParams(baudrate, databits, stopbit,
							parity);
					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
					serialPort.addEventListener(this);

					serialPort.notifyOnDataAvailable(true);
					//outputStr.write("Connected".getBytes());
					AlarmNotificationMonitor.getInstance().updateNotfnStatusMessage("GSM Connected", false);
					connected=true;
					serialConnected=true;
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
			if(!connected){
				AlarmNotificationMonitor.getInstance().updateNotfnStatusMessage("GSM Connection Error", true);
			}
		}
		AlarmNotificationMonitor.getInstance().gsmLastConnectionIntiatedTime=System.currentTimeMillis();
		//StatusMonitor.getInstance().updateSerialPortList(serialPortList);
		/*if(connected){
		readThread = new Thread(this);
		readThread.start();
		}*/
	
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

		
			//SocketMonitor.getInstance().setGSMDataLastReceivedTime(
			//		System.currentTimeMillis());
		
		try {
			String str = "";
			while (inputStr.available() > 0) {
				byte[] dataArr = new byte[1024];
				int len = inputStr.read(dataArr, 0, dataArr.length);
				if (len > 0) {
					str += new String(dataArr, 0, len);
				}
				//int datareaddelay = Integer.parseInt(PropertyUtil.getInstance()
				//		.getGSMConfigProperty(
				//				"gsmport.datareaddelay"));
				System.out.println("Data Received From GSM Connector --->"+str);

				Thread.sleep(100);//datareaddelay

			}

					

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

	public void closeSerialConnection() {
		log.info("Going to close Serial Connection");
		try {
			this.stop=true;
			
			if (inputStr != null) {
				inputStr.close();

			}
			if (outputStr != null) {
				outputStr.close();
			}
			if (serialPort != null) {
				serialPort.close();
			}
			readThread.join(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("ERROR while closing serial connection!!!!!!!!! "
					+ e.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Serial Connection Closed");
	}

	
	
	
	
	public boolean sendGSMMessage(String message,String mobileNo )
	{
		// msgdata[0].length()
	   // String msg = message.substring(3,29)+message.substring(31,71)+"\n"+message.substring(87,127);  
//	    String msg = msgdata[0];	
	    char ch = '"';
	    String dest = ch + mobileNo + ch;  // 11 Digit Mobile Number.
	   // String line1 = "AT+CREG?\r\n";
	    String line1 = "at+cmgf=1\r\n";
	    String line2 = "AT+CMGS=" + dest + "\r\n";
	    String line3 = message+"\r\n";

	    System.out.println(message+": diverter fired:"+mobileNo); 
	    System.out.println(mobileNo); 
	    
	    
	    System.out.println("*****************************************GSM MESSAGE START************************************************************"); 
	    
	    System.out.println(line1); 
	    System.out.println(line2);
	    System.out.println(line3);
	    System.out.println("*****************************************GSM MESSAGE END************************************************************");
	    try {
	    	System.out.println("Message sending. Started Regn.....");
	    	if(!regnFinished || ((System.currentTimeMillis()-lastRegnTimeInterval)>300000)){
	          outputStr.write(line1.getBytes());
	           Thread.sleep(5000); 
	           regnFinished=true; 
	           lastRegnTimeInterval=System.currentTimeMillis();
	    	}
	            System.out.println("Going to send SMS");
	            outputStr.write(line2.getBytes());
	            //bw.write(line2);
		           
	            Thread.sleep(2000);  
	            System.out.println("Text message output to srtream....");
	           
	            //bw.write(line3);
	            //bw.write(26);
	            outputStr.write(line3.getBytes());
	            outputStr.write(26);
	           outputStr.flush();
	           Thread.sleep(100);
	            System.out.println("Message send --------");
	            return true;
	            //saveSendMessageToFile(line1+line2+line3);
	            
		                } catch (Exception e) {
		                    System.out.println("Error writing message " + e);
		                }   
		  return false;
		
	}

	/*public void saveSendMessageToFile(String message){
		try {
		FileWriter out=null;
		out=new FileWriter(PropertyUtil.getInstance()
				.getGSMConfigProperty("messagefileloc"),true);
        PrintWriter bw = new PrintWriter(out);
        
        
			bw.println(message);
			bw.flush();
			
			bw.close();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}*/

	
	public static void main(String[] args) {
		//new GSMConnector(true);
	}
	
}
