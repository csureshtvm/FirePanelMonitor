package com.vision.fpservices.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.vision.fpservices.db.model.Building;

public class AlarmDevicesDto {
	
	private String deviceId;
	private String deviceName;
	private String deviceLoc;
	private String installedFloorNo;
	private String lastMessageReceivedTime;
	private String buildingId;
	private String buildingName;
	private String customerId;
	private String customerName;
	private String communicationStatus;
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceLoc() {
		return deviceLoc;
	}
	public void setDeviceLoc(String deviceLoc) {
		this.deviceLoc = deviceLoc;
	}
	public String getInstalledFloorNo() {
		return installedFloorNo;
	}
	public void setInstalledFloorNo(String installedFloorNo) {
		this.installedFloorNo = installedFloorNo;
	}
	public String getLastMessageReceivedTime() {
		return lastMessageReceivedTime;
	}
	public void setLastMessageReceivedTime(String lastMessageReceivedTime) {
		this.lastMessageReceivedTime = lastMessageReceivedTime;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCommunicationStatus() {
		return communicationStatus;
	}
	public void setCommunicationStatus(String communicationStatus) {
		this.communicationStatus = communicationStatus;
	}
	
	
	
	

}
