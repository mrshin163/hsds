<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageTag" uri="PageTag"%>

<script type="text/javascript">

function getUserList(userName, deptName, pageNo, search){

	if(userName == "") userName = "all";
	if(deptName == "") deptName = "all";
	if(pageNo == null) pageNo = 1;
	
	jQuery.ajax({
		type : 'GET',
		url : '/rest/userApi/userList/' + userName + '/' + deptName + "/" + pageNo,
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
			var userListHtml = "";
			$.each(jsonData.userList, function(key, userList) {
				userListHtml += "<tr>"
						+ "<td><input type='checkbox' /></td>"
						+ "<td>" + userList.userId + "</td>"
						+ "<td>" + userList.comCode  + "</td>"
						+ "<td>" + userList.comName +"</td>"
						+ "<td>" + userList.deptCode + "</td>"
						+ "<td>" + userList.deptName + "</td>"
						+ "<td>" + userList.userName + "</td>"
						+ "</tr>";
				}
			);
			$('#userList').html(userListHtml);
			$('#totalRow').html(jsonData.totalRow);
			
			if(search){
				$("#pagenationSection").pagination({
			        items: jsonData.totalRow,
			        itemsOnPage: 10,
			        cssStyle: 'light-theme',
			       	hrefTextPrefix: "javascript:getUserList('" + userName + "','" + deptName + "',",
					hrefTextSuffix: ");"
			    });
			}
		},
		error : function(err) {
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});
}

jQuery(function(){
	$("#testText").autocomplete({
        source : function(request, response) {
            $.ajax({
                url : "/rest/userApi/nameSearch/" + $("#testText").val(),
                type : "GET",
                dataType : "json",
                success : function(data) {
 
                    var result = data;
                    response(result);
                },
                error : function(data) {
                	bootbox.alert("에러가 발생하였습니다.")
                }
            });
        }
    });
	
	var userName = "";
	var deptName = "";
	
	$("#pagenationSection").pagination({
        items: ${totalRow},
        itemsOnPage: 10,
        cssStyle: 'light-theme',
       	hrefTextPrefix: "javascript:getUserList('" + userName + "','" + deptName + "',",
		hrefTextSuffix: ");"
    });
})
</script>

<style>
.ui-autocomplete { 
    position: absolute; 
    cursor: default; 
    height: 200px; 
    overflow-y: scroll; 
    overflow-x: hidden;}
</STYLE>
	
	<%-- 메시지 테스트 : ${cf:getMessage('alert.delete',null) } --%>

<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2>
			<span>사용자리스트</span>
		</h2>
		<p class="location">Home > 사용자관리 > 사용자리스트</p>
	</div>
	<!-- 검색조건 -->
	<div class="srchCondition">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<div class="left">
				<ul>
					<li><span class="inputTit">권한종류</span> 
					
						<form id="testform" name="testform">
		        			<input type="text" id="testText" placeholder="이름을 입력하세요" value="">
		        			<input type="button" value="Click" />
    					</form>
						<%-- <select name="authSelect" id="authSelect" class="customSelect">
							<option value="all">권한종류</option>
							<c:forEach var="auth" items="${authList}">
								<option value="${auth.authId}">${auth.authName}</option>
							</c:forEach>
						</select> --%>
					</li>
				</ul>
			</div>
			<div class="right">
				<button type="button" class="btnSrch" id="userAuthSearch">검색</button>
			</div>
		</div>
	</div>
	<!-- //검색조건 -->

	<!-- 검색내역 -->
	<div class="srchList">
		<div id="loader" style="position: absolute; display: none;"><img src="/images/admin/ajax-loader.gif"></div>
		<strong class="tit">유저 리스트 <em id="totalRow">${totalRow}</em>건</strong>
		<div class="innerBox" id="dataBox">
			<div class="btns">
				<a href="" class="btnReg" onclick="showModal('newReg');return false;">신규등록</a>
			</div>
			<table class="defaultType">
				<colgroup>
					<col width="4%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="20%" />
					<col width="20%" />
					<col width="" />
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" /></th>
						<th>사번</th>
						<th>회사코드</th>
						<th>회사이름</th>
						<th>부서코드</th>
						<th>부서명</th>
						<th>이름</th>
					</tr>
				</thead>
				<tbody id="userList">
					<c:forEach var="user" items="${userList}">
						<tr>
							<td><input type="checkbox" /></td>
							<td>${user.userId}</td>            
							<td>${user.comCode}</td>           
							<td>${user.comName}</td>           
							<td>${user.deptCode}</td>          
							<td>${user.deptName}</td>          
							<td>${user.userName}</td>          
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<p id="pagenationSection"></p>
	<!-- //검색내역 -->
</div>

<!-- 신규등록 팝업 -->
<div class="layer" id="newReg">
	<div class="layerInner layerInner02">
		<strong class="firstTit">사용자 선택</strong>
		<!-- 권한자 선택 -->
		<div class="long">
			<div class="topSrch">
				<span class="name">부서명</span> <input type="text" id="deptNameSearch">
				<span class="name">성명</span> <input type="text" id="userNameSearch">
				<button type="button" class="btnSrch" id="btnUserSearch">검색</button>
			</div>
			<div class="scrollTable-y" id="scrollTable-y" style="height: 300px">
				<div class="inner">
					<table class="defaultType">
						<colgroup>
							<col width="" />
							<col width="" />
							<col width="" />
							<col width="" />
							<col width="" />
						</colgroup>
						<thead>
							<tr>
								<th><input type="checkbox" /></th>
								<th>사번</th>
								<th>성명</th>
								<th>부서</th>
								<th>직급</th>
							</tr>
						</thead>
						<tbody id="userSearchList">
							<tr>
								<td colspan="5" style="text-align: center;">부서명과 성명으로 검색하여 주십시요.</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="btns">
				<button type="button" class="btnSave" id="btnUserAuthReg">등록</button>
			</div>
		</div>
		<!-- //권한자 선택 -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('newReg');return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- //신규등록 팝업 -->


</html>