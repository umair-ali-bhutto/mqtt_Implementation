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
@Table(name = "BUILD_CONTROL_CONFIG")
@NamedQueries({
	@NamedQuery(name = "BuildControlConfig.fetchByModel", query = "SELECT f FROM BuildControlConfig f where f.controlModel=:controlModel and isActive=1"),
	@NamedQuery(name = "BuildControlConfig.fetchById", query = "SELECT f FROM BuildControlConfig f where f.id IN :ids and isActive=1")
})
public class BuildControlConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "BUILD_CONTROL_CONFIG_ID_GENERATOR", sequenceName = "BUILD_CONTROL_CONFIG_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUILD_CONTROL_CONFIG_ID_GENERATOR")
	private long id;

	

	@Column(name = "CONTROL_NAME")
	private String controlName;

	@Column(name = "CONTROL_STATUS")
	private int controlStatus;	


	@Column(name = "CONTROL_MODEL")
	private String controlModel;
	
	@Column(name = "CONTROL_USER")
	private String controlUser;
	
	@Column(name = "IS_ACTIVE")
	private int isActive;
	
	@Column(name = "MAPPED_VALUE")
	private String mappedValue;
	
	

	public BuildControlConfig() {
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getControlName() {
		return controlName;
	}



	public void setControlName(String controlName) {
		this.controlName = controlName;
	}



	public int getControlStatus() {
		return controlStatus;
	}



	public void setControlStatus(int controlStatus) {
		this.controlStatus = controlStatus;
	}



	public String getControlModel() {
		return controlModel;
	}



	public void setControlModel(String controlModel) {
		this.controlModel = controlModel;
	}



	public String getControlUser() {
		return controlUser;
	}



	public void setControlUser(String controlUser) {
		this.controlUser = controlUser;
	}



	public int getIsActive() {
		return isActive;
	}



	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}



	public String getMappedValue() {
		return mappedValue;
	}



	public void setMappedValue(String mappedValue) {
		this.mappedValue = mappedValue;
	}

}