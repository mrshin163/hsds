<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">

$(document).ready(function () {
	makeYearMonth('year', 'month', 'Y');

	$("#allChk").click(function() {
		if ($(this).prop("checked")) {
			$("input[name='approvalId']").attr("checked", true);
		} else {
			$("input[name='approvalId']").attr("checked", false);
		}
	});

	$("#itemArea").on('click', 'a', function(e) {
		e.preventDefault();

		var flag = $(this).children().attr('name');
		var val = $(this).children().attr('value');

		if (flag == 'seminarReport') {
			showModal('seminarReportPopLayer');
			var arr = val.split('『');
			for (var i = 0; i < arr.length; i++) {
				var tmp = arr[i].split('=');
				if (tmp[0] == 'amount') {
					$("#seminarReportView td[name='" + tmp[0] + "']").html("￦" + Number(tmp[1]).toLocaleString().split(".")[0]);
				} else {
					$("#seminarReportView td[name='" + tmp[0] + "']").html(tmp[1]);
				}
			}
		} else if (flag == 'ftrCd') {
			getRuleDocumentData(val);
			showModal('ruleDocumentPopLayer');
		} else if (flag == 'account2Nm') {
			openAccount2PopLayer($("#accountCdArea").text(), null, 'calculate');
		} else if (flag == 'attachId') {
			getAttachFileData(val);
			showModal('attachViewPopLayer');
		}
	});
	
	//부서 검색 
	autoCompleteSearch("deptListForDept");
	$("#btnDeptSearch").click(function(){
		
		if($("#deptListForDept").val() == '' && $("#deptListForDeptCd").val() == ''){
			bootbox.alert("부서코드나 부서명을 입력 하여 주십시요.");
			return false;
		}
		
		var deptBeforeName = $("#deptListForDept").val();
		var deptBeforeCd = $("#deptListForDeptCd").val();
		var deptCd = emptyCheck(deptBeforeCd);
		var deptName = emptyCheck(deptBeforeName);
		var deptSearchListHtml = "";
		
		$.ajax({
			type : 'GET',
    		url : '/rest/deptApi/deptSearchList/' + encodeURIComponent(encodeURIComponent(deptName)) + '/' + encodeURIComponent(deptCd),
    		dataType : "json",
    		contentType: 'application/json',
    		beforeSend:function(){
    	    },
    	    success : function(jsonData) {
    			if(jsonData.length!=0){
	    			$.each(jsonData, function(key, deptInfo) {
	    				deptSearchListHtml += "<tr style='cursor:pointer' deptCode='" + deptInfo.deptCode + "' deptName = '" + deptInfo.deptName + "'>"
		    								+ "	<td>" + deptInfo.deptCode + "</td>"
		    								+ "	<td>" + deptInfo.deptName + "</td>"
		    								+ "</tr>";
	    			})
	    			$("#deptSearchList").html(deptSearchListHtml);
    			}else{
    				$("#deptSearchList").html('<td colspan="5" style="text-align: center;">검색된 데이터가 없습니다.</td>');
    			}
    			
    		},
			complete:function(){
				
				$("#deptSearchList tr").each(function(){
					$(this).on("click", function(){
						$("#deptName").val($(this).attr("deptName"));
						$("#deptCode").val($(this).attr("deptCode"));
						hideModal("deptListPopLayer");
					})
				})
    	    },
    		error : function(err) {
    			bootbox.alert("처리중 장애가 발생하였습니다." + err);
    		}
		})
	})
	
	
	$(".productCd").change(function(){
		var curValue = $('option:selected',this).text().replace(/(\(.+?\) )/, '');
		$("#productCode").val($(this).val());
	})
		
	getComCdGrpId("PROD_CD", "productCode", "Y");
	
	getSymposiumRqList(null);
	
});

function emptyCheck(str){
	if(str==null || str==''){
		return 'all';
	}
	return str;	
}

function restoreSymposium(){
	var reqCnt = 0;
	var sCnt = 0;

	$("input:checked[name='approvalId']").each(function(idx) {
		reqCnt++;
	});

	if (reqCnt == 0) {
		bootbox.alert('반려할 데이터를 선택해주세요.');
		return false;
	}

	bootbox.confirm("일괄 반려 하시겠습니까?", function(result) {
		if (result) {
			$('.loading').css('display', 'block');
			$("input:checked[name='approvalId']").each(function(idx) {
				$.ajax({
					type : 'DELETE',
					url : '/rest/approvalRqApi/restoreSymposium/' + $(this).val(),
					contentType: 'application/json',
					dataType : "json",
					async : false,
					success : function(data) {
						if (data.code == 'S') {
							sCnt++;
						}
					},
					error : function(e) {
						console.log(e);
					}
				});
			});
			$('.loading').css('display', 'none');
		}
		bootbox.alert(reqCnt + '건 중 ' + sCnt + ' 건이 반려 되었습니다.', function () {
			location.reload();
		});
	});
}

