package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PRODUCT_MASTER")
@NamedQueries({
		@NamedQuery(name = "ProductMaster.retrieveAll", query = "SELECT a FROM ProductMaster a WHERE a.corpId=:corpId ORDER BY name"),
		@NamedQuery(name = "ProductMaster.retrieveById", query = "SELECT a FROM ProductMaster a WHERE a.corpId=:corpId AND a.productId=:productId") })
public class ProductMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String productId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ASSOCIATION")
	private String accociation;

	@Column(name = "MAPPED_TO")
	private String mappedTo;

	@Column(name = "MAPPED_ID")
	private String mappedId;

	@Column(name = "ACCOUNT_CR")
	private String accountCr;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "CR_BY")
	private String crBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "CR_ON")
	private java.util.Date crOn;

	@Column(name = "UPD_BY")
	private String updBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPD_ON")
	private java.util.Date updOn;

	@Column(name = "CORPID")
	private String corpId;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccociation() {
		return accociation;
	}

	public void setAccociation(String accociation) {
		this.accociation = accociation;
	}

	public String getMappedTo() {
		return mappedTo;
	}

	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}

	public String getMappedId() {
		return mappedId;
	}

	public void setMappedId(String mappedId) {
		this.mappedId = mappedId;
	}

	public String getAccountCr() {
		return accountCr;
	}

	public void setAccountCr(String accountCr) {
		this.accountCr = accountCr;
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

	public java.util.Date getCrOn() {
		return crOn;
	}

	public void setCrOn(java.util.Date crOn) {
		this.crOn = crOn;
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

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
}