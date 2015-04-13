package com.ass.basicmanage.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import com.ass.basicmanage.service.BasicService;
import com.ass.common.generated.dao.TOrganizationMapper;
import com.ass.common.generated.dao.TUserMapper;
import com.ass.common.generated.model.TOrganization;
import com.ass.common.generated.model.TUser;
import com.ass.common.service.AccountService;
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
@RequestMapping("/basic.do")
public class BasicController extends BaseController {

	@Resource
	private CommonService commonService;
	
	@Resource
	private TOrganizationMapper tOrganizationMapper;
	
	@Resource
	private TUserMapper tUserMapper;
	
	@Resource
	private BasicService basicService;
	
	@Resource
	private AccountService accountService;
	
	public static final String organization_page= "ass/basicmanage/organization";
	
	public static final String show_organization = "ass/basicmanage/organization_userManage";
	
	
	
	/**显示组织机构页面
	 * @param request
	 * @param response
	 * @return
	 * ModelAndView
	 * @author wangt 2015年1月14日 下午2:47:17 
	 */
	@RequestMapping(params="action=showOrganization" )
	public ModelAndView organizationPage(HttpServletRequest request, HttpServletResponse response){
		logger.info("初始化组织机构");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(organization_page);
		//查出所有角色
		List<Map<String, Object>> lst = commonService.selectList("userManage_SpecSql.getRoles", null);
		modelAndView.addObject("roleLst", lst);
		
		return modelAndView;
		
	}
	/**
	 * @param request
	 * @param response
	 * void
	 * @author 2015年1月10日 下午8:24:58 
	 */
	@RequestMapping(params="action=organizationTree")
	public void organizationTree(HttpServletRequest request, HttpServletResponse response) {
		// 1 一次获得所有的树
		String id = request.getParameter("id");
		logger.info("获取组织机构数节点：  "+ id);
//		this.printStr(response, basicService.getAllOrganizationTree(id));
		
		/* 2 一层一层获得树的方法
		 * String nodeId = request.getParameter("node");*/
		 
		if(StringUtil.isEmpty(id)){
			String sql = " select id, concat(name,'(',code,')') text, pid pid, true expanded, is_leaf leaf "
					+ " from t_organization where isdelete = 0 and pid = 0 ";
			
			List<Map<String, Object>> lst = commonService.selectBySql(sql);
			
			//往下查询一级节点
			
			String sql2 = " select id, concat(name,'(',code,')') text, pid pid,  is_leaf leaf "
					+ "  from t_organization where isdelete = 0 and pid = "+lst.get(0).get("id");
			List<Map<String, Object>> lst2 = commonService.selectBySql(sql2);
			lst.get(0).put("children", lst2);
			
			this.printStr(response, JsonUtil.writeValue(lst));
			return;
		}else{
			String sql = " select id, concat(name,'(',code,')') text, pid pid, is_leaf leaf "
					+ "  from t_organization where isdelete = 0 and pid = "+id;
			List<Map<String, Object>> lst = commonService.selectBySql(sql);
			this.printStr(response, JsonUtil.writeValue(lst));
			return;
			}
			
		
		}
	
	
	
	
	
