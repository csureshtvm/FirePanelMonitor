package com.vision.amclient.conn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Logger;

import com.vision.amclient.monitor.AlarmMonitor;
import com.vision.amclient.monitor.messages.FPDeviceConfigMessage;
import com.vision.amclient.monitor.messages.FPMessage;
import com.vision.amclient.util.PropertyUtil;
import com.vision.amclient.util.StaticMessages;
import com.vision.amclient.util.WSCommUtil;



public class SocketConnector extends Thread{
	
	private String serverIP=null;
	private  int serverPort;
	Socket  clientSocket;
	DataInputStream  inputStream;
	DataOutputStream outputStream;
	boolean started=false;
	public boolean connectToServer=true;
	
	boolean stop=false;
	Logger log = Logger.getLogger("SocketConnector");
	StringBuffer dataBuffer=new StringBuffer();
	
	
	public SocketConnector(){
		serverIP= PropertyUtil.getInstance()
		.getConfigProperty("socket.server_ip");
		serverPort=Integer.parseInt(PropertyUtil.getInstance()
				.getConfigProperty("socket.server_port"));
		
		
	}
	public void connect() {
		 
		try {
		AlarmMonitor.getInstance().lastSocketConnectionInitiatedTime=System.currentTimeMillis();	
		log.info("Connected with server");
		clientSocket=new Socket(serverIP,serverPort);
		
		inputStream=new DataInputStream(clientSocket.getInputStream());
		outputStream=new DataOutputStream(clientSocket.getOutputStream());
		
		FPDeviceConfigMessage devConfigMessage=new FPDeviceConfigMessage(AlarmMonitor.getInstance().getMessageIndex(),WSCommUtil.customerId,WSCommUtil.buildingId,WSCommUtil.buildingFloorNo,WSCommUtil.deviceId);
		
		outputStream.write(devConfigMessage.getMessage().getBytes());
		System.out.println("Send Device Config Message:"+devConfigMessage);
		log.info("Message send successfully");
		AlarmMonitor.getInstance().isSocketConnected=true;
		if(!started){
		this.start();
		started=true;
		}
		connectToServer=true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AlarmMonitor.getInstance().isSocketConnected=false;
			System.out.println("Socket COnnection Failed. Reason : "+e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AlarmMonitor.getInstance().isSocketConnected=false;
			System.out.println("Socket COnnection Failed. Reason : "+e.getMessage());

		}
	}
	public void run(){
		 
		try {
			
			while(!stop){
				
				if(connectToServer){
				checkSocketMessage();
				}
				Thread.sleep(500);
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void checkSocketMessage() throws IOException, InterruptedException{
		try{
		
		while (inputStream.available() > 0) {
			int c = 0;
			byte[] b=new byte[1024];
			int len=inputStream.read(b);
			if(len>0){
			String str=new String(b,0,len);
			dataBuffer.append(str);
			}
			AlarmMonitor.getInstance().socketLastConnectedTime=System.currentTimeMillis();
		}
		if(dataBuffer.length()>0){
			System.out.println("Received Data------>"+dataBuffer.toString());
			processMessage();
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			
		
		
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
			case StaticMessages.MESSAGE_TYPE_REQ_DEVICE_RESET:
				System.out.println("Received MESSAGE_TYPE_REQ_DEVICE_RESET message");
				
				System.out.println("Going to set the Output PIN as high");
				AlarmMonitor.getInstance().processDeviceResetRequest();
				sendResponseMessage(StaticMessages.MESSAGE_TYPE_ACK_DEVICE_RESET,messageId);				
			break;
			case StaticMessages.MESSAGE_TYPE_REQ_SYSTEM_HEALTH:	
				System.out.println("Received MESSAGE_TYPE_REQ_SYSTEM_HEALTH message");
				sendResponseMessage(StaticMessages.MESSAGE_TYPE_ACK_SYSTEM_HEALTH,messageId);				
			break;
			
			case StaticMessages.MESSAGE_TYPE_ACK_DEVICE_ALARM:	
				System.out.println("Received MESSAGE_TYPE_ACK_DEVICE_ALARM message");
			break;
			
			case StaticMessages.MESSAGE_TYPE_ACK_DEVCE_CONFIG:	
				System.out.println("Received MESSAGE_TYPE_ACK_DEVCE_CONFIG message");
			break;
				
			case StaticMessages.MESSAGE_TYPE_ACK_DEVICE_RESET:	
				System.out.println("Received MESSAGE_TYPE_ACK_DEVICE_RESET message");
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
	
	public void sendFPMessages(){
		Vector<FPMessage> fpMessages=new Vector<FPMessage>();
		fpMessages.addAll(AlarmMonitor.getInstance().fpMessagesToServer);
		
		AlarmMonitor.getInstance().fpMessagesToServer.clear();
		for(FPMessage message  : fpMessages){
			try {
				System.out.println("Sending Message --> "+message.getMessage());
				outputStream.write(message.getMessage().getBytes());
				outputStream.flush();
				Thread.sleep(300);
				AlarmMonitor.getInstance().socketLastConnectedTime=System.currentTimeMillis();
				
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void closeSocketConnection() {
		log.info("Going to close Serial Connection");
		try {
			if (inputStream != null) {
				inputStream.close();

			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (clientSocket != null) {
				clientSocket.close();
			}
			connectToServer=false;
			this.join(5000);
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
	public void sendMessage(String message){
		try {
			System.out.println("Sending Message to server....."+message);
			outputStream.write(message.getBytes());
			System.out.println("Message send to server....."+message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendResponseMessage(int messageTypeId,String reqId){
		
		String responseMessage="MSGSTART$MSGID="+(AlarmMonitor.getInstance().getMessageIndex())+"&MSGTYPEID="+messageTypeId+"&REQID="+reqId+"$MSGEND";
		sendMessage(responseMessage);
	}
	
	public boolean resetSocketConnection(){
		closeSocketConnection();
		connect();
		return true;
	}
}