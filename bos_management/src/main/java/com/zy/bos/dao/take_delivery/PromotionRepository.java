package com.zy.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zy.bos.domain.take_delivery.Promotion;

/** 
* @author  philchang 
* @date 2017年12月14日 下午1:06:21 
* @version 1.0.0
*   
*/
public interface PromotionRepository extends JpaRepository<Promotion, Integer>,JpaSpecificationExecutor<Promotion> {

}
