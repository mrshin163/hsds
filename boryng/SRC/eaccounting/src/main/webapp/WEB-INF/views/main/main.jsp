<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="com.bizsp.framework.security.vo.LoginUserVO"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	int dayNum = cal.get(Calendar.DAY_OF_MONTH);
	String month = "";
	if(dayNum<=10) { // 전월기준으로
		cal.add(cal.MONTH,-1);
		month = new SimpleDateFormat("MM").format(cal.getTime());
	}else{
		month = new SimpleDateFormat("MM").format(cal.getTime());
	}
	
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	 
	Object principal = auth.getPrincipal();
	boolean isAdmin = false;

	if(principal != null && principal instanceof LoginUserVO){
	    isAdmin = ((LoginUserVO)principal).getAuthorities().toArray()[0].toString().equals("R99")?true:false;
	}

%>

<script type="text/javascript">

$(document).ready(function () {
	getMainInfomation();
	setCalendar();  
});

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
}

//////제목 클릭했을때 팝업창
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
					$('#noticeFileList').append(getFileListNotice(attachFile));			
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
function getFileListNotice(attachFile){
	var fileListHtml = '<tr>';
	
	////// 최초 등록시(attachFile==null)에는 다운로드가 안된다. 삭제만 됨
	if(attachFile!=null){
		var url = "/rest/uploadApi/downloadFile/" + attachFile.attachId + "/" + attachFile.seq;
		fileListHtml 	+= '<td style="padding:0px; border:0px; "><a href="' + url + '">'	+	attachFile.lFileName + '.' + attachFile.ext + '</a></td>'
						+ '<td style="padding:0px; border:0px; width:20%; text-align:right;">'	+	Number(attachFile.fileSize).toLocaleString().split(".")[0] + ' byte</td>';						
	}
	
	fileListHtml += "</tr>";
	return fileListHtml;
}

function getMainInfomation(){
	 $.ajax({
 		type : 'GET',
 		url : '/rest/mainApi/getMainInfomation',
 		dataType : "json",
 		contentType: 'application/json',
 		beforeSend:function(){
 	    },
 	    complete:function(){
 	    },
 		success : function(jsonData) {
 			
 			$("#cntUnreq").html("<span>" + numberPad(jsonData.cntUnreq.toString(),1) + "</span><em>건</em>");
 			$("#cntRej").html("<span>" + numberPad(jsonData.cntRej.toString(),1) + "</span><em>건</em>");
 			$("#cntIng").html("<span>" + numberPad(jsonData.cntIng.toString(),1) + "</span><em>건</em>");
 			$("#cntComp").html("<span>" + numberPad(jsonData.cntComp.toString(),1) + "</span><em>건</em>");
 			$("#cntTodo").html("<span>" + numberPad(jsonData.cntTodo.toString(),1) + "</span><em>건</em>");

 			var noticeHtml = "";
 			$.each(jsonData.noticeList, function(key, notice) {
 				noticeHtml += "<li><a href='javascript:viewNotice(null, "+notice.seq+");'><div style='width:390px; text-overflow:ellipsis; overflow:hidden;'><nobr>" +notice.title+ "</nobr></div></a> <span>" +dateFormat(notice.regDate)+ "</span></li>";
 			});
 			
 			if(jsonData.noticeList.length==0){
 				noticeHtml = "<li>조회된 데이터가 없습니다.</li>";
			}
 			
 			$("#noticeList").html(noticeHtml);
 		},
 		error : function(err) {
 			bootbox.alert("메인화면 정보 구성중 에러가 발생하였습니다." + err);
 		}
 	});
}

function setCalendar(){
	var monthText = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
	/* 현재 날짜와 현재 달에 1일의 날짜 객체를 생성합니다. */
	var date = new Date(); // 날짜 객체 생성
	var y = date.getFullYear(); // 현재 연도
	var m = date.getMonth(); // 현재 월
	var d = date.getDate(); // 현재 일
	$('#mainToday').text(y + '.' + numberPad(m+1,2) + '.' + numberPad(d,2));
	$('#mainMonth').text((m+1) + '. ' + monthText[m]);
	setCalendarHtml(y, m, d);
}

