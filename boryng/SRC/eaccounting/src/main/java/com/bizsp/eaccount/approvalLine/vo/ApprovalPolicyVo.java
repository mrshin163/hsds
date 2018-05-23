package com.bizsp.eaccount.approvalLine.vo;import com.bizsp.framework.util.model.BaseCndModel;public class ApprovalPolicyVo extends BaseCndModel{	/**	 * 	 */	private static final long serialVersionUID = 340970362502529604L;		private String companyCd	;	private String policyId		;	private String policyNm		;	private String accountCd	;	private String accountNm	;	private String minAmount	;	private String maxAmount	;	private String description	;	private String useYn		;	private String delYn		;	private String regDttm		;	private String regUserId	;	private String updDttm		;	private String updUserId	;	private String deptCd		;	private String deptNm		;			public String getCompanyCd() {		return companyCd;	}	public void setCompanyCd(String companyCd) {		this.companyCd = companyCd;	}	public String getPolicyId() {		return policyId;	}	public void setPolicyId(String policyId) {		this.policyId = policyId;	}	public String getPolicyNm() {		return policyNm;	}	public void setPolicyNm(String policyNm) {		this.policyNm = policyNm;	}	public String getAccountCd() {		return accountCd;	}	public void setAccountCd(String accountCd) {		this.accountCd = accountCd;	}	public String getMinAmount() {		return minAmount;	}	public void setMinAmount(String minAmount) {		this.minAmount = minAmount;	}	public String getMaxAmount() {		return maxAmount;	}	public void setMaxAmount(String maxAmount) {		this.maxAmount = maxAmount;	}	public String getDescription() {		return description;	}	public void setDescription(String description) {		this.description = description;	}	public String getUseYn() {		return useYn;	}	public void setUseYn(String useYn) {		this.useYn = useYn;	}	public String getDelYn() {		return delYn;	}	public void setDelYn(String delYn) {		this.delYn = delYn;	}	public String getRegDttm() {		return regDttm;	}	public void setRegDttm(String regDttm) {		this.regDttm = regDttm;	}	public String getRegUserId() {		return regUserId;	}	public void setRegUserId(String regUserId) {		this.regUserId = regUserId;	}	public String getUpdDttm() {		return updDttm;	}	public void setUpdDttm(String updDttm) {		this.updDttm = updDttm;	}	public String getUpdUserId() {		return updUserId;	}	public void setUpdUserId(String updUserId) {		this.updUserId = updUserId;	}	public String getDeptCd() {		return deptCd;	}	public void setDeptCd(String deptCd) {		this.deptCd = deptCd;	}	public String getDeptNm() {		return deptNm;	}	public void setDeptNm(String deptNm) {		this.deptNm = deptNm;	}		public String getAccountNm() {		return accountNm;	}	public void setAccountNm(String accountNm) {		this.accountNm = accountNm;	}		@Override	public String toString() {		return "ApprovalPolicyVo [companyCd=" + companyCd + ", policyId="				+ policyId + ", policyNm=" + policyNm + ", accountCd="				+ accountCd + ", minAmount=" + minAmount + ", maxAmount="				+ maxAmount + ", description=" + description + ", useYn="				+ useYn + ", delYn=" + delYn + ", regDttm=" + regDttm				+ ", regUserId=" + regUserId + ", updDttm=" + updDttm				+ ", updUserId=" + updUserId + ", deptCd=" + deptCd				+ ", deptNm=" + deptNm + "]";	}	}
