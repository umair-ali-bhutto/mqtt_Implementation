package com.ag.mportal.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;


/**
 * The persistent class for the PREAUTH_MERCHANT database table.
 * 
 */
@Entity
@Table(name="PREAUTH_MERCHANT")
@NamedQuery(name="PreauthMerchant.findAll", query="SELECT p FROM PreauthMerchant p")
@NamedQuery(name = "PreauthMerchant.fetchByID", query = "SELECT d FROM PreauthMerchant d where d.id.paid=:id")
@NamedQuery(name = "PreauthMerchant.fetchByIDActive", query = "SELECT d FROM PreauthMerchant d where d.active='Y' and d.id.paid=:id")

public class PreauthMerchant implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PreauthMerchantPK id;

	private String active;

	private String corpid;

	@Column(name="CR_BY")
	private String crBy;

	
	@Column(name="CR_ON")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Karachi")
	private Timestamp crOn;

	@Column(name="SOURCE_REF")
	private String sourceRef;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;

	public PreauthMerchant() {
	}

	public PreauthMerchantPK getId() {
		return this.id;
	}

	public void setId(PreauthMerchantPK id) {
		this.id = id;
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

	public String getSourceRef() {
		return this.sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
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