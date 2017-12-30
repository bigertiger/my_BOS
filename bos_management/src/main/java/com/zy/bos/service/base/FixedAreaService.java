package com.zy.bos.service.base;

import org.springframework.data.domain.Page;

import com.zy.bos.domain.base.FixedArea;

/** 
* @author  philchang 
* @date 2017年12月5日 下午4:03:38 
* @version 1.0.0
*   
*/
public interface FixedAreaService {

	void save(FixedArea fixedArea);

	Page<FixedArea> page(FixedArea model, int i, int rows);

	void courierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId);

	

}
