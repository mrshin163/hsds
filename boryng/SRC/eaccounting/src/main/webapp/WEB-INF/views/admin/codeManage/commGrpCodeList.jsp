<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageTag" uri="PageTag"%>

<script>

function getCodeList(grpCodeName, pageNo, search){
	if(grpCodeName == "") grpCodeName = "all";
	if(pageNo == null) pageNo = 1;
	
	jQuery.ajax({
		type : 'GET',
		url : '/rest/codeApi/commGrpCodeList/' + grpCodeName + '/' + pageNo,
		dataType : "json",
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			var commGrpCodeListHtml = "";
			
			$.each(jsonData.codeList, function(key, commGrpCodeList) {
				//commGrpCodeListHtml += '<tr onclick="getCodeDetail(this);" style="cursor:pointer;">'
				commGrpCodeListHtml += '<tr onclick="$(location).attr(\'href\',\'/admin/codeManage/commCodeList/'+ commGrpCodeList.grpCodeId +'\');" style="cursor:pointer;">'
						+ '<td>' + checkNull(commGrpCodeList.grpCodeId) + '</td>'
						+ '<td style="text-align:left; padding-left:10px;">' + checkNull(commGrpCodeList.grpCodeName)  + '</td>'
						+ '<td style="text-align:left; padding-left:10px;">' + checkNull(commGrpCodeList.description) + ' </td>'
						+ '<td>' + checkNull(commGrpCodeList.useYn) + '</td>'
						+ '<td>' + dateFormat(checkNull(commGrpCodeList.regDate)) + '</td>'
						+ '</tr>';
				}
			
			);
			
			$('#commGrpCodeList').html(commGrpCodeListHtml);
			$('#totalRow').html(jsonData.totalRow);
			
			if(search){
				$("#pagenationSection").pagination({
			        items: jsonData.totalRow,
			        itemsOnPage: 10,
			        cssStyle: 'light-theme',
			       	hrefTextPrefix: "javascript:getCodeList('" + grpCodeName + "',",
					hrefTextSuffix: ");"
			    });
			}
		},
		error : function(err) {
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});
}

function openPopup(flag){
	if(flag=='modify'){
		$('#btnGrpCodeReg').hide();
	}else if(flag == 'insert'){
		clearPopup();
		$('#btnGrpCodeMod').hide();
		$('#btnGrpCodeDel').hide();
	}
	showModal('grpCodePopup');
} 

function clearPopup(){
	$('#grpCodeId').val('');
	$('#grpCodeName').val('');
	$('#grpCodeType').val('S');
	$('#grpCodeQueryId').val('');
	$('#grpCodeDescription').val('');
	$('#grpCodeUseY').attr('checked',true);
	$('#btnGrpCodeMod').show();
	$('#btnGrpCodeReg').show();
	$('#btnGrpCodeDel').show();
}

/* function getCodeDetail(tr){
	var grpCodeId = $(tr).find('td').eq(0).text();
	
	$.ajax({
		type : 'GET',
		url : '/rest/codeApi/getCommGrpCode/' + grpCodeId ,
		dataType : "json",
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			clearPopup();
			
			$('#grpCodeId').val(checkNull(jsonData.grpCodeId));
			$('#grpCodeName').val(checkNull(jsonData.grpCodeName));
			$('#grpCodeType').val(checkNull(jsonData.codeType));
			$('#grpCodeQueryId').val(checkNull(jsonData.queryId));
			$('#grpCodeDescription').val(checkNull(jsonData.description));
			$('input[name="grpCodeUseYn"]:input[value="'+checkNull(jsonData.useYn)+'"]').attr("checked", true);			
			
			$('#btnGrpCodeMod').show();
			$('#btnGrpCodeReg').hide();
			openPopup('modify');
		},
		error : function(err) {
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});
} */

