package com.bizsp.framework.util.web;

import java.util.Locale;

import org.springframework.context.MessageSource;

public class MessageUtil {

	/**
	 * @param code : 메시지코드
	 * @param params : 메시지코드에 전달할 값.
	 * @return
	 */
	public static String getMessage(String code,Object[] params){
		try{
			MessageSource messageSource = (MessageSource)ContextHolder.getContext().getBean("messageSource");
			String message = messageSource.getMessage(code, params,new Locale("ko","KOR"));
			return message;
		}catch(Exception e){
			return code==null?"":code;
		}
	}

}
