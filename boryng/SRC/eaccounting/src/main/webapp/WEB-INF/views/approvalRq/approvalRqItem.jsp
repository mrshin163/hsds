<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/json2/20121008/json2.js"></script>
<script src="/js/jquery.fileupload.js"></script>

<script type="text/javascript">

// 품의서 상신
function saveApprovalRq(){
	
	var confirmMessage 	= "";
	var completeMessage = "";
	
	if($("#accountCode").val() == 2){
		confirmMessage 	= "심포지움 결재 요청을 하시겠습니까?";
		completeMessage = "심포지움 결재요청을 완료 하였습니다.";
	} else {
		confirmMessage 	= "품의서를 상신하시겠습니까?";
		completeMessage = "품의서 상신을 완료 하였습니다.";
	}
	
	bootbox.confirm(confirmMessage, function(result){
	if(result){
		
		var params = [];
		var lineParam = [];
		var approvalAttachList = [];
		
		var lineList = $("#approvalLine").find("tr");
		
		$(lineList).each(function(i) {
			var apUserId 	= $(this).attr("apUserId");
			var seq 		= $(this).attr("seq");
			lineParam.push({"seq" : seq, "apUserId" : apUserId})
	    });
		
		$("#fileBox").find("a.btnReg").each(function(){
			var attachId 	= $(this).attr("attachId");
			var typeCode 	= $(this).attr("typeCode");
			var seq			= 1;
			
			if(attachId != "") {
				approvalAttachList.push({"seq" : seq, "approvalId" : '${approvalId}', "attachId" : attachId, "approvalAttachTypeCode" : typeCode});
			}
		})
		
		$("#approvalItemList").find("tr").each(function(){
			var approvalId		= $(this).attr("approvalId");
			var seq 			= $(this).attr("seq");
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
			var oldApprovalId	= $("#oldApprovalId").val();
			var accountCode		= $("#accountCode").val();
			var accountName		= $("#accountName").val();
			var budgetDeptCode	= $("#budgetDeptCode").val();
			var budgetDeptName	= $("#budgetDeptName").val();
			
			params.push({
						"approvalId"		: approvalId,
						"oldApprovalId"		: oldApprovalId,
						"seq" 				: seq,
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
					if (jsonData.code == 'RA') {
						bootbox.alert(jsonData.msg, function(){
							location.href = '/approval/completeList';
						});
					} else if (jsonData.code == 'S') {
						bootbox.alert(completeMessage, function(){
							if($("#accountCode").val() == 2){
								location.href = '/approvalRq/cancelSymposiumList';
							}else{
								location.href = '/approval/progressList';
							}
						});
					}
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
	/* 세미나 결과 보고서 조합 */
	var resultDocument = $("#seminarResultDocument_" + seq);
	var resultDocumentId = resultDocument.attr("id");	
	
	var approvalId = $("#" + resultDocumentId + " > input[name=targetId]").val();
	var documentData = $("form[name=resultDocumentForm_" + seq + "]").serialize().replace(/\+/gi, " ");
	
	var resultDocumentData = decodeURIComponent(documentData.replace(/&/gi, "『"));
	var targetId = "#" + approvalId;
	
	$(targetId).attr("seminarReport" , resultDocumentData);
	
	$("#resultDocumentInfo").html("... ");
	
	hideModal(resultDocumentId);
}


function meetingDocument(seq){
	
	var resultDocument = $("#meetingDocument_" + seq);
	var resultDocumentId = resultDocument.attr("id");	
	
	var approvalId = $("#" + resultDocumentId + " > input[name=targetId]").val();
	var documentData = $("form[name=meetingDocumentForm_" + seq + "]").serialize().replace(/\+/gi, " ");

	var resultDocumentData = decodeURIComponent(documentData.replace(/&/gi, "『"));
	var targetId = "#" + approvalId;
	
	$(targetId).attr("meetReport" , resultDocumentData);
	
	$("#resultDocumentInfo").html("... ");
	
	hideModal(resultDocumentId);
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
	
	
	if(accountCode == 2){
		if(budgetDeptCode != '53210' && budgetDeptCode != '54110' && budgetDeptCode != '54120' 
			&& budgetDeptCode != '53003' && budgetDeptCode != '55009' && budgetDeptCode != '55012' 
				&& budgetDeptCode != '55011' && budgetDeptCode != '53001'){
			bootbox.alert("심포지엄 요청은 마케팅 부서를 선택하여야 합니다.");
			return;
		}
	}
	
	
	$("#approvalItemList").find("tr").each(function(){
		
		//첨부파일 첨부 확인 
		var seminarFile 	= $("#seminarList li").length;
		var meetfile		= $("#meetList li").length;
		var qnaFile			= $("#qnaList li").length;
		var cardFile		= $("#cardList li").length;
		var approvalFile	= $("#approvalList li").length;
		var etcFile			= $("#etcList li").length;
		
		
		// 2015-03-26
		// 계정구분이 '기타경비'인 경우 세부계정 필수 아님 처리
		//if(accountCode == 5 || accountCode == 4){
		if(accountCode == 5){
			if($(this).attr("account2Code") == ''){
				bootbox.alert("세부계정을 선택하여 주세요");
				return;
			}
		}
		
		if(accountCode == 1 || accountCode == 2){
			if($(this).attr("productCode") == ''){			
				bootbox.alert("제품군/명을 선택하여 주세요");
				return;
			}
		}
		
		if(accountCode == 1){
			if($(this).attr("customerCode") == ''){			
				bootbox.alert("거래처를 선택 하여 주세요");
				return;
			}
		}
		
		if(accountCode == 1){
			if($(this).attr("ftrCode") == ''){			
				bootbox.alert("공정경쟁규약 선택 하여 주세요");
				return;
			}
		}
		
		// 2015-03-27 사용내역은 필수 체크 아니도록 요청. 처리.
		//if($(this).attr("details") == ''){			
		//	if(accountCode>4){
		//		bootbox.alert("사용내역을 입력 하여 주세요");
		//		return;
		//	}
		//}
		
		if(accountCode == 1){
			if($(this).attr("account2Code") == '635130200' || $(this).attr("account2Code") == '635130400'){
				
				if($(this).attr("seminarReport") == ''){			
					bootbox.alert("결과 보고서를 입력 하여 주세요");
					return;
				}
				
				var seminarVal = $(this).attr("seminarReport");
				
				var arr = seminarVal.split('『');
				for (var i = 0; i < arr.length; i++) {
					var tmp = arr[i].split('=');
					if(tmp[1] == '' || tmp[1] == null) {
						bootbox.alert("결과 보고서 작성을 완료 하여 주십시요");
						return;
					}
				}
				
				if(seminarFile == 0){
					bootbox.alert("세미나 사진을 첨부 하여주세요");
					return;
				}
				if(meetfile == 0){
					bootbox.alert("방명록을 첨부하여 주세요");
					return;
				}
			}
			
			if($(this).attr("account2Code") == '635130500'){
				if(qnaFile == 0){
					bootbox.alert("질의응답을 첨부하여 주세요");
					return;
				}
			}
			
			if(cardFile == 0){
				bootbox.alert("카드 세부를 첨부 하여 주세요");
				return;
			}
		}
		/*	회의록 필수 제외
		if(accountCode == 5 && $(this).attr("account2Code") == '635130100'){
			if($(this).attr("meetReport") == ''){			
				bootbox.alert("회의록을 입력 하여 주세요");
				return;
			}
			
			var meetVal = $(this).attr("meetReport");
			
			var arrMeetVal = meetVal.split('『');
			for (var i = 0; i < arrMeetVal.length; i++) {
				var tmp = arrMeetVal[i].split('=');
				if(tmp[1] == '' || tmp[1] == null) {
					bootbox.alert("회의록 작성이 완료되지 않았습니다. 회의록 작성을 완료해 주세요");
					return;
				}
			}
		}
		*/
		//결재선 생성 확인 
		var lineGenerate = $("#approvalLine").attr("generated");
		
		if(lineGenerate == "false" && accountCode != 2){
			bootbox.alert("결재선이 존재 하지않습니다. 결재선을 추가 해 주세요");
			return;
		}
		
		showModal('approvalRqPopLayer', '${approvalId}');
		
	})
	
}

//코드아이디로 코드리스트를 뿌려준다
function commCodeComboList(grpCodeId, elementId){
	var commCodeListHtml = "";
	var targetForm = "#${approvalId}_form";
	
	var salesYn = '${salesYn}';
	
	$.ajax({
	    url : "/rest/codeApi/commCodeList/" + grpCodeId + "/0",
        type : "GET",
        dataType : "json",
        success : function(jsonData) {
        	var optionSales = '';
        	var optionSemina = '';
        	var optionSympo = '';
        	var optionEtc = '';
        	if(salesYn == 'Y'){
        		$.each(jsonData.codeList, function(key, codeList) {
        			// 2015-03-23
        			// select 순서 지정 요청 - 영업일때만
        			if(codeList.code=='1'){
        				optionSemina = "<option class='inputTit' value='" + codeList.code + "'>" + codeList.codeName +"</option>";
        			}else if(codeList.code=='2'){
        				optionSympo = "<option class='inputTit' value='" + codeList.code + "'>" + codeList.codeName +"</option>";
        			}else if(codeList.code=='3'){
        				optionSales = "<option class='inputTit' value='" + codeList.code + "'>" + codeList.codeName +"</option>";
        			}else if(codeList.code=='4'){
        				optionEtc = "<option class='inputTit' value='" + codeList.code + "'>" + codeList.codeName +"</option>";
        			}
            	})
    			$("#" + elementId).append(optionSales);
    			$("#" + elementId).append(optionSemina);
    			$("#" + elementId).append(optionSympo);
    			$("#" + elementId).append(optionEtc);
        	} else {
        		$.each(jsonData.codeList, function(key, codeList) {
        			if(key >= 4 ) commCodeListHtml += "<option class='inputTit' value='" + codeList.code + "'>" + codeList.codeName +"</option>";
            	})
            	$("#" + elementId).append(commCodeListHtml);
        	}
        	
        	
        	
       },
        error : function(data) {
        	bootbox.alert("에러가 발생하였습니다.")
        }
    }).done(function(){
    	
    	var accountCode = '${accountCode}';
    	if(accountCode != '') {
    		$("#commCodeList").val(accountCode);
    		approvalLineGenerate();
    		validationFiledShow(accountCode);
    	}
    	
    	if(accountCode == '2'){
    		$("#commCodeList option").not(":selected").attr("disabled", "disabled");
    	}
    	
    	initAll();
    	//ajax List 가져온후 jquery ui 적용
    	 $(".customSelect").selectbox({
    		onChange: function (val, inst) {
    			
    			//20150323 유대웅 - 품의서 작성 화면 불러올 때 현재 값으로 결재라인등의 초기화 
    			initAll();
    			/* var accountName = $("#" + elementId + ">option:selected").text();
    			
    			$("#accountCode").val(val);
    			$("#accountName").val(accountName);
    			
				if($("#deptName").val() != "" && val != 2){
					approvalLineGenerate();
				}
				validationFiledShow(val);
				accountCodeByDeptList(val); */
			}
    	});
    });
}

function initAll(){
	var accountName = $("#commCodeList :selected").text();
	var val = $("#commCodeList :selected").val();
	
	$("#accountCode").val(val);
	$("#accountName").val(accountName);
	
	if($("#deptName").val() != "" && val != 2){
		approvalLineGenerate();
	}
	validationFiledShow(val);
	accountCodeByDeptList(val);
}


//계정 구분에 따라 입력 필드 조정
function validationFiledShow(val){
	//유동필드 초기화 
	$("#approvalRqDataTable").find(".dyNode").each(function(i){
		$(this).css("display", "none");
	})
	
	$("#fileBox").find(".dyNode").each(function(i){
		$(this).css("display", "none");
	})
	
	if(val == 2){
		$("#btnAddApprovalLine").hide();
		approvalLineHtml = "<tr><td colspan='5'>심포지움은 결재요청은 결재선을 지정하지 않습니다.</td></tr>";
		$("#approvalLine").html(approvalLineHtml);
		$("#approvalLine").attr("generated", "false");
	}
	
	
	// 꼐정 구분에 따라 필드정보 show
	if(val == 1){
		$("#btnApprovalRq").text("결재 상신");
		$("#approvalRqDataTable").find(".type1").each(function(i){
			$(this).css("display", "table-cell");
		})
		$("#fileBox").find(".fileRow1").each(function(){
			$(this).css("display", "table-row");
		})
		
	} else if(val == 2){
		$("#btnApprovalRq").text("결재 요청");
		$("#approvalRqDataTable").find(".type2").each(function(i){
			$(this).css("display", "table-cell");
		})
		$("#fileBox").find(".fileRow1").each(function(){
			$(this).css("display", "table-row");
		})
	} else if(val == 3){
		$("#btnApprovalRq").text("결재 상신");
		$("#approvalRqDataTable").find(".type3").each(function(i){
			$(this).css("display", "table-cell");
		})
		$("#fileBox").find(".fileRow2").each(function(){
			$(this).css("display", "table-row");
		})
	} else if(val == 4){
		$("#btnApprovalRq").text("결재 상신");
		$("#approvalRqDataTable").find(".type4").each(function(i){
			$(this).css("display", "table-cell");
		})
		$("#fileBox").find(".fileRow2").each(function(){
			$(this).css("display", "table-row");
		})
	} else if(val == 5){
		$("#btnApprovalRq").text("결재 상신");
		$("#approvalRqDataTable").find(".type5").each(function(i){
			$(this).css("display", "table-cell");
		})
		$("#fileBox").find(".fileRow2").each(function(){
			$(this).css("display", "table-row");
		})
	} 
}

function deptChoose(deptCode){
	$("#budgetDeptCode").val(deptCode);
}

function approvalLineGenerate(){
	
	var amount			= ${amountTotal};
	var accountCode 	= $("#accountCode").val();
	var deptCode 		= $("#budgetDeptCode").val();
	var secretary 		= $("#checkAuth").val();
	
	var approvalLineHtml = "";
	
	if(secretary != "R01" && $("#commCodeList>option:selected").val() != 2){
		
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
	    								+ "	<td></td>"
	    								+ "</tr>";
	   				})
	   			} else {
	   				$("#approvalLine").attr("generated", "false");
	   				approvalLineHtml = "<tr><td colspan='5'>선택한 계정구분과 예산부서로 결재선이 존재 하지 않습니다.</td></tr>";
	   				
	   				// 결재선이 만들어지지 않으면 결재선 버튼을 보여짐
	   				/* $("#btnAddApprovalLine").show(); */
	   				
	   			}
	   			$("#approvalLine").html(approvalLineHtml);
	   			
	   		},
	   	    error : function(err) {
	   	    	bootbox.alert("처리중 장애가 발생하였습니다." + err);
	   		}
	   	})
	}
	
}

//////첨부파일 리스트에 들어갈 HTML 생성
function getFileHtml(attachFile){
	//////  체크파일
	var fileListHtml = "";
	
	////// 최초 등록시(attachFile==null)에는 다운로드가 안된다. 삭제만 됨
	fileListHtml += "<li>" + attachFile.lFileName + "." +attachFile.ext + ' <button onclick="deleteFile(\''+ attachFile.attachId +'\', \''+ attachFile.seq +'\',$(this).parent());" class="btnDefaultDel">삭제</button>';
		/* fileListHtml += "<li>" + fileName */ 
/* 					+ "	<div class='progress' style='display:none'>" 
					+ "		<p class='progress-bar'></p>" 
					+ "	</div>" 
					+ "</li>"; */


	fileListHtml += "";
	return fileListHtml;
}

////// 첨부파일리스트 가져오기
function getFileList(attachId, fileBoxId){	
	var url = '/rest/uploadApi/getFileList/'+ attachId;
	
	//////  조회수 증가, 공지사항 상세내용 가져오기
	jQuery.ajax({
		type : 'GET',
		url : url,
		dataType : "json",
		contentType: 'application/json',
		success : function(jsonData) {
			$(fileBoxId).empty();
			////// 첨부파일 리스트			
			if(jsonData.fileList!=null && jsonData.fileList.length!=0){
				var list = jsonData.fileList;
				$.each(list, function(index, attachFile) {
					$(fileBoxId).append(getFileHtml(attachFile));			
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


function approvalFileUpload(targetEl){
	
	var approvalTypeEl 	= "#" + targetEl.id;
	var targetList		= $("#fileBox").attr("targetList", $(approvalTypeEl).parent().find('ul').attr('id'));
	var ftrCode 		= $("#approvalRqform").val();
	var typeCode		= $(approvalTypeEl).attr("typeCode");
	var seq 			= $(targetList).attr("curSeq");
	var accountCode		= $("#approvalRqform input[name=accountCode]").val();
	
	
	var targetRow		= $(approvalTypeEl).parents().parents().parents().parents().parents().parents().parents().find("input[name=targetId]").val();
	var appSeq			= $("#" + targetRow).attr("seq"); 
	

	//////첨부파일 마스터ID
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
				$("#fileUpload").fileupload('option', 'url', '/rest/uploadApi/uploadFile/APR01/' + attachId + '/${approvalId}/' + typeCode + "/" + accountCode + "/" + appSeq);
				
			},
			error : function(err) {
				bootbox.alert("처리중 장애가 발생하였습니다." + err);
			}
		});		
	} else {
		$("#fileUpload").fileupload('option', 'url', '/rest/uploadApi/uploadFile/APR01/' + $(approvalTypeEl).attr('attachId') + '/${approvalId}/' + typeCode + "/" + accountCode + "/" + appSeq);
		
	}
	
	$("#fileUpload").trigger("click");
	
}

function deleteApprovalLine(seq){
	var lineSeq = $("#approvalLine tr").length;
	
	$("#approvalLine_" + seq).remove();
	
	if(lineSeq == 0) {
		$("#approvalLine").attr("generated", "false");
	}
	
	$("#approvalLine tr td.seq").each(function(i){
		$(this).html((i + 1) + "차 결재자");
	})
	
	$("#approvalLine tr").each(function(i){
			$(this).attr("seq", (i + 1));
	})
}


function accountCodeByDeptList(code){
	var deptList = [{"deptCode" : "53210", "deptName" : "CV/CNS MKT팀"}, 
	                {"deptCode" : "54110", "deptName" : "AI/AA MKT팀"},
	                {"deptCode" : "54120", "deptName" : "AN MKT팀"},
	                {"deptCode" : "53003", "deptName" : "카나브-병원"},
	                {"deptCode" : "55009", "deptName" : "카나브-의원"},
	                {"deptCode" : "55012", "deptName" : "의원-항생일반MKT팀"},
	                {"deptCode" : "55011", "deptName" : "의원-순환MKT팀"},
	                {"deptCode" : "53001", "deptName" : "NEPHRO BU"}];
	var listHtml = '';
	 
	if(code == "1" || code == "2" ) {
		for(i in deptList){
			listHtml += "<tr style='cursor:pointer' deptCode='" + deptList[i]['deptCode'] + "' deptName = '" + deptList[i]['deptName'] + "'>"
			+ "	<td>" + deptList[i]['deptCode'] + "</td>"
			+ "	<td>" + deptList[i]['deptName'] + "</td>"
			+ "</tr>";
		}
		
		$("#deptSearchList").html(listHtml);
	} else{
		$("#deptSearchList").html("<tr><td colspan='2'>부서명으로 검색해 주세요</td></tr>");
	}
	
	$("#deptSearchList tr").each(function(){
		$(this).on("click", function(){
			$("#budgetDeptCode").val($(this).attr("deptCode"));
			
			$("#budgetDeptName").val($(this).attr("deptName"));
			$("#deptName").val($(this).attr("deptName"));
			
			if($("#commCodeList>option:selected").val() != 0){
				approvalLineGenerate();
			}
			
			hideModal("deptListPopLayer");
		})
	})
}


function getAttachFileList(seq){
	
	$.ajax({
			type : 'GET',
    		url : '/rest/approvalApi/attachFile/' + ${approvalId} + '/' + seq,
    		dataType : "json",
    		contentType: 'application/json',
    		success : function(jsonData) {
    			$.each(jsonData, function(key, deptInfo) {
    				deptSearchListHtml += "<tr style='cursor:pointer' deptCode='" + deptInfo.deptCode + "' deptName = '" + deptInfo.deptName + "'>"
	    								+ "	<td>" + deptInfo.deptCode + "</td>"
	    								+ "	<td>" + deptInfo.deptName + "</td>"
	    								+ "</tr>";
    			})
    			$("#deptSearchList").html(deptSearchListHtml);
    			
    		},
	})
}

function openUploadFilePopup(popupName, obj){
	bootbox.confirm("현재 선택된 계정구분 - [ "+$("#commCodeList :selected").text()+" ] 로 파일이 업로드 됩니다.\n 계속 하시겠습니까?", function(result){
 		if(result){
			showModal('fileUploadPopLayer', popupName, obj);
			//return false;
		}
	});
}

function emptyCheck(str){
	if(str==null || str==''){
		return 'all';
	}
	return str;	
}


$(function(){
	
	//계정 구분 ACCOUNT_CD 목록 불러오기
	commCodeComboList('ACCOUNT_CD', 'commCodeList');
	getComCdGrpId('PROD_CD_TYPE', 'productCdType', null, 'N');
	getComCdGrpId('PROD_CD', 'productCd', null, 'Y');
	
	$('#productCdType').change(function(){
		if($(this).val()=='all')
			getComCdGrpId('PROD_CD', 'productCd', null, 'Y');
		else
			getComCdGrpId($(this).val(), 'productCd', null, 'Y');
	});
	
	var secretary = $("#checkAuth").val();
	
	if(secretary == "R01"){
		$("#btnAddApprovalLine").show();
	}
	
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
						if($("#accountCode").val() == 2){
							$("#btnAddApprovalLine").hide();
						}
						
						if(($("#commCodeList>option:selected").val() != 0 && $("#accountCode").val() != 2) && secretary != "R01" ){
							approvalLineGenerate();
						}
					})
				})
    	    },
    		error : function(err) {
    			bootbox.alert("처리중 장애가 발생하였습니다." + err);
    		}
		})
	})
	
	$("#targetDetail").blur(function(){
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
    	var targetListId = "#" + $("#fileBox").attr("targetList");
    	getFileList($(targetListId).parent().find('a').attr('attachId') ,targetListId);
    	$("#addFile i").addClass("add");
    	$('.progress').hide();
    	
    }).on('fileuploadfail', function (e, data) {
    	$('#fileList').append("<tr><td colspan=\"2\">"+data.files[0].name + " - 업로드 실패</td></tr>");
    	$('.progress').hide();
    });
	
	$("#btnUserSelect").click(function(){
		
		var approvalLineHtml = "";
		var lineGenerate = $("#approvalLine").attr("generated");
		
		var seq	= $("#approvalLine tr").length;
		var addedUser = [];
		
		$("#approvalLine tr").each(function(){
			addedUser.push($(this).attr('id'));
		})
		
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
			
			if($.inArray("approvalLine_" + userId, addedUser) > 0){
				bootbox.alert("이미 추가된 결재자 입니다.");
				return false;
			} else {
				approvalLineHtml +=   "<tr seq='' id='approvalLine_" + userId + "' apUserId='" + userId + "' apUserName='" + userName + "' apDeptCode='" + deptCode + "' apDeptName='" 
				+ deptName + "' apTitleCode='" + titleCode + "' apTitleName='" + titleName + "'  apDutyCode='" + dutyCode +"' apDutyName='" + dutyName +"'>"
				+ "	<td class='seq'></td>"
				+ "	<td>" + userName + "&nbsp;" + dutyName + "</td>"
				+ "	<td>" + deptName + "</td>"
				+ "	<td><a href='javascript:deleteApprovalLine(\"" + userId + "\");' class='btnDefaultDel'>삭제</a></td>"
				+ "</tr>";
			}
			
		});
		
		if(lineGenerate == "true"){
			$("#approvalLine").append(approvalLineHtml);
		} else {
			$("#approvalLine").html(approvalLineHtml);
			$("#approvalLine").attr("generated", "true");
		}
		
		$("#approvalLine tr td.seq").each(function(i){
			$(this).html((i + 1) + "차 결재자");
		})
		
		$("#approvalLine tr").each(function(i){
			$(this).attr("seq", (i + 1));
		})
		
		hideModal("userListPopLayer");
		$("#userSearchList").html("<tr><td colspan='5' style='text-align: center;'>부서명과 성명으로 검색하여 주십시요.</td></tr>");
		
	})
	
	
	var loginDeptCode 	= '${loginDeptCode}';
	var loginDeptName 	= '${loginDeptName}';
	var budgetDeptCode 	= '${budgetDeptCode}';
	var budgetDeptName 	= '${budgetDeptName}';
	
	if(budgetDeptCode == ''){
		$("#budgetDeptCode").val(loginDeptCode);
		$("#budgetDeptName").val(loginDeptName);
		$("#deptName").val(loginDeptName);
	} else {
		$("#budgetDeptCode").val(budgetDeptCode);
		$("#budgetDeptName").val(budgetDeptName);
		$("#deptName").val(budgetDeptName);
	}
	
	$("#ruleDocumentPopLayer").find(".topSrch").show();

})

