package com.vision.fpservices.db.model;

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

@Entity
@Table(name = "device_status")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class DeviceStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "device_status_id")
	private int deviceStatusId;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private DeviceStat status;

	public enum DeviceStat {
		STATUS0, STATUS1
	};

	@Column(name = "event_updated_time")
	private Date eventUpdateTime;

	@Column(name = "description")
	private String description;

	@OneToOne
	@JoinTable(name = "input_device_status", joinColumns = { @JoinColumn(name = "device_status_id") })
	private InputDevices inputDevice;

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

	/**
	 * @return the inputDevice
	 */
	public InputDevices getInputDevice() {
		return inputDevice;
	}

	/**
	 * @param inputDevice
	 *            the inputDevice to set
	 */
	public void setInputDevice(InputDevices inputDevice) {
		this.inputDevice = inputDevice;
	}

}
