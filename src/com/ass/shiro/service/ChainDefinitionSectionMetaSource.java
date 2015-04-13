package com.ass.shiro.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 借助spring {@link FactoryBean} 对apache shiro的premission进行动态创建 动态的从数据库中读取权限信息
 * 
 * @author gym
 * @since 2014-02-23
 * 
 */
public class ChainDefinitionSectionMetaSource implements
		FactoryBean<Ini.Section> {

	public static int i;
	

	// shiro默认的链接定义 写在xml上的。
	private String filterChainDefinitions;

	/**
	 * 通过filterChainDefinitions对默认的链接过滤定义
	 * 
	 * @param filterChainDefinitions
	 *            默认的接过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	@Override
	public Section getObject() throws BeansException {
		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		/*1加载类似以下的信息
		  	/login.do = authc 
	        /favicon.ico = anon
	        /logout.do = logout
	          
	          
	         2
	         	循环数据库资源的url
	        for (Resource resource : resourceDao.getAll()) {
	if(StringUtils.isNotEmpty(resource.getValue()) && StringUtils.isNotEmpty(resource.getPermission())) {
	        		section.put(resource.getValue(), resource.getPermission());
	        	}
	        }
			加载数据库t_permission 的 value 和 permission组成类似1的格式 ， 暂时value 未用到  added by wangt20141118
			若要这样使用， permission 需要--->  perms[permission]

		 */
		Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
		if (CollectionUtils.isEmpty(section)) {
			section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		}
		
		return section;
	}

	@Override
	public Class<?> getObjectType() {
		return Section.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
