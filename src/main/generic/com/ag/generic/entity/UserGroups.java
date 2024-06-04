package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the USERS_GROUPS database table.
 * 
 */
@Entity
@Table(name = "USERS_GROUPS")

@NamedQuery(name = "UserGroups.getGroupsByScreen", 	query = "SELECT u FROM UserGroups u where u.isActive = 1 AND u.corpId=:corpId AND u.grpId   in (SELECT ug.grpId FROM UserScreensGroup ug WHERE ug.isActive =1 AND ug.corpId=:corpId AND ug.screenId=:screenId) ")        
@NamedQuery(name = "UserGroups.getUserGroups",			query = "SELECT a FROM UserGroups a WHERE a.grpName=:grpName AND a.corpId=:corpId")
@NamedQuery(name = "UserGroups.validetGroup", query = "SELECT a FROM UserGroups a WHERE a.grpId=:grpId AND a.isActive = 1 AND a.corpId=:corpId")
@NamedQuery(name = "UserGroups.allGroup", query = "SELECT a FROM UserGroups a WHERE  a.isActive = 1 AND a.corpId=:corpId")
@NamedQuery(name = "UserGroups.getMaxGroupCode", query = " SELECT max(grpCode) as grpCode FROM UserGroups")  
@NamedQuery(name = "UserGroups.getNonMerchantUserGroups", query = "SELECT a FROM UserGroups a WHERE a.grpId in :grpId AND a.isActive = 1 AND a.corpId=:corpId")

public class UserGroups implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GROUPS_SEQ")
	@SequenceGenerator(sequenceName = "USER_GROUPS_SEQ", allocationSize = 1, name = "USER_GROUPS_SEQ")
	private int grpId;


	@Column(name = "APPLICATION_SOURCE")
	private String applicationSource;

	@Column(name = "CITY_CODE")
	private String cityCode;

	@Column(name = "DEF_GRP")
	private String defGrp;

	private String description;

	@Column(name = "ENB_FROM")
	private String enbFrom;

	@Column(name = "ENB_TO")
	private String enbTo;

	@Column(name = "GRP_CODE")
	private int grpCode;

	

	@Column(name = "GRP_NAME")
	private String grpName;

	@JsonIgnore
	@Column(name = "INSERT_BY")
	private String insertBy;

	@JsonIgnore
	@Column(name = "INSERT_ON")
	private Timestamp insertOn;

	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	private int isActive;

	@JsonIgnore
	@Column(name = "LAST_UPDATE_BY")
	private String lastUpdateBy;

	
	@JsonIgnore
	@Column(name = "LAST_UPDATE_ON")
	private Timestamp lastUpdateOn;

	@Column(name = "M_IP")
	private String mIp;

	private String machine;

	@Column(name = "UPDATE_RIGHTS")
	private int updateRights;

	@Column(name = "VIEW_RIGHTS")
	private int viewRights;
	@Column(name = "CORP_ID")
	private String corpId;
	
	

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public UserGroups() {
	}

	public String getApplicationSource() {
		return this.applicationSource;
	}

	public void setApplicationSource(String applicationSource) {
		this.applicationSource = applicationSource;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDefGrp() {
		return this.defGrp;
	}

	public void setDefGrp(String defGrp) {
		this.defGrp = defGrp;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEnbFrom() {
		return this.enbFrom;
	}

	public void setEnbFrom(String enbFrom) {
		this.enbFrom = enbFrom;
	}

	public String getEnbTo() {
		return this.enbTo;
	}

	public void setEnbTo(String enbTo) {
		this.enbTo = enbTo;
	}

	public int getGrpCode() {
		return this.grpCode;
	}

	public void setGrpCode(int grpCode) {
		this.grpCode = grpCode;
	}

	public int getGrpId() {
		return this.grpId;
	}

	public void setGrpId(int grpId) {
		this.grpId = grpId;
	}

	public String getGrpName() {
		return this.grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public String getInsertBy() {
		return this.insertBy;
	}

	public void setInsertBy(String insertBy) {
		this.insertBy = insertBy;
	}

	public Timestamp getInsertOn() {
		return this.insertOn;
	}

	public void setInsertOn(Timestamp insertOn) {
		this.insertOn = insertOn;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Timestamp getLastUpdateOn() {
		return this.lastUpdateOn;
	}

	public void setLastUpdateOn(Timestamp lastUpdateOn) {
		this.lastUpdateOn = lastUpdateOn;
	}

	public String getMIp() {
		return this.mIp;
	}

	public void setMIp(String mIp) {
		this.mIp = mIp;
	}

	public String getMachine() {
		return this.machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public int getUpdateRights() {
		return this.updateRights;
	}

	public void setUpdateRights(int updateRights) {
		this.updateRights = updateRights;
	}

	public int getViewRights() {
		return this.viewRights;
	}

	public void setViewRights(int viewRights) {
		this.viewRights = viewRights;
	}

}