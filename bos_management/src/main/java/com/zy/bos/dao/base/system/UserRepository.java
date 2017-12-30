package com.zy.bos.dao.base.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zy.bos.domain.system.User;

/** 
* @author  philchang 
* @date 2017年12月25日 下午4:46:41 
* @version 1.0.0
*   
*/
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

}
