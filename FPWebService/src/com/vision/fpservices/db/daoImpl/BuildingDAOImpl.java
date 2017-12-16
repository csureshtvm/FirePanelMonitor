package com.vision.fpservices.db.daoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.BuildingDAO;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.dto.BuildingDTO;
import com.vision.fpservices.util.HibernateUtil;
@Repository("BuildingDAO") 
//@Transactional
public class BuildingDAOImpl extends GenericDaoImpl<Building, Integer> implements BuildingDAO{
	Logger log = Logger.getLogger("BuildingDAO");
	public int saveOrUpdate(Building building) {
		log.info("Going to save building");
		// save example - without transaction
		Session session = getSessionFactory().getCurrentSession();
		int id=0;
		if(building.getBuildingId() ==null || building.getBuildingId() == 0){
			id = (Integer) session.save(building);	
			building.setBuildingId(id);
		}else{
			session.saveOrUpdate(building);
			System.out.println("building.getEnggContactDetails()"+(building.getEnggContactDetails()!=null?building.getEnggContactDetails().getContactId():null));
			System.out.println("building.getSecurtyContactDetails()"+(building.getSecurityContactDetails()!=null?building.getSecurityContactDetails().getContactId():null));
			System.out.println("building.getManteaceContactDetails()"+(building.getMaintenanceContactDetails()!=null?building.getMaintenanceContactDetails().getContactId():null));
			
			
			//session.saveOrUpdate(building.getSecurityContactDetails());
			//session.saveOrUpdate(building.getMaintenanceContactDetails());
			id=building.getBuildingId();
		}
		
		log.info("Building Details saved with id=" + id);

		return id;

	}

	public Building loadBuilding(int buildingId) {
		log.info("Going to load building with ID --->"+buildingId);
		Session session = getSessionFactory().getCurrentSession();
		Building building = (Building) session.get(Building.class,
				buildingId);
		 return building;

	}

	public List<BuildingDTO> getAllBuildings(String customerId) {
		log.info("Going to retreive all  buildings");
		Session session = getSessionFactory().getCurrentSession();
		Query query=session.createQuery("from Building b where b.customerDetails.customerId='"+customerId+"'");
		//query.setParameter("arg_customerId", customerId);
		//Transaction tx1 = session.beginTransaction();
		//Criteria criteria = session.createCriteria(Building.class);
		//criteria.uniqueResult();
		
		List<Building> buildings = query.list();
		
		List<BuildingDTO> resultsList = new ArrayList<BuildingDTO>();
		if(buildings!=null && buildings.size()>0){
			for(Building bldg : buildings){
				resultsList.add(bldg.convertToInternal());
			}
		}
		
		System.out.println("Results size ====>"+buildings.size());
		System.out.println("Results size ====>"+buildings.size());
		return resultsList;

	}

	public static void main(String[] args) {
		//BuildingDAO bldgDAO = new BuildingDAO();
		//List<Building> bldgs=bldgDAO.getAllBuildings("");
		//System.out.println("Buildings.size---->"+bldgs.size());
		
	}
	public static void main1(String[] args) {
			
		
		/*Building building=new Building();
 
		  building.setBuildingName("Test Bldg");
		  building.setDescription("Descr"); building.setLocation("Loc");
		 
		BuildingDAO bldgDAO = new BuildingDAO();
		
		ContactDetails con=new ContactDetails();
		con.setEmail("67777");
		con.setPhone("87788787");
		Set<ContactDetails> contacts=new HashSet<ContactDetails>();
		contacts.add(con);
		building.setContacts(contacts);
		bldgDAO.saveOrUpdate(building);*/
		
		/*
		 * Controller con=new Controller(); Set<Controller> conSet=new
		 * HashSet<Controller>(); conSet.add(con);
		 */
		// building.setControllers(conSet);

		/*List<Building> buildings = bldgDAO.getAllBuildings();
		for (Building bldg : buildings) {
			System.out.println(bldg.getBuildingId() + ":"
					+ bldg.getBuildingName());
		}*/
	}
	
	



}
