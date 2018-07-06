<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<jsp:include page="../commons/head.jsp">
	<jsp:param value="手动提数历史记录" name="title" />
	<jsp:param value="${ctx}" name="ctx" />
</jsp:include>
<head>
	<style type="text/css">
		.templatemo-content-wrapper h1 .btn-primary{
			float:right;
		}
	</style>
</head>
<body>
	<div class="templatemo-content-wrapper">
		<h1>
			手动提数历史记录
		</h1>
		<p class="margin-bottom-15" style="margin-top:10px;">在这里你可以看到自己执行过的sql语句</p>
		<div class="templatemo-progress">
			<div class="list-group">
				<c:forEach var="manualInfo" items="${historyList}" varStatus="manualStatus">
					<a href="javascript:void(0);" class="list-group-item <c:if test="${manualStatus.index%2==0}">active</c:if>">
				        <h3 class="list-group-item-heading">
				        	<button data-method="addNotice" type="button" class="btn btn-primary" onclick="javascript:location.href='${ctx}/takedata/manual?uuid=${manualInfo.id}'" style="float:right;">执行</button>
				        </h3>
						<c:forEach var="sheet" items="${manualInfo.sheetnames}" varStatus="status">
						    <span onclick="javascript:cut(${status.index})" style="font-size:24px;">${manualInfo.sheetnames[status.index]}</span>
						    <p class="list-group-item-text cut cut${status.index}">${manualInfo.sqls[status.index]}</p>
					        <div style="margin-top:10px;right:0px;"></div>
						</c:forEach>
					</a>
				</c:forEach>
			</div>
		</div>
		<script type="text/javascript">
			function cut(i){
				var cutVar = $(".cut"+i);
				if(cutVar.hasClass("cut")){
					cutVar.removeClass("cut");
				}else{
					cutVar.addClass("cut");
				}
			}
		</script>
		<script type="text/javascript">
			$(document).ready(function(){
				$(".addTaskBtn").bind("click",function(){
					edit();
				})
			})
			function edit(id){
				var mailTaskId = "";
				if(id != undefined){
					mailTaskId += "?mailTaskId="+id;
				}
				var offsetTop = ((document.body.clientHeight * 0.4)/2) + "px";
				layui.use('layer', function(){
			    	var layer = layui.layer;
			    	layer.open({
						type: 2
						,title: "自动提数配置" //不显示标题栏
						,closeBtn: 1
						,area: ['80%','520px']
			    		,offset : ["100px"]
						,shade: 0.8
					    ,moveType: 1 //拖拽模式，0或者1
					    ,content: ['${ctx}/takedata/editAutomatic'+mailTaskId]
					    ,success: function(layero){
					    	$("iframe[name='layui-layer-iframe1']").css("height","470px");
						},cancel: function(){ 
							
						}
					});
			    });
		    }
			function del(id){
				layui.use('layer', function(){
			    	var layer = layui.layer;
			    	//删除框
					layer.confirm('确定删除吗？', {
					  btn: ['确定','取消'] //按钮
					}, function(){
						$.ajax({
							async:true,
							url: '${ctx}/takedata/delMailTask',
							type:'post',
							data:{
								taskId : id
							},
							dataType:'json',
							success:function(res){
								var msg = "删除";
								if(res.status == "1"){
									msg+="成功";
								}else{
									msg+="失败";
								}
								var index = layer.msg(msg,{
									icon: 1,
									time: 1000 //2秒关闭（如果不配置，默认是3秒）
								}, function(){
									location.reload();
								});  
							}
						});
					}, function(){
					  
					});
				});
			}
			function runTask(id){
				layui.use('layer', function(){
			    	var layer = layui.layer;
			    	
			    	var index = layer.msg("正在执行中...",{
						icon: 1,
						time: 99000 //2秒关闭（如果不配置，默认是3秒）
					}, function(){});
					$.ajax({
						async:true,
						url: '${ctx}/takedata/runMailTask',
						type:'post',
						data:{
							taskId : id
						},
						dataType:'json',
						success:function(res){
							layer.close(index);
							var msg = "执行";
							var icon = 2;
							if(res.status == "1"){
								msg+="成功";
								icon = 1;
							}else{
								msg+="失败";
							}
							index = layer.msg(msg,{
								icon: icon,
								time: 2000 //2秒关闭（如果不配置，默认是3秒）
							}, function(){
								//location.reload();
							});  
						}
					});
				
				});
			}
		</script>
</body>
</html>