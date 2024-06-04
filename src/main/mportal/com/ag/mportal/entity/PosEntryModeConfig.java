package com.ag.mportal.entity;

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


@Entity
@Table(name = "POS_ENTRY_MODE_CONFIG")
@NamedQueries({
	@NamedQuery(name = "PEMConfig.fetchPemConfigByMode", query = "SELECT pem FROM PosEntryModeConfig pem WHERE pem.posEntryMode=:posEntryMode AND pem.isActive=1 "),
	@NamedQuery(name = "PEMConfig.fetchAll", query = "SELECT p FROM PosEntryModeConfig p WHERE p.isActive=1 ")})
public class PosEntryModeConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "POS_ENTRY_MODE")
	private String posEntryMode;
	
	@Column(name = "POS_ENTRY_VALUE")
	private String posEntryValue;

	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;
	
	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	private Integer isActive;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPosEntryMode() {
		return posEntryMode;
	}
	public void setPosEntryMode(String posEntryMode) {
		this.posEntryMode = posEntryMode;
	}
	public String getPosEntryValue() {
		return posEntryValue;
	}
	public void setPosEntryValue(String posEntryValue) {
		this.posEntryValue = posEntryValue;
	}
	public Timestamp getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}