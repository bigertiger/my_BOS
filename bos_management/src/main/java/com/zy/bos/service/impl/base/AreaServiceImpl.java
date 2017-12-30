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

import com.zy.bos.dao.base.AreaRepository;
import com.zy.bos.domain.base.Area;
import com.zy.bos.service.base.AreaService;

/** 
* @author  philchang 
* @date 2017年12月3日 下午6:33:15 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private AreaRepository areaRepository;

	@Override
	public void save(List<Area> areas) {


		areaRepository.save(areas);
	}

	@Override
	public Page<Area> findPage(final Area area, int i, int rows) {

		Specification<Area> spec = new Specification<Area>() {
			
			@Override
			public Predicate toPredicate(Root<Area> root, 
					CriteriaQuery<?> query, 
					CriteriaBuilder cb) {
				
				List<Predicate> list = new ArrayList<>();
				if(StringUtils.isNotBlank(area.getProvince())) {
					Predicate p1 = cb.equal(root.get("province").as(String.class), 
							area.getProvince());
					list.add(p1);
				}
				if(StringUtils.isNotBlank(area.getCity())) {
					Predicate p2 = cb.like(root.get("city").as(String.class), 
							"%" + area.getCity() + "%");
					list.add(p2);
				}
				if(StringUtils.isNotBlank(area.getDistrict())) {
					Predicate p3 = cb.like(root.get("district").as(String.class), 
							"%" + area.getDistrict() + "%");
					list.add(p3);
				}
				
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		
		Pageable pageable = new PageRequest(i, rows);
		return areaRepository.findAll(spec, pageable);
	}

}
