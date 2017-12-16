package com.vision.fpservices.controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.utils.ServerContextHelper;
import com.vision.fpservices.db.service.AlarmDevicesServices;
import com.vision.fpservices.util.FPUtil;
import com.wordnik.swagger.core.ApiOperation;
import com.wordnik.swagger.core.ApiParam;
import com.wordnik.swagger.jaxrs.JavaHelp;

@RestController 
@RequestMapping("/fpservices/AlarmDevices.json")
public class AlarmDevicesController  extends JavaHelp{@Autowired
	AlarmDevicesServices alarmDeviceServices;

	@Context
	HttpServletResponse response;
	final Logger LOG = LoggerFactory.getLogger(getClass().getName());

	@SuppressWarnings("unchecked")
    @POST
    @RequestMapping(value = "/saveAlarmDevice")
    @ApiOperation(value = "Save Alarm Device")
    public String saveAlarmEvent(@ApiParam(value = "buildingId", required = true) @QueryParam("buildingId") String buildingId,
    	    @ApiParam(value = "deviceId", required = true) @QueryParam("deviceId") String deviceId,
    	    @ApiParam(value = "installedFloorNo", required = true) @QueryParam("installedFloorNo") String installedFloorNo, 
    	    @ApiParam(value = "deviceName", required = false) @QueryParam("deviceName") String deviceName,
    	    @ApiParam(value = "deviceLoc", required = false) @QueryParam("deviceLoc") String deviceLoc,
    	    @ApiParam(value = "updatedBy", required = false) @QueryParam("updatedBy") String updatedBy,
    	    @ApiParam(value = "isUpdate", required = false) @QueryParam("isUpdate") String isUpdate) {
    	
	 	boolean isUpdateVal=Boolean.parseBoolean(isUpdate);
    	//System.out.println(alarmEventService);
    	return FPUtil.getJsonResponseAsString(alarmDeviceServices.saveAlarmDevices(deviceId, deviceName, deviceLoc, buildingId, installedFloorNo,updatedBy,isUpdateVal));
    	
    }
 
	@POST
	@RequestMapping(value = "/updateAlarmDevicestatus")
    @ApiOperation(value = "Save Alarm Device")
    public String updateAlarmDevicestatus(@ApiParam(value = "deviceId", required = true) @QueryParam("deviceId") String deviceId) {
    	
	 	return FPUtil.getJsonResponseAsString(alarmDeviceServices.updateAlarmDeviceLastCommunicationTime(deviceId));
    	
    }
	
	@GET
	@RequestMapping(value = "/getAllAlarmDevices",produces="application/json; charset=UTF-8")
    @ApiOperation(value = "Retrieve all Alarm Device")
    public String getAllAlarmDevices(@ApiParam(value = "customerId", required = true) @QueryParam("customerId") String customerId) {
    	
	 	return FPUtil.getJsonResponseAsString(alarmDeviceServices.getAllAlarmDevices(customerId));
    	
    }
	
	@GET
	@RequestMapping(value = "/getAllDevicesResetDetails")
    @ApiOperation(value = "Retrieve all Alarm Device reset")
    public String getAllDeviceResets() {
    	
	 	return FPUtil.getJsonResponseAsString(alarmDeviceServices.getAllDeviceResetDetails());
    	
    }
	
	@POST
	@RequestMapping(value = "/updateDeviceResetstatus")
    @ApiOperation(value = "Update Alarm Device Reset Status")
    public String updateDeviceResetstatus(@ApiParam(value = "deviceId", required = true) @QueryParam("deviceId") String deviceId,@ApiParam(value = "resetStatus", required = false) @QueryParam("resetStatus") String resetStatus) {
    	
	 	return FPUtil.getJsonResponseAsString(alarmDeviceServices.updateAlarmDeviceResetDetails(deviceId,resetStatus));
    	
    }
	
	@POST
	@RequestMapping(value = "/saveDeviceResetRequest")
    @ApiOperation(value = "Save Alarm Device Reset request")
    public String saveDeviceResetRequest(@ApiParam(value = "deviceId", required = true) @QueryParam("deviceId") String deviceId,@ApiParam(value = "requestedBy", required = true) @QueryParam("requestedBy") String requestedBy) {
    	
		return FPUtil.getJsonResponseAsString(alarmDeviceServices.saveAlarmDeviceResetRequest(deviceId, requestedBy));
    	
    }}
