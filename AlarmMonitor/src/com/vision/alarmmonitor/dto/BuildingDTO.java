package com.vision.alarmmonitor.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;



/**
 * @author as
 *
 */

public class BuildingDTO {//implements Comparable<Building>{

	
	
	private String buildingId;
	private String buildingName;
	private String buildingLocation;
	
	private String description;
	
	private String buildingTpe;
	
	private String marakaniNo;
	
	private Integer noOfFloor;
	
	private String nearPoliceStation;
	
	private String nearCivilDefence;
	
	private String nearHospital;
	
	//private String createdBy;
	
	//private String createdUserId;
	
	//private String updatedBy;
	
	//private String updatedUserId;
	
	//private String deletedBy;
	
	//private String deletedUserId;
	
	//private Date createdDate;
	
	//private Date updatedDate;
	
	//private Date deletedDate;
	
	private String isActive;
	
	private String buildingImageUrl;
	
	private String addressId;
	private String addressType;
	private String addressFirstLine;
	private String addressSecondLine;
	private String town;
	private String county;
	private String country;
	private String postalCode;
	
	
	private Integer contactId;
	private String contactName;
	private String contactType;
	private String phonePrimary;
	private String phoneSecondary;
	private String fax;
	private String email;
	
	
	private Integer maintenanceContactId;
	private String maintenanceContactName;
	private String maintenanceContactType;
	private String maintenancePhonePrimary;
	private String maintenancePhoneSecondary;
	private String maintenanceFax;
	private String maintenanceEmail;	
	
	private Integer enggContactId;
	private String enggContactName;
	private String enggContactType;
	private String enggPhonePrimary;
	private String enggPhoneSecondary;
	private String enggFax;
	private String enggEmail;
	
	private Integer securityContactId;
	private String securityContactName;
	private String securityContactType;
	private String securityPhonePrimary;
	private String securityPhoneSecondary;
	private String securityFax;
	private String securityEmail;
	
	
	
	
	private String notificationSettingSMSId;
	private String notifyCustomerSMS;
	private String notifyBuildingContactSMS;
	private String notifyMaintenanceContactSMS;
	private String notifyEnggContactSMS;
	private String notifySecurityContactSMS;
	
	private String notificationSettingEmailId;
	private String notifyCustomerEmail;
	private String notifyBuildingContactEmail;
	private String notifyMaintenanceContactEmail;
	private String notifyEnggContactEmail;
	private String notifySecurityContactEmail;

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getBuildingLocation() {
		return buildingLocation;
	}

