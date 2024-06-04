package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "USER_STATUS_LOG")
@NamedQuery(name = "UserStatusLog.fetchAll", query = "SELECT d FROM UserStatusLog d where d.corpid is not null")
@NamedQuery(name = "UserStatusLog.fetchByCardNo", query = "SELECT d FROM UserStatusLog d where d.corpid =:corpid AND d.userid =:userid ")
public class UserStatusLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USERID")
	private String userid;

	@Column(name = "CORPID")
	private String corpid;

	@Column(name = "CID")
	private String cid;

	@Column(name = "CR_ON")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp crOn;

	@Column(name = "CR_BY")
	private String crBy;

	@Column(name = "FROMSTATUS")
	private String fromStatus;

	@Column(name = "OPRDESCRIPTION")
	private String oprDescription;

	@Column(name = "SOURCE_REF")
	private String sourceRef;

	@Column(name = "TOSTATUS")
	private String toStatus;

	@Column(name = "USERTYPE")
	private String userType;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Timestamp getCrOn() {
		return crOn;
	}

	public void setCrOn(Timestamp crOn) {
		this.crOn = crOn;
	}

	public String getCrBy() {
		return crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public String getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}

	public String getOprDescription() {
		return oprDescription;
	}

	public void setOprDescription(String oprDescription) {
		this.oprDescription = oprDescription;
	}

	public String getSourceRef() {
		return sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getToStatus() {
		return toStatus;
	}

	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
