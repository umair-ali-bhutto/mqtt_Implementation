package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ACCOUNT_MASTER database table.
 * 
 */
@Entity
@Table(name="ACCOUNT_MASTER")
@NamedQuery(name="AccountMaster.fetchById", query="SELECT a FROM AccountMaster a WHERE a.accId =:accId and a.corpid =:corpid")
@NamedQuery(name="AccountMaster.fetchAllByCorpId", query="SELECT a FROM AccountMaster a WHERE a.corpid =:corpid")
public class AccountMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ACC_ID")
	private String accId;

	@Column(name="ACC_GL")
	private String accGl;

	private String acctitle;

	private String acctype;

	private String acctypea;

	private String acctypeb;

	private String acctypec;

	private String acctyped;

	private BigDecimal balance;

	private String cid;

	private String corpid;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	private String currency;

	@Column(name="GL_SOURCE")
	private String glSource;

	private String isdefault;

	private String other0;

	private String other1;

	private String other2;

	private String other3;

	private String other4;

	private String other5;

	private String other6;

	private BigDecimal other7;

	private BigDecimal other8;

	private BigDecimal other9;

	private BigDecimal otherbalance;

	@Column(name="REC_REF")
	private BigDecimal recRef;

	private BigDecimal resbalance;

	@Column(name="SOURCE_REF")
	private String sourceRef;

	private String status;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;

	public AccountMaster() {
	}

	public String getAccId() {
		return this.accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getAccGl() {
		return this.accGl;
	}

	public void setAccGl(String accGl) {
		this.accGl = accGl;
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

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getGlSource() {
		return this.glSource;
	}

	public void setGlSource(String glSource) {
		this.glSource = glSource;
	}

	public String getIsdefault() {
		return this.isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public String getOther0() {
		return this.other0;
	}

	public void setOther0(String other0) {
		this.other0 = other0;
	}

	public String getOther1() {
		return this.other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
	}

	public String getOther2() {
		return this.other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}

	public String getOther3() {
		return this.other3;
	}

	public void setOther3(String other3) {
		this.other3 = other3;
	}

	public String getOther4() {
		return this.other4;
	}

	public void setOther4(String other4) {
		this.other4 = other4;
	}

	public String getOther5() {
		return this.other5;
	}

	public void setOther5(String other5) {
		this.other5 = other5;
	}

	public String getOther6() {
		return this.other6;
	}

	public void setOther6(String other6) {
		this.other6 = other6;
	}

	public BigDecimal getOther7() {
		return this.other7;
	}

	public void setOther7(BigDecimal other7) {
		this.other7 = other7;
	}

	public BigDecimal getOther8() {
		return this.other8;
	}

	public void setOther8(BigDecimal other8) {
		this.other8 = other8;
	}

	public BigDecimal getOther9() {
		return this.other9;
	}

	public void setOther9(BigDecimal other9) {
		this.other9 = other9;
	}

	public BigDecimal getOtherbalance() {
		return this.otherbalance;
	}

	public void setOtherbalance(BigDecimal otherbalance) {
		this.otherbalance = otherbalance;
	}

	public BigDecimal getRecRef() {
		return this.recRef;
	}

	public void setRecRef(BigDecimal recRef) {
		this.recRef = recRef;
	}

	public BigDecimal getResbalance() {
		return this.resbalance;
	}

	public void setResbalance(BigDecimal resbalance) {
		this.resbalance = resbalance;
	}

	public String getSourceRef() {
		return this.sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}