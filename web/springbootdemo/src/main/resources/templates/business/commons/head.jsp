<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${param.ctx}/resource/bootstrap/css/templatemo_main.css">
<link rel="stylesheet" href="${param.ctx}/resource/layui/css/layui.css"  media="all">
<link rel="stylesheet" href="${param.ctx}/resource/bootstrap/css/bootstrap-datetimepicker.min.css"  media="all">
<script src="${param.ctx}/resource/js/jquery-1.8.2.min.js?ts=${ts}"></script>
<script src="${param.ctx}/resource/bootstrap/js/bootstrap.min.js"></script>
<script src="${param.ctx}/resource/bootstrap/js/bootstrap-table.js"></script>
<script src="${param.ctx}/resource/bootstrap/js/bootstrap-table-zh-CN.js"></script>
<script src="${param.ctx}/resource/bootstrap/js/bootstrap-datetimepicker.js"></script>
<script src="${param.ctx}/resource/layui/layui.js"></script>
<script type="text/javascript">
	var _path = "${param.ctx}/";
	
	
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}

	/**
	 * 文本框根据输入内容自适应高度
	 * @param                {HTMLElement}        输入框元素
	 * @param                {Number}                设置光标与输入框保持的距离(默认0)
	 * @param                {Number}                设置最大高度(可选)
	 */
	var autoTextarea = function(elem, extra, maxHeight) {
		extra = extra || 0;
		var isFirefox = !!document.getBoxObjectFor
				|| 'mozInnerScreenX' in window, isOpera = !!window.opera
				&& !!window.opera.toString().indexOf('Opera'), addEvent = function(
				type, callback) {
			elem.addEventListener ? elem
					.addEventListener(type, callback, false) : elem
					.attachEvent('on' + type, callback);
		}, getStyle = elem.currentStyle ? function(name) {
			var val = elem.currentStyle[name];

			if (name === 'height' && val.search(/px/i) !== 1) {
				var rect = elem.getBoundingClientRect();
				return rect.bottom - rect.top
						- parseFloat(getStyle('paddingTop'))
						- parseFloat(getStyle('paddingBottom')) + 'px';
			}
			;

			return val;
		} : function(name) {
			return getComputedStyle(elem, null)[name];
		}, minHeight = parseFloat(getStyle('height'));

		elem.style.resize = 'none';

		var change = function() {
			var scrollTop, height, padding = 0, style = elem.style;

			if (elem._length === elem.value.length)
				return;
			elem._length = elem.value.length;

			if (!isFirefox && !isOpera) {
				padding = parseInt(getStyle('paddingTop'))
						+ parseInt(getStyle('paddingBottom'));
			}
			;
			scrollTop = document.body.scrollTop
					|| document.documentElement.scrollTop;

			elem.style.height = minHeight + 'px';
			if (elem.scrollHeight > minHeight) {
				if (maxHeight && elem.scrollHeight > maxHeight) {
					height = maxHeight - padding;
					style.overflowY = 'auto';
				} else {
					height = elem.scrollHeight - padding;
					//style.overflowY = 'hidden';
				}
				;
				style.height = height + extra + 'px';
				scrollTop += parseInt(style.height) - elem.currHeight;
				document.body.scrollTop = scrollTop;
				document.documentElement.scrollTop = scrollTop;
				elem.currHeight = parseInt(style.height);
			}
			;
		};

		addEvent('propertychange', change);
		addEvent('input', change);
		addEvent('focus', change);
		change();
	};
</script>
<style type="text/css">
body {
	background-color: white;
	overflow-x: hidden;
}

.templatemo-content {
	margin-left: 0;
}

.templatemo-content-wrapper {
	padding-bottom: 50px;
}

.fixed-table-loading {
	display: none;
}

.cut {
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

/* #textarea {
	display: block;
	margin: 0 auto;
	overflow: hidden;
	width: 550px;
	font-size: 14px;
	height: 18px;
	line-height: 24px;
	padding: 2px;
} */

textarea {
	outline: 0 none;
	border-color: rgba(82, 168, 236, 0.8);
	box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1), 0 0 8px rgba(82, 168, 236, 0.6);
}
</style>
</head>
</html>