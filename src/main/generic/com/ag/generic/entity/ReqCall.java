package com.ag.generic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the REQ_CALLS database table.
 * 
 */
@Entity
@Table(name = "REQ_CALLS")
@NamedQuery(name = "ReqCall.fetchByAllRequestCalls",query = "SELECT a FROM ReqCall a order by a.id asc")
public class ReqCall implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "REQUEST_CALLS_SEQ")
	@SequenceGenerator(name="REQUEST_CALLS_SEQ",sequenceName="REQUEST_CALLS_SEQ", allocationSize=1)
	private long id;

	private String description;

	@Column(name = "MESSAGE_TYPE")
	private String messageType;

	@Column(name = "REQUEST_CLASS")
	private String requestClass;

	private String status;

	public ReqCall() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessageType() {
		return this.messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getRequestClass() {
		return this.requestClass;
	}

	public void setRequestClass(String requestClass) {
		this.requestClass = requestClass;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}