package com.vision.amclient.monitor.main;

import com.vision.amclient.monitor.AlarmMonitor;

public class AlarmMonitorClientMain {

	
	public static void main(String[] args) {
		AlarmMonitor.getInstance().startMonitor();
	}
}
