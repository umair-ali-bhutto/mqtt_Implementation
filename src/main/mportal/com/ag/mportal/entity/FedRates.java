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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "FED_RATES")
@NamedQueries({
	@NamedQuery(name = "FedRates.retrieveByProvinceName", query = "SELECT a FROM FedRates a WHERE a.province=:provinceName AND a.status = 1"),
	@NamedQuery(name = "FedRates.retrieveAll", query = "SELECT a FROM FedRates a WHERE  a.status = 1")

})
public class FedRates implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FED_RATES_SEQ")
	@SequenceGenerator(name = "FED_RATES_SEQ", sequenceName = "FED_RATES_SEQ", allocationSize = 1)
	private int id;
	
	@Column(name = "PROVINCE")
	private String province;
	
	@Column(name = "PROVINCE_CODE")
	private String provinceCode;
	
	@Column(name = "RATE_VALUE")
	private Float rateValue;
	
	@JsonIgnore
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;
	
	@JsonIgnore
	@Column(name = "STATUS")
	private Integer status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public Float getRateValue() {
		return rateValue;
	}

	public void setRateValue(Float rateValue) {
		this.rateValue = rateValue;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}