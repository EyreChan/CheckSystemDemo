package com.system.pojo;

public class Format {
	private String name;

    private String userName;

    private String type;

    private Integer location;

    private String content;
    
    private Integer fontSize; 
    
    private String fontColor;
    
    private String indent;
    
    private String alignment;

    public Format(String name, String userName, String type, Integer location, String content, Integer fontSize, String fontColor, String indent, String alignment) {
    	this.name = name;
        this.userName = userName;
        this.type = type;
        this.location = location;
        this.content = content;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.indent = indent;
        this.alignment = alignment;
    }

    public Format() {
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
    
    public String getType() {
    	return type;
    }
    
    public void setType(String type) {
    	this.type = type;
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
        this.content = content == null ? null : content.trim();
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
}
