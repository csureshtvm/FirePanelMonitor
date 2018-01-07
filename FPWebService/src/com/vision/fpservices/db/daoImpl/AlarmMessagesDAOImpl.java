package com.vision.fpservices.db.daoImpl;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.AlarmEventDao;
import com.vision.fpservices.db.dao.AlarmMessagesDAO;
import com.vision.fpservices.db.model.AlarmEvents;
import com.vision.fpservices.db.model.AlarmMessages;
import com.vision.fpservices.db.model.SoftwareMessage;
import com.vision.fpservices.dto.AlarmMessagesDTO;
import com.vision.fpservices.dto.SoftwareMessageDTO;

@Repository("AlarmMessagesDAO") 
public class AlarmMessagesDAOImpl extends GenericDaoImpl<AlarmMessages, Integer> implements AlarmMessagesDAO{


	Logger log = Logger.getLogger("AlarmMessagesDAOImpl");
	
	public AlarmMessages loadAlarmMessages(Integer alarmMessageId) {
		log.info("Going to load AlarmMessages with ID --->"+alarmMessageId);
		Session session = getSessionFactory().getCurrentSession();
		AlarmMessages alarmMessage = (AlarmMessages) session.get(AlarmMessages.class,
				alarmMessageId);
		return alarmMessage;

	}
	
	public List<AlarmMessagesDTO> getAllSoftwareMessages(Integer customerId,Integer buildingId){
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
		
		
		String str="SELECT a.alarm_message_id AS alarmMessageId, a.building_id AS buildingId," +
		"a.message_details as messageDetails," +
				" a.created_time AS createdTime," +
				" b.building_name as buildingName," +
				" c.address_first_line as bldgAddressFirstLine,c.address_second_line as bldgAddressSecondLine," +
				" c.town as bldgTown, c.country as bldgCountry" +
				" FROM alarm_messages a left join " +
				" building_details b on b.building_id=a.building_id " +
				" left join address_details c on c.address_id=b.building_address_id " +
				"WHERE "+subQry 
				+" order by a.alarm_message_id desc";
		
				
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
		query.addScalar("alarmMessageId",StandardBasicTypes.INTEGER).addScalar("buildingId",StandardBasicTypes.INTEGER).addScalar("messageDetails").
		addScalar("createdTime").
		addScalar("buildingName").addScalar("bldgAddressFirstLine").addScalar("bldgAddressSecondLine")
		.addScalar("bldgTown").addScalar("bldgCountry");
		
				
		query.setResultTransformer(Transformers.aliasToBean(AlarmMessagesDTO.class));
		List<AlarmMessagesDTO> messageList = query.list();
		return messageList;
		
		
	}

	
}
