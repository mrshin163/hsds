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

var aCardList;
var flag;
var srchUserIndex;


jQuery(document).ready(function(){
	//getCardList(null, true);
	
	//////  검색버튼
	$('#btnSrch').click(function(){
		getCardList(null, true);
	});
	
	//////  팝업창 행추가 버튼
	$('#btnAddRow').click(function(){
		var rowIndex = $('#scheduleData tr').length;
		var dataHtml = '<tr>'
			+ '<td><input name="schdUserNm" type="text" size="10" disabled/><input name="schdUserId" type="hidden"/> <div onclick="srchUser(\'srchUserSched\', '+rowIndex+');"  class="btnView" style="display:inline-block;"><i class="icon iconView">돋보기</i></div></td>'
			+ 	'<td></td>'
			+ 	'<td colspan="2">'
			+ 		'<div class="layerCal" style="height:22px;">'
			+ 			'<input class="date-picker" id="firstCal'+rowIndex+'" type="text" name="startDay" size="12"/>'
			+ 			'<label for="firstCal'+rowIndex+'" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>'
			+ 			' ~ '
			+ 			'<input class="date-picker" id="secondCal'+rowIndex+'" type="text" name="endDay"  size="12"/>'
			+ 			'<label for="secondCal'+rowIndex+'" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>'
			+ 		'</div>'
			+ 	'</td>'
			+ '</tr>';
		$('#scheduleData').append(dataHtml);
		
		//////  달력 이벤트 새로 달기  
		$('.date-picker').each(function(){
		    $(this).datepicker();
		});
		
		//////  달력 선택시 이벤트 - 시작날짜
	    $('input[name="startDay"]').bind('change', function(){
	    	var date = new Date();
	    	var year = date.getFullYear();
	    	var month = (date.getMonth() + 1) < 10? "0" + (date.getMonth() + 1):(date.getMonth() + 1);
	    	var date = date.getDate() < 10? "0" + date.getDate():date.getDate();
	    	
	    	var today = "" + year + month + date;
	    	var startDay = $(this).val().replace(/-/g,"");
	    	if((today - startDay)>=0){
	    		bootbox.alert('시작일은 과거 날짜를 지정할 수 없습니다.');
	    		$(this).val('');
	    	}
	    	
	    	var index = $(this).parents('tr').index();
	    	var endDay = $('input[name="endDay"]').eq(index).val().replace(/-/g,"");
	    	var flag= false;
	    	if($(endDay).val()!=''){
		    	$('#scheduleData tr').each(function(rowIndex){
		    		if(index!=rowIndex){
		    			////// 기존에 등록되어있던 스케줄이랑 비교
			    		var tmpStart = $('input[name="startDay"]').eq(rowIndex).val().replace(/-/g,"");
			    		var tmpEnd = $('input[name="endDay"]').eq(rowIndex).val().replace(/-/g,"");
			    		////// 기존에 등록되어있는 끝날짜 보다 시작날짜가 더 빠를경우, 기존에 등록되어있는 시작날짜보다 끝날자가 더 크거나 같을경우
			    		if(startDay<=tmpEnd && endDay>=tmpStart){	    			
			    			bootbox.alert('스케줄이 겹칩니다.');
			    			flag = true;
			    		}
		    		}
		    	});
		    	if(flag){
		    		$('input[name="startDay"]').eq(index).val('');
		    		$('input[name="endDay"]').eq(index).val('')
		    	}
	    	}

	    });
	    
		//////  달력 선택시 이벤트 - 종료날짜
	    $('input[name="endDay"]').bind('change', function(){
	    	var index = $(this).parents('tr').index();
	    	var startDay = $('input[name="startDay"]').eq(index).val().replace(/-/g,"");
	    	if(startDay==''){
	    		bootbox.alert('시작 날짜를 입력하여 주시기 바랍니다.');
	    		$(this).val('');
	    		return;
	    	}
	    	var endDay = $(this).val().replace(/-/g,"");
	    	if((startDay - endDay) > 0){
	    		bootbox.alert('종료일은 시작일보다 뒷 날짜이어야 합니다.');
	    		$(this).val('');
	    	}
			var flag= false;
	    	$('#scheduleData tr').each(function(rowIndex){
	    		if(index!=rowIndex){
	    			////// 기존에 등록되어있던 스케줄이랑 비교
		    		var tmpStart = $('input[name="startDay"]').eq(rowIndex).val().replace(/-/g,"");
		    		var tmpEnd = $('input[name="endDay"]').eq(rowIndex).val().replace(/-/g,"");
		    		////// 기존에 등록되어있는 끝날짜 보다 시작날짜가 더 빠를경우, 기존에 등록되어있는 시작날짜보다 끝날자가 더 크거나 같을경우
		    		if(startDay<=tmpEnd && endDay>=tmpStart){	    			
		    			bootbox.alert('스케줄이 겹칩니다.');
		    			flag = true;
		    		}
	    		}
	    	});
	    	if(flag){
	    		$('input[name="startDay"]').eq(index).val('');
	    		$(this).val('');
	    	}
	    });
	});
	
	//////  스케줄 저장
	$('#btnModiSave').click(function(){
	
		bootbox.confirm('스케줄이 저장됩니다 계속 하시겠습니까?', function(result){
			if(result) {
				if(!valiCheck()){
					bootbox.alert('빈 데이터가 있습니다.');
					return;
				}
				
				var url;
				var params = [];
				var checked = $('input:radio[name="radio"]:checked').val();	
				
				url = '/rest/cardApi/addCardSchedule';
				$('#scheduleData tr').each(function(index){
					params.push({'cardNo' 	: $('#modiCardNo').text(), 
								'userId' 	: $('input[name="schdUserId"]').eq(index).val(),
								'startDay'	: $('input[name="startDay"]').eq(index).val().replace(/-/g,""),
								'endDay'	: $('input[name="endDay"]').eq(index).val().replace(/-/g,"")
					});
								
				});		

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
				    },
					success : function(jsonData) {
						if(jsonData.result=='S'){
							getCardList(null, true);
							bootbox.alert("카드정보 수정에 성공하였습니다.");
							hideModal('cardMod');
							return false;	
						}else{
							bootbox.alert("카드정보 수정된 건이 없습니다.");
						}
								
					},
					error : function(err) {
						bootbox.alert("카드정보 수정에 실패하였습니다." + err);
						return;
					}
				});	
			 } 
		 });		
	});
	
	//////  카드회사 목록 불러오기
	/* jQuery.ajax({
		type : 'GET',
		url : '/rest/codeApi/commCodeList/CARD_CD/0',
		dataType : "json",
		contentType: 'application/json',
		success : function(jsonData) {
			$.each(jsonData.codeList, function(key, code) {
				$('#srchCardCompany').append('<option value="'+ code.code +'">'+ code.codeName +'</option>');
			});
		},
		error : function(err) {
			//alert("카드회사목록을 불러오는데 실패하였습니다." + err);
			return;
		}
	});	 */
	//////  카드상태 목록 불러오기
	jQuery.ajax({
		type : 'GET',
		url : '/rest/codeApi/commCodeList/CARD_ST_CD/0',
		dataType : "json",
		contentType: 'application/json',
		success : function(jsonData) {
			$.each(jsonData.codeList, function(key, code) {
				$('#srchCardStatus').append('<option value="'+ code.code +'">'+ code.codeName +'</option>');
			});
			$('#srchCardStatus').val('2');
		},
		error : function(err) {
			//alert("카드회사목록을 불러오는데 실패하였습니다." + err);
			return;
		}
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
	
	$("#btnUserSelect").click(function(){
		var params = [];
		if($("input[name='userList']:checked").length > 1){
			bootbox.alert("한명만 선택하세요.");
			return;
		}
		var name;
		var id;
		var dept;
		if($("input[name='userList']:checked").length < 1){
			bootbox.alert("직원을 선택해 주세요.");
			return
		}
		
		$("input[name='userList']:checked").each(function(i) {
			$table = $(this).parents("tr");
			id = $table.find("td").eq(1).text();
			name = $table.find("td").eq(2).text();
			dept = $table.find("td").eq(3).text();
	    });
		
		if(flag=='srchUser'){
			$('#srchCardOwnerId').val(id);
			$('#srchCardOwner').val(name);
		}else if(flag=='srchUserSched'){
			$('input[name="schdUserId"]').eq(srchUserIndex).val(id);
			$('input[name="schdUserNm"]').eq(srchUserIndex).val(name);
			$('#scheduleData').find('tr').eq(srchUserIndex).find('td').eq(1).text(dept);
		}
		hideModal('userListPopLayer');
	});	
	
});

