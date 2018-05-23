package com.bizsp.eaccount.approvalRq.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bizsp.eaccount.approval.dao.ApprovalDao;
import com.bizsp.eaccount.approval.vo.ApprovalItemVo;
import com.bizsp.eaccount.approval.vo.ApprovalLineVo;
import com.bizsp.eaccount.approval.vo.ApprovalMgmtVo;
import com.bizsp.eaccount.approvalRq.dao.ApprovalRqDao;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqAttachVo;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqCardMgmt;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqInfoVo;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqItemVo;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqLineVo;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqVo;
import com.bizsp.eaccount.approvalRq.vo.SymposiumMgmtVo;
import com.bizsp.eaccount.approvalRq.vo.SymposiumRqVo;
import com.bizsp.eaccount.batch.dao.BatchDao;
import com.bizsp.framework.security.vo.LoginUserVO;
import com.bizsp.framework.util.exception.ServiceException;
import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.mail.MailUtil;
import com.bizsp.framework.util.security.SecurityUtils;
import com.bizsp.framework.util.system.PropUtil;
import com.bizsp.framework.util.web.HtmlUtil;
import com.bizsp.framework.util.web.StringConstants;

@Path("/rest/approvalRqApi")
@Component
public class ApprovalRqService {

	@Autowired
	ApprovalRqDao approvalRqDao;

	@Autowired
	ApprovalDao approvalDao;
	
	@Autowired
	BatchDao batchDao;

