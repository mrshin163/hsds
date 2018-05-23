package com.bizsp.eaccount.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizsp.eaccount.cache.CacheService;
import com.bizsp.eaccount.user.service.UserService;
import com.bizsp.eaccount.user.vo.UserVo;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CacheService cacheService;
	
	@RequestMapping(value="admin/userManage/userList")
	public String getUserList(@ModelAttribute("userVo")UserVo userVo, Model model) throws Exception{
		
		/*int interval = 1000;
		String userId = "nuax";
		
		List<UserVo> dbResult = (List<UserVo>)cacheService.getCache(userService, "getUserList", "select", interval, "all");
		
		int result = 0;
		
		if(dbResult != null) {
			result = dbResult.size();
		}
		
		System.out.println("result::::::::::::::::::" + result);*/
		
		Map<String, Object> userMap = userService.getUserList("all", "all", 1);
		model.addAttribute("userList", userMap.get("userList"));
		model.addAttribute("totalRow", userMap.get("totalRow"));
		
		return "admin/userManage/userList";
	}
	
}
