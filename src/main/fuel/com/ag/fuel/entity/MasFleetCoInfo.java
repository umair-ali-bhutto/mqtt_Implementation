package com.ag.fuel.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the MAS_FLEET_CO_INFO_CPY database table.
 * 
 */
//@Entity
@Table(name="MAS_FLEET_CO_INFO")
@NamedQueries({
	@NamedQuery(name="MasFleetCoInfo.findAll", query="SELECT m FROM MasFleetCoInfo m"),
	@NamedQuery(name="MasFleetCoInfo.findById", query="SELECT m FROM MasFleetCoInfo m where m.coAccno = :id")
})
public class MasFleetCoInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACC_CODE")
	private Long accCode;

	@Column(name="ACC_REF")
	private String accRef;

	@Column(name="ACC_STATUS")
	private String accStatus;

	@Column(name="ADDRESS_FLAG")
	private String addressFlag;

	@Column(name="CO_ACCNO")
	private Long coAccno;

	@Column(name="CO_ACCNO_TYPE")
	private String coAccnoType;

	@Column(name="CO_ACCT_TYPE")
	private String coAcctType;

	@Column(name="CO_ADDRESS1")
	private String coAddress1;

	@Column(name="CO_ADDRESS2")
	private String coAddress2;

	@Column(name="CO_ADDRESS3")
	private String coAddress3;

	@Column(name="CO_AUTH1_DATE")
	private Timestamp coAuth1Date;

	@Column(name="CO_AUTH1_DESIG")
	private String coAuth1Desig;

	@Column(name="CO_AUTH1_NAME")
	private String coAuth1Name;

	@Column(name="CO_AUTH2_DATE")
	private Timestamp coAuth2Date;

	@Column(name="CO_AUTH2_DESIG")
	private String coAuth2Desig;

	@Column(name="CO_AUTH2_NAME")
	private String coAuth2Name;

	@Column(name="CO_BANK_TRNSFR")
	private String coBankTrnsfr;

	@Column(name="CO_BANK1_ACCT")
	private String coBank1Acct;

	@Column(name="CO_BANK1_BAL")
	private Double coBank1Bal;

	@Column(name="CO_BANK1_BR")
	private String coBank1Br;

	@Column(name="CO_BANK1_CITY")
	private String coBank1City;

	@Column(name="CO_BANK1_CODE")
	private String coBank1Code;

	@Column(name="CO_BANK1_DESIG")
	private String coBank1Desig;

	@Column(name="CO_BANK1_FAX")
	private String coBank1Fax;

	@Column(name="CO_BANK1_NAME")
	private String coBank1Name;

	@Column(name="CO_BANK1_PER")
	private String coBank1Per;

	@Column(name="CO_BANK1_TEL1")
	private String coBank1Tel1;

	@Column(name="CO_BANK1_TEL2")
	private String coBank1Tel2;

	@Column(name="CO_BANK2_ACCT")
	private String coBank2Acct;

	@Column(name="CO_BANK2_BAL")
	private Double coBank2Bal;

	@Column(name="CO_BANK2_BR")
	private String coBank2Br;

	@Column(name="CO_BANK2_CITY")
	private String coBank2City;

	@Column(name="CO_BANK2_CODE")
	private String coBank2Code;

	@Column(name="CO_BANK2_DESIG")
	private String coBank2Desig;

	@Column(name="CO_BANK2_FAX")
	private String coBank2Fax;

	@Column(name="CO_BANK2_NAME")
	private String coBank2Name;

	@Column(name="CO_BANK2_PER")
	private String coBank2Per;

	@Column(name="CO_BANK2_TEL1")
	private String coBank2Tel1;

	@Column(name="CO_BANK2_TEL2")
	private String coBank2Tel2;

	@Column(name="CO_BUS_DESC")
	private String coBusDesc;

	@Column(name="CO_BUS_DESC_OTHER")
	private String coBusDescOther;

	@Column(name="CO_BUS_NAME")
	private String coBusName;

	@Column(name="CO_CARD_CNT")
	private Long coCardCnt;

	@Column(name="CO_CARD_NAME")
	private String coCardName;

	@Column(name="CO_CEO_NAME")
	private String coCeoName;

	@Column(name="CO_CEO_NIC")
	private String coCeoNic;

	@Column(name="CO_CEO_NTN")
	private String coCeoNtn;

	@Column(name="CO_CHEQUES")
	private String coCheques;

	@Column(name="CO_CITY")
	private String coCity;

	@Column(name="CO_CODE")
	private Long coCode;

	@Column(name="CO_CONT_PERSON")
	private String coContPerson;

	@Column(name="CO_DAILY_QTY")
	private Long coDailyQty;

	@Column(name="CO_DAILY_RATE")
	private Double coDailyRate;

	@Column(name="CO_DIRECT_DB")
	private String coDirectDb;

	@Column(name="CO_DLY_AMT")
	private Double coDlyAmt;

	@Column(name="CO_DMND_DRFT")
	private String coDmndDrft;

	@Column(name="CO_EMAIL")
	private String coEmail;

	@Column(name="CO_EMP_CNT")
	private Long coEmpCnt;

	@Column(name="CO_FAX1")
	private String coFax1;

	@Column(name="CO_FAX2")
	private String coFax2;

	@Column(name="CO_FAX3")
	private String coFax3;

	@Column(name="CO_FCARD_CNT")
	private Long coFcardCnt;

	@Column(name="CO_FUEL_EXP")
	private Double coFuelExp;

	@Column(name="CO_NAME")
	private String coName;

	@Column(name="CO_NAME1")
	private String coName1;

	@Column(name="CO_NO_DAYS")
	private Long coNoDays;

	@Column(name="CO_NTN")
	private String coNtn;

	@Column(name="CO_PAY_ORDER")
	private String coPayOrder;

	@Column(name="CO_PSO_CSTMR")
	private String coPsoCstmr;

	@Column(name="CO_REQ_CR_LIMIT")
	private Double coReqCrLimit;

	@Column(name="CO_STATUS")
	private String coStatus;

	@Column(name="CO_STAX_REG")
	private String coStaxReg;

	@Column(name="CO_TEL1")
	private String coTel1;

	@Column(name="CO_TEL2")
	private String coTel2;

	@Column(name="CO_TEL3")
	private String coTel3;

	@Column(name="CO_TELEX")
	private String coTelex;

	@Column(name="CO_VEH_CNT")
	private Long coVehCnt;

	@Column(name="CO_WEB_SITE")
	private String coWebSite;

	@Column(name="CO_YEAR_INC")
	private Double coYearInc;

	@Column(name="COMMERCIAL_FLAG")
	private String commercialFlag;

	@Column(name="CONT_ADDRESS")
	private String contAddress;

	@Column(name="CONT_ADDRESS2")
	private String contAddress2;

	@Column(name="CONT_ADDRESS3")
	private String contAddress3;

	@Column(name="CONT_ADDSTATUS")
	private String contAddstatus;

	@Column(name="CONT_CITY")
	private String contCity;

	@Column(name="CONT_DESIG")
	private String contDesig;

	@Column(name="CONT_DIR_TEL1")
	private String contDirTel1;

	@Column(name="CONT_DIR_TEL2")
	private String contDirTel2;

	@Column(name="CONT_EMAIL")
	private String contEmail;

	@Column(name="CONT_MOBILE")
	private String contMobile;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	@Column(name="CUST_TAX_REGNO")
	private String custTaxRegno;

	@Column(name="DATE_OF_RECIEPT")
	private Timestamp dateOfReciept;

	@Column(name="DFT_LIMIT")
	private Double dftLimit;

	@Column(name="DUE_DATE")
	private Long dueDate;

	private String emailf;

	@Column(name="EMAILF_UPD_BY")
	private String emailfUpdBy;

	@Column(name="EMAILF_UPD_ON")
	private Timestamp emailfUpdOn;

	private String emailid;

	private String exported;

	@Column(name="EXPORTED_BY")
	private String exportedBy;

	@Column(name="EXPORTED_ON")
	private Timestamp exportedOn;

	private String frequency;

	@Column(name="FT_FLAG")
	private String ftFlag;

	@Column(name="GST_ADDRESS")
	private String gstAddress;

	@Column(name="GST_CITY_NAME")
	private String gstCityName;

	@Column(name="LATE_PAY_SUR")
	private Double latePaySur;

	@Column(name="LOY_PAN")
	private String loyPan;

	@Column(name="LOYALTY_CREATION")
	private String loyaltyCreation;

	@Column(name="MEMBER_FEE")
	private Double memberFee;

	@Column(name="MNO_CODE")
	private String mnoCode;

	private String msisdn;

	private String nii;

	@Column(name="NOB_FLAG")
	private String nobFlag;

	@Column(name="OTHER_MODE_PAYMENT")
	private String otherModePayment;

	@Column(name="OTHER_MOP")
	private String otherMop;

	@Column(name="PASSPORT_NO")
	private String passportNo;

	@Column(name="PM_FLAG")
	private String pmFlag;

	@Column(name="PSO_TAX_REGN0")
	private String psoTaxRegn0;

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

	@Column(name="REF1_ADDRESS1")
	private String ref1Address1;

	@Column(name="REF1_ADDRESS2")
	private String ref1Address2;

	@Column(name="REF1_ADDRESS3")
	private String ref1Address3;

	@Column(name="REF1_CO_NAME")
	private String ref1CoName;

	@Column(name="REF1_CONT_DESIG")
	private String ref1ContDesig;

	@Column(name="REF1_CONT_PRSN")
	private String ref1ContPrsn;

	@Column(name="REF1_FAX")
	private String ref1Fax;

	@Column(name="REF1_RLTN_TYPE")
	private String ref1RltnType;

	@Column(name="REF1_STATION_NM")
	private String ref1StationNm;

	@Column(name="REF1_TEL1")
	private String ref1Tel1;

	@Column(name="REF1_TEL2")
	private String ref1Tel2;

	@Column(name="REF2_ADDRESS1")
	private String ref2Address1;

	@Column(name="REF2_ADDRESS2")
	private String ref2Address2;

	@Column(name="REF2_ADDRESS3")
	private String ref2Address3;

	@Column(name="REF2_CO_NAME")
	private String ref2CoName;

	@Column(name="REF2_CONT_DESIG")
	private String ref2ContDesig;

	@Column(name="REF2_CONT_PRSN")
	private String ref2ContPrsn;

	@Column(name="REF2_FAX")
	private String ref2Fax;

	@Column(name="REF2_RLTN_TYPE")
	private String ref2RltnType;

	@Column(name="REF2_STATION_NM")
	private String ref2StationNm;

	@Column(name="REF2_TEL1")
	private String ref2Tel1;

	@Column(name="REF2_TEL2")
	private String ref2Tel2;

	@Column(name="RENEW_MEMBER_FEE")
	private Double renewMemberFee;

	@Column(name="ROC_PRINT")
	private String rocPrint;

	@Column(name="SALES_OFFICER")
	private String salesOfficer;

	@Column(name="SEC_DEPO")
	private Double secDepo;

	@Column(name="SERVICE_CHARGES")
	private Double serviceCharges;

	@Column(name="SMART_MEMBER_FEE")
	private Double smartMemberFee;

	@Column(name="SMART_RENEW_MEMBER_FEE")
	private Double smartRenewMemberFee;

	@Column(name="STATUS_CODE")
	private String statusCode;

	@Column(name="SUPPLIER_NAME")
	private String supplierName;

	@Column(name="TEST_FLAG")
	private String testFlag;

	@Column(name="TRANS_F_D")
	private Double transFD;

	@Column(name="TRANS_F_W")
	private Double transFW;

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

	public MasFleetCoInfo() {
	}

	public Long getAccCode() {
		return accCode;
	}

	public void setAccCode(Long accCode) {
		this.accCode = accCode;
	}

	public String getAccRef() {
		return accRef;
	}

	public void setAccRef(String accRef) {
		this.accRef = accRef;
	}

	public String getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	public String getAddressFlag() {
		return addressFlag;
	}

	public void setAddressFlag(String addressFlag) {
		this.addressFlag = addressFlag;
	}

	public Long getCoAccno() {
		return coAccno;
	}

	public void setCoAccno(Long coAccno) {
		this.coAccno = coAccno;
	}

	public String getCoAccnoType() {
		return coAccnoType;
	}

	public void setCoAccnoType(String coAccnoType) {
		this.coAccnoType = coAccnoType;
	}

	public String getCoAcctType() {
		return coAcctType;
	}

	public void setCoAcctType(String coAcctType) {
		this.coAcctType = coAcctType;
	}

	public String getCoAddress1() {
		return coAddress1;
	}

	public void setCoAddress1(String coAddress1) {
		this.coAddress1 = coAddress1;
	}

	public String getCoAddress2() {
		return coAddress2;
	}

	public void setCoAddress2(String coAddress2) {
		this.coAddress2 = coAddress2;
	}

	public String getCoAddress3() {
		return coAddress3;
	}

	public void setCoAddress3(String coAddress3) {
		this.coAddress3 = coAddress3;
	}

	public Timestamp getCoAuth1Date() {
		return coAuth1Date;
	}

	public void setCoAuth1Date(Timestamp coAuth1Date) {
		this.coAuth1Date = coAuth1Date;
	}

	public String getCoAuth1Desig() {
		return coAuth1Desig;
	}

	public void setCoAuth1Desig(String coAuth1Desig) {
		this.coAuth1Desig = coAuth1Desig;
	}

	public String getCoAuth1Name() {
		return coAuth1Name;
	}

	public void setCoAuth1Name(String coAuth1Name) {
		this.coAuth1Name = coAuth1Name;
	}

	public Timestamp getCoAuth2Date() {
		return coAuth2Date;
	}

	public void setCoAuth2Date(Timestamp coAuth2Date) {
		this.coAuth2Date = coAuth2Date;
	}

	public String getCoAuth2Desig() {
		return coAuth2Desig;
	}

	public void setCoAuth2Desig(String coAuth2Desig) {
		this.coAuth2Desig = coAuth2Desig;
	}

	public String getCoAuth2Name() {
		return coAuth2Name;
	}

	public void setCoAuth2Name(String coAuth2Name) {
		this.coAuth2Name = coAuth2Name;
	}

	public String getCoBankTrnsfr() {
		return coBankTrnsfr;
	}

	public void setCoBankTrnsfr(String coBankTrnsfr) {
		this.coBankTrnsfr = coBankTrnsfr;
	}

	public String getCoBank1Acct() {
		return coBank1Acct;
	}

	public void setCoBank1Acct(String coBank1Acct) {
		this.coBank1Acct = coBank1Acct;
	}

	public Double getCoBank1Bal() {
		return coBank1Bal;
	}

	public void setCoBank1Bal(Double coBank1Bal) {
		this.coBank1Bal = coBank1Bal;
	}

	public String getCoBank1Br() {
		return coBank1Br;
	}

	public void setCoBank1Br(String coBank1Br) {
		this.coBank1Br = coBank1Br;
	}

	public String getCoBank1City() {
		return coBank1City;
	}

	public void setCoBank1City(String coBank1City) {
		this.coBank1City = coBank1City;
	}

	public String getCoBank1Code() {
		return coBank1Code;
	}

	public void setCoBank1Code(String coBank1Code) {
		this.coBank1Code = coBank1Code;
	}

	public String getCoBank1Desig() {
		return coBank1Desig;
	}

	public void setCoBank1Desig(String coBank1Desig) {
		this.coBank1Desig = coBank1Desig;
	}

	public String getCoBank1Fax() {
		return coBank1Fax;
	}

	public void setCoBank1Fax(String coBank1Fax) {
		this.coBank1Fax = coBank1Fax;
	}

	public String getCoBank1Name() {
		return coBank1Name;
	}

	public void setCoBank1Name(String coBank1Name) {
		this.coBank1Name = coBank1Name;
	}

	public String getCoBank1Per() {
		return coBank1Per;
	}

	public void setCoBank1Per(String coBank1Per) {
		this.coBank1Per = coBank1Per;
	}

	public String getCoBank1Tel1() {
		return coBank1Tel1;
	}

	public void setCoBank1Tel1(String coBank1Tel1) {
		this.coBank1Tel1 = coBank1Tel1;
	}

	public String getCoBank1Tel2() {
		return coBank1Tel2;
	}

	public void setCoBank1Tel2(String coBank1Tel2) {
		this.coBank1Tel2 = coBank1Tel2;
	}

	public String getCoBank2Acct() {
		return coBank2Acct;
	}

	public void setCoBank2Acct(String coBank2Acct) {
		this.coBank2Acct = coBank2Acct;
	}

	public Double getCoBank2Bal() {
		return coBank2Bal;
	}

	public void setCoBank2Bal(Double coBank2Bal) {
		this.coBank2Bal = coBank2Bal;
	}

	public String getCoBank2Br() {
		return coBank2Br;
	}

	public void setCoBank2Br(String coBank2Br) {
		this.coBank2Br = coBank2Br;
	}

	public String getCoBank2City() {
		return coBank2City;
	}

	public void setCoBank2City(String coBank2City) {
		this.coBank2City = coBank2City;
	}

	public String getCoBank2Code() {
		return coBank2Code;
	}

	public void setCoBank2Code(String coBank2Code) {
		this.coBank2Code = coBank2Code;
	}

	public String getCoBank2Desig() {
		return coBank2Desig;
	}

	public void setCoBank2Desig(String coBank2Desig) {
		this.coBank2Desig = coBank2Desig;
	}

	public String getCoBank2Fax() {
		return coBank2Fax;
	}

	public void setCoBank2Fax(String coBank2Fax) {
		this.coBank2Fax = coBank2Fax;
	}

	public String getCoBank2Name() {
		return coBank2Name;
	}

	public void setCoBank2Name(String coBank2Name) {
		this.coBank2Name = coBank2Name;
	}

	public String getCoBank2Per() {
		return coBank2Per;
	}

	public void setCoBank2Per(String coBank2Per) {
		this.coBank2Per = coBank2Per;
	}

	public String getCoBank2Tel1() {
		return coBank2Tel1;
	}

	public void setCoBank2Tel1(String coBank2Tel1) {
		this.coBank2Tel1 = coBank2Tel1;
	}

	public String getCoBank2Tel2() {
		return coBank2Tel2;
	}

	public void setCoBank2Tel2(String coBank2Tel2) {
		this.coBank2Tel2 = coBank2Tel2;
	}

	public String getCoBusDesc() {
		return coBusDesc;
	}

	public void setCoBusDesc(String coBusDesc) {
		this.coBusDesc = coBusDesc;
	}

	public String getCoBusDescOther() {
		return coBusDescOther;
	}

	public void setCoBusDescOther(String coBusDescOther) {
		this.coBusDescOther = coBusDescOther;
	}

	public String getCoBusName() {
		return coBusName;
	}

	public void setCoBusName(String coBusName) {
		this.coBusName = coBusName;
	}

	public Long getCoCardCnt() {
		return coCardCnt;
	}

	public void setCoCardCnt(Long coCardCnt) {
		this.coCardCnt = coCardCnt;
	}

	public String getCoCardName() {
		return coCardName;
	}

	public void setCoCardName(String coCardName) {
		this.coCardName = coCardName;
	}

	public String getCoCeoName() {
		return coCeoName;
	}

	public void setCoCeoName(String coCeoName) {
		this.coCeoName = coCeoName;
	}

	public String getCoCeoNic() {
		return coCeoNic;
	}

	public void setCoCeoNic(String coCeoNic) {
		this.coCeoNic = coCeoNic;
	}

	public String getCoCeoNtn() {
		return coCeoNtn;
	}

	public void setCoCeoNtn(String coCeoNtn) {
		this.coCeoNtn = coCeoNtn;
	}

	public String getCoCheques() {
		return coCheques;
	}

	public void setCoCheques(String coCheques) {
		this.coCheques = coCheques;
	}

	public String getCoCity() {
		return coCity;
	}

	public void setCoCity(String coCity) {
		this.coCity = coCity;
	}

	public Long getCoCode() {
		return coCode;
	}

	public void setCoCode(Long coCode) {
		this.coCode = coCode;
	}

	public String getCoContPerson() {
		return coContPerson;
	}

	public void setCoContPerson(String coContPerson) {
		this.coContPerson = coContPerson;
	}

	public Long getCoDailyQty() {
		return coDailyQty;
	}

	public void setCoDailyQty(Long coDailyQty) {
		this.coDailyQty = coDailyQty;
	}

	public Double getCoDailyRate() {
		return coDailyRate;
	}

	public void setCoDailyRate(Double coDailyRate) {
		this.coDailyRate = coDailyRate;
	}

	public String getCoDirectDb() {
		return coDirectDb;
	}

	public void setCoDirectDb(String coDirectDb) {
		this.coDirectDb = coDirectDb;
	}

	public Double getCoDlyAmt() {
		return coDlyAmt;
	}

	public void setCoDlyAmt(Double coDlyAmt) {
		this.coDlyAmt = coDlyAmt;
	}

	public String getCoDmndDrft() {
		return coDmndDrft;
	}

	public void setCoDmndDrft(String coDmndDrft) {
		this.coDmndDrft = coDmndDrft;
	}

	public String getCoEmail() {
		return coEmail;
	}

	public void setCoEmail(String coEmail) {
		this.coEmail = coEmail;
	}

	public Long getCoEmpCnt() {
		return coEmpCnt;
	}

	public void setCoEmpCnt(Long coEmpCnt) {
		this.coEmpCnt = coEmpCnt;
	}

	public String getCoFax1() {
		return coFax1;
	}

	public void setCoFax1(String coFax1) {
		this.coFax1 = coFax1;
	}

	public String getCoFax2() {
		return coFax2;
	}

	public void setCoFax2(String coFax2) {
		this.coFax2 = coFax2;
	}

	public String getCoFax3() {
		return coFax3;
	}

	public void setCoFax3(String coFax3) {
		this.coFax3 = coFax3;
	}

	public Long getCoFcardCnt() {
		return coFcardCnt;
	}

	public void setCoFcardCnt(Long coFcardCnt) {
		this.coFcardCnt = coFcardCnt;
	}

	public Double getCoFuelExp() {
		return coFuelExp;
	}

	public void setCoFuelExp(Double coFuelExp) {
		this.coFuelExp = coFuelExp;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getCoName1() {
		return coName1;
	}

	public void setCoName1(String coName1) {
		this.coName1 = coName1;
	}

	public Long getCoNoDays() {
		return coNoDays;
	}

	public void setCoNoDays(Long coNoDays) {
		this.coNoDays = coNoDays;
	}

	public String getCoNtn() {
		return coNtn;
	}

	public void setCoNtn(String coNtn) {
		this.coNtn = coNtn;
	}

	public String getCoPayOrder() {
		return coPayOrder;
	}

	public void setCoPayOrder(String coPayOrder) {
		this.coPayOrder = coPayOrder;
	}

	public String getCoPsoCstmr() {
		return coPsoCstmr;
	}

	public void setCoPsoCstmr(String coPsoCstmr) {
		this.coPsoCstmr = coPsoCstmr;
	}

	public Double getCoReqCrLimit() {
		return coReqCrLimit;
	}

	public void setCoReqCrLimit(Double coReqCrLimit) {
		this.coReqCrLimit = coReqCrLimit;
	}

	public String getCoStatus() {
		return coStatus;
	}

	public void setCoStatus(String coStatus) {
		this.coStatus = coStatus;
	}

	public String getCoStaxReg() {
		return coStaxReg;
	}

	public void setCoStaxReg(String coStaxReg) {
		this.coStaxReg = coStaxReg;
	}

	public String getCoTel1() {
		return coTel1;
	}

	public void setCoTel1(String coTel1) {
		this.coTel1 = coTel1;
	}

	public String getCoTel2() {
		return coTel2;
	}

	public void setCoTel2(String coTel2) {
		this.coTel2 = coTel2;
	}

	public String getCoTel3() {
		return coTel3;
	}

	public void setCoTel3(String coTel3) {
		this.coTel3 = coTel3;
	}

	public String getCoTelex() {
		return coTelex;
	}

	public void setCoTelex(String coTelex) {
		this.coTelex = coTelex;
	}

	public Long getCoVehCnt() {
		return coVehCnt;
	}

	public void setCoVehCnt(Long coVehCnt) {
		this.coVehCnt = coVehCnt;
	}

	public String getCoWebSite() {
		return coWebSite;
	}

	public void setCoWebSite(String coWebSite) {
		this.coWebSite = coWebSite;
	}

	public Double getCoYearInc() {
		return coYearInc;
	}

	public void setCoYearInc(Double coYearInc) {
		this.coYearInc = coYearInc;
	}

	public String getCommercialFlag() {
		return commercialFlag;
	}

	public void setCommercialFlag(String commercialFlag) {
		this.commercialFlag = commercialFlag;
	}

	public String getContAddress() {
		return contAddress;
	}

	public void setContAddress(String contAddress) {
		this.contAddress = contAddress;
	}

	public String getContAddress2() {
		return contAddress2;
	}

	public void setContAddress2(String contAddress2) {
		this.contAddress2 = contAddress2;
	}

	public String getContAddress3() {
		return contAddress3;
	}

	public void setContAddress3(String contAddress3) {
		this.contAddress3 = contAddress3;
	}

	public String getContAddstatus() {
		return contAddstatus;
	}

	public void setContAddstatus(String contAddstatus) {
		this.contAddstatus = contAddstatus;
	}

	public String getContCity() {
		return contCity;
	}

	public void setContCity(String contCity) {
		this.contCity = contCity;
	}

	public String getContDesig() {
		return contDesig;
	}

	public void setContDesig(String contDesig) {
		this.contDesig = contDesig;
	}

	public String getContDirTel1() {
		return contDirTel1;
	}

	public void setContDirTel1(String contDirTel1) {
		this.contDirTel1 = contDirTel1;
	}

	public String getContDirTel2() {
		return contDirTel2;
	}

	public void setContDirTel2(String contDirTel2) {
		this.contDirTel2 = contDirTel2;
	}

	public String getContEmail() {
		return contEmail;
	}

	public void setContEmail(String contEmail) {
		this.contEmail = contEmail;
	}

	public String getContMobile() {
		return contMobile;
	}

	public void setContMobile(String contMobile) {
		this.contMobile = contMobile;
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

	public String getCustTaxRegno() {
		return custTaxRegno;
	}

	public void setCustTaxRegno(String custTaxRegno) {
		this.custTaxRegno = custTaxRegno;
	}

	public Timestamp getDateOfReciept() {
		return dateOfReciept;
	}

	public void setDateOfReciept(Timestamp dateOfReciept) {
		this.dateOfReciept = dateOfReciept;
	}

	public Double getDftLimit() {
		return dftLimit;
	}

	public void setDftLimit(Double dftLimit) {
		this.dftLimit = dftLimit;
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

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFtFlag() {
		return ftFlag;
	}

	public void setFtFlag(String ftFlag) {
		this.ftFlag = ftFlag;
	}

	public String getGstAddress() {
		return gstAddress;
	}

	public void setGstAddress(String gstAddress) {
		this.gstAddress = gstAddress;
	}

	public String getGstCityName() {
		return gstCityName;
	}

	public void setGstCityName(String gstCityName) {
		this.gstCityName = gstCityName;
	}

	public Double getLatePaySur() {
		return latePaySur;
	}

	public void setLatePaySur(Double latePaySur) {
		this.latePaySur = latePaySur;
	}

	public String getLoyPan() {
		return loyPan;
	}

	public void setLoyPan(String loyPan) {
		this.loyPan = loyPan;
	}

	public String getLoyaltyCreation() {
		return loyaltyCreation;
	}

	public void setLoyaltyCreation(String loyaltyCreation) {
		this.loyaltyCreation = loyaltyCreation;
	}

	public Double getMemberFee() {
		return memberFee;
	}

	public void setMemberFee(Double memberFee) {
		this.memberFee = memberFee;
	}

	public String getMnoCode() {
		return mnoCode;
	}

	public void setMnoCode(String mnoCode) {
		this.mnoCode = mnoCode;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getNii() {
		return nii;
	}

	public void setNii(String nii) {
		this.nii = nii;
	}

	public String getNobFlag() {
		return nobFlag;
	}

	public void setNobFlag(String nobFlag) {
		this.nobFlag = nobFlag;
	}

	public String getOtherModePayment() {
		return otherModePayment;
	}

	public void setOtherModePayment(String otherModePayment) {
		this.otherModePayment = otherModePayment;
	}

	public String getOtherMop() {
		return otherMop;
	}

	public void setOtherMop(String otherMop) {
		this.otherMop = otherMop;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getPmFlag() {
		return pmFlag;
	}

	public void setPmFlag(String pmFlag) {
		this.pmFlag = pmFlag;
	}

	public String getPsoTaxRegn0() {
		return psoTaxRegn0;
	}

	public void setPsoTaxRegn0(String psoTaxRegn0) {
		this.psoTaxRegn0 = psoTaxRegn0;
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

	public String getRef1Address1() {
		return ref1Address1;
	}

	public void setRef1Address1(String ref1Address1) {
		this.ref1Address1 = ref1Address1;
	}

	public String getRef1Address2() {
		return ref1Address2;
	}

	public void setRef1Address2(String ref1Address2) {
		this.ref1Address2 = ref1Address2;
	}

	public String getRef1Address3() {
		return ref1Address3;
	}

	public void setRef1Address3(String ref1Address3) {
		this.ref1Address3 = ref1Address3;
	}

	public String getRef1CoName() {
		return ref1CoName;
	}

	public void setRef1CoName(String ref1CoName) {
		this.ref1CoName = ref1CoName;
	}

	public String getRef1ContDesig() {
		return ref1ContDesig;
	}

	public void setRef1ContDesig(String ref1ContDesig) {
		this.ref1ContDesig = ref1ContDesig;
	}

	public String getRef1ContPrsn() {
		return ref1ContPrsn;
	}

	public void setRef1ContPrsn(String ref1ContPrsn) {
		this.ref1ContPrsn = ref1ContPrsn;
	}

	public String getRef1Fax() {
		return ref1Fax;
	}

	public void setRef1Fax(String ref1Fax) {
		this.ref1Fax = ref1Fax;
	}

	public String getRef1RltnType() {
		return ref1RltnType;
	}

	public void setRef1RltnType(String ref1RltnType) {
		this.ref1RltnType = ref1RltnType;
	}

	public String getRef1StationNm() {
		return ref1StationNm;
	}

	public void setRef1StationNm(String ref1StationNm) {
		this.ref1StationNm = ref1StationNm;
	}

	public String getRef1Tel1() {
		return ref1Tel1;
	}

	public void setRef1Tel1(String ref1Tel1) {
		this.ref1Tel1 = ref1Tel1;
	}

	public String getRef1Tel2() {
		return ref1Tel2;
	}

	public void setRef1Tel2(String ref1Tel2) {
		this.ref1Tel2 = ref1Tel2;
	}

	public String getRef2Address1() {
		return ref2Address1;
	}

	public void setRef2Address1(String ref2Address1) {
		this.ref2Address1 = ref2Address1;
	}

	public String getRef2Address2() {
		return ref2Address2;
	}

	public void setRef2Address2(String ref2Address2) {
		this.ref2Address2 = ref2Address2;
	}

	public String getRef2Address3() {
		return ref2Address3;
	}

	public void setRef2Address3(String ref2Address3) {
		this.ref2Address3 = ref2Address3;
	}

	public String getRef2CoName() {
		return ref2CoName;
	}

	public void setRef2CoName(String ref2CoName) {
		this.ref2CoName = ref2CoName;
	}

	public String getRef2ContDesig() {
		return ref2ContDesig;
	}

	public void setRef2ContDesig(String ref2ContDesig) {
		this.ref2ContDesig = ref2ContDesig;
	}

	public String getRef2ContPrsn() {
		return ref2ContPrsn;
	}

	public void setRef2ContPrsn(String ref2ContPrsn) {
		this.ref2ContPrsn = ref2ContPrsn;
	}

	public String getRef2Fax() {
		return ref2Fax;
	}

	public void setRef2Fax(String ref2Fax) {
		this.ref2Fax = ref2Fax;
	}

	public String getRef2RltnType() {
		return ref2RltnType;
	}

	public void setRef2RltnType(String ref2RltnType) {
		this.ref2RltnType = ref2RltnType;
	}

	public String getRef2StationNm() {
		return ref2StationNm;
	}

	public void setRef2StationNm(String ref2StationNm) {
		this.ref2StationNm = ref2StationNm;
	}

	public String getRef2Tel1() {
		return ref2Tel1;
	}

	public void setRef2Tel1(String ref2Tel1) {
		this.ref2Tel1 = ref2Tel1;
	}

	public String getRef2Tel2() {
		return ref2Tel2;
	}

	public void setRef2Tel2(String ref2Tel2) {
		this.ref2Tel2 = ref2Tel2;
	}

	public Double getRenewMemberFee() {
		return renewMemberFee;
	}

	public void setRenewMemberFee(Double renewMemberFee) {
		this.renewMemberFee = renewMemberFee;
	}

	public String getRocPrint() {
		return rocPrint;
	}

	public void setRocPrint(String rocPrint) {
		this.rocPrint = rocPrint;
	}

	public String getSalesOfficer() {
		return salesOfficer;
	}

	public void setSalesOfficer(String salesOfficer) {
		this.salesOfficer = salesOfficer;
	}

	public Double getSecDepo() {
		return secDepo;
	}

	public void setSecDepo(Double secDepo) {
		this.secDepo = secDepo;
	}

	public Double getServiceCharges() {
		return serviceCharges;
	}

	public void setServiceCharges(Double serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	public Double getSmartMemberFee() {
		return smartMemberFee;
	}

	public void setSmartMemberFee(Double smartMemberFee) {
		this.smartMemberFee = smartMemberFee;
	}

	public Double getSmartRenewMemberFee() {
		return smartRenewMemberFee;
	}

	public void setSmartRenewMemberFee(Double smartRenewMemberFee) {
		this.smartRenewMemberFee = smartRenewMemberFee;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(String testFlag) {
		this.testFlag = testFlag;
	}

	public Double getTransFD() {
		return transFD;
	}

	public void setTransFD(Double transFD) {
		this.transFD = transFD;
	}

	public Double getTransFW() {
		return transFW;
	}

	public void setTransFW(Double transFW) {
		this.transFW = transFW;
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

	public Long getDueDate() {
		return dueDate;
	}

	public void setDueDate(Long dueDate) {
		this.dueDate = dueDate;
	}

	

}