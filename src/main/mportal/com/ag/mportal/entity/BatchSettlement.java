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
 * The persistent class for the BATCH_SETTLEMENT database table.
 * 
 */
@Entity
@Table(name = "BATCH_SETTLEMENT")
public class BatchSettlement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_SETTLEMENT_SEQ")
	@SequenceGenerator(name = "BATCH_SETTLEMENT_SEQ", sequenceName = "BATCH_SETTLEMENT_SEQ", allocationSize = 1)
	private long id;

	private String amount;

	@Column(name = "AUTH_ID")
	private String authId;

	@Column(name = "BATCH_NUMBER")
	private String batchNumber;

	private String bin;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "ENTRY_MODE")
	private String entryMode;

	@Column(name = "FIELD_ONE")
	private String fieldOne;

	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;

	@Column(name = "IS_SETTELED")
	private String isSetteled;

	private String mid;

	private String model;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	private String stan;

	private String tid;

	@Column(name = "TXN_DATE")
	private Timestamp txnDate;

	@Column(name = "TXN_TYPE")
	private String txnType;

	public BatchSettlement() {
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAuthId() {
		return this.authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getBatchNumber() {
		return this.batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getBin() {
		return this.bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryMode() {
		return this.entryMode;
	}

	public void setEntryMode(String entryMode) {
		this.entryMode = entryMode;
	}

	public String getFieldOne() {
		return this.fieldOne;
	}

	public void setFieldOne(String fieldOne) {
		this.fieldOne = fieldOne;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInvoiceNumber() {
		return this.invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getIsSetteled() {
		return this.isSetteled;
	}

	public void setIsSetteled(String isSetteled) {
		this.isSetteled = isSetteled;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRrn() {
		return this.rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getStan() {
		return this.stan;
	}

	public void setStan(String stan) {
		this.stan = stan;
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

	public String getTxnType() {
		return this.txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

}