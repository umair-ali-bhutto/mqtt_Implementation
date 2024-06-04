package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the "PARAMETERS" database table.
 * 
 */
@Entity
@Table(name="PARAMETERS")
@NamedQuery(name="Parameter.findAll", query="SELECT p FROM Parameter p where p.corpid=:corpid")
public class Parameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String corpid;

	private String description;

	private String exvalue;

	private String name;

	@Column(name="TYPE")
	private String type;

	@Column(name="VALUE")
	private String value;

	public Parameter() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExvalue() {
		return this.exvalue;
	}

	public void setExvalue(String exvalue) {
		this.exvalue = exvalue;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}