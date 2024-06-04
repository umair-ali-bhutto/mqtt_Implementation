package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class AccountPaymentsLogId implements Serializable{
	
	//
	private static final long serialVersionUID = 1L;
	private String corpid;
	private String accId;
	private Timestamp txnAuthDate1;
	private Timestamp txnAuthDate2;
	
	
	
	
	public AccountPaymentsLogId() {
	
	}
	
	public AccountPaymentsLogId(String corpid, String accId, Timestamp txnAuthDate1, Timestamp txnAuthDate2) {
		this.corpid = corpid;
		this.accId = accId;
		this.txnAuthDate1 = txnAuthDate1;
		this.txnAuthDate2 = txnAuthDate2;
	}
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
	}
	public Timestamp getTxnAuthDate1() {
		return txnAuthDate1;
	}
	public void setTxnAuthDate1(Timestamp txnAuthDate1) {
		this.txnAuthDate1 = txnAuthDate1;
	}
	public Timestamp getTxnAuthDate2() {
		return txnAuthDate2;
	}
	public void setTxnAuthDate2(Timestamp txnAuthDate2) {
		this.txnAuthDate2 = txnAuthDate2;
	}
	
	
	
}
