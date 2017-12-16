package com.vision.amclient.model;

import java.util.Date;

public class AlarmMessage {

	private String message;
	private int type;
	private Date messageDate;

	public AlarmMessage(String message, int type, Date messageDate) {
		this.message = message;
		this.type = type;
		this.messageDate = messageDate;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the error
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(int type) {
		this.type = type;
	}

	/**
	 * @return the messageDate
	 */
	public Date getMessageDate() {
		return messageDate;
	}

	/**
	 * @param messageDate
	 *            the messageDate to set
	 */
	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

}
