package com.vision.fpservices.db.serviceImpl;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vision.fpservices.db.dao.AddressDAO;
import com.vision.fpservices.db.dao.BuildingDAO;
import com.vision.fpservices.db.dao.CustomerDao;
import com.vision.fpservices.db.model.AddressDetails;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.db.service.AddressService;
import com.vision.fpservices.dto.AddressDetailsDTO;
@Repository("AddressService")
@Transactional(readOnly=true)
public class AddressServiceImpl implements AddressService{
	


	@Autowired
	AddressDAO addressDAO;
	@Autowired
	BuildingDAO buildingDAO;
	@Autowired
	CustomerDao customerDao;
	Logger log = Logger.getLogger("ContactService");
	
	@Transactional(readOnly=false)
	public boolean saveOrUpdate(AddressDetailsDTO address,String addressType,String buildingId,String customerId, String updatedBy){
		Building building=buildingDAO.loadBuilding(Integer.parseInt(buildingId));
		CustomerDetails customerDetails=null;
		AddressDetails addressDetails=null;
		if("building".equalsIgnoreCase(addressType)){
			addressDetails=building.getAddressDetails();
		}else if("customer".equalsIgnoreCase(addressType)){
			customerDetails=customerDao.loadCustomer(Integer.valueOf(customerId));
			addressDetails=customerDetails.getCustomerAddressDetails();
		}
		if(addressDetails==null ){
			addressDetails=new AddressDetails();
			if("customer".equalsIgnoreCase(addressType)){
				addressDetails.setCustomerDetails(customerDetails);
			}else{
				addressDetails.setBuilding(building);
			}
			addressDetails.setCreatedBy(updatedBy);
			addressDetails.setCreatedDate(new Date());
		}else{
			addressDetails.setUpdatedBy(updatedBy);
			addressDetails.setUpdatedDate(new Date());
		}
		addressDetails.setAddressType(address.getAddressType());
		addressDetails.setAddressFirstLine(address.getAddressFirstLine());
		addressDetails.setAddressSecondLine(address.getAddressSecondLine());
		addressDetails.setTown(address.getTown());
		addressDetails.setCountry(address.getCountry());
		addressDetails.setCounty(address.getCounty());
		addressDetails.setPostalCode(address.getPostalCode());
		
		int response=addressDAO.makePersistent(addressDetails).getAddressId();
		System.out.println("**************************Saved Contact ID:"+addressDetails.getAddressId());
		if("customer".equalsIgnoreCase(addressType)){
			customerDetails.setCustomerAddressDetails(addressDetails);
			customerDao.makePersistent(customerDetails);
		}else if("building".equalsIgnoreCase(addressType)){
				building.setAddressDetails(addressDetails);
				buildingDAO.makePersistent(building);
		}
		return true;
		
	}
	
	
	




}
