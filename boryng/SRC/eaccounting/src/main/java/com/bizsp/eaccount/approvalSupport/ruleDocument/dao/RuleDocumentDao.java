package com.bizsp.eaccount.approvalSupport.ruleDocument.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.approvalSupport.ruleDocument.vo.RuleDocumentVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class RuleDocumentDao extends DaoSupport {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	public List<RuleDocumentVo> getRuleDocumentList(RuleDocumentVo ruleDocumentVo) {
		return selectList("RuleDocumentMapper.selectRuleDocumentList", ruleDocumentVo);
	}

	public RuleDocumentVo getRuleDocument(RuleDocumentVo param) {
		logger.info(":::::::::: getRuleDocument ::::::::::");

		return (RuleDocumentVo) getSqlSession().selectOne("RuleDocumentMapper.getRuleDocument", param);
	}
}