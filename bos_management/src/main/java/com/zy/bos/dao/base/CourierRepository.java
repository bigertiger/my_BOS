package com.zy.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zy.bos.domain.base.Courier;

/** 
* @author  philchang 
* @date 2017年12月1日 下午8:44:05 
* @version 1.0.0
*   
*/
public interface CourierRepository extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier>{

	@Query(value = "update Courier set deltag='1' where id in :ids")
	@Modifying
	void updateCourier(@Param("ids")List<Integer> ids);

}
