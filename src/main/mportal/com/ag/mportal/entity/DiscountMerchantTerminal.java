package com.ag.mportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the DISC_MERCHANT_TERMINAL database table.
 * 
 */
@Entity
@Table(name = "DISC_MERCHANT_TERMINAL")
@NamedQuery(name = "DiscountMerchantTerminal.fetchByDiscId", query = "SELECT a FROM DiscountMerchantTerminal a where a.discId=:discId")
@NamedQuery(name = "DiscountMerchantTerminal.fetchByDiscIdAndDiscMidId", query = "SELECT a FROM DiscountMerchantTerminal a where a.discId=:discId and a.discMidId=:discMidId and a.terminalId=:terminalId")
public class DiscountMerchantTerminal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISC_MERCHANT_TERMINAL_SEQ")
	@SequenceGenerator(name = "DISC_MERCHANT_TERMINAL_SEQ", sequenceName = "DISC_MERCHANT_TERMINAL_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "DISC_ID")
	private long discId;

	@Column(name = "DISC_MID_ID")
	private long discMidId;

	@Column(name = "TERMINAL_ID")
	private String terminalId;

	@Column(name = "TERMINAL_DESC")
	private String terminalDesc;

	@Column(name = "MAX_DISCOUNT_PER_TXN")
	private double maxDiscountPerTxn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDiscId() {
		return discId;
	}

	public void setDiscId(long discId) {
		this.discId = discId;
	}

	public long getDiscMidId() {
		return discMidId;
	}

	public void setDiscMidId(long discMidId) {
		this.discMidId = discMidId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalDesc() {
		return terminalDesc;
	}

	public void setTerminalDesc(String terminalDesc) {
		this.terminalDesc = terminalDesc;
	}

	public double getMaxDiscountPerTxn() {
		return maxDiscountPerTxn;
	}

	public void setMaxDiscountPerTxn(double maxDiscountPerTxn) {
		this.maxDiscountPerTxn = maxDiscountPerTxn;
	}

}