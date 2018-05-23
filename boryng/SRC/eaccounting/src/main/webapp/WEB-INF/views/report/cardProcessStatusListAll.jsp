<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	function makeHtml(list) {
		var html = '';

		if (list.length == 0) {
			html = '<tr><td colspan="21">검색된 데이터가 없습니다.</td></tr>';
		} else {
			$(list).each(
					function(idx, card) {
						html += '<tr>' + '<td>'
								+ (Number(idx) + 1)
								+ '</td>'
								+ '<td>'
								+ checkNull(card.approvalStatusCd)
								+ '</td>'
								+ '<td></td>'
								+ '<td>'
								+ checkNull(card.cardName)
								+ '</td>'
								+ '<td>'
								+ checkNull(card.cardNum)
								+ '</td>'
								+ '<td>'
								+ dateFormat(checkNull(card.authDate))
								+ '</td>'
								+ '<td>'
								+ timeFormat(checkNull(card.authTime))
								+ '</td>'
								+ '<td>'
								+ checkNull(card.authNum)
								+ '</td>'
								+ '<td style="text-align:left;">'
								+ checkNull(card.mercName)
								+ '</td>'
								+ '<td>'
								+ checkNull(card.mercSaupNo)
								+ '</td>'
								+ '<td style="text-align:right;">￦'
								+ Number(checkNull(card.requestAmount))
										.toLocaleString().split(".")[0]
								+ '</td>' + '<td>' + checkNull(card.deptNm)
								+ '</td>' + '<td>' + checkNull(card.ownUserNm)
								+ '</td>' + '<td style="text-align:left;">'
								+ checkNull(card.mercAddr) + '</td>' + '<td>'
								+ checkNull(card.mccName) + '</td>' + '<td>'
								+ checkNull(card.accountNm) + '</td>' + '<td>'
								+ checkNull(card.account2Nm) + '</td>'
								+ '<td style="text-align:left;">'
								+ checkNull(card.details) + '</td>' + '<td>'
								+ checkNull(card.financeNo) + '</td>' + '<td>'
								+ checkNull(card.junpyoStCd) + '</td>'
								+ '<td></td>' + '</tr>';

					});
		}

		return html;
	}

	function validForm() {

		if ($('#startAuthDate').val() == '') {
			bootbox.alert('시작일을 입력하십시오.');
			$('#startAuthDate').focus();
			return false;
		}
		if ($('#endAuthDate').val() == '') {
			bootbox.alert('종료일을 입력하십시오.');
			$('#endAuthDate').focus();
			return false;
		}
		return true;
	}

	function emptyIsAll(str) {
		if (jQuery.trim(str) == '')
			return 'all'
		return str;
	}

	function getReportList(search, pageNo) {
		if (!validForm()) {
			return false;
		}

		if (pageNo == null)
			pageNo = 1;

		var startAuthDate = $("#startAuthDate").val().replace(/-/g, '');
		var endAuthDate = $("#endAuthDate").val().replace(/-/g, '');
		var approvalStatusCd = emptyIsAll($("#approvalStatusCd").val());
		var financeNo = emptyIsAll($("#financeNo").val());
		var deptCd = emptyIsAll($("#deptCd").val());
		var deptNm = emptyIsAll($("#deptNm").val());
		var ownUserNm = emptyIsAll($("#ownUserNm").val());
		var mercName = emptyIsAll($("#mercName").val());

		var params = {
			"startAuthDate" : startAuthDate,
			"endAuthDate" : endAuthDate,
			"approvalStatusCd" : approvalStatusCd,
			"financeNo" : financeNo,
			"deptCd" : deptCd,
			"deptNm" : deptNm,
			"ownUserNm" : ownUserNm,
			"mercName" : mercName
		};

		deptNm = encodeURIComponent(encodeURIComponent(deptNm)); // 한글 때문에 한번, '/' 때문에 한번

		$.ajax({
			type : 'GET',
			url : '/rest/reportApi/cardProcessStatusListAll/' + startAuthDate
					+ '/' + endAuthDate + '/' + approvalStatusCd + '/'
					+ financeNo + '/' + deptCd + '/' + deptNm + '/' + ownUserNm
					+ '/' + mercName + '/' + pageNo,
			dataType : "json",
			beforeSend : function() {
				$('.loading').css("display", "block");
			},
			complete : function() {
				$('.loading').css("display", "none");
			},
			success : function(jsonData) {
				//$('#reportDataArea').html(jsonData.cardProcessListHtml);
				$('#reportDataArea').html(makeHtml(jsonData.cardProcessList));
				$('#totalAmount').text(
						'￦'
								+ Number(checkNull(jsonData.totalAmount))
										.toLocaleString().split(".")[0]);
				$('#allAmount').text(
						'￦'
								+ Number(checkNull(jsonData.allAmount))
										.toLocaleString().split(".")[0]);
				$('#totalRow').text(
						Number(checkNull(jsonData.totalRow)).toLocaleString()
								.split(".")[0]);

				if (search) {
					$("#pagenationSection").pagination({
						items : jsonData.totalRow,
						itemsOnPage : 10,
						cssStyle : 'light-theme',
						hrefTextPrefix : "javascript:getReportList(false,",
						hrefTextSuffix : ");"
					});
				}
			},
			error : function(err) {
				bootbox.alert("처리중 오류가 발생하였습니다." + err);
				$('#reportDataArea').html(
						'<tr><td colspan="21">처리중 오류가 발생하였습니다.</td></tr>');
				$('#totalAmount').text('0');
				$('#allAmount').text('0');
				$('#totalRow').text('0');
			}
		});

		/* 	var params = {
		 "startAuthDate"		:	startAuthDate
		 ,	"endAuthDate"		:	endAuthDate
		 ,	"approvalStatusCd"	:	approvalStatusCd
		 ,	"financeNo"	:	financeNo
		 ,	"deptCd"	:	deptCd
		 ,	"deptNm"	:	deptNm
		 ,	"ownUserNm"	:	ownUserNm
		 ,	"mercName"	:	mercName				
		 };

		 $(location).attr('href','/report/cardProcessStatusListAll?' + getSerialize()); */
	}

	function getSerialize() {
		var startAuthDate = $("#startAuthDate").val().replace(/-/g, '');
		var endAuthDate = $("#endAuthDate").val().replace(/-/g, '');
		var approvalStatusCd = emptyIsAll($("#approvalStatusCd").val());
		var financeNo = emptyIsAll($("#financeNo").val());
		var deptCd = emptyIsAll($("#deptCd").val());
		var deptNm = emptyIsAll($("#deptNm").val());
		var ownUserNm = emptyIsAll($("#ownUserNm").val());
		var mercName = emptyIsAll($("#mercName").val());

		var params = "startAuthDate=" + startAuthDate + "&endAuthDate="
				+ endAuthDate + "&approvalStatusCd=" + approvalStatusCd
				+ "&financeNo=" + financeNo + "&deptCd=" + deptCd + "&deptNm="
				+ deptNm + "&ownUserNm=" + ownUserNm + "&mercName=" + mercName;

		return params;
	}

	$(document).ready(
			function() {

				//getReportList();
				$(".customSelect").selectbox();

				$('#btnSrch').click(function() {
					getReportList(true, 1);
				})

				$('#startAuthDate').change(
						function() {
							var val = $(this).val().replace(/-/g, '');
							var date = new Date();
							date.setDate(date.getDate() - 60);
							date = date.getFullYear() + ""
									+ numberPad((date.getMonth() + 1), 2) + ""
									+ numberPad(date.getDate(), 2);

							if (date > val) {
								bootbox.alert('60일 이전은 선택할 수 없습니다.',
										function() {
											$('#startAuthDate').val('');
											$('#startAuthDate').focus();
										});
							}
						});

			});
