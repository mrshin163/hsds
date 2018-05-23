package com.bizsp.framework.util.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.model.AccessInfo;
import com.bizsp.framework.util.system.Client;
import com.bizsp.framework.util.system.PropUtil;






public class WebUtil {
	private static Logger logger = LoggerFactory.getLogger(WebUtil.class);

	@SuppressWarnings("unused")
	private static final Pattern BROWSER_IE = Pattern.compile("MSIE",
			Pattern.CASE_INSENSITIVE); // IE 확인
	private static final Pattern LGT = Pattern.compile("LGTELECOM|LG",
			Pattern.CASE_INSENSITIVE); // LGT 확인
	private static final Pattern LGT_BROWSER_WEBVIEW = Pattern.compile("WV0",
			Pattern.CASE_INSENSITIVE); // WebView 480
	private static final Pattern LGT_BROWSER_WEBVIEW_480 = Pattern.compile(
			"480\\*", Pattern.CASE_INSENSITIVE); // WebView 800
	private static final Pattern SKT = Pattern.compile("SKT",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern SKT_BROWSER_POLARIS = Pattern.compile(
			"NATEBROWSER", Pattern.CASE_INSENSITIVE); // SKT Polaris Full Browser
	private static final Pattern KTF = Pattern.compile("KTF",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern APLLEWEBKIT = Pattern.compile("APPLEWEBKIT",
			Pattern.CASE_INSENSITIVE); // Apple Webkit 확인
	private static final Pattern IPHONE = Pattern.compile("IPHONE",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern IPOD = Pattern.compile("IPOD",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern IPAD = Pattern.compile("IPAD",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern ANDROID = Pattern.compile("ANDROID",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern OPERA = Pattern.compile("OPERA",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern KTH_KB = Pattern.compile("KTH_KB",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern WEBVIEWER_480 = Pattern.compile("480\\*");


	/*
	 * QueryString에서 parameterName에 해당되는 value를 가지고 온다.<br>
	 */
	public static String getQueryStringValue(String queryString, String parameterName){
		if(queryString == null || parameterName == null || "".equals(queryString) || "".equals(parameterName)){
			return "";
		}else{
			int stringIndex = queryString.indexOf(parameterName);
			if(stringIndex > -1){
				String tempString = queryString.substring(stringIndex);
				int splitCharIndex = tempString.indexOf("&");
				if(splitCharIndex > -1){
					return tempString.substring(parameterName.length(),splitCharIndex);
				}else{
					return tempString.substring(parameterName.length());
				}
			}else{
				return "";
			}
		}
	}

	/**
	 * Base64 Decode
	 * @param val
	 * @return
	 */
	public static String base64Decode(String val){
		return new String(Base64.decodeBase64(val));
	}

	/**
	 * Base64 Encode
	 * @param val
	 * @return
	 */
	public static String base64Encode(String val){
		return Base64.encodeBase64String(val.getBytes());
	}

	/*
	 * url 디코딩
	 */
	public static String urlDecoder(String url, String encoding){
		if(url != null){
			try {
				url =  URLDecoder.decode(url, encoding);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
		return url;
	}

	/*
	 * url 디코딩
	 */
	public static String urlDecoder(String url){
		return urlDecoder(url, "UTF-8");
	}

	/*
	 * url 인코딩
	 */
	public static String urlEncoder(String url, String encoding){
		if(url != null){
			try {
				return URLEncoder.encode(url, encoding);
			} catch (UnsupportedEncodingException e) {
				return null;
			}

		}else{
			return "";
		}
	}

	/*
	 * url 인코딩
	 */
	public static String urlEncoder(String url){
		return urlEncoder(url, "UTF-8");
	}

	@SuppressWarnings("rawtypes")
	public static String postQueryString(HttpServletRequest request){
		String sNames = "";
		StringBuffer buf= new StringBuffer();
		Enumeration enu = request.getParameterNames();
		while(enu.hasMoreElements()) {
			sNames = (String)enu.nextElement();
			buf.append("&"+sNames+"=").append(request.getParameter(sNames));
		}
		return buf.toString();
	}

	/**
	 * @Author Administrator
	 * @param response
	 * @param url
	 */
	public static void callback(HttpServletResponse response ,String url) {
		callback(response ,url,null,null);
	}

	/**
	 * @Author Administrator
	 * @param response
	 * @param url : 이동할 URL
	 * @param map : parameter값
	 * @param methodParam : 'post' or 'get'
	 */
	public static void callback(HttpServletResponse response ,String url,Map<String,String> map,String methodParam) {
		PrintWriter out = null;
		String method = StringUtil.isEmpty(methodParam)?"post":methodParam;
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		StringBuffer result = new StringBuffer("");
		StringBuffer script = new StringBuffer("<script type='text/javascript'>\n");
		if(map!=null){
			Set<String> keySet = map.keySet();
			Iterator<String> iter = keySet.iterator();
			String preFix = "<form action='"+url+"' name='frm' id='frm' method='"+method+"'>\n";
			String suffix = "</form>\n";
			StringBuffer inputTags = new StringBuffer("");
			while(iter.hasNext()){
				String key = iter.next();
				String value = map.get(key);
				inputTags.append("<input type='hidden' name='"+key+"' value='"+StringUtil.html(value)+"'/>\n");
			}
			result.append(preFix);
			result.append(inputTags);
			result.append(suffix);
			script.append("window.onload = function(){ document.getElementById('frm').submit(); }");
		}else{
			script.append("location.href=\""+url+"\";\n");
		}
		script.append("\n</script>");
		result.append(script);
		try {
			out = response.getWriter();
			out.print(result);
			out.close();
		} catch (IOException e) {
			//				e.printStackTrace();
			e.getMessage();
		}
	}



	/**
	 * response에 스크립트를 실행한다.
	 * @param response
	 * @param scriptStr
	 */
	public static void printScript(HttpServletResponse response, String scriptStr) {
		PrintWriter out = null;
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		StringBuffer result = new StringBuffer("");
		StringBuffer script = new StringBuffer("<script type='text/javascript'>");
		script.append(scriptStr);
		script.append("</script>");
		result.append(script);
		try {
			out = response.getWriter();
			out.print(result);
			out.close();
		} catch (IOException e) {
			e.getMessage();
		}
	}


	/**
	 * response에 스크립트를 실행한다.
	 * @param response
	 * @param scriptStr
	 */
	public static void printXml(HttpServletResponse response, String scriptStr) {
		PrintWriter out = null;
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		StringBuffer result = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		result.append(scriptStr);
		try {
			out = response.getWriter();
			out.print(result);
			out.close();
		} catch (IOException e) {
			e.getMessage();
		}
	}

	/**
	 * response에 문짜를 찍는다.
	 * @param response
	 * @param scriptStr
	 */
	public static void printString(HttpServletResponse response, String msg) {
		PrintWriter out = null;
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		StringBuffer result = new StringBuffer("");
		result.append(msg);
		try {
			out = response.getWriter();
			out.print(result);
			out.close();
		} catch (IOException e) {
			e.getMessage();
		}
	}

	//인코딩방식 확인
	public static boolean isUTF8(byte[] sequence)
	{
		int numberBytesInChar;

		for (int i=0; i< sequence.length; i++)
		{
			byte b = sequence[i];
			if (((b >> 6) & 0x03) == 2)
			{
				return false;
			}
			byte test = b;
			numberBytesInChar = 0;
			while ((test & 0x80)>0)
			{
				test <<= 1;
				numberBytesInChar ++;
			}

			if (numberBytesInChar > 1) // check that extended bytes are also good...
			{
				for (int j=1; j< numberBytesInChar; j++)
				{
					if (i+j >= sequence.length)
					{
						return false; // not a character encoding - probably random bytes
					}

					if (((sequence[i+j] >> 6) & 0x03) != 2)
					{
						return false;
					}
				}
				i += numberBytesInChar - 1; // increment i to the next utf8 character start position.
			}
		}

		return true;
	}

	/**
	 * HttpServletRequet 를 받아서  모바일 브라우저에 <br>
	 * 맞는 TYPE 을 가져 온다<br>
	 * @param request
	 * @return Client enum
	 */
	public static Client userAgentChecker(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent").toUpperCase();
		logger.debug("User-Agent :"+ userAgent);
		if (StringUtil.isNotEmpty(userAgent)) {
			if (APLLEWEBKIT.matcher(userAgent).find()) {
				if (IPOD.matcher(userAgent).find()) {
					return Client.IPOD;
				}
				else if (IPHONE.matcher(userAgent).find()) {
					return Client.IPHONE;
				}
				else if (IPAD.matcher(userAgent).find()) {
					return Client.IPAD;
				}
				else if (ANDROID.matcher(userAgent).find()) {
					return Client.ANDROID;
				}
				//				else {
				//					return Client.APLLEWEBKIT;
				//				}
			}
			else if (LGT.matcher(userAgent).find()) {
				if (LGT_BROWSER_WEBVIEW.matcher(userAgent).find()) {
					if (LGT_BROWSER_WEBVIEW_480.matcher(userAgent).find()) {
						return Client.LGT_BROWSER_WEBVIEW_480;
					}
					else {
						return Client.LGT_BROWSER_WEBVIEW_800;
					}
				}
				else {
					return Client.LGT_BROWSER_POLARIS;
				}
			}
			else if (SKT_BROWSER_POLARIS.matcher(userAgent).find()) {
				return Client.SKT_BROWSER_POLARIS;
			}
			else if (OPERA.matcher(userAgent).find()) {
				return Client.OPERA;
			}
			else if (KTH_KB.matcher(userAgent).find()) {
				return Client.KTH_KB;
			}
			else if (WEBVIEWER_480.matcher(userAgent).find()) {
				return Client.WEBVIEWER_480;
			}
			else {
				return Client.PC;
			}
		}

		return Client.PC;
	}

	/**
	 * HttpServletRequet 를 받아서 모바일 브라우저에 <br>
	 * 맞는 TYPE 을 가져 온다<br>
	 *
	 * @param request
	 * @return Client enum
	 */
	public static boolean isMobile(HttpServletRequest request) {
		// Pattern pattern = Pattern.compile(regex);
		String userAgent = request.getHeader("User-Agent").toUpperCase();
		boolean isMobile = false;
		if (userAgent != null && !"".equals(userAgent)) {
			if (IPOD.matcher(userAgent).find()) {
				return true;
			} else if (IPHONE.matcher(userAgent).find()) {
				return true;
			} else if (IPAD.matcher(userAgent).find()) {
				return true;
			} else if (OPERA.matcher(userAgent).find()) {
				if (SKT.matcher(userAgent).find()) {
					return true;
				}else if (LGT.matcher(userAgent).find()) {
					return true;
				}else if (KTF.matcher(userAgent).find()) {
					return true;
				}
			} else if (ANDROID.matcher(userAgent).find()) {
				return true;
			}else if (LGT.matcher(userAgent).find()) {
				return true;
			} else if (SKT_BROWSER_POLARIS.matcher(userAgent).find()) {
				return true;
			}

		}

		return isMobile;
	}

	/**
	 * <strong>description</strong> : NS-CLIENT-IP 가지고 오기 <BR/>
	 * @param request
	 * @return <BR/>
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String sRemoteAddr = null;
		try
		{
			if (request.getHeader("NS-CLIENT-IP") != null){
				sRemoteAddr = request.getHeader("NS-CLIENT-IP");
				if (logger.isDebugEnabled()) {
					logger.debug("<><>=NS_CLIENT_IP:"+sRemoteAddr);
				}
			}
			if (sRemoteAddr == null && request.getRemoteAddr() != null){
				sRemoteAddr = request.getRemoteAddr();
				if (logger.isDebugEnabled()) {
					logger.debug("<><>====getRemoteAddr:"+sRemoteAddr);
				}
			}
		}catch (Exception e)
		{
			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			sRemoteAddr = request.getRemoteAddr();
		}
		return sRemoteAddr;
	}

	/**
	 * <strong>description</strong> : 암호화<BR/>
	 * @param byte[] data
	 * @return <BR/>
	 */
	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9)) {
					buf.append((char) ('0' + halfbyte));
				} else {
					buf.append((char) ('a' + (halfbyte - 10)));
				}
				halfbyte = data[i] & 0x0F;
			} while(two_halfs++ < 1);
		}
		return buf.toString();
	}

	public static String SHA1(String text)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		sha1hash = md.digest();
		return convertToHex(sha1hash);
	}

	public static String gethostName(){
		String hostName = "";
		try
		{
			Runtime rt = java.lang.Runtime.getRuntime();
			Process proc = rt.exec("hostname");
			int inp;
			while ((inp = proc.getInputStream().read()) != -1) {
				hostName+=(char)inp;
			}
			proc.waitFor();
			return hostName.trim();
		}catch(Exception e)
		{
			return null;
		}
	}

	/**
	 * url에서 쿠키용 도메인 추출
	 *
	 * @param str 원본문자열
	 * @return 쿠키용 도메인
	 */

	public static String getCollectURL(String str)
	{
		String domain = PropUtil.getValue("common.domain");
		String s = getOriginURL(str);
		if(s.endsWith(domain)) {
			s="."+domain;
		} else {
			s = StringUtil.change(s, "http://", "");
			s = StringUtil.change(s, "https://", "");
			if(s.indexOf("/")>0){
				s = s.substring(0,s.indexOf("/"));
			}
			s = ""+s;
		}
		return s;

	}

	/**
	 * url에서 도메인추출
	 *
	 * @param str 원본문자열
	 * @return 도메인
	 */
	public static String getOriginURL(String str)
	{

		String s = "";
		String regex = "(?i:https?://([^/]+))";
		String regex2 = "(?i:http?://([^/]+))";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while (m.find()) {
			s = str.substring(m.start(), m.end());
		}

		p = Pattern.compile(regex2);
		m = p.matcher(s);
		while (m.find()) {
			s = str.substring(m.start(), m.end());
		}

		return s;
	}

	/**
	 * <strong>title</strong> : getAccessInfo  <BR/>
	 * <strong>description</strong> : 접속정보를 설정한다. <BR/>
	 * <strong>date</strong> : 2010. 12. 10.
	 * @param request
	 * @return
	 */
	public static AccessInfo getAccessInfo(HttpServletRequest request) {
		AccessInfo accessInfo = null;
		String queryString = request.getQueryString();
		String url = (request.getRequestURL()).toString();

		accessInfo = new AccessInfo();
		accessInfo.setUserAddr(getRemoteAddr(request));
		accessInfo.setPath(StringUtil.defaultString(request.getServletPath()));
		accessInfo.setUri(StringUtil.defaultString(request.getRequestURI()));
		accessInfo.setQueryString(StringUtil.defaultString(queryString));
		accessInfo.setUrl(StringUtil.defaultString(url));
		accessInfo.setReferer(request.getHeader(StringUtil.defaultString("Referer")));
		accessInfo.setUserAgent(request.getHeader(StringUtil.defaultString("User-Agent")));
		accessInfo.setCookieDomain(getCollectURL(accessInfo.getUrl()));
		accessInfo.setClient(userAgentChecker(request));
		accessInfo.setMobile(isMobile(request));
		accessInfo.setHostnm(gethostName());

		return accessInfo;
	}

	/*public static void jsonWriteString(HttpServletResponse response ,Object result) {
        JSONObject jsonObject = null;
        PrintWriter out = null;
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
             jsonObject = JSONObject.fromObject(result);
             String jsonStr = jsonObject.toString();
             //jsonStr = StringUtil.euckrToUnicode(jsonStr);
             out = response.getWriter();
             out.print(jsonStr);
             out.close();
        } catch (IOException e) {
        }
	}*/
}
