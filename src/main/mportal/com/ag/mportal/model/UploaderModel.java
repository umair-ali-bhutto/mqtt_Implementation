package com.ag.mportal.model;

public class UploaderModel {
	
	private String sNo;
	private String merchantId;
	private String mdrOnUs;
	private String mdrOffUs;
	private String province;
	private String status;
	
	
	public String getsNo() {
		return sNo;
	}
	public void setsNo(String sNo) {
		this.sNo = sNo;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMdrOnUs() {
		return mdrOnUs;
	}
	public void setMdrOnUs(String mdrOnUs) {
		this.mdrOnUs = mdrOnUs;
	}
	public String getMdrOffUs() {
		return mdrOffUs;
	}
	public void setMdrOffUs(String mdrOffUs) {
		this.mdrOffUs = mdrOffUs;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
