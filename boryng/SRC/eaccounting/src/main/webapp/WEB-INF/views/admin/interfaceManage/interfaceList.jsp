<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">

function executeInterface(interfaceName){

	if(interfaceName == "") {
		bootbox.alert("인터페이스를 지정하십시오.");
		return;
	}
	
	bootbox.confirm("지금 인터페이스 수행하시겠습니까? ", function(result){
		
		if(result){

			jQuery.ajax({
				type : 'GET',
				url : '/rest/interfaceApi/execute/' + interfaceName ,
				dataType : "json",
				beforeSend:function(){
					$('#loader').css("display", "block");
					$('#dataBox').animate({opacity: 0.5});
			    },
			    complete:function(){
			    	 $('#loader').css("display", "none");
			    	 $('#dataBox').animate({opacity: 1});
			    },
				success : function(jsonData) {
					bootbox.alert("인터페이스를 수행하였습니다. 결과  [ " +jsonData+ " ]건");
				},
				error : function(err) {
					bootbox.alert("처리중 장애가 발생하였습니다." + err);
				}
			});
		}
		
	})

}
</script>
<div class="content">

	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2><i class="icon iconSystemlManage02"></i> <span>인터페이스관리</span></h2>
		<p class="location">Home > 인터페이스관리</p>
	</div>
	
	<ul class="interfaceMenu">
		<li><a href="javascript:executeInterface('IF_ACCOUNT2_CD');" id="" >세부계정</a></li>
		<li><a href="javascript:executeInterface('IF_ACCOUNT_FAV');" id="" >자주 사용하는 세부계정</a></li>
		<li><a href="javascript:executeInterface('IF_CARD');" id="" >법인카드</a></li>
		<li><a href="javascript:executeInterface('IF_CUSTOMER');" id="" >거래처</a></li>
		<li><a href="javascript:executeInterface('IF_DEPT');" id="" >부서</a></li>
		<li><a href="javascript:executeInterface('IF_DUTY_CD');" id="" >직책코드</a></li>
		<li><a href="javascript:executeInterface('IF_TITLE_ALIAS_CD');" id="" >호칭코드</a></li>		
		<li><a href="javascript:executeInterface('DO_INSERT_USER_AUTH');" id="" >신규사용자 기본권한 부여</a></li>
	</ul>

</div>


</html>