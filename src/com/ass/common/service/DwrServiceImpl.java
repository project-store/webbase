package com.ass.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ass.common.generated.model.TUser;
import com.ass.shiro.dto.CurUser;

/**
 * 
 * @author wangt 2014年11月25日 下午7:45:46 
 */
@Service
public class DwrServiceImpl implements DwrService{
	
	@Resource
	private CommonService commonService;
	
	
	/*
	 * 
	 * @see com.ass.common.service.DwrService#getMap()
	 * @author wangt 2014年12月1日 上午10:51:08 
	 */
	@Override
	public Map<String, String> getMap(){
		Map<String, String> m = new HashMap<String, String>();
		m.put("success", "true");
		return m;
	}

	/*
	 * 
	 * @see com.ass.common.service.DwrService#getList()
	 * @author wangt 2014年12月1日 上午10:51:08 
	 */
	@Override
	public List<Map<String, String>> getList(){
		return new ArrayList<Map<String, String>>();
	}
	
	
	/*
	 * 
	 * @see com.ass.common.service.DwrService#deleteLogs(java.lang.String)
	 * @author wangt 2014年12月1日 上午10:51:08 
	 */
	@Override
	public String deleteLogs(String logIds){
		String sql = "UPDATE t_log SET isdelete=1 WHERE id IN("+logIds+")";
		commonService.updateBySql(sql);
		return "success";
	}
	
	
	
	
	//************一下为测试方法。**********************************
	/*
	 * 
	 * @see com.ass.common.service.DwrService#testStr(java.lang.String)
	 * @author wangt 2014年12月1日 上午10:51:08 
	 */
	@Override
	public String testStr(String str) {
		System.out.println(str);
		return "hello"+str;
	}
	
	/*
	 * 
	 * @see com.ass.common.service.DwrService#testMap(java.util.Map)
	 * @author wangt 2014年12月1日 上午10:51:08 
	 */
	@Override
	public Map<String, String> testMap(Map<String, String> m){
		System.out.println(m);
		System.out.println(m.get("u"));
		System.out.println(m.get("p"));
		
		Map<String, String> m1 = new HashMap<String, String>(); 
		m1.put("ok", "is ok");
		return m1;
	}
	
	/*
	 * 
	 * @see com.ass.common.service.DwrService#testShuzu1(java.lang.String[])
	 * @author wangt 2014年12月1日 上午10:51:08 
	 */
	@Override
	public String[] testShuzu1(String[] s){
		System.out.println(s);
		System.out.println(s[1]);
		String[] c = {"aa","vv","cc"};
		return c;
	}
	
	/*
	 * 
	 * @see com.ass.common.service.DwrService#testShuzu2(java.util.List)
	 * @author wangt 2014年12月1日 上午10:51:08 
	 */
	@Override
	public List<String> testShuzu2(List<String> l){
		System.out.println(l);
		System.out.println(l.get(0));
		List<String> ll = new ArrayList<String>();
		ll.add("aa");
		ll.add("bb");
		return ll;
	}
	
	
	/*
	 * 
	 * @see com.ass.common.service.DwrService#testList(java.util.List)
	 * @author wangt 2014年12月1日 上午10:51:08 
	 */
	@Override
	public List<Map<String,String>> testList(List<Map<String, String>> l){
		System.out.println(l.get(0));
		Map<String, String> m1 = new HashMap<String, String>(); 
		Map<String, String> m2 = new HashMap<String, String>(); 
		m1.put("ok", "okoko");
		m1.put("oooo", "oooo");
		m2.put("ok", "okoko222");
		m2.put("oooo", "oooo222");
		List<Map<String, String>> ll = new ArrayList<Map<String, String>>();
		ll.add(m1);
		ll.add(m2);
		return ll;
		
	}
	
	
	/*
	 * 
	 * @see com.ass.common.service.DwrService#testUser(com.ass.common.generated.model.TUser)
	 * @author wangt 2014年12月1日 上午10:51:08 
	 */
	@Override
	public TUser testUser(TUser m){
		System.out.println(m.getName());
		TUser c = new TUser();
		c.setName("aaa");
		return c;
	}

	
	
	
}
