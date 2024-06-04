package com.ag.metro.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "METRO_BATCH_DETAIL")
@NamedQueries({

// @NamedQuery(name = "AuditLog.retrieveAll", query = "SELECT a FROM AuditLog
// a")

})
public class MetroBatchDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "METRO_BATCH_DETAIL_SEQ")
	@SequenceGenerator(name="METRO_BATCH_DETAIL_SEQ",sequenceName="METRO_BATCH_DETAIL_SEQ", allocationSize=1)
	private int id;

	@Column(name = "BATCH_NO")
	private String batchNo;

	@Column(name = "CARD_NO")
	private String cardNo;

	@Column(name = "TOPUP_AMT")
	private String topupAmt;

	@Column(name = "EXP_ON")
	private String expOn;

	@Column(name = "EXECUTED_BY")
	private String executedBy;

	@Column(name = "EXECUATED_ON")
	private String executedOn;

	@Column(name = "STATUS_CODE")
	private String statusCode;

	@Column(name = "STATUS_DESC")
	private String statusDesc;

	@Column(name = "DC1")
	private String dc1;

	@Column(name = "DC2")
	private String dc2;

	@Column(name = "MEMBER_ID")
	private String memberId;

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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getTopupAmt() {
		return topupAmt;
	}

	public void setTopupAmt(String topupAmt) {
		this.topupAmt = topupAmt;
	}

	public String getExpOn() {
		return expOn;
	}

	public void setExpOn(String expOn) {
		this.expOn = expOn;
	}

	public String getExecutedBy() {
		return executedBy;
	}

	public void setExecutedBy(String executedBy) {
		this.executedBy = executedBy;
	}

	public String getExecutedOn() {
		return executedOn;
	}

	public void setExecutedOn(String executedOn) {
		this.executedOn = executedOn;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getDc1() {
		return dc1;
	}

	public void setDc1(String dc1) {
		this.dc1 = dc1;
	}

	public String getDc2() {
		return dc2;
	}

	public void setDc2(String dc2) {
		this.dc2 = dc2;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
