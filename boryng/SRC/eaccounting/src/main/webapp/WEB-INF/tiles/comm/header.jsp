<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="com.bizsp.framework.security.vo.LoginUserVO" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!-- Spring Security 사용자 정보 공통 부분 -->
<%
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	 
	LoginUserVO principal = (LoginUserVO)auth.getPrincipal();
	String userId = "";
	String name = "";
	String companyCode = "";
	String companyName = "";
	String deptCode = "";
	String deptName = "";
	String titleCode = "";
	String titleName = "";
	String dutyCode = "";
	String dutyName = "";
	String titleAliasCode = "";
	String titleAliasName = "";
	String virtualUserYn = "";
	
	if(principal != null && principal instanceof LoginUserVO){
		userId = principal.getUsername();
	    name = principal.getName()==null?"":principal.getName();
	    companyCode = principal.getCompanyCode();
	    companyName = principal.getCompanyName()==null?"":principal.getCompanyName();
	    deptCode = principal.getDeptCode();
	    deptName = principal.getDeptName()==null?"":principal.getDeptName();
	    titleCode = principal.getTitleCode();
	    titleName = principal.getTitleName()==null?"":principal.getTitleName();
	    dutyCode = principal.getDutyCode();
	    dutyName = principal.getDutyName()==null?"":principal.getDutyName();
	    titleAliasCode = principal.getTitleAliasCode();
	    titleAliasName = principal.getTitleAliasName()==null?"":principal.getTitleAliasName();
	    virtualUserYn = principal.getVirtualUserYn();
	}
	
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	int dayNum = cal.get(Calendar.DAY_OF_WEEK);
	String day = "";
	switch(dayNum){
	case 1:
	    day = "일";
	    break ;
	case 2:
	    day = "월";
	    break ;
	case 3:
	    day = "화";
	    break ;
	case 4:
	    day = "수";
	    break ;
	case 5:
	    day = "목";
	    break ;
	case 6:
	    day = "금";
	    break ;
	case 7:
	    day = "토";
	    break ;
	}


%>

<!-- HEADER -->
<div class="header">
	<div class="headerInner">
		<div class="logo">
			<h1>
				<a href="/"><img src="/images/logo_boryung.png" alt="boryeong" /></a>
			</h1>
		</div>
		<div class="headerTop">
			<div class="left">
				<span class="subLogo"><h2><img src="/images/sub_breas.png" alt="BR-EAS" /></h2></a></span> <span class="data"><%=new SimpleDateFormat("yyyy.MM.dd").format(new Date()) %> <%=day %></span>
			</div>
			<div class="right">
				<span class="user"><i class="icon iconUser"></i> <em><%=deptName%> ::: <%=name%> <%=titleAliasName%> (<%=userId%>)</em></span> <span class="util"><a href="logout.biz">Log out</a></span>
			</div>
		</div>
	</div>
</div>
<!-- //HEADER -->
