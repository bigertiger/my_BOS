package com.zy.bos.utils;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author philchang
 * @date 2017年12月1日 下午4:00:07
 * @version 1.0.0
 * 
 */
@SuppressWarnings("hiding")
public class PageBean<T> {

	private Long total;
	private List<T> rows;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "PageBean [total=" + total + ", rows=" + rows + "]";
	}
	
	

}
