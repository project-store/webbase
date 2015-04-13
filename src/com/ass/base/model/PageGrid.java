package com.ass.base.model;

import java.util.List;
import java.util.Map;

public class PageGrid {
	/**
	 * 当前页
	 */
	private int page;

	/**
	 * 总页数
	 */
	private int total;

	/**
	 * 总条数
	 */
	private int records;

	/**
	 * 查询数据
	 */
	private List<Map<String, Object>> rows;
	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}


	
}
