package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The persistent class for the DISCOUNT_MASTER database table.
 * 
 */
@Entity
@Table(name = "DISC_MASTER")
@NamedQuery(name = "DiscountMaster.fetchById", query = "SELECT a FROM DiscountMaster a WHERE a.id=:id")
@NamedQuery(name = "DiscountMaster.fetchAll", query = "SELECT a.id,a.discName,b.id FROM DiscountMaster a,DiscountSlab b where a.id = b.discId")
public class DiscountMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISC_MASTER_SEQ")
	@SequenceGenerator(name = "DISC_MASTER_SEQ", sequenceName = "DISC_MASTER_SEQ", allocationSize = 1)
	private long id;

//	@OneToOne
//	@JoinColumn(name = "id")
//	private DiscountSlab discountSlab;

//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "DISC_ID", referencedColumnName = "id", nullable = false)
//	private DiscountSlab discountSlab;

	@Column(name = "DISC_NAME")
	private String discName;

	@Column(name = "DISC_DESCRIPTION")
	private String discDescription;

	@Column(name = "DISC_STATUS")
	private String discStatus;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Karachi")
	@Column(name = "START_DATE")
	private Timestamp startDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Karachi")
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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Karachi")
	@Column(name = "ENTRY_ON")
	private Timestamp entryOn;

	@Column(name = "UPD_BY")
	private String updBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Karachi")
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDiscDescription() {
		return discDescription;
	}

	public void setDiscDescription(String discDescription) {
		this.discDescription = discDescription;
	}

	public String getDiscStatus() {
		return discStatus;
	}

	public void setDiscStatus(String discStatus) {
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

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getDiscName() {
		return discName;
	}

	public void setDiscName(String discName) {
		this.discName = discName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDiscCampaignType() {
		return discCampaignType;
	}

	public void setDiscCampaignType(String discCampaignType) {
		this.discCampaignType = discCampaignType;
	}

	public int getBugdetCapping() {
		return bugdetCapping;
	}

	public void setBugdetCapping(int bugdetCapping) {
		this.bugdetCapping = bugdetCapping;
	}

}