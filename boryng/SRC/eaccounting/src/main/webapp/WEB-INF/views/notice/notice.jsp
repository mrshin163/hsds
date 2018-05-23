<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageTag" uri="PageTag"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.bizsp.framework.security.vo.LoginUserVO"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.Authentication"%>

<%
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
 
Object principal = auth.getPrincipal();
String name = "";
boolean isAdmin = false;

if(principal != null && principal instanceof LoginUserVO){
    name = ((LoginUserVO)principal).getName();
    
    for(Object userAuth : ((LoginUserVO)principal).getAuthorities().toArray()){
    	if(userAuth.toString().equals("R99") || userAuth.toString().equals("R07")){
    		//System.out.println("########## auth : " + userAuth.toString());
    		isAdmin = true;
    	}
    }
}
%>
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/json2/20121008/json2.js"></script>
<script>

var noticeList = null;
var reg = /^[0-9]+$/;

//////  공지사항 리스트 조회
function getNotice(pageNo, search){
	
	if(pageNo == null) pageNo = 1;
	
	jQuery.ajax({
		type : 'GET',
		url : '/rest/noticeApi/noticeList/' + pageNo,
		dataType : "json",
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			var noticeListHtml = "";
			noticeList = jsonData.notice;
			$.each(jsonData.notice, function(key, noticeList) {
				noticeListHtml += "<tr>"
								+ "<td>" + noticeList.seq + "</td>"
								+ "<td onclick=\"viewNotice(this, "+noticeList.seq+");\" style=\"padding:5px 5px 5px 10px; cursor:pointer; text-align:left; text-overflow:ellipsis; overflow:hidden;\"><nobr>"
								+ noticeList.title 
								+ "</nobr></td>"
								+ "<td>" + noticeList.regUserId +"</td>"
								+ "<td>" + dateFormatSeconds(noticeList.regDate) + "</td>"	
								+ "<td name=\"readCnt\">" + noticeList.readCnt + "</td>"
								+ "</tr>";
			});
			
			if(jsonData.notice.length==0){
				noticeListHtml = '<tr><td colspan="5"> 조회된 데이터가 없습니다.</td></tr>';
			}
			
			$('#noticeList').html(noticeListHtml);
			
			$('#totalRow').html(jsonData.totalRow + "건");

			if(search){
				$("#pagenationSection").pagination({
			        items: jsonData.totalRow,
			        itemsOnPage: 10,
			        cssStyle: 'light-theme',
			       	hrefTextPrefix: "javascript:getNotice(",
					hrefTextSuffix: ");"
			    });
			}
		},
		error : function(err) {
			var noticeListHtml = "";
			noticeListHtml = "<tr><td colspan=\"5\" style=\"text-align:center;\">데이터를 불러오는 도중 오류가 발생하였습니다.</td></tr>";
			$('#noticeList').html(noticeListHtml);
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});
}

//////제목 클릭했을때 팝업창 obj=<td>
function viewNotice(obj, seq){
	
	clearNotice();
	$('#btnNoticeSave').hide();
	var tr;
	if(obj!=null) tr= $(obj).parents('tr');
	
	var url = '/rest/noticeApi/addCount/'+ seq;
	
	//////  조회수 증가, 공지사항 상세내용 가져오기
	jQuery.ajax({
		type : 'POST',
		url : url,
		dataType : "json",
		contentType: 'application/json',
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			var notice = jsonData.notice;
			//////  조회수 증가
			if(obj!=null) 
				$(tr).find("[name='readCnt']").text(notice.readCnt);
			
			//////  팝업창 세팅
			$('#noticeSeq').val(notice.seq);
			$('#noticeTitle').val(notice.title);
			$('#noticeRegUserId').val(notice.regUserId);
			$('#noticeRegDate').val(dateFormatSeconds(notice.regDate));
			$('#noticeCount').val(notice.readCnt);
			$('#noticeContent').val(checkNull(notice.content));
			$('#noticeAttachId').val(checkNull(notice.attachId));
			
			////// 첨부파일 리스트
			if(jsonData.fileList!=null && jsonData.fileList.length!=0){
				var list = jsonData.fileList;
				$.each(list, function(index, attachFile) {
					$('#noticeFileList').append(getFileListNotice(null, attachFile));			
				});
			}
			showModal('noticePopup');		
			
		},
		error : function(err) {
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});		
}

