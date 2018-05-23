<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(document).ready(function () {
		$(".customSelect").selectbox();

		$("#authId").change(function () {
			getList(null, true);
		});

		$("#btnUserSelect").click(function () {
			insertUserAuth();
		});

		$("#userListPopLayer input[name='allChk']").click(function() {
			if ($(this).prop("checked")) {
				$("input[name='userList']").each(function() {
					$(this).attr("checked", true);
				});
			} else {
				$("input[name='userList']").attr("checked", false);
			}
		});

		$("#userAuthTitleArea input[name='allChk']").click(function() {
			if ($(this).prop("checked")) {
				$("input[name='userId']").each(function() {
					$(this).attr("checked", true);
				});
			} else {
				$("input[name='userId']").attr("checked", false);
			}
		});

		getList(null, true);
	});

	function initPopup () {
		$("#userListForDept").val('');
		$("#userListForuser").val('');
		$("#userListPopLayer input[name='allChk']").attr("checked", false);
		$("#userSearchList").html('<tr><td colspan="5" style="text-align: center;">부서명과 성명으로 검색하여 주십시요.</td></tr>');
	}

	function insertUserAuth() {
		var reqCnt = 0;
		var sCnt = 0;

		$("input:checked[name='userList']").each(function(idx) {
			reqCnt++;
		});

		if (reqCnt == 0) {
			bootbox.alert('등록할 사용자를 선택해주세요.');
			return false;
		}

		bootbox.confirm($("#authId option:selected").text() + " 권한으로 등록 하시겠습니까?", function(result) {
			if (result) {
				$('.loading').css('display', 'block');
				$("input:checked[name='userList']").each(function(idx) {
					$.ajax({
						type : 'POST',
						url : '/rest/authApi/userAuth/' + $("#authId option:selected").val() + '/' + $(this).val(),
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
			bootbox.alert(reqCnt + '명 중 ' + sCnt + ' 명이 등록 되었습니다.', function () {
				hideModal('userListPopLayer');
				getList(null, true);
			});
		});
	}

	function deleteUserAuth() {
		if ($("#authId").val() == 'all') {
			bootbox.alert('삭제할 권한을 선택해주세요.');
			return false;
		}

		var reqCnt = 0;
		var sCnt = 0;

		$("input:checked[name='userId']").each(function(idx) {
			reqCnt++;
		});

		if (reqCnt == 0) {
			bootbox.alert('삭제할 사용자를 선택해주세요.');
			return false;
		}

		bootbox.confirm($("#authId option:selected").text() + " 권한을 삭제 하시겠습니까?", function(result) {
			if (result) {
				$("input:checked[name='userId']").each(function(idx) {
					$.ajax({
						type : 'DELETE',
						url : '/rest/authApi/userAuth/' + $("#authId option:selected").val() + '/' + $(this).val(),
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
			}
			bootbox.alert(reqCnt + '명 중 ' + sCnt + ' 명이 삭제 되었습니다.', function () {
				getList(null, true);
			});
		});

		$("#userAuthTitleArea input[name='allChk']").attr("checked", false);
	}

	function getList(pageNo, flag){
		if (pageNo == null) {
			pageNo = 1;
		}

		$.ajax({
			type : 'GET',
			url : '/rest/authApi/userAuthList/' + $("#authId").val() + '/' + pageNo,
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

	function openUserListPopLayer() {
		initPopup();
		if ($("#authId").val() == 'all') {
			bootbox.alert('등록할 권한을 선택해주세요.');
			return false;
		}
		showModal('userListPopLayer');
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
			<i class="icon iconAuthManage02"></i> <span>사용자 권한 관리</span>
		</h2>
		<p class="location">Home > 권한관리 > 사용자 권한 관리</p>
	</div>
	<!-- 타이틀 E -->

	<!-- 검색 조건 S -->
	<div class="srchCondition">
		<strong class="tit">검색 조건</strong>
		<div class="innerBox">
			<div class="left">
				<ul>
					<li>
						<span class="inputTit">권한</span>
						<select name="authId" id="authId" class="customSelect">
							<c:forEach var="data" items="${authList}" varStatus="status">
								<c:if test="${status.first}">
									<option value="${data.authId}" selected>${data.authName}</option>
								</c:if>
								<c:if test="${not status.first}">
									<option value="${data.authId}">${data.authName}</option>
								</c:if>
							</c:forEach>
						</select>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- 검색 조건 E -->

	<!-- 검색 내역 S -->
	<div class="srchList">
		<strong class="tit">사용자 권한 리스트<em id="totalRow"></em>건
		</strong>
		<div class="innerBox">
			<div class="btns">
				<button type="button" class="btnReg" onclick="javascript:openUserListPopLayer(); return false;">등록</button>
				<button type="button" class="btnReg" onclick="javascript:deleteUserAuth(); return false;">삭제</button>
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
				</colgroup>
				<thead id="userAuthTitleArea">
					<tr>
						<th><input type="checkbox" name="allChk" /></th>
						<th>사번</th>
						<th>성명</th>
						<th>직급</th>
						<th>직책</th>
						<th>부서명</th>
						<th>권한</th>
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