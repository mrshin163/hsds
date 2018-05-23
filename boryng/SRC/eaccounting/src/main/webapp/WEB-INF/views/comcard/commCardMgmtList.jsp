<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
var flag = "";
var userInfo = [];
var index = "";
jQuery(document).ready(function(){
	
	makeYearMonth('year', 'month', 'Y');	
	getCommCardList(null, true)
	
/* 	$("#pagenationSection").pagination({
	        items: ${totalRow},
	        itemsOnPage: 10,
	        cssStyle: 'light-theme',
	       	hrefTextPrefix: "javascript:getCommCardList(",
			hrefTextSuffix: ");"
	   }); */
	/* 사용자 검색 부서명, 이름 */
	
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
	})


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
			//userInfo.push({"userName" : name, "userId" : id});
			//var deptName = $table.find("td").eq(3).text();
			//var titleAliasName = $table.find("td").eq(4).text();			
			//params.push({"companyCode" : "PHAM", "userId" : $(this).val(), "authId" : "R99"});
	    });
		
		if(flag=="getUserInfo"){
			$('#searchActUserNm').val(name);
			hideModal('userListPopLayer');
		}else if(flag=="updateActUser"){
			
			var params;
			var tr = $('#dataArea tr')[index];
			
			var actUserId 		= $(tr).find("td").eq(8).text();
	        var authNum 		= $(tr).find("td").eq(4).text();
	        var cardNo 			= $(tr).find("td").eq(3).text();
	        var authDate 		= $(tr).find("td").eq(0).text().replace(/-/g,'');
	        var georaeStat 		= $(tr).find("td").eq(6).text();
	        var requestAmount 	= $(tr).find("td").eq(2).text().replace(/,/g,'').replace(/\\/g,"");
	        var georaeColl 		= $(tr).find("td").eq(7).text();
	        
			params = {	"actUserId" 	: actUserId,
						"authDate" 		: authDate,
						"requestAmount" : requestAmount,
						"cardNo" 		: cardNo,
						"authNum" 		: authNum,
						"georaeStat" 	: georaeStat,
						"georaeColl" 	: georaeColl
			};
			
			bootbox.confirm(name+"님이 실 사용자로 등록 됩니다. 계속 하시겠습니까?", function(result){
				if(result){
					//////  등록된 사용자가 없으면 바로 업데이트
					if(actUserId=='null' || actUserId==null || actUserId==""){
						params.actUserId = id;							

						jQuery.ajax({
							type : 'POST',
							url : '/rest/commCardApi/setActUser',
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
								if(jsonData.result =='S'){
									bootbox.alert(jsonData.message);
									$(tr).find("td").eq(8).text(id);
									$(tr).find("td").eq(5).empty();
									$(tr).find("td").eq(5).append(name+' ').append('<div class="btnView" onclick="updateActUser(this);" style="display: inline-block;"><i class="icon iconView">돋보기</i></div>');
								}else if(jsonData.result =='F'){
									bootbox.alert(jsonData.message);
								}
								hideModal('userListPopLayer');
							},
							error : function(err) {
								bootbox.alert("처리중 장애가 발생하였습니다." + err);
								return;
							}
						});			
					
					}else{
						//////  진행중인 문서가 있는지 검색
						jQuery.ajax({
							type : 'GET',
							url : '/rest/commCardApi/getCardApprovalCount/'+actUserId+'/'+authNum+'/'+cardNo+'/'+authDate+'/'+georaeStat+'/'+requestAmount+'/'+georaeColl,
							dataType : "json",
							contentType: 'application/json',
							beforeSend:function(){
								$('.loading').css("display", "block");
						    },
						    complete:function(){
								$('.loading').css("display", "none");
						    },
							success : function(jsonData) {
								//////  진행중인 문서가 없으면 업데이트
								if(jsonData==0){
									params.actUserId = id;
									jQuery.ajax({
										type : 'POST',
										url : '/rest/commCardApi/setActUser',
										dataType : "json",
										contentType: 'application/json',
										data : JSON.stringify(params),
										success : function(jsonData) {
											if(jsonData.result =='S'){
												bootbox.alert(jsonData.message);
												$(tr).find("td").eq(5).empty();
												$(tr).find("td").eq(5).append(name+' ').append('<div class="btnView" onclick="updateActUser(this);" style="display: inline-block;"><i class="icon iconView">돋보기</i></div>');
												$(tr).find("td").eq(8).text(id);
											}else if(jsonData.result =='F'){	
												bootbox.alert(jsonData.message);
											}
											hideModal('userListPopLayer');
										},
										error : function(err) {
											bootbox.alert("처리중 장애가 발생하였습니다." + err);
											return;
										}
									});	
								}else{
									bootbox.alert("해당 사용자로 진행중인 문서가 있습니다.");
									return;
								}
							},
							error : function(err) {
								bootbox.alert("처리중 장애가 발생하였습니다." + err);
								return;
							}
						});//ajax
					}//if
				}//if result
			})// confirm			
		}//if		
	});	//btn click	
});//ready


