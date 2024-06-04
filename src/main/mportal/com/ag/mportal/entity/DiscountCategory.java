package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the BATCH_SETTLEMENT database table.
 * 
 */
@Entity
@Table(name = "DISC_CATEGORY")
public class DiscountCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISC_CATEGORY_SEQ")
	@SequenceGenerator(name = "DISC_CATEGORY_SEQ", sequenceName = "DISC_CATEGORY_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "CAT_CODE")
	private String catCode;

	@Column(name = "CAT_DESC")
	private String catDesc;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}
}