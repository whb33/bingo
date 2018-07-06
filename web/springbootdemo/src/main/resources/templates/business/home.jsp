<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<title>RichInfo提数程序</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="viewport" content="width=device-width">
<link rel="stylesheet" href="${ctx}/resource/bootstrap/css/templatemo_main.css">
<script src="${ctx}/resource/js/jquery-1.8.2.min.js?ts=${ts}"></script>
<script src="${ctx}/resource/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/resource/bootstrap/js/bootstrap-table.js"></script>
<script src="${ctx}/resource/bootstrap/js/templatemo_script.js"></script>
<link rel="stylesheet" href="${ctx}/resource/layui/css/layui.css"  media="all">
<script src="${ctx}/resource/layui/layui.js"></script>
<style>
	.layui-nav-item a{
		text-decoration:none;
	}
	.layui-nav-child dd a{
		line-height:36px;
		text-align:center;
		text-decoration:none;
	}
	.fixed-table-loading{
		display:none;
	}
	
	.table{
		font-size:12px;
	}
		
	.table th,.table td{
		overflow:hidden;
		text-overflow:ellipsis;
		-o-text-overflow:ellipsis;
		-webkit-text-overflow:ellipsis;
		-moz-text-overflow:ellipsis;
		white-space:nowrap;
	}
