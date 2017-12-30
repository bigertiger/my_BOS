package com.zy.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zy.bos.domain.base.Area;

/** 
* @author  philchang 
* @date 2017年12月3日 下午6:35:24 
* @version 1.0.0
*   
*/
public interface AreaRepository extends JpaRepository<Area, String>,JpaSpecificationExecutor<Area> {
	
	
	Area findByProvinceAndCityAndDistrict(String province, String city, String district);

}
