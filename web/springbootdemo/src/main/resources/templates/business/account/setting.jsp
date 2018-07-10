<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<jsp:include page="../commons/head.html">
	<jsp:param value="个人中心" name="title" />
	<jsp:param value="${ctx}" name="ctx" />
</jsp:include>
<head>
	<style type="text/css">
		.setting{
			width:80%;
		}
	</style>
</head>
<body>
	<div class="templatemo-content-wrapper">
		<h1>个人信息
		<button type="submit" class="btn btn-primary" onclick="javascript:saveInfo();" style="float:right;">保存</button>
		</h1>
		<p class="margin-bottom-15" style="margin-top:10px;">在这里你可以修改个人用户信息</p>
		<div class="setting">
			<div class="row">
				<div class="col-md-6 margin-bottom-15">
					<label for="firstName" class="control-label">昵称：</label>
					<input type="text" class="form-control" id="nickName" placeholder="你的昵称" value="${userInfo.nickname}">
				</div>
				<div class="col-md-6 margin-bottom-15">
					<label class="control-label">用户名：</label>
					<input type="text" class="form-control" value="${userInfo.username}" disabled="">
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-6 margin-bottom-15">
					<label for="firstName" class="control-label">密码：</label>
					<input type="password" class="form-control" id="password" placeholder="你的密码" value="${userInfo.password}">
				</div>
				<div class="col-md-6 margin-bottom-15">
					<label for="firstName" class="control-label">手机号码：</label>
					<input type="text" class="form-control" id="mobile" placeholder="你的手机号" value="${userInfo.mobile}">
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-6 margin-bottom-15">
					<label for="firstName" class="control-label">创建时间：</label>
					<input type="text" class="form-control" id="createTime" placeholder="创建时间" value="<fmt:formatDate value="${userInfo.createTime}" pattern="yyyy年MM月dd HH:mm"/>" disabled="">
				</div>
				<div class="col-md-6 margin-bottom-15">
					<label for="firstName" class="control-label">上次登录时间：</label>
					<input type="text" class="form-control" id="loginTime" placeholder="登录时间" value="<fmt:formatDate value="${userInfo.loginTime}" pattern="yyyy年MM月dd HH:mm"/>" disabled="">
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function saveInfo(){
		var nickName = $("#nickName").val();
		var password = $("#password").val();
		var mobile = $("#mobile").val();
		if(nickName.length == 0){
			parent.layer.msg('昵称不能为空');
			return;
		}
		if(password.length == 0){
			parent.layer.msg('密码不能为空');
			return;
		}
		if(mobile.length == 0){
			parent.layer.msg('手机号码不能为空');
			return;
		}
    	var loading = parent.layer.load(2);
		$.ajax({
            type: 'post',
            url: '${ctx}/setting',
			data : {
				"nickName" : nickName,
				"password" : password,
				"mobile" : mobile
			},
			beforeSend : function(XMLHttpRequest) {
				
			},
			success : function(res) {
				parent.layer.close(loading);
				if(res.status == 1){
					parent.layer.msg("修改成功！");
				}else{
					parent.layer.msg(res.msg);
				}
			},error: function(){
				parent.layer.close(loading);
				parent.layer.msg("请求异常");
			}
		});
	}
</script>
</html>