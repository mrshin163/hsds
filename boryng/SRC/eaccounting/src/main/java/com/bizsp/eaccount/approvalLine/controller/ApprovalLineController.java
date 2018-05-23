package com.bizsp.eaccount.approvalLine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizsp.eaccount.approvalLine.service.ApprovalLineService;
import com.bizsp.eaccount.approvalLine.vo.ApprovalPolicyVo;
import com.bizsp.framework.util.exception.ServiceException;

@Controller
public class ApprovalLineController {
	
	@Autowired
	ApprovalLineService approvalLineService;
	
	@RequestMapping("admin/approvalManage/approvalPolicy")
	public String cardManage(Model model){
		return "admin/approvalLineManage/approvalPolicy";
	}
	
	@RequestMapping("admin/approvalManage/approvalLine/{approvalId}")	
	public String cardManageTerm(@PathVariable("approvalId") String policyId, Model model){
		try {
			model.addAttribute("policy", approvalLineService.getApprovalPolicyOne(policyId).get("policy"));
			//model.addAttribute("approvalLine", approvalLineService.getApprovalLine(policyId).get("approvalLine"));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "admin/approvalLineManage/approvalLine";
	}
}
