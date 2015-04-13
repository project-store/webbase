package com.ass.basicmanage.dto;

import java.util.List;


/**
 * 
 * @author wangt 2014年11月19日 下午8:05:20 
 */
public class TOrganizationTreeNodeDTO {
	private Long id;//

	private String name;

	private Long pId;

	private String iconCls;
	
	private boolean open = false;

	/**
	 * 是否是叶子节点. true: 是. children为null 
	 * 	false: 不是叶子节点
	 */
	private boolean leaf;

	private List<TOrganizationTreeNodeDTO> children;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public List<TOrganizationTreeNodeDTO> getChildren() {
		return children;
	}

	public void setChildren(List<TOrganizationTreeNodeDTO> children) {
		this.children = children;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	
	
}
