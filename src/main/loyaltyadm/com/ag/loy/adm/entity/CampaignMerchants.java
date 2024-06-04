package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CAMPAIGN_MERCHANTS")
@NamedQueries({ @NamedQuery(name = "CampaignMerchants.fetchAllRecordByCampId", query = "SELECT a FROM CampaignMerchants a where a.corpId=:corpId and a.campId=:campId"),
		@NamedQuery(name = "CampaignMerchants.fetchAllRecords", query = "SELECT a FROM CampaignMerchants a where a.corpId=:corpId") })
public class CampaignMerchants implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CAMPID")
	private String campId;

	@Column(name = "MID")
	private String mId;

	@Column(name = "AWARD_TYPE")
	private String awardType;

	@Column(name = "AWARD_ID")
	private String awardId;

	@Column(name = "ACCOUNT_DR")
	private String accountDr;

	@Column(name = "PERC_SHARED")
	private double percShared;

	@Column(name = "CORPID")
	private String corpId;

	@Column(name = "CR_BY")
	private String createdBy;

	@Column(name = "CR_ON")
	private java.util.Date createdOn;

	@Column(name = "UPD_BY")
	private String updatedBy;

	@Column(name = "UPD_ON")
	private java.util.Date updateOn;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCampId() {
		return campId;
	}

	public void setCampId(String campId) {
		this.campId = campId;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
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

	public double getPercShared() {
		return percShared;
	}

	public void setPercShared(double percShared) {
		this.percShared = percShared;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
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
}