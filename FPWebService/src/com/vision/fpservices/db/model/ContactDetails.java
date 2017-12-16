package com.vision.fpservices.db.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contact_details")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ContactDetails implements Comparable<ContactDetails>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "contact_id")
	private Integer contactId;
	@Column(name = "contact_name")
	private String contactName;
	
	@Column(name = "type")
	private String type;

	@Column(name = "phone_primary")
	private String phonePrimary;
	
	@Column(name = "phone_secondary")
	private String phoneSecondary;
	
	@Column(name = "fax")
	private String fax;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "created_by")
	private String createdBy;
	
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	
	@OneToOne(mappedBy="maintenanceContactDetails")
	private Building maintenanceBldg;
	
	@OneToOne(mappedBy="enggContactDetails")
	private Building enggBuilding;
	
	@OneToOne(mappedBy="securityContactDetails")
	private Building securityBuilding;
	
	@OneToOne(mappedBy="customerContactDetails")
	private CustomerDetails customerDetails;
	
	@OneToOne(mappedBy="buildingContactDetails")
	private Building building;
	
	@OneToMany(mappedBy="contactDetails")
	private Set<AlarmNotfnHistory> alarmNotfnHistory;

	@Override
	public int compareTo(ContactDetails con) {
		//System.out.println(this.serialNo + ":" + inp.serialNo);
		if (this.contactId > con.contactId) {
			return 1;
		} else {
			return -1;
		}

	}


	public Integer getContactId() {
		return contactId;
	}


	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}


	public String getContactName() {
		return contactName;
	}


	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getPhonePrimary() {
		return phonePrimary;
	}


	public void setPhonePrimary(String phonePrimary) {
		this.phonePrimary = phonePrimary;
	}


	public String getPhoneSecondary() {
		return phoneSecondary;
	}


	public void setPhoneSecondary(String phoneSecondary) {
		this.phoneSecondary = phoneSecondary;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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


	public Building getMaintenanceBldg() {
		return maintenanceBldg;
	}


	public void setMaintenanceBldg(Building maintenanceBldg) {
		this.maintenanceBldg = maintenanceBldg;
	}


	public Building getEnggBuilding() {
		return enggBuilding;
	}


	public void setEnggBuilding(Building enggBuilding) {
		this.enggBuilding = enggBuilding;
	}


	public Building getSecurityBuilding() {
		return securityBuilding;
	}


	public void setSecurityBuilding(Building securityBuilding) {
		this.securityBuilding = securityBuilding;
	}


	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}


	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}


	public Building getBuilding() {
		return building;
	}


	public void setBuilding(Building building) {
		this.building = building;
	}


	public Set<AlarmNotfnHistory> getAlarmNotfnHistory() {
		return alarmNotfnHistory;
	}


	public void setAlarmNotfnHistory(Set<AlarmNotfnHistory> alarmNotfnHistory) {
		this.alarmNotfnHistory = alarmNotfnHistory;
	}
	
}
