<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="cf" uri="CommonFunction"%>

<c:if test="${not empty param.error}">
<script>
     $(function() {
    	 bootbox.alert('<p style="font-size:12px; line-height:24px">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}<br/>사용자 정보를 정확하게 입력해 주세요</p>', function() {
         });
     });
</script>
</c:if>

<div class="loginBox">
	<strong class="loginSystem"><img src="images/login_breas.png"/></strong>
	<p class="loginLogo">
		<img src="images/logo_login.png" />
	</p>
	<form method="post" id="loginfrm" action="/loginProcess.biz">
	<div class="loginForm">
		<ul>
			<li><label for="userId"><img src="images/login_id.gif" alt="아이디"/></label> <input type="text" id="userId" name="userId"/></li>
			<li>
				<label for="password"><img src="images/login_pw.gif" alt="암호"/></label> <input type="password" id="loginpwd" name="password" />
				<button type="submit" class="btnLogin"><i class="icon iconLogin"></i>로그인</button>
			</li>
		</ul>
	</div>
	</form>
	<div class="loginCheck">
		<!-- <input type="checkbox" id="loginIng" /><label for="loginIng">로그인 상태 유지</label> -->
		<span>본 사이트는<br/><br/>크롬 / 익스플로러10 이상 에 최적화 되어있습니다.</span>
	</div>
</div>



<c:forTokens items="" delims=""></c:forTokens>

<%-- 메시지 테스트 : ${cf:getMessage('alert.delete',null)} --%>





