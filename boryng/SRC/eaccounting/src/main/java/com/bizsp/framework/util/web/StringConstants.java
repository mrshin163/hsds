package com.bizsp.framework.util.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringConstants {

	private static Logger logger = LoggerFactory.getLogger(StringConstants.class);

	public static final String APPROVAL_STATUS_CD_T = "T";
	public static final String APPROVAL_STATUS_CD_T_STR = "임시저장";
	public static final String APPROVAL_STATUS_CD_NREQ = "NREQ";
	public static final String APPROVAL_STATUS_CD_NREQ_STR = "상신중";
	public static final String APPROVAL_STATUS_CD_NING = "NING";
	public static final String APPROVAL_STATUS_CD_NING_STR = "결재중";
	public static final String APPROVAL_STATUS_CD_NAPR = "NAPR";
	public static final String APPROVAL_STATUS_CD_NAPR_STR = "결재완료";
	public static final String APPROVAL_STATUS_CD_EAPR = "EAPR";
	public static final String APPROVAL_STATUS_CD_EAPR_STR = "전표완료";
	public static final String APPROVAL_STATUS_CD_NREJ = "NREJ";
	public static final String APPROVAL_STATUS_CD_NREJ_STR = "반려";
	public static final String APPROVAL_STATUS_CD_NCNL = "NCNL";
	public static final String APPROVAL_STATUS_CD_NCNL_STR = "상신취소";
	public static final String APPROVAL_STATUS_CD_SR = "SR";
	public static final String APPROVAL_STATUS_CD_SR_STR = "결재요청";
	public static final String APPROVAL_STATUS_CD_SING = "SING";
	public static final String APPROVAL_STATUS_CD_SING_STR = "결재요청처리중";
	public static final String APPROVAL_STATUS_CD_SAPR = "SAPR";
	public static final String APPROVAL_STATUS_CD_SAPR_STR = "결재요청처리완료";
	public static final String APPROVAL_STATUS_CD_SREJ = "SREJ";
	public static final String APPROVAL_STATUS_CD_SREJ_STR = "결재요청반려";

	public static final String APPROVAL_STATUS_CD_AAAA = "AAAA";
	public static final String APPROVAL_STATUS_CD_AAAA_STR = "미상신";
	public static final String APPROVAL_STATUS_CD_ZZZZ = "ZZZZ";
	public static final String APPROVAL_STATUS_CD_ZZZZ_STR = "상신제외";
	
	public static final String APPROVAL_CD_APR = "APR";
	public static final String APPROVAL_CD_APR_STR = "승인";
	public static final String APPROVAL_CD_REJ = "REJ";
	public static final String APPROVAL_CD_REJ_STR = "반려";
	
	public static final Map<String, Object> APPROVAL_CD_MAP = new HashMap<String, Object>();
	
	static{
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_T, 		APPROVAL_STATUS_CD_T_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_NREQ, 	APPROVAL_STATUS_CD_NREQ_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_NING, 	APPROVAL_STATUS_CD_NING_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_NAPR,	APPROVAL_STATUS_CD_NAPR_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_EAPR, 	APPROVAL_STATUS_CD_EAPR_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_NREJ, 	APPROVAL_STATUS_CD_NREJ_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_NCNL, 	APPROVAL_STATUS_CD_NCNL_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_SR, 		APPROVAL_STATUS_CD_SR_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_SING, 	APPROVAL_STATUS_CD_SING_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_SAPR, 	APPROVAL_STATUS_CD_SAPR_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_SREJ, 	APPROVAL_STATUS_CD_SREJ_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_AAAA, 	APPROVAL_STATUS_CD_AAAA_STR);
		APPROVAL_CD_MAP.put(APPROVAL_STATUS_CD_ZZZZ, 	APPROVAL_STATUS_CD_ZZZZ_STR);
		APPROVAL_CD_MAP.put(APPROVAL_CD_APR, 			APPROVAL_CD_APR_STR);
		APPROVAL_CD_MAP.put(APPROVAL_CD_REJ, 			APPROVAL_CD_REJ_STR);
	}

}