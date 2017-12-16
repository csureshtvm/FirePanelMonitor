package com.vision.fpservices.db.daoImpl;

import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.AddressDAO;
import com.vision.fpservices.db.dao.InputDevicesDAO;
import com.vision.fpservices.db.model.AddressDetails;
import com.vision.fpservices.db.model.InputDevices;
import com.vision.fpservices.util.HibernateUtil;
@Repository("InputDevicesDAO")
public class InputDevicesDAOImpl extends GenericDaoImpl<InputDevices, Integer> implements InputDevicesDAO {

	Logger log = Logger.getLogger("InputDevicesDAO");

	public int saveOrUpdate(InputDevices inputDev) {
		log.info("Going to save InputDevice");
		Session session = getSessionFactory().getCurrentSession();
		int id = 0;
		if (inputDev.getInputDeviceId() == 0) {
			id = (Integer) session.save(inputDev);
		} else {
			session.saveOrUpdate(inputDev);
			id = inputDev.getInputDeviceId();
		}
		log.info("InputDevice saved with id=" + id);
		return id;
	}

	public InputDevices loadInputDevice(Integer inpDevId) {
		log.info("Going to load InputDevice with ID --->"+inpDevId);
		Session session = getSessionFactory().getCurrentSession();
		InputDevices inpDev = (InputDevices) session.get(InputDevices.class,
				inpDevId);
		return inpDev;

	}



}
