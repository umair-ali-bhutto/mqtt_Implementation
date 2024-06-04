package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "AWARDS_POINTS")
@NamedQueries({ @NamedQuery(name = "AwardPoints.fetchRecordById", query = "SELECT a FROM AwardPoints a where a.corpid=:corpId and a.MAWARD_ID=:mawardId") })
public class AwardPoints implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Column(name = "MAWARD_ID")
	private String MAWARD_ID;

	@Id
	@Column(name = "AMOUNT_SLAB")
	private String amountSlab;

	@Column(name = "AMOUNT_FROM")
	private String amountFrom;

	@Column(name = "AMOUNT_TO")
	private String amountTo;

	@Column(name = "PERC")
	private String prec;

	@Column(name = "MIN")
	private String min;

	@Column(name = "MAX")
	private String max;

	@Column(name = "EXP_DAYS")
	private String expDays;

	@Column(name = "ACCOUNT_DR")
	private String accountDr;

	@Column(name = "CR_BY")
	private String createdBY;

	@Column(name = "CR_ON")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Karachi")
	private java.util.Date createdOn;

	@Column(name = "UPD_BY")
	private String updatedBy;

	@Column(name = "UPD_ON")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Karachi")
	private java.util.Date updateOn;
	
	@Column(name = "CORPID")
	private String corpid;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMAWARD_ID() {
		return MAWARD_ID;
	}

	public void setMAWARD_ID(String mAWARD_ID) {
		MAWARD_ID = mAWARD_ID;
	}

	public String getAmountSlab() {
		return amountSlab;
	}

	public void setAmountSlab(String amountSlab) {
		this.amountSlab = amountSlab;
	}

	public String getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(String amountFrom) {
		this.amountFrom = amountFrom;
	}

	public String getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(String amountTo) {
		this.amountTo = amountTo;
	}

	public String getPrec() {
		return prec;
	}

	public void setPrec(String prec) {
		this.prec = prec;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getExpDays() {
		return expDays;
	}

	public void setExpDays(String expDays) {
		this.expDays = expDays;
	}

	public String getAccountDr() {
		return accountDr;
	}

	public void setAccountDr(String accountDr) {
		this.accountDr = accountDr;
	}

	public String getCreatedBY() {
		return createdBY;
	}

	public void setCreatedBY(String createdBY) {
		this.createdBY = createdBY;
	}

	public java.util.Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public java.util.Date getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(java.util.Date updateOn) {
		this.updateOn = updateOn;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
}