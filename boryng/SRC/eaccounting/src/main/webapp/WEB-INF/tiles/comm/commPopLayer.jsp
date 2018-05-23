<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script>
$(function(){
	$("#userListForuser").autocomplete({
        source : function(request, response) {
 
        	$.ajax({
                url : "/rest/userApi/nameSearch/" + encodeURIComponent($("#userListForuser").val()),
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
	
	$("#userListForDept").autocomplete({
        source : function(request, response) {
 
        	$.ajax({
                url : "/rest/deptApi/deptNameSearch/" + encodeURIComponent($("#userListForDept").val()),
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
	
	
	$("#btnUserSearch").click(function(){
		
		var userNameSearch = jQuery("#userListForuser").val();
		var deptNameSearch = jQuery("#userListForDept").val().split('/').join('`');
		
		if(userNameSearch == "" && deptNameSearch == "") {
			bootbox.alert("검색어를 입력해 주세요"); 
			return false;
		}else {
			userSearch(userNameSearch,deptNameSearch,null);
		}
	})
	
	
	function userSearch(userNameSearch, deptNameSearch){
		if(userNameSearch == "") userNameSearch = "all";
		if(deptNameSearch == "") deptNameSearch = "all"; 
		
		jQuery.ajax({
			type : 'GET',
			url : '/rest/userApi/userList/' + encodeURIComponent(userNameSearch) + '/' + encodeURIComponent(deptNameSearch) + "/0",
			dataType : "json",
			success : function(jsonData) {
				var userSearchListHtml = "";
				
				if(jsonData.userList.length > 0){
					$.each(jsonData.userList, function(key, userList) {
						userSearchListHtml += "<tr userId='" + userList.userId +"' userName='" + userList.userName + "' deptCode='" 
								+ userList.deptCode + "' deptName='" + userList.deptName + "' titleCode='" + userList.titleCode + "' titleName='" + userList.titleName 
								+ "' dutyCode='" + userList.dutyCode + "' dutyName='" + userList.dutyName + "'>"
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
	
	
	
	
	$("#userAuthSearch").click(function(){
		var authId = $("#authSelect option:selected").val();
		userAuthList(authId, 1);
	})
})
</script>

<!-- 사용자 선택 팝업 S -->
<div class="layer" id="userListPopLayer">
	<div class="layerInner layerInner02">
		<strong class="firstTit">사용자 선택</strong>
		<!-- 사용자 선택 S -->
		<div class="long">
			<div class="topSrch">
				<span class="name">부서명</span>
				<input type="text" id="userListForDept">
				<span class="name">성명</span>
				<input type="text" id="userListForuser">
				<button type="button" class="btnSrch" id="btnUserSearch">검색</button>
			</div>
			<div class="scrollTable-y" id="scrollTable-y" style="height: 300px">
				<div class="inner">
					<table class="defaultType">
						<colgroup>
							<col width="" />
							<col width="" />
							<col width="" />
							<col width="" />
							<col width="" />
						</colgroup>
						<thead>
							<tr>
								<th><input type="checkbox" name="allChk" /></th>
								<th>사번</th>
								<th>성명</th>
								<th>부서</th>
								<th>직급</th>
							</tr>
						</thead>
						<tbody id="userSearchList">
							<tr>
								<td colspan="5" style="text-align: center;">부서명과 성명으로 검색하여 주십시요.</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="btns">
				<button type="button" class="btnSave" id="btnUserSelect">선택</button>
			</div>
		</div>
		<!-- 사용자 선택 E -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('userListPopLayer'); return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- 사용자 선택 팝업 E -->

<!-- 부서조회  popLayer -->
<div class="layer" id="deptListPopLayer">
	<div class="layerInner layerInner02">
		<strong class="firstTit">부서 선택</strong>
		<div class="long">
			<div class="topSrch">
				<span class="name">부서코드</span> <input type="text" id="deptListForDeptCd">
				<span class="name">부서명</span> <input type="text" id="deptListForDept">
				<button type="button" class="btnSrch" id="btnDeptSearch">검색</button>
			</div>
			<div class="scrollTable-y" id="scrollTable-y" style="height:300px;overflow:auto">
				<div class="inner">
					<table class="defaultType">
						<colgroup>
							<col width="" />
							<col width="" />
						</colgroup>
						<thead>
							<tr>
								<th>부서 코드</th>
								<th>부서명</th>
							</tr>
						</thead>
						<tbody id="deptSearchList">
							<tr>
								<td colspan="5" style="text-align: center;">부서명 으로 검색 하여 주십시요.</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!-- <div class="btns">
				<button type="button" class="btnSave" id="btnUserAuthReg">등록</button>
			</div> -->
		</div>
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal($(this).parent().attr('id'));return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- //신규등록 팝업 -->


<script type="text/javascript">
$(function() {
	$("#customerNameList").autocomplete({source: function(request, response) {

		$.ajax({
	        url: "/rest/customerApi/customerNameSearch/" + $("#customerNameList").val(),
	        type: "GET",
	        dataType: "json",
	        success: function(data) {
	            var result = data;
	            response(result);
	        },
	        error: function(data) {
	        	bootbox.alert("에러가 발생하였습니다.")
	        }
		});
		}
	});

	$("#btnCustomerSearch").click(function() {
	
		var approvalId = $("#customerListPoplayer > input[name=targetId]").val();
		
		var customerNameSearch = $("#customerNameList").val();
	
	    if (customerNameSearch == "") {
	        bootbox.alert("검색어를 입력해 주세요");
	        return false;
	    } else {
	        customerSearch(customerNameSearch, approvalId);
	    }
	})
			
	        
     //거래처를 선택하 거래처이름을 거래처 필드에 넣어 준다
     function customerSearch(customerNameSearch, approvalId) {
	     $.ajax({
                 type: 'GET',
                 url: '/rest/customerApi/customerList/' + customerNameSearch,
                 dataType: "json",
                 success: function(jsonData) {
                     var customerSearchListHtml = "";

                     if (jsonData.length > 0) {
                         $.each(jsonData,function(key,customerList) {
                         	customerSearchListHtml += "<tr style='cursor:pointer' customerCode='" + customerList.customerCode + "' customerName='" + customerList.customerName + "' bizNo='" + customerList.bizNo + "'>" + "<td>" + customerList.customerCode + "</td>" + "<td style='text-align:left;'>" + customerList.customerName + "</td>" + "<td>" + customerList.ceoName + "</td>" + "<td style='text-align:left;'>" + customerList.address + "</td>" + "</tr>";
						});
                     } else {
                         customerSearchListHtml = "<tr>" + "<td colspan='4' style='text-align: center;'>일치하는 검색 결과가 없습니다.</td>" + "</tr>";
                     }

                     $('#customerList').html(customerSearchListHtml);
                 },
                 complete: function() {
					$("#customerList tr").each(function() {
						$(this).on("click", function() {
							//해당 수정 ROW에 대한 form 지정
							var targetId = "#" + approvalId;
							
							$(targetId).attr({
								customerCode	: $(this).attr("customerCode"),
								customerName 	: $(this).attr("customerName"), 
								saupNo 			: $(this).attr("bizNo")
							});
							
							$(targetId + " td input[name=customerInfo]").val($(this).attr("customerName"));
							
							//거래처 검색 리스트 초기화
							$('#customerList').html('<tr><td colspan="5" style="text-align: center;">거래처 명으로 검색 하여 주십시요.</td></tr>');
							$('#customerNameList').val('');
							hideModal("customerListPoplayer");
						})
					})
                 },
                 error: function(err) {
                	 bootbox.alert("처리중 장애가 발생하였습니다." + err);
                 }
             });
     }

 })
</script>

<!-- 거래처 조회  popLayer -->
<div class="layer" id="customerListPoplayer">
	<input type="hidden" name="targetId" value="">
	<div class="layerInner layerInner02">
		<strong class="firstTit">거래처 선택</strong>
		<div class="long">
			<div class="topSrch">
				<span class="name">거래처명</span> <input type="text" name="customerName" id="customerNameList" style="width: 200px !important">
				<button type="button" class="btnSrch" id="btnCustomerSearch">검색</button>
			</div>
			<div class="scrollTable-y" id="scrollTable-y" style="height:300px;overflow:auto">
				<div class="inner">
					<table class="defaultType">
						<colgroup>
							<col width="" />
							<col width="" />
							<col width="" />
						</colgroup>
						<thead>
							<tr>
								<th>거래처코드</th>
								<th>거래처명</th>
								<th>대표자명</th>
								<th>주소</th>
							</tr>
						</thead>
						<tbody id="customerList">
							<tr>
								<td colspan="4" style="text-align: center;">거래처 명으로 검색 하여 주십시요.</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!-- <div class="btns">
				<button type="button" class="btnSave" id="btnUserAuthReg">등록</button>
			</div> -->
		</div>
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal($(this).parent().attr('id'));return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- //거래처 조회  popLayer -->

<script type="text/javascript">
//공정거래 규약 문서를 조회하여 뿌려준다 : 담당자 사번으로 조회 => 수정 로그인한 상신자 기준
function getRuleDocument(sabun, approvalId){
	
	if($("#ruleDocumentSabun").val() != ''){
		sabun = $("#ruleDocumentSabun").val();
	} else {
		sabun = '0';
	}
	
	$.ajax({
	    url : "/rest/ruleDocumentApi/ruleDocumentList/search/" + sabun,
	    type : "GET",
	    dataType : "json",
	    success : function(jsonData) {
	    	var ruleDocumentListHtml = "";
	    	
	    	
	    	if(jsonData.length > 0){
	    		$.each(jsonData, function(key, ruleDocumentList) {
	    			var used = "";
	    			if(ruleDocumentList.FLAG == 'Y') {
	    				used="used";
	    			} 
	    			 
		    		ruleDocumentListHtml += "<tr class='" + used + "' use='" + ruleDocumentList.FLAG + "' ftrCode=" + ruleDocumentList.MUNSEONO + " style='cursor:pointer'>"
		    								+ "<td>" + ruleDocumentList.MUNSEONO + "</td>"
		    								+ "<td>" + ruleDocumentList.MUNSEODATE + "</td>"
		    								+ "<td>" + ruleDocumentList.GEORAENAME + "</td>"
		    								+ "<td>" + ruleDocumentList.PUMMOK + "</td>"
		    								+ "<td>" + ruleDocumentList.JANGSO + "</td>"
		    								+ "<td>" + ruleDocumentList.AMOUNT + "</td>"
		    								+ "<td>" + ruleDocumentList.SABUN + "</td>"
		    								+ "</tr>";
		    	})
	    	} else {
	    		ruleDocumentListHtml += "<tr><td colspan='7'>공정경쟁규약 문서가 없습니다.</td></tr>"
	    	}
	    	
	    	$("#ruleDocumentSearchList").html(ruleDocumentListHtml);
	  	},
	  	complete: function() {
			$("#ruleDocumentSearchList tr").each(function() {
				$(this).on("click", function() {
					if($(this).attr('use') == 'N'){
					
						var itemRow = $("#approvalItemList").find("tr").length;
						//해당 수정 ROW에 대한 form 지정
						var targetId = "#" + $("#ruleDocumentPopLayer > input[name=targetId]").val();
						
						//거래처 코드 
						$(targetId).attr("ftrCode", $(this).attr("ftrCode"));
						$(targetId + " td input[name=ruleDocumentInfo]").val($(this).attr("ftrCode"));
						
						$("#approvalRqform input[name=ftrCode]").val($(this).attr("ftrCode"));
						
						//다중 ROW 이면 모든 컬럼에 값을 넣어준다
						if(itemRow > 1)	setAllFtrCode($(this).attr("ftrCode"), "ftrCode");
						hideModal("ruleDocumentPopLayer");
					} else if ($(this).attr('use') == 'Y'){
						bootbox.alert("이미 사용된 문서 입니다. 다른 문서를 선택해 주세요");
					}
				})
			})
         },
	    error : function(data) {
	    	bootbox.alert("에러가 발생하였습니다.")
	    }
	})	
}
</script>

<!-- 결재상신 팝업 -->
<div class="layer" id="approvalRqPopLayer" >
	<div class="layerInner layerInner02">
		<strong class="firstTit">결재요청의견</strong>
		<!-- 결재상신 -->
		<div class="long">
			<textarea name="opinion" id="opinion" style="width:99%;height:50px;"></textarea>
			<div class="btmbtns">
				<a href="javascript:saveApprovalRq()" class="btnUp">결재 요청</a>
			</div>
		</div>
		<!-- //결재상신 -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('approvalRqPopLayer');return false;"><img src="/images/btn_close.png"/></button>
</div>
<!-- //결재상신 팝업 -->



<!-- 정산서 팝업 S -->
<div class="layer" id="calculateForm" style="height:611px;">
	<div class="layerInner">
		<strong class="firstTit">정산서</strong>

		<!-- 정산서 정보 S -->
		<div class="short left">
			<table class="defaultType" >
				<colgroup>
					<col width="" />
					<col width="" />
				</colgroup>
				<tbody>
					<tr>
						<th>품의번호</th>
						<td id="approvalIdArea"></td>
					</tr>
					<tr>
						<th>계정구분</th>
						<td id="accountCdArea" style="display: none;"></td>
						<td id="accountNmArea"></td>
					</tr>
					<tr>
						<th>예산부서</th>
						<td id="budgetDeptNmArea"></td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- 정산서 정보 E -->

		<!-- 결재라인 S -->
		<div class="short right">
			<table class="defaultType" id="lineArea">
			</table>
		</div>
		<!-- 결재라인 E -->

		<!-- 카드사용내역 S -->
		<div class="long">
			<p class="secondTit">
				카드사용내역
				<span class="btns">
					<button type="button" id="btn01" class="btnCancel02" style="display: none;">상신취소</button>
					<button type="button" id="btn02" class="btnProductional" style="display: none;">재품의</button>
					<button type="button" id="btn03" class="btnApproval" style="display: none;">승인</button>
					<button type="button" id="btn04" class="btnReturn" style="display: none;">반려</button>
					<button type="button" id="btn05" class="btnCancel02" style="display: none;">승인취소</button>
				</span>
			</p>
			<div class="innerBox">
				<div class="layerScroll">
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
						</colgroup>
						<thead id="titleArea">
							<tr>
								<th>사용일</th>
								<th>승인시간</th>
								<th>상호</th>
								<th>합계금액</th>
								<th>담당자</th>
								<th>세부계정</th>
								<th>예산별도계정명</th>
								<th>제품군/명</th>
								<th>거래처</th>
								<th>사용내역</th>
								<th>결과보고서</th>
								<th>공정경쟁규약</th>
								<th>가맹점주소</th>
								<th>업종</th>
								<th>첨부</th>
								<th>회의록</th>
							</tr>
						</thead>
						<tbody id="itemArea">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- 카드사용내역 E -->
	</div>

	<button type="button" class="btnLayerClose" onclick="javascript:closeCalculateForm(); return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- 정산서 팝업 E -->

<!-- 승인 팝업 S -->
<div class="layer" id="idea">
	<div class="layerInner layerInner02">
		<strong class="firstTit">결재의견</strong>
		<!-- 승인 S -->
		<div class="long">
			<div class="btmSelect">
				<select name="accountSp1Cd" id="accountSp1Cd" style="display: none;">
					<option value="">영업경비 예산별도계정</option>
					<option value="635300000">업무관리비</option>
					<option value="635310000">업무활동비</option>
					<option value="635410000">업무활동비(항생)</option>
					<option value="635290600">OCT활동비</option>
				</select>
				<select name="accountSp2Cd" id="accountSp2Cd" style="display: none;">
					<option value="">기타경비 예산별도계정</option>
					<option value="635290300">약품영업지원비</option>
					<option value="635290400">약품영업소경비</option>
					<option value="-">해당없음</option>
				</select>
			</div>
			<textarea name="aprComment" id="aprComment" style="width: 95%; height: 50px;" maxlength="256"></textarea>
			<div class="btmbtns">
				<button type="button" id="btn06" class="btnApproval">승인</button>
			</div>
		</div>
		<!-- 승인 E -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('idea'); return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- 승인 팝업 E -->

<!-- 반려 팝업 S -->
<div class="layer" id="returnReason">
	<div class="layerInner layerInner02">
		<strong class="firstTit">반려사유</strong>
		<!-- 반려 S -->
		<div class="long">
			<div class="btmSelect">
				<select name="selectResComment" id="selectResComment">
					<option value="">반려사유 선택</option>
					<option value="A">계정구분 수정바랍니다.</option>
					<option value="B">세부계정 수정바랍니다.</option>
					<option value="C">결재부서 수정바랍니다.</option>
					<option value="D">첨부문서 재작성바랍니다.</option>
					<option value="E">기타</option>
				</select>
			</div>
			<textarea name="resComment" id="resComment" style="width: 95%; height: 50px;" maxlength="256"></textarea>
			<div class="btmbtns">
				<button type="button" id="btn07" class="btnReturn">반려</button>
			</div>
		</div>
		<!-- 반려 E -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('returnReason'); return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- 반려 팝업 E -->

<script>
function getRuleDocumentData (val) {
	$.ajax({
		type : 'GET',
		url : '/rest/ruleDocumentApi/ruleDocument/' + val,
		dataType : "json",
		success : function(data) {
			if (data.code == 'S') {
				$("#ruleDocumentSearchList").html(data.ruleDocumentVo);
			} else {
				bootbox.alert(data.msg, function () {
					hideModal('ruleDocumentPopLayer');
				});
			}
		},
		error : function(e) {
			console.log(e);
		}
	});
}

function getAttachFileData (val) {
	var arr = val.split('|');
	var approvalId = arr[0];
	var seq = arr[1];

	$.ajax({
		type : 'GET',
		url : '/rest/approvalApi/attachFile/' + approvalId + '/' + seq,
		dataType : "json",
		success : function(data) {
			if (data.code == 'S') {
				$("#attachFileArea").html(data.attachFilelist);
			} else {
				bootbox.alert(data.msg);
			}
		},
		error : function(e) {
			console.log(e);
		}
	});
}

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
	} else if (flag == 'meetReport') {
		showModal('meetReportViewPopLayer');
		var arr = val.split('『');
		for (var i = 0; i < arr.length; i++) {
			var tmp = arr[i].split('=');
			$("#meetReportView td[name='" + tmp[0] + "']").html(tmp[1]);
		}
	}
});

// 상신취소
$("#btn01").click(function() {
	var approvalId = $(this).val();

	bootbox.confirm("상신 취소 하시겠습니까?", function(result) {
		if(result) {
			$.ajax({
				type : 'PUT',
				url : '/rest/approvalApi/cancelRequest/' + approvalId,
				dataType : "json",
				beforeSend : function() {
					$('.loading').css('display', 'block');
				},
				complete : function() {
					$('.loading').css('display', 'none');
				},
				success : function(data) {
					if (data.code == 'S') {
						bootbox.alert('상신 취소 되었습니다.', function () {
							closeCalculateForm();
							getList(null);
						});
					} else {
						bootbox.alert(data.msg);
					}
				},
				error : function(e) {
					console.log(e);
				}
			});
		}
	});
});

// 재품의
$("#btn02").click(function() {
	var approvalId = $(this).val();

	bootbox.confirm("재품의 하시겠습니까?", function(result) {
		if (result) {
			$.ajax({
				type : 'POST',
				url : '/rest/approvalApi/reApproval/' + approvalId,
				dataType : "json",
				beforeSend : function() {
					$('.loading').css('display', 'block');
				},
				complete : function() {
					$('.loading').css('display', 'none');
				},
				success : function(data) {
					if (data.code == 'S') {
						location.href = '/approvalRq/approvalRqItem/' + data.newApprovalId + '/' + data.oldApprovalId;
					} else {
						bootbox.alert(data.msg);
					}
				},
				error : function(e) {
					console.log(e);
				}
			});
		}
	});
});

// 승인 팝업
$("#btn03").click(function() {
	$("#aprComment").val('');
	$("#accountSp1Cd").val('');
	$("#accountSp2Cd").val('');
	showModal('idea');

});

// 반려 팝업
$("#btn04").click(function() {
	$("#resComment").val('');
	$("#selectResComment").val('');
	showModal('returnReason');
});

// 승인취소
$("#btn05").click(function() {
	var approvalId = $(this).val();

	bootbox.confirm("승인 취소 하시겠습니까?", function(result) {
		if (result) {
			$.ajax({
				type : 'PUT',
				url : '/rest/approvalApi/cancelApproval/' + approvalId,
				dataType : "json",
				beforeSend : function() {
					$('.loading').css('display', 'block');
				},
				complete : function() {
					$('.loading').css('display', 'none');
				},
				success : function(data) {
					if (data.code == 'S') {
						bootbox.alert('승인 취소 되었습니다.',function () {
							//location.reload();
							closeCalculateForm();
							getList(null, true);
						});
					} else {
						bootbox.alert(data.msg);
					}
				},
				error : function(e) {
					console.log(e);
				}
			});
		}
	});
});

// 승인
$("#btn06").click(function() {
	var arr = $(this).val().split("/");
	if (arr.length != 3) {
		bootbox.alert('오류가 발생했습니다.');
		return false;
	}

	var approvalId = arr[0];
	var seq = arr[1];
	var accountCd = arr[2];

	var aprComment = 'N';
	if ($.trim($("#aprComment").val()) != '') {
		aprComment = encodeURIComponent($.trim($("#aprComment").val()));
	}

	var accountSpCd = 'N';
	if (accountCd == '3') {
		if ($("#accountSp1Cd").val() == '') {
			bootbox.alert('예산별도계정을 선택해주세요.');
			return false;
		} else {
			accountSpCd = $("#accountSp1Cd").val();
		}
	} else if (accountCd == '4') {
		if ($("#accountSp2Cd").val() == '') {
			bootbox.alert('예산별도계정을 선택해주세요.');
			return false;
		} else {
			accountSpCd = $("#accountSp2Cd").val();
		}
	}
	
	var account2Str = 'N';
	if (seq == 1) {
		$("#itemArea tr").each(function(idx) {
			if (idx == 0) {
				account2Str = '';
			}

			var selector = $(this).find("td:nth-child(6)");
			var code = selector.attr('code');

			var itemSeq = $(this).find("td:last input").val();

			if (code != '' && itemSeq != '') {
				account2Str += itemSeq;
				account2Str += '`';
				account2Str += code;
				account2Str += '|';
			} else {
				bootbox.alert('세부계정을 선택해주세요.');
				account2Str = '';
				return false;
			}
		});
		if (account2Str == '') {
			return false;
		}
	}

	bootbox.confirm("승인 하시겠습니까?", function(result) {
		if (result) {
			$.ajax({
				type : 'PUT',
				url : '/rest/approvalApi/approval/' + approvalId + '/' + seq + '/' + aprComment + '/' + accountSpCd + '/' + account2Str,
				dataType : "json",
				beforeSend : function() {
					$('.loading').css('display', 'block');
				},
				complete : function() {
					$('.loading').css('display', 'none');
				},
				success : function(data) {
					if (data.code == 'S') {
						bootbox.alert('승인 되었습니다.',function () {
							//location.reload();
							closeCalculateForm();
							hideModal('idea');
							getList(null, true);
						});
					} else {
						bootbox.alert(data.msg);
					}
				},
				error : function(e) {
					console.log(e);
				}
			});
		}
	});
});

// 반려
$("#btn07").click(function() {
	var arr = $(this).val().split("/");
	if (arr.length != 2) {
		bootbox.alert('오류가 발생했습니다.');
		return false;
	}

	var approvalId = arr[0];
	var seq = arr[1];

	var aprComment = '';
	if ($.trim($("#resComment").val()) == '') {
		bootbox.alert('반려사유를 입력하거나 선택해주세요.');
		return false;
	} else {
		aprComment = encodeURIComponent($.trim($("#resComment").val()));
	}

	bootbox.confirm("반려 하시겠습니까?", function(result) {
		if (result) {
			$.ajax({
				type : 'PUT',
				url : '/rest/approvalApi/restore/' + approvalId + '/' + seq + '/' + aprComment,
				dataType : "json",
				beforeSend : function() {
					$('.loading').css('display', 'block');
				},
				complete : function() {
					$('.loading').css('display', 'none');
				},
				success : function(data) {
					if (data.code == 'S') {
						bootbox.alert('반려 되었습니다.', function () {
							//location.reload();
							closeCalculateForm();
							hideModal('returnReason');
							getList(null, true);
						});
					} else {
						bootbox.alert(data.msg);
					}
				},
				error : function(e) {
					console.log(e);
				}
			});
		}
	});
});

// 반려 사유 셀렉트 박스 이벤트
$("#selectResComment").change(function() {
	var str = '';
	var flag = $(this).val();
	if (flag == 'A') {
		str = '계정구분 수정바랍니다.';
	} else if (flag == 'B') {
		str = '세부계정 수정바랍니다.';
	} else if (flag == 'C') {
		str = '결재부서 수정바랍니다.';
	} else if (flag == 'D') {
		str = '첨부문서 재작성바랍니다.';
	} else if (flag == 'E') {
		str = '';
	}
	$("#resComment").val(str);
});
</script>

<!-- 공정경쟁규약 팝업  -->
<div class="layer" id="ruleDocumentPopLayer">
	<input type="hidden" name="targetId" value="" />
	<div class="layerInner layerInner02">
		<strong class="firstTit">공정경쟁규약문서</strong>
		<!-- 공정경쟁규약문서 -->
		<div class="long">
			<div class="topSrch" style="display: none;">
				<span class="name">사번</span> <input type="text" name="ruleDocumentSabun" id="ruleDocumentSabun" style="width: 200px !important">
				<button type="button" class="btnSrch" id="btnRuleDocument" onClick="getRuleDocument()">검색</button>
			</div>
			<div class="scrollTable-y" id="scrollTable-y" style="height:300px;overflow:auto">
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
					</colgroup>
					<thead>
						<tr>
							<th>문서번호</th>
							<th>날짜</th>
							<th>거래처</th>
							<th>제품(품목)</th>
							<th>장소</th>
							<th>금액</th>
							<th>사번(담당자)</th>
						</tr>
					</thead>
					<tbody id="ruleDocumentSearchList">
						<tr>
							<td colspan="7"></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- //공정경쟁규약문서 -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('ruleDocumentPopLayer');return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- 공정경쟁규약 팝업  -->

<!-- 세미나 결과보고서 view 팝업 S -->
<div class="layer" id="seminarReportPopLayer">
	<div class="layerInner layerInner02">
		<strong class="firstTit">세미나결과보고서</strong>
		<div class="long">
			<table class="horizontalTable">
				<colgroup>
					<col width="200" />
					<col width="*" />
				</colgroup>
				<tbody id="seminarReportView">
					<tr>
						<th>작성자</th>
						<td name="rqName"></td>
					</tr>
					<tr>
						<th>행사명</th>
						<td name="eventName"></td>
					</tr>
					<tr>
						<th>날짜</th>
						<td name="date"></td>
					</tr>
					<tr>
						<th>장소</th>
						<td name="place"></td>
					</tr>
					<tr>
						<th>참석인원</th>
						<td name="numberOfpeople"></td>
					</tr>
					<tr>
						<th>집행금액</th>
						<td name="amount"></td>
					</tr>
					<tr>
						<th>주요내용</th>
						<td name="content"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('seminarReportPopLayer'); return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- 세미나 결과보고서 view 팝업 E -->

<!-- 첨부파일 view 팝업 S -->
<div class="layer" id="attachViewPopLayer">
	<div class="layerInner layerInner02">
		<strong class="firstTit">첨부파일</strong>
		<div class="long">
			<table class="horizontalTable">
				<colgroup>
					<col width="100" />
					<col width="*" />
				</colgroup>
				<tbody id="attachFileArea">
				</tbody>
			</table>
		</div>
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('attachViewPopLayer'); return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- 첨부파일 view 팝업 E -->

<script>
	$("#attachFileArea").on('click', 'a', function(e) {
		e.preventDefault();
		var downloadUrl = $(this).attr('value');
		var checkUrl = downloadUrl.replace('downloadFile', 'checkFile');

		downloadFile(checkUrl ,downloadUrl);
	});

	function downloadFile(checkUrl, downloadUrl) {
		$.ajax({
			type: 'GET',
			url: checkUrl,
			dataType: "json",
			success: function(data) {
				if (data.code == 'S') {
					location.href = downloadUrl;
				} else {
					bootbox.alert(data.msg);
				}
			},
			error: function(e) {
				console.log(e);
			}
		});
	}
</script>

<!-- 회의록 view 팝업 S -->
<div class="layer" id="meetReportViewPopLayer">
	<div class="layerInner layerInner02">
		<strong class="firstTit">회의록</strong>
		<div class="long">
			<table class="horizontalTable">
				<colgroup>
					<col width="200" />
					<col width="*" />
				</colgroup>
				<tbody id="meetReportView">
					<tr>
						<th>부서명</th>
						<td name="deptName"></td>
					</tr>
					<tr>
						<th>작성자</th>
						<td name="writer"></td>
					</tr>
					<tr>
						<th>회의명</th>
						<td name="meetName"></td>
					</tr>
					<tr>
						<th>날짜</th>
						<td name="date"></td>
					</tr>
					<tr>
						<th>시간</th>
						<td name="time"></td>
					</tr>
					<tr>
						<th>총 인원수</th>
						<td name="numberOfpeople"></td>
					</tr>
					<tr>
						<th>외부 참석자 명단</th>
						<td name="externalList"></td>
					</tr>
					<tr>
						<th>보령 참석자 명단</th>
						<td name="internalList"></td>
					</tr>
					<tr>
						<th>회의내용</th>
						<td name="content"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('meetReportViewPopLayer'); return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- 회의록 view 팝업 E -->

<!-- 세부계정 조회 팝업 S -->
<div class="layer" id="account2Poplayer">
	<input type="hidden" name="targetId" value="" />
	<input type="hidden" name="flag" value="" />
	<div class="layerInner layerInner02">
		<strong class="firstTit">세부계정 선택</strong>
		<div class="long">
			<div class="topSrch">
				<span class="name">세부계정명</span> <input type="text" name="account2" id="account2" style="width: 200px !important">
				<button type="button" class="btnSrch" id="btnAccount2Search">검색</button>
			</div>
			<div class="scrollTable-y" id="scrollTable-y" style="height:300px;overflow:auto">
				<div class="inner">
					<table class="defaultType">
						<colgroup>
							<col width="" />
							<col width="" />
						</colgroup>
						<thead>
							<tr>
								<th>세부계정코드</th>
								<th>세부계정명</th>
							</tr>
						</thead>
						<tbody id="account2ListArea">
							<tr>
								<td colspan="2" style="text-align: center;">세부계정명으로 검색 하여 주십시요.</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('account2Poplayer'); return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- 세부계정 조회 팝업 E -->

<script>
$("#account2").autocomplete({source: function(request, response) {
	$.ajax({
		url: "/rest/codeApi/codeNameSearch/ACCOUNT2_CD/" + encodeURIComponent($("#account2").val()),
		type: "GET",
		dataType: "json",
		success: function(data) {
			var result = data;
			response(result);
		},
		error: function(data) {
			console.log(e);
		}
	});
}});

$("#btnAccount2Search").click(function() {
	var account2 = $("#account2").val();
	var targetId = $("#account2Poplayer > input[name='targetId']").val();
	var flag = $("#account2Poplayer > input[name='flag']").val();

	if (account2 == '') {
		bootbox.alert("검색어를 입력해 주세요");
		return false;
	} else {
		account2Search(account2, targetId, flag);
	}
});

function account2Search(account2, targetId, flag) {
	$.ajax({
		type: 'GET',
		url: '/rest/codeApi/codeSearchList/ACCOUNT2_CD/' + encodeURIComponent(account2),
		dataType: "json",
		success: function(data) {
			makeHtml_account2List(data);
		},
		complete: function() {
			addClickEventListener(targetId, targetId, flag);
		},
		error: function(e) {
			console.log(e);
		}
	});
}

function openAccount2PopLayer(accountCd, targetId, flag) {
	if (flag == 'calculate') {
		$("#account2").val('');
	}
	var list = getAccountFav(accountCd);
	makeHtml_account2List(list);
	addClickEventListener(accountCd, targetId, flag);
	showModal('account2Poplayer', targetId, flag);
}

function getAccountFav(val) {
	var result = null;

	$.ajax({
		type : 'GET',
		url: '/rest/codeApi/accountFav/' + val,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.code == 'S') {
				result = data.list;
			}
		},
		error : function(e) {
			console.log(e);
		}
	});

	return result;
}

function makeHtml_account2List(list) {
	var html = '';

	if (list != null && list.length > 0) {
		for (var i = 0; i < list.length; i++) {
			html += "<tr style='cursor:pointer' code='" + list[i].code + "' codeName='" + list[i].codeName + "'>";
			html += "<td>" + list[i].code + "</td>";
			html += "<td>" + list[i].codeName + "</td>";
			html += "</tr>";
		}
	} else {
			html += "<tr>";
			html += "<td colspan='2' style='text-align: center;'>일치하는 검색 결과가 없습니다.</td>"
			html += "</tr>";
	}

	$("#account2ListArea").html(html);
}

function addClickEventListener (accountCd, targetId, flag) {
	$("#account2ListArea tr").on('click', function() {
		var code = $(this).attr("code");
		var codeName = $(this).attr("codeName");

		if (flag == 'calculate') {
			$("#itemArea tr").each(function(idx) {
				var selector = $(this).find("td:nth-child(6)");

				var tmp = selector.html();
				var arr = tmp.split("&nbsp;");
				selector.html(codeName + "&nbsp;" + arr[1]);

				selector.attr('code', code);
				selector.attr('codeName', codeName);
			});
		} else if (flag == 'rqItem') {
			var targetEl = "#" + targetId;
			
			$(targetEl).attr("account2Code", code);
			$(targetEl).attr("account2Name", codeName);
			
			if(code=='635130100'){				
				$('#approvalRqDataTable').find('[name="meetDocument"]').show();
			}else{
				$('#approvalRqDataTable').find('[name="meetDocument"]').hide();
			}
			
			$(targetEl + " td input[name=account2Info]").val($(this).attr("codeName"));
			setAllFtrCode(code + "|" + codeName, "account2Info");
		}
		hideModal("account2Poplayer");
	});
}

</script>