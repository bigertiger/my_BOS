package com.zy.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;

import com.zy.bos.domain.base.Standard;

/** 
* @author  philchang 
* @date 2017年11月29日 下午8:31:51 
* @version 1.0.0
*   
*/
public interface StandardService {
	
	public void save(Standard standard);

	public Page<Standard> findPage(int page, int rows);

	public List<Standard> findAll();

}
