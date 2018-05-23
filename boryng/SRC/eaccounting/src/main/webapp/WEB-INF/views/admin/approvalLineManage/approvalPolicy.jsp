<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.ui-autocomplete { 
    position: absolute; 
    cursor: default; 
    height: 200px; 
    overflow-y: scroll; 
    overflow-x: hidden;}
</STYLE>

<script type="text/javascript">

function emptyCheck(str){
	if(str==null || str==''){
		return 'all';
	}
	return str;	
}

jQuery(document).ready(function(){	
	commCodeComboList('ACCOUNT_CD', 'accountCode');
	getApprovalPolicy(null, true);
	autoCompleteSearch("deptListForDept");
	
	$('#btnSrch').click(function(){
		getApprovalPolicy(null, true);	
	});
	
	$('#btnAdd').click(function(){
		bootbox.confirm('등록하시겠습니까?', function(result){
			if(result){
				addPolicy();
			}
		});			
	});	
	
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
    			$('.loading').css("display", "block");
    	    },
    	    complete:function(){
    			$('.loading').css("display", "none");
    			$("#deptSearchList tr").each(function(){
					$(this).on("click", function(){
						$("#deptCd").val($(this).attr("deptCode"));
						$("#deptNm").val($(this).attr("deptName"));
						hideModal("deptListPopLayer");
					})
				})
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
    		error : function(err) {
    			bootbox.alert("처리중 장애가 발생하였습니다." + err);
    		}
		})
	})
	
});

//코드아이디로 콤보박스를 만든다
function commCodeComboList(grpCodeId, elementId){
	var commCodeListHtml = "";
	
	$.ajax({
	    url : "/rest/codeApi/commCodeList/" + grpCodeId + "/0",
        type : "GET",
        dataType : "json",
        beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
        success : function(jsonData) {
        	
        	$.each(jsonData.codeList, function(key, codeList) {
        		commCodeListHtml += "<option class='inputTit' value='" + codeList.code + "'>" + codeList.codeName +"</option>" ;
        	})
        	
        	$('select[name="' + elementId+'"]').append(commCodeListHtml);
      	},
        error : function(data) {
            bootbox.alert("에러가 발생하였습니다.")
        }
    }).done(function(){
    	//ajax List 가져온후 jquery ui 적용
    	$(".customSelect").selectbox();
    });
}

//////  Group Code List 조회
function getApprovalPolicy(pageNo, search){
	if (pageNo == null) {
		pageNo = 1;
	}		
	
	var policyNm = $('#srchPolicyNm').val()==''?'all':$('#srchPolicyNm').val();
	var accountCd = $('#srchAccountCd').val();
	var deptNm = $('#srchDeptNm').val()==''?'all':$('#srchDeptNm').val();
	
	$.ajax({
		type : 'GET',
		url : '/rest/policyApi/getApprovalPolicy/' + policyNm + '/' + accountCd + '/' + deptNm + '/' + pageNo,		
		dataType : "json",
		contentType: 'application/json',
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
			$('td[name="tdPolicyTitle"]').on('click',function(){
	    		var policyId 	= $(this).parents('tr').find('td').eq(0).text();
	    		$(location).attr('href','/admin/approvalManage/approvalLine/' + policyId);
			});
	    },
		success : function(jsonData) {
			var policyDataHtml = "";
			$.each(jsonData.approvalPolicy, function(key, policy) {
				policyDataHtml += '<tr>'
									+ '<td>' 												+ checkNull(policy.policyId) 						+ '</td>'
									+ '<td style="text-align:left; cursor:pointer; padding-left:10px;" name="tdPolicyTitle">' 	
																							+ checkNull(policy.policyNm) 						+ '</td>'
									+ '<td>' 												+ checkNull(policy.accountNm)						+ '</td>'
									+ '<td class="currency" style="padding-right:10px;">' 	+ Number(checkNull(policy.minAmount)).toLocaleString().split(".")[0]		+ '</td>'
									+ '<td class="currency" style="padding-right:10px;">' 	+ Number(checkNull(policy.maxAmount)).toLocaleString().split(".")[0] 	+ '</td>'
									+ '<td style="text-align:left; padding-left:10px;">' 	+ checkNull(policy.deptNm)	 						+ '</td>'
									+ '<td>' 												+ checkNull(policy.useYn)	 						+ '</td>'
									+ '</tr>';
			});
			$('#policyData').html(policyDataHtml);
			
			if(search){
			    $("#pagenationSection").pagination({
			        items: jsonData.totalRow,
			        itemsOnPage: 10,
			        cssStyle: 'light-theme',
			       	hrefTextPrefix: "javascript:getApprovalPolicy(",
					hrefTextSuffix: ");"
			    });
			}
			$('#totalRow').html(Number(jsonData.totalRow).toLocaleString().split(".")[0]);			

		},
		error : function(e) {
			console.log(e);
		}
	});	
}

