package com.vision.fpservices.dto;

public class AlarmStatisticsDTO {
	private String customerName;
	private Integer customerId;
	private Integer noOfEvents;
	private String monthName;
	private String monthNameList;
	
	public AlarmStatisticsDTO(String customerName,Integer customerId,Integer noOfEvents,String monthName){
		this.customerName=customerName;
		this.customerId=customerId;
		this.noOfEvents=noOfEvents;
		this.monthName=monthName;
	}
	
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the noOfEvents
	 */
	public Integer getNoOfEvents() {
		return noOfEvents;
	}
	/**
	 * @param noOfEvents the noOfEvents to set
	 */
	public void setNoOfEvents(Integer noOfEvents) {
		this.noOfEvents = noOfEvents;
	}
	/**
	 * @return the monthName
	 */
	public String getMonthName() {
		return monthName;
	}
	/**
	 * @param monthName the monthName to set
	 */
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	/**
	 * @return the monthNameList
	 */
	public String getMonthNameList() {
		return monthNameList;
	}

	/**
	 * @param monthNameList the monthNameList to set
	 */
	public void setMonthNameList(String monthNameList) {
		this.monthNameList = monthNameList;
	}
	
	
	
}
