<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/json2/20121008/json2.js"></script>
<script src="/js/jquery.fileupload.js"></script>

<script type="text/javascript">

// 품의서 상신
function saveApprovalRq(){
	
	var confirmMessage 	= "";
	var completeMessage = "";
	
	$("#${approvalId}_form > input[name=opinion]").val($("#approvalRqPopLayer").val());
	
	bootbox.confirm("품의서를 상신 하시겠습니까?", function(result){
	if(result){ 
		
		var params = [];
		var lineParam = [];
		var approvalAttachList = [];
		
		$(".symposiumFile").find("a.btnReg").each(function(){
			var attachId 	= $(this).attr("attachId");
			var typeCode 	= $(this).attr("typeCode");
			var approvalId	= '${approvalId}';
			var seq			= $(this).attr("seq");
			
			if(attachId != "") {
				approvalAttachList.push({"seq" : seq, "approvalId" : approvalId, "attachId" : attachId, "approvalAttachTypeCode" : typeCode});
			}
			
		})
		
		$($("#approvalLine").find("tr")).each(function(i) {
			var apUserId 	= $(this).attr("apUserId");
			var apUserName 	= $(this).attr("apUserName");
			var apDeptCode 	= $(this).attr("apDeptCode");
			var apDeptName 	= $(this).find("td").eq(2).text();
			var apTitleCode = $(this).attr("apTitleCode");
			var apTitleName = $(this).attr("apTitleName");
			var apDutyCode 	= $(this).attr("apDutyCode");
			var apDutyName 	= $(this).attr("apDutyName");
			var approvalId 	= '${approvalId}';
			var seq 		= $(this).attr("seq");
			
			lineParam.push({"seq" : seq, "approvalId" : approvalId, "apUserId" : apUserId, "apUserName" : apUserName, "apDeptCode" : apDeptCode, "apDeptName" : apDeptName, 
				"apDutyCode" : apDutyCode, "apDutyName" : apDutyName, "apTitleCode": apTitleCode, "apTitleName" : apTitleName})
	    });
		
		$("#approvalItemList").find("tr").each(function(){
			var approvalId		= $(this).attr("approvalId");
			var seq 			= $(this).attr("seq");
			var sApprovalId		= $(this).attr("sApprovalId");
			var sApprovalSeq 	= $(this).attr("sApprovalSeq");
			var customerCode 	= $(this).attr("customerCode");
			var customerName 	= $(this).attr("customerName");
			var saupNo 			= $(this).attr("saupNo");
			var account2Code	= $(this).attr("account2Code");
			var account2Name 	= $(this).attr("account2Name");
			var productCode 	= $(this).attr("productCode");
			var productName 	= $(this).attr("productName");
			var ftrCode 		= $(this).attr("ftrCode");
			var details 		= $(this).attr("details");
			var opinion 		= $("#approvalRqPopLayer textarea[name=opinion]").val();
			var meetReport 		= $(this).attr("meetReport");
			var seminarReport 	= $(this).attr("seminarReport");
			var accountCode		= "2";
			var accountName		= "심포지움";
			var budgetDeptCode	= $("#budgetDeptCode").val();
			var budgetDeptName	= $("#budgetDeptName").val();
			
			params.push({
						"approvalId"		: approvalId,
						"seq" 				: seq,
						"sApprovalId"		: sApprovalId,
						"sApprovalSeq" 		: sApprovalSeq,
						"customerCode" 		: customerCode,
						"customerName"		: customerName,
						"saupNo"			: saupNo,
						"account2Code" 		: account2Code,
						"account2Name" 		: account2Name,
						"productCode" 		: productCode,
						"productName" 		: productName,
						"ftrCode" 			: ftrCode,
						"details" 			: details,
						"opinion" 			: opinion,
						"meetReport" 		: meetReport,
						"seminarReport" 	: seminarReport,
						"accountCode" 		: accountCode,
						"accountName" 		: accountName,
						"budgetDeptCode" 	: budgetDeptCode,
						"budgetDeptName" 	: budgetDeptName,
						"approvalRqLines"	: lineParam,
						"approvalRqAttachs"	: approvalAttachList
					})
			
		})
		
		
		$.ajax({
		    url : "/rest/approvalRqApi/updateApprovalRqItem",
	        type : "POST",
	        dataType : "json",
	        data : JSON.stringify(params),
	        contentType: 'application/json',
	        success : function(jsonData) {
				if($("#approvalLine").attr("generated") == "true"){
		        	//insertApprovalRqLine();
				}	        	
	        	//saveApprovalAttach();
	        	
	        	bootbox.alert("품의서를 상신 하였습니다.", function(){
	        		location.href = '/approval/progressList';	
	        	});
	        },
	        error : function(data) {
	        	bootbox.alert("에러가 발생하였습니다.")
	        },
	        complete : function(){
	        	
	        	
	        },
		});
		}
	})
	
}

