package com.vision.amclient.conn;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.service.unconfirmed.WhoIsRequest;
import com.serotonin.bacnet4j.transport.Transport;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.constructed.SequenceOf;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.type.primitive.OctetString;
import com.serotonin.bacnet4j.util.PropertyReferences;
//import com.serotonin.bacnet4j.util.PropertyUtil;

import com.vision.amclient.util.PropertyUtil;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.bacnet4j.util.RequestUtils;
import com.vision.amclient.model.SoftwareAlarmPoint;
import com.vision.amclient.monitor.AlarmMonitor;
import com.vision.amclient.monitor.messages.FPAlarmMessage;
import com.vision.amclient.monitor.messages.FPMessage;
import com.vision.amclient.monitor.messages.FPSoftwareMessage;
import com.vision.amclient.monitor.messages.FPSoftwareMessageUpdate;
import com.vision.amclient.util.StaticMessages;
import com.vision.amclient.util.WSCommUtil;

public class BacnetMonitor {
	LocalDevice localDevice=null;
	RemoteDevice remoteDevice;
	
	 String remoteDeviceIpAddress = PropertyUtil.getInstance().getConfigProperty("bacnet.remote_device_ip") ;	// example IP address
	 
     int remotePort;// = Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("bacnet.remote_device_port")) ;			// port 0xBAC0
     int remoteDeviceId;// = Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("bacnet.remote_device_id")) ;		// example device instance number
     
     int localPort;// = Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("bacnet.local_device_port")) ;			// port 0xBAC0
     int localDeviceId;//s = Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("bacnet.local_device_id")) ;			// port 0xBAC0
     
     String objIdsToCheck=PropertyUtil.getInstance().getConfigProperty("bacnet.object_ids");
		List<Integer> refObjIds=null;
     
     PropertyReferences references=null;
     Map<Integer,String> currentPointValues=new HashMap<Integer, String>(); 
     Map<Integer,SoftwareAlarmPoint> softwareAlarmPointMap=new HashMap<Integer, SoftwareAlarmPoint>(); 
     
     List<ObjectIdentifier> oids = null;
     List<ObjectIdentifier> oidsRequired = new ArrayList<ObjectIdentifier>();
     
     public boolean stop = false;
     public long lastBacnetDataCheckTime=0;
     public static void main(String[] args) throws Exception {
    	BacnetMonitor bacnetTest=new BacnetMonitor();
 		bacnetTest.initialize();
 		bacnetTest.findAllRemoteDevices();
 		bacnetTest.getAllRemoteObjectIds();
 	}
     
	
     public void initializeMonitor() throws Exception{
    	initialize();
    	findAllRemoteDevices();
  		getAllRemoteObjectIds();
     }
	 
