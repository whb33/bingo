define(function() {
    Date.prototype.format = function (fmt) { //author: meizz
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
    };
	require.config({
		baseUrl: 'js/lib/',
		paths: {
			"jquery": ["jquery-3.2.1.min"],
			"login": ["login"],
			"utils": ["utils"],
			"easyui": ["easyui/jquery.easyui.min"],
			"easyui-lang": ["easyui/locale/easyui-lang-zh_CN"],
			"jquery-form": ["jquery.form"],
			"aes": ["cryptojs/aes"],
			"ecb-mode": ["cryptojs/mode-ecb-min"],
			"encrypt": ["encrypt"],
            "kindeditor": ["kindeditor/kindeditor-all"],
            "kindeditor_zh-CN": ["kindeditor/lang/zh-CN"],
            "fileChecker": ["fileChecker"]
		},
	    shim: {
	    	"login": {
	    		deps: ["jquery"]
	    	},
	    	"utils": {
	    		deps: ["jquery"]
	    	},
	    	"easyui": {
	    		deps: ["jquery"]
	    	},
	    	"easyui-lang": {
	    		deps: ["easyui"]
	    	},
	    	"jquery-form": {
	    		deps: ["jquery"]
	    	},
	    	"ecb-mode": {
	    		deps: ["aes"]
	    	},
	    	"encrypt": {
	    		deps: ["aes", "ecb-mode"]
	    	},
            "kindeditor_zh-CN": {
                deps: ["kindeditor"]
            },
            "fileChecker": {
            	deps: ["jquery"]
            }
		},
		map: {
	        '*': {
	            css: 'js/lib/css.min.js'
	        }
	    }
	});
});