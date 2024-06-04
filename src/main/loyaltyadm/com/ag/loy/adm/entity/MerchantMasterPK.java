package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the MERCHANT_MASTER database table.
 * 
 */
@Embeddable
public class MerchantMasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String corpid;

	private String mid;

	public MerchantMasterPK() {
	}
	public String getCorpid() {
		return this.corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getMid() {
		return this.mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MerchantMasterPK)) {
			return false;
		}
		MerchantMasterPK castOther = (MerchantMasterPK)other;
		return 
			this.corpid.equals(castOther.corpid)
			&& this.mid.equals(castOther.mid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.corpid.hashCode();
		hash = hash * prime + this.mid.hashCode();
		
		return hash;
	}
}
