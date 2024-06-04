package com.ag.mportal.model;

import org.jpos.iso.ISOMsg;

public class ISO8583Model {
	private String code;
	private String message;
	private String header;
	private String nacIp;
	private int nacPort;
	private String peckager;
	private ISOMsg isoMessageRequest;
	private ISOMsg isoMessageResponse;
	private boolean isLoggerEnable;
	private String field55Data;
	
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
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getNacIp() {
		return nacIp;
	}
	public void setNacIp(String nacIp) {
		this.nacIp = nacIp;
	}
	public int getNacPort() {
		return nacPort;
	}
	public void setNacPort(int nacPort) {
		this.nacPort = nacPort;
	}
	public String getPeckager() {
		return peckager;
	}
	public void setPeckager(String peckager) {
		this.peckager = peckager;
	}
	public ISOMsg getIsoMessageRequest() {
		return isoMessageRequest;
	}
	public void setIsoMessageRequest(ISOMsg isoMessageRequest) {
		this.isoMessageRequest = isoMessageRequest;
	}
	public ISOMsg getIsoMessageResponse() {
		return isoMessageResponse;
	}
	public void setIsoMessageResponse(ISOMsg isoMessageResponse) {
		this.isoMessageResponse = isoMessageResponse;
	}
	public boolean isLoggerEnable() {
		return isLoggerEnable;
	}
	public void setLoggerEnable(boolean isLoggerEnable) {
		this.isLoggerEnable = isLoggerEnable;
	}
	public String getField55Data() {
		return field55Data;
	}
	public void setField55Data(String field55Data) {
		this.field55Data = field55Data;
	}
	
}
