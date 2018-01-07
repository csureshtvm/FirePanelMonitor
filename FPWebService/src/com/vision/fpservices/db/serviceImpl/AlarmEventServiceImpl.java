package com.vision.fpservices.db.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.QueryParam;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.test.utils.ServerContextHelper;
import com.vision.fpservices.db.dao.AlarmDevicesDAO;
import com.vision.fpservices.db.dao.AlarmEventDao;
import com.vision.fpservices.db.dao.AlarmEventResetDAO;
import com.vision.fpservices.db.dao.AlarmMessagesDAO;
import com.vision.fpservices.db.dao.AlarmNotfnHistoryDAO;
import com.vision.fpservices.db.dao.BuildingDAO;
import com.vision.fpservices.db.dao.ContactDAO;
import com.vision.fpservices.db.dao.SoftwareMessageDao;
import com.vision.fpservices.db.dao.SoftwareMessageHistoryDao;
import com.vision.fpservices.db.dao.UserDAO;
import com.vision.fpservices.db.daoImpl.AlarmEventDaoImpl;
import com.vision.fpservices.db.model.AlarmDeviceReset;
import com.vision.fpservices.db.model.AlarmDevices;
import com.vision.fpservices.db.model.AlarmEvents;
import com.vision.fpservices.db.model.AlarmMessages;
import com.vision.fpservices.db.model.AlarmNotfnHistory;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.ContactDetails;
import com.vision.fpservices.db.model.SoftwareMessage;
import com.vision.fpservices.db.model.SoftwareMessageHistory;
import com.vision.fpservices.db.service.AlarmEventService;
import com.vision.fpservices.dto.AlarmEventDTO;
import com.vision.fpservices.dto.AlarmEventRequestDTO;
import com.vision.fpservices.dto.AlarmEventStatisticsDTO;
import com.vision.fpservices.dto.AlarmEventWithContactsDTO;
import com.vision.fpservices.dto.AlarmMessagesDTO;
import com.vision.fpservices.dto.AlarmNotfnEventDetailsDTO;
import com.vision.fpservices.dto.AlarmStatisticsDTO;
import com.vision.fpservices.dto.BuildingDTO;
import com.vision.fpservices.dto.OtherContactsDTO;
import com.vision.fpservices.dto.SoftwareMessageDTO;
import com.vision.fpservices.dto.SoftwareMessagePendingDTO;
import com.vision.fpservices.dto.UserDTO;
import com.vision.fpservices.model.SoftwareAlarmPoint;
import com.wordnik.swagger.core.ApiParam;

//@Repository("AlarmEventService") 
//@Transactional
@Repository("AlarmEventService")
@Transactional(readOnly=true)
public class AlarmEventServiceImpl implements AlarmEventService{
	static String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	@Autowired
	AlarmEventDao alarmEventDao;
	
	@Autowired
	AlarmEventResetDAO alarmEventResetDao;
	
	@Autowired
	AlarmDevicesDAO alarmDeviceDao;
	
	@Autowired
	ContactDAO contactDAO;
	
	@Autowired
	BuildingDAO buildingDAO;
	
	@Autowired
	AlarmNotfnHistoryDAO alarmNotfnHistoryDao;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	SoftwareMessageDao softwareMessageDao;

	@Autowired
	SoftwareMessageHistoryDao softwareMessageHistoryDao;
	
	@Autowired
	AlarmMessagesDAO alarmMessagesDao;
	
