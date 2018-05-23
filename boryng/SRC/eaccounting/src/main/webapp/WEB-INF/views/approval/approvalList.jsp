<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$(document).ready(function () {
		$('.date-picker').datepicker();
		getComCdGrpId('PROD_CD', 'productCd', 'Y');

		setData("${html}", "${totalRow}");

		$("#sCalendar").val("${sDate}");
		$("#eCalendar").val("${eDate}");

		controlCheckBox();

		if ("${adminFlag}" == 'Y') {
			$("#allApproval").hide();
			$("#headArea tr th:nth-child(1)").hide();
			$("#dataArea tr td:nth-child(1)").hide();
		}

		$("#allChk").click(function() {
			if ($(this).prop("checked")) {
				$("input[name='approvalId']").each(function() {
					if (!$(this).attr('disabled')) {
						$(this).attr("checked", true);
					}
				});
			} else {
				$("input[name='approvalId']").attr("checked", false);
			}
		});
		$("#dataArea").on('click', 'a', function(e) {
			e.preventDefault();
			openCalculateForm($.trim($(this).text()), $.trim($(this).parent().prev().prev().prev().prev().prev().children().val()), 'approval');
		});
		$("#rqDeptNm").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : "/rest/deptApi/deptNameSearch/" + $("#rqDeptNm").val(),
					type : "GET",
					dataType : "json",
					success : function(data) {
						var result = data;
						response(result);
					},
					error : function(data) {
						console.log(e);
					}
				});
			}
		});
		$("#rqUserNm").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : "/rest/userApi/nameSearch/" + $("#rqUserNm").val(),
					type : "GET",
					dataType : "json",
					success : function(data) {
						var result = data;
						response(result);
					},
					error : function(data) {
						console.log(e);
					}
				});
			}
		});
	});

	function controlCheckBox() {
		$("input[name='seq']").each(function() {
			if ($(this).val() == '1') {
				$(this).parent().prev().children().attr('disabled', true);
			}
		});
		$("input[name='flag']").each(function() {
			if ($(this).val() == '1') {
				$(this).parent().prev().prev().children().attr('disabled', true);
			}
		});
	}

	function validForm() {
		var sDate = $("#sCalendar").val();
		var eDate = $("#eCalendar").val();
		var rqDeptCd = $("#rqDeptCd").val();
		var rqDeptNm = $("#rqDeptNm").val();
		var rqUserId = $("#rqUserId").val();
		var rqUserNm = $("#rqUserNm").val();

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
		if (rqDeptCd == '부서코드') {
			// placeholder
			$("#inputRqDeptCd").val('all');
		} else if (rqDeptCd.length != 5) {
			bootbox.alert('부서코드가 잘못 입력되었습니다.');
			return false;
		} else {
			$("#inputRqDeptCd").val($.trim($("#rqDeptCd").val()));
		}
		if (rqDeptNm == '부서명') {
			// placeholder
			$("#inputRqDeptNm").val('all');
		} else {
			$("#inputRqDeptNm").val($.trim($("#rqDeptNm").val()).split('/').join('`'));
		}
		if (rqUserId == '사번') {
			// placeholder
			$("#inputRqUserId").val('all');
		} else if (rqUserId.length != 5) {
			bootbox.alert('사번이 잘못 입력 되었습니다.');
			return false;
		} else {
			$("#inputRqUserId").val($.trim($("#rqUserId").val()));
		}
		if (rqUserNm == '상신자명') {
			// placeholder
			$("#inputRqUserNm").val('all');
		} else {
			$("#inputRqUserNm").val($.trim($("#rqUserNm").val()));
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
			url : '/rest/approvalApi/approvalList/' + $("#sDate").val() + '/' + $("#eDate").val() + '/' + $("#productCd").val() + '/' + $("#inputRqDeptCd").val() + '/' + $("#inputRqDeptNm").val() + '/' + $("#inputRqUserId").val() + '/' + $("#inputRqUserNm").val() + '/' + pageNo,
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
				controlCheckBox();
				if ("${adminFlag}" == 'Y') {
					$("#dataArea tr td:nth-child(1)").hide();
				}
			},
			error : function(e) {
				console.log(e);
			}
		});
	}

	function approval(){
		var reqCnt = 0;
		var sCnt = 0;

		$("input:checked[name='approvalId']").each(function(idx) {
			if ($(this).attr('disabled') != 'disabled') {
				reqCnt++;
			}
		});

		if (reqCnt == 0) {
			bootbox.alert('승인할 데이터를 선택해주세요.');
			return false;
		}

		bootbox.confirm("일괄 승인 하시겠습니까?", function(result) {
			if (result) {
				$('.loading').css('display', 'block');
				$("input:checked[name='approvalId']").each(function(idx) {
					if ($(this).attr('disabled') != 'disabled') {
						$.ajax({
							type : 'PUT',
							url : '/rest/approvalApi/approval/' + $(this).val() + '/' + $(this).parent().next().children().val() + '/N/N/N',
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
					}
				});
				$('.loading').css('display', 'none');
			}
			bootbox.alert(reqCnt + '건 중 ' + sCnt + ' 건이 승인 되었습니다.', function () {
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
			<i class="icon iconDoDoc"></i>
			<span>결재할 문서</span>
		</h2>
		<p class="location">Home > 결재할 문서 > 결재할 문서</p>
	</div>
	<!-- 타이틀 E -->

	<!-- 검색 조건 S -->
	<div class="srchCondition">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<form id="frm" name="frm">
				<input type="hidden" id="sDate" name="sDate" value="" />
				<input type="hidden" id="eDate" name="eDate" value="" />
				<input type="hidden" id="inputRqDeptCd" name="inputRqDeptCd" value="" />
				<input type="hidden" id="inputRqDeptNm" name="inputRqDeptNm" value="" />
				<input type="hidden" id="inputRqUserId" name="inputRqUserId" value="" />
				<input type="hidden" id="inputRqUserNm" name="inputRqUserNm" value="" />
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
							<span class="inputTit">제품군/명</span>
							<select name="productCd" id="productCd" class="customSelect">
							</select>
						</li>
						<li class="odd">
							<span class="inputTit">상신자부서</span>
							<input type="text" id="rqDeptCd" placeholder="부서코드">
							<input type="text" id="rqDeptNm" placeholder="부서명">
						</li>
						<li class="even">
							<span class="inputTit">상신자명</span>
							<input type="text" id="rqUserId" placeholder="사번">
							<input type="text" id="rqUserNm" placeholder="상신자명">
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
		<strong class="tit">결재할 리스트<em id="totalRow"></em>건</strong>
		<div class="innerBox">
			<div class="btns">
				<button type="button" id="allApproval" class="btnApproval" onclick="javascript:approval(); return false;">승인</button>
			</div>
			<div class="scrollTable" id="scrollTable">
				<div class="inner">
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
							<col width="" />
						</colgroup>
						<thead id="headArea">
							<tr>
								<th><input type="checkbox" id="allChk" name="allChk" /></th>
								<th style="display: none;">SEQ</th>
								<th>사용일</th>
								<th>부서</th>
								<th>상신자</th>
								<th>결재문서번호</th>
								<th>계정구분</th>
								<th>세부계정</th>
								<th>건수</th>
								<th>상신일</th>
								<th>상호</th>
								<th>총사용액</th>
								<th>제품명</th>
							</tr>
						</thead>
						<tbody id="dataArea">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="pagenationSection"></div>
	<!-- 검색 내역 E -->
</div>