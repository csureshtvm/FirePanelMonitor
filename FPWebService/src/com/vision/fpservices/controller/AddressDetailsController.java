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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.utils.ServerContextHelper;
import com.vision.fpservices.db.service.AddressService;
import com.vision.fpservices.dto.AddressDetailsDTO;
import com.vision.fpservices.util.FPUtil;
import com.wordnik.swagger.core.ApiOperation;
import com.wordnik.swagger.jaxrs.JavaHelp;

@RestController 
@RequestMapping("/fpservices/Address.json")
public class AddressDetailsController extends JavaHelp{

	@Autowired
	AddressService addressService;
	
	@Context
    HttpServletResponse response;
	final Logger LOG = LoggerFactory.getLogger(getClass().getName());
	
	
	 	@SuppressWarnings("unchecked")
	    @RequestMapping(value = "/saveAddress", method = RequestMethod.POST)
	    @ApiOperation(value = "To save Address Details")
	    public String saveContact(@QueryParam("addressDetails") String addressDetails,
	    		@QueryParam("addressType") String addressType,
	    	   @QueryParam("buildingId") String buildingId,
	    	    @QueryParam("customerId") String customerId,
	    	    @QueryParam("updatedBy") String updatedBy) {
		 
	    	ObjectMapper mapper=new ObjectMapper();
			AddressDetailsDTO addressDetailsDTO=null;
			try {
				addressDetailsDTO = mapper.readValue(addressDetails, new TypeReference<AddressDetailsDTO>(){});
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
			
	    	return FPUtil.getJsonResponseAsString(addressService.saveOrUpdate(addressDetailsDTO,addressType,buildingId,customerId,updatedBy));
	    	
	    }




}
