package com.bizsp.eaccount.comcard.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bizsp.eaccount.comcard.dao.CommCardMgmtDao;
import com.bizsp.eaccount.comcard.vo.CommCardMgmtVo;
import com.bizsp.framework.util.lang.DateUtil;
import com.bizsp.framework.util.security.SecurityUtils;

@Component
@Path("/rest/commCardApi/")
public class CommCardMgmtService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private SecurityUtils secu;
	
	@Autowired
	private CommCardMgmtDao commCardMgmtDao;	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCardAQList/{authDate}/{searchType}/{actUser}/{pageNo}")
	public Map<String, Object> getCardAQList(	@PathParam("authDate") String authDate,
												@PathParam("searchType") String searchType,
												@PathParam("actUser") String actUser,
												@PathParam("pageNo") int pageNo) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		CommCardMgmtVo commCardMgmtVo = new CommCardMgmtVo();
		commCardMgmtVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		commCardMgmtVo.setCardMgrId(secu.getAuthenticatedUser().getUserId());
		commCardMgmtVo.setAuthDate(authDate);
		commCardMgmtVo.setSearchType(searchType);
		commCardMgmtVo.setActUser(actUser);
		commCardMgmtVo.setPageNo(pageNo);
		commCardMgmtVo.setPagingYn("Y");
		
		List<CommCardMgmtVo> list = commCardMgmtDao.getCardAQList(commCardMgmtVo);
		//DecimalFormat df = new DecimalFormat("###,###");	
		
		long totalAmount = 0;
		for(CommCardMgmtVo vo : list){
			//String date = vo.getAuthDate();
			String curr = vo.getRequestAmount();
			totalAmount += Long.parseLong(curr);
			//vo.setRequestAmount(df.format(Long.parseLong(curr)));
			//vo.setAuthDate(DateUtil.dateFormat(date, "yyyy-MM-dd"));
		}
		
		map.put("commCardList", list);
		map.put("totalRow",commCardMgmtVo.getTotalRow());
		map.put("totalAmount", totalAmount);
		return map;
	}
	
    
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("getCardApprovalCount/{actUserId}/{authNum}/{cardNo}/{authDate}/{georaeStat}/{requestAmount}/{georaeColl}")
	public long getCardApprovalCount(	@PathParam("actUserId") String actUserId,
										@PathParam("authNum") String authNum,
										@PathParam("cardNo") String cardNo,
										@PathParam("authDate") String authDate,
										@PathParam("georaeStat") String georaeStat,
										@PathParam("requestAmount") String requestAmount,
										@PathParam("georaeColl") String georaeColl	) {
		
		CommCardMgmtVo commCardMgmtVo = new CommCardMgmtVo();
		commCardMgmtVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		commCardMgmtVo.setActUserId(actUserId);
		commCardMgmtVo.setAuthNum(authNum);
		commCardMgmtVo.setCardNo(cardNo);
		commCardMgmtVo.setAuthDate(authDate);
		commCardMgmtVo.setGeoraeStat(georaeStat);
		commCardMgmtVo.setRequestAmount(requestAmount);
		commCardMgmtVo.setGeoraeColl(georaeColl);
		return commCardMgmtDao.getCardApprovalCount((CommCardMgmtVo) commCardMgmtVo);
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("setActUser")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> setActUser(CommCardMgmtVo commCardMgmtVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		commCardMgmtVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		commCardMgmtVo.setUpdUserId(secu.getAuthenticatedUser().getUserId());		

		long cnt = commCardMgmtDao.updateCardAQMgmt(commCardMgmtVo);
		if(cnt > 0){
			map.put("result"	, "S");
			map.put("message"	, "실사용자로 등록되었습니다.");
		}else{
			map.put("result"	, "F");
			map.put("message"	, "처리중 장애가 발생했습니다.");
		}
		
		return map;
	}

}
