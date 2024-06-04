package com.ag.loy.adm.entity;


import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the CUSTOMER_USER_MASTER database table.
 * 
 */
@Embeddable
public class CustomerUserMasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String corpid;

	private String cid;

	private String userid;

	public CustomerUserMasterPK() {
	}
	public String getCorpid() {
		return this.corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getCid() {
		return this.cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getUserid() {
		return this.userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CustomerUserMasterPK)) {
			return false;
		}
		CustomerUserMasterPK castOther = (CustomerUserMasterPK)other;
		return 
			this.corpid.equals(castOther.corpid)
			&& this.cid.equals(castOther.cid)
			&& this.userid.equals(castOther.userid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.corpid.hashCode();
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + this.userid.hashCode();
		
		return hash;
	}
}
