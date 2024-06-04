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
@Table(name = "AG_PUSH_EMAIL")
@NamedQueries({

		@NamedQuery(name = "AgPushEmail.retrieveAll", query = "SELECT a FROM AgPushEmail a")

})
public class AgPushEmail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOY_SMS_SEQ")
	@SequenceGenerator(name = "LOY_SMS_SEQ", sequenceName = "LOY_SMS_SEQ", allocationSize = 1)
	private int id;

	@Column(name = "EMAIL_TEXT")
	private String emailText;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "EMAIL_SUBJ")
	private String emailSubj;

	@Column(name = "TXN_REF")
	private String txnRef;

	@Column(name = "DROP_DATE")
	private Timestamp dropDate;

	@Column(name = "SENT_DATE")
	private String sentDate;

	@Column(name = "IS_SENT")
	private int isSent;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "FREQUENCY")
	private String frequency;

	@Column(name = "ISSUER_ID")
	private String issuerId;



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailSubj() {
		return emailSubj;
	}

	public void setEmailSubj(String emailSubj) {
		this.emailSubj = emailSubj;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	
}