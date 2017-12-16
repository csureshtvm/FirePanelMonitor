package com.vision.amclient.monitor.messages;

import com.vision.amclient.util.StaticMessages;

public class FPDeviceConfigMessage extends FPMessage {
	
	private String customerId;
	private String buildingId;
	private Integer floorNo;
	private String deviceId;
	
	public FPDeviceConfigMessage(Integer messageId,String customerId,String buildingId,Integer floorNo,String deviceId){
		super(messageId,System.currentTimeMillis(),StaticMessages.MESSAGE_TYPE_REQ_DEVCE_CONFIG);
		this.customerId=customerId;
		this.buildingId=buildingId;
		this.floorNo=floorNo;
		this.deviceId=deviceId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public Integer getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(Integer floorNo) {
		this.floorNo = floorNo;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getMessage(){
		String deviceConfigMessage="MSGID="+getMessageId()+"&MSGTYPEID="+getMessageType()+"&MSGTIME="+getMessageTime()+"&DEVICEID="+getDeviceId()+"&CUSTID="+getCustomerId()+"&BLDGID="+getBuildingId()+"&FLOORNO="+getFloorNo();
		String message="MSGSTART$"+deviceConfigMessage+"$MSGEND";
		return message;
	}
}