function seminaResultDocument(seq){
	
	
	var obj = $("#seminarResultDocument_"+ seq);
	var objId = obj.attr("id");
	var rqName = $(obj).find('[name="rqName"]').val();
	var eventName = $(obj).find('[name="eventName"]').val();
	var numberOfpeople = $(obj).find('[name="numberOfpeople"]').val();
	var content = $(obj).find('[name="content"]').val();

	
	$('#approvalItemList tr').each(function(idx, tr){
		/* 세미나 결과 보고서 조합 */
		var resultDocument = $("#seminarResultDocument_" + (Number(idx)+1));
		var resultDocumentId = resultDocument.attr("id");
		
		$(resultDocument).find('[name="rqName"]').val(rqName);
		$(resultDocument).find('[name="eventName"]').val(eventName);
		$(resultDocument).find('[name="numberOfpeople"]').val(numberOfpeople);
		$(resultDocument).find('[name="content"]').val(content);
		
		
		var approvalId = $("#" + resultDocumentId + " > input[name=targetId]").val();
		var documentData = $("form[name=resultDocumentForm_" + (Number(idx)+1) + "]").serialize();
		
		var resultDocumentData = decodeURIComponent(documentData.replace(/&/gi, "『"));
		
		$(tr).attr("seminarReport" , resultDocumentData);		
		$(tr).find("span[name=resultDocumentInfo]").html('... ');
		
		/* $("#resultDocumentInfo").html("... "); */

	})

	
	hideModal(objId);
}


//COM_CD_GRP_ID 조회 + 셀렉트박스 마크업 어태치
function getComCdGrpSelect(code, elem, flag, selectVal){
	$.ajax({
		type : 'GET',
		url : '/rest/codeApi/commCodeList/' + code + '/0',
		dataType : "json",
		success : function(data) {
			var html = '<option value="all">전체</option>';
			$.each(data.codeList, function(key, obj) {
				html += '<option value="' + obj.code + '">' + '(' + obj.code + ') ' + obj.codeName + '</option>';
			});
			$("#"+elem + " > td select[name=productCd]").html(html);
			
		},
		error : function(e) {
			bootbox.alert("처리중 에러가 발생하였습니다.");
		}
	}).done(function(){
		if (flag == 'Y') {
			$(".customSelect").selectbox();
		}
	}).done(function(){
		$("#"+elem + " > td select[name=productCd]").val(selectVal);
	});
}

function getAccount2Code(targetEl){
	openAccount2PopLayer($('#accountCode').val(), targetEl, 'rqItem');
}

function approvaValidation(){
	
	var accountCode 	= $("#accountCode").val();
	var budgetDeptCode 	= $("#budgetDeptCode").val();
	
	if(accountCode == ''){
		bootbox.alert("계정구분을 선택하여 주세요");
		return;
	}
	
	if(budgetDeptCode == ''){
		bootbox.alert("예산부서를 선택하여 주세요");
		return;
	}
	
	var arrItemId = [];
	
	$("#approvalItemList").find("tr").each(function(){
		arrItemId.push($(this).attr("id"));
	})
	
	for(var i in arrItemId){
		if($("#" + arrItemId[i]).attr("account2Code") == ''){
			bootbox.alert("세부계정을 선택하여 주세요");
			return;
		}
		if($("#" + arrItemId[i]).attr("ftrCode") == ''){
			bootbox.alert("공정경쟁 규약을 선택하여 주세요");
			return;
		}
		if($("#" + arrItemId[i]).attr("seminarreport") == ''){
			bootbox.alert("결과보고서를 입력해 주세요");
			return;
		}
	}
	
	var lineGenerate = $("#approvalLine").attr("generated");
	
	if(lineGenerate == "false"){
		bootbox.alert("결재선이 존재 하지않습니다. 결재선을 추가 해 주세요");
		return;
	}
	
	showModal('approvalRqPopLayer', '${approvalId}');
}



//코드아이디로 코드리스트를 뿌려준다
function commCodeComboList(grpCodeId, elementId){
	var commCodeListHtml = "";
	var targetForm = "#${approvalId}_form";
	
	$.ajax({
	    url : "/rest/codeApi/commCodeList/" + grpCodeId + "/0",
        type : "GET",
        dataType : "json",
        beforeSend:function(){
        	
   	    },
        success : function(jsonData) {
        	
        	$.each(jsonData.codeList, function(key, codeList) {
        		if(key == 2) commCodeListHtml += "<option class='inputTit' value='" + codeList.code + "'>" + codeList.codeName +"</option>" 
        	})
        	
        	$("#" + elementId).append(commCodeListHtml);
       },
        error : function(data) {
        	bootbox.alert("에러가 발생하였습니다.")
        }
    }).done(function(){
    	
    	var accountCode = '${accountCode}';
    	if(accountCode != '') $("#commCodeList").val(accountCode);
    	
    	
    	if(accountCode == '2'){
    		$("#commCodeList option").not(":selected").attr("disabled", "disabled");
    	}
    	
    	
    	//ajax List 가져온후 jquery ui 적용
    	 $(".customSelect").selectbox({
    		onChange: function (val, inst) {
    			
    			var accountName = $("#" + elementId + ">option:selected").text();
    			
    			$("#accountCode").val(val);
    			$("#accountName").val(accountName);
    			
				if($("#deptName").val() != "" && val != 2){
					approvalLineGenerate();
				}
				
				if(val == 2){
					$("#btnAddApprovalLine").hide();
					approvalLineHtml = "<tr><td colspan='5'>심포지움은 결재요청은 결재선을 지정하지 않습니다.</td></tr>";
					$("#approvalLine").html(approvalLineHtml);
					$("#approvalLine").attr("generated", "false");
				}
				
				if(val == 1 || val == 2){
					//심포지움이면 결재 상신요청 
					if(val == 2) $("#btnApprovalRq").text("결재 요청");
					else if (val == 1) $("#btnApprovalRq").text("결재 상신");
					
					$("#approvalRqDataTable").find(".type1").each(function(i){
						$(this).css("display", "table-cell");
					})
					$("#fileBox").find(".fileRow1").each(function(){
						$(this).css("display", "table-row");
					})
					
					$("#fileBox").find(".fileRow2").each(function(){
						$(this).css("display", "none");
					})
					
				} else {
					$("#btnApprovalRq").text("결재 상신");
					$("#approvalRqDataTable").find(".type1").each(function(i){
						$(this).css("display", "none");
					})
					
					$("#fileBox").find(".fileRow2").each(function(){
						$(this).css("display", "table-row");
					})
					
					$("#fileBox").find(".fileRow1").each(function(){
						$(this).css("display", "none");
					})
				}
    		}
    	});
    });
}


