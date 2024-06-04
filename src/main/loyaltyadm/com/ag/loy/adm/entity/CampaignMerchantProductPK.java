package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CampaignMerchantProductPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private String campid;

	private String mid;

	private String productId;

	private String corpid;

	public String getCampid() {
		return campid;
	}

	public void setCampid(String campid) {
		this.campid = campid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

}
