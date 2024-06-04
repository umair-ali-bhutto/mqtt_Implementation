package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RECON_CONFIG_DETAIL")
@NamedQuery(name = "ReconConfigDetail.fetchAll", query = "SELECT d FROM ReconConfigDetail d WHERE d.isActive = 1 ORDER BY d.id")
@NamedQuery(name = "ReconConfigDetail.fetchByConfigId", query = "SELECT d FROM ReconConfigDetail d WHERE d.isActive = 1 AND d.reconConfigId =:reconConfigId ORDER BY d.id")
public class ReconConfigDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECON_CONFIG_DETAIL_SEQ")
	@SequenceGenerator(name = "RECON_CONFIG_DETAIL_SEQ", sequenceName = "RECON_CONFIG_DETAIL_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "RECON_CONFIG_ID")
	private long reconConfigId;

	@Column(name = "RECON_DATA_MASTER_ID")
	private long reconDataMasterId;

	@Column(name = "DATA_OFF")
	private Date dataOff;

	@Column(name = "RETRIES")
	private int retries;

	@Column(name = "RETRIES_CONFIG")
	private String retriesConfig;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "STATE")
	private String state;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

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

	public long getReconDataMasterId() {
		return reconDataMasterId;
	}

	public void setReconDataMasterId(long reconDataMasterId) {
		this.reconDataMasterId = reconDataMasterId;
	}

	public Date getDataOff() {
		return dataOff;
	}

	public void setDataOff(Date dataOff) {
		this.dataOff = dataOff;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public String getRetriesConfig() {
		return retriesConfig;
	}

	public void setRetriesConfig(String retriesConfig) {
		this.retriesConfig = retriesConfig;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

}
