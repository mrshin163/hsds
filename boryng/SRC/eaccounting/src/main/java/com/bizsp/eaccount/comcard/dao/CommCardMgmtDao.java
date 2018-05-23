package com.bizsp.eaccount.comcard.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.comcard.vo.CommCardMgmtVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class CommCardMgmtDao extends DaoSupport{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CommCardMgmtVo> getCardAQList(CommCardMgmtVo commCardMgmtVo){
		logger.info("::::::::::::::: getCardAQList :::::::::::::::::");
		return pagingList("CommCardMgmtMapper.getCardAQList",commCardMgmtVo, "CommCardMgmtMapper.getCardAQListCount");
	}

	public int updateCardAQMgmt(CommCardMgmtVo commCardMgmtVo){
		logger.info("::::::::::::::: updateCardAQMgmt :::::::::::::::::");
		return update("CommCardMgmtMapper.updateActUser",commCardMgmtVo);
	}
	
	public long getCardApprovalCount(CommCardMgmtVo commCardMgmtVo){
		logger.info("::::::::::::::: getCardApprovalCount :::::::::::::::::");
		return (long)selectCount("CommCardMgmtMapper.getCardApprovalCount",commCardMgmtVo);
	}
}
