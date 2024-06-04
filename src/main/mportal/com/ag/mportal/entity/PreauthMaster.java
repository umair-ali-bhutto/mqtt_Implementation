package com.ag.mportal.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the PREAUTH_MASTER database table.
 * 
 */
@Entity
@Table(name="PREAUTH_MASTER")
@NamedQuery(name="PreauthMaster.findAll", query="SELECT p FROM PreauthMaster p ORDER BY p.paid")
@NamedQuery(name="PreauthMaster.FetchAllActiveConfig", query="SELECT d FROM PreauthMaster d where d.active='Y'")
@NamedQuery(name = "PreauthMaster.fetchByID", query = "SELECT d FROM PreauthMaster d where d.paid=:id")
public class PreauthMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PREAUTH_MASTER_ID_SEQ")
	@SequenceGenerator(name = "PREAUTH_MASTER_ID_SEQ", sequenceName = "PREAUTH_MASTER_ID_SEQ", allocationSize = 1)
	private long paid;

	private String active;

	private String corpid;

	
	@Column(name="CR_BY")
	private String crBy;

	
	@Column(name="CR_ON")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Karachi")
	private Timestamp crOn;

	private String currency;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_FROM")
	private Date dateFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_TO")
	private Date dateTo;

	@Column(name="DEFAULT_FLG")
	private String defaultFlg;

	private String name;

	@Column(name="SOURCE_REF")
	private String sourceRef;

	@Column(name="SUB_SEG")
	private String subSeg;

	@Column(name="TXN_EXP")
	private int txnExp;

	@Column(name="MAX_PERC")
	private float maxPerc;
	
	@Column(name="MIN_PERC")
	private float minPerc;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;

	@Column(name="VAL_TID")
	private String valTid;

	@Column(name="VERSION")
	private float version;

	public PreauthMaster() {
	}

	public long getPaid() {
		return this.paid;
	}

	public void setPaid(long paid) {
		this.paid = paid;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getDefaultFlg() {
		return this.defaultFlg;
	}

	public void setDefaultFlg(String defaultFlg) {
		this.defaultFlg = defaultFlg;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceRef() {
		return this.sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getSubSeg() {
		return this.subSeg;
	}

	public void setSubSeg(String subSeg) {
		this.subSeg = subSeg;
	}

	public int getTxnExp() {
		return this.txnExp;
	}

	public void setTxnExp(int txnExp) {
		this.txnExp = txnExp;
	}

	public float getMinPerc() {
		return this.minPerc;
	}

	public void setMinPerc(float minPerc) {
		this.minPerc = minPerc;
	}
	
	public float getMaxPerc() {
		return this.maxPerc;
	}

	public void setMaxPerc(float maxPerc) {
		this.maxPerc = maxPerc;
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

	public String getValTid() {
		return this.valTid;
	}

	public void setValTid(String valTid) {
		this.valTid = valTid;
	}

	public float getVersion() {
		return this.version;
	}

	public void setVersion(float version) {
		this.version = version;
	}

}