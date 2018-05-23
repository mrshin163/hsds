<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
var options = {};
var index;

function emptyCheck(str){
	if(str==null || str==''){
		return 'all';
	}
	return str;	
}

jQuery(document).ready(function(){
	$('input[name="useYn"]:input[value="${policy.useYn}"]').attr("checked", true);
	accountComboList('ACCOUNT_CD', 'accountCode');
	approvalComboList('APPROVAL_LINE_DEF_CD', 'approvalLineCd');	
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
    			$('.loading').css("display", "block");
    	    },
    	    complete:function(){
    	    	$("#deptSearchList tr").each(function(){
					$(this).on("click", function(){
						$("#deptCd").val($(this).attr("deptCode"));
						$("#deptNm").val($(this).attr("deptName"));
						hideModal("deptListPopLayer");
					})
				})
    			$('.loading').css("display", "none");
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
		});
	});
	
	jQuery("#userListForuser").autocomplete({
        source : function(request, response) {
 
        	jQuery.ajax({
                url : "/rest/userApi/nameSearch/" + jQuery("#userListForuser").val(),
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
	
	jQuery("#userListForDept").autocomplete({
        source : function(request, response) {
 
        	jQuery.ajax({
                url : "/rest/deptApi/deptNameSearch/" + jQuery("#userListForDept").val(),
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
	
	////// 직원 검색 버튼
	$("#btnUserSearch").click(function(){
		
		var userNameSearch = jQuery("#userListForuser").val();
		var deptNameSearch = jQuery("#userListForDept").val();
		
		if(userNameSearch == "" && deptNameSearch == "") {
			bootbox.alert("검색어를 입력해 주세요"); 
			return false;
		}else {
			userSearch(userNameSearch,deptNameSearch,null);
		}
	});
	
	//////  직원검색 후 팝업에 있는 값을 해당필드에 입력
	$("#btnUserSelect").click(function(){
		var params = [];
		if($("input[name='userList']:checked").length > 1){
			bootbox.alert("한명만 선택하세요.");
			return;
		}
		var name;
		var id;
		var dept;
		var title;
		if($("input[name='userList']:checked").length < 1){
			bootbox.alert("직원을 선택해 주세요.");
			return;
		}
		
		$("input[name='userList']:checked").each(function(i) {
			$table = $(this).parents("tr");
			id = $table.find("td").eq(1).text();
			name = $table.find("td").eq(2).text();
			dept = $table.find("td").eq(3).text();
			title = $table.find("td").eq(4).text();
	    });
		$('#popDataArea tr').eq(index).find('[name="approvalLineVal"]').val(id);
		$('#popDataArea tr').eq(index).find('[name="approvalLineValNm"]').val(name);
		$('#popDataArea tr').eq(index).find('td').eq(3).text(dept);
		$('#popDataArea tr').eq(index).find('td').eq(4).text(title);
		
		
		hideModal('userListPopLayer');
	});	
	
});

//코드아이디로 코드리스트를 뿌려준다 - 계정구분
function accountComboList(grpCodeId, elementId){
	var commCodeListHtml = "";
	
	$.ajax({
	    url : "/rest/codeApi/commCodeList/" + grpCodeId + "/0",
        type : "GET",
        dataType : "json",
        success : function(jsonData) {
        	
        	$.each(jsonData.codeList, function(key, codeList) {
        		commCodeListHtml += "<option class='inputTit' value='" + codeList.code + "'>" + codeList.codeName +"</option>" ;
        	})
        	
        	$('select[name="' + elementId+'"]').append(commCodeListHtml);
        	$('select[name="' + elementId+'"]').val('${policy.accountCd}');
      	},
        error : function(data) {
            bootbox.alert("에러가 발생하였습니다.")
        }
    }).done(function(){
    	//ajax List 가져온후 jquery ui 적용
    	//$('select[name="' + elementId+'"]').selectbox();
    });
}

//코드아이디로 코드리스트를 뿌려준다 - 결재자 지정방법 코드
function approvalComboList(grpCodeId, elementId){
	var commCodeListHtml = "";
	
	$.ajax({
	    url : "/rest/codeApi/commCodeList/" + grpCodeId + "/0",
        type : "GET",
        dataType : "json",
        success : function(jsonData) {
        	
        	$.each(jsonData.codeList, function(key, codeList) {
        		commCodeListHtml += "<option class='inputTit' value='" + codeList.code + "'>" + codeList.codeName +"</option>" ;
        	})
        	
        	$('select[name="' + elementId+'"]').append(commCodeListHtml);
        	$(options).attr(elementId, commCodeListHtml);
      	},
        error : function(data) {
            bootbox.alert("에러가 발생하였습니다.")
        }
    }).done(function(){
    	getApprovalLine();
    	//ajax List 가져온후 jquery ui 적용
    	//$('select[name="' + elementId+'"]').selectbox();
    });
}
//////  결재라인 리스트 가져오기
function getApprovalLine(){
	var policyId 	= ${policy.policyId};
	$.ajax({
		type : 'GET',
		url : '/rest/policyApi/getApprovalLine/'+policyId,			
		dataType : "json",
		contentType: 'application/json',
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			if(jsonData.approvalLine!=null){
				$(jsonData.approvalLine).each(function(idx, approvalLine){
					addPopRow(approvalLine);
				});						
			}
		},
		error : function(e) {
			console.log(e);
		}
	});	
}

//////  팀과 직원 이름으로 직원 검색
function userSearch(userNameSearch, deptNameSearch){
	if(userNameSearch == "") userNameSearch = "all";
	if(deptNameSearch == "") deptNameSearch = "all"; 
	
	jQuery.ajax({
		type : 'GET',
		url : '/rest/userApi/userList/' + userNameSearch + '/' + deptNameSearch + "/0",
		dataType : "json",
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			var userSearchListHtml = "";
			
			if(jsonData.userList.length > 0){
				$.each(jsonData.userList, function(key, userList) {
					userSearchListHtml += "<tr>"
							+ "<td><input type='checkbox' name='userList' value='" + userList.userId + "' /></td>"
							+ "<td>" + userList.userId + "</td>"
							+ "<td>" + userList.userName  + "</td>"
							+ "<td>" + userList.deptName +"</td>"
							+ "<td>" + userList.titleAliasName +"</td>"
							+ "</tr>";
					}
				);
			} else {
				userSearchListHtml = "<tr>"
						+ "<td colspan='5' style='text-align: center;'>일치하는 검색 결과가 없습니다.</td>"
						+ "</tr>";
			}
			
			$('#userSearchList').html(userSearchListHtml);
		},
		error : function(err) {
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});	
}


//////  유저목록 팝업 내용 삭제
function deleteUserPop(){
	$('#userSearchList').html('<tr><td colspan="5" style="text-align: center;">부서명과 성명으로 검색하여 주십시요.</td></tr>');
	$('#userListForuser').val('');
	$('#userListForDept').val('')
}
//////  유저검색팝업창 OPEN
function srchUser(obj){
	deleteUserPop();
	index = $(obj).parents('tr').index();
	showModal('userListPopLayer');
	return false;
}

//////  결재라인 필수값 체크
function checkValidation(){
	var flag = true;
	$('#popDataArea tr').each(function(idx, tr){
		
		var appCd = $(tr).find('[name="approvalLineCd"]').val();
		var seq = $(tr).find('td').eq(0).text();
		
		if((idx+1) != seq){
			bootbox.alert('결재순서가 순차적이 아닙니다.');
			flag = false;
			return false;
		}		
		
		if(appCd == ''){
			bootbox.alert('필수 입력값이 비었습니다.');
			flag = false;
			return false;
		}else if(appCd =='U' && $(tr).find('[name="approvalLineVal"]').val()==''){
			bootbox.alert('결재자 입력값이 비었습니다.');
			flag = false;
			return false;
		}
	});
	
	
	//////  결재자지정방법 중복체크, 사용자지정은 userId 체크
	$('[name="approvalLineCd"]').eq(0).find('option').each(function(idx, option){		
		if($('[name="approvalLineCd"] option:selected[value="'+$(option).val()+'"]').length > 1){
			//////  사용자이면
			if($(option).val()=='U'){
				//////  빈값을 제외한 것들 가져와서
				$('[name="approvalLineVal"][value!=""]').each(function(idx, input){
					//////  value 가 두개 이상이면 오류
					if($('[name="approvalLineVal"][value="' + $(input).val() + '"]').length > 1){
						bootbox.alert('결재자가 중복 되었습니다.');
						flag = false;
						return false;						
					}
				});				
			}else{
				bootbox.alert('결재자지정방법이 중복 되었습니다.');
				flag = false;
				return false;
			}
			
		}
	});	
	
	if(flag){
		return flag;
	}
}
////// 정책 필수값 체크
function checkValidationPolicy(){
	var array = ['policyNm','minAmount','maxAmount'];
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

//////  정책 수정
function modifyPolicy(flag){
	var fMsg;
	if(flag=='delete'){
		fMsg = "삭제"
	}else if(flag=="update"){
		fMsg = "수정"
	}
	bootbox.confirm(fMsg + '하시겠습니까?', function(result){
		if(result){
			
			if(!checkValidationPolicy() ||  !validationNum()){
				return;
			}
			
			var url = '/rest/policyApi/###Policy';
			url = url.replace('###',flag);
			
			var policyId 	= $('#policyId').text();
			var policyNm 	= $('#policyNm').val();
			var accountCd 	= $('#accountCd').val();
			var minAmount 	= $('#minAmount').val().replace(/,/g,"");
			var maxAmount 	= $('#maxAmount').val().replace(/,/g,"");
			var description = $('#description').val();
			var useYn		= $('input[name="useYn"]:checked').val();	
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
						//getApprovalPolicy(null, true);
						bootbox.alert(jsonData.message,function(){
							if(flag=='delete'){
								$(location).attr('href','/admin/approvalManage/approvalPolicy');
							}	
						});
						
						//hideModal('newReg');
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
	});

}

//////  결재라인 수정
function modifyPolicyLine(){
	bootbox.confirm('저장하시겠습니까?', function(result){
		if(result){
			
			if(!checkValidation()){
				return;
			}
			
			var params = [];
			var url = '/rest/policyApi/modifyLine';
		
			$('#popDataArea tr').each(function(idx, tr){
				var policyId 			= $('#policyId').text();
				var seq 				= $(tr).find('td').eq(0).text();
				var approvalLineCd 		= $(tr).find('[name="approvalLineCd"]').val();
				var approvalLineVal 	= $(tr).find('[name="approvalLineVal"]').val();
				params.push({
						 "policyId" 		: policyId
						, "seq" 			: seq
						,"approvalLineCd" 	: approvalLineCd
						,"approvalLineVal" 	: approvalLineVal		 
				 });
			});
			
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
						//getApprovalPolicy(null, true);
						bootbox.alert(jsonData.message);
						//hideModal('newReg');
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
	});

}

//////ROW 삭제 이벤트 추가 - 최초 데이터 불러올떄, 행 추가시 실행한다
function deleteRow(obj){
	bootbox.confirm('삭제 하시겠습니까?', function(result){
		if(result){			
			var tr = $(obj).parents('tr');
			
			var params = {};
			var url = '/rest/policyApi/deleteLine';		

			var policyId 			= $('#policyId').text();
			var seq 				= $(tr).find('td').eq(0).text();
			params = {
					 "policyId" 		: policyId
					, "seq" 			: seq 
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
						$(tr).remove();
						bootbox.alert(jsonData.message);
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
	});
	
}

//////  결재자 지정 방법에 따른 결재자 창 유무설정
function changeSelect(obj){
	var tr = $(obj).parents('tr');
	var userInput = $(tr).find('[name="userInput"]');
	if($(obj).find('option:selected').val()!='U'){
		$(userInput).find('[name="approvalLineValNm"]').val('');
		$(userInput).find('[name="approvalLineVal"]').val('');
		$(userInput).hide();
		$(tr).find('td').eq(3).text('');
		$(tr).find('td').eq(4).text('');
	}else{
		$(userInput).show();
	}
}

//////  결재라인 테이블 ROW 추가 - obj가 있으면 데이터 넣고 없으면 빈 ROW 추가
function addPopRow(obj){
	
	var html = '<tr>'
			+	'<td></td>'
			+	'<td><select class="customSelect" name="approvalLineCd" onchange="changeSelect(this);">'+options.approvalLineCd+'</select></td>'
			+	'<td>'
			+		'<span class="customInput" name="userInput">'
			+		'<input name="approvalLineValNm" type="text" style="width:120px; margin-right:3px;"/><input name="approvalLineVal" type="hidden"/>'
			+		'<a class="btnView" onclick="srchUser(this);" style="display: inline-block;"><i class="icon iconView">돋보기</i></a>'
			+		'</span>'
			+	'</td>'
			+	'<td></td>'
			+	'<td></td>'
			+	'<td><button type="button" onclick="deleteRow(this);"><img src="/images/btn_close.png" style="width:22px;height:22px;"/></button></td>'
			+'</tr>';
			
	var tr = $(html).clone();
	if(obj!=null){
		$(tr).find('td').eq(0).text(checkNull(obj.seq));
		$(tr).find('[name="approvalLineCd"]').val(checkNull(obj.approvalLineCd));
		$(tr).find('[name="approvalLineValNm"]').val(checkNull(obj.approvalLineValNm));
		$(tr).find('[name="approvalLineVal"]').val(checkNull(obj.approvalLineVal));
		$(tr).find('td').eq(3).text(checkNull(obj.deptNm));
		$(tr).find('td').eq(4).text(checkNull(obj.titleNm));
		if(obj.approvalLineCd!='U'){
			$(tr).find('[name="userInput"]').hide();
		}
	}else{
		$(tr).find('td').eq(0).text($('#popDataArea tr').length+1);
		if($(tr).find('[name="approvalLineCd"]').val()!='U'){
			$(tr).find('[name="userInput"]').hide();
		}
	}
	$('#popDataArea').append(tr);
	//$(".customSelect").selectbox();
}

//////  입력값을 숫자만 받게 해주는 함수
function checkNum(){
     if((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode != 8 && event.keyCode != 46)){
    	 event.returnValue = false;
     }
}

//////입력값을 숫자만 받게 해주는 함수
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
		<h2><span>결재선 관리</span></h2>
		<p class="location">Home > 결재선관리 > 결재선 관리</p>
	</div>

	<!-- 검색내역 -->
	<div class="srchList" style="height:initial;">
		<strong class="tit">결재선 유형상세</strong>
		<div class="innerBox">
			<div class="btns">			
				<button class="btnReg" onclick="$(location).attr('href','/admin/approvalManage/approvalPolicy');">목록</button>
				<button class="btnReg" onclick="modifyPolicy('update');">수정</button>
				<button class="btnReg" onclick="modifyPolicy('delete');">삭제</button>
			</div>
			<table class="horizontalTable">
					<colgroup>
						<col width=""/>
						<col width=""/>
						<col width=""/>
					</colgroup>
					<tbody>
						<tr>
							<th>결재선 ID</th>							
							<td id="policyId">${policy.policyId}</td>
							<th>결재선명</th>							
							<td><input id="policyNm" type="text" style="width:96%" value="${policy.policyNm}" maxlength="32"/></td>
						</tr>
						<tr>
							<th>계정구분</th>							
							<td><select id="accountCd"  name="accountCode" class="customSelect" style="width:96%"></select></td>
							<th>예산부서명</th>							
							<td>
								<span class="customInput">
									<input type="text" name="deptNm" id="deptNm" style="width:86%" disabled="disabled" value="${policy.deptNm}">
									<input id="deptCd" type="hidden" value="${policy.deptCd}"/>
									<a class="btnView" onClick="showModal('deptListPopLayer');return false;"><i class="icon iconView">돋보기</i></a>
								</span>	
							</td>
						</tr>
						<tr>
							<th>최저금액</th>							
							<td><input id="minAmount" type="text" onkeydown="checkNum(this);" style="width:95%; text-align:right; padding-right:3px;" value="<fmt:formatNumber value="${policy.minAmount}" groupingUsed="true"/>"/></td>
							<th>최고금액</th>							
							<td><input id="maxAmount" type="text" onkeydown="checkNum(this);" style="width:95%; text-align:right; padding-right:3px;" value="<fmt:formatNumber value="${policy.maxAmount}" groupingUsed="true"/>"/></td>
						</tr>
						<tr>
							<th>사용여부</th>
							<td colspan="3">
								<input id="useY" name="useYn" type="radio" value="Y"/><label for="useY">사용</label>
								<input id="useN" name="useYn" type="radio" value="N"/><label for="useN">사용안함</label>
							</td>
						</tr>
						<tr>
							<th>설 명</th>							
							<td colspan="3"><textarea id="description" style="height: 50px; width:98%;" maxlength="64">${policy.description}</textarea></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>

	<div class="srchList" style="height:initial;">
		<strong class="tit">결재선</strong>
		<div class="innerBox">
				<div class="btns">
					<button class="btnReg" onclick="addPopRow(null);">행추가</button>
					<button class="btnReg" onclick="modifyPolicyLine();">저장</button>
				</div>
				<table class="defaultType">
					<colgroup>
						<col width="15%"/>
						<col width=""/>
						<col width=""/>
						<col width=""/>
						<col width=""/>
					</colgroup>	
					<thead>
						<tr>
							<th>결재순서*</th>
							<th>결재자지정방법*</th>
							<th>결재자</th>
							<th>부서</th>
							<th>직책</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody id="popDataArea">
<%-- 						<c:forEach var="approvalLine" items="${approvalLine}">
							<tr>								
								<td><input name="seq" style="text-align:right;" size="3" onkeydown="checkNum();" value="<fmt:formatNumber value="${approvalLine.seq.trim()}" groupingUsed="true"/>"/></td>
								<td><select class="customSelect" name="approvalLineCd"></select></td>
								<td>
									<input name="approvalLineValNm" type="text" style="width:90px;" value="${approvalLine.approvalLineNm}"/><input name="approvalLineVal" type="hidden" value="${approvalLine.approvalLineCd}"/>
									<a class="btnView" onclick="srchUser();" style="display: inline-block;"><i class="icon iconView">돋보기</i></a>
								</td>
								<td>${approvalLine.deptNm}</td>
								<td>${approvalLine.titleNm}</td>
								<td><button type="button" onclick="deleteRow"><img src="/images/btn_close.png" style="width:22px;height:22px;"/></button></td>
							</tr>
						</c:forEach> --%>
					</tbody>
				</table>
		</div>
			<!-- //결재선 라인등록 -->
	</div>
	<!-- //검색내역 -->
	</div>