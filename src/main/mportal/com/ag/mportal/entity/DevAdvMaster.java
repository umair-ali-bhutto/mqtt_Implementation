package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "DEV_ADV_MASTER")
@NamedQuery(name = "DevAdvMaster.fetchAll", query = "SELECT d FROM DevAdvMaster d WHERE d.active = 'Y' ORDER BY d.advId")
public class DevAdvMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ADVID")
	private long advId;

	@Column(name = "CORPID")
	private String corpId;

	@Column(name = "VERSION")
	private int version;

	@Column(name = "NAME")
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_FROM")
	private Date dateFrom;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_TO")
	private Date dateTo;

	@Column(name = "ADV_TYPE")
	private String advType;

	@Column(name = "PUSH_FLG")
	private String pushFlag;

	@Column(name = "NO_OF_MEDIA")
	private long noOfMedia;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "DEFAULT_FLG")
	private String defaultFlag;

	@Column(name = "SOURCE_REF")
	private String sourceRef;

	@Column(name = "CR_BY")
	private String crBy;

	@Column(name = "CR_ON")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp crOn;

	@Column(name = "UPD_BY")
	private String updBy;

	@Column(name = "UPD_ON")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp updOn;

	public long getAdvId() {
		return advId;
	}

	public void setAdvId(long advId) {
		this.advId = advId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getAdvType() {
		return advType;
	}

	public void setAdvType(String advType) {
		this.advType = advType;
	}

	public String getPushFlag() {
		return pushFlag;
	}

	public void setPushFlag(String pushFlag) {
		this.pushFlag = pushFlag;
	}

	public long getNoOfMedia() {
		return noOfMedia;
	}

	public void setNoOfMedia(long noOfMedia) {
		this.noOfMedia = noOfMedia;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getSourceRef() {
		return sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getCrBy() {
		return crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public Timestamp getCrOn() {
		return crOn;
	}

	public void setCrOn(Timestamp crOn) {
		this.crOn = crOn;
	}

	public String getUpdBy() {
		return updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public Timestamp getUpdOn() {
		return updOn;
	}

	public void setUpdOn(Timestamp updOn) {
		this.updOn = updOn;
	}

}
