package com.zy.bos.service.system;

import java.util.List;

import com.zy.bos.domain.system.Permission;
import com.zy.bos.domain.system.User;

/** 
* @author  philchang 
* @date 2017年12月25日 下午6:07:23 
* @version 1.0.0
*   
*/
public interface PermissionService {

	List<Permission> findByUser(User user);

    List<Permission> findAll();


    void save(Permission model);
}