	public void setBuildingLocation(String buildingLocation) {
		this.buildingLocation = buildingLocation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBuildingTpe() {
		return buildingTpe;
	}

	public void setBuildingTpe(String buildingTpe) {
		this.buildingTpe = buildingTpe;
	}

	public String getMarakaniNo() {
		return marakaniNo;
	}

	public void setMarakaniNo(String marakaniNo) {
		this.marakaniNo = marakaniNo;
	}

	public Integer getNoOfFloor() {
		return noOfFloor;
	}

	public void setNoOfFloor(Integer noOfFloor) {
		this.noOfFloor = noOfFloor;
	}

	public String getNearPoliceStation() {
		return nearPoliceStation;
	}

	public void setNearPoliceStation(String nearPoliceStation) {
		this.nearPoliceStation = nearPoliceStation;
	}

	public String getNearCivilDefence() {
		return nearCivilDefence;
	}

	public void setNearCivilDefence(String nearCivilDefence) {
		this.nearCivilDefence = nearCivilDefence;
	}

	public String getNearHospital() {
		return nearHospital;
	}

	public void setNearHospital(String nearHospital) {
		this.nearHospital = nearHospital;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getBuildingImageUrl() {
		return buildingImageUrl;
	}

	public void setBuildingImageUrl(String buildingImageUrl) {
		this.buildingImageUrl = buildingImageUrl;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddressFirstLine() {
		return addressFirstLine;
	}

	public void setAddressFirstLine(String addressFirstLine) {
		this.addressFirstLine = addressFirstLine;
	}

	public String getAddressSecondLine() {
		return addressSecondLine;
	}

	public void setAddressSecondLine(String addressSecondLine) {
		this.addressSecondLine = addressSecondLine;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	public Integer getMaintenanceContactId() {
		return maintenanceContactId;
	}

	public void setMaintenanceContactId(Integer maintenanceContactId) {
		this.maintenanceContactId = maintenanceContactId;
	}

	public String getMaintenanceContactName() {
		return maintenanceContactName;
	}

	public void setMaintenanceContactName(String maintenanceContactName) {
		this.maintenanceContactName = maintenanceContactName;
	}

	public String getMaintenanceContactType() {
		return maintenanceContactType;
	}

	public void setMaintenanceContactType(String maintenanceContactType) {
		this.maintenanceContactType = maintenanceContactType;
	}

	public String getMaintenancePhonePrimary() {
		return maintenancePhonePrimary;
	}

	public void setMaintenancePhonePrimary(String maintenancePhonePrimary) {
		this.maintenancePhonePrimary = maintenancePhonePrimary;
	}

	public String getMaintenancePhoneSecondary() {
		return maintenancePhoneSecondary;
	}

	public void setMaintenancePhoneSecondary(String maintenancePhoneSecondary) {
		this.maintenancePhoneSecondary = maintenancePhoneSecondary;
	}

	public String getMaintenanceFax() {
		return maintenanceFax;
	}

	public void setMaintenanceFax(String maintenanceFax) {
		this.maintenanceFax = maintenanceFax;
	}

	public String getMaintenanceEmail() {
		return maintenanceEmail;
	}

	public void setMaintenanceEmail(String maintenanceEmail) {
		this.maintenanceEmail = maintenanceEmail;
	}

	public Integer getEnggContactId() {
		return enggContactId;
	}

	public void setEnggContactId(Integer enggContactId) {
		this.enggContactId = enggContactId;
	}

	public String getEnggContactName() {
		return enggContactName;
	}

	public void setEnggContactName(String enggContactName) {
		this.enggContactName = enggContactName;
	}

	public String getEnggContactType() {
		return enggContactType;
	}

	public void setEnggContactType(String enggContactType) {
		this.enggContactType = enggContactType;
	}

	public String getEnggPhonePrimary() {
		return enggPhonePrimary;
	}

	public void setEnggPhonePrimary(String enggPhonePrimary) {
		this.enggPhonePrimary = enggPhonePrimary;
	}

	public String getEnggPhoneSecondary() {
		return enggPhoneSecondary;
	}

	public void setEnggPhoneSecondary(String enggPhoneSecondary) {
		this.enggPhoneSecondary = enggPhoneSecondary;
	}

	public String getEnggFax() {
		return enggFax;
	}

	public void setEnggFax(String enggFax) {
		this.enggFax = enggFax;
	}

	public String getEnggEmail() {
		return enggEmail;
	}

	public void setEnggEmail(String enggEmail) {
		this.enggEmail = enggEmail;
	}

	public Integer getSecurityContactId() {
		return securityContactId;
	}

	public void setSecurityContactId(Integer securityContactId) {
		this.securityContactId = securityContactId;
	}

	public String getSecurityContactName() {
		return securityContactName;
	}

	public void setSecurityContactName(String securityContactName) {
		this.securityContactName = securityContactName;
	}

	public String getSecurityContactType() {
		return securityContactType;
	}

	public void setSecurityContactType(String securityContactType) {
		this.securityContactType = securityContactType;
	}

	public String getSecurityPhonePrimary() {
		return securityPhonePrimary;
	}

	public void setSecurityPhonePrimary(String securityPhonePrimary) {
		this.securityPhonePrimary = securityPhonePrimary;
	}

	public String getSecurityPhoneSecondary() {
		return securityPhoneSecondary;
	}

	public void setSecurityPhoneSecondary(String securityPhoneSecondary) {
		this.securityPhoneSecondary = securityPhoneSecondary;
	}

	public String getSecurityFax() {
		return securityFax;
	}

	public void setSecurityFax(String securityFax) {
		this.securityFax = securityFax;
	}

	public String getSecurityEmail() {
		return securityEmail;
	}

	public void setSecurityEmail(String securityEmail) {
		this.securityEmail = securityEmail;
	}

	public String getNotificationSettingSMSId() {
		return notificationSettingSMSId;
	}

	public void setNotificationSettingSMSId(String notificationSettingSMSId) {
		this.notificationSettingSMSId = notificationSettingSMSId;
	}

	public String getNotifyCustomerSMS() {
		return notifyCustomerSMS;
	}

	public void setNotifyCustomerSMS(String notifyCustomerSMS) {
		this.notifyCustomerSMS = notifyCustomerSMS;
	}

	public String getNotifyBuildingContactSMS() {
		return notifyBuildingContactSMS;
	}

	public void setNotifyBuildingContactSMS(String notifyBuildingContactSMS) {
		this.notifyBuildingContactSMS = notifyBuildingContactSMS;
	}

	public String getNotifyMaintenanceContactSMS() {
		return notifyMaintenanceContactSMS;
	}

	public void setNotifyMaintenanceContactSMS(String notifyMaintenanceContactSMS) {
		this.notifyMaintenanceContactSMS = notifyMaintenanceContactSMS;
	}

	public String getNotifyEnggContactSMS() {
		return notifyEnggContactSMS;
	}

	public void setNotifyEnggContactSMS(String notifyEnggContactSMS) {
		this.notifyEnggContactSMS = notifyEnggContactSMS;
	}

	public String getNotifySecurityContactSMS() {
		return notifySecurityContactSMS;
	}

	public void setNotifySecurityContactSMS(String notifySecurityContactSMS) {
		this.notifySecurityContactSMS = notifySecurityContactSMS;
	}

	public String getNotificationSettingEmailId() {
		return notificationSettingEmailId;
	}

	public void setNotificationSettingEmailId(String notificationSettingEmailId) {
		this.notificationSettingEmailId = notificationSettingEmailId;
	}

	public String getNotifyCustomerEmail() {
		return notifyCustomerEmail;
	}

	public void setNotifyCustomerEmail(String notifyCustomerEmail) {
		this.notifyCustomerEmail = notifyCustomerEmail;
	}

	public String getNotifyBuildingContactEmail() {
		return notifyBuildingContactEmail;
	}

	public void setNotifyBuildingContactEmail(String notifyBuildingContactEmail) {
		this.notifyBuildingContactEmail = notifyBuildingContactEmail;
	}

	public String getNotifyMaintenanceContactEmail() {
		return notifyMaintenanceContactEmail;
	}

	public void setNotifyMaintenanceContactEmail(
			String notifyMaintenanceContactEmail) {
		this.notifyMaintenanceContactEmail = notifyMaintenanceContactEmail;
	}

	public String getNotifyEnggContactEmail() {
		return notifyEnggContactEmail;
	}

	public void setNotifyEnggContactEmail(String notifyEnggContactEmail) {
		this.notifyEnggContactEmail = notifyEnggContactEmail;
	}

	public String getNotifySecurityContactEmail() {
		return notifySecurityContactEmail;
	}

	public void setNotifySecurityContactEmail(String notifySecurityContactEmail) {
		this.notifySecurityContactEmail = notifySecurityContactEmail;
	}
	
	
	
	/*@Override
	public int compareTo(Building b) {
		if(this.buildingId > b.buildingId){
		return 1;
		}else{
			return -1;
		}
		
	}*/
	
	
}
