package com.ag.loy.adm.model;

import com.ag.loy.adm.entity.ChartConfigTxnSummary;

public class TxnSummaryModel {

	private ChartConfigTxnSummary txnSummary;
	private String amount;
	private int count;
	
	
	public ChartConfigTxnSummary getTxnSummary() {
		return txnSummary;
	}
	public void setTxnSummary(ChartConfigTxnSummary txnSummary) {
		this.txnSummary = txnSummary;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	


}
