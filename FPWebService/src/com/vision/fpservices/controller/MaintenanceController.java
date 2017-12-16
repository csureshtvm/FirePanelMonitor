package com.vision.fpservices.controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
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
@RequestMapping("/fpservices/Schedule.json")
public class MaintenanceController extends JavaHelp{
	
	@Autowired
	CustomerService customerService;
	
	@Context
	 HttpServletResponse response;
	 final Logger LOG = LoggerFactory.getLogger(getClass().getName());
	 
	 	@SuppressWarnings("unchecked")
	    @GET
	    @RequestMapping(value = "/schedule")
	    @ApiOperation(value = "To get Schedule Details")
	    public String getCustomerDetails(
	    	    @ApiParam(value = "customerId", required = true) @QueryParam("customerId") String customerId) {

	    	return FPUtil.getJsonResponseAsString(customerService.getCustomerDetails(Integer.valueOf(customerId)));
	    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

	    }
}
