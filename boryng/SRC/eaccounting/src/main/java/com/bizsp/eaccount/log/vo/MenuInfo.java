package com.bizsp.eaccount.log.vo;

import java.util.Map;

public class MenuInfo {
	
	private static Map<String, Map<String, Object>> MENU_INFO;

	
	public static Map<String, Map<String, Object>> getMENU_INFO() {
		return MENU_INFO;
	}

	public static void setMENU_INFO(Map<String, Map<String, Object>> mENU_INFO) {
		MENU_INFO = mENU_INFO;
	}
}
