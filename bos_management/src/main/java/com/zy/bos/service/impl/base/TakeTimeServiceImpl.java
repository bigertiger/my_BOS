package com.zy.bos.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.bos.dao.base.TakeTimeRepository;
import com.zy.bos.domain.base.TakeTime;
import com.zy.bos.service.base.TakeTimeService;

/** 
* @author  philchang 
* @date 2017年12月9日 下午12:50:35 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
	
	@Autowired
	private TakeTimeRepository takeTimeRepository;

	@Override
	public List<TakeTime> findAll() {
	
		return takeTimeRepository.findAll();
	}

}
