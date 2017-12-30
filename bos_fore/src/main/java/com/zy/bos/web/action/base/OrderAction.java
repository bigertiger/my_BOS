package com.zy.bos.web.action.base;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zy.bos.domain.base.Area;
import com.zy.bos.domain.take_delivery.Order;
import com.zy.bos.utils.BaseAction;

import cn.itcast.crm.domain.Customer;

/** 
* @author  philchang 
* @date 2017年12月18日 下午5:57:34 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {
	
	
	// 属性驱动封装寄收地址信息
	private String sendAreaInfo; 
	private String recAreaInfo;
	
	
	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}


	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}


	@Action(value = "order_save", results = {
			@Result(type = "redirect", location = "index.html#/myhome")})
	public String save() {
		// 将接收到的发件地址信息进行封装
		System.out.println(sendAreaInfo);
		String[] sendData = sendAreaInfo.split("/");
		Area sendArea = new Area();
		sendArea.setProvince(sendData[0]);
		sendArea.setCity(sendData[1]);
		sendArea.setDistrict(sendData[2]);
		
		// 将接收到的收件地址信息进行封装
		String[] recData = recAreaInfo.split("/");
		Area recArea = new Area();
		recArea.setProvince(recData[0]);
		recArea.setCity(recData[1]);
		recArea.setDistrict(recData[2]);
		// 经两个area信息封装到order中
		model.setSendArea(sendArea);
		model.setRecArea(recArea);
		System.out.println("地址信息封装完成");
		// 将登陆用户信息封装到order中
		Customer customer = (Customer) ServletActionContext.getRequest()
				.getSession().getAttribute("customer");
		System.out.println("登录用户信息封装完成");
		
		// 调用webservice完成fore到bos_management的信息转场(将model信息传递过去)
		// http://localhost:9001/bos_management/services/myOrderService/saveOrder
		WebClient.create("http://localhost:9001/bos_management")
				.path("services")
				.path("myOrderService")
				.path("saveOrder")
				.type(MediaType.APPLICATION_JSON)
				.post(model);
		
		return SUCCESS;
	}
	
	

}