function deptChoose(deptCode){
	$("#budgetDeptCode").val(deptCode);
}

function approvalLineGenerate(){
	
	var amount			= ${amountTotal};
	var accountCode 	= $("#accountCode").val();
	var deptCode 		= $("#budgetDeptCode").val();
	
	var approvalLineHtml = "";
	
	$.ajax({
   		type : 'GET',
   		url : '/rest/approvalRqApi/approvalRqLineList/' + amount + '/' + accountCode + '/' + deptCode,
   		dataType : "json",
   		contentType: 'application/json',
   		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
   		success : function(jsonData) {
   			
   			if(jsonData.length > 0){
   				$("#approvalLine").attr("generated", "true");
   				$("#btnAddApprovalLine").hide();
   				$.each(jsonData, function(key, apUserInfo) {
   			
    				approvalLineHtml +="<tr seq=" + (key + 1) + " apUserId='" + apUserInfo.apUserId + "' apUserName='" + apUserInfo.apUserName + "' apDeptCode='" + apUserInfo.deptCode + "' apDeptName='" + apUserInfo.deptName + "' apTitleCode='" + apUserInfo.titleCode + "' apTitleName='" + apUserInfo.titleName + "'  apDutyCode='" + apUserInfo.dutyCode +"' apDutyName='" + apUserInfo.dutyName +"'>"
    								+ "	<td>" + (key + 1) + "차 결재자</td>"
    								+ "	<td>" + apUserInfo.apUserName + "&nbsp;" + apUserInfo.dutyName + "</td>"
    								+ "	<td>" + apUserInfo.deptName + "</td>"
    								+ "</tr>";
   				})
   			} else {
   				$("#approvalLine").attr("generated", "false");
   				approvalLineHtml = "<tr><td colspan='5'>선택한 계정구분과 예산부서로 결재선이 존재 하지 않습니다.</td></tr>";
   				$("#btnAddApprovalLine").show();
   			}
   			$("#approvalLine").html(approvalLineHtml);
   			
   		},
		error : function(err) {
   	    	bootbox.alert("처리중 장애가 발생하였습니다." + err);
   		}
   	})
	
}

function deleteFile(attachId, seq, targetList){
	var	url = "/rest/uploadApi/deleteFile/" + attachId + "/" + seq;
	
	bootbox.confirm("파일을 삭제 하시겠습니까?",function(result){
		if(result){
			jQuery.ajax({
				type : 'POST',
				url : url,
				dataType : "json",
				contentType: 'application/json',
				success : function(jsonData) {		
					bootbox.alert("파일을 삭제 하였습니다.");
					targetList.remove();
				},
				error : function(err) {
					bootbox.alert("처리중 장애가 발생하였습니다." + err);
				}
			});
		}
	})
}

function rqDownloadFile(attachId, seq){
	var chkUrl = '/rest/downloadApi/checkFile/'+attachId+'/'+seq;
	var downUrl = '/rest/downloadApi/downloadFile/'+attachId+'/'+seq;
	downloadFile(chkUrl, downUrl);
}

//////첨부파일 리스트에 들어갈 HTML 생성
function getFileHtml(attachFile){
	//////  체크파일
	var fileListHtml = "";
	var fileName = attachFile.pFileName==null?attachFile.pfileNm:attachFile.pFileName;
	////// 최초 등록시(attachFile==null)에는 다운로드가 안된다. 삭제만 됨
	
	if(attachFile.attachId!=null){
		fileListHtml += '<li><span onclick="rqDownloadFile(\'' + attachFile.attachId + '\', \'' + attachFile.seq+'\');">' + fileName + '.' +attachFile.ext + '</span> <button onclick="deleteFile(\''+ attachFile.attachId +'\', \''+ attachFile.seq +'\',$(this).parent());" class="btnDefaultDel">삭제</button>';
	}else{
		fileListHtml += '<li>' + fileName + '.' +attachFile.ext + ' <button onclick="deleteFile(\''+ attachFile.attachId +'\', \''+ attachFile.seq +'\',$(this).parent());" class="btnDefaultDel">삭제</button>';
	}
	fileListHtml += "";
	return fileListHtml;
}

