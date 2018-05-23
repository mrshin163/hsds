<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<!-- 검색조건 -->
<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2><i class="icon iconReport"></i><span>영업 카드처리현황</span></h2>
		<p class="location">Home > 리포트 > 영업 카드처리현황</p>
	</div>
	
	<!-- 검색조건 -->	
	<div class="srchCondition twoLine">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<div class="left">
				<ul class="allSearch">
					<li class="allSearchCate">
						<span class="inputTit">사용 기간</span>
						<input class="date-picker" id="firstCal" type="text" />
						<label for="firstCal" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>
						~
						<input class="date-picker" id="secondCal" type="text" />
						<label for="secondCal" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>
					</li>
					<li>
						<span class="inputTit">처리상태</span>
						<input type="text" /> 
					</li>
					<li class="allSearchCate1">
						<span class="inputTit">회계전표번호</span>
						<input type="text" />
					</li>
				
					<li class="allSearchCate">
						<span class="inputTit">부서코드</span>
						<input type="text" />
					</li>
					<li class="allSearchCate2">
						<span class="inputTit">부서명</span>
						<input type="text" /> 
					</li>
					<li>
						<span class="inputTit">소유자</span>
						<input type="text" /> 
					</li>
					<li class="allSearchCate1">
						<span class="inputTit">상호</span>
						<input type="text" /> 
					</li>
					
				</ul>
			</div>
			<div class="right">
				<button type="button" class="btnSrch">검색</button>
			</div>
		</div>
	</div>
	<!-- //검색조건 -->
	<!-- 검색내역 -->
	<div class="srchList">
		<strong class="tit">검색 내역<em>2건</em></strong>
		
		
		<p class="btnDow"><button type="button" class="btnSrch">다운로드</button></p>
	
		<div class="scrollTable" id="scrollTable" >
			<div class="inner">
				<table class="defaultType defaultType02">
					<colgroup>
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
					</colgroup>
					<thead>
						<tr>
							<th>NO.</th>
							<th>처리상태</th>
							<th>결재상태</th>
							<th>카드회사</th>
							<th>카드번호</th>
							<th>사용일</th>
							<th>승인시간</th>
							<th>승인번호</th>
							<th>상호</th>
							<th>사업자번호</th>
							<th>합계금액</th>
							<th>부서</th>
							<th>소유자</th>
							<th>주소</th>
							<th>업종</th>
							<th>계정구분</th>
							<th>세부계정</th>
							<th>사용내역</th>
							<th>회계전표번호</th>
							<th>전표상태</th>
							<th>결재문서번호</th>
						</tr>
					</thead>
					<tbody id="reportDataArea">

					</tbody>
					<tfoot>
						<tr>
							<td class="total">총액</td>
							<td ></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td id="totalAmount"></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
	<!-- //검색내역 -->
</div>