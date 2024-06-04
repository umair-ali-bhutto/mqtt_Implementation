package com.ag.generic.model;

import java.util.Map;

public class RequestModel {
	
	private String message;
	private String imei;
	private String token;
	private String lang;
	private String userid;
	private String corpId;
	private String channel;
	private String password;
	private Map<Object, Object> additionalData;
	
	
	
	
	

	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Map<Object, Object> getAdditionalData() {
		return additionalData;
	}
	public void setAdditionalData(Map<Object, Object> additionalData) {
		this.additionalData = additionalData;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	
	
}
