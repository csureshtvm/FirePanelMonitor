package com.vision.fpservices.db.serviceImpl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vision.fpservices.db.dao.CustomerDao;
import com.vision.fpservices.db.dao.UserDAO;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.db.model.User;
import com.vision.fpservices.db.model.User.UserStatus;
import com.vision.fpservices.db.service.UserServices;
import com.vision.fpservices.dto.UserDTO;
import com.vision.fpservices.util.AppConstants;

@Repository("UserServices")
@Transactional(readOnly=true)
public class UserServicesImpl implements UserServices{
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	CustomerDao customerDao;
	
	public Object loginUser(String userName,String password){
		
		UserDTO userDTO=userDao.getLoginUserDetails(userName);
		if(userDTO!=null){
			if(password==null || !password.equals(userDTO.getPassword())){
				return AppConstants.LOGIN_ERROR_CODE_INCORRECT_PW;
			}else if(!"ACTIVE".equalsIgnoreCase(userDTO.getUserStatus())){
				return AppConstants.LOGIN_ERROR_CODE_INACTIVEUSER;
			}else{
				userDTO.setPassword(null);
				return userDTO;
			}
			
			
		}else{
			return AppConstants.LOGIN_ERROR_CODE_INVALID_USER;
		}
		
		
	}
	@Transactional(readOnly=false)
	public boolean createNewUser(UserDTO userDTO,String createdBy){
		
		User user=new User();
		user.setCreatedBy(createdBy);
		user.setCreatedDate(new Date(System.currentTimeMillis()));
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setPassword(userDTO.getUserName());
		user.setUserName(userDTO.getUserName());
		user.setUserRole(userDTO.getUserRole());
		user.setStatus(UserStatus.ACTIVE);
		user.setUserEmail(userDTO.getUserEmail());
		user.setUserCustomerMapping(userDTO.getUserCustomerMapping());
		
		if("NORMAL USER".equalsIgnoreCase(userDTO.getUserRole())){
		CustomerDetails	customerDetails=customerDao.loadCustomer(userDTO.getCustomerId());
		user.setCustomerDetails(customerDetails);
		}
		return userDTO.getUserName().equalsIgnoreCase(userDao.createNewUser(user))?true:false;
		//userDao.s
		
		
	}
	
	@Transactional(readOnly=false)
	public boolean updatePassword(String userName,String password){
		
		User user=userDao.loadUserDetails(userName);
		if(user!=null){
			user.setPassword(password);
			user.setUpdatedDate(new Date(System.currentTimeMillis()));
			return userDao.updateUser(user);
		}else{
			return false;
		}
		
		
		
	}
	
	
	



}
