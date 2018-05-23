<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageTag" uri="PageTag"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.bizsp.framework.security.vo.LoginUserVO"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.Authentication"%>

<script>
$(function(){
	var date = new Date();
	var today = date.getFullYear() + "-" + numberPad(date.getMonth(), 2) + "-" + numberPad(date.getDate(), 2);
	$('#startDate').val(today);
	$('#endDate').val(today);

	getList();
	
})


function getList(){
	var startDate = $('#startDate').val().replace(/-/g, '');
	var endDate = $('#endDate').val().replace(/-/g, '');

}

</script>

<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2><i class="icon iconNoticeManage02"></i> <span>로그조회</span></h2>
		<p class="location">Home > 기타관리 > 로그조회</p>
	</div>
	
	<!-- 검색 조건 S -->
	<div class="srchCondition">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<div class="left">
				<ul>
					<li class="odd">
						<span class="inputTit">조회기간</span>
						<input type="text" id="startDate" class="date-picker" />
						<label for="startDate" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>
						&nbsp;~&nbsp;
						<input type="text" id="endDate" class="date-picker" />
						<label for="endDate" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>
					</li>
				</ul>
			</div>
			<div class="right">
				<button type="button" class="btnSrch" onclick="getList();">조회</button>
			</div>
		</div>
	</div>
	<!-- 검색 조건 E -->
	
	<!-- 검색내역 -->
	<div class="srchList">
		<strong class="tit">일별통계 접속횟수 - (인원)</strong>
		<div class="innerBox" id="dataBox">
			<div class="btns">
				<a href="" id="btnExcelDown1" class="btnReg" onclick="">Excel</a>
			</div>
			<table class="defaultType"  style="table-layout:fixed;">
				<colgroup>
					<col width="" />
					<col width="" />
					<col width="" />
					<col width="" />
					<col width="" />
				</colgroup>
				<thead>
					<tr>
						<th>날짜</th>
						<th>로그인</th>
						<th>로그아웃</th>
						<th>결재상신</th>
						<th>품의서</th>
						<th>공용카드 결재상신 요청</th>
						<th>심포지움 결재요청</th>
						<th>심포지움 결재상신</th>
						<th>결재상신 - 결재할문서</th>
						<th>결재상신 - 진행중문서</th>
						<th>결재상신 - 반려문서</th>
						<th>결재상신 - 완료문서</th>
						<th>결재할문서 - 진행중문서</th>
						<th>결재할문서 - 완료문서</th>
						<th>개인별 카드처리현황</th>
						<th>부서별 카드처리현황</th>
						<th>전체 카드처리현황</th>						
					</tr>
				</thead>
				<tbody id="dayLogogData">

				</tbody>
			</table>							
		</div>					
	</div>	
	<!-- //검색내역 -->
	
	<!-- 검색내역 -->
	<div class="srchList">
		<strong class="tit">부서별 접속인원현황</strong>
		<div class="innerBox" id="dataBox">
			<div class="btns">
				<a href="" id="btnExcelDown1" class="btnReg" onclick="">Excel</a>
			</div>
			<table class="defaultType"  style="table-layout:fixed;">
				<colgroup>
					<col width="" />
					<col width="" />
					<col width="" />
				</colgroup>
				<thead>
					<tr>
						<th>부서</th>
						<th>총인원</th>
						<th>접속인원</th>
						<th>비접속인원</th>
					</tr>
				</thead>
				<tbody id="deptLogogData">

				</tbody>
			</table>							
		</div>					
	</div>	
	<!-- //검색내역 -->
	
	<!-- 검색내역 -->
	<div class="srchList">
		<strong class="tit">로그인정보</strong>
		<div class="innerBox" id="dataBox">
			<div class="btns">
				<a href="" id="btnExcelDown1" class="btnReg" onclick="">Excel</a>
			</div>
			<table class="defaultType"  style="table-layout:fixed;">
				<colgroup>
					<col width="" />
					<col width="" />
					<col width="" />
					<col width="" />
					<col width="" />
					<col width="" />
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>성명</th>
						<th>부서명</th>
						<th>호칭</th>
						<th>사내전화</th>
						<th>휴대폰</th>					
					</tr>
				</thead>
				<tbody id="loginLogogData">

				</tbody>
			</table>							
		</div>					
	</div>	
	<!-- //검색내역 -->
	
	
</div>