package com.system.dao;

import java.util.List;

import com.system.pojo.Template;

public interface TemplateDao {
	int insert(Template template);
    
    int deleteBySelective(String name, String userName);

	List<Template> selectTemplatesByUser(String userName);
	Template selectBySelective(String name, String userName);
}
