<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageTag" uri="PageTag"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>

function getCodeList(grpCodeId, pageNo, search){

	if(grpCodeId == "" || grpCodeId == null) {
		grpCodeId = "all";
	}
	
	if(pageNo == null) pageNo = 1;
	
	jQuery.ajax({
		type : 'GET',
		url : '/rest/codeApi/commCodeList/' + grpCodeId + '/' + pageNo,
		dataType : "json",
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			var commCodeListHtml = "";
			
			$.each(jsonData.codeList, function(key, commCodeList) {
				commCodeListHtml += '<tr onclick="getCode(this);" style="cursor:pointer;">'
						//+ '<td>' + checkNull(commCodeList.grpCodeId) + '</td>'
						+ '<td>' + checkNull(commCodeList.code) + '</td>'
						+ '<td style="text-align:left; padding-left:10px;">' + checkNull(commCodeList.codeName) + '</td>'
						+ '<td style="text-align:left; padding-left:10px;">' + checkNull(commCodeList.description) + '</td>'
						+ '<td>' + checkNull(commCodeList.useYn) + '</td>'
						+ '<td>' + dateFormat(checkNull(commCodeList.regDate)) + '</td>'
						+ '</tr>';
				}
			);
			
			$('#commCodeList').html(commCodeListHtml);
			$('#totalRow').html(jsonData.totalRow + "건");
			
			if(search){
				$("#pagenationSection").pagination({
			        items: jsonData.totalRow,
			        itemsOnPage: 10,
			        cssStyle: 'light-theme',
			       	hrefTextPrefix: "javascript:getCodeList('" + grpCodeId + "',",
					hrefTextSuffix: ");"
			    });
			}
		},
		error : function(err) {
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});
}

function getCode(tr){
	var grpCodeId = $('#grpCodeId').text();
	var code = $(tr).find('td').eq(0).text();
	
	jQuery.ajax({
		type : 'GET',
		url : '/rest/codeApi/getCommCode/'+grpCodeId + '/' + code,
		dataType : "json",
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			clearPopup();
			$('#codeId').val(jsonData.code);
			$('#codeName').val(jsonData.codeName);
			$('#description').val(jsonData.description);
			$('input[name="codeUseYn"]:input[value="'+jsonData.useYn+'"]').attr("checked", true);			
			openPopup('modify');
		},
		error : function(err) {
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});
}

function openPopup(flag){
	if(flag=='insert'){
		clearPopup();
		$('#btnCodeMod').hide();
		$('#btnCodeDel').hide();
	}else if(flag=='modify'){
		$('#codeId').attr("disabled", true); 
		$('#btnCodeReg').hide();
	}
	showModal('codePopup');
}

function clearPopup(){
	$('#codeId').attr("disabled", false); 
	$('#codeId').val('');
	$('#codeName').val('');
	$('#description').val('');
	$('#codeUseY').attr('checked',true);
	$('#btnCodeMod').show();
	$('#btnCodeReg').show();
	$('#btnCodeDel').show();
}

function checkValidation(){
	var validation = ['codeId','codeName'];
	var result = true;
	$(validation).each(function(idx, id){
		if($('#'+id).val()==''){
			result = false;
		}			
	});
	if(!result){
		bootbox.alert('필수입력값이 비었습니다.', function(){
			result = false;		
		});
	}
	return result;	
}

function checkGrpValidation(){
	var validation = ['grpCodeName', 'grpCodeType'];
	var result = true;
	$(validation).each(function(idx, id){
		if($('#'+id).val()==''){
			result = false;
		}			
	});
	if(!result){
		bootbox.alert('필수입력값이 비었습니다.', function(){
			result = false;		
		});
	}
	return result;	
}


