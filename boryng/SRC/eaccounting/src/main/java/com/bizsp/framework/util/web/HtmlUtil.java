package com.bizsp.framework.util.web;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bizsp.eaccount.approval.vo.ApprovalItemVo;
import com.bizsp.eaccount.approval.vo.ApprovalLineVo;
import com.bizsp.framework.util.lang.StringUtil;

public class HtmlUtil {

	private static Logger logger = LoggerFactory.getLogger(HtmlUtil.class);

	/*
	 * 작성 예제
	 * XXX_NAME 는 화면에 노출 될 순서에 맞춰 Vo 필드명을 작성한다.
	 * XXX_TYPE 은 화면에 노출 될 순서에 맞춰 해당 컬럼의 스타일을 작성한다.
	 * ex) S = 문자, 가운데 정렬 / N = 숫자, 가운데 정렬 / C = 체크박스
	 * W = 통화, 우측 정렬 / A = anchor / H = 히든 / D = 일자 (yyyy-MM-dd) 10자리 / P = 팝업 /
	 * DATE = 년월일 / TIME = 시분초 / CODE = 카드사용내역 세부계정 / PROD = 제품코드,명
	 */

	public static final String[] APPROVAL_NAME = { "approvalId", "seq", "flag", "authDate", "rqDeptNm", "rqUserNm", "approvalId", "accountNm", "account2Nm", "itemCount", "rqDate", "mercNm", "requestAmount", "productNm" };
	public static final String[] APPROVAL_TYPE = { "C", "H", "H", "DATE", "S", "S", "A", "S", "S", "N", "D", "S", "W", "S" };

	public static final String[] APPROVAL_PROGRESS_NAME = { "authDate", "rqDeptNm", "rqUserNm", "approvalId", "accountNm", "itemCount", "requestAmount", "approvalStatusNm" };
	public static final String[] APPROVAL_PROGRESS_TYPE = { "DATE", "S", "S", "A", "S", "N", "W", "S" };

	public static final String[] APPROVAL_COMPLETE_NAME = { "authDate", "aprDate", "rqDeptNm", "rqUserNm", "approvalId", "accountNm", "itemCount", "requestAmount", "approvalStatusNm" };
	public static final String[] APPROVAL_COMPLETE_TYPE = { "DATE", "D", "S", "S", "A", "S", "N", "W", "S" };

	public static final String[] PROGRESS_NAME = { "authDate", "apDeptNm", "apUserNm", "approvalId", "accountNm", "itemCount", "requestAmount" };
	public static final String[] PROGRESS_TYPE = { "DATE", "S", "S", "A", "S", "N", "W" };

	public static final String[] COMPLETE_NAME = { "authDate", "lastApDeptNm", "lastApUserNm", "approvalId", "accountNm", "itemCount", "requestAmount", "aprDate" };
	public static final String[] COMPLETE_TYPE = { "DATE", "S", "S", "A", "S", "N", "W", "D" };

	public static final String[] RESTORE_NAME = { "authDate", "rejApDeptNm", "rejApUserNm", "approvalId", "accountNm", "itemCount", "requestAmount", "rejDate", "rejComment" };
	public static final String[] RESTORE_TYPE = { "DATE", "S", "S", "A", "S", "N", "W", "D", "S" };

	public static final String[] CALCULATE_ITEM_NAME = { "authDate", "authTime", "mercName", "requestAmount", "userName", "account2Nm", "accountSpNm", "productNm", "customerNm", "details", "seminarReport", "ftrCd", "mercAddr", "mccName", "attachId", "meetReport", "seq" };
	public static final String[] CALCULATE_ITEM_TYPE = { "DATE", "TIME", "S", "W", "S", "CODE", "S", "PROD", "S", "S", "P", "P", "S", "S", "P", "P", "H" };

	public static final String[] RULE_DOCUMENT_NAME = { "munseoNo", "munseoDate", "georaeName", "pummok", "jangso", "amount", "damdangName" };
	public static final String[] RULE_DOCUMENT_TYPE = { "S", "DATE", "S", "S", "S", "W", "S" };

	public static final String[] USER_AUTH_NAME = { "userId", "userId", "userNm", "titleNm", "dutyNm", "deptNm", "authNm" };
	public static final String[] USER_AUTH_TYPE = { "C", "S", "S", "S", "S", "S", "S" };

	public static final String[] CANCEL_SYMPOSIUM_NAME = { "approvalId", "authDate", "mercName", "requestAmount", "userNm", "budgetDeptNm", "account2Nm", "productNm", "customerNm", "details" };
	public static final String[] CANCEL_SYMPOSIUM_TYPE = { "C", "DATE", "S", "W", "S", "S", "S", "S", "S", "S" };

