package com.ag.fuel.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the MAS_FLEET_BILL_CPY database table.
 * 
 */
//@Entity
@Table(name="MAS_FLEET_BILL")
@NamedQueries({
	@NamedQuery(name="MasFleetBill.findAll", query="SELECT m FROM MasFleetBill m"),
	@NamedQuery(name="MasFleetBill.findById", query="SELECT m FROM MasFleetBill m where m.billId = :id")
})
public class MasFleetBill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACC_NO")
	private Long accNo;

	@Column(name="ACC_STATUS")
	private String accStatus;

	@Column(name="ADJ_REV")
	private Double adjRev;

	@Column(name="AMOUNT_DUE")
	private Double amountDue;

	@Temporal(TemporalType.DATE)
	@Column(name="BILL_DATE")
	private Date billDate;

	@Column(name="BILL_ID")
	private String billId;

	@Column(name="BILL_MONTH")
	private String billMonth;

	@Column(name="CITY_NAME")
	private String cityName;

	@Column(name="CO_CONT_PERSON")
	private String coContPerson;

	private Double col31;

	private Double col32;

	private Double col33;

	private Double col34;

	private Double col35;

	private String col36;

	private String col37;

	@Column(name="COMMERCIAL_FLAG")
	private String commercialFlag;

	@Column(name="CONT_DESIG")
	private String contDesig;

	@Column(name="CORP_TXN_SUM")
	private Double corpTxnSum;

	@Column(name="CR_LIMIT")
	private Double crLimit;

	@Column(name="CURR_BAL")
	private Double currBal;

	@Column(name="CURR_MON_CONS")
	private Double currMonCons;

	@Column(name="CUST_TAX_REGNO")
	private String custTaxRegno;

	@Column(name="FLEET_TXN_SUM")
	private Double fleetTxnSum;

	@Column(name="GST_ADDRESS")
	private String gstAddress;

	@Column(name="GST_AMOUNT")
	private Double gstAmount;

	@Column(name="GST_CITY_NAME")
	private String gstCityName;

	@Column(name="GST_EXCLUDE_AMT")
	private Double gstExcludeAmt;

	@Temporal(TemporalType.DATE)
	@Column(name="GST_INVOICE_DATE")
	private Date gstInvoiceDate;

	@Column(name="GST_SERIAL_NO")
	private String gstSerialNo;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_PAY_DATE")
	private Date lastPayDate;

	@Column(name="LATE_PAY")
	private Double latePay;

	@Column(name="LEGAL_ADDRESS")
	private String legalAddress;

	@Column(name="LEGAL_NAME")
	private String legalName;

	@Column(name="MEMBERSHIP_FEE")
	private Double membershipFee;

	@Column(name="MEMBFEE_FC")
	private Double membfeeFc;

	@Column(name="MEMFEE_CC")
	private Double memfeeCc;

	private String nii;

	@Column(name="NO_OF_CORPCARDS")
	private Long noOfCorpcards;

	@Column(name="NO_OF_FLEETCARDS")
	private Long noOfFleetcards;

	private Double pay;

	@Temporal(TemporalType.DATE)
	@Column(name="PAY_DUE_DATE")
	private Date payDueDate;

	@Temporal(TemporalType.DATE)
	@Column(name="PAY_REC_DATE")
	private Date payRecDate;

	@Column(name="PAY_STATUS")
	private String payStatus;

	@Column(name="PRE_BAL")
	private Double preBal;

	@Column(name="PROD_CODE5")
	private String prodCode5;

	@Column(name="RENEW_MEMFEE_CC")
	private Double renewMemfeeCc;

	@Column(name="RENEW_MEMFEE_FC")
	private Double renewMemfeeFc;

	@Column(name="SER_CHAR")
	private Double serChar;

	@Column(name="SERVICE_CHARGES")
	private Double serviceCharges;

	@Column(name="TOTAL_PAY_MONTH")
	private Double totalPayMonth;

	@Column(name="VIS_FLAG")
	private String visFlag;

	public MasFleetBill() {
	}

	public Long getAccNo() {
		return accNo;
	}

	public void setAccNo(Long accNo) {
		this.accNo = accNo;
	}

	public String getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	public Double getAdjRev() {
		return adjRev;
	}

	public void setAdjRev(Double adjRev) {
		this.adjRev = adjRev;
	}

	public Double getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(Double amountDue) {
		this.amountDue = amountDue;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public String getBillMonth() {
		return billMonth;
	}

	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCoContPerson() {
		return coContPerson;
	}

	public void setCoContPerson(String coContPerson) {
		this.coContPerson = coContPerson;
	}

	public Double getCol31() {
		return col31;
	}

	public void setCol31(Double col31) {
		this.col31 = col31;
	}

	public Double getCol32() {
		return col32;
	}

	public void setCol32(Double col32) {
		this.col32 = col32;
	}

	public Double getCol33() {
		return col33;
	}

	public void setCol33(Double col33) {
		this.col33 = col33;
	}

	public Double getCol34() {
		return col34;
	}

	public void setCol34(Double col34) {
		this.col34 = col34;
	}

	public Double getCol35() {
		return col35;
	}

	public void setCol35(Double col35) {
		this.col35 = col35;
	}

	public String getCol36() {
		return col36;
	}

	public void setCol36(String col36) {
		this.col36 = col36;
	}

	public String getCol37() {
		return col37;
	}

	public void setCol37(String col37) {
		this.col37 = col37;
	}

	public String getCommercialFlag() {
		return commercialFlag;
	}

	public void setCommercialFlag(String commercialFlag) {
		this.commercialFlag = commercialFlag;
	}

	public String getContDesig() {
		return contDesig;
	}

	public void setContDesig(String contDesig) {
		this.contDesig = contDesig;
	}

	public Double getCorpTxnSum() {
		return corpTxnSum;
	}

	public void setCorpTxnSum(Double corpTxnSum) {
		this.corpTxnSum = corpTxnSum;
	}

	public Double getCrLimit() {
		return crLimit;
	}

	public void setCrLimit(Double crLimit) {
		this.crLimit = crLimit;
	}

	public Double getCurrBal() {
		return currBal;
	}

	public void setCurrBal(Double currBal) {
		this.currBal = currBal;
	}

	public Double getCurrMonCons() {
		return currMonCons;
	}

	public void setCurrMonCons(Double currMonCons) {
		this.currMonCons = currMonCons;
	}

	public String getCustTaxRegno() {
		return custTaxRegno;
	}

	public void setCustTaxRegno(String custTaxRegno) {
		this.custTaxRegno = custTaxRegno;
	}

	public Double getFleetTxnSum() {
		return fleetTxnSum;
	}

	public void setFleetTxnSum(Double fleetTxnSum) {
		this.fleetTxnSum = fleetTxnSum;
	}

	public String getGstAddress() {
		return gstAddress;
	}

	public void setGstAddress(String gstAddress) {
		this.gstAddress = gstAddress;
	}

	public Double getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(Double gstAmount) {
		this.gstAmount = gstAmount;
	}

	public String getGstCityName() {
		return gstCityName;
	}

	public void setGstCityName(String gstCityName) {
		this.gstCityName = gstCityName;
	}

	public Double getGstExcludeAmt() {
		return gstExcludeAmt;
	}

	public void setGstExcludeAmt(Double gstExcludeAmt) {
		this.gstExcludeAmt = gstExcludeAmt;
	}

	public Date getGstInvoiceDate() {
		return gstInvoiceDate;
	}

	public void setGstInvoiceDate(Date gstInvoiceDate) {
		this.gstInvoiceDate = gstInvoiceDate;
	}

	public String getGstSerialNo() {
		return gstSerialNo;
	}

	public void setGstSerialNo(String gstSerialNo) {
		this.gstSerialNo = gstSerialNo;
	}

	public Date getLastPayDate() {
		return lastPayDate;
	}

	public void setLastPayDate(Date lastPayDate) {
		this.lastPayDate = lastPayDate;
	}

	public Double getLatePay() {
		return latePay;
	}

	public void setLatePay(Double latePay) {
		this.latePay = latePay;
	}

	public String getLegalAddress() {
		return legalAddress;
	}

	public void setLegalAddress(String legalAddress) {
		this.legalAddress = legalAddress;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public Double getMembershipFee() {
		return membershipFee;
	}

	public void setMembershipFee(Double membershipFee) {
		this.membershipFee = membershipFee;
	}

	public Double getMembfeeFc() {
		return membfeeFc;
	}

	public void setMembfeeFc(Double membfeeFc) {
		this.membfeeFc = membfeeFc;
	}

	public Double getMemfeeCc() {
		return memfeeCc;
	}

	public void setMemfeeCc(Double memfeeCc) {
		this.memfeeCc = memfeeCc;
	}

	public String getNii() {
		return nii;
	}

	public void setNii(String nii) {
		this.nii = nii;
	}

	public Long getNoOfCorpcards() {
		return noOfCorpcards;
	}

	public void setNoOfCorpcards(Long noOfCorpcards) {
		this.noOfCorpcards = noOfCorpcards;
	}

	public Long getNoOfFleetcards() {
		return noOfFleetcards;
	}

	public void setNoOfFleetcards(Long noOfFleetcards) {
		this.noOfFleetcards = noOfFleetcards;
	}

	public Double getPay() {
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public Date getPayDueDate() {
		return payDueDate;
	}

	public void setPayDueDate(Date payDueDate) {
		this.payDueDate = payDueDate;
	}

	public Date getPayRecDate() {
		return payRecDate;
	}

	public void setPayRecDate(Date payRecDate) {
		this.payRecDate = payRecDate;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Double getPreBal() {
		return preBal;
	}

	public void setPreBal(Double preBal) {
		this.preBal = preBal;
	}

	public String getProdCode5() {
		return prodCode5;
	}

	public void setProdCode5(String prodCode5) {
		this.prodCode5 = prodCode5;
	}

	public Double getRenewMemfeeCc() {
		return renewMemfeeCc;
	}

	public void setRenewMemfeeCc(Double renewMemfeeCc) {
		this.renewMemfeeCc = renewMemfeeCc;
	}

	public Double getRenewMemfeeFc() {
		return renewMemfeeFc;
	}

	public void setRenewMemfeeFc(Double renewMemfeeFc) {
		this.renewMemfeeFc = renewMemfeeFc;
	}

	public Double getSerChar() {
		return serChar;
	}

	public void setSerChar(Double serChar) {
		this.serChar = serChar;
	}

	public Double getServiceCharges() {
		return serviceCharges;
	}

	public void setServiceCharges(Double serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	public Double getTotalPayMonth() {
		return totalPayMonth;
	}

	public void setTotalPayMonth(Double totalPayMonth) {
		this.totalPayMonth = totalPayMonth;
	}

	public String getVisFlag() {
		return visFlag;
	}

	public void setVisFlag(String visFlag) {
		this.visFlag = visFlag;
	}

	

}