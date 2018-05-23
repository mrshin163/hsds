package com.bizsp.eaccount.dept.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bizsp.eaccount.dept.dao.DeptDao;
import com.bizsp.eaccount.dept.vo.DeptVo;


@Path("/rest/deptApi/")
@Component
public class DeptService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	DeptDao deptDao;
	
	/**
	 * 부서조회
	 * @param deptVo
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deptList")
	public List<DeptVo> getDeptList(DeptVo deptVo){
		logger.info("::::::::::::::::: getDeptList :::::::::::::::::::::");
		return deptDao.getDeptList(deptVo);
	}
	
	/**
	 * 부서 조회 (for Ajax Autocomplete)
	 * @param deptName
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deptNameSearch/{deptName}")
	public List<String> getDeptNameList(@PathParam("deptName") String deptName){
		logger.debug("::::::::::::::::::: getDeptNameList ::::::::::::::::");
		DeptVo deptVo = new DeptVo();
		deptVo.setDeptName(deptName);
		
		List<DeptVo> deptList = deptDao.getDeptList(deptVo); 
		List<String> deptNameList = new ArrayList<String>();
		
		for (DeptVo vo : deptList) {
			deptNameList.add(vo.getDeptName());
		}
		
		return deptNameList; 
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deptSearchList/{deptName}/{deptCode}")
	public List<DeptVo> getDeptSerchList(@PathParam("deptName") String deptName, @PathParam("deptCode") String deptCode){
		DeptVo deptVo = new DeptVo();		
		try {
			deptVo.setDeptName(URLDecoder.decode(deptName, "UTF-8"));
			deptVo.setDeptCode(deptCode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deptDao.getDeptList(deptVo);
	}
	
	
}