function validForm() {
	var year = $("#year").val();
	var month = $("#month").val();

	if (year == '' && month == '') {
		$("#authDate").val('all');
		return true;
	} else {
		$("#authDate").val($.trim($("#year").val()) + '' + $.trim($("#month").val()));
		return true;
	}
}


function getSymposiumRqList(pageNo){
	if (!validForm()) {
		return false;
	}
	if (pageNo == null) {
		pageNo = 0;
	}
	
	var deptCode =  $("#deptCode").val();
	var productCode = $("#productCode").val()==null?'all':$("#productCode").val();	
	
	if(deptCode == '') deptCode = "all";
	if(productCode == '') productCode = "all";
	
	 $.ajax({
 		type : 'GET',
 		url : '/rest/approvalRqApi/symposiumRqList/' + $("#authDate").val() + '/' + pageNo + "/" + deptCode + "/" + productCode,
 		dataType : "json",
 		contentType: 'application/json',
 		beforeSend:function(){
 	    },
 	    complete:function(){
 	    },
 		success : function(jsonData) {
 			var symposiumRqHtml = "";
 			var symposiumRqApprovalId = "";
 			
 			
 			/* typeof value === "undefined" */
 			
 			
 			if(jsonData.totalRow > 0){
	 			$.each(jsonData.symposiumRqList, function(key, symposiumRq) {
	 				
	 				symposiumRqHtml += "<tr>";
	 				
	 				if (symposiumRqApprovalId != symposiumRq.S_APPROVAL_ID)
	 					symposiumRqHtml +=  "<td><input type='checkbox' name='approvalId' value='"+symposiumRq.S_APPROVAL_ID+"' /></td>";
					else
	 					symposiumRqHtml +=  "<td></td>";
	 					
					symposiumRqHtml += "" 
					+ "<td class='authDate'>" + dateFormat(symposiumRq.AUTH_DATE) + "</td>"
					+ "<td style='text-align: left;text-overflow:ellipsis;white-space:nowrap;word-wrap:normal'>" + checkNull(symposiumRq.MERC_NAME) + "</td>"
					+ "<td class='currency'>￦" + symposiumRq.REQUEST_AMOUNT.toLocaleString().split(".")[0] + "</td>"
					+ "<td>" + checkNull(symposiumRq.USER_NM) + "</td>"
					+ "<td>" + checkNull(symposiumRq.BUDGET_DEPT_NM) + "</td>"
					/* + "<td>" + checkNull(symposiumRq.ACCOUNT_NM) + "</td>" */
					+ "<td>" + checkNull(symposiumRq.ACCOUNT2_NM) + "</td>"
					+ "<td>" + checkNull(symposiumRq.PRODUCT_NM) + "</td>"
					+ "<td>" + checkNull(symposiumRq.CUSTOMER_NM) + "</td>"
					/* + "<td>" + checkNull(symposiumRq.FTR_CD) + "</td>" */
					/* + "<td><a href='#' class='btnView'><i class='icon iconView' name='seminarReport' value='" + symposiumRq.S_APPROVAL_ID + "'>돋보기</i></a></td>" */
					+ "<td>" + checkNull(symposiumRq.DETAILS) + "</td>"
					/* + "<td><a href='#' ><i class='iconFile' name='attachId' value='" + symposiumRq.S_APPROVAL_ID + "|1'>첨부</i></a></td>" */
					+ "</tr>";
				});
	 			
 			}else{
 				symposiumRqHtml = "<tr><td height='100px' colspan='18'>일치하는 결과가 없습니다.</td></tr>";
 			} 			
 			
 			$("#symposiumRqList").html(symposiumRqHtml);
 			$("#totalRow").html(jsonData.totalRow);
 			$("#requestAmountTotal").html("\\"+jsonData.requestAmountTotal.toLocaleString().split(".")[0]);
 			
 			$("#symposiumRqList").on('click', 'a', function(e) {
 				var flag = $(this).children().attr('name');
 				var val = $(this).children().attr('value');
 				
 				if (flag == 'seminarReport') {
 					showModal('seminarReportPopLayer');
 					var arr = val.split('『');
 					for (var i = 0; i < arr.length; i++) {
 						var tmp = arr[i].split('=');
 						if (tmp[0] == 'amount') {
 							$("#seminarReportView td[name='" + tmp[0] + "']").html("￦" + Number(tmp[1]).toLocaleString().split(".")[0]);
 						} else {
 							$("#seminarReportView td[name='" + tmp[0] + "']").html(tmp[1]);
 						}
 					}
 				} else if (flag == 'ftrCd') {
 					getRuleDocumentData(val);
 					showModal('ruleDocumentPopLayer');
 				} else if (flag == 'account2Nm') {
 					openAccount2PopLayer($("#accountCdArea").text(), null, 'calculate');
 				} else if (flag == 'attachId') {
 					getAttachFileData(val);
 					showModal('attachViewPopLayer');
 				}
 	 		})
 		},
 		error : function(err) {
 			bootbox.alert("처리중 장애가 발생하였습니다." + err);
 		}
 	})
 	
}


