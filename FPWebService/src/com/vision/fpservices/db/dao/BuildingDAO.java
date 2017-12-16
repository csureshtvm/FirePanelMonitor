package com.vision.fpservices.db.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;



import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.vision.fpservices.db.daoImpl.GenericDaoImpl;
import com.vision.fpservices.db.model.AlarmEvents;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.ContactDetails;
import com.vision.fpservices.db.model.Controller;
import com.vision.fpservices.dto.BuildingDTO;
import com.vision.fpservices.util.HibernateUtil;


public interface BuildingDAO extends GenericDao<Building, Integer>{
	public int saveOrUpdate(Building building);
	public Building loadBuilding(int buildingId);
	public List<BuildingDTO> getAllBuildings(String customerId);	
}
