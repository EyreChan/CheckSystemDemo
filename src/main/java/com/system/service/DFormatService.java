package com.system.service;

import com.system.pojo.DFormat;

import java.util.List;

public interface DFormatService {
	public Integer insertFormat(DFormat format);

	public Integer deleteFormat(String name, String userName, String docType);
	public Integer deleteFormatByUser(String userName);
	
	public boolean hasSameFormat(String name, String userName, Integer fontSize, String fontColor, String fontType, String indent, String alignment, Integer rowSpacing);
	public DFormat getFormatByName(String name, String userName, String docType, Integer location);
	public List<DFormat> getFormatByName(String name, String userName, String docType);
}
