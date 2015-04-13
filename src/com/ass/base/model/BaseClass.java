/**
 * FileName: BaseServiceImpl.java Description: service基类
 */
package com.ass.base.model;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.ass.base.prop.MyPropertyConfigurer;
import com.ass.shiro.dto.CurUser;


/**
 * service基类
 * 
 * @author wangt
 * @date 20140307
 */
public class BaseClass {
	protected final Log logger = LogFactory.getLog(getClass());
	
	/**获得当前登陆人
	 * @return
	 */
	public CurUser getUser(){
		//正式使用，前两行 去掉后一行 //TODO
		Subject subject = SecurityUtils.getSubject();		
		return (CurUser)subject.getPrincipal();
		//return new CurUser("2","adminLogin","admin","all");
		
	}
	
	
	
	@Resource
	private MyPropertyConfigurer myPropertyConfigurer;
	
	/**
	 * 获取properties文件值的方法，通过key获取value
	 * @param key
	 * @return
	 * @author wangt 2014年12月5日 上午9:11:39 
	 */
	public String getProp(String key) {
		return myPropertyConfigurer.getVal(key);
	}
	
	/** 构造ajax返回打印对象
	 * @return
	 */
	public Map<String,Object> getMap(){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("msg", "操作成功!");
		m.put("success", true);
		m.put("ok", "ok");
		return m;
	}
	
	
}
