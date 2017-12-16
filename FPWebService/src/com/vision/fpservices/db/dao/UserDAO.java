package com.vision.fpservices.db.dao;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.db.model.InputDevices;
import com.vision.fpservices.db.model.User;
import com.vision.fpservices.dto.AlarmEventDTO;
import com.vision.fpservices.dto.UserDTO;
import com.vision.fpservices.util.HibernateUtil;


public interface UserDAO extends GenericDao<User, Integer>{
	
	public UserDTO getLoginUserDetails(String userName);
	public String createNewUser(User user) ;
	public User loadUserDetails(String userName);
	public boolean updateUser(User user);
}

