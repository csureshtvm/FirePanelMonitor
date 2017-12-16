package com.vision.fpservices.db.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.test.utils.ServerContextHelper;
import com.vision.fpservices.db.dao.AlarmDevicesDAO;
import com.vision.fpservices.db.dao.AlarmEventResetDAO;
import com.vision.fpservices.db.dao.BuildingDAO;
import com.vision.fpservices.db.daoImpl.AlarmDevicesDAOImpl;
import com.vision.fpservices.db.model.AlarmDeviceReset;
import com.vision.fpservices.db.model.AlarmDevices;
import com.vision.fpservices.db.model.Building;
import com.vision.fpservices.db.service.AlarmDevicesServices;
import com.vision.fpservices.db.service.AlarmEventService;
import com.vision.fpservices.dto.AlarmDeviceResetDto;
import com.vision.fpservices.dto.AlarmDevicesDto;
@Repository("AlarmDevicesServices")
@Transactional(readOnly=true)
public class AlarmDevicesServicesImpl implements AlarmDevicesServices{
	
	@Autowired
	AlarmDevicesDAO alarmDevicesDao;
	
	@Autowired
	BuildingDAO buildingDao;
	
	@Autowired
	AlarmEventResetDAO alarmEventResetDao;
	
	
	@Transactional(readOnly=false)
	public boolean saveAlarmDevices(String deviceId,String deviceName,String deviceLoc,String buildingId,String installedFloorNo,String updatedBy,boolean isUpdate){
		AlarmDevices alarmDevice=alarmDevicesDao.loadAlarmDevice(deviceId);
			if(alarmDevice==null && !isUpdate){
				alarmDevice=new AlarmDevices();
				Building  building=buildingDao.loadBuilding(Integer.parseInt(buildingId));
				alarmDevice.setDeviceId(deviceId);
				alarmDevice.setBuilding(building);
				alarmDevice.setDeviceName(deviceName);
				alarmDevice.setDeviceLoc(deviceLoc);
				alarmDevice.setInstalledFloorNo(installedFloorNo);
				
			}
			if(isUpdate && alarmDevice!=null){
				alarmDevice.setDeviceName(deviceName);
				alarmDevice.setDeviceLoc(deviceLoc);
				alarmDevice.setInstalledFloorNo(installedFloorNo);				
			}
			alarmDevice.setUpdatedBy(updatedBy);
			alarmDevice.setUpdatedDate(new Date());
			alarmDevice.setIsActive("Y");
			alarmDevice= alarmDevicesDao.makePersistent(alarmDevice);
			
			return true;
		
	}
	@Transactional(readOnly=false)
	public boolean updateAlarmDeviceLastCommunicationTime(String deviceId){
		AlarmDevices alarmDevice=alarmDevicesDao.loadAlarmDevice(deviceId);
		if(alarmDevice!=null){
			alarmDevice.setLastMessageReceivedTime(new Date());	
			alarmDevice= alarmDevicesDao.makePersistent(alarmDevice);
			return true;
		}
		return false;
	}
	
	public List<AlarmDevicesDto> getAllAlarmDevices(String customerId){
		
		return alarmDevicesDao.getAllAlarmDevices(customerId);
	}

	@Transactional(readOnly=false)
	public boolean updateAlarmDeviceResetDetails(String alarmDeviceId,String resetStatus){
		return alarmEventResetDao.updateAlarmResetDeviceSatus(alarmDeviceId, resetStatus);
	}
	public List<AlarmDeviceResetDto> getAllDeviceResetDetails(){
		List<AlarmDeviceResetDto> alarmDevResList= alarmEventResetDao.getAllAlarmResetDevices();
		
		List<AlarmDeviceResetDto> resultList=new ArrayList<AlarmDeviceResetDto>();
		List<String> addedList=new ArrayList<String>();
		if(alarmDevResList!=null && alarmDevResList.size()>0){
			for(AlarmDeviceResetDto dto : alarmDevResList){
				if(!addedList.contains(dto.getDeviceId())){
					resultList.add(dto);
					addedList.add(dto.getDeviceId());
				}
			}
		}
		
		return resultList;
		
	}
	@Transactional(readOnly=false)
	public boolean saveAlarmDeviceResetRequest(String deviceId,String requestedBy)
	{
		AlarmDeviceReset alarmDevReset=new AlarmDeviceReset();
		AlarmDevices alarmDevice=alarmDevicesDao.findById(deviceId);
		if(alarmDevice!=null){
		alarmDevReset.setAlarmDevice(alarmDevice);
		alarmDevReset.setResetRequestedBy(requestedBy);
		alarmDevReset.setResetRequestedDtm(new Date());
		int id=alarmEventResetDao.makePersistent(alarmDevReset).getDeviceRequestId();
		return (id>0)?true:false;
		}
		return false;
	}
}
