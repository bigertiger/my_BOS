package com.zy.bos.webservice.impl;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.bos.dao.base.AreaRepository;
import com.zy.bos.dao.base.FixedAreareRepository;
import com.zy.bos.dao.take_delivery.OrderRepository;
import com.zy.bos.dao.take_delivery.WorkBillRepository;
import com.zy.bos.domain.base.Area;
import com.zy.bos.domain.base.Courier;
import com.zy.bos.domain.base.FixedArea;
import com.zy.bos.domain.base.SubArea;
import com.zy.bos.domain.take_delivery.Order;
import com.zy.bos.domain.take_delivery.WorkBill;
import com.zy.bos.webservice.MyOrderService;

import cn.itcast.crm.domain.Customer;

/** 
* @author  philchang 
* @date 2017年12月18日 下午7:28:50 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class MyOrderServiceImpl implements MyOrderService {
	
	@Autowired
	private AreaRepository areaRepository;
	
	@Autowired
	private FixedAreareRepository fixedAreaRespository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private WorkBillRepository workBillRepository;

	@Override
	public void saveOrder(Order order) {
		System.out.println("Order传过来了:" + order.getRecAddress());
		Area realSendArea = areaRepository.findByProvinceAndCityAndDistrict(order.getSendArea().getProvince(), 
				order.getSendArea().getCity(), order.getSendArea().getDistrict());
		Area realRecArea = areaRepository.findByProvinceAndCityAndDistrict(order.getRecArea().getProvince(), 
				order.getRecArea().getCity(), order.getRecArea().getDistrict());
		order.setSendArea(realSendArea);
		order.setRecArea(realRecArea);
		
		// 设置订单信息
		order.setOrderNum(UUID.randomUUID().toString());
		System.out.println(order.getOrderNum());
		order.setStatus("1");
		order.setOrderTime(new Date());
		
		// 进行第一种基于客户表的address的完全匹配的自动分单逻辑
		// http://localhost:9002/crm_management/services/customerService/findCustomerByAddress
		System.out.println("通过webservice进行地址的完全匹配");
		Customer customer = WebClient.create("http://localhost:9002/crm_management")
				.path("services")
				.path("customerService")
				.path("findCustomerByAddress")
				.query("address", order.getSendAddress())
				.accept(MediaType.APPLICATION_JSON)
				.get(Customer.class);
		
		if(customer != null) {
			System.out.println("完全地址匹配分单判定成功");
			// 该逻辑自动分单成功
			// 保存订单
			String fixedAreaId = customer.getFixedAreaId();
			FixedArea fixedArea = fixedAreaRespository.findOne(fixedAreaId);
			Courier courier = fixedArea.getCouriers().iterator().next();
			order.setCourier(courier);
			order.setOrderType("1");
			orderRepository.save(order);
			System.out.println("完全地址匹配分单成功");
			// 生成工单
			generalWorkBill(order);
			return;
		}else{
			// 该逻辑自动分单失败,进行基于省市区寻找区域,匹配区域关键字的第二种逻辑分单
			Set<SubArea> subareas = order.getSendArea().getSubareas();
			for (SubArea subArea : subareas) {
				if(order.getSendAddress().contains(subArea.getKeyWords()) || 
						order.getSendAddress().contains(subArea.getAssistKeyWords())) {
					FixedArea fixedArea = subArea.getFixedArea();
					Courier courier = fixedArea.getCouriers().iterator().next();
					order.setCourier(courier);
					order.setOrderType("1");
					orderRepository.save(order);
					System.out.println("关键字匹配分单成功");
					// 生成工单
					generalWorkBill(order);
					return;
				}else{
					order.setOrderType("2");
					orderRepository.save(order);
				}
			}
		}
		

	}

	private void generalWorkBill(Order order) {
		WorkBill workBill = new WorkBill();
		workBill.setCourier(order.getCourier());
		workBill.setBuildtime(new Date());
		workBill.setOrder(order);
		workBill.setRemark(order.getRemark());
		workBill.setType("新");
		workBill.setPickstate("新单");
		String smsNumber = RandomStringUtils.random(4);
		System.out.println(smsNumber);
		workBill.setSmsNumber(smsNumber);
		workBillRepository.save(workBill);
		System.out.println("工单生成成功");
		
		
	}

}
