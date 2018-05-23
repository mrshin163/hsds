/**
 * DevKIM Developed by Java <br>
 * @Copyright 2011 by DevKIM. All rights reserved. <BR/>
 * Contact Me gate7711@gmail.com
 */
package com.bizsp.framework.util.exception.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @author Administrator
 */
public class MvcExceptionResolver  extends SimpleMappingExceptionResolver{

	private MvcExceptionHandler MvcExceptionHandler;


	public MvcExceptionHandler getMvcExceptionHandler() {
		return MvcExceptionHandler;
	}


	public void setMvcExceptionHandler(
			MvcExceptionHandler MvcExceptionHandler) {
		this.MvcExceptionHandler = MvcExceptionHandler;
	}


	@Override
	protected ModelAndView doResolveException( HttpServletRequest request
																						, HttpServletResponse response
																						, Object handler
																						, Exception ex) {
		// Log exception, both at debug log level and at warn level, if desired.
		if (logger.isDebugEnabled()) {
			logger.debug("Resolving exception from handler [" + handler + "]: " + ex);
		}
		logException(ex, request);
//		MVC Exception 처리자가 있으면 처리를 맡긴다.
		if(MvcExceptionHandler!=null){
			return MvcExceptionHandler.resolverException(ex);
		}
		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			 // Apply HTTP status code for error views, if specified.
			 // Only apply it if we're processing a top-level request.
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode.intValue());
			}
			return getModelAndView(viewName, ex, request);
		}else{
			return null;
		}
	}
}