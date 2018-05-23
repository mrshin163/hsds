package com.bizsp.eaccount.card.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.card.vo.CardMgmtVo;
import com.bizsp.eaccount.card.vo.CardScheduleVo;
import com.bizsp.eaccount.card.vo.CardVo;
import com.bizsp.framework.extend.DaoSupport;
import com.bizsp.framework.security.vo.LoginUserVO;

@Repository
public class CardDao extends DaoSupport{

	@Autowired
	SqlSessionTemplate sqlSession;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CardVo> selectCardList(CardVo p){
		logger.info("::::::::::::::: selectCardList :::::::::::::::::");
		return pagingList("CardMapper.selectCardList", p, "CardMapper.countCardList");
	}
	
	@SuppressWarnings("unchecked")
	public List<CardVo> selectCardScheduleList(CardVo p){
		logger.info("::::::::::::::: selectCardList :::::::::::::::::");
		return pagingList("CardMapper.selectCardScheduleList", p, "CardMapper.countCardScheduleList");
	}

	public Map<String, CardMgmtVo> selectCardMgmt(CardMgmtVo p){
		logger.info("::::::::::::::: selectCardMgmt :::::::::::::::::");
		return sqlSession.selectOne("CardMapper.selectCardMgmt", p);
	}
	
	public int insertCardMgmt(CardMgmtVo p){
		logger.info("::::::::::::::: insertCardMgmt :::::::::::::::::");
		return update("CardMapper.insertCardMgmt",p);
	}

	public int updateCardMgmt(CardMgmtVo p){
		logger.info("::::::::::::::: updateCardMgmt :::::::::::::::::");
		return update("CardMapper.updateCardMgmt",p);
	}
	public int updateCardMgmt(CardMgmtVo p, String sqlId){
		logger.info("::::::::::::::: updateCardMgmt :::::::::::::::::");
		return update("CardMapper."+sqlId, p);
	}
	
	public int insertCardSchedule(CardScheduleVo p){
		logger.info("::::::::::::::: insertCardSchedule :::::::::::::::::");
		return update("CardMapper.insertCardSchedule",p);
	}
	
	public int deleteCardSchedule(CardScheduleVo p){
		logger.info("::::::::::::::: deleteCardSchedule :::::::::::::::::");
		return update("CardMapper.deleteCardSchedule",p);
	}

	public List<CardScheduleVo> selectCardSchedule(CardScheduleVo p) {
		logger.info("::::::::::::::: selectCardSchedule :::::::::::::::::");
		return sqlSession.selectList("CardMapper.selectCardSchedule", p);
	}
	
	public List<CardScheduleVo> selectCardScheduleVali(CardScheduleVo p) {
		logger.info("::::::::::::::: selectCardSchedule :::::::::::::::::");
		return sqlSession.selectList("CardMapper.selectCardScheduleVali", p);
	}
}
