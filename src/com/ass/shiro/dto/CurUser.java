package com.ass.shiro.dto;

import java.io.Serializable;

import com.ass.common.generated.model.TUser;
import com.google.common.base.Objects;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 * @author wangt 2014年11月17日 下午6:09:42 
 */
public class CurUser {
	private Long id;
	private String loginName;
	private String name;
	private String queryLevel;
	private Long tOrganizatioinId;
	private String tOrganizationCode;
	/*
	同级+子级  
	<test="queryLevel==all"> 
	code like '8601%'
	</test>
	同级  sibling
	<test="queryLevel==sibling"> 
	code = '8601'
	</test>
	子级   lower
	<test="queryLevel==lower"> 
	code like "8601%" and length(code) > length(8601)
	</test>
	*/
	
	public CurUser(TUser tUser, String queryLevel) {
		this.id        = tUser.getId();
		this.loginName = tUser.getLoginName();
		this.name      = tUser.getName();
		this.queryLevel = queryLevel;
		this.tOrganizatioinId = tUser.gettOrganizationId();
		this.tOrganizationCode = tUser.gettOrganizationCode();
		
	}
	public CurUser(String id, String loginName, String name, String queryLevel) {
		this.id        = new Long(id);
		this.loginName = loginName;
		this.name      = name;
		this.queryLevel = queryLevel;
		this.tOrganizatioinId = null;
		this.tOrganizationCode = "";
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getQueryLevel() {
		return queryLevel;
	}


	public void setQueryLevel(String queryLevel) {
		this.queryLevel = queryLevel;
	}

	
	public Long gettOrganizatioinId() {
		return tOrganizatioinId;
	}
	public void settOrganizatioinId(Long tOrganizatioinId) {
		this.tOrganizatioinId = tOrganizatioinId;
	}
	public String gettOrganizationCode() {
		return tOrganizationCode;
	}
	public void settOrganizationCode(String tOrganizationCode) {
		this.tOrganizationCode = tOrganizationCode;
	}
	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	public String toString() {
		return name;
	}


}
