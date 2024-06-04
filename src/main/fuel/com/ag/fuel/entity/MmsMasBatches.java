package com.ag.fuel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the MMS_MAS_BATCHES_C database table.
 * 
 */
//@Entity
@Table(name="MMS_MAS_BATCHES")
@NamedQuery(name="MmsMasBatches.findAll", query="SELECT m FROM MmsMasBatches m")
public class MmsMasBatches implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACC_NO")
	private String accNo;

	@Column(name="ACTIVITY_TYPE")
	private String activityType;

	private String approved;

	@Column(name="APPROVED_BY")
	private String approvedBy;

	@Column(name="APPROVED_ON")
	private Timestamp approvedOn;

	@Column(name="ASSOC_WITH")
	private String assocWith;

	@Column(name="BATCH_NO")
	private String batchNo;

	@Column(name="C_CODE")
	private BigDecimal cCode;

	@Column(name="C_T_CODE")
	private BigDecimal cTCode;

	@Column(name="CHIP_PROG")
	private String chipProg;

	@Column(name="CHIP_PROG_BY")
	private String chipProgBy;

	@Column(name="CHIP_PROG_ON")
	private Timestamp chipProgOn;

	@Column(name="CN_NUMBER")
	private String cnNumber;

	@Column(name="CO_CODE")
	private String coCode;

	private String cour;

	@Column(name="COUR_BY")
	private String courBy;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="DATA_ENT")
	private String dataEnt;

	@Column(name="DATA_ENT_BY")
	private String dataEntBy;

	@Column(name="DATE_COUR")
	private Timestamp dateCour;

	@Column(name="DATE_CR")
	private Timestamp dateCr;

	@Column(name="DATE_DATA_ENT")
	private Timestamp dateDataEnt;

	@Column(name="DATE_ENV")
	private Timestamp dateEnv;

	@Column(name="DATE_PRO")
	private Timestamp datePro;

	@Column(name="DATE_VER")
	private Timestamp dateVer;

	private String description;

	@Column(name="ENV_BY")
	private String envBy;

	private String enveloped;

	@Column(name="EXPORT_BY")
	private String exportBy;

	@Column(name="EXPORT_ON")
	private Timestamp exportOn;

	private String exported;

	@Column(name="EXPORTED_TO_NFC")
	private String exportedToNfc;

	@Column(name="EXPORTED_TO_NFC_BY")
	private String exportedToNfcBy;

	@Column(name="EXPORTED_TO_NFC_ON")
	private Timestamp exportedToNfcOn;

	@Column(name="EXPORTED_TO_TMS")
	private String exportedToTms;

	@Column(name="ISSUER_ID")
	private String issuerId;

	@Column(name="MEMBER_ID")
	private String memberId;

	private String nii;

	@Column(name="P_CODE")
	private BigDecimal pCode;

	private String priority;

	@Column(name="PRO_BY")
	private String proBy;

	private String production;

	@Column(name="RECORD_ENTERED")
	private BigDecimal recordEntered;

	private BigDecimal records;

	private String remarks;

	@Column(name="STATUS_CODE")
	private String statusCode;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;

	@Column(name="VER_BY")
	private String verBy;

	private String verified;

	public MmsMasBatches() {
	}

	public String getAccNo() {
		return this.accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getActivityType() {
		return this.activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getApproved() {
		return this.approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Timestamp getApprovedOn() {
		return this.approvedOn;
	}

	public void setApprovedOn(Timestamp approvedOn) {
		this.approvedOn = approvedOn;
	}

	public String getAssocWith() {
		return this.assocWith;
	}

	public void setAssocWith(String assocWith) {
		this.assocWith = assocWith;
	}

	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public BigDecimal getCCode() {
		return this.cCode;
	}

	public void setCCode(BigDecimal cCode) {
		this.cCode = cCode;
	}

	public BigDecimal getCTCode() {
		return this.cTCode;
	}

	public void setCTCode(BigDecimal cTCode) {
		this.cTCode = cTCode;
	}

	public String getChipProg() {
		return this.chipProg;
	}

	public void setChipProg(String chipProg) {
		this.chipProg = chipProg;
	}

	public String getChipProgBy() {
		return this.chipProgBy;
	}

	public void setChipProgBy(String chipProgBy) {
		this.chipProgBy = chipProgBy;
	}

	public Timestamp getChipProgOn() {
		return this.chipProgOn;
	}

	public void setChipProgOn(Timestamp chipProgOn) {
		this.chipProgOn = chipProgOn;
	}

	public String getCnNumber() {
		return this.cnNumber;
	}

	public void setCnNumber(String cnNumber) {
		this.cnNumber = cnNumber;
	}

	public String getCoCode() {
		return this.coCode;
	}

	public void setCoCode(String coCode) {
		this.coCode = coCode;
	}

	public String getCour() {
		return this.cour;
	}

	public void setCour(String cour) {
		this.cour = cour;
	}

	public String getCourBy() {
		return this.courBy;
	}

	public void setCourBy(String courBy) {
		this.courBy = courBy;
	}

	public String getCrBy() {
		return this.crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public String getDataEnt() {
		return this.dataEnt;
	}

	public void setDataEnt(String dataEnt) {
		this.dataEnt = dataEnt;
	}

	public String getDataEntBy() {
		return this.dataEntBy;
	}

	public void setDataEntBy(String dataEntBy) {
		this.dataEntBy = dataEntBy;
	}

	public Timestamp getDateCour() {
		return this.dateCour;
	}

	public void setDateCour(Timestamp dateCour) {
		this.dateCour = dateCour;
	}

	public Timestamp getDateCr() {
		return this.dateCr;
	}

	public void setDateCr(Timestamp dateCr) {
		this.dateCr = dateCr;
	}

	public Timestamp getDateDataEnt() {
		return this.dateDataEnt;
	}

	public void setDateDataEnt(Timestamp dateDataEnt) {
		this.dateDataEnt = dateDataEnt;
	}

	public Timestamp getDateEnv() {
		return this.dateEnv;
	}

	public void setDateEnv(Timestamp dateEnv) {
		this.dateEnv = dateEnv;
	}

	public Timestamp getDatePro() {
		return this.datePro;
	}

	public void setDatePro(Timestamp datePro) {
		this.datePro = datePro;
	}

	public Timestamp getDateVer() {
		return this.dateVer;
	}

	public void setDateVer(Timestamp dateVer) {
		this.dateVer = dateVer;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEnvBy() {
		return this.envBy;
	}

	public void setEnvBy(String envBy) {
		this.envBy = envBy;
	}

	public String getEnveloped() {
		return this.enveloped;
	}

	public void setEnveloped(String enveloped) {
		this.enveloped = enveloped;
	}

	public String getExportBy() {
		return this.exportBy;
	}

	public void setExportBy(String exportBy) {
		this.exportBy = exportBy;
	}

	public Timestamp getExportOn() {
		return this.exportOn;
	}

	public void setExportOn(Timestamp exportOn) {
		this.exportOn = exportOn;
	}

	public String getExported() {
		return this.exported;
	}

	public void setExported(String exported) {
		this.exported = exported;
	}

	public String getExportedToNfc() {
		return this.exportedToNfc;
	}

	public void setExportedToNfc(String exportedToNfc) {
		this.exportedToNfc = exportedToNfc;
	}

	public String getExportedToNfcBy() {
		return this.exportedToNfcBy;
	}

	public void setExportedToNfcBy(String exportedToNfcBy) {
		this.exportedToNfcBy = exportedToNfcBy;
	}

	public Timestamp getExportedToNfcOn() {
		return this.exportedToNfcOn;
	}

	public void setExportedToNfcOn(Timestamp exportedToNfcOn) {
		this.exportedToNfcOn = exportedToNfcOn;
	}

	public String getExportedToTms() {
		return this.exportedToTms;
	}

	public void setExportedToTms(String exportedToTms) {
		this.exportedToTms = exportedToTms;
	}

	public String getIssuerId() {
		return this.issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNii() {
		return this.nii;
	}

	public void setNii(String nii) {
		this.nii = nii;
	}

	public BigDecimal getPCode() {
		return this.pCode;
	}

	public void setPCode(BigDecimal pCode) {
		this.pCode = pCode;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getProBy() {
		return this.proBy;
	}

	public void setProBy(String proBy) {
		this.proBy = proBy;
	}

	public String getProduction() {
		return this.production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public BigDecimal getRecordEntered() {
		return this.recordEntered;
	}

	public void setRecordEntered(BigDecimal recordEntered) {
		this.recordEntered = recordEntered;
	}

	public BigDecimal getRecords() {
		return this.records;
	}

	public void setRecords(BigDecimal records) {
		this.records = records;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getUpdBy() {
		return this.updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public Timestamp getUpdOn() {
		return this.updOn;
	}

	public void setUpdOn(Timestamp updOn) {
		this.updOn = updOn;
	}

	public String getVerBy() {
		return this.verBy;
	}

	public void setVerBy(String verBy) {
		this.verBy = verBy;
	}

	public String getVerified() {
		return this.verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

}