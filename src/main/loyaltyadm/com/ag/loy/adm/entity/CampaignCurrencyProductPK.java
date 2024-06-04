package com.ag.loy.adm.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class CampaignCurrencyProductPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private String campid;

	private String currency;

	private String productId;

	private String corpid;

	public String getCampid() {
		return campid;
	}

	public void setCampid(String campid) {
		this.campid = campid;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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
