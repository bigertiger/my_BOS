package com.zy.bos.service.impl.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
import com.zy.bos.domain.base.Courier;
import com.zy.bos.domain.base.Standard;
import com.zy.bos.service.base.CourierService;

/** 
* @author  philchang 
* @date 2017年12月1日 下午8:42:38 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

	@Autowired
	private CourierRepository courierRepository;
	
	@Override
	public void save(Courier courier) {
		courierRepository.save(courier);
	}

	@Override
	public Page<Courier> findPage(final Courier courier, int page, int rows) {
		Specification<Courier> spec = new Specification<Courier>() {
			
			@Override
			public Predicate toPredicate(Root<Courier> root, 
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				// 对快递员进行基于编号的等值查询
				if(StringUtils.isNotBlank(courier.getCourierNum())) {
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), 
							courier.getCourierNum());
					list.add(p1);
				}
				// 对快递员进行基于公司的模糊查询
				if(StringUtils.isNotBlank(courier.getCompany())) {
					Predicate p2 = cb.like(root.get("company").as(String.class), 
							"%" + courier.getCompany() + "%");
					list.add(p2);
				}
				// 对快递员进行基于类型的等值查询
				if(StringUtils.isNotBlank(courier.getType())) {
					Predicate p3 = cb.equal(root.get("type").as(String.class), 
							courier.getType());
					list.add(p3);
				}
				// 对快递员进行基于收派标准standard的多表连接模糊查询
				Join<Courier, Standard> join = root.join("standard", JoinType.INNER);
				if(courier.getStandard() != null && 
						StringUtils.isNotBlank(courier.getStandard().getName())) {
					Predicate p4 = cb.like(join.get("name").as(String.class), 
							courier.getStandard().getName());
					list.add(p4);
				}
				
				
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Pageable pageable = new PageRequest(page, rows);
		return courierRepository.findAll(spec, pageable);
	}

	@Override
	public void delete(String[] idArray) {

		
		// 实际是执行update操作将数据库中courier的deltag设置为1
		List<Integer> ids = new ArrayList<Integer>();
		for (String string : idArray) {
			ids.add(Integer.parseInt(string));
		}
		courierRepository.updateCourier(ids);
	}

	@Override
	public List<Courier> findNoAs() {
		Specification<Courier> spec = new Specification<Courier>() {
			
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.isEmpty(root.get("fixedAreas").as(Set.class));
				
				return p1;
			}
		};
		return courierRepository.findAll(spec);
	}

}
