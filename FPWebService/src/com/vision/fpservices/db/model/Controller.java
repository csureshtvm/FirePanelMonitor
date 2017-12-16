package com.vision.fpservices.db.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "controller")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Controller {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "controller_id")
	private Integer controllerId;
	
	private String controllerName;
	private String installedLocation;
	private String description;
	private int noOfInputs;
	
	public Controller(){}
	
	public Controller(String controllerName,String installedLocation,	String description,int noOfInputs ){
		this.controllerName=controllerName;
		this.installedLocation=installedLocation;
		this.description=description;
		this.noOfInputs=noOfInputs;
		System.out.println("Inside--------");
	}
	
	@ManyToOne( cascade=CascadeType.REMOVE)
	@JoinColumn(name = "building_id")
	private Building building;
	

	@OneToMany(mappedBy="controller" , fetch=FetchType.EAGER)
	private Set<InputDevices> inputDevices;
	/*
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "controller_contactdetails", joinColumns = { @JoinColumn(name = "contact_id") })
	private Set<ContactDetails> contacts;*/
	
	
	
	/**
	 * @return the contacts
	 */
	/*public Set<ContactDetails> getContacts() {
		return contacts;
	}*/

	/**
	 * @param contacts the contacts to set
	 */
	/*public void setContacts(Set<ContactDetails> contacts) {
		this.contacts = contacts;
	}*/

	/**
	 * @return the inputDevices
	 */
	public Set<InputDevices> getInputDevices() {
		return inputDevices;
	}
	/**
	 * @param inputDevices the inputDevices to set
	 */
	public void setInputDevices(Set<InputDevices> inputDevices) {
		this.inputDevices = inputDevices;
	}
	/**
	 * @return the controllerId
	 */
	public Integer getControllerId() {
		return controllerId;
	}
	/**
	 * @return the building
	 */
	public Building getBuilding() {
		return building;
	}
	/**
	 * @param building the building to set
	 */
	public void setBuilding(Building building) {
		this.building = building;
	}
	/**
	 * @param controllerId the controllerId to set
	 */
	public void setControllerId(int controllerId) {
		this.controllerId = controllerId;
	}
	/**
	 * @return the controllerName
	 */

	@Basic(optional = false)
	@Column(name = "controller_name")
	public String getControllerName() {
		return controllerName;
	}
	/**
	 * @param controllerName the controllerName to set
	 */
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	/**
	 * @return the installedLocation
	 */
	@Basic(optional = false)
	@Column(name = "location")
	public String getInstalledLocation() {
		return installedLocation;
	}
	/**
	 * @param installedLocation the installedLocation to set
	 */
	public void setInstalledLocation(String installedLocation) {
		this.installedLocation = installedLocation;
	}
	/**
	 * @return the description
	 */
	@Basic(optional = false)
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the noOfInputs
	 */
	@Basic(optional = false)
	@Column(name = "noofinputs")
	public int getNoOfInputs() {
		return noOfInputs;
	}
	/**
	 * @param noOfInputs the noOfInputs to set
	 */
	public void setNoOfInputs(int noOfInputs) {
		this.noOfInputs = noOfInputs;
	}

	/**
	 * @param controllerId the controllerId to set
	 */
	public void setControllerId(Integer controllerId) {
		this.controllerId = controllerId;
	}
	
	public com.vision.fpservices.model.Controller convertToInternal(){
		com.vision.fpservices.model.Controller controller=new com.vision.fpservices.model.Controller();
		controller.setControllerId(controllerId);
		controller.setControllerName(controllerName);
		controller.setDescription(description);
		controller.setDescription(description);
		controller.setInstalledLocation(installedLocation);
		controller.setNoOfInputs(noOfInputs);
		return controller;
	}
	
	

}