function checkValidation(){
	var validation = ['grpCodeId','grpCodeName', 'grpCodeType'];
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
	getCodeList('', null, true);
	
	//var grpCodeName = $("#srchGrpCodeName").val();
	
	$(".btnSrch").click(function(){		
		var grpCodeName = $("#srchGrpCodeName").val();
		getCodeList(grpCodeName,null,true);
	});
	
	$('#btnGrpCodeReg').click(function(){
		bootbox.confirm('저장 하시겠습니까?', function(result){
			if(result){
				if(!checkValidation()){
					return;
				}
				var grpCodeId = $('#grpCodeId').val();
				var grpCodeName = $('#grpCodeName').val();
				var codeType = $('#grpCodeType').val();
				var queryId = $('#grpCodeQueryId').val();
				var description = $('#grpCodeDescription').val();
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
					url : '/rest/codeApi/insertCommGrpCode' ,
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
							bootbox.alert("저장 하였습니다.");
							hideModal('grpCodePopup');
						}else{
							bootbox.alert("저장 실패하였습니다..");
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
		<h2><i class="icon iconCodeManage02"></i> <span>공통코드그룹관리</span></h2>
		<p class="location">Home > 코드관리 > 공통코드그룹관리</p>
	</div>
	<!-- 검색조건 -->
	<div class="srchCondition">
		<strong class="tit">검색 조건</strong>
		
		<form id="searchForm" name="searchForm">
		<div class="innerBox">
			<div class="left">
				<ul>
					<li><span class="inputTit">그룹명</span> <input type="text" id="srchGrpCodeName" name="grpCodeName" ></li>
				</ul>
			</div>
			<div class="right">
				<button type="button" class="btnSrch">검색</button>
			</div>
		</div>
		</form>
	</div>
	<!-- //검색조건 -->

	<!-- 검색내역 -->
	<div class="srchList">
		<div id="loader" style="position: absolute; display: none; top:45%; left:50%;"><img src="/images/admin/ajax-loader.gif"></div>
		<strong class="tit">코드그룹 목록  총<em id="totalRow">${totalRow}</em>건</strong>
		<div class="innerBox" id="dataBox">
			<div class="btns">
				<a href="" class="btnReg" onclick="openPopup('insert'); return false;">신규등록</a>
			</div>
			<table class="defaultType">
				<colgroup>
					<col width="20%" />
					<col width="20%" />
					<col width="" />
					<col width="10%" />
					<col width="15%" />
				</colgroup>
				<thead>
					<tr>
						<th>공통코드그룹ID</th>
						<th>공통코드그룹명</th>
						<th>설명</th>
						<th>사용여부</th>
						<th>최초등록일</th>
					</tr>
				</thead>
				<tbody id="commGrpCodeList" >
<%-- 				
					<c:forEach var="codeList" items="${codeList}">
					<tr>
						<td style="text-align: left">${codeList.grpCodeId}</td>
						<td style="text-align: left">${codeList.grpCodeName}</td>
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
<div class="layer" id="grpCodePopup">
	<div class="layerInner layerInner02">
		<strong class="firstTit">공통코드그룹 관리</strong>
		<!-- 코드그룹 등록 -->
		<div class="long">
			<table class="horizontalTable">
				<colgroup>
					<col width=""/>
					<col width=""/>
				</colgroup>
				<tbody>
					<tr>
						<th>코드그룹 ID*</th>
						<td><input id="grpCodeId" type="text" style="width:96%" maxlength="32"/></td>
					</tr>
					<tr>
						<th>그룹명*</th>
						<td><input id="grpCodeName" type="text" style="width:96%"  maxlength="32"/></td>
					</tr>
					<tr>
						<th>코드유형*</th>
						<td><input id="grpCodeType" type="text" style="width:96%" maxlength="1"/></td>
					</tr>
					<tr>
						<th>쿼리 ID</th>
						<td><input id="grpCodeQueryId" type="text" style="width:96%"  maxlength="256"/></td>
					</tr>
					<tr>
						<th>사용여부</th>
						<td>						
							<input id="grpCodeUseY" name="grpCodeUseYn" type="radio" value="Y"/><label for="grpCodeUseY">사용</label>
							<input id="grpCodeUseN" name="grpCodeUseYn" type="radio" value="N"/><label for="grpCodeUseN">사용안함</label>
						</td>
					</tr>
					<tr>
						<th>설명</th>
						<td><input id="grpCodeDescription" type="text" style="width:96%"  maxlength="64"/></td>
					</tr>
					
				</tbody>
			</table>
			<div class="btns">
				<button id="btnGrpCodeReg" type="button" class="btnSave">저장</button>
				<button id="btnGrpCodeMod" type="button" class="btnSave">수정</button>
				<button id="btnGrpCodeDel" type="button" class="btnSave">삭제</button>
			</div>
		</div>
		<!-- //코드그룹 등록 -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('grpCodePopup');return false;"><img src="/images/btn_close.png"/></button>
</div>
<!-- //신규등록 팝업 -->



