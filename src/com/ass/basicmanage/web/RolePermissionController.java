package com.ass.basicmanage.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ass.base.web.BaseController;
import com.ass.basicmanage.dto.PermissionTreeDTO;
import com.ass.basicmanage.service.PermissionService;
import com.ass.common.generated.dao.TRoleMapper;
import com.ass.common.generated.model.TRole;
import com.ass.common.service.CommonService;
import com.ass.common.utils.JsonUtil;
import com.ass.common.utils.StringUtil;



@Controller
@RequestMapping("/rolePermission.do")
public class RolePermissionController extends BaseController{

	
	@Resource
	private CommonService commonService;
	
	@Resource
	private PermissionService permissionService;
	
	@Resource
	private TRoleMapper tRoleMapper;
	
	//角色权限管理页面
	public static final String INIT_ROLE_PERMISSION = "ass/basicmanage/rolePermission";
	
	/**
	 * 初始化 角色权限页面
	 */
	@RequestMapping(params="action=initRolePermission" )
	public ModelAndView initRolePermission(HttpServletRequest request, HttpServletResponse response){
		logger.info("初始化 角色权限管理页面");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(INIT_ROLE_PERMISSION);
		
		//查找所有权限
		List<PermissionTreeDTO> lst = commonService.selectList("userManage_SpecSql.searchAllPermission", null);
		modelAndView.addObject("allPermissionLst", lst);
		
		//查出所有角色
		List<Map<String, Object>> lstR = commonService.selectList("userManage_SpecSql.getRoles", null);
		modelAndView.addObject("roleLst", lstR);
		
		return modelAndView;
	}
	
	
	/**
	 * 根据角色id获得其权限。
	 * @param request
	 * @param response
	 * @author wangt 2014年11月20日 下午12:08:38 
	 */
	@RequestMapping(params="action=getRolePermissionByRoleId")
	public void getRolePermissionByRoleId(HttpServletRequest request, HttpServletResponse response) {
		String roleId = request.getParameter("roleid");
		String result = permissionService.getRolePermissionByRoleId(roleId);
		Map<String, Object> m = this.getMap();
		m.put("roleStr", result);
		this.printStr(response, JsonUtil.writeValue(m));
	}
	
	@RequestMapping(params="action=saveRolePermission")
	public void saveRolePermission(HttpServletRequest request, HttpServletResponse response) {
		String roleIds = request.getParameter("roleid");
		Map<String, Object> m = this.getMap();
		if(StringUtil.isEmpty(roleIds)){
			m.put("ok", "保存失败。");
			this.printStr(response, JsonUtil.writeValue(m));
		}else{
			String[] pcIds = request.getParameterValues("pcCheckbox");
			// 传参 到service层，保存权限
			int resultNum = permissionService.saveRolePermission(roleIds, pcIds);
			logger.info("往t_role_permission表增加了"+ resultNum + "条记录");
			m.put("ok", "ok");
			this.printStr(response, JsonUtil.writeValue(m));
		}
		
	}
	
	
	
	
	
	@RequestMapping(params="action=saveOrEditRole")
	public void saveOrEditRole(HttpServletRequest request, HttpServletResponse response) {
		String roleid = request.getParameter("roleid");
		String name = StringUtil.decode(request.getParameter("name"));
		String note = StringUtil.decode(request.getParameter("note"));
		Map<String, Object> m = this.getMap();
		m.put("ok", "ok");
		if("0".equals(roleid)){
			//insert role 代码
			permissionService.saveRole(name, note);
			m.put("msg", "保存成功!");
			
		}else{
			//update role 代码
			TRole ht = new TRole();
			ht.setId(new Long(roleid));
			ht.setName(name);
			ht.setRoleValue(note);
			permissionService.editRole(ht);
			m.put("msg", "更新成功!");
		}
		
		this.printStr(response, JsonUtil.writeValue(m));
		
	}
	
	
	
	
	@RequestMapping(params="action=getRoleInfo")
	public void getRoleInfo(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		String roleid = request.getParameter("roleid");

		TRole tr = tRoleMapper.selectByPrimaryKey(new Long(roleid)); 
		this.printStr(response, JsonUtil.writeValue(tr));
		
	}
	
	
	@RequestMapping(params="action=deleteRole")
	public void deleteRole(HttpServletRequest request, HttpServletResponse response) {
		String ids = request.getParameter("roleIds");
		logger.info("删除角色信息， id为：" + ids);
		permissionService.deleteRoleByIds(ids);
		this.printStr(response, JsonUtil.writeValue(this.getMap()));
	}
	
	
}
