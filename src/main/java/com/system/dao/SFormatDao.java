package com.system.dao;

import java.util.List;

import com.system.pojo.SFormat;

public interface SFormatDao {
    int insertSelective(SFormat sformat);
    
    int deleteBySelective(String name, String userName, String docType);
    int deleteByUser(String userName);

    SFormat selectByStyleName(String name, String userName, String docType, String styleName);
    List<SFormat> selectBySelective(String name, String userName, String docType);
}
