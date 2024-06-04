package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the ACCOUNT_STATUS_MASTER database table.
 * 
 */
@Embeddable
public class CampaignCalenderProductPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String corpid;

	private String campid;

	private String productId;

	private String calid;

	public CampaignCalenderProductPK() {
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getCampid() {
		return campid;
	}

	public void setCampid(String campid) {
		this.campid = campid;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCalid() {
		return calid;
	}

	public void setCalid(String calid) {
		this.calid = calid;
	}

}
