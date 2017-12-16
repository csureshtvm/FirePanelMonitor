package com.vision.fpserver.main;

import com.vision.fpserver.monitor.FirePanelMonitor;

public class FirePanelServerMain {
	
	public static void main(String[] args) {
		FirePanelMonitor.getInstance().startMonitor();
	}

}