	public List<AlarmEventDTO> getAllAlarmEvents(String customerId,String userId){
		List<AlarmEventDTO> alarmEventList= alarmEventDao.getAllalarmEvents(customerId);
		
		List<AlarmEventDTO> resultList=filterAlarmEventsOnUserRole(alarmEventList,userId);
		if(resultList!=null){
			return resultList;
		}
		return alarmEventList;
	
	}
	
	
	private List filterAlarmEventsOnUserRole(List alarmEventList,String userId){
		
		if(userId !=null){
			UserDTO userDto=userDAO.getLoginUserDetails(userId);
			if(userDto!=null && userDto.getUserCustomerMapping()!=null){
				ObjectMapper mapper=new ObjectMapper();
				try {
					List<String> buildingIds = mapper.readValue(userDto.getUserCustomerMapping(), new TypeReference<List<String>>(){});
					if(buildingIds!=null && buildingIds.size()>0){
					List resultList=new ArrayList();
					for(Object o : alarmEventList){
						
						AlarmEventDTO dto=(AlarmEventDTO)o;
						if(resultList.contains(dto.getBuildingId())){
							resultList.add(dto);
						}
					}
					return resultList;
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
			
		}
		return null;
		
	}
	@Transactional(readOnly=false)
	public boolean resetAlarm(Integer alarmEventId,String resetComments,String requestedBy){
		
		AlarmEvents alarmEvent=alarmEventDao.loadAlarmEvent(alarmEventId);
		Integer eventId=null;
		if(alarmEvent!=null){
			alarmEvent.setIsActive("N");
			alarmEvent.setResetComments(resetComments);
			alarmEvent.setResetRequestedBy(requestedBy);
			alarmEvent.setResetRequestedDtm(new Date());
			
			eventId=alarmEventDao.makePersistent(alarmEvent).getAlarmEventId();
			
		AlarmDevices alarmDevice=alarmDeviceDao.getAlarmDeviceIdFromBuildingDetails(alarmEvent.getBuilding().getBuildingId(),String.valueOf(alarmEvent.getBuildingFloorNo()));
		if(alarmDevice!=null){
			AlarmDeviceReset alarmDevReset=new AlarmDeviceReset();
			alarmDevReset.setAlarmDevice(alarmDevice);
			alarmDevReset.setResetRequestedBy(requestedBy);
			alarmDevReset.setAlarmEvent(alarmEvent);
			
			alarmEventResetDao.makePersistent(alarmDevReset);
		}
			
		}
		return (alarmEventId==eventId)?true:false;
		
	}

	@Transactional(readOnly=false)
	public boolean createNewAlarm(AlarmEventRequestDTO reqDto){
		
		AlarmEvents alarmEvent=new AlarmEvents();
		Building  building=buildingDAO.loadBuilding(reqDto.getBuildingId());
		alarmEvent.setBuilding(building);
		alarmEvent.setEventDetails(reqDto.getEventDetails());
		alarmEvent.setEventType(reqDto.getEventType());
		alarmEvent.setEventGeneratedTime(new Date(reqDto.getEventGeneratedTimeInMillis()));
		alarmEvent.setIsTestAlarm(reqDto.getTestAlarm());
		alarmEvent.setIsActive("Y");
		alarmEvent.setBuildingFloorNo(reqDto.getBuildingFloorNo());
		alarmEvent.setCreatedDate(new Date(System.currentTimeMillis()));
		
		alarmEvent.setEventDeviceType(reqDto.getEventDeviceType());
		alarmEvent.setEventSystem(reqDto.getEventSystem());
		alarmEvent.setEventSignal(reqDto.getEventSignal());
		alarmEvent.setEventValue(reqDto.getEventValue());
		
		AlarmDevices dev=alarmDeviceDao.getAlarmDeviceIdFromBuildingDetails(reqDto.getBuildingId(), reqDto.getBuildingFloorNo()!=null?String.valueOf(reqDto.getBuildingFloorNo()):null);
		alarmEvent.setAlarmDevice(dev);
		
		//((AlarmEventDao) ServerContextHelper.getBean( "alarmEventDao" )).saveOrUpdate(alarmEvent);
		
		alarmEvent=alarmEventDao.makePersistent(alarmEvent);
		Integer eventId=alarmEvent.getAlarmEventId();
		return (eventId>0)?true:false;
		
	}
	@Transactional(readOnly=false)
	public boolean updateNotfnStatus(Integer alarmEventId,String notfnType,String notfnStatus){
		
		AlarmEvents alarmEvent=alarmEventDao.loadAlarmEvent(alarmEventId);
		Integer eventId=null;
		if(alarmEvent!=null){
			if("sms".equalsIgnoreCase(notfnType)){
				alarmEvent.setSmsNotified(notfnStatus);
			}else if("email".equalsIgnoreCase(notfnType)){
				alarmEvent.setEmailNotified(notfnStatus);
			}
			alarmEvent=alarmEventDao.makePersistent(alarmEvent);
			eventId=alarmEvent.getAlarmEventId();
			
		}
		return (alarmEventId==eventId)?true:false;
		
	}
	
	@Transactional(readOnly=false)
	public boolean saveAlarmNotfnHistory(Integer alarmEventId,Integer contactId,String notfnType){
		
		AlarmNotfnHistory alarmNotfnHistory=new AlarmNotfnHistory();
		AlarmEvents alarmEvent=alarmEventDao.loadAlarmEvent(alarmEventId);
		ContactDetails contactDetails=contactDAO.loadContactDetails(contactId);
		
		alarmNotfnHistory.setAlarmEvent(alarmEvent);
		alarmNotfnHistory.setContactDetails(contactDetails);
		alarmNotfnHistory.setNotfnType(notfnType);
		alarmNotfnHistory.setCreatedTime(new Date(System.currentTimeMillis()));
		alarmNotfnHistory= alarmNotfnHistoryDao.makePersistent(alarmNotfnHistory);
		
		return (alarmNotfnHistory.getAlarmNotfnEventId()>0)?true:false;
		
	}
	
	public List<AlarmNotfnEventDetailsDTO> getNotfnpendingEventList(){
	return	alarmEventDao.getNotfnpendingEventList();
		
	}
	
	
	public List<AlarmEventStatisticsDTO> getAlarmEventStatistics(){
		return	alarmEventDao.getAlarmEventStatistics();
			
	}
	
	
	public ArrayList<AlarmStatisticsDTO> getEventStatistics(){
		List<AlarmEventStatisticsDTO> eventStatisticsList=alarmEventDao.getAlarmEventStatistics1();
		final long DAY=24*60*60*1000;	
		HashMap<Integer, Integer> dateMap=new HashMap<Integer, Integer>();
		Map<Integer,List<AlarmEventStatisticsDTO>> alarmEventMap=new HashMap<Integer, List<AlarmEventStatisticsDTO>>();
		List<String> monthNameArr=new ArrayList<String>();
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		int prev=0;
		dateMap.put(cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));
		
		int currMonth=cal.get(Calendar.MONTH);
		int currYear=cal.get(Calendar.YEAR);
		alarmEventMap.put(currMonth, null);
		monthNameArr.add(0,monthNames[currMonth]);
		
		
		
		if(currMonth==0){
			currMonth=11;
			currYear=cal.get(Calendar.YEAR)-1;
		}else{
			currMonth-=1;
		}
		dateMap.put(currMonth,currYear);
		alarmEventMap.put(currMonth, null);
		monthNameArr.add(0,monthNames[currMonth]);
		
		if(currMonth==0){
			currMonth=11;
			currYear=cal.get(Calendar.YEAR)-1;
		}else{
			currMonth-=1;
		}
		dateMap.put(currMonth,currYear);
		alarmEventMap.put(currMonth, null);
		monthNameArr.add(0,monthNames[currMonth]);
		
		System.out.println("dateMap===="+dateMap);
		
		
		for( AlarmEventStatisticsDTO alarmEvent : eventStatisticsList ){
			if(alarmEvent.getEventGeneratedTime() !=null){
			cal.setTime(alarmEvent.getEventGeneratedTime());
			if(dateMap.get(cal.get(Calendar.MONTH))!=null && (dateMap.get(cal.get(Calendar.MONTH))==cal.get(Calendar.YEAR))){
				
				List<AlarmEventStatisticsDTO> eventStatList=alarmEventMap.get(cal.get(Calendar.MONTH));
				if(alarmEventMap.get(cal.get(Calendar.MONTH))==null){
					eventStatList=new ArrayList<AlarmEventStatisticsDTO>();
					alarmEventMap.put(cal.get(Calendar.MONTH), eventStatList);
				}
				eventStatList.add(alarmEvent);
				
			}
			}
		}
		System.out.println("alarmEventMap======="+alarmEventMap);
		
		
		Set<Map.Entry<Integer,List<AlarmEventStatisticsDTO>>> entrySet = alarmEventMap.entrySet();
		List<Integer> emptyMonths=new ArrayList<Integer>();
		List<String> customerNameList=new ArrayList<String>();
		Map<Integer,List<AlarmStatisticsDTO>> alarmStatisticsMap=new HashMap<Integer, List<AlarmStatisticsDTO>>();
		for (Map.Entry<Integer,List<AlarmEventStatisticsDTO>> entry : entrySet) {
			System.out.println("entry.getKey() |||||  "+entry.getKey());
			if(entry.getValue()!=null){
				System.out.println(entry.getKey()+":"+ entry.getValue().size());
			for(AlarmEventStatisticsDTO dto :  entry.getValue() ){
			
				List<AlarmStatisticsDTO> customerEventsList=alarmStatisticsMap.get(dto.getCustomerId());
				if(customerEventsList==null){
					customerEventsList=new ArrayList<AlarmStatisticsDTO>();
					alarmStatisticsMap.put(dto.getCustomerId(),customerEventsList);
				}
				AlarmStatisticsDTO alarmObj=getAlarmdtoFromList(customerEventsList,entry.getKey());
				if(alarmObj==null){
					if(!customerNameList.contains(dto.getCustomerName())){
						customerNameList.add(dto.getCustomerName());
					}
					alarmObj=new AlarmStatisticsDTO(dto.getCustomerName(),dto.getCustomerId(),1,monthNames[entry.getKey()]);
					customerEventsList.add(alarmObj);
				}else{
					alarmObj.setNoOfEvents(alarmObj.getNoOfEvents()+1);
				}
			}
			}else{
				if(!emptyMonths.contains(entry.getKey())){
				emptyMonths.add(entry.getKey());
				}
			}
		}
		
		
		
		
		ArrayList<AlarmStatisticsDTO> resultList=new ArrayList<AlarmStatisticsDTO>();
		if(alarmStatisticsMap!=null){
		for(Map.Entry<Integer,List<AlarmStatisticsDTO>> entry : alarmStatisticsMap.entrySet()){
			if(entry.getValue()!=null){
			resultList.addAll(entry.getValue());	
			}
		}
		}
		
		
		
		System.out.println("Empty Months=========="+emptyMonths);
		System.out.println("customerNameList=========="+customerNameList);
		for( Integer month : emptyMonths){
		for(String custName : customerNameList){
			resultList.add(new AlarmStatisticsDTO(custName,null,0,monthNames[month]));
		}
		}
		
		for(AlarmEventStatisticsDTO dto :  eventStatisticsList){
			if(!customerNameList.contains(dto.getCustomerName())){
				for( Integer month : emptyMonths){
					resultList.add(new AlarmStatisticsDTO(dto.getCustomerName(),dto.getCustomerId(),0,monthNames[month]));
				}
				customerNameList.add(dto.getCustomerName());
			}
		}
		String monthNameVal="";
		for (String val :monthNameArr){
			monthNameVal+=val+",";
		}
		
		for(AlarmStatisticsDTO dto : resultList ){
			dto.setMonthNameList(monthNameVal.substring(0,monthNameVal.lastIndexOf(",")));
		}
		return resultList;
		
	}
	
	private AlarmStatisticsDTO getAlarmdtoFromList(List<AlarmStatisticsDTO> customerEventsList,Integer monthId){
		for (AlarmStatisticsDTO dto:customerEventsList){
			if( monthNames[monthId].equalsIgnoreCase(dto.getMonthName())){
				return dto;
			}
		}
		return null;
	}
	

	public static void main(String[] args) {
		final long DAY=24*60*60*1000;	
		HashMap<Integer, Integer> dateMap=new HashMap<Integer, Integer>();
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		dateMap.put(cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));
		cal.setTimeInMillis(System.currentTimeMillis()-30*DAY);
		dateMap.put(cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));
		cal.setTimeInMillis(System.currentTimeMillis()-330*DAY);
		dateMap.put(cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));
		
