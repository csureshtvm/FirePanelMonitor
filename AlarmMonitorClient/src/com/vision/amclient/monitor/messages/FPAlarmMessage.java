package com.vision.amclient.monitor.messages;

import com.vision.amclient.util.StaticMessages;

public class FPAlarmMessage extends FPMessage{

	private String customerId;
	private String buildingId;
	private String system;
	private String signal;
	private String floorNo;
	private String value;
	
	public FPAlarmMessage(Integer messageId,String customerId,String buildingId,String system,String signal,String value){
		super(messageId,System.currentTimeMillis(),StaticMessages.MESSAGE_TYPE_REQ_DEVICE_ALARM);
		this.customerId=customerId;
		this.buildingId=buildingId;
		this.system=system;
		this.signal=signal;
		this.value=value;
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
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getSignal() {
		return signal;
	}
	public void setSignal(String signal) {
		this.signal = signal;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMessage(){
		String alarmMessage = "MSGID=" + getMessageId() + "&MSGTYPEID="
				+ getMessageType() + "&MSGTIME=" + getMessageTime()
				+ "&CUSTID=" + getCustomerId() + "&BLDGID=" + getBuildingId()
				+ "&SYSTEM=" + getSystem() + "&SIGNAL=" + getSignal()+ "&VALUE=" + getValue();
		String message="MSGSTART$"+alarmMessage+"$MSGEND";
		return message;
	}
}