	private static SecurityUtils securityUtils;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 심포지움요청대상 리스트 조회
	 * 
	 * @param authDate
	 * @param pageNo
	 * @return
	 */
	@SuppressWarnings("static-access")
	@GET
	@Path("symposiumRqList/{authDate}/{pageNo}/{deptCdoe}/{productCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getSymposiumRqList(@PathParam("authDate") String authDate, @PathParam("pageNo") int pageNo, @PathParam("deptCdoe") String deptCode, @PathParam("productCode") String productCode) {

		SymposiumRqVo symposiumRqVo = new SymposiumRqVo();

		symposiumRqVo.setCompanyCode(securityUtils.getAuthenticatedUser().getCompanyCode());
		symposiumRqVo.setAuthDate(authDate);
		symposiumRqVo.setBudgetDeptCd(deptCode);
		symposiumRqVo.setProductCd(productCode);

		@SuppressWarnings("unused")
		Collection<GrantedAuthority> authInfo = securityUtils.getAuthenticatedUser().getAuthorities();

		// 권한체크 안함
		/*
		 * if(securityUtils.hasRole("R99")){
		 * symposiumRqVo.setBudgetDeptCd("all"); }else{
		 * symposiumRqVo.setBudgetDeptCd
		 * (securityUtils.getAuthenticatedUser().getDeptCode()); }
		 */

		symposiumRqVo.setPagingYn("Y");
		symposiumRqVo.setPageNo(pageNo);

		List<Map<String, Object>> symposiumRqList = approvalRqDao.getSymposiumRqList(symposiumRqVo);

		long amtAmountTotal = 0;
		long vatAmountTotal = 0;
		long requestAmountTotal = 0;

		// 리스트 합산 금액
		for (Map<String, Object> map : symposiumRqList) {
			amtAmountTotal += Long.parseLong(map.get("AMT_AMOUNT").toString());
			vatAmountTotal += Long.parseLong(map.get("VAT_AMOUNT").toString());
			requestAmountTotal += Long.parseLong(map.get("REQUEST_AMOUNT").toString());
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("symposiumRqList", symposiumRqList);
		returnMap.put("amtAmountTotal", amtAmountTotal);
		returnMap.put("vatAmountTotal", vatAmountTotal);
		returnMap.put("requestAmountTotal", requestAmountTotal);
		returnMap.put("totalRow", symposiumRqVo.getTotalRow());

		return returnMap;
	}

	/**
	 * 미상신 내역 리스트
	 * 
	 * @param approvalRqVo
	 * @return
	 */
	@SuppressWarnings("static-access")
	@GET
	@Path("approvalRqList/{date}/{cardUsingTypeCode}/{cardUserCode}/{pageNo}/{cardUserName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getApprovalUpList(@PathParam("date") String date, @PathParam("cardUsingTypeCode") String cardUsingTypeCode, @PathParam("cardUserCode") String cardUserCode, @PathParam("pageNo") int pageNo, @PathParam("cardUserName") String cardUserName) {

		ApprovalRqVo approvalRqVo = new ApprovalRqVo();

		Map<String, Object> approvalUpMap = new HashMap<String, Object>();

		approvalRqVo.setUserId(securityUtils.getAuthenticatedUser().getUserId());
		approvalRqVo.setCompanyCode(securityUtils.getAuthenticatedUser().getCompanyCode());
		approvalRqVo.setDeptCode(securityUtils.getAuthenticatedUser().getDeptCode());
		approvalRqVo.setsDate(date);
		approvalRqVo.setCardUsingTypeCode(cardUsingTypeCode);
		approvalRqVo.setPagingYn("Y");
		approvalRqVo.setPageNo(pageNo);
		approvalRqVo.setcardUserName(cardUserName);

		List<Map<String, Object>> approvalRqList = new ArrayList<Map<String, Object>>();

		if (cardUserCode.equals("2")) {
			approvalRqList = approvalRqDao.getApprovalRrqList(approvalRqVo);
		} else {
			approvalRqList = approvalRqDao.getApprovalUpList(approvalRqVo);
		}

		long amountTotal = 0;

		// 리스트 합산 금액
		for (Map<String, Object> map : approvalRqList) {
			amountTotal += Long.parseLong(map.get("REQUEST_AMOUNT").toString());
		}

		approvalUpMap.put("approvalRqList", approvalRqList);
		approvalUpMap.put("amountTotal", amountTotal);
		approvalUpMap.put("totalRow", approvalRqVo.getTotalRow());

		return approvalUpMap;
	}
		
	@GET
	@Path("attachFile/{approvalId}/{seq}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response attachFile(@PathParam("approvalId") String approvalId, @PathParam("seq") String seq) throws Exception {

		logger.debug(":::::::::: attachFile 첨부파일 조회 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (approvalId != null && !"".equals(approvalId) && seq != null && !"".equals(seq)) {
			ApprovalMgmtVo paramVo = new ApprovalMgmtVo();
			paramVo.setApprovalId(approvalId);
			if(!seq.equals("all")){
				paramVo.setSeq(seq);			
			}
			paramVo.setCompanyCd(getLoginUserInfo().getCompanyCode());

			List<ApprovalMgmtVo> attachFileList = approvalDao.selectAttachFileList(paramVo);

			if (attachFileList != null && attachFileList.size() > 0) {
				resultMap.put("attachFilelist", attachFileList);
				resultMap.put("code", "S");
			} else {
				throw new ServiceException(makeMap("첨부파일이 존재하지 않습니다."));
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
	}

	/**
	 * 품의서 결재선 라인 리스트
	 * 
	 * @param approvalRqVo
	 * @return
	 */
	@GET
	@Path("approvalRqLineList/{amount}/{accountCode}/{deptCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> getApprovalLineList(@PathParam("amount") String amount, @PathParam("accountCode") String accountCode, @PathParam("deptCode") String deptCode) {
		ApprovalRqVo approvalRqVo = new ApprovalRqVo();

		approvalRqVo.setCompanyCode(securityUtils.getAuthenticatedUser().getCompanyCode());
		approvalRqVo.setAmount(amount);
		approvalRqVo.setAccountCode(accountCode);
		approvalRqVo.setDeptCode(deptCode);
		approvalRqVo.setPagingYn("N");

		List<Map<String, Object>> approvalListList = approvalRqDao.getApprovalLineList(approvalRqVo);

		Map<String, String> apUserInfo = new HashMap<String, String>();

		for (Map<String, Object> map : approvalListList) {
			String info = (String) map.get("AP_USER_INFO");

			String[] infos = info.split("\\$");

			apUserInfo.put("apUserId", infos[0]);
			apUserInfo.put("apUserName", infos[1]);
			apUserInfo.put("deptCode", infos[2]);
			apUserInfo.put("deptName", infos[3]);
			apUserInfo.put("titleCode", infos[4]);
			apUserInfo.put("titleName", infos[5]);
			apUserInfo.put("dutyCode", infos[6]);
			apUserInfo.put("dutyName", infos[7]);
			apUserInfo.put("titleAliasCode", infos[8]);
			apUserInfo.put("titleAliasName", infos[9]);

			map.putAll(apUserInfo);
		}

		return approvalListList;
	}

	/**
	 * 카드 사용 내역 개인 / 법인 상태 설정
	 * 
	 * @param approvalRqCardMgmts
	 * @return
	 */
	@POST
	@Path("updateCardUsingTypeStatus")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response updateCardUsingTypeStatus(ApprovalRqCardMgmt approvalRqCardMgmts) throws Exception {
		approvalRqDao.updateCardUsingTypeStatus(approvalRqCardMgmts);
		return Response.status(200).entity(approvalRqCardMgmts).build();
	}

	/**
	 * 품의서 저장
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	@POST
	@Path("insertApproval")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response insertApproval(ApprovalRqInfoVo approvalInfoVo) {

		// 품의서 문서 번호 생성 주입
		approvalInfoVo.setApprovalId(approvalRqDao.getApprovalId());
		approvalInfoVo.setCompanyCode(securityUtils.getAuthenticatedUser().getCompanyCode());
		approvalInfoVo.setRegUserId(securityUtils.getAuthenticatedUser().getUserId());

		approvalRqDao.insertApproval(approvalInfoVo);
		approvalRqDao.insertApprovalItem(approvalInfoVo);

//		2015-03-24
//		디버그용 System.out.println 주석 처리
//		System.out.println(securityUtils.getAuthenticatedUser().getDutyCode());

		return Response.status(200).entity(approvalInfoVo).build();
	}

	/**
	 * 품의서 작성
	 * 
	 * @param approvalId
	 * @return
	 */
	@GET
	@Path("approvalRqItem/{approvalId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getApprovalItem(@PathParam("approvalId") String approvalId) {

		String accountCode = null;
		String accountName = null;
		String budgetDeptCode = null;
		String budgetDeptName = null;
		String ftrCode = null;

		List<ApprovalRqItemVo> approvalRqItemList = approvalRqDao.getApprovalItem(approvalId);
		Map<String, Object> approvalRqItemMap = new HashMap<String, Object>();

		long requestAmountTotal = 0;

		for (ApprovalRqItemVo approvalRqItemVo : approvalRqItemList) {
			requestAmountTotal += Long.parseLong(approvalRqItemVo.getRequestAmount());
		}

		accountCode = approvalRqItemList.get(0).getAccountCode();
		accountName = approvalRqItemList.get(0).getAccountName();
		budgetDeptCode = approvalRqItemList.get(0).getBudgetDeptCode();
		budgetDeptName = approvalRqItemList.get(0).getBudgetDeptName();
		ftrCode = approvalRqItemList.get(0).getFtrCode();

		approvalRqItemMap.put("approvalRqItemList", approvalRqItemList);
		approvalRqItemMap.put("amountTotal", requestAmountTotal);
		approvalRqItemMap.put("accountCode", accountCode);
		approvalRqItemMap.put("accountName", accountName);
		approvalRqItemMap.put("accountCode", accountCode);
		approvalRqItemMap.put("budgetDeptCode", budgetDeptCode);
		approvalRqItemMap.put("budgetDeptName", budgetDeptName);
		approvalRqItemMap.put("ftrCode", ftrCode);
		approvalRqItemMap.put("loginDeptCode", securityUtils.getAuthenticatedUser().getDeptCode());
		approvalRqItemMap.put("loginDeptName", securityUtils.getAuthenticatedUser().getDeptName());
		approvalRqItemMap.put("salesYn", securityUtils.getAuthenticatedUser().getSalesYn());

		return approvalRqItemMap;
	}

	/**
	 * 품의서 세부사항 저장
	 * 
	 * @param approvalRqItemVo
	 * @return
	 */
	@SuppressWarnings("static-access")
	@POST
	@Path("updateApprovalRqItem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response updateApprovalItem(List<ApprovalRqItemVo> approvalRqItemVos) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String oldApprovalId = "";
		// 1. 품의 내역 저장
		ApprovalRqInfoVo approvalRqInfoVo = new ApprovalRqInfoVo();

		for (ApprovalRqItemVo approvalRqItemVo : approvalRqItemVos) {
			approvalRqItemVo.setUpdtUserId(securityUtils.getAuthenticatedUser().getUserId());
			
			oldApprovalId = approvalRqItemVo.getOldApprovalId();
			
//			디버그용 System.out.println 주석 처리
			//System.out.println(approvalRqItemVo.getOldApprovalId());
			//System.out.println("######## getMeetReport : "+approvalRqItemVo.getMeetReport());

			approvalRqDao.updateApprovalItem(approvalRqItemVo);

			if (approvalRqItemVo.getsApprovalId() != null) {
				approvalRqDao.updateApprovalStaus(approvalRqItemVo.getsApprovalId());
			}
		}

		approvalRqInfoVo.setCompanyCode(securityUtils.getAuthenticatedUser().getCompanyCode());
		approvalRqInfoVo.setAccountCode(approvalRqItemVos.get(0).getAccountCode());
		approvalRqInfoVo.setAccountName(approvalRqItemVos.get(0).getAccountName());
		approvalRqInfoVo.setBudgetDeptCode(approvalRqItemVos.get(0).getBudgetDeptCode());
		approvalRqInfoVo.setBudgetDeptName(approvalRqItemVos.get(0).getBudgetDeptName());
		approvalRqInfoVo.setsApprovalId(approvalRqItemVos.get(0).getsApprovalId());
		approvalRqInfoVo.setUpdtUserId(securityUtils.getAuthenticatedUser().getUserId());
		approvalRqInfoVo.setApprovalId(approvalRqItemVos.get(0).getApprovalId());
		approvalRqInfoVo.setRqOpinion(approvalRqItemVos.get(0).getOpinion());

		if (approvalRqInfoVo.getAccountCode().equals("2") && approvalRqItemVos.get(0).getsApprovalId() == null) {
			approvalRqInfoVo.setApprovalStatusCode("SR"); // 심포지움 결재 요청
		} else {
			approvalRqInfoVo.setApprovalStatusCode("NREQ"); // 상신중으로 업데이트
		}
		approvalRqDao.updateApprovalRq(approvalRqInfoVo);

		// 2. 품의서 라인 저장 - 심포지움은 하지 않는다.
		if (!(approvalRqInfoVo.getAccountCode().equals("2") && approvalRqItemVos.get(0).getsApprovalId() == null)) {

			List<ApprovalRqLineVo> approvalRqLines = approvalRqItemVos.get(0).getApprovalRqLines();

			for (ApprovalRqLineVo approvalRqLineVo : approvalRqLines) {
				approvalRqLineVo.setRegUserId(securityUtils.getAuthenticatedUser().getCompanyCode());
				approvalRqLineVo.setCompanyCode(securityUtils.getAuthenticatedUser().getCompanyCode());
				approvalRqLineVo.setApprovalId(approvalRqItemVos.get(0).getApprovalId());
			}
			approvalRqDao.insertApprovlRqLine(approvalRqLines);
		}

		// 3. 품의서 첨부파일 저장
		List<ApprovalRqAttachVo> approvalRqAttachs = approvalRqItemVos.get(0).getApprovalRqAttachs();
		if (approvalRqAttachs != null && approvalRqAttachs.size() > 0) {
			insertApprovalRqAttach(approvalRqAttachs, approvalRqItemVos.get(0).getApprovalId());
		}
		resultMap.put("code", "S");

		// 4. 전결
		if ("5".equals(approvalRqInfoVo.getAccountCode())) {
			// 계정구분이 5번인지 체크

			if (approvalRqDao.validRqUser(approvalRqInfoVo) == 1) {
				// 본인이 상신자인지 체크

				if ("1".equals(approvalRqDao.validLastItemSeq(approvalRqInfoVo))) {
					// 최종 결재자의 차수가 1차인지 체크

					if (approvalRqDao.validLastApprovalUser(approvalRqInfoVo) == 1) {
						// 본인이 최종 결재 예정자인지 체크

						requestApproval(approvalRqInfoVo.getApprovalId());
						resultMap.put("code", "RA");
						resultMap.put("msg", "품의서 상신 및 결재 되었습니다.");
					}
				}
			}
		}
		
		
		// 재품의일 경우 oldApprovalId 로 기존 데이터 삭제
		if(oldApprovalId!=null && !oldApprovalId.isEmpty() && !oldApprovalId.equals("N")){
			deleteApproval(oldApprovalId);
		}
		
		return Response.status(200).entity(resultMap).build();
	}

	/**
	 * 심포지움 품의서 저장
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("static-access")
	@POST
	@Path("insertSymposiumApproval")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response addSymposiumApproval(List<String> sApprovalIds) throws ServiceException {
		try {
			// ------------------------------
			// 0. 권한 체크 : 심포지움관리자 권한이 있는지 체크
			// ------------------------------
			// TODO 권한 체크 : 심포지움관리자 권한이 있는지 체크

			// ------------------------------
			// 1.품의서 문서 번호 채번
			// ------------------------------
			ApprovalRqInfoVo approvalInfoVo = new ApprovalRqInfoVo();

			String newApprovalId = approvalRqDao.getApprovalId();
			String regUserId = securityUtils.getAuthenticatedUser().getUserId();
			String companyCode = securityUtils.getAuthenticatedUser().getCompanyCode();

			approvalInfoVo.setApprovalId(newApprovalId);

			// ------------------------------
			// 2.품의서 생성
			// ------------------------------
			approvalInfoVo.setCompanyCode(companyCode);
			approvalInfoVo.setRegUserId(regUserId);
			approvalInfoVo.setRqUserId(regUserId);
			approvalInfoVo.setsApprovalIds(sApprovalIds);

			approvalRqDao.insertSymposiumApproval(approvalInfoVo);

			// ------------------------------
			// 3.품의서 내역 생성
			// ------------------------------
			approvalRqDao.insertSymposiumApprovalItem(approvalInfoVo);

			ApprovalRqAttachVo approvalRqAttachVo = new ApprovalRqAttachVo();

			// 심포지움 다건에 대한 첨부파일 복사
			for (String sApprovalId : sApprovalIds) {
				approvalRqAttachVo.setApprovalId(newApprovalId);
				approvalRqAttachVo.setRegUserId(regUserId);
				approvalRqAttachVo.setCompanyCode(companyCode);
				approvalRqAttachVo.setsApprovalId(sApprovalId);
				approvalRqDao.insertApprovalSymposiumRqAttach(approvalRqAttachVo);
			}

			// ------------------------------
			// 3. 리턴
			// ------------------------------
			Response r = Response.status(200).entity(approvalInfoVo).build();
			return r;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@SuppressWarnings("static-access")
	@POST
	@Path("insertApprovalRqAttach/{approvalId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response insertApprovalRqAttach(List<ApprovalRqAttachVo> approvalRqAttachs, @PathParam("approvalId") String approvalId) {
		ApprovalMgmtVo approvalMgmtVo = new ApprovalMgmtVo();

		for (ApprovalRqAttachVo approvalRqAttachVo : approvalRqAttachs) {
			approvalRqAttachVo.setCompanyCode(securityUtils.getAuthenticatedUser().getCompanyCode());
			approvalRqAttachVo.setRegUserId(securityUtils.getAuthenticatedUser().getUserId());
		}

		approvalRqDao.insertApprovalRqAttach(approvalRqAttachs);

		approvalMgmtVo.setCompanyCd(securityUtils.getAuthenticatedUser().getCompanyCode());
		approvalMgmtVo.setApprovalId(approvalId);

		List<ApprovalMgmtVo> attachFileList = new ArrayList<ApprovalMgmtVo>();
		attachFileList = approvalRqDao.selectAttachFileList(approvalMgmtVo);

		ApprovalRqAttachVo approvalRqAttach = new ApprovalRqAttachVo();
		String pFileName = "";
		int attachSeq = 1;

		// 파일 RENAME
		for (ApprovalMgmtVo mgmt : attachFileList) {
			String filePath = PropUtil.getValue("file.uploadPolicy.path") + mgmt.getFilePath() + "\\";
			String fileName = mgmt.getPfileNm();

			pFileName = mgmt.getApprovalId() + "_" + mgmt.getApprovalAttachTpCd() + "_" + attachSeq;
			approvalRqAttach.setpFileName(pFileName);
			approvalRqAttach.setAttachSeq(mgmt.getSeq());
			approvalRqAttach.setAttachId(mgmt.getAttachId());
			approvalRqDao.updateApprovalRqAttach(approvalRqAttach);

			File file = new File(filePath + fileName);

			file.renameTo(new File(filePath + pFileName + "." + mgmt.getExt()));
			attachSeq++;
		}

		return Response.status(200).entity(approvalRqAttachs).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("cancelSymposiumList/{authDate}/{pageNo}")
	public Map<String, Object> getCancelSymposiumList(@PathParam("authDate") String authDate, @PathParam("pageNo") int pageNo) {

		logger.debug(":::::::::: getCancelSymposiumList ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		SymposiumMgmtVo paramVo = new SymposiumMgmtVo();
		setSearchListParam(paramVo, getLoginUserInfo(), authDate, pageNo);

		List<SymposiumMgmtVo> list = approvalRqDao.selectCancelSymposiumList(paramVo);

		resultMap.put("html", HtmlUtil.makeHtmlForList(list, HtmlUtil.CANCEL_SYMPOSIUM_NAME, HtmlUtil.CANCEL_SYMPOSIUM_TYPE));
		resultMap.put("totalRow", paramVo.getTotalRow());

		return resultMap;
	}

	@DELETE
	@Path("cancelSymposium/{approvalId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response cancelSymposium(@PathParam("approvalId") String approvalId) throws Exception {

		logger.debug(":::::::::: cancelSymposium 심포지움 요청취소 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (approvalId != null && !"".equals(approvalId)) {
			deleteSymposium(approvalId);
			// 심포지움 데이터 삭제

			resultMap.put("code", "S");
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
	}

	@DELETE
	@Path("restoreSymposium/{approvalId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response restoreSymposium(@PathParam("approvalId") String approvalId) throws Exception {

		logger.debug(":::::::::: restoreSymposium 심포지움 반려 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (approvalId != null && !"".equals(approvalId)) {
			SymposiumMgmtVo paramVo = new SymposiumMgmtVo();
			paramVo.setCompanyCd(getLoginUserInfo().getCompanyCode());
			paramVo.setApprovalId(approvalId);

			SymposiumMgmtVo restoreVo = approvalRqDao.selectSymposiumRestoreData(paramVo);
			// 심포지움 반려 메일 내용 조회

			deleteSymposium(approvalId);
			// 심포지움 데이터 삭제

			String content = makeSymposiumResoreMailContent(restoreVo);

			try {
				HashMap<String, String> map = new HashMap<String, String>();
				if ("DEV".equalsIgnoreCase(PropUtil.getValue("running.mode"))) {
					map.put("recipients", PropUtil.getValue("test_email_recipient_address"));
				} else if ("REAL".equalsIgnoreCase(PropUtil.getValue("running.mode"))) {
					map.put("recipients", restoreVo.getEmailAddr());

					// FIXME 운영도 4/1 부터 정상 메일 발송
					// 임시로 이청일 대리에게 메일 발송 처리
					// 하기 코드 제거 + 상기 코드 주석 제거
					//map.put("recipients", "cilee99@brnetcomm.co.kr");
				}
				map.put("subject", "법인카드 심포지움 결재요청이 반려되었습니다.");
				map.put("content", content);
				
				String smsContent = makeResoreSmsContent(Long.parseLong(restoreVo.getRequestAmount()));				
//				batchDao.insertSms(smsContent, "01032042877", "01032042877", "V99999");
				batchDao.insertSms(smsContent, getLoginUserInfo().getMobileNo(), restoreVo.getMobileNo(), getLoginUserInfo().getUserId());
				MailUtil.sendMail(map);
			} catch (Exception e) {
				e.printStackTrace();
			}

			resultMap.put("code", "S");
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
	}
	
	private void deleteApproval(String approvalId) {

		if (approvalId != null && !"".equals(approvalId)) {
			SymposiumMgmtVo paramVo = new SymposiumMgmtVo();

			paramVo.setCompanyCd(getLoginUserInfo().getCompanyCode());
			paramVo.setApprovalId(approvalId);			
			

			int validCnt1 = approvalRqDao.deleteSymposiumApprovalItem(paramVo);
			// 품의내역 테이블 삭제

			if (validCnt1 != 0) {

				List<String> attachIdList = approvalRqDao.selectSymposiumApprovalAttach(paramVo);
				// 삭제할 첨부파일 대상 조회 (ATTACH_ID)

				if (attachIdList != null && attachIdList.size() > 0) {

					for (String attachId : attachIdList) {
						paramVo.setAttachId(attachId);

						SymposiumMgmtVo fileInfo = approvalRqDao.selectSymposiumAttachFile(paramVo);
						// 첨부파일 정보 조회

						int validCnt2 = approvalRqDao.deleteSymposiumAttachFile(paramVo);
						// 첨부파일내역 테이블 삭제

						if (validCnt2 == 0) {
							throw new ServiceException(makeMap("첨부파일내역 데이터를 삭제할 수 없습니다."));
						}

						int validCnt3 = approvalRqDao.deleteSymposiumAttach(paramVo);
						// 첨부파일마스터 테이블 삭제

						if (validCnt3 == 0) {
							throw new ServiceException(makeMap("첨부파일마스터 데이터를 삭제할 수 없습니다."));
						}

						int validCnt4 = approvalRqDao.deleteSymposiumApprovalAttach(paramVo);
						// 품의서첨부파일 테이블 삭제

						if (validCnt4 == 0) {
							throw new ServiceException(makeMap("품의서첨부파일 데이터를 삭제할 수 없습니다."));
						}

						File file = new File(PropUtil.getValue("file.uploadPolicy.path") + fileInfo.getFilePath() + "\\" + fileInfo.getPfileNm() + "." + fileInfo.getExt());
						// 물리파일 액세스

						if (file.exists()) {
							try {
								file.delete();
							} catch (Exception e) {
								throw new ServiceException(makeMap("첨부파일 삭제 실패했습니다."));
							}
						}
					}
				}
				
				int validCnt5 = approvalRqDao.deleteApprovalLine(paramVo);
				// 품위서 결재라인 삭제
				if(validCnt5 == 0){
					throw new ServiceException(makeMap("품의서 결재라인을 삭제할 수 없습니다."));
				}
				
				int validCnt6 = approvalRqDao.deleteApproval(paramVo);
				// 품위서 삭제
				if(validCnt6 == 0){
					throw new ServiceException(makeMap("품의서를 삭제할 수 없습니다."));
				}
				
			} else {
				throw new ServiceException(makeMap("품의내역 데이터를 삭제할 수 없습니다."));
			}
		}
	}

	private void deleteSymposium(String approvalId) {

		if (approvalId != null && !"".equals(approvalId)) {
			SymposiumMgmtVo paramVo = new SymposiumMgmtVo();

			paramVo.setCompanyCd(getLoginUserInfo().getCompanyCode());
			paramVo.setApprovalId(approvalId);			
			
			List<SymposiumMgmtVo> valT = approvalRqDao.selectApprovalStatusT(paramVo);
			// 상태값 T인 데이터 지우기
			for(SymposiumMgmtVo tmp : valT){
				tmp.setCompanyCd(paramVo.getCompanyCd());
				approvalRqDao.deleteApproval(tmp);
				approvalRqDao.deleteSymposiumApprovalItem(tmp);
			}	

			
			int validCnt1 = approvalRqDao.validCancelSymposium(paramVo);
			// 심포지움 데이터를 삭제할 수 있는지 체크

			if (validCnt1 == 0) {

				int validCnt2 = approvalRqDao.deleteSymposiumApprovalItem(paramVo);
				// 품의내역 테이블 삭제

				if (validCnt2 != 0) {

					List<String> attachIdList = approvalRqDao.selectSymposiumApprovalAttach(paramVo);
					// 삭제할 첨부파일 대상 조회 (ATTACH_ID)

					if (attachIdList != null && attachIdList.size() > 0) {

						for (String attachId : attachIdList) {
							paramVo.setAttachId(attachId);

							SymposiumMgmtVo fileInfo = approvalRqDao.selectSymposiumAttachFile(paramVo);
							// 첨부파일 정보 조회

							int validCnt3 = approvalRqDao.deleteSymposiumAttachFile(paramVo);
							// 첨부파일내역 테이블 삭제

							if (validCnt3 == 0) {
								throw new ServiceException(makeMap("첨부파일내역 데이터를 삭제할 수 없습니다."));
							}

							int validCnt4 = approvalRqDao.deleteSymposiumAttach(paramVo);
							// 첨부파일마스터 테이블 삭제

							if (validCnt4 == 0) {
								throw new ServiceException(makeMap("첨부파일마스터 데이터를 삭제할 수 없습니다."));
							}

							int validCnt5 = approvalRqDao.deleteSymposiumApprovalAttach(paramVo);
							// 품의서첨부파일 테이블 삭제

							if (validCnt5 == 0) {
								throw new ServiceException(makeMap("품의서첨부파일 데이터를 삭제할 수 없습니다."));
							}

							File file = new File(PropUtil.getValue("file.uploadPolicy.path") + fileInfo.getFilePath() + "\\" + fileInfo.getPfileNm() + "." + fileInfo.getExt());
							// 물리파일 액세스

							if (file.exists()) {
								try {
									file.delete();
								} catch (Exception e) {
									throw new ServiceException(makeMap("첨부파일 삭제 실패했습니다."));
								}
							}
						}
					}
				} else {
					throw new ServiceException(makeMap("품의내역 데이터를 삭제할 수 없습니다."));
				}
			} else {
				throw new ServiceException(makeMap("심포지움 데이터를 삭제할 수 없습니다."));
			}
		}
	}

	private void requestApproval(String approvalId) {

		if (approvalId != null && !"".equals(approvalId)) {
			ApprovalLineVo approvalLineVo = new ApprovalLineVo();
			approvalLineVo.setApprovalId(approvalId);
			approvalLineVo.setSeq("1");
			approvalLineVo.setAprComment("전결");

			LoginUserVO loginUserVO = getLoginUserInfo();
			setApprovalCode(approvalLineVo, loginUserVO);

			ApprovalMgmtVo paramVo = new ApprovalMgmtVo();

			paramVo.setApprovalId(approvalId);
			paramVo.setCompanyCd(loginUserVO.getCompanyCode());
			paramVo.setUpdUserId(loginUserVO.getUserId());

			int validCnt1 = approvalDao.updateApprovalCode(approvalLineVo);
			// 결재라인 테이블 업데이트 (결재코드, 결재의견, 실결재자정보)

			if (validCnt1 != 1) {
				throw new ServiceException(makeMap("결재 정보를 수정할 수 없습니다."));
			}

			paramVo.setRqUserId(approvalLineVo.getActUserId());
			paramVo.setUpdUserId(approvalLineVo.getUpdUserId());

			paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NAPR);

			int validCnt2 = approvalDao.updateApprovalStatusCode(paramVo);
			// 품의서 테이블 업데이트 (APPROVAL_STATUS_CD)

			if (validCnt2 != 1) {
				throw new ServiceException(makeMap("품의서 정보를 수정할 수 없습니다."));
			}

			int validCnt3 = 0;

			List<HashMap<String, String>> interfaceList = approvalDao.selectInterfaceList(paramVo);
			// 인터페이스 전송 목록 조회

			if (interfaceList != null && interfaceList.size() > 0) {
				for (int i = 0; i < interfaceList.size(); i++) {
					validCnt3 += approvalDao.insertInterface(interfaceList.get(i));
					// 인터페이스 전송
				}
			}

			if (validCnt3 == 0) {
				throw new ServiceException(makeMap("인터페이스 데이터 전송 실패했습니다."));
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}
	}

	private Map<String, String> makeMap(String message) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", "F");
		map.put("msg", message);

		return map;
	}
	
	private String makeResoreSmsContent(long amount) {
		
		String today = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
		String smsContent = "법인카드결재("
							+ today
							+"/ "
							+ new DecimalFormat("###,###").format(amount)
							+"원)가 "
							+ getLoginUserInfo().getName() 
							+ "님에 의해 반려되었습니다. 확인바랍니다.";
		return smsContent;
	}

	private String makeSymposiumResoreMailContent(SymposiumMgmtVo obj) {
		StringBuilder sb = new StringBuilder();

		if (obj != null) {
			sb.append("<table width='1000' border='0' cellpadding='0' cellspacing='0' style='border:1px solid #000;font-size:12px;'>");
			sb.append("<tr>");
			sb.append("<td style='padding:20px;'>");
			sb.append("<table width='100%' border='0' cellpadding='0' cellspacing='0' style='font-size:12px;'>");
			sb.append("<tr>");
			sb.append("<td style='padding:20px 0;'>");
			sb.append("아래와 같이 법인카드 심포지움 결재요청 카드내역이 반려되었습니다.<br/>확인하여 처리하시길 바랍니다.");
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("<table width='100%' border='0' cellpadding='0' cellspacing='0' style='font-size:12px;text-align:center;'>");
			sb.append("<tr>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>사용일</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>상호</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>합계금액</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>담당자</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>예산부서</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>계정구분</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>제품군/명</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>거래처</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>사용내역</th>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getAuthDate()).substring(0, 4) + "-" + StringUtil.nvl(obj.getAuthDate()).substring(4, 6) + "-" + StringUtil.nvl(obj.getAuthDate()).substring(6, 8) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getMercName()) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + NumberFormat.getCurrencyInstance().format(Long.parseLong(StringUtil.nvl(obj.getRequestAmount()))) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getUserNm()) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getBudgetDeptNm()) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getAccount2Nm()) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getProductNm()) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getCustomerNm()) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getDetails()) + "</td>");
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>&nbsp;</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td style='font-size:16px;'>");
			sb.append("<a href='http://bron.boryung.co.kr/'>BR-EAS 바로가기</a>");
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("</table>");
		}

		return sb.toString();
	}

	private void setSearchListParam(SymposiumMgmtVo paramVo, LoginUserVO loginUserInfo, String authDate, int pageNo) {
		// paging
		if (pageNo > 0) {
			paramVo.setPagingYn("Y");
		} else {
			paramVo.setPagingYn("N");
		}
		paramVo.setPageNo(pageNo);

		paramVo.setRqUserId(loginUserInfo.getUserId());
		paramVo.setCompanyCd(loginUserInfo.getCompanyCode());

		paramVo.setAuthDate(authDate);
	}

	private void setApprovalCode(ApprovalLineVo param, LoginUserVO loginUserInfo) {
		// company_cd
		param.setCompanyCd(loginUserInfo.getCompanyCode());

		// actUserId, actUserNm, actDeptCd, actDeptNm, actTitleCd, actTitleNm,
		// actDutyCd, actDutyNm, updUserId
		param.setActUserId(loginUserInfo.getUserId());
		param.setActUserNm(loginUserInfo.getName());
		param.setActDeptCd(loginUserInfo.getDeptCode());
		param.setActDeptNm(loginUserInfo.getDeptName());
		param.setActTitleCd(loginUserInfo.getTitleCode());
		param.setActTitleNm(loginUserInfo.getTitleName());
		param.setActDutyCd(loginUserInfo.getDutyCode());
		param.setActDutyNm(loginUserInfo.getDutyName());
		param.setUpdUserId(loginUserInfo.getUserId());

		// apr_cd
		param.setAprCd(StringConstants.APPROVAL_CD_APR);
	}

	private LoginUserVO getLoginUserInfo() {
		return (LoginUserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}