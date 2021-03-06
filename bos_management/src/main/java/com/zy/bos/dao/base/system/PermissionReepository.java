package com.zy.bos.dao.base.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zy.bos.domain.system.Permission;

/** 
* @author  philchang 
* @date 2017年12月25日 下午6:43:40 
* @version 1.0.0
*   
*/
public interface PermissionReepository extends JpaRepository<Permission, Integer> {

	
	@Query("from Permission p inner join fetch p.roles r inner join fetch r.users u where u.id = ?")
	List<Permission> findByUser(Integer id);

}
