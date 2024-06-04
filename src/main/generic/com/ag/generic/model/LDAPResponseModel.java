package com.ag.generic.model;


public class LDAPResponseModel {
	
	private String code;
	private String message;
	private LDAPUserModel usm;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public LDAPUserModel getUsm() {
		return usm;
	}
	public void setUsm(LDAPUserModel usm) {
		this.usm = usm;
	}

}
