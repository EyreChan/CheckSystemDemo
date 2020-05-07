package com.system.pojo;

public class Result {
    private String userName;
    
    private String formatType;
    
    private Integer errLocation;

    private String context;

    private String errType;

    private String errContent;

    private String rightContent;
    
    public Result(String userName, String formatType, Integer errLocation, String context, String errType, String errContent, String rightContent) {
        this.userName = userName;
        this.formatType = formatType;
        this.errLocation = errLocation;
        this.context = context;
        this.errType = errType;
        this.errContent = errContent;
        this.rightContent = rightContent;
    }   

    public Result() {
        super();
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getFormatType() {
    	return formatType;
    }
    
    public void setFormatType(String formatType) {
    	this.formatType = formatType;
    }

    public Integer getErrLocation() {
        return errLocation;
    }

    public void setErrLocation(Integer errLocation) {
        this.errLocation = errLocation;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getErrType() {
        return errType;
    }

    public void setErrType(String errType) {
        this.errType = errType;
    }
    
    public String getErrContent() {
    	return errContent;
    }
    
    public void setErrContent(String errContent) {
    	this.errContent = errContent;
    }
    
    public String getRightContent() {
    	return rightContent;
    }
    
    public void setRightContent(String rightContent) {
    	this.rightContent = rightContent;
    }
}