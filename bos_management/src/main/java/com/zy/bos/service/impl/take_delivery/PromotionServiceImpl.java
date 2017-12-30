package com.zy.bos.service.impl.take_delivery;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.bos.dao.take_delivery.PromotionRepository;
import com.zy.bos.domain.take_delivery.Promotion;
import com.zy.bos.service.take_delivery.PromotionService;

/** 
* @author  philchang 
* @date 2017年12月14日 下午1:01:26 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;
	
	@Override
	public void save(Promotion model) {
		promotionRepository.save(model);

	}

	@Override
	public Page<Promotion> page(int i, int rows, Promotion model) {
		Pageable pageable = (Pageable) new PageRequest(i, rows);
		
		return promotionRepository.findAll(pageable);
	}

}
