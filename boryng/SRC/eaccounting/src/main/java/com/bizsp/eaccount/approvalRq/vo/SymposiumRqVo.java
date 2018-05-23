package com.bizsp.eaccount.approvalRq.vo;

import com.bizsp.framework.util.model.BaseCndModel;

public class SymposiumRqVo extends BaseCndModel{
	
	private static final long serialVersionUID = 8154259299174992274L;
	private String companyCode;
//	private String approvalId;
//	private int    seq;
	private String authDate;
//	private String mercName;
//	private int    amtAmount;
//	private int    vmtAmount;
//	private int    requestAmount;
//	private String userId; // 담당자
//	private String userNm;
//	private String userName; // 소지자
	private String budgetDeptCd; 
//	private String budgetDeptNm; 
//	private String accountCd;
//	private String accountNm;
//	private String account2Cd;
//	private String account2Nm;
	private String productCd;
//	private String productNm;
//	private String customerCd;
//	private String customerNm;
//	private String ftrCd;
//	private String details;
//	private String opinion;
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAuthDate() {
		return authDate;
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public String getBudgetDeptCd() {
		return budgetDeptCd;
	}
	public void setBudgetDeptCd(String budgetDeptCd) {
		this.budgetDeptCd = budgetDeptCd;
	}
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	
	

}
