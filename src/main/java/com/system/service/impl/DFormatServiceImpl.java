package com.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.mapper.DFormatDao;
import com.system.pojo.DFormat;
import com.system.service.DFormatService;

@Service
public class DFormatServiceImpl implements DFormatService {
	@Autowired
	private DFormatDao dformatDao;
	
	public Integer insertFormat(DFormat format) {
		return this.dformatDao.insertSelective(format);
	}
	
	public Integer deleteFormat(String name, String userName, String docType) {
		return this.dformatDao.deleteBySelective(name, userName, docType);
	}

	public Integer deleteFormatByUser(String userName) {
		return this.dformatDao.deleteByUser(userName);
	}

	public boolean hasSameFormat(String name, String userName, 
			Integer fontSize, String fontColor, String fontType, 
			String indent, String alignment, Integer rowSpacing) {
		List<DFormat> dformat1 = this.dformatDao.selectOne(name, userName, "template", fontSize, fontColor, fontType, indent, alignment, rowSpacing);
		List<DFormat> dformat2 = this.dformatDao.selectOne(name, userName, "template", -1, fontColor, fontType, indent, alignment, rowSpacing);
		List<DFormat> dformat3 = this.dformatDao.selectOne(name, userName, "template", fontSize, fontColor, "无", indent, alignment, rowSpacing);
		List<DFormat> dformat4 = this.dformatDao.selectOne(name, userName, "template", fontSize, fontColor, fontType, indent, alignment, -1);
		List<DFormat> dformat5 = this.dformatDao.selectOne(name, userName, "template", -1, fontColor,  "无", indent, alignment, rowSpacing);
		List<DFormat> dformat6 = this.dformatDao.selectOne(name, userName, "template", fontSize, fontColor, "无", indent, alignment, -1);
		List<DFormat> dformat7 = this.dformatDao.selectOne(name, userName, "template", -1, fontColor, "无", indent, alignment, -1);
		List<DFormat> dformat8 = this.dformatDao.selectOne(name, userName, "template", -1, fontColor, fontType, indent, alignment, -1);
		if(dformat1.size() != 0 || dformat2.size() != 0 || dformat3.size() != 0 || dformat4.size() != 0 || dformat5.size() != 0 || dformat6.size() != 0 || dformat7.size() != 0 || dformat8.size() != 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public DFormat getFormatByName(String name, String userName, String docType, Integer location) {
		return this.dformatDao.selectOneBySelective(name, userName, docType, location);
	}
	
	public List<DFormat> getFormatByName(String name, String userName, String docType) {
		return this.dformatDao.selectBySelective(name, userName, docType);
	}
}
