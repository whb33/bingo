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
	.unread{
		color:red;
	}
	.system-item{
		line-height: 24px;
		position: relative;
		padding: 24px 16px;
	    background-color: #fff;
	    border-radius: 4px;
	    /* box-shadow:0 2px 4px 0 rgba(121,146,185,.54); */
	    /* border-bottom:1px solid #edf0f1; */
	}
	.system-item .top .title{
		color:#333;
    	font-weight:700;
	}
	.system-item .top .time{
		color: #999;
	    font-size: 12px;
	    line-height: 22px;
	    margin: 0 10px;
	}
	.system-item .bottom{
		color:#666;
    	padding-left:8px;
	}
	.system-item .bottom .text{
		word-break: break-all;
	}
	.bottom_tool{position:relative;line-height: 30px;text-align: center;}
	.bottom_tool i{display:block;height:1px;background:#edf0f1;position:absolute;width:100%;top:15px;}
	.bottom_tool p{display:inline-block;background:#fff;padding:0 15px;text-align: center;margin:0 auto;position:relative;z-index:2;color:#99a2aa;}
</style>
</head>
<body>
	<div class="templatemo-content-wrapper">
		<h1>系统通知</h1>
		<div class="whisper">
			<input type="hidden" name="pageNum" value="1" />
			<!-- <div class="system-item">
				<div class="top">
					<span class="title">
						《Fate/Grand Order》iOS版本今日正式上线
					</span>
					<span class="time">
						2016年9月29日 16:59
					</span>
				</div>
				<div class="bottom">
					<span class="text">
						FGO国服终于来了！Fate系列首款正版手游《Fate/Grand Order》（中文名《命运-冠位指定》）iOS版本现已正式上线，并将于10月13日开启全平台公测。请至
					</span>
				</div>
			</div> -->
			<c:forEach var="lineMessage" items="${lineMessages}" varStatus="status">
				<div class="system-item">
					<div class="top">
						<span class="title">
							<c:if test="${lineMessage.type == 1}">
								[<span class="unread">未读</span>]
							</c:if>
							 ${lineMessage.title}
						</span>
						<span class="time">
							<fmt:formatDate value="${lineMessage.createTime}" pattern="yyyy年MM月dd HH:mm"/>
						</span>
					</div>
					<div class="bottom">
						<span class="text">
							${lineMessage.content}
						</span>
					</div>
				</div>
				
				<c:if test="${(status.index+1) != fn:length(lineMessages)}">
					<div class="bottom_tool">
						<i></i>
						<p></p>
					</div>
				</c:if>
				<c:if test="${(status.index+1)==fn:length(lineMessages) && fn:length(lineMessages) < 20}">
					<div class="bottom_tool">
						<i></i>
						<p>没有更多信息</p>
					</div>
				</c:if>
			</c:forEach>
			
			<c:if test="${fn:length(lineMessages) == 20}">
				<script type="text/javascript">
					var canScroll = true;
					//页面滚到底部异步加载下一页数据
				    $(window).scroll(function () {
				        //已经滚动到上面的页面高度
				        var scrollTop = parseFloat($(this).scrollTop()),
				        //页面高度
				            scrollHeight = $(document).height(),
				        //浏览器窗口高度
				            windowHeight = parseFloat($(this).height()),
				            totalHeight = scrollTop + windowHeight;
				
				        //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
				        if ((scrollTop + windowHeight >= scrollHeight - 0.7) && canScroll) {
				            $("input[name='pageNum']").val(Number($("input[name='pageNum']").val()) + 1);
				            $.ajax({
				                type: 'post',
				                url: '${ctx}/getLineMessage',
				                data: {"pageNum":$("input[name='pageNum']").val()},
				                beforeSend: function (XMLHttpRequest) {
				    	            console.log('正在加载数据...'+$("input[name='pageNum']").val());
				                },
				                success: function (res) {
				                	var lineMessages = res.data.lineMessages;
				                	console.log(lineMessages);
				                	if(lineMessages.length == 0){
				                		console.log('已加载全部数据！');
				                		canScroll = false;
				                	}else{
				                		for(var i in lineMessages){
				                			var line = lineMessages[i];
				                			var template = $("#template").html();
					        				template = template.replaceAll("lineMessageTitle",line.title);
					        				template = template.replaceAll("lineMessageCreateTime",new Date(line.createTime).Format("yyyy年MM月dd hh:mm"));
					        				template = template.replaceAll("lineMessageContent",line.content);
					                		console.log("加载出了"+lineMessages.length)
					                		if(((Number(i)+1) != lineMessages.length)){
					                			template = template.replaceAll("bottom_tool",'<div class="bottom_tool"><i></i><p></p></div>');
					                		}else{
					                			if(lineMessages.length<20){
					                				template = template.replaceAll("bottom_tool",'<div class="bottom_tool"><i></i><p>没有更多信息</p></div>');
							                		canScroll = false;
						        				}
					                		}
					                		$(".whisper").append(template);
				                		}
				                	}
				                    /* if (data.length == 0) {
					    	            console.log('正在加载数据...');
				                        $("#loadMore").removeClass('hidden').text('已加载全部数据！');
				                    } */
				                    /* var data = getTime(data);
				                    for (var i = 0, length = data.length; i < length; i++) {
				                        $("#infoList").append("<li><a href='public_content.html?id=" + data[i].Id + "&f=" + first + "&s=" + second + "'><span>" + data[i].Title + "</span><span class='time'>" + data[i].PublishTime + "</span></a></li>");
				                    } */
				                },
				                error: function (XMLHttpRequest, textStatus, errorThrown) {
				    	            console.log('数据加载失败，请重试！');
				                }
				            });
				        }
				    });
				</script>
			</c:if>
		</div>

		<div style="display: none;" id="template">
			<div class="system-item">
				<div class="top">
					<span class="title">lineMessageTitle</span>
					<span class="time">lineMessageCreateTime</span>
				</div>
				<div class="bottom">
					<span class="text">lineMessageContent</span>
				</div>
			</div>
			bottom_tool
		</div>
	</div>
	
</body>
</html>