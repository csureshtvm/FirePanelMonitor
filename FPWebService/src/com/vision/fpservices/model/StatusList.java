package com.vision.fpservices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class StatusList {

	private int statusId;
	private String statusNormal;
	private String statusAbnormal;

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getStatusNormal() {
		return statusNormal;
	}

	public void setStatusNormal(String statusNormal) {
		this.statusNormal = statusNormal;
	}

	public String getStatusAbnormal() {
		return statusAbnormal;
	}

	public void setStatusAbnormal(String statusAbnormal) {
		this.statusAbnormal = statusAbnormal;
	}

}
