package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CALENDAR_MASTER")
@NamedQueries({ @NamedQuery(name = "CalenderMaster.fetchAllRecordById", query = "SELECT a FROM CalenderMaster a where a.callId=:callId"),
		@NamedQuery(name = "CalenderMaster.fetchAllRecord", query = "SELECT a FROM CalenderMaster a") })
public class CalenderMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CALID")
	private String callId;

	@Column(name = "DESCRIPTION")
	private String descrption;

	@Column(name = "DATE_FROM")
	private java.sql.Timestamp dateFrom;

	@Column(name = "DATE_TO")
	private java.sql.Timestamp dateTo;

	@Column(name = "KYC")
	private String kyc;

	@Column(name = "CR_BY")
	private String crBy;

	@Column(name = "CR_ON")
	private java.sql.Date crOn;

	@Column(name = "UPD_BY")
	private String updBy;

	@Column(name = "UPD_ON")
	private java.sql.Date updOn;

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	public java.sql.Timestamp getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(java.sql.Timestamp dateFrom) {
		this.dateFrom = dateFrom;
	}

	public java.sql.Timestamp getDateTo() {
		return dateTo;
	}

	public void setDateTo(java.sql.Timestamp dateTo) {
		this.dateTo = dateTo;
	}

	public String getKyc() {
		return kyc;
	}

	public void setKyc(String kyc) {
		this.kyc = kyc;
	}

	public String getCrBy() {
		return crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public java.sql.Date getCrOn() {
		return crOn;
	}

	public void setCrOn(java.sql.Date crOn) {
		this.crOn = crOn;
	}

	public String getUpdBy() {
		return updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public java.sql.Date getUpdOn() {
		return updOn;
	}

	public void setUpdOn(java.sql.Date updOn) {
		this.updOn = updOn;
	}

	
}