function userSearch(userNameSearch, deptNameSearch){
	if(userNameSearch == "") userNameSearch = "all";
	if(deptNameSearch == "") deptNameSearch = "all"; 
	
	jQuery.ajax({
		type : 'GET',
		url : '/rest/userApi/userList/' + userNameSearch + '/' + deptNameSearch + "/0",
		dataType : "json",
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

function getCardList(pageNo, search){

	if (pageNo == null) {
		pageNo = 1;
	}		
	
	var cardOwnCd		= $("#srchCardOwnCd").val()==""?"all":$("#srchCardOwnCd").val();
	var cardNo			= $('#srchCardNo').val()==""?"all":$("#srchCardNo").val();
	var cardOwnUser		= $('#srchCardOwner').val()==""?"all":$("#srchCardOwner").val();
	var cardStCd		= $('#srchCardStatus').val()==""?"all":$("#srchCardStatus").val();
	var cardCom			= $('#srchCardCompany').val()==""?"all":$("#srchCardCompany").val();
	var searchTypeCd 	= $('#searchTypeCd').val()==""?"all":$("#searchTypeCd").val();
	
	$.ajax({
		type : 'GET',
		url : '/rest/cardApi/getCardScheduleList/' + cardOwnCd + '/' + cardNo + '/' + cardOwnUser + '/' + cardStCd + '/' + searchTypeCd + '/' + pageNo,		
		dataType : "json",
		contentType: 'application/json',
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			aCardList = jsonData.cardList;
			//setData(data.html, data.totalRow);
			var dataAreaHtml = "";
			$.each(jsonData.cardList, function(key, card) {
				//var actUserNm = commCard.actUserNm==null?"":commCard.actUserNm;
				dataAreaHtml += '<tr>'
									+ '<td>' + card.cardOwnNm 		+ '</td>'
									+ '<td>' + card.cardTpNm 		+ '</td>'
									+ '<td>' + card.cardNo 			+ '</td>'
									+ '<td>' + card.cardStNm 		+ '</td>'
									+ '<td>' + card.joinDate 		+ '</td>'
									+ '<td>' + card.expireDate 		+ '</td>'
									+ '<td>' + card.cardOwnUserNm 	+ '</td>'
									+ '<td>' + card.cardOwnUserId 	+ '</td>'
									+ '<td>' + card.cardOwnDeptNm 	+ '</td>'
									+ '<td>' + card.cardOwnTiTleNm 	+ '</td>'
									+ '<td>' + card.deptMgrNm		+ '</td>'
									+ '<td>' + card.deptMgrId		+ '</td>'
									//+ '<td>' + card.repUserNm 		+ '</td>'
									//+ '<td>' + card.sec1UserNm;
				//if(card.sec2UserNm!=null && card.sec2UserNm ==''){
				//	dataAreaHtml += '<br/>' + card.sec2UserNm;
				//}

				//dataAreaHtml += '</td>'
									//+ '<td>' + card.comCardMgrNm 	+ '</td>'
									+ '<td>' + card.sUserNm 	+ '</td>'
									+ '<td>' + card.sStartDay 		+ '</td>'
									+ '<td>' + card.sEndDay 		+ '</td>'
									+ '<td><button onclick="cardModify(this);" class="btnModify">수정</button></td>'
									+ '</tr>';
				}
			);
			$('#dataArea').html(dataAreaHtml);
			
			if(search){
			    $("#pagenationSection").pagination({
			        items: jsonData.totalRow,
			        itemsOnPage: 10,
			        cssStyle: 'light-theme',
			       	hrefTextPrefix: "javascript:getCardList(",
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
//////  유저목록 팝업 내용 삭제
function deleteUserPop(){
	$('#userSearchList').empty();
	$('#userListForUser').val('');
	$('#userListForDept').val('')
}


//////  카드수정 팝업창 띄우기
function cardModify(obj){
	var index = $(obj).parents("tr").index();
	var card = aCardList[index];
	$('#modiCardNo').text(card.cardNo);
	$('#modiCardOwn').text(card.cardOwnNm);
	$('#scheduleData').empty();
	
	$.ajax({
		type : 'GET',
		url : '/rest/cardApi/getCardSchedule/' + $('#modiCardNo').text(),		
		dataType : "json",
		contentType: 'application/json',
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			var dataAreaHtml = "";
			$.each(jsonData.cardSchedule, function(index, card) {
				var dataHtml = '<tr>'
					+ '<td><input name="schdUserNm" type="text" size="10" value="'+card.userNm+'"disabled/><input name="schdUserId" value="'+card.userId+'" type="hidden"/>'//' <div onclick="srchUser(\'srchUserSched\', '+index+');"  class="btnView" style="display:inline-block;"><i class="icon iconView">돋보기</i></div></td>'
					+ 	'<td>'+card.deptNm+'</td>'
					+ 	'<td colspan="2">'
					+ 		'<div class="layerCal" style="height:22px;">'
					+ 			'<input class="date-picker" id="firstCal'+index+'" type="text" name="startDay" size="12" value="'+card.startDay+'" disabled/>'
					+ 			'<label for="firstCal'+index+'" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>'
					+ 			' ~ '
					+ 			'<input class="date-picker" id="secondCal'+index+'" type="text" name="endDay"  size="12" value="'+card.endDay+'" disabled/>'
					+ 			'<label for="secondCal'+index+'" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>'
					+ 		'</div>'
					+ 	'</td>'
					+ '</tr>';
				$('#scheduleData').append(dataHtml);
				$('.date-picker').each(function(){
				    $(this).datepicker();
				});
				
			});
			showModal('cardMod');
		},
		error : function(e) {
			console.log(e);
		}
	});	
	
	return false;
}

function srchUser(pFlag, index){
	deleteUserPop();
	flag = pFlag;
	srchUserIndex = index;
	showModal('userListPopLayer');
	return false;
}

function valiCheck(){
	var result = true;
	$('#scheduleData tr').each(function(rowIndex){
		if($('input[name="schdUserId"]').eq(rowIndex).val()=='') result = false;
		if($('input[name="startDay"]').eq(rowIndex).val().replace(/-/g,"")=='') result = false;
		if($('input[name="endDay"]').eq(rowIndex).val().replace(/-/g,"")=='') result = false;
	});
	return result;
}

</script>

<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2>
			<i class="icon iconCardManage02"></i> <span>카드기간관리</span>
		</h2>
		<p class="location">Home > 카드관리 > 카드기간관리</p>
	</div>

	<!-- 검색조건 -->
	<div class="srchCondition twoLine">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<div class="left">
				<ul>
					<li class="odd">
						<span class="inputTit">카드종류</span> 
						<select id="srchCardOwnCd" class="customSelect">
								<option value="all">전체</option>
								<option value="1">법인카드</option>
								<option value="2">법인개인카드</option>
						</select> 
					</li>
					<li class="even">
						<span class="inputTit">카드상태</span>
						<input id="searchTypeCd" type="hidden"/>						
						<select id="srchCardStatus" class="customSelect">
							<option value="all">전체</option>
						</select> 
					</li>
					<li class="odd">
						<span class="inputTit">카드번호</span> 
						<input id="srchCardNo" type="text" style="width:135px;"/>						
					</li>
					<li class="even">
						<span class="inputTit">카드소유자</span>
						<input id="srchCardOwner" type="text"/><input id="srchCardOwnerId" type="hidden"/> <div class="btnView" onclick="srchUser('srchUser');" style="display: inline-block;"><i class="icon iconView">돋보기</i></div>
					</li>					
					<!-- <li class="odd">
						<span class="inputTit">카드회사</span> 
						<select id="srchCardCompany" class="customSelect">
							<option value="all">전체</option>
						</select> 
					</li> -->
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
		<strong class="tit">카드 관리<em id="totalRow">${totalRow}</em>건</strong>
		<div class="innerBox" id="dataBox">
			<div class="btns">
				<!-- <a href="" class="btnCardCall">카드호출</a> -->
			</div>
			
			<div class="scrollTable">
				<div class="inner">
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
							<col width="" />
							<col width="" />
						</colgroup>
						<thead>
							<tr>
								<th>카드종류</th>	<!--  -->
								<th>카드회사</th>	<!--  -->
								<th>카드번호</th>	<!--  -->
								<th>카드상태</th>	<!--  -->
								<th>가입일</th>		<!--  -->
								<th>만기일</th>		<!--  -->
								<th>소유자</th>		<!--  -->
								<th>사번</th>		<!--  -->
								<th>부서</th>		<!--  -->
								<th>직급</th>		<!--  -->
								<th>부서장</th>		<!--  -->
								<th>부서장사번</th>	<!--  -->
								<!-- <th>대체상신자</th>	
								<th>비서</th>
								<th>공용카드관리자</th> -->
								<th>사용(예정)자</th>	<!--  -->
								<th colspan="2">카드허용기간</th>
								<th>수정</th>	
							</tr>
						</thead>
						<tbody id="dataArea">
							<tr>
								<td colspan="16"> 검색버튼으로 조회하시기 바랍니다.</td>
								
							</tr>
						<!-- 
							<c:forEach var="card" items="${cardList}" varStatus="status">
								<tr>
									<td>${card.cardOwnNm}</td>
									<td>${card.cardTpNm}</td>
									<td>${card.cardNo}</td>
									<td>${card.cardStNm}</td>
									<td>${card.joinDate}</td>
									<td>${card.expireDate}</td>
									<td>${card.cardOwnUserNm}</td>
									<td>${card.cardOwnUserId}</td>
									<td>${card.cardOwnDeptNm}</td>
									<td>${card.cardOwnTiTleNm}</td>
									<td></td>
									<td></td>
									<td>${card.repUserNm}</td>
									<td>${card.sec1UserNm}
										<c:if test="${card.sec2UserNm}!=null">
											<br/>${card.sec2UserNm}
										</c:if>
									</td>
									<td>${card.comCardMgrNm}</td>
									<td>${card.sStartDay}</td>
									<td>${card.sEndDay}</td>
									<td><button onclick="cardModify(this);" class="btnModify">수정</button></td>
								</tr>
							</c:forEach>
							 -->
						</tbody>
					</table>					
				</div>
			</div>
		</div>
	</div>
	<div id="pagenationSection"></div>
	<!-- //검색내역 -->
</div>

	<!-- 카드허용기간 수정하기 팝업 -->
	<div class="layer" id="cardMod">
		<div class="layerInner layerInner02">
			<strong class="firstTit">카드정보 수정하기</strong>
			<!-- 카드허용기간 수정하기 -->
			<div class="long">
				<div class="btns">
					<button id="btnModiSave" type="button" class="btnSave">저장</button>
				</div>
				<table class="horizontalTable">
					<colgroup>
						<col width=""/>
						<col width=""/>
						<col width=""/>
						<col width=""/>
					</colgroup>
					<tbody>
						<tr>
							<th>카드번호</th>
							<td colspan="3" id="modiCardNo"> </td>
						</tr>
						<tr>
							<th>카드종류</th>
							<td colspan="3" id="modiCardOwn"></td>
						</tr>
					</tbody>
				</table>
<br/>
				<div class="btns">
					<button id="btnAddRow" type="button" class="btnSave">행추가</button>
				</div>
				<table class="defaultType">
					<colgroup>
						<col width=""/>
						<col width=""/>
						<col width=""/>
						<col width=""/>
					</colgroup>
					<thead>
						<tr>
							<th>카드소유자</th>
							<th>부서</th>
							<th colspan="2">카드사용허용기간</th>
						</tr>
					</thead>
					<tbody id="scheduleData">
<!-- 						<tr>
							<td><input name="schdUserId" type="hidden"/> <div onclick="showModal('memSrch');return false;"  class="btnView"><i class="icon iconView">돋보기</i></div></td>
							<td><input name="schdUserDeptId" type="hidden"/></td>
							<td colspan="2">
								<div class="layerCal">
									<input class="date-picker" id="firstCal00" type="text" />
									<label for="firstCal00" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>
									~
									<input class="date-picker" id="secondCal00" type="text" />
									<label for="secondCal00" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>
								</div>
							</td>
						</tr> -->
					</tbody>
				</table>
			</div>
			<!-- //카드허용기간 수정하기 -->
		</div>
		<button type="button" class="btnLayerClose" onclick="hideModal('cardMod');return false;"><img src="/images/btn_close.png"/></button>
	</div>
	<!-- //카드허용기간 수정하기 팝업 -->