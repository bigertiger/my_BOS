package com.zy.bos.service.impl.take_delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.bos.dao.take_delivery.OrderRepository;
import com.zy.bos.domain.take_delivery.Order;
import com.zy.bos.service.take_delivery.OrderService;

/** 
* @author  philchang 
* @date 2017年12月20日 下午5:53:30 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order findByOrderNum(String orderNum) {
		
		return orderRepository.findByOrderNum(orderNum);
	}

}
