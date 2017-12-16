package com.vision.fpservices.db.daoImpl;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.DeviceHistoryDAO;
import com.vision.fpservices.db.model.AlarmNotificationSettings;
import com.vision.fpservices.db.model.DeviceStatusHistory;
import com.vision.fpservices.util.HibernateUtil;
@Repository("DeviceHistoryDAO") 
public class DeviceHistoryDAOImpl extends GenericDaoImpl<DeviceStatusHistory, Integer>  implements DeviceHistoryDAO{

	Logger log = Logger.getLogger("DeviceHistoryDAO");

	public int saveOrUpdate(DeviceStatusHistory devStatHist) {
		log.info("Going to save DeviceHistory");
		Session session = getSessionFactory().getCurrentSession();
		int id = (Integer) session.save(devStatHist);
		log.info("DeviceStatusHistory saved with id=" + id);
		return (int) id;
	}

	public List<DeviceStatusHistory> getAllDeviceHistory(int deviceId) {
		log.info("Going to load all device history");
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(DeviceStatusHistory.class);
		criteria.add(Restrictions.eq("deviceId", deviceId));
		//criteria.uniqueResult();
		List<DeviceStatusHistory> devStatHistList = criteria.list();
		System.out.println(devStatHistList);
		return devStatHistList;

	}



}
