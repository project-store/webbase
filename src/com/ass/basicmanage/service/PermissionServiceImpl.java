package com.ass.basicmanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ass.base.service.BaseServiceImpl;
import com.ass.common.generated.dao.TRoleMapper;
import com.ass.common.generated.dao.TRolePermissionMapper;
import com.ass.common.generated.model.TRole;
import com.ass.common.generated.model.TRolePermission;
import com.ass.common.generated.model.TRolePermissionExample;
import com.ass.common.service.CommonService;
import com.ass.common.utils.DateUtil;
import com.ass.common.utils.StringUtil;



@Service
public class PermissionServiceImpl extends BaseServiceImpl implements PermissionService {

	@Resource
	private CommonService commonService;
	
	@Resource
	private TRolePermissionMapper tRolePermissionMapper;
	
	@Resource
	private TRoleMapper tRoleMapper;
	
	/**
	 * 根据Roleid 字符串 例如 1,2,3,  删除对应的权限 impl
	 * @param roleIds
	 * @author wangt
	 */
	@Override
	public int clearPermissionByRoleid(String roleIds) {
		String sql = "delete from t_role_permission where t_role_id in ("+roleIds.substring(0, roleIds.length()-1)+")";
		return commonService.deleteBySql(sql);
		
	}

	/**
	 * 保存 角色权限 roleIds 1,2,3, impl
	 * @param roleIds
	 * @param permissionIds
	 * @author wangt
	 */
	@Override
	public int saveRolePermission(String roleIds, String[] permissionIds) {
		//1先清除roleIds 的所有权限
		this.clearPermissionByRoleid(roleIds);
		
		if(permissionIds == null || permissionIds.length == 0){
			return 0;
		}
		//2设置权限，进行保存
		String[] ids = StringUtil.split(roleIds,",");
		int nowtime = DateUtil.getCurrentTimestamp();
		StringBuffer sb = new StringBuffer("insert into t_role_permission (t_role_id,t_permission_id,make_time,operator) values ");
		for(int i = 0; i < ids.length; i++){
			for(int j = 0; j < permissionIds.length; j++){
				sb.append("(").append(ids[i]).append(",").append(permissionIds[j]).append(",").append(nowtime).append(",").append("'"+this.getUser().getName()+"'").append(")");
				if(i != (ids.length-1) || j != (permissionIds.length-1) ){
					sb.append(",");
				}
			}
		}
		String sql = sb.toString();
		return commonService.insertBySql(sql);
		
		
		
	}
	
	
	
	
	@Override
	public String getRolePermissionByRoleId(String roleId) {
		TRolePermissionExample example = new TRolePermissionExample();
		example.createCriteria().andTRoleIdEqualTo(new Long(roleId));
		List<TRolePermission> lst = tRolePermissionMapper.selectByExample(example);
		StringBuffer sb = new StringBuffer("");
		for(int i = 0; i < lst.size(); i++){
			sb.append(lst.get(i).gettPermissionId());
			if(i != (lst.size()-1)){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * 保存新增的角色
	 * @param name
	 * @param note
	 * @author wangt 2014年11月27日 下午5:38:46 
	 */
	@Override
	public void saveRole(String name, String note) {
		TRole r = new TRole();
		r.setName(name);
		r.setRoleValue(note);
		r.setIsdelete("0");
		r.setMakeTime(DateUtil.getCurrentTimestamp());
		r.setOperator(this.getUser().getName());
		tRoleMapper.insertSelective(r);
	}

	/**
	 * 保存编辑后的角色
	 * @param role
	 * @author wangt 2014年11月27日 下午5:38:53 
	 */
	@Override
	public void editRole(TRole role) {
		role.setIsdelete("0");
		role.setModifyTime(DateUtil.getCurrentTimestamp());
		role.setModifyOperator(this.getUser().getName());
		tRoleMapper.updateByPrimaryKeySelective(role);
		
	}
	
	
	

	@Override
	public void deleteRoleByIds(String ids) {
		//删除角色
		String sql = "delete from t_role where id in ("+ ids +")";
		commonService.deleteBySql(sql);
		//删除  用户-角色
		String sql2 = "delete from t_user_role where t_role_id in ("+ids+")";
		commonService.deleteBySql(sql2);
		//删除 角色-权限
		String sql3 = "delete from t_role_permission where t_role_id in ("+ids+")";
		commonService.deleteBySql(sql3);
	}
	
	
	
	
	
	//根据Id获得其能查看的所有下属的id， 格式为：(1,2,3,4)
	//************************************  之下方法未使用   begin*******
	
	//根据Userid 获得其负责的 组织机构节点Id
		private List<String> getOrganizationIdByUserId(String Id){
			String sql = "select id from t_organization where incharge_user_id = "+Id;
			List<Map<String, Object>> lst = commonService.selectBySql(sql);
			List<String> l = new ArrayList<String>();
			if(lst.size() > 0){
				for(Map<String, Object> m : lst){
					l.add(StringUtil.getString(m.get("id")));
				}
				return l;
			}
			return null;
		}
		
	
	/**
	 * 根据Id获得其能查看的所有下属的id， 格式为：(1,2,3,4)
	 * 传null， 默认查询当前登陆人
	 * @param Id
	 * @return
	 * @author wangt 2014年11月27日 下午5:36:39 
	 */
	public String getSubordinateIds(Long Id){
		if(Id == null){
			Id = this.getUser().getId();
		}
		List<String> allSubordinateIds = new ArrayList<String>();
		allSubordinateIds.add(StringUtil.getString(Id));
		List<String> organizationIds = this.getOrganizationIdByUserId(StringUtil.getString(Id));
		this.setAllSubordinateRecursion(organizationIds, allSubordinateIds);
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(int i=0; i<allSubordinateIds.size(); i++){
			sb.append(allSubordinateIds.get(i));
			if(i != allSubordinateIds.size()-1){
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * 递归方法。
	 * 遍历组织机构节点，1 把组织机构下用户添加到 allSubordinate中
	 *            		2 继续找其子节点      找到：调用自身 
	 *                                    未找到： end
	 * @param organizationIds
	 * @param allSubordinates
	 * @return
	 * @author wangt 2014年11月27日 下午6:16:10 
	 */
	private List<String> setAllSubordinateRecursion(List<String> organizationIds, List<String> allSubordinates){
		if(organizationIds != null && organizationIds.size() > 0){//有组织机构节点
			for(String organizationId : organizationIds){
				//把节点下的用户找到， 放进 allSubordinates
				List<String> uIds = this.getSubordinateIdsByOrganizationId(organizationId);
				if(uIds != null){
					allSubordinates.addAll(uIds);
				}
				
				List<String> oIds = this.getIncludeNodesByOrganizationId(organizationId);
				if(oIds != null){
					this.setAllSubordinateRecursion(oIds, allSubordinates);
				}
			}
		}
		return allSubordinates;
	
	}
	
	//根据 organizationId 获得其下包含用户Id
	private List<String> getSubordinateIdsByOrganizationId(String Id){
		String sql = "select id from t_user where t_organization_id = "+Id;
		List<Map<String, Object>> lst = commonService.selectBySql(sql);
		List<String> l = new ArrayList<String>();
		if(lst.size()>0){
			for(Map<String, Object> m : lst){
				l.add(StringUtil.getString(m.get("id")));
			}
			return l;
		}
		return null;
	}
	
	//通过组织机构Id  获取其 下属组织机构节点的Id
	private List<String> getIncludeNodesByOrganizationId(String id){
		String sql = "select id from t_organization where pid = "+id;
		List<Map<String, Object>> lst = commonService.selectBySql(sql);
		List<String> l = new ArrayList<String>();
		if(lst.size()>0){
			for(Map<String, Object> m : lst){
				l.add(StringUtil.getString(m.get("id")));
			}
			return l;
		}
		return null;
	}
	
	//************************************  之下方法未使用   end*******	
		
		
	
	
	
	
	
	
}
