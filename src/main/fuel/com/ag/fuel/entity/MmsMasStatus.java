package com.ag.fuel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the MMS_MAS_STATUS_C database table.
 * 
 */
//@Entity
@Table(name="MMS_MAS_STATUS")
@NamedQuery(name="MmsMasStatus.findAll", query="SELECT m FROM MmsMasStatus m")
public class MmsMasStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	private String application;

	private String flag;

	@Column(name="STATUS_CODE")
	private String statusCode;

	@Column(name="STATUS_DESC")
	private String statusDesc;

	public MmsMasStatus() {
	}

	public String getApplication() {
		return this.application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDesc() {
		return this.statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

}