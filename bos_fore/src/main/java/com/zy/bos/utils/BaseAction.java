package com.zy.bos.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** 
* @author  philchang 
* @date 2017年12月5日 上午8:52:24 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	// 模型驱动
	protected T model;
	
	@Override
	public T getModel() {

		return model;
	}
	
	public BaseAction(){
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
		Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
		try {
			model = modelClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			System.out.println("模型实例化失败!");
		}
	}
	
	// 接收分页查询参数
	protected int page;
	protected int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

		// 将分页查询结果数据，压入值栈的方法
	protected void pushPageDataToValueStack(Page<T> pageData) {
		PageBean<T> pageBean= new PageBean<>();
		pageBean.setTotal(pageData.getTotalElements());
		pageBean.setRows(pageData.getContent());

		ActionContext.getContext().getValueStack().push(pageBean);
	}

}
