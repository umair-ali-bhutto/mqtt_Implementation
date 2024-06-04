package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the SDISCOUNTS_DETAIL_SLABS database table.
 * 
 */
@Entity
@Table(name="SDISCOUNTS_DETAIL_SLABS")
@NamedQueries({
	@NamedQuery(name = "SDiscountsDetailSlab.getMaxId", query = "SELECT MAX(d.sourceId)+1 FROM SDiscountsDetailSlab d"),
	@NamedQuery(name = "SDiscountsDetailSlab.getMaxIdMssql", query = "SELECT MAX(CONVERT(numeric, d.sourceId))+1 FROM SDiscountsDetailSlab d"),
	@NamedQuery(name = "SDiscountsDetailSlab.getSlabByDiscountId", query = "SELECT d FROM SDiscountsDetailSlab d where d.discId in (:discId)")})
public class SDiscountsDetailSlab implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name="AMOUNT_SLAB")
	private double amountSlab;

	@Column(name="AMOUNT_FROM")
	private double amountFrom;

	@Column(name="AMOUNT_TO")
	private double amountTo;

	@Column(name="CORPID")
	private String corpid;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	@Column(name="DISC_ID")
	private String discId;

	@Column(name="MAX")
	private double max;

	@Column(name="MIN")
	private double min;

	@Column(name="PERC")
	private double perc;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;
	
	@Id	
	@Column(name="SOURCE_ID")
	private String sourceId;
	
	@Column(name="SOURCE_REF")
	private String sourceRef;

	public SDiscountsDetailSlab() {
	}

	public double getAmountFrom() {
		return this.amountFrom;
	}

	public void setAmountFrom(double amountFrom) {
		this.amountFrom = amountFrom;
	}

	public double getAmountSlab() {
		return this.amountSlab;
	}

	public void setAmountSlab(double amountSlab) {
		this.amountSlab = amountSlab;
	}

	public double getAmountTo() {
		return this.amountTo;
	}

	public void setAmountTo(double amountTo) {
		this.amountTo = amountTo;
	}

	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getCrBy() {
		return this.crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public Timestamp getCrOn() {
		return this.crOn;
	}

	public void setCrOn(Timestamp crOn) {
		this.crOn = crOn;
	}

	public String getDiscId() {
		return this.discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public double getMax() {
		return this.max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return this.min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getPerc() {
		return this.perc;
	}

	public void setPerc(double perc) {
		this.perc = perc;
	}

	public String getUpdBy() {
		return this.updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public Timestamp getUpdOn() {
		return this.updOn;
	}

	public void setUpdOn(Timestamp updOn) {
		this.updOn = updOn;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceRef() {
		return sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}
	
	

}