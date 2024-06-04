package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the LOV_MASTER database table.
 * 
 */
@Entity
@Table(name = "LOV_MASTER")
@NamedQueries({ @NamedQuery(name = "Lov.fetchLovs", query = "SELECT a FROM LovMaster a WHERE a.corpId=:corpId and a.isActive=:isActive"),
		@NamedQuery(name = "LovMaster.fetchLovsbyId", query = "SELECT a FROM LovMaster a WHERE a.corpId=:corpId and a.id=:id and a.isActive=:isActive") })
public class LovMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "VALUE")
	private String value;
	@Column(name = "CORP_ID")
	private String corpId;
	
	

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public LovMaster() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}