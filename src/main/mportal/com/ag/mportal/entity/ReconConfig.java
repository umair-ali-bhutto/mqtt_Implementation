package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "RECON_CONFIG")
@NamedQuery(name = "ReconConfig.fetchAll", query = "SELECT d FROM ReconConfig d WHERE d.isActive = 1 ORDER BY d.id")
public class ReconConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "MID")
	private String mid;

	@Column(name = "ENTRY_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp entryDate;

	@Column(name = "CONFIG_CLASS")
	private String configClass;

	@Column(name = "EXT_MID")
	private String extMid;

	@Column(name = "EXT_TID")
	private String extTid;

	@Column(name = "MERCHANT_NAME")
	private String merchantName;

	@Column(name = "RETRIES")
	private int retries;

	@Column(name = "TIMEOUT")
	private int timeout;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "RETRIES_CONFIG")
	private String retriesConfig;

	@Column(name = "LAST_RUN")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp lastRun;

	@Column(name = "URI")
	private String uri;

	@Column(name = "URI_TOKEN")
	private String uriToken;

	@Column(name = "CRED_USER")
	private String credUser;

	@Column(name = "CRED_PASS")
	private String credPass;

	@Column(name = "CORP_ID")
	private String corpId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getConfigClass() {
		return configClass;
	}

	public void setConfigClass(String configClass) {
		this.configClass = configClass;
	}

	public String getExtMid() {
		return extMid;
	}

	public void setExtMid(String extMid) {
		this.extMid = extMid;
	}

	public String getExtTid() {
		return extTid;
	}

	public void setExtTid(String extTid) {
		this.extTid = extTid;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getRetriesConfig() {
		return retriesConfig;
	}

	public void setRetriesConfig(String retriesConfig) {
		this.retriesConfig = retriesConfig;
	}

	public Timestamp getLastRun() {
		return lastRun;
	}

	public void setLastRun(Timestamp lastRun) {
		this.lastRun = lastRun;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUriToken() {
		return uriToken;
	}

	public void setUriToken(String uriToken) {
		this.uriToken = uriToken;
	}

	public String getCredUser() {
		return credUser;
	}

	public void setCredUser(String credUser) {
		this.credUser = credUser;
	}

	public String getCredPass() {
		return credPass;
	}

	public void setCredPass(String credPass) {
		this.credPass = credPass;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

}