	public static final String TYPE_S = "S";
	public static final String TYPE_N = "N";
	public static final String TYPE_C = "C";
	public static final String TYPE_W = "W";
	public static final String TYPE_A = "A";
	public static final String TYPE_H = "H";
	public static final String TYPE_D = "D";
	public static final String TYPE_P = "P";
	public static final String TYPE_DATE = "DATE";
	public static final String TYPE_TIME = "TIME";
	public static final String TYPE_CODE = "CODE";
	public static final String TYPE_PROD = "PROD";

	public static String makeHtmlForList(List<?> voList, String[] methodNames, String[] methodTypes) {
		StringBuffer sb = new StringBuffer();

		if (voList != null && voList.size() > 0) {
			for (Object obj : voList) {
				sb.append("<tr>");
				for (int i = 0; i < methodNames.length; i++) {
					sb.append(convertTypeForList(methodTypes[i], methodNames[i], invoke(obj, methodNames[i], null), obj));
				}
				sb.append("</tr>");
			}
		} else {
			sb.append("<tr>");
			sb.append("<td colspan='" + methodNames.length + "'>");
			sb.append("조회된 데이터가 없습니다.");
			sb.append("</td>");
			sb.append("</tr>");
		}

		logger.debug(sb.toString());
		return sb.toString();
	}

