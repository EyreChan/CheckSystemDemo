package com.system.service;

import com.system.pojo.User;

import java.util.List;

public interface UserService {
	public Integer insertUser(User user);

	public Integer deleteUser(String userName);
	
	public User getUserByName(String userName);
	
	public List<User> getAllUsers();
	
	public Integer updateUser(User user);
}
