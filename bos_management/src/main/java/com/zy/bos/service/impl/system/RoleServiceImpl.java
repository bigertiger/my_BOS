package com.zy.bos.service.impl.system;

import java.lang.reflect.Array;
import java.util.List;

import com.zy.bos.dao.base.system.MenuRepository;
import com.zy.bos.dao.base.system.PermissionReepository;
import com.zy.bos.domain.system.Menu;
import com.zy.bos.domain.system.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.bos.dao.base.system.RoleRepository;
import com.zy.bos.domain.system.Role;
import com.zy.bos.domain.system.User;
import com.zy.bos.service.system.Roleservice;

/** 
* @author  philchang 
* @date 2017年12月25日 下午6:35:59 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class RoleServiceImpl implements Roleservice {
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private PermissionReepository permissionReepository;

	@Override
	public List<Role> findByUser(User user) {
		if(user.getUsername().equals("admin")){
			return roleRepository.findAll();
		}
		return roleRepository.findByUser(user.getId());
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public void save(Role model, String[] permissionIds, String menuIds) {

		roleRepository.save(model);

		if(permissionIds != null) {
			for (String id: permissionIds) {
				Permission permission = permissionReepository.findOne(Integer.parseInt(id));
				model.getPermissions().add(permission);
			}
		}

		if(StringUtils.isNotBlank(menuIds)) {
			String[] menus = menuIds.split(",");
			for (String id: menus) {
				Menu menu = menuRepository.findOne(Integer.parseInt(id));
				model.getMenus().add(menu);
			}

		}

	}

}
