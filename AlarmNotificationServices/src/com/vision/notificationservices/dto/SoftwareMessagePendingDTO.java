package com.vision.notificationservices.dto;

import java.util.List;
import java.util.Map;

public class SoftwareMessagePendingDTO extends SoftwareMessageDTO {

	private String smsNotified;
	private String emailNotified;
	
	private String notfnType;
	private String notifyCustomer;
	private String notifyBuildingContact;
	private String notifyMaintenanceContact;
	private String notifyEnggContact;
	private String notifysecurityContact;
	private String buildingContactPhone;
	private String buildingContactEmail;
	private String enggContactPhone;
	private String enggContactEmail;
	private String securityContactPhone;
	private String securityContactEmail;
	private String maintenanceContactPhone;
	private String maintenanceContactEmail;
	private String customerContactPhone;
	private String customerContactEmail;

	String otherEmailContacts;
	String otherSMSContacts;
	
	
	/*private String skipNotifyCustomer;
	private String skipNotifyBldgContact;
	private String skipNotifyEnggContact;
	private String skipNotifyMaintContact;
	private String skipNotifySecContact;*/
	
	
	public String getSmsNotified() {
		return smsNotified;
	}
	public void setSmsNotified(String smsNotified) {
		this.smsNotified = smsNotified;
	}
	public String getEmailNotified() {
		return emailNotified;
	}
	public void setEmailNotified(String emailNotified) {
		this.emailNotified = emailNotified;
	}
	public String getNotfnType() {
		return notfnType;
	}
	public void setNotfnType(String notfnType) {
		this.notfnType = notfnType;
	}
	public String getNotifyCustomer() {
		return notifyCustomer;
	}
	public void setNotifyCustomer(String notifyCustomer) {
		this.notifyCustomer = notifyCustomer;
	}
	public String getNotifyBuildingContact() {
		return notifyBuildingContact;
	}
	public void setNotifyBuildingContact(String notifyBuildingContact) {
		this.notifyBuildingContact = notifyBuildingContact;
	}
	public String getNotifyMaintenanceContact() {
		return notifyMaintenanceContact;
	}
	public void setNotifyMaintenanceContact(String notifyMaintenanceContact) {
		this.notifyMaintenanceContact = notifyMaintenanceContact;
	}
	public String getNotifyEnggContact() {
		return notifyEnggContact;
	}
	public void setNotifyEnggContact(String notifyEnggContact) {
		this.notifyEnggContact = notifyEnggContact;
	}
	public String getNotifysecurityContact() {
		return notifysecurityContact;
	}
	public void setNotifysecurityContact(String notifysecurityContact) {
		this.notifysecurityContact = notifysecurityContact;
	}
	public String getBuildingContactPhone() {
		return buildingContactPhone;
	}
	public void setBuildingContactPhone(String buildingContactPhone) {
		this.buildingContactPhone = buildingContactPhone;
	}
	public String getBuildingContactEmail() {
		return buildingContactEmail;
	}
	public void setBuildingContactEmail(String buildingContactEmail) {
		this.buildingContactEmail = buildingContactEmail;
	}
	public String getEnggContactPhone() {
		return enggContactPhone;
	}
	public void setEnggContactPhone(String enggContactPhone) {
		this.enggContactPhone = enggContactPhone;
	}
	public String getEnggContactEmail() {
		return enggContactEmail;
	}
	public void setEnggContactEmail(String enggContactEmail) {
		this.enggContactEmail = enggContactEmail;
	}
	public String getSecurityContactPhone() {
		return securityContactPhone;
	}
	public void setSecurityContactPhone(String securityContactPhone) {
		this.securityContactPhone = securityContactPhone;
	}
	public String getSecurityContactEmail() {
		return securityContactEmail;
	}
	public void setSecurityContactEmail(String securityContactEmail) {
		this.securityContactEmail = securityContactEmail;
	}
	public String getMaintenanceContactPhone() {
		return maintenanceContactPhone;
	}
	public void setMaintenanceContactPhone(String maintenanceContactPhone) {
		this.maintenanceContactPhone = maintenanceContactPhone;
	}
	public String getMaintenanceContactEmail() {
		return maintenanceContactEmail;
	}
	public void setMaintenanceContactEmail(String maintenanceContactEmail) {
		this.maintenanceContactEmail = maintenanceContactEmail;
	}
	public String getCustomerContactPhone() {
		return customerContactPhone;
	}
	public void setCustomerContactPhone(String customerContactPhone) {
		this.customerContactPhone = customerContactPhone;
	}
	public String getCustomerContactEmail() {
		return customerContactEmail;
	}
	public void setCustomerContactEmail(String customerContactEmail) {
		this.customerContactEmail = customerContactEmail;
	}
	/*public String getSkipNotifyCustomer() {
		return skipNotifyCustomer;
	}
	public void setSkipNotifyCustomer(String skipNotifyCustomer) {
		this.skipNotifyCustomer = skipNotifyCustomer;
	}
	public String getSkipNotifyBldgContact() {
		return skipNotifyBldgContact;
	}
	public void setSkipNotifyBldgContact(String skipNotifyBldgContact) {
		this.skipNotifyBldgContact = skipNotifyBldgContact;
	}
	public String getSkipNotifyEnggContact() {
		return skipNotifyEnggContact;
	}
	public void setSkipNotifyEnggContact(String skipNotifyEnggContact) {
		this.skipNotifyEnggContact = skipNotifyEnggContact;
	}
	public String getSkipNotifyMaintContact() {
		return skipNotifyMaintContact;
	}
	public void setSkipNotifyMaintContact(String skipNotifyMaintContact) {
		this.skipNotifyMaintContact = skipNotifyMaintContact;
	}
	public String getSkipNotifySecContact() {
		return skipNotifySecContact;
	}
	public void setSkipNotifySecContact(String skipNotifySecContact) {
		this.skipNotifySecContact = skipNotifySecContact;
	}*/
	public String getOtherEmailContacts() {
		return otherEmailContacts;
	}
	public void setOtherEmailContacts(String otherEmailContacts) {
		this.otherEmailContacts = otherEmailContacts;
	}
	public String getOtherSMSContacts() {
		return otherSMSContacts;
	}
	public void setOtherSMSContacts(String otherSMSContacts) {
		this.otherSMSContacts = otherSMSContacts;
	}
	
	
}
