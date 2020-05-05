package com.system.pojo;

public class Result {
    private String userName;
    
    private Integer errLocation;

    private String context;

    private String errType;

    private String errContext;

    private String rightContext;
    
    public Result(String userName, Integer errLocation, String context, String errType, String errContext, String rightContext) {
        this.userName = userName;
        this.errLocation = errLocation;
        this.context = context;
        this.errType = errType;
        this.errContext = errContext;
        this.rightContext = rightContext;
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
    
    public String getErrContext() {
    	return errContext;
    }
    
    public void setErrContext(String errContext) {
    	this.errContext = errContext;
    }
    
    public String getRightContext() {
    	return rightContext;
    }
    
    public void setRightContext(String rightContext) {
    	this.rightContext = rightContext;
    }
}