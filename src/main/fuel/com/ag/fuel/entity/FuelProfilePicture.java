package com.ag.fuel.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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

/**
 * The persistent class for the FUEL_PROFILE_PICTURE database table.
 * 
 */
@Entity
@Table(name = "FUEL_PROFILE_PICTURE")
@NamedQueries({
		@NamedQuery(name = "FuelProfilePicture.fetchByUserId", query = "SELECT t FROM FuelProfilePicture t WHERE t.userId=:userId ") })

public class FuelProfilePicture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUEL_PROFILE_PICTURE_SEQ")
	@SequenceGenerator(name = "FUEL_PROFILE_PICTURE_SEQ", sequenceName = "FUEL_PROFILE_PICTURE_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "USER_ID")
	private int userId;

	@Lob
	@Column(name = "PROFILE_PIC")
	private byte[] profilePic;

	@Column(name = "CORP_ID")
	private String corpId;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

}