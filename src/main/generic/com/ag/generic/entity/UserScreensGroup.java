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
 * The persistent class for the USER_SCREENS_GROUPS database table.
 * 
 */
@Entity
@Table(name = "USER_SCREENS_GROUPS")
@NamedQuery(name = "UserScreensGroup.findAll", query = "SELECT u FROM UserScreensGroup u where u.corpId=:corpId")
@NamedQuery(name = "UserScreensGroup.showScreenGroup", query = "SELECT a FROM UserScreensGroup a WHERE a.grpId=:grpId AND a.corpId=:corpId AND a.isActive = 1")
@NamedQuery(name = "UserScreensGroup.addScreenGroup", query = "SELECT a FROM UserScreensGroup a WHERE a.grpId=:grpId AND a.corpId=:corpId AND a.isActive = 1")
@NamedQuery(name = "UserScreensGroup.viewScreenGroupByScreen", query = "SELECT a FROM UserScreensGroup a WHERE a.corpId=:corpId AND a.screenId=:screenId AND a.isActive = 1")

@NamedQuery(name = "UserScreensGroup.deleteRecord", query = "DELETE FROM UserScreensGroup   WHERE grpId=:grpId AND corpId=:corpId ")
public class UserScreensGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CITY_CODE")
	private String cityCode;

	@Column(name = "GRP_ID")
	private int grpId;

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

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SCREENS_GROUPS_SEQ")
	@SequenceGenerator(sequenceName = "USER_SCREENS_GROUPS_SEQ", allocationSize = 1, name = "USER_SCREENS_GROUPS_SEQ")
	private int permId;

	@Column(name = "SCREEN_ID")
	private int screenId;
	@Column(name = "CORP_ID")
	private String corpId;

	public UserScreensGroup() {
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getGrpId() {
		return this.grpId;
	}

	public void setGrpId(int grpId) {
		this.grpId = grpId;
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

	public int getPermId() {
		return this.permId;
	}

	public void setPermId(int permId) {
		this.permId = permId;
	}

	public int getScreenId() {
		return this.screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

}