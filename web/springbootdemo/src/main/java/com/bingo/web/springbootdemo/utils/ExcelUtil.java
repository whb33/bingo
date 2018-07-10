package com.bingo.web.springbootdemo.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class ExcelUtil {

	
	/**
	 * 生成excel workbook
	 * @param list   Map<Strin,Object>类型
	 * @param sheetName  sheet名称
	 * @param titles  xls内容那个标题
	 * @return
	 */
	public static XSSFWorkbook generatorExcel(XSSFWorkbook workbook, List<Map<String,Object>> list, String sheetName, String[] titles){
		// 创建工作簿实例
		if(workbook==null)
		{
		 workbook = new XSSFWorkbook();
		}
		// 创建工作表实例
		XSSFSheet hssfsheet = workbook.createSheet(sheetName);
		// 创建表头
		XSSFRow row = hssfsheet.createRow(0);
		// 设置表头样式
		row.setRowStyle(titleStyle(workbook));
		int findex = 0;
		for(String str:titles){
			setCell(row, findex,str, titleStyle(workbook));
			findex++;
		}
		XSSFCellStyle cellStyle = style(workbook);
		int rowi = 1;
		for(Map<String,Object> map:list){
			int celi = 0;
			row = hssfsheet.createRow(rowi);
			for(String str:titles){
				setCell(row, celi, String.valueOf(map.get(str)==null?"":map.get(str)), cellStyle);
				celi++;
			}
			rowi++;
		}
		return workbook;
	}
	/**
	 * 生成excel workbook
	 * @param list   Map<Strin,Object>类型
	 * @param sheetName  sheet名称
	 * @param titles  xls内容那个标题
	 * @return
	 */
	public static XSSFWorkbook generatorExcel(XSSFWorkbook workbook, List<Map<String,Object>> list, String sheetName){
		// 创建工作簿实例
		if(workbook==null)
		{
			workbook = new XSSFWorkbook();
		}
		// 创建工作表实例
		XSSFSheet hssfsheet = workbook.createSheet(sheetName);
		if(list.size()>0){
			Map<String,Object> mapSinlge = list.get(0);
			Set<Entry<String, Object>> set = mapSinlge.entrySet();
			String[] titles = new String[set.size()];
			int idx = 0;
			for(Entry<String, Object> s : set){
				titles[idx] = s.getKey(); idx++;
			}
			// 创建表头
			XSSFRow row = hssfsheet.createRow(0);
			// 设置表头样式
			row.setRowStyle(titleStyle(workbook));
			int findex = 0;
			for(String str:titles){
				setCell(row, findex,str, titleStyle(workbook));
				findex++;
			}
			XSSFCellStyle cellStyle = style(workbook);
			int rowi = 1;
			for(Map<String,Object> map:list){
				int celi = 0;
				row = hssfsheet.createRow(rowi);
				for(String str:titles){
					setCell(row, celi, String.valueOf(map.get(str)==null?"":map.get(str)), cellStyle);
					celi++;
				}
				rowi++;
			}
		}
		return workbook;
	}

	// 创建字体
	private static XSSFFont font(XSSFWorkbook workbook) {
		XSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 9);
		return font;
	}

	// 创建表头样式
	private static XSSFCellStyle titleStyle(XSSFWorkbook workbook) {
		XSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFont(font(workbook));
		titleStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		return titleStyle;
	}

	// 创建内容样式
	private static XSSFCellStyle style(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font(workbook));
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		return style;
	}

	// 设置日期格式
	@SuppressWarnings("unused")
	private static HSSFCellStyle dataStyle(HSSFWorkbook workbook) {
		HSSFCellStyle dateCellStyle = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat();
		short fmt = format.getFormat("yyyy/MM/dd HH:mm:ss");
		dateCellStyle.setDataFormat(fmt);
		return dateCellStyle;
	}

	private static XSSFCell setCell(XSSFRow row, int index, String value,
			XSSFCellStyle xssfCellStyle) {
		XSSFCell cell = row.createCell(index);
		cell.setCellValue(value);
		cell.setCellStyle(xssfCellStyle);
		return cell;
	}
	
	
	
	
	
	
	/**
	 * 读取EXCEL
	 * @param wb
	 * @return
	 */
	public static List<Map<String, Object>> getExcel(HSSFWorkbook wb)
	{
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		list.clear();
		
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells(); // 得到标题总列数
	
		int i = 1;
		Map<String, Object> map= null;
		for (; i <= sheet.getLastRowNum(); i++) {
			map=new HashMap<String, Object>();
			row = sheet.getRow(i);
			for (int j = 0; j < colNum; j++) {
				
				if(sheet.getRow(i).getCell(j)==null)
				{
					map.put(sheet.getRow(0).getCell(j).getStringCellValue(),"");
					continue;
				}
				switch (sheet.getRow(i).getCell(j).getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:
					map.put(sheet.getRow(0).getCell(j).getStringCellValue().replace("\\s", "").replace("\\n", ""),sheet.getRow(i).getCell(j).getStringCellValue().replace("\\s", "").replace("\\n", ""));
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					String result = null;
					Cell cell = sheet.getRow(i).getCell(j);
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
		                SimpleDateFormat sdf = null;
		                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
		                    sdf = new SimpleDateFormat("HH:mm");
		                } else {// 日期  
		                    sdf = new SimpleDateFormat("yyyy-MM-dd");
		                }  
		                Date date = cell.getDateCellValue();
		                result = sdf.format(date);  
		            } else if (cell.getCellStyle().getDataFormat() == 58) {  
		                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		                double value = cell.getNumericCellValue();  
		                Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
		                result = sdf.format(date);  
		            } else {  
		                double value = cell.getNumericCellValue();  
		                CellStyle style = cell.getCellStyle();  
		                DecimalFormat format = new DecimalFormat();
		                String temp = style.getDataFormatString();
		                // 单元格设置成常规  
		                if (temp.equals("General")) {  
		                    format.applyPattern("#");  
		                }  
		                result = format.format(value);  
		            }  
					map.put(sheet.getRow(0).getCell(j).getStringCellValue(),result);
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					map.put(sheet.getRow(0).getCell(j).getStringCellValue(),"");	
					break;
				default:
					map.put(sheet.getRow(0).getCell(j).getStringCellValue(),"");
					break;
				}
			}
			
			list.add(map);
		}
		return list;
	}
	
}
