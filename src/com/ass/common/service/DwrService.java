package com.ass.common.service;

import java.util.List;
import java.util.Map;

import com.ass.common.generated.model.TUser;

public interface DwrService {

	public  Map<String, String> getMap();

	public  List<Map<String, String>> getList();

	/**
	 * 删除日志
	 * @param logIds
	 * @return
	 * @author wangt 2014年11月26日 上午10:31:20 
	 */
	public  String deleteLogs(String logIds);

	//************一下为测试方法。**********************************
	/**
	 * 测试传递字符串，返回字符串方法
	 * @param str
	 * @return
	 * @author wangt 2014年11月25日 下午10:06:41 
	 */
	public  String testStr(String str);

	/**
	 * 测试传递js的 key--value 对象，返回Map。
	 * @param m
	 * @return
	 * @author wangt 2014年11月25日 下午10:06:53 
	 */
	public  Map<String, String> testMap(Map<String, String> m);

	/**
	 * 前台传数组，后天反数组
	 * @param s
	 * @return
	 * @author wangt 2014年11月25日 下午10:19:27 
	 */
	public  String[] testShuzu1(String[] s);

	/**
	 * 前台传数组，后台反list<String>
	 * @param l
	 * @return
	 * @author wangt 2014年11月25日 下午10:19:45 
	 */
	public  List<String> testShuzu2(List<String> l);

	/**
	 * 测试传递js 数组， 返回List<Map<String,String>>()
	 * @param l
	 * @return
	 * @author wangt 2014年11月25日 下午10:07:17 
	 */
	public  List<Map<String, String>> testList(
			List<Map<String, String>> l);

	/**
	 * 测试 自定义对象，得在dwr.xml添加<convert match="com.ass.common.generated.model.TUser" converter="bean" />  
	 * @param m
	 * @return
	 * @author wangt 2014年11月25日 下午10:08:11 
	 */
	public  TUser testUser(TUser m);

}