package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "AUDIT_LOG")
@NamedQueries({
	
	@NamedQuery(name = "AuditLog.retrieveAll", query = "SELECT a FROM AuditLog a")

})
public class AuditLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "AUDIT_LOG_SEQ")
	@SequenceGenerator(name="AUDIT_LOG_SEQ",sequenceName="AUDIT_LOG_SEQ", allocationSize=1)
	private int id;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "MID")
	private String mid;
	
	@Column(name = "TID")
	private String tid;
	
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;
	
	@Column(name = "SERIAL_NUM")
	private String serialNum;
	@Column(name = "TXN_TYPE")
	private String txnType;
	@Column(name = "REQUEST_MODE")
	private String requestMode;
	@Column(name = "REQUEST_IP")
	private String requestIp;
	@Column(name = "REQUEST")
	private String request;
	@Column(name = "RESPONSE")
	private String response;	
	@Column(name = "CORP_ID")
	private String corpId;
	
	
	
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public Timestamp getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getRequestMode() {
		return requestMode;
	}
	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}
	public String getRequestIp() {
		return requestIp;
	}
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}