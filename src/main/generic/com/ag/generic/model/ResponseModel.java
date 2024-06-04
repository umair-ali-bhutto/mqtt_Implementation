package com.ag.generic.model;

import java.util.Map;

public class ResponseModel {

	private String code;
	private String message;
	private Map<Object, Object> data;
	
	public ResponseModel() {
		
	}
	public ResponseModel(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<Object, Object> getData() {
		return data;
	}
	public void setData(Map<Object, Object> data) {
		this.data = data;
	}
	
}
