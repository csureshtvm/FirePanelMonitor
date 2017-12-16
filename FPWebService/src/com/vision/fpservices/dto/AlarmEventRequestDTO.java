package com.vision.fpservices.dto;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.QueryParam;

import com.wordnik.swagger.core.ApiParam;

public class AlarmEventRequestDTO {

	Integer buildingId;
    String eventType;
    String eventDetails; 
    String testAlarm;
    long eventGeneratedTimeInMillis;
    Integer buildingFloorNo;
    String eventSystem;
    String eventSignal;
    String eventValue;
    String eventDeviceType;
	public Integer getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
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
	public String getTestAlarm() {
		return testAlarm;
	}
	public void setTestAlarm(String testAlarm) {
		this.testAlarm = testAlarm;
	}
	public long getEventGeneratedTimeInMillis() {
		return eventGeneratedTimeInMillis;
	}
	public void setEventGeneratedTimeInMillis(long eventGeneratedTimeInMillis) {
		this.eventGeneratedTimeInMillis = eventGeneratedTimeInMillis;
	}
	public Integer getBuildingFloorNo() {
		return buildingFloorNo;
	}
	public void setBuildingFloorNo(Integer buildingFloorNo) {
		this.buildingFloorNo = buildingFloorNo;
	}
	public String getEventSystem() {
		return eventSystem;
	}
	public void setEventSystem(String eventSystem) {
		this.eventSystem = eventSystem;
	}
	public String getEventSignal() {
		return eventSignal;
	}
	public void setEventSignal(String eventSignal) {
		this.eventSignal = eventSignal;
	}
	public String getEventValue() {
		return eventValue;
	}
	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}
	public String getEventDeviceType() {
		return eventDeviceType;
	}
	public void setEventDeviceType(String eventDeviceType) {
		this.eventDeviceType = eventDeviceType;
	}
    
    public String toString(){
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("buildingId", buildingId);
    	map.put("eventType", eventType);
    	map.put("eventDetails", eventDetails);
    	map.put("testAlarm", testAlarm);
    	map.put("eventGeneratedTimeInMillis", eventGeneratedTimeInMillis);
    	map.put("buildingFloorNo", buildingFloorNo);
    	map.put("eventSystem", eventSystem);
    	map.put("eventSignal", eventSignal);
    	map.put("eventValue", eventValue);
    	map.put("eventDeviceType", eventDeviceType);
    	
       return map.toString(); 
       
    	
    }
}
