package com.zy.bos.web.action.base;

import java.util.List;

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
import com.zy.bos.domain.base.Courier;
import com.zy.bos.domain.base.Standard;
import com.zy.bos.service.base.CourierService;
import com.zy.bos.utils.PageBean;

/** 
* @author  philchang 
* @date 2017年12月1日 下午8:25:10 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{

	
	private int page;
	private int rows;
	private String ids;
	
	
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	private Courier courier = new Courier();
	
	@Autowired
	private CourierService courierService;

	@Override
	public Courier getModel() {
		
		return courier;
	}
	
	@Action(value = "courier_save", results = {@Result(type = "redirect", location = "/pages/base/courier.html")})
	public String save() {
		// System.out.println("kjhfkjhfkj");
		courierService.save(courier);
		return SUCCESS;
	}
	
	@Action(value = "courier_page", results = {@Result(type = "json")})
	public String page() {
		Page<Courier> pageData =  courierService.findPage(courier, page-1, rows);
		PageBean<Courier> pageBean = new PageBean<>();
		pageBean.setTotal(pageData.getTotalElements());
		pageBean.setRows(pageData.getContent());
		System.out.println(pageBean);
		ActionContext.getContext().getValueStack().push(pageBean);
		return SUCCESS;
	}
	
	@Action(value = "courier_delete", results = {@Result(type = "redirect", location = "/pages/base/courier.html")})
	public String delete() {
		// System.out.println(ids);
		String[] idArray = ids.split(",");
		courierService.delete(idArray);
		return SUCCESS;
	}
	
	
	@Action(value = "courier_findNoAs", results= {@Result(type = "json")})
	public String findNoAs() {
		List<Courier> list = courierService.findNoAs();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
	
	
	
}
