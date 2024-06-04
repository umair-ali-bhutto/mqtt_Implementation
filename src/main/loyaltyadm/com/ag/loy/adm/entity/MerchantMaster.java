package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the MERCHANT_MASTER database table.
 * 
 */
@Entity
@Table(name="MERCHANT_MASTER")
@NamedQuery(name="MerchantMaster.findAll", query="SELECT m FROM MerchantMaster m WHERE m.id.mid=:mid and m.id.corpid=:corpid")
@NamedQuery(name="MerchantMaster.findAllByCorpId", query="SELECT m FROM MerchantMaster m WHERE m.id.corpid=:corpid")
public class MerchantMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MerchantMasterPK id;

	private String active;

	private String address1;

	private String address2;

	private String area;

	@Column(name="CAT_CODE")
	private String catCode;

	@Column(name="CITY_ID")
	private String cityId;

	private String cmid;

	@Column(name="CR_BY")
	private String crBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CR_ON")
	private Date crOn;

	private String description;

	@Column(name="GL_ACC1")
	private String glAcc1;

	@Column(name="GL_ACC2")
	private String glAcc2;

	@Column(name="GL_ACC3")
	private String glAcc3;

	@Column(name="GL_ACC4")
	private String glAcc4;

	@Column(name="GL_ACC5")
	private String glAcc5;

	@Column(name="GL_ACC6")
	private String glAcc6;

	@Column(name="LOC_ACCU")
	private BigDecimal locAccu;

	@Column(name="LOC_LAT")
	private BigDecimal locLat;

	@Column(name="LOC_LNG")
	private BigDecimal locLng;

	@Column(name="MAPPED_TO")
	private String mappedTo;

	private String name;

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

	@Column(name="REC_REF")
	private BigDecimal recRef;

	private String region;

	@Column(name="SOURCE_REF")
	private String sourceRef;

	@Column(name="UPD_BY")
	private String updBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_ON")
	private Date updOn;

	public MerchantMaster() {
	}

	public MerchantMasterPK getId() {
		return this.id;
	}

	public void setId(MerchantMasterPK id) {
		this.id = id;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCatCode() {
		return this.catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	public String getCityId() {
		return this.cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCmid() {
		return this.cmid;
	}

	public void setCmid(String cmid) {
		this.cmid = cmid;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGlAcc1() {
		return this.glAcc1;
	}

	public void setGlAcc1(String glAcc1) {
		this.glAcc1 = glAcc1;
	}

	public String getGlAcc2() {
		return this.glAcc2;
	}

	public void setGlAcc2(String glAcc2) {
		this.glAcc2 = glAcc2;
	}

	public String getGlAcc3() {
		return this.glAcc3;
	}

	public void setGlAcc3(String glAcc3) {
		this.glAcc3 = glAcc3;
	}

	public String getGlAcc4() {
		return this.glAcc4;
	}

	public void setGlAcc4(String glAcc4) {
		this.glAcc4 = glAcc4;
	}

	public String getGlAcc5() {
		return this.glAcc5;
	}

	public void setGlAcc5(String glAcc5) {
		this.glAcc5 = glAcc5;
	}

	public String getGlAcc6() {
		return this.glAcc6;
	}

	public void setGlAcc6(String glAcc6) {
		this.glAcc6 = glAcc6;
	}

	public BigDecimal getLocAccu() {
		return this.locAccu;
	}

	public void setLocAccu(BigDecimal locAccu) {
		this.locAccu = locAccu;
	}

	public BigDecimal getLocLat() {
		return this.locLat;
	}

	public void setLocLat(BigDecimal locLat) {
		this.locLat = locLat;
	}

	public BigDecimal getLocLng() {
		return this.locLng;
	}

	public void setLocLng(BigDecimal locLng) {
		this.locLng = locLng;
	}

	public String getMappedTo() {
		return this.mappedTo;
	}

	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public BigDecimal getRecRef() {
		return this.recRef;
	}

	public void setRecRef(BigDecimal recRef) {
		this.recRef = recRef;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public Date getUpdOn() {
		return this.updOn;
	}

	public void setUpdOn(Date updOn) {
		this.updOn = updOn;
	}
}