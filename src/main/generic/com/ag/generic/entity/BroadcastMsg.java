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

/**
 * The persistent class for the BROADCAST_MSG database table.
 * 
 */
@Entity
@Table(name = "BROADCAST_MSG")
@NamedQueries({
		@NamedQuery(name = "BroadcastMsg.searchBroadCastMessage", query = "SELECT d FROM BroadcastMsg d where d.isSent=:isSent order by d.id asc") })
public class BroadcastMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BROADCAST_MSG_SEQ")
	@SequenceGenerator(name = "BROADCAST_MSG_SEQ", sequenceName = "BROADCAST_MSG_SEQ", allocationSize = 1)
	private int id;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "IS_SENT")
	private String isSent;

	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "MESSAGE_DETAIL")
	private String messageDetail;

	@Column(name = "MESSAGE_TITLE")
	private String messageTitle;

	private String msg;

	@Column(name = "SEND_DATE")
	private Timestamp sendDate;

	@Column(name = "SENT_TO")
	private String sentTo;

	@Column(name = "MSG_TYPE")
	private String msgType;

	@Column(name = "CONTENT")
	private String content;

	public BroadcastMsg() {
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

	public String getIsSent() {
		return this.isSent;
	}

	public void setIsSent(String isSent) {
		this.isSent = isSent;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageDetail() {
		return this.messageDetail;
	}

	public void setMessageDetail(String messageDetail) {
		this.messageDetail = messageDetail;
	}

	public String getMessageTitle() {
		return this.messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Timestamp getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}

	public String getSentTo() {
		return this.sentTo;
	}

	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}