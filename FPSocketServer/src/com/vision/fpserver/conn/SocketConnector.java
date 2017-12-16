package com.vision.fpserver.conn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.vision.fpserver.messages.FPMessage;
import com.vision.fpserver.model.AlarmDeviceResetDto;
import com.vision.fpserver.model.FPClientMessage;
import com.vision.fpserver.model.FPConnection;
import com.vision.fpserver.monitor.FirePanelMonitor;
import com.vision.fpserver.util.PropertyUtil;
import com.vision.fpserver.util.StaticMessages;
import com.vision.fpserver.util.WSCommUtil;

public class SocketConnector extends Thread{
	
	private  int serverPort;
	ServerSocket  serverSocket;
	boolean stop=false;
	Logger log = Logger.getLogger("SocketConnector");
	private Set<SocketConnectionHandler> connectionList=new HashSet<SocketConnectionHandler>();
	private long lastHealthRequestMessageSentTime=0;
	
	
	private long deviceResetMessageResendInterval=Long.parseLong(PropertyUtil.getInstance()
			.getConfigProperty("socket.devicereset_message_resend_interval"));
	private long deviceHealthCheckupInterval=Long.parseLong(PropertyUtil.getInstance()
			.getConfigProperty("socket.device_health_checkup_interval"));
	
	
	
	public static Map<String, Long> deviceResetMessageMap=new HashMap<String, Long>();
	
