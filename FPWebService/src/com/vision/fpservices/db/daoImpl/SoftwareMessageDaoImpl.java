package com.vision.fpservices.db.daoImpl;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.SoftwareMessageDao;
import com.vision.fpservices.db.model.AlarmDevices;
import com.vision.fpservices.db.model.SoftwareMessage;
import com.vision.fpservices.dto.AlarmEventWithContactsDTO;
import com.vision.fpservices.dto.OtherContactsDTO;
import com.vision.fpservices.dto.SoftwareMessageDTO;
import com.vision.fpservices.dto.SoftwareMessagePendingDTO;


@Repository("SoftwareMessageDao") 
public class SoftwareMessageDaoImpl extends GenericDaoImpl<SoftwareMessage, Integer> implements SoftwareMessageDao{

	Logger log = Logger.getLogger("SoftwareMessageDaoImpl");
	
	public SoftwareMessage loadAlarmDevice(Integer softwareMessageId) {
		log.info("Going to load SoftwareMessage with ID --->"+softwareMessageId);
		Session session = getSessionFactory().getCurrentSession();
		SoftwareMessage softwareMessage = (SoftwareMessage) session.get(SoftwareMessage.class,
				softwareMessageId);
		return softwareMessage;

	}

	
	@Override
	public List<SoftwareMessage> getAllSoftwareMessagesFromBuilding(Integer buildingId){
		
		
		Session session = getSessionFactory().getCurrentSession();
		
		String queryStr="from SoftwareMessage sw where sw.buildingId=:arg_bldg_id";
		Query query=session.createQuery(queryStr);
		query.setParameter("arg_bldg_id", buildingId);
		List<SoftwareMessage> resultList=(List<SoftwareMessage>)query.list();

		return resultList;
		
	}
	
	
	
