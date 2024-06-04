package com.ag.mportal.entity;

import java.io.Serializable;
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

/**
 * The persistent class for the BATCH_SETTLEMENT database table.
 * 
 */
@Entity
@Table(name = "TAPNPAY_TRANSACTIONS")
@NamedQueries({
		@NamedQuery(name = "TapNPayTransactions.fetchByID", query = "SELECT d FROM TapNPayTransactions d where d.id=:id and d.code='00' order by id desc "),
		@NamedQuery(name = "TapNPayTransactions.fetchTxnByMidTidForVoid", query = "SELECT d FROM TapNPayTransactions d where d.mid=:mid and d.tid=:tid and d.code='00' AND d.isVoid='N' and d.transactionType not in ('VOID','PRE AUTH') order by id desc "),
		@NamedQuery(name = "TapNPayTransactions.fetchTxnByMidTidForViewAll", query = "SELECT d FROM TapNPayTransactions d where d.mid=:mid and d.tid=:tid and d.code='00' order by id desc "),
		@NamedQuery(name = "TapNPayTransactions.fetchUnSettledTxn", query = "SELECT COUNT(d),SUM(d.txnAmount),d.transactionType FROM TapNPayTransactions d where d.isSettled='N' and d.mid=:mid and d.tid=:tid and d.code='00' and d.transactionType not in ('PRE AUTH') group by d.transactionType "),
		@NamedQuery(name = "TapNPayTransactions.fetchUnSettledTxnUpdate", query = "SELECT d FROM TapNPayTransactions d where d.isSettled='N' and d.mid=:mid and d.tid=:tid and d.code='00' "),
		@NamedQuery(name = "TapNPayTransactions.validateAuthIdByMidTid", query = "SELECT d FROM TapNPayTransactions d where d.mid=:mid and d.tid=:tid and d.code='00' and d.transactionType<>'PRE AUTH' and d.authId=:authId order by id desc"),
		@NamedQuery(name = "TapNPayTransactions.fetchTxnByRecIdForReversal", query = "SELECT d FROM TapNPayTransactions d where d.id=:id  order by id desc ")

})
public class TapNPayTransactions implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAPNPAY_TRANSACTIONS_SEQ")
	@SequenceGenerator(name = "TAPNPAY_TRANSACTIONS_SEQ", sequenceName = "TAPNPAY_TRANSACTIONS_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "MID")
	private String mid;

	@Column(name = "TID")
	private String tid;

	@Column(name = "CARD_NUMBER")
	private String cardNumber;

	@Column(name = "CARD_EXPIRY")
	private Date cardExpiriy;

	@Column(name = "TXN_DATE")
	private Timestamp txnDate;

	@Column(name = "POS_DATE")
	private Timestamp posDate;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "AID")
	private String aid;

	@Column(name = "CARD_TYPE")
	private String cardType;

	@Column(name = "STAN")
	private String stan;

	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;

	@Column(name = "ISO_REQUEST")
	private String isoRequest;

	@Column(name = "ISO_RESPONSE")
	private String isoResponse;

	@Column(name = "CODE")
	private String code;

	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "TXN_AMOUNT")
	private Float txnAmount;

	@Column(name = "CARD_SCHEME")
	private String cardSheme;

	@Column(name = "AUTH_ID")
	private String authId;

	@Column(name = "BATCH_NUMBER")
	private String batchNumber;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "ENTRY_BY")
	private int entryBy;

	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;

	@Column(name = "TXN_STATUS")
	private String transactionStatus;

	@Column(name = "TRACK2_DATA")
	private String track2Data;

	@Column(name = "FIELD55_DATA")
	private String field55Data;

	@Column(name = "IS_SETTLED")
	private String isSettled;

	@Column(name = "SETTLED_DATE")
	private Timestamp settledDate;

	@Column(name = "IS_REVERSED")
	private String isReversed;

	@Column(name = "REVERSAL_DATE")
	private Timestamp reversalDate;

	@Column(name = "IS_VOID")
	private String isVoid;

	@Column(name = "VOID_DATE")
	private Timestamp voidDate;
	
	@Column(name = "TVR")
	private String tvr;

	@Column(name = "PAN_SEQ_NO")
	private String panSeqNo;
	
	
	public String getTvr() {
		return tvr;
	}

	public void setTvr(String tvr) {
		this.tvr = tvr;
	}
	
	public String getPanSeqNo() {
		return panSeqNo;
	}

	public void setPanSeqNo(String panSeqNo) {
		this.panSeqNo = panSeqNo;
	}

	public String getIsVoid() {
		return isVoid;
	}

	public void setIsVoid(String isVoid) {
		this.isVoid = isVoid;
	}

	public Timestamp getVoidDate() {
		return voidDate;
	}

	public void setVoidDate(Timestamp voidDate) {
		this.voidDate = voidDate;
	}

	public String getIsSettled() {
		return isSettled;
	}

	public void setIsSettled(String isSettled) {
		this.isSettled = isSettled;
	}

	public Timestamp getSettledDate() {
		return settledDate;
	}

	public void setSettledDate(Timestamp settledDate) {
		this.settledDate = settledDate;
	}

	public String getIsReversed() {
		return isReversed;
	}

	public void setIsReversed(String isReversed) {
		this.isReversed = isReversed;
	}

	public Timestamp getReversalDate() {
		return reversalDate;
	}

	public void setReversalDate(Timestamp reversalDate) {
		this.reversalDate = reversalDate;
	}

	public String getField55Data() {
		return field55Data;
	}

	public void setField55Data(String field55Data) {
		this.field55Data = field55Data;
	}

	public String getTrack2Data() {
		return track2Data;
	}

	public void setTrack2Data(String track2Data) {
		this.track2Data = track2Data;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getCardExpiriy() {
		return cardExpiriy;
	}

	public void setCardExpiriy(Date cardExpiriy) {
		this.cardExpiriy = cardExpiriy;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getStan() {
		return stan;
	}

	public void setStan(String stan) {
		this.stan = stan;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getIsoRequest() {
		return isoRequest;
	}

	public void setIsoRequest(String isoRequest) {
		this.isoRequest = isoRequest;
	}

	public String getIsoResponse() {
		return isoResponse;
	}

	public void setIsoResponse(String isoResponse) {
		this.isoResponse = isoResponse;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Float getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(Float txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getCardSheme() {
		return cardSheme;
	}

	public void setCardSheme(String cardSheme) {
		this.cardSheme = cardSheme;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public int getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(int entryBy) {
		this.entryBy = entryBy;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Timestamp getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Timestamp txnDate) {
		this.txnDate = txnDate;
	}

	public Timestamp getPosDate() {
		return posDate;
	}

	public void setPosDate(Timestamp posDate) {
		this.posDate = posDate;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

}
