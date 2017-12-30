package com.zy.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zy.bos.domain.base.FixedArea;

/** 
* @author  philchang 
* @date 2017年12月5日 下午4:08:45 
* @version 1.0.0
*   
*/
public interface FixedAreareRepository extends JpaRepository<FixedArea, String>, JpaSpecificationExecutor<FixedArea>{
	
	
	

}
