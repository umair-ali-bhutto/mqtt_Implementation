package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACCOUNT_PAYMENTS database table.
 * 
 */
@Entity
@Table(name="ACCOUNT_PAYMENTS")
@NamedQuery(name="AccountPayments.findAll", query="SELECT a FROM AccountPayments a where a.corpid=:corpid")
public class AccountPayments implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACC_CR")
	private String accCr;

	@Column(name="ACC_CURRENCY")
	private String accCurrency;

	@Column(name="ACC_DR")
	private String accDr;

	@Column(name="ACC_ID")
	private String accId;

	private String acctitle;

	private String acctype;

	private String acctypea;

	private String acctypeb;

	private String acctypec;

	private String acctyped;

	private BigDecimal balance;

	private String chid;

	private String cid;

	@Column(name="CONV_RATE")
	private BigDecimal convRate;

	private String corpid;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	@Column(name="DEV_ADD_CAP")
	private String devAddCap;

	@Column(name="DEV_AMOUNT")
	private BigDecimal devAmount;

	@Column(name="DEV_BATCH")
	private String devBatch;

	@Column(name="DEV_CAP")
	private String devCap;

	@Column(name="DEV_CURRENCY")
	private String devCurrency;

	@Column(name="DEV_ENT")
	private String devEnt;

	@Column(name="DEV_ID0")
	private String devId0;

	@Column(name="DEV_ID1")
	private String devId1;

	@Column(name="DEV_ID2")
	private String devId2;

	@Column(name="DEV_ID3")
	private String devId3;

	@Column(name="DEV_ID4")
	private String devId4;

	@Column(name="DEV_MID")
	private String devMid;

	@Column(name="DEV_TID")
	private String devTid;

	@Column(name="DRCR_FLAG")
	private String drcrFlag;

	private String featureid;

	private BigDecimal otherbalance;

	@Column(name="OTHR_ACC_AMT")
	private BigDecimal othrAccAmt;

	@Column(name="OTHR_ACC_CODE")
	private String othrAccCode;

	@Column(name="OTHR_ACC_ID")
	private String othrAccId;

	@Column(name="OTHR_ACCTITLE")
	private String othrAcctitle;

	@Column(name="OTHR_ACCTYPE")
	private String othrAcctype;

	@Column(name="OTHR_ACCTYPEA")
	private String othrAcctypea;

	@Column(name="OTHR_ACCTYPEB")
	private String othrAcctypeb;

	@Column(name="OTHR_ACCTYPEC")
	private String othrAcctypec;

	@Column(name="OTHR_ACCTYPED")
	private String othrAcctyped;

	@Column(name="OTHR_CORP_ID")
	private String othrCorpId;

	@Column(name="OTHR_CURRENCY")
	private String othrCurrency;

	@Column(name="PROD_PUR_FLG")
	private String prodPurFlg;

	@Column(name="PROD_PUR_ID")
	private String prodPurId;

	@Column(name="PRODUCT_ID")
	private String productId;

	private String reason;

	private BigDecimal resbalance;

	private String respcode;

	@Column(name="RET_CURRENCY")
	private String retCurrency;

	private BigDecimal retamount;

	@Id
	@Column(name="RRN")
	private String rrn;

	@Column(name="SOURCE_REF")
	private String sourceRef;

	@Column(name="TXN_AMOUNT")
	private BigDecimal txnAmount;

	@Column(name="TXN_AUTH_DATE1")
	private Timestamp txnAuthDate1;

	@Column(name="TXN_AUTH_DATE2")
	private Timestamp txnAuthDate2;

	@Column(name="TXN_CURRENCY")
	private String txnCurrency;

	@Temporal(TemporalType.DATE)
	@Column(name="TXN_DATE")
	private Date txnDate;

	@Column(name="TXN_FEE")
	private BigDecimal txnFee;

	@Column(name="TXN_FEE_OTHR0")
	private BigDecimal txnFeeOthr0;

	@Column(name="TXN_FEE_OTHR1")
	private BigDecimal txnFeeOthr1;

	@Column(name="TXN_FEE_OTHR2")
	private BigDecimal txnFeeOthr2;

	@Column(name="TXN_FEE_SRC")
	private String txnFeeSrc;

	@Column(name="TXN_FEE_SRC0")
	private String txnFeeSrc0;

	@Column(name="TXN_FEE_SRC1")
	private String txnFeeSrc1;

	@Column(name="TXN_FEE_SRC2")
	private String txnFeeSrc2;

	@Column(name="TXN_REF1")
	private String txnRef1;

	@Column(name="TXN_REF2")
	private String txnRef2;

	@Column(name="TXN_REF3")
	private String txnRef3;

	@Column(name="TXN_REF4")
	private String txnRef4;

	@Column(name="TXN_S_BY")
	private String txnSBy;

	@Column(name="TXN_S_PREV")
	private String txnSPrev;

	@Column(name="TXN_S_REF")
	private String txnSRef;

	@Column(name="TXN_STATUS")
	private String txnStatus;

	@Column(name="TXN_TAX")
	private BigDecimal txnTax;

	@Column(name="TXN_TAX_ID")
	private String txnTaxId;

	@Column(name="TXN_TAX_ID0")
	private String txnTaxId0;

	@Column(name="TXN_TAX_ID1")
	private String txnTaxId1;

	@Column(name="TXN_TAX_ID2")
	private String txnTaxId2;

	@Column(name="TXN_TAX0")
	private BigDecimal txnTax0;

	@Column(name="TXN_TAX1")
	private BigDecimal txnTax1;

	@Column(name="TXN_TAX2")
	private BigDecimal txnTax2;

	@Column(name="TXN_TYPE")
	private String txnType;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_BY_TYPE")
	private String updByType;

	@Column(name="UPD_ON")
	private Timestamp updOn;

	private String userid;

	public AccountPayments() {
	}

	public String getAccCr() {
		return this.accCr;
	}

	public void setAccCr(String accCr) {
		this.accCr = accCr;
	}

	public String getAccCurrency() {
		return this.accCurrency;
	}

	public void setAccCurrency(String accCurrency) {
		this.accCurrency = accCurrency;
	}

	public String getAccDr() {
		return this.accDr;
	}

	public void setAccDr(String accDr) {
		this.accDr = accDr;
	}

	public String getAccId() {
		return this.accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getAcctitle() {
		return this.acctitle;
	}

	public void setAcctitle(String acctitle) {
		this.acctitle = acctitle;
	}

	public String getAcctype() {
		return this.acctype;
	}

	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}

	public String getAcctypea() {
		return this.acctypea;
	}

	public void setAcctypea(String acctypea) {
		this.acctypea = acctypea;
	}

	public String getAcctypeb() {
		return this.acctypeb;
	}

	public void setAcctypeb(String acctypeb) {
		this.acctypeb = acctypeb;
	}

	public String getAcctypec() {
		return this.acctypec;
	}

	public void setAcctypec(String acctypec) {
		this.acctypec = acctypec;
	}

	public String getAcctyped() {
		return this.acctyped;
	}

	public void setAcctyped(String acctyped) {
		this.acctyped = acctyped;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getChid() {
		return this.chid;
	}

	public void setChid(String chid) {
		this.chid = chid;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public BigDecimal getConvRate() {
		return this.convRate;
	}

	public void setConvRate(BigDecimal convRate) {
		this.convRate = convRate;
	}

	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
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

	public String getDevAddCap() {
		return this.devAddCap;
	}

	public void setDevAddCap(String devAddCap) {
		this.devAddCap = devAddCap;
	}

	public BigDecimal getDevAmount() {
		return this.devAmount;
	}

	public void setDevAmount(BigDecimal devAmount) {
		this.devAmount = devAmount;
	}

	public String getDevBatch() {
		return this.devBatch;
	}

	public void setDevBatch(String devBatch) {
		this.devBatch = devBatch;
	}

	public String getDevCap() {
		return this.devCap;
	}

	public void setDevCap(String devCap) {
		this.devCap = devCap;
	}

	public String getDevCurrency() {
		return this.devCurrency;
	}

	public void setDevCurrency(String devCurrency) {
		this.devCurrency = devCurrency;
	}

	public String getDevEnt() {
		return this.devEnt;
	}

	public void setDevEnt(String devEnt) {
		this.devEnt = devEnt;
	}

	public String getDevId0() {
		return this.devId0;
	}

	public void setDevId0(String devId0) {
		this.devId0 = devId0;
	}

	public String getDevId1() {
		return this.devId1;
	}

	public void setDevId1(String devId1) {
		this.devId1 = devId1;
	}

	public String getDevId2() {
		return this.devId2;
	}

	public void setDevId2(String devId2) {
		this.devId2 = devId2;
	}

	public String getDevId3() {
		return this.devId3;
	}

	public void setDevId3(String devId3) {
		this.devId3 = devId3;
	}

	public String getDevId4() {
		return this.devId4;
	}

	public void setDevId4(String devId4) {
		this.devId4 = devId4;
	}

	public String getDevMid() {
		return this.devMid;
	}

	public void setDevMid(String devMid) {
		this.devMid = devMid;
	}

	public String getDevTid() {
		return this.devTid;
	}

	public void setDevTid(String devTid) {
		this.devTid = devTid;
	}

	public String getDrcrFlag() {
		return this.drcrFlag;
	}

	public void setDrcrFlag(String drcrFlag) {
		this.drcrFlag = drcrFlag;
	}

	public String getFeatureid() {
		return this.featureid;
	}

	public void setFeatureid(String featureid) {
		this.featureid = featureid;
	}

	public BigDecimal getOtherbalance() {
		return this.otherbalance;
	}

	public void setOtherbalance(BigDecimal otherbalance) {
		this.otherbalance = otherbalance;
	}

	public BigDecimal getOthrAccAmt() {
		return this.othrAccAmt;
	}

	public void setOthrAccAmt(BigDecimal othrAccAmt) {
		this.othrAccAmt = othrAccAmt;
	}

	public String getOthrAccCode() {
		return this.othrAccCode;
	}

	public void setOthrAccCode(String othrAccCode) {
		this.othrAccCode = othrAccCode;
	}

	public String getOthrAccId() {
		return this.othrAccId;
	}

	public void setOthrAccId(String othrAccId) {
		this.othrAccId = othrAccId;
	}

	public String getOthrAcctitle() {
		return this.othrAcctitle;
	}

	public void setOthrAcctitle(String othrAcctitle) {
		this.othrAcctitle = othrAcctitle;
	}

	public String getOthrAcctype() {
		return this.othrAcctype;
	}

	public void setOthrAcctype(String othrAcctype) {
		this.othrAcctype = othrAcctype;
	}

	public String getOthrAcctypea() {
		return this.othrAcctypea;
	}

	public void setOthrAcctypea(String othrAcctypea) {
		this.othrAcctypea = othrAcctypea;
	}

	public String getOthrAcctypeb() {
		return this.othrAcctypeb;
	}

	public void setOthrAcctypeb(String othrAcctypeb) {
		this.othrAcctypeb = othrAcctypeb;
	}

	public String getOthrAcctypec() {
		return this.othrAcctypec;
	}

	public void setOthrAcctypec(String othrAcctypec) {
		this.othrAcctypec = othrAcctypec;
	}

	public String getOthrAcctyped() {
		return this.othrAcctyped;
	}

	public void setOthrAcctyped(String othrAcctyped) {
		this.othrAcctyped = othrAcctyped;
	}

	public String getOthrCorpId() {
		return this.othrCorpId;
	}

	public void setOthrCorpId(String othrCorpId) {
		this.othrCorpId = othrCorpId;
	}

	public String getOthrCurrency() {
		return this.othrCurrency;
	}

	public void setOthrCurrency(String othrCurrency) {
		this.othrCurrency = othrCurrency;
	}

	public String getProdPurFlg() {
		return this.prodPurFlg;
	}

	public void setProdPurFlg(String prodPurFlg) {
		this.prodPurFlg = prodPurFlg;
	}

	public String getProdPurId() {
		return this.prodPurId;
	}

	public void setProdPurId(String prodPurId) {
		this.prodPurId = prodPurId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BigDecimal getResbalance() {
		return this.resbalance;
	}

	public void setResbalance(BigDecimal resbalance) {
		this.resbalance = resbalance;
	}

	public String getRespcode() {
		return this.respcode;
	}

	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}

	public String getRetCurrency() {
		return this.retCurrency;
	}

	public void setRetCurrency(String retCurrency) {
		this.retCurrency = retCurrency;
	}

	public BigDecimal getRetamount() {
		return this.retamount;
	}

	public void setRetamount(BigDecimal retamount) {
		this.retamount = retamount;
	}

	public String getRrn() {
		return this.rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getSourceRef() {
		return this.sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public BigDecimal getTxnAmount() {
		return this.txnAmount;
	}

	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}

	public Timestamp getTxnAuthDate1() {
		return this.txnAuthDate1;
	}

	public void setTxnAuthDate1(Timestamp txnAuthDate1) {
		this.txnAuthDate1 = txnAuthDate1;
	}

	public Timestamp getTxnAuthDate2() {
		return this.txnAuthDate2;
	}

	public void setTxnAuthDate2(Timestamp txnAuthDate2) {
		this.txnAuthDate2 = txnAuthDate2;
	}

	public String getTxnCurrency() {
		return this.txnCurrency;
	}

	public void setTxnCurrency(String txnCurrency) {
		this.txnCurrency = txnCurrency;
	}

	public Date getTxnDate() {
		return this.txnDate;
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	public BigDecimal getTxnFee() {
		return this.txnFee;
	}

	public void setTxnFee(BigDecimal txnFee) {
		this.txnFee = txnFee;
	}

	public BigDecimal getTxnFeeOthr0() {
		return this.txnFeeOthr0;
	}

	public void setTxnFeeOthr0(BigDecimal txnFeeOthr0) {
		this.txnFeeOthr0 = txnFeeOthr0;
	}

	public BigDecimal getTxnFeeOthr1() {
		return this.txnFeeOthr1;
	}

	public void setTxnFeeOthr1(BigDecimal txnFeeOthr1) {
		this.txnFeeOthr1 = txnFeeOthr1;
	}

	public BigDecimal getTxnFeeOthr2() {
		return this.txnFeeOthr2;
	}

	public void setTxnFeeOthr2(BigDecimal txnFeeOthr2) {
		this.txnFeeOthr2 = txnFeeOthr2;
	}

	public String getTxnFeeSrc() {
		return this.txnFeeSrc;
	}

	public void setTxnFeeSrc(String txnFeeSrc) {
		this.txnFeeSrc = txnFeeSrc;
	}

	public String getTxnFeeSrc0() {
		return this.txnFeeSrc0;
	}

	public void setTxnFeeSrc0(String txnFeeSrc0) {
		this.txnFeeSrc0 = txnFeeSrc0;
	}

	public String getTxnFeeSrc1() {
		return this.txnFeeSrc1;
	}

	public void setTxnFeeSrc1(String txnFeeSrc1) {
		this.txnFeeSrc1 = txnFeeSrc1;
	}

	public String getTxnFeeSrc2() {
		return this.txnFeeSrc2;
	}

	public void setTxnFeeSrc2(String txnFeeSrc2) {
		this.txnFeeSrc2 = txnFeeSrc2;
	}

	public String getTxnRef1() {
		return this.txnRef1;
	}

	public void setTxnRef1(String txnRef1) {
		this.txnRef1 = txnRef1;
	}

	public String getTxnRef2() {
		return this.txnRef2;
	}

	public void setTxnRef2(String txnRef2) {
		this.txnRef2 = txnRef2;
	}

	public String getTxnRef3() {
		return this.txnRef3;
	}

	public void setTxnRef3(String txnRef3) {
		this.txnRef3 = txnRef3;
	}

	public String getTxnRef4() {
		return this.txnRef4;
	}

	public void setTxnRef4(String txnRef4) {
		this.txnRef4 = txnRef4;
	}

	public String getTxnSBy() {
		return this.txnSBy;
	}

	public void setTxnSBy(String txnSBy) {
		this.txnSBy = txnSBy;
	}

	public String getTxnSPrev() {
		return this.txnSPrev;
	}

	public void setTxnSPrev(String txnSPrev) {
		this.txnSPrev = txnSPrev;
	}

	public String getTxnSRef() {
		return this.txnSRef;
	}

	public void setTxnSRef(String txnSRef) {
		this.txnSRef = txnSRef;
	}

	public String getTxnStatus() {
		return this.txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public BigDecimal getTxnTax() {
		return this.txnTax;
	}

	public void setTxnTax(BigDecimal txnTax) {
		this.txnTax = txnTax;
	}

	public String getTxnTaxId() {
		return this.txnTaxId;
	}

	public void setTxnTaxId(String txnTaxId) {
		this.txnTaxId = txnTaxId;
	}

	public String getTxnTaxId0() {
		return this.txnTaxId0;
	}

	public void setTxnTaxId0(String txnTaxId0) {
		this.txnTaxId0 = txnTaxId0;
	}

	public String getTxnTaxId1() {
		return this.txnTaxId1;
	}

	public void setTxnTaxId1(String txnTaxId1) {
		this.txnTaxId1 = txnTaxId1;
	}

	public String getTxnTaxId2() {
		return this.txnTaxId2;
	}

	public void setTxnTaxId2(String txnTaxId2) {
		this.txnTaxId2 = txnTaxId2;
	}

	public BigDecimal getTxnTax0() {
		return this.txnTax0;
	}

	public void setTxnTax0(BigDecimal txnTax0) {
		this.txnTax0 = txnTax0;
	}

	public BigDecimal getTxnTax1() {
		return this.txnTax1;
	}

	public void setTxnTax1(BigDecimal txnTax1) {
		this.txnTax1 = txnTax1;
	}

	public BigDecimal getTxnTax2() {
		return this.txnTax2;
	}

	public void setTxnTax2(BigDecimal txnTax2) {
		this.txnTax2 = txnTax2;
	}

	public String getTxnType() {
		return this.txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getUpdBy() {
		return this.updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public String getUpdByType() {
		return this.updByType;
	}

	public void setUpdByType(String updByType) {
		this.updByType = updByType;
	}

	public Timestamp getUpdOn() {
		return this.updOn;
	}

	public void setUpdOn(Timestamp updOn) {
		this.updOn = updOn;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}