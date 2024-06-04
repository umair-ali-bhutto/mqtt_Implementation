package com.ag.db.proc;

import java.util.HashMap;

public class FuelProcModelDetails {
	
	
	private String code;
	private String message;
	private HashMap<Object, Object> data;
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
	public HashMap<Object, Object> getData() {
		return data;
	}
	public void setData(HashMap<Object, Object> data) {
		this.data = data;
	}
}
