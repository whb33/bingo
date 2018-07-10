<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<jsp:include page="../commons/head.html">
	<jsp:param value="手动提数" name="title" />
	<jsp:param value="${ctx}" name="ctx" />
</jsp:include>
<head>
</head>
<body>
	<div class="templatemo-content-wrapper">
		<h1>
			手动提数
			<button type="submit" class="btn btn-primary" onclick="javascript:location.href='${ctx}/takedata/manualHistory'" style="float:right;">历史</button>
		</h1>
		<p class="margin-bottom-15" style="margin-top:10px;">在这里你能快速的查询想要的数据</p>
		<div class="querys">
			<c:if test="${not empty manualInfo}">
				<c:forEach var="sheet" items="${manualInfo.sheetnames}" varStatus="status">
					<div class="row row${status.index+1}">
						<div class="col-md-12 margin-bottom-15">
				        	<label for="firstName" class="control-label">工作表名称 ${status.index+1}</label>
				        	<input type="text" class="form-control" id="firstName" placeholder="请输入工作表名称..." value="${manualInfo.sheetnames[status.index]}">
						</div>
						<div class="col-md-12 margin-bottom-15">
			                <label for="notes${status.index+1}">查询语句 ${status.index+1}</label>
							<textarea class="form-control" rows="3" id="notes${status.index+1}">${manualInfo.sqls[status.index]}</textarea>
						</div>	
					</div>
					<script type="text/javascript">
						$(document).ready(function(){
							autoTextarea(document.getElementById("notes"+i));
						});
					</script>
				</c:forEach>
				<input type="hidden" id="manualInfoLength" value="${fn:length(manualInfo.sheetnames)}" />
			</c:if>
		</div>
		
		<div style="display:none;" id="templateQuery">
			<div class="row">
				<div class="col-md-12 margin-bottom-15">
		        	<label for="firstName" class="control-label">工作表名称</label>
		        	<input type="text" class="form-control" id="firstName" placeholder="请输入工作表名称..." value="">
				</div>
				<div class="col-md-12 margin-bottom-15">
	                <label for="notes">查询语句</label>
					<textarea class="form-control" rows="3" id="notes">select * from </textarea>
				</div>
			</div>
		</div>
		
		<div class="row templatemo-form-buttons">
			<div class="col-md-12">
				<button type="button" class="btn btn-success">Add</button>
				<button type="submit" class="btn btn-primary">Query</button>
				<button type="reset" class="btn btn-default">Reset</button>
				<!-- <span style="padding-left:15px;">
				<label class="checkbox-inline">
                    <input type="checkbox" id="inlineCheckbox1" value="option1" checked=""> 调试执行时间
                </label>
                <label class="checkbox-inline">
                    <input type="checkbox" id="inlineCheckbox1" value="option1" checked=""> Server status
                </label>
                <label class="checkbox-inline">
                    <input type="checkbox" id="inlineCheckbox1" value="option1" checked=""> Server status
                </label>
				</span> -->
			</div>
		</div>
	</div>
	<script type="text/javascript">
		/* var layer;
		layui.use('layer', function(){
    		layer = layui.layer;
		}); */
		var uuid = '';
		var i = 1;
		var sqls = new Array();
		var sheetnames = new Array();
		$(document).ready(function(){
			
			//增加提数表单
			$(".btn-success").bind("click",function(){
				var template = $("#templateQuery").html();
				template = template.replace("row","row row"+i);
				template = template.replaceAll("notes","notes"+i);
				template = template.replace("工作表名称","工作表名称 "+i);
				template = template.replace("查询语句","查询语句 "+i);
				$(".querys").append(template);
				autoTextarea(document.getElementById("notes"+i));
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
			$(".templatemo-form-buttons .btn-primary").bind("click",function(){
				
				var interpretation = false;
    	    	$(".querys input").each(function(){
    	    		if($(this).val() == ''){
    	    			interpretation = true;
    	    			parent.layer.msg('工作表名称不能为空');
    	    			return false;
    	    		}
    	    	});
    	    	
    	    	if(interpretation){
    	    		return;
    	    	}
    	    	
    	    	$(".querys textarea").each(function(){
    	    		if($(this).val() == ''){
    	    			interpretation = true;
    	    			parent.layer.msg('查询语句不能为空');
    	    			return false;
    	    		}
    	    	});
    	    	
    	    	if(interpretation){
    	    		return;
    	    	}
				
    	    	$(".querys .row").each(function(i,k){
					var sheetname = $(this).find(".form-control:eq(0)").val();
					var sql=$(this).find(".form-control:eq(1)").val();
					sqls[i]=encodeURIComponent(sql);
					sheetnames[i]=sheetname;
				});
    	    	var loading = parent.layer.load(2);
				$.ajax({
			        url: _path+'takedata/manualTake',
			        type: "post",
			        async: true,
			        dataType: "json",
			        data: {"sqls":sqls,"sheetnames":sheetnames},
			        success: function (res) {
			        	parent.layer.close(loading);
			        	if(res.status == '1'){
			        		initQueryI = 0;
			        		parent.layer.open({
								type: 1
						        ,title:"临时查询" //不显示标题栏
								,btn: ['下载']
					    		,yes:function(index,layero){
					    	    	parent.layer.prompt({title: '请输入导出Excel名称',formType:0}, function(text, index){
					    	    		parent.layer.msg('正在为您下载...');
					    	    		console.log(encodeURI(encodeURI(text)));
					    	    		window.location.href="${ctx}/takedata/downloadManualFile/"+uuid+"?excelName="+encodeURI(encodeURI(text));
					    	    		parent.layer.close(index);
					    	    	});
					    	  	}
						        ,closeBtn: 1
						        ,area: ['80%','520px']
					    		,offset : ["100px"]
								,maxmin: true
						        ,shade: 0.8
						        ,moveType: 1 //拖拽模式，0或者1
								,content:'<div class="panel-body"><ul class="nav nav-tabs" id="myTabs"></ul><div class="tab-content"></div></div>'
								,success: function(layero, index){
					        		uuid = res.data.uuid;
					        		initQueryData(res.data.sheet);
								}
							});
			        	}else{
			        		layui.use('layer', function(){
						    	var layer = layui.layer;
						    	layer.msg(res.msg,{
									icon: 2,
									time: 2000 //2秒关闭（如果不配置，默认是3秒）
								}, function(){});
			        		});
			        	}
			        },error: function(){
			        	parent.layer.close(loading);
			        	layui.use('layer', function(){
					    	var layer = layui.layer;
					    	layer.msg("服务器异常",{
								icon: 2,
								time: 2000 //2秒关闭（如果不配置，默认是3秒）
							}, function(){});
		        		});
			        }
				});
			});
			
			if($("#manualInfoLength").val() != undefined){
				i = Number($("#manualInfoLength").val());
			}else{
				$(".btn-success").click();
			}
			
		});
		
		var initQueryI = 0;
		function initQueryData(sheet){
			for(var sname in sheet){
        		colums=[];
        		data=[];
        		var queryList = sheet[sname];
        		if(initQueryI == 0){
        			isActive = "active";
        		}else{
        			isActive = "";
        		}
        		//添加列表
        		parent.$("#myTabs").append("<li role='presentation' class='"+isActive+"'><a href='#takedate"+initQueryI+"'>"+sname+"</a></li>");
        		parent.$(".panel-body .tab-content").append("<div role='tabpanel' class='tab-pane "+isActive+"' id='takedate"+initQueryI+"'><table id='myOrderTable"+initQueryI+"' style='width:100%;'></table></div>");
        		
        		//获取参数标题
        		if(queryList){
 	        	   //获取参数标题
 	        	   for(var k in queryList){
 	        		   var docList=queryList[k];
 	        		   for(var fieldName in docList){
 	        			   colums.push({
        				        field: fieldName,title: fieldName, sortable: true ,width:150
        				   });
 	        		   }
 	        		  break;
 	        	   }
 	        	   //获取参数值列表
 	        	   for(var k in queryList){
 	        		   var docList=queryList[k];
 	        		  var row = {};
 	        		   for(var fieldName in docList){
 	        			   row[fieldName]=docList[fieldName];
 	        		   }
 	        		  data.push(row);
 	        	   }
 	            }
        		console.log("myOrderTable"+initQueryI);
        		console.log(colums);
        		initTable("myOrderTable"+initQueryI,colums,data);
        		++initQueryI;
        	}
			//初始化导航条
			parent.$('#myTabs a').click(function(e) {
				e.preventDefault()
				$(this).tab('show');
				
				parent.$(".panel-body .tab-pane").removeClass("active");
				parent.$($(this).attr("href")).addClass("active");
			})
		}
		
		function initTable(id,colums,data){
			parent.$("#"+id).bootstrapTable({
				pagination: true,
		        singleSelect: false,
		        pageSize: 10,
		        pageList: [10, 20, 40,80,160],
		        sidePagination: "client",
		        paginationPreText:"上一页",
				paginationNextText:"下一页",
		        columns:colums,
		        data:data
			});
		};
		
		var DownLoadFile = function (options) {
		    var config = $.extend(true, { method: 'post' }, options);
		    var $iframe = $('<iframe id="down-file-iframe" />');
		    var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
		    $form.attr('action', config.url);
		    for (var key in config.data) {
		        $form.append('<input type="hidden" name="' + key + '" value="' + config.data[key] + '" />');
		    }
		    $iframe.append($form);
		    $(document.body).append($iframe);
		    $form[0].submit();
		    $iframe.remove();
		}
	</script>
</body>
</html>