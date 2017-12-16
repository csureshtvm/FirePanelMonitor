package com.vision.fpservices.db.daoImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.AlarmEventDao;
import com.vision.fpservices.db.model.AlarmEvents;
import com.vision.fpservices.db.model.AlarmNotfnHistory;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.dto.AlarmEventDTO;
import com.vision.fpservices.dto.AlarmEventStatisticsDTO;
import com.vision.fpservices.dto.AlarmEventWithContactsDTO;
import com.vision.fpservices.dto.AlarmNotfnEventDetailsDTO;
import com.vision.fpservices.dto.CustomerStatisticsDTO;
import com.vision.fpservices.dto.UserDTO;
import com.vision.fpservices.util.HibernateUtil;


@Repository("AlarmEventDao") 
public class AlarmEventDaoImpl extends GenericDaoImpl<AlarmEvents, Integer> implements AlarmEventDao 
{
	//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	/*public AlarmEventDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/
	Logger log = Logger.getLogger("AlarmEventDaoImpl");
	public AlarmEventDaoImpl(){
		
	}
	
	public List<AlarmEventDTO> getAllalarmEvents(String customerId){
		Session session = getSessionFactory().getCurrentSession();
		//session.clear();
		//SQLQuery query=session.createSQLQuery("from AlarmEvents a where a.building.customerDetails.customerId=:arg_cust_id");
		
		//Transaction tx1 = session.beginTransaction();
		SQLQuery query=session.createSQLQuery("SELECT a.alarm_event_id AS alarmEventId, a.building_id AS buildingId," +
				" a.event_details AS eventDetails, a.event_generated_time AS eventGeneratedTime," +
				" a.event_type AS eventType, a.is_test_alarm AS isTestAlarm,a.is_active as isActive," +
				" a.sms_notified as smsNotified, a.email_notified as emailNotified,b.building_name as buildingName," +
				" a.building_floor_no as buildingFloorNo,b.no_of_floor as bldgNoOfFloors," +
				" c.address_first_line as bldgAddressFirstLine,c.address_second_line as bldgAddressSecondLine," +
				" c.town as bldgTown, c.country as bldgCountry, a.event_system as eventSystem,a.event_signal as eventSignal, " +
				"(case when at.alarm_type_mapping is null then a.event_value else at.alarm_type_mapping end) as eventValue" +
				" FROM alarm_events a left join " +
				" building_details b on b.building_id=a.building_id " +
				"left join address_details c on c.address_id=b.building_address_id " +
				" left outer join alarm_types at on a.event_system=at.alarm_type_system and " +
				"a.event_signal=at.alarm_type_signal and a.event_value=at.alarm_type_value " +
				"WHERE b.customer_id=:arg_cust_id " +
				" and a.is_active=:arg_active and (at.show_in_ui is null or at.show_in_ui ='Y' ) order by a.event_generated_time asc ");
		
		//query.setParameter("arg_customerId", customerId);
		
		//Criteria criteria = session.createCriteria(Building.class);
		//criteria.uniqueResult();
		query.setParameter("arg_cust_id", customerId);
		query.setParameter("arg_active", "Y");
		query.addScalar("alarmEventId",StandardBasicTypes.INTEGER).addScalar("buildingId").addScalar("eventDetails").
		addScalar("eventType").addScalar("isActive").addScalar("smsNotified").addScalar("eventGeneratedTime").
		addScalar("emailNotified").addScalar("buildingName").addScalar("buildingFloorNo",StandardBasicTypes.INTEGER)
		.addScalar("bldgNoOfFloors",StandardBasicTypes.INTEGER).addScalar("bldgAddressFirstLine").addScalar("bldgAddressSecondLine")
		.addScalar("bldgTown").addScalar("bldgCountry").addScalar("eventSystem").addScalar("eventSignal").addScalar("eventValue");
		
		query.setResultTransformer(Transformers.aliasToBean(AlarmEventDTO.class));
		List<AlarmEventDTO> alarmEvents = query.list();
		System.out.println("Results size ====>"+alarmEvents);
		//tx1.commit();
		//session.close();
		System.out.println("Results size ====>"+alarmEvents.size());
		return alarmEvents;
		
		
	}
	
	
	public AlarmEvents loadAlarmEvent(Integer alarmEventId) {
		log.info("Going to load Customer with ID --->"+alarmEventId);
		Session session = getSessionFactory().getCurrentSession();
		  AlarmEvents alarmEvent = (AlarmEvents) session.get(AlarmEvents.class,
				 alarmEventId);
		//session.close();
		return alarmEvent;

	}
	
