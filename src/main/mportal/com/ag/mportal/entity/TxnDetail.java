package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the TXN_DETAILS database table.
 * 
 */
@Entity
@Table(name = "TXN_DETAILS")
@NamedQueries({ @NamedQuery(name = "TxnDetail.fetchAllById", query = "SELECT d FROM TxnDetail d where d.id = :id"),
		@NamedQuery(name = "TxnDetail.getTidByMid", query = "SELECT d FROM TxnDetail d where d.merchantId = :merchantId"),
		@NamedQuery(name = "TxnDetail.fetchTxnDetailsVoid", query = "SELECT d FROM TxnDetail d where d.status = :status AND d.batchNumber = :batchNumber AND d.terminalId = :terminalID AND d.invoiceNum = :invoiceNum AND d.type = :type"),
		@NamedQuery(name = "TxnDetail.fetchTxnDetailsNonVoid", query = "SELECT d FROM TxnDetail d where d.status = :status AND d.batchNumber = :batchNumber AND d.terminalId = :terminalID AND d.invoiceNum = :invoiceNum AND d.type <>:type"),
		@NamedQuery(name = "TxnDetail.fetchTxnDetailByATH", query = "SELECT d FROM TxnDetail d where d.status = :status AND d.batchNumber = :batchNumber AND d.terminalId = :terminalID AND d.invoiceNum = :invoiceNum  "),
		@NamedQuery(name = "TxnDetail.fetchTxnDetailByATHPreAuth", query = "SELECT d FROM TxnDetail d where d.status = :status AND d.merchantId = :mid AND d.authIdN = :authIdN AND d.type='PRE AUTH' ORDER BY d.entryDate desc "),
		@NamedQuery(name = "TxnDetail.fetchTxnDetailByATHVoid", query = "SELECT d FROM TxnDetail d where d.status = :status AND d.merchantId = :mid AND d.authIdN = :authIdN AND d.type='PRE AUTH' ORDER BY d.entryDate desc "),
		@NamedQuery(name = "TxnDetail.checkExistanceOfTxnDetail", query = "SELECT d FROM TxnDetail d where d.merchantId = :mid AND d.authId=:authid AND d.refNum=:refno AND d.terminalId=:tid AND d.batchNumber=:batchno AND d.type=:type ") })
