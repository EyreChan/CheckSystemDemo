package com.system.pojo;

public class SFormat {
	private String name;

    private String userName;

    private String docType;

    private String styleName;
    
    private String fontASC;
    
    private String fontEAST;
    
    private Integer fontSZ; 
    
    private Integer fontSZCS; 
    
    private String alignment;

    public SFormat(String name, String userName, String docType, String styleName, String fontASC, String fontEAST, Integer fontSZ, Integer fontSZCS, String alignment) {
    	this.name = name;
        this.userName = userName;
        this.docType = docType;
        this.styleName = styleName;
        this.fontASC = fontASC;
        this.fontEAST = fontEAST;
        this.fontSZ = fontSZ;
        this.fontSZCS = fontSZCS;
        this.alignment = alignment;
    }

    public SFormat() {
        super();
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
    
    public String getDocType() {
    	return docType;
    }
    
    public void setDocType(String docType) {
    	this.docType = docType;
    }
    
    public String getStyleName() {
    	return styleName;
    }
    
    public void setStyleName(String styleName) {
    	this.styleName = styleName;
    }
    
    public String getFontASC() {
    	return fontASC;
    }
    
    public void setFontASC(String fontASC) {
    	this.fontASC = fontASC;
    }
    
    public String getFontEAST() {
    	return fontEAST;
    }
    
    public void setFontEAST(String fontEAST) {
    	this.fontEAST = fontEAST;
    }
    
    public Integer getFontSZ() {
    	return fontSZ;
    }
    
    public void setFontSZ(Integer fontSZ) {
    	this.fontSZ = fontSZ;
    }
    
    public Integer getFontSZCS() {
    	return fontSZCS;
    }
    
    public void setFontSZCS(Integer fontSZCS) {
    	this.fontSZCS = fontSZCS;
    }
    
    public String getAlignment() {
    	return alignment;
    }
    
    public void setAlignment(String alignment) {
    	this.alignment = alignment;
    }
}
