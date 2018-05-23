package com.bizsp.eaccount.report.vo;

import java.util.Collection;

public class DeptListVo {
	private String companyCd;
	private Collection deptList;
	
	public String getCompanyCd() {
		return companyCd;
	}
	public void setCompanyCd(String companyCd) {
		this.companyCd = companyCd;
	}
	public Collection getDeptList() {
		return deptList;
	}
	public void setDeptList(Collection deptList) {
		this.deptList = deptList;
	}	
}
