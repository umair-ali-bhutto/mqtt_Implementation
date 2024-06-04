package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CURRENCY_MASTER")
@NamedQueries({ @NamedQuery(name = "CurrencyMaster.fetchAllRecordById", query = "SELECT a FROM CurrencyMaster a  where a.corpId=:corpId and a.currency=:currency"),
		@NamedQuery(name = "CurrencyMaster.fetchAllRecord", query = "SELECT a FROM CurrencyMaster a where a.corpId=:corpId") })
public class CurrencyMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String currency;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "MAPPED_TO")
	private String mappedTo;

	@Column(name = "MAPPED_ID")
	private String mappedID;

	@Column(name = "CORPID")
	private String corpId;

	@Column(name = "ACTIVE")
	private String active;

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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public String getMappedTo() {
		return mappedTo;
	}

	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}

	public String getMappedID() {
		return mappedID;
	}

	public void setMappedID(String mappedID) {
		this.mappedID = mappedID;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
}