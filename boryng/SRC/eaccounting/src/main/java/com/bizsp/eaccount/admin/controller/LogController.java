package com.bizsp.eaccount.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogController {

	@RequestMapping("admin/log/logList")
	public String notice(Model model){
		return "admin/log/logList";
	}
}
