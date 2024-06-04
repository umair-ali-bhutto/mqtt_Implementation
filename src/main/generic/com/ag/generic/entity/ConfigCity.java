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
 * The persistent class for the CONFIG_CITY database table.
 * 
 */
@Entity
@Table(name = "CONFIG_CITY")
@NamedQueries({ 
		@NamedQuery(name = "ConfigCity.findAll", query = "SELECT d FROM ConfigCity d WHERE d.corpId=:corpId order by d.name asc"),
		@NamedQuery(name = "ConfigCity.findByCode", query = "SELECT d FROM ConfigCity d where d.code=:code and d.corpId=:corpId order by d.id asc"),
		@NamedQuery(name = "ConfigCity.findByCountryCode", query = "SELECT d FROM ConfigCity d where d.countryCode=:countryCode and d.corpId=:corpId order by d.id asc"),
		@NamedQuery(name = "ConfigCity.findByRegionCode", query = "SELECT d FROM ConfigCity d where d.regionCode=:regionCode and d.corpId=:corpId order by d.id asc"),
		@NamedQuery(name = "ConfigCity.findByCountryCodeAndRegionCode", query = "SELECT d FROM ConfigCity d where d.regionCode=:regionCode AND d.countryCode=:countryCode and d.corpId=:corpId order by d.id asc")
		})
public class ConfigCity implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "CONFIG_CITY_SEQ")
	@SequenceGenerator(name="CONFIG_CITY_SEQ",sequenceName="CONFIG_CITY_SEQ", allocationSize=1)
	private int id;
	
	
	private String code;

	@Column(name = "COUNTRY_CODE")
	private String countryCode;


	private String name;

	@Column(name = "REGION_CODE")
	private String regionCode;
	@Column(name = "CORP_ID")
	private String corpId;
	
	

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public ConfigCity() {
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

	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

}