	private static String convertTypeForList(String type, String key, Object val, Object obj) {
		String result = "";

		String convertStr = StringUtil.nvl(val);

		if (TYPE_S.equals(type)) {
			result = "<td>" + convertStr + "</td>";
		} else if (TYPE_N.equals(type)) {
			result = "<td>" + convertStr + "</td>";
		} else if (TYPE_C.equals(type)) {
			result = "<td><input type='checkbox' name='" + StringUtil.nvl(key) + "' value='" + convertStr + "' /></td>";
		} else if (TYPE_W.equals(type)) {
			String data = "";
			try {
				Long.parseLong(convertStr);
				data = convertStr;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			if (!"".equals(data)) {
				result = "<td style='text-align: right; padding-right: 10px;'>" + NumberFormat.getCurrencyInstance().format(Long.parseLong(data)) + "</td>";
			} else {
				result = "<td style='text-align: right; padding-right: 10px;'></td>";
			}
		} else if (TYPE_A.equals(type)) {
			result = "<td><a href='#'>" + convertStr + "</a></td>";
		} else if (TYPE_H.equals(type)) {
			result = "<td style='display: none;'><input type='hidden' name='" + StringUtil.nvl(key) + "' value='" + convertStr + "' /></td>";
		} else if (TYPE_D.equals(type)) {
			if (convertStr.length() == 21) {
				result = "<td>" + convertStr.substring(0, 10) + "</td>";
			} else if (convertStr.length() == 14) {
				result = "<td>" + (convertStr.substring(0, 4) + "-" + convertStr.substring(4, 6) + "-" + convertStr.substring(6, 8)) + "</td>";
			} else {
				result = "<td>&nbsp;</td>";
			}
		} else if (TYPE_P.equals(type)) {
			if (!"".equals(convertStr)) {
				if ("seminarReport".equals(key)) {
					result = "<td><a href='#'><i class='iconDocument' name='" + key + "' value='" + convertStr.replace("'", "\'") + "'>돋보기</i></a></td>";
				} else if ("ftrCd".equals(key)) {
					result = "<td>" + convertStr + "&nbsp;<a href='#' class='btnView'><i class='icon iconView' name='" + key + "' value='" + convertStr + "'>작성문서</i></a></td>";
				} else if ("attachId".equals(key)) {
					if ("Y".equals(convertStr)) {
						ApprovalItemVo approvalItemVo = (ApprovalItemVo) obj;
						result = "<td><a href='#'><i class='iconFile add' name='" + key + "' value='" + StringUtil.nvl(approvalItemVo.getApprovalId()) + "|" + StringUtil.nvl(approvalItemVo.getSeq()) + "'>파일</i></a></td>";
					} else {
						result = "<td>&nbsp;</td>";
					}
				} else if ("meetReport".equals(key)) {
					result = "<td><a href=\"#\"><i class=\"iconDocument\" name=\"" + key + "\" value=\"" + convertStr.replace("'", "\'") + "\">돋보기</i></a></td>";
				}
			} else {
				result = "<td>&nbsp;</td>";
			}
		} else if (TYPE_DATE.equals(type)) {
			String data = "";
			if (!"".equals(convertStr) && convertStr.length() == 8) {
				data = convertStr.substring(0, 4) + "-" + convertStr.substring(4, 6) + "-" + convertStr.substring(6, 8);
			} else {
				data = "&nbsp;";
			}
			result = "<td>" + data + "</td>";
		} else if (TYPE_TIME.equals(type)) {
			String data = "";
			if (!"".equals(convertStr) && convertStr.length() == 6) {
				data = convertStr.substring(0, 2) + ":" + convertStr.substring(2, 4) + ":" + convertStr.substring(4, 6);
			} else {
				data = "&nbsp;";
			}
			result = "<td>" + data + "</td>";
		} else if (TYPE_CODE.equals(type)) {
			ApprovalItemVo approvalItemVo = (ApprovalItemVo) obj;
			result = "<td code='" + StringUtil.nvl(approvalItemVo.getAccount2Cd()) + "' codeName='" + StringUtil.nvl(approvalItemVo.getAccount2Nm()) + "'>" + convertStr + "</td>";
		} else if (TYPE_PROD.equals(type)) {
			ApprovalItemVo approvalItemVo = (ApprovalItemVo) obj;
			if (!"".equals(convertStr)) {
				result = "<td>(" + StringUtil.nvl(approvalItemVo.getProductCd()) + ") " + convertStr + "</td>";
			} else {
				result = "<td>&nbsp;</td>";
			}
		}

		return result;
	}

	public static String makeHtmlForApprovalLineList(List<ApprovalLineVo> approvalLineVoList) {
		StringBuffer sb = new StringBuffer();

		if (approvalLineVoList != null && approvalLineVoList.size() > 0) {
			sb.append("<colgroup>");
			for (int i = 0; i < (approvalLineVoList.size() + 1); i++) {
				sb.append("<col width='' />");
			}
			sb.append("</colgroup>");

			sb.append("<tbody>");

			sb.append("<tr>");
			sb.append("<th width='10px' rowspan='6'>결<br>재<br>정<br>보</th>");
			sb.append("<th>");
			sb.append("결재선");
			sb.append("</th>");
			for (int i = 0; i < approvalLineVoList.size(); i++) {
				// 결재라인
				if (i > 0) {
					sb.append("<th>");
					sb.append(i + "차 결재자");
					sb.append("</th>");
				} else {
					sb.append("<th>상신자</th>");
				}
			}
			sb.append("</tr>");

			sb.append("<td style='background:#ebebeb;'>");
			sb.append("부서");
			sb.append("</td>");

			for (int i = 0; i < approvalLineVoList.size(); i++) {
				// 부서명
				sb.append("<td>");
				sb.append(StringUtil.nvl(approvalLineVoList.get(i).getApDeptNm()));
				sb.append("</td>");
			}
			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td style='background:#ebebeb;'>");
			sb.append("결재자");
			sb.append("</td>");

			for (int i = 0; i < approvalLineVoList.size(); i++) {
				// 결재자명 + 직책명
				sb.append("<td>");
				sb.append(StringUtil.nvl(approvalLineVoList.get(i).getApUserNm()));
				sb.append("&nbsp;");
				sb.append(StringUtil.nvl(approvalLineVoList.get(i).getApDutyNm()));
				sb.append("</td>");
			}
			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td style='background:#ebebeb;'>");
			sb.append("결재상태");
			sb.append("</td>");
			for (int i = 0; i < approvalLineVoList.size(); i++) {
				// 결재상태
				sb.append("<td>");
				sb.append(StringUtil.nvl(approvalLineVoList.get(i).getAprCd()));
				sb.append("</td>");
			}
			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td style='background:#ebebeb;'>");
			sb.append("결재일");
			sb.append("</td>");
			for (int i = 0; i < approvalLineVoList.size(); i++) {
				// 결재일
				sb.append("<td>");
				String data = "";
				String tmp = StringUtil.nvl(approvalLineVoList.get(i).getAprDate());
				if (!"".equals(tmp)) {
					data = tmp.substring(0, 4) + "-" + tmp.substring(4, 6) + "-" + tmp.substring(6, 8);
				} else {
					data = "&nbsp;";
				}
				sb.append(data);
				sb.append("</td>");
			}
			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td style='background:#ebebeb;'>");
			sb.append("의견");
			sb.append("</td>");
			for (int i = 0; i < approvalLineVoList.size(); i++) {
				// 결재의견
				sb.append("<td>");
				sb.append("<div class='toolTip'>");
				sb.append("<span class='toolTipBtns'>");
				sb.append(StringUtil.nvl(approvalLineVoList.get(i).getAprComment()));
				sb.append("</span>");
				sb.append("<div class='toolTipTxt'>");
				sb.append("<div class='toolTipInner'>");
				sb.append(StringUtil.nvl(approvalLineVoList.get(i).getAprComment()));
				sb.append("</div>");
				sb.append("</div>");
				sb.append("</div>");
				sb.append("</td>");
			}
			sb.append("</tr>");

			sb.append("</tbody>");
		}

		logger.debug(sb.toString());
		return sb.toString();
	}

	private static Object invoke(Object obj, String methodName, Object[] objList) {
		Object result = null;

		Method[] methods = obj.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals("get" + StringUtils.capitalize(methodName))) {
				try {
					Object tmpObj = methods[i].invoke(obj, objList);
					if (tmpObj != null) {
						if (tmpObj instanceof String) {
							result = (String) tmpObj;
						} else if (tmpObj instanceof Integer) {
							result = tmpObj;
						} else if (tmpObj instanceof Long) {
							result = tmpObj;
						} else {
							result = "";
						}
					} else {
						result = "";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
}