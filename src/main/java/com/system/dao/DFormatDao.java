package com.system.dao;

import java.util.List;

import com.system.pojo.DFormat;

public interface DFormatDao {
    int insertSelective(DFormat dformat);
    
    int deleteBySelective(String name, String userName, String type);
    int deleteByUser(String userName);

    DFormat selectOneBySelective(String name, String userName, String docType, Integer location);
    List<DFormat> selectBySelective(String name, String userName, String docType);
}
