<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<jsp:include page="../commons/head.html">
	<jsp:param value="支付列表" name="title" />
	<jsp:param value="${ctx}" name="ctx" />
</jsp:include>
<head>
	<style type="text/css">
		.form-group{
			padding-right:10px;
		}
	</style>
</head>
<body>
<div class="templatemo-content-wrapper">
		<form class="form-inline">
			<div class="form-group">
				<label class="form-label">订单号</label>
				<input type="text" class="form-control" name="id">
			</div>
			<div class="form-group">
				<label class="form-label">商品名称</label>
				<input type="text" class="form-control" style="width:120px;" name="productName">
			</div>
			<div class="form-group">
				<label class="form-label">渠道号</label>
				<input type="text" class="form-control" style="width:120px;" name="channelId">
			</div>
			<div class="form-group">
				<label class="form-label">支付状态</label>
				<select class="form-control" name="orderStatus">
				  <option value="">全部</option>
				  <option value="0">待支付</option>
				  <option value="1">支付中</option>
				  <option value="2">支付成功</option>
				  <option value="3">支付失败</option>
				  <option value="4">已过期</option>
				  <option value="5">退款中</option>
				  <option value="6">退款成功</option>
				</select>
			</div>
			<div class="form-group">
				<label class="form-label">支付方式</label>
				<select class="form-control" name="payWayCode">
				  <option value="">全部</option>
				  <option value="ALIPAY">支付宝</option>
				  <option value="WEIXIN">微信</option>
				</select>
			</div>
			<button type="button" class="btn btn-primary">查询</button>
		</form>
		<div class="templatemo-progress">
		<table width="100%" id="myOrderTable" style="margin-top:20px;"></table>
	</div>
</div>

	
<script type="text/javascript">
$(function(){
	$('#myOrderTable').bootstrapTable({
	    url: _path+'/merchantOrder/queryList',
	    striped:true,
	    contentType:"application/x-www-form-urlencoded",
	    striped: true,
	    method:"post",
        pagination: true,
        singleSelect: false,
        pageSize: 10,
        pageList: [10, 20, 40,80,160],
        sidePagination: "server", //服务端请求
        queryParams: queryParams,
        queryParamsType:"",
        paginationPreText:"上一页",
		paginationNextText:"下一页",
        formatLoadingMessage: function () {
		  return '正在努力地加载数据中，请稍候……';
		},
		rowStyle:function rowStyle(row, index){
        	return {
     		   css: {"font-size": "14px"}
     		};
        },
	    columns: [{
	    	field: 'Number',
	    	title: '序号',
	    	formatter: function (value, row, index) {
	    		var param = $('#myOrderTable').bootstrapTable('getOptions');
	    		return Number(param.pageSize*(param.pageNumber-1)+index+1);
	    	}
	    },{
	        field: 'id',
	        align:'center',
	        title: '<span style="font-size: 14px;font-weight: bold;">订单号</span>'
	    },{
	        field: 'productName',
	        align:'center',
	        title: '<span style="font-size: 14px;font-weight: bold">商品名称</span>'
	    },
	    {
	        field: 'orderAmount',
	        align:'center',
	        title: '<span style="font-size: 14px;font-weight: bold">价格</span>'
	    },
	    {
	        field: 'orderTime',
	        align:'center',
	        title: '<span style="font-size: 14px;font-weight: bold">订单时间</span>',
	    	formatter: function (value, row, index) {
	    		var orderTime = row.orderTime;
	    		var now = new Date(orderTime);
	    		return now.Format("yyyy-MM-dd hh:mm");
	    	}
	    },
	    {
	        field: 'successTime',
	        align:'center',
	        title: '<span style="font-size: 14px;font-weight: bold">支付成功时间</span>',
	    	formatter: function (value, row, index) {
	    		var successTime = row.successTime;
	    		if(successTime==null){
	    			return "";
	    		}else{
	    			var now = new Date(successTime);
		    		return now.Format("yyyy-MM-dd hh:mm");
	    		}
	    	}
	    },
	    {
	        field: 'channelId',
	        align:'center',
	        title: '<span style="font-size: 14px;font-weight: bold">渠道号</span>'
	    },
	    {
	        field: 'payWayName',
	        align:'center',
	        title: '<span style="font-size: 14px;font-weight: bold">支付方式</span>'
	    },
	    {
	        field: 'orderStatus',
	        align:'center',
	        title: '<span style="font-size: 14px;font-weight: bold">状态</span>',formatter:function(value,row,index){
	        	var orderStatus = row.orderStatus;
	        	if(orderStatus == 0){
	        		return "待支付";
	        	}else if(orderStatus == 1){
	        		return "支付中";
	        	}else if(orderStatus == 2){
	        		return "支付成功";
	        	}else if(orderStatus == 3){
	        		return "支付失败";
	        	}else if(orderStatus == 4){
	        		return "已过期";
	        	}else if(orderStatus == 5){
	        		return "退款中";
	        	}else if(orderStatus == 6){
	        		return "退款成功";
	        	}
	        }
	    }, 
		{
            field: 'operate',
            title: '<span style="font-size: 14px;font-weight: bold">操作</span>',
            align: 'center',
            formatter:function(value,row,index){
            	var orderStatus = row.orderStatus;
        		var payWayCode = row.payWayCode;
            	var str = "";
            	/* str += "<button style=\"width:50px;padding:6px 10px;margin-right:10px;\" onclick=\"javascript:;\" class='btn btn-primary'>查看</button>"; */
            	/* if(orderStatus == "2" || orderStatus == "5"){
            		str += "<button style=\"width:50px;padding:6px 10px;\" onclick=\"javascript:refund('"+row.id+"','"+payWayCode+"');\" class='btn btn-primary'>退款</button>";
            	} */
            	/** else if(row.status == "2"){
            		str += "<button style=\"width:50px;padding:6px 10px;\" onclick=\"javascript:online('"+row.id+"');\" class='btn btn-primary'>上线</button>";
            	} */
            	return str;
	         }
        }
		]
	});
	
	function queryParams(params) {
        return {
        	id:$("input[name='id']").val().trim(),
        	productName:$("input[name='productName']").val().trim(),
        	channelId:$("input[name='channelId']").val().trim(),
        	orderStatus:$("select[name='orderStatus']").val().trim(),
        	payWayCode:$("select[name='payWayCode']").val().trim(),
            pageSize:params.pageSize,
            pageIndex:params.pageNumber
        };
    }
	
	//查询
	$(".btn-primary").click(function () {
		var params = $('#myOrderTable').bootstrapTable('getOptions');  
        queryParams(params);
        $('#myOrderTable').bootstrapTable('refresh', params);
    });
	
	function GetQueryString(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	
});

function refund(id,payWayCode){
	if(payWayCode == "WEIXIN"){
		alert("微信退款暂未支持");
	}else{
		//询问框
		var confirm = parent.layer.confirm('确认进行退款？', {
		  btn: ['确认','取消'] //按钮
		}, function(){
			//parent.layer.msg('的确很重要', {icon: 1});
			
				$.ajax({
					url: _path+'merchantOrder/refund',
					type: "post",
					async: true,
					dataType: "json",
					data: {"id":id},
					success: function (res) {
						var status = res.status;
						if(status == 1){
							window.location.href="http://hf.mm.10086.cn/2016tyjf_dev/jiechao/dqpay/jsp/alipay/refund.jsp?payId="+id;
						}else{
							parent.layer.msg('退款失败', {icon: 1});
						}
					}
				});
			
			parent.layer.close(confirm);
		}, function(){});
	}
}
</script>
</body>
</html>