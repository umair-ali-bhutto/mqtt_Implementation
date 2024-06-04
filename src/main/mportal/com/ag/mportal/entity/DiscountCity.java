package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the BATCH_SETTLEMENT database table.
 * 
 */
@Entity
@Table(name = "DISC_CITY")
public class DiscountCity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISC_CITY_SEQ")
	@SequenceGenerator(name = "DISC_CITY_SEQ", sequenceName = "DISC_CITY_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "CITY_CODE")
	private String cityCode;

	@Column(name = "CITY_DESC")
	private String cityDesc;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityDesc() {
		return cityDesc;
	}

	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
	}

}