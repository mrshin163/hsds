<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">

$(document).ready(function () {
	$('.date-picker').datepicker();
	$(".customSelect").selectbox();
	
	$('#startAuthDate').change(function(){
		var val = $(this).val().replace(/-/g, '');
		var date = new Date();
		date.setDate(date.getDate() - 60);		
		date = date.getFullYear() + "" + numberPad((date.getMonth() + 1), 2) + "" + numberPad(date.getDate(),2);
		
		if(date > val){
			bootbox.alert('60일 이전은 선택할 수 없습니다.', function(){
				$('#startAuthDate').val('');
				$('#startAuthDate').focus();	
			});			
		}		
	});
	
	
	getList();
});

function validForm() {

	if ($('#startAuthDate').val() == '') {
		bootbox.alert('시작일을 입력하십시오.');
		$('#startAuthDate').focus();
		return false;
	}
	if ($('#endAuthDate').val() == '') {
		bootbox.alert('종료일을 입력하십시오.');
		$('#endAuthDate').focus();
		return false;
	}
	if ($('#startAuthDate').val() > $('#endAuthDate').val()) {
		bootbox.alert('시작일 종료일을 확인하여 주십시오.');
		$('#startAuthDate').focus();
		return false;
	}
 	return true;
}

function getList(){
	if (!validForm()) {
		return false;
	}

	//검색조건 보관
 	var fn = $('#financeNo').val();
	if (fn!='') $('#h_financeNo').val(fn) ;
	else        $('#h_financeNo').val('all') ;
	
	$("#h_startAuthDate").val($("#startAuthDate").val().replace(/-/g,'')) ;
	$("#h_endAuthDate").val($("#endAuthDate").val().replace(/-/g,'')) ;
	$("#h_cardUserCode").val($("#cardUserCode").val()) ;

	$.ajax({
 		type : 'GET',
 		url : '/rest/reportApi/CardProcessStatusList/'+$("#h_startAuthDate").val().replace(/-/g,'')+'/'+$("#h_endAuthDate").val().replace(/-/g,'')+'/'+$('#h_cardUserCode').val()+'/'+$('#h_financeNo').val()+'/all',
 		dataType : "json",
 		contentType: 'application/json',
 		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
 		success : function(jsonData) {
 			var reportHtml = "";
 			var labelStatusType = "";
 			var statusCode = "";
 			$.each(jsonData, function(key, cardProcessStatusVo) {
 				if (cardProcessStatusVo.approvalStatusCd == 'AAAA' )
 					labelStatusType = '01';
 				else if (cardProcessStatusVo.approvalStatusCd == 'NREQ' || cardProcessStatusVo.approvalStatusCd == 'NING' || cardProcessStatusVo.approvalStatusCd == 'SR' || cardProcessStatusVo.approvalStatusCd == 'SING')
 					labelStatusType = '02';
 				else if (cardProcessStatusVo.approvalStatusCd == 'NREJ')
 					labelStatusType = '03';
 				else if (cardProcessStatusVo.approvalStatusCd == 'NAPR' || cardProcessStatusVo.approvalStatusCd == 'EAPR' || cardProcessStatusVo.approvalStatusCd == 'SAPR')
 					labelStatusType = '04';
 				else if (cardProcessStatusVo.approvalStatusCd == 'ZZZZ')
 					labelStatusType = '05';
 				
 				reportHtml  += "<tr>"
				+ "<td><button class='labelStatus type"+labelStatusType+"' onclick=\"showModal03('cardDetailPopLyaer');cardDetailList('" + cardProcessStatusVo.approvalStatusCd + "') \">" + cardProcessStatusVo.approvalStatusNm + "</button></td>"
				+ "<td>" + cardProcessStatusVo.cnt + "</td>"
				+ "<td class='currency'>\\" + Number(cardProcessStatusVo.requestAmount).toLocaleString().split(".")[0] + "</td>"
				+ "</tr>";
 			});
 			$("#reportList").html(reportHtml );
 		},
 		error : function(err) {
 			bootbox.alert("처리중 장애가 발생하였습니다." + err);
 		}
 	});
}


