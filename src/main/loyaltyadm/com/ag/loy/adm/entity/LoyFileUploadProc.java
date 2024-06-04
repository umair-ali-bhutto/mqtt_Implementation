package com.ag.loy.adm.entity;

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
 * The persistent class for the LOY_FILE_UPLOAD_PROP database table.
 * 
 */
@Entity
@Table(name = "LOY_FILE_UPLOAD_PROP")
@NamedQuery(name = "LoyFileUploadProc.fetchAllByCorpId", query = "SELECT a FROM LoyFileUploadProc a WHERE a.corpId =:corpId AND a.type =:type AND a.isActive = 1 ORDER BY a.id")
public class LoyFileUploadProc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOY_FILE_UPLOAD_PROP_SEQ")
	@SequenceGenerator(name = "LOY_FILE_UPLOAD_PROP_SEQ", sequenceName = "LOY_FILE_UPLOAD_PROP_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "FIELD")
	private String field;

	@Column(name = "FROM_LEN")
	private int fromLen;

	@Column(name = "TO_LEN")
	private int toLen;

	@Column(name = "LENGTH")
	private int length;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "KYC_MAPPING")
	private String kycMapping;

	@Column(name = "VALIDATOR")
	private String validator;

	@Column(name = "IS_MENDATORY")
	private int isMendatory;

	@Column(name = "CORP_ID")
	private String corpId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getFromLen() {
		return fromLen;
	}

	public void setFromLen(int fromLen) {
		this.fromLen = fromLen;
	}

	public int getToLen() {
		return toLen;
	}

	public void setToLen(int toLen) {
		this.toLen = toLen;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getKycMapping() {
		return kycMapping;
	}

	public void setKycMapping(String kycMapping) {
		this.kycMapping = kycMapping;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public int getIsMendatory() {
		return isMendatory;
	}

	public void setIsMendatory(int isMendatory) {
		this.isMendatory = isMendatory;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

}