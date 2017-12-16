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
@Table(name = "alarm_device_reset")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AlarmDeviceReset {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "device_request_id")
	private Integer deviceRequestId;
	
	@Column(name = "reset_request_dtm")
	private Date resetRequestedDtm;
	
	@Column(name = "reset_dtm")
	private Date resetDtm;
	
	@Column(name = "reset_status")
	private String resetStatus;
	
	@Column(name = "reset_requested_by")
	private String resetRequestedBy;
	
	@ManyToOne
	@JoinColumn(name = "device_id")
	private AlarmDevices alarmDevice;
	
	@OneToOne
	@JoinColumn(name = "event_id")
	private AlarmEvents alarmEvent;

	public Integer getDeviceRequestId() {
		return deviceRequestId;
	}

	public void setDeviceRequestId(Integer deviceRequestId) {
		this.deviceRequestId = deviceRequestId;
	}

	public Date getResetRequestedDtm() {
		return resetRequestedDtm;
	}

	public void setResetRequestedDtm(Date resetRequestedDtm) {
		this.resetRequestedDtm = resetRequestedDtm;
	}

	public Date getResetDtm() {
		return resetDtm;
	}

	public void setResetDtm(Date resetDtm) {
		this.resetDtm = resetDtm;
	}

	public String getResetStatus() {
		return resetStatus;
	}

	public void setResetStatus(String resetStatus) {
		this.resetStatus = resetStatus;
	}

	public String getResetRequestedBy() {
		return resetRequestedBy;
	}

	public void setResetRequestedBy(String resetRequestedBy) {
		this.resetRequestedBy = resetRequestedBy;
	}

	public AlarmDevices getAlarmDevice() {
		return alarmDevice;
	}

	public void setAlarmDevice(AlarmDevices alarmDevice) {
		this.alarmDevice = alarmDevice;
	}

	public AlarmEvents getAlarmEvent() {
		return alarmEvent;
	}

	public void setAlarmEvent(AlarmEvents alarmEvent) {
		this.alarmEvent = alarmEvent;
	}
	
	
	
}
