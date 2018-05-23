<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page session="false" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


	<h2> 그룹 코드 목록 </h2>
	<table>
		<tr>
			<th>아이디</th>
			<th>비밀번호</th>
			<th>이름</th>
			
		</tr>
		<c:forEach var="member" items="${memberList}">
			<tr>
				<td>${member.id}</td>
				<td>${member.password}</td>
				<td>${member.name}</td>
			</tr>	
		</c:forEach>
	</table>
	
	
	<%-- <h2> 공통 코드 목록 </h2>
	
	조회 :
	
		<form id="codeSearch" action="/code/inqireGrpCodeList" method="get">
			<select id="commGrpCodeList">
				<c:forEach var="code" items="${commGrpCodeList}">
				<option value="${code.grpCodeId}">${code.grpCodeName}</option>
				</c:forEach>
			</select>
			<button type="submit">SEARCH</button>
		</form>
	
	<table style="border: 1px solid #000">
		<tr>
			<th>공통코드그룹ID</th>
			<th>공통코드그룹명</th>
			<th>코드유형코드</th>
			<th>쿼리ID</th>
			<th>설명</th>
			<th>사용여부</th>
			<th>삭제여부</th>
			<th>최초등록일</th>
			<th>최초등록자</th>
			<th>최종수정일</th>
			<th>최종수정자</th>
		</tr>
		<c:forEach var="code" items="${commCodeList}">
			<tr>
				<td>${code.grpCodeId}</td>
				<td>${code.code}</td>
				<td>${code.comCode}</td>
				<td>${code.codeName}</td>
				<td>${code.description}</td>
				<td>${code.useYn}</td>
				<td>${code.delYn}</td>
				<td>${code.regDate}</td>
				<td>${code.regUser}</td>
				<td>${code.updtDate}</td>
				<td>${code.updtUser}</td>
			</tr>
		</c:forEach>
	</table> --%>
	
	
	
	<%-- 메시지 테스트 : ${cf:getMessage('alert.delete',null) } --%>
</body>
</html>