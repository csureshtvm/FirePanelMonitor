package com.vision.fpservices.db.daoImpl;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;


import com.vision.fpservices.db.dao.AlarmEventResetDAO;
import com.vision.fpservices.db.model.AlarmDeviceReset;
import com.vision.fpservices.dto.AlarmDeviceResetDto;

@Repository("AlarmEventResetDAO") 
public class AlarmEventResetDAOImpl extends GenericDaoImpl<AlarmDeviceReset, Integer> implements AlarmEventResetDAO {
	Logger log = Logger.getLogger("AlarmDevicesDaoImpl");
public List<AlarmDeviceResetDto> getAllAlarmResetDevices(){
		
		Session session = getSessionFactory().getCurrentSession();
		String queryStr="select ad.DEVICE_ID as deviceId,bd.BUILDING_ID as buildingId," +
				"cd.CUSTOMER_ID as customerId,ad.INSTALLED_FLOOR_NO as floorNo,'N' as resetStatus" +
				" from ALARM_DEVICE_RESET adr join ALARM_DEVICES ad on adr.DEVICE_ID=ad.DEVICE_ID " +
				"left join building_details bd on bd.BUILDING_ID=ad.BUILDING_ID " +
				"left join fp_customer_details cd on cd.CUSTOMER_ID=bd.CUSTOMER_ID" +
				" where bd.IS_ACTIVE='Y' and ad.IS_ACTIVE='Y' and (adr.RESET_STATUS is null or adr.RESET_STATUS='N' ) " +
				" order by cd.CUSTOMER_ID,bd.BUILDING_ID,ad.INSTALLED_FLOOR_NO";
		SQLQuery query=session.createSQLQuery(queryStr);
		query.addScalar("deviceId",StandardBasicTypes.STRING).addScalar("buildingId",StandardBasicTypes.STRING).
		addScalar("customerId",StandardBasicTypes.STRING).addScalar("floorNo",StandardBasicTypes.STRING)
		.addScalar("resetStatus",StandardBasicTypes.STRING) ;
	
		query.setResultTransformer(Transformers.aliasToBean(AlarmDeviceResetDto.class));
		List<AlarmDeviceResetDto> resultList=query.list();
		return resultList;
	}
	
	public boolean updateAlarmResetDeviceSatus(String alarmDeviceId,String resetStatus){
		
		Session session = getSessionFactory().getCurrentSession();
		String queryStr="update ALARM_DEVICE_RESET set RESET_STATUS=:arg_reset_status,reset_dtm=:arg_currtime " +
				" where device_id=:arg_device_id and (RESET_STATUS is null or RESET_STATUS='N') ";
		SQLQuery query=session.createSQLQuery(queryStr);
		query.setParameter("arg_currtime", new Date());
		query.setParameter("arg_device_id", alarmDeviceId);
		query.setParameter("arg_reset_status", resetStatus);
	
		
		int result=query.executeUpdate();
		return result>0?true:false;
	}
	
	
	public int saveOrUpdateAlarmDeviceResetRequest(AlarmDeviceReset alarmDeviceReset) {
		log.info("Going to update AlarmEvent");
		Session session = getSessionFactory().getCurrentSession();
		int id = 0;
		if (alarmDeviceReset.getDeviceRequestId() == null || alarmDeviceReset.getDeviceRequestId()==0) {
			id = (Integer) session.save(alarmDeviceReset);
		} else {
			session.saveOrUpdate(alarmDeviceReset);
			id = alarmDeviceReset.getDeviceRequestId();
		}
		alarmDeviceReset.setDeviceRequestId(id);
		log.info("Alarm Device reset saved with id=" + id);

		return (int) id;
	}
	
	
	
}
