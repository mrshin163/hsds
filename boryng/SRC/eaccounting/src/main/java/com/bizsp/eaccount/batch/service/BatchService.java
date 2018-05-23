package com.bizsp.eaccount.batch.service;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bizsp.eaccount.batch.dao.BatchDao;
import com.bizsp.eaccount.batch.vo.UnApprovedAQVo;
import com.bizsp.framework.util.lang.DateUtil;
import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.mail.MailUtil;
import com.bizsp.framework.util.system.PropUtil;

@Component
@Service
public class BatchService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BatchDao batchDao;

	final String subject	="법인카드 미상신 내역";
//	final String smtp_host	="mail.brnetcomm.co.kr";//"203.228.180.200";
//	final String from_name	="보령제약 재무팀";
//	final String from		="kimdh824@boryung.co.kr";

	public void sendEmailUnapprovedCardAq() {
		logger.info("미상신 카드승인내역 이메일 송신중....." );

		//-----------------------------------------------
		// 0. 미상신내역 데이터를 데이터베이스에서 조회한다.
		//-----------------------------------------------
		List<UnApprovedAQVo> mailList = batchDao.getUnapprovedCardAq();

		int cnt = 0;
		//-----------------------------------------------
		// 2. 사람별로 데이터 정리하여 발송
		//-----------------------------------------------
		String userId = "";
		String toUserEmailAddr = "";
		StringBuffer sb = null;
		Iterator<UnApprovedAQVo> iter = mailList.listIterator();
		while(iter.hasNext()) {
			UnApprovedAQVo data = iter.next();

			if (!toUserEmailAddr.equals(data.getToUser())) { // 다음 사용자
				
				// 개발로 테스트 할 때는 5명에게만 이메일 보내고 더이상 보내지 않는다.
				if ("DEV".equalsIgnoreCase(PropUtil.getValue("running.mode") ) && cnt++>4) 
					break;
				
				if(sb!=null) {
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
					;
					
					//----------------------------------
					sendEmail(toUserEmailAddr, sb.toString());
					//----------------------------------
					batchDao.insertEmailLog(userId, toUserEmailAddr, subject, sb.toString());
					//----------------------------------
				}
				
				sb = new StringBuffer();
				sb.append("<table width='1000' border='0' cellpadding='0' cellspacing='0' style='border:1px solid #000;font-size:12px;'>");
				sb.append("<tr>");
				sb.append("<td style='padding:20px;'>");
				sb.append("<table width='100%' border='0' cellpadding='0' cellspacing='0' style='font-size:12px;'>");
				sb.append("<tr>");
				sb.append("<td style='padding:20px 0;'>");
				sb.append("아래와 같이 법인카드 사용내역이 미상신되었습니다.<br/>결재상신하기시 바랍니다.");
				sb.append("</td>");
				sb.append("</tr>");
				sb.append("<tr>");
				sb.append("<td>");
				sb.append("<table width='100%' border='1' cellpadding='0' cellspacing='0' style='font-size:12px;text-align:center;'>");
				sb.append("<tr>");
				sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>카드번호</th>");
				sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>사용일</th>");
				sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>시간</th>");
//				sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>공급가액</th>");
//				sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>부가세</th>");
				sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>합계금액</th>");
				sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>승인번호</th>");
				sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>상호</th>");
				sb.append("<th style='border:1px solid #ddd;padding:5px 0;background:#e1e1e1;'>주소</th>");
				sb.append("</tr>");
				;
			}

			sb.append("<tr>")
				.append("<td>"+StringUtil.nvl(data.getCardNum())+"</td>")
				.append("<td>"+DateUtil.dateFormat(data.getAuthDate(),"yyyy-MM-dd")+"</td>")
				.append("<td>"+StringUtil.formatting(StringUtil.nvl(data.getAuthTime()),"0#:0#:0#")+"</td>")
//				.append("<td>"+data.getAmtAmount()+"</td>")
//				.append("<td>"+data.getVatAmount()+"</td>")
				.append("<td style='text-align:right;'>"+NumberFormat.getCurrencyInstance().format(Long.parseLong(StringUtil.nvl(data.getRequestAmount())))+"</td>")
				.append("<td>"+StringUtil.nvl(data.getAuthNum())+"</td>")
				.append("<td style='text-align:left;'>"+StringUtil.nvl(data.getMercName())+"</td>")
				.append("<td style='text-align:left;'>"+StringUtil.nvl(data.getMercAddr())+"</td>")
			.append("</tr>");
			

			userId 			= data.getOwnerId();
			toUserEmailAddr = data.getToUser();
		} // end of while

		if(sb!=null) {
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
			;
			
			//----------------------------------
			sendEmail(toUserEmailAddr, sb.toString());
			//----------------------------------
			batchDao.insertEmailLog(userId, toUserEmailAddr, subject, sb.toString());
			//----------------------------------
		}
		
		logger.info("미상신 카드승인내역 "+cnt--+"명에게 이메일 송신 완료.");
	}

	/**
	 * 
	 */
	private void sendEmail(String toUserEmailAddr, String content) {
		//====================================================================================================
		//=== 메일보내기(자바메일사용) START ===
		//====================================================================================================
		String recipients	= "";
		if ("DEV".equalsIgnoreCase(PropUtil.getValue("running.mode") ) ) {
			recipients	= PropUtil.getValue("test_email_recipient_address") ;
		}else if ("REAL".equalsIgnoreCase(PropUtil.getValue("running.mode") ) ) {
			recipients= toUserEmailAddr;
		}
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("recipients", 	recipients); 	// 받는사람이메일

		if ("DEV".equalsIgnoreCase(PropUtil.getValue("running.mode") ) ) {
			hashMap.put("subject", 		toUserEmailAddr+":::"+subject); 		// 제목
		}else if ("REAL".equalsIgnoreCase(PropUtil.getValue("running.mode") ) ) {
			hashMap.put("subject", 		subject); 		// 제목
		}

		hashMap.put("content", 		content); 		// 내용 
		
		MailUtil.runSendMailThread(hashMap);
		
		//====================================================================================================
		//=== 메일보내기(자바메일사용) END ===
		//====================================================================================================		
	}
}
