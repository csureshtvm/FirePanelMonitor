package com.vision.fpservices.db.dao;

import java.util.List;

import com.vision.fpservices.db.model.AlarmDeviceReset;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.dto.AlarmDeviceResetDto;

public interface AlarmEventResetDAO extends GenericDao<AlarmDeviceReset, Integer>{
	public List<AlarmDeviceResetDto> getAllAlarmResetDevices();
	public boolean updateAlarmResetDeviceSatus(String alarmDeviceId,String resetStatus);
	public int saveOrUpdateAlarmDeviceResetRequest(AlarmDeviceReset alarmDeviceReset);
}
