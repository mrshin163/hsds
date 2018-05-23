package com.bizsp.eaccount.approvalSupport.customer.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bizsp.eaccount.approvalSupport.customer.dao.CustomerDao;
import com.bizsp.eaccount.approvalSupport.customer.vo.CustomerVo;

@Path("/rest/customerApi")
@Component
public class CustomerService {
	
	@Autowired
	CustomerDao customerDao;
	
	/**
	 * 거래처  리스트 조회 
	 * @param customerName
	 * @return
	 */
	@GET
	@Path("customerList/{customerName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerVo> getCustomerList(@PathParam("customerName") String customerName){
		CustomerVo customerVo = new CustomerVo();
		customerVo.setCustomerName(customerName);
		return customerDao.getCustomerList(customerVo);
	}
	
	/**
	 * 거래처 이름 검색 (for ajax autocomplete)
	 * @param customerName
	 * @return
	 */
	@GET
	@Path("customerNameSearch/{customerName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getCustomerNameSearch(@PathParam("customerName") String customerName){
		
		CustomerVo customerVo = new CustomerVo();
		customerVo.setCustomerName(customerName);
		
		List<CustomerVo> customerList = customerDao.getCustomerList(customerVo);
		List<String> customerNameList = new ArrayList<String>();
		
		for (CustomerVo cuVo : customerList) {
			customerNameList.add(cuVo.getCustomerName());
		}
		
		return customerNameList;
		
	}
	
}
