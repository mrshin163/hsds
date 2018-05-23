package com.bizsp.framework.util.exception.mvc;

import org.springframework.web.servlet.ModelAndView;

public interface MvcExceptionHandler {

	/**
	 * MVC에서 발생한 Exception을 처리한다.
	 * @Author Administrator
	 * @param ex
	 * @return
	 */
	public ModelAndView resolverException(Exception ex);
}
