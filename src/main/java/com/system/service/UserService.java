package com.system.service;

import com.system.pojo.User;

import java.util.List;

public interface UserService {
	public Integer insertUser(User user);

	public Integer deleteUser(String username);
	
	public User getUserByName(String username);
	
	public List<User> getAllUsers();
	
	public Integer updatePassword(String username, String password);
	
	public Integer updateUser(User user);
}
