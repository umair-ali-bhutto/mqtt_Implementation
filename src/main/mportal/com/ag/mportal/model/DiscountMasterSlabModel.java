package com.ag.mportal.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DiscountMasterSlabModel {

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "DISC_NAME")
	private String discName;

	@Column(name = "DISC_DESCRIPTION")
	private String discDescription;

	@Column(name = "DISC_STATUS")
	private int discStatus;

	@Column(name = "START_DATE")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "BIN_SPECIFIC")
	private int binSpecific;

	@Column(name = "MERCHANT_SPECIFIC")
	private int merchantSpecific;

	@Column(name = "TIME_SPECIFIC")
	private int timeSpecific;

	@Column(name = "KYC_SPECIFIC")
	private int kycSpecific;

	@Column(name = "CATEGORY_SPECIFIC")
	private int categorySpecific;

	@Column(name = "CITY_SPECIFIC")
	private int citySpecific;

	@Column(name = "TERMINAL_SPECIFIC")
	private int terminalSpecific;

	@Column(name = "MERCHANT_BASED_DISC")
	private int merchantBasedDiscount;

	@Column(name = "GL_ACCOUNT")
	private String glAccount;

	@Column(name = "GL_ACCOUNT2")
	private String glAccount2;

	@Column(name = "BUDGET_AMOUNT_TOTAL")
	private double budgetAmountTotal;

	@Column(name = "BUDGET_AMOUNT_AVBL")
	private double budgetAmountAvbl;

	@Column(name = "DISC_ALLOWED_TXN_PER_DAY")
	private int discAllowedTxtPerDay;

	@Column(name = "DISC_ALLOWED_TXN_PER_WEEK")
	private int discAllowedTxtPerWeek;

	@Column(name = "DISC_ALLOWED_TXN_PER_YEAR")
	private int discAllowedTxtPerYear;

	@Column(name = "DISC_ALLOWED_TXN_PER_MONTH")
	private int discAllowedTxtPerMonth;

	@Column(name = "ENTRY_BY")
	private String entryBy;

	@Column(name = "ENTRY_ON")
	private Timestamp entryOn;

	@Column(name = "UPD_BY")
	private String udpBy;

	@Column(name = "UPD_ON")
	private Timestamp updOn;

	@Column(name = "CORP_ID")
	private String corpId;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "DISC_CAMPAIGN_TYPE")
	private String discCampaignType;

	@Column(name = "BUDGET_CAPPING")
	private int bugdetCapping;

	@Column(name = "DISC_ID")
	private long discId;

	@Column(name = "AMOUNT_FROM")
	private double amountFrom;

	@Column(name = "AMOUNT_TO")
	private double amountTo;

	@Column(name = "DISC_TYPE")
	private String discType;

	@Column(name = "DISC_VALUE")
	private double discValue;

	@Column(name = "MAX_DISCOUNT_PER_TXN")
	private double maxDiscountPerTxn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDiscName() {
		return discName;
	}

	public void setDiscName(String discName) {
		this.discName = discName;
	}

	public String getDiscDescription() {
		return discDescription;
	}

	public void setDiscDescription(String discDescription) {
		this.discDescription = discDescription;
	}

	public int getDiscStatus() {
		return discStatus;
	}

	public void setDiscStatus(int discStatus) {
		this.discStatus = discStatus;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public int getBinSpecific() {
		return binSpecific;
	}

	public void setBinSpecific(int binSpecific) {
		this.binSpecific = binSpecific;
	}

	public int getMerchantSpecific() {
		return merchantSpecific;
	}

	public void setMerchantSpecific(int merchantSpecific) {
		this.merchantSpecific = merchantSpecific;
	}

	public int getTimeSpecific() {
		return timeSpecific;
	}

	public void setTimeSpecific(int timeSpecific) {
		this.timeSpecific = timeSpecific;
	}

	public int getKycSpecific() {
		return kycSpecific;
	}

	public void setKycSpecific(int kycSpecific) {
		this.kycSpecific = kycSpecific;
	}

	public int getCategorySpecific() {
		return categorySpecific;
	}

	public void setCategorySpecific(int categorySpecific) {
		this.categorySpecific = categorySpecific;
	}

	public int getCitySpecific() {
		return citySpecific;
	}

	public void setCitySpecific(int citySpecific) {
		this.citySpecific = citySpecific;
	}

	public int getTerminalSpecific() {
		return terminalSpecific;
	}

	public void setTerminalSpecific(int terminalSpecific) {
		this.terminalSpecific = terminalSpecific;
	}

	public int getMerchantBasedDiscount() {
		return merchantBasedDiscount;
	}

	public void setMerchantBasedDiscount(int merchantBasedDiscount) {
		this.merchantBasedDiscount = merchantBasedDiscount;
	}

	public String getGlAccount() {
		return glAccount;
	}

	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}

	public String getGlAccount2() {
		return glAccount2;
	}

	public void setGlAccount2(String glAccount2) {
		this.glAccount2 = glAccount2;
	}

	public double getBudgetAmountTotal() {
		return budgetAmountTotal;
	}

	public void setBudgetAmountTotal(double budgetAmountTotal) {
		this.budgetAmountTotal = budgetAmountTotal;
	}

	public double getBudgetAmountAvbl() {
		return budgetAmountAvbl;
	}

	public void setBudgetAmountAvbl(double budgetAmountAvbl) {
		this.budgetAmountAvbl = budgetAmountAvbl;
	}

	public int getDiscAllowedTxtPerDay() {
		return discAllowedTxtPerDay;
	}

	public void setDiscAllowedTxtPerDay(int discAllowedTxtPerDay) {
		this.discAllowedTxtPerDay = discAllowedTxtPerDay;
	}

	public int getDiscAllowedTxtPerWeek() {
		return discAllowedTxtPerWeek;
	}

	public void setDiscAllowedTxtPerWeek(int discAllowedTxtPerWeek) {
		this.discAllowedTxtPerWeek = discAllowedTxtPerWeek;
	}

	public int getDiscAllowedTxtPerYear() {
		return discAllowedTxtPerYear;
	}

	public void setDiscAllowedTxtPerYear(int discAllowedTxtPerYear) {
		this.discAllowedTxtPerYear = discAllowedTxtPerYear;
	}

	public int getDiscAllowedTxtPerMonth() {
		return discAllowedTxtPerMonth;
	}

	public void setDiscAllowedTxtPerMonth(int discAllowedTxtPerMonth) {
		this.discAllowedTxtPerMonth = discAllowedTxtPerMonth;
	}

	public String getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	public Timestamp getEntryOn() {
		return entryOn;
	}

	public void setEntryOn(Timestamp entryOn) {
		this.entryOn = entryOn;
	}

	public String getUdpBy() {
		return udpBy;
	}

	public void setUdpBy(String udpBy) {
		this.udpBy = udpBy;
	}

	public Timestamp getUpdOn() {
		return updOn;
	}

	public void setUpdOn(Timestamp updOn) {
		this.updOn = updOn;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public long getDiscId() {
		return discId;
	}

	public void setDiscId(long discId) {
		this.discId = discId;
	}

	public double getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(double amountFrom) {
		this.amountFrom = amountFrom;
	}

	public double getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(double amountTo) {
		this.amountTo = amountTo;
	}

	public String getDiscType() {
		return discType;
	}

	public void setDiscType(String discType) {
		this.discType = discType;
	}

	public double getDiscValue() {
		return discValue;
	}

	public void setDiscValue(double discValue) {
		this.discValue = discValue;
	}

	public double getMaxDiscountPerTxn() {
		return maxDiscountPerTxn;
	}

	public void setMaxDiscountPerTxn(double maxDiscountPerTxn) {
		this.maxDiscountPerTxn = maxDiscountPerTxn;
	}

	public int getBugdetCapping() {
		return bugdetCapping;
	}

	public void setBugdetCapping(int bugdetCapping) {
		this.bugdetCapping = bugdetCapping;
	}

	public String getDiscCampaignType() {
		return discCampaignType;
	}

	public void setDiscCampaignType(String discCampaignType) {
		this.discCampaignType = discCampaignType;
	}

}
