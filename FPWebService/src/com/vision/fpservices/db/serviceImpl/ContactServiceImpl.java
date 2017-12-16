package com.vision.fpservices.db.serviceImpl;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vision.fpservices.db.dao.BuildingDAO;
import com.vision.fpservices.db.dao.ContactDAO;
import com.vision.fpservices.db.dao.CustomerDao;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.ContactDetails;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.db.service.ContactService;
import com.vision.fpservices.dto.ContactDetailsDTO;

@Repository("ContactService")
@Transactional(readOnly=true)
public class ContactServiceImpl implements ContactService{

	@Autowired
	ContactDAO contactDAO;
	
	@Autowired
	BuildingDAO buildingDAO;
	
	@Autowired
	CustomerDao customerDao;
	Logger log = Logger.getLogger("ContactService");
	
	@Transactional(readOnly=false)
	public boolean saveOrUpdate(ContactDetailsDTO contact,String contactType,String buildingId,String customerId){
		Building building=buildingDAO.loadBuilding(Integer.parseInt(buildingId));
		CustomerDetails customerDetails=null;
		ContactDetails contactDetails=null;
		if("engineering".equalsIgnoreCase(contactType)){
			contactDetails=building.getEnggContactDetails();
		}else if("maintenance".equalsIgnoreCase(contactType)){
			contactDetails=building.getMaintenanceContactDetails();
		}else if("security".equalsIgnoreCase(contactType)){
			contactDetails=building.getSecurityContactDetails();
		}else if("building".equalsIgnoreCase(contactType)){
			contactDetails=building.getBuildingContactDetails();
		}else if("customer".equalsIgnoreCase(contactType)){
			customerDetails=customerDao.loadCustomer(Integer.valueOf(customerId));
			contactDetails=customerDetails.getCustomerContactDetails();
		}
		if(contactDetails==null ){
			contactDetails=new ContactDetails();
			if("customer".equalsIgnoreCase(contactType)){
			contactDetails.setCustomerDetails(customerDetails);
			}else{
				contactDetails.setBuilding(building);
			}
		}
		
		contactDetails.setContactName(contact.getContactName());
		contactDetails.setType(contact.getContactType());
		contactDetails.setPhonePrimary(contact.getPhonePrimary());
		contactDetails.setPhoneSecondary(contact.getPhoneSecondary());
		contactDetails.setFax(contact.getFax());
		contactDetails.setEmail(contact.getEmail());
		contactDetails.setUpdatedDate(new Date());
		contactDetails.setUpdatedBy("Admin");
		contactDAO.makePersistent(contactDetails);
		System.out.println("**************************Saved Contact ID:"+contactDetails.getContactId());
		if("customer".equalsIgnoreCase(contactType)){
			customerDetails.setCustomerContactDetails(contactDetails);
			customerDao.makePersistent(customerDetails);
		}else{
			if("engineering".equalsIgnoreCase(contactType)){
				building.setEnggContactDetails(contactDetails);
			}else if("maintenance".equalsIgnoreCase(contactType)){
				building.setMaintenanceContactDetails(contactDetails);
			}else if("security".equalsIgnoreCase(contactType)){
				building.setSecurityContactDetails(contactDetails);
			}else if("building".equalsIgnoreCase(contactType)){
				building.setBuildingContactDetails(contactDetails);
			}
			buildingDAO.makePersistent(building);
		}
		return true;
		
	}
	


}
