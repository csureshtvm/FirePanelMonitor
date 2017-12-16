package com.vision.fpservices.db.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.vision.fpservices.db.model.InputDevices.DeviceStat;

@Entity
@Table(name = "device_status_history")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class DeviceStatusHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "status_history_id")
	private int statusHistoryId;

	@Column(name = "device_id")
	private int deviceId;

	@Column(name = "status")
	private int status;

	@Column(name = "event_generated_time")
	private Timestamp eventGeneratedTime;

	@Column(name = "event_updated_time")
	private Timestamp eventUpdatedTime;

	/**
	 * @return the statusHistoryId
	 */
	public int getStatusHistoryId() {
		return statusHistoryId;
	}

	/**
	 * @param statusHistoryId
	 *            the statusHistoryId to set
	 */
	public void setStatusHistoryId(int statusHistoryId) {
		this.statusHistoryId = statusHistoryId;
	}

	/**
	 * @return the deviceId
	 */
	public int getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the eventGeneratedTime
	 */
	public Timestamp getEventGeneratedTime() {
		return eventGeneratedTime;
	}

	/**
	 * @param eventGeneratedTime
	 *            the eventGeneratedTime to set
	 */
	public void setEventGeneratedTime(Timestamp eventGeneratedTime) {
		this.eventGeneratedTime = eventGeneratedTime;
	}

	/**
	 * @return the eventUpdatedTime
	 */
	public Timestamp getEventUpdatedTime() {
		return eventUpdatedTime;
	}

	/**
	 * @param eventUpdatedTime
	 *            the eventUpdatedTime to set
	 */
	public void setEventUpdatedTime(Timestamp eventUpdatedTime) {
		this.eventUpdatedTime = eventUpdatedTime;
	}
	
	public String toString(){
		return this.getDeviceId()+":"+this.getEventGeneratedTime()+":"+this.getStatus();
	}
	
	public com.vision.fpservices.model.DeviceStatusHistory convertToInternal(){
		com.vision.fpservices.model.DeviceStatusHistory devHistory=new com.vision.fpservices.model.DeviceStatusHistory();
		devHistory.setDeviceId(deviceId);
		devHistory.setEventGeneratedTime(eventGeneratedTime);
		devHistory.setEventUpdatedTime(eventUpdatedTime);
		devHistory.setStatus(status);
		devHistory.setStatusHistoryId(statusHistoryId);
		return devHistory;
	}

}
