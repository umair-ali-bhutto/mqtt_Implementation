package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CAMPAIGN_FEATURES")
@NamedQueries({ @NamedQuery(name = "CampaignFeatures.fetchAllRecords", query = "SELECT a FROM CampaignFeatures a where a.corpId=:corpId"),
		@NamedQuery(name = "CampaignFeatures.fetchAllRecordByCampId", query = "SELECT a FROM CampaignFeatures a where a.corpId=:corpId and a.campId=:campId") })
public class CampaignFeatures implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Column(name = "CAMPID")
	private String campId;

	@Id
	@Column(name = "FEATUREID")
	private String featuredId;

	@Column(name = "FEATURE_TYPE")
	private String featureType;

	@Column(name = "AWARD_TYPE")
	private String awardType;

	@Column(name = "AWARD_ID")
	private String awardId;

	@Column(name = "ACCOUNT_DR")
	private String accountDr;

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

	public String getFeaturedId() {
		return featuredId;
	}

	public void setFeaturedId(String featuredId) {
		this.featuredId = featuredId;
	}

	public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
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