package com.zy.bos.service.take_delivery;

import org.springframework.data.domain.Page;

import com.zy.bos.domain.take_delivery.WayBill;

/** 
* @author  philchang 
* @date 2017年12月20日 上午10:54:01 
* @version 1.0.0
*   
*/
public interface WayBillService {

	void save(WayBill model);

	WayBill findByWayBillNum(String wayBillNum);

	Page<WayBill> page(int page, int rows, WayBill wayBill);

}
