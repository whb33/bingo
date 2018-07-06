<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<jsp:include page="../commons/head.jsp">
	<jsp:param value="自动提数" name="title" />
	<jsp:param value="${ctx}" name="ctx" />
</jsp:include>
<head>
	<style type="text/css">
		.templatemo-content-wrapper h1 .btn-primary{
			float:right;
		}
		.list-group-item{
			padding:20px;
    		box-shadow: 0 1px 3px rgba(26,26,26,.1);
		}
	</style>
</head>
<body>
	<div class="templatemo-content-wrapper">
		<h1>
			自动提数
			<button data-method="addNotice" type="button" class="btn btn-primary addTaskBtn">添加</button>
		</h1>
		<p class="margin-bottom-15" style="margin-top:10px;">在这里你可以看到自己的定时提数任务(每天凌晨2点执行任务)</p>
		<div class="templatemo-progress">
			<div class="list-group">
				<c:forEach var="task" items="${taskList}" varStatus="taskStatus">
					<a href="javascript:void(0);" class="list-group-item" style="border-radius:2px;"><%--  <c:if test="${taskStatus.index%2==0}">active</c:if> --%>
					<!-- <a href="javascript:void(0);" class="list-group-item"> -->
		                <h3 class="list-group-item-heading" style="font-size:22px;color:#555;">
		                	<span onclick="javascript:cut(${taskStatus.index})">${task.projectName}</span>
		                	<button data-method="addNotice" type="button" class="btn btn-primary" onclick="javascript:del('${task.id}')" style="float:right;margin-left:10px;">删除</button>
		                	<button data-method="addNotice" type="button" class="btn btn-primary" onclick="javascript:edit('${task.id}')" style="float:right;margin-left:10px;">编辑</button>
		                	<button data-method="addNotice" type="button" class="btn btn-primary" onclick="javascript:runTask('${task.id}')" style="float:right;">执行</button>
		                </h3>
		                <c:forEach var="taskSheet" items="${task.sheets}" varStatus="taskSheetStatus">
		                	<br/>
			                <h4 class="list-group-item-heading" style="color:#555;">${taskSheet.sheet}</h4>
			                <p class="list-group-item-text cut cut${taskStatus.index}" style="color: #1a1a1a;">${taskSheet.querysql}</p>
		                </c:forEach>
		                <div style="margin-top:10px;right:0px;font-size:12px;color:#bcbcbc;text-align:right;">
		                	创建时间：<fmt:formatDate value="${task.createTime}" pattern="yyyy-MM-dd HH:mm" />
		                </div>
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