<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$(document).ready(function () {
		$('.date-picker').datepicker();
		$(".customSelect").selectbox();

		setData("${html}", "${totalRow}");

		$("#sCalendar").val("${sDate}");
		$("#eCalendar").val("${eDate}");

		$("#dataArea").on('click', 'a', function(e) {
			e.preventDefault();
			openCalculateForm($.trim($(this).text()), null, 'approvalComplete');
		});
	});

	function validForm() {
		var sDate = $("#sCalendar").val();
		var eDate = $("#eCalendar").val();

		if (sDate == '' && eDate != '') {
			bootbox.alert('시작일이 입력되지 않았습니다.');
			return false;
		} else if (sDate != '' && eDate == '') {
			bootbox.alert('종료일이 입력되지 않았습니다.');
			return false;
		} else if (sDate == '' && eDate == '') {
			$("#sDate").val('all');
			$("#eDate").val('all');
		} else if (Number(sDate.split('-').join('')) > Number(eDate.split('-').join(''))) {
			bootbox.alert('종료일이 시작일보다 빠릅니다.');
			return false;
		} else {
			$("#sDate").val(sDate);
			$("#eDate").val(eDate);
		}
		return true;
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
			url : '/rest/approvalApi/approvalCompleteList/' + $("#sDate").val() + '/' + $("#eDate").val() + '/' + $("#approvalStatusCd").val() + '/' + pageNo,
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
</script>

<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<div class="content">
	<!-- 타이틀 S -->
	<div class="titArea subTitArea">
		<h2>
			<i class="icon iconDoDoc"></i>
			<span>완료 문서</span>
		</h2>
		<p class="location">Home > 결재할 문서 > 완료 문서</p>
	</div>
	<!-- 타이틀 E -->

	<!-- 검색 조건 S -->
	<div class="srchCondition">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<form id="frm" name="frm">
				<input type="hidden" id="sDate" name="sDate" value="" />
				<input type="hidden" id="eDate" name="eDate" value="" />
				<div class="left">
					<ul>
						<li class="odd">
							<span class="inputTit">카드사용일</span>
							<input type="text" id="sCalendar" class="date-picker" />
							<label for="sCalendar" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>
							&nbsp;~&nbsp;
							<input type="text" id="eCalendar" class="date-picker" />
							<label for="eCalendar" class="btnCalSmall"><i class="icon iconCalSmall">달력</i></label>
						</li>
						<li class="even">
							<span class="inputTit">결재상태</span>
							<select name="approvalStatusCd" id="approvalStatusCd" class="customSelect">
								<option value="all">전체</option>
								<option value="EAPR">전표완료</option>
								<option value="NAPR">결재완료</option>
								<option value="NREJ">반려</option>
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
		<strong class="tit">결재 리스트<em id="totalRow"></em>건</strong>
		<div class="innerBox">
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
				</colgroup>
				<thead>
					<tr>
						<th>사용일</th>
						<th>결재일</th>
						<th>부서</th>
						<th>상신자</th>
						<th>결재문서번호</th>
						<th>계정구분</th>
						<th>건수</th>
						<th>총사용액</th>
						<th>결재상태</th>
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