//////첨부파일 리스트에 들어갈 HTML 생성
function getFileListNotice(data, attachFile){
	var fileListHtml = '<tr>';
	
	////// 최초 등록시(attachFile==null)에는 다운로드가 안된다. 삭제만 됨
	if(attachFile==null){
		fileListHtml 	+='<td style="padding:0px; border:0px; width:5%;"><input type="checkbox" name="checkFile"/></td>'
						+ '<td style="padding:0px; border:0px; ">'	+	data.files[0].name + '</td>'
						+ '<td style="padding:0px; border:0px; width:20%; text-align:right;">'	+	Number(data.files[0].size).toLocaleString().split(".")[0] + ' byte</td>';
						
	}else{		
		var url = "/rest/uploadApi/downloadFile/" + attachFile.attachId + "/" + attachFile.seq;
		fileListHtml 	+='<td style="padding:0px; border:0px; width:5%;"><input type="checkbox" name="checkFile" attachId="'+attachFile.attachId+'" seq="'+attachFile.seq+'"/></td>'
						+ '<td style="padding:0px; border:0px; "><a href="' + url + '">'	+	attachFile.lFileName + '.' + attachFile.ext + '</a></td>'
						+ '<td style="padding:0px; border:0px; width:20%; text-align:right;">'	+	Number(attachFile.fileSize).toLocaleString().split(".")[0] + ' byte</td>';						
	}
	
	fileListHtml += "</tr>";
	return fileListHtml;
}

//////첨부파일리스트 ROW 삭제
function deleteCheckedFile(){
	var checked = $('input[name="checkFile"]:checked');
	var size = $(checked).length;

	for(var i=0 ; i<size; i++){
		var check = $(checked).eq(i);
		var tr = $(check).parent().parent();
		
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
		break;
	}
	
}

//////신규생성할 때 팝업창
function showPop(){
	clearNotice();
	$('#btnNoticeModi').hide();
	$('#btnNoticeDel').hide();
	$('#noticeRegUserId').val('<%=name%>');
	
	showModal('noticePopup');
}
//////공지사항 저장, 수정, 삭제 AJAX 수행
function modifyNoticeAjax(flag){
	var url = '/rest/noticeApi/###Notice';
	var params = '';
	
	var seq = $('#noticeSeq').val();
	var title = $('#noticeTitle').val();
	var content = $('#noticeContent').val();
	var attachId = $('#noticeAttachId').val();
	
	url = url.replace('###',flag);
	params = {		"title" 	: 	title, 
					"content" 	: 	content, 
					"seq" 		: 	seq, 
					"attachId" 	: 	attachId};
	
	jQuery.ajax({
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
			getNotice(null, true);
	    },
		success : function(jsonData) {			
			bootbox.alert(jsonData.message);
			hideModal('noticePopup');
		},
		error : function(err) {
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});	
}


//////팝업창 클리어
function clearNotice(){
	$('#noticeSeq').val('');
	$('#noticeTitle').val('');
	$('#noticeRegUserId').val('');
	$('#noticeRegDate').val('');
	$('#noticeCount').val('');
	$('#noticeContent').val('');
	$('#noticeAttachId').val('');
	$('#noticeFileList').empty();
	$('#btnNoticeSave').show();
	$('#btnNoticeModi').show();
	$('#btnNoticeDel').show();
}

//////날짜값 포맷 변경 초까지
function dateFormatSeconds(str){
	var date = str.substring(0,4) + "."
			+ str.substring(4,6) + "."
			+ str.substring(6,8) + " "
			+ str.substring(8,10) + ":"
			+ str.substring(10,12);
	
	return date;
}
//////  READY
$(function(){
	
	getNotice(null, true);
	
	$('#btnNoticeInsertFile').click(function(){
		if($('#noticeAttachId').val()==''){
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
					$('#noticeAttachId').val(jsonData.attachId);
					$("#noticeFileUpload").fileupload('option', 'url', '/rest/uploadApi/uploadNoticeFile/notice/' + jsonData.attachId);					
				},
				error : function(err) {
					bootbox.alert("처리중 장애가 발생하였습니다." + err);
				}
			});
		}else{
			$("#noticeFileUpload").fileupload('option', 'url', '/rest/uploadApi/uploadNoticeFile/notice/' + $('#noticeAttachId').val());			
		}
		$("#noticeFileUpload").trigger("click");
		
	});

	
	
	//////  권한에 따른 팝업 화면 설정
	if(<%=isAdmin%>){
		$("#btnNoticeSave").click(function(){
			bootbox.confirm("저장 하겠습니까?", function(result){
				if(result){
					modifyNoticeAjax('insert');
				}				
			}) 
		});
		$("#btnNoticeModi").click(function(){
			bootbox.confirm("수정 하겠습니까?", function(result){
				if(result){
					modifyNoticeAjax('update');
				}
			})
		});
		$("#btnNoticeDel").click(function(){
			bootbox.confirm("삭제 하겠습니까?", function(result){
				if(result){
					modifyNoticeAjax('delete');
				}
			}) 
		});		
		$("#btnNoticeDeleteFile").click(function () {	    
			bootbox.confirm("선택된 파일을 삭제 하겠습니까?", function(result){
				if(result){
					deleteCheckedFile('chkFile', 'fileList');
				}
			}) 
		});
		
		$('div[name="noticeAdminArea"]').show();

		$('#noticeTitle').attr("disabled",false);
		$('#noticeTitle').attr("style", "border : 1;");
		$('#noticeContent').attr("disabled",false);
		$('#noticeContent').attr("style", "border : 1; height: 200;");

	}else{
		$("#btnNoticeReg").hide();
		$("#noticeFileBtns").hide();
	}
	
	//////파일 업로드
	$("#noticeFileUpload").fileupload({
		//////  /rest/uploadApi/uploadFile/{게시판구분}/{첨부파일마스터ID}
        url: '/rest/uploadApi/uploadFile/APR01/',
        dataType: 'json',
        maxFileSize: 5000000, // 5 MB
        
    }).on('fileuploadadd', function (e, data) {				//////  파일 추가할떄의 이벤트
		$('.loading').css("display", "block");
    	data.submit();
    }).on('fileuploaddone', function (e, data) {			//////  파일당 업로드가 끝났을 때 수행, 3개파일 한번에 올리면 각각 한번씩 총 3번 수행
    	var fileListHtml = getFileListNotice(data, null);
		$('#noticeFileList').append(fileListHtml);
		$('.loading').css("display", "none");
    }).on('fileuploadfail', function (e, data) {
    	$('#noticeFileList').append('<tr><td colspan="3" style="padding:0px; border:0px;">'+data.files[0].name + data.files[0].size +" - 업로드 실패"+'</td></tr>');
    });
});
	
