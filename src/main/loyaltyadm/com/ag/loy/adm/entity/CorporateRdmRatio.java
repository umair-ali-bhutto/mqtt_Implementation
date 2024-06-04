package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CORPORATE_RDM_RATIO database table.
 * 
 */
@Entity
@Table(name="CORPORATE_RDM_RATIO")
@NamedQuery(name="CorporateRdmRatio.findAll", query="SELECT c FROM CorporateRdmRatio c")
public class CorporateRdmRatio implements Serializable {
	private static final long serialVersionUID = 1L;

	private String active;

	private String corpid;

	@Column(name="CR_BY")
	private String crBy;

	private String currency;

	@Column(name="DATE_CR")
	private Timestamp dateCr;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_FROM")
	private Date dateFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_TO")
	private Date dateTo;

	@Column(name="DATE_UPD")
	private Timestamp dateUpd;

	@Column(name="RDM_RATIO")
	private BigDecimal rdmRatio;

	@Id
	@Column(name="REC_ID")
	private BigDecimal recId;

	@Column(name="UPD_BY")
	private String updBy;

	public CorporateRdmRatio() {
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Timestamp getDateCr() {
		return this.dateCr;
	}

	public void setDateCr(Timestamp dateCr) {
		this.dateCr = dateCr;
	}

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Timestamp getDateUpd() {
		return this.dateUpd;
	}

	public void setDateUpd(Timestamp dateUpd) {
		this.dateUpd = dateUpd;
	}

	public BigDecimal getRdmRatio() {
		return this.rdmRatio;
	}

	public void setRdmRatio(BigDecimal rdmRatio) {
		this.rdmRatio = rdmRatio;
	}

	public BigDecimal getRecId() {
		return this.recId;
	}

	public void setRecId(BigDecimal recId) {
		this.recId = recId;
	}

	public String getUpdBy() {
		return this.updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

}