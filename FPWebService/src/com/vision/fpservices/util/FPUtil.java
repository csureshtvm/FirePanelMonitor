package com.vision.fpservices.util;

import org.codehaus.jackson.map.ObjectMapper;

public class FPUtil {
	
	public static String getJsonResponseAsString(Object obj){
		if(obj==null){
			return null;
		}
		String json=null;
		try{
			ObjectMapper mapper=new ObjectMapper();
			json=mapper.writeValueAsString(obj);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}

}
