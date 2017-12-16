package com.vision.fpservices.db.service;

import java.util.logging.Logger;

import com.vision.fpservices.db.dao.InputDevicesDAO;
import com.vision.fpservices.db.model.InputDevices;

public interface InputDeviceService {
	public int saveOrUpdate(InputDevices inputDev);
	public InputDevices getInputDevice(int id);
}