$(function(){	
	getCodeList('${commGrpCode.grpCodeId}',null,true);
	
	$('#grpCodeId').text('${commGrpCode.grpCodeId}');
	$('#grpCodeName').val('${commGrpCode.grpCodeName}');
	$('input[name="grpCodeUseYn"]:input[value="${commGrpCode.useYn}"]').attr("checked", true);
	$('#grpRegDate').text(dateFormat('${commGrpCode.regDate}'));
	$('#grpCodeType').val('${commGrpCode.codeType}');
	$('#grpQueryId').val('${commGrpCode.queryId}');
	$('#grpDescription').val('${commGrpCode.description}');
	

	$('#btnGrpCodeMod').click(function(){
		bootbox.confirm('수정 하시겠습니까?', function(result){
			if(result){
				if(!checkGrpValidation()){
					return;
				}
				var grpCodeId = $('#grpCodeId').text();
				var grpCodeName = $('#grpCodeName').val();
				var codeType = $('#grpCodeType').val();
				var queryId = $('#grpQueryId').val();
				var description = $('#grpDescription').val();
				var useYn = $('input[name="grpCodeUseYn"]:checked').val();		
				
				var params = {	
					   "grpCodeId" 		:	grpCodeId
					 , "grpCodeName"	:	grpCodeName
					 , "codeType"		:	codeType
					 , "queryId"		:	queryId
					 , "description"	:	description
					 , "useYn"			:	useYn
				};
				
				$.ajax({
					type : 'POST',
					url : '/rest/codeApi/updateCommGrpCode' ,
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
						if(jsonData.msg=='S'){
							bootbox.alert("수정 하였습니다.");							
						}else{
							bootbox.alert("수정 실패하였습니다..");
						}
					},
					error : function(err) {
						bootbox.alert("처리중 장애가 발생하였습니다." + err);
					}
				});
			}
		});	
	});
	
	$('#btnGrpCodeDel').click(function(){
		bootbox.confirm('삭제 하시겠습니까?', function(result){
			if(result){
				var grpCodeId = $('#grpCodeId').text();				
				if(grpCodeId==null || grpCodeId==''){
					bootbox.alert('필수입력값이 비었습니다.', function(){
						return;		
					});
				}
				
				var params = {	
					   "grpCodeId" 		:	grpCodeId
				};
				
				$.ajax({
					type : 'POST',
					url : '/rest/codeApi/deleteCommGrpCode' ,
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
						if(jsonData.msg=='S'){
							bootbox.alert("삭제 하였습니다.",function(){
								$(location).attr('href','/admin/codeManage/commGrpCodeList');	
							});							
						}else{
							bootbox.alert("삭제 실패하였습니다..");
						}
					},
					error : function(err) {
						bootbox.alert("처리중 장애가 발생하였습니다." + err);
					}
				});
			}
		});	
	});
		
	$('#btnCodeReg').click(function(){
		bootbox.confirm('저장 하시겠습니까?', function(result){
			if(result){
				if(!checkValidation()){
					return;
				}
				var grpCodeId = $('#grpCodeId').text();
				var code = $('#codeId').val();
				var codeName = $('#codeName').val();
				var description = $('#description').val();
				var useYn = $('input[name="codeUseYn"]:checked').val();		
				
				var params = {	
					   "grpCodeId" 		:	grpCodeId
					 , "code"			:	code
					 , "codeName"		:	codeName
					 , "description"	:	description
					 , "useYn"			:	useYn
				};
				
				$.ajax({
					type : 'POST',
					url : '/rest/codeApi/insertCommCode' ,
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
						if(jsonData.msg=='S'){
							getCodeList(grpCodeId,null,true);
							bootbox.alert("저장 하였습니다.");
							hideModal('codePopup');
						}else{
							bootbox.alert("저장 실패하였습니다..");
						}
						hideModal('codePopup');
					},
					error : function(err) {
						bootbox.alert("처리중 장애가 발생하였습니다." + err);
					}
				});
			}
		});	
	});
	
	$('#btnCodeMod').click(function(){
		bootbox.confirm('수정 하시겠습니까?', function(result){
			if(result){
				if(!checkValidation()){
					return;
				}
				var grpCodeId = $('#grpCodeId').text();
				var code = $('#codeId').val();
				var codeName = $('#codeName').val();
				var description = $('#description').val();
				var useYn = $('input[name="codeUseYn"]:checked').val();		
				
				var params = {	
					   "grpCodeId" 		:	grpCodeId
					 , "code"			:	code
					 , "codeName"		:	codeName
					 , "description"	:	description
					 , "useYn"			:	useYn
				};
				
				$.ajax({
					type : 'POST',
					url : '/rest/codeApi/updateCommCode' ,
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
						if(jsonData.msg=='S'){
							getCodeList(grpCodeId,null,true);
							bootbox.alert("수정 하였습니다.");
							hideModal('codePopup');
						}else{
							bootbox.alert("수정 실패하였습니다..");
						}
					},
					error : function(err) {
						bootbox.alert("처리중 장애가 발생하였습니다." + err);
					}
				});
			}
		});	
	});

	$('#btnCodeDel').click(function(){
		bootbox.confirm('삭제 하시겠습니까?', function(result){
			if(result){
				if(!checkValidation()){
					return;
				}
				var grpCodeId = $('#grpCodeId').text();
				var code = $('#codeId').val();

				
				var params = {	
					   "grpCodeId" 		:	grpCodeId
					 , "code"			:	code
				};
				
				$.ajax({
					type : 'POST',
					url : '/rest/codeApi/deleteCommCode' ,
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
						if(jsonData.msg=='S'){
							getCodeList(grpCodeId,null,true);
							bootbox.alert("삭제 하였습니다.");
							hideModal('codePopup');
						}else{
							bootbox.alert("삭제 실패하였습니다..");
						}
					},
					error : function(err) {
						bootbox.alert("처리중 장애가 발생하였습니다." + err);
					}
				});
			}
		});	
	});

	
})

	
	
</script>

