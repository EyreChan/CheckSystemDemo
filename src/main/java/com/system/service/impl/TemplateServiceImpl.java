package com.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.pojo.Template;
import com.system.dao.TemplateDao;
import com.system.service.TemplateService;

@Service
public class TemplateServiceImpl implements TemplateService {
	@Autowired
	private TemplateDao templateDao;

	public Integer insertTemplate(Template template) {
		return this.templateDao.insert(template);
	}

	public Integer deleteTemplate(String name, String userName) {
		return this.templateDao.deleteBySelective(name, userName);
	}
	
	public Integer deleteTemplateByUser(String userName) {
		return this.templateDao.deleteByUser(userName);
	}
	
	public List<Template> getTemplatesByUser(String userName) {
		return this.templateDao.selectTemplatesByUser(userName);
	}
	
	public Template getTemplateByName(String name, String userName) {
		return this.templateDao.selectBySelective(name, userName);
	}
}
