package com.bizsp.eaccount.report.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bizsp.eaccount.dept.vo.DeptVo;
import com.bizsp.eaccount.report.dao.ReportDao;
import com.bizsp.eaccount.report.vo.CardProcessStatusVo;
import com.bizsp.eaccount.report.vo.CardProcessVo;
import com.bizsp.eaccount.report.vo.DeptListVo;
import com.bizsp.eaccount.report.vo.IndividualCardVo;
import com.bizsp.eaccount.user.vo.UserVo;
import com.bizsp.framework.security.vo.LoginUserVO;
import com.bizsp.framework.util.security.SecurityUtils;
import com.bizsp.framework.util.web.StringConstants;

@Path("/rest/reportApi/")
@Component
public class ReportService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ReportDao reportDao;
	
	private static SecurityUtils secu;

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getDefaultWorkduration")
	public Map<String, Object> getDefaultWorkduration() {
		return reportDao.getDefaultWorkduration();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("CardProcessStatusList/{startAuthDate}/{endAuthDate}/{cardUserCode}/{financeNo}/{approvalStatusCd}")
	public List<CardProcessStatusVo> getCardProcessStatusList(
			  @PathParam("startAuthDate") String startAuthDate
			, @PathParam("endAuthDate") String endAuthDate
			, @PathParam("cardUserCode") String cardUserCode
			, @PathParam("financeNo") String financeNo	 
			, @PathParam("approvalStatusCd") String approvalStatusCd	) {
		
		logger.debug("::::::::::::::::::: getCardProcessStatusList::::::::::::::::");
		
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("companyCd"	, secu.getAuthenticatedUser().getCompanyCode());
		paramMap.put("userId"		, secu.getAuthenticatedUser().getUserId());
		paramMap.put("startAuthDate", startAuthDate);
		paramMap.put("endAuthDate"	, endAuthDate);
		paramMap.put("cardUserCode"	, cardUserCode);
		paramMap.put("financeNo"	, financeNo);
		paramMap.put("approvalStatusCd"	, approvalStatusCd);
		
		List<CardProcessStatusVo> list = null;
		
		list = reportDao.getCardProcessStatusList(paramMap);
		
		return list;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("CardProcessList/{startAuthDate}/{endAuthDate}/{cardUserCode}/{financeNo}/{approvalStatusCd}")
	public List<Map<String, Object>> getCardProcessList(
			  @PathParam("startAuthDate") String startAuthDate
			, @PathParam("endAuthDate") String endAuthDate
			, @PathParam("cardUserCode") String cardUserCode
			, @PathParam("financeNo") String financeNo	 
			, @PathParam("approvalStatusCd") String approvalStatusCd	) {
		
		logger.debug("::::::::::::::::::: getCardProcessStatusList::::::::::::::::");
		
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("companyCd"	, secu.getAuthenticatedUser().getCompanyCode());
		paramMap.put("userId"		, secu.getAuthenticatedUser().getUserId());
		paramMap.put("startAuthDate", startAuthDate);
		paramMap.put("endAuthDate"	, endAuthDate);
		paramMap.put("cardUserCode"	, cardUserCode);
		paramMap.put("financeNo"	, financeNo);
		paramMap.put("approvalStatusCd"	, approvalStatusCd);
		
		List<Map<String, Object>> list = null;
		if ("AAAA".equals(approvalStatusCd)) {
			list = reportDao.getUnapprovedList(paramMap);
		}else if ("ZZZZ".equals(approvalStatusCd) ) {
			list = reportDao.getExceptList(paramMap);
		}else if ("NREQ".equals(approvalStatusCd)
			||   "SR".equals(approvalStatusCd)  
			||   "SING".equals(approvalStatusCd)  
			||   "SAPR".equals(approvalStatusCd)  
			||   "NREJ".equals(approvalStatusCd)  
			||   "NING".equals(approvalStatusCd)  
			||   "NAPR".equals(approvalStatusCd) 
			||   "EAPR".equals(approvalStatusCd) ) {
			list = reportDao.getApprovedList(paramMap);
		}
		
		return list;
	}
	
	
	
	/*
	 * 부서별
	 * 
	 * */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("CardProcessStatusListDept/{startAuthDate}/{endAuthDate}/{cardUserCode}/{financeNo}/{approvalStatusCd}")
	public List<CardProcessStatusVo> getCardProcessStatusListDept(
			  @PathParam("startAuthDate") String startAuthDate
			, @PathParam("endAuthDate") String endAuthDate
			, @PathParam("cardUserCode") String cardUserCode
			, @PathParam("financeNo") String financeNo	 
			, @PathParam("approvalStatusCd") String approvalStatusCd	) {
		
		logger.debug("::::::::::::::::::: getCardProcessStatusListDept::::::::::::::::");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("companyCd"	, secu.getAuthenticatedUser().getCompanyCode());
		//paramMap.put("userId"		, secu.getAuthenticatedUser().getUserId());
		paramMap.put("startAuthDate", startAuthDate);
		paramMap.put("endAuthDate"	, endAuthDate);
		paramMap.put("cardUserCode"	, cardUserCode);
		paramMap.put("financeNo"	, financeNo);
		paramMap.put("approvalStatusCd"	, approvalStatusCd);
		paramMap.put("userList"		, getDeptUserList());
		
		List<CardProcessStatusVo> list = null;
		
		list = reportDao.getCardProcessStatusListDept(paramMap);
		
		return list;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("CardProcessListDept/{startAuthDate}/{endAuthDate}/{cardUserCode}/{financeNo}/{approvalStatusCd}")
	public List<Map<String, Object>> getCardProcessListDept(
			  @PathParam("startAuthDate") String startAuthDate
			, @PathParam("endAuthDate") String endAuthDate
			, @PathParam("cardUserCode") String cardUserCode
			, @PathParam("financeNo") String financeNo	 
			, @PathParam("approvalStatusCd") String approvalStatusCd	) {
		
		logger.debug("::::::::::::::::::: getCardProcessStatusListDept::::::::::::::::");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("companyCd"	, secu.getAuthenticatedUser().getCompanyCode());
		//paramMap.put("userId"		, secu.getAuthenticatedUser().getUserId());
		paramMap.put("startAuthDate", startAuthDate);
		paramMap.put("endAuthDate"	, endAuthDate);
		paramMap.put("cardUserCode"	, cardUserCode);
		paramMap.put("financeNo"	, financeNo);
		paramMap.put("approvalStatusCd"	, approvalStatusCd);
		paramMap.put("userList"		, getDeptUserList());
		
		List<Map<String, Object>> list = null;
		if ("AAAA".equals(approvalStatusCd)) {
			list = reportDao.getUnapprovedListDept(paramMap);
		}else if ("ZZZZ".equals(approvalStatusCd) ) {
			list = reportDao.getExceptListDept(paramMap);
		}else if ("NREQ".equals(approvalStatusCd)
			||   "SR".equals(approvalStatusCd)  
			||   "SING".equals(approvalStatusCd)  
			||   "SAPR".equals(approvalStatusCd)  
			||   "NREJ".equals(approvalStatusCd)  
			||   "NING".equals(approvalStatusCd)  
			||   "NAPR".equals(approvalStatusCd) 
			||   "EAPR".equals(approvalStatusCd) ) {
			list = reportDao.getApprovedListDept(paramMap);
		}
		
		return list;
	}
	
	/**
	 * 개인카드 조회
	 * @param cardNo
	 * @param cardStCd
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("IndividualCard/{cardNo}/{cardStCd}")
	public Map<String, Object> getIndividualCard(
			  @PathParam("cardNo") String cardNo
			, @PathParam("cardStCd") String cardStCd) {
		
		logger.debug("::::::::::::::::::: IndividualCard::::::::::::::::");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		IndividualCardVo individualCardVo = new IndividualCardVo();
		individualCardVo.setCompanyCd(getLoginUserInfo().getCompanyCode());
		individualCardVo.setUserId(getLoginUserInfo().getUserId());
		individualCardVo.setCardNo(cardNo);
		individualCardVo.setCardStCd(cardStCd);
		
		List<IndividualCardVo> resultList = reportDao.selectIndividualCard(individualCardVo);
		resultMap.put("individualCardList", resultList);
		resultMap.put("totalRow", reportDao.countIndividualCard(individualCardVo));
		return resultMap;
	}
	
	/**
	 * 전체카드 조회
	 * @param startAuthDate
	 * @param endAuthDate
	 * @param approvalStatusCd
	 * @param financeNo
	 * @param deptCd
	 * @param deptNm
	 * @param ownUserId
	 * @param mercName
	 * @param pageNo
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("cardProcessStatusListAll/{startAuthDate}/{endAuthDate}/{approvalStatusCd}/{financeNo}/{deptCd}/{deptNm}/{ownUserNm}/{mercName}/{pageNo}")
	public Map<String, Object> getCardProcessStatusListAll(
			  @PathParam("startAuthDate") String startAuthDate
			, @PathParam("endAuthDate") String endAuthDate
			, @PathParam("approvalStatusCd") String approvalStatusCd
			, @PathParam("financeNo") String financeNo
			, @PathParam("deptCd") String deptCd
			, @PathParam("deptNm") String deptNm
			, @PathParam("ownUserNm") String ownUserNm
			, @PathParam("mercName") String mercName
			, @PathParam("pageNo") int pageNo) {
		
		logger.debug("::::::::::::::::::: getCardProcessStatusListAll::::::::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		CardProcessVo cardProcessVo = new CardProcessVo();
		
		try {
			deptNm = URLDecoder.decode(deptNm, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("############## deptNm : " + deptNm);
		
		cardProcessVo.setCompanyCd(getLoginUserInfo().getCompanyCode());
		//cardProcessVo.setUserId(getLoginUserInfo().getUserId());
		cardProcessVo.setStartAuthDate(startAuthDate);
		cardProcessVo.setEndAuthDate(endAuthDate);		
		cardProcessVo.setApprovalStatusCd(approvalStatusCd);
		cardProcessVo.setFinanceNo(financeNo);
		cardProcessVo.setDeptCd(deptCd);
		cardProcessVo.setDeptNm(deptNm);
		cardProcessVo.setOwnUserNm(ownUserNm);
		cardProcessVo.setMercName(mercName);
		System.out.println(cardProcessVo.toString());
		if (pageNo > 0) {
			cardProcessVo.setPagingYn("Y");
		} else {
			cardProcessVo.setPagingYn("N");
		}
		cardProcessVo.setPageNo(pageNo);
		
		List<CardProcessVo> resultList = null;		
		long allAmount = 0;
		
		if(!financeNo.equals("all")){
			resultList = reportDao.getApprovedListAll(cardProcessVo);
		}else{
			if ("AAAA".equals(approvalStatusCd)) {
				allAmount = reportDao.getUnapprovedListAllSum(cardProcessVo);
				resultList = reportDao.getUnapprovedListAll(cardProcessVo);			
			}else if ("ZZZZ".equals(approvalStatusCd) ) {
				allAmount = reportDao.getExceptListAllSum(cardProcessVo);
				resultList = reportDao.getExceptListAll(cardProcessVo);			
			}else if ("NREQ".equals(approvalStatusCd)
				||   "SR".equals(approvalStatusCd)  
				||   "SING".equals(approvalStatusCd)  
				||   "SAPR".equals(approvalStatusCd)  
				||   "NREJ".equals(approvalStatusCd)  
				||   "NING".equals(approvalStatusCd)  
				||   "NAPR".equals(approvalStatusCd) 
				||   "EAPR".equals(approvalStatusCd) ) {
				allAmount = reportDao.getApprovedListAllSum(cardProcessVo);
				resultList = reportDao.getApprovedListAll(cardProcessVo);
			}else if("all".equals(approvalStatusCd)){
				allAmount = reportDao.getCardProcessStatusListAllSum(cardProcessVo);
				resultList = reportDao.getCardProcessStatusListAll(cardProcessVo);			
			}
		}

		//makeCardProcessHtml(resultMap, resultList);
		long totalAmount = 0;
		for(CardProcessVo tmp : resultList){
			totalAmount += Long.parseLong(tmp.getRequestAmount());
			tmp.setApprovalStatusCd(StringConstants.APPROVAL_CD_MAP.get(tmp.getApprovalStatusCd()).toString());
			//System.out.println(tmp.getOwnUserId());
		}
		
		resultMap.put("cardProcessList", resultList);
		resultMap.put("totalAmount", totalAmount);
		resultMap.put("totalRow", cardProcessVo.getTotalRow());
		resultMap.put("allAmount", allAmount);
		
		return resultMap;
	}
	

	public List<UserVo> getDeptUserList() {
		DeptVo deptVo = new DeptVo();
		deptVo.setCopanyCode(getLoginUserInfo().getCompanyCode());
		deptVo.setDeptCode(getLoginUserInfo().getDeptCode());
		List<DeptVo> deptList = reportDao.getLowDeptList(deptVo);
		System.out.println("######## "+deptList.size());
		for(DeptVo tmp : deptList){
			System.out.println("### "+tmp.getDeptCode());
		}
		
		DeptListVo deptListVo = new DeptListVo();
		deptListVo.setCompanyCd(getLoginUserInfo().getCompanyCode());
		deptListVo.setDeptList(deptList);
		List<UserVo> userList = reportDao.getLowUserList(deptListVo);
		System.out.println("######## "+userList.size());
		for(UserVo tmp : userList){
			System.out.println("### "+tmp.getUserId() + ", " + tmp.getDeptCode());
		}
		return userList;
	}
	
	private String checkNull(String str){
		if(str==null || str.trim().equals("")) return "";
		return str;		
	}
	
	private Map<String, Object> makeCardProcessHtml(Map<String, Object> resultMap, List<CardProcessVo> list){
		long totalAmount = 0;
		StringBuffer sb = new StringBuffer();
		int size = list.size();
		
		DecimalFormat df = new DecimalFormat("###,###");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		
		for(int i=0; i<size; i++){
			CardProcessVo tmp = list.get(i);
			
			totalAmount += Long.parseLong(tmp.getRequestAmount());
			
			sb.append("<tr>");
			sb.append("<td>" + i+1 + "</td>");
			sb.append("<td>" + checkNull((String) StringConstants.APPROVAL_CD_MAP.get(tmp.getApprovalId())) + "</td>");
			sb.append("<td></td>");
			sb.append("<td>" + checkNull(tmp.getCardName()) + "</td>");
			sb.append("<td>" + checkNull(tmp.getCardNum()) + "</td>");
			sb.append("<td>" + (checkNull(tmp.getAuthDate())) + "</td>");
			sb.append("<td>" + (checkNull(tmp.getAuthTime())) + "</td>");
			sb.append("<td>" + checkNull(tmp.getAuthNum()) + "</td>");
			sb.append("<td style=\"text-align:left;\">" + checkNull(tmp.getMercName()) + "</td>");
			sb.append("<td>" + checkNull(tmp.getMercSaupNo()) + "</td>");
			sb.append("<td style=\"text-align:right;\">￦" + df.format(Long.parseLong(checkNull(tmp.getRequestAmount()))) + "</td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td style=\"text-align:left;\">" + checkNull(tmp.getMercAddr()) + "</td>");
			sb.append("<td>" + checkNull(tmp.getMercSaupNo()) + "</td>");
			sb.append("<td>" + checkNull(tmp.getAccountNm()) + "</td>");
			sb.append("<td>" + checkNull(tmp.getAccount2Nm()) + "</td>");
			sb.append("<td>" + checkNull(tmp.getDetails()) + "</td>");
			sb.append("<td>" + checkNull(tmp.getFinanceNo()) + "</td>");
			sb.append("<td>" + checkNull(tmp.getJunpyoStCd()) + "</td>");
			sb.append("<td></td>");
			sb.append("</tr>");			
		}
		
		resultMap.put("cardProcessListHtml", sb.toString());
		resultMap.put("totalAmount", totalAmount);	
		return resultMap;
	}
	
	private LoginUserVO getLoginUserInfo() {
		return (LoginUserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
