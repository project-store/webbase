package com.ass.common.utilsExcel;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.ass.common.utils.StringUtil;


public class ExcelCreate {

	 private static HSSFWorkbook workBook;
	 
	 
    /**
     * 导出单行表头excel     用不到， 统一使用多行
     * @param title  excel中sheet页名称
     * @param headers 表头字符串数组
     * @param result  数据集List<LinkedHashMap<String, Object>> result ， 一定是list里面放LinkedHashMap
     * @param out      输出流
     * @throws Exception
     * @author wangt 2014年7月23日 上午8:56:36 
     */
    public static void exportExcel(String title, String[] headers, List<LinkedHashMap<String, Object>> result, OutputStream out) throws Exception{  

        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);  
        // 设置表格默认列宽度为20个字节  
        sheet.setDefaultColumnWidth(20);  

        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        // 设置这些样式  
        style.setFillForegroundColor(HSSFColor.GOLD.index);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  

        // 生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.VIOLET.index);  
        //font.setFontHeightInPoints((short) 12);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // 把字体应用到当前的样式  
        style.setFont(font);  

        // 指定当单元格内容显示不下时自动换行  
        style.setWrapText(true);  


          

        // 生成并设置另一个样式   //深橘黄背景  紫色加粗字
        HSSFCellStyle style2 = workbook.createCellStyle(); 
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index); 
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN); 
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN); 
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
        // 生成另一个字体 
        HSSFFont font2 = workbook.createFont(); 
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); 
        // 把字体应用到当前的样式 
        style2.setFont(font2); 
          

        // 产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        for (int i = 0; i < headers.length; i++) {  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  

        // 遍历集合数据，产生数据行  
        if(result != null){  
            int index = 1;  
            for(LinkedHashMap<String, Object> m:result){  
                row = sheet.createRow(index);  
                int cellIndex = 0;  
                for(String s: m.keySet()){  
                    HSSFCell cell = row.createCell(cellIndex);  
                    //cell.setCellStyle(style2);   //黄色背景 
                    HSSFRichTextString richString = new HSSFRichTextString(m.get(s) == null ? "" : m.get(s).toString());  
//                    HSSFFont font3 = workbook.createFont();  //蓝色字
//                    font3.setColor(HSSFColor.BLUE.index);  
//                    richString.applyFont(font3);  
                    cell.setCellValue(richString);  
                    cellIndex++;  
                }  
                index++;  
            }     
        }  
        workbook.write(out);  
    
    }
    
    
    
    
    
    /**
     * 导出多行excel
     * @param sheetName  sheet页名称
     * @param out			输出流
     * @param fieldMap		表头Map
     * @param resultList    表数据
     * @param List<Integer>  是否需要最后一行合计列，否：传null。是：列的索引（从0开始， 不用0，因为0列是'合计'2字）
     * @throws IOException
     * @author wangt 2014年7月23日 上午10:10:30 
     */
    public static void printExcel(String sheetName,  OutputStream out, Map < Integer, List < FieldForDTO >> fieldMap,   List <Map<String, String>> resultList, List<Integer> columns) throws IOException {
        
        List<FieldForDTO> fieldList = new ArrayList<FieldForDTO>();
        
        HSSFSheet sheet = ExcelCreate.generateExcel(sheetName,fieldMap,fieldList);
        int cellNumber = fieldList.size();//表头有columnName单元格的数量
        
        int rowIndex = fieldMap.size();//例：表头有2行，size为2， 表头用了第索引为0，1 两行， 数据从索引为2（第三行）行开始
        for (Map map : resultList) {
       	 HSSFRow row = sheet.createRow(rowIndex);
            for(int i = 0;i < cellNumber;i++)
            {
                
                FieldForDTO fieldDTO = fieldList.get(i);
                
                if(fieldDTO == null){
                    continue;
                }
                Object cell = map.get(fieldList.get(i).getFiledName());
                
                if(cell != null){
               	HSSFCell hssfcell = row.createCell(i);
                	if("java.math.BigDecimal".equals(cell.getClass().getName())){
                		hssfcell.setCellValue(((BigDecimal)cell).doubleValue());
                	}else{
                		hssfcell.setCellValue(StringUtil.getString(cell));
                	}
                }
                
            }
            
            rowIndex++;
            
       }
        
        if(columns != null ){

        	HSSFRow sumRow = sheet.createRow(rowIndex);
    		for(Integer colnum : columns) {
    			HSSFCell cell = sumRow.createCell(colnum);
    			
    			String tempcol = colnum > 25 ? (char)('A' + colnum/26-1)+""+(char)('A' + colnum%26): "" + (char)('A' + colnum);
    			//excel 里面  从B1 C1开始 不从B0 C0开始  
    			String ref = tempcol+ (fieldMap.size()+1) +":" + tempcol + ""+rowIndex;
    			cell.setCellFormula("SUM(" + ref + ")");
    		}

    		HSSFCell cell = sumRow.createCell(0);
    		cell.setCellValue("合计:");
        }
        
        //导出表格
        ExcelCreate.exportExcel(sheet,out);
        out.close();
    }
    
    
    public static void exportExcel(HSSFSheet sheet,OutputStream os) throws IOException 
    {
        sheet.setGridsPrinted(true);//设置网格线是否印刷
        workBook.write(os);
    }

		
    
    public static  HSSFSheet generateExcel(String sheetName, Map <Integer, List<FieldForDTO>> fieldMap,List < FieldForDTO > realFieldDTOList){
        //创建工作本
        workBook = new HSSFWorkbook();
        //创建表
        HSSFSheet sheet = workBook.createSheet(sheetName);
        
        //样式对象
        HSSFCellStyle style = workBook.createCellStyle();   
         //垂直
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
         //水平
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
                
        style.setFillBackgroundColor(HSSFColor.YELLOW.index);
        HSSFFont font2 = workBook.createFont(); 
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); 
        // 把字体应用到当前的样式 
        style.setFont(font2); 
        
        
        //取得复合表头的行数
        int headerRownum  = fieldMap.keySet().size(); 
        
        
        Map<Integer,HSSFRow> headerRowMap = new HashMap<Integer,HSSFRow>();
        
        //创建 headerRownum 行 表头
        for(int i = 0;i<headerRownum;i++){
            HSSFRow headerRow =sheet.createRow((short) i);
            headerRowMap.put(i, headerRow);
        }
        
        /**
         * 创建整个Excel表 头
         **/
        Map<String,Integer> parentIndexMap = new HashMap<String,Integer>();
        Map<String,Integer> parentRowMap = new HashMap<String,Integer>();
        int rowIndex = 0;
        
        //循环创建表头
        for(int i = 0;i<headerRownum;i++){
            List<FieldForDTO> fieldForDTOList = fieldMap.get(i);//第i行的所有FieldForDTO
            /**
             * 创建表头
             */
                HSSFHeader header = sheet.getHeader();
                header.setCenter(sheetName);
                
                int colIndex = 0;
                int preColnum = 0;
                String preParentIndex = "";
                //一行行的创建表头
                for(int j = 0;j < fieldForDTOList.size();j++){
                    
                    FieldForDTO fieldForDTO = fieldForDTOList.get(j);//第i行的 第j列
                    
                    Integer parentColIndex = null;
                    
                    if(fieldForDTO.getParentIndex()!=null){//是否包含父引用
                        parentColIndex = parentIndexMap.get((i-1)+"-"+fieldForDTO.getParentIndex());
                        rowIndex = parentRowMap.get((i-1)+"-"+fieldForDTO.getParentIndex());
                    }
                    
                    if(parentColIndex == null){
                        //四个参数分别是：起始行，结束行,起始列，结束列
                        sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex+fieldForDTO.getRownum()-1,colIndex,colIndex+fieldForDTO.getColnum()-1));
                        parentColIndex = 0;
                    }
                    else{
                        
                        if(!preParentIndex.equals(fieldForDTO.getParentIndex()))
                        {
                            preColnum = 0;
                        }
                        
                        //四个参数分别是：起始行，结束行,起始列，结束列
                        sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex+fieldForDTO.getRownum()-1,parentColIndex+fieldForDTO.getIndex()-1+preColnum,parentColIndex+fieldForDTO.getIndex()+fieldForDTO.getColnum()-2+preColnum));
                       
                        colIndex = parentColIndex+preColnum+fieldForDTO.getIndex()-1;
                    
                    }
                    
                    if(StringUtil.isNotBlank(fieldForDTO.getFiledName()) && StringUtil.isNotBlank(fieldForDTO.getColumnName())){
                        
                        int tempIndex = colIndex - realFieldDTOList.size()+1;
                        
                         for(;tempIndex>0;tempIndex--)
                        {
                             realFieldDTOList.add(null);
                        }
                        realFieldDTOList.set(colIndex, fieldForDTO);
                    }
                    if(fieldForDTO.getParentIndex()==null){
                      parentIndexMap.put(i+"-"+fieldForDTO.getIndex(), colIndex);
                      parentRowMap.put(i+"-"+fieldForDTO.getIndex(), rowIndex+fieldForDTO.getRownum());
                    }
                    else{
                        parentIndexMap.put(i+"-"+fieldForDTO.getParentIndex()+"-"+fieldForDTO.getIndex(), colIndex);
                        parentRowMap.put(i+"-"+fieldForDTO.getParentIndex()+"-"+fieldForDTO.getIndex(), rowIndex+fieldForDTO.getRownum());
                    }
                    HSSFCell   cell   =   headerRowMap.get(rowIndex).createCell(colIndex);   
                    cell.setCellValue(fieldForDTO.getColumnName()); //跨单元格显示的数据
                    cell.setCellStyle(style);   //样式
                    colIndex = colIndex+fieldForDTO.getColnum();
                    preColnum = preColnum+fieldForDTO.getColnum()-1;
                    preParentIndex = fieldForDTO.getParentIndex();
                }
        }
             
             return sheet;

    }
		
		
		
	}
	
