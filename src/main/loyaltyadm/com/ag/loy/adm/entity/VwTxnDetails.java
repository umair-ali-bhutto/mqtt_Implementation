package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "VW_TXN_DETAILS")
@NamedQuery(name = "VwTxnDetails.fetchAll", query = "SELECT d FROM VwTxnDetails d where d.corpid is not null")
public class VwTxnDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RRN")
	private String rrn;

	@Column(name = "CORPID")
	private String corpid;

	@Column(name = "CORP_NAME")
	private String corpName;

	@Column(name = "CID")
	private String cid;

	@Column(name = "ACC_ID")
	private String accId;

	@Column(name = "USERID")
	private String userid;

	@Column(name = "TXN_DATE")
	private String txnDate;

	@Column(name = "DEV_BATCH")
	private String devBatch;

	@Column(name = "SOURCE_REF")
	private String sourceRef;

	@Column(name = "DEV_MID")
	private String devMid;

	@Column(name = "DEV_TID")
	private String devTid;

	@Column(name = "CUSTTYPEDESC")
	private String custtypedesc;

	@Column(name = "USERDISPNAME")
	private String userdispname;

	@Column(name = "TXN_TYPE")
	private String txnType;

	@Column(name = "DEV_AMOUNT")
	private String devAmount;

	@Column(name = "TXN_AMOUNT")
	private String txnAmount;

	@Column(name = "OTHERBALANCE")
	private String otherbalance;

	@Column(name = "TXN_STATUS")
	private String txnStatus;

	@Column(name = "AWARD_RATIO")
	private String awardRatio;

	@Column(name = "RDM_RATIO")
	private String rdmRatio;

	@Column(name = "MER_NAME")
	private String merName;

	@Column(name = "P_NAME")
	private String pName;

	@Column(name = "DRCR_FLAG")
	private String drcrFlag;

	@Column(name = "CHID")
	private String chid;

	@Column(name = "TXN_S_REF")
	private String txnSRef;

	@Column(name = "SETTLED")
	private String settled;
	
	@Column(name = "SETTLED_DATE")
	private String settledDate;
	
	public String getSettledDate() {
		return settledDate;
	}

	public void setSettledDate(String settledDate) {
		this.settledDate = settledDate;
	}


	
	

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getDevBatch() {
		return devBatch;
	}

	public void setDevBatch(String devBatch) {
		this.devBatch = devBatch;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getSourceRef() {
		return sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getDevMid() {
		return devMid;
	}

	public void setDevMid(String devMid) {
		this.devMid = devMid;
	}

	public String getDevTid() {
		return devTid;
	}

	public void setDevTid(String devTid) {
		this.devTid = devTid;
	}

	public String getCusttypedesc() {
		return custtypedesc;
	}

	public void setCusttypedesc(String custtypedesc) {
		this.custtypedesc = custtypedesc;
	}

	public String getUserdispname() {
		return userdispname;
	}

	public void setUserdispname(String userdispname) {
		this.userdispname = userdispname;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getDevAmount() {
		return devAmount;
	}

	public void setDevAmount(String devAmount) {
		this.devAmount = devAmount;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getOtherbalance() {
		return otherbalance;
	}

	public void setOtherbalance(String otherbalance) {
		this.otherbalance = otherbalance;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public String getAwardRatio() {
		return awardRatio;
	}

	public void setAwardRatio(String awardRatio) {
		this.awardRatio = awardRatio;
	}

	public String getRdmRatio() {
		return rdmRatio;
	}

	public void setRdmRatio(String rdmRatio) {
		this.rdmRatio = rdmRatio;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getDrcrFlag() {
		return drcrFlag;
	}

	public void setDrcrFlag(String drcrFlag) {
		this.drcrFlag = drcrFlag;
	}

	public String getChid() {
		return chid;
	}

	public void setChid(String chid) {
		this.chid = chid;
	}

	public String getTxnSRef() {
		return txnSRef;
	}

	public void setTxnSRef(String txnSRef) {
		this.txnSRef = txnSRef;
	}

	public String getSettled() {
		return settled;
	}

	public void setSettled(String settled) {
		this.settled = settled;
	}
}
