require(['require.config'], function() {
	require(['jquery', "easyui", 'easyui-lang', 'jquery-form', "fileChecker"], function ($) {
		$('#datagrid').datagrid({
			url: basePath + '/getHonorguessList',
			columns:[[
		          {field:'ck',width:'2%',checkbox:true},
		          {field:'name',title:'姓名',width:'6%',align:'center'},
		          {field:'company',title:'公司名称',width:'25%',align:'center'},
		          {field:'position',title:'职位',width:'10%',align:'center'},
		          {field:'picurl',title:'图片地址',width:'47%',align:'center'},
		          {field:'display',title:'是否显示',width:'6%',align:'center',formatter: formateDisplay},
		          {field:'sortnum',title:'排序号',width:'5%',align:'center'}
		    ]],
		    fit:true,
			pagination:true,
			loadMsg:'数据加载中....',
			fitColumns:true,
			rownumbers:true,
			pageSize:10
		});
		$('#search').click(function() {
			var data = {
				name_search: $('#name_search').val()
        	};
        	var dataGirdList = $('#datagrid');
        	dataGirdList.datagrid("options").queryParams = data;
        	dataGirdList.datagrid('load');
		});
		$('#insert-cancel').click(function() {
			$('#dlg').window('close');
		});
		$('#edit-cancel').click(function() {
			$('#dlg-edit').window('close');
		});
		$('#insert-confirm').click(function() {
			var checkMsg = checkFile('#insert-picture');
			if (checkMsg == '') {
				$('#insert_form').form('submit', {
					url: basePath + "/insertHonorguess",
					success: function(data) {
						var data = eval('(' + data + ')');
						if (data.code == '1') {
							$.messager.alert('系统提示', data.msg, 'info');
			                $('#dlg').dialog('close');      // close the dialog
			                $('#datagrid').datagrid('reload');    // reload the user data
			                $('#datagrid').datagrid('clearSelections');
						} else {
							$.messager.alert('系统提示', data.msg, 'error');
						}
					}
				});
			} else {
				alert(checkMsg);
			}
		});
		$('#edit-confirm').click(function() {
			var checkMsg = checkFile('#edit-picture');
			if (checkMsg == '') {
				$('#edit_form').form('submit', {
					url: basePath + "/insertHonorguess",
					success: function(data) {
						var data = eval('(' + data + ')');
						if (data.code == '1') {
							$.messager.alert('系统提示', data.msg, 'info');
			                $('#dlg-edit').dialog('close');      // close the dialog
			                $('#datagrid').datagrid('reload');    // reload the user data
			                $('#datagrid').datagrid('clearSelections');
						} else {
							$.messager.alert('系统提示', data.msg, 'error');
						}
					}
				});
			} else {
				alert(checkMsg);
			}
		});
		$('#addHonorguess').click(function() {
			resetInsertForm();
			$('#dlg').dialog('open').dialog('setTitle','新增嘉宾');
		});
		$('#editHonorguess').click(function() {
			var rows = $('#datagrid').datagrid('getSelections');
			if (rows && rows.length != 1) {
				$.messager.alert('系统提示', "请选择一条记录！", 'info');
				return;
			} else {
				editHonorguess(rows[0].id);
			}
		});
		$('#deleteHonorguess').click(function() {
			var rows = $('#datagrid').datagrid('getSelections');
			if (rows && rows.length > 0) {
				$.messager.confirm('确认', '确认删除?', function (r) {
					if (r) {
						var jsonObj = {ids:[]};
						for (var i = 0; i < rows.length; i++) {
							jsonObj.ids[i] = rows[i].id;
						}
						$.post(basePath + '/deleteHonorguess', jQuery.param(jsonObj, true), function (data) {
							if (data.code == '1') {
								$.messager.alert('系统提示', data.msg, 'info');
				                $('#datagrid').datagrid('reload');    // reload the user data
				                $('#datagrid').datagrid('clearSelections');
							} else {
								$.messager.alert('系统提示', data.msg, 'error');
							}
						},'json');
					}
				});
			} else {
				$.messager.alert("系统提示", "请先选择记录!", "info");
				return;
			}
		});
		$('#viewHonorguess').click(function() {
			var rows = $('#datagrid').datagrid('getSelections');
			if (rows && rows.length != 1) {
				$.messager.alert('系统提示', "请选择一条记录！", 'info');
				return;
			} else {
				lookupHonorguess(rows[0].id);
			}
		});
		$('#lookup-close').click(function() {
			$('#dlg-lookup').window('close');
		});
		
		function lookupHonorguess(id) {
			$.ajax({
				url: basePath + "/getHonorguessById",
				type: "POST",
				data: {id: id},
				dataType: 'json',
				success: function(data) {
					$('#lookup-name').html(data.name);
					$('#lookup-company').html(data.company);
					$('#lookup-position').html(data.position);
					$('#lookup-display').html(data.display == 1 ? "显示" : "不显示");
					$('#lookup-sortnum').html(data.sortnum);
					$('#look-images').html('<img style="width:300px;height:310px;" src="' + data.picurl + '">');
					$('#dlg-lookup').dialog('open').dialog('setTitle','嘉宾详情');
				}
			});
		}
		
		function editHonorguess(id) {
			$.ajax({
				url: basePath + "/getHonorguessById",
				type: "POST",
				data: {id: id},
				dataType: 'json',
				success: function(data) {
					$('#edit-id').val(data.id);
					$('#edit-name').textbox('setValue', data.name);
					$('#edit-company').textbox('setValue', data.company);
					$('#edit-position').textbox('setValue', data.position);
					$('#edit-display').combobox('setValue', data.display);
					$('#edit-sortnum').textbox('setValue', data.sortnum);
					$('#editPic-div').html('<label>图片:</label><input name="picture" id="edit-picture" type="file"/>');
					$('#dlg-edit').dialog('open').dialog('setTitle','编辑嘉宾');
				}
			});
		}
		
		function formateDisplay(val, rec) {
			var str = "";
			if (val == '0') {
				str += "不显示";
			} else if (val == '1') {
				str += "显示";
			}
			return str;
		}
		
		function resetInsertForm() {
			$('#insert-name').textbox('setValue', '');
			$('#insert-company').textbox('setValue', '');
			$('#insert-position').textbox('setValue', '');
			$('#insert-display').combobox('setValue', 0);
			$('#insert-sortnum').textbox('setValue', '');
			$('#insertPic-div').html('<label>图片:</label><input name="picture" id="insert-picture" type="file"/>');
		}
		
	});
});