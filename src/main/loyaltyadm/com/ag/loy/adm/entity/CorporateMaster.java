package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CORPORATE_MASTER database table.
 * 
 */
@Entity
@Table(name="CORPORATE_MASTER")
@NamedQueries({@NamedQuery(name="CorporateMaster.findAllByCorpID", query="SELECT c FROM CorporateMaster c where c.corpid=:corpid and c.active = 'Y' ORDER BY corpid "),
	@NamedQuery(name="CorporateMaster.findAll", query="SELECT c FROM CorporateMaster c ORDER BY corpid")})
public class CorporateMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String corpid;

	@Column(name="ACCOUNT_CR")
	private String accountCr;

	@Column(name="ACCOUNT_DR")
	private String accountDr;

	private String active;

	@Column(name="CR_BY")
	private String crBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CR_ON")
	private Date crOn;

	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name="PROGRAM_END")
	private Date programEnd;

	@Column(name="PROGRAM_NAME")
	private String programName;

	@Temporal(TemporalType.DATE)
	@Column(name="PROGRAM_START")
	private Date programStart;

	@Column(name="RDM_RATIO")
	private BigDecimal rdmRatio;

	@Column(name="UPD_BY")
	private String updBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_ON")
	private Date updOn;

	public CorporateMaster() {
	}

	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getProgramEnd() {
		return this.programEnd;
	}

	public void setProgramEnd(Date programEnd) {
		this.programEnd = programEnd;
	}

	public String getProgramName() {
		return this.programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Date getProgramStart() {
		return this.programStart;
	}

	public void setProgramStart(Date programStart) {
		this.programStart = programStart;
	}

	public BigDecimal getRdmRatio() {
		return this.rdmRatio;
	}

	public void setRdmRatio(BigDecimal rdmRatio) {
		this.rdmRatio = rdmRatio;
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