//////  필수값 체크
function checkValidation(){
	var array = ['policyId','policyNm','minAmount','maxAmount'];
	var flag = true;
	$(array).each(function(index, id){
		if($('#'+id).val()==''){
			bootbox.alert('필수 입력값이 비었습니다.');
			flag = false;
			return false;
		}		
	});	
	if(flag){
		return flag;
	}
}

function addPolicy(){ 
	if(!checkValidation() || !validationNum()){
		return;
	}		
	
	var url = '/rest/policyApi/addPolicy';
	
	var policyId 	= $('#policyId').val();
	var policyNm 	= $('#policyNm').val();
	var accountCd 	= $('#accountCd').val();
	var minAmount 	= $('#minAmount').val();
	var maxAmount 	= $('#maxAmount').val();
	var useYn		= $('input[name="useYn"]:checked').val();
	var description = $('#description').val();
	var deptCd 		= $('#deptCd').val();
	
	var params = {
			 "policyId" 	: policyId
			,"policyNm" 	: policyNm
			,"accountCd" 	: accountCd
			,"minAmount" 	: minAmount
			,"maxAmount" 	: maxAmount
			,"useYn"		: useYn
			,"description" 	: description
			,"deptCd" 		: deptCd		 
	 };
	
	
	$.ajax({
		type : 'POST',
		url : url,		
		dataType : "json",
		contentType: 'application/json',
		data : JSON.stringify(params),
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			if(jsonData.result=='S'){
				getApprovalPolicy(null, true);
				bootbox.alert(jsonData.message);
				hideModal('policyPopup');
			}else{
				bootbox.alert(jsonData.message);
			}
			
		},
		error : function(e) {
			bootbox.alert("처리중 오류가 발생하였습니다."+e);
			console.log(e);
		}
	});	
}

function clearPopup(){
	$('#policyId').val('');
	$('#policyNm').val('');
	$('#accountCd').val('1');
	$('#accountCd').parents('td').find('.sbSelector').text('세미나비');
	$('#minAmount').val('');
	$('#maxAmount').val('');
	$('#description').val('');
	$('#useY').attr('checked', true);
	$('#deptCd').val('');
	$('#deptNm').val('');
}

function openPopup(flag){
	clearPopup();
	$('#popTitle').text('등록');
	showModal('policyPopup');
	return false;	
}
//////입력값을 숫자만 받게 해주는 함수
function checkNum(){
     if((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode != 8 && event.keyCode != 46)){
    	 event.returnValue = false;
     }
}

////// 최저, 최고금액 체크
function validationNum(){
	if(!(Number($('#minAmount').val().replace(/,/g,"")) < Number($('#maxAmount').val().replace(/,/g,"")))){
		bootbox.alert('최저, 최고금액을 확인하여 주십시오.');
		return false;
	}
	return true;
}


</script>