	public SocketConnector(){
		serverPort=Integer.parseInt(PropertyUtil.getInstance()
				.getConfigProperty("socket.server_port"));
	}
	public void connect() {
		 
		try {
			log.info("Creating server socket");
			serverSocket=new ServerSocket(serverPort);
			this.start();
			while(!stop){
			log.info("Waiting for client connection.........Port  : "+serverPort);
			System.out.println("Waiting for client connection.........Port  : "+serverPort);
			Socket clientSocket=serverSocket.accept();
			SocketConnectionHandler handler=new SocketConnectionHandler();
			connectionList.add(handler);
			handler.handleConnection(clientSocket);
			
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void run(){
		 
		
			while(!stop){
				
				try {
					if((System.currentTimeMillis() -  lastHealthRequestMessageSentTime)>=deviceHealthCheckupInterval){
						
						lastHealthRequestMessageSentTime=System.currentTimeMillis();
						for( SocketConnectionHandler handler :connectionList){
							try{
								FPMessage message=new FPMessage(handler.getMessageIndex(), System.currentTimeMillis(), StaticMessages.MESSAGE_TYPE_REQ_SYSTEM_HEALTH);
								handler.sendMessage(message.getMessage());
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}
						
					}
					
					
					try {
						List<AlarmDeviceResetDto> devResetList=WSCommUtil.getAllAlarmDeviceResetDetails();
						Map<String, Long> deviceResetMessageMapTemp=new HashMap<String, Long>();
						if(devResetList!=null && devResetList.size()>0){							
							for(AlarmDeviceResetDto dto : devResetList){
								deviceResetMessageMapTemp.put(dto.getDeviceId(), (deviceResetMessageMap.get(dto.getDeviceId())!=null? deviceResetMessageMap.get(dto.getDeviceId()):0));
							}
						}
						deviceResetMessageMap.clear();
						if(deviceResetMessageMapTemp!=null && deviceResetMessageMapTemp.size()>0){
						deviceResetMessageMap.putAll(deviceResetMessageMapTemp);
						}
						
					} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					//System.out.println("deviceResetMessageMap===============>"+deviceResetMessageMap+":"+connectionList.size());
					if(deviceResetMessageMap!=null && deviceResetMessageMap.size()>0){
						
						for( SocketConnectionHandler handler :connectionList){
							System.out.println("deviceResetMessageMap.get(handler.conn.getDeviceId()):"+deviceResetMessageMap.get(handler.conn.getDeviceId()));
							if(handler!=null && handler.conn!=null && deviceResetMessageMap.get(handler.conn.getDeviceId())!=null){
								
								if((System.currentTimeMillis() - deviceResetMessageMap.get(handler.conn.getDeviceId()))> deviceResetMessageResendInterval){
									FPMessage message=new FPMessage(handler.getMessageIndex(), System.currentTimeMillis(), StaticMessages.MESSAGE_TYPE_REQ_DEVICE_RESET);
									handler.sendMessage(message.getMessage());
									deviceResetMessageMap.put(handler.conn.getDeviceId(), System.currentTimeMillis());
								}
							}
							
							try{
								
							}catch(Exception ex){
								ex.printStackTrace();
							}
							}
						
					}
					
					Thread.sleep(20000);
					//checkSocketMessages();
					//sendFPMessagesToClient();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
	}
	
	
	
	
	/*private void sendFPMessagesToClient(){
		Vector<FPClientMessage> fpMessages=new Vector<FPClientMessage>();
		fpMessages.addAll(FirePanelMonitor.getInstance().getFpMessageListFromClient());
		
		FirePanelMonitor.getInstance().getFpMessageListFromClient().clear();
		
		for(FPClientMessage message  : fpMessages){
			
			
			 if(FirePanelMonitor.connectionMap != null && FirePanelMonitor.connectionMap.size()>0){
			        Set<Map.Entry<String, FPConnection>> entrySet=FirePanelMonitor.connectionMap.entrySet();
			        
			        for(Map.Entry<String, FPConnection> entry :  entrySet){
			        	
			        FPConnection fpCon=	entry.getValue();
			        if(fpCon.getConnectionName()!=null && fpCon.getConnectionName().equalsIgnoreCase(message.getClientName())){
			        	continue;
			        }
			        if(fpCon!=null && fpCon.getOutputStream()!=null){	
			        	
			        OutputStream outputStream=fpCon.getOutputStream();
			
			try {
				outputStream.write((message.getMessage()+"\n").getBytes());
				Thread.sleep(100);
				FirePanelMonitor.getInstance().updateSystemMessages("To::"+fpCon.getConnectionName()+"Message:"+message.getMessage(), StaticMessages.SEND);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				FirePanelMonitor.getInstance().updateSystemMessages("To::"+fpCon.getConnectionName()+"Message:"+message.getMessage(), StaticMessages.ERROR);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				FirePanelMonitor.getInstance().updateSystemMessages("To::"+fpCon.getConnectionName()+"Message:"+message.getMessage(), StaticMessages.ERROR);

			}
			
			 }
			  }}
			
		}
		
		
		
		
	}*/
	
	
     /* private void checkSocketMessages() {
    	  
    	 // log.info("Periodical check........checkSocketMessages: "+connectionMap.size());
    	  
    	 // System.out.println("Periodical check started............"+connectionMap.size());
        if(FirePanelMonitor.connectionMap != null && FirePanelMonitor.connectionMap.size()>0){
        Set<Map.Entry<String, FPConnection>> entrySet=FirePanelMonitor.connectionMap.entrySet();
        
        for(Map.Entry<String, FPConnection> entry :  entrySet){
        	
        FPConnection fpCon=	entry.getValue();
        if(fpCon!=null && fpCon.getInputStream()!=null){
        	
       // System.out.println("Cecking input data............"+fpCon.getConnectionName());
        //System.out.println("Gong to check inputstream of client----->"+fpCon.getConnectionName());	
        InputStream inputStream=fpCon.getInputStream();
		StringBuffer readBuffer = new StringBuffer();
		try {
			while (inputStream.available() > 0) {
				int c = 0;
				
				try {
					while ((c = inputStream.read()) != '\n') {
						if (c != 10)
							readBuffer.append((char) c);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					FirePanelMonitor.getInstance().updateSystemMessages("From::"+fpCon.getConnectionName()+"Message:"+readBuffer.toString(), StaticMessages.ERROR);
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FirePanelMonitor.getInstance().updateSystemMessages("From::"+fpCon.getConnectionName()+"Message:"+readBuffer.toString(), StaticMessages.ERROR);
			
		}
		
		if(readBuffer.length()>0){
			log.info("Received Message from client ----->"+fpCon.getConnectionName()+":"+readBuffer.toString());
			FirePanelMonitor.getInstance().addFPMessageFromClient(readBuffer.toString(),fpCon.getConnectionName());
			FirePanelMonitor.getInstance().updateSystemMessages("From::"+fpCon.getConnectionName()+"Message:"+readBuffer.toString(), StaticMessages.RECEIVE);
			FirePanelMonitor.getInstance().updateStatusMessagesInFile(fpCon.getConnectionName(),readBuffer.toString());
		}	
        }
        }
        }
	}*/
	
	
}