package com.bizsp.eaccount.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bizsp.eaccount.admin.service.LogService;
import com.bizsp.eaccount.admin.vo.LogVo;
import com.bizsp.eaccount.log.vo.MenuInfo;
import com.bizsp.eaccount.menu.service.MenuService;
import com.bizsp.framework.security.vo.LoginUserVO;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private MenuService menuService;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LogVo logVo = new LogVo();
        
        HttpSession session = request.getSession();
        SecurityContextImpl context = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        
        if(context!=null){
        	
    		// MenuInfo는  static으로 선언
            if(MenuInfo.getMENU_INFO()==null){        	
            	// key : 주소, value : 메뉴정보
            	Map<String, Map<String,Object>> menu = new HashMap<String, Map<String, Object>>();
            	List<Map<String, Object>> menuList =  menuService.getMenuList();
            	for(Map<String, Object> map : menuList){
            		if((String) map.get("URL")!=null)
            			menu.put((String) map.get("URL"), map);
            	}            	
            	MenuInfo.setMENU_INFO(menu);
            }
        	
            // 주소값으로 메뉴정보 가져오기
            Map<String, Object> menuInfo = MenuInfo.getMENU_INFO().get(request.getServletPath());

        	if(menuInfo!=null){
	        	logVo.setMENU_DEPTH1((String) menuInfo.get("UPMENUID"));
	        	logVo.setMENU_DEPTH2((String) menuInfo.get("MENUID"));
	        	logVo.setMENU_NAME((String) menuInfo.get("MENUNAME"));
        	}else{
	        	logVo.setMENU_NAME("HOME");
        	}
        	
        	LoginUserVO loginUserVo = (LoginUserVO) context.getAuthentication().getPrincipal();
        	logVo.setC_CD(loginUserVo.getCompanyCode());
        	logVo.setEMP_ID(loginUserVo.getUserId());
        	logVo.setEMP_NM(loginUserVo.getName());
        	logVo.setHANDPHONE(loginUserVo.getMobileNo());
        	logVo.setDIVISION_CODE(loginUserVo.getDeptCode());
        	logVo.setDIVISION_NAME(loginUserVo.getDeptName());
        	logVo.setPOSITION(loginUserVo.getTitleAliasName());
        	logVo.setCALLNAME(loginUserVo.getDutyName());
        	logVo.setOFFICE(loginUserVo.getTelNo());
        	logVo.setLOG_IP(request.getRemoteAddr());
        	logVo.setLOG_URL(request.getServletPath());


        	System.out.println(logVo.toString());
        	// 로그 insert
        	logService.insertLog(logVo);        	
        }
        
        return super.preHandle(request, response, handler);
    }
}
