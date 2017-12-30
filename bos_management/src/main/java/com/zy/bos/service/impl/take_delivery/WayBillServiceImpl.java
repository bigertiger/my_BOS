package com.zy.bos.service.impl.take_delivery;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.bos.dao.take_delivery.WayBillRepository;
import com.zy.bos.domain.take_delivery.WayBill;
import com.zy.bos.index.WayBillIndexRepository;
import com.zy.bos.service.take_delivery.WayBillService;

/** 
* @author  philchang 
* @date 2017年12月20日 下午3:09:27 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {
	
	@Autowired
	private WayBillRepository wayBillRepository;
	
	
	@Autowired
	private WayBillIndexRepository wayBillIndexRepository;

	@Override
	public void save(WayBill model) {
		if(model.getOrder() != null && 
				(model.getOrder().getId() == null ||
				model.getOrder().getId() == 0)) {
			model.setOrder(null);
		}
		WayBill perWayBil = wayBillRepository.findByWayBillNum(model.getWayBillNum());
		if(perWayBil == null){
			wayBillRepository.save(model);
			wayBillIndexRepository.save(model);
		}else{
			try {
				Integer id = perWayBil.getId();
				BeanUtils.copyProperties(perWayBil, model);
				perWayBil.setId(id);
				wayBillRepository.save(perWayBil);
				wayBillIndexRepository.save(perWayBil);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		

	}

	@Override
	public WayBill findByWayBillNum(String wayBillNum) {
		
		return wayBillRepository.findByWayBillNum(wayBillNum);
	}

	@Override
	public Page<WayBill> page(int page, int rows, WayBill model) {
		Pageable pageable = new PageRequest(page - 1, rows);
		// 判断条件是否存在
		if(StringUtils.isBlank(model.getWayBillNum())
				&& StringUtils.isBlank(model.getSendAddress())
				&& StringUtils.isBlank(model.getRecAddress())
				&& StringUtils.isBlank(model.getSendProNum())
				&& (model.getSignStatus() == null || model.getSignStatus() == 0)){
			// 这是无条件查询,直接查询数据库
			return wayBillRepository.findAll(pageable);
		}else{
			BoolQueryBuilder query = new BoolQueryBuilder();
			// 查询条件
			
			// 拼接运单号的条件
			if(StringUtils.isNotBlank(model.getWayBillNum())) {
				QueryBuilder termQuery = new TermQueryBuilder("wayBillNum"
						, model.getWayBillNum());
				query.must(termQuery);
			}
			
			
			// 拼接发货地址的条件
			if(StringUtils.isNotBlank(model.getSendAddress())) {
				
				QueryBuilder wildQuery = new WildcardQueryBuilder("sendAddress"
						, "*" + model.getSendAddress() + "*");
				
				QueryBuilder queryStringQuery = new QueryStringQueryBuilder(
						model.getSendAddress())
						.field("sendAddress").defaultOperator(Operator.OR);
				BoolQueryBuilder stringQuery = new BoolQueryBuilder();
				stringQuery.should(wildQuery);
				stringQuery.should(queryStringQuery);
				query.must(stringQuery);
			}
			
			// 拼接收货地址的条件
			if(StringUtils.isNotBlank(model.getRecAddress())) {
				
				QueryBuilder wildQuery = new WildcardQueryBuilder("recAddress"
						, "*" + model.getRecAddress() + "*");
				
				QueryBuilder queryStringQuery = new QueryStringQueryBuilder(
						model.getRecAddress())
						.field("recAddress").defaultOperator(Operator.OR);
				BoolQueryBuilder stringQuery = new BoolQueryBuilder();
				stringQuery.should(wildQuery);
				stringQuery.should(queryStringQuery);
				query.must(stringQuery);
			}
			
			// 拼接速运类型的条件
			if(StringUtils.isNotBlank(model.getSendProNum())) {
				QueryBuilder termQuery = new TermQueryBuilder("sendProNum"
						, model.getSendProNum());
				query.must(termQuery);
			}
			
			// 拼接速运类型的条件
			if(model.getSignStatus() != null && model.getSignStatus() != 0) {
				QueryBuilder termQuery = new TermQueryBuilder("signStatus"
						, model.getSignStatus());
				query.must(termQuery);
			}
			
			SearchQuery searchQuery = new NativeSearchQuery(query);
			searchQuery.setPageable(pageable);
			
			
			return wayBillIndexRepository.search(searchQuery);
			
			
			
			
		}
		
		
		
		
		
		
		
		
	}

}
