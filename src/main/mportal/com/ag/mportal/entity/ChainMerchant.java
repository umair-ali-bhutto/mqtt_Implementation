package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * The persistent class for the CHAIN_MERCHANTS database table.
 * 
 */
@Entity
@Table(name = "CHAIN_MERCHANTS")
@NamedQueries({ 
		@NamedQuery(name = "ChainMerchants.fetchByID", query = "SELECT d FROM ChainMerchant d where d.chainMerchantMid=:chainMerchantMid"),
		@NamedQuery(name = "ChainMerchants.fetchByOtherParams", query = "SELECT d FROM ChainMerchant d where d.id=:id"),
		@NamedQuery(name = "ChainMerchants.fetchAll", query = "SELECT d FROM ChainMerchant d ORDER BY d.mid"),
		@NamedQuery(name = "ChainMerchants.fetchAllByID", query = "SELECT d FROM ChainMerchant d where d.chainMerchantMid=:chainMerchantMid"),
		@NamedQuery(name = "ChainMerchants.searchByTypeAndMID", query = "SELECT d FROM ChainMerchant d where d.chainMerchantMid=:chainMerchantMid AND d.mid=:mid AND d.isChainMerchant=:type") ,
		@NamedQuery(name = "ChainMerchants.isAlreadyALinkMerchant", query = "SELECT d FROM ChainMerchant d where d.mid=:mid") 

})
public class ChainMerchant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHAIN_MERCHANTS_SEQ")
	@SequenceGenerator(name = "CHAIN_MERCHANTS_SEQ", sequenceName = "CHAIN_MERCHANTS_SEQ", allocationSize = 1)
	private long id;

	private String address;

	@Column(name = "CHAIN_MERCHANT_MID")
	private String chainMerchantMid;

	private String city;

	@Column(name = "ENTRY_BY")
	private String entryBy;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "IS_CHAIN_MERCHANT")
	private String isChainMerchant;

	private String mid;

	private String name;

	private String office;

	@Column(name = "USER_LOGIN_ID")
	private int userLoginId;

	@Column(name = "USER_NAME")
	private String userName;

	public ChainMerchant() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getChainMerchantMid() {
		return this.chainMerchantMid;
	}

	public void setChainMerchantMid(String chainMerchantMid) {
		this.chainMerchantMid = chainMerchantMid;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEntryBy() {
		return this.entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getIsChainMerchant() {
		return this.isChainMerchant;
	}

	public void setIsChainMerchant(String isChainMerchant) {
		this.isChainMerchant = isChainMerchant;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
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

	public int getUserLoginId() {
		return this.userLoginId;
	}

	public void setUserLoginId(int userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}