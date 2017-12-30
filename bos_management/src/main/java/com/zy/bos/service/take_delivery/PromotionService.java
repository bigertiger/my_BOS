package com.zy.bos.service.take_delivery;

import org.springframework.data.domain.Page;

import com.zy.bos.domain.take_delivery.Promotion;

/** 
* @author  philchang 
* @date 2017年12月14日 下午12:59:47 
* @version 1.0.0
*   
*/

public interface PromotionService {

	void save(Promotion model);

	Page<Promotion> page(int i, int rows, Promotion model);

}
