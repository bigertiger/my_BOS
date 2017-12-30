package com.zy.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;

import com.zy.bos.domain.base.Courier;

/** 
* @author  philchang 
* @date 2017年12月1日 下午8:40:08 
* @version 1.0.0
*   
*/
public interface CourierService {

	void save(Courier courier);

	Page<Courier> findPage(Courier courier, int page, int rows);

	void delete(String[] idArray);

	List<Courier> findNoAs();

}
