package com.ag.mportal.model;

import java.sql.Timestamp;

public class ViewR1R2MarketSegmebtThresholdModel {

	private long id;
	private String mid;
	private String tid;
	private Timestamp entryDate;
	private String entryBy;
	private Timestamp updateDate;
	private String updatedBy;
	private String r1CreditThreshold;
	private int r1CreditThresholdId;
	private String r1DebitThreshold;
	private int r1DebitThresholdId;
	private String r2CreditThreshold;
	private int r2CreditThresholdId;
	private String r2DebitThreshold;
	private int r2DebitThresholdId;
	private int isActive;
	public long getId() {
		return id;
	}
	public String getMid() {
		return mid;
	}
	public String getTid() {
		return tid;
	}
	public Timestamp getEntryDate() {
		return entryDate;
	}
	public String getEntryBy() {
		return entryBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public String getR1CreditThreshold() {
		return r1CreditThreshold;
	}
	public int getR1CreditThresholdId() {
		return r1CreditThresholdId;
	}
	public String getR1DebitThreshold() {
		return r1DebitThreshold;
	}
	public int getR1DebitThresholdId() {
		return r1DebitThresholdId;
	}
	public String getR2CreditThreshold() {
		return r2CreditThreshold;
	}
	public int getR2CreditThresholdId() {
		return r2CreditThresholdId;
	}
	public String getR2DebitThreshold() {
		return r2DebitThreshold;
	}
	public int getR2DebitThresholdId() {
		return r2DebitThresholdId;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}
	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setR1CreditThreshold(String r1CreditThreshold) {
		this.r1CreditThreshold = r1CreditThreshold;
	}
	public void setR1CreditThresholdId(int r1CreditThresholdId) {
		this.r1CreditThresholdId = r1CreditThresholdId;
	}
	public void setR1DebitThreshold(String r1DebitThreshold) {
		this.r1DebitThreshold = r1DebitThreshold;
	}
	public void setR1DebitThresholdId(int r1DebitThresholdId) {
		this.r1DebitThresholdId = r1DebitThresholdId;
	}
	public void setR2CreditThreshold(String r2CreditThreshold) {
		this.r2CreditThreshold = r2CreditThreshold;
	}
	public void setR2CreditThresholdId(int r2CreditThresholdId) {
		this.r2CreditThresholdId = r2CreditThresholdId;
	}
	public void setR2DebitThreshold(String r2DebitThreshold) {
		this.r2DebitThreshold = r2DebitThreshold;
	}
	public void setR2DebitThresholdId(int r2DebitThresholdId) {
		this.r2DebitThresholdId = r2DebitThresholdId;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

}
