package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the LOV_DETAILS database table.
 * 
 */
@Entity
@Table(name = "LOV_DETAILS")
@NamedQuery(name = "LovDetail.findAll", query = "SELECT l FROM LovDetail l WHERE l.corpId=:corpId and l.isActive=:isActive")
@NamedQuery(name = "LovDetail.fetchLovsDetails", query = "SELECT l FROM LovDetail l WHERE l.lovId =:lovId AND l.isActive=:isActive and l.corpId=:corpId")
@NamedQuery(name = "LovDetail.fetchLovsDetailsbyId", query = "SELECT l FROM LovDetail l WHERE l.lovId =:lovId AND l.isActive=:isActive and l.corpId=:corpId")
@NamedQuery(name = "LovDetail.fetchLovsDetailsByLovIDandLovDetailID", query = "SELECT l FROM LovDetail l WHERE l.id=:id AND l.isActive=:isActive and l.corpId=:corpId")
@NamedQuery(name = "LovDetail.fetchLovsDetailsbyLovId", query = "SELECT l FROM LovDetail l WHERE l.id=:id AND l.isActive=:isActive and l.corpId=:corpId")
public class LovDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Id
	@Column(name = "KEY_ID")
	private int key;

	
	@Column(name = "ID")
	private String id;

	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "LOV_ID")
	private String lovId;

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

	public LovDetail() {
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getLovId() {
		return this.lovId;
	}

	public void setLovId(String lovId) {
		this.lovId = lovId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}