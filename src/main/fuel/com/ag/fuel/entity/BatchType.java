package com.ag.fuel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the BATCH_TYPE_C database table.
 * 
 */
//@Entity
@Table(name="BATCH_TYPE")
@NamedQuery(name="BatchType.findAll", query="SELECT b FROM BatchType b")
public class BatchType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="C_CODE")
	private BigDecimal cCode;

	@Column(name="C_DESC")
	private String cDesc;

	@Column(name="C_T_CODE")
	private BigDecimal cTCode;

	@Column(name="C_T_DESC")
	private String cTDesc;

	@Column(name="CARD_TYPE")
	private String cardType;

	private BigDecimal id;

	private String nii;

	@Column(name="P_CODE")
	private BigDecimal pCode;

	@Column(name="P_DESC")
	private String pDesc;

	public BatchType() {
	}

	public BigDecimal getCCode() {
		return this.cCode;
	}

	public void setCCode(BigDecimal cCode) {
		this.cCode = cCode;
	}

	public String getCDesc() {
		return this.cDesc;
	}

	public void setCDesc(String cDesc) {
		this.cDesc = cDesc;
	}

	public BigDecimal getCTCode() {
		return this.cTCode;
	}

	public void setCTCode(BigDecimal cTCode) {
		this.cTCode = cTCode;
	}

	public String getCTDesc() {
		return this.cTDesc;
	}

	public void setCTDesc(String cTDesc) {
		this.cTDesc = cTDesc;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getNii() {
		return this.nii;
	}

	public void setNii(String nii) {
		this.nii = nii;
	}

	public BigDecimal getPCode() {
		return this.pCode;
	}

	public void setPCode(BigDecimal pCode) {
		this.pCode = pCode;
	}

	public String getPDesc() {
		return this.pDesc;
	}

	public void setPDesc(String pDesc) {
		this.pDesc = pDesc;
	}

}