package com.system.service;

import com.system.pojo.SFormat;

import java.util.List;

public interface SFormatService {
	public Integer insertFormat(SFormat format);

	public Integer deleteFormat(String name, String userName, String docType);
	public Integer deleteFormatByUser(String userName);
	
	public List<SFormat> getFormatByName(String name, String userName);
}
