package com.system.pojo;

public class DFormat {
    private Integer location;
    
	private String name;

    private String userName;

    private String docType;

    private String content;
    
    private String fontType;
    
    private Integer fontSize; 
    
    private String fontColor;
    
    private String indent;
    
    private String alignment;
    
    private Integer rowSpacing;

    public DFormat(Integer location, String name, String userName, String docType, String content, String fontType, Integer fontSize, String fontColor, String indent, String alignment, Integer rowSpacing) {
        this.location = location;
        this.name = name;
        this.userName = userName;
        this.docType = docType;
        this.content = content;
        this.fontType = fontType;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.indent = indent;
        this.alignment = alignment;
        this.rowSpacing = rowSpacing;
    }

    public DFormat() {
        super();
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getDocType() {
    	return docType;
    }
    
    public void setDocType(String docType) {
    	this.docType = docType;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getFontType() {
    	return fontType;
    }
    
    public void setFontType(String fontType) {
    	this.fontType = fontType;
    }
    
    public Integer getFontSize() {
    	return fontSize;
    }
    
    public void setFontSize(Integer fontSize) {
    	this.fontSize = fontSize;
    }
    
    public String getFontColor() {
    	return fontColor;
    }
    
    public void setFontColor(String fontColor) {
    	this.fontColor = fontColor;
    }
    
    public String getIndent() {
    	return indent;
    }
    
    public void setIndent(String indent) {
    	this.indent = indent;
    }
    
    public String getAlignment() {
    	return alignment;
    }
    
    public void setAlignment(String alignment) {
    	this.alignment = alignment;
    }
    
    public Integer getRowSpacing() {
    	return rowSpacing;
    }
    
    public void setRowSpacing(Integer rowSpacing) {
    	this.rowSpacing = rowSpacing;
    }
}
