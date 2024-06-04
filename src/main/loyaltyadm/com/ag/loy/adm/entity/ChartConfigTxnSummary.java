package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the CHART_CONFIG_TXN_SUMMARY database table.
 * 
 */
@Entity
@Table(name = "CHART_CONFIG_TXN_SUMMARY")
@NamedQuery(name = "ChartConfigTxnSummary.fetchAllByCorpId", query = "SELECT a FROM ChartConfigTxnSummary a WHERE a.corpId =:corpId and a.isActive = 1 order by a.id")
public class ChartConfigTxnSummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "CORP_ID")
	private String corpId;

	@Column(name = "TXN_TYPE")
	private String txnType;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "THEME")
	private String theme;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
