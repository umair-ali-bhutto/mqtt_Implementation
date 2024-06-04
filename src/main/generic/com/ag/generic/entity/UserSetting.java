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
 * The persistent class for the USER_SETTING database table.
 * 
 */
@Entity
@Table(name = "USER_SETTING")
@NamedQueries({ @NamedQuery(name = "UserSetting.fetchAllUserSetting", query = "SELECT d FROM UserSetting d"),
		@NamedQuery(name = "UserSetting.fetchSettingById", query = "SELECT d FROM UserSetting d where d.id=:id"),
		@NamedQuery(name = "UserSetting.fetchSettingByIdName", query = "SELECT d FROM UserSetting d WHERE d.userLoginId =:id AND d.propName=:name AND d.isActive=1"),
		@NamedQuery(name = "UserSetting.fetchSettingByUserLoginId", query = "SELECT d FROM UserSetting d WHERE d.userLoginId =:id AND isActive='1'"),
		@NamedQuery(name = "UserSetting.fetchSettingByLstUserLoginId", query = "SELECT d FROM UserSetting d WHERE d.userLoginId in (:userIds)")})

public class UserSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "USER_SETTING_SEQ")
	@SequenceGenerator(name="USER_SETTING_SEQ",sequenceName="USER_SETTING_SEQ", allocationSize=1)
	private long id;

	@Column(name = "PROP_NAME")
	private String propName;

	@Column(name = "PROP_VALUE")
	private String propValue;

	@Column(name = "USER_LOGIN_ID")
	private int userLoginId;
	
	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;
	
	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	private Integer isActive;
	
	@JsonIgnore
	@Column(name = "ENTRY_BY")
	private String entryBy;

	public UserSetting() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPropName() {
		return this.propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropValue() {
		return this.propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	public int getUserLoginId() {
		return this.userLoginId;
	}

	public void setUserLoginId(int userLoginId) {
		this.userLoginId = userLoginId;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}
	

}