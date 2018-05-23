package com.bizsp.framework.util.database;

import org.apache.commons.dbcp.BasicDataSource;
import com.bizsp.framework.util.security.Cipher;

public class SecureBasicDataSource extends BasicDataSource {

	private Cipher cipher = new Cipher();

	
	public void setUrl(String url) {
		super.setUrl(cipher.seedDecrypt(url));
	}
	
	public void setUsername(String username) {
		super.setUsername(cipher.seedDecrypt(username));
	}
	
	public void setPassword(String password) {
		super.setPassword(cipher.seedDecrypt(password));
	}

}
