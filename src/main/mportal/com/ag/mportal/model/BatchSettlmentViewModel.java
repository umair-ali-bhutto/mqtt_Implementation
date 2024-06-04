package com.ag.mportal.model;

public class BatchSettlmentViewModel {

	
	private String txnType;
	private String txnCount;
	private String tnxAmount;
	
	
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnCount() {
		return txnCount;
	}
	public void setTxnCount(String txnCount) {
		this.txnCount = txnCount;
	}
	public String getTnxAmount() {
		return tnxAmount;
	}
	public void setTnxAmount(String tnxAmount) {
		this.tnxAmount = tnxAmount;
	}
}
