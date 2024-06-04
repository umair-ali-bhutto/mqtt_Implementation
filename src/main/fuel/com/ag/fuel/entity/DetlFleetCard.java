package com.ag.fuel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the DETL_FLEET_CARD_CPY database table.
 * 
 */
//@Entity
@Table(name="DETL_FLEET_CARD")
@NamedQueries({
	@NamedQuery(name="DetlFleetCard.findAll", query="SELECT d FROM DetlFleetCard d"),
	@NamedQuery(name="DetlFleetCard.findById", query="SELECT d FROM DetlFleetCard d where d.lId = :id")
})
public class DetlFleetCard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACTIVATION_DATE")
	private Timestamp activationDate;

	@Column(name="ADD_DATE")
	private String addDate;

	@Column(name="AMU_FLAG")
	private String amuFlag;

	@Column(name="BATCH_NO")
	private String batchNo;

	@Column(name="BILL_ID")
	private String billId;

	@Column(name="C_DATE_OF_BIRTH")
	private Timestamp cDateOfBirth;

	@Column(name="C_FIRST_NAME")
	private String cFirstName;

	@Column(name="C_GENDER")
	private String cGender;

	@Column(name="C_LAST_NAME")
	private String cLastName;

	@Column(name="C_NIC")
	private String cNic;

	@Column(name="C_RELATION_WITH_PRI_CARDHOLDER")
	private String cRelationWithPriCardholder;

	@Column(name="CARD_NO")
	private Long cardNo;

	@Column(name="CARD_STATUS")
	private String cardStatus;

	@Column(name="CARD_TYPE")
	private String cardType;

	private String card1;

	private String card2;

	private String card3;

	private String card4;

	private String card5;

	private String city;

	@Column(name="CO_ACCNO")
	private Long coAccno;

	@Column(name="CO_CODE")
	private Long coCode;

	@Column(name="COMMERCIAL_FLAG")
	private String commercialFlag;

	@Column(name="COUREIR_DATE")
	private Timestamp coureirDate;

	private String couriered;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	private String cvv;

	@Column(name="DAYS")
	private String days;

	@Column(name="DELIVERED_DATE")
	private Timestamp deliveredDate;

	private String devlivered;

	private String email;

	private String emailf;

	@Column(name="EMAILF_UPD_BY")
	private String emailfUpdBy;

	@Column(name="EMAILF_UPD_ON")
	private Timestamp emailfUpdOn;

	private String emailid;

	@Column(name="EXP_DATE")
	private Timestamp expDate;

	private String exported;

	@Column(name="EXPORTED_BY")
	private String exportedBy;

	@Column(name="EXPORTED_ON")
	private Timestamp exportedOn;

	@Column(name="EXPORTED_TTS")
	private String exportedTts;

	@Column(name="EXPORTED_TTS_BY")
	private String exportedTtsBy;

	@Column(name="EXPORTED_TTS_ON")
	private Timestamp exportedTtsOn;

	private String ext;

	@Column(name="F_VEH_CATEGORY")
	private String fVehCategory;

	@Column(name="F_VEH_REG_NO")
	private String fVehRegNo;

	@Column(name="FCEAR_FLAG")
	private String fcearFlag;

	private BigDecimal fcearm;

	@Column(name="FIRST_BILLING")
	private Timestamp firstBilling;

	private String frequency;

	@Column(name="FROM_DATE")
	private Timestamp fromDate;

	@Column(name="FT_FLAG")
	private String ftFlag;

	@Column(name="FULE_TANK_DESC")
	private String fuleTankDesc;

	@Column(name="ISSUE_DATE")
	private Timestamp issueDate;

	@Column(name="ISSUE_TYPE")
	private String issueType;

	@Column(name="L_COUNT")
	private String lCount;

	@Column(name="L_DATE")
	private String lDate;

	@Column(name="L_ID")
	private String lId;

	@Column(name="L_TIME")
	private String lTime;

	@Column(name="LOY_PAN")
	private String loyPan;

	@Column(name="MARITAL_STATUS")
	private String maritalStatus;

	@Column(name="MAX_DAILY_USEAGE")
	private Long maxDailyUseage;

	@Column(name="MAX_WEEKLY_USEAGE")
	private Long maxWeeklyUseage;

	@Column(name="MEMBER_ID")
	private String memberId;

	@Column(name="MIDD_NAME")
	private String middName;

	@Column(name="MNO_CODE")
	private String mnoCode;

	private String mobile;

	@Column(name="MONTH_PER")
	private BigDecimal monthPer;

	@Column(name="MPRODUCT_FLAG")
	private String mproductFlag;

	private String msisdn;

	@Column(name="NAME_ON_CARD")
	private String nameOnCard;

	@Column(name="NONFUEL_MONTH")
	private BigDecimal nonfuelMonth;

	@Column(name="OFF_TEL")
	private String offTel;

	@Column(name="OLD_EXP_DATE")
	private Timestamp oldExpDate;

	private String outlet;

	private Long pan;

	@Column(name="PASSPORT_NO")
	private String passportNo;

	@Column(name="PRI_CARD_FLAG")
	private String priCardFlag;

	@Column(name="PRI_LINK")
	private String priLink;

	@Column(name="PRIMARY_PAN")
	private Long primaryPan;

	@Column(name="PRINT_STATUS")
	private String printStatus;

	@Column(name="PRINTED_ON")
	private Timestamp printedOn;

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

	@Column(name="PROD_CODE13")
	private String prodCode13;

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

	private Long prodfleet;

	@Column(name="PULL_SMSF")
	private String pullSmsf;

	@Column(name="PULL_SMSF_UPD_BY")
	private String pullSmsfUpdBy;

	@Column(name="PULL_SMSF_UPD_ON")
	private Timestamp pullSmsfUpdOn;

	@Column(name="PUSH_SMSF")
	private String pushSmsf;

	@Column(name="PUSH_SMSF_UPD_BY")
	private String pushSmsfUpdBy;

	@Column(name="PUSH_SMSF_UPD_ON")
	private Timestamp pushSmsfUpdOn;

	private String pvv;

	@Column(name="QTY_AMT_DAY")
	private Double qtyAmtDay;

	@Column(name="QTY_AMT_MONTH")
	private Double qtyAmtMonth;

	@Column(name="QTY_AMT_TXN")
	private Double qtyAmtTxn;

	@Column(name="QTY_AMT_WEEK")
	private Double qtyAmtWeek;

	@Column(name="QTY_AMT_YEAR")
	private Double qtyAmtYear;

	@Column(name="RE_PRINT")
	private String rePrint;

	@Column(name="REG_NO")
	private String regNo;

	private String renewal;

	@Column(name="RES_ADDRESS")
	private String resAddress;

	@Column(name="RES_TEL")
	private String resTel;

	private String reser;

	@Column(name="SERV_CODE")
	private String servCode;

	@Column(name="SMART_CARD_FLG")
	private String smartCardFlg;

	@Column(name="STATUS_CODE")
	private String statusCode;

	@Column(name="SUPP_DESC")
	private String suppDesc;

	@Column(name="SUPPL_CARD")
	private String supplCard;

	@Column(name="SUPPLI_LIMIT_PERCENTAGE")
	private BigDecimal suppliLimitPercentage;

	@Column(name="SUPPLI_PAN")
	private Long suppliPan;

	private String supplimentary;

	@Column(name="TXNREPORT_EMAILF")
	private String txnreportEmailf;

	@Column(name="TXNREPORT_EMAILF_UPD_BY")
	private String txnreportEmailfUpdBy;

	@Column(name="TXNREPORT_EMAILF_UPD_ON")
	private Timestamp txnreportEmailfUpdOn;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;

	@Column(name="VEH_DESC")
	private String vehDesc;

	@Column(name="VISION_RANDOM")
	private String visionRandom;

	public DetlFleetCard() {
	}

	public Timestamp getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Timestamp activationDate) {
		this.activationDate = activationDate;
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getAmuFlag() {
		return amuFlag;
	}

	public void setAmuFlag(String amuFlag) {
		this.amuFlag = amuFlag;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public Timestamp getcDateOfBirth() {
		return cDateOfBirth;
	}

	public void setcDateOfBirth(Timestamp cDateOfBirth) {
		this.cDateOfBirth = cDateOfBirth;
	}

	public String getcFirstName() {
		return cFirstName;
	}

	public void setcFirstName(String cFirstName) {
		this.cFirstName = cFirstName;
	}

	public String getcGender() {
		return cGender;
	}

	public void setcGender(String cGender) {
		this.cGender = cGender;
	}

	public String getcLastName() {
		return cLastName;
	}

	public void setcLastName(String cLastName) {
		this.cLastName = cLastName;
	}

	public String getcNic() {
		return cNic;
	}

	public void setcNic(String cNic) {
		this.cNic = cNic;
	}

	public String getcRelationWithPriCardholder() {
		return cRelationWithPriCardholder;
	}

	public void setcRelationWithPriCardholder(String cRelationWithPriCardholder) {
		this.cRelationWithPriCardholder = cRelationWithPriCardholder;
	}

	public Long getCardNo() {
		return cardNo;
	}

	public void setCardNo(Long cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCard1() {
		return card1;
	}

	public void setCard1(String card1) {
		this.card1 = card1;
	}

	public String getCard2() {
		return card2;
	}

	public void setCard2(String card2) {
		this.card2 = card2;
	}

	public String getCard3() {
		return card3;
	}

	public void setCard3(String card3) {
		this.card3 = card3;
	}

	public String getCard4() {
		return card4;
	}

	public void setCard4(String card4) {
		this.card4 = card4;
	}

	public String getCard5() {
		return card5;
	}

	public void setCard5(String card5) {
		this.card5 = card5;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getCoAccno() {
		return coAccno;
	}

	public void setCoAccno(Long coAccno) {
		this.coAccno = coAccno;
	}

	public Long getCoCode() {
		return coCode;
	}

	public void setCoCode(Long coCode) {
		this.coCode = coCode;
	}

	public String getCommercialFlag() {
		return commercialFlag;
	}

	public void setCommercialFlag(String commercialFlag) {
		this.commercialFlag = commercialFlag;
	}

	public Timestamp getCoureirDate() {
		return coureirDate;
	}

	public void setCoureirDate(Timestamp coureirDate) {
		this.coureirDate = coureirDate;
	}

	public String getCouriered() {
		return couriered;
	}

	public void setCouriered(String couriered) {
		this.couriered = couriered;
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

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public Timestamp getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(Timestamp deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public String getDevlivered() {
		return devlivered;
	}

	public void setDevlivered(String devlivered) {
		this.devlivered = devlivered;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailf() {
		return emailf;
	}

	public void setEmailf(String emailf) {
		this.emailf = emailf;
	}

	public String getEmailfUpdBy() {
		return emailfUpdBy;
	}

	public void setEmailfUpdBy(String emailfUpdBy) {
		this.emailfUpdBy = emailfUpdBy;
	}

	public Timestamp getEmailfUpdOn() {
		return emailfUpdOn;
	}

	public void setEmailfUpdOn(Timestamp emailfUpdOn) {
		this.emailfUpdOn = emailfUpdOn;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public Timestamp getExpDate() {
		return expDate;
	}

	public void setExpDate(Timestamp expDate) {
		this.expDate = expDate;
	}

	public String getExported() {
		return exported;
	}

	public void setExported(String exported) {
		this.exported = exported;
	}

	public String getExportedBy() {
		return exportedBy;
	}

	public void setExportedBy(String exportedBy) {
		this.exportedBy = exportedBy;
	}

	public Timestamp getExportedOn() {
		return exportedOn;
	}

	public void setExportedOn(Timestamp exportedOn) {
		this.exportedOn = exportedOn;
	}

	public String getExportedTts() {
		return exportedTts;
	}

	public void setExportedTts(String exportedTts) {
		this.exportedTts = exportedTts;
	}

	public String getExportedTtsBy() {
		return exportedTtsBy;
	}

	public void setExportedTtsBy(String exportedTtsBy) {
		this.exportedTtsBy = exportedTtsBy;
	}

	public Timestamp getExportedTtsOn() {
		return exportedTtsOn;
	}

	public void setExportedTtsOn(Timestamp exportedTtsOn) {
		this.exportedTtsOn = exportedTtsOn;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getfVehCategory() {
		return fVehCategory;
	}

	public void setfVehCategory(String fVehCategory) {
		this.fVehCategory = fVehCategory;
	}

	public String getfVehRegNo() {
		return fVehRegNo;
	}

	public void setfVehRegNo(String fVehRegNo) {
		this.fVehRegNo = fVehRegNo;
	}

	public String getFcearFlag() {
		return fcearFlag;
	}

	public void setFcearFlag(String fcearFlag) {
		this.fcearFlag = fcearFlag;
	}

	public BigDecimal getFcearm() {
		return fcearm;
	}

	public void setFcearm(BigDecimal fcearm) {
		this.fcearm = fcearm;
	}

	public Timestamp getFirstBilling() {
		return firstBilling;
	}

	public void setFirstBilling(Timestamp firstBilling) {
		this.firstBilling = firstBilling;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public String getFtFlag() {
		return ftFlag;
	}

	public void setFtFlag(String ftFlag) {
		this.ftFlag = ftFlag;
	}

	public String getFuleTankDesc() {
		return fuleTankDesc;
	}

	public void setFuleTankDesc(String fuleTankDesc) {
		this.fuleTankDesc = fuleTankDesc;
	}

	public Timestamp getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Timestamp issueDate) {
		this.issueDate = issueDate;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getlCount() {
		return lCount;
	}

	public void setlCount(String lCount) {
		this.lCount = lCount;
	}

	public String getlDate() {
		return lDate;
	}

	public void setlDate(String lDate) {
		this.lDate = lDate;
	}

	public String getlId() {
		return lId;
	}

	public void setlId(String lId) {
		this.lId = lId;
	}

	public String getlTime() {
		return lTime;
	}

	public void setlTime(String lTime) {
		this.lTime = lTime;
	}

	public String getLoyPan() {
		return loyPan;
	}

	public void setLoyPan(String loyPan) {
		this.loyPan = loyPan;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Long getMaxDailyUseage() {
		return maxDailyUseage;
	}

	public void setMaxDailyUseage(Long maxDailyUseage) {
		this.maxDailyUseage = maxDailyUseage;
	}

	public Long getMaxWeeklyUseage() {
		return maxWeeklyUseage;
	}

	public void setMaxWeeklyUseage(Long maxWeeklyUseage) {
		this.maxWeeklyUseage = maxWeeklyUseage;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMiddName() {
		return middName;
	}

	public void setMiddName(String middName) {
		this.middName = middName;
	}

	public String getMnoCode() {
		return mnoCode;
	}

	public void setMnoCode(String mnoCode) {
		this.mnoCode = mnoCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getMonthPer() {
		return monthPer;
	}

	public void setMonthPer(BigDecimal monthPer) {
		this.monthPer = monthPer;
	}

	public String getMproductFlag() {
		return mproductFlag;
	}

	public void setMproductFlag(String mproductFlag) {
		this.mproductFlag = mproductFlag;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public BigDecimal getNonfuelMonth() {
		return nonfuelMonth;
	}

	public void setNonfuelMonth(BigDecimal nonfuelMonth) {
		this.nonfuelMonth = nonfuelMonth;
	}

	public String getOffTel() {
		return offTel;
	}

	public void setOffTel(String offTel) {
		this.offTel = offTel;
	}

	public Timestamp getOldExpDate() {
		return oldExpDate;
	}

	public void setOldExpDate(Timestamp oldExpDate) {
		this.oldExpDate = oldExpDate;
	}

	public String getOutlet() {
		return outlet;
	}

	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}

	public Long getPan() {
		return pan;
	}

	public void setPan(Long pan) {
		this.pan = pan;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getPriCardFlag() {
		return priCardFlag;
	}

	public void setPriCardFlag(String priCardFlag) {
		this.priCardFlag = priCardFlag;
	}

	public String getPriLink() {
		return priLink;
	}

	public void setPriLink(String priLink) {
		this.priLink = priLink;
	}

	public Long getPrimaryPan() {
		return primaryPan;
	}

	public void setPrimaryPan(Long primaryPan) {
		this.primaryPan = primaryPan;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public Timestamp getPrintedOn() {
		return printedOn;
	}

	public void setPrintedOn(Timestamp printedOn) {
		this.printedOn = printedOn;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdCode1() {
		return prodCode1;
	}

	public void setProdCode1(String prodCode1) {
		this.prodCode1 = prodCode1;
	}

	public String getProdCode10() {
		return prodCode10;
	}

	public void setProdCode10(String prodCode10) {
		this.prodCode10 = prodCode10;
	}

	public String getProdCode11() {
		return prodCode11;
	}

	public void setProdCode11(String prodCode11) {
		this.prodCode11 = prodCode11;
	}

	public String getProdCode12() {
		return prodCode12;
	}

	public void setProdCode12(String prodCode12) {
		this.prodCode12 = prodCode12;
	}

	public String getProdCode13() {
		return prodCode13;
	}

	public void setProdCode13(String prodCode13) {
		this.prodCode13 = prodCode13;
	}

	public String getProdCode2() {
		return prodCode2;
	}

	public void setProdCode2(String prodCode2) {
		this.prodCode2 = prodCode2;
	}

	public String getProdCode3() {
		return prodCode3;
	}

	public void setProdCode3(String prodCode3) {
		this.prodCode3 = prodCode3;
	}

	public String getProdCode4() {
		return prodCode4;
	}

	public void setProdCode4(String prodCode4) {
		this.prodCode4 = prodCode4;
	}

	public String getProdCode5() {
		return prodCode5;
	}

	public void setProdCode5(String prodCode5) {
		this.prodCode5 = prodCode5;
	}

	public String getProdCode6() {
		return prodCode6;
	}

	public void setProdCode6(String prodCode6) {
		this.prodCode6 = prodCode6;
	}

	public String getProdCode7() {
		return prodCode7;
	}

	public void setProdCode7(String prodCode7) {
		this.prodCode7 = prodCode7;
	}

	public String getProdCode8() {
		return prodCode8;
	}

	public void setProdCode8(String prodCode8) {
		this.prodCode8 = prodCode8;
	}

	public String getProdCode9() {
		return prodCode9;
	}

	public void setProdCode9(String prodCode9) {
		this.prodCode9 = prodCode9;
	}

	public Long getProdfleet() {
		return prodfleet;
	}

	public void setProdfleet(Long prodfleet) {
		this.prodfleet = prodfleet;
	}

	public String getPullSmsf() {
		return pullSmsf;
	}

	public void setPullSmsf(String pullSmsf) {
		this.pullSmsf = pullSmsf;
	}

	public String getPullSmsfUpdBy() {
		return pullSmsfUpdBy;
	}

	public void setPullSmsfUpdBy(String pullSmsfUpdBy) {
		this.pullSmsfUpdBy = pullSmsfUpdBy;
	}

	public Timestamp getPullSmsfUpdOn() {
		return pullSmsfUpdOn;
	}

	public void setPullSmsfUpdOn(Timestamp pullSmsfUpdOn) {
		this.pullSmsfUpdOn = pullSmsfUpdOn;
	}

	public String getPushSmsf() {
		return pushSmsf;
	}

	public void setPushSmsf(String pushSmsf) {
		this.pushSmsf = pushSmsf;
	}

	public String getPushSmsfUpdBy() {
		return pushSmsfUpdBy;
	}

	public void setPushSmsfUpdBy(String pushSmsfUpdBy) {
		this.pushSmsfUpdBy = pushSmsfUpdBy;
	}

	public Timestamp getPushSmsfUpdOn() {
		return pushSmsfUpdOn;
	}

	public void setPushSmsfUpdOn(Timestamp pushSmsfUpdOn) {
		this.pushSmsfUpdOn = pushSmsfUpdOn;
	}

	public String getPvv() {
		return pvv;
	}

	public void setPvv(String pvv) {
		this.pvv = pvv;
	}

	public Double getQtyAmtDay() {
		return qtyAmtDay;
	}

	public void setQtyAmtDay(Double qtyAmtDay) {
		this.qtyAmtDay = qtyAmtDay;
	}

	public Double getQtyAmtMonth() {
		return qtyAmtMonth;
	}

	public void setQtyAmtMonth(Double qtyAmtMonth) {
		this.qtyAmtMonth = qtyAmtMonth;
	}

	public Double getQtyAmtTxn() {
		return qtyAmtTxn;
	}

	public void setQtyAmtTxn(Double qtyAmtTxn) {
		this.qtyAmtTxn = qtyAmtTxn;
	}

	public Double getQtyAmtWeek() {
		return qtyAmtWeek;
	}

	public void setQtyAmtWeek(Double qtyAmtWeek) {
		this.qtyAmtWeek = qtyAmtWeek;
	}

	public Double getQtyAmtYear() {
		return qtyAmtYear;
	}

	public void setQtyAmtYear(Double qtyAmtYear) {
		this.qtyAmtYear = qtyAmtYear;
	}

	public String getRePrint() {
		return rePrint;
	}

	public void setRePrint(String rePrint) {
		this.rePrint = rePrint;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRenewal() {
		return renewal;
	}

	public void setRenewal(String renewal) {
		this.renewal = renewal;
	}

	public String getResAddress() {
		return resAddress;
	}

	public void setResAddress(String resAddress) {
		this.resAddress = resAddress;
	}

	public String getResTel() {
		return resTel;
	}

	public void setResTel(String resTel) {
		this.resTel = resTel;
	}

	public String getReser() {
		return reser;
	}

	public void setReser(String reser) {
		this.reser = reser;
	}

	public String getServCode() {
		return servCode;
	}

	public void setServCode(String servCode) {
		this.servCode = servCode;
	}

	public String getSmartCardFlg() {
		return smartCardFlg;
	}

	public void setSmartCardFlg(String smartCardFlg) {
		this.smartCardFlg = smartCardFlg;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getSuppDesc() {
		return suppDesc;
	}

	public void setSuppDesc(String suppDesc) {
		this.suppDesc = suppDesc;
	}

	public String getSupplCard() {
		return supplCard;
	}

	public void setSupplCard(String supplCard) {
		this.supplCard = supplCard;
	}

	public BigDecimal getSuppliLimitPercentage() {
		return suppliLimitPercentage;
	}

	public void setSuppliLimitPercentage(BigDecimal suppliLimitPercentage) {
		this.suppliLimitPercentage = suppliLimitPercentage;
	}

	public Long getSuppliPan() {
		return suppliPan;
	}

	public void setSuppliPan(Long suppliPan) {
		this.suppliPan = suppliPan;
	}

	public String getSupplimentary() {
		return supplimentary;
	}

	public void setSupplimentary(String supplimentary) {
		this.supplimentary = supplimentary;
	}

	public String getTxnreportEmailf() {
		return txnreportEmailf;
	}

	public void setTxnreportEmailf(String txnreportEmailf) {
		this.txnreportEmailf = txnreportEmailf;
	}

	public String getTxnreportEmailfUpdBy() {
		return txnreportEmailfUpdBy;
	}

	public void setTxnreportEmailfUpdBy(String txnreportEmailfUpdBy) {
		this.txnreportEmailfUpdBy = txnreportEmailfUpdBy;
	}

	public Timestamp getTxnreportEmailfUpdOn() {
		return txnreportEmailfUpdOn;
	}

	public void setTxnreportEmailfUpdOn(Timestamp txnreportEmailfUpdOn) {
		this.txnreportEmailfUpdOn = txnreportEmailfUpdOn;
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

	public String getVehDesc() {
		return vehDesc;
	}

	public void setVehDesc(String vehDesc) {
		this.vehDesc = vehDesc;
	}

	public String getVisionRandom() {
		return visionRandom;
	}

	public void setVisionRandom(String visionRandom) {
		this.visionRandom = visionRandom;
	}

	

}