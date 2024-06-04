package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the CUSTOMER_STATUS_MASTER database table.
 * 
 */
@Embeddable
public class CustomerStatusMasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String corpid;

	private String custtype;

	private String status;

	public CustomerStatusMasterPK() {
	}
	public String getCorpid() {
		return this.corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getCusttype() {
		return this.custtype;
	}
	public void setCusttype(String custtype) {
		this.custtype = custtype;
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
		if (!(other instanceof CustomerStatusMasterPK)) {
			return false;
		}
		CustomerStatusMasterPK castOther = (CustomerStatusMasterPK)other;
		return 
			this.corpid.equals(castOther.corpid)
			&& this.custtype.equals(castOther.custtype)
			&& this.status.equals(castOther.status);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.corpid.hashCode();
		hash = hash * prime + this.custtype.hashCode();
		hash = hash * prime + this.status.hashCode();
		
		return hash;
	}
}