	/**用户管理页面
	 * @param request
	 * @param response
	 * @return
	 * ModelAndView
	 * @author 2015年1月10日 下午11:06:02 
	 */
	@RequestMapping(params="action=userManage" )
	public ModelAndView userManagePage(HttpServletRequest request, HttpServletResponse response){
		logger.info("初始化 用户管理页面");
		String organizationId = request.getParameter("id");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(show_organization);
		
		if(null == organizationId){
			organizationId = "1";
		}
		
		//1 组织机构名称
		TOrganization organization = tOrganizationMapper.selectByPrimaryKey(Long.parseLong(organizationId));
		modelAndView.addObject("organization", organization);
		//2 负责人 姓名 ， 联系方式
		TUser oUser = tUserMapper.selectByPrimaryKey(organization.getInchargeUserId());
		modelAndView.addObject("oUser", oUser);
		/* ajax 方法做了
		//3 包含用户的列表
		Map<String, String> param = new HashMap<String, String>();
		//param.put("tOrganizationId", organizationId);
		// name
		//loginname
		List<Map<String, Object>> list = commonService.selectList("userManage_SpecSql.getUserRelated", param);
		modelAndView.addObject("uList", list);
		*/
		
		return modelAndView;
	}
	
	
	/**
	 * 保存or更新组织机构信息
	 * @param request
	 * @param response
	 * @author wangt 2014年11月19日 下午4:51:40 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(params="action=saveOrEditOrganization")
	public void saveOrEditOrganization(HttpServletRequest request, HttpServletResponse response)  {

		String addOrEdit = request.getParameter("flag");//edit or add
		String inchargeUserId = request.getParameter("inchargeUser");
		String name = request.getParameter("organizationName");
		String code = request.getParameter("organizationCode");
		String organizationId = request.getParameter("OrganId");
		String tel = request.getParameter("organizationTel");
		
		TOrganization o = new TOrganization();
		if(StringUtil.isNotEmpty(inchargeUserId)){
			o.setInchargeUserId(new Long(inchargeUserId));
		}
		o.setName(name);
		o.setCode(code);
		o.setTel(tel);
		if("edit".equals(addOrEdit)){
			//edit organization
			Map<String, Object> m = this.getMap();
			m.put("code", "ok");
			//1 验证部门编码是否重复
			String sql = "select id, code from t_organization where code = '"+o.getCode()+"'";
			List<Map<String, Object>> lst = commonService.selectBySql(sql);
			// A size 等于1（未修改部门编码 或者修改成别的部门的了） 
			// B size == 0  ok
			if(lst.size() == 0){
				//ok无操作
			}else if(lst.size() == 1){
				String getId = StringUtil.getString(lst.get(0).get("id"));
				if(getId.equals(organizationId)){
					//未修改部门编码， ok
				}else{
					//修改成别的部门的了
					m.put("code", "部门编码已经存在，请重新输入。");
					this.printStr(response, JsonUtil.writeValue(m));
					return;
				}
			}else{
				m.put("code", "机构编码有重复，请检查！");
				this.printStr(response, JsonUtil.writeValue(m));
				return;
			}
			
			o.setId(new Long(organizationId));
			basicService.saveOrganization(o, "edit");
			
			m.put("id", organizationId);
			m.put("text", name);
			m.put("msg", "修改成功!");
			m.put("flag", "edit");
			this.printStr(response, JsonUtil.writeValue(m));
		}else{
			//add organization
			Map<String, Object> m = this.getMap();
			m.put("code", "ok");
			//1 检测部门编号是否重复
			String sql = "select * from t_organization where code = '"+o.getCode()+"'";
			List<Object> l = commonService.selectBySql(sql);
			if(l.size() > 0){
				m.put("code", "部门编号已经存在，请重新输入。");
				this.printStr(response, JsonUtil.writeValue(m));
				return;
			}
			
			String nId = basicService.saveOrganization(o, organizationId);
			
			m.put("id", nId);
			m.put("text", name);
			m.put("pid", organizationId);
			m.put("msg", "新增成功!");
			m.put("flag", "add");
			this.printStr(response, JsonUtil.writeValue(m));
		}
		
		
	}
	
	

	/**保存用户  默认密码和loninname相等
	 * @param tUser
	 * @param request
	 * @param response
	 * void
	 * @author wangt 2015年1月13日 下午9:15:13 
	 */
	@RequestMapping(params="action=saveUser")
	public void saveUser(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("uname");
		String loginname = request.getParameter("uloginname");
		String organizationid = request.getParameter("belongOid");
		String mobile = request.getParameter("umobile");
		String email = request.getParameter("uemail");
		String uid = request.getParameter("uid");
		TUser tUser = new TUser();
		tUser.setId(new Long(uid));
		tUser.setName(name);
		tUser.setLoginName(loginname);
		tUser.setMobile(mobile);
		tUser.setEmail(email);
		//获得 机构代码
		String organizationCode = commonService.selectText("select code from t_organization where id = "+organizationid);
		tUser.settOrganizationId(new Long(organizationid));
		tUser.settOrganizationCode(organizationCode);
		
		String roleids = request.getParameter("roleids");
		roleids = StringUtil.replaceAllStr(roleids, "role", "");
		Map<String, Object> m = this.getMap();
		m.put("id", tUser.gettOrganizationId());
		/* 角色
		String roles = request.getParameter("htRoleIds");
		if(StringUtil.isBlank(roles)){
			roles = "";
		}*/
		
		String sql = "select id from t_user where login_name = '"+tUser.getLoginName()+"'";
		List<Map<String, Object>> lst = commonService.selectBySql(sql);
		if("0".equals(StringUtil.getString(tUser.getId()))){//新增
			//判断员工号是否重复
			
			if(lst.size() == 0){
				tUser.setIsdelete(0);
				accountService.saveUser(tUser, roleids);
				m.put("msg", "添加用户"+tUser.getName()+"成功!");
				this.printStr(response, JsonUtil.writeValue(m));
			}else{
				m.put("msg", "员工号已经存在,请重新输入。");
				m.put("success", false);
				this.printStr(response, JsonUtil.writeValue(m));
			}
			
		}else{//修改
			//判断员工号是否重复
			if(lst.size() == 0){
				accountService.editUser(tUser,roleids);
				m.put("msg", "修改用户"+tUser.getName()+"信息成功!");
				this.printStr(response, JsonUtil.writeValue(m));
			}else if(lst.size() == 1){
				if(StringUtil.getString(lst.get(0).get("id")).equals(StringUtil.getString(tUser.getId()))){
					//没有修改 继续保存
					accountService.editUser(tUser,roleids);
					m.put("msg", "修改用户"+tUser.getName()+"信息成功!");
					this.printStr(response, JsonUtil.writeValue(m));
				}else{
					m.put("msg", "员工号已经存在,请重新输入。");
					m.put("success", false);
					this.printStr(response, JsonUtil.writeValue(m));
				}
			}else{
				m.put("msg", "员工号存在重复");
				m.put("success", false);
				this.printStr(response, JsonUtil.writeValue(m));
			}
			
		}
		
		
	}
	
	
	