	public int saveOrUpdate(AlarmEvents alarmEvent) {
		log.info("Going to update AlarmEvent");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		int id = 0;
		if (alarmEvent.getAlarmEventId() == null || alarmEvent.getAlarmEventId()==0) {
			id = (Integer) session.save(alarmEvent);
		} else {
			session.saveOrUpdate(alarmEvent);
			id = alarmEvent.getAlarmEventId();
		}
		alarmEvent.setAlarmEventId(id);
		log.info("Contact saved with id=" + id);

		tx1.commit();
		//session.close();
		return (int) id;
	}
	/*public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/
	public List<AlarmNotfnEventDetailsDTO> getNotfnpendingEventList(){
		Session session =  getSessionFactory().getCurrentSession();
		//session.clear();
		//SQLQuery query=session.createSQLQuery("from AlarmEvents a where a.building.customerDetails.customerId=:arg_cust_id");
		
		SQLQuery query=session.createSQLQuery("SELECT a.alarm_event_id AS eventId, b.building_name AS buildingName," +
				" a.event_system AS eventDetails, CONVERT(DATE_FORMAT(a.event_generated_time,'%Y-%m-%d %T'),CHAR) AS eventGeneratedTime," +
				" (case when at.alarm_type_mapping is null then a.event_value else at.alarm_type_mapping end) AS eventType,notfnSettings.notification_type as notfnType," +
				" notfnSettings.notify_customer as notifyCustomer,notfnSettings.notify_building_contact as notifyBuildingContact,  " +
				" notfnSettings.notify_maintenance_contact as notifyMaintenanceContact,notfnSettings.notify_engg_contact as notifyEnggContact,  " +
				" notfnSettings.notify_security_contact as notifysecurityContact," +
				" buildingContact.phone_primary as buildingContactPhone,buildingContact.email as buildingContactEmail,  " +
				" enggContact.phone_primary as enggContactPhone,enggContact.email as enggContactEmail,  " +
				" securityContact.phone_primary as securityContactPhone,securityContact.email as securityContactEmail,  " +
				" maintenanceContact.phone_primary as maintenanceContactPhone,maintenanceContact.email as maintenanceContactEmail,  " +
				" customerContact.phone_primary as customerContactPhone,customerContact.email as customerContactEmail,  " +
				" customerContact.contact_id as customerContactId,buildingContact.contact_id as buildingContactId,  " +
				" maintenanceContact.contact_id as maintenanceContactId,enggContact.contact_id as enggContactId,  " +
				" securityContact.contact_id as securityContactId " +
				" FROM alarm_events a join " +
				" building_details b on a.building_id=b.building_id " +
				" left join fp_customer_details c on c.customer_id=b.customer_id  " +
				" left join alarm_notification_settings notfnSettings on notfnSettings.building_id=b.building_id " +				
				" left join contact_details buildingContact on buildingContact.contact_id=b.building_contact_id " +
				" left join contact_details enggContact on enggContact.contact_id=b.engineering_contact_id " +
				" left join contact_details securityContact on securityContact.contact_id=b.security_contact_id " +
				" left join contact_details maintenanceContact on maintenanceContact.contact_id=b.maintenance_contact_id " +
				" left join contact_details customerContact on customerContact.contact_id=c.customer_contact_id " +
				" left outer join alarm_types at on a.event_system=at.alarm_type_system and " +
				"a.event_signal=at.alarm_type_signal and a.event_value=at.alarm_type_value " +
				" WHERE a.is_active=:arg_active and a.event_type='FIRE' and ((notfnSettings.notification_type='SMS' and a.sms_notified is null) or (notfnSettings.notification_type='EMAIL' and a.email_notified is null)) " +
				"order by a.event_generated_time asc");
		
		query.setParameter("arg_active", "Y");
		query.addScalar("eventId",StandardBasicTypes.INTEGER).addScalar("buildingName").addScalar("eventDetails").
		addScalar("eventType").addScalar("eventGeneratedTime").addScalar("notfnType").
		addScalar("notifyCustomer").addScalar("notifyBuildingContact").addScalar("notifyMaintenanceContact").
		addScalar("notifyEnggContact").addScalar("notifysecurityContact").addScalar("buildingContactPhone").
		addScalar("buildingContactEmail").addScalar("enggContactPhone").addScalar("enggContactEmail").
		addScalar("securityContactPhone").addScalar("securityContactEmail").addScalar("maintenanceContactPhone").
		addScalar("maintenanceContactEmail").addScalar("customerContactPhone").addScalar("customerContactEmail").
		addScalar("buildingContactId",StandardBasicTypes.INTEGER).addScalar("customerContactId",StandardBasicTypes.INTEGER).addScalar("maintenanceContactId",StandardBasicTypes.INTEGER).
		addScalar("enggContactId",StandardBasicTypes.INTEGER).addScalar("securityContactId",StandardBasicTypes.INTEGER);
		
		
		
		
		
		query.setResultTransformer(Transformers.aliasToBean(AlarmNotfnEventDetailsDTO.class));
		List<AlarmNotfnEventDetailsDTO> alarmEventList = query.list();
		return alarmEventList;
		
		
	}
	public int saveOrUpdate(AlarmNotfnHistory alarmotfnHistory) {
		log.info("Going to update AlarmEvent");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		int id = 0;
		if (alarmotfnHistory.getAlarmNotfnEventId() == null || alarmotfnHistory.getAlarmNotfnEventId()==0) {
			id = (Integer) session.save(alarmotfnHistory);
		} else {
			session.saveOrUpdate(alarmotfnHistory);
			id = alarmotfnHistory.getAlarmNotfnEventId();
		}
		alarmotfnHistory.setAlarmNotfnEventId(id);
		log.info("Contact saved with id=" + id);

		tx1.commit();
		//session.close();
		return (int) id;
	}
	public List<AlarmEventStatisticsDTO> getAlarmEventStatistics(){
		Session session =  getSessionFactory().getCurrentSession();
		/*SQLQuery query=session.createSQLQuery(" SELECT c.customer_id,c.customer_name," +
				"a.event_generated_time,a.event_type FROM fp_customer_details c " +
				"JOIN building_details b ON b.customer_id=c.customer_id JOIN alarm_events a " +
				"ON a.building_id=b.building_id ");*/
		
