package com.vision.fpservices.db.dao;

import java.util.List;

import com.vision.fpservices.db.model.AlarmDeviceReset;
import com.vision.fpservices.db.model.AlarmDevices;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.dto.AlarmDeviceResetDto;
import com.vision.fpservices.dto.AlarmDevicesDto;

public interface AlarmDevicesDAO extends GenericDao<AlarmDevices, String> {
	public AlarmDevices loadAlarmDevice(String alarmDeviceId);
	public String saveOrUpdate(AlarmDevices alarmDevice);
	public List<AlarmDevicesDto> getAllAlarmDevices(String customerId);
	public AlarmDevices getAlarmDeviceIdFromBuildingDetails(Integer buildingId,String floorNo);
	
	
}
