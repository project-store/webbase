package com.ass.basicmanage.dto;

import java.util.List;

import com.ass.common.utils.StringUtil;

/**
 * 用于初始化 角色权限管理页面时候， 存放所有的权限信息
 * @author wangt
 * @time 2014年4月9日 下午10:27:53 
 */
public class PermissionTreeDTO {
	
	private Long id;

	private Long pid;

	private String text;
	
	private boolean leaf;
	//用于权限树的时候
	
	private String permission;

	private String value;

	private Integer position;
	
	private String cls;
	
	private List<PermissionTreeDTO> children;
	
	private boolean expanded = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public List<PermissionTreeDTO> getChildren() {
		return children;
	}

	public void setChildren(List<PermissionTreeDTO> children) {
		this.children = children;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}


	
	
	
	
}
