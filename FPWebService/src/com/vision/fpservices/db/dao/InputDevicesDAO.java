package com.vision.fpservices.db.dao;

import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.vision.fpservices.db.model.AddressDetails;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.Controller;
import com.vision.fpservices.db.model.InputDevices;
import com.vision.fpservices.util.HibernateUtil;

public interface InputDevicesDAO extends GenericDao<InputDevices, Integer>{

	public int saveOrUpdate(InputDevices inputDev) ;
	public InputDevices loadInputDevice(Integer inpDevId);

}
