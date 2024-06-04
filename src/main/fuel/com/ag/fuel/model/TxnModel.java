package com.ag.fuel.model;

import java.util.List;

public class TxnModel {

	private String monthlyLimit;
	private String consumedLimit;
	private String avbBalance;
	private String labelF1;
	private String fieldOne;
	private String labelF2;
	private String fieldTwo;
	private List<TxnViewDetails> txnViewDetails;

	public void setAvbBalance(String avbBalance) {
		this.avbBalance = avbBalance;
	}

	public String getMonthlyLimit() {
		return monthlyLimit;
	}

	public void setMonthlyLimit(String monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
	}

	public String getFieldOne() {
		return fieldOne;
	}

	public void setFieldOne(String fieldOne) {
		this.fieldOne = fieldOne;
	}

	public String getFieldTwo() {
		return fieldTwo;
	}

	public void setFieldTwo(String fieldTwo) {
		this.fieldTwo = fieldTwo;
	}

	public List<TxnViewDetails> getTxnViewDetails() {
		return txnViewDetails;
	}

	public void setTxnViewDetails(List<TxnViewDetails> txnViewDetails) {
		this.txnViewDetails = txnViewDetails;
	}

	public String getAvbBalance() {
		return avbBalance;
	}

	public String getConsumedLimit() {
		return consumedLimit;
	}

	public void setConsumedLimit(String consumedLimit) {
		this.consumedLimit = consumedLimit;
	}

	public String getLabelF1() {
		return labelF1;
	}

	public void setLabelF1(String labelF1) {
		this.labelF1 = labelF1;
	}

	public String getLabelF2() {
		return labelF2;
	}

	public void setLabelF2(String labelF2) {
		this.labelF2 = labelF2;
	}

}
