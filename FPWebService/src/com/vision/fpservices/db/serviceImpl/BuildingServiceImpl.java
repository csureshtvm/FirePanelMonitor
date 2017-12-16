package com.vision.fpservices.db.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.test.utils.ServerContextHelper;
import com.vision.fpservices.db.dao.BuildingDAO;
import com.vision.fpservices.db.dao.CustomerDao;
import com.vision.fpservices.db.dao.NotfnSettingsDAO;
import com.vision.fpservices.db.dao.UserDAO;
import com.vision.fpservices.db.model.AlarmNotificationSettings;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.db.service.BuildingService;
import com.vision.fpservices.dto.BuildingDTO;
import com.vision.fpservices.dto.BuildingsummaryDTO;
import com.vision.fpservices.dto.UserDTO;

@Repository("BuildingService")
@Transactional(readOnly=true)
public class BuildingServiceImpl implements BuildingService{


	//public BuildingDAO buildingDAO = new BuildingDAO();
	@Autowired
	BuildingDAO buildingDAO;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	NotfnSettingsDAO notfnDao;
	
	@Autowired
	UserDAO userDAO;

	Logger log = Logger.getLogger("BuildingService");
	

	public List<com.vision.fpservices.dto.BuildingDTO> getAllBuildings(String customerId,String userId){
		
		
		List<BuildingDTO> bldgs=buildingDAO.getAllBuildings(customerId);
		
		if(userId !=null){
			UserDTO userDto=userDAO.getLoginUserDetails(userId);
			if(userDto!=null && userDto.getUserCustomerMapping()!=null){
				ObjectMapper mapper=new ObjectMapper();
				try {
					List<String> buildingIds = mapper.readValue(userDto.getUserCustomerMapping(), new TypeReference<List<String>>(){});
					if(buildingIds!=null && buildingIds.size()>0){
					List<BuildingDTO> resultList=new ArrayList<BuildingDTO>();
					for(BuildingDTO dto : bldgs){
						if(resultList.contains(dto.getBuildingId())){
							resultList.add(dto);
						}
					}
					return resultList;
					}
					
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
		
		return bldgs;
	}
	
	@Transactional(readOnly=false)
	public boolean saveBuilding(BuildingsummaryDTO bldgSummary,Integer customerId, String updatedBy){
		
		Building building=null;
		if(bldgSummary.getBuildingId()!=null && bldgSummary.getBuildingId()>0){
			building=buildingDAO.loadBuilding(bldgSummary.getBuildingId());
			building.setUpdatedBy(updatedBy);
			building.setUpdatedDate(new Date());
		}else{
			building=new Building();
			building.setCreatedBy(updatedBy);
			building.setCreatedDate(new Date());
			CustomerDetails customerDetails=customerDao.loadCustomer(customerId);
			
			building.setCustomerDetails(customerDetails);
		}
		building.setBuildingLocation(bldgSummary.getBuildingLocation());
		building.setBuildingName(bldgSummary.getBuildingName());
		building.setBuildingLocation(bldgSummary.getBuildingLocation());
		building.setDescription(bldgSummary.getDescription());
		building.setBuildingTpe(bldgSummary.getBuildingTpe());
		building.setMarakaniNo(bldgSummary.getMarakaniNo());
		building.setNoOfFloor(bldgSummary.getNoOfFloor());
		building.setNearPoliceStation(bldgSummary.getNearPoliceStation());
		building.setNearCivilDefence(bldgSummary.getNearCivilDefence());
		building.setNearHospital(bldgSummary.getNearHospital());
		building.setGpsCordinate(bldgSummary.getGpsCordinates());
		
		return buildingDAO.makePersistent(building).getBuildingId()>0?true:false;
		
	}
	public Building getBuilding(int bldgId){
		
		return buildingDAO.loadBuilding(bldgId);
	}
	public int saveOrUpdate(Building building) {
		return buildingDAO.makePersistent(building).getBuildingId();
	}

	@Transactional(readOnly=false)	
	public boolean saveNotificationSettings(String buildingId, String contactType,String notifySMS,String notifyEmail,String updatedBy){
		Building building=buildingDAO.loadBuilding(Integer.parseInt(buildingId));
		
		List<AlarmNotificationSettings> notfnList=notfnDao.getAllNotfnSettingsOfBuilding(Integer.parseInt(buildingId));
		
		AlarmNotificationSettings smsNotfySetng=null;
		AlarmNotificationSettings emailNotfnSetng=null;
		if(notfnList!=null && notfnList.size()>0){
			for (AlarmNotificationSettings notfnSettg : notfnList){
				if("SMS".equalsIgnoreCase(notfnSettg.getNotificationType())){
					smsNotfySetng=notfnSettg;
				}else if("EMAIL".equalsIgnoreCase(notfnSettg.getNotificationType())){
					emailNotfnSetng=notfnSettg;
				}
				
			}
		}
		if(smsNotfySetng==null){
			smsNotfySetng=new AlarmNotificationSettings();
			smsNotfySetng.setBuilding(building);
			smsNotfySetng.setNotificationType("SMS");
			smsNotfySetng.setNotifyBuildingContact("Y");
		}
		
		if("engineering".equalsIgnoreCase(contactType)){
			smsNotfySetng.setNotifyEnggContact(("Y".equalsIgnoreCase(notifySMS)?"Y":null));
		}else if("maintenance".equalsIgnoreCase(contactType)){
			smsNotfySetng.setNotifyMaintenanceContact(("Y".equalsIgnoreCase(notifySMS)?"Y":null));
		}else if("security".equalsIgnoreCase(contactType)){
			smsNotfySetng.setNotifySecurityContact(("Y".equalsIgnoreCase(notifySMS)?"Y":null));
		}else if("building".equalsIgnoreCase(contactType)){
			smsNotfySetng.setNotifyBuildingContact(("Y".equalsIgnoreCase(notifySMS)?"Y":null));
		}else if("customer".equalsIgnoreCase(contactType)){
			smsNotfySetng.setNotifyCustomer(("Y".equalsIgnoreCase(notifySMS)?"Y":null));			
		}

		smsNotfySetng.setUpdatedBy(updatedBy);
		smsNotfySetng.setUpdatedDate(new Date());
		
		
		
		if(emailNotfnSetng==null){
			emailNotfnSetng=new AlarmNotificationSettings();
			emailNotfnSetng.setBuilding(building);
			emailNotfnSetng.setNotificationType("EMAIL");
			emailNotfnSetng.setNotifyBuildingContact("Y");
		}
		
		if("engineering".equalsIgnoreCase(contactType)){
			emailNotfnSetng.setNotifyEnggContact(("Y".equalsIgnoreCase(notifyEmail)?"Y":null));
		}else if("maintenance".equalsIgnoreCase(contactType)){
			emailNotfnSetng.setNotifyMaintenanceContact(("Y".equalsIgnoreCase(notifyEmail)?"Y":null));
		}else if("security".equalsIgnoreCase(contactType)){
			emailNotfnSetng.setNotifySecurityContact(("Y".equalsIgnoreCase(notifyEmail)?"Y":null));
		}else if("building".equalsIgnoreCase(contactType)){
			emailNotfnSetng.setNotifyBuildingContact(("Y".equalsIgnoreCase(notifyEmail)?"Y":null));
		}else if("customer".equalsIgnoreCase(contactType)){
			emailNotfnSetng.setNotifyCustomer(("Y".equalsIgnoreCase(notifyEmail)?"Y":null));			
		}

		emailNotfnSetng.setUpdatedBy(updatedBy);
		emailNotfnSetng.setUpdatedDate(new Date());
		
		notfnDao.makePersistent(smsNotfySetng);
		notfnDao.makePersistent(emailNotfnSetng);
		
		return true;
	}
	
	public Set<com.vision.fpservices.model.ContactDetails> getAllContactsOfBuilding(int buildingId){
		Set<com.vision.fpservices.model.ContactDetails> contacts=new HashSet<com.vision.fpservices.model.ContactDetails>();
				
		Building building=getBuilding(buildingId);
		/*for(ContactDetails con : building.getContacts()){
			contacts.add(con.convertToInternal());
		}*/
		
		
		return contacts; 
	}

}
