package com.vision.alarmmonitor.dto;

public class AlarmEventStatisticsDTO {
	
	private String customerName;
	private Integer customerId;
	private Integer noOfEvents;
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
	
	

}
