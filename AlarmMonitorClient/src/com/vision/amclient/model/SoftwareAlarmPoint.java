package com.vision.amclient.model;

public class SoftwareAlarmPoint {
	
	Integer objectId;
	String objectType;
	String description;
	
	
	
	
	public SoftwareAlarmPoint(Integer objectId, String objectType,
			String description) {
		super();
		this.objectId = objectId;
		this.objectType = objectType;
		this.description = description;
	}
	public Integer getObjectId() {
		return objectId;
	}
	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
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
	
	public String toString(){		
		return this.objectId+":"+this.objectType+":"+this.description;
	}

}
