<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${param.jquery eq true}">
	<script type="text/javascript" src="/application-js/jquery-3.2.1.js?f=d"></script>
	<script type="text/javascript" src="/application-js/jquery_lazyload-1.7.2/jquery.lazyload.js?f=d"></script>
</c:if>
<c:if test="${param.amazeui eq true}">
	<!-- 移动端样式 -->
	<link rel="stylesheet" type="text/css" href="/amazeui/css/amazeui.css?1=3"/>
	<script type="text/javascript" src="/amazeui/js/amazeui.js?1=3"></script>
</c:if>
<c:if test="${param.jweixin eq true}">
<!-- 微信工具 -->
	<script type="text/javascript" src="/application-js/jweixin-1.4.0.js?1=3"></script>
	<script type="text/javascript" src="/application-js/jweixin-util.js?2=3"></script>
</c:if>