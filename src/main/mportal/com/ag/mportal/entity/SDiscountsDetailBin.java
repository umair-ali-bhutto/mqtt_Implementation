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
 * The persistent class for the SDISCOUNTS_DETAIL_BINS database table.
 * 
 */
@Entity
@Table(name="SDISCOUNTS_DETAIL_BINS")
@NamedQueries({
	@NamedQuery(name = "SDiscountsDetailBin.getMaxId", query = "SELECT MAX(d.sourceId)+1 FROM SDiscountsDetailBin d"),
	@NamedQuery(name = "SDiscountsDetailBin.getMaxIdMssql", query = "SELECT MAX(CONVERT(numeric, d.sourceId))+1 FROM SDiscountsDetailBin d"),
	@NamedQuery(name = "SDiscountsDetailBin.getLstBinByDiscountId", query = "SELECT d FROM SDiscountsDetailBin d where d.discId in (:lstDiscountId) AND d.active='Y' order by d.discId asc, d.binNo asc"),
	@NamedQuery(name = "SDiscountsDetailBin.deleteByDiscountId", query = "UPDATE SDiscountsDetailBin d set d.active='N', d.updBy=:updBy,d.updOn=:updOn where d.discId=:discId")})
public class SDiscountsDetailBin implements Serializable {
	private static final long serialVersionUID = 1L;
	

	
	@Column(name="BIN_NO")
	private short binNo;

	@Column(name="ACTIVE")
	private String active;

	@Column(name="BIN_DESC")
	private String binDesc;

	@Column(name="BIN_END")
	private int binEnd;

	@Column(name="BIN_START")
	private int binStart;

	@Column(name="CORPID")
	private String corpid;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	@Column(name="DISC_ID")
	private String discId;

	@Column(name="SOURCE_REF")
	private String sourceRef;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;
	
	@Id
	@Column(name="SOURCE_ID")
	private String sourceId;

	public SDiscountsDetailBin() {
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getBinDesc() {
		return this.binDesc;
	}

	public void setBinDesc(String binDesc) {
		this.binDesc = binDesc;
	}

	public int getBinEnd() {
		return this.binEnd;
	}

	public void setBinEnd(int binEnd) {
		this.binEnd = binEnd;
	}

	public short getBinNo() {
		return this.binNo;
	}

	public void setBinNo(short binNo) {
		this.binNo = binNo;
	}

	public int getBinStart() {
		return this.binStart;
	}

	public void setBinStart(int binStart) {
		this.binStart = binStart;
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

	public String getSourceRef() {
		return this.sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
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

}