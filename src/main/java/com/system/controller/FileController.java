package com.system.controller;

import java.util.ArrayList;
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
import com.system.pojo.Result;
import com.system.pojo.SFormat;
import com.system.pojo.Template;
import com.system.service.DFormatService;
import com.system.service.ResultService;
import com.system.service.SFormatService;
import com.system.service.TemplateService;

@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired 
	private TemplateService templateService = null;
	@Autowired 
	private DFormatService dformatService = null;
	@Autowired 
	private SFormatService sformatService = null;
	@Autowired
	private ResultService resultService = null;
	
	@RequestMapping("/file_parserDocx")
	@ResponseBody
	public int file_parserDocx(String path, String name, String type, String tempName, String dump, HttpServletRequest request,Model model) throws Exception {
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
				
				if(type.equals("document")) {
					this.dformatService.deleteFormat(name, userName, "document");
					this.sformatService.deleteFormat(name, userName, "document");
					this.resultService.deleteResult(userName);
				}
				
				res1 = parserDocx1(name, userName, path + "\\" + name, type, req);
				if(res1 == 1) {
					res2 = parserDocx2(name, userName, path + "\\" + name, type, req);
				}
				
				if(res2 == 1) {
					if(type.equals("template")) {
						Template template = new Template(name, userName);
						return this.templateService.insertTemplate(template);
					}
					else if(type.equals("document")){
						int t1 = compareDoc(name, tempName, userName, dump);
						if(t1 == 1) {
							return compareStyle(name, tempName, userName);
						}
						else {
							return 0;
						}
					}
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
	
	private int compareDoc(String name, String tempName, String userName, String dump) {
		DFormat pre = null;
		DFormat later = null;
		for(int i = 1; this.dformatService.getFormatByName(tempName, userName, "template", i) != null; i++) {
			if(i != 1) {
				pre = this.dformatService.getFormatByName(tempName, userName, "template", i - 1);
			}
			if(this.dformatService.getFormatByName(tempName, userName, "template", i + 1) != null) {
				later = this.dformatService.getFormatByName(tempName, userName, "template", i + 1);
			}
			DFormat dformat1 = this.dformatService.getFormatByName(tempName, userName, "template", i);
			DFormat dformat2 = this.dformatService.getFormatByName(name, userName, "document", i);
			if(compareDFormat(dformat1, dformat2, dump, pre, later) != 1) {
				return 0;
			}
		}
		return 1;
	}
	
	boolean isEqual(DFormat dformat1, DFormat dformat2) {
		if(!dformat1.getAlignment().equals(dformat2.getAlignment())) {
			return false;
		}
		else if(!dformat1.getFontColor().equals(dformat2.getFontColor())) {
			return false;
		}
		else if(!dformat1.getFontSize().equals(dformat2.getFontSize()) && !dformat1.getFontSize().equals(-1)) {
			return false;
		}
		else if(!dformat1.getFontType().equals(dformat2.getFontType()) && !dformat1.getFontType().equals("无") && !dformat1.getFontType().equals("EAST_ASIA")) {
			return false;
		}
		else if(!dformat1.getIndent().equals(dformat2.getIndent()) && !dformat1.getIndent().equals("无")) {
			return false;
		}
		else if(!dformat1.getRowSpacing().equals(dformat2.getRowSpacing()) && !dformat1.getRowSpacing().equals(-1)) {
			return false;
		}
		else {
			return true;
		}
	}
	
	private int compareDFormat(DFormat dformat1, DFormat dformat2, String dump, DFormat pre, DFormat later) {
		if(isEqual(dformat1, dformat2)) {
			return 1;
		}
		else if(dump.equals("yes") && this.dformatService.hasSameFormat(dformat2.getName(), dformat2.getUserName(), dformat2.getFontSize(), dformat2.getFontColor(), dformat2.getFontType(), dformat2.getIndent(), dformat2.getAlignment(), dformat2.getRowSpacing())) {
			return 1;
		}
		else {
			if(!dformat1.getAlignment().equals(dformat2.getAlignment())) {
				Result result = new Result(dformat2.getUserName(), "doc", dformat2.getLocation(), dformat2.getContent(), pre.getContent(), later.getContent(), "alignment", dformat2.getAlignment(), dformat1.getAlignment());
				int res = this.resultService.insertResult(result);
				if( res == 0) {
					return 0;
				}
			}
			if(!dformat1.getFontColor().equals(dformat2.getFontColor())) {
				Result result = new Result(dformat2.getUserName(), "doc", dformat2.getLocation(), dformat2.getContent(), pre.getContent(), later.getContent(), "fontColor", dformat2.getFontColor(), dformat1.getFontColor());
				int res = this.resultService.insertResult(result);
				if( res == 0) {
					return 0;
				}
			}
			if(!dformat1.getFontSize().equals(dformat2.getFontSize()) && !dformat1.getFontSize().equals(-1)) {
				Result result = new Result(dformat2.getUserName(), "doc", dformat2.getLocation(), dformat2.getContent(), pre.getContent(), later.getContent(), "fontSize", dformat2.getFontSize().toString(), dformat1.getFontSize().toString());
				int res = this.resultService.insertResult(result);
				if( res == 0) {
					return 0;
				}
			}
			if(!dformat1.getFontType().equals(dformat2.getFontType()) && !dformat1.getFontType().equals("无") && !dformat1.getFontType().equals("EAST_ASIA")) {
				Result result = new Result(dformat2.getUserName(), "doc", dformat2.getLocation(), dformat2.getContent(), pre.getContent(), later.getContent(), "fontType", dformat2.getFontType(), dformat1.getFontType());
				int res = this.resultService.insertResult(result);
				if( res == 0) {
					return 0;
				}
			}
			if(!dformat1.getIndent().equals(dformat2.getIndent())) {
				Result result = new Result(dformat2.getUserName(), "doc", dformat2.getLocation(), dformat2.getContent(), pre.getContent(), later.getContent(), "indent", dformat2.getIndent(), dformat1.getIndent());
				int res = this.resultService.insertResult(result);
				if( res == 0) {
					return 0;
				}
			}
			if(!dformat1.getRowSpacing().equals(dformat2.getRowSpacing()) && !dformat1.getRowSpacing().equals(-1)) {
				Result result = new Result(dformat2.getUserName(), "doc", dformat2.getLocation(), dformat2.getContent(), pre.getContent(), later.getContent(), "rowSpacing", dformat2.getRowSpacing().toString(), dformat2.getRowSpacing().toString());
				int res = this.resultService.insertResult(result);
				if( res == 0) {
					return 0;
				}
			}
			return 1;
		}
	}
	
	private int compareStyle(String name, String tempName, String userName) {
		List<SFormat> sfs = this.sformatService.getFormatByName(tempName, userName, "template");
		for(SFormat sf:sfs) {
			String styleName = sf.getStyleName();
			SFormat sf2 = this.sformatService.getFormatByStyleName(name, userName, styleName);
			if(compareSFormat(sf, sf2) != 1) {
				return 0;
			}
		}
		return 1;
	}
	
	private int compareSFormat(SFormat sformat1, SFormat sformat2) {
		if(!sformat1.getFontSZ().equals(sformat2.getFontSZ()) && !sformat1.getFontSZ().equals(-1)) {
			Result result = new Result(sformat2.getUserName(), "style", -1, sformat2.getStyleName(), "", "", "fontSZ", sformat2.getFontSZ().toString(), sformat1.getFontSZ().toString());
			int res = this.resultService.insertResult(result);
			if( res == 0) {
				return 0;
			}
		}
		if(!sformat1.getFontSZCS().equals(sformat2.getFontSZCS()) && !sformat1.getFontSZCS().equals(-1)) {
			Result result = new Result(sformat2.getUserName(), "style", -1, sformat2.getStyleName(), "", "", "fontSZCS", sformat2.getFontSZCS().toString(), sformat1.getFontSZCS().toString());
			int res = this.resultService.insertResult(result);
			if( res == 0) {
				return 0;
			}
		}
		if(!sformat1.getFontASC().equals(sformat2.getFontASC()) && !sformat1.getFontASC().equals("无")) {
			Result result = new Result(sformat2.getUserName(), "style", -1, sformat2.getStyleName(), "", "", "fontASC", sformat2.getFontASC(), sformat1.getFontASC());
			int res = this.resultService.insertResult(result);
			if( res == 0) {
				return 0;
			}
		}
		if(!sformat1.getFontEAST().equals(sformat2.getFontEAST()) && !sformat1.getFontEAST().equals("无")) {
			Result result = new Result(sformat2.getUserName(), "style", -1, sformat2.getStyleName(), "", "", "fontEAST", sformat2.getFontEAST(), sformat1.getFontEAST());
			int res = this.resultService.insertResult(result);
			if( res == 0) {
				return 0;
			}
		}
		if(!sformat1.getAlignment().equals(sformat2.getAlignment())) {
			Result result = new Result(sformat2.getUserName(), "style", -1, sformat2.getStyleName(), "", "", "alignment", sformat2.getAlignment(), sformat1.getAlignment());
			int res = this.resultService.insertResult(result);
			if( res == 0) {
				return 0;
			}
		}
		return 1;
	}
	
	private int parserDocx1(String name, String userName, String inputfilepath, String type, HttpServletRequest req) throws Exception {
		int res = 0;
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File(inputfilepath));
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		System.out.println(documentPart.getXML());
		org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart
				.getJaxbElement();
		Body body = wmlDocumentEl.getBody();
		List<Object> bodyChildren = body.getContent();//.getEGBlockLevelElts();
		
		res = walkJAXBElements(name, userName, inputfilepath, type, bodyChildren);
		return res;
	}
	
	private int parserDocx2(String name, String userName, String inputfilepath, String type, HttpServletRequest req) throws Exception {
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
					
					SFormat format = new SFormat(name, userName, type, styleName, fontASC, fontEAST, fontSZ, fontSZCS, alignment);
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
	
	private int walkJAXBElements(String name, String userName, String inputpath, String type, List<Object> bodyChildren) {		
		ArrayList<String> lss = new ArrayList<String>();
		int res = 0;
		Integer location = 0;
		for (Object o : bodyChildren) {
			if (o instanceof javax.xml.bind.JAXBElement) {
				System.out.println("JAXBElement:" + o.getClass().getName());
			} 
			else if (o instanceof org.docx4j.wml.P) {
				try {
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
						DFormat format = new DFormat(location, name, userName, type, content, fontType, fontSize, fontColor, indent, alignment, rowSpacing);
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
	private List<DFormat> file_getDocFormat(String name, String docType, HttpServletRequest request,Model model) {
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
		return this.dformatService.getFormatByName(name, userName, docType);
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
		return this.sformatService.getFormatByName(name, userName, "template");
	}
}
