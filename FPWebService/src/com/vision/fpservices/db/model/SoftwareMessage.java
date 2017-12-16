package com.vision.fpservices.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@Table(name = "software_message")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SoftwareMessage implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SOFTWARE_MESSAGE_ID")
	private Integer softwareMessageId;
	
	@Column(name = "building_id")
	private Integer buildingId;
	
	@Column(name = "object_id")
	private Integer objectId;
	
	@Column(name = "DEVICE_ID")
	private String deviceId;
	
	@Column(name = "OBJECT_TYPE")
	private String objectType;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "EVENT_VALUE")
	private String eventValue;
	

	@Column(name = "CREATED_BY")
	private String createdBy;
	

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	

	@Column(name = "CREATED_DTM")
	private Date createdDtm;
	

	@Column(name = "UPDATED_DTM")
	private Date updatedDtm;
	

	@Column(name = "sms_notified")
	private String smsNotified;
	@Column(name = "email_notified")
	private String emailNotified;


	public Integer getSoftwareMessageId() {
		return softwareMessageId;
	}


	public void setSoftwareMessageId(Integer softwareMessageId) {
		this.softwareMessageId = softwareMessageId;
	}


	public Integer getBuildingId() {
		return buildingId;
	}


	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}


	public Integer getObjectId() {
		return objectId;
	}


	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}


	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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


	public String getEventValue() {
		return eventValue;
	}


	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Date getCreatedDtm() {
		return createdDtm;
	}


	public void setCreatedDtm(Date createdDtm) {
		this.createdDtm = createdDtm;
	}


	public Date getUpdatedDtm() {
		return updatedDtm;
	}


	public void setUpdatedDtm(Date updatedDtm) {
		this.updatedDtm = updatedDtm;
	}


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
	
	
	
	
	
}
