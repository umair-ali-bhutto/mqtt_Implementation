package com.ag.mportal.model;

public class DiscountBinBasedListModel {
	private String fromBin;
	private String toBin;
	private String productId;
	private String productDescription;
	private String discountType;
	private String discountPercentage;
	private String discountAmount;

	public String getFromBin() {
		return fromBin;
	}

	public void setFromBin(String fromBin) {
		this.fromBin = fromBin;
	}

	public String getToBin() {
		return toBin;
	}

	public void setToBin(String toBin) {
		this.toBin = toBin;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(String discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
