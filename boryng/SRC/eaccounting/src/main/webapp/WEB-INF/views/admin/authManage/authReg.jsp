<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
$(document).ready(function () {
	$("#authDataArea").on('click', 'tr', function(e) {
		openAuthPopup('mod', $(this).attr('value'));
	});

	// 등록
	$("#authRegBtn").click(function() {
		if (!validForm()) {
			return false;
		}

		var authId = $.trim($("#authPopDataArea input[name='authId']").val());
		var authName = $.trim($("#authPopDataArea input[name='authName']").val());
		var description = $.trim($("#authPopDataArea input[name='description']").val()).split('/').join('`');

		bootbox.confirm("등록 하시겠습니까?", function(result) {
			if(result) {
				$.ajax({
					type : 'POST',
					url : '/rest/authApi/auth/' + authId + '/' + authName + '/' + description,
					dataType : "json",
					beforeSend : function() {
						$('.loading').css('display', 'block');
					},
					complete : function() {
						$('.loading').css('display', 'none');
					},
					success : function(data) {
						if (data.code == 'S') {
							bootbox.alert('권한 등록 되었습니다.', function () {
								location.reload();
							});
						} else {
							bootbox.alert(data.msg);
						}
					},
					error : function(e) {
						console.log(e);
					}
				});
			}
		});
	});

	// 수정
	$("#authModBtn").click(function() {
		if (!validForm()) {
			return false;
		}

		var authId = $.trim($("#authPopDataArea input[name='authId']").val());
		var authName = $.trim($("#authPopDataArea input[name='authName']").val());
		var description = $.trim($("#authPopDataArea input[name='description']").val()).split('/').join('`');

		bootbox.confirm("수정 하시겠습니까?", function(result) {
			if(result) {
				$.ajax({
					type : 'PUT',
					url : '/rest/authApi/auth/' + authId + '/' + authName + '/' + description,
					dataType : "json",
					beforeSend : function() {
						$('.loading').css('display', 'block');
					},
					complete : function() {
						$('.loading').css('display', 'none');
					},
					success : function(data) {
						if (data.code == 'S') {
							bootbox.alert('권한 수정 되었습니다.', function () {
								location.reload();
							});
						} else {
							bootbox.alert(data.msg);
						}
					},
					error : function(e) {
						console.log(e);
					}
				});
			}
		});
	});
});

function openAuthPopup (flag, val) {
	if (flag == 'reg') {
		initPopup();
		hideButton(flag);
		showModal('authPopLayer');
	} else if (flag == 'mod') {
		initPopup();
		hideButton(flag);
		getAuthData(val);
		showModal('authPopLayer');
	}
}

function initPopup () {
	$("#authPopDataArea input").val('');
	$("#authPopDataArea input[name='authId']").attr('readonly', false);
	$("#authRegBtn").show();
	$("#authModBtn").show();
}

function hideButton (flag) {
	if (flag == 'reg') {
		$("#authModBtn").hide();
	} else if (flag == 'mod') {
		$("#authRegBtn").hide();
	}
}

function getAuthData (val) {
	$.ajax({
		type : 'GET',
		url : '/rest/authApi/auth/' + val,
		dataType : "json",
		success : function(data) {
			if (data.code == 'S') {
				makeHtml(data.authVo);
			} else {
				bootbox.alert(data.msg, function () {
					hideModal('authPopLayer');
				});
			}
		},
		error : function(e) {
			console.log(e);
		}
	});
}

function makeHtml (obj) {
	$("#authPopDataArea input[name='authId']").val(obj.authId);
	$("#authPopDataArea input[name='authId']").attr('readonly', true);
	$("#authPopDataArea input[name='authName']").val(obj.authName);
	$("#authPopDataArea input[name='description']").val(obj.description);
}

function validForm () {
	var authId = $.trim($("#authPopDataArea input[name='authId']").val());
	var authName = $.trim($("#authPopDataArea input[name='authName']").val());
	var description = $.trim($("#authPopDataArea input[name='description']").val());

	if (authId == '') {
		bootbox.alert('권한 ID를 입력해주세요.');
		return false;
	}
	if (authName == '') {
		bootbox.alert('권한명을 입력해주세요.');
		return false;
	}
	if (description == '') {
		bootbox.alert('설명을 입력해주세요.');
		return false;
	} else {
		return true;
	}
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
			<i class="icon iconAuthManage02"></i> <span>권한 관리</span>
		</h2>
		<p class="location">Home > 권한관리 > 권한 관리</p>
	</div>
	<!-- 타이틀 E -->

	<!-- 검색 내역 S -->
	<div class="srchList">
		<strong class="tit">권한 리스트<em id="totalRow">${totalRow}</em>건
		</strong>
		<div class="innerBox">
			<div class="btns">
				<button type="button" class="btnReg" onclick="openAuthPopup('reg'); return false;">등록</button>
			</div>
			<table class="defaultType">
				<colgroup>
					<col width="20%" />
					<col width="20%" />
					<col width="60%" />
				</colgroup>
				<thead>
					<tr>
						<th>권한 ID</th>
						<th>권한명</th>
						<th>설명</th>
					</tr>
				</thead>
				<tbody id="authDataArea">
					<c:forEach var="auth" items="${authList}">
						<tr value="${auth.authId}">
							<td>${auth.authId}</td>
							<td>${auth.authName}</td>
							<td>${auth.description}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 검색 내역 E -->
</div>

<!-- 권한 관리 팝업 S -->
<div class="layer" id="authPopLayer">
	<div class="layerInner layerInner02">
		<strong class="firstTit">권한 관리</strong>
		<!-- 권한 관리 S -->
		<div class="long">
			<table class="horizontalTable">
				<colgroup>
					<col width="" />
					<col width="" />
					<col width="" />
				</colgroup>
				<tbody id="authPopDataArea">
					<tr>
						<th>권한 ID</th>
						<td><input type="text" name="authId" style="width: 96%" maxlength="32" /></td>
					</tr>
					<tr>
						<th>권한명</th>
						<td><input type="text" name="authName" style="width: 96%" maxlength="64" /></td>
					</tr>
					<tr>
						<th>설명</th>
						<td><input type="text" name="description" style="width: 96%" maxlength="128" /></td>
					</tr>
				</tbody>
			</table>
			<div class="btmbtns">
				<button type="button" id="authRegBtn" class="btnReg">등록</button>
				<button type="button" id="authModBtn" class="btnReg">수정</button>
			</div>
		</div>
		<!-- 권한 관리 E -->
	</div>
	<button type="button" class="btnLayerClose" onclick="hideModal('authPopLayer'); return false;">
		<img src="/images/btn_close.png" />
	</button>
</div>
<!-- 권한 관리 팝업 E -->