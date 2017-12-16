package com.vision.fpservices.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alarm_notification_settings")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AlarmNotificationSettings {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "notification_setting_id")
	private Integer notificationSettingId;
	
	@Column(name = "notify_customer")
	private String notifyCustomer;
	
	@Column(name = "notify_building_contact")
	private String notifyBuildingContact;
	
	@Column(name = "notify_maintenance_contact")
	private String notifyMaintenanceContact;
	
	@Column(name = "notify_engg_contact")
	private String notifyEnggContact;
	
	@Column(name = "notify_security_contact")
	private String notifySecurityContact;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "notification_type")
	private String notificationType;
	
	
	 @ManyToOne
	 @JoinColumn(name="building_id")
	 private Building building;


	

	public Integer getNotificationSettingId() {
		return notificationSettingId;
	}


	public void setNotificationSettingId(Integer notificationSettingId) {
		this.notificationSettingId = notificationSettingId;
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


	public String getNotifySecurityContact() {
		return notifySecurityContact;
	}


	public void setNotifySecurityContact(String notifySecurityContact) {
		this.notifySecurityContact = notifySecurityContact;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Date getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}


	public String getNotificationType() {
		return notificationType;
	}


	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}


	public Building getBuilding() {
		return building;
	}


	public void setBuilding(Building building) {
		this.building = building;
	}
	 
	 
	 
}
