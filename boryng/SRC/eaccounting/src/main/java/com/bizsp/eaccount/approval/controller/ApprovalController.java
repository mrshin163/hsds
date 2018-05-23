package com.bizsp.eaccount.approval.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizsp.eaccount.approval.service.ApprovalService;

@Controller
public class ApprovalController {

	@Autowired
	ApprovalService approvalService;

	@RequestMapping("approval/approvalList")
	public String approvalList(Model model) {

		Map<String, Object> resultMap = approvalService.getApprovalList(getDate("S"), getDate("C"), "all", "all", "all", "all", "all", 1);
		setModel(model, resultMap);

		return "approval/approvalList";
	}

	@RequestMapping("approval/approvalProgressList")
	public String approvalProgressList(Model model) {

		Map<String, Object> resultMap = approvalService.getApprovalProgressList(getDate("S"), getDate("C"), 1);
		setModel(model, resultMap);

		return "approval/approvalProgressList";
	}

	@RequestMapping("approval/approvalCompleteList")
	public String approvalCompleteList(Model model) {

		Map<String, Object> resultMap = approvalService.getApprovalCompleteList(getDate("S"), getDate("C"), "all", 1);
		setModel(model, resultMap);

		return "approval/approvalCompleteList";
	}

	@RequestMapping("approval/progressList")
	public String progressList(Model model) {

		Map<String, Object> resultMap = approvalService.getProgressList(getDate("S"), getDate("C"), 1);
		setModel(model, resultMap);

		return "approval/progressList";
	}

	@RequestMapping("approval/completeList")
	public String completeList(Model model) {

		Map<String, Object> resultMap = approvalService.getCompleteList(getDate("S"), getDate("C"), 1);
		setModel(model, resultMap);

		return "approval/completeList";
	}

	@RequestMapping("approval/restoreList")
	public String restoreList(Model model) {

		Map<String, Object> resultMap = approvalService.getRestoreList(getDate("S"), getDate("C"), 1);
		setModel(model, resultMap);

		return "approval/restoreList";
	}

	private String getDate(String flag) {
		String result = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		if ("S".equals(flag)) {
			result = sdf.format(date).substring(0, 8) + "01";
		} else if ("C".equals(flag)) {
			result = sdf.format(date);
		}

		return result;
	}

	private void setModel(Model model, Map<String, Object> resultMap) {
		model.addAttribute("sDate", getDate("S"));
		model.addAttribute("eDate", getDate("C"));
		for (String key : resultMap.keySet()) {
			model.addAttribute(key, resultMap.get(key));
		}
	}
}