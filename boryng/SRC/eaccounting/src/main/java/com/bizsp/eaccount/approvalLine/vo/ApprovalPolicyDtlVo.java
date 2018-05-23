package com.bizsp.eaccount.approvalLine.vo;

import com.bizsp.framework.util.model.BaseCndModel;

public class ApprovalPolicyDtlVo extends BaseCndModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5811483517668622552L;
	private String companyCd			;
	private String policyId				;	
	private String seq					;
	private String approvalLineCd		;
	private String approvalLineVal		;
	private String approvalLineValNm	;
	private String deptNm				;
	private String titleNm				;
	private String regDttm				;
	private String regUserId			;
	private String updDttm				;
	private String updUserId			;
	
	

	public String getApprovalLineValNm() {
		return approvalLineValNm;
	}
	public void setApprovalLineValNm(String approvalLineValNm) {
		this.approvalLineValNm = approvalLineValNm;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getTitleNm() {
		return titleNm;
	}
	public void setTitleNm(String titleNm) {
		this.titleNm = titleNm;
	}
	public String getCompanyCd() {
		return companyCd;
	}
	public void setCompanyCd(String companyCd) {
		this.companyCd = companyCd;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getApprovalLineCd() {
		return approvalLineCd;
	}
	public void setApprovalLineCd(String approvalLineCd) {
		this.approvalLineCd = approvalLineCd;
	}
	public String getApprovalLineVal() {
		return approvalLineVal;
	}
	public void setApprovalLineVal(String approvalLineVal) {
		this.approvalLineVal = approvalLineVal;
	}
	public String getRegDttm() {
		return regDttm;
	}
	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getUpdDttm() {
		return updDttm;
	}
	public void setUpdDttm(String updDttm) {
		this.updDttm = updDttm;
	}
	public String getUpdUserId() {
		return updUserId;
	}
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	@Override
	public String toString() {
		return "ApprovalPolicyDtlVo [companyCd=" + companyCd + ", policyId="
				+ policyId + ", seq=" + seq + ", approvalLineCd="
				+ approvalLineCd + ", approvalLineVal=" + approvalLineVal
				+ ", regDttm=" + regDttm + ", regUserId=" + regUserId
				+ ", updDttm=" + updDttm + ", updUserId=" + updUserId + "]";
	}
	
	

}
