package com.ag.fuel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the MAS_FLEET_COMPANY_C database table.
 * 
 */
//@Entity
@Table(name="MAS_FLEET_COMPANY")
@NamedQuery(name="MasFleetCompany.findAll", query="SELECT m FROM MasFleetCompany m")
public class MasFleetCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ADD_1")
	private String add1;

	@Column(name="ADD_2")
	private String add2;

	@Column(name="CITY_CODE")
	private String cityCode;

	@Column(name="CITY_NAME")
	private String cityName;

	@Column(name="CO_CARD_NAME")
	private String coCardName;

	@Column(name="CO_CODE")
	private BigDecimal coCode;

	@Column(name="CO_NTN")
	private String coNtn;

	@Column(name="CO_STRN")
	private String coStrn;

	@Column(name="COMP_DESC")
	private String compDesc;

	@Column(name="COMP_NAME")
	private String compName;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	@Column(name="EMAIL_ID")
	private String emailId;

	private String exported;

	@Column(name="EXPORTED_BY")
	private String exportedBy;

	@Column(name="EXPORTED_ON")
	private Timestamp exportedOn;

	private String nii;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;

	public MasFleetCompany() {
	}

	public String getAdd1() {
		return this.add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return this.add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCoCardName() {
		return this.coCardName;
	}

	public void setCoCardName(String coCardName) {
		this.coCardName = coCardName;
	}

	public BigDecimal getCoCode() {
		return this.coCode;
	}

	public void setCoCode(BigDecimal coCode) {
		this.coCode = coCode;
	}

	public String getCoNtn() {
		return this.coNtn;
	}

	public void setCoNtn(String coNtn) {
		this.coNtn = coNtn;
	}

	public String getCoStrn() {
		return this.coStrn;
	}

	public void setCoStrn(String coStrn) {
		this.coStrn = coStrn;
	}

	public String getCompDesc() {
		return this.compDesc;
	}

	public void setCompDesc(String compDesc) {
		this.compDesc = compDesc;
	}

	public String getCompName() {
		return this.compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
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

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getExported() {
		return this.exported;
	}

	public void setExported(String exported) {
		this.exported = exported;
	}

	public String getExportedBy() {
		return this.exportedBy;
	}

	public void setExportedBy(String exportedBy) {
		this.exportedBy = exportedBy;
	}

	public Timestamp getExportedOn() {
		return this.exportedOn;
	}

	public void setExportedOn(Timestamp exportedOn) {
		this.exportedOn = exportedOn;
	}

	public String getNii() {
		return this.nii;
	}

	public void setNii(String nii) {
		this.nii = nii;
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