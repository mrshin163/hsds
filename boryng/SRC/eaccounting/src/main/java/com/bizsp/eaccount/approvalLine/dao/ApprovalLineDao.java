package com.bizsp.eaccount.approvalLine.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.approvalLine.vo.ApprovalPolicyDtlVo;
import com.bizsp.eaccount.approvalLine.vo.ApprovalPolicyVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class ApprovalLineDao extends DaoSupport{

	@Autowired
	SqlSessionTemplate sqlSession;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ApprovalPolicyVo> selectPolicy(ApprovalPolicyVo p){
		logger.info("::::::::::::::: selectPolicy :::::::::::::::::");
		return pagingList("ApprovalLine.selectPolicy", p, "ApprovalLine.countSelectPolicy");
	}
	
	@SuppressWarnings("unchecked")
	public List<ApprovalPolicyDtlVo> selectLine(ApprovalPolicyDtlVo p){
		logger.info("::::::::::::::: selectLine :::::::::::::::::");
		return selectList("ApprovalLine.selectLine", p);
	}
	
	public ApprovalPolicyDtlVo selectLineOne(ApprovalPolicyDtlVo p){
		logger.info("::::::::::::::: selectLine :::::::::::::::::");
		return getSqlSession().selectOne("ApprovalLine.selectLineOne", p);
	}
	
	public ApprovalPolicyVo selectPolicyOne(ApprovalPolicyVo p){
		logger.info("::::::::::::::: selectPolicyOne :::::::::::::::::");
		return getSqlSession().selectOne("ApprovalLine.selectPolicyOne", p);
	}
	
	public long  selectPolicyOneCount(ApprovalPolicyVo p){
		logger.info("::::::::::::::: selectPolicyOneCount :::::::::::::::::");
		return selectCount("ApprovalLine.selectPolicyOneCount", p);
	}
	
	public int insertPolicy(ApprovalPolicyVo p){
		logger.info("::::::::::::::: insertPolicy :::::::::::::::::");
		return update("ApprovalLine.insertPolicy",p);
	}	
	
	public int insertLine(ApprovalPolicyDtlVo p){
		logger.info("::::::::::::::: insertLine :::::::::::::::::");
		return update("ApprovalLine.insertLine",p);
	}

	public int updatePolicy(ApprovalPolicyVo p){
		logger.info("::::::::::::::: updatePolicy :::::::::::::::::");
		return update("ApprovalLine.updatePolicy",p);
	}
	public int updateLine(ApprovalPolicyDtlVo p){
		logger.info("::::::::::::::: updateLine :::::::::::::::::");
		return update("ApprovalLine.updateLine", p);
	}

	
	public int deletePolicy(ApprovalPolicyVo p){
		logger.info("::::::::::::::: deletePolicy :::::::::::::::::");
		return update("ApprovalLine.deletePolicy",p);
	}
	
	public int deleteLine(ApprovalPolicyDtlVo p){
		logger.info("::::::::::::::: deleteLine :::::::::::::::::");
		return update("ApprovalLine.deleteLine",p);
	}
}
