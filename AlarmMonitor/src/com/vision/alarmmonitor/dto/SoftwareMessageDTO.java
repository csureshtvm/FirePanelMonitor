package com.vision.alarmmonitor.dto;

import java.util.Date;

public class SoftwareMessageDTO {

	
	
	private Integer softwareMessageId;
	private Date eventUpdatedTime;
	private String eventDetails;
	private Integer buildingId;
	
	private Integer bldgNoOfFloors;
	private String bldgAddressFirstLine;
	private String bldgAddressSecondLine;
	private String bldgTown;
	private String bldgCountry;
	
	private Integer customerId;
	private String customerName;
	private String eventValue;
	

	private String buildingName;
	public Integer getSoftwareMessageId() {
		return softwareMessageId;
	}
	public void setSoftwareMessageId(Integer softwareMessageId) {
		this.softwareMessageId = softwareMessageId;		
	}
	public Date getEventUpdatedTime() {
		return eventUpdatedTime;
	}
	public void setEventUpdatedTime(Date eventUpdatedTime) {
		this.eventUpdatedTime = eventUpdatedTime;
	}
	public String getEventDetails() {
		return eventDetails;
	}
	public void setEventDetails(String eventDetails) {
		this.eventDetails = eventDetails;
	}
	public Integer getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}
	public Integer getBldgNoOfFloors() {
		return bldgNoOfFloors;
	}
	public void setBldgNoOfFloors(Integer bldgNoOfFloors) {
		this.bldgNoOfFloors = bldgNoOfFloors;
	}
	public String getBldgAddressFirstLine() {
		return bldgAddressFirstLine;
	}
	public void setBldgAddressFirstLine(String bldgAddressFirstLine) {
		this.bldgAddressFirstLine = bldgAddressFirstLine;
	}
	public String getBldgAddressSecondLine() {
		return bldgAddressSecondLine;
	}
	public void setBldgAddressSecondLine(String bldgAddressSecondLine) {
		this.bldgAddressSecondLine = bldgAddressSecondLine;
	}
	public String getBldgTown() {
		return bldgTown;
	}
	public void setBldgTown(String bldgTown) {
		this.bldgTown = bldgTown;
	}
	public String getBldgCountry() {
		return bldgCountry;
	}
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
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getEventValue() {
		return eventValue;
	}
	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}
	
	
	
	
}
