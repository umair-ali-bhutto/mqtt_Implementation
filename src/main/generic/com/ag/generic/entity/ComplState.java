package com.ag.generic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the COMPL_STATE database table.
 * 
 */
@Entity
@Table(name = "COMPL_STATE")
@NamedQueries({ 
		@NamedQuery(name = "ComplState.fetchByID", query = "SELECT d FROM ComplState d where d.id=:id"),
		@NamedQuery(name = "ComplState.fetchAll", query = "SELECT d FROM ComplState d")
	})
public class ComplState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String category;

	@Column(name = "ROLE_GROUP")
	private String roleGroup;

	@Column(name = "SEQUENCE")
	private String sequence;

	@Column(name = "STATUS_FROM")
	private String statusFrom;

	@Column(name = "STATUS_TO")
	private String statusTo;

	@Column(name = "SUB_TYPE")
	private String subType;

	@Column(name = "TYPE")
	private String type;

	public ComplState() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRoleGroup() {
		return this.roleGroup;
	}

	public void setRoleGroup(String roleGroup) {
		this.roleGroup = roleGroup;
	}

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getStatusFrom() {
		return this.statusFrom;
	}

	public void setStatusFrom(String statusFrom) {
		this.statusFrom = statusFrom;
	}

	public String getStatusTo() {
		return this.statusTo;
	}

	public void setStatusTo(String statusTo) {
		this.statusTo = statusTo;
	}

	public String getSubType() {
		return this.subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}