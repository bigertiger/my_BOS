package com.zy.bos.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.bos.dao.base.StandardRepository;
import com.zy.bos.domain.base.Standard;
import com.zy.bos.service.base.StandardService;

/** 
* @author  philchang 
* @date 2017年11月29日 下午8:33:44 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class StandardServiceImpl implements StandardService {
	
	@Autowired
	private StandardRepository standardRepository;

	@Override
	@CacheEvict(value = "standard", allEntries = true)
	public void save(Standard standard) {
		
		standardRepository.save(standard);
	}

	@Override
	public Page<Standard> findPage(int page, int rows) {
		
		Pageable pageable = new PageRequest(page, rows);
		return standardRepository.findAll(pageable);
	}

	@Override
	@Cacheable("standard")
	public List<Standard> findAll() {
		
		return standardRepository.findAll();
	}

}
