/**
 * DevKIM Developed by Java <br>
 * @Copyright 2011 by DevKIM. All rights reserved. <BR/>
 * Contact Me gate7711@gmail.com
 */
package com.bizsp.framework.util.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * @since 2011. 9. 27.
 * @version 1.0
 */
public class UploadPolicyModel implements Serializable{
	private static final long serialVersionUID = -206106379158714578L;

	/**
	 * 파일업로드 정책
	 */
	private String policyName;
	/**
	 * 파일업로드가능 사이즈 KB
	 */
	private String limitSize;
	/**
	 * 웹루트에 업로드가능여부
	 */
	private String webRootAt;
	/**
	 * 기본디렉토리
	 */
	private String defaultDir;
	/**
	 * 허용가능 확장자
	 */
	private String allowExt;


	/**
	 * 업로드 불가능 확장자
	 */
	private String denyExt;

	/**
	 * 썸네일정책 목록
	 */
	private List<ThumbnailPolicyModel> thumbnailPolicyList;



	public List<ThumbnailPolicyModel> getThumbnailPolicyList() {
		return thumbnailPolicyList;
	}
	public void setThumbnailPolicyList(
			List<ThumbnailPolicyModel> thumbnailPolicyList) {
		this.thumbnailPolicyList = thumbnailPolicyList;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getLimitSize() {
		return limitSize;
	}
	public void setLimitSize(String limitSize) {
		this.limitSize = limitSize;
	}
	public String getWebRootAt() {
		return webRootAt;
	}
	public void setWebRootAt(String webRootAt) {
		this.webRootAt = webRootAt;
	}
	public String getDefaultDir() {
		return defaultDir;
	}
	public void setDefaultDir(String defaultDir) {
		this.defaultDir = defaultDir;
	}
	public String getAllowExt() {
		return allowExt;
	}
	public void setAllowExt(String allowExt) {
		this.allowExt = allowExt;
	}
	public String getDenyExt() {
		return denyExt;
	}
	public void setDenyExt(String denyExt) {
		this.denyExt = denyExt;
	}


}
