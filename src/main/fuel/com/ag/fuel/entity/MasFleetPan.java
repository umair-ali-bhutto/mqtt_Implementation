package com.ag.fuel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the MAS_FLEET_PAN_C database table.
 * 
 */
//@Entity
@Table(name="MAS_FLEET_PAN")
@NamedQuery(name="MasFleetPan.findAll", query="SELECT m FROM MasFleetPan m")
public class MasFleetPan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="AMU_FLAG")
	private String amuFlag;

	@Column(name="APP_DATE")
	private Timestamp appDate;

	@Column(name="BATCH_NO")
	private String batchNo;

	@Column(name="BULK_SALE")
	private String bulkSale;

	@Column(name="C_CARD_NAME")
	private String cCardName;

	@Column(name="C_DATE_OF_BIRTH")
	private Timestamp cDateOfBirth;

	@Column(name="C_DESIGNATION")
	private String cDesignation;

	@Column(name="C_EMPLOYEE_CODE")
	private String cEmployeeCode;

	@Column(name="C_FIRST_NAME")
	private String cFirstName;

	@Column(name="C_GENDER")
	private String cGender;

	@Column(name="C_LAST_NAME")
	private String cLastName;

	@Column(name="C_NIC")
	private String cNic;

	private String city;

	@Column(name="CO_ACCNO")
	private BigDecimal coAccno;

	@Column(name="CO_CODE")
	private BigDecimal coCode;

	@Column(name="COMMERCIAL_FLAG")
	private String commercialFlag;

	@Column(name="COST_CENTER")
	private BigDecimal costCenter;

	@Column(name="COUNTRY_OF_RESID")
	private String countryOfResid;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	@Column(name="DAYS")
	private String days;

	@Column(name="DEPT_NO")
	private BigDecimal deptNo;

	private String email;

	private String exported;

	@Column(name="EXPORTED_BY")
	private String exportedBy;

	@Column(name="EXPORTED_ON")
	private Timestamp exportedOn;

	private String ext;

	@Column(name="F_VEH_CATEGORY")
	private String fVehCategory;

	@Column(name="F_VEH_REG_NO")
	private String fVehRegNo;

	@Column(name="FULE_TANK_DESC")
	private String fuleTankDesc;

	private String imei;

	@Column(name="ISSUER_PRODUCT_CODE")
	private String issuerProductCode;

	@Column(name="LIMIT_IND")
	private String limitInd;

	@Column(name="LOY_BATCH_NO")
	private String loyBatchNo;

	@Column(name="LOY_PAN")
	private String loyPan;

	@Column(name="MARITAL_STATUS")
	private String maritalStatus;

	@Column(name="MAX_DAILY_USEAGE")
	private BigDecimal maxDailyUseage;

	@Column(name="MAX_WEEKLY_USEAGE")
	private BigDecimal maxWeeklyUseage;

	@Column(name="MEMBER_ID")
	private String memberId;

	@Column(name="MIDD_NAME")
	private String middName;

	@Column(name="MNTHLY_LMT_LTR")
	private BigDecimal mnthlyLmtLtr;

	@Column(name="MNTHLY_LMT_RS")
	private BigDecimal mnthlyLmtRs;

	private String mobile;

	@Column(name="MONTH_PER")
	private BigDecimal monthPer;

	@Column(name="MPRODUCT_FLAG")
	private String mproductFlag;

	private String nationality;

	private String nii;

	@Column(name="NONFUEL_MONTH")
	private BigDecimal nonfuelMonth;

	@Column(name="OFF_TEL")
	private String offTel;

	@Column(name="OUT_LETS")
	private String outLets;

	private String outlet;

	@Column(name="PAK_CITIZEN")
	private String pakCitizen;

	private BigDecimal pan;

	@Column(name="PAN_TYPE")
	private String panType;

	@Column(name="PASSPORT_NO")
	private String passportNo;

	@Column(name="PIN_REQ")
	private String pinReq;

	@Column(name="PPLC_CREATED")
	private String pplcCreated;

	@Column(name="PRI_CARD_NO")
	private BigDecimal priCardNo;

	@Column(name="PRI_CARD_NO_EFT")
	private BigDecimal priCardNoEft;

	@Column(name="PRIMARY_PAN")
	private BigDecimal primaryPan;

	@Column(name="PROD_CODE")
	private String prodCode;

	@Column(name="PROD_CODE1")
	private String prodCode1;

	@Column(name="PROD_CODE10")
	private String prodCode10;

	@Column(name="PROD_CODE11")
	private String prodCode11;

	@Column(name="PROD_CODE12")
	private String prodCode12;

	@Column(name="PROD_CODE2")
	private String prodCode2;

	@Column(name="PROD_CODE3")
	private String prodCode3;

	@Column(name="PROD_CODE4")
	private String prodCode4;

	@Column(name="PROD_CODE5")
	private String prodCode5;

	@Column(name="PROD_CODE6")
	private String prodCode6;

	@Column(name="PROD_CODE7")
	private String prodCode7;

	@Column(name="PROD_CODE8")
	private String prodCode8;

	@Column(name="PROD_CODE9")
	private String prodCode9;

	private BigDecimal prodfleet;

	@Column(name="QTY_AMT_DAY")
	private BigDecimal qtyAmtDay;

	@Column(name="QTY_AMT_MONTH")
	private BigDecimal qtyAmtMonth;

	@Column(name="QTY_AMT_TXN")
	private BigDecimal qtyAmtTxn;

	@Column(name="QTY_AMT_WEEK")
	private BigDecimal qtyAmtWeek;

	@Column(name="QTY_AMT_YEAR")
	private BigDecimal qtyAmtYear;

	@Column(name="REGION_CODE")
	private BigDecimal regionCode;

	@Column(name="RES_ADDRESS")
	private String resAddress;

	@Column(name="RES_TEL")
	private String resTel;

	private String restssid;

	@Column(name="SMART_CARD_FLG")
	private String smartCardFlg;

	@Column(name="STATUS_CODE")
	private String statusCode;

	@Column(name="TEST_FLG")
	private String testFlg;

	@Column(name="TTS_EXP")
	private String ttsExp;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;

	@Column(name="V_MODEL")
	private BigDecimal vModel;

	@Column(name="VEH_DESC")
	private String vehDesc;

	@Column(name="VEHICLE_NF")
	private String vehicleNf;

	public MasFleetPan() {
	}

	public String getAmuFlag() {
		return this.amuFlag;
	}

	public void setAmuFlag(String amuFlag) {
		this.amuFlag = amuFlag;
	}

	public Timestamp getAppDate() {
		return this.appDate;
	}

	public void setAppDate(Timestamp appDate) {
		this.appDate = appDate;
	}

	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getBulkSale() {
		return this.bulkSale;
	}

	public void setBulkSale(String bulkSale) {
		this.bulkSale = bulkSale;
	}

	public String getCCardName() {
		return this.cCardName;
	}

	public void setCCardName(String cCardName) {
		this.cCardName = cCardName;
	}

	public Timestamp getCDateOfBirth() {
		return this.cDateOfBirth;
	}

	public void setCDateOfBirth(Timestamp cDateOfBirth) {
		this.cDateOfBirth = cDateOfBirth;
	}

	public String getCDesignation() {
		return this.cDesignation;
	}

	public void setCDesignation(String cDesignation) {
		this.cDesignation = cDesignation;
	}

	public String getCEmployeeCode() {
		return this.cEmployeeCode;
	}

	public void setCEmployeeCode(String cEmployeeCode) {
		this.cEmployeeCode = cEmployeeCode;
	}

	public String getCFirstName() {
		return this.cFirstName;
	}

	public void setCFirstName(String cFirstName) {
		this.cFirstName = cFirstName;
	}

	public String getCGender() {
		return this.cGender;
	}

	public void setCGender(String cGender) {
		this.cGender = cGender;
	}

	public String getCLastName() {
		return this.cLastName;
	}

	public void setCLastName(String cLastName) {
		this.cLastName = cLastName;
	}

	public String getCNic() {
		return this.cNic;
	}

	public void setCNic(String cNic) {
		this.cNic = cNic;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigDecimal getCoAccno() {
		return this.coAccno;
	}

	public void setCoAccno(BigDecimal coAccno) {
		this.coAccno = coAccno;
	}

	public BigDecimal getCoCode() {
		return this.coCode;
	}

	public void setCoCode(BigDecimal coCode) {
		this.coCode = coCode;
	}

	public String getCommercialFlag() {
		return this.commercialFlag;
	}

	public void setCommercialFlag(String commercialFlag) {
		this.commercialFlag = commercialFlag;
	}

	public BigDecimal getCostCenter() {
		return this.costCenter;
	}

	public void setCostCenter(BigDecimal costCenter) {
		this.costCenter = costCenter;
	}

	public String getCountryOfResid() {
		return this.countryOfResid;
	}

	public void setCountryOfResid(String countryOfResid) {
		this.countryOfResid = countryOfResid;
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

	public String getDays() {
		return this.days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public BigDecimal getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(BigDecimal deptNo) {
		this.deptNo = deptNo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getExt() {
		return this.ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getFVehCategory() {
		return this.fVehCategory;
	}

	public void setFVehCategory(String fVehCategory) {
		this.fVehCategory = fVehCategory;
	}

	public String getFVehRegNo() {
		return this.fVehRegNo;
	}

	public void setFVehRegNo(String fVehRegNo) {
		this.fVehRegNo = fVehRegNo;
	}

	public String getFuleTankDesc() {
		return this.fuleTankDesc;
	}

	public void setFuleTankDesc(String fuleTankDesc) {
		this.fuleTankDesc = fuleTankDesc;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getIssuerProductCode() {
		return this.issuerProductCode;
	}

	public void setIssuerProductCode(String issuerProductCode) {
		this.issuerProductCode = issuerProductCode;
	}

	public String getLimitInd() {
		return this.limitInd;
	}

	public void setLimitInd(String limitInd) {
		this.limitInd = limitInd;
	}

	public String getLoyBatchNo() {
		return this.loyBatchNo;
	}

	public void setLoyBatchNo(String loyBatchNo) {
		this.loyBatchNo = loyBatchNo;
	}

	public String getLoyPan() {
		return this.loyPan;
	}

	public void setLoyPan(String loyPan) {
		this.loyPan = loyPan;
	}

	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public BigDecimal getMaxDailyUseage() {
		return this.maxDailyUseage;
	}

	public void setMaxDailyUseage(BigDecimal maxDailyUseage) {
		this.maxDailyUseage = maxDailyUseage;
	}

	public BigDecimal getMaxWeeklyUseage() {
		return this.maxWeeklyUseage;
	}

	public void setMaxWeeklyUseage(BigDecimal maxWeeklyUseage) {
		this.maxWeeklyUseage = maxWeeklyUseage;
	}

	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMiddName() {
		return this.middName;
	}

	public void setMiddName(String middName) {
		this.middName = middName;
	}

	public BigDecimal getMnthlyLmtLtr() {
		return this.mnthlyLmtLtr;
	}

	public void setMnthlyLmtLtr(BigDecimal mnthlyLmtLtr) {
		this.mnthlyLmtLtr = mnthlyLmtLtr;
	}

	public BigDecimal getMnthlyLmtRs() {
		return this.mnthlyLmtRs;
	}

	public void setMnthlyLmtRs(BigDecimal mnthlyLmtRs) {
		this.mnthlyLmtRs = mnthlyLmtRs;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getMonthPer() {
		return this.monthPer;
	}

	public void setMonthPer(BigDecimal monthPer) {
		this.monthPer = monthPer;
	}

	public String getMproductFlag() {
		return this.mproductFlag;
	}

	public void setMproductFlag(String mproductFlag) {
		this.mproductFlag = mproductFlag;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNii() {
		return this.nii;
	}

	public void setNii(String nii) {
		this.nii = nii;
	}

	public BigDecimal getNonfuelMonth() {
		return this.nonfuelMonth;
	}

	public void setNonfuelMonth(BigDecimal nonfuelMonth) {
		this.nonfuelMonth = nonfuelMonth;
	}

	public String getOffTel() {
		return this.offTel;
	}

	public void setOffTel(String offTel) {
		this.offTel = offTel;
	}

	public String getOutLets() {
		return this.outLets;
	}

	public void setOutLets(String outLets) {
		this.outLets = outLets;
	}

	public String getOutlet() {
		return this.outlet;
	}

	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}

	public String getPakCitizen() {
		return this.pakCitizen;
	}

	public void setPakCitizen(String pakCitizen) {
		this.pakCitizen = pakCitizen;
	}

	public BigDecimal getPan() {
		return this.pan;
	}

	public void setPan(BigDecimal pan) {
		this.pan = pan;
	}

	public String getPanType() {
		return this.panType;
	}

	public void setPanType(String panType) {
		this.panType = panType;
	}

	public String getPassportNo() {
		return this.passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getPinReq() {
		return this.pinReq;
	}

	public void setPinReq(String pinReq) {
		this.pinReq = pinReq;
	}

	public String getPplcCreated() {
		return this.pplcCreated;
	}

	public void setPplcCreated(String pplcCreated) {
		this.pplcCreated = pplcCreated;
	}

	public BigDecimal getPriCardNo() {
		return this.priCardNo;
	}

	public void setPriCardNo(BigDecimal priCardNo) {
		this.priCardNo = priCardNo;
	}

	public BigDecimal getPriCardNoEft() {
		return this.priCardNoEft;
	}

	public void setPriCardNoEft(BigDecimal priCardNoEft) {
		this.priCardNoEft = priCardNoEft;
	}

	public BigDecimal getPrimaryPan() {
		return this.primaryPan;
	}

	public void setPrimaryPan(BigDecimal primaryPan) {
		this.primaryPan = primaryPan;
	}

	public String getProdCode() {
		return this.prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdCode1() {
		return this.prodCode1;
	}

	public void setProdCode1(String prodCode1) {
		this.prodCode1 = prodCode1;
	}

	public String getProdCode10() {
		return this.prodCode10;
	}

	public void setProdCode10(String prodCode10) {
		this.prodCode10 = prodCode10;
	}

	public String getProdCode11() {
		return this.prodCode11;
	}

	public void setProdCode11(String prodCode11) {
		this.prodCode11 = prodCode11;
	}

	public String getProdCode12() {
		return this.prodCode12;
	}

	public void setProdCode12(String prodCode12) {
		this.prodCode12 = prodCode12;
	}

	public String getProdCode2() {
		return this.prodCode2;
	}

	public void setProdCode2(String prodCode2) {
		this.prodCode2 = prodCode2;
	}

	public String getProdCode3() {
		return this.prodCode3;
	}

	public void setProdCode3(String prodCode3) {
		this.prodCode3 = prodCode3;
	}

	public String getProdCode4() {
		return this.prodCode4;
	}

	public void setProdCode4(String prodCode4) {
		this.prodCode4 = prodCode4;
	}

	public String getProdCode5() {
		return this.prodCode5;
	}

	public void setProdCode5(String prodCode5) {
		this.prodCode5 = prodCode5;
	}

	public String getProdCode6() {
		return this.prodCode6;
	}

	public void setProdCode6(String prodCode6) {
		this.prodCode6 = prodCode6;
	}

	public String getProdCode7() {
		return this.prodCode7;
	}

	public void setProdCode7(String prodCode7) {
		this.prodCode7 = prodCode7;
	}

	public String getProdCode8() {
		return this.prodCode8;
	}

	public void setProdCode8(String prodCode8) {
		this.prodCode8 = prodCode8;
	}

	public String getProdCode9() {
		return this.prodCode9;
	}

	public void setProdCode9(String prodCode9) {
		this.prodCode9 = prodCode9;
	}

	public BigDecimal getProdfleet() {
		return this.prodfleet;
	}

	public void setProdfleet(BigDecimal prodfleet) {
		this.prodfleet = prodfleet;
	}

	public BigDecimal getQtyAmtDay() {
		return this.qtyAmtDay;
	}

	public void setQtyAmtDay(BigDecimal qtyAmtDay) {
		this.qtyAmtDay = qtyAmtDay;
	}

	public BigDecimal getQtyAmtMonth() {
		return this.qtyAmtMonth;
	}

	public void setQtyAmtMonth(BigDecimal qtyAmtMonth) {
		this.qtyAmtMonth = qtyAmtMonth;
	}

	public BigDecimal getQtyAmtTxn() {
		return this.qtyAmtTxn;
	}

	public void setQtyAmtTxn(BigDecimal qtyAmtTxn) {
		this.qtyAmtTxn = qtyAmtTxn;
	}

	public BigDecimal getQtyAmtWeek() {
		return this.qtyAmtWeek;
	}

	public void setQtyAmtWeek(BigDecimal qtyAmtWeek) {
		this.qtyAmtWeek = qtyAmtWeek;
	}

	public BigDecimal getQtyAmtYear() {
		return this.qtyAmtYear;
	}

	public void setQtyAmtYear(BigDecimal qtyAmtYear) {
		this.qtyAmtYear = qtyAmtYear;
	}

	public BigDecimal getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(BigDecimal regionCode) {
		this.regionCode = regionCode;
	}

	public String getResAddress() {
		return this.resAddress;
	}

	public void setResAddress(String resAddress) {
		this.resAddress = resAddress;
	}

	public String getResTel() {
		return this.resTel;
	}

	public void setResTel(String resTel) {
		this.resTel = resTel;
	}

	public String getRestssid() {
		return this.restssid;
	}

	public void setRestssid(String restssid) {
		this.restssid = restssid;
	}

	public String getSmartCardFlg() {
		return this.smartCardFlg;
	}

	public void setSmartCardFlg(String smartCardFlg) {
		this.smartCardFlg = smartCardFlg;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getTestFlg() {
		return this.testFlg;
	}

	public void setTestFlg(String testFlg) {
		this.testFlg = testFlg;
	}

	public String getTtsExp() {
		return this.ttsExp;
	}

	public void setTtsExp(String ttsExp) {
		this.ttsExp = ttsExp;
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

	public BigDecimal getVModel() {
		return this.vModel;
	}

	public void setVModel(BigDecimal vModel) {
		this.vModel = vModel;
	}

	public String getVehDesc() {
		return this.vehDesc;
	}

	public void setVehDesc(String vehDesc) {
		this.vehDesc = vehDesc;
	}

	public String getVehicleNf() {
		return this.vehicleNf;
	}

	public void setVehicleNf(String vehicleNf) {
		this.vehicleNf = vehicleNf;
	}

}