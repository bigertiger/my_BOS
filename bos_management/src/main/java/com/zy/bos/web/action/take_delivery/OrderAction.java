package com.zy.bos.web.action.take_delivery;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.zy.bos.domain.take_delivery.Order;
import com.zy.bos.service.take_delivery.OrderService;
import com.zy.bos.utils.BaseAction;

/** 
* @author  philchang 
* @date 2017年12月20日 下午4:09:42 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {
	
	@Autowired
	private OrderService orderService;
	
	
	@Action(value = "order_findByOrderNum", results = {@Result(type = "json")})
	public String findByOrderNum() {
		Map<String, Object> result = new HashMap<String, Object>();
		Order order = orderService.findByOrderNum(model.getOrderNum());
		if(order == null) {
			System.out.println("根据订单号回显失败,订单号是:" + model.getOrderNum());
			result.put("success", false);
		}else {
			System.out.println("根据订单号回显成功,订单号是:" + model.getOrderNum());
			result.put("success", true);
			result.put("orderData", order);
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}
	
	

}
