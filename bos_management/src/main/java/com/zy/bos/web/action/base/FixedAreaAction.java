package com.zy.bos.web.action.base;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.zy.bos.domain.base.FixedArea;
import com.zy.bos.service.base.FixedAreaService;
import com.zy.bos.utils.BaseAction;

import cn.itcast.crm.domain.Customer;

/** 
* @author  philchang 
* @date 2017年12月5日 下午2:58:34 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {
	
	// 注入service
	@Autowired
	private FixedAreaService fixedAreaService;
	
	// 保存定区的action
	@Action(value = "fixedArea_save", results = {@Result(type = "redirect", 
			location = "/pages/base/fixed_area.html")})
	public String save() {
		fixedAreaService.save(model);
		
		return SUCCESS;
	}
	
	// 分页查询定区的action
	@Action(value = "fixedArea_page",results = {@Result(type = "json")})
	public String page() {
		Page<FixedArea> pageData = fixedAreaService.page(model, page-1, rows);
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}
	
	@Action(value = "fixedArea_findCusromerNull", results = {@Result(type = "json")})
	public String findCusromerNull() {
		Collection<? extends Customer> collection = WebClient
		.create("http://localhost:9002/crm_management/services/customerService/findCustomerNull")
		.accept(MediaType.APPLICATION_JSON)
		.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}
	
	
	@Action(value = "fixedArea_findCusromerByFid", results = {@Result(type = "json")})
	public String findCusromerByFid() {
		Collection<? extends Customer> collection = WebClient
		.create("http://localhost:9002/crm_management/services/customerService/findCustomerByFid/" + model.getId())
		.accept(MediaType.APPLICATION_JSON)
		.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}
	
	// 属性驱动接收传入的客户id
	private String[] customerIds;
	
	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	@Action(value = "fixedArea_customerToFixedArea", 
			results = {@Result(type = "redirect", 
			location = "/pages/base/fixed_area.html")})
	public String customerToFixedArea() {
		String join = StringUtils.join(customerIds, ",");
		// System.out.println(join);
		if(join == null){
			join = "null";
		}
		System.out.println(model.getId());
		WebClient.create("http://localhost:9002/crm_management/services/customerService/customerToFixedArea")
				.query("cidStr", join)
				.query("fid", model.getId())
				.put(null);
		return SUCCESS;
	}
	
	// 属性驱动，用于接收提交来的数据
	private Integer courierId;
	private Integer takeTimeId;
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}
	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}
	
	@Action(value = "fixedArea_courierToFixedArea", 
			results = {@Result(type = "redirect", 
			location = "/pages/base/fixed_area.html")})
	public String courierToFixedArea() {
		fixedAreaService.courierToFixedArea(model, courierId, takeTimeId);
		return SUCCESS;
	}

}
