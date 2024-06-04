package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the ECR_SAF database table.
 * 
 */
@Entity
@Table(name = "ECR_SAF")
@NamedQueries({ @NamedQuery(name = "EcrSaf.fetchAllSaf", query = "SELECT es FROM EcrSaf es WHERE nextRun <= SYSTIMESTAMP AND status = 'PROCESS' AND maxRetry <> minRetry and ecrLogId=373") })
public class EcrSaf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ECR_SAF_ID_GENERATOR", sequenceName = "ECR_SAF_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECR_SAF_ID_GENERATOR")
	@Column(name = "ID")
	private long id;

	@Column(name = "ECR_LOG_ID")
	private int ecrLogId;

	@Column(name = "CONFIG_ECR_ROUTING_ID")
	private int configEcrRoutingId;

	@Column(name = "REQUEST_DATA")
	private String requestData;

	@Column(name = "REQUEST_HEADER")
	private String requestHeader;

	@Column(name = "MAX_RETRY")
	private int maxRetry;

	@Column(name = "MIN_RETRY")
	private int minRetry;

	@Column(name = "LAST_RUN")
	private String lastRun;

	@Column(name = "NEXT_RUN")
	private Timestamp nextRun;

	@Column(name = "STATUS")
	private String status;

	@Lob
	@Column(name = "RESPONSE_DATA", columnDefinition = "CLOB")
	private String responseData;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getEcrLogId() {
		return ecrLogId;
	}

	public void setEcrLogId(int ecrLogId) {
		this.ecrLogId = ecrLogId;
	}

	public int getConfigEcrRoutingId() {
		return configEcrRoutingId;
	}

	public void setConfigEcrRoutingId(int configEcrRoutingId) {
		this.configEcrRoutingId = configEcrRoutingId;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(String requestHeader) {
		this.requestHeader = requestHeader;
	}

	public int getMaxRetry() {
		return maxRetry;
	}

	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	public int getMinRetry() {
		return minRetry;
	}

	public void setMinRetry(int minRetry) {
		this.minRetry = minRetry;
	}

	public Timestamp getNextRun() {
		return nextRun;
	}

	public void setNextRun(Timestamp nextRun) {
		this.nextRun = nextRun;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public String getLastRun() {
		return lastRun;
	}

	public void setLastRun(String lastRun) {
		this.lastRun = lastRun;
	}

}