package com.ag.mportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the BATCH_SETTLEMENT database table.
 * 
 */
@Entity
@Table(name = "TAPNPAY_RESP_CODES_CONFIG")
@NamedQueries({
	
	@NamedQuery(name = "TapNPayResponseCodesConfig.retrieveAll", query = "SELECT a FROM TapNPayResponseCodesConfig a WHERE a.isActive=1 ")

})
public class TapNPayResponseCodesConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "CODE")
	private String code;

	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}