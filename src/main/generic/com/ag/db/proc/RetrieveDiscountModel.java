package com.ag.db.proc;

public class RetrieveDiscountModel {
	private String pRespCode;
	private String pDiscountName;
	private String pDiscountPerc;
	private String pNetAmount;
	private String pDiscountAmount;
	private String Message;
	
	public String getpRespCode() {
		return pRespCode;
	}

	public void setpRespCode(String pRespCode) {
		this.pRespCode = pRespCode;
	}

	public String getpDiscountName() {
		return pDiscountName;
	}

	public void setpDiscountName(String pDiscountName) {
		this.pDiscountName = pDiscountName;
	}

	public String getpDiscountPerc() {
		return pDiscountPerc;
	}

	public void setpDiscountPerc(String pDiscountPerc) {
		this.pDiscountPerc = pDiscountPerc;
	}

	public String getpNetAmount() {
		return pNetAmount;
	}

	public void setpNetAmount(String pNetAmount) {
		this.pNetAmount = pNetAmount;
	}

	public String getpDiscountAmount() {
		return pDiscountAmount;
	}

	public void setpDiscountAmount(String pDiscountAmount) {
		this.pDiscountAmount = pDiscountAmount;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
}
