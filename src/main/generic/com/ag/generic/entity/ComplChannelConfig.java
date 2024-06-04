package com.ag.generic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the COMPL_CHANNEL_CONFIG database table.
 * 
 */
@Entity
@Table(name = "COMPL_CHANNEL_CONFIG")
@NamedQueries({ @NamedQuery(name = "ComplChannelConfig.fetchAll", query = "SELECT d FROM  ComplChannelConfig d"),
		@NamedQuery(name = "ComplChannelConfig.fetchAllByOtherParams", query = "SELECT d FROM  ComplChannelConfig d where d.category=:category AND d.type=:type AND d.subType=:subType AND d.corpId=:corpId "),
		@NamedQuery(name = "ComplChannelConfig.fetchAllByID", query = "SELECT d FROM  ComplChannelConfig d where d.id=:id")

})
public class ComplChannelConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "CATEGORY")
	private String category;

	private String channel;

	@Column(name = "IS_IMAGE_ACTIVE")
	private int isImageActive;

	@Column(name = "IS_TEXT_ACTIVE")
	private int isTextActive;

	@Column(name = "SUB_TYPE")
	private String subType;

	private String subject;

	private String text;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "CORP_ID")
	private String corpId;

	public ComplChannelConfig() {
	}

	public ComplChannelConfig(long id, String channel) {
		super();
		this.id = id;
		this.channel = channel;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getIsImageActive() {
		return this.isImageActive;
	}

	public void setIsImageActive(int isImageActive) {
		this.isImageActive = isImageActive;
	}

	public int getIsTextActive() {
		return this.isTextActive;
	}

	public void setIsTextActive(int isTextActive) {
		this.isTextActive = isTextActive;
	}

	public String getSubType() {
		return this.subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

}