function cardDetailList(statusCode){
	
	/* {startAuthDate}/{endAuthDate}/{cardUserCode}/{financeNo}/{approvalStatusCd} */
	
	var startAuthDate	 	= $("#frm input[name=h_startAuthDate]").val().replace(/-/g,'');
	var endAuthDate			= $("#frm input[name=h_endAuthDate]").val().replace(/-/g,'');
	var cardUserCode		= $("#frm input[name=h_cardUserCode]").val();
	var financeNo			= $("#frm input[name=h_financeNo]").val();
	
	console.log(startAuthDate + " : " + endAuthDate + " : " + cardUserCode + " : " + financeNo + " : ");
	
	$.ajax({
 		type : 'GET',
 		url : "/rest/reportApi/CardProcessList/" + startAuthDate + "/" + endAuthDate + "/" + cardUserCode + "/" + financeNo + "/" + statusCode,
 		dataType : "json",
 		contentType: 'application/json',
 		beforeSend:function(){
 	    },
 	    complete:function(){
 	    },
 		success : function(jsonData) {
 			var cardDetailHtml = "";
 			$.each(jsonData, function(key, cardDetail){
 				cardDetailHtml += "<tr>"
 							+ "<td>" + (key+1) + "</td>"
 							+ "<td>" + dateFormat(cardDetail.AUTH_DATE) + "</td>"
 							+ "<td style='text-align:left;'>" + checkNull(cardDetail.MERC_NAME) + "</td>"
 							+ "<td>" + checkNull(cardDetail.CARD_NUM) + "</td>"
 							+ "<td>" + checkNull(cardDetail.CARD_NAME) + "</td>"
 							+ "<td>" + checkNull(cardDetail.RQ_DEPT_NM) + "</td>"
 							+ "<td>" + checkNull(cardDetail.RQ_USER_NM) + "</td>"
 							+ "<td>" + checkNull(cardDetail.ACCOUNT_NM) + "</td>"
 							+ "<td>" + checkNull(cardDetail.ACCOUNT2_NM) + "</td>"
 							+ "<td class='currency'>\\" + Number(cardDetail.REQUEST_AMOUNT).toLocaleString().split(".")[0] + "</td>"
 							+ "<td style='text-align:left;'>" + checkNull(cardDetail.DETAILS) + "</td>"
 							+ "<td style=\"cursor:pointer;\" onClick=\"javascript:openCalculateForm('" + checkNull(cardDetail.APPROVAL_ID) + "', null, 'report')\">" + checkNull(cardDetail.APPROVAL_ID) + "</td>"
 							+ "<td>" + checkNull(cardDetail.JUNPYO_ST_NM) + "</td>"
 							+ "<td>" + checkNull(cardDetail.FINANCE_NO) + "</td>"
 							+ "</tr>";
 			});
 			
 			$("#cardDetailList").html(cardDetailHtml);
 		},
 		error : function(err) {
 			bootbox.alert("처리중 장애가 발생하였습니다." + err);
 		}
 	});
}



</script>

	<!-- //컨텐츠 -->
	<div class="content">
		<!-- 타이틀 영역 -->
		<div class="titArea subTitArea">
			<h2><i class="icon iconStatus"></i> <span>개인별 카드처리현황</span></h2>
			<p class="location">Home > 레포트 > 개인별 카드처리현황</p>
		</div>
	
		<!-- 검색조건 -->
		<div class="srchCondition twoLine">
			<strong class="tit">검색 조건</strong>
			<div class="innerBox">
				<form id="frm" name="frm">
				<input type="hidden" id="h_startAuthDate" name="h_startAuthDate" value="" />
				<input type="hidden" id="h_endAuthDate" name="h_endAuthDate" value="" />
				<input type="hidden" id="h_cardUserCode" name="h_cardUserCode" value="" />
				<input type="hidden" id="h_financeNo" name="h_financeNo" value="" />
				<div class="left">
					<ul>
						<li class="odd">
							<span class="inputTit">카드승인일</span>
							<input type="text" id="startAuthDate" class="date-picker" value='<fmt:parseDate value="${defaultWorkduration.DEFAULT_START_DATE}" var="dateFmt" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd"/>'/>
							<label for="startAuthDate" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>
							~
							<input type="text" id="endAuthDate" class="date-picker" value='<fmt:parseDate value="${defaultWorkduration.DEFAULT_END_DATE}" var="dateFmt" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd"/>'/>
							<label for="endAuthDate" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>
						</li>
						<li class="even">
							<span class="inputTit">카드사용자</span> 
							<select name="" id="cardUserCode" class="customSelect">
								<option value="1">본인</option>
								<option value="2">대체자</option>
							</select>
						</li>
						<li class="odd">
							<span class="inputTit">회계전표번호</span>
							<input type="text" id="financeNo" maxLength="12"/> 
						</li>
						<li class="even">
						</li>
					</ul>
				</div>
				<div class="right">
					<button type="button" class="btnSrch" onClick="javascript:getList();">검색</button>
				</div>
				</form>
			</div>
		</div>
		<!-- //검색조건 -->
		<!-- 검색내역 -->
		<div class="srchList">
			<div class="innerBox">
				<table class="defaultType">
					<colgroup>
						<col width="30%" />
						<col width="30%" />
						<col width="*" />
					</colgroup>
					<thead>
						<tr>
							<th>처리상태</th>
							<th>건수</th>
							<th>카드사용 총액 합</th>
						</tr>
					</thead>
					<tbody id="reportList">
					</tbody>
				</table>
			</div>
		</div>
		<!-- //검색내역 -->
	</div>
	<!-- //컨텐츠 -->
	
	
	
<!-- 카드내역조회 팝업 -->
	<div class="layer" id="cardDetailPopLyaer">
		<div class="layerInner">
			<strong class="firstTit">카드내역 조회</strong>
			<!-- 카드사용내역 -->
			<div class="long">
				<div class="innerBox02">
					<div class="layerScroll">
						<table class="defaultType">
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
							</colgroup>
							<thead>
								<tr>
									<th>NO</th>
									<th>사용일</th>
									<th>상호</th>
									<th>카드번호</th>
									<th>카드이름</th>
									<th>부서</th>
									<th>상신자</th>
									<th>계정구분</th>
									<th>세부계정</th>
									<th>금액</th>
									<th>사용내역</th>
									<th>결재문서번호</th>
									<th>전표상태</th>
									<th>회계전표번호</th>
								</tr>
							</thead>
							<tbody id="cardDetailList">
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- //카드사용내역 -->
		</div>
		<button type="button" class="btnLayerClose" onclick="hideModal03('cardDetailPopLyaer');return false;"><img src="/images/btn_close.png"/></button>
	</div>
	<!-- //카드내역조회 팝업 -->
