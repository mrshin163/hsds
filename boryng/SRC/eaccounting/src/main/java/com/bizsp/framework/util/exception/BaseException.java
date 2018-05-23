package com.bizsp.framework.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.web.MessageUtil;

public class BaseException extends Exception{

	/**  */
	private static final long serialVersionUID = -7557225298092162201L;

	private static Logger logger = LoggerFactory.getLogger(BaseException.class);

	/**
	 * 에러메시지 코드
	 */
	private String code;
	/**
	 * 에러메시지 코드출력시 필요한 인자값들
	 */
	private String messageParams[];
	/**
	 * 에러메시지 코드로 출력된 메시지값
	 */
	private String codeMessage;

	private String exceptionLayout;


	public String getExceptionLayout() {
		return exceptionLayout;
	}
	public void setExceptionLayout(String exceptionLayout) {
		this.exceptionLayout = exceptionLayout;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object[] getMessageParams() {
		return messageParams;
	}
	public void setMessageParams(String[] messageParams) {
		this.messageParams = messageParams;
	}

	public String getCodeMessage() {
		return codeMessage;
	}
	public void setCodeMessage(String codeMessage) {
		this.codeMessage = codeMessage;
	}

	/**
	 * 사용자 정의 에러를 메시지코드만으로 발생한다.
	 * @param code
	 */
	public BaseException(String code){
		this(null,code,null);
	}

	/**
	 * 시스템에러가 발생 하였을 경우.
	 * @param cause
	 */
	public BaseException( Throwable cause ) {
		this(cause,null,null);
	}

	/**
	 * 사용자정의 에러가 아닌 시스템에러가 있을 경우 코드와 함께 발생 시킨다.
	 * @param cause
	 * @param code
	 */
	public BaseException(Throwable cause,String code){
		this(cause,code,null);
	}

	/**
	 * 사용자 정의 에러를 발생한다.
	 * @param code
	 * @param messageParam
	 */
	public BaseException(String code,String messageParam[]){
		this(null,code,messageParam);
	}

	public BaseException(Throwable cause,String code,String messageParam[]){
		this(null,code,messageParam,"BASE");
	}

	public BaseException(Throwable cause,String code,String messageParam[],String exceptionLayout){
		super(code,cause);
		initValue(code,messageParam);
		setExceptionLayout(exceptionLayout);
		writeLog();
	}

	/**
	 * 코드와 파라미터로 메시지를 설정한다.
	 * @Author Administrator
	 * @param code
	 * @param messageParam
	 */
	public void initValue(String code,String messageParam[]){
		setCode(code);
		setMessageParams(messageParam);
		setCodeMessage(MessageUtil.getMessage(code, messageParam));
	}

	/**
	 * @Author Administrator
	 */
	public void writeLog(){
		StringBuffer sb = new StringBuffer();
		if (!StringUtil.isEmpty(code)) {
			sb.append("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			sb.append("==> EXCEPTION LAYOUT :::: "+getExceptionLayout()+ "\n");
			sb.append("==> ERROR CODE :::: " + getCode()+ "\n");
			sb.append("==> ERROR CODE_MSG : " + getCodeMessage()+ "\n");
			sb.append("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}
		logger.error(sb.toString());
		if(getCause()!=null){
			sb.append("==> StackTrace \n");
			getCause().printStackTrace();
		}
	}
}
