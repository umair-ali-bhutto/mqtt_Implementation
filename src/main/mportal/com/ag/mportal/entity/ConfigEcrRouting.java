package com.ag.mportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the CONFIG_ECR_ROUTING database table.
 * 
 */
@Entity
@Table(name = "CONFIG_ECR_ROUTING")
@NamedQueries({
	@NamedQuery(name = "ConfigEcrRouting.findByMidTid", query = "SELECT c FROM ConfigEcrRouting c WHERE c.isActive=1 and c.mid=:mid AND c.tid=:tid AND c.callType=:callType"),
	@NamedQuery(name = "ConfigEcrRouting.findByID", query = "SELECT c FROM ConfigEcrRouting c WHERE c.isActive=1 and c.id=:id")
})
public class ConfigEcrRouting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name = "BR_TOKEN")
	private String brToken;

	@Column(name = "CALL_TYPE")
	private String callType;

	@Column(name = "REQUEST_TEMPLETE")
	private String requestTemplete;

	@Column(name = "CRED_PASS")
	private String credPass;

	@Column(name = "CRED_USER")
	private String credUser;

	@Column(name = "EXT_MID")
	private String extMid;

	@Column(name = "EXT_TID")
	private String extTid;

	@Column(name = "NETWORK_HANDLER")
	private String networkHandler;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	private String mid;

	private int retries;

	private String tid;

	@Column(name = "TIME_OUT")
	private int timeOut;

	private String uri;

	@Column(name = "URI_TYPE")
	private String uriType;
	
	@Column(name = "SAF")
	private int saf;
	
	@Column(name = "SAF_RETRIES_CONFIG")
	private String safRetriesConfig;

	@Column(name = "SAF_RETRIES")
	private int safRetries;
	
	@Column(name = "CORPORATE")
	private String corporate;

	public ConfigEcrRouting() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBrToken() {
		return brToken;
	}

	public void setBrToken(String brToken) {
		this.brToken = brToken;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	
	public String getCredPass() {
		return credPass;
	}

	public void setCredPass(String credPass) {
		this.credPass = credPass;
	}

	public String getCredUser() {
		return credUser;
	}

	public void setCredUser(String credUser) {
		this.credUser = credUser;
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

	

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUriType() {
		return uriType;
	}

	public void setUriType(String uriType) {
		this.uriType = uriType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRequestTemplete() {
		return requestTemplete;
	}

	public void setRequestTemplete(String requestTemplete) {
		this.requestTemplete = requestTemplete;
	}

	public String getNetworkHandler() {
		return networkHandler;
	}

	public void setNetworkHandler(String networkHandler) {
		this.networkHandler = networkHandler;
	}

	public int getSaf() {
		return saf;
	}

	public void setSaf(int saf) {
		this.saf = saf;
	}

	public String getSafRetriesConfig() {
		return safRetriesConfig;
	}

	public void setSafRetriesConfig(String safRetriesConfig) {
		this.safRetriesConfig = safRetriesConfig;
	}

	public int getSafRetries() {
		return safRetries;
	}

	public void setSafRetries(int safRetries) {
		this.safRetries = safRetries;
	}

	public String getCorporate() {
		return corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	

}