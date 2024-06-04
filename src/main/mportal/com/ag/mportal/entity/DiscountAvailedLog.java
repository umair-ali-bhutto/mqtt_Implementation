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

/**
 * The persistent class for the TXN_DETAILS database table.
 * 
 */
@Entity
@Table(name = "DISCOUNT_AVAILED_LOG")
@NamedQueries({ @NamedQuery(name = "DiscountAvailedLog.getById", query = "SELECT dl FROM DiscountAvailedLog dl where dl.id = :id") })
public class DiscountAvailedLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISCOUNT_AVL_LOG")
	@SequenceGenerator(name = "DISCOUNT_AVL_LOG", sequenceName = "DISCOUNT_AVL_LOG", allocationSize = 1)
	private int id;

	@Column(name = "MID")
	private String mId;

	@Column(name = "TID")
	private String tId;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	@Column(name = "MODEL")
	private String model;

	@Column(name = "CARD_SCHEME")
	private String cardScheme;

	@Column(name = "BIN")
	private String bin;

	@Column(name = "TXN_DATE")
	private String txnDate;

	@Column(name = "TXN_TIME")
	private String txnTime;

	@Column(name = "AMOUNT")
	private String amount;

	@Column(name = "FIELD_ONE")
	private String fieldOne;

	@Column(name = "TXN_DATE_TIME")
	private Timestamp txnDateTime;

	@Column(name = "IS_DISCOUNT_AVAILED")
	private Integer isDiscountAvailed;

	@Column(name = "DISCOUNT_AVAILED_DATE")
	private Timestamp discountAvailedDate;

	@Column(name = "REF_NUM")
	private String refNum;
	
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;
	
	@Column(name = "RRN")
	private String rrn;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "DISCOUNT_LOG_ID")
	private int discountLogId;
	
	
	@Column(name = "DISCOUNT_AMOUNT")
	private String discountAmnt;
	
	@Column(name = "DISCOUNT_PERC")
	private String discountPerc;
	
	@Column(name = "DISCOUNT_NAME")
	private String discountName;
	
	@Column(name = "NET_AMOUNT")
	private String netAmount;
	

	public DiscountAvailedLog() {

	}

	public DiscountAvailedLog(String mId, String tId, String serialNumber, String model, String cardScheme, String bin,
			String amount, String fieldOne, Integer isDiscountAvailed, Timestamp txnDateTime, String refNum,
			String txnDate, String txnTime,Timestamp discountAvailedDate,Timestamp entryDate) {
		this.mId = mId;
		this.tId = tId;
		this.serialNumber = serialNumber;
		this.model = model;
		this.cardScheme = cardScheme;
		this.bin = bin;
		this.amount = amount;
		this.fieldOne = fieldOne;
		this.isDiscountAvailed = isDiscountAvailed;
		this.txnDateTime = txnDateTime;
		this.refNum = refNum;
		this.txnDate = txnDate;
		this.txnTime = txnTime;
		this.discountAvailedDate = discountAvailedDate;
		this.entryDate = entryDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCardScheme() {
		return cardScheme;
	}

	public void setCardScheme(String cardScheme) {
		this.cardScheme = cardScheme;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFieldOne() {
		return fieldOne;
	}

	public void setFieldOne(String fieldOne) {
		this.fieldOne = fieldOne;
	}

	public Timestamp getTxnDateTime() {
		return txnDateTime;
	}

	public void setTxnDateTime(Timestamp txnDateTime) {
		this.txnDateTime = txnDateTime;
	}

	public Integer getIsDiscountAvailed() {
		return isDiscountAvailed;
	}

	public void setIsDiscountAvailed(Integer isDiscountAvailed) {
		this.isDiscountAvailed = isDiscountAvailed;
	}

	public Timestamp getDiscountAvailedDate() {
		return discountAvailedDate;
	}

	public void setDiscountAvailedDate(Timestamp discountAvailedDate) {
		this.discountAvailedDate = discountAvailedDate;
	}

	public String getRefNum() {
		return refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDiscountLogId() {
		return discountLogId;
	}

	public void setDiscountLogId(int discountLogId) {
		this.discountLogId = discountLogId;
	}

	public String getDiscountAmnt() {
		return discountAmnt;
	}

	public void setDiscountAmnt(String discountAmnt) {
		this.discountAmnt = discountAmnt;
	}

	public String getDiscountPerc() {
		return discountPerc;
	}

	public void setDiscountPerc(String discountPerc) {
		this.discountPerc = discountPerc;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	

}