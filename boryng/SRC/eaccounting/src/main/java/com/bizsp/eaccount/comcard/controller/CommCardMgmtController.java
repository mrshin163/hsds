package com.bizsp.eaccount.comcard.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizsp.eaccount.comcard.service.CommCardMgmtService;
import com.bizsp.eaccount.comcard.vo.CommCardMgmtVo;
import com.bizsp.framework.util.lang.DateUtil;
import com.bizsp.framework.util.security.SecurityUtils;

@Controller
public class CommCardMgmtController {
	private SecurityUtils secu;
	@Autowired
	CommCardMgmtService commCardMgmtService;
	
	@RequestMapping(value="comcard/commCardMgmtList")
	public String getInterfaceList(Model model){
		/*CommCardMgmtVo commCardMgmtVo = new CommCardMgmtVo();
		
		String yearMonth = "";
		String year = Integer.toString(DateUtil.getCurrentYear());
		String month = DateUtil.getCurrentMonth() < 10 ?
				"0"+Integer.toString(DateUtil.getCurrentMonth()) : Integer.toString(DateUtil.getCurrentMonth());
		
		yearMonth = year + month;
		
		if(commCardMgmtVo.getAuthDate()==null || commCardMgmtVo.getAuthDate().isEmpty()){
			commCardMgmtVo.setAuthDate(yearMonth);
		}
		commCardMgmtVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		commCardMgmtVo.setCardMgrId(secu.getAuthenticatedUser().getUserId());		
		commCardMgmtVo.setPagingYn("Y");
		
		Map<String, Object> map = commCardMgmtService.getCardAQList(commCardMgmtVo);
		
		model.addAttribute("commCardList", map.get("commCardList"));
		model.addAttribute("totalRow", map.get("totalRow"));
		model.addAttribute("totalAmount", map.get("totalAmount"));
		model.addAttribute("year", year);
		model.addAttribute("month", month);*/
		return "comcard/commCardMgmtList";
	}
	
}