function setCalendarHtml(y, m, d){

	// 현재 년(y)월(m)의 1일(1)의 요일을 구합니다.
	var theDate = new Date(y,m,1);
	// 그 날의 요일
	var theDay = theDate.getDay();

	/* 현재 월의 마지막 일을 구합니다. */
	// 1월부터 12월까지의 마지막 일을 배열로 만듭니다.
	var last = [31,28,31,30,31,30,31,31,30,31,30,31];
	// 4년마다 있는 윤년을 계산합니다.(100년||400년 주기는 제외)
	if (y%4 && y%100!=0 || y%400===0) {
	    lastDate = last[1] = 29;
	}
	// 현재 월의 마지막 일이 몇일인지 구합니다.
	var lastDate = last[m];


	/* 현재 월의 달력에 필요한 행의 개수를 구합니다. */
	// theDay(빈 칸의 수), lastDate(월의 전체 일수)
	var row = Math.ceil((theDay+lastDate)/7);

	var calendar = '';
	var clsSun = 'sun';
	var clsToday = 'selected';
	var cls = '';
	// 달력에 표기되는 일의 초기값!
	var dNum = 1;
	for (var i=1; i<=row; i++) { // 행 만들기
	    calendar += "<tr>";
	    for (var k=1; k<=7; k++) { // 열 만들기
	    	cls = '';
	        // 월1일 이전 + 월마지막일 이후 = 빈 칸으로!	        
	        if(i===1 && k<=theDay || dNum>lastDate) {
	            calendar += "<td> &nbsp; </td>";
	        } else {
	        	// 클래스 설정
				if(k==1){	//일요일이면
					cls += clsSun;
				}
				if(d==dNum){	//오늘 날짜이면
					cls += ' ' + clsToday;
				}
	            calendar += "<td class=\""+cls+"\">" + dNum + "</td>";
	            dNum++;

	        }
	    }
	    calendar += "</tr>";
	}
	$('#calData').html(calendar);
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


</script>

<div class="content">

	<div class="mainColumn">
		<!-- 상신 -->
		<div class="column column01">
			<strong class="tit">상신<em>Request</em></strong>
			<ul>
				<li class="odd"><a href="/approvalRq/approvalRqList"> <i class="icon iconUncheck"></i> <strong>미상신 카드건수<span>Unreported</span></strong>
						<div class="num" id="cntUnreq">
							0<em>건</em>
						</div>
				</a></li> 
				<li class="even"><a href="/approval/progressList"> <i class="icon iconReturn"></i> <strong>결재중 문서<span>In Progress</span></strong>
						<div class="num red" id="cntIng">
							0<em>건</em>
						</div>
				</a></li>
				<li class="odd"><a href="/approval/restoreList"> <i class="icon iconPayment"></i> <strong>반려 카드건수<span>Return</span></strong>
						<div class="num red" id="cntRej">
							0<em>건</em>
						</div>
				</a></li>
				<li class="even"><a href="/approval/completeList"> <i class="icon iconComplete"></i> <strong><em><%=month %>월</em>결재완료문서<span>Completion</span></strong>
						<div class="num red" id="cntComp">
							0<em>건</em>
						</div>
				</a></li>
			</ul>
		</div>
		<!-- //상신 -->
		<!-- 승인 -->
		<div class="column column02">
			<strong class="tit">승인<em>Approval</em></strong>
			<div class="inner">
				<a href="/approval/approvalList"> <i class="icon iconDoc"></i> <strong>결재할 문서<span>Document</span></strong>
					<div class="num" id="cntTodo">
						0<em>건</em>
					</div>
				</a>
			</div>
		</div>
		<!-- //승인 -->
		<!-- 공지사항 -->
		<div class="column column03">
			<strong class="tit"><span>공지사항<em>Notice</em></span><a href="/notice/notice"><i class="icon iconNiticeMore">더보기</i></a></strong>
			<ul id="noticeList">
				<li></li>
			</ul>
		</div>
		<!-- //공지사항 -->
		<!-- 캘린더 -->
		<div class="column column04">
			<strong class="tit"><span>캘린더<em>Calendar</em></span></strong>
			<div class="inner">
				<div class="left">
					<i class="icon iconCal"></i> <span id="mainToday">2015.01.16</span>
				</div>
				<div class="right">
					<span id="mainMonth">1. January</span>
					<table>
						<colgroup>
							<col width="33" />
							<col width="33" />
							<col width="33" />
							<col width="33" />
							<col width="33" />
							<col width="33" />
							<col width="33" />
						</colgroup>
						<thead>
							<tr>
								<th class="sun">Su</th>
								<th>Mo</th>
								<th>Tu</th>
								<th>We</th>
								<th>Th</th>
								<th>Fr</th>
								<th>Sa</th>
								
							</tr>
						</thead>
						<tbody id="calData">
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td>1</td>
								<td>2</td>
								<td>3</td>
								<td class="sun">4</td>
							</tr>
							<tr>
								<td>5</td>
								<td>6</td>
								<td>7</td>
								<td>8</td>
								<td>9</td>
								<td>10</td>
								<td class="sun">4</td>
							</tr>
							<tr>
								<td>12</td>
								<td>13</td>
								<td>14</td>
								<td>15</td>
								<td>16</td>
								<td>17</td>
								<td class="sun">11</td>
							</tr>
							<tr>
								<td class="selected">19</td>
								<td>20</td>
								<td>21</td>
								<td>22</td>
								<td>23</td>
								<td>24</td>
								<td class="sun">18</td>
							</tr>
							<tr>
								<td>26</td>
								<td>27</td>
								<td>28</td>
								<td>29</td>
								<td>30</td>
								<td>31</td>
								<td class="sun">25</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- //캘린더 -->
	</div>
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
								<input id="noticeTitle" type="text" style="width:96%; border: 0; background-color: white;" disabled="disabled"/>
						</td>
					</tr>
					<tr>
						<th>첨부파일</th>
						<td colspan="5">
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
							<textarea id="noticeContent" style="height: 200px; border: 0; background-color: white;" disabled="disabled"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<button type="button" class="btnSave" onclick="hideModal('noticePopup');return false;">닫기</button>
		</div>
		<!-- //공지사항 등록 -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('noticePopup');return false;"><img src="/images/btn_close.png"/></button>
</div>
<!-- //공지사항 팝업 -->