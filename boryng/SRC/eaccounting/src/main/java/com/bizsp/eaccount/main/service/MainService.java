package com.bizsp.eaccount.main.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bizsp.eaccount.approval.dao.ApprovalDao;
import com.bizsp.eaccount.approval.vo.ApprovalMgmtVo;
import com.bizsp.eaccount.comcard.dao.CommCardMgmtDao;
import com.bizsp.eaccount.comcard.vo.CommCardMgmtVo;
import com.bizsp.eaccount.main.dao.MainDao;
import com.bizsp.eaccount.notice.dao.NoticeDao;
import com.bizsp.eaccount.notice.vo.NoticeVo;
import com.bizsp.framework.util.lang.DateUtil;
import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.security.SecurityUtils;

@Component
@Path("/rest/mainApi/")
public class MainService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private SecurityUtils secu;
	
	@Autowired
	private MainDao mainDao;	
	
	@Autowired
	private NoticeDao noticeDao;	
	
	@Autowired
	private ApprovalDao approvalDao;	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getMainInfomation")
	public Map<String, Object> getMainInfomation() {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("companyCd", secu.getAuthenticatedUser().getCompanyCode());
		param.put("userId", secu.getAuthenticatedUser().getUserId());
		
		// 1. 미상신 카드건수
		long cntUnreq 	= mainDao.getCountOfUnreq(param);
		// 2. 반려카드건수
		long cntRej 	= mainDao.getCountOfRej(param);
		// 3. 결재중 문서건수
		long cntIng 	= mainDao.getCountOfIng(param);
		// 4. 결재완료 문서건수
		long cntComp 	= mainDao.getCountOfComplete(param);
		// 5. 결재할 문서건수
		ApprovalMgmtVo approvalMgmtVo = new ApprovalMgmtVo();
		approvalMgmtVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		approvalMgmtVo.setUserId(secu.getAuthenticatedUser().getUserId());
		approvalMgmtVo.setDeptCd(secu.getAuthenticatedUser().getDeptCode());
		List<ApprovalMgmtVo> aprList = approvalDao.selectApprovalList(approvalMgmtVo);
		long cntTodo	= 0;
		if (aprList!=null) cntTodo = aprList.size();
		
		// 6. 공지사항 Top5
		List<NoticeVo> noticeList = noticeDao.getNoticeListTop5();
		
		// 리턴
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("cntUnreq", 		cntUnreq);
		rtnMap.put("cntRej", 		cntRej);
		rtnMap.put("cntIng", 		cntIng);
		rtnMap.put("cntComp", 		cntComp);
		rtnMap.put("cntTodo", 		cntTodo);
		rtnMap.put("noticeList",	noticeList);
		return rtnMap;
	}
	


}
