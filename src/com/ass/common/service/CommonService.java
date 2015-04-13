package com.ass.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;





import com.ass.base.model.PageGrid;
import com.ass.base.model.PageQueryModel;
public interface CommonService {

	// 分页查询方法
	public PageGrid pageForObject(String statementName,
			PageQueryModel parameterObject);

	public <E> List<E> selectList(String statement,
			Map<String, String> parameter);

	public Map<String, String> selectMap(String statement,
			Map<String, String> parameter, String mapKey);

	public <T> T selectOne(String statement, Map<String, String> parameter);


	public int delete(String statement);

	public int delete(String statement, List lst);
	
	
	public int update(String statement);

	public int update(String statement, Map<String, String> parameter);
	
	
	//传字符串的查询,插入,修改,删除方法
	/**
	 * 通过sql语句查找
	 * @param sql
	 * @return
	 * @author wangt
	 * @time 2014年7月10日 下午2:46:49 
	 */
	public <E> List<E> selectBySql(String sql);
	
	/**
	 * 通过sql语句查找
	 * @param sql
	 * @return
	 * @author wangt
	 * @return 
	 * @time 2014年7月10日 下午2:46:49 
	 */
	public <E> E selectOneBySql(String sql);
	
	/**
	 * 通过sql语句进行更新，返回更新的行数
	 * @param sql
	 * @return
	 * @author wangt
	 * @time 2014年7月10日 下午2:47:21 
	 */
	public int updateBySql(String sql);
	
	/**
	 * 通过sql语句删除，返回删除的行数
	 * @param sql
	 * @return
	 * @author wangt
	 * @time 2014年7月10日 下午2:47:49 
	 */
	public int deleteBySql(String sql);
	
	/**
	 * 通过sql语句增加，返回新增的行数
	 * @param sql
	 * @return
	 * @author wangt
	 * @time 2014年7月10日 下午2:48:04 
	 */
	public int insertBySql(String sql);
	
	/**
	 * 查找int类型，比如搜索COUNT(*)或者是int类型的数据。
	 * @param sql
	 * @return int
	 * @author wangt
	 * @time 2014年7月10日 下午2:48:21 
	 */
	public int selectInt(String sql);
	
	/**
	 * 查找String类型
	 * @param sql
	 * @return String
	 * @author wangt
	 * @time 2014年7月10日 下午2:48:27 
	 */
	public String selectText(String sql);
	
	
	/**
	 * 插入数据量过多的时候分步插入
	 * @param head 例如：insert into ht_bill_customer(ht_station_id,bill_code ) values 
	 * @param valueLst 例如： (xxx,xxx) (xxx,xxx) (xxx,xxx) (xxx,xxx)  list集合
	 * @author wangt 2014年7月11日 下午5:17:36 
	 */
	public void insertByThread(String head, List<String> valueLst);
	
	
	
}
