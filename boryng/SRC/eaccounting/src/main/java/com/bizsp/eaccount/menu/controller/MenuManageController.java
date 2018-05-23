package com.bizsp.eaccount.menu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuManageController {
	
	/**
	 * 메뉴 관리 화면 
	 * @return
	 */
	@RequestMapping("admin/menuManage/menuManage")
	public String menuManage(){
		return "admin/menuManage/menuManage";
	}
}
