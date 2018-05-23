<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="/js/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet" type="text/css" href="/css/zTreeStyle.css">




<script type="text/javascript">

function onClick(event, treeId, treeNode, clickFlag) {
	menuSelect(treeNode.MENUID);
}

function checkValidation(){
	var result = true;
	var array = ['menuId'];

	$(array).each(function(index, id){
		if($('#'+id).val()==''){
			bootbox.alert('필수 입력값이 비었습니다.');
			result = false;
			return false;
		}		
	});	
	return result;
}

function menuInsert(){
	bootbox.confirm("등록 하시겠습니까?",function(result){
		if(result){
			if(!checkValidation()){
				return;
			}
			
			var params = "";
			
			var menuId = $("#menuId").val();
			var menuName = $("#menuName").val();
			var upMenuId = $("#upMenuId").val();
			var menuLevel = $("#menuLevel").val();
			var menuOrder = $("#menuOrder").val();
			var url = $("#menuUrl").val();
			var iconId = $("#iconId").val();
			var useYn = $(':radio[name="menuYn"]:checked').val();
			
			params = {"menuId" : menuId, "menuName" : menuName, "upMenuId" : upMenuId, "menuLevel" : menuLevel, "menuOrder" : menuOrder, "url" : url, "useYn" : useYn, "iconId" : iconId};
			
			jQuery.ajax({
				type : 'POST',
				url : '/rest/menuApi/insertMenu',
				dataType : "json",
				contentType: 'application/json',
				data : JSON.stringify(params),
				success : function() {
					bootbox.alert('등록 되었습니다.');
					getMenuList();
				},
				error : function(err) {
					bootbox.alert("처리중 장애가 발생하였습니다." + err);
				}
			});			
		} //if		
	}) //confirm
	
}

function menuUpdate(){
	bootbox.confirm("수정 하시겠습니까?",function(result){
		if(result){
			var params = "";
			
			var menuId = $("#menuId").val();
			var menuName = $("#menuName").val();
			var upMenuId = $("#upMenuId").val();
			var menuLevel = $("#menuLevel").val();
			var menuOrder = $("#menuOrder").val();
			var url = $("#menuUrl").val();
			var iconId = $("#iconId").val();
			var useYn = $(':radio[name="menuYn"]:checked').val();
			
			params = {"menuId" : menuId, "menuName" : menuName, "upMenuId" : upMenuId, "menuLevel" : menuLevel, "menuOrder" : menuOrder, "url" : url, "useYn" : useYn, "iconId" : iconId};
			
			jQuery.ajax({
				type : 'POST',
				url : '/rest/menuApi/updateMenu',
				dataType : "json",
				contentType: 'application/json',
				data : JSON.stringify(params),
				success : function() {
					bootbox.alert('업데이트 되었습니다.');
					//location.reload();
				},
				error : function(err) {
					bootbox.alert("처리중 장애가 발생하였습니다." + err);
				}
			});
		}
	})
}

function menuDelete(){
	bootbox.confirm("삭제 하시겠습니까?",function(result){
		if(result){
			var params = "";
			
			var menuId = $("#menuId").val();	
			params = {"menuId" : menuId};
			
			jQuery.ajax({
				type : 'POST',
				url : '/rest/menuApi/deleteMenu',
				dataType : "json",
				contentType: 'application/json',
				data : JSON.stringify(params),
				success : function() {
					bootbox.alert('삭제 되었습니다.');
					getMenuList();
				},
				error : function(err) {
					bootbox.alert("처리중 장애가 발생하였습니다." + err);
				}
			});
		}
	})
}



