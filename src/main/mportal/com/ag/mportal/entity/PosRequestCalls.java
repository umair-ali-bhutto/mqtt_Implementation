package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the BATCH_SETTLEMENT database table.
 * 
 */
@Entity
@Table(name = "POS_REQUEST_CALLS")
@NamedQuery(name = "PosRequestCalls.findByMessageType", query = "SELECT c FROM PosRequestCalls c WHERE c.isActive=1 and c.messageType=:messageType")
public class PosRequestCalls implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name = "MESSAGE_TYPE")
	private String messageType;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "FUNCTION_TYPE")
	private String functionType;

	@Column(name = "AUTH_ENABLE")
	private String authEnable;

	@Column(name = "AUTH_ID_PARAM")
	private String authIdParam;

	@Column(name = "AUTH_PASS_PARAM")
	private String authPassParam;

	@Column(name = "FUNCTION_NAME")
	private String funcationName;

	@Column(name = "FUNCTION_CLASS")
	private String funcationClass;

	@Column(name = "FUNCATION_GROOVY")
	private String funcationGroovy;

	@Column(name = "FUNCTION_IP_PARAMS")
	private String functionIpParams;

	@Column(name = "FUNCATION_OP_PARAMS")
	private String functionOpParams;
	
	
	@Column(name = "FUNCTION_SUCCESS_CODE")
	private String functionSuccessCode;


	@Column(name = "IS_ACTIVE")
	private int isActive;

//	@Column(name = "ENTRY_DATE")
//	private Timestamp entryDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public String getAuthEnable() {
		return authEnable;
	}

	public void setAuthEnable(String authEnable) {
		this.authEnable = authEnable;
	}

	public String getAuthIdParam() {
		return authIdParam;
	}

	public void setAuthIdParam(String authIdParam) {
		this.authIdParam = authIdParam;
	}

	public String getAuthPassParam() {
		return authPassParam;
	}

	public void setAuthPassParam(String authPassParam) {
		this.authPassParam = authPassParam;
	}

	public String getFuncationName() {
		return funcationName;
	}

	public void setFuncationName(String funcationName) {
		this.funcationName = funcationName;
	}

	public String getFuncationClass() {
		return funcationClass;
	}

	public void setFuncationClass(String funcationClass) {
		this.funcationClass = funcationClass;
	}

	public String getFuncationGroovy() {
		return funcationGroovy;
	}

	public void setFuncationGroovy(String funcationGroovy) {
		this.funcationGroovy = funcationGroovy;
	}

	public String getFunctionIpParams() {
		return functionIpParams;
	}

	public void setFunctionIpParams(String functionIpParams) {
		this.functionIpParams = functionIpParams;
	}

	public String getFunctionOpParams() {
		return functionOpParams;
	}

	public void setFunctionOpParams(String functionOpParams) {
		this.functionOpParams = functionOpParams;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

//	public Timestamp getEntryDate() {
//		return entryDate;
//	}
//
//	public void setEntryDate(Timestamp entryDate) {
//		this.entryDate = entryDate;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFunctionSuccessCode() {
		return functionSuccessCode;
	}

	public void setFunctionSuccessCode(String functionSuccessCode) {
		this.functionSuccessCode = functionSuccessCode;
	}

}