////// 첨부파일리스트 가져오기
function getFileList(){
	
	var targetFileBox = $("#targetFileBox").val();
	var targetPopupId = $("#targetPopupId").val();
	var targetList = $('#'+targetPopupId).find('[id="'+targetFileBox+'"]').attr("targetList");
	
	var attachId = $('#'+targetPopupId).find('[id="'+targetFileBox+'"]').find('.' + targetList).parent().find('a').attr('attachId');	
	var url = '/rest/uploadApi/getFileList/'+ attachId;
	
	//////  조회수 증가, 공지사항 상세내용 가져오기
	jQuery.ajax({
		type : 'GET',
		url : url,
		dataType : "json",
		contentType: 'application/json',
		success : function(jsonData) {
			var targetUl = $('#'+targetPopupId).find('[id="'+targetFileBox+'"]').find('.' + targetList);
			$(targetUl).empty();
			////// 첨부파일 리스트			
			if(jsonData.fileList!=null && jsonData.fileList.length!=0){
				var list = jsonData.fileList;
				$.each(list, function(index, attachFile) {
					$(targetUl).append(getFileHtml(attachFile));			
				});
			}
			
/* 			$("button[class='btnDefaultDel']").on("click", function(){
	    		var attachId = $(this).attr("attachId");
	    		var seq = $(this).attr("seq");
	    		deleteFile(attachId, seq, $(this).parent());
	    	}) */
			
		},
		error : function(err) {
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});
}


function approvalFileUpload(targetEl, targetFileBox, targetPopupId){
	
	var approvalTypeEl 	= "#" + targetEl.id;
	var fileBoxId		= "#" + $(approvalTypeEl).parent().parent().parent().attr("id");
	$(fileBoxId).attr("targetList", $(approvalTypeEl).parent().find('ul').attr('class'));
	/* var sn 				= $(fileBoxId + "tr td ul.").length; */
	$typeList = $(fileBoxId).find("." + $(fileBoxId).attr("targetlist"));
	var sn = $($typeList.find("li").length);
	var accountCode		= $("#approvalRqform input[name=accountCode]").val();
	var typeCode		= $(approvalTypeEl).attr("typeCode");
	
	
	$("#targetFileBox").val(targetFileBox);
	$("#targetPopupId").val(targetPopupId);
	var seq = $(fileBoxId).attr('seq');
	var dataEl = $("#approvalRq_" + seq);
	
	//////  첨부파일 마스터ID
	if($(approvalTypeEl).attr('attachId')==''){			
		jQuery.ajax({
			type : 'GET',
			url : '/rest/uploadApi/getAttachId',
			dataType : "json",
			contentType: 'application/json',
			beforeSend:function(){
				$('.loading').css("display", "block");
		    },
		    complete:function(){
				$('.loading').css("display", "none");
		    },
			success : function(jsonData) {
				var attachId = jsonData.attachId;
				$(approvalTypeEl).attr("attachId" , attachId);
				$("#fileUpload").fileupload('option', 'url', '/rest/uploadApi/uploadFile/APR01/' + attachId + '/${approvalId}/' + typeCode + "/" + accountCode + "/" + seq);
			},
			error : function(err) {
				alert("처리중 장애가 발생하였습니다." + err);
			}
		});
	}else{
		$("#fileUpload").fileupload('option', 'url', '/rest/uploadApi/uploadFile/APR01/' + $(approvalTypeEl).attr('attachId') + '/${approvalId}/' + typeCode + "/" + accountCode + "/" + seq);
	}
	
	
	$("#fileUpload").trigger("click");
}

function initAll(){
	approvalLineGenerate();
}

function setAttachFile(){
	$('#approvalItemList tr').each(function(idx, tr){
		var layer = $('#fileUploadPopLayer_'+$(tr).attr("seq"));
		$.ajax({
			type : 'GET',
			url : '/rest/approvalRqApi/attachFile/' + $(tr).attr("approvalId") + '/all',
			dataType : "json",
			beforeSend:function(){
				$('.loading').css("display", "block");
		    },
		    complete:function(){
				$('.loading').css("display", "none");
		    },
			success : function(data) {
				if (data.code == 'S') {
					$(data.attachFilelist).each(function(idx, file){
						if(file.approvalAttachTpCd=='A'){
							$(layer).find('ul.seminaList').append(getFileHtml(file));
						}else if(file.approvalAttachTpCd=='B'){
							$(layer).find('ul.meetList').append(getFileHtml(file));
						}else if(file.approvalAttachTpCd=='C'){
							$(layer).find('ul.qnaList').append(getFileHtml(file));
						}else if(file.approvalAttachTpCd=='D'){
							$(layer).find('ul.cardList').append(getFileHtml(file));
						}
					})
				} else {
					bootbox.alert(data.msg);
				}
			},
			error : function(e) {
				console.log(e);
			}
		});
	})
}

function emptyCheck(str){
	if(str==null || str==''){
		return 'all';
	}
	return str;	
}


function getComCdGrpIdByName(code, elem, codeYn){
	$.ajax({
		type : 'GET',
		url : '/rest/codeApi/commCodeList/' + code + '/0',
		dataType : "json",
		success : function(data) {
			var html = '<option value="all">전체</option>';
			$.each(data.codeList, function(key, obj) {
				if(codeYn=='Y'){
					html += '<option value="' + obj.code + '">' + '(' + obj.code + ') ' + obj.codeName + '</option>';
				}else{
					html += '<option value="' + obj.code + '">'+ obj.codeName + '</option>';
				}
			});
			$("select[name="+elem+"]").html(html);
		},
		error : function(e) {
			console.log(e);
		}
	});
}

$(function(){
	
	$('#productCdType').change(function(){
		if($(this).val()=='all')
			getComCdGrpId('PROD_CD', 'productCd', null, 'Y');
		else
			getComCdGrpId($(this).val(), 'productCd', null, 'Y');
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
						$("#budgetDeptCode").val($(this).attr("deptCode"));
						
						$("#budgetDeptName").val($(this).attr("deptName"));
						$("#deptName").val($(this).attr("deptName"));
						hideModal("deptListPopLayer");
						
						
						approvalLineGenerate();
						
					})
				})
    	    },
    		error : function(err) {
    			bootbox.alert("처리중 장애가 발생하였습니다." + err);
    		}
		})
	})
	
	$('input[name="details"]').blur(function(){
		$(this).parent().parent().attr("details", $(this).val());
	})
	
	function checkData(){
		if($("#commCodeList>option:selected").val() != 0 && $("#").val() != ""){
			return true;
		} else {
			return false;
		}
	}
	
	
	$(".productCd").change(function(){
		var curValue = $('option:selected',this).text().replace(/(\(.+?\) )/, '');
		
		var targetId = "#" + $(this).parent().parent().parent().attr("id");
		
		$(targetId).attr({
			productCode : $(this).val(),
			productName : curValue
		})
	})
	
	//////파일 업로드
	$("#fileUpload").fileupload({
		//////  /rest/uploadApi/uploadFile/{게시판구분}/{첨부파일마스터ID}
        url: '/rest/uploadApi/uploadFile/APR01/',
        dataType: 'json',
        maxFileSize: 5000000, // 5 MB
        
    }).on('fileuploadadd', function (e, data) {				//////  파일 추가할떄의 이벤트    	
    	var targetListId = "#" + $("#fileBox").attr("targetList");
    	var curSeq = parseInt($(targetListId).attr("curSeq"));
    
    	if($(targetListId).attr("curSeq") == 0){
    		$(targetListId).attr("curSeq", 1);
    	} else {
    		$(targetListId).attr("curSeq", (curSeq + 1));
    	}
    	
    	//$(targetListId).append(getListHtml(data.files[0].name, data.files[0].size));
    	//$(targetListId).children("li").last().find(".progress").css("display", "block");
        data.submit();
        
    }).on('fileuploadprogressall', function (e, data) {		//////  총 파일의 progress를 보여준다.
    	var targetListId = "#" + $("#fileBox").attr("targetList");
    	 var progress = parseInt(data.loaded / data.total * 100, 10);
	        $('.progress-bar').css(
	            'width',
	            progress + '%'
	        );
	        if(progress=='100'){
	        	$(targetListId).children("li").last().find(".progress").css("display", "none");
		        $('#btnStopUpload').hide();
		   		$('#progress .bar').css('width', '0%');;
	        }
    }).on('fileuploaddone', function (e, data) {			//////  파일당 업로드가 끝났을 때 수행, 3개파일 한번에 올리면 각각 한번씩 총 3번 수행
  
    	getFileList();
    	$("#addFile i").addClass("add");
    	//$('.progress').hide();
    	
    }).on('fileuploadfail', function (e, data) {
    	$('#fileList').append("<tr><td colspan=\"2\">"+data.files[0].name + " - 업로드 실패</td></tr>");
    	//$('.progress').hide();
    });
	/* 
	
	//////파일 업로드
	$("#fileUpload").fileupload({
		//////  /rest/uploadApi/uploadFile/{게시판구분}/{첨부파일마스터ID}
        url: '/rest/uploadApi/uploadFile/APR01/',
        dataType: 'json',
        maxFileSize: 5000000, // 5 MB
        
    }).on('fileuploadadd', function (e, data) {				//////  파일 추가할떄의 이벤트
    	var targetFileBoxId 	= $("#targetFileBox").val();
    	var targetListName 		= $("#" + targetFileBoxId).attr("targetList");
    	$targetList				= $("#" + targetFileBoxId).find('.' + targetListName);

    	$targetList.append(getListHtml(data.files[0].name, data.files[0].size));
    	$targetList.children("li").last().find(".progress").css("display", "block");
    	
    	$("a.btnDefaultDel").on("click", function(){
    		$(this).parent().remove();
    	})
    	
        data.submit();
        
        
    }).on('fileuploadprogressall', function (e, data) {		//////  총 파일의 progress를 보여준다.
    	var targetListId = "#" + $("#fileBox").attr("targetList");
    	 var progress = parseInt(data.loaded / data.total * 100, 10);
	        $('.progress-bar').css(
	            'width',
	            progress + '%'
	        );
	        if(progress=='100'){
	        	$(targetListId).children("li").last().find(".progress").css("display", "none");
		        $('#btnStopUpload').hide();
		   		$('#progress .bar').css('width', '0%');;
	        }
    }).on('fileuploaddone', function (e, data) {			//////  파일당 업로드가 끝났을 때 수행, 3개파일 한번에 올리면 각각 한번씩 총 3번 수행
    	
    }).on('fileuploadfail', function (e, data) {
    	$('#fileList').append("<tr><td colspan=\"2\">"+data.files[0].name + " - 업로드 실패</td></tr>");
    	$('#progress').hide();
    }); */
	
	
	$("#btnUserSelect").click(function(){
		
		var approvalLineHtml = "";
		var lineGenerate = $("#approvalLine").attr("generated");
		var no = $("#approvalLine tr").length; 
		
		$("input[name='userList']:checked").each(function(i) {
			
			$table = $(this).parents("tr");
			var userId 		= $table.attr("userId");
			var userName 	= $table.attr("userName");
			var deptCode 	= $table.attr("deptCode");
			var deptName 	= $table.attr("deptName");
			var titleCode 	= $table.attr("titleCode");
			var titleName 	= $table.attr("titleName");
			var dutyCode 	= $table.attr("dutyCode");
			var dutyName 	= $table.attr("dutyName");
			
			approvalLineHtml +=   "<tr seq=" + (i + 1) + " apUserId='" + userId + "' apUserName='" + userName + "' apDeptCode='" + deptCode + "' apDeptName='" 
			+ deptName + "' apTitleCode='" + titleCode + "' apTitleName='" + titleName + "'  apDutyCode='" + dutyCode +"' apDutyName='" + dutyName +"'>"
			+ "	<td>" + (i + 1) + "차 결재자</td>"
			+ "	<td>" + userName + "&nbsp;" + dutyName + "</td>"
			+ "	<td>" + deptName + "</td>"
			+ "</tr>";
			
		});
		
		if(lineGenerate == "true"){
			$("#approvalLine").append(approvalLineHtml);
		} else {
			$("#approvalLine").html(approvalLineHtml);
			$("#approvalLine").attr("generated", "true");
		}
		
		hideModal("userListPopLayer");
		$("#userSearchList").html("<tr><td colspan='5' style='text-align: center;'>부서명과 성명으로 검색하여 주십시요.</td></tr>");
		
	})
	
	$("#ruleDocumentPopLayer").find(".topSrch").show();
	initAll();
	setAttachFile();
	//계정 구분 ACCOUNT_CD 목록 불러오기
	commCodeComboList('ACCOUNT_CD', 'commCodeList');
	getComCdGrpIdByName('PROD_CD_TYPE', 'productCdType', 'N');
	getComCdGrpIdByName('PROD_CD', 'productCd', 'Y');
})
</script>
<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<form action="" id="approvalRqform">
	<input type="hidden" name="accountCode" id="accountCode" value="${accountCode}">
	<input type="hidden" name="accountName" id="accountName" value="${accountName}">
	<input type="hidden" name="budgetDeptCode" id="budgetDeptCode" value="${loginDeptCode}">
	<input type="hidden" name="budgetDeptName" id="budgetDeptName" value="${loginDeptName}">
	<input type="hidden" name="ftrCode" id="ftrCode" value="">
