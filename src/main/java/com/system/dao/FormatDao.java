package com.system.dao;

import java.util.List;

import com.system.pojo.Format;

public interface FormatDao {
	int insert(Format format);

    int insertSelective(Format format);
    
    int deleteBySelective(String name, String userName);

    Format selectBySelective(String name, String userName);
    
    List<Format> selectFormatsByUser(String userName);
}
