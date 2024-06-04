package com.ag.mportal.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "QR_PAYMENTS")
@NamedQueries({
		@NamedQuery(name = "QrPayments.retrieveUnpaidQrPayment", query = "SELECT p FROM QrPayments p WHERE p.isResponseSent=0 AND p.mid=:mid AND p.tid=:tid AND p.serialNumber=:serialNumber") })
public class QrPayments {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "QR_PAYMENTS_SEQ")
	@SequenceGenerator(name="QR_PAYMENTS_SEQ",sequenceName="QR_PAYMENTS_SEQ", allocationSize=1)
	private int id;
	@Column(name = "REMARKS")
	private String remarks;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "TID")
	private String tid;
	@Column(name = "MID")
	private String mid;
	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;
	@Column(name = "RESPONSE_SENT_DATE")
	private Timestamp responseSentDate;
	@Column(name = "IS_RESPONSE_SENT")
	private int isResponseSent;
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;
	@Column(name = "PAYMENT_DATE")
	private Date paymentDate;
	@Column(name = "POINTS_AWD_REDEMED")
	private String pointsAwdRedemed;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "BALANCE")
	private String balance;
	@Column(name = "CURRENCY")
	private String currency;
	@Column(name = "TO_ACCOUNT")
	private String toAccount;
	@Column(name = "TO_NAME")
	private String toName;
	@Column(name = "SRC_NAME")
	private String srcName;
	@Column(name = "SRC_ACCOUNT")
	private String srcAccount;
	@Column(name = "TXN_REF")
	private String txnRef;
	@Column(name = "TXN_AMOUNT")
	private String txnAmount;
	@Column(name = "STYPE")
	private String stype;
	
	@Column(name = "CARD_TYPE")
	private String cardType;
	@Column(name = "BATCH_NUMBER")
	private String batchNumber;
	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;
	@Column(name = "CARD_EXPIRY")
	private Timestamp cardExpiry;
	@Column(name = "CARD_NUMBER")
	private String cardNumber;
	@Column(name = "ISSUER_NAME")
	private String issuerName;
	@Column(name = "PRODUCT_ID")
	private String productId;
	@Column(name = "CARD_SCHEME")
	private String cardScheme;
	@Column(name = "QR_EXPIRY")
	private Timestamp qrExpiry;
	
	
	
	
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Timestamp getResponseSentDate() {
		return responseSentDate;
	}

	public void setResponseSentDate(Timestamp responseSentDate) {
		this.responseSentDate = responseSentDate;
	}

	public int getIsResponseSent() {
		return isResponseSent;
	}

	public void setIsResponseSent(int isResponseSent) {
		this.isResponseSent = isResponseSent;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPointsAwdRedemed() {
		return pointsAwdRedemed;
	}

	public void setPointsAwdRedemed(String pointsAwdRedemed) {
		this.pointsAwdRedemed = pointsAwdRedemed;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getSrcName() {
		return srcName;
	}

	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}

	public String getSrcAccount() {
		return srcAccount;
	}

	public void setSrcAccount(String srcAccount) {
		this.srcAccount = srcAccount;
	}

	public String getTxnRef() {
		return txnRef;
	}

	public void setTxnRef(String txnRef) {
		this.txnRef = txnRef;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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

	public Timestamp getCardExpiry() {
		return cardExpiry;
	}

	public void setCardExpiry(Timestamp cardExpiry) {
		this.cardExpiry = cardExpiry;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCardScheme() {
		return cardScheme;
	}

	public void setCardScheme(String cardScheme) {
		this.cardScheme = cardScheme;
	}

	public Timestamp getQrExpiry() {
		return qrExpiry;
	}

	public void setQrExpiry(Timestamp qrExpiry) {
		this.qrExpiry = qrExpiry;
	}

}
