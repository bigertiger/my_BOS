package com.zy.bos.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.zy.bos.dao.take_delivery.PromotionRepository;
import com.zy.bos.domain.take_delivery.Promotion;
import com.zy.bos.domain.webservice.PageBean;
import com.zy.bos.webservice.MyPromotionService;

/** 
* @author  philchang 
* @date 2017年12月14日 下午6:50:17 
* @version 1.0.0
*   
*/
public class MyPromotionServiceImpl implements MyPromotionService {
	
	@Autowired
	private PromotionRepository promotionRepository;

	@Override
	public PageBean<Promotion> page(Integer page, Integer rows) {
		Pageable pageable = new PageRequest(page -1, rows);
		Page<Promotion> pageData = promotionRepository.findAll(pageable);
		PageBean<Promotion> pageBean = new PageBean<>();
		pageBean.setTotalCount(pageData.getTotalElements());
		pageBean.setPageData(pageData.getContent());
		return pageBean;
	}

}
