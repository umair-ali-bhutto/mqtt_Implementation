package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ACCOUNT_STATUS_MASTER database table.
 * 
 */
@Entity
@Table(name="ACCOUNT_STATUS_MASTER")
@NamedQuery(name="AccountStatusMaster.fetchAll", query="SELECT a FROM AccountStatusMaster a where a.id.corpid=:corpid and a.id.acctype=:acctype")
public class AccountStatusMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AccountStatusMasterPK id;

	private String active;

	private String sdescription;

	@Column(name="STATUS_NAME")
	private String statusName;

	public AccountStatusMaster() {
	}

	public AccountStatusMasterPK getId() {
		return this.id;
	}

	public void setId(AccountStatusMasterPK id) {
		this.id = id;
	}
	
	public String getActive() {
		return this.active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getStatusName() {
		return this.statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getSdescription() {
		return this.sdescription;
	}
	public void setSdescription(String sDescription) {
		this.sdescription = sDescription;
	}
	
}