package com.ass.basicmanage.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Service;

import com.ass.base.service.BaseServiceImpl;
import com.ass.basicmanage.dto.TOrganizationTreeNodeDTO;
import com.ass.common.generated.dao.TOrganizationMapper;
import com.ass.common.generated.model.TOrganization;
import com.ass.common.service.CommonService;
import com.ass.common.utils.JsonUtil;
import com.ass.common.utils.StringUtil;


@Service
public class BasicServiceImpl extends BaseServiceImpl implements BasicService {

	@Resource
	private TOrganizationMapper tOrganizationMapper;
	
	@Resource
	private CommonService commonService;
	
	private Map<String, String> param = new HashMap<String, String>();
	
	@Override
	public String saveOrganization(TOrganization tOrganization, String flag) {
		
		if("edit".equals(flag)){
			//edit
			tOrganizationMapper.updateByPrimaryKeySelective(tOrganization);
			//t_user, 有字段t_organization_code
			//修改organization需要修改其下员工的此字段
			String sql = "update t_user set t_organization_code = '"+tOrganization.getCode()+"' where t_organization_id = "+tOrganization.getId();
			commonService.updateBySql(sql);
			
			return null;
		}else{
			//add
			tOrganization.setIsLeaf(true);
			tOrganization.setPid(new Long(flag));
			tOrganizationMapper.insertSelective(tOrganization);
			//设置父节点的isleaf为false
			TOrganization tOrganizationParent = tOrganizationMapper.selectByPrimaryKey(tOrganization.getPid());
			if(tOrganizationParent.getIsLeaf()){//如果是叶子节点,则改成假
				tOrganizationParent.setIsLeaf(false);
				tOrganizationMapper.updateByPrimaryKeySelective(tOrganizationParent);
			}
			return StringUtil.getString(tOrganization.getId());
		}
		
	}
	
	///////////////////////////////////////////////
	
	
	
	
	
	
	
	
	@Override
	public void deleteOrganizationAndItsUser(String organizationId) {
		//删除组织机构
		String updateOrganization = "update t_organization set isdelete = 1 where id = "+ organizationId;
		commonService.updateBySql(updateOrganization);
		//删除其包含的用户
		String updateUser = "update t_user set isdelete = 1 where t_organization_id = "+ organizationId;
		commonService.updateBySql(updateUser);
		
		
	}
	
	

	
	
	

	

	@Override
	public String getAllOrganizationTree(String node) {
		node = "root";
		if("root".equals(node)){
			List<TOrganizationTreeNodeDTO> lst = new ArrayList<TOrganizationTreeNodeDTO>();
			lst = commonService.selectList(	"organizationManage_SpecSql.getTopOrganization", null);
			this.getOrganizationRecursion(lst);
			
			return JsonUtil.writeValue(lst);
		}else{
			return null;
		}
	}
	
	public List<TOrganizationTreeNodeDTO> getOrganizationRecursion(List<TOrganizationTreeNodeDTO> lst0) {
		/*
		 * 1 lst0.size()>0， 遍历其内容，判断是否有子节点
		 * 有：设置leaf为false，找到所有子节点，setChildren，继续方法 
		 * 否：设置leaf为true，结束 
		 * 2 lst.size()<=0, 结束。
		 */
		if (lst0.size() > 0) {//
			for (TOrganizationTreeNodeDTO hoDTO : lst0) {
				List<TOrganizationTreeNodeDTO> subLst = this.getSubTree(hoDTO.getId());
				if (subLst.size() > 0) {// 有子节点,把子节点都找出来,放到childRen中，然后递归此方法
					hoDTO.setLeaf(false);
					hoDTO.setChildren(subLst);
					hoDTO.setIconCls("organization_branch");//分支样式
					this.getOrganizationRecursion(subLst);
				} else {
					hoDTO.setIconCls("organization_leaf");//叶子节点样式
					hoDTO.setLeaf(true);
				}
			}
		}

		return lst0;
	}
	
	/**
	 * 获得此节点的子节点， 如果这个菜单为2级，那只获取其下的三级。四级不会获取
	 * 
	 * @param pid
	 * @return
	 */
	public List<TOrganizationTreeNodeDTO> getSubTree(Long pid) {
		this.param.clear();
		this.param.put("pid", StringUtil.getString(pid));
		List<TOrganizationTreeNodeDTO> lst = commonService.selectList("organizationManage_SpecSql.getLeafs", this.param);
		return lst;
	}

	@Override
	public void deleteUsersByIds(String ids) {
		String sql = "update t_user set isdelete = 1 where id in ("+ids+")";
		commonService.updateBySql(sql);
		
	}
	
	
	
	
}
