package com.vision.fpservices.db.daoImpl;

import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.ContactDAO;
import com.vision.fpservices.db.dao.UserDAO;
import com.vision.fpservices.db.model.ContactDetails;
import com.vision.fpservices.db.model.User;
import com.vision.fpservices.util.HibernateUtil;
@Repository("ContactDAO")
public class ContactDAOImpl extends GenericDaoImpl<ContactDetails, Integer> implements ContactDAO  {

	Logger log = Logger.getLogger("ContactDAO");

	public int saveOrUpdate(ContactDetails contact) {
		log.info("Going to save ContactDetails");
		Session session = getSessionFactory().getCurrentSession();
		int id = 0;
		if (contact.getContactId() == null || contact.getContactId() == 0) {
			id = (Integer) session.save(contact);
		} else {
			session.saveOrUpdate(contact);
			id = contact.getContactId();
		}
		contact.setContactId(id);
		log.info("Contact saved with id=" + id);

		return (int) id;
	}
	
	public ContactDetails loadContactDetails(int contactId) {
		log.info("Going to load Contact with ID --->"+contactId);
		Session session = getSessionFactory().getCurrentSession();
		ContactDetails contactDetails = (ContactDetails) session.get(ContactDetails.class,
				 contactId);
		return contactDetails;

	}



}
