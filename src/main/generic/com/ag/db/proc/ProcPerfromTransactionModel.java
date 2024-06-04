package com.ag.db.proc;

public class ProcPerfromTransactionModel {
	
	private String pRespCode;
	private String pTxnRef;
	private String pRetCur;
	private String pBalance;
	private String pRetAmount;
	private String Message;
	
	public String getpRespCode() {
		return pRespCode;
	}
	public void setpRespCode(String pRespCode) {
		this.pRespCode = pRespCode;
	}
	public String getpTxnRef() {
		return pTxnRef;
	}
	public void setpTxnRef(String pTxnRef) {
		this.pTxnRef = pTxnRef;
	}
	public String getpRetCur() {
		return pRetCur;
	}
	public void setpRetCur(String pRetCur) {
		this.pRetCur = pRetCur;
	}
	public String getpBalance() {
		return pBalance;
	}
	public void setpBalance(String pBalance) {
		this.pBalance = pBalance;
	}
	public String getpRetAmount() {
		return pRetAmount;
	}
	public void setpRetAmount(String pRetAmount) {
		this.pRetAmount = pRetAmount;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	
}
