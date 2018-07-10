<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<jsp:include page="../commons/head.html">
	<jsp:param value="个人中心" name="title" />
	<jsp:param value="${ctx}" name="ctx" />
</jsp:include>
<head>
</head>
<body>
	<div class="templatemo-content-wrapper">
		<h1>个人信息
		<button type="submit" class="btn btn-primary" onclick="javascript:location.href='/takedata/takedata/manualHistory'" style="float:right;">保存</button>
		</h1>
		<p class="margin-bottom-15" style="margin-top:10px;">在这里你可以修改个人用户信息</p>
		<div class="setting">
			<div class="row">
				<div class="col-md-6 margin-bottom-15">
					<label for="firstName" class="control-label">昵称：</label>
					<input type="text" class="form-control" id="nickName" placeholder="你的昵称" value="">
				</div>
				<div class="col-md-6 margin-bottom-15">
					<label class="control-label">用户名：</label>
					<input type="text" class="form-control" value="assd" disabled="">
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-6 margin-bottom-15">
					<label class="control-label">密码：</label>
					<a href="">修改密码</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>