package com.ag.mportal.model;

public class DiscountDecisionModel {

	private long discountId;
	private String discountType;
	private double discountValue;
	private String discountCategory;
	private String discountAmount;

	public DiscountDecisionModel(long discountId, String discountType, double discountValue, String discountCategory,
			String discountAmount) {
		this.discountId = discountId;
		this.discountType = discountType;
		this.discountValue = discountValue;
		this.discountCategory = discountCategory;
		this.discountAmount = discountAmount;
	}

	public long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(long discountId) {
		this.discountId = discountId;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public String getDiscountCategory() {
		return discountCategory;
	}

	public void setDiscountCategory(String discountCategory) {
		this.discountCategory = discountCategory;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

}
