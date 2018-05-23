package com.bizsp.eaccount.approvalSupport.customer.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.approvalSupport.customer.vo.CustomerVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class CustomerDao extends DaoSupport{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	public List<CustomerVo> getCustomerList(CustomerVo customerVo){
		
		logger.info("::::::::::::::::::: getCustomerList ::::::::::::::::::::::::");
		return selectList("CustomerMapper.selectCustomerList", customerVo);
	}
}
