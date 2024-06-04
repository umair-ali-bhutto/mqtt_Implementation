package com.ag.generic.entity;

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
 * The persistent class for the COMPLAINTS database table.
 * 
 */
@Entity
@Table(name = "COMPLAINTS")
@NamedQueries({ 
		@NamedQuery(name = "Complaint.fetchComplaintById", query = "SELECT c from Complaint c where c.id=:id"),
		@NamedQuery(name = "Complaint.fetchComplaintByIdUpd", query = "SELECT c from Complaint c where c.id=:id AND c.tid=:tid AND c.mid=:mid"),
		@NamedQuery(name = "Complaint.fetchAllComplaint", query = "SELECT c from Complaint c where c.assignedTo is null order by c.id asc "),
		@NamedQuery(name = "Complaint.fetchAllComplaintOnlyNew", query = "SELECT c from Complaint c where c.status=:status AND c.assignedTo is null order by c.id asc "),
		@NamedQuery(name = "Complaint.fetchAllComplaintNotClosed", query = "SELECT c from Complaint c where c.status=:status AND c.assignedDate is not null AND c.closureDate is null order by c.id asc")
})
public class Complaint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPLAINT_SEQ")
	@SequenceGenerator(name = "COMPLAINT_SEQ", sequenceName = "COMPLAINT_SEQ", allocationSize = 1)
	private int id;

	@Column(name = "ASSIGNED_ADDRESS")
	private String assignedAddress;

	@Column(name = "ASSIGNED_CHANNEL")
	private String assignedChannel;

	@Column(name = "ASSIGNED_DATE")
	private Timestamp assignedDate;

	@Column(name = "ASSIGNED_SELF_FLAG")
	private String assignedSelfFlag;

	@Column(name = "ASSIGNED_TO")
	private String assignedTo;

	private String category;

	@Column(name = "CLOSED_BY")
	private String closedBy;

	@Column(name = "CLOSURE_DATE")
	private Timestamp closureDate;

	@Column(name = "COMPLAINT_DESC")
	private String complaintDesc;

	@Column(name = "COMPLAINT_SUB_TYPE")
	private String complaintSubType;

	@Column(name = "COMPLAINT_TYPE")
	private String complaintType;

	private String description;

	@Column(name = "ENTRY_BY")
	private String entryBy;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "ISSUE_ADDRESSED")
	private String issueAddressed;

	@Column(name = "LAST_UPDATED")
	private Timestamp lastUpdated;

	private String maker;

	private String mid;

	private String model;

	@Column(name = "REASON_FAILURE")
	private String reasonFailure;

	private String resolution;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	private String status;

	@Column(name = "SUB_TYPE")
	private String subType;

	private String tid;

	@Column(name = "TYPE")
	private String type;

	public Complaint() {
	}

	public String getAssignedAddress() {
		return this.assignedAddress;
	}

	public void setAssignedAddress(String assignedAddress) {
		this.assignedAddress = assignedAddress;
	}

	public String getAssignedChannel() {
		return this.assignedChannel;
	}

	public void setAssignedChannel(String assignedChannel) {
		this.assignedChannel = assignedChannel;
	}

	public Timestamp getAssignedDate() {
		return this.assignedDate;
	}

	public void setAssignedDate(Timestamp assignedDate) {
		this.assignedDate = assignedDate;
	}

	public String getAssignedSelfFlag() {
		return this.assignedSelfFlag;
	}

	public void setAssignedSelfFlag(String assignedSelfFlag) {
		this.assignedSelfFlag = assignedSelfFlag;
	}

	public String getAssignedTo() {
		return this.assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getClosedBy() {
		return this.closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	public Timestamp getClosureDate() {
		return this.closureDate;
	}

	public void setClosureDate(Timestamp closureDate) {
		this.closureDate = closureDate;
	}

	public String getComplaintDesc() {
		return this.complaintDesc;
	}

	public void setComplaintDesc(String complaintDesc) {
		this.complaintDesc = complaintDesc;
	}

	public String getComplaintSubType() {
		return this.complaintSubType;
	}

	public void setComplaintSubType(String complaintSubType) {
		this.complaintSubType = complaintSubType;
	}

	public String getComplaintType() {
		return this.complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEntryBy() {
		return this.entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIssueAddressed() {
		return this.issueAddressed;
	}

	public void setIssueAddressed(String issueAddressed) {
		this.issueAddressed = issueAddressed;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getMaker() {
		return this.maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getReasonFailure() {
		return this.reasonFailure;
	}

	public void setReasonFailure(String reasonFailure) {
		this.reasonFailure = reasonFailure;
	}

	public String getResolution() {
		return this.resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubType() {
		return this.subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}