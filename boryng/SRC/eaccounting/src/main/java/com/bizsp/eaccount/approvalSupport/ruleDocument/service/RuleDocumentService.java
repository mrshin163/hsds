package com.bizsp.eaccount.approvalSupport.ruleDocument.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bizsp.eaccount.approvalSupport.ruleDocument.dao.RuleDocumentDao;
import com.bizsp.eaccount.approvalSupport.ruleDocument.vo.RuleDocumentVo;
import com.bizsp.framework.util.exception.ServiceException;
import com.bizsp.framework.util.lang.DateUtil;
import com.bizsp.framework.util.security.SecurityUtils;
import com.bizsp.framework.util.web.HtmlUtil;

@Path("/rest/ruleDocumentApi")
@Component
public class RuleDocumentService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@SuppressWarnings("unused")
	private static SecurityUtils securityUtils;
	@Autowired
	RuleDocumentDao ruleDocumentDao;

	/**
	 * 공정거래 규약 문서 리스트 조회 담당자 사번과 현재 달을 기준으로 조회한다
	 * 
	 * @return
	 */
	@GET
	@Path("ruleDocumentList/search/{sabun}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RuleDocumentVo> getRuleDocumentList(@PathParam("sabun") String sabun) {

		String userId;

		RuleDocumentVo ruleDocumentVo = new RuleDocumentVo();
		String currentMonth = DateUtil.getToday("yyyyMM");
		if (sabun.equals("0")) {
			userId = securityUtils.getAuthenticatedUser().getUserId();
		} else {
			userId = sabun;
		}

		ruleDocumentVo.setSabun(userId);
		ruleDocumentVo.setMonth(currentMonth);
		return ruleDocumentDao.getRuleDocumentList(ruleDocumentVo);
	}

	@GET
	@Path("ruleDocument/{munseoNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ruleDocument(@PathParam("munseoNo") String munseoNo) throws Exception {

		logger.debug(":::::::::: ruleDocument 공정경쟁규약 조회 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (munseoNo != null && !"".equals(munseoNo)) {
			RuleDocumentVo paramVo = new RuleDocumentVo();
			paramVo.setMunseoNo(munseoNo);

			RuleDocumentVo ruleDocumentVo = ruleDocumentDao.getRuleDocument(paramVo);

			if (ruleDocumentVo != null) {
				List<RuleDocumentVo> list = new ArrayList<RuleDocumentVo>();
				list.add(ruleDocumentVo);

				resultMap.put("ruleDocumentVo", HtmlUtil.makeHtmlForList(list, HtmlUtil.RULE_DOCUMENT_NAME, HtmlUtil.RULE_DOCUMENT_TYPE));
				resultMap.put("code", "S");
			} else {
				throw new ServiceException(makeMap("공정경쟁규약 정보가 존재하지 않습니다."));
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return Response.status(200).entity(resultMap).build();
	}

	private Map<String, String> makeMap(String message) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", "F");
		map.put("msg", message);

		return map;
	}
}