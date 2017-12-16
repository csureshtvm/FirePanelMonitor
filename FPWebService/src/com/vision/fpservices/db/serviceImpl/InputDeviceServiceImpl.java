package com.vision.fpservices.db.serviceImpl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vision.fpservices.db.dao.InputDevicesDAO;
import com.vision.fpservices.db.model.InputDevices;
import com.vision.fpservices.db.service.InputDeviceService;

@Repository("InputDeviceService")
@Transactional(readOnly=true)
public class InputDeviceServiceImpl implements InputDeviceService{

	@Autowired
	public InputDevicesDAO inpDAO;
	
	@Transactional(readOnly=false)
	public int saveOrUpdate(InputDevices inputDev){
		return inpDAO.makePersistent(inputDev).getInputDeviceId();
	}
	public InputDevices getInputDevice(int id){
		return inpDAO.loadInputDevice(id);
	}


}
