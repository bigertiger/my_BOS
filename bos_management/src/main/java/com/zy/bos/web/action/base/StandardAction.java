package com.zy.bos.web.action.base;

import java.util.List;

import javax.persistence.Entity;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zy.bos.domain.base.Standard;
import com.zy.bos.service.base.StandardService;
import com.zy.bos.utils.PageBean;

/** 
* @author  philchang 
* @date 2017年11月29日 下午6:09:08 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

	
	private Standard standard = new Standard();
	
	private int page;
	private int rows;
	
	public void setPage(int page) {
		this.page = page;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	@Override
	public Standard getModel() {
		return standard;
	}
	
	@Autowired
	private StandardService standardService;
	
	@Action(value = "standard_save", results = {@Result(type = "redirect", location = "/pages/base/standard.html")})
	public String save() {
		System.out.println("save执行了");
		standardService.save(standard);
		return SUCCESS;
	}
	
	@Action(value = "standard_page", results = {@Result(type = "json")})
	public String page() {
		Page<Standard> pageData =  standardService.findPage(page-1, rows);
		PageBean<Standard> pageBean = new PageBean<>();
		pageBean.setTotal(pageData.getTotalElements());
		pageBean.setRows(pageData.getContent());
		System.out.println(pageBean);
		ActionContext.getContext().getValueStack().push(pageBean);
		return SUCCESS;
	}
	
	@Action(value = "standard_findAll", results = {@Result(type = "json")})
	public String findAll() {
		List<Standard> list =  standardService.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}

}
