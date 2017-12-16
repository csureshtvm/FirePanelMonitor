package com.vision.fpservices.db.service;

import java.util.List;

import javax.ws.rs.QueryParam;

import com.vision.fpservices.dto.AlarmDeviceResetDto;
import com.vision.fpservices.dto.AlarmDevicesDto;
import com.wordnik.swagger.core.ApiParam;

public interface AlarmDevicesServices {	
	public boolean saveAlarmDevices(String deviceId,String deviceName,String deviceLoc,String buildingId,String installedFloorNo,String updatedBy,boolean isUpdate);
	public boolean updateAlarmDeviceLastCommunicationTime(String deviceId);
	public List<AlarmDevicesDto> getAllAlarmDevices(String customerId);
	
	public boolean updateAlarmDeviceResetDetails(String alarmDeviceId,String resetStatus);
	public List<AlarmDeviceResetDto> getAllDeviceResetDetails();
	public boolean saveAlarmDeviceResetRequest(String deviceId,String requestedBy);
	
}