package com.bizsp.framework.util.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * <strong>description</strong> :  데이타셋의 기본구성 <BR/>
 * <strong>author</strong> : DevKIM (gate7711@gmail.com) <BR/>
 * <strong>version</strong> 1.0 <BR/>
 * <strong>date</strong> : 2010. 12. 1.
 */
public class BaseModel implements Serializable{


	private static final long serialVersionUID = -4615057929044746330L;
	/**
	 * 전송코드
	 */
	private String trCode;
	/**
	 * 전송메시지
	 */
	private String trMessage;


	public String getTrCode() {
		return trCode;
	}

	public void setTrCode(String trCode) {
		this.trCode = trCode;
	}

	public String getTrMessage() {
		return trMessage;
	}

	public void setTrMessage(String trMessage) {
		this.trMessage = trMessage;
	}

	/**
	 * <strong>title</strong> : describe  <BR/>
	 * <strong>description</strong> : 빈즈를 Map으로 변환한다. <BR/>
	 * <strong>date</strong> : 2010. 12. 10.
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map describe(){
		try {
			return BeanUtils.describe(this);
		} catch (IllegalAccessException e) {
			return null;
		} catch (InvocationTargetException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
}
