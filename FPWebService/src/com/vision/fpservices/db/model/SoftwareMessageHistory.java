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
@Table(name = "software_message_history")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SoftwareMessageHistory implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SOFTWARE_MESSAGE_HISTORY_ID")
	private Integer softwareMessageHistoryId;
	
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


	@Column(name = "CREATED_DTM")
	private Date createdDtm;


	public Integer getSoftwareMessageHistoryId() {
		return softwareMessageHistoryId;
	}


	public void setSoftwareMessageHistoryId(Integer softwareMessageHistoryId) {
		this.softwareMessageHistoryId = softwareMessageHistoryId;
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


	public Date getCreatedDtm() {
		return createdDtm;
	}


	public void setCreatedDtm(Date createdDtm) {
		this.createdDtm = createdDtm;
	}

	
}
