package com.system.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.JAXBElement;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Ind;
import org.docx4j.wml.PPrBase.Spacing;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/file")
public class FileController {
	@RequestMapping("/file_parserDocx")
	@ResponseBody
	public void file_parserDocx(String path, String name, HttpServletRequest request,Model model) {
		try {
			//parserDocx(path + "\\" + name);
			//System.out.println(path);
			//System.out.println(path + "\\" + name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> parserDocx(String inputfilepath) throws Exception {
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File(inputfilepath));
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		//System.out.println(documentPart.getXML());
		org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart
				.getJaxbElement();
		Body body = wmlDocumentEl.getBody();
		List<Object> bodyChildren = body.getContent();//.getEGBlockLevelElts();
		ArrayList<String> lss = walkJAXBElements(inputfilepath, bodyChildren);
		return lss;
	}
	
	public ArrayList<String> walkJAXBElements(String inputpath,
			List<Object> bodyChildren) {
		ArrayList<String> lss = new ArrayList<String>();
		for (Object o : bodyChildren) {
			if (o instanceof javax.xml.bind.JAXBElement) {
				System.out.println("JAXBElement:" + o.getClass().getName());
			} else if (o instanceof org.docx4j.wml.P) {
				try{
					System.out.println("=====================");
					String paragraph = walkList(((org.docx4j.wml.P) o).getContent());
					System.out.println("------------段落内容------------");
					//System.out.println(paragraph);
					System.out.println("------------段落内容结束-----------");
					lss.add(paragraph);
					System.out.println("------------段落样式------------");
					PPr ppr = ((org.docx4j.wml.P) o).getPPr();
					if(ppr!=null){
						ParaRPr prpr=ppr.getRPr();
						RFonts rfs = prpr.getRFonts();
						HpsMeasure hps = prpr.getSz();
						System.out.println("字体Ascii："+rfs.getAscii());
						System.out.println("字体HAnsi："+rfs.getHAnsi());
						System.out.println("字体大小："+hps.getVal());
						System.out.println("字体颜色："+prpr.getColor().getVal());
						Ind ind=ppr.getInd();
						System.out.println("左缩进："+ind.getLeftChars());
						Spacing sp=ppr.getSpacing();
						System.out.println("行距："+sp.getLine());
					}
					System.out.println("---------样式结束--------------");
					System.out.println("=====================");
				}catch(Exception e){
					System.out.println(e.getMessage());
					continue;
				}
			}
		}
		return lss;
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
}
