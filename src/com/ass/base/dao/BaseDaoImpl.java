package com.ass.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.ass.base.model.BaseClass;
import com.ass.base.model.PageGrid;
import com.ass.base.model.PageQueryModel;
import com.ass.common.utils.StringUtil;

/**
 * 封装sqlSessionTemplate的代码,Dao继承BaseDaoImpl, 少写代码
 * 
 * @author Administrator
 * 
 */
@Repository("BaseDaoImpl")
public class BaseDaoImpl extends BaseClass{

	@Resource(name = "SqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 分页查询方法
	 * 
	 * @param statementName
	 *            SQL名称
	 * @param parameterObject
	 *            参数
	 * @return Object
	 */
	public PageGrid pageForObject(String statementName,
			PageQueryModel parameterObject) {
		int records = 0;
		PageGrid pageGrid = new PageGrid();
		PageQueryModel queryModel = parameterObject;
		
		//每次查询都带上当前登录人的信息
		queryModel.addParameter("cur_userid", this.getUser().getId());
		queryModel.addParameter("cur_code", this.getUser().gettOrganizationCode());
		queryModel.addParameter("queryLevel", this.getUser().getQueryLevel());
		
		if (queryModel == null) {
			// 不传 PageQueryModel参数，默认查询全部，不记数
			queryModel = new PageQueryModel();
			// 设置查询全部
			queryModel.setQueryAll(true);
			// 不进行数量查询
			queryModel.setDoCount(false);
		}
		Map<String, String> queryParam = new HashMap<String, String>();
		if (queryModel.getQueryParam() != null) {
			queryParam = queryModel.getQueryParam();
		}
		queryParam.put("RESULT_COUNT", PageQueryModel.COUNT_ALL_NAME);
		queryParam.put("doCount", queryModel.getDoCount());

		if (queryModel.isDoCount()) {
			Map result = (Map) this.selectOne(statementName, queryParam);
			records = Integer.parseInt(result.get(PageQueryModel.COUNT_ALL_NAME).toString());
		}
		// 设置总条数
		pageGrid.setRecords(records);
		// 查询数据
		queryModel.setDoCount(false);

		queryParam.put("doCount", queryModel.getDoCount());
		queryParam.put("startPosition",
				StringUtil.getString(queryModel.getStartPosition()));
		queryParam.put("pageSize",
				StringUtil.getString(queryModel.getPageSize()));
		queryParam.put("sortCol", queryModel.getSortCol());
		queryParam.put("sortOrder", queryModel.getSortOrder());
		queryParam.put("queryAll", queryModel.getQueryAll());

		// jqGrid查询参数 searchField searchString searchOper
		List data = this.selectList(statementName, queryParam);
		pageGrid.setRows(data);
		//
		if (!queryModel.isQueryAll()) {// 不是查询全部 需要总页total数来显示出来
			// 设置总页数
			int pageSize = queryModel.getPageSize();
			int total = 0;
			if (records != 0) {
				total = (records % pageSize) == 0 ? (records / pageSize)
						: (records / pageSize + 1);
			}
			pageGrid.setTotal(total);
			// 设置当前页
			int currentPage = queryModel.getCurrentPage();
			pageGrid.setPage(currentPage);
		}
		return pageGrid;
	}

	public <T> T selectOne(String statement) {
		return this.sqlSessionTemplate.selectOne(statement);
	}

	public <T> T selectOne(String statement, Object parameter) {
		return this.sqlSessionTemplate.selectOne(statement, parameter);
	}

	// offen
	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return this.sqlSessionTemplate.selectMap(statement, mapKey);
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter,
			String mapKey) {
		return this.sqlSessionTemplate.selectMap(statement, parameter, mapKey);
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter,
			String mapKey, RowBounds rowBounds) {
		return this.sqlSessionTemplate.selectMap(statement, parameter, mapKey,
				rowBounds);
	}

	public <E> List<E> selectList(String statement) {
		return this.sqlSessionTemplate.selectList(statement);
	}

	public <E> List<E> selectList(String statement, Object parameter) {
		return this.sqlSessionTemplate.selectList(statement, parameter);
	}

	public <E> List<E> selectList(String statement, Object parameter,
			RowBounds rowBounds) {
		return this.sqlSessionTemplate.selectList(statement, parameter,
				rowBounds);
	}

	public void select(String statement, ResultHandler handler) {
		this.sqlSessionTemplate.select(statement, handler);
	}

	public void select(String statement, Object parameter, ResultHandler handler) {
		this.sqlSessionTemplate.select(statement, parameter, handler);
	}

	public void select(String statement, Object parameter, RowBounds rowBounds,
			ResultHandler handler) {
		this.sqlSessionTemplate
				.select(statement, parameter, rowBounds, handler);
	}

	public int insert(String statement) {
		return this.sqlSessionTemplate.insert(statement);
	}

	public int insert(String statement, Object parameter) {
		return this.sqlSessionTemplate.insert(statement, parameter);
	}

	public int update(String statement) {
		return this.sqlSessionTemplate.update(statement);
	}

	public int update(String statement, Object parameter) {
		return this.sqlSessionTemplate.update(statement, parameter);
	}

	public int delete(String statement) {
		return this.sqlSessionTemplate.delete(statement);
	}

	public int delete(String statement, Object parameter) {
		//this.sqlSessionTemplate.
		return this.sqlSessionTemplate.delete(statement, parameter);
	}
	
	
}