		System.out.println(dateMap);
	}

	
	public List<AlarmEventDTO> getAllAlarmEvents(){
		List<AlarmEventDTO> alarmEventList= alarmEventDao.getAllalarmEvents();
		
		/*List<AlarmEventDTO> resultList=new ArrayList<AlarmEventDTO>();
		for(AlarmEvents event:  alarmEventList){
			resultList.add(event.convertToAlarmEventDTO());
		}*/
		
		
		return alarmEventList;
	}
	
	public List<AlarmEventWithContactsDTO> getAllAlarmEventsWithContactDetails(String customerId, String userId){
		List<AlarmEventWithContactsDTO> alarmEventList= alarmEventDao.getAllalarmEventsWithContactDetails(customerId);
		List<AlarmEventWithContactsDTO> resultList=filterAlarmEventsOnUserRole(alarmEventList,userId);
		if(resultList!=null){
			return resultList;
		}
		
		return alarmEventList;
	
	}
	
	@Transactional(readOnly=false)
	public boolean saveSoftwareMessage(Integer buildingId,Integer customerId,
    		String deviceId,String isUpdate,
    	    long eventGeneratedTimeInMillis,String value,String updatedBy){
	
		List<SoftwareMessage> swMessages=softwareMessageDao.getAllSoftwareMessagesFromBuilding(buildingId);
		List<SoftwareAlarmPoint> swAlarmPointList=getAlarmPointsFromMessage(value);
		
		Map<Integer,SoftwareMessage> swMessagesFromDB=new HashMap<Integer, SoftwareMessage>();
		
		if(swMessages!=null && swMessages.size()>0){
			for(SoftwareMessage swMsg : swMessages){
				swMessagesFromDB.put(swMsg.getObjectId(), swMsg);
			}
			
		}
		
		
		if(swAlarmPointList!=null && swAlarmPointList.size()>0){
			for(SoftwareAlarmPoint sw:  swAlarmPointList){
				
				SoftwareMessage softwareMessage=swMessagesFromDB.get(sw.getObjectId());
				System.out.println("Software Message frm DB===="+softwareMessage);
				if(softwareMessage!=null && !sw.getValue().equalsIgnoreCase(softwareMessage.getEventValue())){
					saveSoftwareMessage(softwareMessage, sw, updatedBy, true);					
				}else if(softwareMessage!=null && sw.getValue().equalsIgnoreCase(softwareMessage.getEventValue())){
					// No needto do anything
				} else{
					softwareMessage =new SoftwareMessage();
					//softwareMessage.setSoftwareMessageId(1);
					softwareMessage.setBuildingId(buildingId);
					softwareMessage.setCreatedBy(updatedBy);
					softwareMessage.setCreatedDtm(new Date());
					softwareMessage.setDescription(sw.getDescription());
					softwareMessage.setDeviceId(deviceId);
					softwareMessage.setEventValue(sw.getValue());
					softwareMessage.setObjectId(sw.getObjectId());
					softwareMessage.setObjectType(sw.getObjectType());
					softwareMessage.setEmailNotified(null);
					softwareMessage.setSmsNotified(null);
					softwareMessageDao.makePersistent(softwareMessage);
					//saveSoftwareMessage(softwareMessage, sw, updatedBy, false);
				}
				
			}
			
		}
		//for(SoftwareMessage swMsg :  swMessages){
			
		//}
		
		
		return true;
	}
	@Transactional(readOnly=false)
	private boolean saveSoftwareMessage(SoftwareMessage softwareMessage,SoftwareAlarmPoint swPoint,String createdBy,boolean isUpdate ){
		
		if(isUpdate){
			saveSoftwareMessageHistory(softwareMessage,createdBy);
			softwareMessage.setObjectType(swPoint.getObjectType());
			softwareMessage.setDescription(swPoint.getDescription());
			softwareMessage.setEventValue(swPoint.getValue());
			softwareMessage.setUpdatedBy(createdBy);
			softwareMessage.setUpdatedDtm(new Date());
			
			softwareMessage.setCreatedDtm(new Date());
			softwareMessage.setEmailNotified(null);
			softwareMessage.setSmsNotified(null);
			softwareMessageDao.makePersistent(softwareMessage);
			
		}
		return true;
	}
	
	private boolean saveSoftwareMessageHistory(SoftwareMessage softwareMessage,String createdBy){
	
		SoftwareMessageHistory history=new SoftwareMessageHistory();
		
		history.setBuildingId(softwareMessage.getBuildingId());
		history.setCreatedBy(createdBy);
		history.setCreatedDtm(new Date());
		history.setDescription(softwareMessage.getDescription());
		history.setDeviceId(softwareMessage.getDeviceId());
		history.setEventValue(softwareMessage.getEventValue());
		history.setObjectId(softwareMessage.getObjectId());
		history.setObjectType(softwareMessage.getObjectType());
		softwareMessageHistoryDao.makePersistent(history);
		return true;
	}
	
	private List<SoftwareAlarmPoint> getAlarmPointsFromMessage(String alarmValue){
		
		//Map<Integer,SoftwareAlarmPoint> swAlarmPointMap=new HashMap<Integer, SoftwareAlarmPoint>();
		
		List<SoftwareAlarmPoint> alrmPointList=new ArrayList<SoftwareAlarmPoint>();
		String[] arr=alarmValue.split(",");
		
		if(arr.length>0){
			for(String val : arr){
			String vals[]=val.split(":");
			Integer objId=null;
			String objType=null;
			String description=null;
			String  value=null;
			if(vals.length>0){
				objId=Integer.parseInt(vals[0]);
			}
			if(vals.length>1){
				objType=vals[1];
			}
			if(vals.length>2){
				description=vals[2];
			}
			if(vals.length>3){
				value=vals[3];
			}
			
			alrmPointList.add(new SoftwareAlarmPoint(objId, objType, description, value));
			
			}
			
		}
		return alrmPointList;
	}
	
	public List<SoftwareMessageDTO> getAllSoftwareMessages(Integer customerId,Integer buildingId){
		return softwareMessageDao.getAllSoftwareMessages(customerId, buildingId);
	}
	
	
	public List<SoftwareMessagePendingDTO> getAllNotfnPendingSoftwareMessages(){
		
		
		
		List<SoftwareMessagePendingDTO> swMessageList=softwareMessageDao.getAllNotfnPendingSoftwareMessages();
		
		List<OtherContactsDTO> otherContactsList=softwareMessageDao.getAllAlarmOtherEventsContacts();
		
		Map<String,String> otherContactMap=new HashMap<String, String>();
		
		if(otherContactsList!=null && otherContactsList.size()>0){
			for( OtherContactsDTO dto : otherContactsList){
			String searchStrSMS=dto.getCustomerId()+":"+dto.getDescription()+"SMS";
			String searchStrEmail=dto.getCustomerId()+":"+dto.getDescription()+"EMAIL";

			if(otherContactMap.get(searchStrSMS)!=null){
				String newVal=otherContactMap.get(searchStrSMS)+","+dto.getMobileNo();
				otherContactMap.put(searchStrSMS, newVal);
			}else{
				otherContactMap.put(searchStrSMS, dto.getMobileNo());
			}
			
			if(otherContactMap.get(searchStrEmail)!=null){
				String newVal=otherContactMap.get(searchStrEmail)+","+dto.getEmailId();
				otherContactMap.put(searchStrEmail, newVal);
			}else{
				otherContactMap.put(searchStrEmail, dto.getEmailId());
			}
			
			
			}
			
		}
		
		
		List<SoftwareMessagePendingDTO> resultList=new ArrayList<SoftwareMessagePendingDTO>();
		for(SoftwareMessagePendingDTO dto : swMessageList ){
			if(dto.getEventValue().equalsIgnoreCase("ALARM") && !( ("SMS".equalsIgnoreCase(dto.getNotfnType()) && "Y".equalsIgnoreCase(dto.getSmsNotified()))
					 || ("email".equalsIgnoreCase(dto.getNotfnType()) && "Y".equalsIgnoreCase(dto.getEmailNotified())))){
				
				if("SMS".equalsIgnoreCase(dto.getNotfnType())){
					String otherSMSContactsStr=dto.getCustomerId()+":"+dto.getEventDetails()+"SMS";
					dto.setOtherSMSContacts(otherContactMap.get(otherSMSContactsStr));					
				}else if("EMAIL".equalsIgnoreCase(dto.getNotfnType())){
					String otherEmailContactsStr=dto.getCustomerId()+":"+dto.getEventDetails()+"EMAIL";
					dto.setOtherEmailContacts(otherContactMap.get(otherEmailContactsStr));					
				}
				resultList.add(dto);
			}
			
		}
		return resultList;
	}
	
	@Transactional(readOnly=false)
	public boolean updateSwNotfnStatus(Integer alarmEventId,String notfnType,String notfnStatus){
		
		SoftwareMessage alarmEvent=softwareMessageDao.loadAlarmDevice(alarmEventId);
		Integer eventId=null;
		if(alarmEvent!=null){
			if("sms".equalsIgnoreCase(notfnType)){
				alarmEvent.setSmsNotified(notfnStatus);
			}else if("email".equalsIgnoreCase(notfnType)){
				alarmEvent.setEmailNotified(notfnStatus);
			}
			alarmEvent=softwareMessageDao.makePersistent(alarmEvent);
			eventId=alarmEvent.getSoftwareMessageId();
			
		}
		return (alarmEventId==eventId)?true:false;
		
	}
	
	
	@Transactional(readOnly=false)
	public boolean saveAlarmMessage(Integer buildingId,String deviceId, String messageDetails){
		
		AlarmMessages alarmMessage=new AlarmMessages();
		alarmMessage.setBuildingId(buildingId);
		alarmMessage.setDeviceId(deviceId);
		alarmMessage.setMessageDetails(messageDetails);
		alarmMessage.setCreatedTime(new Date());
		alarmMessagesDao.makePersistent(alarmMessage);
			
		
		return true;
	}
	@Override
	public List<AlarmMessagesDTO> getAllAlarmMessages(Integer customerId,Integer buildingId){
		
		return alarmMessagesDao.getAllSoftwareMessages(customerId, buildingId);
	}
	
	
	
}
