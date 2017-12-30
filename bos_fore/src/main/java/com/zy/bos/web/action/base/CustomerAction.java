package com.zy.bos.web.action.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.zy.bos.utils.BaseAction;
import com.zy.bos.utils.MailUtils;

import cn.itcast.crm.domain.Customer;

/** 
* @author  philchang 
* @date 2017年12月11日 下午1:55:47 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer>{
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsQueueTemplate;
	
	// 属性驱动注入前台传入的验证码
	private String code;

	
	public void setCode(String code) {
		this.code = code;
	}
	
	
	@Action(value = "customer_login", results = {@Result(type = "redirect", location = "index.html#/myhome"),
			@Result(name = "error", type = "redirect", location = "login.html")})
	public String login() {
		// http://localhost:9002/crm_management/services/customerService/
		Customer customer = WebClient.create("http://localhost:9002/crm_management")
				.path("services")
				.path("customerService")
				.path("login")
				.query("telephone", model.getTelephone())
				.query("password", model.getPassword())
				.accept(MediaType.APPLICATION_JSON)
				.get(Customer.class);
		if(customer == null) {
			// 登陆失败
			return ERROR;
		}else {
			// 登陆成功
			ServletActionContext.getRequest().getSession().setAttribute("customer", customer);
			return SUCCESS;
		}
		
	}
	
	// 发送短信验证码
	@Action(value = "customer_sendSms")
	public void sendSms(){
		final String sendCode = RandomStringUtils.randomNumeric(4);
		ServletActionContext.getRequest()
					.getSession()
					.setAttribute(model.getTelephone(), sendCode);
		jmsQueueTemplate.send("bos_sms", new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				message.setString("telephone", model.getTelephone());
				message.setString("msg", sendCode);
				return message;
			}
		});
		// System.out.println("发送的验证码是"+ sendCode);
		
	}
	
	
	// 客户注册
	@Action(value = "customer_signup", results = {
			@Result(type = "redirect", location = "signup-success.html"),
			@Result(name = "error", type = "redirect", location = "signup.html")})
	public String signup() {
		String sessionCode = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
		if(sessionCode == null || !code.equals(sessionCode)){
			System.out.println("验证码错误");
			return ERROR;
		}
		
		WebClient.create("http://localhost:9002/crm_management/services/customerService/"
					+ "saveCustomer")
					.type(MediaType.APPLICATION_XML)
					.post(model);
		System.out.println("客户注册成功");
		
		// 给用户发送激活邮件
		String activeUrl = "http://localhost:9003/bos_fore/customer_active.action";
		String activeCode = RandomStringUtils.randomNumeric(32);
		System.out.println("邮箱激活码是:" + activeCode);
		redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 24, TimeUnit.HOURS);
		String content = String.format("尊敬的客户您好,感谢您注册速运快递!请于24小时内点击以下链接进行邮箱激活:"
				+"<br/><a href = '%s?telephone=%s&activeCode=%s'>速运快递邮箱激活地址</a>",
				activeUrl, model.getTelephone(), activeCode);
		MailUtils.sendMail("速运快递邮箱激活", content, model.getEmail());
		return SUCCESS;
	}
	
	// 属性驱动接收激活时的激活码
	private String activeCode;
	
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	// 激活用户邮箱
	@Action(value = "customer_active")
	public void active() throws IOException {
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		String redisCode = redisTemplate.opsForValue().get(model.getTelephone());
		
		// 判断激活码是否有效
		if(activeCode == null || redisCode == null || !redisCode.equals(activeCode)) {
			ServletActionContext.getResponse().getWriter().println("激活码无效,请重新绑定邮箱!");
		} else {
			// 判断是否重复绑定
			Customer customer = WebClient.create("http://localhost:9002/crm_management/services/customerService/"
					+ "findByTelephone/" + model.getTelephone())
					.accept(MediaType.APPLICATION_JSON)
					.get(Customer.class);
			System.out.println(customer);
			if(customer.getType() == null){
				// 进行绑定
				WebClient.create("http://localhost:9002/crm_management/services/customerService/"
						+ "updateType/" + model.getTelephone())
						.get();
				
				ServletActionContext.getResponse().getWriter().println("恭喜您,邮箱绑定成功!");
				
			} else {
				// 已经绑定
				ServletActionContext.getResponse().getWriter().println("您的邮箱已经绑定,请不要重复绑定!");
			}
			
			redisTemplate.delete(model.getTelephone());
		}
		
		
		
	}
	

}
