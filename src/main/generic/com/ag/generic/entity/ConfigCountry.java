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
 * The persistent class for the CONFIG_COUNTRY database table.
 * 
 */
@Entity
@Table(name = "CONFIG_COUNTRY")
@NamedQueries({ 
		@NamedQuery(name = "ConfigCountry.findAll", query = "SELECT d FROM ConfigCountry d WHERE d.corpId=:corpId order by d.id asc"),
		@NamedQuery(name = "ConfigCountry.findByCode", query = "SELECT d FROM ConfigCountry d where d.code=:code and d.corpId=:corpId order by d.id asc")
		})
public class ConfigCountry implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "CONFIG_COUNTRY_SEQ")
	@SequenceGenerator(name="CONFIG_COUNTRY_SEQ",sequenceName="CONFIG_COUNTRY_SEQ", allocationSize=1)
	private int id;

	private String code;

	

	private String name;
	
	@Column(name = "CORP_ID")
	private String corpId;
	
	

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	
	

	public ConfigCountry() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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