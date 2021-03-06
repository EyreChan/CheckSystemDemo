package com.system.mapper;

import java.util.List;

import com.system.pojo.User;

public interface UserDao {
    int insertSelective(User user);
    
    int deleteByPrimaryKey(String name);
    User selectByPrimaryKey(String name);
    
    List<User> selectAllUsers();

    int updateSelectiveByPrimaryKey(User user);
}