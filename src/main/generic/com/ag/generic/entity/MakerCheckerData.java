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

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The persistent class for the BATCH_SETTLEMENT database table.
 * 
 */
@Entity
@Table(name = "MAKER_CHECKER_DATA")
@NamedQueries({
		@NamedQuery(name = "MakerCheckerData.fetchByID", query = "SELECT d FROM MakerCheckerData d where d.status='PROCESS' and d.id=:id"),
		@NamedQuery(name = "MakerCheckerData.fetchByRecID", query = "SELECT d FROM MakerCheckerData d where d.status='PROCESS_FOR_VERIFIER' and d.id=:id"),
		@NamedQuery(name = "MakerCheckerData.fetchByUniqueIdentifier", query = "SELECT d FROM MakerCheckerData d where d.status='PROCESS' AND d.screenId=:screenId AND d.uniqueidentifier=:uniqueidentifier AND d.uniqueidentifier2=:uniqueidentifier2 AND d.uniqueidentifier3=:uniqueidentifier3"),
		@NamedQuery(name = "MakerCheckerData.fetchByUniqueIdentifierVerifier", query = "SELECT d FROM MakerCheckerData d where d.status='PROCESS_FOR_VERIFIER' AND d.screenId=:screenId AND d.uniqueidentifier=:uniqueidentifier AND d.uniqueidentifier2=:uniqueidentifier2 AND d.uniqueidentifier3=:uniqueidentifier3"),
		@NamedQuery(name = "MakerCheckerData.fetchAllByViewerID", query = "SELECT d FROM MakerCheckerData d where  d.status='PROCESS' and d.viewerId=:viewerId "),
		@NamedQuery(name = "MakerCheckerData.fetchbyScreenId", query = "SELECT d FROM MakerCheckerData d where  d.status='PROCESS' and d.screenId IN :screenId ")
})

public class MakerCheckerData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MAKER_CHECKER_DATA_SEQ")
	@SequenceGenerator(name = "MAKER_CHECKER_DATA_SEQ", sequenceName = "MAKER_CHECKER_DATA_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "CONFIG_ID")
	private long configId;
	
	@Column(name = "SCREEN_ID")
	private long screenId;

	public long getScreenId() {
		return screenId;
	}

	public void setScreenId(long screenId) {
		this.screenId = screenId;
	}

	@Column(name = "REQUESTED_DATA")
	private String requestedData;

	@Column(name = "REQUESTED_BY")
	private int requestedBy;

	
	@Column(name = "REQUESTED_ON")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Karachi")
	private Timestamp requestedOn;

	@Column(name = "REQUESTED_BY_REMARKS")
	private String requestedByRemarks;

	

	@Column(name = "STATUS")
	private String status;

	@Column(name = "APPROVED_BY")
	private int approvedBy;

	@Column(name = "APPROVED_ON")
	private Timestamp approvedOn;

	@Column(name = "APPROVER_REMARKS")
	private String approverRemarks;

	@Column(name = "VIEWER_ID")
	private int viewerId;

	@Column(name = "REJECTED_BY")
	private int rejectedBy;

	@Column(name = "REJECTED_ON")
	private Timestamp rejectedOn;

	@Column(name = "REJECTION_REMARKS")
	private String rejectionRemarsk;

	@Column(name = "UNIQUE_IDENTIFIER")
	private String uniqueidentifier;

	@Column(name = "UNIQUE_IDENTIFIER_2")
	private String uniqueidentifier2;

	@Column(name = "UNIQUE_IDENTIFIER_3")
	private String uniqueidentifier3;

	@Column(name = "ACTION_TYPE")
	private String actionType;

	@Column(name = "PAGE_NAME")
	private String pageName;
	
	@Column(name = "VERIFIED_BY")
	private String verifiedBy;
	
	@Column(name = "VERIFIED_ON")
	private Timestamp verifiedOn;
	
	@Column(name = "VERIFIER_REMARKS")
	private String verifierRemarks;

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public Timestamp getVerifiedOn() {
		return verifiedOn;
	}

	public void setVerifiedOn(Timestamp verifiedOn) {
		this.verifiedOn = verifiedOn;
	}

	public String getVerifierRemarks() {
		return verifierRemarks;
	}

	public void setVerifierRemarks(String verifierRemarks) {
		this.verifierRemarks = verifierRemarks;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getConfigId() {
		return configId;
	}

	public void setConfigId(long configId) {
		this.configId = configId;
	}

	public String getRequestedData() {
		return requestedData;
	}

	public void setRequestedData(String requestedData) {
		this.requestedData = requestedData;
	}

	public int getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(int requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Timestamp getRequestedOn() {
		return requestedOn;
	}

	public void setRequestedOn(Timestamp requestedOn) {
		this.requestedOn = requestedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Timestamp getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Timestamp approvedOn) {
		this.approvedOn = approvedOn;
	}

	public String getApproverRemarks() {
		return approverRemarks;
	}

	public void setApproverRemarks(String approverRemarks) {
		this.approverRemarks = approverRemarks;
	}

	public int getViewerId() {
		return viewerId;
	}

	public void setViewerId(int viewerId) {
		this.viewerId = viewerId;
	}

	public int getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectedBy(int rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public Timestamp getRejectedOn() {
		return rejectedOn;
	}

	public void setRejectedOn(Timestamp rejectedOn) {
		this.rejectedOn = rejectedOn;
	}

	public String getRejectionRemarsk() {
		return rejectionRemarsk;
	}

	public void setRejectionRemarsk(String rejectionRemarsk) {
		this.rejectionRemarsk = rejectionRemarsk;
	}

	public String getUniqueidentifier() {
		return uniqueidentifier;
	}

	public void setUniqueidentifier(String uniqueidentifier) {
		this.uniqueidentifier = uniqueidentifier;
	}

	public String getUniqueidentifier2() {
		return uniqueidentifier2;
	}

	public void setUniqueidentifier2(String uniqueidentifier2) {
		this.uniqueidentifier2 = uniqueidentifier2;
	}

	public String getUniqueidentifier3() {
		return uniqueidentifier3;
	}

	public void setUniqueidentifier3(String uniqueidentifier3) {
		this.uniqueidentifier3 = uniqueidentifier3;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getRequestedByRemarks() {
		return requestedByRemarks;
	}

	public void setRequestedByRemarks(String requestedByRemarks) {
		this.requestedByRemarks = requestedByRemarks;
	}

}