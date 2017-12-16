package com.vision.fpservices.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.utils.ServerContextHelper;
import com.vision.fpservices.db.service.UserServices;
import com.vision.fpservices.dto.UserDTO;
import com.vision.fpservices.util.FPUtil;
import com.wordnik.swagger.core.ApiOperation;
import com.wordnik.swagger.core.ApiParam;
import com.wordnik.swagger.jaxrs.JavaHelp;

@RestController 
@RequestMapping("/fpservices/User.json")
public class UserController extends JavaHelp{

	
	@Context
    HttpServletResponse response;
	
	@Autowired
	UserServices userServices;

   // @Autowired
   // AlarmEventService alarmEventService;
    final Logger LOG = LoggerFactory.getLogger(getClass().getName());
    @SuppressWarnings("unchecked")
    @POST
    @RequestMapping(value = "/loginUser")
    @ApiOperation(value = "To check user login")
    public String loginUser(
    	    @ApiParam(value = "userName", required = true) @QueryParam("userName") String userName,
    	    @ApiParam(value = "password", required = true) @QueryParam("password") String password) {
    	
    	//System.out.println(alarmEventService);
    	return FPUtil.getJsonResponseAsString(userServices.loginUser(userName, password));
    	
    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }
    
    
    @SuppressWarnings("unchecked")
    @POST
    @RequestMapping(value = "/createNewUser")
    @ApiOperation(value = "To save User Details")
    public String createUser(@ApiParam(value = "userDetails", required = true) @QueryParam("userDetails") String userDetails,
    		@ApiParam(value = "createdBy", required = true) @QueryParam("createdBy") String createdBy) {
    	
    	
    	ObjectMapper mapper=new ObjectMapper();
		UserDTO userDTO=null;
		try {
			userDTO = mapper.readValue(userDetails, new TypeReference<UserDTO>(){});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	//System.out.println(alarmEventService);
    	return FPUtil.getJsonResponseAsString((userServices.createNewUser(userDTO,createdBy)));
    	
    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }
    
    @SuppressWarnings("unchecked")
    @POST
    @RequestMapping(value = "/changePassword")
    @ApiOperation(value = "To change User Password")
    public String changePassword(@ApiParam(value = "userName", required = true) @QueryParam("userName") String userName,
    		@ApiParam(value = "newPw", required = true) @QueryParam("newPw") String newPassword) {
    	
    	
    	
    	//System.out.println(alarmEventService);
    	return FPUtil.getJsonResponseAsString(userServices.updatePassword(userName, newPassword));
    	
    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }



}
