package com.vision.alarmmonitor.dto;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class UserDTO {
	private String userName;
	private String firstName;
	private String lastName;
	private Date createdDate;
	private Date updatedDate;
	private String userRole;
	private Integer customerId;
	private String userEmail;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private String userStatus;
	
	private String userCustomerMapping;
	
	public UserDTO(){
		
	}
	public UserDTO(String userName,String firstName,String lastName,String userEmail,String userRole,Integer customerId){
		this.userName=userName;
		this.firstName=firstName;
		this.lastName=lastName;
		this.userEmail=userEmail;
		this.userRole=userRole;
		this.customerId=customerId;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getUserCustomerMapping() {
		return userCustomerMapping;
	}
	public void setUserCustomerMapping(String userCustomerMapping) {
		this.userCustomerMapping = userCustomerMapping;
	}
	
	

}
