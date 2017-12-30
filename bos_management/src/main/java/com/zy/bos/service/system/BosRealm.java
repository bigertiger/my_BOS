package com.zy.bos.service.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zy.bos.domain.system.Permission;
import com.zy.bos.domain.system.Role;
import com.zy.bos.domain.system.User;

/** 
* @author  philchang 
* @date 2017年12月25日 下午4:42:16 
* @version 1.0.0
*   
*/
// @Service("bosRealm")
public class BosRealm extends AuthorizingRealm {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Roleservice roleService;
	
	@Autowired
	private PermissionService permissionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("认证授权程序");
		// 创建一个装角色和权限的容器对象
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
		
		// 获取subject对象
		Subject subject = SecurityUtils.getSubject();
		// 通过subject获取当前登录的user对象
		User user = (User) subject.getPrincipal();
		// 通过user关联查询当前用户的所有角色
		List<Role> roles = roleService.findByUser(user);
		for (Role role : roles) {
			// 将当前用户的所有角色添加到容器中
			info.addRole(role.getKeyword());
		}
		// 通过user关联查询当前用户的所有权限
		List<Permission> permissions = permissionService.findByUser(user);
		for (Permission permission : permissions) {
			// 将当前用户的所有权限添加到容器中
			info.addStringPermission(permission.getKeyword());
		}
		// 将容器返回给securitymanager对象
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证登录程序");
		// 通过向下转型将传过来的token对象转为usernameandpasswordtoken对象
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		// 调用userservice的findbyusername方法来查询当前用户输入的用户名对应的用户是否存在
		User user = userService.findByUsername(userToken.getUsername());
		
		// 判断查询到的用户对象是否为空,如果为空,就是用户输入的用户名错误
		if(user == null) {
			return null;
		}else{
			// 查询到的
			return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
		}
	}

	

}
