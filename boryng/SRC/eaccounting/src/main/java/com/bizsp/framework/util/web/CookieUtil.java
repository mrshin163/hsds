package com.bizsp.framework.util.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bizsp.framework.util.lang.StringUtil;

public class CookieUtil {
	protected transient static Logger logger = Logger
			.getLogger(CookieUtil.class);;

	private static final String COOKIE_PATH = "/";
	private static final String COOKIE_ENC_CHATSET = "euc-kr";
	private static final String COOKIE_HEADER = "X-BTO-VER";
	private static final String COOKIE_VER = "1.0";

	public static final String COOKIE_MAINCOOKIE_TWITTER = "PTOC";
	public static final String COOKIE_MAINCOOKIE_ME2DAY = "PMOC";
	public static final String COOKIE_MAINCOOKIE_FACEBOOK = "PFOC";
	public static final String COOKIE_MAINCOOKIE_SSO = "PUDSSO";

	private HttpServletResponse res;

	@SuppressWarnings("rawtypes")
	private HashMap cookieMap;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CookieUtil(HttpServletRequest req) {
		Cookie cookies[] = req.getCookies();
		if (cookies != null) {
			cookieMap = new HashMap();
			try {
				for (int i = 0; i < cookies.length; i++) {
					cookieMap.put(cookies[i].getName(), URLDecoder.decode(
							cookies[i].getValue(), COOKIE_ENC_CHATSET));
				}
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException("UnsupportedEncodingException", e);
			}
		}
	}

	public CookieUtil(HttpServletRequest req, HttpServletResponse res) {
		this(req);

		this.res = res;
		res.setHeader(COOKIE_HEADER, COOKIE_VER);
	}

	public String getCookieValue(String key) {
		if (cookieMap == null) {
			return null;
		}
		else {
			String tmp = (String) cookieMap.get(key);

			if ((tmp == null) || ("".equals(tmp) == true)) {
				return null;
			}

			return tmp;
		}
	}

	public static void setCookieValue(HttpServletResponse res, String name,
			String value, int maxAge,String cookiedomain) {
		if (res == null || name == null || ("".equals(name) == true)) {
			return;
		}

		try {
			Cookie cookie = new Cookie(name, URLEncoder.encode(StringUtil.nvl(value),
						COOKIE_ENC_CHATSET));
			cookie.setDomain(cookiedomain);
			cookie.setPath(COOKIE_PATH);
			cookie.setMaxAge(maxAge);

			res.addCookie(cookie);
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UnsupportedEncodingException", e);
		}

	}

	public static void setCookieValue(HttpServletResponse res, String name,
			String value,String cookiedomain) {
		setCookieValue(res, name, value, -1,cookiedomain);
	}

	public void setCookieValue(String name, String value, int maxAge,String cookiedomain) {
		setCookieValue(res, name, value, maxAge,cookiedomain);
	}

	public void setCookieValue(String name, String value,String cookiedomain) {
		setCookieValue(res, name, value, -1,cookiedomain);
	}

}