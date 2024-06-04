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
@Table(name = "RECON_DATA_MASTER")
@NamedQuery(name = "ReconDataMaster.fetchAll", query = "SELECT d FROM ReconDataMaster d")
@NamedQuery(name = "ReconDataMaster.fetchById", query = "SELECT d FROM ReconDataMaster d WHERE d.id =:id")
public class ReconDataMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECON_DATA_MASTER_SEQ")
	@SequenceGenerator(name = "RECON_DATA_MASTER_SEQ", sequenceName = "RECON_DATA_MASTER_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "RECON_CONFIG_ID")
	private long reconConfigId;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ENTRY_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp entryDate;

	@Column(name = "ENTRY_BY")
	private String entryBy;

	@Column(name = "UPD_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp updDate;

	@Column(name = "UPD_BY")
	private String updBy;

	@Column(name = "TOTAL_RECORDS")
	private int totalRecords;

	@Column(name = "TOTAL_SYNCED_RECORDS")
	private int totalSyncedRecords;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "FILE_PATH")
	private String filePath;

	@Column(name = "URI_NAME")
	private String uriName;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "REMARKS")
	private String remarks;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	public Timestamp getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Timestamp updDate) {
		this.updDate = updDate;
	}

	public String getUpdBy() {
		return updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalSyncedRecords() {
		return totalSyncedRecords;
	}

	public void setTotalSyncedRecords(int totalSyncedRecords) {
		this.totalSyncedRecords = totalSyncedRecords;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUriName() {
		return uriName;
	}

	public void setUriName(String uriName) {
		this.uriName = uriName;
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

}
