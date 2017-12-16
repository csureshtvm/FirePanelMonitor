package com.vision.fpservices.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "alarm_devices")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AlarmDevices implements Serializable{
	@Id
	@Column(name = "device_id")
	private String deviceId;
	@Column(name = "device_name")
	private String deviceName;
	@Column(name = "device_loc")
	private String deviceLoc;
	@Column(name = "installed_floor_no")
	private String installedFloorNo;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "last_message_received_time")
	private Date lastMessageReceivedTime;
	@ManyToOne
	@JoinColumn(name = "building_id")
	private Building building;
	
	
	 @OneToMany(mappedBy="alarmDevice")
	 private Set<AlarmDeviceReset> alarmDeviceReset;
	 
	 @OneToMany(mappedBy="alarmDevice")
	 private Set<AlarmEvents> alarmEvents;
	
	@Column(name = "is_active")
	private String isActive;
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
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Date getLastMessageReceivedTime() {
		return lastMessageReceivedTime;
	}
	public void setLastMessageReceivedTime(Date lastMessageReceivedTime) {
		this.lastMessageReceivedTime = lastMessageReceivedTime;
	}
	public Building getBuilding() {
		return building;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Set<AlarmDeviceReset> getAlarmDeviceReset() {
		return alarmDeviceReset;
	}
	public void setAlarmDeviceReset(Set<AlarmDeviceReset> alarmDeviceReset) {
		this.alarmDeviceReset = alarmDeviceReset;
	}
	public Set<AlarmEvents> getAlarmEvents() {
		return alarmEvents;
	}
	public void setAlarmEvents(Set<AlarmEvents> alarmEvents) {
		this.alarmEvents = alarmEvents;
	}
	
	
	
	
	
}