<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2><i class="icon iconApprovalManage02"></i><span>결재선 유형 관리</span></h2>
		<p class="location">Home > 결재선관리 > 결재선 유형 관리</p>
	</div>
	<!-- 검색조건 -->
	<div class="srchCondition ">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<div class="left">
				<ul>
					<li class="odd">
						<span class="inputTit">결재선명</span>
						<input type="text" id="srchPolicyNm" value=""/><!--  <a href="" class="btnView"><i class="icon iconView">돋보기</i></a> -->
					</li>
					<li class="even">
						<span class="inputTit">계정구분</span>
						<select id="srchAccountCd" class="customSelect" name="accountCode">
							<option value="all">전체</option>
						</select>
					</li>
					<li class="odd">
						<span class="inputTit">부서명</span>
						<input type="text" id="srchDeptNm" value=""/><!--  <a href="" class="btnView"><i class="icon iconView">돋보기</i></a> -->
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
		<div id="loader" style="position: absolute; display: none; top:45%; left:50%;"><img src="/images/admin/ajax-loader.gif"></div>
		<strong class="tit">결재선 유형목록 <em id="totalRow">${totalRow}</em>건</strong>
		<div class="innerBox" id="dataBox">
			<div class="btns">
				<button class="btnReg" onclick="openPopup('add');">신규등록</button>
			</div>
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
				</colgroup>
				<thead>
					<tr>
						<th>결재선 ID</th>
						<th>결재선명</th>
						<th>계정구분</th>
						<th>최저금액</th>
						<th>최고금액</th>
						<th>예산부서명</th>
						<th>사용여부</th>
					</tr>
				</thead>
				<tbody id="policyData">
				</tbody>
			</table>
		</div>
	</div>
	<div id="pagenationSection"></div>
	<!-- //검색내역 -->
</div>

	<!-- 신규등록 팝업 -->
	<div class="layer" id="policyPopup">
		<div class="layerInner layerInner02">
			<strong class="firstTit">결재선 유형<span id="popTitle">등록</span></strong>
			<!-- 결재선 유형등록 -->
			<div class="long">
				<table class="horizontalTable">
					<colgroup>
						<col width=""/>
						<col width=""/>
					</colgroup>
					<tbody>
						<tr>
							<th>결재선 ID *</th>
							<td><input id="policyId" type="text" style="width:96%" maxlength="16"/></td>
						</tr>
						<tr>
							<th>결재선명 *</th>
							<td><input id="policyNm" type="text" style="width:96%"  maxlength="32"/></td>
						</tr>
						<tr>
							<th>계정구분</th>
							<td><select id="accountCd"  name="accountCode" class="customSelect" style="width:96%"></select></td>
						</tr>
						<tr>
							<th>최저금액 *</th>
							<td><input id="minAmount" type="text" onkeydown="checkNum(this);" style="width:95%; text-align:right; padding-right:3px;" /></td>
						</tr>
						<tr>
							<th>최고금액 *</th>
							<td><input id="maxAmount" type="text" onkeydown="checkNum(this);" style="width:95%; text-align:right; padding-right:3px;" /></td>
						</tr>
						<tr>
							<th>예산부서</th>
							<td>								
								<span class="customInput"> 
									<input type="text" name="deptNm" id="deptNm" style="width:86%" disabled="disabled">
									<input id="deptCd" type="hidden"/>
									<a class="btnView" onClick="showModal('deptListPopLayer');return false;"><i class="icon iconView">돋보기</i></a>
								</span>							
							</td>
						</tr>
						<tr>
							<th>사용여부</th>
							<td colspan="3">
								<input id="useY" name="useYn" type="radio" value="Y"/><label for="useY">사용</label>
								<input id="useN" name="useYn" type="radio" value="N"/><label for="useN">사용안함</label>
							</td>
						</tr>
						<tr>
							<th>설명</th>
							<td><textarea id="description" style="width:96%; height:50px;"  maxlength="64"></textarea></td>
						</tr>
						
					</tbody>
				</table>
				<div class="btmbtns">
					<button id="btnAdd" class="btnReg" onclick="">등록</button>
				</div>
			</div>
			<!-- //결재선 유형등록 -->
		</div>
		<button type="button" class="btnLayerClose" onclick="hideModal('policyPopup');return false;"><img src="../../images/btn_close.png"/></button>
	</div>
	<!-- //신규등록 팝업 -->