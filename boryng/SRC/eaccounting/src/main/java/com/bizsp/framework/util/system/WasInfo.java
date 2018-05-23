package com.bizsp.framework.util.system;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.web.ContextHolder;


public class WasInfo {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public void initilize(){
		log.info("Initializing devkimFramework");
		ApplicationContext  apx =  ContextHolder.getContext();
		ServletContext servletConfig = apx.getAutowireCapableBeanFactory().getBean(javax.servlet.ServletContext.class);

		Runtime runTime = Runtime.getRuntime();
		long free = runTime.freeMemory();
		long total = runTime.totalMemory();
		if (log.isInfoEnabled()) {
			StringBuffer infoBuffer = new StringBuffer();
			infoBuffer.append("\n\t--------------------------------------------");
			infoBuffer.append("\n\tSystem Information");
			infoBuffer.append("\n\t--------------------------------------------");
			infoBuffer.append("\n\t- Version:          " + "devkim framework");
			infoBuffer.append("\n\t- Build:            " + "2011.09.15 - v1.0");
			infoBuffer.append("\n\t- Contact Info:     " + "Dev.KIM [Kim jin hyug] : email : gate7711@gmail.com");

			infoBuffer.append("\n\t- Servlet Engine:   " + servletConfig.getServerInfo());
			infoBuffer.append("\n\t- Context Name:     " + servletConfig.getServletContextName());
			infoBuffer.append("\n\t- Context Home:     " + servletConfig.getRealPath("/"));
			infoBuffer.append("\n\t- OS Info:          " + SystemInfo.OS_NAME + "-" + SystemInfo.OS_VERSION);
			infoBuffer.append("\n\t- Java Home:        " + SystemInfo.JAVA_HOME);
			infoBuffer.append("\n\t- Java Version:     " + SystemInfo.JAVA_VERSION);
			infoBuffer.append("\n\t- Java Vendor:      " + SystemInfo.JAVA_VENDOR);
			infoBuffer.append("\n\t- total Memory:     " + StringUtil.format((total / 1024)) + "KB");
			infoBuffer.append("\n\t- used Memory:	    " + StringUtil.format((total / 1024 - free / 1024)) + "KB");
			infoBuffer.append("\n\t- free Memory:      " + StringUtil.format((free / 1024)) + "KB");
			infoBuffer.append("\n\t- getFreeRatio:     " + StringUtil.format((free * 100 / total))+ "%");
			infoBuffer.append("\n\t--------------------------------------------");

			log.info(infoBuffer.toString());
			//printMemory();
			log.info("Initialized successfully");
			log.info("---------------------------------");
			infoBuffer = null;
		}
		//캐시를 불러 온다.
		
		/*OSCacheService oSCacheService = CacheManager.getInstance();
		oSCacheService.initialize();*/
	}

	/**
	 * 임시 메소드
	 */
	private void printMemory() {
		Runtime runTime = Runtime.getRuntime();
		long free = runTime.freeMemory();
		long total = runTime.totalMemory();
		StringBuffer infoBuffer = new StringBuffer();
		infoBuffer.append("\n\t--------------------------------------------");
		infoBuffer.append("totalMemory : " + StringUtil.format((total / 1024)) + "KB");
		infoBuffer.append("useMemory : "+ StringUtil.format((total / 1024 - free / 1024)) + "KB");
		infoBuffer.append("freeMemory : " + StringUtil.format((free / 1024)) + "KB");
		infoBuffer.append("getFreeRatio : " + StringUtil.format((free * 100 / total))+ "%");
		log.info(infoBuffer.toString());
	}
}
