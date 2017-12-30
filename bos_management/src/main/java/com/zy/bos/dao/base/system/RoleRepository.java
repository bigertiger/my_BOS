package com.zy.bos.dao.base.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zy.bos.domain.system.Role;

/** 
* @author  philchang 
* @date 2017年12月25日 下午6:37:47 
* @version 1.0.0
*   
*/
public interface RoleRepository extends JpaRepository<Role, Integer>{

	@Query("from Role r inner join fetch r.users u where u.id = ?")
	List<Role> findByUser(Integer id);

}
