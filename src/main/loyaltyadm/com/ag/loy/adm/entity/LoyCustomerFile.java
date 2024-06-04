package com.ag.loy.adm.entity;

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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "LOY_CUSTOMER_FILE")
@NamedQueries({
		@NamedQuery(name = "LoyCustomerFile.fetchByName", query = "SELECT a FROM LoyCustomerFile a WHERE a.fileName=:fileName AND (a.status = 'NEW' OR a.status='APPROVED')"),
		@NamedQuery(name = "LoyCustomerFile.fetchAllByCorpId", query = "SELECT a FROM LoyCustomerFile a WHERE a.corpId=:corpId"),
		@NamedQuery(name = "LoyCustomerFile.fetchAllNew", query = "SELECT a FROM LoyCustomerFile a WHERE a.status='NEW' "),
		@NamedQuery(name = "LoyCustomerFile.fetchById", query = "SELECT a FROM LoyCustomerFile a WHERE a.id=:id") })
public class LoyCustomerFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loy_customer_file_seq")
	@SequenceGenerator(name = "loy_customer_file_seq", sequenceName = "LOY_CUSTOMER_FILE_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private long id;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "ENTRY_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp entryDate;

	@Column(name = "ENTRY_BY")
	private String entryBy;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "TOTAL_RECORDS")
	private int totalRecords;

	@Column(name = "SUCCESS_RECORDS")
	private int successRecords;

	@Column(name = "FAILURE_RECORDS")
	private int failureRecords;

	@Column(name = "FILE_TYPE")
	private String fileType;

	@Column(name = "APPROVED_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp approvedDate;

	@Column(name = "APPROVED_BY")
	private String approvedBy;

	@Column(name = "REJECTED_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp rejectedDate;

	@Column(name = "REJECTED_BY")
	private String rejectedBy;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "CORP_ID")
	private String corpId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Timestamp getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Timestamp getRejectedDate() {
		return rejectedDate;
	}

	public void setRejectedDate(Timestamp rejectedDate) {
		this.rejectedDate = rejectedDate;
	}

	public String getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getSuccessRecords() {
		return successRecords;
	}

	public void setSuccessRecords(int successRecords) {
		this.successRecords = successRecords;
	}

	public int getFailureRecords() {
		return failureRecords;
	}

	public void setFailureRecords(int failureRecords) {
		this.failureRecords = failureRecords;
	}
}
