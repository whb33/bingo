<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RichInfo提数程序</title>
<script src="${ctx }/resource/js/jquery-1.8.2.min.js?ts=${ts}"></script>
<script src="${ctx }/resource/js/jquery.cycle.all.js?ts=${ts}"></script>
<script src="${ctx }/resource/js/jquery.form.min.js?ts=${ts}"></script>
<script src="${ctx }/resource/js/ajaxfileupload.js?ts=${ts}"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resource/css/login.css?ts=${ts}">
</head>
<body>
    <div class="container">
		<%-- <img src="${ctx}/resource/image/login/logo.png" style="width:50%;position: absolute;left: 50%;margin-left: -25%;top: 50%;margin-top: -350px;"> --%>
		<form id="cform" action="#" method="post">
			<div class="login_div">
				<div class="login_inner" style="text-align: center; width: 100%;padding-top:75px;">
					<input type="text" name="username" class="login_text login_div_space username" placeholder="账号" autocapitalize="off" value="" />
					<input type="password" name="password" class="login_text login_div_space password" placeholder="密码" value="" autocapitalize="off" />
					<div style="width:300px;margin:0 auto;height:40px;">
						<input type="text" id="captcha" name="captcha" class="login_text login_div_space" placeholder="请输入验证码" autocapitalize="off" />
						<a href="javascript:changeImg('${ctx}/captcha1');" style="float:right;">
						<img id="captchaImg" src="${ctx}/captcha1"></a>
					</div>
					<div id="signBtn" class="login_div_loginbtn" style="margin-bottom: 10px;">
						登录
					</div>
					<!-- <div class="login_div_btns">
						<div class="right">
							<a href="javascript:void(0);">忘记了</a>
						</div>
					</div> -->
				</div>
			</div>
		</form>
	</div>
	
	<script type="text/javascript">
		function imgdragstart() {
			return false;
		}
		function changeImg(_api) {
		    $("#captchaImg").attr('src', _api + '?nocache=' + (new Date().getTime()));
		}
		var index = 0;
		$(document).ready(function() {
			//配置图片不可拖拉
			for (i in document.images) {
				document.images[i].ondragstart = imgdragstart;
			}
			$("#signBtn").bind("click",function(){
				var username = $("input[name='username']").val();
				var password = $("input[name='password']").val();
				if(username==""||password==""){
					alert("请输入");
					return;
				}
				$("#cform").ajaxSubmit({
					type:'POST',
					cache:false,
					url:"${ctx}/login",
					success:function(data){
						var obj = eval(data);
						console.log(obj.code);
						if(obj.code==1){
							window.location.href="${ctx}/home";
						}else{
							alert(obj.msg);
						}
					},
					error:function(error){
						alert("数据提交失败，服务器可能出现错误，请稍后再试。");
					}
				});
			});
		});
	</script>
</body>
</html>