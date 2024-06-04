package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TXN_STATUS_LOG database table.
 * 
 */
@Entity
@Table(name = "TXN_STATUS_LOG")
//@NamedQuery(name = "TxnStatusLog.findAll", query = "SELECT t FROM TxnStatusLog t")
public class TxnStatusLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "DATA_NAME")
	private String dataName;

	@Column(name = "DATA_VALUE")
	private String dataValue;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String mid;

	@Column(name = "ROW_ID")
	private String rowId;

	private String tid;

	@Column(name = "TXN_DATE")
	private Timestamp txnDate;

	@Column(name = "TYPE")
	private String type;

	public TxnStatusLog() {
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

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getRowId() {
		return this.rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Timestamp getTxnDate() {
		return this.txnDate;
	}

	public void setTxnDate(Timestamp txnDate) {
		this.txnDate = txnDate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}