package com.bizsp.eaccount.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizsp.eaccount.admin.service.LogService;
import com.bizsp.eaccount.admin.vo.LogVo;


@Controller
public class LoginController {
	@Autowired
	LogService logService;
	
	
	@RequestMapping(value="login.biz")
	public String LoginScrn(){    	
		return "login";
	}
	
/*	@RequestMapping(value="logout.biz")
	public String LogoutScrn(Model model){
		LogVo logVo = new LogVo();
        logVo.setMENU_NAME("LOGOUT");
    	logService.insertLog(logVo);
		return "logoutt.biz";
	}*/

}
