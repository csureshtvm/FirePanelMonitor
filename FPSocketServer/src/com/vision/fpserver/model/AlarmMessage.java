package com.vision.fpserver.model;

import java.util.Date;

public class AlarmMessage {

	private String message;
	private String type;
	private Date messageDate;
	private String floorNo;
	private String system;
	private String signal;
	private String value;
	
	private String customerId;
	private String buildingId;

	public AlarmMessage(String message, String type,String value, Date messageDate) {
		this.message = message;
		this.type = type;
		this.messageDate = messageDate;
		
		this.system=type;
		this.signal=message;
		this.value=value;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the error
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String type) {
		this.type = type;
	}

	/**
	 * @return the messageDate
	 */
	public Date getMessageDate() {
		return messageDate;
	}

	/**
	 * @param messageDate
	 *            the messageDate to set
	 */
	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
