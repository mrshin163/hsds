<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
$(function(){
	
$.ajax({
		url : "/rest/menuApi/userMenuList",
		type : "GET",
		dataType : "json",
		success : function(jsonData) {
			menuListHtml = "";
			subMenuListHtml = "";
			if (jsonData.length > 0) {
				
				menuListHtml += "<li><a href='/' ><i class='icon iconHome'></i> <span>Home</span></a></li>";
				
				$.each(jsonData,function(key, menuList) {
					
					$.each(menuList.children, function(i, subList){
						subMenuListHtml += "<li><a href='" + subList.URL + "'>" + subList.MENUNAME + "</a></li>"
										
					});
					menuListHtml +=  "<li class=''>"
									+"	<a href='javascript:;'><i class='icon " + menuList.ICONID + "'></i> <span>" + menuList.title	+ "</span></a>"
									+"	<ul class='depth02'>"
									+ subMenuListHtml
									+"	</ul>"
									+ "</li>";
					subMenuListHtml = "";				
				});
				
			}
			$("#leftMenu").html(menuListHtml);
			
		},
		error : function(data) {
			bootbox.alert("에러가 발생하였습니다.")
		}
	});
})
	
</script>


<!-- lnb -->
<div class="lnb">
	<div class="lnbInner">
		<ul class="leftMenu" id="leftMenu">

		</ul>
	</div>
</div>
<!-- //lnb -->