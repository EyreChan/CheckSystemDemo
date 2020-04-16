package com.system.service;

import com.system.pojo.Format;

import java.util.List;

public interface FormatService {
	public Integer insertFormat(Format format);

	public Integer deleteFormat(String name, String userName);
	
	public Format getFormatByName(String name, String userName);
	
	public List<Format> getFormatsByUser(String userName);
}
