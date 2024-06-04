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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the BANNER_TICKER database table.
 * 
 */
@Entity
@Table(name = "PAYMENT_CONFIGURATION")
@NamedQueries({
		@NamedQuery(name = "Paymentconfiguration.getPaymentconfiguration", query = "SELECT d FROM Paymentconfiguration d where d.isActive=:isActive AND d.corpId=:corpId order by d.dayNumber"),
		@NamedQuery(name = "Paymentconfiguration.getPaymentconfigurationByDay", query = "SELECT d FROM Paymentconfiguration d where d.isActive=:isActive and d.corpId=:corpId and d.dayNumber=:day order by d.dayNumber")})
public class Paymentconfiguration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "DAY_NUMBER")
	private Integer dayNumber;
	@Column(name = "DETAIL")
	private String detail;
	@Column(name = "NUMBER_OFF_DAYS_ADDED")
	private Integer numberOfDaysAdded;

	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	private Integer isActive;
	@Column(name = "CORP_ID")
	private String corpId;
	
	

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getNumberOfDaysAdded() {
		return numberOfDaysAdded;
	}

	public void setNumberOfDaysAdded(Integer numberOfDaysAdded) {
		this.numberOfDaysAdded = numberOfDaysAdded;
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

}