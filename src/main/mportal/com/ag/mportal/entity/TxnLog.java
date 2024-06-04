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

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The persistent class for the TXN_LOGS database table.
 * 
 */
@Entity
@Table(name = "TXN_LOGS")
public class TxnLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TXN_LOGS_SEQ")
	@SequenceGenerator(name = "TXN_LOGS_SEQ", sequenceName = "TXN_LOGS_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "ACTIVITY_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Karachi")
	private Timestamp activityDate;

	@Column(name = "ACTIVITY_REMARKS")
	private String activityRemarks;

	@Column(name = "ACTIVITY_TYPE")
	private String activityType;

	private String fieldfive;

	private String fieldfour;

	private String fieldone;

	private String fieldsix;

	private String fieldthree;

	private String fieldtwo;

	private String imei;

	private String latitude;

	private String longitude;

	@Column(name = "MERCHANT_ID")
	private String merchantId;

	private String model;

	private String posdatetime;

	private String scheme;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	private String telco;

	@Column(name = "TERMINAL_ID")
	private String terminalId;

	// ADDED BY UMAIR.ALI
	@Column(name = "SIGNAL_STRENGTH")
	private String signalStrength;

	@Column(name = "FIRMWARE")
	private String firmware;

	@Column(name = "CPU_MEMORY")
	private String cpuMemory;

	@Column(name = "BATTERY")
	private String battery;

	@Column(name = "OS")
	private String os;

	@Column(name = "APP_VERSION")
	private String appVersion;

	public TxnLog() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getActivityDate() {
		return this.activityDate;
	}

	public void setActivityDate(Timestamp activityDate) {
		this.activityDate = activityDate;
	}

	public String getActivityRemarks() {
		return this.activityRemarks;
	}

	public void setActivityRemarks(String activityRemarks) {
		this.activityRemarks = activityRemarks;
	}

	public String getActivityType() {
		return this.activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getFieldfive() {
		return this.fieldfive;
	}

	public void setFieldfive(String fieldfive) {
		this.fieldfive = fieldfive;
	}

	public String getFieldfour() {
		return this.fieldfour;
	}

	public void setFieldfour(String fieldfour) {
		this.fieldfour = fieldfour;
	}

	public String getFieldone() {
		return this.fieldone;
	}

	public void setFieldone(String fieldone) {
		this.fieldone = fieldone;
	}

	public String getFieldsix() {
		return this.fieldsix;
	}

	public void setFieldsix(String fieldsix) {
		this.fieldsix = fieldsix;
	}

	public String getFieldthree() {
		return this.fieldthree;
	}

	public void setFieldthree(String fieldthree) {
		this.fieldthree = fieldthree;
	}

	public String getFieldtwo() {
		return this.fieldtwo;
	}

	public void setFieldtwo(String fieldtwo) {
		this.fieldtwo = fieldtwo;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPosdatetime() {
		return this.posdatetime;
	}

	public void setPosdatetime(String posdatetime) {
		this.posdatetime = posdatetime;
	}

	public String getScheme() {
		return this.scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTelco() {
		return this.telco;
	}

	public void setTelco(String telco) {
		this.telco = telco;
	}

	public String getTerminalId() {
		return this.terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}

	public String getFirmware() {
		return firmware;
	}

	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}

	public String getCpuMemory() {
		return cpuMemory;
	}

	public void setCpuMemory(String cpuMemory) {
		this.cpuMemory = cpuMemory;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

}