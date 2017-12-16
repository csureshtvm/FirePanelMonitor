package com.vision.fpservices.db.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.test.utils.ServerContextHelper;
import com.vision.fpservices.db.dao.BuildingDAO;
import com.vision.fpservices.db.dao.CustomerDao;
import com.vision.fpservices.db.dao.NotfnSettingsDAO;
import com.vision.fpservices.db.model.AlarmNotificationSettings;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.dto.BuildingsummaryDTO;
import com.vision.fpservices.model.FacilityResult;

public interface BuildingService {

	
	public List<com.vision.fpservices.dto.BuildingDTO> getAllBuildings(String customerId,String userId);
	public boolean saveBuilding(BuildingsummaryDTO bldgSummary,Integer customerId, String updatedBy);
	public Building getBuilding(int bldgId);
	public int saveOrUpdate(Building building) ;
	public boolean saveNotificationSettings(String buildingId, String contactType,String notifySMS,String notifyEmail,String updatedBy);
	public Set<com.vision.fpservices.model.ContactDetails> getAllContactsOfBuilding(int buildingId);
}
