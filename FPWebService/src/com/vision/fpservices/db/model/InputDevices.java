package com.vision.fpservices.db.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vision.fpservices.db.model.DeviceStatus.DeviceStat;

@Entity
@Table(name = "input_devices")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class InputDevices implements Comparable<InputDevices> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "input_device_id")
	private int inputDeviceId;

	@Column(name = "device_name")
	private String deviceName;

	@Column(name = "installed_location")
	private String installedLocation;

	@Column(name = "device_desc")
	private String deviceDesc;

	@Column(name = "serial_no")
	private int serialNo;

	@Column(name = "status_value")
	private String statusValue;

	/**
	 * @return the serialNo
	 */
	public int getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "controller_id")
	private Controller controller;

	/*
	 * @OneToOne (mappedBy ="inputDevice")
	 * 
	 * @JoinTable(name = "input_device_status", joinColumns = { @JoinColumn(name
	 * = "device_status_id") }) private DeviceStatus deviceStatus;
	 */

	@Column(name = "status")
	private int status;

	@Column(name = "emailing_required")
	private Boolean emailingRequired;

	@Column(name = "sms_required")
	private Boolean smsRequired;

	public enum DeviceStat {
		STATUS0, STATUS1
	};

	@Column(name = "last_updated_time")
	private Timestamp lastUpdateTime;

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
	 * @return the lastUpdateTime
	 */
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return the controller
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * @param controller
	 *            the controller to set
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * @return the inputDeviceId
	 */
	public int getInputDeviceId() {
		return inputDeviceId;
	}

	/**
	 * @param inputDeviceId
	 *            the inputDeviceId to set
	 */
	public void setInputDeviceId(int inputDeviceId) {
		this.inputDeviceId = inputDeviceId;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName
	 *            the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the installedLocation
	 */
	public String getInstalledLocation() {
		return installedLocation;
	}

	/**
	 * @param installedLocation
	 *            the installedLocation to set
	 */
	public void setInstalledLocation(String installedLocation) {
		this.installedLocation = installedLocation;
	}

	/**
	 * @return the deviceDesc
	 */
	public String getDeviceDesc() {
		return deviceDesc;
	}

	/**
	 * @param deviceDesc
	 *            the deviceDesc to set
	 */
	public void setDeviceDesc(String deviceDesc) {
		this.deviceDesc = deviceDesc;
	}

	@Override
	public int compareTo(InputDevices inp) {
		System.out.println(this.serialNo + ":" + inp.serialNo);
		if (this.serialNo > inp.serialNo) {
			return 1;
		} else {
			return -1;
		}

	}

	public String toString() {
		return this.serialNo + ":" + this.deviceName;
	}

	/**
	 * @return the emailingRequired
	 */
	public Boolean isEmailingRequired() {
		return emailingRequired;
	}

	/**
	 * @param emailingRequired
	 *            the emailingRequired to set
	 */
	public void setEmailingRequired(Boolean emailingRequired) {
		this.emailingRequired = emailingRequired;
	}

	/**
	 * @return the smsRequired
	 */
	public Boolean isSmsRequired() {
		return smsRequired;
	}

	/**
	 * @param smsRequired
	 *            the smsRequired to set
	 */
	public void setSmsRequired(Boolean smsRequired) {
		this.smsRequired = smsRequired;
	}

	/**
	 * @return the statusValue
	 */
	public String getStatusValue() {
		return statusValue;
	}

	/**
	 * @param statusValue
	 *            the statusValue to set
	 */
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	/**
	 * @return the emailingRequired
	 */
	/*public Boolean getEmailingRequired() {
		return emailingRequired;
	}*/

	/**
	 * @return the smsRequired
	 */
	/*public Boolean getSmsRequired() {
		return smsRequired;
	}*/

	public com.vision.fpservices.model.InputDevices convertToInternal(){
		
		com.vision.fpservices.model.InputDevices inpDev=new com.vision.fpservices.model.InputDevices();
		inpDev.setDeviceDesc(deviceDesc);
		inpDev.setDeviceName(deviceName);
		inpDev.setEmailingRequired(emailingRequired);
		inpDev.setInputDeviceId(inputDeviceId);
		inpDev.setInstalledLocation(installedLocation);
		inpDev.setLastUpdateTime(lastUpdateTime);
		inpDev.setSerialNo(serialNo);
		inpDev.setSmsRequired(smsRequired);
		inpDev.setStatusActual(status);
		inpDev.setStatus(statusValue.split(":")[status]);
		inpDev.setStatusValue(statusValue);
		
		return inpDev;
	}
}
