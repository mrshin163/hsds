<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">

//미상신 내역 리스트
function getApprovalRqList(date, cardUsingTypeCode, cardUserCode, search, cardUserName, pageNo){
	
	if(date == '') date = "all";
	if(cardUserCode == '') cardUserCode = "1";
	if(cardUserName == '') cardUserName = "all";

	$.ajax({
 		type : 'GET',
 		url : '/rest/approvalRqApi/approvalRqList/' + date + "/" + cardUsingTypeCode + "/" + cardUserCode + "/" + pageNo + "/" + cardUserName,
 		dataType : "json",
 		contentType: 'application/json',
 		beforeSend : function() {
			$('.loading').css('display', 'block');
		},
		complete : function() {
			$('.loading').css('display', 'none');
		},
 		success : function(jsonData) {
 			var approvalRqHtml = "";
 			
 			if(jsonData.totalRow > 0){
 			
	 			$.each(jsonData.approvalRqList, function(key, approvalRqList) {
	 				
	 				var cardUsingInputHtml = '';
	 				
	 				var cardUsingCode = approvalRqList.CARD_USING_TP_CD;
	 				if(cardUsingCode == null || cardUsingCode == '1') {
	 					cardUsingInputHtml = "<td><input type='checkbox' value='1' name='useType'></td>" 
	 										+ "<td><a href='javascript:;' class='btnRoundRobin'><i class='icon iconRoundRobin'></i>품의서 작성</a></td>";
	 				} else {
	 					cardUsingInputHtml = "<td><input type='checkbox' value='" + cardUsingCode + "' name='useType' checked='checked'></td>"
	 										+ "<td></td>";
	 				}
	 				
	 				approvalRqHtml += "<tr id='approval" + key + "' authDate = '" + approvalRqList.AUTH_DATE + "' requestAmount='" + approvalRqList.REQUEST_AMOUNT + "'"
	 								+ " georaeStat='" + approvalRqList.GEORAE_STAT +  "'" + "georaeColl = '" + approvalRqList.GEORAE_COLL + "'" + "ownerId='" + approvalRqList.OWNER_ID + "'" 
	 								+ "nreqUserId='" + approvalRqList.NREQ_USER_ID + "' cardNum='" + approvalRqList.CARD_NUM + "' authNum='" + approvalRqList.AUTH_NUM + "'" 
			 						+ ">"
									+ "<td class='authDate'>" + dateFormat(approvalRqList.AUTH_DATE) + "</td>"
									+ "<td style='text-align: left;text-overflow:ellipsis;white-space:nowrap;word-wrap:normal'>" + approvalRqList.MERC_NAME + "</td>"
									+ "<td class='currency'>￦" + approvalRqList.REQUEST_AMOUNT.toLocaleString().split(".")[0] + "</td>"
									+ "<td class='cardName'>" + approvalRqList.CARD_NAME + "</td>"
									+ "<td class='cardNum'>" + approvalRqList.CARD_NUM + "</td>"
									+ "<td>" + approvalRqList.OWNER_NM + "</td>"
									+ "<td class='authNum'>" + approvalRqList.AUTH_NUM + "</td>"
									+ cardUsingInputHtml
									+ "</tr>";
					}
				);
 			} else {
 				approvalRqHtml = "<tr><td height='100px' colspan='9'>일치하는 결과가 없습니다.</td></tr>";
 			}
 			
 			$("#approvalRqList").html(approvalRqHtml);
 			$("#listTotal").html("￦" + jsonData.amountTotal.toLocaleString().split(".")[0]);
 			$("#totalRow").html(jsonData.totalRow);
 			if(search){
	 			$("#pagenationSection").pagination({
	 		        items: jsonData.totalRow,
	 		        itemsOnPage: 10,
	 		        cssStyle: 'light-theme',
	 		       	hrefTextPrefix: "javascript:getApprovalRqList('" + date + "','" + cardUsingTypeCode + "','" + cardUserCode + "', false,'" + cardUserName + "',",
	 				hrefTextSuffix: ");"
	 			 });
 			}
 		},
 		error : function(err) {
 			bootbox.alert("처리중 장애가 발생하였습니다." + err);
 		}
 	});
}

