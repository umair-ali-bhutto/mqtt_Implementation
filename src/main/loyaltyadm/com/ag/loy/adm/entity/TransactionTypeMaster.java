package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION_TYPES_MASTER")
@NamedQueries({
		@NamedQuery(name = "TransactionTypeMaster.retrieveAll", query = "SELECT a FROM TransactionTypeMaster a WHERE a.id.corpid=:corpId"),
		@NamedQuery(name = "TransactionTypeMaster.retrieveAllById", query = "SELECT a FROM TransactionTypeMaster a WHERE a.id.corpid=:corpId AND a.id.txnType=:txnType"), })
public class TransactionTypeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TransactionTypesMasterPK id;

	@Column(name = "ACCOUNT_CR")
	private String accountCr;

	@Column(name = "ACCOUNT_DR")
	private String accountDr;

	private String active;

	@Column(name = "CR_BY")
	private String crBy;

	@Column(name = "CR_ON")
	private Date crOn;

	@Column(name = "INCL_SETTLMNT")
	private String inclSettlmnt;

	@Column(name = "MAPPED_ID")
	private String mappedId;

	@Column(name = "MAPPED_TO")
	private String mappedTo;

	@Column(name = "TXN_FLOW")
	private String txnFlow;

	@Column(name = "UPD_BY")
	private String updBy;

	@Column(name = "UPD_ON")
	private Date updOn;

	public TransactionTypeMaster() {

	}

	public TransactionTypesMasterPK getId() {
		return this.id;
	}

	public void setId(TransactionTypesMasterPK id) {
		this.id = id;
	}

	public String getAccountCr() {
		return this.accountCr;
	}

	public void setAccountCr(String accountCr) {
		this.accountCr = accountCr;
	}

	public String getAccountDr() {
		return this.accountDr;
	}

	public void setAccountDr(String accountDr) {
		this.accountDr = accountDr;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCrBy() {
		return this.crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public Date getCrOn() {
		return this.crOn;
	}

	public void setCrOn(Date crOn) {
		this.crOn = crOn;
	}

	public String getInclSettlmnt() {
		return this.inclSettlmnt;
	}

	public void setInclSettlmnt(String inclSettlmnt) {
		this.inclSettlmnt = inclSettlmnt;
	}

	public String getMappedId() {
		return this.mappedId;
	}

	public void setMappedId(String mappedId) {
		this.mappedId = mappedId;
	}

	public String getMappedTo() {
		return this.mappedTo;
	}

	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}

	public String getTxnFlow() {
		return this.txnFlow;
	}

	public void setTxnFlow(String txnFlow) {
		this.txnFlow = txnFlow;
	}

	public String getUpdBy() {
		return this.updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public Date getUpdOn() {
		return this.updOn;
	}

	public void setUpdOn(Date updOn) {
		this.updOn = updOn;
	}

}