<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<div class="content">

	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2>
			<span>공통코드그룹상세</span>
		</h2>
		<p class="location">Home > 코드관리 > 공통코드그룹관리 > 공통코드그룹상세</p>
	</div>
	
	<!-- 검색내역 -->
	<div class="srchList" style="height:initial;">
		<strong class="tit">공통코드그룹상세</strong>
		<div class="innerBox">
			<div class="btns">			
				<button class="btnReg" onclick="$(location).attr('href','/admin/codeManage/commGrpCodeList');">목록</button>
				<button id="btnGrpCodeMod" class="btnReg">수정</button>
				<button id="btnGrpCodeDel" class="btnReg">삭제</button>
			</div>
			<table class="horizontalTable">
					<colgroup>
						<col width=""/>
						<col width=""/>
						<col width=""/>
					</colgroup>
					<tbody>
						<tr>
							<th>공통코드그룹ID*</th>							
							<td id="grpCodeId"></td>
							<th>공통코드그룹명*</th>							
							<td><input id="grpCodeName" type="text" style="width:96%" value="" maxlength="16"/></td>
						</tr>						
						<tr>
							<th>코드유형*</th>							
							<td><input id="grpCodeType" type="text" style="width:95%;" value="" maxlength="1"/></td>
							<th>쿼리 ID</th>							
							<td><input id="grpQueryId" type="text" style="width:95%;" value="" maxlength="128"/></td>
						</tr>
						<tr>
							<th>사용여부*</th>							
							<td>
								<input id="grpCodeUseY" name="grpCodeUseYn" type="radio" value="Y"/><label for="grpCodeUseY">사용</label>
								<input id="grpCodeUseN" name="grpCodeUseYn" type="radio" value="N"/><label for="grpCodeUseN">사용안함</label>
							</td>
							<th>최초등록일</th>							
							<td id="grpRegDate"></td>
						</tr>
						<tr>
							<th>설 명</th>							
							<td colspan="3"><textarea id="grpDescription" style="height: 50px; width:98%;" maxlength="64"></textarea></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	
	<!-- 검색내역 -->
	<div class="srchList">
		<strong class="tit">공통코드 목록 총<em id="totalRow">${totalRow}</em></strong>
		<div class="innerBox">
			<div class="btns">
				<a href="" class="btnReg" onclick="clearPopup();openPopup('insert');return false;">신규등록</a>
			</div>
			<table class="defaultType">
				<colgroup>
					<col width="15%" />
					<col width="25%" />
					<col width="" />
					<col width="8%" />
					<col width="10%" />
				</colgroup>
				<thead>
					<tr>
						<!-- <th>공통코드그룹ID</th> -->
						<th>공통코드</th>
						<th>공통코드명</th>
						<th>설명</th>
						<th>사용여부</th>
						<th>최초등록일</th>
					</tr>
				</thead>
				<tbody id="commCodeList" >
				
<%-- 					<c:forEach var="codeList" items="${codeList}">
					<tr>
						<td>${codeList.grpCodeId}</td>
						<td>${codeList.code}</td>
						<td>${codeList.codeName}</td>
						<td>${codeList.description}</td>
						<td>${codeList.useYn}</td>
						<td>${codeList.regDate}</td>
					</tr>	
					</c:forEach> --%>
				</tbody>
			</table>
		</div>
	</div>
	<!-- //검색내역 -->
	<p id="pagenationSection"></p>
</div>


<!-- 신규등록 팝업 -->
<div class="layer" id="codePopup">
	<div class="layerInner layerInner02">
		<strong class="firstTit">공통코드 관리</strong>
		<!-- 공통코드 등록 -->
		<div class="long">
			<table class="horizontalTable">
				<colgroup>
					<col width=""/>
					<col width=""/>
				</colgroup>
				<tbody>
					<tr>
						<th>공통코드*</th>
						<td><input id="codeId" type="text" style="width:96%" maxlength="16"/></td>
					</tr>
					<tr>
						<th>공통코드명*</th>
						<td><input id="codeName" type="text" style="width:96%"  maxlength="32"/></td>
					</tr>
					<tr>
						<th>사용여부*</th>
						<td>
							<input id="codeUseY" name="codeUseYn" type="radio" value="Y"/><label for="grpCodeUseY">사용</label>
							<input id="codeUseN" name="codeUseYn" type="radio" value="N"/><label for="grpCodeUseN">사용안함</label>
						</td>
					</tr>					
					<tr>
						<th>설명</th>
						<td><textarea id="description" style="height: 40px; width:96%;" maxlength="64"></textarea></td>
					</tr>
				</tbody>
			</table>
			<div class="btns">
				<button id="btnCodeReg" type="button" class="btnSave">저장</button>
				<button id="btnCodeMod" type="button" class="btnSave">수정</button>
				<button id="btnCodeDel" type="button" class="btnSave">삭제</button>
			</div>
		</div>
		<!-- //공통코드 등록 -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('codePopup');return false;"><img src="/images/btn_close.png"/></button>
</div>
<!-- //신규등록 팝업 -->



