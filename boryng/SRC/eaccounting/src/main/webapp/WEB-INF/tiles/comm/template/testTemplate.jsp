<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><tiles:insertAttribute name="title" /></title>
</head>
<body>

<div id="wrap" class="wrap">
	<tiles:insertAttribute name="header" />
	
	<!-- CONTENTS -->
	<section class="container">
		<div class="containerInner">
			<div class="containerInner">
				<tiles:insertAttribute name="lnb" />
				<tiles:insertAttribute name="content" />
			</div>
		</div>
	</section>
	<!--// CONTENTS -->
	
	<tiles:insertAttribute name="footer" />
</div>

</body>
</html>