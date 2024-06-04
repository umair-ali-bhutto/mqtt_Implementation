package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The persistent class for the USERS_LOGIN database table.
 * 
 * 
 * COMPANY_CODE IS_REG
 */
@Entity
@Table(name = "USERS_LOGIN")
@NamedQueries({
	@NamedQuery(name = "UserLogin.validetUserId", query = "SELECT a FROM UserLogin a WHERE a.userId=:userId AND a.isActive = 1"),
		@NamedQuery(name = "UserLogin.validetUser", query = "SELECT a FROM UserLogin a WHERE a.userCode=:userCode AND a.isActive = 1 AND a.corpId=:corpId"),
		@NamedQuery(name = "UserLogin.validetUserByNumber", query = "SELECT a FROM UserLogin a WHERE a.userCode=:userCode AND a.isActive = 1 AND a.corpId=:corpId AND a.msisdn=:number"),
		@NamedQuery(name = "UserLogin.validetUserWithoutCorpID", query = "SELECT a FROM UserLogin a WHERE a.userCode=:userCode AND a.isActive = 1"),
		@NamedQuery(name = "UserLogin.validetUserWithouStatus", query = "SELECT a FROM UserLogin a WHERE a.userCode=:userCode AND a.corpId=:corpId"),
		@NamedQuery(name = "UserLogin.validetUserPassword", query = "SELECT a FROM UserLogin a WHERE a.userCode=:userCode  AND  a.isActive = 1 AND a.password=:password"),
		@NamedQuery(name = "UserLogin.fetchAllAdmins", query = "SELECT a FROM UserLogin a WHERE a.groupCode in (:groupCode) AND a.corpId=:corpId and a.isActive = 1 order by id "),
		@NamedQuery(name = "UserLogin.fetchAll", query = "SELECT a FROM UserLogin a WHERE a.isActive = 1 AND a.corpId=:corpId order by userId "),
		@NamedQuery(name = "UserLogin.fetchAllUsers", query = "SELECT a FROM UserLogin a WHERE a.isActive = 1"),
		@NamedQuery(name = "UserLogin.fetchAllByMID", query = "SELECT a FROM UserLogin a WHERE a.isActive = 1 and a.mid=:mid AND a.corpId=:corpId order by userId "),
		@NamedQuery(name = "UserLogin.lstUserMerchants", query = "SELECT a.userId, a.mid FROM UserLogin a WHERE a.mid in (:mids) AND a.corpId=:corpId")})
@JsonInclude(Include.NON_NULL)
public class UserLogin implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
	@SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
	@Column(name="USER_ID")
	private int userId;

	@Column(name = "USER_CODE")
	private String userCode;

	@JsonIgnore
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "GROUP_CODE")
	private int groupCode;

	@Column(name = "IS_ACTIVE")
	private int isActive;
	
	@JsonIgnore
	@Column(name = "CR_BY")
	private String crBy;
	
	@JsonIgnore
	@Column(name = "CR_ON")
	private Timestamp crOn;
	
	@JsonIgnore
	@Column(name = "UPD_BY")
	private String updBy;
	
	@JsonIgnore
	@Column(name = "UPD_ON")
	private Timestamp updOn;

	@Column(name = "USER_NAME")
	private String userName;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "USER_TYPE")
	private String userType;

	@Column(name = "FORCE_LOGIN")
	private int forceLogin;
	
	
	@Column(name = "MID")
	private String mid;
	@Column(name = "ADDITIONAL_DATA")
	private String additionalData;

	@JsonIgnore
	@Column(name = "TEMP_PASS")
	private String tempPass;

	@Column(name = "EMAIL_ID")
	private String email;

	@Column(name = "CNIC")
	private String cnic;

	@Column(name = "MSISDN")
	private String msisdn;

	@Column(name = "CITY")
	private String city;

	@Column(name = "REGION")
	private String region;

	@Column(name = "CORP_ID")
	private String corpId;

	
	@Column(name = "LOYALTY_CORP_ID")
	private String loyaltyCorpId;

	@Column(name = "NII")
	private String nii;

	
	@JsonIgnore
	@Column(name = "TOKEN")
	private String token;

	@JsonIgnore
	@Column(name = "IMEI_UUID")
	private String imeiUuid;
	
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "DOB")
	private Date dob;

	@Column(name = "USER_PROFILE_IMG")
	private String userProfileImg;

	@JsonIgnore
	@Column(name = "TOKEN_GENERATION_TIME")
	private Timestamp tokenGenerationTime;

	@JsonIgnore
	@Column(name = "PIN")
	private String pin;

	@Column(name = "USER_TYPE_FUEL")
	private String userTypeFuel;

	@Column(name = "LAST_LOGIN")
	private Timestamp lastLogin;

	@Column(name = "COMPANY_CODE")
	private String companyCode;

	@JsonIgnore
	@Column(name = "IS_REG")
	private int isReg;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(int groupCode) {
		this.groupCode = groupCode;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getCrBy() {
		return crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public Timestamp getCrOn() {
		return crOn;
	}

	public void setCrOn(Timestamp crOn) {
		this.crOn = crOn;
	}

	public String getUpdBy() {
		return updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public Timestamp getUpdOn() {
		return updOn;
	}

	public void setUpdOn(Timestamp updOn) {
		this.updOn = updOn;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getForceLogin() {
		return forceLogin;
	}

	public void setForceLogin(int forceLogin) {
		this.forceLogin = forceLogin;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	public String getTempPass() {
		return tempPass;
	}

	public void setTempPass(String tempPass) {
		this.tempPass = tempPass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCnic() {
		return cnic;
	}

	public void setCnic(String cnic) {
		this.cnic = cnic;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getImeiUuid() {
		return imeiUuid;
	}

	public void setImeiUuid(String imeiUuid) {
		this.imeiUuid = imeiUuid;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getUserProfileImg() {
		return userProfileImg;
	}

	public void setUserProfileImg(String userProfileImg) {
		this.userProfileImg = userProfileImg;
	}

	public Timestamp getTokenGenerationTime() {
		return tokenGenerationTime;
	}

	public void setTokenGenerationTime(Timestamp tokenGenerationTime) {
		this.tokenGenerationTime = tokenGenerationTime;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getUserTypeFuel() {
		return userTypeFuel;
	}

	public void setUserTypeFuel(String userTypeFuel) {
		this.userTypeFuel = userTypeFuel;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getIsReg() {
		return isReg;
	}

	public void setIsReg(int isReg) {
		this.isReg = isReg;
	}

	public String getLoyaltyCorpId() {
		return loyaltyCorpId;
	}

	public void setLoyaltyCorpId(String loyaltyCorpId) {
		this.loyaltyCorpId = loyaltyCorpId;
	}

	public String getNii() {
		return nii;
	}

	public void setNii(String nii) {
		this.nii = nii;
	}

}