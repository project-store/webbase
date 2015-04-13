package com.ass.test.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ass.base.model.PageGrid;
import com.ass.base.model.PageQueryModel;
import com.ass.base.web.BaseController;
import com.ass.common.generated.dao.TTestMapper;
import com.ass.common.generated.model.TTest;
import com.ass.common.generated.model.TTestExample;
import com.ass.common.service.CommonService;
import com.ass.common.utils.JsonUtil;
import com.ass.common.utils.StringUtil;
@Controller
@RequestMapping("/test.do")
public class TestController extends BaseController {

	@Resource
	private CommonService commonService;
	
	@Resource
	private TTestMapper tTestMapper;//  mapper  就是 Dao！单表增删改查使用。

	//这里是对数据库 t_test 表的增删改查， 示例。
	//注意：除了查询。          增加，修改，删除，的方法都应该写在service 层。
	
	
	//一、 用com.ass.common.generated 下面自动生成的类对单表增删改查
    	
	@RequestMapping(params = "action=1")
	public void test1(HttpServletRequest r){
		//1.增加
		TTest t = new TTest();
		t.setName1("name1");
		tTestMapper.insertSelective(t);//值插入类中不为空的属性。不会去理会name2，因为没有设置它的值
		tTestMapper.insert(t);         //全部插入。 一般都用 insertSerlective 方法。
		
	}

	@RequestMapping(params = "action=2")
	public void test2(HttpServletRequest r){
		//2.删除 
		//a 通过主键删除
		tTestMapper.deleteByPrimaryKey(1l);
		
		//b 通过example 删除， example可以理解为 sql语句 where 后面的条件。
		TTestExample e = new TTestExample();
		//e.createCriteria().注意这个  . 后面能  点出来好多方法
		e.createCriteria().andName1EqualTo("aaa");
		tTestMapper.deleteByExample(e);//通过example条件删除 name1 等于aaa的
		
	}
	
	
	@RequestMapping(params = "action=3")
	public void test3(HttpServletRequest r){
		//3.修改 
		TTest t = new TTest();
		t.setId(1l);
		t.setName1("name1");
		t.setName2(null);
		tTestMapper.updateByPrimaryKey(t);//不建议使用
		tTestMapper.updateByPrimaryKeySelective(t);//通过主键修改， 
		
	}
	
	@RequestMapping(params = "action=4")
	public void test4(HttpServletRequest r){
		//2.查询
		//a 通过主键查询
		tTestMapper.selectByPrimaryKey(1l);
		
		//b 通过example 查询， example可以理解为 sql语句 where 后面的条件。
		TTestExample e = new TTestExample();
		//e.createCriteria().注意这个  . 后面能  点出来好多方法
		e.createCriteria().andName1EqualTo("aaa");
		tTestMapper.selectByExample(e);//通过example条件查询 name1等于 aaa的
		
	}
	
	
	
	@RequestMapping(params = "action=5")
	public void test5(HttpServletRequest r){
		//mybatis 动态sql的查询方法。
		//1. 查询结果为多的。
		Map<String, String> m1 = new HashMap<String, String>();
		m1.put("name1", "wangt");
		//第一个参数statement为 xml文件的namespace点里面节点的id
		List<Map<String, Object>> lst1 = commonService.selectList("Test_SpecSql.testSelect1", m1);
		//sql见com.ass.test.xml.Test_SpecSql.xml
		
		
		//2.查询结果为1个
		Map<String, String> m2 = new HashMap<String, String>();
		m1.put("id", "2");
		Map<String, Object> lst2 = commonService.selectOne("Test_SpecSql.testSelect1", m1);
		
		//3. 通过传sql字符串查询
		String sql = "select * from t_test";
		List<Map<String, Object>> lst3 = commonService.selectBySql(sql);
		
		//4.查询int
		String sql1 = "select id from t_test where id = 1";
		int a = commonService.selectInt(sql);//参数为自己写的sql，注意，sql的返回值应该为一个数字。
		//5. 查询字符串
		String sql2 = "select name1 from t_test where id = 1";
		String b = commonService.selectText(sql);//参数为自己写的sql，注意，sql的返回值应该为一个数字。

		//下行3个方法，参数为sql字符串
		commonService.updateBySql("  ");
		commonService.deleteBySql("  ");
		commonService.insertBySql("  ");
		
		//下行为删除，更新  
		Map<String, String> mm = new HashMap<String, String>();
		commonService.delete("Test_SpecSql.testSelect1");
		commonService.update("Test_SpecSql.testSelect1");
		commonService.update("Test_SpecSql.testSelect1",mm);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
