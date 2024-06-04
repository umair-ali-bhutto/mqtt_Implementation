package com.ag.mportal.entity;

import java.io.Serializable;

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
 * The persistent class for the DISC_BIN database table.
 * 
 */
@Entity
@Table(name = "DISC_BIN")
@NamedQueries({
		@NamedQuery(name = "DiscountBin.fetchByDiscId", query = "SELECT d FROM DiscountBin d where d.discId =:discId"),
		@NamedQuery(name = "DiscountBin.getBinByDisctId", query = "SELECT d FROM DiscountBin d where d.discId in (:discId)") })
public class DiscountBin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISC_BIN_SEQ")
	@SequenceGenerator(name = "DISC_BIN_SEQ", sequenceName = "DISC_BIN_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "DISC_ID")
	private long discId;

	@Column(name = "BIN_FROM")
	private String binFrom;

	@Column(name = "BIN_TO")
	private String binTo;

	@Column(name = "BIN_PRODUCT_NAME")
	private String binProductName;

	@Column(name = "BIN_DISC_TYPE")
	private String binDiscType;

	@Column(name = "BIN_DISC_VALUE")
	private double binDiscValue;

	@Column(name = "MAX_DISCOUNT_PER_TXN")
	private double maxDiscPerTxn;

	@Column(name = "BIN_PRODUCT_ID")
	private String binProductId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBinFrom() {
		return binFrom;
	}

	public void setBinFrom(String binFrom) {
		this.binFrom = binFrom;
	}

	public String getBinTo() {
		return binTo;
	}

	public void setBinTo(String binTo) {
		this.binTo = binTo;
	}

	public String getBinProductName() {
		return binProductName;
	}

	public void setBinProductName(String binProductName) {
		this.binProductName = binProductName;
	}

	public String getBinDiscType() {
		return binDiscType;
	}

	public void setBinDiscType(String binDiscType) {
		this.binDiscType = binDiscType;
	}

	public double getBinDiscValue() {
		return binDiscValue;
	}

	public void setBinDiscValue(double binDiscValue) {
		this.binDiscValue = binDiscValue;
	}

	public double getMaxDiscPerTxn() {
		return maxDiscPerTxn;
	}

	public void setMaxDiscPerTxn(double maxDiscPerTxn) {
		this.maxDiscPerTxn = maxDiscPerTxn;
	}

	public long getDiscId() {
		return discId;
	}

	public void setDiscId(long discId) {
		this.discId = discId;
	}

	public String getBinProductId() {
		return binProductId;
	}

	public void setBinProductId(String binProductId) {
		this.binProductId = binProductId;
	}

}