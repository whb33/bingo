package com.bingo.web.springbootdemo.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * io类
 * 
 * @author liyuan
 * 
 */
public class IO {

	/**
	 * 获取全路径
	 * 
	 * @return
	 */
	public static String GetFullPath() {

		if (IO.class.getResource("/") != null) {
			if (IO.class.getResource("/").getPath().indexOf("/WEB-INF/") >= 0) {
				return IO.class.getResource("/").getPath();
			} else {
				return System.getProperty("user.dir") + System.getProperty("file.separator");
			}

		} else {
			return System.getProperty("user.dir") + System.getProperty("file.separator");
		}
	}

	/**
	 * @param path
	 *            文件路径
	 * @param suffix
	 *            后缀名, 为空则表示所有文件
	 * @param isdepth
	 *            是否遍历子目录
	 * @return list
	 */
	public static List<File> GetListFiles(String path, String suffix, boolean isdepth) {
		List<File> lstFileNames = new ArrayList<File>();
		File file = new File(path);
		return listFile(lstFileNames, file, suffix, isdepth);
	}

	private static List<File> listFile(List<File> lstFileNames, File f, String suffix, boolean isdepth) {
		// 若是目录, 采用递归的方法遍历子目录
		if (f.isDirectory()) {
			File[] t = f.listFiles();

			for (int i = 0; i < t.length; i++) {
				if (isdepth || t[i].isFile()) {
					listFile(lstFileNames, t[i], suffix, isdepth);
				}
			}
		} else {
			String filePath = f.getAbsolutePath();
			if (!suffix.equals("")) {
				int begIndex = filePath.lastIndexOf("."); // 最后一个.(即后缀名前面的.)的索引
				String tempsuffix = "";

				if (begIndex != -1) {
					tempsuffix = filePath.substring(begIndex + 1, filePath.length());
					if (tempsuffix.equals(suffix)) {
						lstFileNames.add(f);
					}
				}
			} else {
				lstFileNames.add(f);
			}
		}
		return lstFileNames;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				//System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				//System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}
	
	/**
	 * 从指定输入流读取文件，并保存到指定位置
	 * @param fileName 文件名
	 * @param in  指定输入流
	 * @return String 保存的文件全路径
	 */
    public static String saveFile(String fileName, InputStream in) throws Exception {
    	String filename="";
        String osName = System.getProperty("os.name");
        String storedir = IO.GetFullPath();
  
        if (osName == null)   
            osName = "";   
        if (osName.toLowerCase().indexOf("win") != -1) {   
            if (storedir == null || storedir.equals(""))  
                storedir = "D:\\tmp";  
        } else {  
            storedir = "/tmp";  
        }  
        File storefile = new File(storedir + fileName);
        filename=storedir +fileName;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {  
            bos = new BufferedOutputStream(new FileOutputStream(storefile));
            bis = new BufferedInputStream(in);
            int c;  
            while ((c = bis.read()) != -1) {  
                bos.write(c);  
                bos.flush();  
            }  
        } catch (Exception exception) {
            exception.printStackTrace();  
            throw new Exception("文件保存失败!");
        } finally {  
            bos.close();  
            bis.close();  
        } 
        return filename;
    }
    
    /**
     * 输入流转为字符串
     * @param in
     * @return str 字符串
     * @throws IOException
     */
    public static String inputStream2Str(InputStream in) throws IOException {
    	StringBuffer resultStr = new StringBuffer("");
    	BufferedReader reader=new BufferedReader(new InputStreamReader(in,"UTF-8"));
    	String line=null;
    	try {
			while((line=reader.readLine())!=null){ 
				resultStr.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			in.close();
			reader.close();
		}
    	return StringUtils.isNotBlank(new String(resultStr))?new String(resultStr):"";
    			
    }
}