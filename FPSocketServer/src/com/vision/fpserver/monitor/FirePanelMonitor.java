package com.vision.fpserver.monitor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.LogManager;
import java.util.logging.Logger;


import com.vision.fpserver.conn.SocketConnector;
import com.vision.fpserver.manage.FileManager;
import com.vision.fpserver.model.FPClientMessage;
import com.vision.fpserver.model.FPConnection;
import com.vision.fpserver.model.Message;
import com.vision.fpserver.ui.LogsUI;
import com.vision.fpserver.ui.SocketTray;
import com.vision.fpserver.ui.SystemMessagesUI;
import com.vision.fpserver.util.StaticMessages;

public class FirePanelMonitor extends Thread {

	SocketTray tray = null;
	static FirePanelMonitor socketMonitor = null;
	public boolean stop = false;
	boolean startSocketConnection = true;
	public boolean gsmSendingEnabled = false;

	private long serialDataLastReceivedTime = 0;
	private long gsmDataLastReceivedTime = 0;

	private long serialDataCheckInterval = 300 * 1000;
	private long gsmDataCheckInterval = 300 * 1000;
	
	public List<String> phoneNumbers = new ArrayList<String>();

	public Vector<FPClientMessage> fpMessageListFromClient = new Vector<FPClientMessage>();
	
	public Vector<String> currDeviceFpMessageList = new Vector<String>();
	public Vector<Message> logMessages = new Vector<Message>();
	
	public Vector<Message> systemLogMessages = new Vector<Message>();

	//public LogsUI logUI = new LogsUI(true);
	//public SystemMessagesUI systemMessagesUI = new SystemMessagesUI(true);
	Logger log = Logger.getLogger("SocketMonitor");
	private long lastLogCheckedInterval=System.currentTimeMillis();
	private long initialUsedMemory=(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
	
	private static final int SYSTEM_LOG_MESSAGES_SZE=100;
	
	SocketConnector socketConnector=null;
	
	private long statusFileLastUpdatedTime=System.currentTimeMillis();
	//FileManager fileManager=null;
	
	public static Map<String, FPConnection> connectionMap=new HashMap<String, FPConnection>();
	


	private FirePanelMonitor() {
		//Properties preferences = new Properties();
		try {
			InputStream ins=getClass().getResourceAsStream("/logging.properties");
			//System.out.println(ins);
		    //preferences.load(ins);
		    LogManager.getLogManager().readConfiguration(ins);
		    //Commenting for testing
		   // fileManager=new FileManager();
		} catch (IOException ex)
		{
			
		}
	}

	public static FirePanelMonitor getInstance() {
		if (socketMonitor == null) {
			socketMonitor = new FirePanelMonitor();
		}
		return socketMonitor;
	}

	public void run() {
		//tray = new SocketTray();
		monitor();
	}

	public void monitor() {
		// updateSocketStatus("TestMessage", false);
		
		int count=0;
		while (!stop) {
			if (startSocketConnection) {

								
				if(socketConnector == null){
					socketConnector=new SocketConnector();
					socketConnector.connect();
				}

				
			}
			if((System.currentTimeMillis() - lastLogCheckedInterval) >=1000){
				long totalMem=Runtime.getRuntime().totalMemory();
				long freeMem=Runtime.getRuntime().freeMemory();
				long usedMem=totalMem-freeMem;
				//log.info("Memory Utilization : "+totalMem+":"+freeMem+":"+usedMem+":"+(usedMem-initialUsedMemory));
				lastLogCheckedInterval=System.currentTimeMillis();
			}
			

			if((System.currentTimeMillis() - statusFileLastUpdatedTime) >=5000){
				
				try {
					//fileManager.saveCurrentStatusFile();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				statusFileLastUpdatedTime=System.currentTimeMillis();
			}
			
			/*try {
				Thread.sleep(3000);
				if(count%2 == 0){
				updateSystemMessages("Message No "+((count/2)+1), StaticMessages.RECEIVE);
				}else{
					updateSystemMessages("Message No "+((count/2)+1), StaticMessages.SEND);
				}
				count++;
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}*/
			

		}
	}

	public void stopMonitor() {
		this.stop = true;
	}
	public void startMonitor() {
		this.start();
		monitor();
	}
		
	public void updateSocketStatus(String incomingmessage, int type) {
		//tray.showTrayMessage(incomingmessage, type);
		//socketStatus = incomingmessage;
		Message message = new Message(incomingmessage, type, new Date());
		addMessageToList(logMessages,message);
		//logMessages.add(message);
		//logUI.refereshMessages(logMessages);
	}
	
	private void addMessageToList(Vector list,Message message){
		if(list.size() >=SYSTEM_LOG_MESSAGES_SZE){
			list.removeElementAt(0);			
		}
		list.add(message);
	}
	
	public void updateSystemMessages(String incomingmessage, int type) {
		
		if(type == StaticMessages.SEND){
		//tray.showTrayMessage("Message Sent", StaticMessages.INFO);
		}else if(type == StaticMessages.RECEIVE){
		//tray.showTrayMessage("New Message Received", StaticMessages.INFO);
		}
		//socketStatus = incomingmessage;
		Message message = new Message(incomingmessage, type, new Date());
		addMessageToList(systemLogMessages,message);
		//systemLogMessages.add(message);
		//systemMessagesUI.refereshMessages(systemLogMessages);
		System.out.println("System Log Size--->"+systemLogMessages.size());
		System.out.println("Log Size--->"+logMessages.size());
	}

	public void startSocketConnection() {
		startSocketConnection = true;
	}

	public void setSerialDataLastReceivedTime(long timeinmillis) {
		serialDataLastReceivedTime = System.currentTimeMillis();
	}

	public void setGSMDataLastReceivedTime(long timeinmillis) {
		gsmDataLastReceivedTime = System.currentTimeMillis();
	}

	public void addFPMessageFromClient(String message, String clientName) {
		
		fpMessageListFromClient.add(new FPClientMessage(message, clientName));
	}
	public void addLocalFPMessage(String message) {
		currDeviceFpMessageList.add(message);
	}


	public Vector<String> getCurrDeviceFpMessageList() {
		return currDeviceFpMessageList;
	}

	public void setCurrDeviceFpMessageList(Vector<String> currDeviceFpMessageList) {
		this.currDeviceFpMessageList = currDeviceFpMessageList;
	}

	public Vector<FPClientMessage> getFpMessageListFromClient() {
		return fpMessageListFromClient;
	}

	public void setFpMessageListFromClient(Vector<FPClientMessage> fpMessageListFromClient) {
		this.fpMessageListFromClient = fpMessageListFromClient;
	}
	
	public void updateStatusMessagesInFile(String clientName, String message){
		try {
			//fileManager.updateCurrentStatusFileMessage(clientName, message);
			//fileManager.saveFirePanelMessageToFile(clientName, message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
 