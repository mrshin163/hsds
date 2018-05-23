<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title><tiles:insertAttribute name="title" /></title>
	<link href="/images/favicon.png" rel="icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="/css/common.css">
	<link rel="stylesheet" type="text/css" href="/css/bizSP.css">
	
	<script src="/js/jquery-1.8.3.min.js"></script>
	<script src="/js/jquery.plugin.js"></script>
	<script src="/js/bizSP.js"></script>
	<script src="/js/bootbox.js"></script>
	<script src="/js/jquery.fileupload.js"></script>
	
	<script  src="/js/jquery-ui.js" type="text/javascript"></script>
	<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div id="wrap" class="wrap">
	<tiles:insertAttribute name="header" />
	
	<!-- CONTENTS -->
	<section class="container">
			<div class="containerInner">
			<tiles:insertAttribute name="lnb" />
			<tiles:insertAttribute name="content" />
			</div>
	</section>
	<!--// CONTENTS -->
	
	<tiles:insertAttribute name="footer" />
</div>
<tiles:insertAttribute name="commPopLayer" />
</body>
</html>