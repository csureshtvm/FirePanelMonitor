package com.vision.alarmmonitor.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.vision.alarmmonitor.dto.AddressDetailsDTO;
import com.vision.alarmmonitor.dto.AlarmDevicesDto;
import com.vision.alarmmonitor.dto.AlarmEventDTO;
import com.vision.alarmmonitor.dto.AlarmEventStatisticsDTO;
import com.vision.alarmmonitor.dto.AlarmStatisticsDTO;
import com.vision.alarmmonitor.dto.BuildingDTO;
import com.vision.alarmmonitor.dto.BuildingsummaryDTO;
import com.vision.alarmmonitor.dto.ContactDetailsDTO;
import com.vision.alarmmonitor.dto.CustomerDTO;
import com.vision.alarmmonitor.dto.CustomerStatisticsDTO;
import com.vision.alarmmonitor.dto.SoftwareMessageDTO;
import com.vision.alarmmonitor.dto.UserDTO;
import com.vision.alarmmonitor.main.AlarmMonitorMain;
import com.vision.alarmmonitor.model.AlarmEventGraphicalDetals;
import com.vision.alarmmonitor.monitor.AlarmMonitor;


public class WSCommUtil {
	
	public static final String WS_BASE_URL=PropertyUtil.getInstance().getConfigProperty("alarmmonitor.webservice.baseurl");
	public static final String ALL_BUILDINGS_PATH="Building.json/buildings";
	public static final String EVENT_LIST_PATH="AlarmEvents.json/events";
	public static String customerId=PropertyUtil.getInstance().getConfigProperty("alarmmonitor.customer_id");
	public static final String CUSTOMER_DETAILS_PATH="Customer.json/customer";
	public static final String CONTACT_DETAILS_SAVE_PATH="Contacts.json/saveContact";
	public static final String ALARM_EVENT_RESET_PATH="AlarmEvents.json/resetAlarm";
	public static final String USER_LOGIN_PATH="User.json/loginUser";
	public static final String CUSTOMER_ANALYTICS_PATH="Customer.json/customerAnalytics";
	public static final String EVENT_ANALYTICS_PATH="AlarmEvents.json/eventAnalytics";
	public static final String CREATE_CUSTOMER_PATH="Customer.json/saveCustomer";
	public static final String UPDATE_CUSTOMER_PATH="Customer.json/updateCustomer";
	public static final String CREATE_USER_PATH="User.json/createNewUser";
	public static final String SAVE_BUILDING_PATH="Building.json/saveBuilding";
	public static final String CHANGE_PW_PATH="User.json/changePassword";
	public static final String ADDRESS_DETAILS_SAVE_PATH="Address.json/saveAddress";
	public static final String EVENT_ANALYTICS_PATH_1="AlarmEvents.json/eventStatiStics";
	public static final String ALL_CUSTOMER_EVENT_LIST_PATH="AlarmEvents.json/getAllCustomerEvents";
	public static final String ALARM_DEVICES_DETAILS_PATH="AlarmDevices.json/getAllAlarmDevices";
	public static final String ALARM_DEVICE_SAVE_PATH="AlarmDevices.json/saveAlarmDevice";
	public static final String SAVE_ALARM_NOTFN_SETTINGS_PATH="Building.json/saveBuildingNotificationSettings";
	public static final String SOFTWARE_MESSAGE_PATH="AlarmEvents.json/getAllSoftwareMessages";
	
	public static List<BuildingDTO> buildingList=new ArrayList<BuildingDTO>();
	public static List<AlarmEventDTO> alarmEventList=new ArrayList<AlarmEventDTO>();

