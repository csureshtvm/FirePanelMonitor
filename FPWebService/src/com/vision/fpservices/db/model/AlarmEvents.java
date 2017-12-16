package com.vision.fpservices.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vision.fpservices.dto.AlarmEventDTO;

@Entity
@Table(name = "alarm_events")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AlarmEvents implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "alarm_event_id")
	private Integer alarmEventId;
	
	@Column(name = "event_type")
	private String eventType;
	
	@Column(name = "event_generated_time")
	private Date eventGeneratedTime;
	
	@Column(name = "is_test_alarm")
	private String isTestAlarm;
	
	@Column(name = "is_active")
	private String isActive;
	@Column(name = "sms_notified")
	private String smsNotified;
	@Column(name = "email_notified")
	private String emailNotified;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "building_floor_no")
	private int buildingFloorNo;
	@Column(name = "event_details")
	private String eventDetails;
	
	@Column(name = "event_system")
	private String eventSystem;
	
	@Column(name = "event_signal")
	private String eventSignal;
	
	@Column(name = "event_value")
	private String eventValue;
	
	@Column(name = "event_device_type")
	private String eventDeviceType;
	
	@Column(name = "event_status")
	private String eventStatus;
	
	@Column(name = "reset_comments")
	private String resetComments;
	
	@Column(name = "reset_requested_by")
	private String resetRequestedBy;
	
	@Column(name = "reset_requested_dtm")
	private Date resetRequestedDtm;
	
	
	@ManyToOne
	@JoinColumn(name = "building_id")
	private Building building;
	
	 @OneToMany(mappedBy="alarmEvent")
	 private Set<AlarmNotfnHistory> alarmNotfnHstory;
	 
	 
	 @ManyToOne
	 @JoinColumn(name = "device_id")
	 private AlarmDevices alarmDevice;
	 
	 @OneToOne(mappedBy="alarmEvent")
	 private AlarmDeviceReset alarmDeviceReset;

	public Integer getAlarmEventId() {
		return alarmEventId;
	}

	public void setAlarmEventId(Integer alarmEventId) {
		this.alarmEventId = alarmEventId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Date getEventGeneratedTime() {
		return eventGeneratedTime;
	}

	public void setEventGeneratedTime(Date eventGeneratedTime) {
		this.eventGeneratedTime = eventGeneratedTime;
	}

	public String getIsTestAlarm() {
		return isTestAlarm;
	}

	public void setIsTestAlarm(String isTestAlarm) {
		this.isTestAlarm = isTestAlarm;
	}

	public String getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(String eventDetails) {
		this.eventDetails = eventDetails;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
	
	
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public AlarmEventDTO convertToAlarmEventDTO(){
		
		return new AlarmEventDTO(alarmEventId, eventType, eventGeneratedTime, isTestAlarm, eventDetails, String.valueOf(building.getBuildingId()),smsNotified,emailNotified,isActive);
	}
	public String toString(){
		return this.getAlarmEventId()+":"+this.getEventType()+","+this.getEventDetails()+","+this.getIsTestAlarm();
	}

	public Set<AlarmNotfnHistory> getAlarmNotfnHstory() {
		return alarmNotfnHstory;
	}

	public void setAlarmNotfnHstory(Set<AlarmNotfnHistory> alarmNotfnHstory) {
		this.alarmNotfnHstory = alarmNotfnHstory;
	}

	/**
	 * @return the buildingFloorNo
	 */
	public int getBuildingFloorNo() {
		return buildingFloorNo;
	}

	/**
	 * @param buildingFloorNo the buildingFloorNo to set
	 */
	public void setBuildingFloorNo(int buildingFloorNo) {
		this.buildingFloorNo = buildingFloorNo;
	}

	public String getEventSystem() {
		return eventSystem;
	}

	public void setEventSystem(String eventSystem) {
		this.eventSystem = eventSystem;
	}

	public String getEventSignal() {
		return eventSignal;
	}

	public void setEventSignal(String eventSignal) {
		this.eventSignal = eventSignal;
	}

	public String getEventValue() {
		return eventValue;
	}

	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}

	public String getEventDeviceType() {
		return eventDeviceType;
	}

	public void setEventDeviceType(String eventDeviceType) {
		this.eventDeviceType = eventDeviceType;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getResetComments() {
		return resetComments;
	}

	public void setResetComments(String resetComments) {
		this.resetComments = resetComments;
	}

	public String getResetRequestedBy() {
		return resetRequestedBy;
	}

	public void setResetRequestedBy(String resetRequestedBy) {
		this.resetRequestedBy = resetRequestedBy;
	}

	public Date getResetRequestedDtm() {
		return resetRequestedDtm;
	}

	public void setResetRequestedDtm(Date resetRequestedDtm) {
		this.resetRequestedDtm = resetRequestedDtm;
	}

	public AlarmDevices getAlarmDevice() {
		return alarmDevice;
	}

	public void setAlarmDevice(AlarmDevices alarmDevice) {
		this.alarmDevice = alarmDevice;
	}

	public AlarmDeviceReset getAlarmDeviceReset() {
		return alarmDeviceReset;
	}

	public void setAlarmDeviceReset(AlarmDeviceReset alarmDeviceReset) {
		this.alarmDeviceReset = alarmDeviceReset;
	}
	
	
	
}
