package com.ag.mportal.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PREAUTH_MERCHANT database table.
 * 
 */
@Embeddable
public class PreauthMerchantPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long paid;

	private String mid;

	public PreauthMerchantPK() {
	}
	public long getPaid() {
		return this.paid;
	}
	public void setPaid(long paid) {
		this.paid = paid;
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
		if (!(other instanceof PreauthMerchantPK)) {
			return false;
		}
		PreauthMerchantPK castOther = (PreauthMerchantPK)other;
		return 
			(this.paid == castOther.paid)
			&& this.mid.equals(castOther.mid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.paid ^ (this.paid >>> 32)));
		hash = hash * prime + this.mid.hashCode();
		
		return hash;
	}
}