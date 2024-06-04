package com.ag.mportal.model;

import java.sql.Timestamp;

public class ViewOfflineSaleModel {

	private long id;
	private String mid;
	private String tid;
	private Timestamp entryDate;
	private String entryBy;
	private Timestamp updateDate;
	private String updatedBy;
	private String minAmountThreshold;
	private int minAmountThresholdId;
	private String maxAmountThreshold;
	private int maxAmountThresholdId;
	private String thresholdCount;
	private int thresholdCountId;
	private int isActive;

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

	public String getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getMinAmountThreshold() {
		return minAmountThreshold;
	}

	public void setMinAmountThreshold(String minAmountThreshold) {
		this.minAmountThreshold = minAmountThreshold;
	}

	public int getMinAmountThresholdId() {
		return minAmountThresholdId;
	}

	public void setMinAmountThresholdId(int minAmountThresholdId) {
		this.minAmountThresholdId = minAmountThresholdId;
	}

	public String getMaxAmountThreshold() {
		return maxAmountThreshold;
	}

	public void setMaxAmountThreshold(String maxAmountThreshold) {
		this.maxAmountThreshold = maxAmountThreshold;
	}

	public int getMaxAmountThresholdId() {
		return maxAmountThresholdId;
	}

	public void setMaxAmountThresholdId(int maxAmountThresholdId) {
		this.maxAmountThresholdId = maxAmountThresholdId;
	}

	public String getThresholdCount() {
		return thresholdCount;
	}

	public void setThresholdCount(String thresholdCount) {
		this.thresholdCount = thresholdCount;
	}

	public int getThresholdCountId() {
		return thresholdCountId;
	}

	public void setThresholdCountId(int thresholdCountId) {
		this.thresholdCountId = thresholdCountId;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

}
