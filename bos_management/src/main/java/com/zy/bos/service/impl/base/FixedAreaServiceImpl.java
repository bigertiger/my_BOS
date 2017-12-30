package com.zy.bos.service.impl.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.bos.dao.base.CourierRepository;
import com.zy.bos.dao.base.FixedAreareRepository;
import com.zy.bos.dao.base.TakeTimeRepository;
import com.zy.bos.domain.base.Courier;
import com.zy.bos.domain.base.FixedArea;
import com.zy.bos.domain.base.TakeTime;
import com.zy.bos.service.base.FixedAreaService;

/** 
* @author  philchang 
* @date 2017年12月5日 下午4:05:37 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
	
	@Autowired
	private FixedAreareRepository fixedAreaRepository;
	
	@Autowired
	private CourierRepository courierRepository;
	
	@Autowired
	private TakeTimeRepository takeTimeRepository;

	@Override
	public void save(FixedArea fixedArea) {
		
		fixedAreaRepository.save(fixedArea);

	}

	@Override
	public Page<FixedArea> page(final FixedArea model, int i, int rows) {
		
		Pageable pageable = new PageRequest(i, rows);
		Specification<FixedArea> spec = new Specification<FixedArea>() {
			
			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				
				// 基于定区id(定区编码)进行模糊查询
				if(StringUtils.isNotBlank(model.getId())) {
					Predicate p1 = cb.like(root.get("id").as(String.class), 
							"%" + model.getId() + "%");
					list.add(p1);
				}
				
				// 基于所属单位进行模糊查询
				if(StringUtils.isNotBlank(model.getCompany())) {
					Predicate p2 = cb.like(root.get("company").as(String.class), 
							"%" + model.getCompany() + "%");
					list.add(p2);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		
		
		return fixedAreaRepository.findAll(spec, pageable);
	}

	@Override
	public void courierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId) {
		
		FixedArea fixedArea = fixedAreaRepository.findOne(model.getId());
		Courier courier = courierRepository.findOne(courierId);
		// System.out.println(courier);
		// System.out.println(fixedArea);
		// System.out.println(model.getId());
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		
		fixedArea.getCouriers().add(courier);
		
		courier.setTakeTime(takeTime);
		
	}

}
