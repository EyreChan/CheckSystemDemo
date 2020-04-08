package com.system.dao;

import com.system.pojo.Admin;

public interface AdminDao {
    Admin selectByPrimaryKey(String name);
}