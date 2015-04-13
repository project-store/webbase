package com.ass.common.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ass.base.dao.BaseDaoImpl;
import com.ass.base.model.PageGrid;
import com.ass.base.model.PageQueryModel;
import com.ass.base.prop.MyPropertyConfigurer;
import com.ass.base.service.BaseServiceImpl;
import com.ass.common.utils.StringUtil;

@Service
//@Scope("prototype")
//@Transactional
public class CommonServiceImpl extends BaseServiceImpl implements CommonService {

	@Resource
	private BaseDaoImpl baseDaoImpl;
	
	@Resource
	private MyPropertyConfigurer myPropertyConfigurer;
	
	protected final Logger logger = Logger.getLogger(getClass());
	

	@Override
	public <E> List<E> selectList(String statement,
			Map<String, String> parameter) {
		return baseDaoImpl.selectList(statement, parameter);
	}

	@Override
	public Map<String, String> selectMap(String statement,
			Map<String, String> parameter, String mapKey) {
		return baseDaoImpl.selectMap(statement, parameter, mapKey);
	}

	@Override
	public <T> T selectOne(String statement, Map<String, String> parameter) {
		return baseDaoImpl.selectOne(statement, parameter);
	}

	@Override
	public PageGrid pageForObject(String statementName,
			PageQueryModel parameterObject) {
		
		return baseDaoImpl.pageForObject(statementName, parameterObject);
	}

	@Override
	public int delete(String statement) {
		return baseDaoImpl.delete(statement);
	}

	@Override
	public int delete(String statement, List lst) {
		return baseDaoImpl.delete(statement, lst);
	}
	

	@Override
	public int update(String statement) {
		return baseDaoImpl.update(statement);
	}

	@Override
	public int update(String statement, Map<String, String> parameter) {
		return baseDaoImpl.update(statement, parameter);
	}
	
	
	
	//****begin  通用的增删改查传sql方法 
	public Map<String, String> param = new HashMap<String, String>();
	/*
	 * 返回List<E>
	 * @see com.ass.common.service.CommonService#selectBySql(java.lang.String)
	 * @author wangt
	 * @time 2014年6月12日 上午11:33:03 
	 */
	@Override
	public <E> List<E> selectBySql(String sql) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("sql", sql);
		return baseDaoImpl.selectList("BaseDAOImpl.select", param);
	}

	@Override
	public <E> E selectOneBySql(String sql) {
		
		return (E) this.selectBySql(sql).get(0);
	}
	
	@Override
	public int updateBySql(String sql) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("sql", sql);
		return baseDaoImpl.update("BaseDAOImpl.update", param);
	}

	@Override
	public int deleteBySql(String sql) {
		param.put("sql", sql);
		return baseDaoImpl.delete("BaseDAOImpl.delete", param);
	}

	@Override
	public int insertBySql(String sql) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("sql", sql);
		return baseDaoImpl.insert("BaseDAOImpl.insert", param);
	}
	//****end  通用的增删改查传sql方法 

	@Override
	public int selectInt(String sql) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("sql", sql);
		int a = baseDaoImpl.selectOne("BaseDAOImpl.selectInt", param);
		return a;
	}

	@Override
	public String selectText(String sql) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("sql", sql);
		String a = baseDaoImpl.selectOne("BaseDAOImpl.selectText", param);
		return a;
	}

	
	/**
	 * 插入数据量过多的时候分步插入
	 * @param head 例如：insert into ht_bill_customer(ht_station_id,bill_code ) values 
	 * @param valueLst 例如： (xxx,xxx) (xxx,xxx)  list集合
	 * @author wangt 2014年7月11日 下午5:17:36 
	 */
	@Override
	public void insertByThread(String head, List<String> valueLst) {
		//每次插入的条数
		int step = 2000;
		//head insert into xxx(xxx,xxx,xxx) values 
		//values: ('xxx','xxx','xxx') 
		int num = valueLst.size()/step;
		int remainder = valueLst.size()%step;//余数
		if(remainder != 0){
			num++;
	
			List<Thread> list = new ArrayList<Thread>();
			final CommonServiceImpl cs = this;
			for(int i=0; i<num; i++){
				final StringBuilder sb = new StringBuilder();
				if(remainder != 0 && i == (num-1)){
					
					//例如： valueLst.size() 为 45条， step  为10 ， 则num = 5， i为 0~4
					//when i = 0 , 1-10条   (在下面的else执行**)
					//when i = 1 , 11-20条 (在下面的else执行**)
					//when i = 2 , 21-30条 (在下面的else执行**)
					//when i = 3 , 31-40条 (在下面的else执行**)
					//when i = 4 , 41-45条      （remainder != 0 && i == (num-1)）******* (在这个if执行**)
					
					sb.append(head);
					for(int j=i*step; j<valueLst.size(); j++){
						sb.append(valueLst.get(j));
						if(j != (valueLst.size()-1)){
							sb.append(",");
						}
					}
					//this.insertBySql(sb.toString());
				}else{
					sb.append(head+ " ");
					for(int j=i*step; j<((i+1)*step); j++){
						sb.append(valueLst.get(j));
						if(j != ((i+1)*step-1)){
							sb.append(",");
						}
					}
					//this.insertBySql(sb.toString());
				}
				Thread t = new Thread(new Runnable(){
					public void run() {
						cs.insertBySql(sb.toString());
					}
				});
				list.add(t);
				t.start();
			}
			try{ 
				//此处问题很严重，子线程join阻塞主线程会报错！
	            for(Thread t : list){ 
	        		//t.join();
	            } 
		    }catch (Exception e){ 
	            e.printStackTrace(); 
		    } 
		}
	}	
	
	
	public static void main(String[] args) {
		System.out.println(46/10);
		int step = 10;
		int i = 0;
		for(int j = i*step; j<(i+1) * step; j++){
			System.out.println(j);
		}
	}




	
}







