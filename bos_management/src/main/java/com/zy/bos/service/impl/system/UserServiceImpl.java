package com.zy.bos.service.impl.system;

import com.zy.bos.domain.system.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.bos.dao.base.system.UserRepository;
import com.zy.bos.domain.system.User;
import com.zy.bos.service.system.UserService;

import java.util.List;

/** 
* @author  philchang 
* @date 2017年12月25日 下午4:49:46 
* @version 1.0.0
*   
*/
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

}
