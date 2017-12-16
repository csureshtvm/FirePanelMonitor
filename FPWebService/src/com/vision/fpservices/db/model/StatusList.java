package com.vision.fpservices.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "status_list")
public class StatusList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int statusId;
	@Column(name = "status_normal")
	private String statusNormal;
	@Column(name = "status_abnormal")
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
	
	public com.vision.fpservices.model.StatusList convertToInternal(){
		com.vision.fpservices.model.StatusList statusList=new com.vision.fpservices.model.StatusList();
		statusList.setStatusAbnormal(statusAbnormal);
		statusList.setStatusId(statusId);
		statusList.setStatusNormal(statusNormal);
		
		return statusList;
	}

}
