package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "RECON_DATA_DETAIL")
@NamedQuery(name = "ReconDataDetail.fetchAll", query = "SELECT d FROM ReconDataDetail d")
public class ReconDataDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECON_DATA_DETAIL_SEQ")
	@SequenceGenerator(name = "RECON_DATA_DETAIL_SEQ", sequenceName = "RECON_DATA_DETAIL_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "RECON_DATA_MASTER_ID")
	private long reconDataMasterId;

	@Column(name = "ECR_UNIQUE_ID")
	private long ecrUniqueId;

	@Column(name = "TRANSACTION_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp transactionDate;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TILL")
	private String till;

	@Column(name = "CUSTOMER_CODE")
	private String customerCode;

	@Column(name = "TRANSACTION_AMOUNT")
	private String transactionAmount;

	@Column(name = "NET_AMOUNT")
	private String netAmount;

	@Column(name = "TENDER_TYPE")
	private String tenderType;

	@Column(name = "CARD_NUMBER")
	private String cardNumber;

	@Column(name = "TRANSACTION_ID")
	private long transactionId;

	@Column(name = "ACTUAL_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp actualDate;

	@Column(name = "IVEND_USER")
	private String ivendUser;

	@Column(name = "AUTH_ID")
	private String authId;

	@Column(name = "BATCH_NO")
	private String batchNo;

	@Column(name = "TID")
	private String tid;

	@Column(name = "INVOICE_NO")
	private String invoiceNo;

	@Column(name = "MANUAL_TRANSACTION")
	private String manualTransaction;

	@Column(name = "APP_VERSION")
	private String appVersion;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "IDENTIFIER")
	private String identifier;

	@Column(name = "ENTRY_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp entryDate;

	@Column(name = "TENDER_TYPE_KEY")
	private String tenderTypeKey;

	@Column(name = "STORE_KEY")
	private String storeKey;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getReconDataMasterId() {
		return reconDataMasterId;
	}

	public void setReconDataMasterId(long reconDataMasterId) {
		this.reconDataMasterId = reconDataMasterId;
	}

	public long getEcrUniqueId() {
		return ecrUniqueId;
	}

	public void setEcrUniqueId(long ecrUniqueId) {
		this.ecrUniqueId = ecrUniqueId;
	}

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTill() {
		return till;
	}

	public void setTill(String till) {
		this.till = till;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public Timestamp getActualDate() {
		return actualDate;
	}

	public void setActualDate(Timestamp actualDate) {
		this.actualDate = actualDate;
	}

	public String getIvendUser() {
		return ivendUser;
	}

	public void setIvendUser(String ivendUser) {
		this.ivendUser = ivendUser;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getManualTransaction() {
		return manualTransaction;
	}

	public void setManualTransaction(String manualTransaction) {
		this.manualTransaction = manualTransaction;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getTenderTypeKey() {
		return tenderTypeKey;
	}

	public void setTenderTypeKey(String tenderTypeKey) {
		this.tenderTypeKey = tenderTypeKey;
	}

	public String getStoreKey() {
		return storeKey;
	}

	public void setStoreKey(String storeKey) {
		this.storeKey = storeKey;
	}

}
