package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DEBUG_LOG database table.
 * 
 */
@Entity
@Table(name="DEBUG_LOG")
@NamedQuery(name="DebugLog.findAll", query="SELECT d FROM DebugLog d")
public class DebugLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue
    int id;    

	@Column(name="DB_CONFIG")
	private String dbConfig;

	@Column(name="DEBUG_LEVEL")
	private BigDecimal debugLevel;

	@Column(name="DEBUG_MESSAGE")
	private String debugMessage;

	@Temporal(TemporalType.DATE)
	@Column(name="LOG_DATE")
	private Date logDate;

	@Column(name="LOG_TIME")
	private Timestamp logTime;

	@Column(name="\"MODULE\"")
	private String module;

	@Column(name="\"PROC\"")
	private String proc;

	public DebugLog() {
	}

	public String getDbConfig() {
		return this.dbConfig;
	}

	public void setDbConfig(String dbConfig) {
		this.dbConfig = dbConfig;
	}

	public BigDecimal getDebugLevel() {
		return this.debugLevel;
	}

	public void setDebugLevel(BigDecimal debugLevel) {
		this.debugLevel = debugLevel;
	}

	public String getDebugMessage() {
		return this.debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public Date getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public Timestamp getLogTime() {
		return this.logTime;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getProc() {
		return this.proc;
	}

	public void setProc(String proc) {
		this.proc = proc;
	}

}