public class TxnDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TXN_DETAILS_SEQ")
	@SequenceGenerator(name = "TXN_DETAILS_SEQ", sequenceName = "TXN_DETAILS_SEQ", allocationSize = 1)
	private int id;

	private String amount;

	@Column(name = "AUTH_ID")
	private String authId;

	@Column(name = "AUTH_ID_N")
	private String authIdN;

	@Column(name = "BATCH_NUMBER")
	private String batchNumber;

	@Column(name = "BATCH_SETTLE_RECORDS")
	private int batchSettleRecords;

	private String bin;

	@Column(name = "CARD_CC_TYPE")
	private String cardCcType;

	@Column(name = "CUST_ID")
	private int custId;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "FIELD_ONE")
	private String fieldOne;

	@Column(name = "INVOICE_NUM")
	private String invoiceNum;

	@Column(name = "MERCHANT_ID")
	private String merchantId;

	private String model;

	private String msg;

	private String paid;

	@Column(name = "PAID_DATE")
	private Timestamp paidDate;

	@Column(name = "POS_ENTRY_MODE")
	private String posEntryMode;

	@Column(name = "REF_NUM")
	private String refNum;

	private String response;

	@Column(name = "RESULT_STATUS")
	private String resultStatus;

	private String reversal;

	@Column(name = "REVERSAL_DATE")
	private Timestamp reversalDate;

	private String scheme;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	private String setteled;

	@Column(name = "SETTELED_DATE")
	private Timestamp setteledDate;

	@Column(name = "SETTELMENT_RESPONSE")
	private String settelmentResponse;
	@Column(name = "STATUS")
	private String status;

	@Column(name = "TERMINAL_ID")
	private String terminalId;

	@Column(name = "TRANSACTION_ID")
	private String transactionId;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ORIGNAL_REF_NUM")
	private String orignalRef;

	@Column(name = "REASON")
	private String reason;

	@Column(name = "TIP_AMOUNT")
	private String tipAmount;

	@Column(name = "ADJ_AMOUNT")
	private String adjAmount;

	@Column(name = "QUANTITY_TYPE")
	private String quantityType;

	@Column(name = "PRD_NAME")
	private String prdName;

	@Column(name = "PRD_CODE")
	private String prdCode;

	@Column(name = "QUANTITY")
	private Integer quantity;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "MDR_ONUS")
	private Double mdrOnUs;
	@Column(name = "MDR_OFFUS")
	private Double mdrOffUs;

	@Column(name = "FED_RATE")
	private Double fedRates;
	@Column(name = "TVR")
	private String tvr;

	@Column(name = "AID")
	private String aid;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Column(name = "CARD_TYPE")
	private String cardType;

	@Column(name = "CARD_EXPIRY")
	private Date cardExpiry;

	@Column(name = "NET_AMOUNT")
	private String netAmount;

	@Column(name = "PAYMENT_DATE")
	private Timestamp paymentDate;

	public TxnDetail() {
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

	public String getAuthIdN() {
		return this.authIdN;
	}

	public void setAuthIdN(String authIdN) {
		this.authIdN = authIdN;
	}

	public String getBatchNumber() {
		return this.batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public int getBatchSettleRecords() {
		return this.batchSettleRecords;
	}

	public void setBatchSettleRecords(int batchSettleRecords) {
		this.batchSettleRecords = batchSettleRecords;
	}

	public String getBin() {
		return this.bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getCardCcType() {
		return this.cardCcType;
	}

	public void setCardCcType(String cardCcType) {
		this.cardCcType = cardCcType;
	}

	public int getCustId() {
		return this.custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getFieldOne() {
		return this.fieldOne;
	}

	public void setFieldOne(String fieldOne) {
		this.fieldOne = fieldOne;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvoiceNum() {
		return this.invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPaid() {
		return this.paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public Timestamp getPaidDate() {
		return this.paidDate;
	}

	public void setPaidDate(Timestamp paidDate) {
		this.paidDate = paidDate;
	}

	public String getPosEntryMode() {
		return this.posEntryMode;
	}

	public void setPosEntryMode(String posEntryMode) {
		this.posEntryMode = posEntryMode;
	}

	public String getRefNum() {
		return this.refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getResultStatus() {
		return this.resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getReversal() {
		return this.reversal;
	}

	public void setReversal(String reversal) {
		this.reversal = reversal;
	}

	public Timestamp getReversalDate() {
		return this.reversalDate;
	}

	public void setReversalDate(Timestamp reversalDate) {
		this.reversalDate = reversalDate;
	}

	public String getScheme() {
		return this.scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSetteled() {
		return this.setteled;
	}

	public void setSetteled(String setteled) {
		this.setteled = setteled;
	}

	public Timestamp getSetteledDate() {
		return this.setteledDate;
	}

	public void setSetteledDate(Timestamp setteledDate) {
		this.setteledDate = setteledDate;
	}

	public String getSettelmentResponse() {
		return this.settelmentResponse;
	}

	public void setSettelmentResponse(String settelmentResponse) {
		this.settelmentResponse = settelmentResponse;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTerminalId() {
		return this.terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrignalRef() {
		return orignalRef;
	}

	public void setOrignalRef(String orignalRef) {
		this.orignalRef = orignalRef;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTipAmount() {
		return tipAmount;
	}

	public void setTipAmount(String tipAmount) {
		this.tipAmount = tipAmount;
	}

	public String getAdjAmount() {
		return adjAmount;
	}

	public void setAdjAmount(String adjAmount) {
		this.adjAmount = adjAmount;
	}

	public String getQuantityType() {
		return quantityType;
	}

	public void setQuantityType(String quantityType) {
		this.quantityType = quantityType;
	}

	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	public String getPrdCode() {
		return prdCode;
	}

	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getMdrOnUs() {
		return mdrOnUs;
	}

	public void setMdrOnUs(Double mdrOnUs) {
		this.mdrOnUs = mdrOnUs;
	}

	public Double getMdrOffUs() {
		return mdrOffUs;
	}

	public void setMdrOffUs(Double mdrOffUs) {
		this.mdrOffUs = mdrOffUs;
	}

	public Double getFedRates() {
		return fedRates;
	}

	public void setFedRates(Double fedRates) {
		this.fedRates = fedRates;
	}

	public String getTvr() {
		return tvr;
	}

	public void setTvr(String tvr) {
		this.tvr = tvr;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Date getCardExpiry() {
		return cardExpiry;
	}

	public void setCardExpiry(Date cardExpiry) {
		this.cardExpiry = cardExpiry;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

}