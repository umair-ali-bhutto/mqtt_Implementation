package com.ag.mportal.model;

import org.json.simple.JSONArray;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PosResponseOpModel {
	
	
	private String code;
	private String message;
	private JSONObject data;
	private JSONArray dataValue;
	
	
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
	public JSONObject getData() {
		return data;
	}
	public void setData(JSONObject data) {
		this.data = data;
	}
	public JSONArray getDataValue() {
		return dataValue;
	}
	public void setDataValue(JSONArray dataValue) {
		this.dataValue = dataValue;
	}
	
	
	
	

}
