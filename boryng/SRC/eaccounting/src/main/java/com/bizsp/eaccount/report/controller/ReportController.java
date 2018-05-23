package com.bizsp.eaccount.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bizsp.eaccount.approvalRq.vo.ApprovalRqVo;
import com.bizsp.eaccount.code.service.CodeService;
import com.bizsp.eaccount.report.service.ReportService;
import com.bizsp.framework.security.vo.LoginUserVO;

@Controller
public class ReportController {

	@Autowired
	ReportService reportService;
	CodeService codeService;

	/**
	 * 개인별 카드처리현황
	 * 
	 * @return
	 */
	@RequestMapping("report/cardProcessStatusList")
	public String cardProcessStatusList(ApprovalRqVo approvalRqVo, Model model) {
		model.addAttribute("defaultWorkduration", reportService.getDefaultWorkduration());		
		return "report/cardProcessStatusList";
	}
	
	/**
	 * 부서별 카드 처리 현황
	 * 
	 * @return
	 */
	@RequestMapping("report/cardProcessStatusListDept")
	public String cardProcessStatusListDept(ApprovalRqVo approvalRqVo, Model model) {
		model.addAttribute("defaultWorkduration", reportService.getDefaultWorkduration());
		return "report/cardProcessStatusListDept";
	}
	
	/**
	 * 전체 카드 처리 현황
	 * 
	 * @return
	 */
	@RequestMapping(value = "/report/cardProcessStatusListAll", method = RequestMethod.GET)
	public String cardProcessStatusListAll(Model model, 
				@RequestParam(value = "startAuthDate", defaultValue = "") String startAuthDate
			,	@RequestParam(value = "endAuthDate", defaultValue = "") String endAuthDate
			,	@RequestParam(value = "approvalStatusCd", defaultValue = "") String approvalStatusCd
			,	@RequestParam(value = "financeNo", defaultValue = "") String financeNo
			,	@RequestParam(value = "deptCd", defaultValue = "") String deptCd
			,	@RequestParam(value = "deptNm", defaultValue = "") String deptNm
			,	@RequestParam(value = "ownUserId", defaultValue = "") String ownUserId
			,	@RequestParam(value = "mercName", defaultValue = "") String mercName) {
		
		
		model.addAttribute("defaultWorkduration", reportService.getDefaultWorkduration());
		model.addAttribute("deptCd", getLoginUserInfo().getDeptCode());
		model.addAttribute("deptNm", getLoginUserInfo().getDeptName());
		//if(!startAuthDate.isEmpty() && !endAuthDate.isEmpty()){
		//	Map<String, Object> map = reportService.getCardProcessStatusListAll(startAuthDate, endAuthDate, approvalStatusCd, financeNo, deptCd, deptNm, ownUserId, mercName);
		//	model.addAttribute("cardProcessList", map.get("cardProcessList"));
		//	model.addAttribute("totalAmount", map.get("totalAmount"));
		//}
		return "report/cardProcessStatusListAll";
	}

	/**
	 * 영업 카드 처리 현황
	 * 
	 * @return
	 */
	@RequestMapping("report/cardProcessStatusListSales")
	public String cardProcessStatusListSales(ApprovalRqVo approvalRqVo, Model model) {

		return "report/cardProcessStatusListSales";
	}
	
	/**
	 * 공장 카드 처리 현황
	 * 
	 * @return
	 */
	@RequestMapping("report/cardProcessStatusListPlant")
	public String cardProcessStatusListPlant(ApprovalRqVo approvalRqVo, Model model) {

		return "report/cardProcessStatusListPlant";
	}
	
	/**
	 * 본사연구소 카드 처리 현황
	 * 
	 * @return
	 */
	@RequestMapping("report/cardProcessStatusListCompLab")
	public String cardProcessStatusListCompLab(ApprovalRqVo approvalRqVo, Model model) {

		return "report/cardProcessStatusListCompLab";
	}
	
	/**
	 * 안산연구소 카드 처리 현황
	 * 
	 * @return
	 */
	@RequestMapping("report/cardProcessStatusListAnsanLab")
	public String cardProcessStatusListAnsanLab(ApprovalRqVo approvalRqVo, Model model) {

		return "report/cardProcessStatusListAnsanLab";
	}
	
	/**
	 * 개인카드 리스트 조회
	 * 
	 * @return
	 */
	@RequestMapping("report/individualCardList")
	public String individualCardList(ApprovalRqVo approvalRqVo, Model model) {
		return "report/individualCardList";
	}
	
	private LoginUserVO getLoginUserInfo() {
		return (LoginUserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
