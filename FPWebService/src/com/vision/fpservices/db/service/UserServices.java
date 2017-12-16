package com.vision.fpservices.db.service;

import java.sql.Date;

import com.test.utils.ServerContextHelper;
import com.vision.fpservices.db.dao.CustomerDao;
import com.vision.fpservices.db.dao.UserDAO;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.db.model.User;
import com.vision.fpservices.db.model.User.UserStatus;
import com.vision.fpservices.dto.UserDTO;
import com.vision.fpservices.util.AppConstants;

public interface UserServices {
	
	public Object loginUser(String userName,String password);
	
	public boolean createNewUser(UserDTO userDTO,String createdBy);
	
	public boolean updatePassword(String userName,String password);
	

}
