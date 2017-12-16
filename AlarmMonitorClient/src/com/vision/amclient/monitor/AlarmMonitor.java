package com.vision.amclient.monitor;

import java.awt.SystemTray;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.serotonin.bacnet4j.exception.BACnetException;
import com.vision.amclient.conn.BacnetMonitor;
import com.vision.amclient.conn.GPIOCommunicator;
import com.vision.amclient.conn.SerialConnector;
import com.vision.amclient.conn.SocketConnector;
//import com.vision.amclient.conn.SerialConnector;
import com.vision.amclient.model.AlarmMessage;
import com.vision.amclient.monitor.messages.FPAlarmMessage;
import com.vision.amclient.monitor.messages.FPMessage;
import com.vision.amclient.ui.LogsUI;
import com.vision.amclient.ui.SocketTray;
import com.vision.amclient.ui.SystemMessagesUI;
import com.vision.amclient.ui.TestMessageInputUI;
import com.vision.amclient.util.PropertyUtil;
import com.vision.amclient.util.StaticMessages;
import com.vision.amclient.util.WSCommUtil;


public class AlarmMonitor extends Thread {

	SocketTray tray = null;
	static AlarmMonitor socketMonitor = null;
	public boolean stop = false;
	boolean startSocketConnection = true;
	public boolean gsmSendingEnabled = false;

	private long serialDataLastReceivedTime = 0;
	private long gsmDataLastReceivedTime = 0;

	private long serialDataCheckInterval = 300 * 1000;
	private long gsmDataCheckInterval = 300 * 1000;
	
	public List<String> phoneNumbers = new ArrayList<String>();

	public Vector<String> alarmMessagesFromSerial = new Vector<String>();
	public Vector<AlarmMessage> alarmMessages = new Vector<AlarmMessage>();
	
	public Vector<AlarmMessage> systemLogMessages = new Vector<AlarmMessage>();

