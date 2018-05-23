package com.bizsp.eaccount.batch.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.batch.vo.UnApprovedAQVo;
import com.bizsp.framework.extend.DaoSupport;
import com.bizsp.framework.security.vo.LoginUserVO;
import com.bizsp.framework.util.security.SecurityUtils;

@Repository
public class BatchDao extends DaoSupport{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	private static SecurityUtils securityUtils;

	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<UnApprovedAQVo> getUnapprovedCardAq(){
		logger.info("::::::::::::::: getUnapprovedCardAq :::::::::::::::::");
		Map<String,String> param = new HashMap<String,String>();
		param.put("companyCd", "PHARM");
		
		return selectList("BatchMapper.selectUnapprovedCardAqList",param);
	}

	
	/**
	 * @param userId 수신자ID
	 * @param emailAddr 수신자이메일주소
	 * @param title 이메일제목
	 * @param content 이메일 내용
	 * @return 
	 */
	public int insertEmailLog(String userId, String emailAddr, String title, String content) {
		logger.info("::::::::::::::: insertEmailLog :::::::::::::::::");
		Map<String, String> param = new HashMap<String, String>();
		param.put("companyCd", 	"PHARM");
		param.put("userId", 	userId);
		param.put("emailAddr", 	emailAddr);
		param.put("title", 		title);
		param.put("content", 	content);
		
		return (Integer) insert("BatchMapper.insertUnapprovedCardAqListEmailLog", param);
	}
	
	
	/**
	 * 
	 * @param content
	 * @param sendPhoneNum
	 * @param getPhoneNum
	 * @param sendUserId
	 * @return int
	 */
	public int insertSms(String content, String sendPhoneNum, String getPhoneNum, String sendUserId){
		logger.info("::::::::::::::: insertSms :::::::::::::::::");
		Map<String, String> param = new HashMap<String, String>();
		param.put("content", content);
		param.put("sendPhoneNum", sendPhoneNum);
		param.put("getPhoneNum", getPhoneNum);
		param.put("sendUserId", sendUserId);
		return (Integer) insert("BatchMapper.insertUnapprovedCardAqListSmsLog", param);
	}
}
