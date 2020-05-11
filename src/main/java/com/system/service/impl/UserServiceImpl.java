package com.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.mapper.UserDao;
import com.system.pojo.User;
import com.system.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	public Integer insertUser(User user) {
		return this.userDao.insertSelective(user);
	}
	
	public Integer deleteUser(String username) {
		return this.userDao.deleteByPrimaryKey(username);
	}
	
	public User getUserByName(String username) {
		return this.userDao.selectByPrimaryKey(username);
	}
	
	public List<User> getAllUsers() {
		return this.userDao.selectAllUsers();
	}

	public Integer updatePassword(String username, String password) {
		User user = new User();
		user.setName(username);
		user.setPassword(password);
		return this.userDao.updateSelectiveByPrimaryKey(user);
	}
	
	public Integer updateUser(User user) {
		return this.userDao.updateSelectiveByPrimaryKey(user);
	}
}