	public LogsUI logUI = null;//new LogsUI(true);
	public SystemMessagesUI systemMessagesUI =null;// new SystemMessagesUI(true);
	Logger log = Logger.getLogger("SocketMonitor");
	private long lastLogCheckedInterval=System.currentTimeMillis();
	private long initialUsedMemory=(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
	BacnetMonitor bacnetMonitor=null;
	
	int testCount=0;
		
	private static final int SYSTEM_LOG_MESSAGES_SZE=100;
	
	public boolean isSocketConnected=false;	
	public long lastSocketmessageReceivedTime=System.currentTimeMillis();
	public long lastSocketConnectionInitiatedTime=System.currentTimeMillis();
	
	SerialConnector serialCon=null;//
	public static boolean isInputViaTestUI=false;
	GPIOCommunicator gpioCommunicator=null;
	public TestMessageInputUI testMessageInputUI=null;//new TestMessageInputUI();
	long lastFireMessageReceivedTime=System.currentTimeMillis();
	long lastNormalMessageReceivedTime=System.currentTimeMillis();
	public int lastAddedMessageType=0;
	public int lastRecievedMessageType=0;
	public AlarmMessage lastReceivedMessage=null;
	String inputMode=PropertyUtil.getInstance()
	.getConfigProperty("fpclient.input_mode");
	long deviceHealthUpdateTimeInterval=Long.parseLong(PropertyUtil.getInstance()
	.getConfigProperty("fpclient.alarm_device_health_update_interval"))*1000;
	boolean alarmDeviceDetailsSaved=false;
	long lastDeviceHealthUpdatedTime=0;
	
	SocketConnector socketConnector=null;
	public Vector<FPMessage> fpMessagesToServer = new Vector<FPMessage>();
	public int messageIndex=10000;
	String sendMode=PropertyUtil.getInstance().getConfigProperty("fpclient.send_mode");
	boolean sendModeSocket=false;
	
	
	
	long bacnetDataCheckTimeInterval=Long.parseLong(PropertyUtil.getInstance()
			.getConfigProperty("bacnet.data_check_interval"))*1000;
	
	long socketResetTimeInterval=(PropertyUtil.getInstance()
	.getConfigProperty("fpclient.timetoresetsocket_send_when_no_response")!=null?Long.parseLong(PropertyUtil.getInstance()
			.getConfigProperty("fpclient.timetoresetsocket_send_when_no_response")):300)*1000;
	
	boolean bacnetEnabled=Boolean.parseBoolean(PropertyUtil.getInstance().getConfigProperty("bacnet.enabled"));
	public long lastBacnetDataCheckTime=0;
	
	public long socketLastConnectedTime=System.currentTimeMillis();
	
	
	
	private AlarmMonitor() {
		//Properties preferences = new Properties();
		try {
			InputStream ins=getClass().getResourceAsStream("/logging.properties");
			//System.out.println(ins);
		    //preferences.load(ins);
		    LogManager.getLogManager().readConfiguration(ins);
		} catch (IOException ex)
		{
			
		}
	}

	public static AlarmMonitor getInstance() {
		if (socketMonitor == null) {
			System.out.println("AlarmMonitor is null.Going to initialize......");
			socketMonitor = new AlarmMonitor();
			System.out.println("Going to retrieve Socet Monitor Instance :"+socketMonitor);
		}
		
		return socketMonitor;
	}

	public void run() {
		//tray = new SocketTray();
		
		monitor();
		
	}

	public void monitor() {
		// updateSocketStatus("TestMessage", false);
		System.out.println("Alarm Monitor Started......................");
		System.out.println("Initializer test:"+AlarmMonitor.getInstance().getAlarmMessages());
		int count=0;
		if(sendMode!=null && "socket".equalsIgnoreCase(sendMode)){
			sendModeSocket=true;
			socketConnector=new SocketConnector();
			socketConnector.connect();
		}
		
		if(bacnetEnabled && bacnetMonitor==null){
			
			 
		    // int localPort = Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("bacnet.local_device_port")) ;			// port 0xBAC0
		    // int localDeviceId = Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("bacnet.local_device_id")) ;			// port 0xBAC0
		     
			bacnetMonitor=new BacnetMonitor();
			try {
				bacnetMonitor.initializeMonitor();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		while (!stop) {
			if(!sendModeSocket){
			if(!alarmDeviceDetailsSaved){
				alarmDeviceDetailsSaved=WSCommUtil.saveAlarmDevice();
			}
			if((System.currentTimeMillis()-lastDeviceHealthUpdatedTime)>deviceHealthUpdateTimeInterval){
				WSCommUtil.updateAlarmHealth();
				lastDeviceHealthUpdatedTime=System.currentTimeMillis();
			}
			}
			
			//System.out.println("Going to check Messages ---->"+AlarmMonitor.getInstance().getAlarmMessages().size());
			if((System.currentTimeMillis() - lastLogCheckedInterval) >=1000){
				long totalMem=Runtime.getRuntime().totalMemory();
				long freeMem=Runtime.getRuntime().freeMemory();
				long usedMem=totalMem-freeMem;
				//log.info("Memory Utilization : "+totalMem+":"+freeMem+":"+usedMem+":"+(usedMem-initialUsedMemory));
				lastLogCheckedInterval=System.currentTimeMillis();
			}
			
			if(sendModeSocket && (System.currentTimeMillis()-lastSocketmessageReceivedTime)>socketResetTimeInterval){
				socketConnector.resetSocketConnection();
				lastSocketmessageReceivedTime=System.currentTimeMillis();
			}
			
			if((AlarmMonitor.getInstance().getAlarmMessages().size()>0) || (AlarmMonitor.getInstance().fpMessagesToServer.size()>0)){
				
				if("raspberry".equalsIgnoreCase(inputMode) || bacnetEnabled){
				processAndSendSerialMessages();
				}
			}
		
			/*if((System.currentTimeMillis() - lastTestMessageSendTime)  >=testMessageAddInterval){
				addLocalFPMessage("Test Message "+(++testCount)+"\n");
				lastTestMessageSendTime=System.currentTimeMillis();
				
			}*/
			
			
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
			
			
			if(gpioCommunicator !=null){
				gpioCommunicator.checkAndUpdateOutput();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println("Bacnet periodic check---->"+bacnetEnabled+":"+(System.currentTimeMillis()-lastBacnetDataCheckTime)+":"+bacnetDataCheckTimeInterval+":"+((System.currentTimeMillis()-lastBacnetDataCheckTime)>bacnetDataCheckTimeInterval));
			if(bacnetEnabled && (System.currentTimeMillis()-lastBacnetDataCheckTime)>bacnetDataCheckTimeInterval){
				try {
					bacnetMonitor.doAlarmPointsPeriodicCheck();
				} catch (BACnetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lastBacnetDataCheckTime=System.currentTimeMillis();
			}
			

		}
	}

	public void stopMonitor() {
		this.stop = true;
	}
	public void startMonitor() {
		this.start();
		
			if("socket".equalsIgnoreCase(inputMode)){
				serialCon=new SerialConnector();
			}else if("raspberry".equalsIgnoreCase(inputMode)){
				if(gpioCommunicator==null){
					gpioCommunicator=new GPIOCommunicator();
					try {
						gpioCommunicator.initialize();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if("testui".equalsIgnoreCase(inputMode)){
				testMessageInputUI=new TestMessageInputUI();
			}
		
		
	}
	
	
	public void addAlarmMessage(String incomingmessage, int type ) {
		if(tray!=null){
		tray.showTrayMessage(incomingmessage, type);
		}
		lastReceivedMessage= new AlarmMessage(incomingmessage, type, new Date());
		lastRecievedMessageType=type;
		//if(((lastAddedMessageType==StaticMessages.ALARM_TYPE_FIRE) && (System.currentTimeMillis()-lastFireMessageReceivedTime)<2000) || ((lastAddedMessageType==StaticMessages.ALARM_TYPE_NORMAL) && (System.currentTimeMillis()-lastNormalMessageReceivedTime)<2000)|| ((type==StaticMessages.ALARM_TYPE_NORMAL && lastAddedMessageType!=StaticMessages.ALARM_TYPE_FIRE))){
		//	return;
		//}
		if(((lastAddedMessageType==StaticMessages.ALARM_TYPE_FIRE) && (System.currentTimeMillis()-lastFireMessageReceivedTime)<3000) || 
				((lastAddedMessageType==StaticMessages.ALARM_TYPE_NORMAL)) ){
			return;
		}
		//socketStatus = incomingmessage;
		if(!sendModeSocket){
		AlarmMessage message = new AlarmMessage(incomingmessage, type, new Date());
		addMessageToList(alarmMessages,message);
		}
		
		
		if(type==StaticMessages.ALARM_TYPE_FIRE){
		lastFireMessageReceivedTime=System.currentTimeMillis();
		}else if(type==StaticMessages.ALARM_TYPE_NORMAL){
		lastNormalMessageReceivedTime=System.currentTimeMillis();
		}
		lastAddedMessageType=type;
		
		
		if(sendModeSocket){
			
			String system=(type == StaticMessages.ALARM_TYPE_FIRE)?"ALARM":"NORMAL";
			String signal="BURGLAR_ALARM";
			FPAlarmMessage alarmMessage = new FPAlarmMessage(getMessageIndex(),
					WSCommUtil.customerId, WSCommUtil.buildingId,
					system,signal,(type == StaticMessages.ALARM_TYPE_FIRE) ? "1"
									: "0");
			fpMessagesToServer.add(alarmMessage);
		}
		//logMessages.add(message);
		//logUI.refereshMessages(alarmMessages);
	}
	
	private void addMessageToList(Vector list,AlarmMessage message){
		//if(list.size() >=SYSTEM_LOG_MESSAGES_SZE){
		//	list.removeElementAt(0);			
		//}
		System.out.println("Alarm Message Added to Alarm List------------>"+list.size());
		list.add(message);
	}
	
	public void updateSystemMessages(String incomingmessage, int type) {
		
		if(type == StaticMessages.SEND){
		tray.showTrayMessage("Message Sent", StaticMessages.INFO);
		}else if(type == StaticMessages.RECEIVE){
		tray.showTrayMessage("New Message Received", StaticMessages.INFO);
		}
		//socketStatus = incomingmessage;
		AlarmMessage message = new AlarmMessage(incomingmessage, type, new Date());
		addMessageToList(systemLogMessages,message);
		//systemLogMessages.add(message);
		systemMessagesUI.refereshMessages(systemLogMessages);
		System.out.println("System Log Size--->"+systemLogMessages.size());
		System.out.println("Log Size--->"+alarmMessages.size());
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

	
	public void addLocalFPMessage(String message) {
		System.out.println("New message added =====>"+message);
		alarmMessagesFromSerial.add(message);
	}
	public Vector<String> getAlarmMessagesFromSerial() {
		return alarmMessagesFromSerial;
	}

	public void setAlarmMessagesFromSerial(Vector<String> alarmMessagesFromSerial) {
		this.alarmMessagesFromSerial = alarmMessagesFromSerial;
	}
	
	
	public Vector<AlarmMessage> getAlarmMessages() {
		return alarmMessages;
	}

	public void setAlarmMessages(Vector<AlarmMessage> alarmMessages) {
		this.alarmMessages = alarmMessages;
	}

	public void processServerMessage(String message){
		
		if(message!=null && message.toLowerCase().contains("high")){
		gpioCommunicator.updateHighInput();
		}else if(message!=null && message.toLowerCase().contains("low")){
		gpioCommunicator.updateLowInput();
		}
	}
	
	public void processAndSendSerialMessages(){
		System.out.println("Going to Process Message");
		try {
			//while (!stop)
			{
			Thread.sleep(100);
			if(lastRecievedMessageType>0 && (lastAddedMessageType != lastRecievedMessageType)){
				if(lastReceivedMessage!=null){
				AlarmMonitor.getInstance().getAlarmMessages().add(lastReceivedMessage);
				String system="NORMAL";
				String signal="BURGLAR_ALARM";
				String value="0";
				if(lastReceivedMessage.getMessage().toLowerCase().contains("fire")){
					value="1";
					system="ALARM";
				}
;				
						FPAlarmMessage alarmMessage = new FPAlarmMessage(
								getMessageIndex(), WSCommUtil.customerId,
								WSCommUtil.buildingId, system,
								signal,value);
						fpMessagesToServer.add(alarmMessage);

				lastReceivedMessage=null;
				lastRecievedMessageType=0;
				}
			}
			
			if(sendModeSocket){
				socketConnector.sendFPMessages();
			}else{
			
			List<AlarmMessage> messages=new ArrayList<AlarmMessage>();
			messages.addAll(AlarmMonitor.getInstance().getAlarmMessages());
			AlarmMonitor.getInstance().getAlarmMessages().clear();
			//System.out.println("AlarmMonitor.getInstance().getAlarmMessages()----"+AlarmMonitor.getInstance().getAlarmMessages().size());
			for(AlarmMessage message: messages){
				if(!processAlarmMessage(message)){
					AlarmMonitor.getInstance().getAlarmMessages().add(0, message);
				}
			}
			}
			
			
			
			Thread.sleep(200);
			}
		} catch (InterruptedException e) {
		}
		System.out.println("Message processing finished-------->");
	}
	public boolean processAlarmMessage(AlarmMessage message) {
		
		return WSCommUtil.saveAlarmEvent((message.getType()==StaticMessages.ALARM_TYPE_FIRE?"FIRE":"NORMAL"), message.getMessage(),"N", message.getMessageDate().getTime(),WSCommUtil.buildingFloorNo);
		
	}
	
	public int getMessageIndex(){
		if(messageIndex>99999){
			messageIndex=10000;
		}
		return messageIndex++;
	}
	
	public void processDeviceResetRequest(){
		System.out.println("Inside processDeviceResetRequest:"+gpioCommunicator);
		if(gpioCommunicator!=null){
		gpioCommunicator.updateDeviceResetRequest();
		}
	}
}
 