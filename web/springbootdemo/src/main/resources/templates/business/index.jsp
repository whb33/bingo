<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<jsp:include page="commons/head.html">
	<jsp:param value="自动提数" name="title" />
	<jsp:param value="${ctx}" name="ctx" />
</jsp:include>
<head>
	<!-- ECharts单文件引入 -->
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
	<style type="text/css">
		.templatemo-content-wrapper{
			padding-bottom:0px;
		}
		.panel-body{
			padding:0px;
		}
		.user-survey table{
			width:100%;
		}
		.user-survey table tr td{
			border:1px solid #ddd;
		}
		.user-survey table tr td span:nth-child(1){
			padding:0px 10px;
		}
		.user-survey table tr td span:nth-child(2){
			font-size:26px;font-weight:bold;
		}
		.user-survey table tr td p:nth-child(2){
			color:#ff5622;padding:0px 10px;font-weight:bold;
		}
		.user-survey table tr td:first-child {
			letter-spacing:1px;text-align: center;
		}
		.user-survey table tr td:not(:first-child){
			padding:20px 10px;text-align:left;line-height:28px;text-align: center;
		}
	</style>
</head>
<body>
	<div class="templatemo-content-wrapper">
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">用户概况</h4>
					</div>
					<div class="panel-body user-survey">
						<table>
							<tr>
								<td width="2%;">昨天</td>
								<td width="19.6%;">
									<p>
										<span>登录人次</span>
										<span class="yesterday_login_people_survey">0</span>
									</p>
									<p class="before_yesterday_login_people_survey">0(0%)</p>
								</td>
								<td width="19.6%;">
									<p>
										<span>登录人数</span>
										<span class="yesterday_login_number_survey">0</span>
									</p>
									<p class="before_yesterday_login_number_survey">0(0%)</p>
								</td>
								<td width="19.6%;">
									<p>
										<span>浏览量 ( PV )</span>
										<span class="yesterday_pv_survey">0</span>
									</p>
									<p class="before_yesterday_pv_survey">0(0%)</p>
								</td>
								<td width="19.6%;">
									<p>
										<span>访客量 ( UV )</span>
										<span class="yesterday_uv_survey">0</span>
									</p>
									<p class="before_yesterday_uv_survey">0(0%)</p>
								</td>
								<td width="19.6%;">
									<p>
										<span>分享量</span>
										<span class="yesterday_share_member_surver">0</span>
									</p>
									<p class="before_yesterday_share_member_surver">0(0%)</p>
								</td>
							</tr>
							
							<tr>
								<td width="2%;">今天</td>
								<td width="19.6%;">
									<p>
										<span>登录人次</span>
										<span class="login_people_survey">0</span>
									</p>
								</td>
								<td width="19.6%;">
									<p>
										<span>登录人数</span>
										<span class="login_number_survey">0</span>
									</p>
								</td>
								<td width="19.6%;">
									<p>
										<span>浏览量 ( PV )</span>
										<span class="pv_survey">0</span>
									</p>
								</td>
								<td width="19.6%;">
									<p>
										<span>访客量 ( UV )</span>
										<span class="uv_survey">0</span>
									</p>
								</td>
								<td width="19.6%;">
									<p>
										<span>分享量</span>
										<span class="share_member_surver">0</span>
									</p>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="templatemo-content-wrapper">
		
		<div class="row">
			<div class="col-md-12 margin-bottom-30">
				<div class="panel panel-default">
					<div class="panel-heading">
						<ul class="nav nav-tabs" role="tablist">
		                  <li class="active"><a href="#pv" role="tab" data-toggle="tab">浏览量</a></li>
		                  <li><a href="#uv" onclick="javascript:generate('uv_main',data.uv);" role="tab" data-toggle="tab">访客量</a></li>
		                  <li><a href="#login" onclick="javascript:generate('login_main',data.loginPeople);" role="tab" data-toggle="tab">登录量</a></li>
		                  <li><a href="#share" onclick="javascript:generate('share_main',data.shareMember);" role="tab" data-toggle="tab">分享量</a></li>
		                  <!-- <li><a href="#new" role="tab" data-toggle="tab">新增量</a></li> -->
		                </ul>
					</div>
					<div class="panel-body">
						<!-- Tab panes -->
						<div class="tab-content">
							<div class="tab-pane fade in active" id="pv">
								<div id="pv_main" style="height:400px"></div>
							</div>
							<div class="tab-pane fade" id="uv">
								<div id="uv_main" style="height:400px"></div>
							</div>
							<div class="tab-pane fade" id="login">
								<div id="login_main" style="height:400px"></div>
							</div>
							<div class="tab-pane fade" id="share">
								<div id="share_main" style="height:400px"></div>
							</div>
							<div class="tab-pane fade" id="new">
								new
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
        
     	
     	var data;
     	$(document).ready(function(){
     		$.ajax({
		        url: _path+'survey',
		        type: "post",
		        async: false,
		        dataType: "json",
		        data: {},
		        success: function (res) {
		        	if(res.status == '1'){
		        		data = res.data;
		        		generate('pv_main',data.pv);
		        		
		        		getSurvey('pv_survey',data.pv);
		        		getSurvey('uv_survey',data.uv);
		        		getSurvey('login_people_survey',data.loginPeople);
		        		getSurvey('login_number_survey',data.loginNumber);
		        		getSurvey('share_member_surver',data.shareMember);
		        	}
		        }
     		});
     	})
     	
     	function getSurvey(id,colu){
     		var today = 0;
     		var yesterday = 0;
     		var beforeYesterday = 0;
     		var legend = data.projectNames;
     		for(var l in legend){
         		var coluLegend = colu[legend[l]];
         		today += coluLegend[coluLegend.length-1];
         		yesterday += coluLegend[coluLegend.length-2];
         		beforeYesterday += coluLegend[coluLegend.length-3];
     		}
     		$("."+id).html(today);
     		$("."+"yesterday_"+id).html(numFormat(yesterday));
     		var mark = "";
     		if(yesterday<beforeYesterday){
     			mark = "-";
     		}
     		var before_yesterday = beforeYesterday == 0 ? "0(0%)" : (numFormat(beforeYesterday) + "("+mark+((1-yesterday/beforeYesterday)*100).toFixed(1)+"%)");
     		$("."+"before_yesterday_"+id).html(before_yesterday);
     	}
     	
     	function numFormat(num){
     		return String(num).replace(/(\d)(?=(\d{3})+$)/g, "$1,");
     	}
 		
 		function generate(id,colu){
     		if($("#"+id).hasClass("isClick")){
     			return;
     		}
     		setTimeout(function(){
     			var xAxis = data.dates;
        		var legend = data.projectNames;
        		var seriesData = [];
     			for(var l in legend){
        			seriesData.push({
        				name:legend[l],
        	            type:'line',
        	            stack: '总量'+l,
        	            data:colu[legend[l]]
        			});
        		}
     			createCharts(id,legend,xAxis,seriesData);
     			$("#"+id).addClass("isClick");
     		},200);
 		}
 		
 		function createCharts(id,legend,xAxis,seriesData){
 		// 使用
            require(
                [
                    'echarts',
                    'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                ],
                function (ec) {
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init(document.getElementById(id));
                    var option = {
                    	    tooltip : {
                    	        trigger: 'axis'
                    	    },
                    	    legend: {
                    	        data:legend
                    	    },
                    	    toolbox: {
                    	        show : true,
                    	        feature : {
                    	            //mark : {show: true},
                    	            //dataView : {show: true, readOnly: false},
                    	            //magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    	            //restore : {show: true},
                    	            //saveAsImage : {show: true}
                    	        }
                    	    },
                    	    calculable : false,
                    	    xAxis : [
                    	        {
                    	            type : 'category',
                    	            boundaryGap : false,
                    	            data : xAxis
                    	        }
                    	    ],
                    	    yAxis : [
                    	        {
                    	            type : 'value'
                    	        }
                    	    ],
                    	    series : seriesData
                    	};
                 	// 为echarts对象加载数据 
                    myChart.setOption(option);
                }
           	)
 		}
    </script>
</body>
</html>