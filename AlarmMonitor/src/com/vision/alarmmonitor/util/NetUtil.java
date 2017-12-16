package com.vision.alarmmonitor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.vision.alarmmonitor.dto.BuildingDTO;
import com.vision.alarmmonitor.dto.FacilityResult;

public class NetUtil {
	
	
	public static String getResponseFromService(String urlStr){

		String output=null;
		  try {

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream()),"UTF-8"));

			
			System.out.println("Output from Server .... \n");
			StringBuffer buf=new StringBuffer();
			while ((output = br.readLine()) != null) {
				buf.append(output);
			}
			output=buf.toString();
			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		  
		  

		return output;
		
	}
	public static String postResponseToServer(String urlStr){

		String output=null;
		  try {

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			
			System.out.println("Output from Server .... \n");
			StringBuffer buf=new StringBuffer();
			while ((output = br.readLine()) != null) {
				buf.append(output);
			}
			output=buf.toString();
			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		  
		  

		return output;
		
	}
	
	public static String postResponseToServerAsUTF8(String urlStr){

		String output=null;
		  try {

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			
			System.out.println("Output from Server .... \n");
			StringBuffer buf=new StringBuffer();
			while ((output = br.readLine()) != null) {
				buf.append(output);
			}
			output=buf.toString();
			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		  
		  

		return output;
		
	}
	// http://localhost:8080/RESTfulExample/json/product/get
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		
		String data=getResponseFromService("http://localhost:8080/FPWebService/fpservices/Building.json/buildings?customerId=1");
		
		System.out.println("Data====>"+data);
		ObjectMapper mapper=new ObjectMapper();
		List<BuildingDTO> bldgs=mapper.readValue(data, new TypeReference<List<BuildingDTO>>(){});
		//mapper.registerSubtypes(Building.class);
		//System.out.println("result Data Name:"+result.getResultName());
		//System.out.println("result Data List:"+result.getResultList());
		
		for(BuildingDTO bldg : bldgs){
			
			//Building bldg=(Building)ob;
			System.out.println("Building Details -->"+bldg.getBuildingName());
		}
		
		
	}
	

}
