package com.ag.mportal.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PREAUTH_TERMINAL database table.
 * 
 */
@Embeddable
public class PreauthTerminalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long paid;

	private String mid;

	private String tid;

	public PreauthTerminalPK() {
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
	public String getTid() {
		return this.tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PreauthTerminalPK)) {
			return false;
		}
		PreauthTerminalPK castOther = (PreauthTerminalPK)other;
		return 
			(this.paid == castOther.paid)
			&& this.mid.equals(castOther.mid)
			&& this.tid.equals(castOther.tid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.paid ^ (this.paid >>> 32)));
		hash = hash * prime + this.mid.hashCode();
		hash = hash * prime + this.tid.hashCode();
		
		return hash;
	}
}