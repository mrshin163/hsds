package com.bizsp.framework.util.exception;

public class DaoException extends BaseException{

	/**
	 *
	 */
	private static final long serialVersionUID = 9066992280360657989L;
	/**
	 * 사용자 정의 에러를 메시지코드만으로 발생한다.
	 * @param code
	 */
	public DaoException(String code){
		this(null,code,null);
	}

	/**
	 * 시스템에러가 발생 하였을 경우.
	 * @param cause
	 */
	public DaoException( Throwable cause ) {
		this(cause,null,null);
	}

	/**
	 * 사용자정의 에러가 아닌 시스템에러가 있을 경우 코드와 함께 발생 시킨다.
	 * @param cause
	 * @param code
	 */
	public DaoException(Throwable cause,String code){
		this(cause,code,null);
	}

	/**
	 * 사용자 정의 에러를 발생한다.
	 * @param code
	 * @param messageParam
	 */
	public DaoException(String code,String messageParam[]){
		this(null,code,messageParam);
	}

	public DaoException(Throwable cause,String code,String messageParam[]){
		super(cause,code,messageParam);
	}
}
