package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the USER_STATUS_MASTER database table.
 * 
 */
@Embeddable
public class UserStatusMasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String corpid;

	private String usertype;

	private String status;

	public UserStatusMasterPK() {
	}
	public String getCorpid() {
		return this.corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getUsertype() {
		return this.usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserStatusMasterPK)) {
			return false;
		}
		UserStatusMasterPK castOther = (UserStatusMasterPK)other;
		return 
			this.corpid.equals(castOther.corpid)
			&& this.usertype.equals(castOther.usertype)
			&& this.status.equals(castOther.status);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.corpid.hashCode();
		hash = hash * prime + this.usertype.hashCode();
		hash = hash * prime + this.status.hashCode();
		
		return hash;
	}
}
