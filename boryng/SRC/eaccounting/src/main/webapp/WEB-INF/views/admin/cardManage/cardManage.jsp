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
var userFlag;


jQuery(document).ready(function(){
	//getCardList(null, true);
	
	$('#btnSrch').click(function(){
		getCardList(null, true);
	});
	
	$('#btnModiSave').click(function(){
		bootbox.confirm('카드 정보가 수정됩니다. 계속 하시겠습니까?', function(result){
			if(result) {
				var url;
				var params ;
				var checked = $('input:radio[name="radio"]:checked').val();
				
				if(checked =='repCardYn'){
					url = '/rest/cardApi/setRepUser';
					params = {'cardNo' : $('#modiCardNo').text(), 'repUserId' : $('#modiRepUserId').val()};
				}else if(checked =='comCardYn'){
					url = '/rest/cardApi/setComCardMgr';
					params = {'cardNo' : $('#modiCardNo').text(), 'comCardMgrId' : $('#modiComCardMgrId').val()};
				}else if(checked =='secCardYn'){
					url = '/rest/cardApi/setSecCardMgr';
					params = {'cardNo' : $('#modiCardNo').text(), 'sec1UserId' : $('#modiSec1UserId').val(), 'sec2UserId' : $('#modiSec2UserId').val()};
				}else{
					url = '/rest/cardApi/setInitCardMgmt';
					params = {'cardNo' : $('#modiCardNo').text()};
				}
				/*
				params = {	'cardNo' 		: $('#modiCardNo').text(), 
							'repUserId' 	: $('#modiRepUserId').val(),
							'comCardMgrId' 	: $('#modiComCardMgrId').val(),
							'sec1UserId' 	: $('#modiSec1UserId').val(),
							'sec2UserId' 	: $('#modiSec2UserId').val()};
				*/
				
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
						getCardList(null, true);
						bootbox.alert("카드정보 수정에 성공하였습니다.");
						hideModal('cardMod');
						return false;
					},
					error : function(err) {
						bootbox.alert("카드정보 수정에 실패하였습니다." + err);
						return;
					}
				});
			}
		});
	});
	
	
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
			//bootbox.alert("카드회사목록을 불러오는데 실패하였습니다." + err);
			return;
		}
	}); */
	
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
			//bootbox.alert("카드회사목록을 불러오는데 실패하였습니다." + err);
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

	$("#btnUserSelect").click(function(){
		var params = [];
		if($("input[name='userList']:checked").length > 1){
			bootbox.alert("한명만 선택하세요.");
			return;
		}
		var name;
		var id;
		if($("input[name='userList']:checked").length < 1){
			bootbox.alert("직원을 선택해 주세요.");
			return
		}
		
		$("input[name='userList']:checked").each(function(i) {
			$table = $(this).parents("tr");
			id = $table.find("td").eq(1).text();
			name = $table.find("td").eq(2).text();
	    });
		
		if(userFlag=='srchUser'){
			$('#srchCardOwnerId').val(id);
			$('#srchCardOwner').val(name);
		}else if(userFlag=='cardUser'){
			$('#modiCardUserId').val(id);
			$('#modiCardUser').val(name);
		}else if(userFlag=='repUser'){
			$('#modiRepUserId').val(id);
			$('#modiRepUserNm').val(name);
			$('#modiRepUserId').parents('li').find('input[type="radio"]').attr('checked',true);
		}else if(userFlag=='comCardUser'){
			$('#modiComCardMgrId').val(id);
			$('#modiComCardMgrNm').val(name);
			$('#modiComCardMgrId').parents('li').find('input[type="radio"]').attr('checked',true);
		}else if(userFlag=='sec1User'){
			$('#modiSec1UserId').val(id);
			$('#modiSec1UserNm').val(name);
			$('#modiSec1UserId').parents('li').find('input[type="radio"]').attr('checked',true);
		}else if(userFlag=='sec2User'){
			$('#modiSec2UserId').val(id);
			$('#modiSec2UserNm').val(name);
			$('#modiSec2UserId').parents('li').find('input[type="radio"]').attr('checked',true);
		}
		changeRadio();
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
		url : '/rest/cardApi/getCardList/' + cardOwnCd + '/' + cardNo + '/' + cardOwnUser + '/' + cardStCd + '/' + searchTypeCd + '/' + pageNo,		
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
									+ '<td>' + card.repUserNm 		+ '</td>'
									+ '<td>' + card.sec1UserNm;
				if(card.sec2UserNm!=null && card.sec2UserNm !=''){
					dataAreaHtml += '<br/>' + card.sec2UserNm;
				}

				dataAreaHtml += '</td>'
									+ '<td>' + card.comCardMgrNm 	+ '</td>'
									//+ '<td>' + card.sStartDay 		+ '</td>'
									//+ '<td>' + card.sEndDay 		+ '</td>'
									+ '<td><button onclick="cardModify(this);" class="btnModify">수정</button></td>'
									+ '</tr>';
			});
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

//////유저목록 팝업 내용 삭제
function clearUserPop(){
	$('#userSearchList').empty();
	$('#userListForUser').val('');
	$('#userListForDept').val('')
}

