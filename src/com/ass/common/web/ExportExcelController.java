package com.ass.common.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ass.base.web.BaseController;
import com.ass.common.service.CommonService;
import com.ass.common.utils.JsonUtil;
import com.ass.common.utils.StringUtil;
import com.ass.common.utilsExcel.ExcelCreate;
import com.ass.common.utilsExcel.FieldForDTO;

/**
 * excel导出的controller
 * @author wangt 2014年7月24日 下午4:51:17 
 */
@Controller
@RequestMapping("/excel.do")
public class ExportExcelController extends BaseController {

	@Resource
	private CommonService commonService;
	
	
	
	
	
	
	public static void main(String[] args)  {
		System.out.println((char)('Z'+3));
		
		System.out.println(StringUtil.decode("%E6%98%A5"));
		Map m = new HashMap();
		m.put("success", true);
		
		Map m2 = new HashMap();
		m2.put("success", "sdfsdf");
		System.out.println(JsonUtil.writeValue(m));
		System.out.println(JsonUtil.writeValue(m2));
	}
	
	
	
	/**
	 * 获得要导出的数据的总记录，如果 记录数大于6w则提示不能导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(params="action=canExport")
	public void canExport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = this.getRequestParams(request);
		//获取行数据
		List<Map<String, String>> resultLst = commonService.selectList(param.get("statement"), param);
		int count = resultLst.size();
		System.out.println(count);
		if(count > 60000){
			this.printStr(response, "{\"success\": false}");
		}else{
			this.printStr(response, JsonUtil.writeValue(this.getMap()));
		}
	}
	
	
	//多表头测试  OK 用这个
	@RequestMapping(params="action=2")
	public void testExcel2(HttpServletRequest request, HttpServletResponse response) throws IOException{
    /*
    // 创建单元格样式  
    HSSFCellStyle cellStyle = workbook.createCellStyle();  
    // 指定单元格居中对齐  
    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
    // 指定单元格垂直居中对齐  
    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
    // 指定当单元格内容显示不下时自动换行  
    cellStyle.setWrapText(true);  
    cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);  
    */
		
		/*
		//第一行的表头
         List<FieldForDTO> firstFieldList  = new ArrayList<FieldForDTO>();
         Map<Integer,List<FieldForDTO> > fieldMap = new HashMap<Integer,List<FieldForDTO> >();
		 fieldMap.put(0, firstFieldList);
   		 //第二行的表头
   		 List<FieldForDTO> secondFieldList  = new ArrayList<FieldForDTO>();
   		 fieldMap.put(1, secondFieldList);
   		 firstFieldList.add(new FieldForDTO("id","这里是id",1,2,1));
   		
   		 firstFieldList.add(new FieldForDTO(null,"合并的表头",2,1,4));
   		 
   		 secondFieldList.add(new FieldForDTO("billno","发票号","2",1));
   		 secondFieldList.add(new FieldForDTO("billtype","类型","2",2));
   		 secondFieldList.add(new FieldForDTO("receivebillname","收款人","2",3));
   		 secondFieldList.add(new FieldForDTO("realincomemoney","付款方式","2",4));
    */
		
		
			
					// 第一行的表头
					List <  FieldForDTO > firstFieldList = new ArrayList <  FieldForDTO >();
					// 第二行的表头
					List <  FieldForDTO > secondFieldList = new ArrayList <  FieldForDTO >();
					// 第三行的表头
					List <  FieldForDTO > thirdFieldList = new ArrayList <  FieldForDTO >();
					
					Map < Integer, List <  FieldForDTO > > fieldMap = new HashMap < Integer, List <  FieldForDTO > >();
					fieldMap.put(0, firstFieldList);
					fieldMap.put(1, secondFieldList);
					fieldMap.put(2, thirdFieldList);
					
					firstFieldList.add( new FieldForDTO("_PAYDATE","缴纳年月",1,3,1));
					firstFieldList.add( new FieldForDTO("_BATCHNO","批次号",2,3,1));
					firstFieldList.add( new FieldForDTO("_STAFFNAME","员工姓名",3,3,1));
					firstFieldList.add( new FieldForDTO("_STAFFCODE","员工编码",4,3,1));
					firstFieldList.add( new FieldForDTO("_MANAGECOM","总/分公司",5,3,1));
					firstFieldList.add( new FieldForDTO("_THRIDBRANCH","三级机构",6,3,1));
					firstFieldList.add( new FieldForDTO("_FOURTHBRANCH","四级机构",7,3,1));
					firstFieldList.add( new FieldForDTO("_DEPT","部门",8,3,1));
					firstFieldList.add( new FieldForDTO("_SUBJECT","处/室",9,3,1));
					firstFieldList.add( new FieldForDTO("_OCCLEVEL","职级",10,3,1));
					firstFieldList.add( new FieldForDTO("_STATIONNAME","岗位名称",11,3,1));
					firstFieldList.add( new FieldForDTO("_STATIONLINE","岗位系列/条线",12,3,1));
					firstFieldList.add( new FieldForDTO("_FBFLAG","前/后线",13,3,1));
					firstFieldList.add( new FieldForDTO("_KINDFLAG","内勤/外勤",14,3,1));
					
