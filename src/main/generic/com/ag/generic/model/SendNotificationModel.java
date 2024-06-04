package com.ag.generic.model;

import java.sql.Timestamp;

public class SendNotificationModel {
	
	private String merchantName;
	private String resolution;
	private Timestamp closureDate;
	private Timestamp requestComplDate;
	private String complaintNum;
	private String accountOpeningDate;
	private String userName;
	private String pass;
	private String closedBy;
	private int reciverId;
	private String otp;
	
	
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public Timestamp getClosureDate() {
		return closureDate;
	}
	public void setClosureDate(Timestamp closureDate) {
		this.closureDate = closureDate;
	}
	public Timestamp getRequestComplDate() {
		return requestComplDate;
	}
	public void setRequestComplDate(Timestamp requestComplDate) {
		this.requestComplDate = requestComplDate;
	}
	public String getComplaintNum() {
		return complaintNum;
	}
	public void setComplaintNum(String complaintNum) {
		this.complaintNum = complaintNum;
	}
	public String getAccountOpeningDate() {
		return accountOpeningDate;
	}
	public void setAccountOpeningDate(String accountOpeningDate) {
		this.accountOpeningDate = accountOpeningDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getClosedBy() {
		return closedBy;
	}
	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}
	public int getReciverId() {
		return reciverId;
	}
	public void setReciverId(int reciverId) {
		this.reciverId = reciverId;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	

}
