package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "DOCUMENT_CONFIG")
@NamedQuery(name = "DocumentConfig.fetchAll", query = "SELECT d FROM DocumentConfig d where d.isActive = 1")
@NamedQuery(name = "DocumentConfig.fetchAllByGroupCode", query = "SELECT d FROM DocumentConfig d where d.isActive = 1 AND d.corpId=:corpId AND d.userGroup=:userGroup ORDER BY d.id")
public class DocumentConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "FILE_PATH")
	private String filePath;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "LEGEND_NAME")
	private String legendName;

	@Column(name = "CORP_ID")
	private String corpId;

	@Column(name = "USER_GROUP")
	private int userGroup;

	@Column(name = "PARENT_Id")
	private int parentId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getLegendName() {
		return legendName;
	}

	public void setLegendName(String legendName) {
		this.legendName = legendName;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public int getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(int userGroup) {
		this.userGroup = userGroup;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

}
