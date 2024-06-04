package com.ag.generic.entity;

import java.io.Serializable;

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
 * The persistent class for the COMPL_ASSIGNMENT_DETAILS database table.
 * 
 */
@Entity
@Table(name = "COMPL_ASSIGNMENT_DETAILS")
@NamedQueries({ 
		@NamedQuery(name = "ComplAssignmentDetails.fetchByID", query = "SELECT c from ComplAssignmentDetail c where c.id=:id"),
		@NamedQuery(name = "ComplAssignmentDetails.fetchByOtherParams", query = "SELECT c from ComplAssignmentDetail c where c.id=:id"),
		@NamedQuery(name = "ComplAssignmentDetails.fetchAll", query = "SELECT c from ComplAssignmentDetail c order by c.id asc"),
		@NamedQuery(name = "ComplAssignmentDetails.fetchAllByID", query = "SELECT c from ComplAssignmentDetail c where c.id=:id order by c.id asc"),
		})
public class ComplAssignmentDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "COMPL_ASSIGNMENT_DETAILS_SEQ")
	@SequenceGenerator(name="COMPL_ASSIGNMENT_DETAILS_SEQ",sequenceName="COMPL_ASSIGNMENT_DETAILS_SEQ", allocationSize=1)
	private long id;

	@Column(name = "ASSIGNMENT_ID")
	private int assignmentId;

	private String channel;

	@Column(name = "USER_LOGIN_ID")
	private int userLoginId;

	@Column(name = "VALUE")
	private String value;

	public ComplAssignmentDetail() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAssignmentId() {
		return this.assignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getUserLoginId() {
		return this.userLoginId;
	}

	public void setUserLoginId(int userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}