</form>


<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2>
			<i class="icon iconIngDoc"></i> <span>결재 상신</span>
		</h2>
		<p class="location">Home > 결재상신 > 품의서 작성</p>
	</div>

	<!-- 품의서 작성 -->
	<div class="writeForm">
		<div class="tit">
			<strong>품의서 작성</strong>
			<div class="btns">
				<a href="javascript:approvaValidation();" id="btnApprovalRq" class="btnUp">결재 상신</a>
			</div>
		</div>
		<div class="innerBox first">
			<div class="sTit">
				<strong>결재선</strong>
				<div class="btns">
					<a href="" id="btnAddApprovalLine" onclick="showModal('userListPopLayer');return false;" class="btnAdd" style="display: none"><i class="icon iconAdd"></i>결재선 추가</a>
					<!-- <a href="" class="btnDel"><i class="icon iconDel"></i>삭제</a> -->
				</div>
			</div>
			<table class="defaultType">
				<colgroup>
					<col width="100px" />
					<col width="" />
					<col width="" />
				</colgroup>
				<thead>
					<tr>
						<th>순서</th>
						<th>성명</th>
						<th>부서</th>
					</tr>
				</thead>
				<tbody id="approvalLine" generated="false">
					<!-- 결재라인  -->
					<tr>
						<td style="text-align: center;" colspan="6">결재 계정 구분과 예산부서를 선택해 주세요</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="innerBox">
			<div class="sTit">
				<strong>카드 사용 내역</strong>
			</div>
			<div class="optionSelect">
				<ul>
					<li>
						<span class="inputTit">계정 구분</span> 
						<span>
							<select name="commCodeList" id="commCodeList" class="customSelect">
								<option value="0">계정 선택</option>
							</select>
						</span>
						<span class="inputTit second">예산 부서</span><span class="customInput">
							<input type="text" name="deptName" id="deptName" disabled="disabled" value="${loginDeptName}"> 
							<a class="btnView" onClick="showModal('deptListPopLayer');return false;"><i class="icon iconView">돋보기</i></a>
						</span>
					</li>
				</ul>
			</div>
			
			
			<div class="scrollTable" id="scrollTable">
				<div class="inner">
					<table class="defaultType defaultType02" id="approvalRqDataTable">
						<thead>
							<tr>
								<th width="30px">번호</th>
								<th width="70px">사용일</th>
								<th width="200px">상호</th>
								<th width="100px">합계금액</th>
								<th width="80px">담당자</th>
								<th width="120px">세부계정</th>
								<th width="100px">제품군/명</th>
								<!-- <th width="120px">거래처</th> -->
								<th>사용내역</th>
								
								<!-- 세미나 / 심포지움 -->
								<th width="120px">공정경쟁규약</th>
								<th width="80px">결과보고서</th>
								<th width="50px" >첨부</th>
							</tr>
						</thead>
						<tbody id="approvalItemList">
							<c:forEach var="approvalItem" items="${approvalItem}" varStatus="status">
							<tr id="approvalRq_${approvalItem.seq}" 
								approvalId="${approvalItem.approvalId}" 
								seq ="${approvalItem.seq}" 
								sApprovalSeq="${approvalItem.sApprovalSeq}" 
								sApprovalId="${approvalItem.sApprovalId}" 
								customerCode="${approvalItem.customerCode}" 
								customerName="${approvalItem.customerName}" 
								account2Code="${approvalItem.account2Code}" 
								account2Name="${approvalItem.account2Name}" 
								productCode="${approvalItem.productCode}" 
								productName="${approvalItem.productName}" 
								ftrCode="${approvalItem.ftrCode}" 
								details="${approvalItem.details}" 
								opinion="${approvalItem.opinion}" 
								meetReport="${approvalItem.meetReport}" 
								seminarReport="${approvalItem.seminarReport}">
							
								<!-- seq -->
								<td>
									${approvalItem.seq}
									<input type="hidden" name="productCodeData" id="productCodeData" value="${approvalItem.productCode}">
								</td>
								
								<!-- 카드 사용 일자 -->
								<td><fmt:parseDate value="${approvalItem.authDate}" var="dateFmt" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd"/></td>
								<!-- 상호 -->
								<td style="text-align: left;">${approvalItem.mercName}</td>
								<!-- 금액 -->
								<td><fmt:formatNumber value="${approvalItem.requestAmount}" type="currency" groupingUsed="true"/></td>
								<!-- 담당자 -->
								<td>${approvalItem.userName}</td>
								<!-- <td><a href="" onclick="showModal('detailAccount');return false;">심포지움</a></td> -->
								<td>
									<input class="infoField" name="account2Info" type="text" readonly="readonly" value="${approvalItem.account2Name}" >
									<a href="javascript:;" title="세부계정" class="btnView"  onclick="getAccount2Code('approvalRq_${approvalItem.seq}')"><i class="icon iconView">돋보기</i></a>
								</td>
								<td>
									<div style="display:flex;">
										<select name="productCdType" id="productCdType"></select>
										<select name="productCd" id="productCd" class="productCd" value="1"></select>
									</div>
								
								</td>
								<%-- <td>
									<input class="infoField" name="customerInfo" type="text" readonly="readonly" value="${approvalItem.customerName}" >
									<a href="" title="거래처" class="btnView" onclick="showModal('customerListPoplayer' , 'approvalRq_${approvalItem.seq}');return false;"><i class="icon iconView">돋보기</i></a>
								</td> --%>
								<td><input type="text" name="details" style="width: 90%" id="targetDetail" value="${approvalItem.details}"></td>
								<td>
									<input class="infoField" name="ruleDocumentInfo" type="text" readonly="readonly" value="${approvalItem.ftrCode}" > 								
									<a href="" title="공정경쟁규약" class="btnView" onclick="showModal('ruleDocumentPopLayer', 'approvalRq_${approvalItem.seq}');getRuleDocument('0', 'approvalRq_${approvalItem.seq}'); return false;"><i class="icon iconView">돋보기</i></a>
								</td>
								<td>
									<span name="resultDocumentInfo"></span><a href="" title="결과보고서" onclick="showModal('seminarResultDocument_${approvalItem.seq}', 'approvalRq_${approvalItem.seq}'); return false;"><i class="iconDocument">작성문서</i></a>
								</td>
								<td><a href="" title="첨부파일" onclick="showModal('fileUploadPopLayer_${approvalItem.seq}', 'approvalRq_${approvalItem.approvalId}');return false;"><i class="iconFile">파일</i></a></td>
							</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td></td>
								<td></td>
								<td>총계</td>
								<td><fmt:formatNumber value="${amountTotal}" type="currency" groupingUsed="true"/></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td ></td>
								<td ></td>
								<td></td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- //품의서 작성 -->
	
	
	<!-- 파일첨부 팝업 -->
	<input type="hidden" id="targetPopupId" value="">
	<input type="hidden" id="targetFileBox" value="">
	<c:forEach var="approvalItem" items="${approvalItem}" varStatus="status">
	<div class="layer" id="fileUploadPopLayer_${approvalItem.seq}">
		<input type="hidden" name="targetId" value="">
		<div class="layerInner layerInner02">
			<strong class="firstTit">파일첨부</strong>
			<!-- 파일첨부 -->
			<div class="long">
				<!-- <div class="topSrch">
					<a href="" class="btnReg">파일추가</a> <a href="" class="btnCancel">파일삭제</a>
				</div> -->
				<table class="defaultType symposiumFile">
					<colgroup>
						<col width="100" />
						<col width="*" />
					</colgroup>
					<thead>
						<tr>
							<th>종류</th>
							<th>파일</th>
						</tr>
					</thead>
					
					<tbody id="fileBox_${approvalItem.seq}" seq="${approvalItem.seq}"  sApprovalId="${approvalItem.sApprovalId}" targetList="">
						<tr class="fileRow1">
							<th>세미나 사진</th>
							<td>
								<ul style="float: left" class="seminaList"></ul>
								<a style="float:right;" attachId="" seq="${approvalItem.seq}" sApprovalId="${approvalItem.sApprovalId}" id="seminaFile_${approvalItem.seq}" href="javascript:;" onClick="approvalFileUpload(this, 'fileBox_${approvalItem.seq}', 'fileUploadPopLayer_${approvalItem.seq}');" class="btnReg" typeCode="A">파일찾기</a>
							</td>
						</tr>
						<tr class="fileRow1">
							<th>방명록</th>
							<td>
								<ul style="float: left" class="meetList"></ul>
								<a style="float:right;"  attachId="" seq="${approvalItem.seq}" sApprovalId="${approvalItem.sApprovalId}" id="meetFile_${approvalItem.seq}" href="javascript:;" onClick="approvalFileUpload(this, 'fileBox_${approvalItem.seq}', 'fileUploadPopLayer_${approvalItem.seq}');" class="btnReg" typeCode="B">파일찾기</a>
							</td>
						</tr>
						<tr class="fileRow1">
							<th>질의응답</th>
							<td>
								<ul style="float: left" class="qnaList"></ul>
								<a style="float:right;" attachId="" seq="${approvalItem.seq}" sApprovalId="${approvalItem.sApprovalId}"  id="qnaFile_${approvalItem.seq}" href="javascript:;" onClick="approvalFileUpload(this, 'fileBox_${approvalItem.seq}', 'fileUploadPopLayer_${approvalItem.seq}');" class="btnReg" typeCode="C">파일찾기</a>
							</td>
						</tr>
						<tr class="fileRow1">
							<th>카드세부</th>
							<td>
								<ul style="float: left" class="cardList"></ul>
								<a style="float:right;" attachId="" seq="${approvalItem.seq}" sApprovalId="${approvalItem.sApprovalId}" id="cardFile_${approvalItem.seq}" href="javascript:;" onClick="approvalFileUpload(this, 'fileBox_${approvalItem.seq}', 'fileUploadPopLayer_${approvalItem.seq}');" class="btnReg" typeCode="D">파일찾기</a>
							</td>
						</tr>
					</tbody>
				</table>
				<p style="text-align: center; margin-top: 20px"><a href="javascript:hideModal('fileUploadPopLayer_${approvalItem.seq}');" class="btnReg">확인</a></p>
			</div>
			<!-- //파일첨부 -->
		</div>
		<button type="button" class="btnLayerClose" onclick="hideModal('fileUploadPopLayer_${approvalItem.seq}');return false;">
			<img src="/images/btn_close.png" />
		</button>
	</div>
	<!-- 세미나결과보고서 팝업 -->
	<div class="layer" id="seminarResultDocument_${approvalItem.seq}">
	<input type="hidden" name="targetId" value="">
	<div class="layerInner layerInner02">
		<strong class="firstTit">세미나결과보고서</strong>
		<!-- 세미나결과보고서 -->
		<div class="long">
			<!-- <div class="topSrch">
				<a href="" class="btnCheckLong">선택</a>
			</div> -->
			<form name="resultDocumentForm_${approvalItem.seq}">
				<table class="horizontalTable">
					<colgroup>
						<col width="200" />
						<col width="*" />
					</colgroup>
					<tbody>
						<tr>
							<th>작성자</th>
							<td><input type="text" name="rqName"></td>
						</tr>
						<tr>
							<th>행사명</th>
							<td><input type="text" name="eventName"></td>
						</tr>
						<tr>
							<th>날짜</th>
							<td>
								<input type="hidden" name="date" value="${approvalItem.authDate}" readonly="readonly" />
								${approvalItem.authDate}
							</td>
						</tr>
						<tr>
							<th>장소</th>
							<td>
								<input type="hidden" name="place" value="${approvalItem.mercName}" readonly="readonly">
								${approvalItem.mercName}
							</td>
						</tr>
						<tr>
							<th>참석인원</th>
							<td><input type="text" name="numberOfpeople"></td>
						</tr>
						<tr>
							<th>집행금액</th>
							<td><input type="hidden" name="amount" value="${approvalItem.requestAmount}" >
								<fmt:formatNumber value="${approvalItem.requestAmount}" type="currency" groupingUsed="true"/>
							</td>
						</tr>
						<tr>
							<th>주요내용</th>
							<td><textarea name="content" id=""></textarea></td>
						</tr>
					</tbody>
				</table>

			</form>
			<div class="btmbtns">
				<a href="javascript:seminaResultDocument('${approvalItem.seq}');" class="btnSaveLong">저장</a>
			</div>
		</div>
		<!-- //세미나결과보고서 -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('seminarResultDocument_${approvalItem.seq}');return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- //세미나결과보고서 팝업 -->

