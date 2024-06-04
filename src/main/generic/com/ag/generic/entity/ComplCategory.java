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
 * The persistent class for the COMPL_CATEGORY database table.
 * 
 */
@Entity
@Table(name = "COMPL_CATEGORY")
@NamedQueries({
		@NamedQuery(name = "ComplCategory.fetchByID", query = "SELECT c from ComplCategory c where c.id=:id"),
		@NamedQuery(name = "ComplCategory.fetchByOtherParams", query = "SELECT c from ComplCategory c where c.code=:code AND c.typeCode=:typeCode"),     
		@NamedQuery(name = "ComplCategory.fetchAll", query = "SELECT c from ComplCategory c"),
		@NamedQuery(name = "ComplCategory.fetchAllByID", query = "SELECT c from ComplCategory c where c.id=:id")
		})
public class ComplCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String category;

	private String code;

	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;
	
	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "SUB_TYPE")
	private String subType;

	@Column(name = "SUB_TYPE_CODE")
	private String subTypeCode;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "TYPE_CODE")
	private String typeCode;

	public ComplCategory() {
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getSubType() {
		return this.subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSubTypeCode() {
		return this.subTypeCode;
	}

	public void setSubTypeCode(String subTypeCode) {
		this.subTypeCode = subTypeCode;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

}