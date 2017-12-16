package com.vision.amclient.monitor.messages;

import com.vision.amclient.util.StaticMessages;

public class FPSoftwareMessage extends FPMessage{

	
	private String customerId;
	private String buildingId;
	private String softwareMessage;
	
	
	
	public FPSoftwareMessage(Integer messageId, String customerId, String buildingId,
			String softwareMessage) {
		super(messageId, System.currentTimeMillis(),StaticMessages.MESSAGE_TYPE_REQ_SOFTWARE_MESSAGE);
		this.customerId = customerId;
		this.buildingId = buildingId;
		this.softwareMessage = softwareMessage;
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
	public String getSoftwareMessage() {
		return softwareMessage;
	}
	public void setSoftwareMessage(String softwareMessage) {
		this.softwareMessage = softwareMessage;
	}
	
	
	public String getMessage(){
		String alarmMessage = "MSGID=" + getMessageId() + "&MSGTYPEID="
				+ getMessageType() + "&MSGTIME=" + getMessageTime()
				+ "&CUSTID=" + getCustomerId() + "&BLDGID=" + getBuildingId()
				+"&VALUE=" + getSoftwareMessage();
		String message="MSGSTART$"+alarmMessage+"$MSGEND";
		return message;
	}
	
	
}
