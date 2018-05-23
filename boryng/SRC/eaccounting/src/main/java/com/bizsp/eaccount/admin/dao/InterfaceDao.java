package com.bizsp.eaccount.admin.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.framework.extend.DaoSupport;

@Repository
public class InterfaceDao extends DaoSupport{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @return
	 */
	public long executeInterfaceAccount2Cd(){
		logger.info("::::::::::::::: executeInterfaceAccount2Cd :::::::::::::::::");
		
		long cnt = 0;
		Map<String, Object> a = new HashMap<String, Object>();
		select("InterfaceMapper.ifAccount2Cd", a);
		cnt = (Long)a.get("cnt");
		
		return cnt;
	}
	
	/**
	 * @return
	 */
	public long executeInterfaceAccountFav(){
		logger.info("::::::::::::::: executeInterfaceAccountFav :::::::::::::::::");
		
		long cnt = 0;
		Map<String, Object> a = new HashMap<String, Object>();
		select("InterfaceMapper.ifAccountFav", a);
		cnt = (Long)a.get("cnt");
		
		return cnt;
	}
	
	/**
	 * @return
	 */
	public long executeInterfaceCard(){
		logger.info("::::::::::::::: executeInterfaceCard :::::::::::::::::");
		
		long cnt = 0;
		Map<String, Object> a = new HashMap<String, Object>();
		select("InterfaceMapper.ifCard", a);
		cnt = (Long)a.get("cnt");
		
		return cnt;
	}
	
	/**
	 * @return
	 */
	public long executeInterfaceCustomer(){
		logger.info("::::::::::::::: executeInterfaceCustomer :::::::::::::::::");
		
		long cnt = 0;
		Map<String, Object> a = new HashMap<String, Object>();
		select("InterfaceMapper.ifCustomer", a);
		cnt = (Long)a.get("cnt");
		
		return cnt;
	}
	
	/**
	 * @return
	 */
	public long executeInterfaceDept(){
		logger.info("::::::::::::::: executeInterfaceDept :::::::::::::::::");
		
		long cnt = 0;
		Map<String, Object> a = new HashMap<String, Object>();
		select("InterfaceMapper.ifDept", a);
		cnt = (Long)a.get("cnt");
		
		return cnt;
	}
	
	/**
	 * @return
	 */
	public long executeInterfaceDutyCd(){
		logger.info("::::::::::::::: executeInterfaceDutyCd :::::::::::::::::");
		
		long cnt = 0;
		Map<String, Object> a = new HashMap<String, Object>();
		select("InterfaceMapper.ifDutyCd", a);
		cnt = (Long)a.get("cnt");
		
		return cnt;
	}
	
	/**
	 * @return
	 */
	public long executeInterfaceTitleAliasCd(){
		logger.info("::::::::::::::: executeInterfaceTitleAliasCd :::::::::::::::::");
		
		long cnt = 0;
		Map<String, Object> a = new HashMap<String, Object>();
		select("InterfaceMapper.ifTitleAliasCd", a);
		cnt = (Long)a.get("cnt");
		
		return cnt;
	}

	/**
	 * @return
	 */
	public long executeInterfaceTitleCd(){
		logger.info("::::::::::::::: executeInterfaceTitleCd :::::::::::::::::");
		
		long cnt = 0;
		Map<String, Object> a = new HashMap<String, Object>();
		select("InterfaceMapper.ifTitleCd", a);
		cnt = (Long)a.get("cnt");
		
		return cnt;
	}
	
	/**
	 * @return
	 */
	public long executeInsertUserAuth(){
		logger.info("::::::::::::::: executeInsertUserAuth :::::::::::::::::");
		
		long cnt = 0;
		Map<String, Object> a = new HashMap<String, Object>();
		select("InterfaceMapper.insertUserAuth", a);
		cnt = (Long)a.get("cnt");
		
		return cnt;
	}
	
}
