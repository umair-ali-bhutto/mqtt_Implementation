package com.ag.mportal.entity;

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

/**
 * The persistent class for the TAPNPAY_SAF database table.
 * 
 */
@Entity
@Table(name = "TAPNPAY_SAF")
@NamedQueries({
	@NamedQuery(name = "TapNPaySaf.fetchAll", query = "SELECT d FROM TapNPaySaf d where d.maxHits<>d.minHits and d.status is null order by id desc "),
})


public class TapNPaySaf implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAPNPAY_SAF_SEQ")
	@SequenceGenerator(name = "TAPNPAY_SAF_SEQ", sequenceName = "TAPNPAY_SAF_SEQ", allocationSize = 1)
	private long id;
	
	@Column(name ="TAPNPAY_TXN_ID")
	private long tapnpayTxnId;
	
	@Column(name="ENTRY_DATE")
	private Timestamp entryDate;
	
	@Column(name="MAX_HITS")
	private int maxHits;
	
	@Column(name="MIN_HITS")
	private int minHits;
	
	
	@Column(name="STATUS")
	private String status;
	
	public int getMaxHits() {
		return maxHits;
	}

	public void setMaxHits(int maxHits) {
		this.maxHits = maxHits;
	}

	public int getMinHits() {
		return minHits;
	}

	public void setMinHits(int minHits) {
		this.minHits = minHits;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTapnpayTxnId() {
		return tapnpayTxnId;
	}

	public void setTapnpayTxnId(long idVal) {
		this.tapnpayTxnId = idVal;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}


	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	@Column(name="TXN_TYPE")
	private String txnType;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}