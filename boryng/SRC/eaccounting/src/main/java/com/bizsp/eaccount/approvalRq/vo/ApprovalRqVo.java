package com.bizsp.eaccount.approvalRq.vo;

import com.bizsp.framework.util.model.BaseCndModel;

public class ApprovalRqVo extends BaseCndModel{
	
	private static final long serialVersionUID = -8221576712942257536L;
	private String userId;
	private String companyCode;
	private String deptCode;
	private String budgetDeptCode;
	private String accountCode;
	private String amount;
	private String sDate;
	private String cardUsingTypeCode;
	private String cardUserCode;
	private String cardUserName;
	
	public String getCardUserCode() {
		return cardUserCode;
	}
	public void setCardUserCode(String cardUserCode) {
		this.cardUserCode = cardUserCode;
	}
	public String getcardUserName() {
		return cardUserName;
	}
	public void setcardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	
	public String getCardUsingTypeCode() {
		return cardUsingTypeCode;
	}
	public void setCardUsingTypeCode(String cardUsingTypeCode) {
		this.cardUsingTypeCode = cardUsingTypeCode;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public String getBudgetDeptCode() {
		return budgetDeptCode;
	}
	public void setBudgetDeptCode(String budgetDeptCode) {
		this.budgetDeptCode = budgetDeptCode;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
}