	public static List<SoftwareMessageDTO> softwareMessagesList=new ArrayList<SoftwareMessageDTO>();
	public static CustomerDTO customerDetails=null;
	public static Map<String,Integer> customerMap=new HashMap<String,Integer>();
	public static List<AlarmEventGraphicalDetals> alramEventGrphicalList=new ArrayList<AlarmEventGraphicalDetals>();
	public static List<AlarmDevicesDto> alarmDevicestList=new ArrayList<AlarmDevicesDto>();
	
	
	public static void populateAllBuildingsOfCustomer() throws JsonParseException, JsonMappingException, IOException{
		
		try{
		String urlStr=WS_BASE_URL+ALL_BUILDINGS_PATH+"?customerId="+customerId;
		String data=NetUtil.getResponseFromService(urlStr);
		//if(data!=null){
			//data=new String(data.getBytes(),"UTF-8");
		//}
		System.out.println("populateAllBuildingsOfCustomer====>"+data);
		ObjectMapper mapper=new ObjectMapper();
		List<BuildingDTO> bldgs=mapper.readValue(data, new TypeReference<List<BuildingDTO>>(){});
		buildingList.clear();
		buildingList.addAll(bldgs);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	public static void populateEventListOfCustomer() throws JsonParseException, JsonMappingException, IOException{
		if(customerId==null){
			return;
		}
		try{
		String urlStr=WS_BASE_URL+EVENT_LIST_PATH+"?customerId="+customerId;
		String data=NetUtil.getResponseFromService(urlStr);
		ObjectMapper mapper=new ObjectMapper();
		List<AlarmEventDTO> eventList=mapper.readValue(data, new TypeReference<List<AlarmEventDTO>>(){});
		alarmEventList.clear();
		alarmEventList.addAll(eventList);
		Map<String,AlarmEventGraphicalDetals> alarmCountMap=new HashMap<String, AlarmEventGraphicalDetals>();
		
		AlarmEventComparator eventComp=new AlarmEventComparator(alarmCountMap);
		for(AlarmEventDTO alarmEvent :  eventList){
			AlarmEventGraphicalDetals alarmEventGraphDet=alarmCountMap.get(alarmEvent.getBuildingId());
			
			
			if(alarmEventGraphDet==null){
				alarmEventGraphDet=new AlarmEventGraphicalDetals();
				alarmEventGraphDet.setBuildingName(alarmEvent.getBuildingName());
				alarmEventGraphDet.setNoOfFloors(alarmEvent.getBldgNoOfFloors());
				alarmEventGraphDet.setBuildingAddressFirstLine(alarmEvent.getBldgAddressFirstLine());
				alarmEventGraphDet.setBuildingAddressSecondLine(alarmEvent.getBldgAddressSecondLine());
				alarmEventGraphDet.setTown(alarmEvent.getBldgTown());
				alarmEventGraphDet.setCountry(alarmEvent.getBldgCountry());
				alarmCountMap.put(alarmEvent.getBuildingId(), alarmEventGraphDet);
			}
			
			Map<Integer, Integer> fireAlarmCountMap=alarmEventGraphDet.getFireAlarmCountMap();
			Map<Integer, Integer> normalAlarmCountMap=alarmEventGraphDet.getNormalAlarmCountMap();
			if(fireAlarmCountMap==null){
				fireAlarmCountMap=new HashMap<Integer, Integer>();
				alarmEventGraphDet.setFireAlarmCountMap(fireAlarmCountMap);
			}
			if(normalAlarmCountMap==null){
				normalAlarmCountMap=new HashMap<Integer, Integer>();
				alarmEventGraphDet.setNormalAlarmCountMap(normalAlarmCountMap);
			}
			
			if(AppConstants.ALARM_TYPE_FIRE.equalsIgnoreCase(alarmEvent.getEventType())){
				
				if(fireAlarmCountMap.get(alarmEvent.getBuildingFloorNo())==null){
					fireAlarmCountMap.put(alarmEvent.getBuildingFloorNo(), 1);
				}else{
					fireAlarmCountMap.put(alarmEvent.getBuildingFloorNo(), fireAlarmCountMap.get(alarmEvent.getBuildingFloorNo())+1);
				}
				alarmEventGraphDet.setTotalFireAlarms(alarmEventGraphDet.getTotalFireAlarms()+1);
				
			}else if (AppConstants.ALARM_TYPE_NORMAL.equalsIgnoreCase(alarmEvent.getEventType())){

				
				if(normalAlarmCountMap.get(alarmEvent.getBuildingFloorNo())==null){
					normalAlarmCountMap.put(alarmEvent.getBuildingFloorNo(), 1);
				}else{
					normalAlarmCountMap.put(alarmEvent.getBuildingFloorNo(), normalAlarmCountMap.get(alarmEvent.getBuildingFloorNo())+1);
				}
				alarmEventGraphDet.setTotalNormalAlarms(alarmEventGraphDet.getTotalNormalAlarms()+1);
			}
		}
		
		TreeMap<String, AlarmEventGraphicalDetals> sorted_map = new TreeMap<String, AlarmEventGraphicalDetals>(eventComp);
		sorted_map.putAll(alarmCountMap);
		if(sorted_map.size()>0){
			alramEventGrphicalList.clear();
			int count=0;
		for(Map.Entry<String, AlarmEventGraphicalDetals> entry : sorted_map.entrySet()) {
			alramEventGrphicalList.add(entry.getValue());
			count++;
			if(count==2){
				break;
			}
			
		}
		}
		
		
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	}
	
	
	public static void populateCustomerDetails() {
		if(customerId==null){
			return;
		}
		try{
		String urlStr=WS_BASE_URL+CUSTOMER_DETAILS_PATH+"?customerId="+customerId;
		String data=NetUtil.getResponseFromService(urlStr);
		ObjectMapper mapper=new ObjectMapper();
		CustomerDTO customerDTO=mapper.readValue(data, new TypeReference<CustomerDTO>(){});
		customerDetails=customerDTO;
		
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	}
	
	
	public static boolean saveContactDetails(ContactDetailsDTO contactDetails,String contactType, Integer buildingId,String customerId){
		try{
			
			ObjectMapper mapper=new ObjectMapper();
			String contactDetailsStr=mapper.writeValueAsString(contactDetails);
			contactDetailsStr=URLEncoder.encode(contactDetailsStr);
			String urlStr=WS_BASE_URL+CONTACT_DETAILS_SAVE_PATH+"?customerId="+customerId+"&contactType="+contactType+"&buildingId="+buildingId+"&contactDetails="+contactDetailsStr;
			
			System.out.println("URL==========>"+urlStr);
			String data=NetUtil.postResponseToServer(urlStr);
			Boolean result=new Boolean(data);
			AlarmMonitor.dataUpdated=true;
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	public static void populateEventData() throws JsonParseException, JsonMappingException, IOException{
		if(AlarmMonitor.isSupportUser){
		populateAllCustomerEvents();
		}else{
		populateEventListOfCustomer();
		
		populateSoftwareMessages();

		}	}
	public static boolean resetAlarm(Integer alarmEventid,String resetComments){
		try{
			resetComments=URLEncoder.encode(resetComments,"UTF-8");
			String urlStr=WS_BASE_URL+ALARM_EVENT_RESET_PATH+"?alarmEventId="+alarmEventid+"&resetComments="+resetComments+"&requestedBy="+AlarmMonitor.loggedInUserName;
			
			System.out.println("URL==========>"+urlStr);
			String data=NetUtil.postResponseToServer(urlStr);
			Boolean result=new Boolean(data);
			AlarmMonitor.dataUpdated=true;
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	public static void populateData() throws JsonParseException, JsonMappingException, IOException{
		populateCustomerDetails();
		populateAllBuildingsOfCustomer();
	}
	
	
	public static Map<String,Object> loginUser(String userName,String password){
		try{
			
			String urlStr=WS_BASE_URL+USER_LOGIN_PATH+"?userName="+userName+"&password="+password;
			System.out.println("URL==========>"+urlStr);
			String data=NetUtil.postResponseToServer(urlStr);
			System.out.println("Response --------------"+data);
			Map<String,Object> resultMap=new HashMap<String, Object>();
			if(data!=null){
				if(AppConstants.LOGIN_ERROR_CODE_INACTIVEUSER.equals(data)){
					resultMap.put("status", AppConstants.STATUS_FAILED);
					resultMap.put("message", AppConstants.LOGIN_ERROR_INACTIVE_USER_MSG);
					
				}else if(AppConstants.LOGIN_ERROR_CODE_INCORRECT_PW.equals(data)){
					resultMap.put("status", AppConstants.STATUS_FAILED);
					resultMap.put("message", AppConstants.LOGIN_ERROR_INCORRECT_PW_MSG);
				}else if(AppConstants.LOGIN_ERROR_CODE_INVALID_USER.equals(data)){
					resultMap.put("status", AppConstants.STATUS_FAILED);
					resultMap.put("message", AppConstants.LOGIN_ERROR_INVALID_USER_MSG);
				}else{
					AlarmMonitor.loggedInUserPW=password;
					resultMap.put("status", AppConstants.STATUS_SUCCESS);
					ObjectMapper mapper=new ObjectMapper();
					UserDTO userDTO=mapper.readValue(data, new TypeReference<UserDTO>(){});
					AlarmMonitor.loggedInUserName=userDTO.getUserName();
					AlarmMonitor.loginUserFullName=userDTO.getFirstName()+" "+userDTO.getLastName()!=null?userDTO.getLastName():"";
					resultMap.put("user",userDTO);
				}
			}
			
			return resultMap;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public static List<CustomerStatisticsDTO> populateCustomerAnalytics() {
		try{
		String urlStr=WS_BASE_URL+CUSTOMER_ANALYTICS_PATH;
		String data=NetUtil.getResponseFromService(urlStr);
		ObjectMapper mapper=new ObjectMapper();
		List<CustomerStatisticsDTO> customerStatsDTOList=mapper.readValue(data, new TypeReference<List<CustomerStatisticsDTO>>(){});
		if(customerStatsDTOList!=null && customerStatsDTOList.size()>0){
			customerMap.clear();
			for(CustomerStatisticsDTO custStat : customerStatsDTOList){
				customerMap.put(custStat.getCustomerName(),custStat.getCustomerId());
				if(custStat.getBuildings()!=null){
					custStat.setNoOfBuildings(custStat.getBuildings());
				}
			}
		}
		
		return customerStatsDTOList;
		
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return null;
	}
	
	
	public static List<AlarmEventStatisticsDTO> populateEventAnalytics() {
		try{
		String urlStr=WS_BASE_URL+EVENT_ANALYTICS_PATH;
		String data=NetUtil.getResponseFromService(urlStr);
		ObjectMapper mapper=new ObjectMapper();
		List<AlarmEventStatisticsDTO> eventStatsDTOList=mapper.readValue(data, new TypeReference<List<AlarmEventStatisticsDTO>>(){});
		return eventStatsDTOList;
		
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return null;
	}
	public static Map<String,Object> populateEventAnalyticsNew() {
		String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		Map<String,Object> resultMap=new HashMap<String, Object>();
		try{
		String urlStr=WS_BASE_URL+EVENT_ANALYTICS_PATH_1;
		String data=NetUtil.getResponseFromService(urlStr);
		System.out.println(data);
		
		ObjectMapper mapper=new ObjectMapper();
		List<AlarmStatisticsDTO> eventStatsDTOList=mapper.readValue(data, new TypeReference<List<AlarmStatisticsDTO>>(){});
		Map<String,Object> eventAnalyticsMap=null;
		if(eventStatsDTOList!=null){
			eventAnalyticsMap=new HashMap<String, Object>();
		ArrayList<String> custList=new ArrayList<String>();
		Map<String,Map<String,Integer>> custMap=new HashMap<String, Map<String,Integer>>();
		List<String> monthNamesInData=new ArrayList<String>();
		String monthNameList="";
		for(AlarmStatisticsDTO eventStat: eventStatsDTOList ){
			
			Map<String,Integer> evMap=custMap.get(eventStat.getCustomerName());
			if(evMap==null){
				evMap=new HashMap<String, Integer>();
				custMap.put(eventStat.getCustomerName(), evMap);
			}
			evMap.put(eventStat.getMonthName(), eventStat.getNoOfEvents());	
			monthNameList=eventStat.getMonthNameList();
			if(!monthNamesInData.contains(eventStat.getMonthName())){
				monthNamesInData.add(eventStat.getMonthName());
			}
		}
		
		
		
		String custNameLbls[]=new String[custMap.size()];
		String[] months=monthNameList.split(",");
		Integer[][] vals=new Integer[3][custMap.size()];
		resultMap.put("custNames", custNameLbls);
		resultMap.put("months", months);
		resultMap.put("vals", vals);
		
		int i=0;
		int countVal=0;
		int custCount=0;
		int monthCount=0;
		for(Map.Entry<String,Map<String,Integer>> entry : custMap.entrySet()){
			custNameLbls[i++]=entry.getKey();
			Map<String,Integer> valMap=entry.getValue();
			for(int j=0;j<months.length;j++ ){
				vals[custCount++][monthCount]=valMap.get(months[j]);
			}
			custCount=0;
			monthCount++;
		}
		
		}
		
		
		
		return resultMap;
		
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return null;
	}
	
	public static void main(String[] args) {
		//System.out.println(loginUser("visionuser", "visionfocus"));
		
		HashMap<String, AlarmEventGraphicalDetals> alarmMap=new HashMap<String, AlarmEventGraphicalDetals>();
		AlarmEventComparator comp=new AlarmEventComparator(alarmMap);
		AlarmEventGraphicalDetals al1=new AlarmEventGraphicalDetals();
		al1.setTotalFireAlarms(5);
		al1.setTotalNormalAlarms(2);
		
		AlarmEventGraphicalDetals al2=new AlarmEventGraphicalDetals();
		al2.setTotalFireAlarms(3);
		al2.setTotalNormalAlarms(1);
		AlarmEventGraphicalDetals al3=new AlarmEventGraphicalDetals();
		al3.setTotalFireAlarms(11);
		al3.setTotalNormalAlarms(4);
		AlarmEventGraphicalDetals al4=new AlarmEventGraphicalDetals();
		al4.setTotalFireAlarms(2);
		al4.setTotalNormalAlarms(1);
		AlarmEventGraphicalDetals al5=new AlarmEventGraphicalDetals();
		al5.setTotalFireAlarms(5);
		al5.setTotalNormalAlarms(5);
		
		TreeMap<String, AlarmEventGraphicalDetals> sorted_map = new TreeMap<String, AlarmEventGraphicalDetals>(comp);
		alarmMap.put("1", al1);
		alarmMap.put("2", al2);
		alarmMap.put("3", al3);
		alarmMap.put("4", al4);
		alarmMap.put("5", al5);
		
		sorted_map.putAll(alarmMap);
		System.out.println(sorted_map);
	}
	public static boolean createCustomer(String customerName){
		try{
			
			String urlStr=WS_BASE_URL+CREATE_CUSTOMER_PATH+"?customerName="+URLEncoder.encode(customerName)+"&createdBy="+AlarmMonitor.loggedInUserName;
			
			System.out.println("URL==========>"+urlStr);
			String data=NetUtil.postResponseToServer(urlStr);
			Boolean result=new Boolean(data);
			if(result){
			AlarmMonitor.dataUpdated=true;
			}
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean updateCustomer(String customerName){
		try{
			if(customerId==null){
				return false;
			}
			String urlStr=WS_BASE_URL+UPDATE_CUSTOMER_PATH+"?customerName="+URLEncoder.encode(customerName)+"&updatedBy="+AlarmMonitor.loggedInUserName+"&customerId="+WSCommUtil.customerId;
			
			System.out.println("URL==========>"+urlStr);
			String data=NetUtil.postResponseToServer(urlStr);
			Boolean result=new Boolean(data);
			if(result){
			AlarmMonitor.dataUpdated=true;
			}
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean createNewUser(UserDTO userDTO){
		try{
			ObjectMapper mapper=new ObjectMapper();
			String userDetailsStr=mapper.writeValueAsString(userDTO);
			String urlStr=WS_BASE_URL+CREATE_USER_PATH+"?userDetails="+URLEncoder.encode(userDetailsStr)+"&createdBy="+AlarmMonitor.loggedInUserName;
			
			System.out.println("URL==========>"+urlStr);
			String data=NetUtil.postResponseToServer(urlStr);
			Boolean result=new Boolean(data);
			
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	public static boolean saveBuilding(BuildingsummaryDTO bldgSummaryDTO){
		try{
			ObjectMapper mapper=new ObjectMapper();
			String bldgDetailsStr=mapper.writeValueAsString(bldgSummaryDTO);
			String urlStr=WS_BASE_URL+SAVE_BUILDING_PATH+"?buildingDetails="+URLEncoder.encode(bldgDetailsStr,"UTF-8")+"&customerId="+customerId+"&updatedBy="+AlarmMonitor.loggedInUserName;
			
			System.out.println("URL==========>"+urlStr);
			String data=NetUtil.postResponseToServer(urlStr);
			Boolean result=new Boolean(data);
			if(result){
				AlarmMonitor.dataUpdated=true;
				AlarmMonitor.lastDataRefreshTime=System.currentTimeMillis();
				}
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean updatePassword(String newPassword){
		try{
			String urlStr=WS_BASE_URL+CHANGE_PW_PATH+"?userName="+AlarmMonitor.loggedInUserName+"&newPw="+newPassword;
			
			System.out.println("URL==========>"+urlStr);
			String data=NetUtil.postResponseToServer(urlStr);
			Boolean result=new Boolean(data);
			
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	
	public static boolean saveAddressDetails(AddressDetailsDTO addressDetails,String addressType, Integer buildingId,String customerId){
		try{
			
			ObjectMapper mapper=new ObjectMapper();
			String addressDetailsStr=mapper.writeValueAsString(addressDetails);
			addressDetailsStr=URLEncoder.encode(addressDetailsStr);
			String urlStr=WS_BASE_URL+ADDRESS_DETAILS_SAVE_PATH+"?customerId="+customerId+"&addressType="+addressType+"&buildingId="+buildingId+"&addressDetails="+addressDetailsStr+"&updatedBy="+AlarmMonitor.loggedInUserName;
			
			System.out.println("URL==========>"+urlStr);
			String data=NetUtil.postResponseToServer(urlStr);
			Boolean result=new Boolean(data);
			if(result){
			AlarmMonitor.dataUpdated=true;
			AlarmMonitor.lastDataRefreshTime=System.currentTimeMillis();
			}
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	
	public static void populateAllCustomerEvents() throws JsonParseException, JsonMappingException, IOException{
		try{
		String urlStr=WS_BASE_URL+ALL_CUSTOMER_EVENT_LIST_PATH;
		String data=NetUtil.getResponseFromService(urlStr);
		ObjectMapper mapper=new ObjectMapper();
		List<AlarmEventDTO> eventList=mapper.readValue(data, new TypeReference<List<AlarmEventDTO>>(){});
		alarmEventList.clear();
		alarmEventList.addAll(eventList);
		Map<String,AlarmEventGraphicalDetals> alarmCountMap=new HashMap<String, AlarmEventGraphicalDetals>();
		
		AlarmEventComparator eventComp=new AlarmEventComparator(alarmCountMap);
		for(AlarmEventDTO alarmEvent :  eventList){
			AlarmEventGraphicalDetals alarmEventGraphDet=alarmCountMap.get(alarmEvent.getBuildingId());
			
			
			if(alarmEventGraphDet==null){
				alarmEventGraphDet=new AlarmEventGraphicalDetals();
				alarmEventGraphDet.setBuildingName(alarmEvent.getBuildingName());
				alarmEventGraphDet.setNoOfFloors(alarmEvent.getBldgNoOfFloors());
				alarmEventGraphDet.setBuildingAddressFirstLine(alarmEvent.getBldgAddressFirstLine());
				alarmEventGraphDet.setBuildingAddressSecondLine(alarmEvent.getBldgAddressSecondLine());
				alarmEventGraphDet.setTown(alarmEvent.getBldgTown());
				alarmEventGraphDet.setCountry(alarmEvent.getBldgCountry());
				alarmCountMap.put(alarmEvent.getBuildingId(), alarmEventGraphDet);
			}
			
			Map<Integer, Integer> fireAlarmCountMap=alarmEventGraphDet.getFireAlarmCountMap();
			Map<Integer, Integer> normalAlarmCountMap=alarmEventGraphDet.getNormalAlarmCountMap();
			if(fireAlarmCountMap==null){
				fireAlarmCountMap=new HashMap<Integer, Integer>();
				alarmEventGraphDet.setFireAlarmCountMap(fireAlarmCountMap);
			}
			if(normalAlarmCountMap==null){
				normalAlarmCountMap=new HashMap<Integer, Integer>();
				alarmEventGraphDet.setNormalAlarmCountMap(normalAlarmCountMap);
			}
			
			if(AppConstants.ALARM_TYPE_FIRE.equalsIgnoreCase(alarmEvent.getEventType())){
				
				if(fireAlarmCountMap.get(alarmEvent.getBuildingFloorNo())==null){
					fireAlarmCountMap.put(alarmEvent.getBuildingFloorNo(), 1);
				}else{
					fireAlarmCountMap.put(alarmEvent.getBuildingFloorNo(), fireAlarmCountMap.get(alarmEvent.getBuildingFloorNo())+1);
				}
				alarmEventGraphDet.setTotalFireAlarms(alarmEventGraphDet.getTotalFireAlarms()+1);
				
			}else if (AppConstants.ALARM_TYPE_NORMAL.equalsIgnoreCase(alarmEvent.getEventType())){

				
				if(normalAlarmCountMap.get(alarmEvent.getBuildingFloorNo())==null){
					normalAlarmCountMap.put(alarmEvent.getBuildingFloorNo(), 1);
				}else{
					normalAlarmCountMap.put(alarmEvent.getBuildingFloorNo(), normalAlarmCountMap.get(alarmEvent.getBuildingFloorNo())+1);
				}
				alarmEventGraphDet.setTotalNormalAlarms(alarmEventGraphDet.getTotalNormalAlarms()+1);
			}
		}
		
		TreeMap<String, AlarmEventGraphicalDetals> sorted_map = new TreeMap<String, AlarmEventGraphicalDetals>(eventComp);
		sorted_map.putAll(alarmCountMap);
		if(sorted_map.size()>0){
			alramEventGrphicalList.clear();
			int count=0;
		for(Map.Entry<String, AlarmEventGraphicalDetals> entry : sorted_map.entrySet()) {
			alramEventGrphicalList.add(entry.getValue());
			count++;
			if(count==2){
				break;
			}
			
		}
		}
		
		
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	}
	
	
	public static void populateAlarmDevicesData() {
		try{
		String urlStr=WS_BASE_URL+ALARM_DEVICES_DETAILS_PATH+"?customerId="+customerId;
		
		System.out.println(urlStr);
		String data=NetUtil.getResponseFromService(urlStr);
		ObjectMapper mapper=new ObjectMapper();
		List<AlarmDevicesDto> resultList=mapper.readValue(data, new TypeReference<List<AlarmDevicesDto>>(){});
		
		
		System.out.println("ALARM DEVICES ===================>"+resultList.size());
		if(resultList!=null && resultList.size()>0){
			alarmDevicestList.clear();
			alarmDevicestList.addAll(resultList);
		}
		
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	}
	
	public static boolean saveAlarmDevice(String buildingId,String deviceId,String deviceName,String deviceLoc,String deviceFloorNo ){
		try{
			
			String urlStr=WS_BASE_URL+ALARM_DEVICE_SAVE_PATH+"?buildingId="+buildingId+"&deviceId="+deviceId+"&installedFloorNo="+deviceFloorNo+"&deviceName="+URLEncoder.encode(deviceName)+"&deviceLoc="+URLEncoder.encode(deviceLoc)+"&updatedBy="+AlarmMonitor.loggedInUserName+"&isUpdate=true";
			System.out.println("Going to invoke URL==========>"+urlStr);
			String data=NetUtil.postResponseToServer(urlStr);
			Boolean result=new Boolean(data);
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean saveNotfnSettings(String buildingId,String contactType,String notifySMS,String notifyEmail){
		try{
			
			ObjectMapper mapper=new ObjectMapper();
			String urlStr=WS_BASE_URL+SAVE_ALARM_NOTFN_SETTINGS_PATH+"?buildingId="+buildingId+"&contactType="+contactType+"&notifySMS="+notifySMS+"&notifyEmail="+notifyEmail+"&updatedBy="+AlarmMonitor.loggedInUserName;
			
			System.out.println("Notification Save URL==========>"+urlStr);
			String data=NetUtil.postResponseToServer(urlStr);
			Boolean result=new Boolean(data);
			if(result){
				AlarmMonitor.dataUpdated=true;
				AlarmMonitor.lastDataRefreshTime=System.currentTimeMillis();
				}
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public static void populateSoftwareMessages()throws JsonParseException, JsonMappingException, IOException{
		if(customerId==null){
			return;
		}
		try{
		String urlStr=WS_BASE_URL+SOFTWARE_MESSAGE_PATH+"?customerId="+customerId;
		String data=NetUtil.getResponseFromService(urlStr);
		ObjectMapper mapper=new ObjectMapper();
		List<SoftwareMessageDTO> swAlarmsList=mapper.readValue(data, new TypeReference<List<SoftwareMessageDTO>>(){});
		softwareMessagesList.clear();
		softwareMessagesList.addAll(swAlarmsList);
				
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	}
	
	

	
	
}
