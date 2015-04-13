package com.ass.common.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ass.base.model.PageGrid;
import com.ass.base.model.PageQueryModel;
import com.ass.base.web.BaseController;
import com.ass.common.constant.CommonConstant;
import com.ass.common.service.CommonService;
import com.ass.common.utils.JsonUtil;
import com.ass.common.utils.StringUtil;

/**
 * 通用查询方法
 * 
 * @author wangt
 * @time 20140225
 * 
 */
@Controller
@RequestMapping("/common.do")
public class CommonController extends BaseController {

	@Resource
	private CommonService commonService;
	
			
	/**
	 * ajax 调用
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@RequestMapping(params = "action=list")
	public void list(
			/* 通用参数使用可以避免手动类型转换。
			 * String statment,
			 * Integer page,
			 * Integer pageSize,
			 * Integer start,
			 * Integer limit,
			 */
			HttpServletRequest request, HttpServletResponse response)
			 {
		

		// 查询sql 的id ，必传
		String statement = StringUtil.getString(
				request.getParameter("statement")).trim();
		// 查询的页码----------------------**
		String currentPage = StringUtil.getString(request.getParameter("page")).trim();
		// 一页的记录数--------------------**
		String limit = StringUtil.getString(request.getParameter("limit")).trim();
		// 排序的列名----------------------**
		String sortCol = StringUtil.getString(request.getParameter("sidx")).trim();//StringUtil.getString(null) 返回值 为 空字符串
		// asc desc queryAll 为false或者没传 默认false， 此项默认 desc
		String sortOrder = StringUtil.getString(request.getParameter("sord")).trim();
		
		/*
		 * 1 queryAll 为 a:null， b:""， c:"0" ，d: ("1"之外的其他值)   为 分页查询，计数
		 *  2 queryAll为1 ， 查询所有
		 */
		String queryAll = StringUtil.getString(request.getParameter("queryAll")).trim();
		
		PageQueryModel pageQueryModel = new PageQueryModel();
		if (StringUtil.isNotEmpty(currentPage)) {
			pageQueryModel.setCurrentPage(Integer.parseInt(currentPage));
		}
		if (StringUtil.isNotEmpty(limit)) {
			pageQueryModel.setPageSize(Integer.parseInt(limit));
		} else {
			pageQueryModel.setPageSize(100);
		}
		if (StringUtil.isNotEmpty(sortCol)) {
			pageQueryModel.setSortCol(sortCol);
		}
		if (StringUtil.isNotEmpty(sortOrder)) {
			pageQueryModel.setSortOrder(sortOrder);
		}
		if ("1".equals(queryAll)) {
			// queryAll为1 ，--true 查询所有
			pageQueryModel.setQueryAll(true);
			pageQueryModel.setDoCount(false);
		}
		// 获得表单提交请求参数名枚举
		Enumeration pNames = request.getParameterNames();
		while (pNames.hasMoreElements()) {
			// 取得表单参数名
			String name = (String) pNames.nextElement();
			// 取得表单参数值
			String value = StringUtil.getString(request.getParameter(name)).trim();
			//用于乱码 的转换
			value = StringUtil.decode(value);
			
			logger.info("The name is:" + name + ";The value is:" + value);
			if (StringUtil.isNotEmpty(value)) {
				pageQueryModel.addParameter(name, value);
			}
		}
		
		
		
		PageGrid pageGrid = this.commonService.pageForObject(statement,
				pageQueryModel);
		this.printStr(response, JsonUtil.writeValue(pageGrid));

	}

	
//	/**
//	 * 通用查询方法，用来查询全部。
//	 * @author G
//	 */
//	@SuppressWarnings({"rawtypes", "unchecked" })
//	@RequestMapping(params = "action=listALL")
//	public @ResponseBody Map listALL_(
//			String statement,
//			HttpServletRequest request, HttpServletResponse response){
//		
//		
//		Map<String,String[]>requestParamsMap = request.getParameterMap();
//		Map<String,String>methodParamsMap = new HashMap<String,String>();
//		
//		
//		for(String key:requestParamsMap.keySet()){
//			String value = requestParamsMap.get(key)[0];
//			
//			if(value!=null){
//				methodParamsMap.put(key, value);
//			}
//		}
//		map.put("rows", commonService.selectList(statement, methodParamsMap));
//		return map;
//	}
	
	
	
	
	
	@RequestMapping(value = "/testjson")
	public void testJson(HttpServletRequest request,
			HttpServletResponse response) {
		String str = "{ \"results\":19,\"datastr\":[ { \"productID\":\"1\",\"productCode\":\"083-QMC16009-19B\",\"productName\":\"C1系列600柜\"}, { \"productID\":\"2\",\"productCode\":\"083-QMC1-600B1-RL\",\"productName\":\"600侧山左右各1\"}, { \"productID\":\"3\",\"productCode\":\"083-QMC1-600B1-L\",\"productName\":\"左侧山\"}, { \"productID\":\"4\",\"productCode\":\"083-QMC1-600B1-L\",\"productName\":\"左侧山(两边铣)\"}, { \"productID\":\"5\",\"productCode\":\"083-QMC1-600H2\",\"productName\":\"600身后板\"}, { \"productID\":\"6\",\"productCode\":\"083-QMC1-600H2\",\"productName\":\"600身后板\"}, { \"productID\":\"7\",\"productCode\":\"083-QMC1-600H2\",\"productName\":\"600身后板\"}, { \"productID\":\"8\",\"productCode\":\"083-QMC1-600SF2\",\"productName\":\"大拉板\"}, { \"productID\":\"9\",\"productCode\":\"083-QMC1-600SF2\",\"productName\":\"大拉板\"}, { \"productID\":\"10\",\"productCode\":\"083-QMC1-600SF2H\",\"productName\":\"滑道条\"}, { \"productID\":\"11\",\"productCode\":\"QMC1-600G3\",\"productName\":\"金属衣杆\"}, { \"productID\":\"12\",\"productCode\":\"QMC1-600G3\",\"productName\":\"金属衣杆\"}, { \"productID\":\"13\",\"productCode\":\"QMC1-600G3\",\"productName\":\"金属衣杆\"}, { \"productID\":\"14\",\"productCode\":\"F-QMC1600L2-3-1\",\"productName\":\"电视柜\"}, { \"productID\":\"15\",\"productCode\":\"F-QMC1600L2-3CDB\",\"productName\":\"顶底山条\"}, { \"productID\":\"16\",\"productCode\":\"F-QMC1600L2-3CD\",\"productName\":\"顶底板\"}, { \"productID\":\"17\",\"productCode\":\"F-QMC1600L2-3CD\",\"productName\":\"顶底板\"}, { \"productID\":\"18\",\"productCode\":\"F-QMC1-H807\",\"productName\":\"380\500后身板\"}, { \"productID\":\"19\",\"productCode\":\"F-QMC1-H807\",\"productName\":\"后身板\"} ]} ";

		this.printStr(response, str);
	}

	// 注销登陆  
	@RequestMapping(params = "action=exit")
	public void exit(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.getSession().invalidate();
		// request.getSession().removeAttribute("person");
		this.printStr(response, "ok");
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtil.getString(null));
		System.out.println(StringUtil.getString("  "));
		System.out.println(StringUtil.getString(""));
	}
	
	

}
