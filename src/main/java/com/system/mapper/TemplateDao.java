package com.system.mapper;

import java.util.List;

import com.system.pojo.Template;

public interface TemplateDao {
	int insert(Template template);
    
    int deleteBySelective(String name, String userName);
    int deleteByUser(String userName);

	List<Template> selectTemplatesByUser(String userName);
	Template selectBySelective(String name, String userName);
}
