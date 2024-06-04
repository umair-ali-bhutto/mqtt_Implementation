package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CAMPAIGN_CALENDAR_PRODUCTS")
@NamedQueries({
		@NamedQuery(name = "CampaignCalenderProduct.fetchAllRecords", query = "SELECT a FROM CampaignCalenderProduct a where a.id.corpid=:corpId"),
		@NamedQuery(name = "CampaignCalenderProduct.fetchAllRecordByCampId", query = "SELECT a FROM CampaignCalenderProduct a where a.id.corpid=:corpId and a.id.campid=:campID") })
public class CampaignCalenderProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CampaignCalenderProductPK id;

	@Column(name = "DATE_FROM")
	private java.util.Date dateFrom;

	@Column(name = "DATE_TO")
	private java.util.Date dateTo;

	@Column(name = "AWARD_TYPE")
	private String awardType;

	@Column(name = "AWARD_ID")
	private String awardId;

	@Column(name = "ACCOUNT_DR")
	private String accountDr;

	@Column(name = "PERC_SHARED")
	private String percShared;

	@Column(name = "CR_BY")
	private String createdBy;

	@Column(name = "CR_ON")
	private java.util.Date createdOn;

	@Column(name = "UPD_BY")
	private String updBy;

	@Column(name = "UPD_ON")
	private java.util.Date updOn;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CampaignCalenderProductPK getId() {
		return id;
	}

	public void setId(CampaignCalenderProductPK id) {
		this.id = id;
	}

	public java.util.Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(java.util.Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public java.util.Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(java.util.Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getAwardId() {
		return awardId;
	}

	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}

	public String getAccountDr() {
		return accountDr;
	}

	public void setAccountDr(String accountDr) {
		this.accountDr = accountDr;
	}

	public String getPercShared() {
		return percShared;
	}

	public void setPercShared(String percShared) {
		this.percShared = percShared;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public java.util.Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdBy() {
		return updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public java.util.Date getUpdOn() {
		return updOn;
	}

	public void setUpdOn(java.util.Date updOn) {
		this.updOn = updOn;
	}
}