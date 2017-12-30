package com.zy.bos.service.system;

import java.util.List;

import com.zy.bos.domain.system.Role;
import com.zy.bos.domain.system.User;

/** 
* @author  philchang 
* @date 2017年12月25日 下午6:06:58 
* @version 1.0.0
*   
*/
public interface Roleservice {

	List<Role> findByUser(User user);


    List<Role> findAll();


    void save(Role model, String[] permissionIds, String menuIds);
}
