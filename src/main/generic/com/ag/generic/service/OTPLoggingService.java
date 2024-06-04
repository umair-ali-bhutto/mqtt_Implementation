package com.ag.generic.service;

import com.ag.generic.entity.OtpLogging;

public interface OTPLoggingService
{
	
	public void generateOtp(String imei, String mobileNo,String chemistId,String otp);
	public void generateOtpWithCorpId(String imei, String mobileNo,String chemistId,String otp,String corpId,String productName);
	public void deactiveOtp(String chemistId);
	public OtpLogging validateOtp(String otp,String chemistId);
	public OtpLogging validateOtpFuel(String otp,String mobileNumber,String corpId,String prdName);
	
}
