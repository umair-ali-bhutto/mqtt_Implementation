package com.ag.generic.entity;

import java.io.Serializable;
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
@Table(name = "AG_PUSH_SMS")
@NamedQueries({

		@NamedQuery(name = "AgPushSMS.retrieveAll", query = "SELECT a FROM AgPushSMS a")

})
public class AgPushSMS implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOY_SMS_SEQ")
	@SequenceGenerator(name = "LOY_SMS_SEQ", sequenceName = "LOY_SMS_SEQ", allocationSize = 1)
	private int id;

	@Column(name = "SMS_TEXT")
	private String smsText;

	@Column(name = "SMS_NUMBER")
	private String smsNumber;

	@Column(name = "TXN_REF")
	private String txnRef;

	@Column(name = "DROP_DATE")
	private Timestamp dropDate;

	@Column(name = "SENT_DATE")
	private String sentDate;

	@Column(name = "IS_SENT")
	private int isSent;

	@Column(name = "TXN_AMOUNT")
	private String txnAmount;

	@Column(name = "TXN_DATE")
	private Timestamp txnDate;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "CARD_NUMBER")
	private String cardNumber;

	@Column(name = "TERM_ID")
	private String termId;

	@Column(name = "FREQUENCY")
	private String frequency;

	@Column(name = "PRIORITY")
	private String priority;

	@Column(name = "TXN_TYPE")
	private String txnType;

	@Column(name = "REB_AMT")
	private String rebAmt;

	@Column(name = "AWD_IND")
	private String awdInd;

	@Column(name = "RDM_IND")
	private String rdmInd;

	@Column(name = "ISSUER_ID")
	private String issuerId;

	@Column(name = "MSG_HEADER")
	private String msgHeader;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public String getSmsNumber() {
		return smsNumber;
	}

	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}

	public String getTxnRef() {
		return txnRef;
	}

	public void setTxnRef(String txnRef) {
		this.txnRef = txnRef;
	}

	public Timestamp getDropDate() {
		return dropDate;
	}

	public void setDropDate(Timestamp dropDate) {
		this.dropDate = dropDate;
	}

	public String getSentDate() {
		return sentDate;
	}

	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}

	public int getIsSent() {
		return isSent;
	}

	public void setIsSent(int isSent) {
		this.isSent = isSent;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public Timestamp getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Timestamp txnDate) {
		this.txnDate = txnDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getRebAmt() {
		return rebAmt;
	}

	public void setRebAmt(String rebAmt) {
		this.rebAmt = rebAmt;
	}

	public String getAwdInd() {
		return awdInd;
	}

	public void setAwdInd(String awdInd) {
		this.awdInd = awdInd;
	}

	public String getRdmInd() {
		return rdmInd;
	}

	public void setRdmInd(String rdmInd) {
		this.rdmInd = rdmInd;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getMsgHeader() {
		return msgHeader;
	}

	public void setMsgHeader(String msgHeader) {
		this.msgHeader = msgHeader;
	}

}