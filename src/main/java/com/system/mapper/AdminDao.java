package com.system.mapper;

import com.system.pojo.Admin;

public interface AdminDao {
    Admin selectByPrimaryKey(String name);
}