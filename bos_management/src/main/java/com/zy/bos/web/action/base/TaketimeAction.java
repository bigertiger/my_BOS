package com.zy.bos.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.zy.bos.domain.base.TakeTime;
import com.zy.bos.service.base.TakeTimeService;
import com.zy.bos.utils.BaseAction;

/** 
* @author  philchang 
* @date 2017年12月9日 上午11:01:04 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class TaketimeAction extends BaseAction<TakeTime> {
	
	@Autowired
	private TakeTimeService takeTimeService;
	
	@Action(value = "takeTime_findAll", results = {@Result(type = "json")})
	public String findAll() {
		List<TakeTime> list = takeTimeService.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
	
	

}