		/*SQLQuery query=session.createSQLQuery(" SELECT c.customer_id,c.customer_name," +
		"a.event_generated_time,a.event_type FROM fp_customer_details c " +
		"JOIN building_details b ON b.customer_id=c.customer_id JOIN alarm_events a " +
		"ON a.building_id=b.building_id ");*/
		
		SQLQuery query=session.createSQLQuery("SELECT DISTINCT(c.customer_id) as customerId,c.customer_name as customerName,COUNT(*) as noOfEvents FROM fp_customer_details c JOIN building_details b ON b.customer_id=c.customer_id JOIN alarm_events a ON a.building_id=b.building_id WHERE a.event_generated_time>=(CURRENT_DATE-180) GROUP BY c.customer_id");
		
		query.addScalar("customerId",StandardBasicTypes.INTEGER).addScalar("customerName").addScalar("noOfEvents",StandardBasicTypes.INTEGER);
			
		
		query.setResultTransformer(Transformers.aliasToBean(AlarmEventStatisticsDTO.class));
		List<AlarmEventStatisticsDTO> alarmEventStatsList = query.list();
		System.out.println("Result Size---------"+alarmEventStatsList.size());
		
		return alarmEventStatsList;
		
		
	}
	
	public List<AlarmEventStatisticsDTO> getAlarmEventStatistics1(){
		Session session =  getSessionFactory().getCurrentSession();
		/*SQLQuery query=session.createSQLQuery(" SELECT c.customer_id,c.customer_name," +
				"a.event_generated_time,a.event_type FROM fp_customer_details c " +
				"JOIN building_details b ON b.customer_id=c.customer_id JOIN alarm_events a " +
				"ON a.building_id=b.building_id ");*/
		
		/*SQLQuery query=session.createSQLQuery(" SELECT c.customer_id,c.customer_name," +
		"a.event_generated_time,a.event_type FROM fp_customer_details c " +
		"JOIN building_details b ON b.customer_id=c.customer_id JOIN alarm_events a " +
		"ON a.building_id=b.building_id ");*/
		
		SQLQuery query=session.createSQLQuery("SELECT c.customer_id AS customerId,c.customer_name AS customerName," +
				"a.event_type as eventType,a.event_generated_time AS eventGeneratedTime FROM fp_customer_details c " +
" JOIN building_details b ON b.customer_id=c.customer_id LEFT OUTER JOIN alarm_events a ON a.building_id=b.building_id and " +
" (a.event_generated_time BETWEEN (NOW()-INTERVAL 90 DAY) AND NOW()) order BY c.customer_id");
		
		query.addScalar("customerId",StandardBasicTypes.INTEGER).addScalar("customerName").addScalar("eventGeneratedTime").addScalar("eventType");
			
		
		query.setResultTransformer(Transformers.aliasToBean(AlarmEventStatisticsDTO.class));
		List<AlarmEventStatisticsDTO> alarmEventStatsList = query.list();
		System.out.println("Result Size---------"+alarmEventStatsList.size());
		
		return alarmEventStatsList;
		
		
	}
	
	public List<AlarmEventDTO> getAllalarmEvents(){
		Session session =  getSessionFactory().getCurrentSession();
		SQLQuery query=session.createSQLQuery("SELECT a.alarm_event_id AS alarmEventId, a.building_id AS buildingId," +
				" a.event_details AS eventDetails, a.event_generated_time AS eventGeneratedTime," +
				" a.event_type AS eventType, a.is_test_alarm AS isTestAlarm,a.is_active as isActive," +
				" a.sms_notified as smsNotified, a.email_notified as emailNotified,b.building_name as buildingName," +
				" a.building_floor_no as buildingFloorNo,b.no_of_floor as bldgNoOfFloors," +
				" c.address_first_line as bldgAddressFirstLine,c.address_second_line as bldgAddressSecondLine," +
				" c.town as bldgTown, c.country as bldgCountry, cust.customer_id as customerId," +
				" cust.customer_name as customerName, a.event_system as eventSystem,a.event_signal as eventSignal, " +
				"(case when at.alarm_type_mapping is null then a.event_value else at.alarm_type_mapping end) as eventValue" +
				" FROM alarm_events a left join " +
				" building_details b on b.building_id=a.building_id " +
				"left join address_details c on c.address_id=b.building_address_id " +
				"left join fp_customer_details cust on b.customer_id=cust.customer_id " +
				" left outer join alarm_types at on a.event_system=at.alarm_type_system and " +
				"a.event_signal=at.alarm_type_signal and a.event_value=at.alarm_type_value " +
				"WHERE a.is_active=:arg_active and a.event_generated_time > (CURRENT_DATE - 7) and (at.show_in_ui is null or at.show_in_ui ='Y' ) order by a.event_generated_time asc");
		
		//query.setParameter("arg_customerId", customerId);
		
		//Criteria criteria = session.createCriteria(Building.class);
		//criteria.uniqueResult();
		query.setParameter("arg_active", "Y");
		query.addScalar("alarmEventId",StandardBasicTypes.INTEGER).addScalar("buildingId").addScalar("eventDetails").
		addScalar("eventType").addScalar("isActive").addScalar("smsNotified").addScalar("eventGeneratedTime").
		addScalar("emailNotified").addScalar("buildingName").addScalar("buildingFloorNo",StandardBasicTypes.INTEGER)
		.addScalar("bldgNoOfFloors",StandardBasicTypes.INTEGER).addScalar("bldgAddressFirstLine").addScalar("bldgAddressSecondLine")
		.addScalar("bldgTown").addScalar("bldgCountry").addScalar("customerId",StandardBasicTypes.INTEGER).addScalar("customerName")
		.addScalar("eventSystem").addScalar("eventSignal").addScalar("eventValue");
		
		query.setResultTransformer(Transformers.aliasToBean(AlarmEventDTO.class));
		List<AlarmEventDTO> alarmEvents = query.list();
		System.out.println("Results size ====>"+alarmEvents);
		System.out.println("Results size ====>"+alarmEvents.size());
		return alarmEvents;
		
		
	}
	
	
	public List<AlarmEventWithContactsDTO> getAllalarmEventsWithContactDetails(String customerId){
		Session session = getSessionFactory().getCurrentSession();
		
		String str="SELECT a.alarm_event_id AS alarmEventId, a.building_id AS buildingId," +
				" a.event_details AS eventDetails, a.event_generated_time AS eventGeneratedTime," +
				" a.event_type AS eventType, a.is_test_alarm AS isTestAlarm,a.is_active as isActive," +
				" a.sms_notified as smsNotified, a.email_notified as emailNotified,b.building_name as buildingName," +
				" a.building_floor_no as buildingFloorNo,b.no_of_floor as bldgNoOfFloors," +
				" c.address_first_line as bldgAddressFirstLine,c.address_second_line as bldgAddressSecondLine," +
				" c.town as bldgTown, c.country as bldgCountry," +
				" secContact.contact_name as securityContactName,secContact.phone_primary as securityContactPhone1," +
				" secContact.phone_secondary as securityContactPhone2,secContact.email as securityContactEmail," +
				" enggContact.contact_name as enggContactName,enggContact.phone_primary as enggContactPhone1," +
				" enggContact.phone_secondary as enggContactPhone2,enggContact.email as enggContactEmail," +
				" mainContact.contact_name as maintenanceContactName,mainContact.phone_primary as maintenanceContactPhone1," +
				" mainContact.phone_secondary as maintenanceContactPhone2,mainContact.email as maintenanceContactEmail," +
				" bldgContact.contact_name as bldgContactName,bldgContact.phone_primary as bldgContactPhone1," +
				" bldgContact.phone_secondary as bldgContactPhone2,bldgContact.email as bldgContactEmail," +
				" b.gps_cordinate as gpsCordinates, a.event_system as eventSystem,a.event_signal as eventSignal, " +
				"(case when at.alarm_type_mapping is null then a.event_value else at.alarm_type_mapping end) as eventValue" +
				" FROM alarm_events a left join " +
				" building_details b on b.building_id=a.building_id " +
				" left outer join contact_details secContact on secContact.contact_id=b.security_contact_id" +
				" left outer join contact_details enggContact on enggContact.contact_id=b.engineering_contact_id" +
				" left outer join contact_details mainContact on mainContact.contact_id=b.maintenance_contact_id" +
				" left outer join contact_details bldgContact on bldgContact.contact_id=b.building_contact_id" +
				" left join address_details c on c.address_id=b.building_address_id " +
				" left outer join alarm_types at on a.event_system=at.alarm_type_system and " +
				"a.event_signal=at.alarm_type_signal and a.event_value=at.alarm_type_value " +
				"WHERE a.is_active=:arg_active  and (at.show_in_ui is null or at.show_in_ui ='Y' ) "+((customerId!=null && !customerId.isEmpty())?" and b.customer_id=:arg_cust_id":"") +" order by a.event_generated_time asc";
		
				
		SQLQuery query=session.createSQLQuery(str);
		System.out.println("Query======="+str);
		//query.setParameter("arg_customerId", customerId);
		
		//Criteria criteria = session.createCriteria(Building.class);
		//criteria.uniqueResult();
		if(customerId!=null && !customerId.isEmpty()){
		query.setParameter("arg_cust_id", customerId);
		}
		query.setParameter("arg_active", "Y");
		query.addScalar("alarmEventId",StandardBasicTypes.INTEGER).addScalar("buildingId").addScalar("eventDetails").
		addScalar("eventType").addScalar("isActive").addScalar("smsNotified").addScalar("eventGeneratedTime").
		addScalar("emailNotified").addScalar("buildingName").addScalar("buildingFloorNo",StandardBasicTypes.INTEGER)
		.addScalar("bldgNoOfFloors",StandardBasicTypes.INTEGER).addScalar("bldgAddressFirstLine").addScalar("bldgAddressSecondLine")
		.addScalar("bldgTown").addScalar("bldgCountry")
		.addScalar("securityContactName").addScalar("securityContactPhone1").addScalar("securityContactPhone2").addScalar("securityContactEmail")
		.addScalar("maintenanceContactName").addScalar("maintenanceContactPhone1").addScalar("maintenanceContactPhone2").addScalar("maintenanceContactEmail")
		.addScalar("enggContactName").addScalar("enggContactPhone1").addScalar("enggContactPhone2").addScalar("enggContactEmail")
		.addScalar("bldgContactName").addScalar("bldgContactPhone1").addScalar("bldgContactPhone2").addScalar("bldgContactEmail").addScalar("gpsCordinates").addScalar("eventSystem").addScalar("eventSignal").addScalar("eventValue");
		
				
		query.setResultTransformer(Transformers.aliasToBean(AlarmEventWithContactsDTO.class));
		List<AlarmEventWithContactsDTO> alarmEvents = query.list();
		System.out.println("Results size ====>"+alarmEvents);
		System.out.println("Results size ====>"+alarmEvents.size());
		return alarmEvents;
		
		
	}
	
	
}
