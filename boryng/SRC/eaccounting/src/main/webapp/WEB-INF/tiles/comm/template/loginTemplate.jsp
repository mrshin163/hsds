<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	
	<script src="/js/jquery-1.8.3.min.js"></script>
	<script src="/js/jquery.plugin.js"></script>
	<script src="/js/bizSP.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/bootbox.js"></script>
</head>
<body>
<div class="loginArea">
	<tiles:insertAttribute name="content" />
	<tiles:insertAttribute name="footer" />
</div>


</body>
</html>