	public void initialize() throws Exception{
		
		System.out.println("BacnetTest.initialize()   START");
		
		
		
		
		String remoteDeviceIpAddress = PropertyUtil.getInstance().getConfigProperty("bacnet.remote_device_ip") ;	// example IP address
		System.out.println("Socket Mode:"+PropertyUtil.getInstance().getConfigProperty("fpclient.send_mode"));
		System.out.println("Remote dev ip:"+PropertyUtil.getInstance().getConfigProperty("bacnet.remote_device_ip")) ;	// example IP address
		Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("bacnet.remote_device_port"));
		System.out.println("Remote dev port:"+PropertyUtil.getInstance().getConfigProperty("bacnet.remote_device_port")) ;			// port 0xBAC0
		System.out.println("Remote dev id:"+PropertyUtil.getInstance().getConfigProperty("bacnet.remote_device_id")) ;		// example device instance number
	     remotePort = Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("bacnet.remote_device_port")) ;			// port 0xBAC0
	     remoteDeviceId = Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("bacnet.remote_device_id")) ;		// example device instance number
	     
	      localPort = Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("bacnet.local_device_port")) ;			// port 0xBAC0
	     localDeviceId = Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("bacnet.local_device_id")) ;			// port 0xBAC0
	     
		
		 IpNetwork network = new IpNetwork(IpNetwork.DEFAULT_BROADCAST_IP,localPort);
	     Transport transport = new Transport(network);
	     localDevice = new LocalDevice(localDeviceId, transport);		
	     localDevice.initialize();
	     remoteDevice = localDevice.findRemoteDevice(new Address(remoteDeviceIpAddress, remotePort), null, remoteDeviceId);
	    // RequestUtils.getExtendedDeviceInformation(localDevice, remoteDevice);
	     System.out.println("Remote Device Name-----------"+remoteDevice.getName());
	     List<String> objIdVals=new ArrayList(Arrays.asList(objIdsToCheck.split(",")));	     
	     refObjIds= new ArrayList();
	     if(objIdVals!=null && objIdVals.size()>0){
	    	 for(String str :objIdVals){
	    		 String[] arr=str.split(":");
	    		 Integer objIdVal=null;
	    		 String objType=null;
	    		 String objDesc=null;
	    		 if(arr.length>0){
	    			 objIdVal=Integer.parseInt(arr[0]);
	    			 refObjIds.add(Integer.parseInt(arr[0]));
	    		 }
	    		 if(arr.length>1){
	    			 objType=arr[1];
	    		 }
	    		 if(arr.length>2){
	    			 objDesc=arr[2];
	    		 }
	    		 
	    		 softwareAlarmPointMap.put(objIdVal, new SoftwareAlarmPoint(objIdVal, objType, objDesc));
	    		 
	    	 }
	     }
	     
	     System.out.println("BacnetTest.initialize()   END");
	}
	
	public void findAllRemoteDevices() throws BACnetException, InterruptedException{
		System.out.println("BacnetTest.findAllRemoteDevices()   START");
		localDevice.sendGlobalBroadcast(new WhoIsRequest());
		System.out.println("Searching for remote devices ......");
		Thread.sleep(1*1000);	// wait a bit to collect the response messages
		List<RemoteDevice> devices = localDevice.getRemoteDevices();
		
		System.out.println("Remote Devices identified count...:"+devices.size());
		
		for(RemoteDevice dev :   devices){
		//RequestUtils.getExtendedDeviceInformation(localDevice, dev);
		System.out.println("Remote Device Name-----------"+dev.getName());
		
		/*localDevice.getEventHandler().addListener(new DeviceEventListener() {		
			@Override
			public void iAmReceived(RemoteDevice device) {
					
			}
		});*/
		}
		System.out.println("BacnetTest.findAllRemoteDevices()   END");
	}
	
	public void getAllRemoteObjectIds() throws BACnetException{
		System.out.println("BacnetTest.getAllRemoteObjectIds()   START");
		oids = ((SequenceOf<ObjectIdentifier>) RequestUtils.sendReadPropertyAllowNull(localDevice, remoteDevice, remoteDevice.getObjectIdentifier(), PropertyIdentifier.objectList)).getValues();
		System.out.println("No of object Ids identified......:"+oids.size());
		for(ObjectIdentifier oid : oids ){
		//	System.out.println("Object ID details---------->"+oid.getInstanceNumber()+":"+oid.getObjectType().toString());
		}
		
		
		
		references = new PropertyReferences();
		
		for (ObjectIdentifier objectIdentifier : oids) {
			if(refObjIds.contains(objectIdentifier.getInstanceNumber())){
			System.out.println("Object ID details---------->"+objectIdentifier.getInstanceNumber()+":"+objectIdentifier.getObjectType().toString());
			oidsRequired.add(objectIdentifier);
			references.add(objectIdentifier, PropertyIdentifier.presentValue);
			}
		}
		    	
		PropertyValues values = RequestUtils.readProperties(localDevice, remoteDevice, references, null);

		for (ObjectIdentifier objectIdentifier : oidsRequired) {
			System.out.println("Property Value--------->"+objectIdentifier.getInstanceNumber()+":"+values.getString(objectIdentifier, PropertyIdentifier.presentValue));
			currentPointValues.put(objectIdentifier.getInstanceNumber(),values.getString(objectIdentifier, PropertyIdentifier.presentValue));
		}
		saveSoftwareAlarmPoints(false);	
		System.out.println("BacnetTest.getAllRemoteObjectIds()   END");
	
	}
	
	public void doAlarmPointsPeriodicCheck() throws BACnetException{
		System.out.println("Periodic check started------");
		PropertyValues values = RequestUtils.readProperties(localDevice, remoteDevice, references, null);
		//Map<Integer,String> tempValues=new HashMap<Integer, String>();
		
		boolean valueChanged=false;
		for (ObjectIdentifier objectIdentifier : oidsRequired) {
			System.out.println("Property Value--------->"+objectIdentifier.getInstanceNumber()+":"+values.getString(objectIdentifier, PropertyIdentifier.presentValue));
			//currentPointValues.put(objectIdentifier.getInstanceNumber(),values.getString(objectIdentifier, PropertyIdentifier.presentValue));
			
			String presentValue=values.getString(objectIdentifier, PropertyIdentifier.presentValue);
			if(presentValue!=null && !(presentValue.equalsIgnoreCase(currentPointValues.get(objectIdentifier.getInstanceNumber())))){
				
				System.out.println("Value Changed--------->"+currentPointValues.get(objectIdentifier.getInstanceNumber())+":"+presentValue);
				currentPointValues.put(objectIdentifier.getInstanceNumber(),values.getString(objectIdentifier, PropertyIdentifier.presentValue));
				valueChanged=true;
			}
			
		}
		if(valueChanged){
			saveSoftwareAlarmPoints(true);
		}
		System.out.println("Periodic check finished. Data update status--"+valueChanged);

	}

	public boolean saveSoftwareAlarmPoints(boolean isUpdate){
		if(currentPointValues!=null && currentPointValues.size()>0){
		String alarmMessageStr="";
		for (Map.Entry<Integer, String> entry : currentPointValues.entrySet())
		{
			if(!alarmMessageStr.isEmpty()){
				alarmMessageStr+=",";	
			}
			SoftwareAlarmPoint alarmPoint=softwareAlarmPointMap.get(entry.getKey());
			if(alarmPoint!=null){
			alarmMessageStr+=alarmPoint.toString()+":"+entry.getValue();
			}
		}
		
		if(!alarmMessageStr.isEmpty()){
			FPMessage alarmMessage = null;
			if(isUpdate){
				alarmMessage=new FPSoftwareMessageUpdate(AlarmMonitor.getInstance().getMessageIndex(),
					WSCommUtil.customerId, WSCommUtil.buildingId,alarmMessageStr);
			}else{
				alarmMessage=new FPSoftwareMessage(AlarmMonitor.getInstance().getMessageIndex(),
						WSCommUtil.customerId, WSCommUtil.buildingId,alarmMessageStr);
				
			}
			
			AlarmMonitor.getInstance().fpMessagesToServer.add(alarmMessage);
			
		}
			
		}
		
		return true;
		
		
		
	}
	
	
}
