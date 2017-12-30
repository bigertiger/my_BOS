package com.zy.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zy.bos.domain.take_delivery.Order;

/** 
* @author  philchang 
* @date 2017年12月18日 下午8:25:55 
* @version 1.0.0
*   
*/
public interface OrderRepository extends JpaRepository<Order, Integer> {

	Order findByOrderNum(String orderNum);

}
