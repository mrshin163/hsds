package com.bizsp.eaccount.admin.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bizsp.eaccount.admin.dao.InterfaceDao;

@Path("/rest/interfaceApi/")
@Component
public class InterfaceService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InterfaceDao interfaceDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("execute/{procName}")
	public long executeInterface(@PathParam("procName") String procName) {
		logger.debug("::::::::::::::::::: executeInterface::::::::::::::::"+procName);

		if ("IF_ACCOUNT2_CD".equals(procName)) 
			return interfaceDao.executeInterfaceAccount2Cd();
		else if ("IF_ACCOUNT_FAV".equals(procName)) 
			return interfaceDao.executeInterfaceAccountFav();
		else if ("IF_CARD".equals(procName)) 
			return interfaceDao.executeInterfaceCard();
		else if ("IF_CUSTOMER".equals(procName)) 
			return interfaceDao.executeInterfaceCustomer();
		else if ("IF_DEPT".equals(procName)) 
			return interfaceDao.executeInterfaceDept();
		else if ("IF_DUTY_CD".equals(procName)) 
			return interfaceDao.executeInterfaceDutyCd();
		else if ("IF_TITLE_ALIAS_CD".equals(procName)) 
			return interfaceDao.executeInterfaceTitleAliasCd();
		else if ("IF_TITLE_CD".equals(procName)) 
			return interfaceDao.executeInterfaceTitleCd();
		else if ("DO_INSERT_USER_AUTH".equals(procName)) 
			return interfaceDao.executeInsertUserAuth();
		else
			return -1;
	}
	
	
}
