<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$(document).ready(function () {
		makeYearMonth("year", "month", "Y");	

		$("#allChk").click(function() {
			if ($(this).prop("checked")) {
				$("input[name='approvalId']").attr("checked", true);
			} else {
				$("input[name='approvalId']").attr("checked", false);
			}
		});

		getList(null, true);
	});

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
			$("#authDate").val('all');
			return true;
		} else {
			$("#authDate").val($.trim($("#year").val()) + '' + $.trim($("#month").val()));
			return true;
		}
	}

	function getList(pageNo, flag){
		if (!validForm()) {
			return false;
		}
		if (pageNo == null) {
			pageNo = 1;
		}

		$.ajax({
			type : 'GET',
			url : '/rest/approvalRqApi/cancelSymposiumList/' + $("#authDate").val() + '/' + pageNo,
			dataType : "json",
			beforeSend : function() {
				$('.loading').css('display', 'block');
			},
			complete : function() {
				$('.loading').css('display', 'none');
			},
			success : function(data) {
				if (flag) {
					setData(data.html, data.totalRow);
				} else {
					$("#totalRow").html(data.totalRow);
					$("#dataArea").html(data.html);
				}
			},
			error : function(e) {
				console.log(e);
			}
		});
	}

	function cancelSymposium(){
		var reqCnt = 0;
		var sCnt = 0;

		$("input:checked[name='approvalId']").each(function(idx) {
			reqCnt++;
		});

		if (reqCnt == 0) {
			bootbox.alert('요청취소할 데이터를 선택해주세요.');
			return false;
		}

		bootbox.confirm("일괄 요청취소 하시겠습니까?", function(result) {
			if (result) {
				$('.loading').css('display', 'block');
				$("input:checked[name='approvalId']").each(function(idx) {
					$.ajax({
						type : 'DELETE',
						url : '/rest/approvalRqApi/cancelSymposium/' + $(this).val(),
						contentType: 'application/json',
						dataType : "json",
						async : false,
						success : function(data) {
							if (data.code == 'S') {
								sCnt++;
							}
						},
						error : function(e) {
							console.log(e);
						}
					});
				});
				$('.loading').css('display', 'none');
			}
			bootbox.alert(reqCnt + '건 중 ' + sCnt + ' 건이 요청취소 되었습니다.', function () {
				location.reload();
			});
		});
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
			<span>심포지움 요청취소</span>
		</h2>
		<p class="location">Home > 결재상신 > 심포지움 요청취소</p>
	</div>
	<!-- 타이틀 E -->

	<!-- 검색 조건 S -->
	<div class="srchCondition">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<form id="frm" name="frm">
				<input type="hidden" id="authDate" name="authDate" value="" />
				<div class="left">
					<ul>
						<li class="odd">
							<span class="inputTit">카드승인년월</span>
							<select name="year" id="year" class="customSelect">
							</select>
							<select name="month" id="month" class="customSelect">
							</select>
						</li>
					</ul>
				</div>
				<div class="right">
					<button type="button" class="btnSrch" onclick="javascript:getList(null, true); return false;">검색</button>
				</div>
			</form>
		</div>
	</div>
	<!-- 검색 조건 E -->

	<!-- 검색 내역 S -->
	<div class="srchList">
		<strong class="tit">요청취소 리스트<em id="totalRow"></em>건</strong>
		<div class="innerBox">
			<div class="btns">
				<button type="button" class="btnRoundRobin" onclick="javascript:cancelSymposium(); return false;">요청취소</button>
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
					<col width="" />
					<col width="" />
				</colgroup>
				<thead id="headArea">
					<tr>
						<th><input type="checkbox" id="allChk" name="allChk" /></th>
						<th>사용일</th>
						<th>상호</th>
						<th>합계금액</th>
						<th>담당자</th>
						<th>예산부서</th>
						<th>계정구분</th>
						<th>제품군/명</th>
						<th>거래처</th>
						<th>사용내역</th>
					</tr>
				</thead>
				<tbody id="dataArea">
				</tbody>
			</table>
		</div>
	</div>
	<div id="pagenationSection"></div>
	<!-- 검색 내역 E -->
</div>