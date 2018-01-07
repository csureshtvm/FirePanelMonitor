package com.vision.fpservices.dto;

import java.util.Date;

import javax.persistence.Column;

public class AlarmMessagesDTO {
	
	private Integer alarmMessageId;
	private Integer customerId;
	private Integer buildingId;
	private String customerName;
	private String buildingName;
	private String bldgAddressFirstLine;

	private String bldgAddressSecondLine;

	private String bldgTown;
	private String bldgCountry;
	private String deviceId;
	private String messageDetails;
	private Date createdTime;
	private Date updatedTime;
	public Integer getAlarmMessageId() {
		return alarmMessageId;
	}
	public void setAlarmMessageId(Integer alarmMessageId) {
		this.alarmMessageId = alarmMessageId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
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
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getMessageDetails() {
		return messageDetails;
	}
	public void setMessageDetails(String messageDetails) {
		this.messageDetails = messageDetails;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
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

	
	

}
