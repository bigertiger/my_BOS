package com.zy.bos.web.action.base;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.zy.bos.domain.take_delivery.Promotion;
import com.zy.bos.domain.webservice.PageBean;
import com.zy.bos.utils.BaseAction;

/** 
* @author  philchang 
* @date 2017年12月14日 下午7:00:32 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion> {
	
	@Action(value = "promotion_page", results = {@Result(type = "json")})
	public String page() {
		// http://localhost:9001/bos_management/services/myPromotionService/page?page=1&rows=5
		PageBean<Promotion> pageBean = WebClient.create("http://localhost:9001")
				.path("bos_management")
				.path("services")
				.path("myPromotionService")
				.path("page")
				.query("page", page)
				.query("rows", rows)
				.accept(MediaType.APPLICATION_JSON)
				.get(PageBean.class);
		
		ActionContext.getContext().getValueStack().push(pageBean);
		System.out.println("jjjj");
		return SUCCESS;
	}
	

}