</script>
<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>
<form action="" id="approvalRqform">
	<sec:authorize access="hasRole('R01')">
	<input type="hidden" name="checkAuth" id="checkAuth" value="R01">
	</sec:authorize>
	<input type="hidden" name="accountCode" id="accountCode" value="${accountCode}">
	<input type="hidden" name="accountName" id="accountName" value="${accountName}">
	<input type="hidden" name="budgetDeptCode" id="budgetDeptCode" value="${budgetDeptCode}">
	<input type="hidden" name="budgetDeptName" id="budgetDeptName" value="${budgetDeptName}">
	<input type="hidden" name="ftrCode" id="ftrCode" value="${ftrCode}">
	<input type="hidden" name="rqLineYn" id="rqLineYn" value="N">
	<input type="hidden" name="attachYn" id="attachYn" value="N">
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
						<th width="40px"></th>
					</tr>
				</thead>
				<tbody id="approvalLine" generated="false">
					<!-- 결재라인  -->
					<tr seq = "0">
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
							</select>
						</span>
						<span class="inputTit second">예산 부서</span><span class="customInput">
							<input type="text" name="deptName" id="deptName" disabled="disabled" value="${budgetDeptName}"> 
							<a class="btnView" onClick="showModal('deptListPopLayer');return false;"><i class="icon iconView">돋보기</i></a>
						</span>
					</li>
				</ul>
			</div>
			
			<div class="scrollTable" id="scrollTable">
				<div class="inner">
					<input id="oldApprovalId" type="hidden" value="${oldApprovalId}"/>	
					<table class="defaultType defaultType02" id="approvalRqDataTable">
						<thead>
							<tr>
								<th width="30px">번호</th>
								<th width="70px">사용일</th>
								<th width="200px">상호</th>
								<th width="100px">합계금액</th>
								<th width="80px">담당자</th>
								<th width="120px" class="dyNode type1 type3 type4 type5">세부계정</th>
								<th width="100px" class="dyNode type1 type2 type3 type4">제품군/명</th>
								<th width="120px" class="dyNode type1 type3 type4">거래처</th>
								<th>사용내역</th>
								
								<!-- 세미나 / 심포지움 -->
								<th width="120px" class="dyNode type1">공정경쟁규약</th>
								<th width="80px" class="dyNode type1">결과보고서</th>
								<th width="50px" class="dyNode" name="meetDocument" style="display:none;">회의록</th>
								<th width="50px" >첨부</th>
							</tr>
						</thead>
						<tbody id="approvalItemList">
							<c:forEach var="approvalItem" items="${approvalItem}" varStatus="status">
							
							<tr id="approvalRq_${approvalItem.seq}" 
								approvalId="${approvalItem.approvalId}"							 
								seq ="${approvalItem.seq}" 
								sApprovalSeq="${approvalItem.sApprovalSeq}" 
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
								<td class="dyNode type1 type3 type4 type5">
									<input class="infoField" name="account2Info" type="text" readonly="readonly" value="${approvalItem.account2Name}" >
									<a href="javascript:;" title="세부계정" class="btnView"  onclick="getAccount2Code('approvalRq_${approvalItem.seq}')"><i class="icon iconView">돋보기</i></a>
								</td>
								<td class="dyNode type1 type2 type3 type4">
									<div style="display:flex;">
										<select name="productCdType" id="productCdType"></select>
										<select name="productCd" id="productCd" class="productCd"></select>
									</div>
								</td>
								<td class="dyNode type1 type3 type4">
									<input class="infoField" name="customerInfo" type="text" readonly="readonly" value="${approvalItem.customerName}" >
									<a href="" title="거래처" class="btnView" onclick="showModal('customerListPoplayer' , 'approvalRq_${approvalItem.seq}');return false;"><i class="icon iconView">돋보기</i></a>
								</td>
								<td ><input type="text" name="details" style="width: 90%" id="targetDetail" value="${approvalItem.details}"></td>
								<td class="dyNode type1">
									<input class="infoField" name="ruleDocumentInfo" type="text" readonly="readonly" value="${approvalItem.ftrCode}" >								
									<span></span> <a href="" title="공정경쟁규약" class="btnView" onclick="showModal('ruleDocumentPopLayer', 'approvalRq_${approvalItem.seq}');getRuleDocument('0', 'approvalRq_${approvalItem.seq}'); return false;"><i class="icon iconView">돋보기</i></a>
								</td>
								<td class="dyNode type1">
									<span id="resultDocumentInfo"></span><a href="" title="결과보고서" onclick="showModal('seminarResultDocument_${approvalItem.seq}', 'approvalRq_${approvalItem.seq}'); return false;"><i class="iconDocument">작성문서</i></a>
								</td>
								<td class="dyNode" name="meetDocument" style="display:none;">
									<span id="meetingDocumentInfo"></span><a href="" title="회의록" onclick="showModal('meetingDocument_${approvalItem.seq}', 'approvalRq_${approvalItem.seq}');return false;"><i class="iconDocument">작성문서</i></a>
								</td>
								<td><button id="addFile" title="첨부파일" onclick="openUploadFilePopup('approvalRq_${approvalItem.seq}', this);"><i class="iconFile">파일</i></button></td>
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
								<td class="dyNode type1 type3 type4 type5"></td>
								<td class="dyNode type1 type2 type3 type4"></td>
								<td class="dyNode type1 type3 type4"></td>
								<td></td>
								<td class="dyNode type1"></td>
								<td class="dyNode type1"></td>
								<td class="dyNode type5"></td>
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
	<div class="layer" id="fileUploadPopLayer">
		<input type="hidden" name="targetId" value="">
		<div id="attachIdList"></div>
		<div class="layerInner layerInner02">
			<strong class="firstTit">파일첨부</strong>
			<!-- 파일첨부 -->
			<div class="long">
				<!-- <div class="topSrch">
					<a href="" class="btnReg">파일추가</a> <a href="" class="btnCancel">파일삭제</a>
				</div> -->
				<table class="defaultType">
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
					<tbody id="fileBox" targetList="">
						<tr class="dyNode fileRow1">
							<th>세미나 사진</th>
							<td>
								<ul style="float: left" id="seminarList" curSeq=0></ul>
								<a style="float:right;" attachId=""  id="seminaFile" href="javascript:;" onClick="approvalFileUpload(this);" class="btnReg" typeCode="A">파일찾기</a>
							</td>
						</tr>
						<tr class="dyNode fileRow1">
							<th>방명록</th>
							<td>
								<ul style="float: left" id="meetList" curSeq=0></ul>
								<a style="float:right;"  attachId=""  id="meetFile" href="javascript:;" onClick="approvalFileUpload(this);" class="btnReg" typeCode="B">파일찾기</a>
							</td>
						</tr>
						<tr class="dyNode fileRow1">
							<th>질의응답</th>
							<td>
								<ul style="float: left" id="qnaList" curSeq=0></ul>
								<a style="float:right;" attachId=""  id="qnaFile" href="javascript:;" onClick="approvalFileUpload(this);" class="btnReg" typeCode="C">파일찾기</a>
							</td>
						</tr>
						<tr class="dyNode fileRow1">
							<th>카드세부</th>
							<td>
								<ul style="float: left" id="cardList" curSeq=0></ul>
								<a style="float:right;" attachId=""  id="cardFile" href="javascript:;" onClick="approvalFileUpload(this);" class="btnReg" typeCode="D">파일찾기</a>
							</td>
						</tr>
						<tr class="dyNode fileRow2">
							<th>품의서</th>
							<td>
								<ul style="float: left" id="approvalList" curSeq=0></ul>
								<a style="float:right;" attachId=""  id="approvalFile" href="javascript:;" onClick="approvalFileUpload(this);" class="btnReg" typeCode="E">파일찾기</a>
							</td>
						</tr>
						<tr class="dyNode fileRow2">
							<th>기타첨부</th>
							<td>
								<ul style="float: left" id="etcList" curSeq=0></ul>
								<a style="float:right;" attachId=""  id="etcFile" href="javascript:;" onClick="approvalFileUpload(this);" class="btnReg" typeCode="F">파일찾기</a>
							</td>
						</tr>
					</tbody>
				</table>
				<input id="fileUpload" name="file" type="file"  multiple="multiple" style="display: none;">
				<!--
					// 2015-03-23
					// 첨부파일 등록시 다중 선택되지 않도록 변경
					<input id="fileUpload" name="file" type="file" multiple style="display: none;">
				-->
				<p style="text-align: center; margin-top: 20px"><a href="javascript:hideModal('fileUploadPopLayer');" class="btnReg">확인</a></p>
			</div>
			<!-- //파일첨부 -->
		</div>
		<button type="button" class="btnLayerClose" onclick="hideModal('fileUploadPopLayer');return false;">
			<img src="/images/btn_close.png" />
		</button>
	</div>
<!-- //파일첨부 팝업 -->


<c:forEach var="approvalItem" items="${approvalItem}" varStatus="status">
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
					<tbody name="seminarData">
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
				<tbody name="meetData">
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



</div>
