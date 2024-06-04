package com.ag.metro.entity;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
/**
 * The persistent class for the METRO_TXN_DETAILS database table.
 * 
 */
@Entity
@Table(name="METRO_TXN_DETAILS")
@NamedQuery(name="MetroTxnDetail.findAll", query="SELECT m FROM MetroTxnDetail m")
public class MetroTxnDetail  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="ACC_NO")
	private String accNo;

	@Column(name="AUTH_ID")
	private String authId;

	@Column(name="BALANCE")
	private Double balance;

	@Column(name="BATCH_NO")
	private String batchNo;

	@Column(name="CARD_BIN")
	private String cardBin;

	@Column(name="CARD_EXPIRY")
	private String cardExpiry;

	@Column(name="CARD_NO")
	private String cardNo;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	@Column(name="DBA_NAME")
	private String dbaName;

	private String dc1;

	@Id
	private int id;

	@Column(name="INV_NUMBER")
	private String invNumber;

	@Column(name="MEMBER_ID")
	private String memberId;

	private String mid;

	@Column(name="REJ_REASON")
	private String rejReason;

	@Column(name="RET_REF")
	private String retRef;

	@Column(name="RSP_CODE")
	private String rspCode;

	private String settled;

	@Column(name="SETTLED_ON")
	private Timestamp settledOn;

	@Column(name="SHOW_CARD_NO")
	private String showCardNo;

	private String tid;

	@Column(name="TOPUP_BY")
	private String topupBy;

	@Column(name="TOPUP_ID")
	private Integer topupId;

	@Column(name="TOPUP_STATUS")
	private String topupStatus;

	@Column(name="TXN_AMT")
	private Double txnAmt;

	@Column(name="TXN_DATE")
	private Timestamp txnDate;

	@Column(name="TXN_TYPE")
	private String txnType;

	public MetroTxnDetail() {
	}

	public String getAccNo() {
		return this.accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAuthId() {
		return this.authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCardBin() {
		return this.cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getCardExpiry() {
		return this.cardExpiry;
	}

	public void setCardExpiry(String cardExpiry) {
		this.cardExpiry = cardExpiry;
	}

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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

	public String getDbaName() {
		return this.dbaName;
	}

	public void setDbaName(String dbaName) {
		this.dbaName = dbaName;
	}

	public String getDc1() {
		return this.dc1;
	}

	public void setDc1(String dc1) {
		this.dc1 = dc1;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvNumber() {
		return this.invNumber;
	}

	public void setInvNumber(String invNumber) {
		this.invNumber = invNumber;
	}

	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getRejReason() {
		return this.rejReason;
	}

	public void setRejReason(String rejReason) {
		this.rejReason = rejReason;
	}

	public String getRetRef() {
		return this.retRef;
	}

	public void setRetRef(String retRef) {
		this.retRef = retRef;
	}

	public String getRspCode() {
		return this.rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getSettled() {
		return this.settled;
	}

	public void setSettled(String settled) {
		this.settled = settled;
	}

	public Timestamp getSettledOn() {
		return this.settledOn;
	}

	public void setSettledOn(Timestamp settledOn) {
		this.settledOn = settledOn;
	}

	public String getShowCardNo() {
		return this.showCardNo;
	}

	public void setShowCardNo(String showCardNo) {
		this.showCardNo = showCardNo;
	}

	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTopupBy() {
		return this.topupBy;
	}

	public void setTopupBy(String topupBy) {
		this.topupBy = topupBy;
	}

	public Integer getTopupId() {
		return this.topupId;
	}

	public void setTopupId(Integer topupId) {
		this.topupId = topupId;
	}

	public String getTopupStatus() {
		return this.topupStatus;
	}

	public void setTopupStatus(String topupStatus) {
		this.topupStatus = topupStatus;
	}

	public Double getTxnAmt() {
		return this.txnAmt;
	}

	public void setTxnAmt(Double txnAmt) {
		this.txnAmt = txnAmt;
	}

	public Timestamp getTxnDate() {
		return this.txnDate;
	}

	public void setTxnDate(Timestamp txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnType() {
		return this.txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	
	
}
