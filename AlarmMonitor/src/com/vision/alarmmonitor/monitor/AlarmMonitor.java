package com.vision.alarmmonitor.monitor;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.vision.alarmmonitor.ui.AlarmMonitorMainUI;
import com.vision.alarmmonitor.ui.LoginUI;
import com.vision.alarmmonitor.util.PropertyUtil;
import com.vision.alarmmonitor.util.WSCommUtil;


public class AlarmMonitor extends Thread{
	
	private static AlarmMonitor alarmMonitor;
	
	AlarmMonitorMainUI alarmmonitorMainUI=null;
	public static long lastDataRefreshTime=0;
	public static long lastAlarmEventRefreshTime=0;
	public static final long DATA_REFRESH_INTERVAL=Long.parseLong(PropertyUtil.getInstance().getConfigProperty("alarmmonitor.data_refresh_interval"));
	public static final long ALARMEVENT_REFRESH_INTERVAL=Long.parseLong(PropertyUtil.getInstance().getConfigProperty("alarmmonitor.event_refresh_interval"));
	public static boolean stop=false;
	public static boolean dataUpdated=false;
	public static boolean isAdminUser=false;
	public static boolean isSupportUser=false;
	public static String loggedInUserName="";
	public static String loginUserFullName="";
	public static String loggedInUserPW="";
	public static LoginUI loginUI;
	public static boolean started=false;
	private  AlarmMonitor(){
		alarmmonitorMainUI=new AlarmMonitorMainUI(loginUI);
	}
	
	public static AlarmMonitor getInstance() {
		if (alarmMonitor == null) {
			alarmMonitor = new AlarmMonitor();
		}
		return alarmMonitor;
	}
	
	public void startMonitor() {
		if(!started){
		this.start();
		started=true;
		}
	}
	public void run(){
		while(!stop){
			if((System.currentTimeMillis()-lastAlarmEventRefreshTime)>=ALARMEVENT_REFRESH_INTERVAL){
				
				try {
					WSCommUtil.populateEventData();
					WSCommUtil.populateAlarmDevicesData();
					alarmmonitorMainUI.populateAlarmEventData();
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
				lastAlarmEventRefreshTime=System.currentTimeMillis();
			}
			
			if(((System.currentTimeMillis()-lastDataRefreshTime)>=DATA_REFRESH_INTERVAL)  || dataUpdated){
				
				try {
					if(!AlarmMonitor.isSupportUser){
					WSCommUtil.populateData();
					alarmmonitorMainUI.populateData();
					lastDataRefreshTime=System.currentTimeMillis();
					dataUpdated=false;
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
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	

}
