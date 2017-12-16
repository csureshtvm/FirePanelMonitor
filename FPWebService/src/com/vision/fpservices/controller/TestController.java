package com.vision.fpservices.controller;

import javax.ws.rs.Produces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/fpservices")
public class TestController {

	
	@RequestMapping(value = "/testMethod", method = RequestMethod.GET)
	public String testMethod(@RequestParam("id") String id){
		System.out.println("Inside Method---->"+id);
		return "Received ID-"+id;
	}
	
}
