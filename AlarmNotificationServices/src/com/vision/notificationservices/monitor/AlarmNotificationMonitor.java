package com.vision.notificationservices.monitor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.vision.notificationservices.communicator.EmailCommunicator;
import com.vision.notificationservices.communicator.SMSCommunicator;
import com.vision.notificationservices.dto.AlarmNotfnEventDetailsDTO;
import com.vision.notificationservices.dto.SoftwareMessagePendingDTO;
import com.vision.notificationservices.util.PropertyUtil;
import com.vision.notificationservices.util.WSCommUtil;



public class AlarmNotificationMonitor extends Thread{

	private static AlarmNotificationMonitor alarmMonitor=null;
	public static final long ALARMEVENT_NOTFN_CHECK_INTERVAL=Long.parseLong(PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.pending_notification_check_interval"));
	public static final long ALARMEVENT_GSM_RECONNECT_INTERVAL=Long.parseLong(PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.time_interval_to_check_gsm_reconnect"));
	
	public static final boolean SMS_NOTIFY=Boolean.parseBoolean(PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.smsnotify"));
	public static final boolean EMAIL_NOTIFY=Boolean.parseBoolean(PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.emailnotify"));
	static Logger log = Logger.getLogger("AlarmNotificationMonitor");
	
	
	
	public static boolean stop=false;
	public SMSCommunicator smsCommunicator=null;
	public static long  gsmLastConnectionIntiatedTime=0;
	
	public static AlarmNotificationMonitor getInstance() {
		if (alarmMonitor == null) {
			alarmMonitor = new AlarmNotificationMonitor();
		}
		return alarmMonitor;
	}
	private AlarmNotificationMonitor(){
		
	}
	public void startMonitor() {
		this.start();
	}
	public void run(){
		
		while(!stop){
			if(smsCommunicator==null && SMS_NOTIFY){
				smsCommunicator=new SMSCommunicator();
				smsCommunicator.initializeSerialCommunication();
			}
			if(smsCommunicator!=null){
			System.out.println(smsCommunicator.serialConnected+":"+((System.currentTimeMillis()-gsmLastConnectionIntiatedTime)> ALARMEVENT_GSM_RECONNECT_INTERVAL));
			}
			if( SMS_NOTIFY && !smsCommunicator.serialConnected && ((System.currentTimeMillis()-gsmLastConnectionIntiatedTime)> ALARMEVENT_GSM_RECONNECT_INTERVAL)){
				smsCommunicator.initializeSerialCommunication();
			}
			
			try {
				processPendingSoftwareNotifications();				
				
			} catch(Exception ex){
				ex.printStackTrace();
			}
			try {
				processPendingNotifications();
				Thread.sleep(ALARMEVENT_NOTFN_CHECK_INTERVAL);
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
		
	}public void processPendingNotifications(){
		try {
			List<AlarmNotfnEventDetailsDTO> alrmNotfnList=WSCommUtil.getNotificationUnsentEventList();
			
			log.info("Notification Pending List -->"+(alrmNotfnList!=null?alrmNotfnList.size():0));
			for(AlarmNotfnEventDetailsDTO notfnDTO : alrmNotfnList){
				Integer eventId=notfnDTO.getEventId();
				String eventType=notfnDTO.getEventType();
				String eventGenTime=notfnDTO.getEventGeneratedTime();
				String eventDetails=notfnDTO.getEventDetails();
				String buildingName=notfnDTO.getBuildingName();
				
				if(notfnDTO.getNotfnType()!=null && "SMS".equalsIgnoreCase(notfnDTO.getNotfnType()) && (SMS_NOTIFY && smsCommunicator.serialConnected)){
					log.info("Going to Process SMS");
					String buildingContactPhone=notfnDTO.getBuildingContactPhone();
					String customerContactPhone=notfnDTO.getCustomerContactPhone();
					String enggContactPhone=notfnDTO.getEnggContactPhone();
					String securityContactPhone=notfnDTO.getSecurityContactPhone();
					String maintenanceCotactPhone=notfnDTO.getMaintenanceContactPhone();
					boolean smsNotificationSuccess=true;
					
					if ( "Y".equalsIgnoreCase(notfnDTO.getNotifyBuildingContact()) &&  buildingContactPhone != null
							&& !buildingContactPhone.isEmpty()
							&& notfnDTO.getBuildingContactId() != null
							&& notfnDTO.getBuildingContactId() > 0 ) {
						if (!sendNotificationSMS(eventId, notfnDTO
								.getBuildingContactId(), eventType,
								buildingContactPhone, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							smsNotificationSuccess = false;
						}
					}

					if ("Y".equalsIgnoreCase(notfnDTO.getNotifyCustomer()) && customerContactPhone != null
							&& !customerContactPhone.isEmpty()
							&& notfnDTO.getCustomerContactId() != null
							&& notfnDTO.getCustomerContactId() > 0) {
						if (!sendNotificationSMS(eventId, notfnDTO
								.getCustomerContactId(), eventType,
								customerContactPhone, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							smsNotificationSuccess = false;
						}
					}
					if ("Y".equalsIgnoreCase(notfnDTO.getNotifyEnggContact()) && enggContactPhone != null && !enggContactPhone.isEmpty()
							&& notfnDTO.getEnggContactId() != null
							&& notfnDTO.getEnggContactId() > 0) {
						if (!sendNotificationSMS(eventId, notfnDTO
								.getEnggContactId(), eventType,
								enggContactPhone, eventGenTime, eventDetails,
								buildingName,notfnDTO.getNotfnType())) {
							smsNotificationSuccess = false;
						}
					}
					if ("Y".equalsIgnoreCase(notfnDTO.getNotifysecurityContact()) && securityContactPhone != null
							&& !securityContactPhone.isEmpty()
							&& notfnDTO.getSecurityContactId() != null
							&& notfnDTO.getSecurityContactId() > 0) {
						if (!sendNotificationSMS(eventId, notfnDTO
								.getSecurityContactId(), eventType,
								securityContactPhone, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							smsNotificationSuccess = false;
						}
					}
					if ("Y".equalsIgnoreCase(notfnDTO.getNotifyMaintenanceContact()) && maintenanceCotactPhone != null
							&& !maintenanceCotactPhone.isEmpty()
							&& notfnDTO.getMaintenanceContactId() != null
							&& notfnDTO.getMaintenanceContactId() > 0) {
						if (!sendNotificationSMS(eventId, notfnDTO
								.getMaintenanceContactId(), eventType,
								maintenanceCotactPhone, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							smsNotificationSuccess = false;
						}
					}
					if (smsNotificationSuccess) {
						WSCommUtil.updateNotificationSendStatus(eventId, "SMS",
								"Y");
					}
					
					
				}else if(notfnDTO.getNotfnType()!=null && "email".equalsIgnoreCase(notfnDTO.getNotfnType()) && EMAIL_NOTIFY){
					log.info("Going to Process Email");
					
					String buildingContactEmail=notfnDTO.getBuildingContactEmail();
					String customerContactEmail=notfnDTO.getCustomerContactEmail();
					String enggContactEmail=notfnDTO.getEnggContactEmail();
					String securityContactEmail=notfnDTO.getSecurityContactEmail();
					String maintenanceCotactEmail=notfnDTO.getMaintenanceContactEmail();
					
					
					boolean emailNotificationSuccess=true;
					
					if ( "Y".equalsIgnoreCase(notfnDTO.getNotifyBuildingContact()) &&  buildingContactEmail != null
							&& !buildingContactEmail.isEmpty()
							&& notfnDTO.getBuildingContactId() != null
							&& notfnDTO.getBuildingContactId() > 0 ) {
						if (!sendNotificationEmail(eventId, notfnDTO
								.getBuildingContactId(), eventType,
								buildingContactEmail, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							emailNotificationSuccess = false;
						}
					}
					
					System.out.println("Email send finished ---->Building Contact:"+emailNotificationSuccess);
					if ( "Y".equalsIgnoreCase(notfnDTO.getNotifyCustomer()) &&  customerContactEmail != null
							&& !customerContactEmail.isEmpty()
							&& notfnDTO.getCustomerContactId() != null
							&& notfnDTO.getCustomerContactId() > 0 ) {
						if (!sendNotificationEmail(eventId, notfnDTO
								.getCustomerContactId(), eventType,
								customerContactEmail, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							emailNotificationSuccess = false;
						}
					}
					System.out.println("Email send finished ---->Customer Contact:"+emailNotificationSuccess);
					
					if ("Y".equalsIgnoreCase(notfnDTO.getNotifyEnggContact()) && enggContactEmail != null && !enggContactEmail.isEmpty()
							&& notfnDTO.getEnggContactId() != null
							&& notfnDTO.getEnggContactId() > 0) {
						if (!sendNotificationEmail(eventId, notfnDTO
								.getEnggContactId(), eventType,
								enggContactEmail, eventGenTime, eventDetails,
								buildingName,notfnDTO.getNotfnType())) {
							emailNotificationSuccess = false;
						}
					}
					System.out.println("Email send finished ---->Engg Contact:"+emailNotificationSuccess);
					
					if ("Y".equalsIgnoreCase(notfnDTO.getNotifysecurityContact()) && securityContactEmail != null
							&& !securityContactEmail.isEmpty()
							&& notfnDTO.getSecurityContactId() != null
							&& notfnDTO.getSecurityContactId() > 0) {
						if (!sendNotificationEmail(eventId, notfnDTO
								.getSecurityContactId(), eventType,
								securityContactEmail, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							emailNotificationSuccess = false;
						}
					}
					System.out.println("Email send finished ---->Security Contact:"+emailNotificationSuccess);
					
					if ("Y".equalsIgnoreCase(notfnDTO.getNotifyMaintenanceContact()) && maintenanceCotactEmail != null
							&& !maintenanceCotactEmail.isEmpty()
							&& notfnDTO.getMaintenanceContactId() != null
							&& notfnDTO.getMaintenanceContactId() > 0) {
						if (!sendNotificationEmail(eventId, notfnDTO
								.getMaintenanceContactId(), eventType,
								maintenanceCotactEmail, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							emailNotificationSuccess = false;
						}
					}
					System.out.println("Email send finished ---->Maint Contact:"+emailNotificationSuccess);
					
					System.out.println("Email send status------------------------------>"+emailNotificationSuccess);
					
					if (emailNotificationSuccess) {
						WSCommUtil.updateNotificationSendStatus(eventId, "EMAIL",
								"Y");
					}
					
				}
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
	}

	
	public void processPendingSoftwareNotifications(){
		try {
			List<SoftwareMessagePendingDTO> alrmNotfnList=WSCommUtil.getSwNotificationUnsentEventList();
			
			log.info("Notification Pending List -->"+(alrmNotfnList!=null?alrmNotfnList.size():0));
			for(SoftwareMessagePendingDTO notfnDTO : alrmNotfnList){
				Integer eventId=notfnDTO.getSoftwareMessageId();
				String eventType=notfnDTO.getEventValue();
				String eventGenTime=notfnDTO.getEventUpdatedTime().toString();
				String eventDetails=notfnDTO.getEventDetails();
				String buildingName=notfnDTO.getBuildingName();
				
				if(notfnDTO.getNotfnType()!=null && "SMS".equalsIgnoreCase(notfnDTO.getNotfnType()) && (SMS_NOTIFY && smsCommunicator.serialConnected)
						&& !"Y".equalsIgnoreCase(notfnDTO.getSmsNotified())){
					log.info("Going to Process SMS");
					String buildingContactPhone=notfnDTO.getBuildingContactPhone();
					String customerContactPhone=notfnDTO.getCustomerContactPhone();
					String enggContactPhone=notfnDTO.getEnggContactPhone();
					String securityContactPhone=notfnDTO.getSecurityContactPhone();
					String maintenanceCotactPhone=notfnDTO.getMaintenanceContactPhone();
					boolean smsNotificationSuccess=true;
					
					if ( "Y".equalsIgnoreCase(notfnDTO.getNotifyBuildingContact()) &&  buildingContactPhone != null
							&& !buildingContactPhone.isEmpty() ) {
						if (!sendNotificationSMS(eventId, 0, eventType,
								buildingContactPhone, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							smsNotificationSuccess = false;
						}
					}

					if ("Y".equalsIgnoreCase(notfnDTO.getNotifyCustomer()) && customerContactPhone != null
							&& !customerContactPhone.isEmpty()) {
						if (!sendNotificationSMS(eventId, 0, eventType,
								customerContactPhone, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							smsNotificationSuccess = false;
						}
					}
					if ("Y".equalsIgnoreCase(notfnDTO.getNotifyEnggContact()) && enggContactPhone != null && !enggContactPhone.isEmpty()
							) {
						if (!sendNotificationSMS(eventId, 0, eventType,
								enggContactPhone, eventGenTime, eventDetails,
								buildingName,notfnDTO.getNotfnType())) {
							smsNotificationSuccess = false;
						}
					}
					if ("Y".equalsIgnoreCase(notfnDTO.getNotifysecurityContact()) && securityContactPhone != null
							&& !securityContactPhone.isEmpty()
							) {
						if (!sendNotificationSMS(eventId, 0, eventType,
								securityContactPhone, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							smsNotificationSuccess = false;
						}
					}
					if ("Y".equalsIgnoreCase(notfnDTO.getNotifyMaintenanceContact()) && maintenanceCotactPhone != null
							&& !maintenanceCotactPhone.isEmpty()
							) {
						if (!sendNotificationSMS(eventId, 0, eventType,
								maintenanceCotactPhone, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							smsNotificationSuccess = false;
						}
					}
					
					
					if(notfnDTO.getOtherSMSContacts()!=null && !notfnDTO.getOtherSMSContacts().isEmpty()){
						
						String[] strArr=notfnDTO.getOtherSMSContacts().split(",");
						
						if(strArr!=null && strArr.length>0){
							for( String phNo :  strArr){
								if(phNo!=null && !phNo.isEmpty() ){
								if (!sendNotificationSMS(eventId, 0, eventType,
										phNo.trim(), eventGenTime,
										eventDetails, buildingName,notfnDTO.getNotfnType())) {
									smsNotificationSuccess = false;
								}
								}
							}
						}
						
					}
					
					
					
					if (smsNotificationSuccess) {
						WSCommUtil.updateSwNotificationSendStatus(eventId, "SMS",
								"Y");
					}
					
					
				}else if(notfnDTO.getNotfnType()!=null && "email".equalsIgnoreCase(notfnDTO.getNotfnType()) && EMAIL_NOTIFY
						&& !"Y".equalsIgnoreCase(notfnDTO.getEmailNotified())){
					log.info("Going to Process Email");
					
					String buildingContactEmail=notfnDTO.getBuildingContactEmail();
					String customerContactEmail=notfnDTO.getCustomerContactEmail();
					String enggContactEmail=notfnDTO.getEnggContactEmail();
					String securityContactEmail=notfnDTO.getSecurityContactEmail();
					String maintenanceCotactEmail=notfnDTO.getMaintenanceContactEmail();
					
					
					boolean emailNotificationSuccess=true;
					
					if ( "Y".equalsIgnoreCase(notfnDTO.getNotifyBuildingContact()) &&  buildingContactEmail != null
							&& !buildingContactEmail.isEmpty() ) {
						if (!sendNotificationEmail(eventId, 0, eventType,
								buildingContactEmail, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							emailNotificationSuccess = false;
						}
					}
					System.out.println("Email send finished ---->Building Contact:"+emailNotificationSuccess);

					if ( "Y".equalsIgnoreCase(notfnDTO.getNotifyCustomer()) &&  customerContactEmail != null
							&& !customerContactEmail.isEmpty()) {
						if (!sendNotificationEmail(eventId, 0, eventType,
								customerContactEmail, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							emailNotificationSuccess = false;
						}
					}
					System.out.println("Email send finished ---->Customer Contact:"+emailNotificationSuccess);

					if ("Y".equalsIgnoreCase(notfnDTO.getNotifyEnggContact()) && enggContactEmail != null && !enggContactEmail.isEmpty()
							) {
						if (!sendNotificationEmail(eventId, 0, eventType,
								enggContactEmail, eventGenTime, eventDetails,
								buildingName,notfnDTO.getNotfnType())) {
							emailNotificationSuccess = false;
						}
					}
					System.out.println("Email send finished ---->Engg Contact:"+emailNotificationSuccess);

					if ("Y".equalsIgnoreCase(notfnDTO.getNotifysecurityContact()) && securityContactEmail != null
							&& !securityContactEmail.isEmpty()
							) {
						if (!sendNotificationEmail(eventId, 0, eventType,
								securityContactEmail, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							emailNotificationSuccess = false;
						}
					}
					System.out.println("Email send finished ---->Security Contact:"+emailNotificationSuccess);

					if ("Y".equalsIgnoreCase(notfnDTO.getNotifyMaintenanceContact()) && maintenanceCotactEmail != null
							&& !maintenanceCotactEmail.isEmpty()
							) {
						if (!sendNotificationEmail(eventId, 0, eventType,
								maintenanceCotactEmail, eventGenTime,
								eventDetails, buildingName,notfnDTO.getNotfnType())) {
							emailNotificationSuccess = false;
						}
					}
					
					if(notfnDTO.getOtherEmailContacts()!=null && !notfnDTO.getOtherEmailContacts().isEmpty()){
						
						String[] strArr=notfnDTO.getOtherEmailContacts().split(",");
						
						if(strArr!=null && strArr.length>0){
							for( String emailId :  strArr){
								if(emailId!=null && !emailId.isEmpty() && !"null".equalsIgnoreCase(emailId)){
								if (!sendNotificationEmail(eventId, 0, eventType,
										emailId.trim(), eventGenTime,
										eventDetails, buildingName,notfnDTO.getNotfnType())) {
									emailNotificationSuccess = false;
								}
								}
							}
						}
						
					}
					
					System.out.println("Email send finished ---->Mant Contact:"+emailNotificationSuccess);

					if (emailNotificationSuccess) {
						WSCommUtil.updateSwNotificationSendStatus(eventId, "EMAIL",
								"Y");
					}
					
				}
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
	}
	
	public boolean sendNotificationSMS(Integer eventId,Integer contactId,String eventType,String phoneNo,String eventGenTime,String eventDetails,String buildingName,String notfnType){
		
		if(!SMS_NOTIFY){
			return false;
		}
		
		String smsMessageDetails=" ";
		
		if("FIRE".equalsIgnoreCase(eventType)){
			smsMessageDetails+=" Fire Alarm happened on "+buildingName ;
		}else{
			smsMessageDetails+="Alarm happened on "+buildingName;
		}
		smsMessageDetails+=" at "+eventGenTime+". Please do necessary actions.";
		
		boolean smsSendStatus= smsCommunicator.sendGSMMessage(smsMessageDetails, phoneNo);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("GSM Message Send--------------------------->"+smsSendStatus);
		boolean notfnUpdateStatus=true;
		if(contactId!=0){
		notfnUpdateStatus=WSCommUtil.saveAlarmEventHistory(eventId,contactId,notfnType);
		}
		if(smsSendStatus && notfnUpdateStatus){
			return true;
		}
		return false;
		
	}
	
	
	public boolean sendNotificationEmail(Integer eventId,Integer contactId,String eventType,String emailId,String eventGenTime,String eventDetails,String buildingName,String notfnType){
		
		if(!EMAIL_NOTIFY){
			return false;
		}
		
		
		if(emailId==null || emailId.isEmpty() || "null".equalsIgnoreCase(emailId)){
			return true;
		}
		String emailMessageDetails=" ";
		
		if("FIRE".equalsIgnoreCase(eventType)){
			emailMessageDetails+=" Fire Alarm happened on "+buildingName ;
		}else{			emailMessageDetails+="Alarm happened on "+buildingName;
		}
		emailMessageDetails+=" at "+eventGenTime+". Please do necessary actions.";
		
		String subject="Alarm Notification";
		boolean smsSendStatus=false;
		try {
			//emailMessageDetails=new String(emailMessageDetails.getBytes(),"UTF-8");
			smsSendStatus = EmailCommunicator.sendEmail(emailMessageDetails,subject, emailId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean notfnUpdateStatus=true;
		if(contactId!=0){
		notfnUpdateStatus=WSCommUtil.saveAlarmEventHistory(eventId,contactId,notfnType);
		}
		if(smsSendStatus && notfnUpdateStatus){
			return true;
		}
		return false;
		
	}
	public void updateNotfnStatusMessage(String message, boolean bol){
		
	}
	
}
