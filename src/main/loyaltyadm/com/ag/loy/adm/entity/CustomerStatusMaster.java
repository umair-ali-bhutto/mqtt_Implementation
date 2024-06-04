package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the CUSTOMER_STATUS_MASTER database table.
 * 
 */
@Entity
@Table(name="CUSTOMER_STATUS_MASTER")
@NamedQuery(name="CustomerStatusMaster.findAll", query="SELECT c FROM CustomerStatusMaster c where c.id.corpid=:corpid and c.id.custtype=:custtype")
public class CustomerStatusMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CustomerStatusMasterPK id;

	private String active;

	private String sdescription;

	@Column(name="STATUS_NAME")
	private String statusName;

	public CustomerStatusMaster() {
	}
    
	public String getActive() {
		return this.active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getStatusName() {
		return this.statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getSdescription() {
		return this.sdescription;
	}
	public void setStatus(String sdescription) {
		this.sdescription = sdescription;
	}
	
	public CustomerStatusMasterPK getId() {
		return this.id;
	}

	public void setId(CustomerStatusMasterPK id) {
		this.id = id;
	}
	
	
}