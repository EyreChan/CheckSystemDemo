package com.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.mapper.AdminDao;
import com.system.pojo.Admin;
import com.system.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDao adminDao;
	
	public Admin getAdminByName(String adminname) {
		return this.adminDao.selectByPrimaryKey(adminname);
	}
}
