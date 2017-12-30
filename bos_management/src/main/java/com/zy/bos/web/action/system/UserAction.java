package com.zy.bos.web.action.system;



import com.opensymphony.xwork2.ActionContext;
import com.zy.bos.domain.system.Role;
import com.zy.bos.service.system.Roleservice;
import com.zy.bos.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zy.bos.domain.system.User;
import com.zy.bos.utils.BaseAction;

import java.util.List;

/** 
* @author  philchang 
* @date 2017年12月25日 下午4:28:40 
* @version 1.0.0
*   
*/
@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	@Autowired
	private UserService userService;
	
	@Action(value = "user_login", results = {
			@Result(type = "redirect", location = "index.html"),
			@Result(name = "login", type = "redirect", location = "login.html")})
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		AuthenticationToken token = 
				new UsernamePasswordToken(model.getUsername(), model.getPassword());
		// System.out.println("用户名:" + model.getUsername() + "密码:" + model.getPassword());
		try {
			// 登陆成功
			subject.login(token);
			return SUCCESS;
		} catch (AuthenticationException e) {
			// 登陆失败
			e.printStackTrace();
			return LOGIN;
		}
		
	}


	@Action(value= "user_logout", results = {
			@Result(type = "redirect", location = "login.html")
	})
	public String loggOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return SUCCESS;
	}

	@Action(value = "user_list", results = {
			@Result(type = "json")
	})
	public String list() {
		List<User> users =  userService.findAll();
		ActionContext.getContext().getValueStack().push(users );

		return SUCCESS;
	}
}
