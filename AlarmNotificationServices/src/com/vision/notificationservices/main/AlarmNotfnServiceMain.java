package com.vision.notificationservices.main;

import java.util.logging.Logger;

import com.vision.notificationservices.monitor.AlarmNotificationMonitor;

public class AlarmNotfnServiceMain {
	static Logger log = Logger.getLogger("AlarmNotfnServiceMain");
	
	public static void main(String[] args) {
		
		log.info("AlarmNotfnServiceMain STARTED");
		AlarmNotificationMonitor.getInstance().startMonitor();
	}

}
