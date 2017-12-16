package com.vision.notificationservices.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.vision.notificationservices.communicator.EmailCommunicator;
import com.vision.notificationservices.dto.AlarmNotfnEventDetailsDTO;
import com.vision.notificationservices.dto.SoftwareMessagePendingDTO;





public class WSCommUtil {
	
	public static final String WS_BASE_URL=PropertyUtil.getInstance().getConfigProperty("alarmmonitor.webservice.baseurl");
	public static final String ALARM_NOTFN_STATUS_UPDATE_PATH="AlarmEvents.json/updateAlarmEventStatus";
	public static final String NOTFN_PENDING_SERVICE_PATH="AlarmEvents.json/notfnPendingEventlist";
	public static final String SAVE_ALARM_NOTFN_HISTORY_PATH="AlarmEvents.json/saveAlarmEventHistory";
	
	public static final String SW_ALARM_NOTFN_STATUS_UPDATE_PATH="AlarmEvents.json/updateswAlarmEventStatus";
	public static final String SW_NOTFN_PENDING_SERVICE_PATH="AlarmEvents.json/swnotfnPendingEventlist";
	
	public static String escapeUnicode(String input) {
		  StringBuilder b = new StringBuilder(input.length());
		  Formatter f = new Formatter(b);
		  for (char c : input.toCharArray()) {
		    if (c < 128) {
		      b.append(c);
		    } else {
		      f.format("\\u%04x", (int) c);
		    }
		  }
		  return b.toString();
		}
	
	public static List<AlarmNotfnEventDetailsDTO> getNotificationUnsentEventList() throws JsonParseException, JsonMappingException, IOException{
		try{
		String urlStr=WS_BASE_URL+NOTFN_PENDING_SERVICE_PATH;
		System.out.println("URL--->"+urlStr);
		String data=NetUtil.getResponseFromService(urlStr);
		//data=escapeUnicode(data);
		System.out.println("Data>"+data);
		//data=new String(data.getBytes("UTF8"),"UTF8");
		//=new String(data.getBytes(),"UTF-8");
		
		//EmailCommunicator.sendEmail(data,
			//	"Test Email Subject 11", "csuresh.tvm@gmail.com");

		ObjectMapper mapper=new ObjectMapper();
		List<AlarmNotfnEventDetailsDTO> eventList=mapper.readValue(data, new TypeReference<List<AlarmNotfnEventDetailsDTO>>(){});
		return eventList;
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return null;
	}
	
	
	public static boolean updateNotificationSendStatus(Integer alarmEventId,String notfnType,String notfnStatus){
		try{
			
			String urlStr=WS_BASE_URL+ALARM_NOTFN_STATUS_UPDATE_PATH+"?alarmEventId="+alarmEventId+"&notfnType="+notfnType+"&notfnStatus="+notfnStatus;
			
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
	
	public static boolean saveAlarmEventHistory(Integer alarmEventid,Integer contactId,String notfnType){
		try{
			
			String urlStr=WS_BASE_URL+SAVE_ALARM_NOTFN_HISTORY_PATH+"?alarmEventId="+alarmEventid+"&contactId="+contactId+"&notfnType="+notfnType;
			
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
	
	public static void main(String[] args) {
		saveAlarmEventHistory(4,4,"SMS");
	}
	
	
	
	public static List<SoftwareMessagePendingDTO> getSwNotificationUnsentEventList() throws JsonParseException, JsonMappingException, IOException{
		try{
		String urlStr=WS_BASE_URL+SW_NOTFN_PENDING_SERVICE_PATH;
		System.out.println("URL--->"+urlStr);
		String data=NetUtil.getResponseFromService(urlStr);
		//data=escapeUnicode(data);
		System.out.println("Data>"+data);
		//data=new String(data.getBytes("UTF8"),"UTF8");
		//=new String(data.getBytes(),"UTF-8");
		
		//EmailCommunicator.sendEmail(data,
			//	"Test Email Subject 11", "csuresh.tvm@gmail.com");

		ObjectMapper mapper=new ObjectMapper();
		List<SoftwareMessagePendingDTO> eventList=mapper.readValue(data, new TypeReference<List<SoftwareMessagePendingDTO>>(){});
		return eventList;
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return null;
	}
	
	
	public static boolean updateSwNotificationSendStatus(Integer alarmEventId,String notfnType,String notfnStatus){
		try{
			
			String urlStr=WS_BASE_URL+SW_ALARM_NOTFN_STATUS_UPDATE_PATH+"?alarmEventId="+alarmEventId+"&notfnType="+notfnType+"&notfnStatus="+notfnStatus;
			
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
	

}
