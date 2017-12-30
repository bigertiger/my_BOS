package com.zy.bos.web.action.take_delivery;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.zy.bos.domain.take_delivery.WayBill;
import com.zy.bos.service.take_delivery.WayBillService;
import com.zy.bos.utils.BaseAction;

/** 
* @author  philchang 
* @date 2017年12月20日 上午10:50:24 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill> {
	
	@Autowired
	private WayBillService wayBillService;
	
	private static Logger LOG = Logger.getLogger(WayBill.class);
	
	@Action(value = "waybill_save", results = {@Result(type = "json")})
	public String save() {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			wayBillService.save(model);
			result.put("success", true);
			result.put("msg", "运单保存成功!");
			LOG.info("保存成功,运单号:" + model.getWayBillNum());
		} catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "运单保存失败!");
			LOG.error("保存失败,运单号:" + model.getWayBillNum(), e);
		}
		ActionContext.getContext().getValueStack().push(result);
		
		return SUCCESS;
	}
	
	@Action(value = "waybill_findByWayBillNum", 
			results = {@Result(type = "json")})
	public String findByWayBillNum() {
		Map<String, Object> result = new HashMap<String, Object>();
		WayBill wayBill =  wayBillService.findByWayBillNum(model.getWayBillNum());
		if(wayBill == null){
			// 数据为空,不能回显数据
			result.put("success", false);
			System.out.println("通过运单回显数据失败,运单号是:" + model.getWayBillNum());
		}else{
			// 数据不为空,可以回显数据
			result.put("success", true);
			result.put("wayBillData", wayBill);
			ActionContext.getContext().getValueStack().push(result);
			System.out.println("通过运单回显数据成功,运单号是:" + model.getWayBillNum());
		}
		return SUCCESS;
	}
	
	
	@Action(value = "waybill_page", results = {@Result(type = "json")})
	public String page() {
		Page<WayBill> pageData =  wayBillService.page(page, rows, model);
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}

}
