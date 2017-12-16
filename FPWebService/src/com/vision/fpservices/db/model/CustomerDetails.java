package com.vision.fpservices.db.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vision.fpservices.dto.CustomerDTO;

@Entity
@Table(name = "fp_customer_details")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CustomerDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_id")
	private Integer customerId;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "created_by")
	private String createdBy;
	
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "is_active")
	private String isActive;
	
	@OneToMany(mappedBy="customerDetails")
	private Set<Building> buildings;
	
	@OneToOne
	@JoinColumn(name="customer_contact_id")
	private ContactDetails customerContactDetails;
	
	@OneToOne
	@JoinColumn(name="customer_address_id")
	private AddressDetails customerAddressDetails;
	
	@OneToMany(mappedBy="customerDetails")
	private Set<User> user;
	

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Set<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(Set<Building> buildings) {
		this.buildings = buildings;
	}
	
	
	
	public ContactDetails getCustomerContactDetails() {
		return customerContactDetails;
	}

	public void setCustomerContactDetails(ContactDetails customerContactDetails) {
		this.customerContactDetails = customerContactDetails;
	}

	public AddressDetails getCustomerAddressDetails() {
		return customerAddressDetails;
	}

	public void setCustomerAddressDetails(AddressDetails customerAddressDetails) {
		this.customerAddressDetails = customerAddressDetails;
	}
	
	

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

	public CustomerDTO convertToInternal(){
		CustomerDTO custDTO=new CustomerDTO();
		custDTO.setCustomerId(String.valueOf(customerId));
		custDTO.setCustomerName(customerName);
		custDTO.setIsActive(isActive);
		
		if(customerAddressDetails!=null){
			custDTO.setAddressId(customerAddressDetails.getAddressId()!=null?customerAddressDetails.getAddressId()+"":null);
			custDTO.setAddressType(customerAddressDetails.getAddressType());
			custDTO.setAddressFirstLine(customerAddressDetails.getAddressFirstLine());
			custDTO.setAddressSecondLine(customerAddressDetails.getAddressSecondLine());
			custDTO.setTown(customerAddressDetails.getTown());
			custDTO.setCounty(customerAddressDetails.getCounty());
			custDTO.setCountry(customerAddressDetails.getCountry());
			custDTO.setPostalCode(customerAddressDetails.getPostalCode());
		}
		if(customerContactDetails!=null){
			custDTO.setContactId(customerContactDetails.getContactId());
			custDTO.setContactName(customerContactDetails.getContactName());
			custDTO.setContactType(customerContactDetails.getType());
			custDTO.setPhonePrimary(customerContactDetails.getPhonePrimary());
			custDTO.setPhoneSecondary(customerContactDetails.getPhoneSecondary());
			custDTO.setFax(customerContactDetails.getFax());
			custDTO.setEmail(customerContactDetails.getEmail());
		}
		
		return custDTO;
	}
	
	
}
