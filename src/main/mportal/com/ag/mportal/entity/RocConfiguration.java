package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ROC_CONFIG")
public class RocConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(name = "TXN_TYPE")
	private String txnType;
	
	@Column(name = "CARD_TYPE")
	private String cardType;
	
	@Column(name = "POS_TYPE")
	private String posType;
	
	@Column(name = "ROC_FILE")
	private String rocFile;

	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	private Integer isActive;
	@Column(name = "CORP_ID")
	private String corpId;
	
	

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getPosType() {
		return posType;
	}

	public void setPosType(String posType) {
		this.posType = posType;
	}

	public String getRocFile() {
		return rocFile;
	}

	public void setRocFile(String rocFile) {
		this.rocFile = rocFile;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

}