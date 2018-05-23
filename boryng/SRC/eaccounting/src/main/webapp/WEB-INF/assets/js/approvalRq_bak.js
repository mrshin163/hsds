/**************************** 품의서 상신 ************************//*

// 품의서 상신 (세부사항 저장)
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
			var accountCode		= $("#accountCode").val();
			var accountName		= $("#accountName").val();
			var budgetDeptCode	= $("#budgetDeptCode").val();
			var budgetDeptName	= $("#budgetDeptName").val();
			
			params.push({
						"approvalId"		: approvalId,
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
						"budgetDeptName" 	: budgetDeptName
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
	    	    	insertApprovalRqLine();
	    		}
	    		saveApprovalAttach();
	        	
		    	bootbox.alert(completeMessage, function(){
	        		location.href = '/approval/progressList';	
	        	});
	        },
	        error : function(data) {
	            alert("에러가 발생하였습니다.")
	        },
	        complete : function(){
	        },
		});
		} 
	})
}


*//** 세미나 결과 보고서 저장**//*
function seminaResultDocument(seq){
	var resultDocument = $("#seminarResultDocument_" + seq);
	var resultDocumentId = resultDocument.attr("id");	
	
	var approvalId = $("#" + resultDocumentId + " > input[name=targetId]").val();
	var documentData = $("form[name=resultDocumentForm_" + seq + "]").serialize();
	
	var resultDocumentData = decodeURIComponent(documentData.replace(/&/gi, "『"));
	var targetId = "#" + approvalId;
	
	$(targetId).attr("seminarReport" , resultDocumentData);
	
	$("#resultDocumentInfo").html("... ");
	
	hideModal(resultDocumentId);
}


*//** 회의록 저장 **//*
function meetingDocument(seq){
	
	var resultDocument = $("#meetingDocument_" + seq);
	var resultDocumentId = resultDocument.attr("id");	
	
	var approvalId = $("#" + resultDocumentId + " > input[name=targetId]").val();
	var documentData = $("form[name=meetingDocumentForm_" + seq + "]").serialize();
	
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
			alert("처리중 에러가 발생하였습니다.");
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


*//** 품의서 저장 validation **//*
function approvaValidation(){
	
	var accountCode 	= $("#accountCode").val();
	var budgetDeptCode 	= $("#budgetDeptCode").val();
	
	if(accountCode == ''){
		bootbox.alert("계정구분을 선택하여 주세요");
		return false;
	}
	
	if(budgetDeptCode == ''){
		bootbox.alert("예산부서를 선택하여 주세요");
		return false;
	}
	
	
	if(accountCode == 2){
		if(budgetDeptCode != '53210' && budgetDeptCode != '54110' && budgetDeptCode != '54120'){
			bootbox.alert("심포지엄 요청은 마케팅 부서를 선택하여야 합니다.");
			return false;
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
		
		
		if(accountCode == 5 || accountCode == 4){
			if($(this).attr("account2Code") == ''){
				bootbox.alert("세부계정을 선택하여 주세요");
				return false;
			}
		}
		
		if(accountCode == 1 || accountCode == 2){
			if($(this).attr("productCode") == ''){			
				bootbox.alert("제품군/명을 선택하여 주세요");
				return false;
			}
		}
		
		if(accountCode == 1){
			if($(this).attr("customerCode") == ''){			
				bootbox.alert("거래처를 선택 하여 주세요");
				return false;
			}
		}
		
		if(accountCode == 1){
			if($(this).attr("ftrCode") == ''){			
				bootbox.alert("공정경쟁규약 선택 하여 주세요");
				return false;
			}
		}
		
		if($(this).attr("details") == ''){			
			bootbox.alert("사용내역을 입력 하여 주세요");
			return false;
		}
		
		if(accountCode == 1){
			if($(this).attr("account2Code") == '635130200' || $(this).attr("account2Code") == '635130400'){
				
				if($(this).attr("seminarReport") == ''){			
					bootbox.alert("결과 보고서를 입력 하여 주세요");
					return false;
				}
				
				var seminarVal = $(this).attr("seminarReport");
				
				var arr = seminarVal.split('『');
				for (var i = 0; i < arr.length; i++) {
					var tmp = arr[i].split('=');
					if(tmp[1] == '' || tmp[1] == null) {
						bootbox.alert("결과 보고서 작성을 완료 하여 주십시요");
						return false;
					}
				}
				
				if(seminarFile == 0){
					bootbox.alert("세미나 사진을 첨부 하여주세요");
					return false;
				}
				if(meetfile == 0){
					bootbox.alert("방명록을 첨부하여 주세요");
					return false;
				}
			}
			
			if($(this).attr("account2Code") == '635130500'){
				if(qnaFile == 0){
					bootbox.alert("질의응답을 첨부하여 주세요");
					return false;
				}
			}
			
			if(cardFile == 0){
				bootbox.alert("카드 세부를 첨부 하여 주세요");
				return false;
			}
		}
		
		if(accountCode == 5 && $(this).attr("account2Code") == '635130100'){
			if($(this).attr("meetReport") == ''){			
				bootbox.alert("회의록을 입력 하여 주세요");
				return false;
			}
			
			var meetVal = $(this).attr("meetReport");
			
			var arrMeetVal = meetVal.split('『');
			for (var i = 0; i < arrMeetVal.length; i++) {
				var tmp = arrMeetVal[i].split('=');
				if(tmp[1] == '' || tmp[1] == null) {
					bootbox.alert("회의록 작성이 완료되지 않았습니다. 회의록 작성을 완료해 주세요");
					return false;
				}
			}
		}
		
		
		
		
		//결재선 생성 확인 
		var lineGenerate = $("#approvalLine").attr("generated");
		
		if(lineGenerate == "false" && accountCode != 2){
			bootbox.alert("결재선이 존재 하지않습니다. 결재선을 추가 해 주세요");
			return false;
		}
		
		showModal('approvalRqPopLayer', '${approvalId}');
		
	})
	
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



function insertApprovalRqLine(){
	
	var params = [];
	var lineList = $("#approvalLine").find("tr");
	
	$(lineList).each(function(i) {
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
		
		params.push({"seq" : seq, "approvalId" : approvalId, "apUserId" : apUserId, "apUserName" : apUserName, "apDeptCode" : apDeptCode, "apDeptName" : apDeptName, 
			"apDutyCode" : apDutyCode, "apDutyName" : apDutyName, "apTitleCode": apTitleCode, "apTitleName" : apTitleName})
    });
	
	jQuery.ajax({
		type : 'POST',
		url : '/rest/approvalRqApi/insertApprovalRqLine',
		dataType : "json",
		contentType: 'application/json',
		data : JSON.stringify(params),
		success : function() {
			
		},
		error : function(err) {
			alert("처리중 장애가 발생하였습니다." + err);
			return false;
		}
	});
	
}

//////첨부파일 리스트에 들어갈 HTML 생성
function getListHtml(fileName, attachFile, dataId){
	//////  체크파일
	var fileListHtml = "";
	
	////// 최초 등록시(attachFile==null)에는 다운로드가 안된다. 삭제만 됨
	if(attachFile==null){
		fileListHtml += "<li>"+ fileName +"</li>";
	}else{		
		fileListHtml += "<li>" + fileName + " <a href='javascript:;' class='btnDefaultDel' >삭제</a>"
		 fileListHtml += "<li>" + fileName  
					+ "	<div class='progress' style='display:none'>" 
					+ "		<p class='progress-bar'></p>" 
					+ "	</div>" 
					+ "</li>";
	}

	fileListHtml += "";
	return fileListHtml;
}


function deleteFile(attachId, seq){
	var	url = "/rest/uploadApi/deleteFile/" + $(check).attr("attachId") + "/" + $(check).attr("seq");
	
	jQuery.ajax({
		type : 'POST',
		url : url,
		dataType : "json",
		contentType: 'application/json',
		success : function(jsonData) {		
			$(tr).remove();
			deleteCheckedFile();
		},
		error : function(err) {
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});
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
			success : function(jsonData) {
				var attachId = jsonData.attachId;
				$(approvalTypeEl).attr("attachId" , attachId);
				$("#fileUpload").fileupload('option', 'url', '/rest/uploadApi/uploadFile/APR01/' + attachId + '/${approvalId}/' + typeCode + "/" + accountCode + "/" + seq);
			},
			error : function(err) {
				alert("처리중 장애가 발생하였습니다." + err);
			}
		});		
	} else {
		$("#fileUpload").fileupload('option', 'url', '/rest/uploadApi/uploadFile/APR01/' + attachId + '/${approvalId}/' + typeCode + "/" + accountCode + "/" + seq);
	}
	
	$("#fileUpload").trigger("click");
	
}


function saveApprovalAttach(){
	var approvalAttachList = [];
	
	$("#fileBox").find("a.btnReg").each(function(){
		var attachId 	= $(this).attr("attachId");
		var typeCode 	= $(this).attr("typeCode");
		var seq			= 1;
		
		if(attachId != "") {
			approvalAttachList.push({"seq" : seq, "approvalId" : '${approvalId}', "attachId" : attachId, "approvalAttachTypeCode" : typeCode});
		}
		
	})
	
	if(approvalAttachList.length > 0){
		$.ajax({
	   		type : 'POST',
	   		url : '/rest/approvalRqApi/insertApprovalRqAttach/${approvalId}',
	   		dataType : "json",
	   		contentType: 'application/json',
	   		data : JSON.stringify(approvalAttachList),
	   		beforeSend:function(){
	   	    },
	   		success : function(jsonData) {
	   		},
			complete:function(){
			},
	   	    error : function(err) {
	   			alert("처리중 장애가 발생하였습니다." + err);
	   			return false;
	   		}
			})
	}
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



*/