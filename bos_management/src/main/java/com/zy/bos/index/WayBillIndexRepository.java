package com.zy.bos.index;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zy.bos.domain.take_delivery.WayBill;

/** 
* @author  philchang 
* @date 2017年12月24日 下午6:34:32 
* @version 1.0.0
*   
*/
public interface WayBillIndexRepository extends ElasticsearchRepository<WayBill, Integer> {

}
