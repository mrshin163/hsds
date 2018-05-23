package com.bizsp.framework.util.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

public class ServletUtil {

	/**
	 * ContextHolder에서 request를 가지고 온다.
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		ApplicationContext appCtx = ContextHolder.getContext();
		HttpRequestHolder req = appCtx.getAutowireCapableBeanFactory().getBean("httpServletRequestHolder",HttpRequestHolder.class);
		return req.getRequest();
	}

	/**
	 * Session을 가지고 오기.
	 * @Author Administrator
	 * @return
	 */
	public static HttpSession getSession(){
		return getRequest().getSession();
	}

	/**
	 * Session을 가지고 오기.
	 * @Author Administrator
	 * @return
	 */
	public static Object getSessionValue(String key){
		return getRequest().getSession().getAttribute(key);
	}

	/**
	 * 섹션 값 설정하기
	 * @Author Administrator
	 * @param key
	 * @param value
	 */
	public static void setSession(String key,Object value){
		getSession().setAttribute(key, value);
	}

	/**
	 * 해당 키의 세션값을 지우기
	 * @Author Administrator
	 * @param key
	 */
	public static  void removeSession(String key){
		getSession().removeAttribute(key);
	}

	/**
	 * 세션아웃.
	 * @Author Administrator
	 */
	public static void invalidate(){
		getSession().invalidate();
	}
}