</script>
<style>	
.bar {
    height: 24px;
    background: green;
}
</style>

<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2><i class="icon iconNoticeManage02"></i> <span>공지사항 등록</span></h2>
		<p class="location">Home > 공지사항 등록</p>
	</div>
	<!-- 검색내역 -->
	<div class="srchList">
		<div id="loader" style="position: absolute; display: none; top:45%; left:50%;"><img src="/images/admin/ajax-loader.gif"></div>
		<strong class="tit">공지사항 목록 <em id="totalRow"> 건</em></strong>
		<div class="innerBox" id="dataBox">
			<div class="btns">
				<a href="" id="btnNoticeReg" class="btnReg" onclick="showPop();return false;">신규등록</a>
			</div>
			<table class="defaultType"  style="table-layout:fixed;">
				<colgroup>
					<col width="5%" />
					<col width="50%" />
					<col width="" />
					<col width="" />
					<col width="5%" />
				</colgroup>
				<thead>
					<tr>
						<th>NO</th>
						<th>제목</th>
						<th>작성자</th>
						<th>등록일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody id="noticeList">

				</tbody>
			</table>							
		</div>	
		<p id="pagenationSection" style="margin-top : 10px;"></p>					
	</div>
	
	<!-- //검색내역 -->
</div>
<!-- 공지사항 팝업 -->
<div class="layer" id="noticePopup" >
	<div class="layerInner layerInner02" style="width: 700px;">
		<strong id="firstTit" class="firstTit">공지사항</strong>
		<!-- 공지사항 등록 -->
		<div class="long">
			<div id="noticeLoader" style="position: absolute; display: none; top:45%; left:50%;"><img src="/images/admin/ajax-loader.gif"></div>
			<table class="horizontalTable" id="noticeDataBox">
				<colgroup>
					<col width=""/>
					<col width=""/>
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td colspan="5">
							<input id="noticeSeq" type="hidden"/>
								<input id="noticeTitle" type="text" style="width:96%; border: 0; background-color: white;" maxlength="60" disabled="disabled"/>
						</td>
					</tr>
					<tr>
						<th>첨부파일</th>
						<td colspan="5">
							<input id="noticeAttachId" type="hidden"/>
							<input id="noticeFileUpload" name="file" type="file" multiple="multiple" style="display: none;">
							<div id="noticeFileBtns" style="display:flex;">
								<button id="btnNoticeInsertFile" class="btnReg">파일첨부</button>
								<button id="btnNoticeDeleteFile" class="btnReg">파일삭제</button>
							</div>
							<table id="noticeFileList" style="width:98%;">

							</table>
							
						</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>
							<input id="noticeRegUserId" type="text" style="border: 0; background-color: white;" disabled="disabled"/>
						</td>
						<th>등록일</th>
						<td>
							<input id="noticeRegDate" type="text" style="border: 0; background-color: white;" disabled="disabled"/>
						</td>
						<th>조회수</th>
						<td>
							<input id="noticeCount" type="text" style="border: 0; background-color: white;" disabled="disabled"/>
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td colspan="5">					
							<textarea id="noticeContent" maxlength="2000" style="height: 200px; border: 0; background-color: white;" disabled="disabled"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="btns" style="display:flex;">
				<div name="noticeAdminArea" style="display:none;">
					<button id="btnNoticeSave" type="button" class="btnSave" >저장</button>
					<button id="btnNoticeModi" type="button" class="btnModify" >수정</button>
					<button id="btnNoticeDel" type="button" class="btnDelShort" >삭제</button>
				</div>
				<button type="button" class="btnSave" onclick="hideModal('noticePopup');return false;">닫기</button>
			</div>
		</div>
		<!-- //공지사항 등록 -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('noticePopup');return false;"><img src="/images/btn_close.png"/></button>
</div>
<!-- //공지사항 팝업 -->