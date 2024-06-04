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
 * The persistent class for the PLAYERS_IDS_PUSH_NOTIFICATIONS database table.
 * 
 */
@Entity
@Table(name = "PLAYERS_IDS_PUSH_NOTIFICATIONS")
@NamedQueries({
		@NamedQuery(name = "PlayersIdsPushNotification.searchAll", query = "SELECT d FROM PlayersIdsPushNotification d WHERE d.playerId not like '00' order by d.id asc"),
		@NamedQuery(name = "PlayersIdsPushNotification.searchByUserLoginId", query = "SELECT d FROM PlayersIdsPushNotification d WHERE d.userLoginId=:userLoginId ") })
public class PlayersIdsPushNotification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAYERS_IDS_NOTIFICATION_SEQ")
	@SequenceGenerator(name = "PLAYERS_IDS_NOTIFICATION_SEQ", sequenceName = "PLAYERS_IDS_NOTIFICATION_SEQ", allocationSize = 1)
	private int id;

	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "PLAYER_ID")
	private String playerId;

	@JsonIgnore
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "USER_LOGIN_ID")
	private int userLoginId;

	@Column(name = "CHANNEL")
	private String channel;

	public PlayersIdsPushNotification() {
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public Timestamp getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getUserLoginId() {
		return this.userLoginId;
	}

	public void setUserLoginId(int userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}