	public List<SoftwareMessageDTO> getAllSoftwareMessages(Integer customerId,Integer buildingId){
		Session session = getSessionFactory().getCurrentSession();
		
		String subQry="";
		
		if(customerId!=null ){
			if(buildingId!=null ){
				subQry+=" b.building_id=:arg_bldg_id ";				
			}else{
			subQry+=" b.customer_id=:arg_cust_id ";
			}
		}else{
			subQry+=" b.building_id=:arg_bldg_id ";
		}
		
		
		String str="SELECT a.software_message_id AS softwareMessageId, a.building_id AS buildingId," +
		"(case when smt.DESCRIPTION_MAPPING_VALUE is null then a.DESCRIPTION else smt.DESCRIPTION_MAPPING_VALUE end) as eventDetails," +
				" a.UPDATED_DTM AS eventUpdatedTime," +
				" b.building_name as buildingName,b.no_of_floor as bldgNoOfFloors," +
				" c.address_first_line as bldgAddressFirstLine,c.address_second_line as bldgAddressSecondLine," +
				" c.town as bldgTown, c.country as bldgCountry," +
				"(case when smt.MAPPING_VALUE is null then a.event_value else smt.MAPPING_VALUE end) as eventValue" +
				" FROM SOFTWARE_MESSAGE a left join " +
				" building_details b on b.building_id=a.building_id " +
				" left join address_details c on c.address_id=b.building_address_id " +
				" left outer join software_message_types smt on a.DESCRIPTION=MESSAGE_DESCRIPTION and " +
				"a.OBJECT_TYPE=smt.MESSAGE_DATA_TYPE and a.event_value=smt.MESSAGE_VALUE " +
				"WHERE "+subQry 
				+" order by a.OBJECT_ID asc";
		
				
		SQLQuery query=session.createSQLQuery(str);
		System.out.println("Query======="+str);
		//query.setParameter("arg_customerId", customerId);
		
		//Criteria criteria = session.createCriteria(Building.class);
		//criteria.uniqueResult();
		if(customerId!=null && buildingId==null ){
		query.setParameter("arg_cust_id", customerId);
		}
		if(buildingId!=null ){
			query.setParameter("arg_bldg_id", buildingId);			
		}
		query.addScalar("softwareMessageId",StandardBasicTypes.INTEGER).addScalar("buildingId",StandardBasicTypes.INTEGER).addScalar("eventDetails").
		addScalar("eventUpdatedTime").
		addScalar("buildingName").addScalar("bldgNoOfFloors",StandardBasicTypes.INTEGER).addScalar("bldgAddressFirstLine").addScalar("bldgAddressSecondLine")
		.addScalar("bldgTown").addScalar("bldgCountry").addScalar("eventValue");
		
				
		query.setResultTransformer(Transformers.aliasToBean(SoftwareMessageDTO.class));
		List<SoftwareMessageDTO> softwareMessageList = query.list();
		return softwareMessageList;
		
		
	}

	
	public List<SoftwareMessagePendingDTO> getAllNotfnPendingSoftwareMessages(){
		Session session = getSessionFactory().getCurrentSession();
		
		
		String str="SELECT a.software_message_id AS softwareMessageId, a.building_id AS buildingId," +
		"(case when smt.DESCRIPTION_MAPPING_VALUE is null then a.DESCRIPTION else smt.DESCRIPTION_MAPPING_VALUE end) as eventDetails," +
		" a.UPDATED_DTM AS eventUpdatedTime," +
				" b.building_name as buildingName,b.no_of_floor as bldgNoOfFloors," +
				" c.address_first_line as bldgAddressFirstLine,c.address_second_line as bldgAddressSecondLine," +
				" c.town as bldgTown, c.country as bldgCountry," +
				"(case when smt.MAPPING_VALUE is null then a.event_value else smt.MAPPING_VALUE end) as eventValue," +
				" (case when a.sms_notified='Y' then 'Y' else 'N' end) as smsNotified," +
				" (case when a.email_notified='Y' then 'Y' else 'N' end) as emailNotified," +
				" notfnSettings.notification_type as notfnType," +
				" (case when smt.skip_notify_customer='Y' then 'N' else notfnSettings.notify_customer end) as notifyCustomer," +
				" (case when smt.skip_notify_bldgcontact='Y' then 'N' else notfnSettings.notify_building_contact end) as notifyBuildingContact,  " +
				" (case when smt.skip_notify_maintcontact='Y' then 'N' else notfnSettings.notify_maintenance_contact end) as notifyMaintenanceContact," +
				" (case when smt.skip_notify_enggcontact='Y' then 'N' else notfnSettings.notify_engg_contact end) as notifyEnggContact,  " +
				" (case when smt.skip_notify_seccontact='Y' then 'N' else notfnSettings.notify_security_contact end) as notifysecurityContact," +
				" buildingContact.phone_primary as buildingContactPhone,buildingContact.email as buildingContactEmail,  " +
				" enggContact.phone_primary as enggContactPhone,enggContact.email as enggContactEmail,  " +
				" securityContact.phone_primary as securityContactPhone,securityContact.email as securityContactEmail,  " +
				" maintenanceContact.phone_primary as maintenanceContactPhone,maintenanceContact.email as maintenanceContactEmail,  " +
				" customerContact.phone_primary as customerContactPhone,customerContact.email as customerContactEmail," +
				" cd.customer_id as customerId" +
				//" smt.  " +
				" FROM SOFTWARE_MESSAGE a left join " +
				" building_details b on b.building_id=a.building_id " +
				" left join fp_customer_details cd on cd.customer_id=b.customer_id  " +
				" left join address_details c on c.address_id=b.building_address_id " +
				" left join alarm_notification_settings notfnSettings on notfnSettings.building_id=b.building_id " +				
				" left join contact_details buildingContact on buildingContact.contact_id=b.building_contact_id " +
				" left join contact_details enggContact on enggContact.contact_id=b.engineering_contact_id " +
				" left join contact_details securityContact on securityContact.contact_id=b.security_contact_id " +
				" left join contact_details maintenanceContact on maintenanceContact.contact_id=b.maintenance_contact_id " +
				" left join contact_details customerContact on customerContact.contact_id=cd.customer_contact_id " +
				
				" left outer join software_message_types smt on a.DESCRIPTION=MESSAGE_DESCRIPTION and " +
				"a.OBJECT_TYPE=smt.MESSAGE_DATA_TYPE and a.event_value=smt.MESSAGE_VALUE " +
				"WHERE (a.sms_notified is null or a.sms_notified='N' or a.email_notified is null or a.email_notified='N')" 
				+" order by a.OBJECT_ID asc";
		
				
		SQLQuery query=session.createSQLQuery(str);
		System.out.println("Query======="+str);
		//query.setParameter("arg_customerId", customerId);
		
		//Criteria criteria = session.createCriteria(Building.class);
		//criteria.uniqueResult();
		
		query.addScalar("softwareMessageId",StandardBasicTypes.INTEGER).addScalar("buildingId",StandardBasicTypes.INTEGER).addScalar("eventDetails").
		addScalar("eventUpdatedTime").
		addScalar("buildingName").addScalar("bldgNoOfFloors",StandardBasicTypes.INTEGER).addScalar("bldgAddressFirstLine").addScalar("bldgAddressSecondLine")
		.addScalar("bldgTown").addScalar("bldgCountry").addScalar("eventValue")
		.addScalar("smsNotified").addScalar("emailNotified").addScalar("notfnType").
		addScalar("notifyCustomer").addScalar("notifyBuildingContact").addScalar("notifyMaintenanceContact").
		addScalar("notifyEnggContact").addScalar("notifysecurityContact").addScalar("buildingContactPhone").
		addScalar("buildingContactEmail").addScalar("enggContactPhone").addScalar("enggContactEmail").
		addScalar("securityContactPhone").addScalar("securityContactEmail").addScalar("maintenanceContactPhone").
		addScalar("maintenanceContactEmail").addScalar("customerContactPhone").addScalar("customerContactEmail")
		.addScalar("customerId",StandardBasicTypes.INTEGER);
		/*.addScalar("skipNotifyCustomer").addScalar("skipNotifyBldgContact").addScalar("skipNotifyEnggContact")
		.addScalar("skipNotifyMaintContact").addScalar("skipNotifySecContact");*/
		
			
				
		query.setResultTransformer(Transformers.aliasToBean(SoftwareMessagePendingDTO.class));
		List<SoftwareMessagePendingDTO> softwareMessageList = query.list();
		return softwareMessageList;
		
		
	}
	

	public List<OtherContactsDTO> getAllAlarmOtherEventsContacts(){
		Session session = getSessionFactory().getCurrentSession();
		
		
		String queryStr=" select othCont.OTHER_CONTACT_ID as otherContactId," +
				" othCont.OBJECT_TYPE as objectType,othCont.DESCRIPTION as description," +
				" othCont.CUSTOMER_ID as customerId,othCont.EMAIL_ID as emailId," +
				" othCont.MOBILE_NO as mobileNo" +
				
				" from EVENT_OTHER_CONTACTS othCont";
		SQLQuery query=session.createSQLQuery(queryStr);
		query.addScalar("otherContactId",StandardBasicTypes.INTEGER).addScalar("customerId",StandardBasicTypes.INTEGER)
		.addScalar("objectType").
		addScalar("description").addScalar("emailId").addScalar("mobileNo");
		
		query.setResultTransformer(Transformers.aliasToBean(OtherContactsDTO.class));
		List<OtherContactsDTO> otherContactsList = query.list();
		return otherContactsList;
		
		
	}

}
