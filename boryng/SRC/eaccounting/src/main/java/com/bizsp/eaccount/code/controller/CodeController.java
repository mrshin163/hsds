package com.bizsp.eaccount.code.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizsp.eaccount.code.service.CodeService;
import com.bizsp.eaccount.code.vo.CommCodeVo;
import com.bizsp.eaccount.code.vo.CommGrpCodeVo;

@Controller
public class CodeController {
	
	@Autowired
	CodeService codeService;
	
	/**
	 * 공통 코드 그룹 코드 리스트 화면
	 * @param commGrpCodeVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="admin/codeManage/commGrpCodeList")
	public String grpCodeListScrn(CommGrpCodeVo commGrpCodeVo, Model model){
/*		Map<String, Object> commGrpCodeMap;
		
		commGrpCodeMap = codeService.getCommGrpCodeList("all", 1);
		model.addAttribute("codeList", commGrpCodeMap.get("codeList"));
		model.addAttribute("totalRow", commGrpCodeMap.get("totalRow"));*/
		
		return "admin/codeManage/commGrpCodeList";
	}
	
	/**
	 * 공통 코드 리스트 화면 
	 * @param commCodeVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="admin/codeManage/commCodeList/{grpCodeId}")
	public String codeListScrn(Model model, @PathVariable("grpCodeId") String grpCodeId){
		CommGrpCodeVo commGrpCode = codeService.getCommGrpCode(grpCodeId);
		
		//Map<String, Object> commCodeList = codeService.getCommCodeList(grpCodeId, 1);
		
		model.addAttribute("commGrpCode", commGrpCode);
		//model.addAttribute("codeList", commCodeList.get("codeList"));
		//model.addAttribute("totalRow", commCodeList.get("totalRow"));
		
		return "admin/codeManage/commCodeList";
	}
	
}
