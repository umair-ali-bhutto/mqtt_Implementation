package com.ag.mportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the MERCHANT database table.
 * 
 */
@Entity
//@NamedQuery(name = "Merchant.findAll", query = "SELECT m FROM Merchant m WHERE m.id=:id")
@NamedQuery(name = "Merchant.fetchByID", query = "SELECT m FROM Merchant m WHERE m.id=:id")
@NamedQuery(name = "Merchant.fetchByOtherParams", query = "SELECT m FROM Merchant m WHERE m.id=:id")
@NamedQuery(name = "Merchant.fetchAll", query = "SELECT m FROM Merchant m order by m.id asc")
@NamedQuery(name = "Merchant.fetchAllByID", query = "SELECT m FROM Merchant m WHERE m.id=:id order by m.id asc")
public class Merchant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "MERCHANT_SEQ")
	@SequenceGenerator(name="MERCHANT_SEQ",sequenceName="MERCHANT_SEQ", allocationSize=1)
	private long id;

	@Column(name = "CHAIN_MERCHANT_ID")
	private String chainMerchantId;

	@Column(name = "IS_CHAIN_MERCHANT")
	private String isChainMerchant;

	private int mid;

	private String name;

	private String office;

	public Merchant() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getChainMerchantId() {
		return this.chainMerchantId;
	}

	public void setChainMerchantId(String chainMerchantId) {
		this.chainMerchantId = chainMerchantId;
	}

	public String getIsChainMerchant() {
		return this.isChainMerchant;
	}

	public void setIsChainMerchant(String isChainMerchant) {
		this.isChainMerchant = isChainMerchant;
	}

	public int getMid() {
		return this.mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

}