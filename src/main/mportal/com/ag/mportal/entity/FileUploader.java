package com.ag.mportal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * The persistent class for the FILE_UPLOADER database table.
 * 
 */
@Entity
@Table(name = "FILE_UPLOADER")

@NamedQueries({ @NamedQuery(name = "FileUploader.findAll", query = "SELECT f FROM FileUploader f"),
		@NamedQuery(name = "FileUploader.fetchByType", query = "SELECT f FROM FileUploader f WHERE  f.type IN (:type) and f.corpId= :corpId"),
		@NamedQuery(name = "FileUploader.fetchByTypeAndStatus", query = "SELECT f FROM FileUploader f WHERE  f.type IN (:type) and f.status = :status and f.corpId= :corpId"),
		@NamedQuery(name = "FileUploader.fetchByTypeStatus", query = "SELECT f FROM FileUploader f WHERE  f.type=:type and f.status = 'APPROVER' and f.corpId= :corpId"),
		@NamedQuery(name = "FileUploader.fetchByStatus", query = "SELECT f FROM FileUploader f WHERE  f.type ='PRODUCT_PRICE' and f.status = 'NEW' or f.status = 'APPROVER'"),
		@NamedQuery(name = "FileUploader.fetchById", query = "SELECT f FROM FileUploader f where f.id = :id and f.corpId= :corpId") })
public class FileUploader implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FILE_UPLOADER_ID_GENERATOR", sequenceName = "FILE_UPLOADER_SEQ",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_UPLOADER_ID_GENERATOR")
	private long id;

	@Column(name = "APPR_BY")
	private String apprBy;

	@Column(name = "APPR_ON")
	private Timestamp apprOn;

	@Column(name = "CR_BY")
	private String crBy;

	@Column(name = "CR_ON")
	private Timestamp crOn;

	@Column(name = "FAILURE_REC")
	private BigDecimal failureRec;

	@Column(name = "FILE_NAME")
	private String fileName;

	private String msg;

	@Column(name = "REJ_BY")
	private String rejBy;

	@Column(name = "REJ_ON")
	private Timestamp rejOn;

	private String remarks;

	private String status;

	@Column(name = "SUCCESS_REC")
	private BigDecimal successRec;

	@Column(name = "TOTAL_REC")
	private BigDecimal totalRec;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "FILE_PATH")
	private String filePath;

	@Column(name = "CORP_ID")
	private String corpId;

	public FileUploader() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApprBy() {
		return this.apprBy;
	}

	public void setApprBy(String apprBy) {
		this.apprBy = apprBy;
	}

	public Timestamp getApprOn() {
		return this.apprOn;
	}

	public void setApprOn(Timestamp apprOn) {
		this.apprOn = apprOn;
	}

	public String getCrBy() {
		return this.crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public Timestamp getCrOn() {
		return this.crOn;
	}

	public void setCrOn(Timestamp crOn) {
		this.crOn = crOn;
	}

	public BigDecimal getFailureRec() {
		return this.failureRec;
	}

	public void setFailureRec(BigDecimal failureRec) {
		this.failureRec = failureRec;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRejBy() {
		return this.rejBy;
	}

	public void setRejBy(String rejBy) {
		this.rejBy = rejBy;
	}

	public Timestamp getRejOn() {
		return this.rejOn;
	}

	public void setRejOn(Timestamp rejOn) {
		this.rejOn = rejOn;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getSuccessRec() {
		return this.successRec;
	}

	public void setSuccessRec(BigDecimal successRec) {
		this.successRec = successRec;
	}

	public BigDecimal getTotalRec() {
		return this.totalRec;
	}

	public void setTotalRec(BigDecimal totalRec) {
		this.totalRec = totalRec;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

}