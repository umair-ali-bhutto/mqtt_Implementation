package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the USER_STATUS_MASTER database table.
 * 
 */
@Entity
@Table(name = "USER_STATUS_MASTER")
@NamedQuery(name = "UserStatusMaster.findAll", query = "SELECT u FROM UserStatusMaster u where u.id.corpid=:corpid and u.id.usertype=:usertype")
@NamedQuery(name = "UserStatusMaster.findByUsertype", query = "SELECT u FROM UserStatusMaster u where u.id.corpid=:corpid and u.id.usertype=:usertype and u.statusName = 'ACTIVE' ")
public class UserStatusMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserStatusMasterPK id;

	private String active;

	@Column(name = "ALLOW_REPLACE")
	private String allowReplace;

	private String sdescription;

	@Column(name = "STATUS_NAME")
	private String statusName;

	public UserStatusMaster() {
	}

	public UserStatusMasterPK getId() {
		return this.id;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getSdescription() {
		return this.sdescription;
	}

	public void setSdescription(String sDescription) {
		this.sdescription = sDescription;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return this.statusName;
	}
}