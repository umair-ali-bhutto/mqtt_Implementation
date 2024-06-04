package com.ag.mportal.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the FILE_DOWNLOAD database table.
 * 
 */
@Entity
@Table(name = "FILE_DOWNLOAD")
//@NamedQuery(name = "FileDownload.findAll", query = "SELECT f FROM FileDownload f")
@NamedQueries({ @NamedQuery(name = "FileDownload.fetchAll", query = "SELECT f FROM FileDownload f"),
		@NamedQuery(name = "FileDownload.fetchByVersion", query = "SELECT f FROM FileDownload f where f.version=:version"),
		@NamedQuery(name = "FileDownload.fetchById", query = "SELECT f FROM FileDownload f where f.id=:id"),
		@NamedQuery(name = "FileDownload.deactivePrevApk", query = "Update FileDownload f set f.isActive=0 where f.releaseType=:releaseType and f.id != :id"),
		@NamedQuery(name = "FileDownload.countReleaseTypeAndStatusWise", query = "select count(f) from FileDownload f where f.releaseType=:releaseType and f.status = :status") })
public class FileDownload implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_DOWNLOAD_SEQ")
	@SequenceGenerator(name = "FILE_DOWNLOAD_SEQ", sequenceName = "FILE_DOWNLOAD_SEQ", allocationSize = 1)
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "FILE_CHECKSUM")
	private String fileChecksum;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "FILE_PATH")
	private String filePath;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "VERSION")
	private String version;

	@JsonIgnore
	@Column(name = "ENTRY_BY")
	private String entryBy;

	@JsonIgnore
	@Column(name = "APPROVED_BY")
	private String approvedBy;

	@JsonIgnore
	@Column(name = "APPROVED_DATE")
	private Timestamp approvedDate;

	@JsonIgnore
	@Column(name = "REJECTED_BY")
	private String rejectedBy;

	@JsonIgnore
	@Column(name = "REJECTED_DATE")
	private Timestamp rejectedDate;

	@JsonIgnore
	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "BUNDLE_ID")
	private String bundleId;

	@Column(name = "APP_ICON")
	private String appIcon;

	@Column(name = "APP_NAME")
	private String appName;

	@Column(name = "RELEASE_TYPE")
	private String releaseType;

	@Column(name = "IS_DEPENDENT")
	private String isDependent;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIsDependent() {
		return isDependent;
	}

	public void setIsDependent(String isDependent) {
		this.isDependent = isDependent;
	}

	@JsonIgnore
	@Column(name = "TERMINAL_IDS")
	private String terminalIds;

	public FileDownload() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getFileChecksum() {
		return this.fileChecksum;
	}

	public void setFileChecksum(String fileChecksum) {
		this.fileChecksum = fileChecksum;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Timestamp getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public Timestamp getRejectedDate() {
		return rejectedDate;
	}

	public void setRejectedDate(Timestamp rejectedDate) {
		this.rejectedDate = rejectedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
	}

	public String getTerminalIds() {
		return terminalIds;
	}

	public void setTerminalIds(String terminalIds) {
		this.terminalIds = terminalIds;
	}

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}