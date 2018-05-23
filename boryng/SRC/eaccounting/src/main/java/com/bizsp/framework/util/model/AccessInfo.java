package com.bizsp.framework.util.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.system.Client;


/**
 * <strong>description</strong> : 사용자의 접속정보를 담고 있다.  <BR/>
 */
public class AccessInfo extends BaseModel{
	/**  */
	private static final long serialVersionUID = 1009513163447392606L;
	private String userAddr;
	private String path;
	private String uri;
	private String queryString;
	private String url;
	private String queryStringEncoding;
	private String urlEncoding;
	private String referer;
	private String cookieDomain;
	private Client client;
	private boolean isMobile;//모바일인지 아닌지
	private String rtUrl;
	private String hostnm;
	private String userAgent;

	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getUserAddr() {
		return userAddr;
	}
	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getQueryStringEncoding() {
		return queryStringEncoding;
	}
	public void setQueryStringEncoding(String queryStringEncoding) {
		this.queryStringEncoding = queryStringEncoding;
	}
	public String getUrlEncoding() {
		return urlEncoding;
	}
	public void setUrlEncoding(String urlEncoding) {
		this.urlEncoding = urlEncoding;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getCookieDomain() {
		return cookieDomain;
	}
	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public boolean isMobile() {
		return isMobile;
	}
	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}
	public String getRtUrl() {
		String url = uri;
		if(StringUtil.isNotEmpty(queryString)){
			url += "?"+queryString;
		}
		try {
			rtUrl = URLEncoder.encode(url,"utf-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		return rtUrl;
	}
	public void setRtUrl(String rtUrl) {
		this.rtUrl = rtUrl;
	}
	public String getHostnm() {
		return hostnm;
	}
	public void setHostnm(String hostnm) {
		this.hostnm = hostnm;
	}


}
