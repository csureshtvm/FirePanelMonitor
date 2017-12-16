package com.vision.fpservices.dto;

import javax.persistence.Column;

public class OtherContactsDTO {
	private Integer otherContactId;
	private String objectType;
	private String description;
	
	private Integer customerId;
	private String emailId;
	private String mobileNo;
	
	
	public Integer getOtherContactId() {
		return otherContactId;
	}
	public void setOtherContactId(Integer otherContactId) {
		this.otherContactId = otherContactId;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	
	
	

}
