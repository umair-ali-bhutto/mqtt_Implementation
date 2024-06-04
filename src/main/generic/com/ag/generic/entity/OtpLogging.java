package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the OTP_LOGGING database table.
 * 
 */
@Entity
@Table(name = "OTP_LOGGING")
@NamedQuery(name = "OTPLogging.validateOtpByChemistId", query = "SELECT a FROM OtpLogging a WHERE a.status='ACTIVE' AND a.chemistId=:chemistId")
@NamedQuery(name = "OTPLogging.validateOtp", query = "SELECT a FROM OtpLogging a WHERE a.status='ACTIVE'  AND a.chemistId=:chemistId AND a.otp=:otp")
@NamedQuery(name = "OTPLogging.validateOtpFuel", query = "SELECT a FROM OtpLogging a WHERE a.status='ACTIVE'  AND a.mobileNo=:mobileNo AND a.otp=:otp AND a.corpId=:corpId AND a.productName=:productName")
public class OtpLogging implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CHEMIST_ID")
	private String chemistId;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OTP_LOGGING_SEQ")
	@SequenceGenerator(name = "OTP_LOGGING_SEQ", sequenceName = "OTP_LOGGING_SEQ", allocationSize = 1)
	private int id;

	private String imei;

	@Column(name = "MOBILE_NO")
	private String mobileNo;

	private String otp;

	@Column(name = "OTP_TIME")
	private Timestamp otpTime;

	@Column(name = "PRODUCT_NAME")
	private String productName;

	private String status;
	@Column(name = "CORP_ID")
	private String corpId;

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public OtpLogging() {
	}

	public String getChemistId() {
		return this.chemistId;
	}

	public void setChemistId(String chemistId) {
		this.chemistId = chemistId;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getOtp() {
		return this.otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Timestamp getOtpTime() {
		return this.otpTime;
	}

	public void setOtpTime(Timestamp otpTime) {
		this.otpTime = otpTime;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}