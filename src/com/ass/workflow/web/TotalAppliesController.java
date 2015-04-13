package com.ass.workflow.web;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ass.base.web.BaseController;
import com.ass.common.service.CommonService;
import com.ass.common.utilsExcel.ExcelCreate;
import com.ass.common.utilsExcel.FieldForDTO;

/**
 */
@Controller
@RequestMapping("/totalapplies.do")
public class TotalAppliesController extends BaseController {

	@Resource
	private CommonService commonService;
	
	
	
	
	@RequestMapping(params="action=vacations")
	public void vacations(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		Map<String, String> param = this.getRequestParams(request);
		//获取行数据
		List<Map<String, String>> resultLst = commonService.selectList(param.get("statement"), param);
		
		//构造表头， 就一行
        Map<Integer,List<FieldForDTO> > fieldMap = new HashMap<Integer,List<FieldForDTO> >();
        
        List<FieldForDTO> firstFieldList  = new ArrayList<FieldForDTO>();
        
		fieldMap.put(0, firstFieldList);
		
		
		firstFieldList.add( new FieldForDTO("title", "名称",	1,1,1));
		firstFieldList.add( new FieldForDTO("vacationtype", "休假类型",2,1,1));
		firstFieldList.add( new FieldForDTO("startdate", "休假开始时间",3,1,1));
		firstFieldList.add( new FieldForDTO("enddate", "休假结束时间",4,1,1));
		firstFieldList.add( new FieldForDTO("apphours" , "申请小时",5,1,1));
		firstFieldList.add( new FieldForDTO("appdate", "申请日期",6,1,1));
		firstFieldList.add( new FieldForDTO("appyuser", "申请人",7,1,1));
		firstFieldList.add( new FieldForDTO("reason", "原因",8,1,1));
		firstFieldList.add( new FieldForDTO("soucefrom", "来源",	9,1,1));
		firstFieldList.add( new FieldForDTO("taskstatus", "状态",10,1,1));
        
        
      //获取输出流，设置下载文件名
      	OutputStream out = this.getOut(response, "休假汇总.xls");
      	ExcelCreate.printExcel("休假汇总", out, fieldMap, resultLst,null);
      	
      	
	}
	
	
	
	
	
	@RequestMapping(params="action=travels")
	public void travels(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		Map<String, String> param = this.getRequestParams(request);
		//获取行数据
		List<Map<String, String>> resultLst = commonService.selectList(param.get("statement"), param);
		
		//构造表头， 就一行
        Map<Integer,List<FieldForDTO> > fieldMap = new HashMap<Integer,List<FieldForDTO> >();
        
        List<FieldForDTO> firstFieldList  = new ArrayList<FieldForDTO>();
        
		fieldMap.put(0, firstFieldList);
		
		
		firstFieldList.add( new FieldForDTO("title", "名称",	1,1,1));
		firstFieldList.add( new FieldForDTO("startdate", "外出开始时间",3,1,1));
		firstFieldList.add( new FieldForDTO("enddate", "外出结束时间",4,1,1));
		firstFieldList.add( new FieldForDTO("apphours" , "申请小时",5,1,1));
		firstFieldList.add( new FieldForDTO("appdate", "申请日期",6,1,1));
		firstFieldList.add( new FieldForDTO("appyuser", "申请人",7,1,1));
		firstFieldList.add( new FieldForDTO("reason", "原因",8,1,1));
		firstFieldList.add( new FieldForDTO("soucefrom", "来源",	9,1,1));
		firstFieldList.add( new FieldForDTO("taskstatus", "状态",10,1,1));
        
        
      //获取输出流，设置下载文件名
      	OutputStream out = this.getOut(response, "外出汇总.xls");
      	ExcelCreate.printExcel("外出汇总", out, fieldMap, resultLst,null);
      	
      	
	}
	
	
	
	
	
	
	
	
	
	
	

}