$(function(){
	
	makeYearMonth("sYear", "sMonth", "Y");

	// 메뉴 진입시 해당월로 검색된 상태로 보여줘여 함. 디폴트검색조건으로 바로 조회되게 한다.
	var t_year = new Date().getFullYear();	// 년
	var t_month = new Date().getMonth()+1;	// 월 (실제 월 보다 1이 적게 나와 강제로 +1 적용)
	if(t_month<10) t_month = "0" + t_month;	// 10 보다 작은 수 인 경우 두자리 맞추기 위해 강제로 0 string 추가
	getApprovalRqList(t_year + t_month, 1, 1, true, "all", 1);	// 페이지 진입시 RqList 바로 호출 처리

	$("#approvalSearch").click(function(){
		var year = $("#sYear option:selected").val();
		var month = $("#sMonth option:selected").val();
		var cardUsingTypeCode = $("#cardUsingTypeCode").val();
		var cardUserCode = $("#cardUserCode").val();
		var cardUserName = $("#cardUserName").val();

		getApprovalRqList(year + month, cardUsingTypeCode, cardUserCode, true, cardUserName, 1);
	})
	
	
	//리스트 페이징 처리 
/*     $("#pagenationSection").pagination({
        items: ${totalRow},
        itemsOnPage: 10,
        cssStyle: 'light-theme',
       	hrefTextPrefix: "javascript:getApprovalRqList('all', '1', '1', 'all', ",
		hrefTextSuffix: ");"
   }); */
   
	var approvalInfo = "";
	
	
	//미상신 리스트 품의서 작성 
	$(document).on("click", '#approvalRqList > tr > td > a.btnRoundRobin', function(){
		var curApprovalId 	= $(this).parents().parents().attr("id");
		
		var cardNum 		= $("#" + curApprovalId).attr("cardNum");
		var authNum 		= $("#" + curApprovalId).attr("authNum");
		var authDate 		= $("#" + curApprovalId).attr("authDate");
		var requestAmount 	= $("#" + curApprovalId).attr("requestAmount");
		var georaeStat 		= $("#" + curApprovalId).attr("georaeStat");
		var georaeColl		= $("#" + curApprovalId).attr("georaeColl");
		var ownerId 		= $("#" + curApprovalId).attr("ownerId");
		var nreqUserId		= $("#" + curApprovalId).attr("nreqUserId");
		
		approvalInfo = {
				"cardNum"				: cardNum,
				"authDate"				: authDate,
				"authNum"				: authNum,
				"requestAmount"			: requestAmount,
				"georaeStat"			: georaeStat,
				"georaeColl"			: georaeColl,
				"ownerId"				: ownerId,
				"nreqUserId"			: nreqUserId
				};
		
		bootbox.confirm('선택하신 건으로 품의서를 작성 하시겠습니까?', function(result){
    		if(result) {
				 $.ajax({
		    		type : 'POST',
		    		url : '/rest/approvalRqApi/insertApproval',
		    		dataType : "json",
		    		data : JSON.stringify(approvalInfo),
		    		contentType: 'application/json',
		    		beforeSend : function() {
		    			$('.loading').css('display', 'block');
		    		},
		    		complete : function() {
		    			$('.loading').css('display', 'none');
		    		},
		    		success : function(data) {
		    			$(location).attr('href','/approvalRq/approvalRqItem/' + data.approvalId + '/N');
		    		},
		    		error : function(err) {
		    			alert("처리중 장애가 발생하였습니다." + err);
		    		}
		    	});
			 } 
		 });
	})
	
	//미상신 리스트 용도 변경 
	$(document).on('click', "#approvalRqList > tr > td > input[name='useType']", function(){
		var params = '';
		
		if($(this).is(":checked")){
			$(this).val("2");	
		} else {
			$(this).val("1");
		}
		
		$target = $(this).parents("tr");
		
		var cardNum 			= $target.attr('cardNum');
		var authDate 			= $target.attr('authDate');
		var authNum 			= $target.attr('authNum');
		var georaeStatus 		= $target.attr('georaeStat');
		var requestAmount 		= $target.attr('requestAmount');
		var georaeColl			= $target.attr('georaeColl');
		var cardUsingTypeCode 	= $(this).val();
		
		params = {"cardNum" : cardNum, "authDate": authDate, "authNum" : authNum, "georaeStatus" : georaeStatus, "requestAmount" : requestAmount, "georaeColl" : georaeColl, "cardUsingTypeCode" : cardUsingTypeCode};
		
		bootbox.confirm("선택한 항목의 용도를 변경 하시겠습니까?", function(result){
			if(result){
			jQuery.ajax({
				type : 'POST',
				url : '/rest/approvalRqApi/updateCardUsingTypeStatus',
				dataType : "json",
				contentType: 'application/json',
				data : JSON.stringify(params),
				success : function() {
					bootbox.alert('선택된 항목의 용도가 변경 되었습니다.', function(){
						location.reload();	
					});
				},
				error : function(err) {
					alert("처리중 장애가 발생하였습니다." + err);
				}
			});
			}
		})
	})
})
</script>

