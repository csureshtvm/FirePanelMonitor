package com.vision.fpservices.model;

import java.util.Date;

public class AlarmEventDto {
	
	private String buildingId;
	private String buildingName;
	private String alarmEventId;
	private String eventType;
	private String eventDetails;
	private boolean isTestAlarm;
	private Date eventGeneratedTime;
	
	public AlarmEventDto(String buildingId, String buildingName,
			String alarmEventId, String eventType, String eventDetails,
			boolean isTestAlarm, Date eventGeneratedTime) {
		
		this.buildingId=buildingId;
		this.buildingName=buildingName;
		this.alarmEventId=alarmEventId;
		this.eventType=eventType;
		this.eventDetails=eventDetails;
		this.isTestAlarm=isTestAlarm;
		this.eventGeneratedTime=eventGeneratedTime;

	}
	
	
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getAlarmEventId() {
		return alarmEventId;
	}
	public void setAlarmEventId(String alarmEventId) {
		this.alarmEventId = alarmEventId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventDetails() {
		return eventDetails;
	}
	public void setEventDetails(String eventDetails) {
		this.eventDetails = eventDetails;
	}
	public boolean isTestAlarm() {
		return isTestAlarm;
	}
	public void setTestAlarm(boolean isTestAlarm) {
		this.isTestAlarm = isTestAlarm;
	}
	public Date getEventGeneratedTime() {
		return eventGeneratedTime;
	}
	public void setEventGeneratedTime(Date eventGeneratedTime) {
		this.eventGeneratedTime = eventGeneratedTime;
	}
	
	
	

}
