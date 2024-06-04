package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the CONFIG_CORPORATES database table.
 * 
 */
@Entity
@Table(name="CONFIG_CORPORATES")

@NamedQueries({ @NamedQuery(name = "ConfigCorporate.findAll", query = "SELECT c FROM ConfigCorporate c"),
		@NamedQuery(name = "ConfigCorporate.fetchByCorpId", query = "SELECT c FROM ConfigCorporate c WHERE c.isActive = 1 AND c.corpId=:corpId"),
	@NamedQuery(name = "ConfigCorporate.fetchCorporateByPkgName", query = "SELECT c FROM ConfigCorporate c where c.packageName=:pkgName")})
public class ConfigCorporate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="CORP_ID")
	private String corpId;

	@Column(name="CORP_NAME")
	private String corpName;

	@Column(name="ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name="IS_ACTIVE")
	private Integer isActive;

	@Column(name="PACKAGE_NAME")
	private String packageName;

	@Column(name="TYPE")
	private String type;

	public ConfigCorporate() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCorpId() {
		return this.corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCorpName() {
		return this.corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public Integer getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}