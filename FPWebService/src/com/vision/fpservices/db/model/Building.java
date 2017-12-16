package com.vision.fpservices.db.model;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author as
 *
 */
@Entity
@Table(name = "building_details")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Building implements Comparable<Building>{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "building_id")
	private Integer buildingId;
	@Column(name = "building_name") 
	private String buildingName;
	@Column(name = "building_location")
	private String buildingLocation;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "building_type")
	private String buildingTpe;
	
	@Column(name = "marakani_no")
	private String marakaniNo;
	
	@Column(name = "no_of_floor")
	private Integer noOfFloor;
	
	@Column(name = "near_police_station")
	private String nearPoliceStation;
	
	@Column(name = "near_civil_defence")
	private String nearCivilDefence;
	
	@Column(name = "near_hospital")
	private String nearHospital;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_user_id")
	private String createdUserId;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated_user_id")
	private String updatedUserId;
	
	@Column(name = "deleted_by")
	private String deletedBy;
	
	@Column(name = "deleted_user_id")
	private String deletedUserId;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "deleted_date")
	private Date deletedDate;
	
	@Column(name = "is_active")
	private String isActive;
	
	@Column(name = "building_image_url")
	private String buildingImageUrl;
	
	@Column(name = "gps_cordinate")
	private String gpsCordinate;

	 @ManyToOne
	 @JoinColumn(name="customer_id")
	 private CustomerDetails customerDetails;
	 
	 @OneToOne
	 @JoinColumn(name="building_address_id")
	 private AddressDetails addressDetails;
	 
	 
	 @OneToOne
	 @JoinColumn(name="maintenance_contact_id")
	 private ContactDetails maintenanceContactDetails;
	 
	 @OneToOne
	 @JoinColumn(name="engineering_contact_id")
	 private ContactDetails enggContactDetails;
	 
	 @OneToOne
	 @JoinColumn(name="security_contact_id")
	 private ContactDetails securityContactDetails;
	 
	 @OneToMany(mappedBy="building")
	 private Set<AlarmNotificationSettings> alarmNotificationSettings;
	 
	 @OneToMany(mappedBy="building")
	 private Set<AlarmTestSchedule> alarmTestSchedules;
	 
	 @OneToMany(mappedBy="building")
	 private Set<AlarmEvents> alarmEvents;
	 
	 @OneToOne
	 @JoinColumn(name="building_contact_id")
	 private ContactDetails buildingContactDetails;
	
	 
	 @OneToMany(mappedBy="building")
	 private Set<AlarmDevices> alarmDevices;
	 
	@Override
	public int compareTo(Building o) {
		// TODO Auto-generated method stub
		return 0;
	}



	public Integer getBuildingId() {
		return buildingId;
	}



	public void setBuildingId(Integer buildingId) {
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



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getCreatedUserId() {
		return createdUserId;
	}



	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}



	public String getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}



	public String getUpdatedUserId() {
		return updatedUserId;
	}



	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}



	public String getDeletedBy() {
		return deletedBy;
	}



	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}



	public String getDeletedUserId() {
		return deletedUserId;
	}



	public void setDeletedUserId(String deletedUserId) {
		this.deletedUserId = deletedUserId;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}



	public Date getUpdatedDate() {
		return updatedDate;
	}



	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}



	public Date getDeletedDate() {
		return deletedDate;
	}



	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
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



	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}



	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}



	public AddressDetails getAddressDetails() {
		return addressDetails;
	}



	public void setAddressDetails(AddressDetails addressDetails) {
		this.addressDetails = addressDetails;
	}



	public ContactDetails getMaintenanceContactDetails() {
		return maintenanceContactDetails;
	}



	public void setMaintenanceContactDetails(
			ContactDetails maintenanceContactDetails) {
		this.maintenanceContactDetails = maintenanceContactDetails;
	}



	public ContactDetails getEnggContactDetails() {
		return enggContactDetails;
	}



	public void setEnggContactDetails(ContactDetails enggContactDetails) {
		this.enggContactDetails = enggContactDetails;
	}



	public ContactDetails getSecurityContactDetails() {
		return securityContactDetails;
	}



	public void setSecurityContactDetails(ContactDetails securityContactDetails) {
		this.securityContactDetails = securityContactDetails;
	}



	public Set<AlarmNotificationSettings> getAlarmNotificationSettings() {
		return alarmNotificationSettings;
	}



	public void setAlarmNotificationSettings(
			Set<AlarmNotificationSettings> alarmNotificationSettings) {
		this.alarmNotificationSettings = alarmNotificationSettings;
	}



	public Set<AlarmTestSchedule> getAlarmTestSchedules() {
		return alarmTestSchedules;
	}



	public void setAlarmTestSchedules(Set<AlarmTestSchedule> alarmTestSchedules) {
		this.alarmTestSchedules = alarmTestSchedules;
	}
	
	public ContactDetails getBuildingContactDetails() {
		return buildingContactDetails;
	}



	public void setBuildingContactDetails(ContactDetails buildingContactDetails) {
		this.buildingContactDetails = buildingContactDetails;
	}



	public com.vision.fpservices.dto.BuildingDTO convertToInternal(){
		com.vision.fpservices.dto.BuildingDTO bldg=new com.vision.fpservices.dto.BuildingDTO();
		bldg.setBuildingId(String.valueOf(buildingId));
		bldg.setBuildingImageUrl(buildingImageUrl);
		bldg.setBuildingLocation(buildingLocation);
		bldg.setBuildingName(buildingName);
		bldg.setBuildingTpe(buildingTpe);
		bldg.setDescription(description);
		bldg.setIsActive(isActive);
		bldg.setMarakaniNo(marakaniNo);
		bldg.setNearCivilDefence(nearCivilDefence);
		bldg.setNearPoliceStation(nearPoliceStation);
		bldg.setNearHospital(nearHospital);
		bldg.setNoOfFloor(noOfFloor);
		bldg.setBuildingImageUrl(buildingImageUrl);
		
		
		if(addressDetails!=null){
			bldg.setAddressId(addressDetails.getAddressId()!=null?addressDetails.getAddressId()+"":null);
			bldg.setAddressType(addressDetails.getAddressType());
			bldg.setAddressFirstLine(addressDetails.getAddressFirstLine());
			bldg.setAddressSecondLine(addressDetails.getAddressSecondLine());
			bldg.setTown(addressDetails.getTown());
			bldg.setCounty(addressDetails.getCounty());
			bldg.setCountry(addressDetails.getCountry());
			bldg.setPostalCode(addressDetails.getPostalCode());
		}
		if(buildingContactDetails!=null){		
			
			bldg.setContactId(buildingContactDetails.getContactId());
			bldg.setContactName(buildingContactDetails.getContactName());
			bldg.setContactType(buildingContactDetails.getType());
			bldg.setPhonePrimary(buildingContactDetails.getPhonePrimary());
			bldg.setPhoneSecondary(buildingContactDetails.getPhoneSecondary());
			bldg.setFax(buildingContactDetails.getFax());
			bldg.setEmail(buildingContactDetails.getEmail());
		}
		
		if(maintenanceContactDetails!=null){		
			
			bldg.setMaintenanceContactId(maintenanceContactDetails.getContactId());
			bldg.setMaintenanceContactName(maintenanceContactDetails.getContactName());
			bldg.setMaintenanceContactType(maintenanceContactDetails.getType());
			bldg.setMaintenancePhonePrimary(maintenanceContactDetails.getPhonePrimary());
			bldg.setMaintenancePhoneSecondary(maintenanceContactDetails.getPhoneSecondary());
			bldg.setMaintenanceFax(maintenanceContactDetails.getFax());
			bldg.setMaintenanceEmail(maintenanceContactDetails.getEmail());
		}
		
		
		if(securityContactDetails!=null){		
			
			bldg.setSecurityContactId(securityContactDetails.getContactId());
			bldg.setSecurityContactName(securityContactDetails.getContactName());
			bldg.setSecurityContactType(securityContactDetails.getType());
			bldg.setSecurityPhonePrimary(securityContactDetails.getPhonePrimary());
			bldg.setSecurityPhoneSecondary(securityContactDetails.getPhoneSecondary());
			bldg.setSecurityFax(securityContactDetails.getFax());
			bldg.setSecurityEmail(securityContactDetails.getEmail());
		}
		
		if(enggContactDetails!=null){		
			
			bldg.setEnggContactId(enggContactDetails.getContactId());
			bldg.setEnggContactName(enggContactDetails.getContactName());
			bldg.setEnggContactType(enggContactDetails.getType());
			bldg.setEnggPhonePrimary(enggContactDetails.getPhonePrimary());
			bldg.setEnggPhoneSecondary(enggContactDetails.getPhoneSecondary());
			bldg.setEnggFax(enggContactDetails.getFax());
			bldg.setEnggEmail(enggContactDetails.getEmail());
		}
		
		if(alarmNotificationSettings!=null){
			Iterator<AlarmNotificationSettings> iter=alarmNotificationSettings.iterator();
			while(iter.hasNext()){
				AlarmNotificationSettings alarmNotificSettings=iter.next();
				if(alarmNotificSettings!=null && alarmNotificSettings.getNotificationType()!=null && "SMS".equalsIgnoreCase( alarmNotificSettings.getNotificationType())){
					bldg.setNotificationSettingSMSId(alarmNotificSettings.getNotificationSettingId()!=null?alarmNotificSettings.getNotificationSettingId()+"":null);
					bldg.setNotifyCustomerSMS(alarmNotificSettings.getNotifyCustomer());
					bldg.setNotifyBuildingContactSMS(alarmNotificSettings.getNotifyBuildingContact());
					bldg.setNotifyMaintenanceContactSMS(alarmNotificSettings.getNotifyMaintenanceContact());
					bldg.setNotifyEnggContactSMS(alarmNotificSettings.getNotifyEnggContact());
					bldg.setNotifySecurityContactSMS(alarmNotificSettings.getNotifySecurityContact());
					
				}else if(alarmNotificSettings!=null && alarmNotificSettings.getNotificationType()!=null && "EMAIL".equalsIgnoreCase( alarmNotificSettings.getNotificationType())){

					bldg.setNotificationSettingEmailId(alarmNotificSettings.getNotificationSettingId()!=null?alarmNotificSettings.getNotificationSettingId()+"":null);
					bldg.setNotifyCustomerEmail(alarmNotificSettings.getNotifyCustomer());
					bldg.setNotifyBuildingContactEmail(alarmNotificSettings.getNotifyBuildingContact());
					bldg.setNotifyMaintenanceContactEmail(alarmNotificSettings.getNotifyMaintenanceContact());
					bldg.setNotifyEnggContactEmail(alarmNotificSettings.getNotifyEnggContact());
					bldg.setNotifySecurityContactEmail(alarmNotificSettings.getNotifySecurityContact());
					
				}
			}
		}
		
		return bldg;
	}



	public Set<AlarmEvents> getAlarmEvents() {
		return alarmEvents;
	}



	public void setAlarmEvents(Set<AlarmEvents> alarmEvents) {
		this.alarmEvents = alarmEvents;
	}



	public Set<AlarmDevices> getAlarmDevices() {
		return alarmDevices;
	}



	public void setAlarmDevices(Set<AlarmDevices> alarmDevices) {
		this.alarmDevices = alarmDevices;
	}



	public String getGpsCordinate() {
		return gpsCordinate;
	}



	public void setGpsCordinate(String gpsCordinate) {
		this.gpsCordinate = gpsCordinate;
	}
	
	
	
}
