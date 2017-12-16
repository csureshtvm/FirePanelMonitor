package com.vision.fpservices.db.service;

import java.util.Date;
import java.util.List;

import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.dto.CustomerBuildingSummaryDTO;
import com.vision.fpservices.dto.CustomerDTO;
import com.vision.fpservices.dto.CustomerStatisticsDTO;

public interface CustomerService {

	public CustomerDTO  getCustomerDetails(Integer customerId);
	public List<CustomerStatisticsDTO>  getCustomerStatistics();
	
	public List<CustomerBuildingSummaryDTO> getAllCustomerBuildingsSummaryList();
	public boolean  createCustomer(String customerName, String createdBy);
	public boolean  updateCustomer(String customerName, String createdBy,Integer customerId);
}
