package com.vision.notificationservices.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.Map;

public class PropertyUtil {

	Properties configProperties = null;
	static PropertyUtil propertyUtil = null;
	Logger log = Logger.getLogger("PropertyUtil");

	private PropertyUtil() {
	}

	public static PropertyUtil getInstance() {
		if (propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
		return propertyUtil;
	}

	private Properties loadPropertyFile(String fileName) {

		Properties props = new Properties();
		InputStream inputStr = getClass().getClassLoader().getResourceAsStream(
				fileName);
		try {
			props.load(inputStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR while loading Property file!!!!!!!!! "
					+ e.toString());
		}

		return props;
	}

	private void loadConfigProperties(String fileName) {

		if (configProperties == null || configProperties.size() == 0) {
			configProperties = loadPropertyFile(fileName);
		}

	}

	public String getConfigProperty(String propName) {
		loadConfigProperties(getConfigPropertyFileLoc());
		return configProperties.getProperty(propName);
	}

	public void setConfigProperties(String propName, String propValue) {
		loadConfigProperties(getConfigPropertyFileLoc());
		configProperties.setProperty(propName, propValue);
	}

	private String getConfigPropertyFileLoc() {
		return "config.properties";
	}

		
}
