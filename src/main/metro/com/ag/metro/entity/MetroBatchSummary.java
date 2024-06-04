package com.ag.metro.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "METRO_BATCH_SUMMARY")
@NamedQueries({

// @NamedQuery(name = "AuditLog.retrieveAll", query = "SELECT a FROM AuditLog
// a")

})
public class MetroBatchSummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "METRO_BATCH_SUMMARY_SEQ")
	@SequenceGenerator(name = "METRO_BATCH_SUMMARY_SEQ", sequenceName = "METRO_BATCH_SUMMARY_SEQ", allocationSize = 1)
	private int id;

	@Column(name = "BATCH_NO")
	private String batchNo;

	@Column(name = "CARD_FROM")
	private String cardFrom;

	@Column(name = "CARD_TO")
	private String cardTo;

	@Column(name = "TOTAL_CARD")
	private int totalCards;

	@Column(name = "TOPUP_AMT")
	private String topupAmt;

	@Column(name = "TOTAL_TOPUP_AMT")
	private String totalTopupAmt;

	@Column(name = "EXP_ON")
	private String expOn;

	@Column(name = "DC1")
	private String dc1;

	@Column(name = "EXECUTED_BY")
	private String executedBy;

	@Column(name = "EXECUATED_ON")
	private Timestamp executedOn;

	@Column(name = "MEMBER_ID")
	private String memberId;

	@Column(name = "SHELF_LIFE")
	private int shelfLife;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCardFrom() {
		return cardFrom;
	}

	public void setCardFrom(String cardFrom) {
		this.cardFrom = cardFrom;
	}

	public String getCardTo() {
		return cardTo;
	}

	public void setCardTo(String cardTo) {
		this.cardTo = cardTo;
	}

	public int getTotalCards() {
		return totalCards;
	}

	public void setTotalCards(int totalCards) {
		this.totalCards = totalCards;
	}

	public String getTopupAmt() {
		return topupAmt;
	}

	public void setTopupAmt(String topupAmt) {
		this.topupAmt = topupAmt;
	}

	public String getTotalTopupAmt() {
		return totalTopupAmt;
	}

	public void setTotalTopupAmt(String totalTopupAmt) {
		this.totalTopupAmt = totalTopupAmt;
	}

	public String getExpOn() {
		return expOn;
	}

	public void setExpOn(String expOn) {
		this.expOn = expOn;
	}

	public String getDc1() {
		return dc1;
	}

	public void setDc1(String dc1) {
		this.dc1 = dc1;
	}

	public String getExecutedBy() {
		return executedBy;
	}

	public void setExecutedBy(String executedBy) {
		this.executedBy = executedBy;
	}

	public Timestamp getExecutedOn() {
		return executedOn;
	}

	public void setExecutedOn(Timestamp executedOn) {
		this.executedOn = executedOn;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

}
