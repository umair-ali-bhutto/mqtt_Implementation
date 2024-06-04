package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComplaintsWebService implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String mid;
	private String tid;
	private String complaintType;
	private String complaintSub_Type;
	private String category;
	private String type;
	private String subType;
	private String model;
	private String serialNumber;
	private String maker;
	private String status;	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Karachi")
	private Timestamp entryDate;
	private String entryBy;
	private Timestamp lastUpdated;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Karachi")
	private Timestamp closureDate;
	private String closedBy;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Karachi")
	private Timestamp assignedDate;
	private String assignedTo;
	private String assignedAddress;
	private String complaintDesc;
	private boolean canClosable;
	private boolean canbeAssigned;
	private String remarks;
	
	
	
	public boolean isCanClosable() {
		return canClosable;
	}
	public void setCanClosable(boolean canClosable) {
		this.canClosable = canClosable;
	}
	public boolean isCanbeAssigned() {
		return canbeAssigned;
	}
	public void setCanbeAssigned(boolean canbeAssigned) {
		this.canbeAssigned = canbeAssigned;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}
	public String getComplaintSub_Type() {
		return complaintSub_Type;
	}
	public void setComplaintSub_Type(String complaintSub_Type) {
		this.complaintSub_Type = complaintSub_Type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public Timestamp getClosureDate() {
		return closureDate;
	}
	public void setClosureDate(Timestamp closureDate) {
		this.closureDate = closureDate;
	}
	public String getClosedBy() {
		return closedBy;
	}
	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}
	public Timestamp getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(Timestamp assignedDate) {
		this.assignedDate = assignedDate;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getAssignedAddress() {
		return assignedAddress;
	}
	public void setAssignedAddress(String assignedAddress) {
		this.assignedAddress = assignedAddress;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getComplaintDesc() {
		return complaintDesc;
	}
	public void setComplaintDesc(String complaintDesc) {
		this.complaintDesc = complaintDesc;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}