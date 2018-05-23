package com.bizsp.eaccount.approvalRq.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bizsp.eaccount.approvalRq.service.ApprovalRqService;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqVo;
import com.bizsp.eaccount.code.service.CodeService;
import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.security.SecurityUtils;

@Controller
public class ApprovalRqController {

	@Autowired
	ApprovalRqService approvalRqService;
	CodeService codeService;

	private SecurityUtils secu;

	/**
	 * 결재상신 리스트 화면
	 * 
	 * @return
	 */
	@RequestMapping("approvalRq/approvalRqList")
	public String approvalRqList(ApprovalRqVo approvalRqVo, Model model) {
		SimpleDateFormat today = new SimpleDateFormat("yyyyMM");
		String curDate = StringUtil.toString(today);
		
		Map<String, Object> approvalRqMap = approvalRqService.getApprovalUpList(curDate, "1", "1", 1, "all");

		model.addAttribute("approvalRqList", approvalRqMap.get("approvalRqList"));
		model.addAttribute("amountTotal", approvalRqMap.get("amountTotal"));
		model.addAttribute("totalRow", approvalRqMap.get("totalRow"));

//		2015-03-24
//		디버그용 System.out.println 주석 처리
//		System.out.println("titleCode :::::::::::::::::::::::::" + secu.getAuthenticatedUser().getTitleCode());
//		System.out.println("titleName :::::::::::::::::::::::::" + secu.getAuthenticatedUser().getTitleName());
//		System.out.println("dutyCode :::::::::::::::::::::::::" + secu.getAuthenticatedUser().getDutyCode());
//		System.out.println("dutyName :::::::::::::::::::::::::" + secu.getAuthenticatedUser().getDutyName());

		return "approvalRq/approvalRqList";

	}

	/**
	 * 결재 품의서 작성 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "approvalRq/approvalRqItem/{approvalId}/{oldApprovalId}", method = RequestMethod.GET)
	public String approvalRq(@PathVariable("approvalId") String approvalId, @PathVariable("oldApprovalId") String oldApprovalId, Model model) {
		Map<String, Object> approvalItem = approvalRqService.getApprovalItem(approvalId);
		model.addAttribute("approvalItem", approvalItem.get("approvalRqItemList"));
		model.addAttribute("amountTotal", approvalItem.get("amountTotal"));
		model.addAttribute("accountCode", approvalItem.get("accountCode"));
		model.addAttribute("accountName", approvalItem.get("accountName"));
		model.addAttribute("budgetDeptCode", approvalItem.get("budgetDeptCode"));
		model.addAttribute("budgetDeptName", approvalItem.get("budgetDeptName"));
		model.addAttribute("ftrCode", approvalItem.get("ftrCode"));
		model.addAttribute("loginDeptCode", approvalItem.get("loginDeptCode"));
		model.addAttribute("loginDeptName", approvalItem.get("loginDeptName"));
		model.addAttribute("approvalId", approvalId);
		model.addAttribute("oldApprovalId", oldApprovalId);
		model.addAttribute("salesYn", approvalItem.get("salesYn"));

		return "approvalRq/approvalRqItem";
	}

	/**
	 * 결재 심포지움 품의서 작성 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "approvalRq/approvalSymposiumRqItem/{approvalId}", method = RequestMethod.GET)
	public String approvalSymposiumRqItemRq(@PathVariable("approvalId") String approvalId, Model model) {
		Map<String, Object> approvalItem = approvalRqService.getApprovalItem(approvalId);
		model.addAttribute("approvalItem", approvalItem.get("approvalRqItemList"));
		model.addAttribute("amountTotal", approvalItem.get("amountTotal"));
		model.addAttribute("accountCode", "2");
		model.addAttribute("approvalId", approvalId);
		model.addAttribute("loginDeptCode", approvalItem.get("loginDeptCode"));
		model.addAttribute("loginDeptName", approvalItem.get("loginDeptName"));

		return "approvalRq/approvalSymposiumRqItem";
	}

	/**
	 * 결재 품의서 작성 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "approvalRq/approvalRqSymposium/{approvalId}", method = RequestMethod.GET)
	public String approvalRq(@PathVariable("approvalId") String approvalId, @RequestParam(value = "syposiumList", required = true) List<String> syposiumList, Model model) {
		Map<String, Object> approvalItem = approvalRqService.getApprovalItem(approvalId);
		model.addAttribute("approvalItem", approvalItem.get("approvalRqItemList"));
		model.addAttribute("amountTotal", approvalItem.get("amountTotal"));
		model.addAttribute("accountCode", approvalItem.get("accountCode"));
		model.addAttribute("approvalId", approvalId);
		return "approvalRq/approvalRqItem";
	}

	/**
	 * 심포지움 결재 요청 리스트 화면
	 * 
	 * @return
	 */
	@RequestMapping("approvalRq/symposiumList")
	public String symposiumList() {
		return "approvalRq/symposiumList";
	}

	/**
	 * 심포지움 품의서 작성 화면
	 * 
	 * @return
	 */
	@RequestMapping("approvalRq/symposiumRq")
	public String symposiumWrite() {
		return "approvalRq/symposiumRq";
	}

	@RequestMapping("approvalRq/test")
	public String test() {
		return "approvalRq/test";
	}

	@RequestMapping("approvalRq/cancelSymposiumList")
	public String cancelSymposiumList(Model model) {

		return "approvalRq/cancelSymposiumList";
	}
}