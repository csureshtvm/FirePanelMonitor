package com.vision.amclient.util;

public class StaticMessages {

	public static final String PULL_STATION_MESSAGE = "PULL STATION                  FIRE ALARM";
	public static final String SMOKE_DETACTER_MESSAGE = "SMOKE DETECTOR                FIRE ALARM";
	public static final String HEAT_DETACTER_MESSAGE = "HEAT DETECTOR                 FIRE ALARM";
	public static final String DUCT_DETACTER_MESSAGE = "DUCT DETECTOR                 FIRE ALARM";
	public static final String FIRE_MONITOR_ZONE = "FIRE MONITOR ZONE             FIRE ALARM";

	public static final int ERROR = 2;
	public static final int NORMAL = 0;
	public static final int INFO = 1;
	
	public static final int SEND = 3;
	public static final int RECEIVE = 4;
	public static final int ALARM_TYPE_FIRE=1;
	public static final int ALARM_TYPE_NORMAL=2;
	
	public static final int MESSAGE_TYPE_REQ_DEVICE_ALARM=1;
	public static final int MESSAGE_TYPE_REQ_DEVCE_CONFIG=2;
	public static final int MESSAGE_TYPE_ACK_SYSTEM_HEALTH=3;
	public static final int MESSAGE_TYPE_ACK_DEVICE_RESET=4;
	
	public static final int MESSAGE_TYPE_RES_DEVICE_RESET=5;
	public static final int MESSAGE_TYPE_REQ_DEVICE_RESET=6;
	public static final int MESSAGE_TYPE_ACK_DEVICE_ALARM=7;
	public static final int MESSAGE_TYPE_ACK_DEVCE_CONFIG=8;
	public static final int MESSAGE_TYPE_REQ_SYSTEM_HEALTH=9;
	
	public static final int MESSAGE_TYPE_REQ_SOFTWARE_MESSAGE=13;
	public static final int MESSAGE_TYPE_REQ_SOFTWARE_MESSAGE_UPDATE=14;
}
