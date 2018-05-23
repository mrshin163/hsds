package com.bizsp.eaccount.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeController {

	@RequestMapping("notice/notice")
	public String notice(Model model){
		return "notice/notice";
	}
}
