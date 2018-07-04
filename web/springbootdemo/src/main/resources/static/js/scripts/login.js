require(['require.config'], function() {
	require(['jquery', 'encrypt', "easyui", 'easyui-lang'], function ($) {
		document.onkeydown = function (event) {
	        e = event ? event :(window.event ? window.event : null);
	        if (e.keyCode == 13) {
	            //执行的方法 
	        	$('#login').click();
	        }
	    }
		$('#login').click(function() {
			if ($("#login").attr("disabled") != "disabled") {
				var account = $('#username').val();
				var password = $('#password').val();
				var vcode = $('#vcode').val();
				if (account == '') {
					alert('用户名不能为空！');
					return;
				}
				if (password == '') {
					alert('密码不能为空！');
					return;
				}
				if (vcode == '') {
					alert('请输入验证码！');
					return;
				}
				$.ajax({
				      url: basePath + "/vcode/validate",
				      dataType: "json",
				      data: {
				        code: vcode
				      },
				      success: function (data) {
				    	  if (!data.success) {
				    		  alert("验证码错误");
				    		  $("#captchaImg").attr("src", basePath + "/vcode/random?t=" + Math.random());
				    		  return;
				    	  } else {
				    		  $("#login").attr("disabled", true);
				    		  login();
				    	  }
				      }
			    });
			}
		});
		
		function login() {
			$.ajax({
				url: basePath + '/login',
				type: 'POST',
				dataType: 'JSON',
				cache: false,
				data: {
					username: $('#username').val(),
					password: encrypt($('#password').val())
				},
				success: function (data) {
					console.log('登录： ' + JSON.stringify(data));
					var status = data.code;
			       	if (status == '1') {
			       		window.location.reload();
			       	} else {
			       		$('#captchaImg').attr("src", basePath + "/vcode/random?t=" + Math.random());
			       		alert(data.msg);
					}
			       	$("#login").attr("disabled", false);
				},
				error: function(error) {
					console.log(error);
					alert("数据提交失败，服务器可能出现错误，请稍后再试。");
				}
			});
		}
	});
});