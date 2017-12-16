package com.vision.fpservices.db.dao;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.vision.fpservices.db.model.AlarmNotificationSettings;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.model.Controller;
import com.vision.fpservices.db.model.DeviceStatusHistory;
import com.vision.fpservices.util.HibernateUtil;

public interface DeviceHistoryDAO extends GenericDao<DeviceStatusHistory, Integer>{

	public int saveOrUpdate(DeviceStatusHistory devStatHist);

	public List<DeviceStatusHistory> getAllDeviceHistory(int deviceId);

}
