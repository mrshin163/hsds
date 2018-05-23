<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">

function makeHtml(list){
	var html = '';
	$(list).each(function(idx, card){
		html += '<tr>'
					+'<td>' + checkNull(card.cardOwnCd) 		+ '</td>'
					+'<td>' + checkNull(card.cardNo) 		+ '</td>'
					+'<td>' + checkNull(card.cardTpNm) 		+ '</td>'
					+'<td>' + checkNull(card.cardStNm) 		+ '</td>'
					+'<td>' + checkNull(card.gyejwaNo)			+ '</td>'
					+'<td>' + checkNull(card.pBankBranchNm)			+ '</td>'
					+'<td>' + checkNull(card.pBankUserNm)			+ '</td>'
					+'<td>' + checkNull(card.gyeljeDay)			+ '</td>'
					+'<td>' + checkNull(card.joinDate).substr(0, 10)			+ '</td>'
					+'<td>' + checkNull(card.expireDate).substr(0, 10)			+ '</td>'
					+'<td style="text-align:right;">￦' + Number(checkNull(card.limitAmount)).toLocaleString().split(".")[0]		+ '</td>'
					+'<td>' + checkNull(card.jijungUserNm)	+ '</td>'
					+'<td style="text-align:left;">'	+ checkNull(card.remark)		+ '</td>'
					+'<td>'	+ checkNull(card.nreqUser)		+ '</td>'
					+'<td>' + checkNull(card.commUser)			+ '</td>'
				+'</tr>';
	
	});
	
	return html;
}

function emptyIsAll(str){
	if(str==null || str=='') return 'all'
	return str;	
}

function getReportList(){
	var cardNo = emptyIsAll($('#cardNo').val());
	var cardStCd = emptyIsAll($('#cardStCd').val());
	
	$.ajax({
 		type : 'GET',
 		url : '/rest/reportApi/IndividualCard/'+ cardNo +'/' + cardStCd,
 		dataType : "json",
 		contentType: 'application/json',
 		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
 		success : function(jsonData) {
			$('#reportDataArea').html(makeHtml(jsonData.individualCardList));
			$('#totalRow').text(jsonData.totalRow);
 		},
 		error : function(err) {
 			bootbox.alert("처리중 오류가 발생하였습니다." + err);
 		}
 	});

}

$(document).ready(function () {
	getComCdGrpId('CARD_ST_CD','cardStCd','Y');
	getReportList();
	
	$('#btnSrch').click(function(){
		getReportList();
	})
	
});


</script>


<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<!-- 검색조건 -->
<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2><i class="icon iconReport"></i><span>개인카드 조회</span></h2>
		<p class="location">Home > 리포트 > 개인카드 조회</p>
	</div>
	
	<!-- 검색조건 -->
	<div class="srchCondition twoLine">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<div class="left">
				<ul>
					<li class="odd">
						<span class="inputTit">카드번호</span> 
						<input id="cardNo" type="text" style="width:135px;"/>
					</li>
					<li class="even">
						<span class="inputTit">카드상태</span>
						<select id="cardStCd" class="customSelect">
						</select> 
					</li>
				</ul>
			</div>
			<div class="right">
				<button id="btnSrch" type="button" class="btnSrch">검색</button>
			</div>
		</div>
	</div>
	<!-- //검색조건 -->
	<!-- 검색내역 -->
	<div class="srchList">
		<strong class="tit">검색 내역<em id="totalRow"></em>건</strong>
		<div class="scrollTable" id="scrollTable" >
			<div class="inner">
				<table class="defaultType defaultType02">
					<colgroup>
						<col width="" />
						<col width="120px" />
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
						<col width="200px" />
						<col width="" />
						<col width="" />
					</colgroup>
					<thead>
						<tr>
							<th >카드구분</th>
							<th >카드번호</th>
							<th>카드회사</th>
							<th>카드상태</th>
							<th>계좌번호</th>
							<th>은행지점</th>
							<th>예금주</th>
							<th>결재일</th>
							<th>가입일</th>
							<th>만기일</th>
							<th>한도금액</th>
							<th>카드관리자</th>
							<th>비고</th>
							<th>대체상신자</th>
							<th>공용카드담당</th>
						</tr>
					</thead>
					<tbody id="reportDataArea">
					</tbody>
				</table>
			</div>
		</div>
	<!-- //검색내역 -->
	</div>
</div>