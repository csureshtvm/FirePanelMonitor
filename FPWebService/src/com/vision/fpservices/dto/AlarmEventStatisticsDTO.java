package com.vision.fpservices.dto;

import java.util.Date;

public class AlarmEventStatisticsDTO {
	
	private String customerName;
	private Integer customerId;
	private Integer noOfEvents;
	private String eventType;
	private Date eventGeneratedTime;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getNoOfEvents() {
		return noOfEvents;
	}
	public void setNoOfEvents(Integer noOfEvents) {
		this.noOfEvents = noOfEvents;
	}
	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}
	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	/**
	 * @return the eventGeneratedTime
	 */
	public Date getEventGeneratedTime() {
		return eventGeneratedTime;
	}
	/**
	 * @param eventGeneratedTime the eventGeneratedTime to set
	 */
	public void setEventGeneratedTime(Date eventGeneratedTime) {
		this.eventGeneratedTime = eventGeneratedTime;
	}
	
	

}
