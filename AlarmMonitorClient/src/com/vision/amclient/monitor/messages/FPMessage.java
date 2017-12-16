package com.vision.amclient.monitor.messages;

public class FPMessage {
	
	private Integer messageId;
	private long messageTime;
	private Integer messageType;
	
	public FPMessage(Integer messageId,long messageTime,Integer messageType){
		this.messageId=messageId;
		this.messageTime=messageTime;
		this.messageType=messageType;
	}
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public long getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(long messageTime) {
		this.messageTime = messageTime;
	}
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	
	public String getMessage(){
		String alarmMessage="MSGID="+getMessageId()+"&MSGTYPEID="+getMessageType()+"&MSGTIME="+getMessageTime();
		String message="MSGSTART$"+alarmMessage+"$MSGEND";
		return message;
	}

}
