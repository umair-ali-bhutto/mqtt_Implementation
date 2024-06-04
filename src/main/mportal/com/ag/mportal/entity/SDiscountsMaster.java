package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the SDISCOUNTS_MASTER database table.
 * 
 */
@Entity
@Table(name="SDISCOUNTS_MASTER")
@NamedQueries({
	@NamedQuery(name = "SDiscountsMaster.getMaxId", query = "SELECT MAX(discId)+1 FROM SDiscountsMaster d"),
	@NamedQuery(name = "SDiscountsMaster.getMaxIdMssql", query = "SELECT MAX(CONVERT(numeric, discId))+1 FROM SDiscountsMaster d"),
	@NamedQuery(name = "SDiscountsMaster.getRecordByDiscountId", query = "SELECT d FROM SDiscountsMaster d where d.discId = :discId"),
	@NamedQuery(name = "SDiscountsMaster.getDiscountInDateRange", query = "SELECT d FROM SDiscountsMaster d where (:fromDate between dateFrom and dateTo) OR (:toDate between dateFrom and dateTo)"),
	@NamedQuery(name = "SDiscountsMaster.getDiscountInDateRangeNotInID", query = "SELECT d FROM SDiscountsMaster d where d.discId <>:discId AND ((:fromDate between dateFrom and dateTo) OR (:toDate between dateFrom and dateTo)) ")
	})
public class SDiscountsMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DISC_ID")
	private String discId;
	
	@Column(name="ACTIVE")
	private String active;

	@Column(name="ALL_BINS")
	private String allBins;

	@Column(name="ALL_CATS")
	private String allCats;

	@Column(name="ALL_CITIES")
	private String allCities;

	@Column(name="ALL_MERCHANTS")
	private String allMerchants;
	
	@Column(name="ALL_DAY")
	private String allDay;

	@Column(name="CMID_MID")
	private String cmidMid;

	@Column(name="CORPID")
	private String corpid;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	@Column(name="CURRENCY")
	private String currency;

	@Column(name="DATE_FROM")
	private Timestamp dateFrom;

	@Column(name="DATE_TO")
	private Timestamp dateTo;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="GL_ACC1")
	private String glAcc1;

	@Column(name="GL_ACC2")
	private String glAcc2;

	@Column(name="NAME")
	private String name;

	@Column(name="Other0")
	private String other0;

	@Column(name="Other1")
	private String other1;

	@Column(name="Other2")
	private String other2;

	@Column(name="Other3")
	private Double other3;

	@Column(name="Other4")
	private Double other4;

	@Column(name="Other5")
	private Double other5;

	@Column(name="PROC_CODE")
	private String procCode;

	@Column(name="SOURCE_REF")
	private String sourceRef;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;
	
	@Transient
	private ArrayList<SDiscountsDetailBin> lstBin; 
	
	@Transient
	private SDiscountsDetailSlab discountSlab; 

	public SDiscountsMaster() {
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getAllBins() {
		return this.allBins;
	}

	public void setAllBins(String allBins) {
		this.allBins = allBins;
	}

	public String getAllCats() {
		return this.allCats;
	}

	public void setAllCats(String allCats) {
		this.allCats = allCats;
	}

	public String getAllCities() {
		return this.allCities;
	}

	public void setAllCities(String allCities) {
		this.allCities = allCities;
	}

	public String getAllMerchants() {
		return this.allMerchants;
	}
	

	public void setAllMerchants(String allMerchants) {
		this.allMerchants = allMerchants;
	}
	
	public String getAllDay() {
		return this.allDay;
	}
	

	public void setAllDay(String allDay) {
		this.allDay = allDay;
	}

	public String getCmidMid() {
		return this.cmidMid;
	}

	public void setCmidMid(String cmidMid) {
		this.cmidMid = cmidMid;
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

	public Timestamp getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Timestamp dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Timestamp getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Timestamp dateTo) {
		this.dateTo = dateTo;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiscId() {
		return this.discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
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

	public double getOther3() {
		return this.other3;
	}

	public void setOther3(Double other3) {
		this.other3 = other3;
	}

	public Double getOther4() {
		return this.other4;
	}

	public void setOther4(Double other4) {
		this.other4 = other4;
	}

	public Double getOther5() {
		return this.other5;
	}

	public void setOther5(Double other5) {
		this.other5 = other5;
	}

	public String getProcCode() {
		return this.procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
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

	public ArrayList<SDiscountsDetailBin> getLsBin() {
		return lstBin;
	}

	public void setLsBin(ArrayList<SDiscountsDetailBin> lsBin) {
		this.lstBin = lsBin;
	}

	public SDiscountsDetailSlab getDiscountSlab() {
		return discountSlab;
	}

	public void setDiscountSlab(SDiscountsDetailSlab discountSlab) {
		this.discountSlab = discountSlab;
	}

}