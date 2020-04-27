package com.system.controller;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBElement;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Ind;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.pojo.Format;
import com.system.pojo.Template;
import com.system.service.FormatService;
import com.system.service.TemplateService;

@Controller
@RequestMapping("/file")
public class FileController {
	private Integer location = 0;
	
	@Autowired 
	private TemplateService templateService = null;
	@Autowired 
	private FormatService formatService = null;
	
	@RequestMapping("/file_parserDocx")
	@ResponseBody
	public int file_parserDocx(String path, String name, HttpServletRequest request,Model model) {
		HttpServletRequest req = (HttpServletRequest) request;
		int res = 0;
		try {
			res = parserDocx(name, path + "\\" + name, req);
			//System.out.println(path);
			//System.out.println(path + "\\" + name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public int parserDocx(String name, String inputfilepath, HttpServletRequest req) throws Exception {
		int res = 0, res1 = 0, res2 = 0;
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File(inputfilepath));
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		System.out.println(documentPart.getXML());
		org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart
				.getJaxbElement();
		Body body = wmlDocumentEl.getBody();
		List<Object> bodyChildren = body.getContent();//.getEGBlockLevelElts();
		
		String userName = null;
		
		Cookie[] cookies = req.getCookies();
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().toString().equals("loginName")) {
					userName = cookie.getValue();
				}
			}
		}
		Template template = new Template(name, userName);
		res1 = this.templateService.insertTemplate(template);
		res2 = walkJAXBElements(name, userName, inputfilepath, bodyChildren);
		res = res1 & res2;
		return res;
		//return null;
	}
	
	public int walkJAXBElements(String name, String userName, String inputpath, List<Object> bodyChildren) {		
		ArrayList<String> lss = new ArrayList<String>();
		int res = 0;
		for (Object o : bodyChildren) {
			if (o instanceof javax.xml.bind.JAXBElement) {
				System.out.println("JAXBElement:" + o.getClass().getName());
			} 
			else if (o instanceof org.docx4j.wml.P) {
				try {
					String type = "template";
					String content = "无";
					String fontType = "无";
					Integer fontSize = -1;
					String fontColor = "无";
					String indent = "无";
					String alignment = "无";
					Integer rowSpacing = -1;
					//文本内容
					content = walkList(((org.docx4j.wml.P) o).getContent());
					if(content != null && content != "") {
						//文本位置
						location++;
						System.out.println("=====================");
						System.out.println("------------段落内容------------");
						System.out.println(content);
						System.out.println("------------段落内容结束-----------");
						lss.add(content);
						System.out.println("------------段落样式------------");
						PPr ppr = ((org.docx4j.wml.P) o).getPPr();
						if(ppr != null) {
							//对齐方式
							if(ppr.getJc() != null) {
								alignment = ppr.getJc().getVal().toString();
							}
							//缩进
							if(ppr.getInd() != null) {
								Ind ind = ppr.getInd();
								if(ind.getFirstLine() != null) {
									indent = ind.getFirstLine().toString();
								}
							}
							if(ppr.getRPr() != null) {
								ParaRPr prpr=ppr.getRPr();
								//字体类型
								if(prpr.getRFonts() != null) {
									RFonts rfs = prpr.getRFonts();
									if(rfs.getAscii() != null) {
										fontType = rfs.getAscii();
									}
									else if(rfs.getHAnsi() != null) {
										fontType = rfs.getHAnsi();
									}
									else if(rfs.getEastAsia() != null) {
										fontType = rfs.getEastAsia();
									}
									else if(rfs.getHint() != null) {
										fontType = rfs.getHint().toString();
									}
								}
								//字体大小
								if(prpr.getSz() != null) {
									HpsMeasure hps = prpr.getSz();
									fontSize = hps.getVal().intValue();
								}
								//字体颜色
								if(prpr.getColor() != null) {
									fontColor = prpr.getColor().getVal();
								}
							}
							if(ppr.getSpacing() != null && ppr.getSpacing().getLine() != null) {
								rowSpacing = ppr.getSpacing().getLine().intValue();
							}
						}
						System.out.println("---------样式结束--------------");
						System.out.println("=====================");
						Format format = new Format(location, name, userName, type, content, fontSize, fontColor, indent, alignment, rowSpacing);
						res = this.formatService.insertFormat(format);
					}
				}
				catch(Exception e){
					System.out.println(e.getMessage());
					continue;
				}
			}
		}
		return res;
	}
	
	public String walkList(List children) {
		String line = "";
		for (Object o : children) {
			if (o instanceof javax.xml.bind.JAXBElement) {
				if (((JAXBElement) o).getDeclaredType().getName()
						.equals("org.docx4j.wml.Text")) {
					org.docx4j.wml.Text t = (org.docx4j.wml.Text) ((JAXBElement) o)
							.getValue();
					line = line + t.getValue();
				} else if (((JAXBElement) o).getDeclaredType().getName()
						.equals("org.docx4j.wml.Drawing")) {
					System.out.println("find img");
					// ((JAXBElement)o).getValue() );
				}
			} else if (o instanceof org.w3c.dom.Node) {
				System.out.println(" IGNORED "
						+ ((org.w3c.dom.Node) o).getNodeName());
			} else if (o instanceof org.docx4j.wml.R) {
				RPr rPr = ((R) o).getRPr();
				// System.out.println("=========字体样式============");
				// if(rPr!=null){
				//
				// RFonts rfs = rPr.getRFonts();
				// HpsMeasure hps = rPr.getSz();
				// System.out.println("字体Ascii："+rfs.getAscii());
				// System.out.println("字体HAnsi："+rfs.getHAnsi());
				// System.out.println("字体大小："+hps.getVal());
				// System.out.println("字体颜色："+rPr.getColor().getVal());
				//
				// }
				// System.out.println("=====================");
				org.docx4j.wml.U u = rPr.getU();
				org.docx4j.wml.R run = (org.docx4j.wml.R) o;
				String tmpStr = walkList(run.getRunContent());
				if (u != null) {
					for (int i = 0; i < tmpStr.length(); i++) {
						line = line + "_" + tmpStr.charAt(i);
					}
				} else
					line = line + tmpStr;
			}
			else {
				System.out.println(" IGNORED " + o.getClass().getName());
			}
		}
		return line;
	}
	
	@RequestMapping("/file_getFormat")
	@ResponseBody
	public List<Format> file_getFormat(String name, HttpServletRequest request,Model model) {
		HttpServletRequest req = (HttpServletRequest) request;
		
		String userName = null;
		Cookie[] cookies = req.getCookies();
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().toString().equals("loginName")) {
					userName = cookie.getValue();
				}
			}
		}
		System.out.println(name);
		System.out.println(userName);
		return this.formatService.getFormatByName(name, userName);
	}
}
