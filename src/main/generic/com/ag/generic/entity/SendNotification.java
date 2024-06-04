package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the SEND_NOTIFICATION database table.
 * 
 */
@Entity
@Table(name = "SEND_NOTIFICATION")
//@NamedQuery(name = "SendNotification.findAll", query = "SELECT s FROM SendNotification s")
public class SendNotification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String channel;

	@Column(name = "DROP_DATE")
	private Timestamp dropDate;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "EMAIL_SUBJECT")
	private String emailSubject;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "IMAGE_FILE_PATH")
	private String imageFilePath;

	@Column(name = "IS_SENT")
	private int isSent;

	private String mid;

	private String msdisn;

	private int priority;

	private String tid;

	@Column(name = "VALUE")
	private String value;

	public SendNotification() {
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

	public Timestamp getDropDate() {
		return this.dropDate;
	}

	public void setDropDate(Timestamp dropDate) {
		this.dropDate = dropDate;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailSubject() {
		return this.emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getImageFilePath() {
		return this.imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public int getIsSent() {
		return this.isSent;
	}

	public void setIsSent(int isSent) {
		this.isSent = isSent;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMsdisn() {
		return this.msdisn;
	}

	public void setMsdisn(String msdisn) {
		this.msdisn = msdisn;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}