package thesis;

import org.apache.log4j.Logger;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Ind;
import org.docx4j.wml.PPrBase.Spacing;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Style;
import org.docx4j.wml.Styles;

public class MyTest {
	private static final Logger logger = Logger.getLogger(MyTest.class);
	
	@Test
	public void t1() {
		try {
			parserDocx("d:\\easy.docx");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> parserDocx(String inputfilepath) throws Exception {
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File(inputfilepath));
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		System.out.println(documentPart.getXML());
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
					System.out.println(paragraph);
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

	@Test
	public void t2() {
		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
					.load(new java.io.File("d:\\easy.docx"));
			StyleDefinitionsPart styleDefinitionsPart =
					wordMLPackage.getMainDocumentPart().getStyleDefinitionsPart(true);
			Styles styles = styleDefinitionsPart.getContents();
			System.out.println(styleDefinitionsPart.getXML());
			try {
				String defaultParagraphStyleId = styleDefinitionsPart.getDefaultParagraphStyle().getStyleId();
				System.out.println("defaultParagraphStyleId:"+defaultParagraphStyleId);
			} catch (NullPointerException npe) {
				System.out.println("No default paragraph style!!");
			}
			try {
				String defaultCharacterStyleId = styleDefinitionsPart.getDefaultCharacterStyle().getStyleId();
				System.out.println("defaultCharacterStyleId:"+defaultCharacterStyleId);
			} catch (NullPointerException npe) {
				System.out.println("No default character style!!");
			}
			List<Style> stylesList = styles.getStyle();
			for (Style style : stylesList) {
				System.out.println("************************");
				try{
					System.out.println(style.getName().getVal());
					System.out.println(style.getStyleId());
					RFonts rfs = style.getRPr().getRFonts();
					HpsMeasure hps = style.getRPr().getSz();
					System.out.println("字体Ascii："+rfs.getAscii());
					System.out.println("字体HAnsi："+rfs.getHAnsi());
					System.out.println("字体大小："+hps.getVal());
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				System.out.println("************************");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

