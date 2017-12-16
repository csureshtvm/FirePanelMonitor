package com.vision.fpservices.db.dao;

import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.ContactDetails;
import com.vision.fpservices.db.model.Controller;
import com.vision.fpservices.db.model.User;
import com.vision.fpservices.util.HibernateUtil;

public interface ContactDAO extends GenericDao<ContactDetails, Integer>{

	public int saveOrUpdate(ContactDetails contact) ;
	
	public ContactDetails loadContactDetails(int contactId);
}
