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
 * The persistent class for the COMPL_STATUS database table.
 * 
 */
@Entity
@Table(name = "COMPL_STATUS")
@NamedQueries({
		@NamedQuery(name = "ComplStatus.fetchByID", query = "SELECT d FROM ComplStatus d where d.id=:id"),
		@NamedQuery(name = "ComplStatus.fetchAll", query = "SELECT d FROM ComplStatus d")
	})
public class ComplStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "VALUE")
	private String value;

	public ComplStatus() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}