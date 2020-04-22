package com.system.service;

import java.util.List;

import com.system.pojo.Template;

public interface TemplateService {
	public Integer insertTemplate(Template template);

	public Integer deleteTemplate(String name, String userName);
	
	public List<Template> getTemplatesByUser(String userName);
	public Template getTemplateByName(String name, String userName);
}