function userSearch(userNameSearch, deptNameSearch){
	if(userNameSearch == "") userNameSearch = "all";
	if(deptNameSearch == "") deptNameSearch = "all"; 
	
	jQuery.ajax({
		type : 'GET',
		url : '/rest/userApi/userList/' + userNameSearch + '/' + deptNameSearch + "/0",
		dataType : "json",
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
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

function validForm() {
	var year = $("#year").val();
	var month = $("#month").val();

	if (year == '' && month != '') {
		bootbox.alert('년, 월은 동시에 선택해야 합니다.');
		return false;
	} else if (year != '' && month == '') {
		bootbox.alert('년, 월은 동시에 선택해야 합니다.');
		return false;
	} else if (year == '' && month == '') {
		$("#rqDate").val('all');
		return true;
	} else {
		$("#rqDate").val($.trim($("#year").val()) + '' + $.trim($("#month").val()));
		return true;
	}
}

function checkEmpty(str, param){
	if(str=='') return param;
	return str;	
}

function getCommCardList(pageNo, search){
	if (!validForm()) {
		return false;
	}
	if (pageNo == null) {
		pageNo = 1;
	}

	var authDate	= checkEmpty($("#year").val() + $("#month").val(), 'all');
	var searchType	= checkEmpty($('#searchType').val(), 'all');
	var actUser		= checkEmpty($('#searchActUserNm').val(), 'all');
	
	var params		= {	"authDate"		:	authDate,
						"searchType"	:	searchType,
						"actUser"		:	actUser,
						"pageNo"		:	pageNo };
	
	$.ajax({
		type : 'GET',
		url : '/rest/commCardApi/getCardAQList/'+authDate+'/'+searchType+'/'+actUser+'/'+pageNo,		
		dataType : "json",
		contentType: 'application/json',
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			//setData(data.html, data.totalRow);
			var dataAreaHtml = "";
			$.each(jsonData.commCardList, function(key, commCard) {
				var actUserNm = commCard.actUserNm==null?"":commCard.actUserNm;
				dataAreaHtml += '<tr>'
						+ '<td class="authDate">' 	+ dateFormat(commCard.authDate) + '</td>'
						+ '<td style="text-align: left;text-overflow:ellipsis;white-space:nowrap;word-wrap:normal;">' + commCard.mercName  + '</td>'
						+ '<td class="currency"  style="padding-right:10px;">\\'	+ Number(commCard.requestAmount).toLocaleString().split(".")[0] + '</td>'
						+ '<td class="cardNum">' 	+ commCard.cardNo +'</td>'
						+ '<td class="authNum">' 	+ commCard.authNum +'</td>'
						+ '<td style="text-align:center;">' + actUserNm +' <div class="btnView" onclick="updateActUser(this);" style="display: inline-block;"><i class="icon iconView">돋보기</i></div></td>'
						+ '<td style="display:none;">' + commCard.georaeStat + '</td>'
						+ '<td style="display:none;">' + commCard.georaeColl + '</td>'
						+ '<td style="display:none;">' + commCard.actUserId + '</td>'
						+ '</tr>';
				}
			);
			$('#dataArea').html(dataAreaHtml);
			$('#totalAmount').text("\\"+Number(jsonData.totalAmount).toLocaleString().split(".")[0]);
			if(search){
			    $("#pagenationSection").pagination({
			        items: jsonData.totalRow,
			        itemsOnPage: 10,
			        cssStyle: 'light-theme',
			       	hrefTextPrefix: "javascript:getCommCardList(",
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

function togleSearchEmp(){
	if($('#searchType').val() =='1'){
		$('#inputUserNameLayer').css("display","");
	}else{
		$('#inputUserNameLayer').css("display","none");
	}

}

function getActUserIdNm(){
	flag = 'getUserInfo';
	showModal('userListPopLayer');
	return false;
}

function updateActUser(obj){
	index = $(obj).parents("tr").index();
	flag = 'updateActUser';
	showModal('userListPopLayer');
	return false;
}

</script>

<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<div class="content">
	<!-- 타이틀 S -->
	<div class="titArea subTitArea">
		<h2>
			<i class="icon iconIngDoc"></i>
			<span>공용카드 결재상신요청</span>
		</h2>
		<p class="location">Home > 결재상신 > 공용카드 결재상신요청</p>
	</div>
	<!-- 타이틀 E -->

	<!-- 검색 조건 S -->
	<div class="srchCondition">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<form id="frm" name="frm">
				<input type="hidden" id="rqDate" name="rqDate" value="" />
				<div class="left">
					<ul>
						<li class="odd">
							<span class="inputTit">사용년월</span>
							<select name="year" id="year" class="customSelect">
							</select>
							<select name="month" id="month" class="customSelect">
							</select>
						</li>
						<li class="even">
							<span class="inputTit">검색구분</span>
							<select name="searchType" id="searchType" class="customSelect" onchange="togleSearchEmp();">
								<option value="">전체</option>
								<option value="1">실사용자 지정</option>
								<option value="2">실사용자 미지정</option>
							</select>
						</li>
						<li class="odd" id="inputUserNameLayer" style="display:none;">
							<span class="inputTit">실사용자</span>
							<input id="searchActUserNm" type="text" value=""/>
							<div class="btnView" onclick="getActUserIdNm();" style="display: inline-block;"><i class="icon iconView">돋보기</i></div>
							<!-- <a href="" class="btnReg" onclick="showModal('userListPopLayer');return false;">직원검색</a> -->
						</li>
						
						
					</ul>
				</div>
				<div class="right">
					<button type="button" class="btnSrch" onclick="getCommCardList(null, true); return false;">검색</button>
				</div>
			</form>
		</div>
	</div>
	<!-- 검색 조건 E -->

	<!-- 검색 내역 S -->
	<div class="srchList">
		<div id="loader" style="position: absolute; display: none; top:45%; left:50%;"><img src="/images/admin/ajax-loader.gif"></div>
			<strong class="tit">공용카드 결재상신요청<em id="totalRow"><fmt:formatNumber value="${totalRow}" groupingUsed="true" /></em>건</strong>
			<div class="innerBox" id="dataBox">
				<div class="btns">
					
				</div>
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
							<th>사용일</th>
							<th>상호</th>
							<th>총액</th>
							<th>카드번호</th>
							<th>승인번호</th>
							<th>사용자</th>
						</tr>
					</thead>
					<tbody id="dataArea">
						<c:forEach var="commCardList" items="${commCardList}" varStatus="status">
							<tr>
								<td class="authDate">${commCardList.authDate}</td>
								<td style="text-align: left;text-overflow:ellipsis;white-space:nowrap;word-wrap:normal;">${commCardList.mercName}</td>
								<td class="currency">${commCardList.requestAmount}</td>
								<td class="cardNum">${commCardList.cardNo}</td>
								<td class="authNum">${commCardList.authNum}</td>
								<td style="text-align:center;">${commCardList.actUserNm}
									<div class="btnView" onclick="updateActUser(this);" style="display: inline-block;"><i class="icon iconView">돋보기</i></div>
								</td>
								<td style="display:none;">${commCardList.georaeStat}</td>
								<td style="display:none;">${commCardList.georaeColl}</td>
								<td style="display:none;">${commCardList.actUserId}</td>
							</tr>
						</c:forEach>				
					</tbody>
					<tfoot>
						<tr>
							<td colspan="2" class="total" style="text-align: center;">총계</td>
							<!--<td class="currency"><fmt:formatNumber value="${amtAmountTotal}" type="currency" groupingUsed="true" /></td>
							<td class="currency"><fmt:formatNumber value="${vatAmountTotal}" type="currency" groupingUsed="true" /></td>  -->
							<td class="currency" id="totalAmount" style="text-align:right;padding-right:10px;"><fmt:formatNumber value="" type="currency" groupingUsed="true" /></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tfoot>
				</table>
			</div>
	</div>
	<div id="pagenationSection"></div>
	<!-- 검색 내역 E -->
</div>