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
 * The persistent class for the DISC_MERCHANT database table.
 * 
 */
@Entity
@Table(name = "DISC_MERCHANT")
@NamedQueries({
		@NamedQuery(name = "DiscountMerchant.fetchAllByDiscId", query = "SELECT d FROM DiscountMerchant d where d.discId=:discId"),
		@NamedQuery(name = "DiscountMerchant.getMerchantByDisctId", query = "SELECT d FROM DiscountMerchant d where d.discId in (:discId)"),
		@NamedQuery(name = "DiscountMerchant.fetchByDiscIdAndMid", query = "SELECT a FROM DiscountMerchant a where a.discId=:discId and a.mid=:mid") })
public class DiscountMerchant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISC_MERCHANT_SEQ")
	@SequenceGenerator(name = "DISC_MERCHANT_SEQ", sequenceName = "DISC_MERCHANT_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "DISC_ID")
	private long discId;

	@Column(name = "MID")
	private String mid;

	@Column(name = "MERCHANT_NAME")
	private String merchantName;

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

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public double getMaxDiscountPerTxn() {
		return maxDiscountPerTxn;
	}

	public void setMaxDiscountPerTxn(double maxDiscountPerTxn) {
		this.maxDiscountPerTxn = maxDiscountPerTxn;
	}

}