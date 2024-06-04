package com.ag.generic.entity;

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
 * The persistent class for the CONFIG_REGION database table.
 * 
 */
@Entity
@Table(name = "CONFIG_REGION")
@NamedQueries({ 
		@NamedQuery(name = "ConfigRegion.findAll", query = "SELECT d FROM ConfigRegion d where d.corpId=:corpId order by d.id asc"),
		@NamedQuery(name = "ConfigRegion.findByCode", query = "SELECT d FROM ConfigRegion d where d.code=:code and d.corpId=:corpId order by d.id asc"),
		@NamedQuery(name = "ConfigRegion.findByCountryCode", query = "SELECT d FROM ConfigRegion d where d.countryCode=:countryCode and d.corpId=:corpId order by d.id asc")
		})
public class ConfigRegion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "CONFIG_REGION_SEQ")
	@SequenceGenerator(name="CONFIG_REGION_SEQ",sequenceName="CONFIG_REGION_SEQ", allocationSize=1)
	private int id;

	private String code;

	@Column(name = "COUNTRY_CODE")
	private String countryCode;


	private String name;
	@Column(name = "CORP_ID")
	private String corpId;
	
	

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public ConfigRegion() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}