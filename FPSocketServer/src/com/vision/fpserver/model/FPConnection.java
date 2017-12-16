package com.vision.fpserver.model;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FPConnection {
	private Socket clientSocket;
	private OutputStream outputStream;
	private InputStream inputStream;
	private String deviceId;
	private String customerId;
	private String buildngId;
	private String floorNo;
	
	public FPConnection(Socket clientSocket,InputStream inputStream,OutputStream outputStream) {
		this.clientSocket=clientSocket;
		this.outputStream=outputStream;
		this.inputStream=inputStream;
	}
	
	public FPConnection(Socket clientSocket,InputStream inputStream,OutputStream outputStream,String deviceId,String customerId,String buildngId,String floorNo) {
		this.clientSocket=clientSocket;
		this.outputStream=outputStream;
		this.inputStream=inputStream;
		this.deviceId=deviceId;
		this.customerId=customerId;
		this.buildngId=buildngId;
		this.floorNo=floorNo;
	}
	public Socket getClientSocket() {
		return clientSocket;
	}
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	public OutputStream getOutputStream() {
		return outputStream;
	}
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getBuildngId() {
		return buildngId;
	}
	public void setBuildngId(String buildngId) {
		this.buildngId = buildngId;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	
	
	
	

}
