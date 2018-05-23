package com.bizsp.eaccount.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizsp.eaccount.admin.service.InterfaceService;

@Controller
public class InterfaceController {
	
	@Autowired
	InterfaceService interfaceService;
	
	@RequestMapping(value="admin/interfaceManage/interfaceList")
	public String getInterfaceList(Model model){
		return "admin/interfaceManage/interfaceList";
	}
	
}
