package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the CONFIG_PROPERTIES database table.
 * 
 */
@Entity
@Table(name = "QR_CONFIG")
@NamedQuery(name = "QrConfig.findAll", query = "SELECT d FROM QrConfig d WHERE d.isActive = 1 AND d.corpId=:corpId")
@NamedQuery(name = "QrConfig.findAllByMid", query = "SELECT d FROM QrConfig d WHERE d.isActive = 1 AND d.mid=:mid AND d.corpId=:corpId")
public class QrConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "CORP_ID")
	private String corpId;

	@Column(name = "PROP_KEY")
	private String propKey;
	
	
	@Column(name = "MID")
	private String mid;
	
	
	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "PROP_VALUE")
	private String propValue;
	

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;


	public QrConfig() {
	}


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


	public String getPropKey() {
		return propKey;
	}


	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}


	public String getMid() {
		return mid;
	}


	public void setMid(String mid) {
		this.mid = mid;
	}


	public int getIsActive() {
		return isActive;
	}


	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}


	public String getPropValue() {
		return propValue;
	}


	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}


	public Timestamp getEntryDate() {
		return entryDate;
	}


	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}


} 