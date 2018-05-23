package com.bizsp.eaccount.report.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.dept.vo.DeptVo;
import com.bizsp.eaccount.report.vo.CardProcessStatusVo;
import com.bizsp.eaccount.report.vo.CardProcessVo;
import com.bizsp.eaccount.report.vo.DeptListVo;
import com.bizsp.eaccount.report.vo.IndividualCardVo;
import com.bizsp.eaccount.user.vo.UserVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class ReportDao extends DaoSupport{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<CardProcessStatusVo> getCardProcessStatusList(Map<String,String> paramMap){
		logger.info("::::::::::::::: getCardProcessStatusList :::::::::::::::::");
		return selectList("ReportMapper.getCardProcessStatusList",paramMap);
	}
	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Map<String,Object>> getUnapprovedList(Map<String,String> paramMap){
		logger.info("::::::::::::::: getUnapprovedList :::::::::::::::::");
		return selectList("ReportMapper.getUnapprovedList",paramMap);
	}
	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Map<String,Object>> getApprovedList(Map<String,String> paramMap){
		logger.info("::::::::::::::: getApprovedList :::::::::::::::::");
		return selectList("ReportMapper.getApprovedList",paramMap);
	}
	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Map<String,Object>> getExceptList(Map<String,String> paramMap){
		logger.info("::::::::::::::: getExceptList :::::::::::::::::");
		return selectList("ReportMapper.getExceptList",paramMap);
	}
	
	
	
	/* 부서별*/
	
	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<CardProcessStatusVo> getCardProcessStatusListDept(Map<String,Object> paramMap){
		logger.info("::::::::::::::: getCardProcessStatusList :::::::::::::::::");
		return selectList("ReportMapper.getCardProcessStatusListDept",paramMap);
	}
	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Map<String,Object>> getUnapprovedListDept(Map<String,Object> paramMap){
		logger.info("::::::::::::::: getUnapprovedList :::::::::::::::::");
		return selectList("ReportMapper.getUnapprovedListDept",paramMap);
	}
	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Map<String,Object>> getApprovedListDept(Map<String,Object> paramMap){
		logger.info("::::::::::::::: getApprovedList :::::::::::::::::");
		return selectList("ReportMapper.getApprovedListDept",paramMap);
	}
	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Map<String,Object>> getExceptListDept(Map<String,Object> paramMap){
		logger.info("::::::::::::::: getExceptList :::::::::::::::::");
		return selectList("ReportMapper.getExceptListDept",paramMap);
	}
	
	
	
	
	
	
	/**
	 * @return
	 */
	public Map<String,Object> getDefaultWorkduration(){
		logger.info("::::::::::::::: getDefaultWorkduration :::::::::::::::::");
		return getSqlSession().selectOne("ReportMapper.getDefaultWorkduration");
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DeptVo> getLowDeptList(DeptVo deptVo){
		logger.info("::::::::::::::: getLowDeptList :::::::::::::::::");
		return selectList("ReportMapper.getLowDeptList", deptVo);
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserVo> getLowUserList(DeptListVo deptListVo){
		logger.info("::::::::::::::: getLowUserList :::::::::::::::::");
		return selectList("ReportMapper.getLowUserList", deptListVo);
	}
	
	
	
	
	
	
	/** 
	 * @param cardProcessVo
	 * @return List<CardProccessVo>
	 */
	@SuppressWarnings("unchecked")
	public List<CardProcessVo> getCardProcessStatusListAll(CardProcessVo cardProcessVo){
		logger.info("::::::::::::::: getCardProcessStatusListAll :::::::::::::::::");
		return pagingList("ReportMapper.getCardProcessStatusListAll", cardProcessVo, "ReportMapper.getCardProcessStatusListAllCount");
	}

	public long getCardProcessStatusListAllSum(CardProcessVo cardProcessVo){
		logger.info("::::::::::::::: getCardProcessStatusListAllSum :::::::::::::::::");
		return getSqlSession().selectOne("ReportMapper.getCardProcessStatusListAllSum", cardProcessVo);
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<CardProcessVo> getUnapprovedListAll(CardProcessVo cardProcessVo){
		logger.info("::::::::::::::: getUnapprovedListAll :::::::::::::::::");
		return pagingList("ReportMapper.getUnapprovedListAll",cardProcessVo,"ReportMapper.getUnapprovedListAllCount");
	}
	public long getUnapprovedListAllSum(CardProcessVo cardProcessVo){
		logger.info("::::::::::::::: getUnapprovedListAllSum :::::::::::::::::");
		return getSqlSession().selectOne("ReportMapper.getUnapprovedListAllSum",cardProcessVo);
	}
	
	
	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<CardProcessVo> getApprovedListAll(CardProcessVo cardProcessVo){
		logger.info("::::::::::::::: getApprovedListAll :::::::::::::::::");
		return pagingList("ReportMapper.getApprovedListAll",cardProcessVo,"ReportMapper.getApprovedListAllCount");
	}
	public long getApprovedListAllSum(CardProcessVo cardProcessVo){
		logger.info("::::::::::::::: getApprovedListAllSum :::::::::::::::::");
		return getSqlSession().selectOne("ReportMapper.getApprovedListAllSum",cardProcessVo);
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<CardProcessVo> getExceptListAll(CardProcessVo cardProcessVo){
		logger.info("::::::::::::::: getExceptListAll :::::::::::::::::");
		return pagingList("ReportMapper.getExceptListAll",cardProcessVo,"ReportMapper.getExceptListAllCount");
	}
	public long getExceptListAllSum(CardProcessVo cardProcessVo){
		logger.info("::::::::::::::: getExceptListAllSum :::::::::::::::::");
		return getSqlSession().selectOne("ReportMapper.getExceptListAllSum",cardProcessVo);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param individualCardVo
	 * @return List<IndividualCardVo>
	 */
	@SuppressWarnings("unchecked")
	public List<IndividualCardVo> selectIndividualCard(IndividualCardVo individualCardVo){
		logger.info("::::::::::::::: selectIndividualCard :::::::::::::::::");
		return selectList("ReportMapper.selectIndividualCard", individualCardVo);
	}
	

	public long countIndividualCard(IndividualCardVo individualCardVo){
		logger.info("::::::::::::::: countIndividualCard :::::::::::::::::");
		return selectCount("ReportMapper.countIndividualCard", individualCardVo);
	}
}