<!-- 회의록 팝업 -->
<div class="layer" id="meetingDocument_${approvalItem.seq}">
	<input type="hidden" name="targetId" value="">
	<div class="layerInner layerInner02">
		<strong class="firstTit">회의록</strong>
		<!-- 회의록 -->
		<div class="long" >
			<form name="meetingDocumentForm_${approvalItem.seq}">
			<table class="horizontalTable">
				<colgroup>
					<col width="200" />
					<col width="*" />
				</colgroup>
				<tbody>
					<tr>
						<th>부서명</th>
						<td><input type="text" name="deptName" value=""></td>
					</tr>
					<tr>
						<th>작성자</th>
						<td><input type="text" name="writer"></td>
					</tr>
					<tr>
						<th>회의명</th>
						<td><input type="text" name="meetName"></td>
					</tr>
					<tr>
						<th>날짜</th>
						<td>
							<input type="text" id="meetDate" name="date" class="date-picker" style="width: 120px" />
							<label for="meetDate" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>
						</td>
					</tr>
					<tr>
						<th>시간</th>
						<td><input type="text" name="time"></td>
					</tr>
					<tr>
						<th>총 인원수</th>
						<td><input type="text" name="numberOfpeople" ></td>
					</tr>
					<tr>
						<th>외부 참석자 명단</th>
						<td><input type="text" name="externalList"></td>
					</tr>
					<tr>
						<th>보령 참석자 명단</th>
						<td><input type="text" name="internalList"></td>
					</tr>
					<tr>
						<th>회의내용</th>
						<td><textarea name="content" id=""></textarea></td>
					</tr>
				</tbody>
			</table>
			</form>
			<div class="btmbtns">
				<a href="javascript:meetingDocument('${approvalItem.seq}');" class="btnSaveLong">저장</a>
			</div>
		</div>
		<!-- //회의록 -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('meetingDocument_${approvalItem.seq}');return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- //회의록 팝업 -->
	
	</c:forEach>
	<input id="fileUpload" name="file" type="file" multiple style="display: none;">
<!-- //파일첨부 팝업 -->
</div>