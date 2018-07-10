<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<jsp:include page="../commons/head.html">
	<jsp:param value="手动提数" name="title" />
	<jsp:param value="${ctx}" name="ctx" />
</jsp:include>
<head>
</head>
<body>
	<div class="templatemo-content">
		<div class="templatemo-content-wrapper">
			<div class="row">
				<div class="col-md-12 margin-bottom-15">
			        <label for="firstName" class="control-label">邮件名称</label>
			        <input type="text" class="form-control" name="projectName" id="projectName" placeholder="请输入邮件名称..." value="${mailTask.projectName}">
				</div>
				<div class="col-md-12 margin-bottom-15">
			        <label for="firstName" class="control-label">收件人</label>
			        <input type="text" class="form-control" name="toAddress" id="toAddress" placeholder="请输入收件人..." value="${mailTask.toAddress}">
				</div>
			</div>
			<div class="row">
				<div class="col-md-6 margin-bottom-15" style="width:50%;float:left;">
					<label for="password_1">开始时间</label>
					<input type="text" id="startTime" name="startTime" readonly class="form-control form_datetime" value="<fmt:formatDate value="${mailTask.startTime}" pattern="yyyy-MM-dd HH:mm"/>">
				</div>
				<div class="col-md-6 margin-bottom-15" style="width:50%;float:left;">
					<label for="password_2">结束时间</label>
					<input type="text" id="endTime" name="endTime" readonly class="form-control form_datetime" value="<fmt:formatDate value="${mailTask.endTime}" pattern="yyyy-MM-dd HH:mm"/>">
				</div>
			</div>
			<div class="querys">
				<c:forEach var="sheets" items="${mailTask.sheets}" varStatus="status">
					<div class="row row${status.index+1}">
						<div class="col-md-12 margin-bottom-15">
				        	<label for="firstName" class="control-label">工作表名称 ${status.index+1}</label>
				        	<input type="text" class="form-control" id="firstName" placeholder="请输入工作表名称..." value="${sheets.sheet}">
						</div>
						<div class="col-md-12 margin-bottom-15">
			                <label for="notes">查询语句 ${status.index+1}</label>
							<textarea class="form-control" rows="3" id="notes">${sheets.querysql}</textarea>
						</div>
					</div>
				</c:forEach>
			</div>
			
			<div style="display:none;" id="templateQuery">
				<div class="row">
					<div class="col-md-12 margin-bottom-15">
			        	<label for="firstName" class="control-label">工作表名称</label>
			        	<input type="text" class="form-control" id="firstName" placeholder="请输入工作表名称..." value="">
					</div>
					<div class="col-md-12 margin-bottom-15">
		                <label for="notes">查询语句</label>
						<textarea class="form-control" rows="3" id="notes"></textarea>
					</div>
				</div>
			</div>
			
			<div class="row templatemo-form-buttons">
				<div class="col-md-12">
					<button type="button" class="btn btn-success">Add</button>
					<c:if test="${empty mailTask}">
						<button type="submit" class="btn btn-primary">Submit</button>
					</c:if>
					<c:if test="${not empty mailTask}">
						<button type="submit" class="btn btn-primary">Update</button>
					</c:if>
					<button type="reset" class="btn btn-default">Reset</button>
					<span style="padding-left:15px;">
					<!-- <label class="checkbox-inline">
	                    <input type="checkbox" id="inlineCheckbox1" value="option1" checked=""> Server status
	                </label>
	                <label class="checkbox-inline">
	                    <input type="checkbox" id="inlineCheckbox1" value="option1" checked=""> Server status
	                </label>
	                <label class="checkbox-inline">
	                    <input type="checkbox" id="inlineCheckbox1" value="option1" checked=""> Server status
	                </label> -->
					</span>
				</div>
			</div>
			<div class="panel-body">
				<ul class="nav nav-tabs" id="myTabs">
				</ul>
				<div class="tab-content">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var i = Number("${fn:length(mailTask.sheets)}");
		$(document).ready(function(){
			if(i == 0){
				++i;
			}
			//增加提数表单
			$(".btn-success").bind("click",function(){
				var template = $("#templateQuery").html();
				template = template.replace("row","row row"+i);
				template = template.replace("工作表名称","工作表名称 "+i);
				template = template.replace("查询语句","查询语句 "+i);
				
				$(".querys").append(template);
				++i;
			});
			//初始化提数表单
			$(".btn-default").bind("click",function(){
				i = 1
				$(".querys").html("");
				$("#myTabs").html("");
				$(".panel-body .tab-content").html("");
				$(".btn-success").click();
			});
			$(".btn-primary").bind("click",function(){
				var projectName = $("input[name='projectName']").val();
				var toAddress = $("input[name='toAddress']").val();
				var startTime = $("input[name='startTime']").val();
				var endTime = $("input[name='endTime']").val();
				if(projectName == ''){
					tip("邮件名称不能为空");
					return;
				}else if(toAddress == ''){
					tip("收件人不能为空");
					return;
				}else if(startTime == ''){
					tip("开始时间不能为空");
					return;
				}else if(endTime == ''){
					tip("结束时间不能为空");
					return;
				}
				
				var sqls = new Array();
				var sheetnames = new Array();
				$(".querys .row").each(function(i,k){
					var sheetname = $(this).find(".form-control:eq(0)").val();
					var sql=$(this).find(".form-control:eq(1)").val();
					sqls[i]=encodeURIComponent(sql);
					sheetnames[i]=sheetname;
				});
				$.ajax({
			        url: _path+'takedata/editMailTask',
			        type: "post",
			        async: false,
			        dataType: "json",
					data : {
						"taskId" : "${mailTask.id}",
						"projectName" : projectName,
						"toAddress" : toAddress,
						"startTime" : startTime,
						"endTime" : endTime,
						"sqls" : sqls,
						"sheetnames" : sheetnames
					},
					success : function(resultData) {
						tip("提交成功",function(){
							var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							parent.layer.close(index); //再执行关闭
							parent.location.reload();
						});
					}
				});
			});
			if ("${empty mailTask}" == "true") {
				$(".btn-success").click();
			}
		});
		
		function tip(msg,callback){
			layui.use('layer', function(){
		    	var layer = layui.layer;
		    	layer.msg(msg,{
					icon: 1,
					time: 2000 //2秒关闭（如果不配置，默认是3秒）
				},callback);
			});
		}
		$(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});
	</script>
</body>
</html>