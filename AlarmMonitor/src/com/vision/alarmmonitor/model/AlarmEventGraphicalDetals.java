package com.vision.alarmmonitor.model;

import java.util.Comparator;
import java.util.Map;

public class AlarmEventGraphicalDetals {

	private String buildingName;
	private Map<Integer,Integer> fireAlarmCountMap;
	private Map<Integer,Integer> normalAlarmCountMap;
	private int totalFireAlarms;
	private int totalNormalAlarms;
	
	private String buildingAddressFirstLine;
	private String buildingAddressSecondLine;
	private String town;
	private String country;
	private Integer noOfFloors;
	
	/**
	 * @return the buildingName
	 */
	public String getBuildingName() {
		return buildingName;
	}
	/**
	 * @param buildingName the buildingName to set
	 */
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	/**
	 * @return the fireAlarmCountMap
	 */
	public Map<Integer, Integer> getFireAlarmCountMap() {
		return fireAlarmCountMap;
	}
	/**
	 * @param fireAlarmCountMap the fireAlarmCountMap to set
	 */
	public void setFireAlarmCountMap(Map<Integer, Integer> fireAlarmCountMap) {
		this.fireAlarmCountMap = fireAlarmCountMap;
	}
	/**
	 * @return the normalAlarmCountMap
	 */
	public Map<Integer, Integer> getNormalAlarmCountMap() {
		return normalAlarmCountMap;
	}
	/**
	 * @param normalAlarmCountMap the normalAlarmCountMap to set
	 */
	public void setNormalAlarmCountMap(Map<Integer, Integer> normalAlarmCountMap) {
		this.normalAlarmCountMap = normalAlarmCountMap;
	}
	/**
	 * @return the totalFireAlarms
	 */
	public int getTotalFireAlarms() {
		return totalFireAlarms;
	}
	/**
	 * @param totalFireAlarms the totalFireAlarms to set
	 */
	public void setTotalFireAlarms(int totalFireAlarms) {
		this.totalFireAlarms = totalFireAlarms;
	}
	/**
	 * @return the totalNormalAlarms
	 */
	public int getTotalNormalAlarms() {
		return totalNormalAlarms;
	}
	/**
	 * @param totalNormalAlarms the totalNormalAlarms to set
	 */
	public void setTotalNormalAlarms(int totalNormalAlarms) {
		this.totalNormalAlarms = totalNormalAlarms;
	}
	
	public int getValue(){
		
		return (totalFireAlarms*2) + (totalNormalAlarms);
	}
	/**
	 * @return the buildingAddressFirstLine
	 */
	public String getBuildingAddressFirstLine() {
		return buildingAddressFirstLine;
	}
	/**
	 * @param buildingAddressFirstLine the buildingAddressFirstLine to set
	 */
	public void setBuildingAddressFirstLine(String buildingAddressFirstLine) {
		this.buildingAddressFirstLine = buildingAddressFirstLine;
	}
	/**
	 * @return the buildingAddressSecondLine
	 */
	public String getBuildingAddressSecondLine() {
		return buildingAddressSecondLine;
	}
	/**
	 * @param buildingAddressSecondLine the buildingAddressSecondLine to set
	 */
	public void setBuildingAddressSecondLine(String buildingAddressSecondLine) {
		this.buildingAddressSecondLine = buildingAddressSecondLine;
	}
	/**
	 * @return the town
	 */
	public String getTown() {
		return town;
	}
	/**
	 * @param town the town to set
	 */
	public void setTown(String town) {
		this.town = town;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the noOfFloors
	 */
	public Integer getNoOfFloors() {
		return noOfFloors;
	}
	/**
	 * @param noOfFloors the noOfFloors to set
	 */
	public void setNoOfFloors(Integer noOfFloors) {
		this.noOfFloors = noOfFloors;
	}
	
	
	
}
