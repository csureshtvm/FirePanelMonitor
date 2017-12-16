package com.vision.fpservices.db.daoImpl;

import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.AlarmNotfnHistoryDAO;
import com.vision.fpservices.db.model.AddressDetails;
import com.vision.fpservices.db.model.AlarmNotfnHistory;

@Repository("AlarmNotfnHistoryDAO") 
public class AlarmNotfnHistoryDAOImpl extends GenericDaoImpl<AlarmNotfnHistory, Integer> implements AlarmNotfnHistoryDAO {

}
