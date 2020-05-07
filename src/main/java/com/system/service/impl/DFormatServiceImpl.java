package com.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.dao.DFormatDao;
import com.system.pojo.DFormat;
import com.system.service.DFormatService;

@Service
public class DFormatServiceImpl implements DFormatService {
	@Autowired
	private DFormatDao dformatDao;
	
	public Integer insertFormat(DFormat format) {
		return this.dformatDao.insertSelective(format);
	}
	
	public Integer deleteFormat(String name, String userName, String docType) {
		return this.dformatDao.deleteBySelective(name, userName, docType);
	}

	public Integer deleteFormatByUser(String userName) {
		return this.dformatDao.deleteByUser(userName);
	}
	
	public DFormat getFormatByName(String name, String userName, String docType, Integer location) {
		return this.dformatDao.selectOneBySelective(name, userName, docType, location);
	}
	
	public List<DFormat> getFormatByName(String name, String userName, String docType) {
		return this.dformatDao.selectBySelective(name, userName, docType);
	}
}
