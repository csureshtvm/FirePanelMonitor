package com.vision.fpservices.dto;

public class CustomerStatisticsDTO {

	private Integer customerId;
	private String customerName;
	private String customerAddress;
	private Integer buildings;
	private int noOfBuildings;
	public int getNoOfBuildings() {
		return noOfBuildings;
	}
	public void setNoOfBuildings(int noOfBuildings) {
		this.noOfBuildings = noOfBuildings;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getBuildings() {
		return buildings;
	}
	public void setBuildings(Integer buildings) {
		this.buildings = buildings;
	}
	
	
	
}
