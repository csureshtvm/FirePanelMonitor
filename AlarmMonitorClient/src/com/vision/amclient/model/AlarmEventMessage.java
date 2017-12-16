package com.vision.amclient.model;

import java.util.Date;

public class AlarmEventMessage extends AlarmMessage{

	int floorNo=-1;
	public AlarmEventMessage(String message, int type, Date messageDate,int floorNo){
		
		super(message,type,messageDate);
		this.floorNo=floorNo;
	}
	public int getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(int floorNo) {
		this.floorNo = floorNo;
	}
	
	
}
