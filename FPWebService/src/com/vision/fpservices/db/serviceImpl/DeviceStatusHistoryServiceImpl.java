package com.vision.fpservices.db.serviceImpl;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vision.fpservices.db.dao.DeviceHistoryDAO;
import com.vision.fpservices.db.model.DeviceStatusHistory;
import com.vision.fpservices.db.service.DeviceStatusHistoryService;


@Repository("DeviceStatusHistoryService")
@Transactional(readOnly=true)
public class DeviceStatusHistoryServiceImpl implements DeviceStatusHistoryService{

	@Autowired
	DeviceHistoryDAO devStatHistDAO;
	@Transactional(readOnly=false)
	public int saveDeviceStatusHistory(DeviceStatusHistory devStatHist) {
		return devStatHistDAO.makePersistent(devStatHist).getStatusHistoryId();
	}

	public List<DeviceStatusHistory> getDeviceStatusHistoryList(int devId) {
		return devStatHistDAO.getAllDeviceHistory(devId);
	}



}
