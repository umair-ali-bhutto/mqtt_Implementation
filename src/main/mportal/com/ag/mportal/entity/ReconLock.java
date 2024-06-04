package com.ag.mportal.entity;

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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "RECON_LOCK")
@NamedQuery(name = "ReconLock.fetchAll", query = "SELECT d FROM ReconLock d")
@NamedQuery(name = "ReconLock.fetchById", query = "SELECT d FROM ReconLock d WHERE d.reconConfigId=:reconConfigId AND d.status =:status")
public class ReconLock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECON_LOCK_SEQ")
	@SequenceGenerator(name = "RECON_LOCK_SEQ", sequenceName = "RECON_LOCK_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "RECON_CONFIG_ID")
	private long reconConfigId;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "ENTRY_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp entryDate;

	@Column(name = "UPD_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp updDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getReconConfigId() {
		return reconConfigId;
	}

	public void setReconConfigId(long reconConfigId) {
		this.reconConfigId = reconConfigId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public Timestamp getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Timestamp updDate) {
		this.updDate = updDate;
	}

}
