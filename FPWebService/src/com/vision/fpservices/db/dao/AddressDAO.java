package com.vision.fpservices.db.dao;

import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.vision.fpservices.db.model.AddressDetails;
import com.vision.fpservices.db.model.AlarmNotificationSettings;
import com.vision.fpservices.db.model.ContactDetails;
import com.vision.fpservices.util.HibernateUtil;

public interface AddressDAO extends GenericDao<AddressDetails, Integer>{
	public int saveOrUpdate(AddressDetails address);
	public AddressDetails loadAddressDetails(int addressId) ;
}
