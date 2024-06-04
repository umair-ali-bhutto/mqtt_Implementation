package com.ag.mportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the DISC_BIN_PROD_CONFIG database table.
 * 
 */
@Entity
@Table(name = "DISC_BIN_PROD_CONFIG")
@NamedQueries({
		@NamedQuery(name = "DiscountBinProdConfig.fetchByCorpId", query = "SELECT f FROM DiscountBinProdConfig f where f.corpId= :corpId and f.isActive =1") })
public class DiscountBinProdConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISC_BIN_PROD_CONFIG_SEQ")
	@SequenceGenerator(name = "DISC_BIN_PROD_CONFIG_SEQ", sequenceName = "DISC_BIN_PROD_CONFIG_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "CORP_ID")
	private String corpId;

	@Column(name = "BIN")
	private String bin;

	@Column(name = "PRODUCT_ID")
	private String productId;

	@Column(name = "PRODUCT_VALUE")
	private String productValue;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	public long getId() {
		return id;
	}

	public String getCorpId() {
		return corpId;
	}

	public String getBin() {
		return bin;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductValue() {
		return productValue;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProductValue(String productValue) {
		this.productValue = productValue;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

}
