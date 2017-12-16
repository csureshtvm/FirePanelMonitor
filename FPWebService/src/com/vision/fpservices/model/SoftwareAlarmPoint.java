package com.vision.fpservices.model;

public class SoftwareAlarmPoint {
	
	Integer objectId;
	String objectType;
	String description;
	String value;
	
	
	
	public SoftwareAlarmPoint(Integer objectId, String objectType,
			String description) {
		super();
		this.objectId = objectId;
		this.objectType = objectType;
		this.description = description;
	}
	
	
	public SoftwareAlarmPoint(Integer objectId, String objectType,
			String description, String value) {
		super();
		this.objectId = objectId;
		this.objectType = objectType;
		this.description = description;
		this.value = value;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
