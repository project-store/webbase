package com.ass.basicmanage.service;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ass.base.model.PageGrid;
import com.ass.base.model.PageQueryModel;
import com.ass.base.service.BaseServiceImpl;
import com.ass.common.generated.dao.TOrganizationMapper;
import com.ass.common.generated.dao.TUserMapper;
import com.ass.common.generated.model.TOrganizationExample;
import com.ass.common.generated.model.TUser;
import com.ass.common.service.CommonService;
import com.ass.common.utils.StringUtil;

@Service
public class BasicDwrServiceImpl extends BaseServiceImpl {

	@Resource
	private CommonService commonService;
	
	@Resource
	private TUserMapper tUserMapper;
	
	@Resource 
	private TOrganizationMapper tOrganizationMapper;
	
	
	
	//删除组织机构节点
	public Map<String, Object> deleteOrganization(String str) {
		//1 判断要删除的节点 是否有子节点
		String sql = "select id from t_organization where pid = "+str;
		List<Map<String, Object>> lst = commonService.selectBySql(sql);
		Map<String, Object> m = this.getMap();
		m.put("isok", "1");
		if(lst.size() > 0){
			m.put("isok", "0");
			m.put("msg", "当前机构包含下属机构，不能删除。");
			return m;
		}
		
		//3判断要删除的节点的父节点是否还有其他子节点,如果没有, 设置其isLeaf为true
		String fujiedianId = commonService.selectText(" select pid from t_organization where id = "+str);
		
		String sql4 = "select id from t_organization where pid = "+fujiedianId+" and isdelete = 0";
		//父节点下的所有子节点(包括自己)
		List<Object> numL = commonService.selectBySql(sql4);//
		if(numL.size() == 1){
			//父节点只有 要删除的这一个子节点 设置父节点的 isLeaf为true
			String sql5 = "update t_organization set is_leaf = true where id ="+fujiedianId;
			commonService.updateBySql(sql5);
		}
		
		
		//2 删除节点  ， 删除所包含的员工
		String sql1 = "update t_organization set isdelete = 1 where id = "+str;
		String sql2 = "update t_user set isdelete = 1 where t_organization_id = "+str;
		commonService.updateBySql(sql1);
		commonService.updateBySql(sql2);
		
		
		
		
		return m;
	}
	
	public String deleteUser(String id){
		String sql = "update t_user set isdelete = 1 where id = "+id;
		commonService.updateBySql(sql);
		return "ok";
	}
	
	
	public Map<String, Object> getUserInfo(String id){
		Map<String, Object> m = this.getMap();
		TUser u =tUserMapper.selectByPrimaryKey(new Long(id));
		//基本信息
		m.put("tOrganizationId", u.gettOrganizationId());
		m.put("id", u.getId());
		m.put("name",u.getName());
		m.put("loginName", u.getLoginName());
		m.put("mobile",u.getMobile());
		m.put("email", u.getEmail());
		//查找用户拥有的角色
		String sql = "select t_role_id from t_user_role where t_user_id = "+u.getId();
		List<Map<String, Object>> l = commonService.selectBySql(sql);
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<l.size(); i++){
			sb.append(l.get(i).get("t_role_id"));
			if(i < (l.size()-1)){
				sb.append(",");
			}
		}
		m.put("roleIds", sb.toString());
		
		return m;
	}
	
	
	
	
	
	
	
	///////////////////////////////////
	
	public String deleteLogs(String logIds){
		String sql = "UPDATE t_log SET isdelete=1 WHERE id IN("+logIds+")";
		commonService.updateBySql(sql);
		return "success";
	}
	
	
	
	
	
}
