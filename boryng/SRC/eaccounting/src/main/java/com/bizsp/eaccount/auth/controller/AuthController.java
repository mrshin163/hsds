package com.bizsp.eaccount.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizsp.eaccount.auth.service.AuthService;

@Controller
public class AuthController {

	@Autowired
	AuthService authService;

	@RequestMapping(value = "admin/authManage/authReg")
	public String authManagerScrn(Model model) {

		Map<String, Object> resultMap = authService.getAuthList();

		model.addAttribute("authList", resultMap.get("authList"));
		model.addAttribute("totalRow", resultMap.get("totalRow"));

		return "admin/authManage/authReg";
	}

	@RequestMapping(value = "admin/authManage/userAuthReg")
	public String userAuthManageScrn(Model model) {

		Map<String, Object> resultMap = authService.getUserAuthList("all", 1);

		model.addAttribute("authList", resultMap.get("authList"));
		model.addAttribute("html", resultMap.get("html"));
		model.addAttribute("totalRow", resultMap.get("totalRow"));

		return "admin/authManage/userAuthReg";
	}

	@RequestMapping(value = "admin/authManage/menuAuthReg")
	public String menuAuthScrn(Model model) {
		Map<String, Object> menuAuthList = authService.getAuthList();

		// model.addAttribute("menuAuthList", menuAuthList.get("menuAuthList"));
		model.addAttribute("authList", menuAuthList.get("authList"));
		return "admin/authManage/menuAuthReg";
	}
}