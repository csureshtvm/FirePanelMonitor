package com.vision.fpservices.model;

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


public class Controller {
	

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
	
	
	
	/**
	 * @return the controllerId
	 */
	public Integer getControllerId() {
		return controllerId;
	}
	
	
	/**
	 * @return the controllerName
	 */

	
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
	
	
	
	

}
