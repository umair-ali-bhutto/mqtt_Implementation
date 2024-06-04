package com.ag.mportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the TXN_DETAILS database table.
 * 
 */
@Entity
@Table(name = "VW_PREAUTH")
@NamedQuery(name = "VwPreauth.fetchAll", query = "SELECT d FROM VwPreauth d ORDER BY d.txndetailid")
public class VwPreauth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TXNDETAILID")
	private String txndetailid;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "FIELD_ONE")
	private String fieldOne;

	@Column(name = "MERCHANTID")
	private String merchantid;

	@Column(name = "TERMINALID")
	private String terminalid;

	@Column(name = "ENTRYDATE")
	private String entrydate;

	@Column(name = "TXNAMOUNT")
	private String txnamount;

	@Column(name = "AUTHID")
	private String authid;

	@Column(name = "AUTH_STATUS")
	private String authStatus;

	@Column(name = "AUTH_EXP")
	private String authExp;

	@Column(name = "CUSTOMERNAME")
	private String customername;

	@Column(name = "SCHEME")
	private String scheme;

	@Column(name = "MODEL")
	private String model;

	@Column(name = "CARD_TYPE")
	private String cardType;

	@Column(name = "CARD_EXPIRY")
	private String cardExpiry;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "MDR_ONUS")
	private String mdrOnus;

	@Column(name = "SETTLED_DATE")
	private String settledDate;

	@Column(name = "MDR_OFFUS")
	private String mdrOffus;

	@Column(name = "ADJ_AMOUNT")
	private String adjAmount;

	@Column(name = "INVOICE_NUM")
	private String invoiceNum;

	@Column(name = "FED")
	private String fed;

	@Column(name = "TIM_AMOUNT")
	private String timAmount;

	@Column(name = "POS_ENTRY_MODE")
	private String posEntryMode;

	@Column(name = "NET_AMOUNT")
	private String netAmount;

	@Column(name = "TVR")
	private String tvr;

	@Column(name = "AID")
	private String aid;

	@Column(name = "IMSI_NO")
	private String imsiNo;

	@Column(name = "APP_VER")
	private String appVer;

	public String getTxndetailid() {
		return txndetailid;
	}

	public void setTxndetailid(String txndetailid) {
		this.txndetailid = txndetailid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFieldOne() {
		return fieldOne;
	}

	public void setFieldOne(String fieldOne) {
		this.fieldOne = fieldOne;
	}

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getTerminalid() {
		return terminalid;
	}

	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}

	public String getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}

	public String getTxnamount() {
		return txnamount;
	}

	public void setTxnamount(String txnamount) {
		this.txnamount = txnamount;
	}

	public String getAuthid() {
		return authid;
	}

	public void setAuthid(String authid) {
		this.authid = authid;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getAuthExp() {
		return authExp;
	}

	public void setAuthExp(String authExp) {
		this.authExp = authExp;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardExpiry() {
		return cardExpiry;
	}

	public void setCardExpiry(String cardExpiry) {
		this.cardExpiry = cardExpiry;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getMdrOnus() {
		return mdrOnus;
	}

	public void setMdrOnus(String mdrOnus) {
		this.mdrOnus = mdrOnus;
	}

	public String getSettledDate() {
		return settledDate;
	}

	public void setSettledDate(String settledDate) {
		this.settledDate = settledDate;
	}

	public String getMdrOffus() {
		return mdrOffus;
	}

	public void setMdrOffus(String mdrOffus) {
		this.mdrOffus = mdrOffus;
	}

	public String getAdjAmount() {
		return adjAmount;
	}

	public void setAdjAmount(String adjAmount) {
		this.adjAmount = adjAmount;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getFed() {
		return fed;
	}

	public void setFed(String fed) {
		this.fed = fed;
	}

	public String getTimAmount() {
		return timAmount;
	}

	public void setTimAmount(String timAmount) {
		this.timAmount = timAmount;
	}

	public String getPosEntryMode() {
		return posEntryMode;
	}

	public void setPosEntryMode(String posEntryMode) {
		this.posEntryMode = posEntryMode;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getTvr() {
		return tvr;
	}

	public void setTvr(String tvr) {
		this.tvr = tvr;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getImsiNo() {
		return imsiNo;
	}

	public void setImsiNo(String imsiNo) {
		this.imsiNo = imsiNo;
	}

	public String getAppVer() {
		return appVer;
	}

	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}

}
