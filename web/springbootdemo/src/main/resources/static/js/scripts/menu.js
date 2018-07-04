require(['require.config'], function() {
	require(['jquery', 'encrypt', "easyui", 'easyui-lang'], function ($) {
		$('.menuLeft').height(document.documentElement.clientHeight - 60);
		$('#page_content').height(document.documentElement.clientHeight - 60);
		$('.menu_1').find('a:eq(0)').click();
		$('#loginOut').click(function() {
	          $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
	             if (r) {
	            	 $.ajax({
	            		 url: basePath + "/logout",
	            		 type: "POST",
	            		 dataType: "json",
	            		 success: function(data) {
	            			 window.location.href = basePath + '/login'
	         			}
	            	 });
	             }
	         });
	 	});
		$('#changePassword').click(function() {
			$('#w').window('open');
		});
		$('#btnCancel').click(function() {
			$('#w').window('close');
		});
		$('#btnEp').click(function() {
			var $newpass = $('#txtNewPass');
		    var $rePass = $('#txtRePass');
		    var $txtOriginPass = $('#txtOriginPass');
		    if ($txtOriginPass.val() == '') {
		        msgShow('系统提示', '请输入原密码！', 'warning');
		        return false;
		    }
		    if ($newpass.val() == '') {
		        msgShow('系统提示', '请输入密码！', 'warning');
		        return false;
		    }
		    if ($rePass.val() == '') {
		        msgShow('系统提示', '请在一次输入密码！', 'warning');
		        return false;
		    }
		    if ($newpass.val() != $rePass.val()) {
		        msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
		        return false;
		    }
		    var np = encrypt($newpass.val());
		    var op = encrypt($txtOriginPass.val());
		    $.post(basePath + '/changePassword?txtNewPass=' + np + '&txtOriginPass=' + op, function(msg) {
		    	if (msg.code == '1') {
			        $.messager.alert('系统提示', '密码修改成功!<br>您的新密码为【' + msg.data + '】', 'info');
		    	} else {
		    		$.messager.alert('系统提示', '密码修改失败!', 'info');
		    	}
		    	$txtOriginPass.textbox('reset');
		        $newpass.textbox('reset');
		        $rePass.textbox('reset');
		        $('#btnCancel').click();
		    })
		});
	});
});

function addTabs(name, url) {
	//不为空，说明是一个具体的菜单
	if (name != null) {
		var b =  $("#tabs").tabs("exists", name);
		//如果选项卡不存在，则去新增选项卡
		if (b) {
			$("#tabs").tabs("select", name);
		} else {
			$('#tabs').tabs('add', {
				title: name,//tab页的名称
				content: " <iframe scrolling=yes class='content_iframe' " +
						 " style='width:100%; height:100%; " +
						 " display:block;' scrolling='auto' frameborder='0' " +
						 " src='" + basePath + url + "'></iframe>",
				closable: true
			});
		}
	}
}