	/**权限
	 * @param request
	 * @param response
	 * void
	 * @author wangt 2015年1月14日 下午4:20:33 
	 */
	@RequestMapping(params="action=permissionTree")
	public void permissionTree(HttpServletRequest request,HttpServletResponse response)  {
		List<PermissionTreeDTO> lst = commonService.selectList("userManage_SpecSql.searchAllPermission", null);
		String roleIds = request.getParameter("ids");
		
		if(StringUtil.isNotEmpty(roleIds)){
			roleIds = StringUtil.replaceAllStr(roleIds, "role", "");
			//通过 roleids 获得所包含的权限
			List<Map<String, Object>> permissionLst = commonService.selectBySql("select t_permission_id  from t_role_permission where t_role_id in ("+ roleIds +")");
			
			//去除lst 中重复的数据
			HashSet<Map<String, Object>> hs = new HashSet<Map<String,Object>>(permissionLst);
			permissionLst.clear();
			permissionLst.addAll(hs);
			//把List<Map<>> 转成 List<Long>
			List<Long> permissionLstLong = new ArrayList<Long>();
			for(Map<String,Object> m : permissionLst){
				permissionLstLong.add(Long.parseLong(StringUtil.getString(m.get("t_permission_id"))));
			}
			for(PermissionTreeDTO ptDTO : lst){
				if(permissionLstLong.contains(ptDTO.getId())){
					ptDTO.setCls("checkPer");
				}
				if(ptDTO.getChildren() != null){
					for(PermissionTreeDTO ptDTOChild : ptDTO.getChildren()){
						if(permissionLstLong.contains(ptDTOChild.getId())){
							ptDTOChild.setCls("checkPer");
						}
					}
				}
				
			}
			
			
		}
		this.printStr(response, JsonUtil.writeValue(lst));
	}
	