</style>
<script type="text/javascript">
function IsPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}
$(document).ready(function(){
	//判断是否电脑端
	if(IsPC()){
		$(".layui-nav .phone").hide();
	}else{
		$(".layui-nav .pc").hide();
	}
	//增加textarea上下键触发
});
</script>
<body>
    <div class="navbar navbar-inverse" role="navigation">
      <%-- <img src="${ctx}/resource/images/logo.jpg" style="position: absolute;z-index: 99999;height: 60%;margin-top: 10px;margin-left: 10px;"> --%>
      <ul class="layui-nav" style="text-align:right;">
		  <%--<li class="layui-nav-item">
		    <a href="javascript:openMenu('${ctx}/console');">控制台<!-- <span class="layui-badge">99</span> --></a>
		  </li>--%><!--
		  <li class="layui-nav-item pc">
		    <a href="javascript:void(0);">个人中心<span class="layui-badge-dot"></span></a>
		  </li> -->
		  <li class="layui-nav-item pc">
		    <a href="javascript:openMenu('${ctx}/whisper');">消息
		    	<c:if test="${unread>0}">
		    		<span class="layui-badge">${unread}</span>
		    	</c:if>
		    </a>
		  </li>
		  <li class="layui-nav-item phone">
		    <a href="javascript:void(0);">提数<span class="layui-badge-dot"></span></a>
		  </li>
		  <li class="layui-nav-item" lay-unselect="">
		    <a href="javascript:;"><img src="http://t.cn/RCzsdCq" class="layui-nav-img">${userName}</a>
		    <dl class="layui-nav-child">
		      <dd><a href="javascript:openMenu('${ctx}/setting');">修改信息</a></dd>
		      <!-- <dd><a href="javascript:void(0);">修改信息</a></dd> -->
		      <dd><a href="javascript:void(0);">安全管理</a></dd>
		      <dd><a href="javascript:location.href='${ctx}/logout';">退出</a></dd>
		    </dl>
		  </li>
		</ul>
    </div>
	<div class="template-page-wrapper">
		<div class="navbar-collapse collapse templatemo-sidebar">
			<ul class="templatemo-sidebar-menu">
				<%--<li>
					<form class="navbar-form">
						<input type="text" class="form-control" id="templatemo_search_box" placeholder="Search...">
						<span class="btn btn-default">Go</span>
					</form>
				</li>--%>
				<c:forEach var="meue" items="${secMeueList}">
					<c:if test="${not empty meue.menuUrl}">
						<li class="meue_${meue.functionId}" <c:if test="${meue.functionId == functionId}"> class="active"</c:if>><a href="javascript:openWin('${ctx}${meue.menuUrl}','meue_${meue.functionId}')"><i class="${meue.remark}"></i>${meue.functionName}</a></li>
					</c:if>
					<c:if test="${empty meue.menuUrl}">
						<li class="sub">
				            <a href="javascript:;">
				              <i class="${meue.remark}"></i>${meue.functionName}<div class="pull-right" style="margin-top: 5px;"><span class="caret"></span></div>
				            </a>
				            <c:forEach var="childMenu" items="${meue.childMenu}">
				            	<ul class="templatemo-submenu">
				              		<li class="meue_${childMenu.functionId}" <c:if test="${childMenu.functionId == functionId}"> class="active"</c:if>><a href="javascript:openWin('${ctx}${childMenu.menuUrl}','meue_${childMenu.functionId}')">${childMenu.functionName}</a></li>
				              	</ul>
				            </c:forEach>
				          </li>
					</c:if>
				</c:forEach>
				<!-- <li><a href="javascript:location.href='/logout';" data-toggle="modal" ><i class="fa fa-sign-out"></i>退出</a></li> -->
			</ul>
		</div>
	</div>

	<%-- <div class="template-page-wrapper">
	      <div class="navbar-collapse collapse templatemo-sidebar">
	        <ul class="templatemo-sidebar-menu">
	          <li>
	            <form class="navbar-form">
	              <input type="text" class="form-control" id="templatemo_search_box" placeholder="Search...">
	              <span class="btn btn-default">Go</span>
	            </form>
	          </li>
	          <li class="active"><a href="#"><i class="fa fa-home"></i>Dashboard</a></li>
	          <li class="sub open">
	            <a href="javascript:;">
	              <i class="fa fa-database"></i> Nested Menu <div class="pull-right"><span class="caret"></span></div>
	            </a>
	            <ul class="templatemo-submenu">
	              <li><a href="#">Aenean</a></li>
	              <li><a href="#">Pellentesque</a></li>
	              <li><a href="#">Congue</a></li>             
	              <li><a href="#">Interdum</a></li>
	              <li><a href="#">Facilisi</a></li>
	            </ul>
	          </li>
	          <li><a href="data-visualization.html"><i class="fa fa-cubes"></i><span class="badge pull-right">9</span>Data Visualization</a></li>
	          <li><a href="maps.html"><i class="fa fa-map-marker"></i><span class="badge pull-right">42</span>Maps</a></li>
	          <li><a href="tables.html"><i class="fa fa-users"></i><span class="badge pull-right">NEW</span>Manage Users</a></li>
	          <li><a href="preferences.html"><i class="fa fa-cog"></i>Preferences</a></li>
	          <li><a href="javascript:;" data-toggle="modal" data-target="#confirmModal"><i class="fa fa-sign-out"></i>Sign Out</a></li>
	        </ul>
	      </div>
      </div> --%>
      
      <div class="templatemo-content-wrapper">
        <!-- <div class="templatemo-content">
          <ol class="breadcrumb">
            <li><a href="index.html">Admin Panel</a></li>
            <li><a href="#">Dashboard</a></li>
            <li class="active">Overview</li>
            <li><a href="sign-in.html">Sign In Form</a></li>
          </ol>
          <h1>Dashboard</h1>
        </div> -->
        <div class="templatemo-content">
	        <iframe id="mainright" name="mainright" marginwidth="1" marginheight="1" hspace="0" vspace="0" scrolling="yes" frameborder="0" style="width: 100%;height:90%;" src="${ctx}${iframesrc}"></iframe>
        </div>
      </div>
      
      <footer class="templatemo-footer">
        <div class="templatemo-copyright">
          <p>Copyright © 2016 Your Company Name</p>
        </div>
      </footer>
      <script type="text/javascript">
      	var layer;
		layui.use('layer', function(){
  			layer = layui.layer;
		});
      	function openWin(url,_this){
      		$(".layui-nav .layui-nav-item").removeClass("layui-this");
      		$(".templatemo-sidebar-menu li").removeClass("active");
      		$("."+_this).addClass('active');
      		if (url.indexOf("?") >= 0){
    			$("#mainright").attr("src", url + "&d=" + new Date().getTime());
      		}else{
    			$("#mainright").attr("src", url + "?d=" + new Date().getTime());
      		}
      	}
      	function openMenu(url){
    		$("#mainright").attr("src", url);
      	}
      	
      	layui.use('element', function(){
      	  var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
      	  
      	  //监听导航点击
      	  element.on('nav(demo)', function(elem){
      	    //console.log(elem)
      	    layer.msg(elem.text());
      	  });
      	});
      </script>
</body>
</html>