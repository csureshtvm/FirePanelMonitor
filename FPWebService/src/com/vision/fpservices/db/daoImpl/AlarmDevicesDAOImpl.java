package com.vision.fpservices.db.daoImpl;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.AlarmDevicesDAO;
import com.vision.fpservices.db.model.AlarmDeviceReset;
import com.vision.fpservices.db.model.AlarmDevices;
import com.vision.fpservices.db.model.AlarmEvents;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.dto.AlarmDeviceResetDto;
import com.vision.fpservices.dto.AlarmDevicesDto;
import com.vision.fpservices.dto.AlarmEventDTO;
import com.vision.fpservices.util.HibernateUtil;
@Repository("AlarmDevicesDAO") 
public class AlarmDevicesDAOImpl extends GenericDaoImpl<AlarmDevices, String> implements AlarmDevicesDAO{

	Logger log = Logger.getLogger("AlarmDevicesDaoImpl");
	
	public AlarmDevices loadAlarmDevice(String alarmDeviceId) {
		log.info("Going to load AlarmDevice with ID --->"+alarmDeviceId);
		Session session = getSessionFactory().getCurrentSession();
		AlarmDevices alarmDevice = (AlarmDevices) session.get(AlarmDevices.class,
				 alarmDeviceId);
		return alarmDevice;

	}
	
	public String saveOrUpdate(AlarmDevices alarmDevice) {
		log.info("Going to update AlarmEvent");
		Session session = getSessionFactory().getCurrentSession();
		int id = 0;
		session.saveOrUpdate(alarmDevice);
		return alarmDevice.getDeviceId();
	}
	
	
	public List<AlarmDevicesDto> getAllAlarmDevices(String customerId){
		
		Session session = getSessionFactory().getCurrentSession();
		String queryStr="select ad.DEVICE_ID as deviceId,ad.DEVICE_NAME as deviceName," +
				"ad.DEVICE_LOC as deviceLoc,ad.installed_floor_no as installedFloorNo," +
				"CAST(DATE_FORMAT(ad.last_message_received_time,'%d %b %Y %h:%i:%s') as CHAR) as lastMessageReceivedTime,bd.BUILDING_ID as buildingId," +
				"bd.BUILDING_NAME as buildingName,cd.CUSTOMER_ID as customerId,cd.CUSTOMER_NAME as  customerName," +
				"(case when ad.last_message_received_time is null then '-101' else" +
				" (case when DATEDIFF(CURRENT_DATE,ad.last_message_received_time)<=1 then '100' else '-102' END) end) as communicationStatus" +
				" from ALARM_DEVICES ad left join building_details bd on " +
				"bd.BUILDING_ID=ad.BUILDING_ID left join fp_customer_details cd on cd.CUSTOMER_ID=bd.CUSTOMER_ID" +
				" where bd.IS_ACTIVE='Y' and ad.IS_ACTIVE='Y' " +
				((customerId!=null && !customerId.trim().isEmpty() && !("null".equalsIgnoreCase(customerId)) && !"0".equals(customerId.trim()))?(" and cd.CUSTOMER_ID='"+customerId.trim()+"'"):"")+" order by cd.CUSTOMER_ID,bd.BUILDING_ID,ad.INSTALLED_FLOOR_NO";
		SQLQuery query=session.createSQLQuery(queryStr);
		query.addScalar("deviceId",StandardBasicTypes.STRING).addScalar("deviceName",StandardBasicTypes.STRING).
		addScalar("deviceLoc",StandardBasicTypes.STRING).addScalar("installedFloorNo",StandardBasicTypes.STRING)
		.addScalar("lastMessageReceivedTime",StandardBasicTypes.STRING).addScalar("buildingId",StandardBasicTypes.STRING)
		.addScalar("buildingName",StandardBasicTypes.STRING).addScalar("customerId",StandardBasicTypes.STRING)
		.addScalar("customerName",StandardBasicTypes.STRING).addScalar("communicationStatus",StandardBasicTypes.STRING) ;
	
		System.out.println(queryStr);
		query.setResultTransformer(Transformers.aliasToBean(AlarmDevicesDto.class));
		List<AlarmDevicesDto> resultList=query.list();
		return resultList;
	}
	
	public AlarmDevices getAlarmDeviceIdFromBuildingDetails(Integer buildingId,String floorNo){
		
		Session session = getSessionFactory().getCurrentSession();
		String queryStr="from AlarmDevices dev where dev.building.buildingId=:arg_bldg_id and " +
				"dev.installedFloorNo=:arg_floor_no ";
		Query query=session.createQuery(queryStr);
		query.setParameter("arg_bldg_id", buildingId);
		query.setParameter("arg_floor_no", floorNo);	
		
		List<AlarmDevices> resultList=(List<AlarmDevices>)query.list();
		
		return (resultList!=null && resultList.size()>0)?resultList.get(0):null;
	}
}
