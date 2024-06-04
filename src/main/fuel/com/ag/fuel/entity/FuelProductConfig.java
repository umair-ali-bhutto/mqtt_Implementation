package com.ag.fuel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the FUEL_PRODUCT_CONFIG database table.
 * 
 */
@Entity
@Table(name = "FUEL_PRODUCT_CONFIG")
@NamedQuery(name = "FuelProductConfig.fetchAll", query = "SELECT d FROM FuelProductConfig d WHERE d.isActive = 1")
@NamedQuery(name = "FuelProductConfig.fetchAllByCorpId", query = "SELECT d FROM FuelProductConfig d WHERE d.isActive = 1 AND d.corpId=:corpId")
public class FuelProductConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUEL_PRODUCT_CONFIG_SEQ")
	@SequenceGenerator(name = "FUEL_PRODUCT_CONFIG_SEQ", sequenceName = "FUEL_PRODUCT_CONFIG_SEQ", allocationSize = 1)
	private int id;

	@Column(name = "CORP_ID")
	private String corpId;

	@Column(name = "PROD_CODE")
	private String prodCode;

	@Column(name = "PROD_NAME")
	private String prodName;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "THEME")
	private String theme;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
