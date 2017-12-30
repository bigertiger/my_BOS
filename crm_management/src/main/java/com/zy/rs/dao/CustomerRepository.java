package com.zy.rs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.itcast.crm.domain.Customer;

/** 
* @author  philchang 
* @date 2017年12月7日 下午6:04:26 
* @version 1.0.0
*   
*/
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {
	
	List<Customer> findByFixedAreaIdIsNull();
	
	List<Customer> findByFixedAreaId(String fid);
	
	@Query("update Customer set fixedAreaId = :id where id in :ids")
	@Modifying
	void updateFixedAreaId(@Param("id")String fid, @Param("ids")List<Integer> ids);
	
	@Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
	@Modifying
	void clearFixedAreaId(String fid);

	
	Customer findByTelephone(String telephone);

	Customer findByTelephoneAndPassword(String telephone, String password);

	Customer findByAddress(String address);

}
