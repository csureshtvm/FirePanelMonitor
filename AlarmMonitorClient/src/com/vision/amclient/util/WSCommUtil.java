package com.vision.amclient.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class WSCommUtil {
	
	public static final String WS_BASE_URL=PropertyUtil.getInstance().getConfigProperty("alarmmonitor.webservice.baseurl");
	public static final String ALARM_EVENT_SAVE_PATH="AlarmEvents.json/saveAlarmEvent";
	public static final String ALARM_DEVICE_SAVE_PATH="/AlarmDevices.json/saveAlarmDevice";
	public static final String UPDATE_DEVICE_HEALTH_PATH="/AlarmDevices.json/updateAlarmDevicestatus";
	public static final String customerId=PropertyUtil.getInstance().getConfigProperty("fpclient.customer_id");
	public static final String buildingId=PropertyUtil.getInstance().getConfigProperty("fpclient.building_id");
	public static final Integer buildingFloorNo=Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("fpclient.building_floor_no"));
	public static final String deviceId=PropertyUtil.getInstance().getConfigProperty("fpclient.alarm_device_id");
	
	public static boolean saveAlarmEvent( String eventType, String eventDetails,String testAlarm,long eventGeneratedTimeInMillis,Integer floorNo){
		try{
			
			ObjectMapper mapper=new ObjectMapper();
			String urlStr=WS_BASE_URL+ALARM_EVENT_SAVE_PATH+"?buildingId="+buildingId+"&eventType="+eventType+"&eventDetails="+URLEncoder.encode(eventDetails)+"&eventGeneratedTimeInMillis="+eventGeneratedTimeInMillis+"&testAlarm="+testAlarm+"&buildingFloorNo="+floorNo;
			
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
	
	public static boolean saveAlarmDevice(){
		try{
			
			ObjectMapper mapper=new ObjectMapper();
			String urlStr=WS_BASE_URL+ALARM_DEVICE_SAVE_PATH+"?buildingId="+buildingId+"&deviceId="+deviceId+"&installedFloorNo="+buildingFloorNo+"&updatedBy=AlarmDevice&isUpdate=false";
			
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
	public static boolean updateAlarmHealth(){
		try{
			
			ObjectMapper mapper=new ObjectMapper();
			String urlStr=WS_BASE_URL+UPDATE_DEVICE_HEALTH_PATH+"?deviceId="+deviceId;
			
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
	
	public static void main(String[] args) {
		System.out.println(saveAlarmEvent("FIRE", URLEncoder.encode("Fire Alarm"), "N", System.currentTimeMillis(),1));
	}
	
}
