package com.ag.generic.entity;

import java.io.Serializable;

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
 * The persistent class for the USER_CHANNELS database table.
 * 
 */
@Entity
@Table(name = "USER_CHANNELS")
@NamedQueries({ @NamedQuery(name = "UserChannels.fetchByID", query = "SELECT u FROM UserChannel u WHERE u.id=:id"),
		@NamedQuery(name = "UserChannels.fetchByOtherParams", query = "SELECT u FROM UserChannel u WHERE u.id=:id"),
		@NamedQuery(name = "UserChannels.fetchAll", query = "SELECT u FROM UserChannel u "),
		@NamedQuery(name = "UserChannels.fetchAllByID", query = "SELECT u FROM UserChannel u WHERE u.userLoginId=:userLoginId") })
public class UserChannel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_CHANNELS_SEQ")
	@SequenceGenerator(name = "USER_CHANNELS_SEQ", sequenceName = "USER_CHANNELS_SEQ", allocationSize = 1)
	private long id;

	private String channel;

	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "USER_LOGIN_ID")
	private int userLoginId;

	@Column(name = "VALUE")
	private String value;

	public UserChannel() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public int getUserLoginId() {
		return this.userLoginId;
	}

	public void setUserLoginId(int userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}