</script>


<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<!-- 검색조건 -->
<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2>
			<i class="icon iconReport"></i><span>전체 카드처리현황</span>
		</h2>
		<p class="location">Home > 리포트 > 전체 카드처리현황</p>
	</div>

	<form id="form1" method="GET" action="/report/cardProcessStatusListAll">
		<!-- 검색조건 -->
		<div class="srchCondition twoLine">
			<strong class="tit">검색 조건</strong>
			<div class="innerBox">
				<div class="left">
					<ul class="allSearch">
						<li class="allSearchCate"><span class="inputTit">사용 기간</span>
							<input type="text" id="startAuthDate" class="date-picker"
							value='<fmt:parseDate value="${defaultWorkduration.DEFAULT_START_DATE}" var="dateFmt" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd"/>' />
							<label for="startAuthDate" class="btnCalSmall"><i
								class="icon iconCalSmall">달력</i></label> ~ <input type="text"
							id="endAuthDate" class="date-picker"
							value='<fmt:parseDate value="${defaultWorkduration.DEFAULT_END_DATE}" var="dateFmt" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd"/>' />
							<label for="endAuthDate" class="btnCalSmall"><i
								class="icon iconCalSmall">달력</i></label></li>
						<li><span class="inputTit">처리상태</span> <select
							class="customSelect" id="approvalStatusCd">
								<option value="all">전체</option>
								<option value="AAAA">미상신</option>
								<option value="ZZZZ">상신제외</option>
								<option value="NREQ">상신중</option>
								<option value="NING">결재중</option>
								<option value="NREJ">반려</option>
								<option value="NAPR">결재완료</option>
								<option value="SR">심포지움요청</option>
								<option value="SING">심포지움처리중</option>
								<option value="SAPR">심포지움처리완료</option>
								<option value="EAPR">전표완료</option>
						</select></li>
						<li class="allSearchCate1"><span class="inputTit">회계전표번호</span>
							<input id="financeNo" type="text" /></li>
						<li class="SearchCate-first"><span class="inputTit">부서코드</span>
							<input id="deptCd" type="text" value="${deptCd}" /></li>
						<li class="SearchCate-pad"><span class="inputTit">부서명</span>
							<input id="deptNm" type="text" value="${deptNm}" /></li>
						<li class="SearchCate-pad"><span class="inputTit">소유자</span>
							<input id="ownUserNm" type="text" /></li>
						<li class="SearchCate-pad"><span class="inputTit">상호</span> <input
							id="mercName" type="text" /></li>
					</ul>
				</div>
				<div class="right">
					<button id="btnSrch" type="button" class="btnSrch">검색</button>
				</div>
			</div>
		</div>
		<!-- //검색조건 -->
	</form>

	<!-- 검색내역 -->
	<div class="srchList">
		<strong class="tit">검색 내역<em id="totalRow">0</em>건,
			&nbsp;&nbsp;&nbsp; 검색총액<em id="allAmount">0</em></strong>


		<!-- <p class="btnDow"><button type="button" class="btnSrch">다운로드</button></p> -->

		<div class="scrollTable" id="scrollTable">
			<div class="reportInner">
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
						<col width="" />
						<col width="" />
						<col width="" />
					</colgroup>
					<thead>
						<tr>
							<th>NO.</th>
							<th>처리상태</th>
							<th>결재상태</th>
							<th>카드회사</th>
							<th>카드번호</th>
							<th>사용일</th>
							<th>승인시간</th>
							<th>승인번호</th>
							<th>상호</th>
							<th>사업자번호</th>
							<th>합계금액</th>
							<th>부서</th>
							<th>소유자</th>
							<th>주소</th>
							<th>업종</th>
							<th>계정구분</th>
							<th>세부계정</th>
							<th>사용내역</th>
							<th>회계전표번호</th>
							<th>전표상태</th>
							<th>결재문서번호</th>
						</tr>
					</thead>
					<tbody id="reportDataArea">
						<c:forEach var="card" items="${cardProcessList}">
							<tr>
								<td></td>
								<td>${card.approvalStatusCd}</td>
								<td></td>
								<td>${card.cardName}</td>
								<td>${card.cardNum}</td>
								<td>${card.authDate}</td>
								<td>${card.authTime}</td>
								<td>${card.authNum}</td>
								<td style="text-align: left;">${card.mercName}</td>
								<td>${card.mercSaupNo}</td>
								<td style="text-align: right;">￦ ${card.requestAmount}</td>
								<td></td>
								<td></td>
								<td style="text-align: left;">${card.mercAddr}</td>
								<td>${card.mercSaupNo}</td>
								<td>${card.accountNm}</td>
								<td>${card.account2Nm}</td>
								<td>${card.details}</td>
								<td>${card.financeNo}</td>
								<td>${card.junpyoStCd}</td>
								<td></td>
							</tr>

						</c:forEach>
						<tr>
							<td colspan="21">검색하여 주시기 바랍니다.</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td class="total">총액</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td id="totalAmount" style="text-align: right;">${totalAmount}</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
	<div id="pagenationSection"></div>
	<!-- //검색내역 -->
</div>