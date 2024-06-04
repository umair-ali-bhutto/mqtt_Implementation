package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the BATCH_SETTLEMENT database table.
 * 
 */
@Entity
@Table(name = "MAKER_CHECKER_CONFIG")
@NamedQueries({
		@NamedQuery(name = "MakerCheckerConfig.fetchByScreenID", query = "SELECT d FROM MakerCheckerConfig d where d.isActive=1 and d.screenId=:screenId"),
		@NamedQuery(name = "MakerCheckerConfig.fetchByID", query = "SELECT d FROM MakerCheckerConfig d where d.isActive=1 and d.id=:id"),
		@NamedQuery(name = "MakerCheckerConfig.fetchByCorpId", query = "SELECT d FROM MakerCheckerConfig d where d.isActive=1 and d.corpId=:corpId"),
		@NamedQuery(name = "MakerCheckerConfig.updateGroupAssignedApproverById", query = "UPDATE MakerCheckerConfig SET groupAssignedApprover=:groupId WHERE isActive = 1 AND id IN :ids "),
		@NamedQuery(name = "MakerCheckerConfig.updateGroupAssignedVerifierById", query = "UPDATE MakerCheckerConfig SET groupAssignedVerifier=:groupId WHERE isActive = 1 AND id IN :ids "),
		@NamedQuery(name = "MakerCheckerConfig.fetchByApproverGroupId", query = "SELECT d FROM MakerCheckerConfig d where d.isActive=1 and d.groupAssignedApprover=:groupId"),
		@NamedQuery(name = "MakerCheckerConfig.fetchByVerifierGroupId", query = "SELECT d FROM MakerCheckerConfig d where d.isActive=1 and d.groupAssignedVerifier=:groupId") })
public class MakerCheckerConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name = "SCREEN_ID")
	private int screenId;

	@Column(name = "APPROVER_ID")
	private int approverId;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "REQUEST_MODEL")
	private String requestModel;

	@Column(name = "APPROVER_ACTION_CLASS")
	private String approverActionClass;

	@Column(name = "REJECTION_ACTION_CLASS")
	private String rejectionActionClass;

	@Column(name = "PAGE_NAME")
	private String pageName;

	@Column(name = "GROUP_ASSIGNED_APPROVER")
	private int groupAssignedApprover;

	@Column(name = "GROUP_ASSIGNED_VERIFIER")
	private int groupAssignedVerifier;

	@Column(name = "VERIFIER_ID")
	private int verifierId;

	@Column(name = "CORP_ID")
	private String corpId;

	public int getGroupAssignedApprover() {
		return groupAssignedApprover;
	}

	public void setGroupAssignedApprover(int groupAssignedApprover) {
		this.groupAssignedApprover = groupAssignedApprover;
	}

	public int getGroupAssignedVerifier() {
		return groupAssignedVerifier;
	}

	public void setGroupAssignedVerifier(int groupAssignedVerifier) {
		this.groupAssignedVerifier = groupAssignedVerifier;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public int getVerifierId() {
		return verifierId;
	}

	public void setVerifierId(int verifierId) {
		this.verifierId = verifierId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getScreenId() {
		return screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public int getApproverId() {
		return approverId;
	}

	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getRequestModel() {
		return requestModel;
	}

	public void setRequestModel(String requestModel) {
		this.requestModel = requestModel;
	}

	public String getApproverActionClass() {
		return approverActionClass;
	}

	public void setApproverActionClass(String approverActionClass) {
		this.approverActionClass = approverActionClass;
	}

	public String getRejectionActionClass() {
		return rejectionActionClass;
	}

	public void setRejectionActionClass(String rejectionActionClass) {
		this.rejectionActionClass = rejectionActionClass;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}