package com.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.dao.SFormatDao;
import com.system.pojo.SFormat;
import com.system.service.SFormatService;

@Service
public class SFormatServiceImpl implements SFormatService {
	@Autowired
	private SFormatDao sformatDao;
	
	public Integer insertFormat(SFormat format) {
		return this.sformatDao.insertSelective(format);
	}
	
	public Integer deleteFormat(String name, String userName, String docType) {
		return this.sformatDao.deleteBySelective(name, userName, docType);
	}

	public Integer deleteFormatByUser(String userName) {
		return this.sformatDao.deleteByUser(userName);
	}
	
	public List<SFormat> getFormatByName(String name, String userName) {
		return this.sformatDao.selectBySelective(name, userName);
	}
}
