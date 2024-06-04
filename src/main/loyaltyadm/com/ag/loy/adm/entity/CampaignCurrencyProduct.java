package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CAMPAIGN_CURRENCY_PRODUCTS")
@NamedQueries({
		@NamedQuery(name = "CampaignCurrencyProduct.fetchAllRecords", query = "SELECT a FROM CampaignCurrencyProduct a where a.id.corpid=:corpId"),
		@NamedQuery(name = "CampaignCurrencyProduct.fetchAllRecordByCampId", query = "SELECT a FROM CampaignCurrencyProduct a where a.id.corpid=:corpId and a.id.campid=:campID") })
public class CampaignCurrencyProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CampaignCurrencyProductPK id;

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

	public CampaignCurrencyProductPK getId() {
		return id;
	}

	public void setId(CampaignCurrencyProductPK id) {
		this.id = id;
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