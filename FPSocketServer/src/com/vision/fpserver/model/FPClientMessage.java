package com.vision.fpserver.model;

public class FPClientMessage {
	
	private String message;
	private String clientName;
	
	public FPClientMessage(String message,String clientName){
		this.message=message;
		this.clientName=clientName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	

}
