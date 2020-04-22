package com.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.dao.FormatDao;
import com.system.pojo.Format;
import com.system.service.FormatService;

@Service
public class FormatServiceImpl implements FormatService {
	@Autowired
	private FormatDao formatDao;
	
	public Integer insertFormat(Format format) {
		return this.formatDao.insertSelective(format);
	}
	
	public Integer deleteFormat(String name, String userName, String type) {
		return this.formatDao.deleteBySelective(name, userName, type);
	}
	
	public Format getFormatByName(String name, String userName) {
		return this.formatDao.selectBySelective(name, userName);
	}
	
	public List<Format> getFormatsByUser(String userName) {
		return this.formatDao.selectFormatsByUser(userName);
	}
}
