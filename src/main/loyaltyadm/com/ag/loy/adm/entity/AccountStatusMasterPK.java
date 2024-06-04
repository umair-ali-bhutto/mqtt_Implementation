package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the ACCOUNT_STATUS_MASTER database table.
 * 
 */
@Embeddable
public class AccountStatusMasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String corpid;

	private String acctype;

	private String status;

	public AccountStatusMasterPK() {
	}
	public String getCorpid() {
		return this.corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getAcctype() {
		return this.acctype;
	}
	public void setAcctype(String acctype) {
		this.acctype = acctype;
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
		if (!(other instanceof AccountStatusMasterPK)) {
			return false;
		}
		AccountStatusMasterPK castOther = (AccountStatusMasterPK)other;
		return 
			this.corpid.equals(castOther.corpid)
			&& this.acctype.equals(castOther.acctype)
			&& this.status.equals(castOther.status);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.corpid.hashCode();
		hash = hash * prime + this.acctype.hashCode();
		hash = hash * prime + this.status.hashCode();
		
		return hash;
	}
}
