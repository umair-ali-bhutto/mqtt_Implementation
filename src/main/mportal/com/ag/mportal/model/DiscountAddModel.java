package com.ag.mportal.model;

import java.util.List;

import com.ag.generic.model.KeyValueModel;

public class DiscountAddModel {

	private long discId;
	private String discName;
	private String discDescription;
	private String discType;
	private String discount;
	private String startDate;
	private String endDate;
	private String maxDiscAmountPerTxn;
	private boolean isMerchantLogin;

	private boolean isBudgetCapping;
	private String budgetoryAmount;

	private boolean isDiscAmountLimit;
	private String minimumAmount;
	private String maximumAmount;

	private boolean isMerchantBased;
	private String merchantName;
	private List<KeyValueModel> mid;

	private boolean isTerminalBased;
	private List<String> tid;

	private String[] discCardBasedRestrictions;
	private String discTxnPerDay;
	private String discTxnPerWeek;
	private String discTxnPerMonth;
	private String discTxnPerYear;

	private boolean isBinBased;
	private List<DiscountBinBasedListModel> binBasedList;

	private boolean isDateBased;
	private List<DiscountDateBasedListModel> dateBasedList;

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

	public String getDiscType() {
		return discType;
	}

	public void setDiscType(String discType) {
		this.discType = discType;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMaxDiscAmountPerTxn() {
		return maxDiscAmountPerTxn;
	}

	public void setMaxDiscAmountPerTxn(String maxDiscAmountPerTxn) {
		this.maxDiscAmountPerTxn = maxDiscAmountPerTxn;
	}

	public boolean isBudgetCapping() {
		return isBudgetCapping;
	}

	public void setBudgetCapping(boolean isBudgetCapping) {
		this.isBudgetCapping = isBudgetCapping;
	}

	public String getBudgetoryAmount() {
		return budgetoryAmount;
	}

	public void setBudgetoryAmount(String budgetoryAmount) {
		this.budgetoryAmount = budgetoryAmount;
	}

	public boolean isDiscAmountLimit() {
		return isDiscAmountLimit;
	}

	public void setDiscAmountLimit(boolean isDiscAmountLimit) {
		this.isDiscAmountLimit = isDiscAmountLimit;
	}

	public String getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(String minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public String getMaximumAmount() {
		return maximumAmount;
	}

	public void setMaximumAmount(String maximumAmount) {
		this.maximumAmount = maximumAmount;
	}

	public boolean isMerchantBased() {
		return isMerchantBased;
	}

	public void setMerchantBased(boolean isMerchantBased) {
		this.isMerchantBased = isMerchantBased;
	}

	public List<KeyValueModel> getMid() {
		return mid;
	}

	public void setMid(List<KeyValueModel> mid) {
		this.mid = mid;
	}

	public List<String> getTid() {
		return tid;
	}

	public void setTid(List<String> tid) {
		this.tid = tid;
	}

	public String[] getDiscCardBasedRestrictions() {
		return discCardBasedRestrictions;
	}

	public void setDiscCardBasedRestrictions(String[] discCardBasedRestrictions) {
		this.discCardBasedRestrictions = discCardBasedRestrictions;
	}

	public String getDiscTxnPerDay() {
		return discTxnPerDay;
	}

	public void setDiscTxnPerDay(String discTxnPerDay) {
		this.discTxnPerDay = discTxnPerDay;
	}

	public String getDiscTxnPerWeek() {
		return discTxnPerWeek;
	}

	public void setDiscTxnPerWeek(String discTxnPerWeek) {
		this.discTxnPerWeek = discTxnPerWeek;
	}

	public String getDiscTxnPerMonth() {
		return discTxnPerMonth;
	}

	public void setDiscTxnPerMonth(String discTxnPerMonth) {
		this.discTxnPerMonth = discTxnPerMonth;
	}

	public String getDiscTxnPerYear() {
		return discTxnPerYear;
	}

	public void setDiscTxnPerYear(String discTxnPerYear) {
		this.discTxnPerYear = discTxnPerYear;
	}

	public boolean isBinBased() {
		return isBinBased;
	}

	public void setBinBased(boolean isBinBased) {
		this.isBinBased = isBinBased;
	}

	public List<DiscountBinBasedListModel> getBinBasedList() {
		return binBasedList;
	}

	public void setBinBasedList(List<DiscountBinBasedListModel> binBasedList) {
		this.binBasedList = binBasedList;
	}

	public boolean isDateBased() {
		return isDateBased;
	}

	public void setDateBased(boolean isDateBased) {
		this.isDateBased = isDateBased;
	}

	public List<DiscountDateBasedListModel> getDateBasedList() {
		return dateBasedList;
	}

	public void setDateBasedList(List<DiscountDateBasedListModel> dateBasedList) {
		this.dateBasedList = dateBasedList;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public boolean isTerminalBased() {
		return isTerminalBased;
	}

	public void setTerminalBased(boolean isTerminalBased) {
		this.isTerminalBased = isTerminalBased;
	}

	public boolean isMerchantLogin() {
		return isMerchantLogin;
	}

	public void setMerchantLogin(boolean isMerchantLogin) {
		this.isMerchantLogin = isMerchantLogin;
	}

	public long getDiscId() {
		return discId;
	}

	public void setDiscId(long discId) {
		this.discId = discId;
	}

}
