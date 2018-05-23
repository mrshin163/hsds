package com.bizsp.framework.util.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import sun.org.mozilla.javascript.internal.regexp.SubString;

import com.bizsp.framework.security.vo.LoginUserVO;

public abstract class SecurityUtils {

    public static Authentication getCurrentAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static LoginUserVO getAuthenticatedUser(){
        return (LoginUserVO) getCurrentAuthentication().getPrincipal();
    }
    
    public static boolean hasRole(String role){
    	
    	String auths = getAuthenticatedUser().getAuths();
    	
    	if(auths == null) return false;
    	
    	if(auths.indexOf(role) > -1){
    		return true;
    	}
    	return false;
    }

}