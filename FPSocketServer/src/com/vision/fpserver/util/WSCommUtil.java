package com.vision.fpserver.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.vision.fpserver.model.AlarmDeviceResetDto;

public class WSCommUtil {
	
	public static final String WS_BASE_URL=PropertyUtil.getInstance().getConfigProperty("alarmmonitor.webservice.baseurl");
	public static final String ALARM_EVENT_SAVE_PATH="AlarmEvents.json/saveAlarmEvent";
	public static final String ALARM_DEVICE_SAVE_PATH="AlarmDevices.json/saveAlarmDevice";
	public static final String UPDATE_DEVICE_HEALTH_PATH="AlarmDevices.json/updateAlarmDevicestatus";
	public static final String GET_ALL_DEVICE_RESET_PATH="AlarmDevices.json/getAllDevicesResetDetails";
	public static final String DEVICE_RESET_UPDATE_PATH="AlarmDevices.json/updateDeviceResetstatus";
	public static final String SOFTWARE_MESSAGE_SAVE_PATH="AlarmEvents.json/saveSoftwareMessage";
	//AlarmDevices.json/updateDeviceResetstatus?deviceId=TestDev23&resetStatus=Y
	//public static final String buildingId=PropertyUtil.getInstance().getConfigProperty("fpclient.building_id");
	//public static final Integer buildingFloorNo=Integer.parseInt(PropertyUtil.getInstance().getConfigProperty("fpclient.building_floor_no"));
	//public static final String deviceId=PropertyUtil.getInstance().getConfigProperty("fpclient.alarm_device_id");
	
	public static boolean saveAlarmEvent( String system, String signal,String value,String testAlarm,long eventGeneratedTimeInMillis,String floorNo,String buildingId){
		try{
			
			ObjectMapper mapper=new ObjectMapper();
			String urlStr = WS_BASE_URL + ALARM_EVENT_SAVE_PATH
					+ "?buildingId=" + buildingId + "&eventType=" + system
					+ "&eventDetails=" + URLEncoder.encode(signal)
					+ "&eventGeneratedTimeInMillis="
					+ eventGeneratedTimeInMillis + "&testAlarm=" + testAlarm
					+ "&buildingFloorNo=" + floorNo
					+ "&system=" + system
					+ "&signal=" + signal
					+ "&value=" + value;

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
	
	public static boolean saveAlarmDevice(String buildingId,String deviceId,String buildingFloorNo ){
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
	public static boolean updateAlarmHealth(String deviceId){
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
	
	public static void main1(String[] args) {
		//System.out.println(saveAlarmEvent("FIRE", URLEncoder.encode("Fire Alarm"), "N", System.currentTimeMillis(),1));
	}
	
	
	public static List<AlarmDeviceResetDto> getAllAlarmDeviceResetDetails() throws JsonParseException, JsonMappingException, IOException{
		
		try{
		String urlStr=WS_BASE_URL+GET_ALL_DEVICE_RESET_PATH;
		System.out.println("Going to invoke URL==========>"+urlStr);
		String data=NetUtil.getResponseFromService(urlStr);
		//if(data!=null){
			//data=new String(data.getBytes(),"UTF-8");
		//}
		System.out.println("getAllAlarmDeviceResetDetails====>"+data);
		ObjectMapper mapper=new ObjectMapper();
		List<AlarmDeviceResetDto> devResetDto=mapper.readValue(data, new TypeReference<List<AlarmDeviceResetDto>>(){});
		return devResetDto;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	public static boolean updateDeviceResetStatus(String deviceId,String status){
		try{
			
			ObjectMapper mapper=new ObjectMapper();
			String urlStr=WS_BASE_URL+DEVICE_RESET_UPDATE_PATH+"?deviceId="+deviceId+"&resetStatus="+status;
			
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
	
	public static boolean saveSoftwareMessage(String customerId,String buildingId,String deviceId,Date messageTime,String messageVal,boolean isUpdate){
		try{
			
			ObjectMapper mapper=new ObjectMapper();
			String urlStr=WS_BASE_URL + SOFTWARE_MESSAGE_SAVE_PATH
			+ "?customerId="+customerId+"&buildingId=" + buildingId 
			+ "&eventGeneratedTimeInMillis="+ messageTime.getTime()
			+ "&deviceId=" + deviceId
			+ "&isUpdate=" + isUpdate			
			+ "&value=" + URLEncoder.encode(messageVal);

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
		System.out.println(saveSoftwareMessage("1", "1", "", new Date(1509299952418l), "1281:Binary:UNACKNOWLEDGED FIRE:1,1282:Binary:UNACKNOWLEDGED SUPERVISORY:0,8:Binary:Basement Floor Fire:0,9:Binary:Ground Floor Fire:1,10:Binary:Service Mezz Fire:0,11:Binary:Mezzonine Fire:1,12:Binary:First Floor Fire:0,13:Binary:Second Floor Fire:0,14:Binary:Third Floor Fire:0,15:Binary:Fourth Floor Fire:0,16:Binary:Fifth Floor Fire:0,17:Binary:Sixth Floor Fire:0,18:Binary:Seventh Floor Fire:0,19:Binary:Eighth Floor Fire:0,20:Binary:Nineth Floor Fire:0,21:Binary:Tenth Floor Fire:0,22:Binary:Eleventh Floor Fire:0,23:Binary:Twelth Floor Fire:0,24:Binary:Thirteenth Floor Fire:0,25:Binary:Fourteenth Floor Fire:0,26:Binary:Service Roof:0,27:Binary:Roof:0,28:Binary:Common Fire:0,29:Binary:Common Trouble:0,1441:Analog:Fire Alarm:1.0,1442:Analog:Supervisory Alarm:0.0,1443:Analog:Troubles:8.0,1445:Analog:Dirty Sensors:0.0,38:Binary:Single Knock Fire:0,39:Binary:Double Knock Fire:0,1002:Binary:AC FAILURE:0,1005:Binary:LOW BATTERY:0", false));
	
		/*System.out
				.println(saveSoftwareMessage(
						"1",
						"1",
						"TEST",
						new Date(1509299952418l),
						"1281:Binary:UNACKNOWLEDGED FIRE:1,1282:Binary:UNACKNOWLEDGED SUPERVISORY:0",
						false));*/

	}
	
}
