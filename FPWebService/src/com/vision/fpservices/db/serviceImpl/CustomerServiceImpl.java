package com.vision.fpservices.db.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vision.fpservices.db.dao.CustomerDao;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.db.service.CustomerService;
import com.vision.fpservices.dto.CustomerBuildingSummaryDTO;
import com.vision.fpservices.dto.CustomerDTO;
import com.vision.fpservices.dto.CustomerStatisticsDTO;

@Repository("CustomerService")
@Transactional(readOnly=true)
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerDao customerDAO;
	
	public CustomerDTO  getCustomerDetails(Integer customerId){
		
		CustomerDetails  custDetails=customerDAO.loadCustomer(customerId);
		
		return (custDetails!=null?custDetails.convertToInternal():null);
		
	}
	
	
	public List<CustomerStatisticsDTO>  getCustomerStatistics(){
		
		return customerDAO.getCustomerStatistics();
		
	}
	
	@Transactional(readOnly=false)
	public boolean  createCustomer(String customerName, String createdBy){
		
		CustomerDetails customer=new CustomerDetails();
		customer.setCreatedBy(createdBy);
		customer.setCreatedDate(new Date());
		customer.setCustomerName(customerName);
		customer.setIsActive("Y");
		return customerDAO.makePersistent(customer).getCustomerId()>0?true:false;
		
	}
	@Transactional(readOnly=false)
	public boolean  updateCustomer(String customerName, String createdBy,Integer customerId){
		
		CustomerDetails customer=null;
		
		customer=customerDAO.loadCustomer(customerId);
		if(customer==null){
			return false;
		}
		customer.setUpdatedBy(createdBy);
		customer.setUpdatedDate(new Date());
		customer.setCustomerName(customerName);
		customer.setIsActive("Y");
		return customerDAO.makePersistent(customer).getCustomerId()>0?true:false;
		
	}
	
	public List<CustomerBuildingSummaryDTO> getAllCustomerBuildingsSummaryList(){
		return customerDAO.getAllCustomerBuildingsSummaryList();
	}
	
	

}
