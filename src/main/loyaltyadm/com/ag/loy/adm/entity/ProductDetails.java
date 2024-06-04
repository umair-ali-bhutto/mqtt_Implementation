package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT_DETAIL")
@NamedQueries({ @NamedQuery(name = "ProductDetails.retrieveAll", query = "SELECT a FROM ProductDetails a") })
public class ProductDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String productId;

	@Column(name = "BIN_START")
	private String binStart;

	@Column(name = "BIN_END")
	private String binEnd;
	
	@Column(name = "CORPID")
	private String corpId;

	@Column(name = "MAPPED_ID")
	private String mappedId;

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getBinStart() {
		return binStart;
	}

	public void setBinStart(String binStart) {
		this.binStart = binStart;
	}

	public String getBinEnd() {
		return binEnd;
	}

	public void setBinEnd(String binEnd) {
		this.binEnd = binEnd;
	}

	public String getMappedId() {
		return mappedId;
	}

	public void setMappedId(String mappedId) {
		this.mappedId = mappedId;
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