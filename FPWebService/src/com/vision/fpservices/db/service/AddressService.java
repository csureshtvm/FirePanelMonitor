package com.vision.fpservices.db.service;

import java.util.Date;
import java.util.logging.Logger;

import com.vision.fpservices.db.dao.AddressDAO;
import com.vision.fpservices.db.dao.BuildingDAO;
import com.vision.fpservices.db.dao.ContactDAO;
import com.vision.fpservices.db.dao.CustomerDao;
import com.vision.fpservices.db.model.AddressDetails;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.ContactDetails;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.dto.AddressDetailsDTO;
import com.vision.fpservices.dto.ContactDetailsDTO;

public interface AddressService {	

	public boolean saveOrUpdate(AddressDetailsDTO address,String addressType,String buildingId,String customerId, String updatedBy);
	
}
