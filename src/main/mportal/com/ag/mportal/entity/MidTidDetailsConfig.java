package com.ag.mportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the MID_TID_DETAILS_CONFIG database table.
 * 
 */
@Entity
@Table(name = "MID_TID_DETAILS_CONFIG")
@NamedQuery(name = "MidTidDetailsConfig.fetchAll", query = "SELECT d FROM MidTidDetailsConfig d where d.isActive=1")
public class MidTidDetailsConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name = "DATA_NAME")
	private String dataName;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

}