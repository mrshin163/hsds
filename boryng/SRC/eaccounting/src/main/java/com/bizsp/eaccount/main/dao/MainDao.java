package com.bizsp.eaccount.main.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.framework.extend.DaoSupport;

@Repository
public class MainDao extends DaoSupport{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @param p
	 * @return
	 */
	public long getCountOfUnreq(Map<String, String> p){
		logger.info("::::::::::::::: getCountOfUnreq :::::::::::::::::");
		return (Long)getSqlSession().selectOne("MainMapper.getCountOfUnreq", p);
	}

	/**
	 * @param p
	 * @return
	 */
	public long getCountOfRej(Map<String, String> p){
		logger.info("::::::::::::::: getCountOfRej :::::::::::::::::");
		return (Long)getSqlSession().selectOne("MainMapper.getCountOfRej", p);
	}
	
	/**
	 * @param p
	 * @return
	 */
	public long getCountOfIng(Map<String, String> p){
		logger.info("::::::::::::::: getCountOfIng :::::::::::::::::");
		return (Long)getSqlSession().selectOne("MainMapper.getCountOfIng", p);
	}
	
	/**
	 * @param p
	 * @return
	 */
	public long getCountOfComplete(Map<String, String> p){
		logger.info("::::::::::::::: getCountOfComplete :::::::::::::::::");
		return (Long)getSqlSession().selectOne("MainMapper.getCountOfComplete", p);
	}
}
