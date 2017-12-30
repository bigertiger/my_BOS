package com.zy.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zy.bos.domain.take_delivery.WayBill;

/** 
* @author  philchang 
* @date 2017年12月20日 下午3:56:51 
* @version 1.0.0
*   
*/
public interface WayBillRepository extends JpaRepository<WayBill, Integer> {

	WayBill findByWayBillNum(String wayBillNum);

}
