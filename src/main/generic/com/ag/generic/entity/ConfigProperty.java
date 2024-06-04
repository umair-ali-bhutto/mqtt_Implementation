package com.ag.generic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the CONFIG_PROPERTIES database table.
 * 
 */
@Entity
@Table(name = "CONFIG_PROPERTIES")
@NamedQuery(name = "ConfigProperty.findAll", query = "SELECT d FROM ConfigProperty d WHERE d.isActive = 1")
@NamedQuery(name = "ConfigProperty.findByGroup", query = "SELECT d FROM ConfigProperty d WHERE d.isActive = 1 AND d.groupA=:group")
public class ConfigProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int code;

	@Column(name = "PROP_KEY")
	private String propKey;
	
	
	@Column(name = "GROUP_A")
	private String groupA;
	
	
	@Column(name = "IS_ACTIVE")
	private int isActive;


	@Column(name = "VALUE")

	private String value;

	public ConfigProperty() {
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getPropKey() {
		return this.propKey;
	}

	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}
	
	public String getGroupA() {
		return this.groupA;
	}

	public void setGroupA(String groupA) {
		this.groupA = groupA;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

} 