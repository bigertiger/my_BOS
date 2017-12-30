package com.zy.bos.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.bos.dao.base.system.PermissionReepository;
import com.zy.bos.domain.system.Permission;
import com.zy.bos.domain.system.User;
import com.zy.bos.service.system.PermissionService;

/** 
* @author  philchang 
* @date 2017年12月25日 下午6:41:37 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionReepository permissionRepository;

	@Override
	public List<Permission> findByUser(User user) {
		if(user.getUsername().equals("admin")) {
			return permissionRepository.findAll();
		}
		return permissionRepository.findByUser(user.getId());
	}

	@Override
	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	@Override
	public void save(Permission model) {
		permissionRepository.save(model);
	}

}
