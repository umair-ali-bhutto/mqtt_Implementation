package com.ag.mportal.entity;

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

/**
 * The persistent class for the QUEUE_LOG database table.
 * 
 */
@Entity
@Table(name = "QUEUE_LOG")
@NamedQuery(name = "BannerTicker.fetchQueueLogDetails", query = "SELECT q FROM QueueLog q WHERE q.mid=:mid AND q.status=:status ")
public class QueueLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "QUEUE_LOG_SEQ")
	@SequenceGenerator(name="QUEUE_LOG_SEQ",sequenceName="QUEUE_LOG_SEQ", allocationSize=1)
	private long id;

	private String email;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "MAX_HITS")
	private int maxHits;

	private String mid;

	private String model;

	private String msdisn;

	private String remarks;

	@Column(name = "SERIAL_NUM")
	private String serialNum;

	private String status;

	@Column(name = "STATUS_MSG")
	private String statusMsg;

	private String tid;

	public QueueLog() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public int getMaxHits() {
		return this.maxHits;
	}

	public void setMaxHits(int maxHits) {
		this.maxHits = maxHits;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMsdisn() {
		return this.msdisn;
	}

	public void setMsdisn(String msdisn) {
		this.msdisn = msdisn;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSerialNum() {
		return this.serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMsg() {
		return this.statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

}