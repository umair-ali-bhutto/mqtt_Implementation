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
 * The persistent class for the DISCOUNT_SLAB database table.
 * 
 */
@Entity
@Table(name = "DISC_SLAB")
@NamedQueries({
		@NamedQuery(name = "DiscountSlab.getSlabByDisctId", query = "SELECT d FROM DiscountSlab d where d.discId in (:discId)"),
		@NamedQuery(name = "DiscountSlab.fetchById", query = "SELECT a FROM DiscountSlab a WHERE a.discId=:discId")})
public class DiscountSlab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISC_SLAB_SEQ")
	@SequenceGenerator(name = "DISC_SLAB_SEQ", sequenceName = "DISC_SLAB_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "DISC_ID")
	private long discId;

	@Column(name = "AMOUNT_FROM")
	private double amountFrom;

	@Column(name = "AMOUNT_TO")
	private double amountTo;

	@Column(name = "DISC_TYPE")
	private String discType;

	@Column(name = "DISC_VALUE")
	private double discValue;

	@Column(name = "MAX_DISCOUNT_PER_TXN")
	private double maxDiscountPerTxn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(double amountFrom) {
		this.amountFrom = amountFrom;
	}

	public double getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(double amountTo) {
		this.amountTo = amountTo;
	}

	public String getDiscType() {
		return discType;
	}

	public void setDiscType(String discType) {
		this.discType = discType;
	}

	public double getDiscValue() {
		return discValue;
	}

	public void setDiscValue(double discValue) {
		this.discValue = discValue;
	}

	public double getMaxDiscountPerTxn() {
		return maxDiscountPerTxn;
	}

	public void setMaxDiscountPerTxn(double maxDiscountPerTxn) {
		this.maxDiscountPerTxn = maxDiscountPerTxn;
	}

	public long getDiscId() {
		return discId;
	}

	public void setDiscId(long discId) {
		this.discId = discId;
	}

}