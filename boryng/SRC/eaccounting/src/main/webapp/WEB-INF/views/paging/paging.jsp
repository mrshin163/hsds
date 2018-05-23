<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageTag" uri="PageTag"%>

paging




<h2> 유저 목록 </h2>
	<table>
		<c:forEach var="data" items="${codeList}">
			<tr>
				<td>${data.grpCodeId}</td>            
				          
			</tr>
		</c:forEach>
	</table>
	
	
	<pageTag:pageTag list="${codeList}" script="pageNavi" policyName="default"/>