<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2><i class="icon iconIngDoc"></i> <span>결재 상신</span></h2>
		<p class="location">Home > 결재상신</p>
	</div>

	<!-- 검색조건 -->
	<div class="srchCondition twoLine">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<div class="left">
				<ul>
					<li class="odd"><span class="inputTit">사용년월</span> <select name="" id="sYear" class="customSelect">
							<option value="2014">2014</option>
					</select> <select name="" id="sMonth" class="customSelect">
							<option value="1월">1월</option>
					</select></li>
					<li class="even"><span class="inputTit">카드사용자</span> <select name="" id="cardUserCode" class="customSelect">
							<option value="1">본인</option>
							<option value="2">대체상신요청</option>
					</select></li>
					<li class="odd"><span class="inputTit">사용용도</span> <select name="" id="cardUsingTypeCode" class="customSelect">
							<option value="all">전체</option>
							<option value="1" selected>상신</option>
							<option value="2">상신제외</option>
					</select></li>
					<li class="even"><span class="inputTit">소유자</span><input type="text" id="cardUserName"></li>
				</ul>
			</div>
			<div class="right">
				<button type="button" class="btnSrch" id="approvalSearch">검색</button>
			</div>
		</div>
	</div>
	<!-- //검색조건 -->
	<!-- 검색내역 -->
	<div class="srchList" style="height:470px">
		<strong class="tit left">카드 미상신 내역<em id="totalRow">${totalRow}</em>건</strong>
		<!-- <button type="button" class="btnSrch" id="btnUseType" style="float: right; margin-bottom: 20px">용도 업데이트</button> -->
		<div class="innerBox">
			<table class="defaultType">
				<thead>
					<tr>
						<th width="80px">사용일</th>
						<th width="200px">상호</th>
						<th>합계금액</th>
						<th>카드회사</th>
						<th>카드번호</th>
						<th>소유자</th>
						<th>승인번호</th>
						<th width="60px">상신제외</th>
						<th>품의서</th>
					</tr>
				</thead>
				<tbody id="approvalRqList">
				
					<c:if test="${empty approvalRqList}">
					<tr><td height='100px' colspan='9'>일치하는 결과가 없습니다.</td></tr>
					</c:if>
					
					<c:forEach var="approvalRqList" items="${approvalRqList}" varStatus="status">
					<tr id="approval${status.count}" cardNum="${approvalRqList.CARD_NUM}" amount=${approvalRqList.REQUEST_AMOUNT} authNum="${approvalRqList.AUTH_NUM}" authDate="${approvalRqList.AUTH_DATE}" requestAmount=${approvalRqList.REQUEST_AMOUNT} georaeStat="${approvalRqList.GEORAE_STAT}" georaeColl="${approvalRqList.GEORAE_COLL}" ownerId = "${approvalRqList.OWNER_ID}" nreqUserId = "${approvalRqList.NREQ_USER_ID}" >
						<td class="authDate"><fmt:parseDate value="${approvalRqList.AUTH_DATE}" var="dateFmt" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd"/></td>
						<td style="text-align: left;text-overflow:ellipsis;white-space:nowrap;word-wrap:normal">${approvalRqList.MERC_NAME}</td>
						<td class="currency requestAmout"><fmt:formatNumber value="${approvalRqList.REQUEST_AMOUNT}" type="currency" groupingUsed="true"/></td>
						<td class="cardNum">${approvalRqList.CARD_NUM}</td>
						<td>${approvalRqList.OWNER_NM}</td>
						<td class="authNum">${approvalRqList.AUTH_NUM}</td>
						<td>
							<!-- 2015.2.9 체크 박스로 변경  (체크스 개인용도) -->
								<c:choose>
									<c:when test="${approvalRqList.CARD_USING_TP_CD eq null || approvalRqList.CARD_USING_TP_CD == '1'}"><input type="checkbox" value="1" name="useType"></c:when>
									<c:otherwise><input type="checkbox" value="${approvalRqList.CARD_USING_TP_CD}" name="useType" checked="checked"></c:otherwise>
								</c:choose>
							<!-- <button type="button" class="customCheck">개인용도 <i class="iconCustomCheck"></i></button> -->
						</td>
						<td><a href="javascript:;" class="btnRoundRobin"><i class="icon iconRoundRobin"></i>품의서 작성</a></td>
					</tr>
					</c:forEach>
				</tbody>
				<tfoot >
					<tr>
						<td colspan="2" class="total" style="text-align: center;">총계</td>
						<td class="currency" id="listTotal" style="text-align: right;padding-right:6px;"><fmt:formatNumber value="${amountTotal}" type="currency" groupingUsed="true" /></td>
						<td colspan="6"></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	<p id="pagenationSection"></p>
	<!-- //검색내역 -->
</div>


