package com.bizsp.framework.util.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextHolder implements ApplicationContextAware {

    private static ApplicationContext appCtx;

    public ContextHolder() {
    }

    @Override
	public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        appCtx = applicationContext;
    }

    public static ApplicationContext getContext() {
        return appCtx;
    }
    
    /**
     * Bean이름으로 가지고 오기
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){
    	return ContextHolder.getContext().getBean(beanName);
    }
    
    /**
     * Bean클래스로 가지고 오기
     * @param classObj
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Object getBean(Class classObj){
    	return ContextHolder.getContext().getBean(classObj);
    }
}
