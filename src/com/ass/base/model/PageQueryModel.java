package com.ass.base.model;

import java.util.HashMap;
import java.util.Map;

public class PageQueryModel {

	public static final String COUNT_ALL_NAME = "count";

	// 是否查询所有
	private boolean queryAll = false;

	// 是否 计算数量
	private boolean doCount = true;

	// 当前页 第一条记录的索引 第1页的第1条记录索引为0
	private int startPosition = 1;

	// 一页的记录数
	private int pageSize = 50;

	// 要查询的页码
	private int currentPage = 1;

	// 按照 某列排序
	private String sortCol = "";

	// 排序方式 asc desc
	private String sortOrder = "";

	// 其他查询参数列表
	private Map queryParam = new HashMap();

	public PageQueryModel() {
	}

	public PageQueryModel(int currentPage, int pageSize, String sortCol,
			String sortOrder) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.sortCol = sortCol;
		this.sortOrder = sortOrder;
		if (currentPage < 1) {// 初始化的时候设置初始记录的 索引
			currentPage = 1;
			startPosition = 0;
		} else {
			startPosition = pageSize * (currentPage - 1);
		}
	}

	/**
	 * 方法功能描述
	 */
	public boolean isDoCount() {
		return doCount;
	}

	/**
	 * 方法功能描述
	 */
	public boolean isQueryAll() {
		return queryAll;
	}

	/**
	 * 方法功能描述
	 */
	public int getFirstCursorPosition() {
		return startPosition;
	}

	/**
	 * 方法功能描述
	 * 
	 * @return
	 * @return int
	 */
	public int getLastCursorPosition() {
		return (startPosition + pageSize) - 1;
	}

	/**
	 * 
	 * 方法功能描述
	 * 
	 * @param page
	 *            当前页
	 */
	public void setCurrentPage(int page) {
		if (page < 1) {
			currentPage = 1;
			startPosition = 0;
		} else {
			currentPage = page;
			startPosition = pageSize * (page - 1);
		}
	}

	/**
	 * 
	 * 方法功能描述
	 * 
	 * @return
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 
	 * 方法功能描述
	 * 
	 * @param pageSize
	 *            分页数量
	 */
	public void setPageSize(int pageSize) {
		if (0 >= pageSize) {
			pageSize = 1;
		}
		this.pageSize = pageSize;
		startPosition = pageSize * (currentPage - 1);
	}

	/**
	 * 方法功能描述
	 * 
	 * @return int
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * 方法功能描述
	 * 
	 * @param startPosition
	 *            参数
	 */
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * 方法功能描述
	 * 
	 * @param doCount
	 *            參數
	 */
	public void setDoCount(boolean doCount) {
		this.doCount = doCount;
	}

	/**
	 * 方法功能描述
	 * 
	 * @param queryAll
	 *            參數
	 */
	public void setQueryAll(boolean queryAll) {
		this.queryAll = queryAll;
	}

	/**
	 * 方法功能描述
	 * 
	 * @return
	 * @return String
	 */
	public String getQueryAll() {
		return String.valueOf(queryAll);
	}

	/**
	 * 方法功能描述
	 * 
	 * @return
	 * @return String
	 */
	public String getDoCount() {
		return String.valueOf(doCount);
	}

	/**
	 * 方法功能描述
	 */
	public void nextPage() {
		setCurrentPage(getCurrentPage() + 1);
	}

	/**
	 * 方法功能描述
	 */
	public void prePage() {
		setCurrentPage(getCurrentPage() - 1);
	}

	/**
	 * 方法功能描述
	 * 
	 * @return
	 * @return int
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 方法功能描述
	 * 
	 * @return
	 * @return Map
	 */
	public Map getQueryParam() {
		return queryParam;
	}

	/**
	 * 方法功能描述
	 * 
	 * @return
	 * @return String
	 */
	public String getSortCol() {
		return sortCol;
	}

	/**
	 * 方法功能描述
	 * 
	 * @param sortCol
	 */
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}

	/**
	 * 方法功能描述
	 * 
	 * @return
	 * @return String
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * 方法功能描述
	 * 
	 * @param sortOrder
	 *            设置排序
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * 方法功能描述
	 * 
	 * @param queryParam
	 *            查询条件
	 */
	public void setQueryParam(Map queryParam) {
		this.queryParam = queryParam;
	}

	/**
	 * 方法功能描述
	 * 
	 * @param key
	 *            key值
	 * @param value
	 *            value值
	 */
	public void addParameter(Object key, Object value) {
		this.queryParam.put(key, value);
	}

}
