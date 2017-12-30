package com.zy.bos.service.take_delivery;

import com.zy.bos.domain.take_delivery.Order;

/** 
* @author  philchang 
* @date 2017年12月20日 下午4:11:14 
* @version 1.0.0
*   
*/
public interface OrderService {

	Order findByOrderNum(String orderNum);

}
