package com.zy.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zy.bos.domain.base.TakeTime;

/** 
* @author  philchang 
* @date 2017年12月9日 下午12:52:24 
* @version 1.0.0
*   
*/
public interface TakeTimeRepository extends JpaRepository<TakeTime, Integer> {

}
