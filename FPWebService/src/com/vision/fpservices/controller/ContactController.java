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
import com.vision.fpservices.db.service.ContactService;
import com.vision.fpservices.dto.ContactDetailsDTO;
import com.vision.fpservices.util.FPUtil;
import com.wordnik.swagger.core.ApiOperation;

@RestController 
@RequestMapping("/fpservices/Contacts.json")
public class ContactController {

	
	@Autowired
	ContactService contactService;
	
	@Context
    HttpServletResponse response;

   // @Autowired
   // AlarmEventService alarmEventService;
    final Logger LOG = LoggerFactory.getLogger(getClass().getName());
    
    @SuppressWarnings("unchecked")
    @POST
    @RequestMapping(value = "/saveContact")
    @ApiOperation(value = "To save Contact Details")
    public String saveContact(@QueryParam("contactDetails") String contactDetails,
    	    @QueryParam("contactType") String contactType,
    	   @QueryParam("buildingId") String buildingId,
    	    @QueryParam("customerId") String customerId) {
    	
    	System.out.println("Contact Details=========>"+contactDetails);
    	System.out.println("Contact Type=========>"+contactType);
    	System.out.println("buildingId=========>"+buildingId);
    	ObjectMapper mapper=new ObjectMapper();
		ContactDetailsDTO contactDTO=null;
		try {
			contactDTO = mapper.readValue(contactDetails, new TypeReference<ContactDetailsDTO>(){});
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
		return FPUtil.getJsonResponseAsString(contactService.saveOrUpdate(contactDTO,contactType,buildingId,customerId));
    	
    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }


}