function cardModify(obj){
	var index = $(obj).parents("tr").index();
	var card = aCardList[index];
	$('#modiCardNo').text(card.cardNo);
	$('#modiCardOwn').text(card.cardOwnNm);
	$('#modiCardUser').val(card.cardOwnUserNm);
	$('#modiRepUserNm').val(card.repUserNm);
	$('#modiComCardMgrNm').val(card.comCardMgrNm);
	$('#modiSec1UserNm').val(card.sec1UserNm);
	$('#modiSec2UserNm').val(card.sec2UserNm);
	
	if($('#modiRepUserNm').val()!=''){
		$('#radioRep').attr('checked', true);
	}else if($('#modiComCardMgrNm').val()!=''){
		$('#radioCom').attr('checked', true);
	}else if($('#modiSec1UserNm').val()!=''){
		$('#radioSec').attr('checked', true);
	}else{
		$('input:radio[name="radio"]').each(function(index, radio){
			$(radio).attr('checked', false);
			var id = '#'+$(radio).val()+' :input';
			$(id).attr('disabled',true);
		});
	}
	
	changeRadio();
	showModal('cardMod');	
	return false;
}


////// 유저검색이 여러개다. 어떤곳에서 버튼을 눌렀는지 pFlag로 구분
function srchUser(pFlag){
	clearUserPop();
	userFlag = pFlag;
	showModal('userListPopLayer');
	return false;
}

////// 라디오버튼 선택할 때마다 다른 라디오버튼 은 disabled 처리
function changeRadio(){
	$('input:radio[name="radio"]').each(function(index, radio){
		var id = '#'+$(radio).val()+' :input';
		if($(radio).is(':checked')){
			$(id).attr('disabled',false);
		}else{
			$(id).val('');
			$(id).attr('disabled',true);
		}
	});
}

////// 삭제버튼 누를시 <input>에 등록되어있던 user 이름과 id 삭제
function deleteUserId(obj){
	var parent = $(obj).parents('li');
	$(parent).find('input[type="text"]').val('');
	$(parent).find('input[name="radio"]').attr('checked', false);
	changeRadio();
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
			<i class="icon iconCardManage02"></i> <span>카드관리</span>
		</h2>
		<p class="location">Home > 카드관리 > 카드관리</p>
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
						<input id="srchCardOwner" type="text"/><input id="srchCardOwnerId" type="hidden"/> <a class="btnView" onclick="srchUser('srchUser');" style="display: inline-block;"><i class="icon iconView">돋보기</i></a>
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
								<th>대체상신자</th>	<!--  -->
								<th>비서</th>
								<th>공용카드관리자</th>
								<!-- <th colspan="2">카드허용기간</th> -->
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


	<!-- 카드정보수정하기 팝업 -->
	<div class="layer" id="cardMod">
		<div class="layerInner layerInner02">
			<strong class="firstTit">카드정보 수정하기</strong>
			<!-- 카드정보수정하기 -->
			<div class="long">
			<div class="btns">
				<button type="button" class="btnSave" id="btnModiSave">저장</button>
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
					<tr>
						<th>카드소유자</th>
						<td colspan="3">
							<input id="modiCardUser" type="text" class="shortInput" value=""/><input id="modiCardUserId" type="hidden" class="shortInput" value=""/> <a class="btnView" onclick="srchUser('cardUser');" style="display: inline-block;"><i class="icon iconView">돋보기</i></a>
						</td>
					</tr>
					<tr>
						<th>상위 부서장</th>
						<td colspan="3" id=""></td>
					</tr>
					<tr>
						<td colspan="4" class="radioArea">
							<ul class="choiceList">
								<li>
									<input type="radio" id="radioRep" name="radio" value="repCardYn" onclick="changeRadio();"/> <label for="choice01">대체상신자</label><br/>
									<div id="repCardYn"><input id="modiRepUserNm" type="text" class="shortInput" value=""/><input id="modiRepUserId" type="hidden" class="shortInput" value=""/> <a class="btnView" onclick="srchUser('repUser');" style="display: inline-block;"><i class="icon iconView">돋보기</i></a> <button type="button" onclick="deleteUserId(this);" class="btnDelShort">삭제</button></div>
								</li>
								<li>
									<input type="radio" id="radioCom" name="radio" value="comCardYn" onclick="changeRadio();"/> <label for="choice02">공용카드담당자(영업)</label><br/>
									<div id="comCardYn"><input id="modiComCardMgrNm" type="text" class="shortInput" value=""/><input id="modiComCardMgrId" type="hidden" class="shortInput" value=""/> <a class="btnView" onclick="srchUser('comCardUser');" style="display: inline-block;"><i class="icon iconView">돋보기</i></a> <button type="button" onclick="deleteUserId(this);" class="btnDelShort">삭제</button></div>
								</li>
								<li>
									<input type="radio" id="radioSec" name="radio" value="secCardYn" onclick="changeRadio();"/> <label for="choice03">비서</label><br/>
									<div id="secCardYn">
										<span>
											<input id="modiSec1UserNm" type="text" class="shortInput" value=""/><input id="modiSec1UserId" type="hidden" class="shortInput" value=""/> <a class="btnView" onclick="srchUser('sec1User');" style="display: inline-block;"><i class="icon iconView">돋보기</i></a> <button type="button" onclick="deleteUserId(this);" class="btnDelShort">삭제</button>
											<br/>
											<input id="modiSec2UserNm" type="text" class="shortInput" value=""/><input id="modiSec2UserId" type="hidden" class="shortInput" value=""/> <a class="btnView" onclick="srchUser('sec2User');" style="display: inline-block;"><i class="icon iconView">돋보기</i></a> <button type="button" onclick="deleteUserId(this);" class="btnDelShort">삭제</button>
										</span>
									</div>
								</li>
							</ul>
						</td>
					</tr>
				</tbody>
			</table>
			</div>
			<!-- //카드정보수정하기 -->
		</div>
		<button type="button" class="btnLayerClose" onclick="hideModal('cardMod');return false;"><img src="/images/btn_close.png"/></button>
	</div>
	<!-- //카드정보수정하기 팝업 -->