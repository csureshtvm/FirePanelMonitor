package com.vision.fpservices.db.service;

import java.util.List;
import java.util.logging.Logger;

import com.vision.fpservices.db.dao.DeviceHistoryDAO;
import com.vision.fpservices.db.model.DeviceStatusHistory;

public interface DeviceStatusHistoryService {

	public int saveDeviceStatusHistory(DeviceStatusHistory devStatHist);
	public List<DeviceStatusHistory> getDeviceStatusHistoryList(int devId) ;
}
