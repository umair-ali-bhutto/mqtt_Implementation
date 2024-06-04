package com.ag.mportal.entity;

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

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The persistent class for the DISC_DATE_TIME database table.
 * 
 */
@Entity
@Table(name = "DISC_DATE_TIME")
@NamedQuery(name = "DiscountDateTime.fetchAllByDisc", query = "SELECT d FROM DiscountDateTime d where d.discId=:discId")
public class DiscountDateTime implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISC_DATE_TIME_SEQ")
	@SequenceGenerator(name = "DISC_DATE_TIME_SEQ", sequenceName = "DISC_DATE_TIME_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "DISC_ID")
	private long discId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy  hh:mm a", timezone = "Asia/Karachi")
	@Column(name = "DISC_START_TIME")
	private Timestamp discStartTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy  hh:mm a", timezone = "Asia/Karachi")
	@Column(name = "DISC_END_TIME")
	private Timestamp discEndTime;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDiscId() {
		return discId;
	}

	public void setDiscId(long discId) {
		this.discId = discId;
	}

	public Timestamp getDiscStartTime() {
		return discStartTime;
	}

	public void setDiscStartTime(Timestamp discStartTime) {
		this.discStartTime = discStartTime;
	}

	public Timestamp getDiscEndTime() {
		return discEndTime;
	}

	public void setDiscEndTime(Timestamp discEndTime) {
		this.discEndTime = discEndTime;
	}

}