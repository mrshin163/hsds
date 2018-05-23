package com.bizsp.eaccount.card.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CardController {
	
	//@Autowired
	//CardService cardService;
	
	@RequestMapping("admin/cardManage/cardManage")
	public String cardManage(Model model){
/*		try {
			Map<String, Object> map = cardService.getCardAQList("1", "all", "all", "all", "all", 1);
			model.addAttribute("cardList", map.get("cardList"));
			model.addAttribute("totalRow", map.get("totalRow"));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return "admin/cardManage/cardManage";
	}
	
	@RequestMapping("admin/cardManage/cardPeriodManage")
	public String cardManageTerm(Model model){
/*		try {
			Map<String, Object> map = cardService.getCardAQList("1", "all", "all", "all", "all", 1);
			model.addAttribute("cardList", map.get("cardList"));
			model.addAttribute("totalRow", map.get("totalRow"));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return "admin/cardManage/cardPeriodManage";
	}
}
