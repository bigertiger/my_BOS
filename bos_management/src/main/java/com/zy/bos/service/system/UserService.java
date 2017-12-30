package com.zy.bos.service.system;

import com.zy.bos.domain.system.Role;
import com.zy.bos.domain.system.User;

import java.util.List;

/** 
* @author  philchang 
* @date 2017年12月25日 下午4:48:31 
* @version 1.0.0
*   
*/
public interface UserService {
	
	User findByUsername(String username);


	List<User> findAll();
}
