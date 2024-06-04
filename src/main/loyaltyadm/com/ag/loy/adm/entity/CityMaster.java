package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CITY_MASTER database table.
 * 
 */
@Entity
@Table(name="CITY_MASTER")
@NamedQuery(name="CityMaster.fetchById", query="SELECT c FROM CityMaster c WHERE c.cityId=:cityId and c.corpid=:corpid")
@NamedQuery(name="CityMaster.fetchAllByCorpId", query="SELECT c FROM CityMaster c where c.corpid=:corpid")
public class CityMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	private String active;

	@Id
	@Column(name="CITY_ID")
	private String cityId;

	@Column(name="CITY_NAME")
	private String cityName;

	private String corpid;

	@Column(name="CR_BY")
	private String crBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CR_ON")
	private Date crOn;

	@Column(name="DEF_LANGUAGE")
	private String defLanguage;

	@Column(name="MAPPED_ID")
	private String mappedId;

	private BigDecimal population;

	@Column(name="PRI_LANGUAGE")
	private String priLanguage;

	private String province;

	@Column(name="SEC_LANGUAGE")
	private String secLanguage;

	@Column(name="UPD_BY")
	private String updBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_ON")
	private Date updOn;

	public CityMaster() {
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCityId() {
		return this.cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getCrBy() {
		return this.crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public Date getCrOn() {
		return this.crOn;
	}

	public void setCrOn(Date crOn) {
		this.crOn = crOn;
	}

	public String getDefLanguage() {
		return this.defLanguage;
	}

	public void setDefLanguage(String defLanguage) {
		this.defLanguage = defLanguage;
	}

	public String getMappedId() {
		return this.mappedId;
	}

	public void setMappedId(String mappedId) {
		this.mappedId = mappedId;
	}

	public BigDecimal getPopulation() {
		return this.population;
	}

	public void setPopulation(BigDecimal population) {
		this.population = population;
	}

	public String getPriLanguage() {
		return this.priLanguage;
	}

	public void setPriLanguage(String priLanguage) {
		this.priLanguage = priLanguage;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSecLanguage() {
		return this.secLanguage;
	}

	public void setSecLanguage(String secLanguage) {
		this.secLanguage = secLanguage;
	}

	public String getUpdBy() {
		return this.updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public Date getUpdOn() {
		return this.updOn;
	}

	public void setUpdOn(Date updOn) {
		this.updOn = updOn;
	}

}