function menuSelect(menuId){
	$.ajax({
		url : "/rest/menuApi/menuSearch/" + menuId,
		type : "GET",
		dataType : "json",
		beforeSend:function(){
			$('#loader').css("display", "block");
	    },
	    complete:function(){
	    	 $('#loader').css("display", "none");
	    },
		success : function(jsonData) {
			$("#menuId").val(checkNull(jsonData.MENUID));
			$("#menuName").val(checkNull(jsonData.MENUNAME));
			$("#upMenuId").val(checkNull(jsonData.UPMENUID));
			$("#menuLevel").val(checkNull(jsonData.MENULEVEL));
			$("#menuOrder").val(checkNull(jsonData.MENUORDER));
			$("#menuUrl").val(checkNull(jsonData.URL));
			$("#iconId").val(checkNull(jsonData.ICONID));
			$('input:radio[name="menuYn"]:input[value="'+checkNull(jsonData.USEYN)+'"]').attr("checked", true);
		},
		error : function(data) {
			bootbox.alert("에러가 발생하였습니다.")
		}
	});
}

function getMenuList(){
	var setting = {
			data: {
				key: {
					name: "MENUNAME", 
					menuId : "MENUID",
					id : "MENUID"
				}
			},
			callback : {
				onClick: onClick
			}
		};
	
	$.ajax({
		url : "/rest/menuApi/menuList",
		type : "GET",
		dataType : "json",
		success : function(jsonData) {
			$.fn.zTree.init($("#treeMenu"), setting, jsonData);
		},
		error : function(data) {
			bootbox.alert("에러가 발생하였습니다.")
		}
	});
}

$(function(){	
	getMenuList();
	
	$("#menuInfo input").each(function(i){
		$(this).focus(function(){
			$(this).animate({"opacity":"1"}, 400);
		}).blur(function() {
			$(this).animate({"opacity":"0.8"}, 400);
		});
	})
})
	
</script>



<div class="content">
	<div id="loader" style="position: absolute; display: none;"><img src="/images/admin/ajax-loader.gif"></div>
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2>
			<i class="icon iconMenuManage02"></i><span>메뉴관리</span>
		</h2>
		<p class="location">Home > 메뉴관리</p>
	</div>

	<div class="treeArea">
		
	
		<div class="left">
		
			<!-- zTree Component -->
			<ul id="treeMenu" class="ztree"></ul>
		</div>
		<div class="right">
			<strong>메뉴정보</strong>
			<br/>
			<br/>
			<table class="horizontalTable">
				<colgroup>
					<col width="150" />
					<col width="" />
				</colgroup>
				
				<tbody id="menuInfo">
					<form id="">
					<tr>
						<th>메뉴 ID</th>
						<td><input id="menuId" name="menuId" type="text" maxlength="16"></td>
					</tr>
					<tr>
						<th>메뉴명</th>
						<td><input id="menuName" name="menuName" type="text" maxlength="32"></td>
					</tr>
					<tr>
						<th>상위메뉴 ID</th>
						<td><input id="upMenuId" name="upMenuId" type="text" maxlength="32"></td>
					</tr>
					<tr>
						<th>메뉴레벨</th>
						<td><input id="menuLevel" name="menuLevel" type="text" maxlength="1"></td>
					</tr>
					<tr>
						<th>메뉴순서</th>
						<td><input id="menuOrder" name="menuOrder" type="text" maxlength="2"></td>
					</tr>
					<tr>
						<th>메뉴 URL</th>
						<td><input id="menuUrl" name="menuUrl" type="text" maxlength="128"></td>
					</tr>
					<tr>
						<th>아이콘 ID</th>
						<td><input id="iconId" name="iconId" type="text" maxlength="32"></td>
					</tr>
					<tr>
						<th>메뉴 사용 여부</th>
						<td><input type="radio" id="menuON" name="menuYn"  value="Y"/><label for="menuON">사용</label> <input type="radio" id="menuOFF" name="menuYn" value="N" /><label for="menuOFF">사용안함</label></td>
					</tr>
					</form>
				</tbody>
				
			</table>
			<span class="infoMent">* 상위메뉴 ID가 없는 경우는 값을 0으로 등록 하시기 바랍니다.</span>
			<div class="btns">
				<a onclick="menuInsert();" class="btnRegShort" style="cursor:pointer;">등록</a><a onclick="menuUpdate();" class="btnModify" style="cursor:pointer;">수정</a> <a onclick="menuDelete();" class="btnDelShort" style="cursor:pointer;">삭제</a>
			</div>
		</div>
	</div>
</div>


