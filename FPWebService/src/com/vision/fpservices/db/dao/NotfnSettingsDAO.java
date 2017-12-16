package com.vision.fpservices.db.dao;

import java.util.List;

import com.vision.fpservices.db.model.AlarmNotificationSettings;
import com.vision.fpservices.db.model.Building;

public interface NotfnSettingsDAO extends GenericDao<AlarmNotificationSettings, Integer>{
	public int saveOrUpdate(AlarmNotificationSettings notfnSettings);
	public List<AlarmNotificationSettings> getAllNotfnSettingsOfBuilding(Integer buildingId );
}
