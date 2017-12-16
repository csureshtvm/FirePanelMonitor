package com.vision.fpservices.controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.utils.ServerContextHelper;
import com.vision.fpservices.db.service.AlarmEventService;
import com.vision.fpservices.db.serviceImpl.AlarmEventServiceImpl;
import com.vision.fpservices.dto.AlarmEventRequestDTO;
import com.vision.fpservices.util.FPUtil;
import com.wordnik.swagger.core.ApiOperation;
import com.wordnik.swagger.core.ApiParam;

@RestController 
@RequestMapping("/fpservices/AlarmEvents.json")
public class AlarmController {

	@Autowired
	AlarmEventService alarmEventService;
	
	 @RequestMapping(value = "/events", method = RequestMethod.GET,produces="application/json; charset=UTF-8")
	 @ResponseBody
	 public String getAllEvents(
	    	    @RequestParam("customerId") String customerId,
	    	    @ApiParam(value = "userId", required = false) @QueryParam("userId") String userId) {
	    	
	    	System.out.println("Inside getAllEvents------------>"+customerId);
	    	System.out.println("alarmEventService--"+alarmEventService);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.getAllAlarmEvents(customerId,userId));
	    	
	    }
	
	 	@RequestMapping(value="/resetAlarm", method = RequestMethod.POST)
	    @ApiOperation(value = "Reset Alarm Event")
	    public String resetAlarmEvent(
	    	    @ApiParam(value = "alarmEventId", required = true) @QueryParam("alarmEventId") Integer alarmEventId,
	    	    @ApiParam(value = "resetComments", required = false) @QueryParam("resetComments") String resetComments,
	    	    @ApiParam(value = "requestedBy", required = false) @QueryParam("requestedBy") String requestedBy) {
	    	
	    	return FPUtil.getJsonResponseAsString(alarmEventService.resetAlarm(alarmEventId,resetComments,requestedBy));
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }
	    
	    
	    @SuppressWarnings("unchecked")
	    @RequestMapping(value="/saveAlarmEvent", method = RequestMethod.POST)
	    @ApiOperation(value = "Save Alarm Event")
	    public String saveAlarmEvent(@ApiParam(value = "buildingId", required = true) @QueryParam("buildingId") Integer buildingId,
	    	    @ApiParam(value = "eventType", required = false) @QueryParam("eventType") String eventType,
	    	    @ApiParam(value = "eventDetails", required = false) @QueryParam("eventDetails") String eventDetails, 
	    	    @ApiParam(value = "testAlarm", required = true) @QueryParam("testAlarm") String testAlarm,
	    	    @ApiParam(value = "eventGeneratedTimeInMillis", required = true) @QueryParam("eventGeneratedTimeInMillis") long eventGeneratedTimeInMillis,
	    	    @ApiParam(value = "buildingFloorNo", required = true) @QueryParam("buildingFloorNo") Integer buildingFloorNo,
	    	    @ApiParam(value = "system", required = false) @QueryParam("system") String system,
	    	    @ApiParam(value = "signal", required = false) @QueryParam("signal") String signal,
	    	    @ApiParam(value = "value", required = false) @QueryParam("value") String value,
	    	    @ApiParam(value = "deviceType", required = false) @QueryParam("deviceType") String deviceType) {
	    	
	    	AlarmEventRequestDTO reqDto=new AlarmEventRequestDTO();
	    	reqDto.setBuildingFloorNo(buildingFloorNo);
	    	reqDto.setBuildingId(buildingId);
	    	reqDto.setEventDetails(eventDetails);
	    	reqDto.setEventDeviceType(deviceType);
	    	reqDto.setEventGeneratedTimeInMillis(eventGeneratedTimeInMillis);
	    	reqDto.setEventSignal(signal);
	    	reqDto.setEventSystem(system);
	    	reqDto.setEventType(eventType);
	    	reqDto.setEventValue(value);
	    	reqDto.setTestAlarm(testAlarm);
	    	
	    	System.out.println("Alarm DTO ====>"+reqDto);
	    	//System.out.println(alarmEventService);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.createNewAlarm(reqDto));
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }
	    
	    
	    @SuppressWarnings("unchecked")
	    @RequestMapping(value="/updateAlarmEventStatus", method = RequestMethod.POST)
	    @ApiOperation(value = "Reset Alarm Event")
	    public String updateAlarmEventStatus(
	    	    @ApiParam(value = "alarmEventId", required = true) @QueryParam("alarmEventId") Integer alarmEventId,
	    	    @ApiParam(value = "notfnType", required = true) @QueryParam("notfnType") String notfnType,
	    	    @ApiParam(value = "notfnStatus", required = true) @QueryParam("notfnStatus") String notfnStatus 
	    	    ) {
	    	
	    	//System.out.println(alarmEventService);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.updateNotfnStatus(alarmEventId, notfnType, notfnStatus));
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }
	    
	    
	    @SuppressWarnings("unchecked")
	    @GET
	    @RequestMapping(value="/notfnPendingEventlist",produces="application/json; charset=UTF-8")
	    @ApiOperation(value = "To get all notification pendng events")
	    public String getAllNotfnPendingEventsList() {
	    	
	    	//System.out.println(alarmEventService);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.getNotfnpendingEventList());
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }
	    
		/*public AlarmEventService getAlarmEventService() {
			return alarmEventService;
		}
		public void setAlarmEventService(AlarmEventService alarmEventService) {
			this.alarmEventService = alarmEventService;
		}*/
	    @SuppressWarnings("unchecked")
	    @RequestMapping(value="/saveAlarmEventHistory", method = RequestMethod.POST)
	    @ApiOperation(value = "Save Alarm Event History")
	    public String saveAlarmEventHistory(@ApiParam(value = "alarmEventId", required = true) @QueryParam("alarmEventId") Integer alarmEventId,
	    		@ApiParam(value = "contactId", required = true) @QueryParam("contactId") Integer contactId,
	    	    @ApiParam(value = "eventType", required = true) @QueryParam("notfnType") String notfnType
	    	   ) {
	    	
	    	//System.out.println(alarmEventService);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.saveAlarmNotfnHistory(alarmEventId,contactId,notfnType));
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }
	    
	    @SuppressWarnings("unchecked")
	    @GET
	    @RequestMapping(value="/eventAnalytics")
	    @ApiOperation(value = "To get event analytics")
	    public String getAlarmEventStatistics() {
	    	
	    	//System.out.println(alarmEventService);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.getAlarmEventStatistics());
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }
	    
	    @SuppressWarnings("unchecked")
	    @GET
	    @RequestMapping(value="/eventStatiStics")
	    @ApiOperation(value = "To get event statistics")
	    public String getEventStatistics() {
	    	
	    	//System.out.println(alarmEventService);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.getEventStatistics());
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }
	    
	    @SuppressWarnings("unchecked")
	    @GET
	    @RequestMapping(value="/getAllCustomerEvents",produces="application/json; charset=UTF-8")
	    @ApiOperation(value = "To get events of all Customer")
	   public String getAllCustomerEvents() {
	    	
	    	//System.out.println(alarmEventService);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.getAllAlarmEvents());
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }
	    
	    
	    @SuppressWarnings("unchecked")
	    @GET
	    @RequestMapping(value="/eventsV2",produces="application/json; charset=UTF-8")
	    @ApiOperation(value = "To get all events of Customer")
	    public String getAllEventsWithContacts(
	    	    @ApiParam(value = "customerId", required = false) @QueryParam("customerId") String customerId,
	    	    @ApiParam(value = "userId", required = false) @QueryParam("userId") String userId) {
	    	
	    	//System.out.println(alarmEventService);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.getAllAlarmEventsWithContactDetails(customerId,userId));
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }
	    @SuppressWarnings("unchecked")
	    @RequestMapping(value="/saveSoftwareMessage", method = RequestMethod.POST)
	    @ApiOperation(value = "Save Software message")
	    public String saveSoftwareMessage(@ApiParam(value = "buildingId", required = true) @QueryParam("buildingId") Integer buildingId,
	    		@ApiParam(value = "customerId", required = true) @QueryParam("customerId") Integer customerId,
	    		@ApiParam(value = "deviceId", required = true) @QueryParam("deviceId") String deviceId,
	    		@ApiParam(value = "isUpdate", required = false) @QueryParam("isUpdate") String isUpdate,
	    	    @ApiParam(value = "eventGeneratedTimeInMillis", required = true) @QueryParam("eventGeneratedTimeInMillis") long eventGeneratedTimeInMillis,
	    	    @ApiParam(value = "value", required = false) @QueryParam("value") String value,
	    	    @ApiParam(value = "updatedBy", required = false) @QueryParam("updatedBy") String updatedBy) {
	    	
	    	System.out.println("Value--------->"+value);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.saveSoftwareMessage(buildingId, customerId, deviceId, isUpdate, eventGeneratedTimeInMillis, value,updatedBy));
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }
	    
	    @RequestMapping(value = "/getAllSoftwareMessages", method = RequestMethod.GET,produces="application/json; charset=UTF-8")
		 @ResponseBody
		 public String getAllSoftwareMessages(
		    	    @RequestParam(value = "customerId", required = false) Integer customerId,
		    	    @RequestParam(value = "buildingId", required = false) Integer buildingId) {
		    	
		    	System.out.println("Inside getAllSoftwareMessages------------>"+customerId);
		    	return FPUtil.getJsonResponseAsString(alarmEventService.getAllSoftwareMessages(customerId,buildingId));
		    	
		    }
	    
	    
	    @SuppressWarnings("unchecked")
	    @GET
	    @RequestMapping(value="/swnotfnPendingEventlist",produces="application/json; charset=UTF-8")
	    @ApiOperation(value = "To get all softwarenotification pendng events")
	    public String getAllSoftwareNotfnPendingEventsList() {
	    	
	    	//System.out.println(alarmEventService);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.getAllNotfnPendingSoftwareMessages());
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }

	    @SuppressWarnings("unchecked")
	    @RequestMapping(value="/updateswAlarmEventStatus", method = RequestMethod.POST)
	    @ApiOperation(value = "Reset sw Alarm Event")
	    public String updateSwAlarmEventStatus(
	    	    @ApiParam(value = "alarmEventId", required = true) @QueryParam("alarmEventId") Integer alarmEventId,
	    	    @ApiParam(value = "notfnType", required = true) @QueryParam("notfnType") String notfnType,
	    	    @ApiParam(value = "notfnStatus", required = true) @QueryParam("notfnStatus") String notfnStatus 
	    	    ) {
	    	
	    	//System.out.println(alarmEventService);
	    	return FPUtil.getJsonResponseAsString(alarmEventService.updateSwNotfnStatus(alarmEventId, notfnType, notfnStatus));
	    	
	    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }


}
