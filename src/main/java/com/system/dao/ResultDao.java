package com.system.dao;

import java.util.List;

import com.system.pojo.Result;

public interface ResultDao {
	int insertSelective(Result result);
    
    int deleteByUser(String userName);

	List<Result> selectResultsByUser(String userName);
}
