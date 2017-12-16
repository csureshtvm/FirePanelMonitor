package com.vision.fpservices.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vision.fpservices.db.model.User.UserStatus;


public class DeviceStatus {

	
	private int deviceStatusId;

	
	private DeviceStat status;

	public enum DeviceStat {
		STATUS0, STATUS1
	};

	
	private Date eventUpdateTime;

	
	private String description;

	

	/**
	 * @return the deviceStatusId
	 */
	public int getDeviceStatusId() {
		return deviceStatusId;
	}

	/**
	 * @param deviceStatusId
	 *            the deviceStatusId to set
	 */
	public void setDeviceStatusId(int deviceStatusId) {
		this.deviceStatusId = deviceStatusId;
	}

	/**
	 * @return the status
	 */
	public DeviceStat getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(DeviceStat status) {
		this.status = status;
	}

	/**
	 * @return the eventUpdateTime
	 */
	public Date getEventUpdateTime() {
		return eventUpdateTime;
	}

	/**
	 * @param eventUpdateTime
	 *            the eventUpdateTime to set
	 */
	public void setEventUpdateTime(Date eventUpdateTime) {
		this.eventUpdateTime = eventUpdateTime;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
}
