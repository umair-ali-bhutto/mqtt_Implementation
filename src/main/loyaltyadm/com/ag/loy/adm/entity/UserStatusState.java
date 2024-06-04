package com.ag.loy.adm.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the USER_STATUS_STATES database table.
 * 
 */
@Entity
@Table(name="USER_STATUS_STATES")
@NamedQuery(name="UserStatusState.findAll", query="SELECT u FROM UserStatusState u")
public class UserStatusState implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue
    int id;  

	private String corpid;

	private String fromstate;

	private String sdescription;

	private String tostate;

	private String usertype;

	public UserStatusState() {
	}

	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getFromstate() {
		return this.fromstate;
	}

	public void setFromstate(String fromstate) {
		this.fromstate = fromstate;
	}

	public String getSdescription() {
		return this.sdescription;
	}

	public void setSdescription(String sdescription) {
		this.sdescription = sdescription;
	}

	public String getTostate() {
		return this.tostate;
	}

	public void setTostate(String tostate) {
		this.tostate = tostate;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

}