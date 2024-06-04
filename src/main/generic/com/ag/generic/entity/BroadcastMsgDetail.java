package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.ag.generic.model.BroadCastMessageModel;

/**
 * The persistent class for the BROADCAST_MSG_DETAIL database table.
 * 
 */
@Entity
@Table(name = "BROADCAST_MSG_DETAIL")
@NamedQueries({
		@NamedQuery(name = "BroadCastMessageDetail.searBroadCastMessageDetail", query = "SELECT d FROM BroadcastMsgDetail d order by d.id asc") })
@SqlResultSetMapping(name = "BroadCastMessageModels", classes = {
		@ConstructorResult(targetClass = BroadCastMessageModel.class, columns = {
				@ColumnResult(name = "BROADCAST_MESSAGE_ID", type = String.class),
				@ColumnResult(name = "MESSAGE", type = String.class),
				@ColumnResult(name = "MESSAGE_TITLE", type = String.class),
				@ColumnResult(name = "MESSAGE_DETIAL", type = String.class),
				@ColumnResult(name = "SEND_TO", type = String.class),
				@ColumnResult(name = "IS_SENT", type = String.class),
				@ColumnResult(name = "SEND_DATE", type = String.class),
				@ColumnResult(name = "BROADCAST_DETAIL_ID", type = String.class),
				@ColumnResult(name = "USERLOGIN_ID", type = String.class),
				@ColumnResult(name = "ENTRY_DATE", type = String.class),
				@ColumnResult(name = "BROADCAST_DETAIL_SEND_DATE", type = String.class),
				@ColumnResult(name = "READ_DATE", type = String.class),
				@ColumnResult(name = "MESSAGE_ID", type = String.class) }) })
public class BroadcastMsgDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BROADCAST_MSG_DETAIL_SEQ")
	@SequenceGenerator(name = "BROADCAST_MSG_DETAIL_SEQ", sequenceName = "BROADCAST_MSG_DETAIL_SEQ", allocationSize = 1)
	private int id;

	@Column(name = "MESSAGE_ID")
	private int messageId;

	@Column(name = "READ_DATE")
	private Timestamp readDate;

	@Column(name = "SEND_DATE")
	private Timestamp sendDate;

	@Column(name = "USER_LOGIN_ID")
	private String userLoginId;

	public BroadcastMsgDetail() {
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

	public int getMessageId() {
		return this.messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public Timestamp getReadDate() {
		return this.readDate;
	}

	public void setReadDate(Timestamp readDate) {
		this.readDate = readDate;
	}

	public Timestamp getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}

	public String getUserLoginId() {
		return this.userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

}