	/**
	 * 通过角色id获得包含的所有权限permission。  放到上面的方法里了
	 * @param request
	 * @param response
	 * @author wangt 2014年11月19日 上午11:38:03 
	 */
	@RequestMapping(params="action=getAllPermissionsByRoleIds")
	public void getAllPermissionsByRoleIds(HttpServletRequest request, HttpServletResponse response) {
		String roleids = request.getParameter("ids");// 1,2,3
		roleids = StringUtil.replaceAllStr(roleids, "role", "");
		if(StringUtil.isNotEmpty(roleids)){
			//通过 roleids 获得所包含的权限
			List<Map<String, Object>> lst = commonService.selectBySql("select t_permission_id  from t_role_permission where t_role_id in ("+ roleids +")");
			
			//去除lst 中重复的数据
			HashSet<Map<String, Object>> hs = new HashSet<Map<String,Object>>(lst);
			lst.clear();
			lst.addAll(hs);
			
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<lst.size(); i++){
				sb.append(StringUtil.getString(lst.get(i).get("t_permission_id")));
				if(i != (lst.size()-1)){
					sb.append(",");
				}
			}
			String sbstr = sb.toString();
			Map<String, Object> m = this.getMap();
			if(StringUtil.isNotEmpty(sbstr)){
				m.put("str", sbstr);
				this.printStr(response, JsonUtil.writeValue(m));
			}else{
				m.put("str", "none");
				this.printStr(response, JsonUtil.writeValue(m));
			}
			
		}else{
			Map<String, Object> m = this.getMap();
			m.put("str", "none");
			this.printStr(response, JsonUtil.writeValue(m));
		}
		
	}
	
	
	
	
	
/////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	public static void main(String[] args) {
		System.out.println("sdfsdf.sdf".split("\\.")[0]);
		System.out.println("sdfsdf.sdf".split("\\.")[1]);
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("1", true);
		m.put("2", "true");
		System.out.println(JsonUtil.writeValue(m));
		System.out.println(StringUtil.isEmpty(""));
		System.out.println(StringUtil.isEmpty(" "));
		System.out.println(StringUtil.isBlank(""));
		System.out.println(StringUtil.isBlank(" "));
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获得一个机构的详细信息
	 * @param request
	 * @param response
	 * @author wangt 2014年11月19日 下午2:57:35 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(params="action=getOrganizationDetail")
	public void getOrganizationDetail(HttpServletRequest request, HttpServletResponse response) {
		String organizationId = request.getParameter("tOrganizationId");
		String sql = "select tor.id, tor.pid, tor.name, tor.code, tor.incharge_user_id uid, tor.tel, tor.phone, tor.introduce, "
				+ " concat(tu.name,'(',tu.login_name,')') uname from t_organization tor "
				+ "left join t_user tu on tu.id = tor.incharge_user_id where tor.id = "+organizationId;
		Map<String, Object> m = commonService.selectOneBySql(sql);
		this.printStr(response, JsonUtil.writeValue(m));
	}
	
	
	
	
	

	
	
	
	/**
	 * 删除一个组织机构几点（isdelete置1） 并且，删除其所包含的用户（isdelete置1）
	 * @param request
	 * @param response
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @author wangt 2014年11月19日 下午9:04:55 
	 */
	@RequestMapping(params="action=deleteOrganization")
	public void deleteOrganization(HttpServletRequest request, HttpServletResponse response) {
		String organizationId = request.getParameter("id");
		basicService.deleteOrganizationAndItsUser(organizationId);
		
		Map<String, Object> m = this.getMap();
		this.printStr(response, JsonUtil.writeValue(m));
	}
	
	
	
	
	
	@RequestMapping(params="action=getRolesByUserId")
	public void getRolesByUserId(TUser tUser,HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		Map<String, Object> m = this.getMap();
		if(StringUtil.isNotBlank(userId)){
			String sql = "select t_role_id from t_user_role where t_user_id = "+userId;
			List<Map<String, Object>> l = commonService.selectBySql(sql);
			StringBuilder sb = new StringBuilder("");
			for(int i=0; i<l.size(); i++){
				sb.append(StringUtil.getString(l.get(i).get("t_role_id")));
				if(i != l.size()-1){
					sb.append(",");
				}
			}
			if(StringUtil.isNotBlank(sb.toString())){
				m.put("id", sb.toString());
				this.printStr(response, JsonUtil.writeValue(m));
			}else{
				m.put("id", "none");
				this.printStr(response, JsonUtil.writeValue(m));
			}
			
		}else{
			m.put("id", "none");
			this.printStr(response, JsonUtil.writeValue(m));
		}
	}
	
	
	@RequestMapping(params="action=deleteUsers")
	public void deleteUsers(HttpServletRequest request, HttpServletResponse response){
		String ids = request.getParameter("ids");
		basicService.deleteUsersByIds(ids);
		this.printStr(response, "true");
	}
	
	
	
	
	
	

}
