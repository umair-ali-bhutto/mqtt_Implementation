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

/**
 * The persistent class for the TXN_LOGS_DETAIL database table.
 * 
 */
@Entity
@Table(name = "TXN_LOGS_DETAIL")
public class TxnLogsDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "TXN_LOGS_DETAIL_SEQ")
	@SequenceGenerator(name="TXN_LOGS_DETAIL_SEQ",sequenceName="TXN_LOGS_DETAIL_SEQ", allocationSize=1)
	private long id;

	@Column(name = "DATA_NAME")
	private String dataName;

	@Column(name = "DATA_VALUE")
	private String dataValue;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "TXN_LOG_REF_ID")
	private int txnLogRefId;

	public TxnLogsDetail() {
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

	public int getTxnLogRefId() {
		return this.txnLogRefId;
	}

	public void setTxnLogRefId(int txnLogRefId) {
		this.txnLogRefId = txnLogRefId;
	}

}