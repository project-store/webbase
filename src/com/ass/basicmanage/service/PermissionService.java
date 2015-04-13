package com.ass.basicmanage.service;

import java.util.List;
import java.util.Map;

import com.ass.common.generated.model.TRole;
import com.ass.common.utils.StringUtil;


public interface PermissionService {

	/**
	 * 情况角色对应的所有全新啊
	 * @param roleIds
	 * @return
	 * @author wangt 2014年11月20日 上午11:39:00 
	 */
	public int clearPermissionByRoleid(String roleIds);
	
	
	/**
	 * 保存角色权限
	 * @param roleIds
	 * @param authIds
	 * @return
	 * @author wangt 2014年11月20日 上午11:39:12 
	 */
	public int saveRolePermission(String roleIds, String[] authIds);
	
	
	/**
	 * 通过角色id 获得对应的权限
	 * @param roleId
	 * @return
	 * @author wangt 2014年11月20日 上午11:39:24 
	 */
	public String getRolePermissionByRoleId(String roleId);
	
	public void saveRole(String name, String note);

	public void editRole(TRole role);
	
	
	/**
	 * 删除角色
	 * @param ids
	 * @author wangt 2014年11月20日 下午2:00:47 
	 */
	public void deleteRoleByIds(String ids);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 未使用！！
	 * 根据Id获得其能查看的所有下属的id， 格式为：(1,2,3,4)
	 * 传null， 默认查询当前登陆人
	 * @param Id
	 * @return
	 * @author wangt 2014年11月27日 下午5:36:39 
	 */
	public String getSubordinateIds(Long Id);
	
	
}
