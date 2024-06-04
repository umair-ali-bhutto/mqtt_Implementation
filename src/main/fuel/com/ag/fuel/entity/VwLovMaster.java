package com.ag.fuel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the VW_LOV_MASTER database table.
 * 
 */
@Entity
@Table(name = "VW_LOV_MASTER")
@NamedQuery(name = "VwLovMaster.findAll", query = "SELECT v FROM VwLovMaster v")
@NamedQuery(name = "VwLovMaster.findAllByScreenId", query = "SELECT a FROM VwLovMaster a WHERE a.screenId=:screenId AND a.corpid =:corpid")

public class VwLovMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "SCREEN_ID")
	private String screenId;

	@Column(name = "CORPID")
	private String corpid;

	@Column(name = "ENTITY_NO")
	private String entityNo;

	@Column(name = "DISP_VALUE")
	private String dispValue;

	@Column(name = "PARENT_ID")
	private String parentId;

	@Column(name = "EXPIRY")
	private String expiry;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getScreenId() {
		return screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getEntityNo() {
		return entityNo;
	}

	public void setEntityNo(String entityNo) {
		this.entityNo = entityNo;
	}

	public String getDispValue() {
		return dispValue;
	}

	public void setDispValue(String dispValue) {
		this.dispValue = dispValue;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

}