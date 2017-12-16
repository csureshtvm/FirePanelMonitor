package com.vision.fpservices.db.dao;

import java.util.List;

import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.dto.CustomerBuildingSummaryDTO;
import com.vision.fpservices.dto.CustomerStatisticsDTO;

public interface CustomerDao extends GenericDao<CustomerDetails, Integer>{
	
	public CustomerDetails loadCustomer(Integer customerId);
	public int saveOrUpdate(CustomerDetails customer);
	public List<CustomerStatisticsDTO> getCustomerStatistics();
	
	public List<CustomerBuildingSummaryDTO> getAllCustomerBuildingsSummaryList();

}
