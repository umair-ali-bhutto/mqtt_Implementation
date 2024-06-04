package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the TRANSACTION_TYPES_MASTER database table.
 * 
 */
@Embeddable
public class TransactionTypesMasterPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String corpid;

	@Column(name = "TXN_TYPE")
	private String txnType;

	public TransactionTypesMasterPK() {
	}

	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getTxnType() {
		return this.txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TransactionTypesMasterPK)) {
			return false;
		}
		TransactionTypesMasterPK castOther = (TransactionTypesMasterPK) other;
		return this.corpid.equals(castOther.corpid) && this.txnType.equals(castOther.txnType);
	}

}