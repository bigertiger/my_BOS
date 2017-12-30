package com.zy.rs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.rs.dao.CustomerRepository;
import com.zy.rs.service.CustomerService;

import cn.itcast.crm.domain.Customer;

/** 
* @author  philchang 
* @date 2017年12月7日 下午6:02:54 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> findCustomerNull() {
		
		return customerRepository.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findCustomerByFid(String fid) {
		
		return customerRepository.findByFixedAreaId(fid);
	}

	@Override
	public void customerToFixedArea(String cidStr, String fid) {
		
		List<Integer> ids = new ArrayList<>();
		String[] split = cidStr.split(",");
		for (String string : split) {
			try{
				Integer id = Integer.parseInt(string);
				ids.add(id);
			}catch(Exception e){
				customerRepository.clearFixedAreaId(fid);
				return;
			}
		}
		
		customerRepository.clearFixedAreaId(fid);
		customerRepository.updateFixedAreaId(fid, ids);

	}

	@Override
	public void saveCustomer(Customer customer) {
		customerRepository.save(customer);
		
	}

	@Override
	public Customer findByTelephone(String telephone) {
		
		return customerRepository.findByTelephone(telephone);
	}

	@Override
	public void updateType(String telephone) {
		Customer customer = customerRepository.findByTelephone(telephone);
		customer.setType(1);
		
	}

	@Override
	public Customer login(String telephone, String password) {
		
		return customerRepository.findByTelephoneAndPassword(telephone, password);
	}

	@Override
	public Customer findCustomerByAddress(String address) {
		
		return customerRepository.findByAddress(address);
	}

}
