package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "CAMPAIGN_MASTER")
@NamedQueries({
		@NamedQuery(name = "CampaignMaster.fetchAllRecords", query = "SELECT a FROM CampaignMaster a where a.corpId=:corpId order by a.campId desc"),
		@NamedQuery(name = "CampaignMaster.fetchAllRecordById", query = "SELECT a FROM CampaignMaster a where a.corpId=:corpId and a.campId=:campId") })
public class CampaignMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CAMPID")
	private String campId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CAMPAIGN_TYPE")
	private String campaignType;

	@Column(name = "CAMPAIGN_SUB_TYPE")
	private String campaignSubType;

	@Column(name = "ALL_MERCHANTS")
	private String allMerchants;

	@Column(name = "ACC_FLG")
	private String accFlg;

	@Column(name = "EXP_FLG")
	private String expFlg;

	@Column(name = "AUT_EXP")
	private String autExp;

	@Column(name = "BUDGET")
	private String budget;

	@Column(name = "BUDGET_USED")
	private String budgetUsed;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Karachi")
	@Column(name = "DATE_FROM")
	private Timestamp dateFrom;

	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Karachi")
	@Column(name = "DATE_TO")
	private Timestamp toDate;

	@Column(name = "ACCOUNT_DR")
	private String accountDr;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "AWARD_TYPE")
	private String awardType;

	@Column(name = "AWARD_ID")
	private String awardId;

	@Column(name = "CORPID")
	private String corpId;

	@Column(name = "CR_BY")
	private String createdBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Karachi")
	@Column(name = "CR_ON")
	private java.util.Date createdOn;

	@Column(name = "UPD_BY")
	private String updatedBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Karachi")
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}

	public String getCampaignSubType() {
		return campaignSubType;
	}

	public void setCampaignSubType(String campaignSubType) {
		this.campaignSubType = campaignSubType;
	}

	public String getAllMerchants() {
		return allMerchants;
	}

	public void setAllMerchants(String allMerchants) {
		this.allMerchants = allMerchants;
	}

	public String getAccFlg() {
		return accFlg;
	}

	public void setAccFlg(String accFlg) {
		this.accFlg = accFlg;
	}

	public String getExpFlg() {
		return expFlg;
	}

	public void setExpFlg(String expFlg) {
		this.expFlg = expFlg;
	}

	public String getAutExp() {
		return autExp;
	}

	public void setAutExp(String autExp) {
		this.autExp = autExp;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getBudgetUsed() {
		return budgetUsed;
	}

	public void setBudgetUsed(String budgetUsed) {
		this.budgetUsed = budgetUsed;
	}

	public Timestamp getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Timestamp dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Timestamp getToDate() {
		return toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}

	public String getAccountDr() {
		return accountDr;
	}

	public void setAccountDr(String accountDr) {
		this.accountDr = accountDr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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