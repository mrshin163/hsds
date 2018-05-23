package com.bizsp.eaccount.code.vo;

import com.bizsp.framework.util.model.BaseCndModel;

public class CommGrpCodeVo extends BaseCndModel{
	
	private static final long serialVersionUID = 422644327311814327L;
	
	private String grpCodeId;
	private String companyCode;
	private String grpCodeName;
	private String codeType;
	private String queryId;
	private String description;
	private String useYn;
	private String delYn;
	private String regDate;
	private String regUser;
	private String updtDate;
	private String updtUser;
	
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getGrpCodeId() {
		return grpCodeId;
	}
	public void setGrpCodeId(String grpCodeId) {
		this.grpCodeId = grpCodeId;
	}
	public String getGrpCodeName() {
		return grpCodeName;
	}
	public void setGrpCodeName(String grpCodeName) {
		this.grpCodeName = grpCodeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegUser() {
		return regUser;
	}
	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}
	public String getUpdtDate() {
		return updtDate;
	}
	public void setUpdtDate(String updtDate) {
		this.updtDate = updtDate;
	}
	public String getUpdtUser() {
		return updtUser;
	}
	public void setUpdtUser(String updtUser) {
		this.updtUser = updtUser;
	}
}
