package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ECR_LOGS database table.
 * 
 */
@Entity
@Table(name = "ECR_LOGS")
@NamedQueries({
	@NamedQuery(name = "EcrLog.fetchAllById", query = "SELECT f FROM EcrLog f WHERE f.id=:id and f.state=:state AND f.mid=:mid and f.tid=:tid and f.enquiryStatus='SUCCESS'" ),
	@NamedQuery(name = "EcrLog.fetchAllRecByMIDTID", query = "SELECT f FROM EcrLog f WHERE f.state=:state AND f.mid=:mid and f.tid=:tid and f.enquiryStatus='SUCCESS'"),
	@NamedQuery(name = "EcrLog.fetchAllRecByMIDTIDOrder", query = "SELECT f FROM EcrLog f WHERE f.state=:state AND f.mid=:mid and f.tid=:tid and f.enquiryStatus='SUCCESS' ORDER BY f.id DESC"),
	@NamedQuery(name = "EcrLog.fetchAllRecByOrderId", query = "SELECT f FROM EcrLog f WHERE f.orderId=:orderId  and f.state='ENQUIRY' AND f.recType='ENQUIRY'"),
	@NamedQuery(name = "EcrLog.fetchAllPaymentByMidTid", query = "SELECT f FROM EcrLog f WHERE f.mid=:mid and f.tid=:tid and f.state='ENQUIRY' AND f.recType='ENQUIRY'"),
	@NamedQuery(name = "EcrLog.fetchByOrderId", query = "SELECT f FROM EcrLog f WHERE f.orderId=:orderId")
})
public class EcrLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ECR_LOGS_ID_GENERATOR", sequenceName = "ECR_LOGS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECR_LOGS_ID_GENERATOR")
	private long id;

	private String amount;

	@Column(name = "CONSUMER_NAME")
	private String consumerName;

	@Column(name = "CONSUMER_NUMBER")
	private String consumerNumber;

	@Temporal(TemporalType.DATE)
	@Column(name = "DUE_DATE")
	private Date dueDate;
	
	
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;
	
	
	@Column(name = "PAYMENT_DATE")
	private Timestamp paymentDate;
	
	@Column(name = "PAYMENT_AMOUNT")
	private String paymentAmount;

	private String mid;

	private String remarks;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "REC_TYPE")
	private String recType;
	
	@Column(name = "ENQUIRY_STATUS")
	private String enquiryStatus;
	
	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;

	private String tid;

	@Column(name = "CONFIG_ECR_ID")
	private int configEcrId;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "TXN_DETAIL_ID")
	private long txnDetailId;

	@Column(name = "ORDER_ID")
	private String orderId;

	@Column(name = "HOST_RESPONSE_CODE")
	private String hostResponseCode;
	
	@Lob
    @Column(name = "RESPONSE_DATA", columnDefinition = "CLOB")
    private String responseData;
	
	@Column(name = "CORPORATE")
	private String corporate;

	public EcrLog() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getConsumerName() {
		return this.consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getConsumerNumber() {
		return this.consumerNumber;
	}

	public void setConsumerNumber(String consumerNumber) {
		this.consumerNumber = consumerNumber;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRrn() {
		return this.rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	

	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public int getConfigEcrId() {
		return configEcrId;
	}

	public void setConfigEcrId(int configEcrId) {
		this.configEcrId = configEcrId;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getEnquiryStatus() {
		return enquiryStatus;
	}

	public void setEnquiryStatus(String enquiryStatus) {
		this.enquiryStatus = enquiryStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getRecType() {
		return recType;
	}

	public void setRecType(String recType) {
		this.recType = recType;
	}

	public long getTxnDetailId() {
		return txnDetailId;
	}

	public void setTxnDetailId(long txnDetailId) {
		this.txnDetailId = txnDetailId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getHostResponseCode() {
		return hostResponseCode;
	}

	public void setHostResponseCode(String hostResponseCode) {
		this.hostResponseCode = hostResponseCode;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public String getCorporate() {
		return corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

}