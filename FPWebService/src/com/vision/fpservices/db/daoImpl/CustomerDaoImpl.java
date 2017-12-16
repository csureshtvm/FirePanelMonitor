package com.vision.fpservices.db.daoImpl;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.CustomerDao;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.ContactDetails;
import com.vision.fpservices.db.model.CustomerDetails;
import com.vision.fpservices.dto.CustomerBuildingSummaryDTO;
import com.vision.fpservices.dto.CustomerStatisticsDTO;
import com.vision.fpservices.dto.UserDTO;
import com.vision.fpservices.util.HibernateUtil;
@Repository("CustomerDao") 
public class CustomerDaoImpl extends GenericDaoImpl<CustomerDetails, Integer> implements CustomerDao{
	Logger log = Logger.getLogger("CustomerDaoImpl");
	
	public CustomerDetails loadCustomer(Integer customerId) {
		log.info("Going to load Customer with ID --->"+customerId);
		Session session = getSessionFactory().getCurrentSession();
		CustomerDetails customerDetails = (CustomerDetails) session.get(CustomerDetails.class,
				 customerId);
		return customerDetails;

	}
	
	public int saveOrUpdate(CustomerDetails customer) {
		log.info("Going to save ContactDetails");
		Session session = getSessionFactory().getCurrentSession();
		int id = 0;
		if (customer.getCustomerId() == null || customer.getCustomerId()==0) {
			id = (Integer) session.save(customer);
		} else {
			session.saveOrUpdate(customer);
			id = customer.getCustomerId();
		}
		customer.setCustomerId(id);
		log.info("Contact saved with id=" + id);

		return (int) id;
	}
	
	
	public List<CustomerStatisticsDTO> getCustomerStatistics(){
		Session session = getSessionFactory().getCurrentSession();
		/*SQLQuery query=session.createSQLQuery("SELECT c.customer_id customerId,c.customer_name AS customerName," +
				" CONCAT_WS(' , ',ca.address_first_line,ca.address_second_line,ca.town,ca.country) AS customerAddress," +
				" ca.address_first_line AS buildings " +
				" FROM fp_customer_details c " +
				"LEFT JOIN address_details ca ON ca.address_id=c.customer_address_id ");*/
		
		SQLQuery query=session.createSQLQuery("SELECT c.customer_id customerId,c.customer_name AS customerName," +
				" CONCAT_WS(' , ',ca.address_first_line,ca.address_second_line,ca.town,ca.country) AS customerAddress," +
				" (SELECT COUNT(*) FROM building_details b WHERE b.customer_id=c.customer_id)  AS buildings FROM fp_customer_details c " +
				"LEFT JOIN address_details ca ON ca.address_id=c.customer_address_id ");
		
		//" (SELECT GROUP_CONCAT(CONCAT_WS(' , ',b.building_name,b.building_location) SEPARATOR '#*#*#') " +
		//" FROM building_details b WHERE b.customer_id=c.customer_id) AS buildings
		
		
		query.addScalar("customerId",StandardBasicTypes.INTEGER).addScalar("customerName").addScalar("customerAddress").
		addScalar("buildings",StandardBasicTypes.INTEGER);
			
		
		query.setResultTransformer(Transformers.aliasToBean(CustomerStatisticsDTO.class));
		List<CustomerStatisticsDTO> custList =query.list();
		System.out.println("Result Size---------"+custList.size());
		
		return custList;
		
		
	}
	public List<CustomerBuildingSummaryDTO> getAllCustomerBuildingsSummaryList(){
		Session session = getSessionFactory().getCurrentSession();
		SQLQuery query=session.createSQLQuery("SELECT c.customer_id customerId,c.customer_name AS customerName," +
				"b.building_id as buildingId,b.building_name as buildingName FROM fp_customer_details c " +
				"LEFT JOIN building_details b ON b.customer_id=c.customer_id " +
				" where c.is_active='Y' and c.is_active='Y' order by c.customer_name,b.building_name");
		
		//" (SELECT GROUP_CONCAT(CONCAT_WS(' , ',b.building_name,b.building_location) SEPARATOR '#*#*#') " +
		//" FROM building_details b WHERE b.customer_id=c.customer_id) AS buildings
		
		
		query.addScalar("customerId",StandardBasicTypes.INTEGER).addScalar("customerName").addScalar("buildingName").
		addScalar("buildingId",StandardBasicTypes.INTEGER);
			
		
		query.setResultTransformer(Transformers.aliasToBean(CustomerBuildingSummaryDTO.class));
		List<CustomerBuildingSummaryDTO> custList =query.list();
		System.out.println("Result Size---------"+custList.size());
		
		return custList;
		
	}
	
}
