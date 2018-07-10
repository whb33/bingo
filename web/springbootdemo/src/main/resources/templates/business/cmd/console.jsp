<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<jsp:include page="../commons/head.html">
	<jsp:param value="控制台" name="title" />
	<jsp:param value="${ctx}" name="ctx" />
</jsp:include>
<head>
	<style type="text/css">
		.wrapper {
		    height: 700px;
		    border-radius: 5px;
		    border: 1px solid #ccc;
		    margin: 10px auto;
		    background-color: #EFF4FA;
		    box-sizing: border-box;
		}
		.wrapper .content {
		    box-sizing: border-box;
		    height: 73%;
		    border: 1px solid #ccc;
		    margin: 1% 10px;
		    padding: 5px;
		    overflow-y: auto;
		    background-color: #000000;
		}
		.wrapper .content ul li {
		    font-size: 14px;
		    padding: 5px;
		    color: #ffffff;
		}
		.wrapper .content ul li span {
		    font-style: italic;
		    color: greenyellow;
		}
		.wrapper .action {
		    box-sizing: border-box;
		    height: 23%;
		    border: 1px solid #ccc;
		    margin: 1% 10px;
		    padding: 5px;
		    overflow: hidden;
		}
		.wrapper .action textarea {
		    width: 100%;
		    height: 60%;
		    border-radius: 3px;
		    display: block;
		    margin-bottom: 5px;
		    padding: 5px;
		}
		.wrapper .action button {
		    float: right;
		    margin-right: 5px;
		    margin-left: 5px;
		}
	</style>
</head>
<body>
	<div class="wrapper">
		<div class="content" id="chat">
			<ul id="chat_conatiner">
				<li><span>系统规则</span> : 控制台只允许redis命令:get hget</li>
			</ul>
		</div>
		<div class="action">
			<textarea id="texts"></textarea>
			<button class="btn btn-success" id="clear">清屏</button>
			<button class="btn btn-success" id="send">发送</button>
		</div>
	</div>
	<script type="text/javascript">
		function sendMsg(from,msg){
			var li = document.createElement('li');
            li.innerHTML = '<span>' + from + '</span>' + ' : ' + msg;
            document.querySelector('#chat_conatiner').appendChild(li);
            // 设置内容区的滚动条到底部
            document.querySelector('#chat').scrollTop = document.querySelector('#chat').scrollHeight;
            // 并设置焦点
            document.querySelector('textarea').focus();
		}
		function send(){
			var msg = $("#texts").val().replace('\r\n', '').trim();
			sendMsg("我",msg);
			$.ajax({
				async:true,
				url: '${ctx}/console',
				type:'post',
				dataType:'json',
				data:{
					command : msg
				},
				success:function(res){
					if(res.status == '1'){
						sendMsg("系统",res.data);
					}else{
						sendMsg("系统","命令非法,控制台只允许redis命令:get hget");
					}
				},error:function(){
					sendMsg("系统","请求错误");
				}
			});
			setTimeout(function(){
				$("#texts").val("");
			},100);
		}
		$(document).ready(function(){
			document.querySelector('textarea').addEventListener('keypress', function(event){
	              if(event.which == 13){
	                  send();
	              }
	          });
	          document.querySelector('#send').addEventListener('click', function(){
	              send();
	          });

	          document.querySelector('#clear').addEventListener('click', function(){
	              document.querySelector('#chat_conatiner').innerHTML = '';
	          });
		})
	</script>
</body>
</html>