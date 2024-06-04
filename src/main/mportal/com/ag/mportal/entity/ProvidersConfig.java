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

/**
 * The persistent class for the DONATION_CONFIG database table.
 * 
 */
@Entity
@Table(name = "Providers_CONFIG")
@NamedQueries({
		@NamedQuery(name = "ProvidersConfig.fetchAllRecordById", query = "SELECT a FROM ProvidersConfig a  where a.id=:id"),
		@NamedQuery(name = "ProvidersConfig.fetchAllRecordByMidTidType", query = "SELECT a FROM ProvidersConfig a where a.isActive = 1 and a.mid=:mid and a.tid=:tid and a.serialNumber=:serialNumber and a.typeCode=:type order by a.sortBy desc"),
		@NamedQuery(name = "ProvidersConfig.fetchAllRecord", query = "SELECT a FROM ProvidersConfig a where a.isActive=1")
		})

public class ProvidersConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DONATION_ID_SEQ")
	@SequenceGenerator(name = "DONATION_ID_SEQ", sequenceName = "DONATION_ID_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "MID")
	private String mid;

	@Column(name = "TID")
	private String tid;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "TYPE_CODE")
	private String typeCode;

	@Column(name = "TYPE_DESC")
	private String typeDescription;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "ACCOUNT_TITLE")
	private String accountTitle;

	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "MERCHANT_NAME")
	private String merchantName;

	@Column(name = "MERCHANT_ADD1")
	private String merchantAdd1;

	@Column(name = "MERCHANT_ADD2")
	private String merchantAdd2;

	@Column(name = "MERCHANT_ADD3")
	private String merchantAdd3;

	@Column(name = "MERCHANT_ADD4")
	private String merchantAdd4;

	@JsonIgnore
	@Column(name = "IS_FIXED_TID")
	private int isFixedTid;

	@JsonIgnore
	@Column(name = "SORT_BY")
	private int sortBy;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	@Column(name = "MIN_AMOUNT")
	private float minAmount;

	@Column(name = "MAX_AMOUNT")
	private float maxAmount;

	@Column(name = "IMG")
	private byte[] img;

	@Column(name = "TXN_MID")
	private String txnMid;

	@Column(name = "TXN_TID")
	private String txnTid;

	public ProvidersConfig() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsFixedTid() {
		return isFixedTid;
	}

	public void setIsFixedTid(int isFixedTid) {
		this.isFixedTid = isFixedTid;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public float getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(float minAmount) {
		this.minAmount = minAmount;
	}

	public float getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(float maxAmount) {
		this.maxAmount = maxAmount;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTxnMid() {
		return txnMid;
	}

	public void setTxnMid(String txnMid) {
		this.txnMid = txnMid;
	}

	public String getTxnTid() {
		return txnTid;
	}

	public void setTxnTid(String txnTid) {
		this.txnTid = txnTid;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public int getSortBy() {
		return sortBy;
	}

	public void setSortBy(int sortBy) {
		this.sortBy = sortBy;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantAdd1() {
		return merchantAdd1;
	}

	public void setMerchantAdd1(String merchantAdd1) {
		this.merchantAdd1 = merchantAdd1;
	}

	public String getMerchantAdd2() {
		return merchantAdd2;
	}

	public void setMerchantAdd2(String merchantAdd2) {
		this.merchantAdd2 = merchantAdd2;
	}

	public String getMerchantAdd3() {
		return merchantAdd3;
	}

	public void setMerchantAdd3(String merchantAdd3) {
		this.merchantAdd3 = merchantAdd3;
	}

	public String getMerchantAdd4() {
		return merchantAdd4;
	}

	public void setMerchantAdd4(String merchantAdd4) {
		this.merchantAdd4 = merchantAdd4;
	}

}