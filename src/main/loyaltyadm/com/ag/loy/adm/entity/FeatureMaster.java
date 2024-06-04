package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "FEATURE_MASTER")
@NamedQueries({ @NamedQuery(name = "FeatureMaster.getAllFeatures", query = "SELECT a FROM FeatureMaster a where a.corpId=:corpId"),
		@NamedQuery(name = "FeatureMaster.getAllFeaturesByFeatureId", query = "SELECT a FROM FeatureMaster a where a.corpId=:corpId and a.featureId=:featureId") })
public class FeatureMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FEATUREID")
	private String featureId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "FEATURE_TYPE")
	private String featureType;

	@Column(name = "MAPPED_TO")
	private String mappedTo;

	@Column(name = "MAPPED_ID")
	private String mappedId;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "CORPID")
	private String corpId;

	@Column(name = "CR_BY")
	private String crBy;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CR_ON")
	private java.util.Date crOn;

	@Column(name = "UPD_BY")
	private String updBy;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "UPD_ON")
	private java.util.Date updOn;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	public String getMappedTo() {
		return mappedTo;
	}

	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}

	public String getMappedId() {
		return mappedId;
	}

	public void setMappedId(String mappedId) {
		this.mappedId = mappedId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCrBy() {
		return crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public java.util.Date getCrOn() {
		return crOn;
	}

	public void setCrOn(java.util.Date crOn) {
		this.crOn = crOn;
	}

	public String getUpdBy() {
		return updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public java.util.Date getUpdOn() {
		return updOn;
	}

	public void setUpdOn(java.util.Date updOn) {
		this.updOn = updOn;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
}