package com.ag.mportal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the PREAUTH_TERMINAL database table.
 * 
 */
@Entity
@Table(name="PREAUTH_TERMINAL")
@NamedQuery(name="PreauthTerminal.findAll", query="SELECT p FROM PreauthTerminal p")
public class PreauthTerminal implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PreauthTerminalPK id;

	private String active;

	private String corpid;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	@Column(name="SOURCE_REF")
	private String sourceRef;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;

	public PreauthTerminal() {
	}

	public PreauthTerminalPK getId() {
		return this.id;
	}

	public void setId(PreauthTerminalPK id) {
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