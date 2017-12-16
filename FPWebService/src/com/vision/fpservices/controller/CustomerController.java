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
import com.vision.fpservices.db.service.CustomerService;
import com.vision.fpservices.db.serviceImpl.CustomerServiceImpl;
import com.vision.fpservices.util.FPUtil;
import com.wordnik.swagger.core.ApiOperation;
import com.wordnik.swagger.core.ApiParam;
import com.wordnik.swagger.jaxrs.JavaHelp;

@RestController 
@RequestMapping("/fpservices/Customer.json")
public class CustomerController extends JavaHelp{

    @Context
    HttpServletResponse response;
    
    @Autowired
    CustomerService customerService;
    
    final Logger LOG = LoggerFactory.getLogger(getClass().getName());

    @SuppressWarnings("unchecked")
    @GET
    @RequestMapping(value = "/customer")
    @ApiOperation(value = "To get Customer Details")
    public String getCustomerDetails(
    	    @ApiParam(value = "customerId", required = true) @QueryParam("customerId") String customerId) {

    	return FPUtil.getJsonResponseAsString(customerService.getCustomerDetails(Integer.valueOf(customerId)));
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }
    
    @SuppressWarnings("unchecked")
    @GET
    @RequestMapping(value = "/customerAnalytics")
    @ApiOperation(value = "To get Customer analytics Details")
    public String getCustomerAnalytcs() {

    	return FPUtil.getJsonResponseAsString(customerService.getCustomerStatistics());
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }
    
    
    @SuppressWarnings("unchecked")
    @POST
    @RequestMapping(value = "/saveCustomer")
    @ApiOperation(value = "Save Customer Details")
    public String saveAlarmEventHistory(@ApiParam(value = "customerName", required = true) @QueryParam("customerName") String customerName,
    	    @ApiParam(value = "createdBy", required = true) @QueryParam("createdBy") String createdBy
    	   ) {
    	
    	//System.out.println(alarmEventService);
    	return FPUtil.getJsonResponseAsString(customerService.createCustomer(customerName, createdBy));
    	
    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }
    @SuppressWarnings("unchecked")
    @POST
    @RequestMapping(value = "/updateCustomer")
    @ApiOperation(value = "Update Customer Details")
    public String updateCustomer(@ApiParam(value = "customerName", required = true) @QueryParam("customerName") String customerName,
    	    @ApiParam(value = "updatedBy", required = true) @QueryParam("updatedBy") String updatedBy,
    	    @ApiParam(value = "customerId", required = true) @QueryParam("customerId") Integer customerId
    	   ) {
    	
    	//System.out.println(alarmEventService);
    	return FPUtil.getJsonResponseAsString(customerService.updateCustomer(customerName, updatedBy,customerId));
    	
    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }
    
    @SuppressWarnings("unchecked")
    @GET
    @RequestMapping(value = "/customerBldgSummaryList",produces="application/json; charset=UTF-8")
    @ApiOperation(value = "To get Customer analytics Details")
    public String getAllCustomerBuildingsSummaryList() {

    	return FPUtil.getJsonResponseAsString(customerService.getAllCustomerBuildingsSummaryList());
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }

}
