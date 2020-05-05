package com.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.dao.ResultDao;
import com.system.pojo.Result;
import com.system.service.ResultService;

@Service
public class ResultServiceImpl implements ResultService {
	@Autowired
	private ResultDao resultDao;
	
	public Integer insertResult(Result result) {
		return this.resultDao.insertSelective(result);
	}
	
	public Integer deleteResult(String userName) {
		return this.resultDao.deleteByUser(userName);
	}

	public List<Result> getResultsByUser(String userName) {
		return this.resultDao.selectResultsByUser(userName);
	}
}
