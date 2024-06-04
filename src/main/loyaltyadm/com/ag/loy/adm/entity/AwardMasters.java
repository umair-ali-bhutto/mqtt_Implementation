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
@Table(name = "AWARDS_MASTER")
@NamedQueries({ @NamedQuery(name = "AwardMasters.fetchAllRecordById", query = "SELECT a FROM AwardMasters a where a.corpId=:corpId and a.mAwardId=:mAwardId"),
		@NamedQuery(name = "AwardMasters.fetchByAwardName", query = "SELECT a FROM AwardMasters a where a.corpId=:corpId and a.description=:description"),
		@NamedQuery(name = "AwardMasters.fetchAllRecords", query = "SELECT a FROM AwardMasters a where a.corpId=:corpId ORDER BY mAwardId"),
		@NamedQuery(name = "AwardMasters.fetchAllActiveRecords", query = "SELECT a FROM AwardMasters a where a.corpId=:corpId and active='Y' ORDER BY mAwardId ")})
public class AwardMasters implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MAWARD_ID")
	private Long mAwardId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "EXP_DAYS")
	private Long expDays;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "ACCOUNT_DR")
	private String accountDr;

	@Column(name = "CORPID")
	private String corpId;

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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getmAwardId() {
		return mAwardId;
	}

	public void setmAwardId(Long mAwardId) {
		this.mAwardId = mAwardId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getExpDays() {
		return expDays;
	}

	public void setExpDays(Long expDays) {
		this.expDays = expDays;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

}