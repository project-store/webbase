package com.ass.common.utilsExcel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ass.common.utils.DateUtil;
import com.ass.common.utils.StringUtil;


public class ExcelRead {
   
    private HSSFWorkbook workbook;//工作簿
    
   ///////////构造函数  begin 
    public ExcelRead() {
 	}
   public ExcelRead(String strfile) {
	   try {
			workbook = new HSSFWorkbook(new FileInputStream(strfile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   
   public ExcelRead(File file) {
	   try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   
   
   public ExcelRead(InputStream filestream) {
	   try {
			workbook = new HSSFWorkbook(filestream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   
   
   
   
///////////构造函数  end 
   
/*
    * 读取文件路径字符串
    */
    public void importExcel(String strfile){
        //获取工作薄workbook
        try {
			workbook = new HSSFWorkbook(new FileInputStream(strfile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /*
     * 读取文件
     */
    public void importExcel(File file){
		 try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /*
     * 读取文件流
     */
    public void importExcel(InputStream filestream){
		 try {
			workbook = new HSSFWorkbook(filestream);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
   
    
    
    
    
    
    /**
     * 获取第sheetNumber页的数据， sheet页
     * @param sheetNumber
     * @return
     * @author wangt 2014年7月22日 下午4:18:58 
     */
    public List<Map<Integer, Object>> readRows(int sheetNumber){
        List<Map<Integer, Object>> result = new ArrayList<Map<Integer, Object>>();
       
        //获得指定的sheet
        HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
        //获得sheet总行数
        int rowCount = sheet.getLastRowNum();
        if(rowCount < 1){
            return result;
        }
        
      //遍历行row
        for (int index = 0; index <= rowCount; index++) {
            //获得行对象
            HSSFRow row = sheet.getRow(index);
            int flag;//是否往result 里面添加的标记
            if(null != row){
           	 flag = 0;
           	 Map<Integer, Object> map=new HashMap<Integer, Object>();
                //获得本行中单元格的个数
                int cellCount = row.getLastCellNum();
                //遍历列cell
                for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                    HSSFCell cell = row.getCell(cellIndex);
                    //获得指定单元格中的数据
                    Object cellStr =this.getCellString(cell);
                    if(StringUtil.isNotBlank(StringUtil.getString(cellStr).trim())){
                   	 flag = 1;
                   	 map.put(cellIndex, cellStr);
                    }
                    
                }
                if(flag == 1){
               	 result.add(map);
                }
            }
        }
        
        
        
       
        return result;
    }
   
     /**
     * 获取第sheetNumber页的 从第beginRowIndex行开始所有的数据(rowIndex为0 代表第一行)
     * @param sheetNumber
     * @param beginRowIndex
     * @return
     * @author wangt 2014年7月22日 下午4:20:01 
     */
    public List<Map<Integer, Object>> readRows(int sheetNumber,int beginRowIndex){
         List<Map<Integer, Object>> result = new ArrayList<Map<Integer, Object>>();
         //获得指定的sheet
         HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
         //获得sheet总行数
         int rowCount = sheet.getLastRowNum();
         if(rowCount < 1){
             return result;
         }
         //遍历行row
         for (int index = beginRowIndex; index <= rowCount; index++) {
             //获得行对象
             HSSFRow row = sheet.getRow(index);
             int flag;//是否往result 里面添加的标记
             if(null != row){
            	 flag = 0;
            	 Map<Integer, Object> map=new HashMap<Integer, Object>();
                 //获得本行中单元格的个数
                 int cellCount = row.getLastCellNum();
                 //遍历列cell
                 for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                     HSSFCell cell = row.getCell(cellIndex);
                     //获得指定单元格中的数据
                     Object cellStr =this.getCellString(cell);
                     if(StringUtil.isNotBlank(StringUtil.getString(cellStr).trim())){
                    	 flag = 1;
                    	 map.put(cellIndex, cellStr);
                     }
                     
                 }
                 if(flag == 1){
                	 result.add(map);
                 }
             }
         }
        
         return result;
     }

     /**
     *  获取第sheetNumber页的 从第beginRowIndex行开始到第endRowIndex 的所有数据
     * @param sheetNumber
     * @param beginRowIndex
     * @param endRowIndex
     * @return
     * @author wangt 2014年7月22日 下午4:26:17 
     */
    public List<Map<Integer, Object>> readRows(int sheetNumber,int beginRowIndex,int endRowIndex){
         List<Map<Integer, Object>> result = new ArrayList<Map<Integer, Object>>();
        
         //获得指定的sheet
         HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
         //获得sheet总行数
         int rowCount = endRowIndex;
         if(rowCount < 1){
             return result;
         }
         //HashMap<Integer, Object> map=new HashMap<Integer, Object>();
         //遍历行row
         for (int index = beginRowIndex; index <= rowCount; index++) {
             //获得行对象
             HSSFRow row = sheet.getRow(index);
             int flag;//是否往result 里面添加的标记
             if(null != row){
            	 flag = 0;
            	 Map<Integer, Object> map=new HashMap<Integer, Object>();
                 //获得本行中单元格的个数
                 int cellCount = row.getLastCellNum();
                 //遍历列cell
                 for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                     HSSFCell cell = row.getCell(cellIndex);
                     //获得指定单元格中的数据
                     Object cellStr =this.getCellString(cell);
                     if(StringUtil.isNotBlank(StringUtil.getString(cellStr).trim())){
                    	 flag = 1;
                    	 map.put(cellIndex, cellStr);
                     }
                     
                 }
                 if(flag == 1){
                	 result.add(map);
                 }
             }
         }
        
         return result;
     }
     
     /**
      * 获取第 sheetNumber 页的总行数    [3代表3行]
      * @param sheetNumber
      * @return
      * @author wangt 2014年7月22日 下午4:23:26 
      */
     public int getRowNum(int sheetNumber){
          HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
          //获得sheet总行数
          int rowCount = sheet.getLastRowNum();
          if(rowCount < 1){
              return 0;
          }
          return rowCount+1;
      }
   /**
    * 获取一个cell的数据类型
    * @param cell
    * @return
    */
    private Object getCellString(HSSFCell cell) {
        Object result = null;
        if(cell != null){
            //单元格类型：Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
            int cellType = cell.getCellType();
            switch (cellType) {
            case HSSFCell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					result = cell.getDateCellValue(); //如果excel单元格格式设置的为时间，这里返回Date类型时间格式
				}else{
					result = cell.getNumericCellValue();  
					BigDecimal db1 = new BigDecimal(result.toString());//转换科学计数法形势的数字格式
			    	result = db1.toPlainString();
				}
				break;
            case HSSFCell.CELL_TYPE_FORMULA:
                result = cell.getNumericCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                result = null;
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                result = null;
                break;
            default:
                System.out.println("枚举了所有类型");
                break;
            }
        }
        return result;
    }
   
    //test
    public static void main(String[] args) throws Exception {
    	
    	String d1  = "2014-03-03";
    	String d2 = "2014-5-5";
    	Date dd1 = DateUtil.parseDate(d1, "yyyy-MM-dd");
    	System.out.println(dd1);
    	System.out.println(DateUtil.getDateStr(dd1, "yyyy=MM=dd"));
    	Date dd2 = DateUtil.parseDate(d2, "yyyy-MM-dd");
    	System.out.println(dd2);
    	System.out.println(DateUtil.getDateStr(dd2, "yyyy=MM=dd"));
    	
    	String af = "100000000";
    	String bf = "1E10";
    	BigDecimal db1 = new BigDecimal(af);
    	String ii1 = db1.toPlainString();
    	System.out.println(ii1);
    	
    	BigDecimal db2 = new BigDecimal(bf);
    	String ii2 = db2.toPlainString();
    	System.out.println(ii2);
    	
    	String a = null;
    	String b = "";
    	String c =  "    ";
    	
    	System.out.println(StringUtil.isEmpty(a));
    	System.out.println(StringUtil.isEmpty(b));
    	System.out.println(StringUtil.isEmpty(c));
//    	System.out.println(StringUtil.isEmpty(c));
    	System.out.println(StringUtil.isBlank(a));
    	System.out.println(StringUtil.isBlank(b));
    	System.out.println(StringUtil.isBlank(c));
    	
    	
    	/*
    	ExcelRead excel=new ExcelRead();
    	excel.importExcel("F:/temp/新建 Microsoft Excel 工作表2.xls");
//    	System.out.println(excel.getRowNum(0));
    	List<Map<Integer, Object>> l = excel.readRows(0);
    	
    	for(Map<Integer, Object> m : l ){
    		System.out.println(m.size());
    		for(int i = 0; i<m.size(); i++){
    			System.out.println(m.get(i));
    		}
    		System.out.println("over one map");
    	}
    	*/

    }
}

