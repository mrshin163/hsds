<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="/js/jquery.ztree.core-3.5.js"></script>
<script src="/js/jquery.ztree.excheck-3.5.js"></script>
<link rel="stylesheet" type="text/css" href="/css/zTreeStyle.css">
<script type="text/javascript">

/*  트리 기술참조
 *  ZTREE - http://www.ztree.me/v3/main.php#_zTreeInfo 
 */
 
 
//////  트리 글자부분 클릭했을때의 이벤트
function onClick(event, treeId, treeNode, clickFlag) {
	var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
	
	//////  클릭한 노드가 체크되어 있으면
	if(treeNode.checked){
		//////  체크 해제한다.
		treeObj.checkNode(treeNode, false, true);
	}else{
		////// 체크한다.
		treeObj.checkNode(treeNode, true, true);
	}
	onCheck(null, null, treeNode);
}

////// 체크박스를 체크했을떄
function onCheck(event, treeId, treeNode) {
	//////  체크했으면
	if(treeNode.checked){
		////// 권한 insert
		modifyMenuAuth('insert', treeNode.MENUID);
	}else{
		////// 권한 delete
		modifyMenuAuth('delete', treeNode.MENUID);
	}
    //alert(treeNode.MENUID + '  '+ treeNode.checked);
}

function modifyMenuAuth(flag, menuId){
	if(menuId==null || menuId ==''){
		bootbox.alert('menuId is null....');
	}
	var url = '/rest/authApi/###MenuAuth';
	var params;
	var authId = $('#authList :selected').val();
	
	url = url.replace("###", flag);
	params = {	 "menuId"	:	menuId
				,"authId"	:	authId			
	};
	
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
		},
		error : function(err) {
			bootbox.alert("처리중 장애가 발생하였습니다." + err);
		}
	});	
}


//////  트리 초기화 - 하나도 체크되지 않은 트리로 만들기
function setUnCheckedTree(obj){
	var treeObj = $.fn.zTree.getZTreeObj("treeMenu");				
	$(obj).each(function(idx, node){
		setUnCheckedTree(node.children);
		node.checked = false;
	});
}


////// 매뉴권한 조회하여 트리에 적용
function getMenuAuthList(){
	var authId = $('#authList :selected').val();
	
	if(authId==null||authId==''){
		bootbox.alert('처리도중 오류가 발생하였습니다. - 1');
	}
	
	$.ajax({
		url : "/rest/authApi/menuAuthList/"+authId,
		type : "GET",
		dataType : "json",
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			if(jsonData.menuAuthList!=null){
				var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
				setUnCheckedTree(treeObj.getNodes());
				
				////// 트리를 menuId로 검색하여 있으면 체크
				$(jsonData.menuAuthList).each(function(idx, menuAuth){
					var node = treeObj.getNodeByParam("MENUID", menuAuth.menuId, null);
					if(node!=null)
						node.checked = true;
				});
				treeObj.refresh();
				
				
			}else{
				bootbox.alert("처리도중 오류가 발생하였습니다. - 2");
			}
		},
		error : function(data) {
			bootbox.alert("처리도중 오류가 발생하였습니다. - 3")
		}
	});
}


$(function(){
	$(".customSelect").selectbox();
	
	var setting = {
			data: {
				key: {
					name: "MENUNAME", 
					menuId : "MENUID",
					id : "MENUID"
				}
			},
			callback : {
				onClick: onClick,
				onCheck: onCheck
			},

			check: {
	            enable: true,
	            chkDisabledInherit: true
	        }
		};
	
	$.ajax({
		url : "/rest/menuApi/menuList",
		type : "GET",
		dataType : "json",
		beforeSend:function(){
			$('.loading').css("display", "block");
	    },
	    complete:function(){
			$('.loading').css("display", "none");
	    },
		success : function(jsonData) {
			$.fn.zTree.init($("#treeMenu"), setting, jsonData);
			getMenuAuthList();
		},
		error : function(data) {
			bootbox.alert("에러가 발생하였습니다.")
		}
	});
	
	
	$("#menuInfo input").each(function(i){
		$(this).focus(function(){
			$(this).animate({"opacity":"1"}, 400);
		}).blur(function() {
			$(this).animate({"opacity":"0.8"}, 400);
		});
	})
	
})
	
</script>

<div class="loading">
	<div class="loadingInner"></div>
	<div class="loadingModal"></div>
</div>

<div class="content">
	<!-- 타이틀 영역 -->
	<div class="titArea subTitArea">
		<h2>
			<i class="icon iconAuthManage02"></i> <span>메뉴권한관리</span>
		</h2>
		<p class="location">Home > 권한관리 > 메뉴권한관리</p>
	</div>
	<div class="srchCondition">
		<div class="innerBox">

			<ul>
				<li class="odd">
					<span style="display:inline-block;*zoom:1;*display:inline;width:70px;font-weight:700;vertical-align:top;margin-top:4px;">권한</span> 
					<select id="authList" class="customSelect" onchange="getMenuAuthList();">
						<c:forEach var="auth" items="${authList}">
							<option value="${auth.authId}">${auth.authName}</option>				
						</c:forEach>				
					</select>
				</li>
			</ul>

		</div>
	</div>
	<div class="treeArea" id="dataBox">		
		<div id="loader" style="position: absolute; display: none; top:45%; left:50%;"><img src="/images/admin/ajax-loader.gif"></div>
		<div class="left">		
			<!-- zTree Component -->
			<ul id="treeMenu" class="ztree"></ul>
		</div>
	</div>
</div>


