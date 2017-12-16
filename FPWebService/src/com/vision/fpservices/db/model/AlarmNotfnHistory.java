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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "alarm_notfn_history")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AlarmNotfnHistory implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "alarm_notfn_history_id")
	private Integer alarmNotfnEventId;
	@Column(name = "notfn_type")
	private String notfnType;
	@Column(name = "notify_status")
	private String notifyStatus;
	@Column(name = "created_time")
	private Date createdTime;

	@ManyToOne
	@JoinColumn(name = "alarm_event_id")
	private AlarmEvents alarmEvent;
	
	@ManyToOne
	@JoinColumn(name = "contact_id")
	private ContactDetails contactDetails;

	public Integer getAlarmNotfnEventId() {
		return alarmNotfnEventId;
	}

	public void setAlarmNotfnEventId(Integer alarmNotfnEventId) {
		this.alarmNotfnEventId = alarmNotfnEventId;
	}

	public String getNotfnType() {
		return notfnType;
	}

	public void setNotfnType(String notfnType) {
		this.notfnType = notfnType;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public AlarmEvents getAlarmEvent() {
		return alarmEvent;
	}

	public void setAlarmEvent(AlarmEvents alarmEvent) {
		this.alarmEvent = alarmEvent;
	}

	public ContactDetails getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(ContactDetails contactDetails) {
		this.contactDetails = contactDetails;
	}
	
	
	
}
