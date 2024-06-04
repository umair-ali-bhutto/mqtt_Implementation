package com.ag.loy.adm.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the ACCOUNT_MASTER database table.
 * 
 */
@Entity
@Table(name = "LOYALTY_ERROR_MAPPING")
@NamedQuery(name = "LoyaltyErrorMapping.fetchByCode", query = "SELECT a FROM LoyaltyErrorMapping a WHERE a.code =:code and a.isActive = 1")
public class LoyaltyErrorMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "CODE")
	private String code;

	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
