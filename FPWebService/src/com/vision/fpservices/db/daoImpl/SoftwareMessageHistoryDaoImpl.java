package com.vision.fpservices.db.daoImpl;

import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.SoftwareMessageHistoryDao;
import com.vision.fpservices.db.model.SoftwareMessageHistory;


@Repository("SoftwareMessageHistoryDao")
public class SoftwareMessageHistoryDaoImpl extends GenericDaoImpl<SoftwareMessageHistory, Integer> implements SoftwareMessageHistoryDao{

}
