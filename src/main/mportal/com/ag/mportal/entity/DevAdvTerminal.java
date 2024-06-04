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
@Table(name = "DEV_ADV_TERMINAL")
@NamedQuery(name = "DevAdvTerminal.fetchAll", query = "SELECT d FROM DevAdvTerminal d WHERE d.active = 'Y' ORDER BY d.advId")
public class DevAdvTerminal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ADVID")
	private long advId;

	@Column(name = "CORPID")
	private String corpId;

	@Column(name = "MID")
	private String mid;

	@Column(name = "TID")
	private String tid;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "CR_BY")
	private String crBy;

	@Column(name = "CR_ON")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp crOn;

	@Column(name = "UPD_BY")
	private String updBy;

	@Column(name = "UPD_ON")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp updOn;

	public long getAdvId() {
		return advId;
	}

	public void setAdvId(long advId) {
		this.advId = advId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

}
