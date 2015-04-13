package com.ass.basicmanage.service;

import java.util.List;

import com.ass.common.generated.model.TOrganization;


public interface BasicService {
	
	/**
	 * flag为id，表示新增。
	 * flag为edit，表示编辑。
	 * @param tOrganization
	 * @param flag
	 * @return edit返回null， add返回新增数据的id
	 * @author wangt 2014年11月19日 下午5:27:27 
	 */
	public String saveOrganization(TOrganization tOrganization, String flag);
	
	
	/**
	 * 删除一个组织机构几点（isdelete置1）
	 * 并且，删除其所包含的用户（isdelete置1）
	 * @param organizationId
	 * @author wangt 2014年11月19日 下午7:54:16 
	 */
	public void deleteOrganizationAndItsUser(String organizationId);
	
	/**
	 * 获得整个组织机构数所有的节点
	 * @param node
	 * @return
	 * @author wangt 2014年11月19日 下午8:09:02 
	 */
	public String getAllOrganizationTree(String node);
	
	
	/**
	 * 通过逗号隔开的用户id，删除用户（isdelete置1）
	 * @param ids
	 * @author wangt 2014年11月20日 上午10:11:56 
	 */
	public void deleteUsersByIds(String ids);
	
}
