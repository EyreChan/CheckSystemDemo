package com.system.service;

import java.util.List;

import com.system.pojo.Result;

public interface ResultService {
	public Integer insertResult(Result result);

	public Integer deleteResult(String userName);
	
	public List<Result> getResultsByUser(String userName);
}
