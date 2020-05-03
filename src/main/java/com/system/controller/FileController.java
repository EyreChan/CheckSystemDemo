package com.system.controller;

import java.util.*;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBElement;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Ind;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Style;
import org.docx4j.wml.Styles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.pojo.DFormat;
import com.system.pojo.SFormat;
import com.system.pojo.Template;
import com.system.service.DFormatService;
import com.system.service.SFormatService;
import com.system.service.TemplateService;
import com.aspose.words.Document;

@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired 
	private TemplateService templateService = null;
	@Autowired 
	private DFormatService dformatService = null;
	@Autowired 
	private SFormatService sformatService = null;
	
	@RequestMapping("/file_parserDocx")
	@ResponseBody
	public int file_parserDocx(String path, String name, HttpServletRequest request,Model model) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		
		int len = name.length();
		if(name.substring(len-3, len).equals("doc")) {
			System.out.println("请先将doc格式文件转换成docx格式文件");
		}
		else if(!name.substring(len-4, len).equals("docx")) {
			System.out.println("请输入docx格式文件");
		}
		else {
			int res1 = 0, res2 = 0;
			try {
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
				
				res1 = parserDocx1(name, userName, path + "\\" + name, req);
				if(res1 == 1) {
					res2 = parserDocx2(name, userName, path + "\\" + name, req);
				}
				
				if(res2 == 1) {
					return this.templateService.insertTemplate(template);
				}
				else {
					return 0;
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res2;
		}
		return 0;
	}
	
	private int parserDocx1(String name, String userName, String inputfilepath, HttpServletRequest req) throws Exception {
		int res = 0;
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File(inputfilepath));
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		System.out.println(documentPart.getXML());
		org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart
				.getJaxbElement();
		Body body = wmlDocumentEl.getBody();
		List<Object> bodyChildren = body.getContent();//.getEGBlockLevelElts();
		
		res = walkJAXBElements(name, userName, inputfilepath, bodyChildren);
		return res;
	}
	
	private int parserDocx2(String name, String userName, String inputfilepath, HttpServletRequest req) throws Exception {
		int res = 0;
		
		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
					.load(new java.io.File(inputfilepath));
			StyleDefinitionsPart styleDefinitionsPart =
					wordMLPackage.getMainDocumentPart().getStyleDefinitionsPart(true);
			Styles styles = styleDefinitionsPart.getContents();
			System.out.println(styleDefinitionsPart.getXML());
			List<Style> stylesList = styles.getStyle();
			for (Style style : stylesList) {
				try{
					System.out.println("************************");
					System.out.println("name: " + style.getName().getVal());
					System.out.println("************************");
					
					String docType = "template";
					String styleName = style.getName().getVal();
					String fontASC = "无";
					String fontEAST = "无";
					Integer fontSZ = -1;
					Integer fontSZCS = -1;
					String alignment = "无";
					
					if(style.getPPr() != null) {
						PPr ppr = style.getPPr();
						if(ppr.getJc() != null) {
							alignment = ppr.getJc().getVal().toString();
						}
					}
					if(style.getRPr() != null) {
						RPr rpr = style.getRPr();
						if(rpr.getRFonts() != null) {
							RFonts rfs = style.getRPr().getRFonts();
							if(rfs.getAscii() != null) {
								fontASC = rfs.getAscii();
							}
							if(rfs.getEastAsia() != null) {
								fontEAST = rfs.getEastAsia();
							}
						}
						if(rpr.getSz() != null) {
							fontSZ = rpr.getSz().getVal().intValue();
						}
						if(rpr.getSzCs() != null) {
							fontSZCS = rpr.getSzCs().getVal().intValue();
						}
					}
					
					SFormat format = new SFormat(name, userName, docType, styleName, fontASC, fontEAST, fontSZ, fontSZCS, alignment);
					res = this.sformatService.insertFormat(format);
					if(res == 0) {
						return 0;
					}
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	private int walkJAXBElements(String name, String userName, String inputpath, List<Object> bodyChildren) {		
		ArrayList<String> lss = new ArrayList<String>();
		int res = 0;
		Integer location = 0;
		for (Object o : bodyChildren) {
			if (o instanceof javax.xml.bind.JAXBElement) {
				System.out.println("JAXBElement:" + o.getClass().getName());
			} 
			else if (o instanceof org.docx4j.wml.P) {
				try {
					String docType = "template";
					String content = "无";
					String fontType = "无";
					Integer fontSize = -1;
					String fontColor = "黑色";
					String indent = "无";
					String alignment = "无";
					Integer rowSpacing = -1;
					//文本内容
					content = walkList(((org.docx4j.wml.P) o).getContent());
					if(content != null) {
						//文本位置
						location++;
						System.out.println("=====================");
						System.out.println("------------段落内容------------");
						System.out.println(content);
						System.out.println("------------段落内容结束-----------");
						System.out.println("=====================");
						lss.add(content);
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
						DFormat format = new DFormat(location, name, userName, docType, content, fontType, fontSize, fontColor, indent, alignment, rowSpacing);
						res = this.dformatService.insertFormat(format);
						if(res == 0) {
							return 0;
						}
					}
				}
				catch(Exception e){
					System.out.println(e.getMessage());
					continue;
				}
			}
		}
		return 1;
	}
	
	/*
	private boolean fullOfSpace(String content) {
		for(int i = 0; i < content.length(); i++) {
			if(content.charAt(i) != ' ') {
				return false;
			}
		}
		return true;
	}
	*/
	
	private String walkList(List children) {
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
	
	@RequestMapping("/file_getDocFormat")
	@ResponseBody
	private List<DFormat> file_getDocFormat(String name, HttpServletRequest request,Model model) {
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
		//System.out.println(name);
		//System.out.println(userName);
		return this.dformatService.getFormatByName(name, userName);
	}
	
	@RequestMapping("/file_getStyleFormat")
	@ResponseBody
	private List<SFormat> file_getStyleFormat(String name, HttpServletRequest request,Model model) {
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
		//System.out.println(name);
		//System.out.println(userName);
		return this.sformatService.getFormatByName(name, userName);
	}
}
