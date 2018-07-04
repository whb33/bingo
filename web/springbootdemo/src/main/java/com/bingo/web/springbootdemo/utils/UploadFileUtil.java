package com.bingo.web.springbootdemo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 上传文件操作工具类
 * 
 */
public class UploadFileUtil {
	/**
	 * 文件最大限制10*1024*1024节节(即10M)
	 */
	private static final long FILE_MAX_SIZE = 10*1024*1024;
	
	public static boolean deleteOldUploadFile(String url) {
		File file = new File(url);
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查上传的文件类型是否是被允许的
	 * 
	 * @param uploadFile
	 * @return
	 */
	public static boolean checkFile(MultipartFile uploadFile, String[] canUpload) {
		if (uploadFile == null) {
			return true;
		}
		String fileName = uploadFile.getOriginalFilename();
		for (String suffix : canUpload) {
			if (fileName.endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkFileSize(MultipartFile uploadFile) {
		if (uploadFile == null) {
			return true;
		}
		if (uploadFile.getSize() > FILE_MAX_SIZE) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param fileName文件名
	 */
	public static String uploadFile(MultipartFile uploadFile, String path, String fileName) throws IOException {
		if (uploadFile != null && uploadFile.getSize() > 0) {
			path = saveFileFromInputStream(uploadFile, path, fileName);
		}
		return path;
	}

	/**
	 * 保存文件
	 * 
	 * @param
	 * @param path
	 * @param filename
	 * @return path
	 * @throws IOException
	 */
	private static String saveFileFromInputStream(MultipartFile file, String path, String filename) throws IOException {
		if (file == null || file.getSize() == 0) {
			return "";
		}
		// 如果文件路径不存在则创建路径
		pathIsExist(path);
		// 如果文件名再改文件夹下存在了 ,重名一个
		Integer count = getNameExistInPath(path, filename);
		if (count > 0) {// 重名
			filename = filename.substring(0, filename.lastIndexOf("."))
					+ "("
					+ count
					+ ")"
					+ filename.substring(filename.lastIndexOf("."),
							filename.length());
		}
		InputStream stream = file.getInputStream();
		FileOutputStream fs = new FileOutputStream(new File(path + File.separator
				+ filename));
		byte[] buffer = new byte[1024 * 1024];
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
		return path + filename;
	}

	/**
	 * 创建文件夹 文件夹不存在就创建
	 * 
	 * @param path
	 *            eg: /1/2/3
	 */
	public static void pathIsExist(String path) {
		path = path.replace("/", "'").replace("\\", "'");
		String[] paths = path.split("'");
		String str = "";
		if (paths.length > 0) {
			for (int i = 0; i < paths.length; i++) {
				str = str + "/" + paths[i];
				File file = new File(str);// 绝对路径
				if (!file.exists()) {
					file.mkdir();
				}
			}
		} else {
			File file = new File(str);
			if (!file.exists()) {
				file.mkdir();
			}
		}
	}

	/**
	 * 获取文件的名称 在文件夹里存在的个数
	 * 
	 * @param path
	 *            绝对路径
	 * @param name
	 *            文件名称 带后缀
	 */
	public static Integer getNameExistInPath(String path, String name) {
		Integer count = 0;
		try {
			File file = new File(path);
			if (file.exists() && file.isDirectory()) {
				File[] files = file.listFiles();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						Pattern pattern = Pattern.compile("^"+name.substring(0,name.lastIndexOf("."))+"(\\(\\d+\\))?"+
								name.substring(name.lastIndexOf("."), name.length())); 
						Matcher matcher = pattern.matcher(files[i].getName());
						boolean flag = matcher.matches(); //当条件满足时，将返回true，否则返回false  
						if (flag) {
							count++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