					firstFieldList.add( new FieldForDTO(null, "养老保险", 15, 1, 2));
					firstFieldList.add( new FieldForDTO(null, "医疗保险", 16, 1, 2));
					firstFieldList.add( new FieldForDTO(null, "失业保险", 17, 1, 2));
					firstFieldList.add( new FieldForDTO(null, "工伤保险", 18, 1, 1));
					firstFieldList.add( new FieldForDTO(null, "生育保险", 19, 1, 1));
					firstFieldList.add( new FieldForDTO(null, "公积金", 20, 1, 2));
					firstFieldList.add( new FieldForDTO(null, "其它", 21, 1, 2));
					
					firstFieldList.add( new FieldForDTO("_SOCIALSECAREA","社保地区",27,3,1));
					firstFieldList.add( new FieldForDTO("_PROVIDENTFUNDAREA","公积金地区",28,3,1));
					firstFieldList.add( new FieldForDTO("_SUMCOMPAY","单位合计",29,3,1));
					firstFieldList.add( new FieldForDTO("_SUMPERPAY","个人合计",30,3,1));
					firstFieldList.add( new FieldForDTO("_PROVIDENTFUNDACC","公积金帐号",31,3,1));
					
					secondFieldList.add( new FieldForDTO(null, "单位", "15", 1, 1, 1));
					secondFieldList.add( new FieldForDTO(null, "个人", "15", 2, 1, 1));
					secondFieldList.add( new FieldForDTO(null, "单位", "16", 1, 1, 1));
					secondFieldList.add( new FieldForDTO(null, "个人", "16", 2, 1, 1));
					secondFieldList.add( new FieldForDTO(null, "单位", "17", 1, 1, 1));
					secondFieldList.add( new FieldForDTO(null, "个人", "17", 2, 1, 1));
					secondFieldList.add( new FieldForDTO(null, "单位", "18", 1, 1, 1));
					secondFieldList.add( new FieldForDTO(null, "单位", "19", 1, 1, 1));
					secondFieldList.add( new FieldForDTO(null, "单位", "20", 1, 1, 1));
					secondFieldList.add( new FieldForDTO(null, "个人", "20", 2, 1, 1));
					secondFieldList.add( new FieldForDTO(null, "工本费", "21", 1, 1, 1));
					secondFieldList.add( new FieldForDTO(null, "服务费", "21", 2, 1, 1));
					//secondFieldList.add( new FieldForDTO(null, "单位", "15", 1, 1, 1));    第三个参数和第四个参数连起来
					thirdFieldList.add( new FieldForDTO("_SUMYANGLAOCOMPAY","缴纳金额", "15-1", 1));
					thirdFieldList.add( new FieldForDTO("_SUMYANGLAOPERPAY","缴纳金额", "15-2", 1));
					thirdFieldList.add( new FieldForDTO("_SUMYILIAOCOMPAY","缴纳金额", "16-1", 1));
					thirdFieldList.add( new FieldForDTO("_SUMYILIAOPERPAY","缴纳金额", "16-2", 1));
					thirdFieldList.add( new FieldForDTO("_SUMSHIYECOMPAY","缴纳金额", "17-1", 1));
					thirdFieldList.add( new FieldForDTO("_SUMSHIYEPERPAY","缴纳金额", "17-2", 1));
					thirdFieldList.add( new FieldForDTO("_SUMGONGSHANGCOMPAY","缴纳金额", "18-1", 1));
					thirdFieldList.add( new FieldForDTO("_SUMSHENGYUCOMPAY","缴纳金额", "19-1", 1));
					thirdFieldList.add( new FieldForDTO("_SUMGJJCOMPAY","缴纳金额", "20-1", 1));
					thirdFieldList.add( new FieldForDTO("_SUMGJJPERPAY","缴纳金额", "20-2", 1));
					thirdFieldList.add( new FieldForDTO("_COSTFEE","缴纳金额", "21-1", 1));
					thirdFieldList.add( new FieldForDTO("_SERVICEFEE","缴纳金额", "21-2", 1));
   		 
   		 OutputStream out = this.getOut(response, "多表头导出.xls");
   		 
   		List<Map<String, String>> result = commonService.selectList("Bill_SpecSql.tttttt", null);
   		ExcelCreate.printExcel("sheet页", out, fieldMap, result, null);
	
	}
	
	//test 导出单行表头excel ， 不建议使用。
	//1 因为需要xml返回LinkedHashMap，
	//2 xml查询的数据数量必须对应的上。
	@RequestMapping(params="action=1")
	public void testExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//单行表头导出用不到了
		String[] headers = {"ID", "用户名", "密码"}; 
		OutputStream out = this.getOut(response, "导出文件.xls");
		List<LinkedHashMap<String, Object>> result = commonService.selectList("Bill_SpecSql.tttttt", null);
		ExcelCreate.exportExcel("TestBiao", headers,  result, out);
	}	
	
}


