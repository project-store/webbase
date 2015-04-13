package com.ass.common.web;

import java.io.IOException;
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
import com.ass.common.service.PropertiesService;
import com.ass.common.utils.JsonUtil;
import com.ass.common.utils.StringUtil;

/**
 */
@Controller
@RequestMapping("/selectOption.do")
public class SelectOptionController extends BaseController {

	@Resource
	private CommonService commonService;

	@Resource
	private PropertiesService propertiesService;

	/**用户下拉框
	 * @param request
	 * @param response
	 * void
	 * @author wangt 2015年1月13日 下午6:07:36 
	 */
	@RequestMapping(params="action=user")
	public void getUserCombobox(HttpServletRequest request, HttpServletResponse response) {
		String param = request.getParameter("index");
		String sql = "";
		if(StringUtil.isEmpty(param)){
			sql = "SELECT id, CONCAT(NAME,'(',login_name,')') name FROM t_user where isdelete = 0" ;
		}else{
			sql = "SELECT t.id id , t.namea name FROM "
					+ "( SELECT id,  CONCAT(NAME, '(', login_name, ')') namea FROM  t_user WHERE isdelete = 0 ) t "
					+ " WHERE t.namea LIKE '%"+param+"%' ";
		}
		
		List<Map<String, String>> lst = commonService.selectBySql(sql);
		this.printStr(response, JsonUtil.writeValue(lst));
	}
	
	/**
	 * 获得组织机构的下拉框
	 * @param request
	 * @param response
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @author wangt 2014年11月19日 下午8:52:27 
	 */
	@RequestMapping(params="action=organization")
	public void getOrganizationCombobox(HttpServletRequest request, HttpServletResponse response) {
		String sName = request.getParameter("index");
		String sql = "select id, name from t_organization  where isdelete = 0 order by id";
		if(StringUtil.isNotEmpty(sName)){
			sql = "select id, name from t_organization  where isdelete = 0 and name like '%"+sName+"%' order by id";
		}
		List<Map<String, String>> lst = commonService.selectBySql(sql);
		this.printStr(response, JsonUtil.writeValue(lst));
	}
	
	

	/**
	 * 根据codetype 获得 id(key)--id(value)     name--codename 的properties 
	 * @param request
	 * @param response
	 * @author wangt 2014年11月26日 上午10:21:00 
	 */
	@RequestMapping(params="action=id2nameOfProperties")
	public void getId2nameOfProperties(HttpServletRequest request, HttpServletResponse response){
		String codetype = request.getParameter("index");
		if(StringUtil.isEmpty(codetype)){
			return ;
		}
		List<Map<String, String>> l = propertiesService.getComboboxId2NameByType(codetype);
		this.printStr(response, JsonUtil.writeValue(l));
		
	}
	
	
	
	
	/**
	 * 根据codetype 获得 id(key)--code(value)     name--codename 的properties 
	 * @param request
	 * @param response
	 * @author wangt 2014年11月26日 上午10:20:28 
	 */
	@RequestMapping(params="action=code2nameOfProperties")
	public void getCode2nameOfProperties(HttpServletRequest request, HttpServletResponse response){
		String codetype = request.getParameter("index");
		if(StringUtil.isEmpty(codetype)){
			return ;
		}
		List<Map<String, String>> l = propertiesService.getComboboxCode2NameByType(codetype);
		this.printStr(response, JsonUtil.writeValue(l));
		
	}
	
	
	
	
	
	
	
}


