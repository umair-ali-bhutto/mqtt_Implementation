package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the BANNER_TICKER database table.
 * 
 */
@Entity
@Table(name = "BANNER_TICKER")
@NamedQueries({ @NamedQuery(name = "BannerTicker.getBannerTickerByCorpID", query = "SELECT d FROM BannerTicker d where d.type=:type and d.isActive=1 and d.corpId=:corpId") })
public class BannerTicker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BANNER_TICKER_SEQ")
	@SequenceGenerator(name = "BANNER_TICKER_SEQ", sequenceName = "BANNER_TICKER_SEQ", allocationSize = 1)
	private long id;

	@JsonIgnore
	@Column(name = "ENTRY_BY")
	private String entryBy;

	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "FILE_PATH")
	private String filePath;

	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "TITLE_DESCRIPTION")
	private String titleDescription;

	@Column(name = "TITLE_TEXT")
	private String titleText;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "MID")
	private String mid;
	
	@Column(name = "TID")
	private String tid;
	
	@Column(name = "CORP_ID")
	private String corpId;
	
	@Column(name = "GROUP_ID")
	private String groupId;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "LOYALTY_CORP_ID")
	private String loyaltyCorpId;

	private String url;

	public BannerTicker() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEntryBy() {
		return this.entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getTitleDescription() {
		return this.titleDescription;
	}

	public void setTitleDescription(String titleDescription) {
		this.titleDescription = titleDescription;
	}

	public String getTitleText() {
		return this.titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoyaltyCorpId() {
		return loyaltyCorpId;
	}

	public void setLoyaltyCorpId(String loyaltyCorpId) {
		this.loyaltyCorpId = loyaltyCorpId;
	}

}