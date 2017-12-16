package com.vision.fpservices.db.daoImpl;

import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.AddressDAO;
import com.vision.fpservices.db.dao.NotfnSettingsDAO;
import com.vision.fpservices.db.model.AddressDetails;
import com.vision.fpservices.db.model.AlarmNotificationSettings;
import com.vision.fpservices.util.HibernateUtil;
@Repository("AddressDAO") 
public class AddressDAOImpl extends GenericDaoImpl<AddressDetails, Integer> implements AddressDAO{
	Logger log = Logger.getLogger("ContactDAO");

	public int saveOrUpdate(AddressDetails address) {
		log.info("Going to save ContactDetails");
		Session session = getSessionFactory().getCurrentSession();
		int id = 0;
		if (address.getAddressId() == null || address.getAddressId() == 0) {
			id = (Integer) session.save(address);
		} else {
			session.saveOrUpdate(address);
			id = address.getAddressId();
		}
		address.setAddressId(id);
		log.info("Address saved with id=" + id);
		return (int) id;
	}
	
	public AddressDetails loadAddressDetails(int addressId) {
		log.info("Going to load Address with ID --->"+addressId);
		Session session = getSessionFactory().getCurrentSession();
		 AddressDetails addressDetails = (AddressDetails) session.get(AddressDetails.class,
				 addressId);
		return addressDetails;

	}
}
