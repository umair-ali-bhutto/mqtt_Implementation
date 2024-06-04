package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CAMPAIGN_CALENDAR")
@NamedQueries({ @NamedQuery(name = "CampaignCalender.fetchAllRecordByCampId", query = "SELECT a FROM CampaignCalender a where a.corpId=:corpId and a.campID=:campID") })
public class CampaignCalender implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Column(name = "CAMPID")
	private String campID;

	@Id
	@Column(name = "CALID")
	private String calId;

	@Column(name = "DESCRIPTION")
	private String descrption;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "DATE_FROM")
	private java.sql.Timestamp dateFrom;

	@Column(name = "DATE_TO")
	private java.sql.Timestamp dateTo;

	@Column(name = "KYC")
	private String kyc;

	@Column(name = "AWARD_ID")
	private String awardId;

	@Column(name = "ACCOUNT_DR")
	private String accountDr;

	@Column(name = "CORPID")
	private String corpId;

	@Column(name = "CR_BY")
	private String crBy;

	@Column(name = "CR_ON")
	private java.sql.Date crOn;

	@Column(name = "UPD_BY")
	private String updBy;

	@Column(name = "UPD_ON")
	private java.sql.Date updOn;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCampID() {
		return campID;
	}

	public void setCampID(String campID) {
		this.campID = campID;
	}

	public String getCalId() {
		return calId;
	}

	public void setCalId(String calId) {
		this.calId = calId;
	}

	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public java.sql.Timestamp getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(java.sql.Timestamp dateFrom) {
		this.dateFrom = dateFrom;
	}

	public java.sql.Timestamp getDateTo() {
		return dateTo;
	}

	public void setDateTo(java.sql.Timestamp dateTo) {
		this.dateTo = dateTo;
	}

	public String getKyc() {
		return kyc;
	}

	public void setKyc(String kyc) {
		this.kyc = kyc;
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

	public String getCrBy() {
		return crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public java.sql.Date getCrOn() {
		return crOn;
	}

	public void setCrOn(java.sql.Date crOn) {
		this.crOn = crOn;
	}

	public String getUpdBy() {
		return updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public java.sql.Date getUpdOn() {
		return updOn;
	}

	public void setUpdOn(java.sql.Date updOn) {
		this.updOn = updOn;
	}
}