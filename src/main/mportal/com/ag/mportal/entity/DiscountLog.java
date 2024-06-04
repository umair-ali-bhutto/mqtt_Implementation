package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the DISCOUNT_LOG database table.
 * 
 */
@Entity
@Table(name = "DISC_LOG")
@NamedQuery(name = "DiscountLog.fetchAll", query = "SELECT a FROM DiscountLog a")
@NamedQuery(name = "DiscountLog.fetchAllByCid", query = "SELECT a FROM DiscountLog a WHERE a.cid=:cid")
@NamedQuery(name = "DiscountLog.fetchById", query = "SELECT a FROM DiscountLog a WHERE a.id=:recId")
public class DiscountLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISC_LOG_SEQ")
	@SequenceGenerator(name = "DISC_LOG_SEQ", sequenceName = "DISC_LOG_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "DISC_ID")
	private long discId;

	@Column(name = "TXN_DATE")
	private Timestamp txnDate;

	@Column(name = "TXN_AMOUNT")
	private double txnAmount;

	@Column(name = "CID")
	private String cid;

	@Column(name = "CID_TYPE")
	private String cidType;

	@Column(name = "DISCOUNT_AMOUNT")
	private double discountAmount;

	@Column(name = "NET_AMOUNT")
	private double netAmount;

	@Column(name = "MID")
	private String mid;

	@Column(name = "TID")
	private String tid;

	@Column(name = "BATCH_NUMBER")
	private String batchNumber;

	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;

	@Column(name = "DISCOUNT_AVAILED")
	private double discountAvailed;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "ENTRY_ON")
	private Timestamp entryOn;

	@Column(name = "UPD_ON")
	private Timestamp updOn;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	@Column(name = "TXN_ID")
	private int txnId;

	@Column(name = "MERCHANT_BASED")
	private int merchantBased;

	@Column(name = "TXN_CURRENCY")
	private int txnCurrency;

	@Column(name = "DISC_AVAILED_DATE")
	private Timestamp discAvailedDate;

	@Column(name = "DISC_STATUS")
	private String discStatus;

	@Column(name = "DISC_RATE")
	private double discRate;

	public double getDiscRate() {
		return discRate;
	}

	public void setDiscRate(double discRate) {
		this.discRate = discRate;
	}

	public long getId() {
		return id;
	}

	public Timestamp getDiscAvailedDate() {
		return discAvailedDate;
	}

	public String getDiscStatus() {
		return discStatus;
	}

	public void setDiscAvailedDate(Timestamp discAvailedDate) {
		this.discAvailedDate = discAvailedDate;
	}

	public void setDiscStatus(String discStatus) {
		this.discStatus = discStatus;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDiscId() {
		return discId;
	}

	public void setDiscId(long discId) {
		this.discId = discId;
	}

	public Timestamp getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Timestamp txnDate) {
		this.txnDate = txnDate;
	}

	public double getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(double txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCidType() {
		return cidType;
	}

	public void setCidType(String cidType) {
		this.cidType = cidType;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public double getDiscountAvailed() {
		return discountAvailed;
	}

	public void setDiscountAvailed(double discountAvailed) {
		this.discountAvailed = discountAvailed;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public Timestamp getEntryOn() {
		return entryOn;
	}

	public void setEntryOn(Timestamp entryOn) {
		this.entryOn = entryOn;
	}

	public Timestamp getUpdOn() {
		return updOn;
	}

	public void setUpdOn(Timestamp updOn) {
		this.updOn = updOn;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getTxnId() {
		return txnId;
	}

	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}

	public int getMerchantBased() {
		return merchantBased;
	}

	public void setMerchantBased(int merchantBased) {
		this.merchantBased = merchantBased;
	}

	public int getTxnCurrency() {
		return txnCurrency;
	}

	public void setTxnCurrency(int txnCurrency) {
		this.txnCurrency = txnCurrency;
	}

}