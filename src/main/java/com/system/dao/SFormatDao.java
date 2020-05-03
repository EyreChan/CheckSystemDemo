package com.system.dao;

import java.util.List;

import com.system.pojo.SFormat;

public interface SFormatDao {
	int insert(SFormat sformat);

    int insertSelective(SFormat sformat);
    
    int deleteBySelective(String name, String userName, String docType);
    int deleteByUser(String userName);

    List<SFormat> selectBySelective(String name, String userName);
}
