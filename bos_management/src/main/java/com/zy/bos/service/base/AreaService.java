package com.zy.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;

import com.zy.bos.domain.base.Area;

/** 
* @author  philchang 
* @date 2017年12月3日 下午6:31:56 
* @version 1.0.0
*   
*/
public interface AreaService {

	void save(List<Area> areas);

	Page<Area> findPage(Area area, int i, int rows);
	

}
