package com.vision.fpserver.conn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Logger;

import com.vision.fpserver.model.AlarmMessage;
import com.vision.fpserver.model.FPConnection;
import com.vision.fpserver.monitor.FirePanelMonitor;
import com.vision.fpserver.util.PropertyUtil;
import com.vision.fpserver.util.StaticMessages;
import com.vision.fpserver.util.WSCommUtil;

public class SocketConnectionHandler extends Thread{

	private Socket socket;
	DataInputStream  inputStream;
	DataOutputStream outputStream;
	boolean stop=false;
	Logger log = Logger.getLogger("SocketConnectionHandler");
	FPConnection conn=null;
	StringBuffer dataBuffer=new StringBuffer();
	private static boolean useSystemTimeAsEventTime=Boolean.parseBoolean(PropertyUtil.getInstance()
			.getConfigProperty("socket.use_systemtime_as_eventtime"));
	int messageIndex=10000;
	public void handleConnection(Socket clientSocket){
		this.socket=socket;
		try {
		
			
		inputStream=new DataInputStream(clientSocket.getInputStream());
		
		outputStream=new DataOutputStream(clientSocket.getOutputStream());
		
		log.info("Connected.............");
		
		
		conn=new FPConnection(clientSocket,inputStream,outputStream);
		
		new Thread(this).start();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(!stop){
			
			try {
				//System.out.println("Waiting for message from client");
				while (inputStream.available() > 0) {
					int c = 0;
					byte[] b=new byte[1024];
					int len=inputStream.read(b);
					if(len>0){
					String str=new String(b,0,len);
					dataBuffer.append(str);
					}
				}
				if(dataBuffer.length()>0){
					System.out.println("Received Data------>"+dataBuffer.toString());
					processMessage();
				}
				Thread.sleep(500);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}
	}
	
	public void closeConnection(){
		
	}
	
	
	public void processMessage(){
		boolean processed=false;
		while(!processed){
		String dataStr=dataBuffer.toString();
		
		if(!dataStr.isEmpty()){
		System.out.println("Message In Buffer----->"+dataStr);
		}
		if((dataStr.contains("MSGSTART$") && dataStr.contains("$MSGEND"))){
			int messageStartIndex=dataStr.indexOf("MSGSTART$");
			dataStr=dataStr.substring(messageStartIndex);
			int messageEndIndex=dataStr.indexOf("$MSGEND");
			if(messageStartIndex==-1 || messageEndIndex==-1){
				dataBuffer=new StringBuffer(dataStr);
				processed=true;
			}else{
				String message=dataStr.substring(messageStartIndex+"MSGSTART$".length(),messageEndIndex);
				
				dataStr=dataStr.substring(messageEndIndex+"$MSGEND".length());
				dataBuffer=new StringBuffer(dataStr);
				processAlarmMessage(message);
			}
			
		}else{
			processed=true;
		}
		}
		
	}
	
	public void processAlarmMessage(String alarmMessage){
		System.out.println("Going to process Message----->"+alarmMessage);
		
		String messageId=getMessageProperty(alarmMessage,"MSGID");
		String messageType=getMessageProperty(alarmMessage,"MSGTYPEID");
		int messageTypeInt=-1;
		try{
		messageTypeInt=messageType!=null?Integer.parseInt(messageType):-1;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		String msgTime=getMessageProperty(alarmMessage,"MSGTIME");
		
		if(messageTypeInt!=-1){
			String customerId=null;
			String bldgId=null;
			
			switch(messageTypeInt){
			case StaticMessages.MESSAGE_TYPE_REQ_DEVCE_CONFIG:
				System.out.println("Received message MESSAGE_TYPE_REQ_DEVCE_CONFIG ");
				String deviceId=getMessageProperty(alarmMessage,"DEVICEID");
				customerId=getMessageProperty(alarmMessage,"CUSTID");
				bldgId=getMessageProperty(alarmMessage,"BLDGID");
				String floorNo=getMessageProperty(alarmMessage,"FLOORNO");
				if(conn!=null){
					conn.setBuildngId(bldgId);
					conn.setCustomerId(customerId);
					conn.setBuildngId(bldgId);
					conn.setFloorNo(floorNo);
					conn.setDeviceId(deviceId);
					saveAlarmDevice();
				}
				
				sendResponseMessage(StaticMessages.MESSAGE_TYPE_ACK_DEVCE_CONFIG,messageId);				
			break;
			case StaticMessages.MESSAGE_TYPE_REQ_DEVICE_ALARM:	
				System.out.println("Received message MESSAGE_TYPE_REQ_DEVICE_ALARM ");
				customerId=getMessageProperty(alarmMessage,"CUSTID");
				bldgId=getMessageProperty(alarmMessage,"BLDGID");
				String system=getMessageProperty(alarmMessage,"SYSTEM");
				String signal=getMessageProperty(alarmMessage,"SIGNAL");
				String value=getMessageProperty(alarmMessage,"VALUE");
				sendResponseMessage(StaticMessages.MESSAGE_TYPE_ACK_DEVICE_ALARM,messageId);
				Date eventDate=null;
				if(useSystemTimeAsEventTime){
					eventDate=new Date(System.currentTimeMillis());
				}else{
					eventDate=new Date(Long.parseLong(msgTime));
				}
				AlarmMessage fpMessage=new AlarmMessage(signal, system, value,eventDate);
				
				fpMessage.setBuildingId(conn.getBuildngId());
				fpMessage.setFloorNo(conn.getFloorNo());
				sendAlarmMessageToServer(fpMessage);
				
			break;
			
			case StaticMessages.MESSAGE_TYPE_ACK_DEVICE_RESET:
				System.out.println("Received message MESSAGE_TYPE_ACK_DEVICE_RESET ");				
			break;
			case StaticMessages.MESSAGE_TYPE_ACK_SYSTEM_HEALTH:
				System.out.println("Received message MESSAGE_TYPE_ACK_SYSTEM_HEALTH ");
				saveDeviceHealth();
			break;
			case StaticMessages.MESSAGE_TYPE_RES_DEVICE_RESET:
				String resetStatus=getMessageProperty(alarmMessage,"STATUS");
				if("1".equals(resetStatus)){
					resetStatus="Y";
				}else{
					resetStatus="N";
				}
				updateDeviceResetStatus(resetStatus);
			break;
			case StaticMessages.MESSAGE_TYPE_REQ_SOFTWARE_MESSAGE:
				customerId=getMessageProperty(alarmMessage,"CUSTID");
				bldgId=getMessageProperty(alarmMessage,"BLDGID");
				String softwareMessageValue=getMessageProperty(alarmMessage,"VALUE");
				eventDate=null;
				if(useSystemTimeAsEventTime){
					eventDate=new Date(System.currentTimeMillis());
				}else{
					eventDate=new Date(Long.parseLong(msgTime));
				}
				
				sendSoftwareMessageToServer(customerId, bldgId,conn.getDeviceId(), eventDate, softwareMessageValue, false);
				
			break;
			
			case StaticMessages.MESSAGE_TYPE_REQ_SOFTWARE_MESSAGE_UPDATE:
				customerId=getMessageProperty(alarmMessage,"CUSTID");
				bldgId=getMessageProperty(alarmMessage,"BLDGID");
				String softwareMessageUpdateValue=getMessageProperty(alarmMessage,"VALUE");
				eventDate=null;
				if(useSystemTimeAsEventTime){
					eventDate=new Date(System.currentTimeMillis());
				}else{
					eventDate=new Date(Long.parseLong(msgTime));
				}
				sendSoftwareMessageToServer(customerId, bldgId,conn.getDeviceId(), eventDate, softwareMessageUpdateValue, true);
				
			break;
				
			}
		}
		
		
	}
	
	public static String getMessageProperty(String alarmMessage,String propertyName){
		
		String responseVal=null;
		if(alarmMessage.contains(propertyName)){
			int index=alarmMessage.indexOf(propertyName);
			String propertyMsg=alarmMessage.substring(index);
			
			int startIndex=propertyMsg.indexOf("=");
			int endIndex=propertyMsg.indexOf("&");
			if(startIndex!=-1){
				if(endIndex!=-1){
					responseVal=propertyMsg.substring(startIndex+1,endIndex).trim();
				}else{
					responseVal=propertyMsg.substring(startIndex+1).trim();
				}
			}
		
		}
		return responseVal;
	}
	public void sendMessage(String message){
		try {
			outputStream.write(message.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendResponseMessage(int messageTypeId,String reqId){
		String responseMessage="MSGSTART$MSGID="+(getMessageIndex())+"&MSGTYPEID="+messageTypeId+"&REQID="+reqId+"$MSGEND";
		sendMessage(responseMessage);
	}
	public static void main(String[] args) {
		SocketConnectionHandler handler=new SocketConnectionHandler();
		
		String message="MSGSTART$MSGID=1001&MSGTYPEID=1&MSGTIME=123546466 &CUSTID=1 &BLDGID=1&SYSTEM=FIRE&SIGNAL=NORMAL$MSGEND";
		handler.dataBuffer=new StringBuffer(message);
		handler.processMessage();
		/*System.out.println(getMessageProperty(message,"MSGID"));
		System.out.println(getMessageProperty(message,"MSGTYPEID"));
		System.out.println(getMessageProperty(message,"MSGTIME"));
		System.out.println(getMessageProperty(message,"CUSTID"));
		System.out.println(getMessageProperty(message,"BLDGID"));
		System.out.println(getMessageProperty(message,"SYSTEM"));
		System.out.println(getMessageProperty(message,"SIGNAL"));*/
	}
	
	public boolean sendAlarmMessageToServer(AlarmMessage message) {
		
		return WSCommUtil.saveAlarmEvent(message.getSystem(), message.getSignal(),message.getValue(),"N", message.getMessageDate().getTime(),message.getFloorNo(),message.getBuildingId());
		
	}
	
	public boolean saveAlarmDevice() {
		
		return WSCommUtil.saveAlarmDevice(conn.getBuildngId(),conn.getDeviceId(),conn.getFloorNo());
		
	}
	public boolean saveDeviceHealth() {
		return WSCommUtil.updateAlarmHealth(conn.getDeviceId());
	}
	public boolean updateDeviceResetStatus(String resetStatus) {
		return WSCommUtil.updateDeviceResetStatus(conn.getDeviceId(),resetStatus);
	}
	public int getMessageIndex(){
		if(messageIndex>99999){
			messageIndex=10000;
		}
		return messageIndex++;
	}
	
	public boolean sendSoftwareMessageToServer(String customerId,String buildingId,String deviceId,Date messageTime,String messageVal,boolean isUpdate) {
	
		return WSCommUtil.saveSoftwareMessage(customerId, buildingId, deviceId, messageTime, messageVal, isUpdate);
	
	}
	
	
	
}
