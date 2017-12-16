package com.vision.fpservices.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.vision.fpservices.db.model.Building;

public class AlarmEventDTO {
	
	
	private Integer alarmEventId;
	private String eventType;
	private Date eventGeneratedTime;
	private String isTestAlarm;
	private String eventDetails;
	private String buildingId;
	private String isActive;
	private String smsNotified;
	private String emailNotified;
	private String buildingName;
	private Integer buildingFloorNo;
	
	private Integer bldgNoOfFloors;
	private String bldgAddressFirstLine;
	private String bldgAddressSecondLine;
	private String bldgTown;
	private String bldgCountry;
	
	private Integer customerId;
	private String customerName;
	
	
	private String eventSystem;
	private String eventSignal;
	private String eventValue;
	
	/**
	 * @return the buildingFloorNo
	 */
	public Integer getBuildingFloorNo() {
		return buildingFloorNo;
	}
	/**
	 * @param buildingFloorNo the buildingFloorNo to set
	 */
	public void setBuildingFloorNo(Integer buildingFloorNo) {
		this.buildingFloorNo = buildingFloorNo;
	}
	public AlarmEventDTO(Integer alarmEventId, String eventType,Date eventGeneratedTime, String isTestAlarm,String eventDetails,String buildingId,String isActive,String smsNotified,String emailNotified){
		this.alarmEventId=alarmEventId;
		this.eventType=eventType;
		this.eventGeneratedTime=eventGeneratedTime;
		this.isTestAlarm=isTestAlarm;
		this.eventDetails=eventDetails;
		this.buildingId=buildingId;
		this.isActive=isActive;
		this.smsNotified=smsNotified;
		this.emailNotified=emailNotified;
	}
	public AlarmEventDTO(){}

	public Integer getAlarmEventId() {
		return alarmEventId;
	}

	public void setAlarmEventId(Integer alarmEventId) {
		this.alarmEventId = alarmEventId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Date getEventGeneratedTime() {
		return eventGeneratedTime;
	}

	public void setEventGeneratedTime(Date eventGeneratedTime) {
		this.eventGeneratedTime = eventGeneratedTime;
	}

	public String getIsTestAlarm() {
		return isTestAlarm;
	}

	public void setIsTestAlarm(String isTestAlarm) {
		this.isTestAlarm = isTestAlarm;
	}

	public String getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(String eventDetails) {
		this.eventDetails = eventDetails;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getSmsNotified() {
		return smsNotified;
	}
	public void setSmsNotified(String smsNotified) {
		this.smsNotified = smsNotified;
	}
	public String getEmailNotified() {
		return emailNotified;
	}
	public void setEmailNotified(String emailNotified) {
		this.emailNotified = emailNotified;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	/**
	 * @return the bldgNoOfFloors
	 */
	public Integer getBldgNoOfFloors() {
		return bldgNoOfFloors;
	}
	/**
	 * @param bldgNoOfFloors the bldgNoOfFloors to set
	 */
	public void setBldgNoOfFloors(Integer bldgNoOfFloors) {
		this.bldgNoOfFloors = bldgNoOfFloors;
	}
	/**
	 * @return the bldgAddressFirstLine
	 */
	public String getBldgAddressFirstLine() {
		return bldgAddressFirstLine;
	}
	/**
	 * @param bldgAddressFirstLine the bldgAddressFirstLine to set
	 */
	public void setBldgAddressFirstLine(String bldgAddressFirstLine) {
		this.bldgAddressFirstLine = bldgAddressFirstLine;
	}
	/**
	 * @return the bldgAddressSecondLine
	 */
	public String getBldgAddressSecondLine() {
		return bldgAddressSecondLine;
	}
	/**
	 * @param bldgAddressSecondLine the bldgAddressSecondLine to set
	 */
	public void setBldgAddressSecondLine(String bldgAddressSecondLine) {
		this.bldgAddressSecondLine = bldgAddressSecondLine;
	}
	/**
	 * @return the bldgTown
	 */
	public String getBldgTown() {
		return bldgTown;
	}
	/**
	 * @param bldgTown the bldgTown to set
	 */
	public void setBldgTown(String bldgTown) {
		this.bldgTown = bldgTown;
	}
	/**
	 * @return the bldgCountry
	 */
	public String getBldgCountry() {
		return bldgCountry;
	}
	/**
	 * @param bldgCountry the bldgCountry to set
	 */
	public void setBldgCountry(String bldgCountry) {
		this.bldgCountry = bldgCountry;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	
	
	

}
