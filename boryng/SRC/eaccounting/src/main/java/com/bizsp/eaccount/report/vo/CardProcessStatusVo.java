package com.bizsp.eaccount.report.vo;public class CardProcessStatusVo {    /**	 * 	 */	private static final long serialVersionUID = -7106561252895164811L;	String approvalStatusCd;
	String approvalStatusNm;
	long cnt;
	long amtAmount;
	long vatAmount;
	long requestAmount;	public String getApprovalStatusCd() {		return approvalStatusCd;	}	public void setApprovalStatusCd(String approvalStatusCd) {		this.approvalStatusCd = approvalStatusCd;	}	public String getApprovalStatusNm() {		return approvalStatusNm;	}	public void setApprovalStatusNm(String approvalStatusNm) {		this.approvalStatusNm = approvalStatusNm;	}	public long getCnt() {		return cnt;	}	public void setCnt(long cnt) {		this.cnt = cnt;	}	public long getAmtAmount() {		return amtAmount;	}	public void setAmtAmount(long amtAmount) {		this.amtAmount = amtAmount;	}	public long getVatAmount() {		return vatAmount;	}	public void setVatAmount(long vatAmount) {		this.vatAmount = vatAmount;	}	public long getRequestAmount() {		return requestAmount;	}	public void setRequestAmount(long requestAmount) {		this.requestAmount = requestAmount;	}	}
