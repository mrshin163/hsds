package com.bizsp.eaccount.auth.vo;

import com.bizsp.framework.util.model.BaseCndModel;

public class MenuAuthVo extends BaseCndModel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7757679580086876865L;
	private String companyCode;
	private String menuId;
	private String authId;
	private String regDate;
	private String regUserId;
	
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
}
