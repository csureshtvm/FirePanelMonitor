/*
 * ? 2012  Infosys Technologies Limited, Bangalore, India. All rights reserved.
 *   Version: 1.5
 *   Except for any open source software components embedded in this Infosys  proprietary software program
 *   (?Program?), this Program is protected by copyright laws, international treaties and other pending or existing 
 *   intellectual property rights in India, the United States and other countries. Except as expressly permitted, any 
 *   unauthorized reproduction, storage, transmission in any form or by any means (including without limitation 
 *   electronic, mechanical, printing, photocopying, recording or otherwise), or any distribution of this Program, 
 *   or any portion of it, may result in severe civil and criminal penalties, and will be prosecuted to the maximum 
 *   extent possible under the law.
 */

package com.test.utils;

import java.net.URLClassLoader;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerContextHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ServerContextHelper.class.getName());
	private static final CharSequence INCOMPATIBILITY = Messages
			.getString("INCOMPATIBILITY");
	private static ApplicationContext applicationContext = null;
	private static Object applicationContextLock = new Object();

	public static ApplicationContext getApplicationContext() {
		boolean isConsole = getWebContext(ServerContextHelper.class
				.getClassLoader());
		synchronized (applicationContextLock) {
			if (applicationContext != null) {
				return applicationContext;
			}
			//if (isConsole) {
				applicationContext = new ClassPathXmlApplicationContext(
						"classpath*:SpringContext.xml");
			/*} else {
				applicationContext = new ClassPathXmlApplicationContext(
						"classpath*:SpringContext.xml",
						"classpath*:CacheSupport.xml");
			}*/
			return applicationContext;
		}
	}

	public static Object getBean(String beanName) {
		if (applicationContext == null) {
			getApplicationContext();
		}
		return applicationContext.getBean(beanName);
	}

	public static void init(ApplicationContext appCtx) {
		applicationContext = appCtx;
	}

	public static boolean getWebContext(ClassLoader loader) {
		LOG.info("loader class file: " + loader.getClass().getName());
		LOG.info("Classloader " + loader + ":");

		while (loader != null) {
			if (loader.toString().contains("aham-console-web")) {
				return true;
			}

			if (loader instanceof URLClassLoader) {
				URLClassLoader ucl = (URLClassLoader) loader;
				if (StringUtils.indexOfAny(Arrays.toString(ucl.getURLs()),
						INCOMPATIBILITY.toString().split(",")) > 0) {
					return true;
				}
			}
			loader = loader.getParent();
		}
		return false;
	}
}
