<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<jsp:include page="../commons/head.html">
	<jsp:param value="活动监控" name="title" />
	<jsp:param value="${ctx}" name="ctx" />
</jsp:include>
<head>
</head>
<body>
	<div class="templatemo-content-wrapper">
		<h1>
			活动监控
			<button type="submit" class="btn btn-primary" onclick="javascript:location.href='${ctx}/takedata/manualHistory'" style="float:right;">历史</button>
		</h1>
		<p class="margin-bottom-15" style="margin-top:10px;">在这里你能快速的查询想要的数据</p>
		
	</div>
	<script type="text/javascript">
		
	</script>
</body>
</html>