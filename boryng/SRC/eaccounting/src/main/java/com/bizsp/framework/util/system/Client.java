package com.bizsp.framework.util.system;

/**
 * <strong>description</strong> :  모바일 접속정보 <BR/>
 */
public enum Client {

	APLLEWEBKIT("모바일", "SMART", "APLLEWEBKIT", "NONE", "SAFARI, PALM PRE",
			"smart/", "webkit_1.0.css", "1.0", "", "480"), IPHONE("아이폰",
			"SMART", "IPHONE", "KTF", "SAFARI", "smart/", "webkit_1.0.css",
			"1.0", "", "480"), IPOD("아이팟", "SMART", "IPOD", "NONE", "SAFARI",
			"smart/", "webkit_1.0.css", "1.0", "", "480"), IPAD("아이패드",
			"SMART", "IPAD", "NONE", "SAFARI", "smart/", "webkit_1.0.css",
			"1.0", "", "480"), ANDROID("안드로이드", "SMART", "ANDROID", "NONE",
			"SAFARI", "smart/", "webkit_1.0.css", "1.0", "", "480"), OPERA(
			"오페라", "SMART", "OPERA", "NONE", "OPERA", "smart/",
			"webkit_1.0.css", "0.75", "", "480"), LGT_BROWSER_WEBVIEW_480(
			"모바일", "WEBVIEW", "LGT_BROWSER_WEBVIEW_480", "LGT", "WEBVIEWER",
			"webviewer/", "wview_1.0.css", "1.0", "width:480px;", "480"), LGT_BROWSER_WEBVIEW_800(
			"모바일", "WEBVIEW", "LGT_BROWSER_WEBVIEW_800", "LGT", "WEBVIEWER",
			"webviewer/", "wview_1.0.css", "1.0", "width:800px;", "800"), LGT_BROWSER_POLARIS(
			"모바일", "WEBVIEW", "LGT_BROWSER_POLARIS", "LGT", "POLARIS",
			"webviewer/", "polaris_1.0.css", "1.0", "", "480"), SKT_BROWSER_POLARIS(
			"모바일", "WEBVIEW", "SKT_BROWSER_POLARIS", "SKT", "POLARIS",
			"webviewer/", "polaris_1.0.css", "1.0", "", "480"), KTH_KB(
			"KB금융단말", "WEBVIEW", "KTH_KB", "NONE", "MINIWEB", "webviewer/",
			"polaris_1.0.css", "1.0", "", "480"), WEBVIEWER_480("모바일",
			"WEBVIEW", "WEBVIEWER_480", "NONE", "WEBVIEWER", "webviewer/",
			"wview_1.0.css", "1.0", "width:480px;", "480"), WEBVIEWER("모바일",
			"WEBVIEW", "WEBVIEWER", "NONE", "WEBVIEWER", "webviewer/",
			"wview_1.0.css", "1.0", "width:800px;", "800"), PC("모바일",
					"PC", "PC", "PC", "PC", "smart/",
					"webkit_1.0.css", "1.0", "", "");

	public final String NAME;
	public final String CATEGORY;
	public final String TYPE;
	public final String TELCO;
	public final String BROWSER;
	public final String TEMPLATE_PATH;
	public final String CSS_NAME;
	public final String VIEW_PORT;
	public final String WIDTH;
	public final String IMGWIDTH;

	private Client(String name, String category, String type, String telco,
			String browser, String templatePath, String cssName,
			String viewPort, String width, String imgWidth) {
		this.NAME = name;
		this.CATEGORY = category;
		this.TYPE = type;
		this.TELCO = telco;
		this.BROWSER = browser;
		this.TEMPLATE_PATH = templatePath;
		this.CSS_NAME = cssName;
		this.VIEW_PORT = viewPort;
		this.WIDTH = width;
		this.IMGWIDTH = imgWidth;
	}

	/**
	 * @return the nAME
	 */
	public String getNAME() {
		return NAME;
	}

	/**
	 * @return the cATEGORY
	 */
	public String getCATEGORY() {
		return CATEGORY;
	}

	/**
	 * @return the iMGWIDTH
	 */
	public String getIMGWIDTH() {
		return IMGWIDTH;
	}

	/**
	 * @return the tYPE
	 */
	public String getTYPE() {
		return TYPE;
	}

	/**
	 * @return the tELCO
	 */
	public String getTELCO() {
		return TELCO;
	}

	/**
	 * @return the bROWSER
	 */
	public String getBROWSER() {
		return BROWSER;
	}

	/**
	 * @return the tEMPLATE_PATH
	 */
	public String getTEMPLATE_PATH() {
		return TEMPLATE_PATH;
	}

	/**
	 * @return the cSS_NAME
	 */
	public String getCSS_NAME() {
		return CSS_NAME;
	}

	/**
	 * @return the vIEW_PORT
	 */
	public String getVIEW_PORT() {
		return VIEW_PORT;
	}

	/**
	 * @return the wIDTH
	 */
	public String getWIDTH() {
		return WIDTH;
	}
}
