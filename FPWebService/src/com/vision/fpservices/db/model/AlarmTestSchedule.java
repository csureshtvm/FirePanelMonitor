package com.vision.fpservices.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alarm_test_schedule")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AlarmTestSchedule {

	@Id
	@Column(name = "schedule_id")
	private String scheduleId;

	@Column(name = "test_status")
	private String testStatus;

	@Column(name = "test_date_from")
	private Date testDateFrom;

	@Column(name = "test_date_to")
	private Date testDateTo;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "building_id")
	private Building building;

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	public Date getTestDateFrom() {
		return testDateFrom;
	}

	public void setTestDateFrom(Date testDateFrom) {
		this.testDateFrom = testDateFrom;
	}

	public Date getTestDateTo() {
		return testDateTo;
	}

	public void setTestDateTo(Date testDateTo) {
		this.testDateTo = testDateTo;
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

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	
	
}
