function checkFile(selecter) {
	// 判断是否有文件
	if ($(selecter).val() != "") {
		var size = $(selecter)[0].files[0].size;
		var name = $(selecter)[0].files[0].name;
		var msize = (size / 1024 / 1024);
		// 判断文件大小是否合适
		console.log('上传文件大小为：' + msize + 'm');
		console.log('上传文件名称为：' + name);
		if (msize > 10) {
			return '上传文件大小为：' + msize + 'm, 文件过大！';
		}
		// 判断后缀名是否为jpg,png,jpeg
		var names = name.split(".");
		if (names != null && names.length > 0) {
			var s = names[names.length - 1];
			var suffix = fileSuffix.split(',');
			if (suffix.indexOf(s) == -1) {
				return '上传文件名称为：' + name + '，后缀错误，应该上传图片！';
			}
		}
	}
	return '';
}
