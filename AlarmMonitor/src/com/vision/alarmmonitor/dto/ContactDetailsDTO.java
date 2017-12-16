package com.vision.alarmmonitor.dto;

public class ContactDetailsDTO {

	private Integer contactId;
	private String contactName;
	private String contactType;
	private String phonePrimary;
	private String phoneSecondary;
	private String fax;
	private String email;
	
	public ContactDetailsDTO(Integer contactId,String contactName,String contactType,String phonePrimary,String phoneSecondary,String fax, String email ){
		
	this.contactId=contactId;
	this.contactName=contactName;
	this.contactType=contactType;
	this.phonePrimary=phonePrimary;
	this.phoneSecondary=phoneSecondary;
	this.fax=fax;
	this.email=email;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getPhonePrimary() {
		return phonePrimary;
	}
	public void setPhonePrimary(String phonePrimary) {
		this.phonePrimary = phonePrimary;
	}
	public String getPhoneSecondary() {
		return phoneSecondary;
	}
	public void setPhoneSecondary(String phoneSecondary) {
		this.phoneSecondary = phoneSecondary;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
