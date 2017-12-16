package com.vision.fpservices.db.daoImpl;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.NotfnSettingsDAO;
import com.vision.fpservices.db.model.AlarmEvents;
import com.vision.fpservices.db.model.AlarmNotificationSettings;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.dto.AlarmEventDTO;
import com.vision.fpservices.util.HibernateUtil;
@Repository("NotfnSettingsDAO") 
public class NotfnSettingsDAOImpl extends GenericDaoImpl<AlarmNotificationSettings, Integer> implements NotfnSettingsDAO {
	Logger log = Logger.getLogger("NotfnSettingsDAOImpl");
	public int saveOrUpdate(AlarmNotificationSettings notfnSettings) {
		log.info("Going to update notfnSettings");
		Session session = getSessionFactory().getCurrentSession();
		int id = 0;
		if (notfnSettings.getNotificationSettingId() == null || notfnSettings.getNotificationSettingId()==0) {
			id = (Integer) session.save(notfnSettings);
		} else {
			session.saveOrUpdate(notfnSettings);
			id = notfnSettings.getNotificationSettingId();
		}
		notfnSettings.setNotificationSettingId(id);
		log.info("notfnSettings saved with id=" + id);

		return (int) id;
	}

	public List<AlarmNotificationSettings> getAllNotfnSettingsOfBuilding(Integer buildingId ){
		
		Session session = getSessionFactory().getCurrentSession();
		//session.clear();
		//SQLQuery query=session.createSQLQuery("from AlarmEvents a where a.building.customerDetails.customerId=:arg_cust_id");
		
		Query query=session.createQuery("from AlarmNotificationSettings notfnSet where notfnSet.building.buildingId=:arg_building_id");
		
		query.setParameter("arg_building_id", buildingId);
			
		
		List<AlarmNotificationSettings> alarmNotfnList = query.list();
		System.out.println("Results size ====>"+alarmNotfnList.size());
		
		return alarmNotfnList;
	}
}
