package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the MERCHANT_TERMINAL_DETAILS database table.
 * 
 */
@Entity
@Table(name = "MERCHANT_TERMINAL_DETAILS")
public class MerchantTerminalDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MID_TID_DETAILS_SEQ")
	@SequenceGenerator(name = "MID_TID_DETAILS_SEQ", sequenceName = "MID_TID_DETAILS_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "DATA_NAME")
	private String dataName;

	@Column(name = "DATA_VALUE")
	private String dataValue;

	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	private String mid;

	private String tid;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "TXN_LOGS_ID")
	private Integer txnLogsId;

	public MerchantTerminalDetail() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDataName() {
		return this.dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDataValue() {
		return this.dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTxnLogsId() {
		return txnLogsId;
	}

	public void setTxnLogsId(Integer txnLogsId) {
		this.txnLogsId = txnLogsId;
	}

}