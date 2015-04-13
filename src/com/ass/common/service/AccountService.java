package com.ass.common.service;

import java.util.List;

import com.ass.common.generated.model.TUser;

public interface AccountService {
	
	/**
	 * 保存新建用户以及其角色
	 * @param tUser
	 * @param roles
	 * @author wangt 2014年11月27日 下午5:32:19 
	 */
	public void saveUser(TUser tUser, String roles);
	
	/**
	 * 保存编辑后的用户信息以及角色
	 * @param tUser
	 * @param roles
	 * @author wangt 2014年11月27日 下午5:32:44 
	 */
	public void editUser(TUser tUser, String roles);
	
	/**
	 * 获得用户的所有权限 的permission
	 * 传null为当前登录人
	 * @param id
	 * @return List<String>
	 */
	public List<String> getPermissionsByUserid(Long id);
	
	
	/**
	 * 获得用户的所有权限 的id
	 * 传null为当前登录人
	 * @param id
	 * @return
	 * @author wangt 2014年11月27日 下午9:44:03 
	 */
	public List<String> getPermissionIdsByUserid(Long id);
	
	/**
	 * 判断是否是管理员
	 * 传null为当前登录人
	 * @param id
	 * @return
	 * @author wangt 2014年11月27日 下午9:50:15 
	 */
	public boolean isAdmin(Long id);
	
	
	/**
	 * 判断是否有同级查询权限
	 * 传null为当前登录人
	 * @param id
	 * @return
	 * @author wangt 2014年11月27日 下午9:50:15 
	 */
	public boolean haveSiblingQuery(Long id);
	
	
	/**
	 * 判断是否有查询下级的权限
	 * 传null为当前登录人
	 * @param id
	 * @return
	 * @author wangt 2014年11月27日 下午9:53:12 
	 */
	public boolean haveLowerQuery(Long id);
	
	
	/**
	 * 通过登录名获得用户
	 * @param LoginName
	 * @return
	 * @author wangt 2014年11月27日 下午5:33:06 
	 */
	public TUser findUserByLoginName(String LoginName);
	
	
	
	
	
}
