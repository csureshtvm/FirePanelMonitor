package com.vision.fpservices.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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
import com.vision.fpservices.db.service.BuildingService;
import com.vision.fpservices.dto.BuildingsummaryDTO;
import com.vision.fpservices.util.FPUtil;
import com.vision.fpservices.util.PropertyUtil;
import com.wordnik.swagger.core.ApiOperation;
import com.wordnik.swagger.core.ApiParam;
import com.wordnik.swagger.jaxrs.JavaHelp;

@RestController 
@RequestMapping("/fpservices/Building.json")
public class BuildingController extends JavaHelp{

    @Context
    HttpServletResponse response;
   
    @Autowired
    BuildingService buildingService;
    
    final Logger LOG = LoggerFactory.getLogger(getClass().getName());

    
    @SuppressWarnings("unchecked")
    @GET
    @RequestMapping(value ="/{buildingId}/building")
    @ApiOperation(value = "To get all building details")
    public String getBuildings(
    	    @ApiParam(value = "buildingId", required = true) @PathParam("buildingId") int buildingId) {

    	
    	return FPUtil.getJsonResponseAsString(buildingService.getBuilding(buildingId));
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }

    @SuppressWarnings("unchecked")
    @GET
    @RequestMapping(value = "/buildings",produces="application/json; charset=UTF-8")
    @ApiOperation(value = "To get all buildings")
    public String getAllBuildings(
    	    @ApiParam(value = "customerId", required = true) @QueryParam("customerId") String customerId,
    	    @ApiParam(value = "userId", required = false) @QueryParam("userId") String userId) {

    	System.out.println("customerI===================>"+customerId);
    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
    	
    	return FPUtil.getJsonResponseAsString(buildingService.getAllBuildings(customerId,userId));
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }
    
    @SuppressWarnings("unchecked")
    @GET
    @RequestMapping(value = "/{buildingId}/building/contacts")
    @ApiOperation(value = "To get all contacts of building")
    public String getAllCOntactsOfBuilding(
    	    @ApiParam(value = "buildingId", required = true) @PathParam("buildingId") int buildingId) {

    	
    	return FPUtil.getJsonResponseAsString(buildingService.getAllContactsOfBuilding(buildingId));
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }
    
    
    
    @SuppressWarnings("unchecked")
    @GET
    @RequestMapping(value = "/images/download/{imageFile}")
    @ApiOperation(value = "To retreive building image")
    public void downloadImage(
    	    @ApiParam(value = "imageFile", required = true) @PathParam("imageFile") String imageFile){
    	
    try {
    File file=new File(PropertyUtil.getInstance().getConfigProperty("fpserver.building_image_loc")+imageFile);
    InputStream fis;
	
		fis = new FileInputStream(file);
	
	//String mimeType = ctx.getMimeType(file.getAbsolutePath());
	//response.setContentType(mimeType != null? mimeType:"application/octet-stream");
    response.setContentType("image/jpeg");
	response.setContentLength((int) file.length());
	//response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	
	OutputStream os  = response.getOutputStream();
	byte[] bufferData = new byte[1024];
	int read=0;
	while((read = fis.read(bufferData))!= -1){
		os.write(bufferData, 0, read);
	}
	os.flush();
	os.close();
	fis.close();
	System.out.println("File downloaded at client successfully");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
    
    
    
    @SuppressWarnings("unchecked")
    @POST
    @RequestMapping(value = "/saveBuilding")
    @ApiOperation(value = "To save Building")
   //@Consumes(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public String saveContact(@QueryParam("buildingDetails") String buildingDetails,
    	    @QueryParam("customerId") Integer customerId,
    	    @QueryParam("updatedBy") String updatedBy) {
    	
    	ObjectMapper mapper=new ObjectMapper();
		BuildingsummaryDTO bldgSummaryDTO=null;
		try {
			bldgSummaryDTO = mapper.readValue(buildingDetails, new TypeReference<BuildingsummaryDTO>(){});
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
		return FPUtil.getJsonResponseAsString(buildingService.saveBuilding(bldgSummaryDTO, customerId, updatedBy));
    	
    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }
    
    @SuppressWarnings("unchecked")
    @POST
    @RequestMapping(value = "/saveBuildingNotificationSettings")
    @ApiOperation(value = "To save Notification settings")
    public String saveNotificationSettings(@QueryParam("buildingId") String buildingId,
    	    @QueryParam("contactType") String contactType,
    	    @QueryParam("notifySMS") String notifySMS,
    	    @QueryParam("notifyEmail") String notifyEmail,
    	    @QueryParam("updatedBy") String updatedBy) {
    	
    	//System.out.println(alarmEventService);
		return FPUtil.getJsonResponseAsString(buildingService.saveNotificationSettings(buildingId, contactType, notifySMS,notifyEmail,updatedBy));
    	
    	//return ((BuildingServicesProvider) ServerContextHelper.getBean( "buildingServicesProvider" ) ).getBuildings(customerId);
    	//return Response.ok().entity( "getClusterRecommendations Test: "+result+":"+testId ).build();

    }
    


}
