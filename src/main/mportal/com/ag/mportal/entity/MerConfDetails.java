package com.ag.mportal.entity;

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
 * The persistent class for the ECR_SAF database table.
 * 
 */
@Entity
@Table(name = "MER_CONF_DETAILS")
@NamedQueries({ @NamedQuery(name = "MerConfDetails.fetchById", query = "SELECT f FROM MerConfDetails f where f.recId IN :ids"),
	@NamedQuery(name = "MerConfDetails.fetchRecById", query = "SELECT bc FROM MerConfDetails mcd, BuildControlConfig bc WHERE bc.mappedValue = mcd.value AND mcd.recId IN :ids")})
public class MerConfDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MER_CONF_DETAILS_ID_GENERATOR", sequenceName = "MER_CONF_DETAILS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MER_CONF_DETAILS_ID_GENERATOR")
	@Column(name = "ID")
	private long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "REC_ID")
	private long recId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getRecId() {
		return recId;
	}

	public void setRecId(long recId) {
		this.recId = recId;
	}
	
	
}