function insertSymposium(){
	
	var params = [];
	
	$("#symposiumRqList tr td").find("input[name=approvalId]:checked").each(function(){
		params.push($(this).val())
	})
	
	if(params.length == 0){
		bootbox.alert("사용내역을 선택 후 품의서를 작성해 주세요.");
		return false;
	}
	
	bootbox.confirm('선택하신 건으로 품의서를 작성 하시겠습니까?', function(result){
		if(result) {
			 $.ajax({
	    		type : 'POST',
	    		url : '/rest/approvalRqApi/insertSymposiumApproval',
	    		dataType : "json",
	    		data : JSON.stringify(params),
	    		contentType: 'application/json',
	    		beforeSend:function(){
	    	    },
	    	    complete:function(){
	    	    },
	    		success : function(data) {
	    			$(location).attr('href','/approvalRq/approvalSymposiumRqItem/' + data.approvalId);
	    		},
	    		error : function(err) {
	    			bootbox.alert("처리중 장애가 발생하였습니다." + err);
	    		}
	    	});
		 } 
	 });
}

</script>

<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2>
			<i class="icon iconIngDoc"></i> <span>심포지움 결재요청 리스트 조회</span>
		</h2>
		<p class="location">Home > 결재상신 > 심포지움 결재 상신</p>
	</div>

	<!-- 검색조건 -->
	<div class="srchCondition">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<form id="frm" name="frm">
			<input type="hidden" id="authDate" name="authDate" value="" />
			<div class="left">
				<ul>
					<li class="odd">
						<span class="inputTit">카드승인년월</span>
						<select name="year" id="year" class="customSelect">
						</select>
						<select name="month" id="month" class="customSelect">
						</select>
					</li>
					<li class="customInput">
						<span>부서명</span>
						<input type="hidden" name="deptCode" id="deptCode" value="">
						<input type="text" name="deptName" id="deptName" disabled="disabled" value=""> 
						<a class="btnView" onclick="showModal('deptListPopLayer');return false;"><i class="icon iconView">돋보기</i></a>
					</li>
					<li class="odd">
						<span class="inputTit">제품군/명</span>
						<select name="productCode" id="productCode"></select>
					</li>
				
				</ul>
			</div>
			<div class="right">
				<button type="button" class="btnSrch" onclick='javascript:getSymposiumRqList(1); return false;'>검색</button>
			</div>
			</form>
		</div>
	</div>
	<!-- //검색조건 -->
	<!-- 검색내역 -->
	<div class="srchList" style="height:470px">
		<strong class="tit">검색 내역<em><span id="totalRow">${totalRow}</span></em>건</strong>
		<div class="innerBox">
			<div class="btns">
				<button type="button" class="btnRoundRobin" onclick="javascript:insertSymposium(); return false;"><i class="icon iconRoundRobin"></i>품의서 작성</button>
				<button type="button" class="btnRoundRobin" onclick="javascript:restoreSymposium(); return false;"><i class="icon iconRoundRobin"></i>반려</button>
			</div>
			<div class="scrollTable" >
				<div class="inner">
					<table class="defaultType defaultType02">
						<colgroup>
							<col width="20px" />
							<col width="100px" />
							<col width="300px" />
							<col width="100px" />
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
								<th><input type="checkbox" id="allChk" /></th>
								<th>사용일</th>
								<th>상호</th>
								<th>합계금액</th>
								<th>담당자</th>
								<th>예산부서</th>
								<th>계정구분</th>
								<!-- <th>세부계정</th> -->
								<th>제품군/명</th>
								<th>거래처</th>
								<!-- <th>공정경쟁규약</th> -->
								<!-- <th>결과보고서</th> -->
								<th>사용내역</th>
								<!-- <th>첨부</th> -->
							</tr>
						</thead>
						<tbody id="symposiumRqList">
							<tr>
								<td colspan="18" height="120px">상단의 검색 조건으로 검색하여 주십시요</td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td></td>
								<td colspan="2" class="total" style="text-align: center;">총계</td>
								<td class="currency" style="text-align: right" id="requestAmountTotal"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<!-- <td></td> -->
								<td></td>
								<td></td>
								<!-- <td></td> -->
								<!-- <td></td> -->
								<td></td>
								<!-- <td></td> -->
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
	<p id="pagenationSection"></p>
	<!-- //검색내역 -->
</div>