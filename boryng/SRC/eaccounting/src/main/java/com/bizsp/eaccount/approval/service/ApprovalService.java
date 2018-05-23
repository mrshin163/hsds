package com.bizsp.eaccount.approval.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bizsp.eaccount.approval.dao.ApprovalDao;
import com.bizsp.eaccount.approval.vo.ApprovalItemVo;
import com.bizsp.eaccount.approval.vo.ApprovalLineVo;
import com.bizsp.eaccount.approval.vo.ApprovalMgmtVo;
import com.bizsp.eaccount.approval.vo.ApprovalVo;
import com.bizsp.eaccount.batch.dao.BatchDao;
import com.bizsp.framework.security.vo.LoginUserVO;
import com.bizsp.framework.util.exception.ServiceException;
import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.mail.MailUtil;
import com.bizsp.framework.util.system.PropUtil;
import com.bizsp.framework.util.web.HtmlUtil;
import com.bizsp.framework.util.web.StringConstants;

@Path("/rest/approvalApi")
@Component
public class ApprovalService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApprovalDao approvalDao;

	@Autowired
	private BatchDao batchDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("approvalList/{sDate}/{eDate}/{productCd}/{rqDeptCd}/{rqDeptNm}/{rqUserId}/{rqUserNm}/{pageNo}")
	public Map<String, Object> getApprovalList(@PathParam("sDate") String sDate, @PathParam("eDate") String eDate, @PathParam("productCd") String productCd, @PathParam("rqDeptCd") String rqDeptCd, @PathParam("rqDeptNm") String rqDeptNm, @PathParam("rqUserId") String rqUserId, @PathParam("rqUserNm") String rqUserNm, @PathParam("pageNo") int pageNo) {

		logger.debug(":::::::::: getApprovalList ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ApprovalMgmtVo paramVo = new ApprovalMgmtVo();
		setSearchListParam(paramVo, getLoginUserInfo(), sDate, eDate, pageNo);

		paramVo.setProductCd(productCd);
		paramVo.setRqDeptCd(rqDeptCd);
		paramVo.setRqDeptNm(rqDeptNm.replaceAll("`", "/"));
		paramVo.setRqUserId(rqUserId);
		paramVo.setRqUserNm(rqUserNm);

		List<ApprovalMgmtVo> list = null;

		if (!isAdmin()) {
			list = approvalDao.selectApprovalList(paramVo);
		} else {
			list = approvalDao.selectApprovalListForAdmin(paramVo);
			resultMap.put("adminFlag", "Y");
		}

		resultMap.put("html", HtmlUtil.makeHtmlForList(list, HtmlUtil.APPROVAL_NAME, HtmlUtil.APPROVAL_TYPE));
		resultMap.put("totalRow", paramVo.getTotalRow());

		return resultMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("approvalProgressList/{sDate}/{eDate}/{pageNo}")
	public Map<String, Object> getApprovalProgressList(@PathParam("sDate") String sDate, @PathParam("eDate") String eDate, @PathParam("pageNo") int pageNo) {

		logger.debug(":::::::::: getApprovalProgressList ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ApprovalMgmtVo paramVo = new ApprovalMgmtVo();
		setSearchListParam(paramVo, getLoginUserInfo(), sDate, eDate, pageNo);

		List<ApprovalMgmtVo> list = approvalDao.selectApprovalProgressList(paramVo);

		resultMap.put("html", HtmlUtil.makeHtmlForList(list, HtmlUtil.APPROVAL_PROGRESS_NAME, HtmlUtil.APPROVAL_PROGRESS_TYPE));
		resultMap.put("totalRow", paramVo.getTotalRow());

		return resultMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("approvalCompleteList/{sDate}/{eDate}/{approvalStatusCd}/{pageNo}")
	public Map<String, Object> getApprovalCompleteList(@PathParam("sDate") String sDate, @PathParam("eDate") String eDate, @PathParam("approvalStatusCd") String approvalStatusCd, @PathParam("pageNo") int pageNo) {

		logger.debug(":::::::::: getApprovalCompleteList ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ApprovalMgmtVo paramVo = new ApprovalMgmtVo();
		setSearchListParam(paramVo, getLoginUserInfo(), sDate, eDate, pageNo);

		paramVo.setApprovalStatusCd(approvalStatusCd);

		List<ApprovalMgmtVo> list = approvalDao.selectApprovalCompleteList(paramVo);

		resultMap.put("html", HtmlUtil.makeHtmlForList(list, HtmlUtil.APPROVAL_COMPLETE_NAME, HtmlUtil.APPROVAL_COMPLETE_TYPE));
		resultMap.put("totalRow", paramVo.getTotalRow());

		return resultMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("progressList/{sDate}/{eDate}/{pageNo}")
	public Map<String, Object> getProgressList(@PathParam("sDate") String sDate, @PathParam("eDate") String eDate, @PathParam("pageNo") int pageNo) {

		logger.debug(":::::::::: getProgressList ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ApprovalMgmtVo paramVo = new ApprovalMgmtVo();
		setSearchListParam(paramVo, getLoginUserInfo(), sDate, eDate, pageNo);

		List<ApprovalMgmtVo> list = approvalDao.selectProgressList(paramVo);

		resultMap.put("html", HtmlUtil.makeHtmlForList(list, HtmlUtil.PROGRESS_NAME, HtmlUtil.PROGRESS_TYPE));
		resultMap.put("totalRow", paramVo.getTotalRow());

		return resultMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("completeList/{sDate}/{eDate}/{pageNo}")
	public Map<String, Object> getCompleteList(@PathParam("sDate") String sDate, @PathParam("eDate") String eDate, @PathParam("pageNo") int pageNo) {

		logger.debug(":::::::::: getCompleteList ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ApprovalMgmtVo paramVo = new ApprovalMgmtVo();
		setSearchListParam(paramVo, getLoginUserInfo(), sDate, eDate, pageNo);

		List<ApprovalMgmtVo> list = approvalDao.selectCompleteList(paramVo);

		resultMap.put("html", HtmlUtil.makeHtmlForList(list, HtmlUtil.COMPLETE_NAME, HtmlUtil.COMPLETE_TYPE));
		resultMap.put("totalRow", paramVo.getTotalRow());

		return resultMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("restoreList/{sDate}/{eDate}/{pageNo}")
	public Map<String, Object> getRestoreList(@PathParam("sDate") String sDate, @PathParam("eDate") String eDate, @PathParam("pageNo") int pageNo) {

		logger.debug(":::::::::: getRestoreList ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ApprovalMgmtVo paramVo = new ApprovalMgmtVo();
		setSearchListParam(paramVo, getLoginUserInfo(), sDate, eDate, pageNo);

		List<ApprovalMgmtVo> list = approvalDao.selectRestoreList(paramVo);

		resultMap.put("html", HtmlUtil.makeHtmlForList(list, HtmlUtil.RESTORE_NAME, HtmlUtil.RESTORE_TYPE));
		resultMap.put("totalRow", paramVo.getTotalRow());

		return resultMap;
	}

	@GET
	@Path("approvalData/{approvalId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response approvalData(@PathParam("approvalId") String approvalId) throws Exception {

		logger.debug(":::::::::: approvalData 품의서 정보 조회 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (approvalId != null && !"".equals(approvalId)) {
			ApprovalMgmtVo paramVo = new ApprovalMgmtVo();
			paramVo.setCompanyCd(getLoginUserInfo().getCompanyCode());
			paramVo.setApprovalId(approvalId);

			ApprovalVo approvalVo = approvalDao.selectApproval(paramVo);

			if (approvalVo != null) {
				resultMap.put("approvalVo", approvalVo);
				resultMap.put("code", "S");
			} else {
				throw new ServiceException(makeMap("품의서 정보가 존재하지 않습니다."));
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
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
			paramVo.setSeq(seq);
			paramVo.setCompanyCd(getLoginUserInfo().getCompanyCode());

			List<ApprovalMgmtVo> attachFileList = approvalDao.selectAttachFileList(paramVo);

			if (attachFileList != null && attachFileList.size() > 0) {
				resultMap.put("attachFilelist", makeHtmlForAttachFileList(attachFileList));
				resultMap.put("code", "S");
			} else {
				throw new ServiceException(makeMap("첨부파일이 존재하지 않습니다."));
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
	}

	@GET
	@Path("calculateData/{approvalId}/{flag}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response calculateData(@PathParam("approvalId") String approvalId, @PathParam("flag") String flag) throws Exception {

		logger.debug(":::::::::: calculateData 정산서 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (approvalId != null && !"".equals(approvalId)) {
			ApprovalMgmtVo paramVo = new ApprovalMgmtVo();
			paramVo.setFlag(flag);

			LoginUserVO loginUserVO = getLoginUserInfo();

			paramVo.setApprovalId(approvalId);
			paramVo.setCompanyCd(loginUserVO.getCompanyCode());
			paramVo.setRqUserId(loginUserVO.getUserId());

			List<ApprovalLineVo> approvalLineVoList = approvalDao.selectApprovalLineList(paramVo);
			// 결재라인 조회
			List<ApprovalItemVo> approvalItemVoList = approvalDao.selectApprovalItemList(paramVo);
			// 품의내역 조회

			if (approvalLineVoList != null && approvalLineVoList.size() > 0) {
				if (approvalItemVoList != null && approvalItemVoList.size() > 0) {
					resultMap.put("lineList", HtmlUtil.makeHtmlForApprovalLineList(approvalLineVoList));
					resultMap.put("itemList", HtmlUtil.makeHtmlForList(approvalItemVoList, HtmlUtil.CALCULATE_ITEM_NAME, HtmlUtil.CALCULATE_ITEM_TYPE));

					if ("approval".equals(flag)) {
						if (!isAdmin()) {
							paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NREQ);
							int validCnt1 = approvalDao.validApprovalStatusCode(paramVo);
							// 상신중 상태인지 체크

							if (approvalDao.validFirstApUserId(paramVo) == 1 && validCnt1 == 1) {
								// 1차 결재자가 준법경영팀인지 체크 && 현재 결재 차수가 1차인지 체크
								resultMap.put("firstFlag", "Y");
							}

							paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NING);
							int validCnt2 = approvalDao.validApprovalStatusCode(paramVo);
							// 진행중 상태인지 체크

							if (validCnt1 == 1 || validCnt2 == 1) {
								resultMap.put("btnFlag", "Y");

								if (approvalDao.validFirstApprovalCode(paramVo) == 1) {
									// 1차 결재자의 결재 여부 체크
									if ("52140".equals(loginUserVO.getDeptCode())) {
										// 본인의 부서가 준법경영팀인지 체크
										if (approvalDao.validFirstApUserId(paramVo) == 1 && approvalDao.validAccountCd(paramVo) == 1) {
											// 1차 결재자가 준법경영팀인지 체크 && ACCOUNT_CD IN (3, 4) 인 품의서인지 체크
											resultMap.put("accountSpCdFlag", "Y");
											resultMap.put("accountCd", approvalDao.selectAccountCd(paramVo));
										}
									}
								}
							}
						} else {
							resultMap.put("adminFlag", "Y");
						}
					} else if ("approvalComplete".equals(flag)) {
						paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NAPR);
						if (approvalDao.validApprovalStatusCode(paramVo) == 1 && approvalDao.validLastApprovalUser(paramVo) == 1) {
							// 결재진행상태가 NAPR 인지 체크 && 본인이 최종 결재자인지 체크
							resultMap.put("btnFlag", "Y");
						}
					} else if ("progress".equals(flag)) {
						if (approvalDao.validFirstApprovalCode(paramVo) == 1 && approvalDao.validMyApproval(paramVo) == 1) {
							// 1차 결재자의 결재 여부 체크 && 본인이 상신자이거나 대체상신자인지 체크
							resultMap.put("btnFlag", "Y");
						}
					} else if ("restore".equals(flag)) {
						if (approvalDao.validAccountCode(paramVo) == 1 && approvalDao.validMyApproval(paramVo) == 1) {
							// 품의서가 심포지움 건이 아닌지 체크 && 본인이 상신자이거나 대체상신자인지 체크
							resultMap.put("btnFlag", "Y");
						}
					} else if ("report".equals(flag)) {
						// FIXME 사후 처리 용도
					}
					resultMap.put("code", "S");
				} else {
					throw new ServiceException(makeMap("카드사용내역이 존재하지 않습니다."));
				}
			} else {
				throw new ServiceException(makeMap("결재 정보가 존재하지 않습니다."));
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
	}

	@PUT
	@Path("cancelRequest/{approvalId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response cancelRequest(@PathParam("approvalId") String approvalId) throws Exception {

		logger.debug(":::::::::: cancelRequest 상신 취소 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (approvalId != null && !"".equals(approvalId)) {
			ApprovalMgmtVo paramVo = new ApprovalMgmtVo();

			LoginUserVO loginUserVO = getLoginUserInfo();

			paramVo.setApprovalId(approvalId);
			paramVo.setCompanyCd(loginUserVO.getCompanyCode());
			paramVo.setRqUserId(loginUserVO.getUserId());

			int validCnt1 = approvalDao.validFirstApprovalCode(paramVo);
			// 1차 결재자의 결재 여부 체크

			int validCnt2 = approvalDao.validMyApproval(paramVo);
			// 본인이 상신자이거나 대체상신자인지 체크

			if (validCnt1 == 1 && validCnt2 == 1) {
				paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NCNL);
				paramVo.setUpdUserId(loginUserVO.getUserId());

				int validCnt3 = approvalDao.updateApprovalStatusCode(paramVo);
				// 품의서 테이블 업데이트 (APPROVAL_STATUS_CD)

				if (validCnt3 != 1) {
					throw new ServiceException(makeMap("품의서 정보를 수정할 수 없습니다."));
				}

				int validCnt4 = approvalDao.validSymposiumApproval(paramVo);
				// 품의서가 심포지움 건인지 체크

				if (validCnt4 == 1) {
					paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_SR);
					int validCnt5 = approvalDao.updateForSymposium(paramVo);
					// 심포지움 건이면 원천 데이터 SR로 업데이트

					if (validCnt5 == 0) {
						throw new ServiceException(makeMap("심포지움 원천 품의서 정보를 수정할 수 없습니다."));
					}
				}
				resultMap.put("code", "S");
			} else {
				throw new ServiceException(makeMap("이미 결재 진행중입니다."));
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
	}

	@POST
	@Path("reApproval/{approvalId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response reApproval(@PathParam("approvalId") String approvalId) throws Exception {

		logger.debug(":::::::::: reApproval 재품의 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (approvalId != null && !"".equals(approvalId)) {
			ApprovalMgmtVo paramVo = new ApprovalMgmtVo();

			LoginUserVO loginUserVO = getLoginUserInfo();

			paramVo.setApprovalId(approvalId);
			paramVo.setCompanyCd(loginUserVO.getCompanyCode());
			paramVo.setRqUserId(loginUserVO.getUserId());

			int validCnt1 = approvalDao.validAccountCode(paramVo);
			// 품의서가 심포지움 건이 아닌지 체크

			int validCnt2 = approvalDao.validMyApproval(paramVo);
			// 본인이 상신자이거나 대체상신자인지 체크

			if (validCnt1 == 1 && validCnt2 == 1) {
				int validCnt3 = approvalDao.insertApproval(paramVo);
				// 품의서 복사

				int validCnt4 = approvalDao.insertApprovalItem(paramVo);
				// 품의내역 복사

				approvalDao.insertApprovalAttach(paramVo);
				// 첨부파일 복사

				if (validCnt3 == 1 && validCnt4 > 0) {
					resultMap.put("newApprovalId", paramVo.getNewApprovalId());
					resultMap.put("oldApprovalId", approvalId);
					resultMap.put("code", "S");
				} else {
					throw new ServiceException(makeMap("재품의 실패했습니다."));
				}
			} else {
				throw new ServiceException(makeMap("재품의 할 수 없습니다."));
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
	}

	@PUT
	@Path("cancelApproval/{approvalId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response cancelApproval(@PathParam("approvalId") String approvalId) throws Exception {

		logger.debug(":::::::::: cancelApproval 승인 취소 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (approvalId != null && !"".equals(approvalId)) {
			ApprovalMgmtVo paramVo = new ApprovalMgmtVo();

			LoginUserVO loginUserVO = getLoginUserInfo();

			paramVo.setApprovalId(approvalId);
			paramVo.setCompanyCd(loginUserVO.getCompanyCode());
			paramVo.setRqUserId(loginUserVO.getUserId());
			paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NAPR);

			int validCnt1 = approvalDao.validApprovalStatusCode(paramVo);
			// 결재진행상태가 NAPR 인지 체크

			int validCnt2 = approvalDao.validLastApprovalUser(paramVo);
			// 본인이 최종 결재자인지 체크

			if (validCnt1 == 1 && validCnt2 == 1) {
				ApprovalLineVo approvalLineVo = new ApprovalLineVo();
				approvalLineVo.setUpdUserId(loginUserVO.getUserId());
				approvalLineVo.setCompanyCd(loginUserVO.getCompanyCode());
				approvalLineVo.setApprovalId(approvalId);

				int validCnt3 = approvalDao.updateCancelApprovalCode(approvalLineVo);
				// 결재라인 테이블 승인 취소 업데이트

				String validStr = approvalDao.selectSeqOfLastApprovalUser(paramVo);

				if (!"1".equals(validStr)) {
					paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NING);
				} else {
					// 최종결재자의 차수가 1이면 NREQ로 원복
					paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NREQ);
				}
				paramVo.setUpdUserId(loginUserVO.getUserId());

				int validCnt4 = approvalDao.updateCancelApprovalStatusCode(paramVo);
				// 품의서 테이블 승인 취소 업데이트

				if (validCnt3 == 1 && validCnt4 == 1) {
					int validCnt5 = approvalDao.deleteInterfaceData(paramVo);
					// 인터페이스 삭제

					if (validCnt5 == 0) {
						throw new ServiceException(makeMap("인터페이스 데이터 삭제 실패했습니다."));
					}

					int validCnt6 = approvalDao.validSymposiumApproval(paramVo);
					// 품의서가 심포지움 건인지 체크

					if (validCnt6 == 1) {
						paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_SING);
						paramVo.setApprovalDate(null);

						int validCnt7 = approvalDao.updateForSymposium(paramVo);
						// 심포지움 건이면 원천 데이터 업데이트

						if (validCnt7 == 0) {
							throw new ServiceException(makeMap("심포지움 원천 품의서 정보를 수정할 수 없습니다."));
						}
					}
					resultMap.put("code", "S");
				} else {
					throw new ServiceException(makeMap("승인 취소 실패했습니다."));
				}
			} else {
				throw new ServiceException(makeMap("승인 취소 할 수 없습니다."));
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
	}

	@PUT
	@Path("approval/{approvalId}/{seq}/{aprComment}/{accountSpCd}/{account2Str}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response approval(@PathParam("approvalId") String approvalId, @PathParam("seq") String seq, @PathParam("aprComment") String aprComment, @PathParam("accountSpCd") String accountSpCd, @PathParam("account2Str") String account2Str) throws Exception {

		logger.debug(":::::::::: approval 승인 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (approvalId != null && !"".equals(approvalId) && seq != null && !"".equals(seq) && aprComment != null && !"".equals(aprComment) && accountSpCd != null && !"".equals(accountSpCd)) {
			ApprovalLineVo approvalLineVo = new ApprovalLineVo();
			approvalLineVo.setApprovalId(approvalId);
			approvalLineVo.setSeq(seq);

			if (aprComment != null && !"N".equals(aprComment)) {
				approvalLineVo.setAprComment(aprComment);
			}

			LoginUserVO loginUserVO = getLoginUserInfo();
			setApprovalCode(approvalLineVo, loginUserVO);

			ApprovalMgmtVo paramVo = new ApprovalMgmtVo();

			paramVo.setApprovalId(approvalId);
			paramVo.setCompanyCd(loginUserVO.getCompanyCode());
			paramVo.setUpdUserId(loginUserVO.getUserId());

			paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NREQ);
			int validCnt1 = approvalDao.validApprovalStatusCode(paramVo);
			// 상신중 상태인지 체크

			paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NING);
			int validCnt2 = approvalDao.validApprovalStatusCode(paramVo);
			// 진행중 상태인지 체크

			if (validCnt1 == 1 || validCnt2 == 1) {
				if (validCnt1 == 1) {
					if (account2Str == null || "N".equals(account2Str)) {
						throw new ServiceException(makeMap("세부계정이 입력되지 않았습니다."));
					}

					int validCnt3 = 0;

					String[] arr = account2Str.split("\\|");

					for (int i = 0; i < arr.length; i++) {
						String[] tmpArr = arr[i].split("`");

						ApprovalMgmtVo tmpVo = new ApprovalMgmtVo();

						tmpVo.setApprovalId(approvalId);
						tmpVo.setCompanyCd(loginUserVO.getCompanyCode());
						tmpVo.setUpdUserId(loginUserVO.getUserId());
						tmpVo.setSeq(tmpArr[0]);
						tmpVo.setAccount2Cd(tmpArr[1]);

						validCnt3 += approvalDao.updateAccount2Cd(tmpVo);
						// 1차 결재자 승인 시, 품의내역 테이블 세부계정 업데이트
					}

					if (validCnt3 == 0) {
						throw new ServiceException(makeMap("세부계정을 수정할 수 없습니다."));
					}
				}

				if (approvalDao.validFirstApprovalCode(paramVo) == 1) {
					// 1차 결재자의 결재 여부 체크

					if ("52140".equals(loginUserVO.getDeptCode())) {
						// 본인의 부서가 준법경영팀인지 체크

						int validCnt4 = approvalDao.validFirstApUserId(paramVo);
						// 1차 결재자가 준법경영팀인지 체크

						int validCnt5 = approvalDao.validAccountCd(paramVo);
						// ACCOUNT_CD IN (3, 4) 인 품의서인지 체크

						if (validCnt4 == 1 && validCnt5 == 1) {

							if (accountSpCd != null && !"N".equals(accountSpCd)) {
								paramVo.setAccountSpCd(accountSpCd);
							}

							int validCnt6 = approvalDao.updateAccountSpCd(paramVo);
							// 품의내역 테이블 업데이트 (ACCOUNT_SP_CD, ACCOUNT_SP_NM)

							if (validCnt6 == 0) {
								throw new ServiceException(makeMap("품의내역을 수정할 수 없습니다."));
							}
						}
					}
				}

				int validCnt7 = approvalDao.updateApprovalCode(approvalLineVo);
				// 결재라인 테이블 업데이트 (결재코드, 결재의견, 실결재자정보)

				if (validCnt7 != 1) {
					throw new ServiceException(makeMap("결재 정보를 수정할 수 없습니다."));
				}

				paramVo.setRqUserId(approvalLineVo.getActUserId());
				paramVo.setUpdUserId(approvalLineVo.getUpdUserId());

				int validCnt8 = approvalDao.validLastApprovalUser(paramVo);
				// 본인이 최종 결재자인지 체크

				if (validCnt8 == 1) {
					paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NAPR);

					int validCnt9 = approvalDao.updateApprovalStatusCode(paramVo);
					// 품의서 테이블 업데이트 (APPROVAL_STATUS_CD)

					if (validCnt9 != 1) {
						throw new ServiceException(makeMap("품의서 정보를 수정할 수 없습니다."));
					}

					int validCnt10 = 0;

					List<HashMap<String, String>> interfaceList = approvalDao.selectInterfaceList(paramVo);
					// 인터페이스 전송 목록 조회

					if (interfaceList != null && interfaceList.size() > 0) {
						for (int i = 0; i < interfaceList.size(); i++) {
							validCnt10 += approvalDao.insertInterface(interfaceList.get(i));
							// 인터페이스 전송
						}
					}

					if (validCnt10 == 0) {
						throw new ServiceException(makeMap("인터페이스 데이터 전송 실패했습니다."));
					}

					int validCnt11 = approvalDao.validSymposiumApproval(paramVo);
					// 품의서가 심포지움 건인지 체크

					if (validCnt11 == 1) {
						paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_SAPR);
						int validCnt12 = approvalDao.updateForSymposium(paramVo);
						// 심포지움 건이면 원천 데이터 SAPR로 업데이트

						if (validCnt12 == 0) {
							throw new ServiceException(makeMap("심포지움 원천 품의서 정보를 수정할 수 없습니다."));
						}
					}
				} else {
					paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NING);
					int validCnt13 = approvalDao.updateApprovalStatusCode(paramVo);
					// 품의서 테이블 업데이트 (APPROVAL_STATUS_CD)

					if (validCnt13 != 1) {
						throw new ServiceException(makeMap("품의서 정보를 수정할 수 없습니다."));
					}
				}
				resultMap.put("code", "S");
			} else {
				throw new ServiceException(makeMap("승인할 수 없습니다."));
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
	}

	@PUT
	@Path("restore/{approvalId}/{seq}/{aprComment}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Response restore(@PathParam("approvalId") String approvalId, @PathParam("seq") String seq, @PathParam("aprComment") String aprComment) throws Exception {

		logger.debug(":::::::::: restore 반려 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (approvalId != null && !"".equals(approvalId) && seq != null && !"".equals(seq) && aprComment != null && !"".equals(aprComment)) {
			ApprovalLineVo approvalLineVo = new ApprovalLineVo();
			approvalLineVo.setApprovalId(approvalId);
			approvalLineVo.setSeq(seq);

			if (aprComment != null && !"".equals(aprComment)) {
				approvalLineVo.setAprComment(aprComment);
			}

			LoginUserVO loginUserVO = getLoginUserInfo();
			setRestoreCode(approvalLineVo, loginUserVO);

			ApprovalMgmtVo paramVo = new ApprovalMgmtVo();

			paramVo.setApprovalId(approvalLineVo.getApprovalId());
			paramVo.setCompanyCd(approvalLineVo.getCompanyCd());
			paramVo.setUpdUserId(loginUserVO.getUserId());

			paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NREQ);
			int validCnt1 = approvalDao.validApprovalStatusCode(paramVo);
			// 상신중 상태인지 체크

			paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NING);
			int validCnt2 = approvalDao.validApprovalStatusCode(paramVo);
			// 진행중 상태인지 체크

			if (validCnt1 == 1 || validCnt2 == 1) {

				int validCnt3 = approvalDao.updateApprovalCode(approvalLineVo);
				// 결재라인 테이블 업데이트 (결재코드, 결재의견, 실결재자정보)

				if (validCnt3 != 1) {
					throw new ServiceException(makeMap("결재 정보를 수정할 수 없습니다."));
				}

				paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_NREJ);
				int validCnt4 = approvalDao.updateApprovalStatusCode(paramVo);
				// 품의서 테이블 업데이트 (APPROVAL_STATUS_CD)

				if (validCnt4 != 1) {
					throw new ServiceException(makeMap("품의서 정보를 수정할 수 없습니다."));
				}

				int validCnt5 = approvalDao.validSymposiumApproval(paramVo);
				// 품의서가 심포지움 건인지 체크

				if (validCnt5 == 1) {
					paramVo.setApprovalStatusCd(StringConstants.APPROVAL_STATUS_CD_SREJ);

					int validCnt6 = approvalDao.updateForSymposium(paramVo);
					// 심포지움 건이면 원천 데이터 SREJ로 업데이트

					if (validCnt6 == 0) {
						throw new ServiceException(makeMap("심포지움 원천 품의서 정보를 수정할 수 없습니다."));
					}
				}

				paramVo.setSeq(seq);
				List<HashMap<String, String>> sendMailList = approvalDao.selectUserListForSendMail(paramVo);

				String recipients = "";
				for (HashMap<String, String> tmpMap : sendMailList) {
					recipients += tmpMap.get("EMAIL_ADDR");
					recipients += ",";
				}

				ApprovalMgmtVo restoreVo = approvalDao.selectRestoreData(paramVo);
				// 반려 메일 내용 조회

				String content = makeResoreMailContent(restoreVo);

				try {
					HashMap<String, String> map = new HashMap<String, String>();
					if ("DEV".equalsIgnoreCase(PropUtil.getValue("running.mode"))) {
						map.put("recipients", PropUtil.getValue("test_email_recipient_address"));
					} else if ("REAL".equalsIgnoreCase(PropUtil.getValue("running.mode"))) {
						map.put("recipients", recipients);

						// FIXME 운영도 4/1 부터 정상 메일 발송
						// 임시로 이청일 대리에게 메일 발송 처리
						// 하기 코드 제거 + 상기 코드 주석 제거
						//map.put("recipients", "cilee99@brnetcomm.co.kr");
					}
					map.put("subject", "법인카드 결재가 반려되었습니다.");
					map.put("content", content);									
					
					ApprovalItemVo approvalItemVo = approvalDao.selectApprovalItem(paramVo);
					for (HashMap<String, String> tmpMap : sendMailList) {
						String smsContent = makeResoreSmsContent(Long.parseLong(approvalItemVo.getRequestAmount()));
//						batchDao.insertSms(smsContent, "01032042877", "01032042877", "V99999");
						batchDao.insertSms(smsContent, getLoginUserInfo().getMobileNo(), tmpMap.get("MOBILE_NO"), getLoginUserInfo().getUserId());
					}					
					
					MailUtil.sendMail(map);
				} catch (Exception e) {
					e.printStackTrace();
				}

				resultMap.put("code", "S");
			} else {
				throw new ServiceException(makeMap("반려 할 수 없습니다."));
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
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

	private void setRestoreCode(ApprovalLineVo param, LoginUserVO loginUserInfo) {
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
		param.setAprCd(StringConstants.APPROVAL_CD_REJ);
	}

	private void setSearchListParam(ApprovalMgmtVo paramVo, LoginUserVO loginUserInfo, String sDate, String eDate, int pageNo) {
		// paging
		if (pageNo > 0) {
			paramVo.setPagingYn("Y");
		} else {
			paramVo.setPagingYn("N");
		}
		paramVo.setPageNo(pageNo);

		// user_id, dept_cd, company_cd
		paramVo.setUserId(loginUserInfo.getUserId());
		paramVo.setDeptCd(loginUserInfo.getDeptCode());
		paramVo.setCompanyCd(loginUserInfo.getCompanyCode());

		// common search param
		paramVo.setsDate(sDate);
		paramVo.seteDate(eDate);
	}

	private String makeHtmlForAttachFileList(List<ApprovalMgmtVo> attachFileList) {
		StringBuilder sb = new StringBuilder();

		List<List<ApprovalMgmtVo>> splitList = new ArrayList<List<ApprovalMgmtVo>>();

		List<ApprovalMgmtVo> list_A = new ArrayList<ApprovalMgmtVo>();
		List<ApprovalMgmtVo> list_B = new ArrayList<ApprovalMgmtVo>();
		List<ApprovalMgmtVo> list_C = new ArrayList<ApprovalMgmtVo>();
		List<ApprovalMgmtVo> list_D = new ArrayList<ApprovalMgmtVo>();
		List<ApprovalMgmtVo> list_E = new ArrayList<ApprovalMgmtVo>();
		List<ApprovalMgmtVo> list_F = new ArrayList<ApprovalMgmtVo>();

		for (int i = 0; i < attachFileList.size(); i++) {
			if ("A".equals(attachFileList.get(i).getApprovalAttachTpCd())) {
				list_A.add(attachFileList.get(i));
			} else if ("B".equals(attachFileList.get(i).getApprovalAttachTpCd())) {
				list_B.add(attachFileList.get(i));
			} else if ("C".equals(attachFileList.get(i).getApprovalAttachTpCd())) {
				list_C.add(attachFileList.get(i));
			} else if ("D".equals(attachFileList.get(i).getApprovalAttachTpCd())) {
				list_D.add(attachFileList.get(i));
			} else if ("E".equals(attachFileList.get(i).getApprovalAttachTpCd())) {
				list_E.add(attachFileList.get(i));
			} else if ("F".equals(attachFileList.get(i).getApprovalAttachTpCd())) {
				list_F.add(attachFileList.get(i));
			}
		}

		splitList.add(list_A);
		splitList.add(list_B);
		splitList.add(list_C);
		splitList.add(list_D);
		splitList.add(list_E);
		splitList.add(list_F);

		if (splitList != null && splitList.size() > 0) {
			for (int i = 0; i < splitList.size(); i++) {
				if (splitList.get(i) != null && splitList.size() > 0) {
					sb.append(makeHtmlForAttachFileRow(splitList.get(i)));
				}
			}
		}
		return sb.toString();
	}

	private String makeHtmlForAttachFileRow(List<ApprovalMgmtVo> list) {
		StringBuilder sb = new StringBuilder();

		if (list != null && list.size() > 0) {
			sb.append("<tr rowspan='" + list.size() + "'>");
			sb.append("<th>");
			sb.append(StringUtil.nvl(list.get(0).getApprovalAttachTpNm()));
			sb.append("</th>");
			sb.append("<td>");
			for (int i = 0; i < list.size(); i++) {
				sb.append("<a href='' value='/rest/downloadApi/downloadFile/" + StringUtil.nvl(list.get(i).getAttachId()) + "/" + StringUtil.nvl(list.get(i).getSeq()) + "'>");
				sb.append(StringUtil.nvl(list.get(i).getPfileNm()) + "." + StringUtil.nvl(list.get(i).getExt()));
				sb.append("</a><br/>");
			}
			sb.append("</td>");
			sb.append("</tr>");
		}

		return sb.toString();
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

	private String makeResoreMailContent(ApprovalMgmtVo obj) {
		StringBuilder sb = new StringBuilder();

		if (obj != null) {
			sb.append("<table width='1000' border='0' cellpadding='0' cellspacing='0' style='border:1px solid #000;font-size:12px;'>");
			sb.append("<tr>");
			sb.append("<td style='padding:20px;'>");
			sb.append("<table width='100%' border='0' cellpadding='0' cellspacing='0' style='font-size:12px;'>");
			sb.append("<tr>");
			sb.append("<td style='padding:20px 0;'>");
			sb.append("아래와 같이 법인카드 결재문서가 반려되었습니다.<br/>확인하여 처리하시길 바랍니다.");
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("<table width='100%' border='0' cellpadding='0' cellspacing='0' style='font-size:12px;text-align:center;'>");
			sb.append("<tr>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>사용일</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>부서</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>반려자</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>결재문서번호</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>계정구분</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>건수</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>총사용액</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>반려일</th>");
			sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>반려사유</th>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getRqDate()).substring(0, 10) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getRejApDeptNm()) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getRejApUserNm()) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getApprovalId()) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getAccountNm()) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getItemCount()) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + NumberFormat.getCurrencyInstance().format(Long.parseLong(StringUtil.nvl(obj.getRequestAmount()))) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + (StringUtil.nvl(obj.getRejDate()).substring(0, 4) + "-" + StringUtil.nvl(obj.getRejDate()).substring(4, 6) + "-" + StringUtil.nvl(obj.getRejDate()).substring(6, 8)) + "</td>");
			sb.append("<td style='border:1px solid #ddd;padding:5px 0;'>" + StringUtil.nvl(obj.getRejComment()) + "</td>");
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

	private Map<String, String> makeMap(String message) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", "F");
		map.put("msg", message);

		return map;
	}

	private boolean isAdmin() {
		boolean result = false;
		result = getLoginUserInfo().getAuthorities().toString().contains("R99");

		return result;
	}

	private LoginUserVO getLoginUserInfo() {
		return (LoginUserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}